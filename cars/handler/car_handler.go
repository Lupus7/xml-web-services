package handler

import (
	"fmt"
	"net/http"
	"strconv"
	"strings"
	"xml-web-services/cars/handler/dto"
	"xml-web-services/cars/model"
	"xml-web-services/cars/service"

	"github.com/labstack/echo"
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
		car, err := ch.CarService.Store.FindCarById(ad.CarID)
		if err != nil {
			return err
		}
		carsDto = append(carsDto, toResponse(ad, car))
	}

	return c.JSON(http.StatusOK, carsDto)
}

func (ch *CarHandler) SearchAds(c echo.Context) error {
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
		car, err := ch.CarService.Store.FindCarById(ad.CarID)
		if err != nil {
			return err
		}
		carsResponse = append(carsResponse, toResponse(ad, car))
	}
	return c.JSON(http.StatusOK, carsResponse)
}

func (ch *CarHandler) GetAdById(c echo.Context) error {
	path := strings.Split(c.Request().URL.Path, "/")
	id, err := strconv.Atoi(path[3])
	if err != nil {
		return err
	}
	ad, err := ch.CarService.FindAdById(id)
	if err != nil {
		return err
	}
	car, err := ch.CarService.Store.FindCarById(ad.CarID)
	if err != nil {
		return err
	}
	adResponse := toResponse(ad, car)
	return c.JSON(http.StatusOK, adResponse)
}

func (ch *CarHandler) AllBrands(c echo.Context) error {
	type Response struct {
		Name   string
		Models []string
	}
	brands, err := ch.CarService.FindAllBrands()
	if err != nil {
		return err
	}
	responses := []Response{}
	for _, brand := range brands {
		models := []string{}
		mds := ch.CarService.Store.FindModelsByBrandID(brand.Id)
		for _, model := range mds {
			models = append(models, model.Name)
		}

		response := Response{
			Name:   brand.Name,
			Models: models,
		}
		responses = append(responses, response)
	}
	return c.JSON(http.StatusOK, responses)
}

func (ch *CarHandler) AllModels(c echo.Context) error {
	models, err := ch.CarService.FindAllModels()
	if err != nil {
		return err
	}
	modelNames := []string{}
	for _, model := range models {
		modelNames = append(modelNames, model.Name)
	}
	return c.JSON(http.StatusOK, modelNames)
}

func (ch *CarHandler) AllFuels(c echo.Context) error {
	fuels, err := ch.CarService.FindAllFuels()
	if err != nil {
		return err
	}
	fuelNames := []string{}
	for _, brand := range fuels {
		fuelNames = append(fuelNames, brand.Name)
	}
	return c.JSON(http.StatusOK, fuelNames)
}

func (ch *CarHandler) AllTransmissions(c echo.Context) error {
	trs, err := ch.CarService.FindAllTransmissions()
	if err != nil {
		return err
	}
	trNames := []string{}
	for _, tr := range trs {
		trNames = append(trNames, tr.Name)
	}
	return c.JSON(http.StatusOK, trNames)
}

func (ch *CarHandler) AllClasses(c echo.Context) error {
	classes, err := ch.CarService.FindAllClasses()
	if err != nil {
		return err
	}
	classNames := []string{}
	for _, class := range classes {
		classNames = append(classNames, class.Name)
	}
	return c.JSON(http.StatusOK, classNames)
}

func toResponse(ad *model.Ad, car *model.Car) *dto.SearchResponse {
	collisionDamage := "not allowed"
	if car.ColDamProtection {
		collisionDamage = "allowed"
	}
	allowedMileage := "UNLIMITED"
	if car.AllowedMileage == 0 {
		allowedMileage = fmt.Sprintf("%f", car.AllowedMileage)
	}
	//images := []string{}
	//if len(car.) != 0 {
	//	for _, image := range ad.Car.Images {
	//		images = append(images, image.Encoded64Image)
	//	}
	//}

	return &dto.SearchResponse{
		Id:           int(ad.Id),
		Advertiser:   car.Owner,
		Brand:        car.Brand,
		Model:        car.Model,
		Fuel:         car.Fuel,
		Transmission: car.Transmission,
		Class:        car.CarClass,
		//Price:           ad.Car.Price,
		AllowedMileage:  allowedMileage,
		TotalMileage:    float32(car.TotalMileage),
		SeatsNumber:     car.ChildrenSeats,
		CollisionDamage: collisionDamage,
		//Rating:          ad.Car.Rating,
		Description: car.Description,
		//Images:          images,
		Place:     ad.Place,
		StartDate: strings.Split(ad.StartDate.String(), " ")[0],
		EndDate:   strings.Split(ad.EndDate.String(), " ")[0],
	}
}
