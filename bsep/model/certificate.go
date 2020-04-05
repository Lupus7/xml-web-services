package model

import (
	"crypto/x509"
	"github.com/jinzhu/gorm"
	"strconv"
	"time"
)

type Certificate struct {
	gorm.Model
	Country      string
	Organization string
	Address      string
	Province     string
	Email        string
	Locality     string
	SerialNumber int
	PostalCode   int
	ValidFrom    time.Time
	ValidTo      time.Time
	Valid        bool
}

func CreateCertificate(cert *x509.Certificate) *Certificate {
	serialNumber, _ := strconv.Atoi(cert.SerialNumber.String())
	postalCode, _ := strconv.Atoi(cert.Subject.PostalCode[0])
	return &Certificate{
		Country:      cert.Subject.Country[0],
		Organization: cert.Subject.Organization[0],
		Address:      cert.Subject.Organization[0],
		Province:     cert.Subject.Organization[0],
		Email:        cert.EmailAddresses[0],
		Locality:     cert.Subject.Locality[0],
		SerialNumber: serialNumber,
		PostalCode:   postalCode,
		ValidFrom:    cert.NotBefore,
		ValidTo:      cert.NotAfter,
		Valid:        true,
	}
}
