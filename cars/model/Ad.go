package model

import "time"

type Ad struct {
	Id        int `gorm:"primary_key"`
	Car       Car `gorm:"foreignkey:CarId"`
	CarId     int `gorm:"not null"'`
	StartDate time.Time
	EndDate   time.Time
	Place     string `gorm:"not_null"`
}
