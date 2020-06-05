package service

import (
	"fmt"
	"strings"
	"time"
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

func (cs *CarService) FindAll() ([]*model.Ad, error) {
	return cs.Store.FindAllAds()
}

func (cs *CarService) FindAdById(id int) (*model.Ad, error) {
	return cs.Store.FindAdById(id)
}

func(cs *CarService)FindAllCars()([]*model.Car,error){
	return cs.Store.FindAllCars()
}

func(cs *CarService)FindImagesByCarId(carId int64)([]*model.Image,error){
	return cs.Store.FindImagesByCarId(carId)
}

func (cs *CarService) Search(request *dto.SearchDTO) ([]*model.Ad, error) {
	fmt.Printf("%v", *request)
	var ads []*model.Ad
	ads, _ = cs.Store.FindAllAds()
	if request.TakeOverDate != "" && request.ReturnDate != "" {
		startDate, err := time.Parse(time.RFC3339, request.TakeOverDate)
		if err != nil {
			fmt.Println(err.Error())
			return nil, err
		}
		endDate, err := time.Parse(time.RFC3339, request.ReturnDate)
		if err != nil {
			fmt.Println(err.Error())
			return nil, err
		}
		fmt.Println("Start date : ", startDate.String(), "  End date: ", endDate.String())
		ads, err = cs.Store.FindAllAdsBetweenDates(startDate, endDate)
		if err != nil {
			return nil, err
		}
		fmt.Println("Returned : ", len(ads), " ads")
	}

	if request.Place != "" {
		for i := len(ads) - 1; i >= 0; i-- {
			if !strings.Contains(strings.ToLower(ads[i].Place), strings.ToLower(request.Place)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.Brand != "" {
		//search by brand
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if !strings.Contains(strings.ToLower(car.Brand), strings.ToLower(request.Brand)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.Model != "" {
		//search by model
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if !strings.Contains(strings.ToLower(car.Model), strings.ToLower(request.Model)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.Fuel != "" {
		//search by fuel
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if !strings.Contains(strings.ToLower(car.Fuel), strings.ToLower(request.Fuel)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.Transmission != "" {
		//search by transmission type
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if !strings.Contains(strings.ToLower(car.Transmission), strings.ToLower(request.Transmission)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.Class != "" {
		//search by class
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if !strings.Contains(strings.ToLower(car.CarClass), strings.ToLower(request.Class)) {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	//TODO: wait to implement price service first
	//if request.PriceMin != 0 {
	//	//search by minimum price
	//	for i := len(ads) - 1; i >= 0; i-- {
	//		if request.PriceMin > ads[i].Car.Price {
	//			ads = append(ads[:i], ads[i+1:]...)
	//		}
	//	}
	//}

	//if request.PriceMax != 0 {
	//	//search by maximum price
	//	for i := len(ads) - 1; i >= 0; i-- {
	//		if request.PriceMax < ads[i].Car.Price {
	//			ads = append(ads[:i], ads[i+1:]...)
	//		}
	//	}
	//}

	if request.TotalMileAge != 0 {
		//search by total mileage
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if request.TotalMileAge < car.TotalMileage {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	//filtratind by CollisinDamage
	for i := len(ads) - 1; i >= 0; i-- {
		car, err := cs.Store.FindCarById(ads[i].CarId)
		if err != nil {
			return nil, err
		}
		if request.CollisionDamage != car.ColDamProtection {
			ads = append(ads[:i], ads[i+1:]...)

		}
	}

	if request.SeatsNumber != 0 {
		//search by seats number
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if request.SeatsNumber != car.ChildrenSeats {
				ads = append(ads[:i], ads[i+1:]...)
			}
		}
	}

	if request.PlannedMileAge != 0 {
		for i := len(ads) - 1; i >= 0; i-- {
			car, err := cs.Store.FindCarById(ads[i].CarId)
			if err != nil {
				return nil, err
			}
			if car.AllowedMileage != 0 {
				allowedInt := car.AllowedMileage
				wantedInt := request.PlannedMileAge
				if allowedInt < wantedInt {
					ads = append(ads[:i], ads[i+1:]...)
				}
			}
		}
	}

	return ads, nil
}

func (cs *CarService) FindAllBrands() ([]*model.Brand, error) {
	return cs.Store.FindAllBrands()
}

func (cs *CarService) FindAllModels() ([]*model.Model, error) {
	return cs.Store.FindAllModels()
}

func (cs *CarService) FindAllFuels() ([]*model.Fuel, error) {
	return cs.Store.FindAllFuels()
}

func (cs *CarService) FindAllTransmissions() ([]*model.Transmission, error) {
	return cs.Store.FindAllTransmissions()
}

func (cs *CarService) FindAllClasses() ([]*model.Class, error) {
	return cs.Store.FindAllClasses()
}
