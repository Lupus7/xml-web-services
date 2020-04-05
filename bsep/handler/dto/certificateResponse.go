package dto

type CertificateResponse struct {
	Country      string
	Organization string
	Address      string
	Province     string
	Email        string
	SerialNumber string
	PostalCode   string
	Revoken      bool
}
