package handler

import (
	"bsep/handler/dto"
	"bsep/service"
	"crypto/x509"
	"errors"
	"fmt"
	"github.com/labstack/echo/v4"
	"html/template"
	"io"
	"net/http"
	"os"
	"strconv"
	"strings"
)

type CertificateHandler struct {
	certificateService *service.CertificateService
}

func NewCertificateHandler(cs *service.CertificateService) *CertificateHandler {
	return &CertificateHandler{certificateService: cs}
}

const maxUploadSize = 2 * 1024 * 1024 // 2 mb
const uploadPath = "./keys"

func (ch *CertificateHandler) CreateNew(c echo.Context) error {
	data := []string{}
	certs := ch.certificateService.ValidToBeCA()
	for _, c := range certs {
		majorInfo := fmt.Sprintf("%s, %s, %s, %s, %s, %s, %s", c.Subject.Organization[0], c.Subject.StreetAddress[0], c.Subject.Locality[0], c.Subject.Province[0], c.Subject.Country[0], c.Subject.SerialNumber, c.Subject.PostalCode[0])
		fmt.Sprintf("%s", c.Subject)
		data = append(data, majorInfo)
	}
	tpl := template.Must(template.ParseFiles("views/create.gohtml"))

	return tpl.Execute(c.Response().Writer, data)
}

func (ch *CertificateHandler) Create(c echo.Context) error {
	var request dto.CertificateRequest
	err := c.Bind(&request)
	fmt.Println("ddd", request.SerialNumber)
	if err != nil {
		fmt.Println(err.Error())
		return err
	}
	err = ch.certificateService.CreateCertificate(&request)
	if err != nil {
		return err
	}

	return c.HTML(http.StatusOK, `<body><h3>Successfully created...</h3><br><a href="/home">Go to home page</a></body>`)
}

func (ch *CertificateHandler) Home(c echo.Context) error {
	tpl := template.Must(template.ParseFiles("views/home.gohtml"))
	certificats, err := ch.certificateService.ReadKeyStoreAllInfo()
	if err != nil {
		return err
	}
	if len(certificats) == 0 {
		return tpl.Execute(c.Response().Writer, []dto.CertificateResponse{})
	}
	responses := []dto.CertificateResponse{}
	for _, c := range certificats {
		revoked := ch.certificateService.IsRevoked(c)
		responses = append(responses, toCertificateResponse(c, revoked))
	}

	return tpl.Execute(c.Response().Writer, responses)
}

func (ch *CertificateHandler) ReadAllInfo(c echo.Context) error {
	certs, err := ch.certificateService.ReadKeyStoreAllInfo()
	if err != nil {
		return err
	}

	return c.JSON(http.StatusOK, certs)
}

func (ch *CertificateHandler) Revoke(c echo.Context) error {
	//I need to splitr url path : /revoke/2313
	parts := strings.Split(c.Request().URL.Path, "/")
	serialNumber := parts[2]
	err := ch.certificateService.RevokeCertificate(serialNumber)
	if err != nil {
		fmt.Println("here we got the error : ", err.Error())
		return err
	}
	return c.HTML(http.StatusOK, `<body><h3>Successfully revoked...</h3><br><a href="/home">Go to home page</a></body>`)
}

func (ch *CertificateHandler) Download(c echo.Context) error {
	parts := strings.Split(c.Request().URL.Path, "/")
	Filename := parts[2]
	if Filename == "" {
		//Get not set, send a 400 bad request
		return errors.New("Get 'file' not specified in url.")
	}
	fmt.Println("Client requests: " + Filename)
	Openfile, err := os.Open(fmt.Sprintf("keys/certpem%s.pem", Filename))
	defer Openfile.Close() //Close after function return
	if err != nil {
		return err
	}

	FileHeader := make([]byte, 512)
	//Copy the headers into the FileHeader buffer
	Openfile.Read(FileHeader)
	//Get content type of file
	FileContentType := http.DetectContentType(FileHeader)

	//Get the file size
	FileStat, _ := Openfile.Stat()                     //Get info from file
	FileSize := strconv.FormatInt(FileStat.Size(), 10) //Get file size as a string

	//Send the headers
	c.Response().Writer.Header().Set("Content-Disposition", "attachment; filename="+Filename)
	c.Response().Writer.Header().Set("Content-Type", FileContentType)
	c.Response().Writer.Header().Set("Content-Length", FileSize)

	//Send the file
	//We read 512 bytes from the file already, so we reset the offset back to 0
	Openfile.Seek(0, 0)
	io.Copy(c.Response().Writer, Openfile) //'Copy' the file to the client

	return nil
}

func toCertificateResponse(c *x509.Certificate, revoked bool) dto.CertificateResponse {
	return dto.CertificateResponse{
		Country:      c.Subject.Country[0],
		Organization: c.Subject.Organization[0],
		Address:      c.Subject.StreetAddress[0],
		Province:     c.Subject.Province[0],
		Email:        c.EmailAddresses[0],
		SerialNumber: c.SerialNumber.String(),
		PostalCode:   c.Subject.PostalCode[0],
		Revoken:      revoked,
	}
}
