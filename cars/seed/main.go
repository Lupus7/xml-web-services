package main

import (
	"fmt"
	"xml-web-services/cars/model"
	"xml-web-services/cars/store/postgres"
)

var cars = []*model.Car{
	{
		Advertiser:            "Auto kuca",
		Brand:                 "Opel",
		CarModel:              "Astra",
		FuelType:              model.BIODIESEL,
		Transmission:          model.MANUALTRANSMISION,
		Class:                 model.ECONOMICVEHICLE,
		Price:                 1000,
		AllowedMileAge:        0,
		MileAgeInTotal:        150 * 1000,
		CollisionDamageWaiver: false,
		NumberOfSeats:         4,
		Available:             true,
		Rating:                9.5,
		Description:           "Really cool auto, nice looking",
		Images:                nil,
	},
	{
		Advertiser:            "Neko lice",
		Brand:                 "Passat",
		CarModel:              "B6",
		FuelType:              model.BIODIESEL,
		Transmission:          model.MANUALTRANSMISION,
		Class:                 model.ECONOMICVEHICLE,
		Price:                 1500,
		AllowedMileAge:        2000,
		MileAgeInTotal:        180000,
		CollisionDamageWaiver: true,
		NumberOfSeats:         4,
		Available:             true,
		Rating:                9.2,
		Description:           "Nice looking german car model",
		Images:                nil,
	},
	{
		Advertiser:            "Auto plac",
		Brand:                 "BMW",
		CarModel:              "X3",
		FuelType:              model.FUELDIESEL,
		Transmission:          "automatic",
		Class:                 model.SUV,
		Price:                 4000,
		AllowedMileAge:        5000,
		MileAgeInTotal:        120 * 1000,
		CollisionDamageWaiver: true,
		NumberOfSeats:         4,
		Available:             true,
		Rating:                8,
		Description:           "The BMW X3 is a compact luxury crossover SUV manufactured by German automaker BMW",
	},
	{
		Advertiser:            "username",
		Brand:                 "Fiat",
		CarModel:              "Stilo",
		FuelType:              model.FUELGASOLINE,
		Transmission:          model.MANUALTRANSMISION,
		Class:                 model.ECONOMICVEHICLE,
		Price:                 100,
		AllowedMileAge:        0,
		MileAgeInTotal:        200 * 1000,
		CollisionDamageWaiver: false,
		NumberOfSeats:         4,
		Available:             true,
		Rating:                7.4,
		Description:           " Well built underrated car, very spacious in the Multiwagon form, Multijet engine powerful yet fairly economical",
		Images:                nil,
	},
}

func main() {
	config := postgres.Config{
		Host:     "localhost",
		Port:     5432,
		User:     "postgres",
		Password: "postgres",
		Name:     "xws",
	}
	fmt.Println("Connected ... ")
	store, err := postgres.Open(config)
	if err != nil {
		panic(err)
	}
	err = store.AutoMigrate()
	if err != nil {
		panic(err)
	}
	defer store.Close()

	for _, car := range cars {
		err = store.CreateCar(car)
		if err != nil {
			panic(err)
		}
	}

}
