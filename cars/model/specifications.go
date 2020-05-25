package model


//type Brand struct {
//	ID   int `gorm:"primary_key"`
//	Name string
//	Models []Model `gorm:"foreignkey:"BrandId"`
//}
//
//type Model struct {
//	ID   int `gorm:"primary_key"`
//	Name string
//	BrandId uint
//}
//
//type Fuel struct {
//	ID   int `gorm:"primary_key"`
//	Name string
//}
//
//type Transmission struct {
//	ID   int `gorm:"primary_key"`
//	Name string
//}
//
//type Class struct {
//	ID   int `gorm:"primary_key"`
//	Name string
//}

type Brand struct {
	Id     int64   `xml:"xml-web-service-specs id" gorm:"primary_key"`
	Name   string  `xml:"xml-web-service-specs name"`
}

type Class struct {
	Id   int64  `xml:"xml-web-service-specs id" gorm:"primary_key"`
	Name string `xml:"xml-web-service-specs name"`
}

type Fuel struct {
	Id   int64  `xml:"xml-web-service-specs id" gorm:"primary_key"`
	Name string `xml:"xml-web-service-specs name"`
}

type Model struct {
	Id   int64  `xml:"xml-web-service-specs id" gorm:"primary_key"`
	Name string `xml:"xml-web-service-specs name"`
	BrandId int64 `xml:"xml-web-service-specs brandId"`
}

type Transmission struct {
	Id   int64  `xml:"xml-web-service-specs id" gorm:"primary_key"`
	Name string `xml:"xml-web-service-specs name"`
}