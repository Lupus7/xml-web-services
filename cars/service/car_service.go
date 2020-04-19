package service

import (
	"fmt"
	"strings"
	"xml-web-services/cars/handler/dto"
	"xml-web-services/cars/model"
	"xml-web-services/cars/store/postgres"
)

type CarService struct {
	Store *postgres.Store
}

func NewCarService(store *postgres.Store) *CarService {
	return &CarService{store}
}

func (cs *CarService) FindAll() ([]*model.Car, error) {
	return cs.Store.FindAllCars()
}

func (cs *CarService) Search(request *dto.SearchDTO) ([]*model.Car, error) {
	//first retrieve all available cars
	fmt.Printf("%v", *request)
	availableCars, err := cs.Store.FindAvailableCars()
	if err != nil {
		return nil, err
	}

	if request.Brand != "" {
		//search by brand
		for i := len(availableCars) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(request.Brand), strings.ToLower(availableCars[i].Brand)) {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Model != "" {
		//search by model
		for i := len(availableCars) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(request.Model), strings.ToLower(availableCars[i].CarModel)) {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Fuel != "" {
		//search by fuel
		for i := len(availableCars) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(request.Fuel), strings.ToLower(availableCars[i].FuelType)) {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Transmission != "" {
		//search by transmission type
		for i := len(availableCars) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(request.Transmission), strings.ToLower(availableCars[i].Transmission)) {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.Class != "" {
		//search by class
		for i := len(availableCars) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(request.Class), strings.ToLower(availableCars[i].Class)) {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PriceMin != 0 {
		//search by minimum price
		for i := len(availableCars) - 1; i >= 0; i-- {
			if request.PriceMin > availableCars[i].Price {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PriceMax != 0 {
		//search by maximum price
		for i := len(availableCars) - 1; i >= 0; i-- {
			if request.PriceMax < availableCars[i].Price {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.TotalMileAge != 0 {
		//search by total mileage
		for i := len(availableCars) - 1; i >= 0; i-- {
			if request.TotalMileAge < availableCars[i].MileAgeInTotal {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	//filtratind by CollisinDamage
	for i := len(availableCars) - 1; i >= 0; i-- {
		if request.CollisionDamage != availableCars[i].CollisionDamageWaiver {
			availableCars = append(availableCars[:i], availableCars[i+1:]...)

		}
	}

	if request.SeatsNumber != 0 {
		//search by seats number
		for i := len(availableCars) - 1; i >= 0; i-- {
			if request.SeatsNumber != availableCars[i].NumberOfSeats {
				availableCars = append(availableCars[:i], availableCars[i+1:]...)
			}
		}
	}

	if request.PlannedMileAge != 0 {
		for i := len(availableCars) - 1; i >= 0; i-- {
			if availableCars[i].AllowedMileAge != 0 {
				allowedInt := availableCars[i].AllowedMileAge
				wantedInt := request.PlannedMileAge
				if allowedInt < wantedInt {
					availableCars = append(availableCars[:i], availableCars[i+1:]...)
				}
			}
		}
	}

	return availableCars, nil
}
