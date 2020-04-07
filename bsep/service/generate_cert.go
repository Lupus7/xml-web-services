package service

import (
	"bsep/handler/dto"
	"bsep/model"
	"bsep/repository"
	"bytes"
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/x509"
	"crypto/x509/pkix"
	"encoding/gob"
	"encoding/pem"
	"fmt"
	"github.com/labstack/echo/v4"
	"github.com/pavel-v-chernykh/keystore-go"
	"golang.org/x/crypto/ocsp"
	"io/ioutil"
	"log"
	"math/big"
	"net/http"
	"net/url"
	"os"
	"strconv"
	"strings"
	"time"
)

type CertificateService struct {
	CertificateDB *repository.CertificateDB
}

//Here should be passed request, then parse to certificate, and be written to file
func (cs *CertificateService) CreateCertificate(request *dto.CertificateRequest) error {
	//Parsing values
	serialNumber, err := strconv.Atoi(request.SerialNumber)
	if err != nil {
		log.Println(err.Error())
		return err
	}
	startDate, _ := time.Parse("2006-01-02", request.StartsAt)
	endDate, _ := time.Parse("2006-01-02", request.EndsAt)
	isCA := false
	if request.CertificateAuthority == "on" {
		isCA = true
	}

	template := &x509.Certificate{
		IsCA:                  isCA,
		BasicConstraintsValid: true,
		SubjectKeyId:          []byte{1, 2, 3},
		SerialNumber:          big.NewInt(int64(serialNumber)),
		Subject: pkix.Name{
			Country:       []string{request.Country},
			Organization:  []string{request.Organization},
			PostalCode:    []string{request.PostalCode},
			StreetAddress: []string{request.StreetAddress},
			Locality:      []string{request.Locality},
			SerialNumber:  request.SerialNumber,
			Province:      []string{request.Province},
		},
		EmailAddresses: []string{request.EmailAddress},
		NotBefore:      startDate,
		NotAfter:       endDate,
		// see http://golang.org/pkg/crypto/x509/#KeyUsage
		ExtKeyUsage: []x509.ExtKeyUsage{x509.ExtKeyUsageClientAuth, x509.ExtKeyUsageServerAuth},
		KeyUsage:    x509.KeyUsageDigitalSignature | x509.KeyUsageCertSign,
	}
	fmt.Println(template)

	var parent = template
	//Check field issuer from request, if issuer is root-self signed, certificat is self signed
	// otherwise, get parent certificate by serial number, which must be parsed from request object
	//exm: FTN, Address, Srbija, Novi Sad, 243314,21000 - serial number is on 5.element, we must also trim spaces
	if request.Issuer != "root - self signed" {
		infoParts := strings.Split(request.Issuer, ",")
		serialNumberString := strings.TrimSpace(infoParts[5])
		parentSerialKey, err := strconv.Atoi(serialNumberString)
		if err != nil {
			return err
		}
		rootCert := cs.FindCertificatBySerialNumber(parentSerialKey)
		if rootCert != nil {
			parent = rootCert
		}
	}

	//Save certificate model in dataabse
	err = cs.saveCertificateInDatabase(template)
	if err != nil {
		return err
	}

	// generate private key
	privatekey, err := rsa.GenerateKey(rand.Reader, 2048)
	if err != nil {
		return err
	}
	publickey := &privatekey.PublicKey

	cert, err := x509.CreateCertificate(rand.Reader, template, parent, publickey, privatekey)
	if err != nil {
		return err
	}

	f, _ := os.Open("./keystore.jks")
	if f == nil {
		fmt.Println("This is the first certificate creating")
	}
	keyStore, _ := keystore.Decode(f, []byte("password"))

	//If we already have file, just append new certificate
	if keyStore != nil {
		entry := keyStore["alias"]
		privKeyEntry := entry.(*keystore.PrivateKeyEntry)
		privKeyEntry.CertChain = append(privKeyEntry.CertChain, keystore.Certificate{Content: cert})

	}
	// IF keyStore is nil, we initialize it, and set the first certificate information
	if keyStore == nil {
		keyStore = keystore.KeyStore{
			"alias": &keystore.PrivateKeyEntry{CertChain: []keystore.Certificate{
				keystore.Certificate{
					Content: cert,
				},
			},
			},
		}
	}

	//if we already have KeysStore, just append new certificate into the slice

	password := []byte{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'}
	defer zeroing(password)
	err = writeKeyStore(keyStore, "keystore.jks", password)
	if err != nil {
		return err
	}

	// save private key
	pkey := x509.MarshalPKCS1PrivateKey(privatekey)
	pubkey, _ := x509.MarshalPKIXPublicKey(publickey)
	ioutil.WriteFile("keys/private.key", pkey, 0777)
	fmt.Println("private key saved to keys/private.key")

	// save public key

	ioutil.WriteFile("keys/public.key", pubkey, 0777)
	fmt.Println("public key saved to keys/public.key")

	// save cert
	ioutil.WriteFile("keys/cert.pem", cert, 0777)
	fmt.Println("certificate saved to cert.pem")

	// these are the files save with encoding/gob style
	privkeyfile, _ := os.Create("keys/privategob.key")
	privkeyencoder := gob.NewEncoder(privkeyfile)
	privkeyencoder.Encode(privatekey)
	privkeyfile.Close()

	pubkeyfile, _ := os.Create("keys/publickgob.key")
	pubkeyencoder := gob.NewEncoder(pubkeyfile)
	pubkeyencoder.Encode(publickey)
	pubkeyfile.Close()

	// this will create plain text PEM file.
	filestring := fmt.Sprintf("keys/certpem%s.pem", request.SerialNumber)
	pemfile, _ := os.Create(filestring)
	var pemkey = &pem.Block{
		Type:  "RSA PRIVATE KEY",
		Bytes: x509.MarshalPKCS1PrivateKey(privatekey)}
	pem.Encode(pemfile, pemkey)
	pemfile.Close()
	return nil
}

func (cs *CertificateService) CheckUser(username, password string) error {
	return cs.CertificateDB.ValidateUser(username, password)
}

func (cs *CertificateService) saveCertificateInDatabase(c *x509.Certificate) error {
	certificat := &model.Certificate{
		Country:      c.Subject.Country[0],
		Organization: c.Subject.Organization[0],
		ValidFrom:    c.NotBefore,
		ValidTo:      c.NotAfter,
		Valid:        true,
	}
	if c.NotAfter.Before(time.Now()) {
		certificat.Valid = false
	}
	return cs.CertificateDB.Create(certificat)
}

//All information of certificates, read from keystore file .. password needed
func (cs *CertificateService) ReadKeyStoreAllInfo() ([]*x509.Certificate, error) {
	certificates := []*x509.Certificate{}
	f, err := os.Open("./keystore.jks")
	if err != nil {
		fmt.Println(err.Error())
		return nil, err
	}
	keyStore, err := keystore.Decode(f, []byte("password"))
	if err != nil {
		fmt.Println(err.Error())
		return nil, err
	}
	entry := keyStore["alias"]
	privKeyEntry := entry.(*keystore.PrivateKeyEntry)
	for i := 0; i < len(privKeyEntry.CertChain); i++ {
		key, err := x509.ParseCertificate(privKeyEntry.CertChain[i].Content)
		if err != nil {
			fmt.Println(err.Error())
			return nil, err
		}
		certificates = append(certificates, key)
	}

	return certificates, nil
}

//Return certificate with given serial number
func (cs *CertificateService) FindCertificatBySerialNumber(number int) *x509.Certificate {
	wantedSerialKey := big.NewInt(int64(number))
	certifications, err := cs.ReadKeyStoreAllInfo()
	if err != nil {
		return nil
	}
	for _, c := range certifications {
		if c.SerialNumber.Cmp(wantedSerialKey) == 0 {
			return c
		}
	}
	return nil
}

//Read all certificates, but return only those who are allowed to be CA and not expired yet
//TODO: also make sure to return only those who are not yet revoked
func (cs *CertificateService) ValidToBeCA() []*x509.Certificate {
	certifications, err := cs.ReadKeyStoreAllInfo()
	toReturn := []*x509.Certificate{}
	if err != nil {
		return nil
	}
	for _, c := range certifications {
		if c.IsCA && time.Now().Before(c.NotAfter) && !cs.IsRevoked(c) {
			toReturn = append(toReturn, c)
		}
	}
	return toReturn
}

//Here we need to revoke certificates, but first need to have this on mind:
//If another certificate from the chain, has revoked certificate as issuer, that that certificate is also revoked
func (cs *CertificateService) RevokeCertificate(s string) error {
	number, err := strconv.Atoi(s)
	if err != nil {
		return err
	}
	certificat := cs.FindCertificatBySerialNumber(number)
	if certificat == nil {
		return &echo.HTTPError{
			Code:    http.StatusInternalServerError,
			Message: "Certificat with that serial number does not exist",
		}
	}
	toBeRevoked := []*x509.Certificate{}
	toBeRevoked = append(toBeRevoked, certificat)
	allCertificats, err := cs.ReadKeyStoreAllInfo()
	if err != nil {
		return err
	}
	var position int
	for i, c := range allCertificats {
		if c.SerialNumber.String() == certificat.SerialNumber.String() {
			position = i
		}
	}
	for i := position + 1; i < len(allCertificats); i++ {
		for _, cc := range toBeRevoked {
			if allCertificats[i].Issuer.SerialNumber == cc.SerialNumber.String() {
				toBeRevoked = append(toBeRevoked, allCertificats[i])
			}
		}
	}
	//for _, c := range allCertificats {
	//	if c.Issuer.SerialNumber == certificat.SerialNumber.String() {
	//		toBeRevoked = append(toBeRevoked, c)
	//	}
	//}

	//convert big.Int to int , fastest way to convert to string, and then to int
	//save all revoked certificates in database
	for _, c := range toBeRevoked {
		serial, err := strconv.Atoi(c.SerialNumber.String())
		if err != nil {
			return err
		}
		err = cs.CertificateDB.RevokeCertificat(serial)
		if err != nil {
			return err
		}
	}

	return nil
}

func (cs *CertificateService) IsRevoked(c *x509.Certificate) bool {
	revoked, _ := cs.CertificateDB.GetAllRevoked()
	isRevoked := false
	for _, r := range revoked {
		if c.SerialNumber.Cmp(big.NewInt(int64(r.CertificatID))) == 0 {
			//this one is revoked,
			isRevoked = true
		}
	}
	return isRevoked
}

// See: https://groups.google.com/forum/#!topic/golang-nuts/QC5FOysyVxg
func (cs *CertificateService) IsCertificateRevokedByOCSP(commonName string, clientCert, issuerCert *x509.Certificate, ocspServer string) bool {
	opts := &ocsp.RequestOptions{Hash: crypto.SHA1}
	buffer, err := ocsp.CreateRequest(clientCert, issuerCert, opts)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	fmt.Println(buffer)
	httpRequest, err := http.NewRequest(http.MethodPost, "http://ocsp.int-x3.letsencrypt.org", bytes.NewBuffer(buffer))
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	ocspUrl, err := url.Parse("http://ocsp.int-x3.letsencrypt.org")
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	httpRequest.Header.Add("Content-Type", "application/ocsp-request")
	httpRequest.Header.Add("Accept", "application/ocsp-response")
	httpRequest.Header.Add("host", ocspUrl.Host)
	httpClient := &http.Client{}
	httpResponse, err := httpClient.Do(httpRequest)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}

	defer httpResponse.Body.Close()
	output, err := ioutil.ReadAll(httpResponse.Body)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	fmt.Println("OUTPUT: ", output)
	ocspResponse, err := ocsp.ParseResponse(output, issuerCert)
	if err != nil {
		fmt.Println(err.Error())
		return false
	}
	if ocspResponse.Status == ocsp.Revoked {
		fmt.Printf("Certificate %s has been revoked by OCSP server %s, refusng connection", commonName, ocspServer)
		return true
	}
	return false
}

////////////////////
func writeKeyStore(keyStore keystore.KeyStore, filename string, password []byte) error {
	f, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer func() {
		if err := f.Close(); err != nil {

		}
	}()
	err = keystore.Encode(f, keyStore, password)
	if err != nil {
		return err
	}
	return nil
}

func zeroing(s []byte) {
	for i := 0; i < len(s); i++ {
		s[i] = 0
	}
}
