package model

import (
	"encoding/xml"
	"time"
)

//type Ad struct {
//	Id        int `gorm:"primary_key"`
//	Car       Car `gorm:"foreignkey:CarId"`
//	CarId     int `gorm:"not null"'`
//	StartDate time.Time
//	EndDate   time.Time
//	Place     string `gorm:"not_null"`
//}

type Ad struct {
	Id        int64     `xml:"xml-web-services-cars id" gorm:"primary_key" `
	StartDate time.Time `xml:"xml-web-services-cars startDate"`
	EndDate   time.Time `xml:"xml-web-services-cars endDate"`
	Place     string    `xml:"xml-web-services-cars place"`
	CarID     int64     `xml:"xml-web-services-cars carID" gorm:"not null"`
}

//type customTime struct{
//	time.Time
//}

//func (c *customTime) UnmarshalXML(d *xml.Decoder, start xml.StartElement) error {
//	const shortForm = "20060102" // yyyymmdd date format
//	var v string
//	d.DecodeElement(&v, &start)
//	parse, err := time.Parse(shortForm, v)
//	if err != nil {
//		return err
//	}
//	*c = customTime{parse}
//	return nil
//}

func (t *Ad) MarshalXML(e *xml.Encoder, start xml.StartElement) error {
	type T Ad
	var layout struct {
		*T
		StartDate *xsdDate `xml:"xml-web-services-cars startDate"`
		EndDate   *xsdDate `xml:"xml-web-services-cars endDate"`
	}
	layout.T = (*T)(t)
	layout.StartDate = (*xsdDate)(&layout.T.StartDate)
	layout.EndDate = (*xsdDate)(&layout.T.EndDate)
	return e.EncodeElement(layout, start)
}
func (t *Ad) UnmarshalXML(d *xml.Decoder, start xml.StartElement) error {
	type T Ad
	var overlay struct {
		*T
		StartDate *xsdDate `xml:"xml-web-services-cars startDate"`
		EndDate   *xsdDate `xml:"xml-web-services-cars endDate"`
	}
	overlay.T = (*T)(t)
	overlay.StartDate = (*xsdDate)(&overlay.T.StartDate)
	overlay.EndDate = (*xsdDate)(&overlay.T.EndDate)
	return d.DecodeElement(&overlay, &start)
}
