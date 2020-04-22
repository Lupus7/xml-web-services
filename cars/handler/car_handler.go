package handler

import (
	"fmt"
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
	ads, err := ch.CarService.FindAll()
	if err != nil {
		return err
	}
	carsDto := []*dto.SearchResponse{}
	for _, ad := range ads {
		carsDto = append(carsDto, toResponse(ad))
	}

	return c.JSON(http.StatusOK, carsDto)
}

func (ch *CarHandler) SearchCars(c echo.Context) error {
	dtoRequest := &dto.SearchDTO{}
	if err := c.Bind(&dtoRequest); err != nil {
		fmt.Println(err.Error())
		return err
	}
	filtrated, err := ch.CarService.Search(dtoRequest)
	if err != nil {
		return err
	}
	carsResponse := []*dto.SearchResponse{}
	for _, ad := range filtrated {
		carsResponse = append(carsResponse, toResponse(ad))
	}
	return c.JSON(http.StatusOK, carsResponse)
}

func toResponse(ad *model.Ad) *dto.SearchResponse {
	collisionDamage := "not allowed"
	if ad.Car.CollisionDamageWaiver {
		collisionDamage = "allowed"
	}
	allowedMileage := "UNLIMITED"
	if ad.Car.AllowedMileAge == 0 {
		allowedMileage = fmt.Sprintf("%f", ad.Car.AllowedMileAge)
	}
	images := []string{}
	if len(ad.Car.Images) != 0 {
		for _, image := range ad.Car.Images {
			images = append(images,image.Encoded64Image)
		}
	}

	return &dto.SearchResponse{
		Advertiser:      ad.Car.Advertiser,
		Brand:           ad.Car.Brand.Name,
		Model:           ad.Car.Model.Name,
		Fuel:            ad.Car.Fuel.Name,
		Transmission:    ad.Car.Transmission.Name,
		Class:           ad.Car.Class.Name,
		Price:           ad.Car.Price,
		AllowedMileage:  allowedMileage,
		TotalMileage:    ad.Car.MileAgeInTotal,
		SeatsNumber:     ad.Car.NumberOfSeats,
		CollisionDamage: collisionDamage,
		Rating:          ad.Car.Rating,
		Description:     ad.Car.Description,
		Images:          images,
	}
}
