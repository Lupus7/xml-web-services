package ipam

import (
	"bytes"
	"encoding/xml"
	"time"
)

type Ad struct {
	Id        int64     `xml:"xml-web-services-cars id"`
	StartDate time.Time `xml:"xml-web-services-cars startDate"`
	EndDate   time.Time `xml:"xml-web-services-cars endDate"`
	Place     string    `xml:"xml-web-services-cars place"`
	CarID     int64     `xml:"xml-web-services-cars carID"`
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

type Car struct {
	Id               int64    `xml:"xml-web-services-cars id"`
	TotalMileage     float64  `xml:"xml-web-services-cars totalMileage"`
	AllowedMileage   float64  `xml:"xml-web-services-cars allowedMileage"`
	ChildrenSeats    int      `xml:"xml-web-services-cars childrenSeats"`
	Description      string   `xml:"xml-web-services-cars description"`
	ColDamProtection bool     `xml:"xml-web-services-cars colDamProtection"`
	Owned            string   `xml:"xml-web-services-cars owned"`
	Brand            string   `xml:"xml-web-services-cars brand"`
	Model            string   `xml:"xml-web-services-cars model"`
	CarClass         string   `xml:"xml-web-services-cars carClass"`
	Fuel             string   `xml:"xml-web-services-cars fuel"`
	Transmission     string   `xml:"xml-web-services-cars transmission"`
	Bookings         []int64  `xml:"xml-web-services-cars bookings,omitempty"`
	Images           []string `xml:"xml-web-services-cars images,omitempty"`
	Ads              []Ad     `xml:"xml-web-services-cars ads,omitempty"`
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
