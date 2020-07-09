package model

import (
	"encoding/xml"
	"time"
)

type Ad struct {
	Id        int64     `xml:"xml-web-services-cars id" gorm:"primary_key" `
	StartDate time.Time `xml:"xml-web-services-cars startDate"`
	EndDate   time.Time `xml:"xml-web-services-cars endDate"`
	Place     string    `xml:"xml-web-services-cars place"`
	CarId     int64     `xml:"xml-web-services-cars carId" gorm:"not null"`
	OwnerId   int64     `xml:"xml-web-services-cars ownerId"`
	Active    bool      `xml:"xml-web-services-cars active"`
	PriceListId int64   `xml:"xml-web-services-cars pricelist" gorm:"column:pricelist"`
}

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
