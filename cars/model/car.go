package model

import (
	"github.com/jinzhu/gorm"
)

const (
	FUELDIESEL    = "DIESEL"
	FUELGASOLINE  = "GASOLINE"
	FUELPETROLEUM = "PETROLEUM"
	NATURALGAS    = "NATURALGAS"
	BIODIESEL     = "BIO-DIESEL"
	ETHANOL       = "ETHANOL"

	MANUALTRANSMISION     = "MANUAL"
	AUTOMATICTRANSMISSION = "AUTOMATIC"

	ECONOMICVEHICLE = "ECONOMIC"
	LUXUREVEHICLE   = "LUXURY"
	SPORTS          = "SPORTS"
	SUV             = "SUV"
	OLD_TIMER       = "OLD TIMER"
)

type Car struct {
	gorm.Model
	Advertiser            string //probably to be username or email
	Brand                 string
	CarModel              string
	FuelType              string
	Transmission          string
	Class                 string
	Price                 float32
	AllowedMileAge        int //if its 0 that means its unlimited
	MileAgeInTotal        int
	CollisionDamageWaiver bool
	NumberOfSeats         int
	Available             bool
	Rating                float32
	Description           string
	Images                []Image `gorm:"-"`
}
