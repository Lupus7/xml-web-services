package handler

import (
	"github.com/labstack/echo"
	"net/http"
	"xml-web-services/cars/handler/dto"
	"xml-web-services/cars/model"
	"xml-web-services/cars/service"
)

type CarHandler struct {
	CarService *service.CarService
}

func NewCarHandler(cs *service.CarService) *CarHandler {
	return &CarHandler{cs}
}

func (ch *CarHandler) FindAll(c echo.Context) error {
	cars, err := ch.CarService.FindAll()
	if err != nil {
		return err
	}
	carsDto := []*dto.SearchResponse{}
	for _, car := range cars {
		carsDto = append(carsDto, toResponse(car))
	}

	return c.JSON(http.StatusOK, carsDto)
}

func(ch *CarHandler)SearchCars(c echo.Context)error {
	dtoRequest := &dto.SearchDTO{}
	if err := c.Bind(dtoRequest); err != nil {
		return err
	}

	filtrated, err := ch.CarService.Search(dtoRequest)
	if err != nil {
		return err
	}
	carsResponse := []*dto.SearchResponse{}
	for _, car := range filtrated {
		carsResponse = append(carsResponse, toResponse(car))
	}
	return c.JSON(http.StatusOK, carsResponse)
}

func toResponse(car *model.Car)*dto.SearchResponse {
	collisionDamage := "not allowed"
	if car.CollisionDamageWaiver {
		collisionDamage = "allowed"
	}
	return &dto.SearchResponse{
		Advertiser:      car.Advertiser,
		Brand:           car.Brand,
		Model:           car.CarModel,
		Fuel:            car.FuelType,
		Transmission:    car.Transmission,
		Class:           car.Class,
		Price:           car.Price,
		AllowedMileage:  car.AllowedMileAge,
		TotalMileage:    car.MileAgeInTotal,
		SeatsNumber:     car.NumberOfSeats,
		CollisionDamage: collisionDamage,
		Rating:          car.Rating,
		Description:     car.Description,
	}
}
