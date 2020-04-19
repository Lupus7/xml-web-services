package service

import (
	"errors"
	"fmt"
	"github.com/labstack/echo"
	"html/template"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"xml-web-services/cars/model"
)

type ImageService struct {
}

func NewImageService() *ImageService {
	return &ImageService{}
}

func (is *ImageService) Create(carID uint, r io.ReadCloser, filename string) error {
	defer r.Close()
	path, err := is.imagePath(carID)
	if err != nil {
		return err
	}
	dst, err := os.Create(path + filename)
	if err != nil {
		return err
	}
	defer dst.Close()
	_, err = io.Copy(dst, r)
	if err != nil {
		return err
	}
	return nil
}

func (is *ImageService) imgPath(carID uint) string {
	return fmt.Sprintf("images/cars/%v/", carID)
}

func (is *ImageService) imagePath(carID uint) (string, error) {
	carPath := is.imgPath(carID)
	err := os.Mkdir(carPath, 0755)
	if err != nil {
		return "", err
	}
	return carPath, nil
}

func (is *ImageService) Delete(i *model.Image) error {
	return os.Remove(i.RelPath())
}

func (is *ImageService) ByCarID(carID uint) ([]model.Image, error) {
	path := is.imgPath(carID)
	stringss, err := filepath.Glob(path + "*")
	if err != nil {
		return nil, err
	}
	ret := make([]model.Image, len(stringss))
	for i := range stringss {
		stringss[i] = strings.Replace(stringss[i], path, "", 1)
		ret[i] = model.Image{
			Filename: stringss[i],
			CarId:    carID,
		}
	}
	return ret, nil
}

//We use three steps for uploading files as follows:
//
//Add enctype="multipart/form-data" to your form.
//Call r.ParseMultipartForm on the server side to save the file either to memory or to a temporary file.
//Call r.FormFile to get the file handle and save to the file system.
func (is *ImageService) SaveImage(c echo.Context) error {
	if c.Request().Method == "GET" {
		t, _ := template.ParseFiles("upload.gohtml")
		t.Execute(c.Response().Writer, nil)
	} else {
		fmt.Printf("method is post")
		c.Request().ParseMultipartForm(32 << 20)
		file, hander, err := c.Request().FormFile("uploadfile")
		if err != nil {
			fmt.Println(err.Error())
			return err
		}
		defer file.Close()
		f, err := os.OpenFile("./assets/images/"+hander.Filename, os.O_WRONLY|os.O_CREATE, 777)
		if err != nil {
			fmt.Println(err.Error())
			return err
		}
		defer f.Close()
		io.Copy(f, file)
	}
	return nil
}

func (is *ImageService) ReturnImage(c echo.Context) error {
	fmt.Println(c.Request().URL.Path)
	filename := c.Request().URL.Query().Get("file")
	if filename == "" {
		http.Error(c.Response().Writer, "no such picture ", http.StatusNotFound)
		return errors.New("no such picture")
	}
	fmt.Println("client request ", filename)
	openfile, err := os.Open("./assets/images/" + filename)
	defer openfile.Close()
	if err != nil {
		fmt.Println(err.Error())
		return errors.New("file not found")
	}
	fileHeader := make([]byte, 512)
	openfile.Read(fileHeader)
	//get content type
	fileContentType := http.DetectContentType(fileHeader)
	//get file size
	fileStat, _ := openfile.Stat()
	fileSize := strconv.FormatInt(fileStat.Size(), 10)
	//set headers
	c.Response().Writer.Header().Set("Content-Disposition", "attachment; filename="+filename)
	c.Response().Writer.Header().Set("Content-Type", fileContentType)
	c.Response().Writer.Header().Set("Content-Lenght", fileSize)
	//Send file
	openfile.Seek(0, 0)
	io.Copy(c.Response().Writer, openfile)
	return nil

}
