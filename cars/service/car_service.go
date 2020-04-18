package service

import (
	"fmt"
	"strconv"
	"strings"
	"xml-web-services/cars/handler/dto"
	"xml-web-services/cars/model"
	"xml-web-services/cars/store/postgres"
)

type CarService struct {
	Store *postgres.Store
}

func NewCarService(store *postgres.Store)*CarService{
	return &CarService{store}
}

func(cs *CarService)FindAll()([]*model.Car,error){
	return cs.Store.FindAllCars()
}

func (cs *CarService) Search(request *dto.SearchDTO) ([]*model.Car, error) {
	//first retrieve all available cars
	fmt.Printf("%v",*request)
	availableCars, err := cs.Store.FindAvailableCars()
	if err != nil{
		return nil,err
	}

	if request.Brand != "" {
		//search by brand
		for i, c := range availableCars {
			if !strings.Contains(strings.ToLower(request.Brand), strings.ToLower(c.Brand)){
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Model != "" {
		//search by model
		for i, c := range availableCars {
			if !strings.Contains(strings.ToLower(request.Model), strings.ToLower(c.CarModel)){
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Fuel != "" {
		//search by fuel
		for i, c := range availableCars {
			if !strings.Contains(strings.ToLower(request.Fuel), strings.ToLower(c.FuelType)){
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Transmission != "" {
		//search by transmission type
		for i, c := range availableCars {
			if !strings.Contains(strings.ToLower(request.Transmission), strings.ToLower(c.Transmission)){
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Class != "" {
		//search by class
		for i, c := range availableCars {
			if !strings.Contains(strings.ToLower(request.Class), strings.ToLower(c.Class)){
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PriceMin != 0 {
		//search by minimum price
		for i, c := range availableCars {
			if request.PriceMin > c.Price{
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PriceMax != 0 {
		//search by maximum price
		for i, c := range availableCars {
			if request.PriceMax < c.Price{
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.TotalMileAge != 0 {
		//search by total mileage
		for i, c := range availableCars {
			if request.TotalMileAge > c.MileAgeInTotal{
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.CollisionDamage != nil {
		//search by collision damage
		for i, c := range availableCars {
			if *request.CollisionDamage != c.CollisionDamageWaiver{
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.SeatsNumber != 0 {
		//search by seats number
		for i, c := range availableCars {
			if request.SeatsNumber != c.NumberOfSeats{
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PlannedMileAge != "" {
		for i, c := range availableCars {
			if c.AllowedMileAge != "UNLIMITED" {
				allowedInt, _ := strconv.Atoi(c.AllowedMileAge)
				wantedInt, _ := strconv.Atoi(request.PlannedMileAge)
				if allowedInt < wantedInt {
					fmt.Printf("Car is allowed : ", c.AllowedMileAge, "  and we want to : ", wantedInt)
					availableCars = append(availableCars[:i], availableCars[i+1:]...)
				}
			}
		}
	}

	return availableCars,nil
}



