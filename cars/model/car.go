package model

import (
	"bytes"
	"encoding/xml"
	"time"
)

//type Car struct {
//	ID                    int          `gorm:"primary_key"`
//	Advertiser            string
//	Brand                 Brand        `gorm:"foreignkey:BrandId"`
//	BrandId               int          `gorm:"not null"`
//	Model                 Model        `gorm:"foreignkey:ModelId"`
//	ModelId               int          `gorm:"not null"`
//	Fuel                  Fuel         `gorm:"foreignkey:FuelId"`
//	FuelId                int          `gorm:"not null"`
//	Transmission          Transmission `gorm:"foreignkey:TransmissionId"`
//	TransmissionId        int          `gorm:"not null"`
//	Class                 Class        `gorm:"foreignkey:ClassId"`
//	ClassId               int          `gorm:"not null"`
//	Price                 float32
//	AllowedMileAge        float32 //if its 0 that means its unlimited
//	MileAgeInTotal        float32
//	CollisionDamageWaiver bool
//	NumberOfSeats         int
//	Rating                float32
//	Description           string
//	Images                []Image      `gorm:"foreignkey:"CarId"`
//}

type Car struct {
	Id               int64   `xml:"xml-web-services-cars id" gorm:"primary_key"`
	TotalMileage     float64 `xml:"xml-web-services-cars totalMileage"`
	AllowedMileage   float64 `xml:"xml-web-services-cars allowedMileage"`
	ChildrenSeats    int     `xml:"xml-web-services-cars childrenSeats"`
	Description      string  `xml:"xml-web-services-cars description"`
	ColDamProtection bool    `xml:"xml-web-services-cars colDamProtection"`
	Owner            string  `xml:"xml-web-services-cars owner"`
	Brand            string  `xml:"xml-web-services-cars brand"`
	Model            string  `xml:"xml-web-services-cars model"`
	CarClass         string  `xml:"xml-web-services-cars carClass"`
	Fuel             string  `xml:"xml-web-services-cars fuel"`
	Transmission     string  `xml:"xml-web-services-cars transmission"`
	//Bookings         []int64  `xml:"xml-web-services-cars bookings,omitempty" gorm:"-"`
	//Images           []string `xml:"xml-web-services-cars images,omitempty" gorm:"-"`
	//Ads              []Ad     `xml:"xml-web-services-cars ads,omitempty" gorm:"-"`
}

type xsdDate time.Time

func (t *xsdDate) UnmarshalText(text []byte) error {
	return _unmarshalTime(text, (*time.Time)(t), "2006-01-02")
}
func (t xsdDate) MarshalText() ([]byte, error) {
	return []byte((time.Time)(t).Format("2006-01-02")), nil
}
func (t xsdDate) MarshalXML(e *xml.Encoder, start xml.StartElement) error {
	if (time.Time)(t).IsZero() {
		return nil
	}
	m, err := t.MarshalText()
	if err != nil {
		return err
	}
	return e.EncodeElement(m, start)
}
func (t xsdDate) MarshalXMLAttr(name xml.Name) (xml.Attr, error) {
	if (time.Time)(t).IsZero() {
		return xml.Attr{}, nil
	}
	m, err := t.MarshalText()
	return xml.Attr{Name: name, Value: string(m)}, err
}
func _unmarshalTime(text []byte, t *time.Time, format string) (err error) {
	s := string(bytes.TrimSpace(text))
	*t, err = time.Parse(format, s)
	if _, ok := err.(*time.ParseError); ok {
		*t, err = time.Parse(format+"Z07:00", s)
	}
	return err
}
