package model

type Image struct {
	Id int `gorm:"primary_key"`
	Encoded64Image string
	CarId    uint
}


