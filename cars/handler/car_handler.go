package handler

import (
	"fmt"
	"os"

	"io/ioutil"
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
		car, err := ch.CarService.Store.FindCarById(ad.CarId)
		if err != nil {
			return err
		}
		images, err := ch.CarService.FindImagesByCarId(car.Id)
		carsDto = append(carsDto, toResponse(ad, car, images, 0))
	}

	return c.JSON(http.StatusOK, carsDto)
}

func (ch *CarHandler) SearchAds(c echo.Context) error {
	api := os.Getenv("API_GATEWAY")
	client := &http.Client{}

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
		car, err := ch.CarService.Store.FindCarById(ad.CarId)
		if err != nil {
			return err
		}
		images, err := ch.CarService.FindImagesByCarId(car.Id)

		url := fmt.Sprintf("http://%s:8080/community/rate/%v",api,car.Id)
		fmt.Println(url)

		resp, err := client.Get(url)
		if err != nil{
			fmt.Errorf(err.Error())
			return err
		}

		fmt.Println("RESPONSE CODE: ", resp.StatusCode)
		respString, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			fmt.Println(err.Error())
			return err
		}
		fmt.Println("ODGOVOR: ", string(respString))

		rateFloat, err := strconv.ParseFloat(string(respString), 32)
		if err != nil {
			return err
		}
		carsResponse = append(carsResponse, toResponse(ad, car, images, float32(rateFloat)))

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
	car, err := ch.CarService.Store.FindCarById(ad.CarId)
	if err != nil {
		return err
	}
	images, err := ch.CarService.FindImagesByCarId(car.Id)
	adResponse := toResponse(ad, car, images, 0)
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

func (ch *CarHandler) FindAllCars(c echo.Context) error {
	cars, err := ch.CarService.FindAllCars()
	if err != nil {
		return err
	}
	return c.JSON(http.StatusOK, cars)
}

//func lookupServiceWithConsul(client *consulapi.Client) (string, error) {
//	services, err := client.Agent().Services()
//	if err != nil {
//		fmt.Println(err.Error())
//
//		return "", err
//	}
//	fmt.Printf("%v", services)
//	srvc, ok := services["community-8080"]
//	if !ok{
//		fmt.Println("NE POSTOJI TAJ SERVIS")
//	}
//	address := srvc.Address
//	port := srvc.Port
//	return fmt.Sprintf("http://%s:%v", address, port), nil
//}

func toResponse(ad *model.Ad, car *model.Car, images []*model.Image, rating float32) *dto.SearchResponse {
	collisionDamage := "not allowed"
	if car.ColDamProtection {
		collisionDamage = "allowed"
	}
	allowedMileage := "UNLIMITED"
	if car.AllowedMileage == 0 {
		allowedMileage = fmt.Sprintf("%f", car.AllowedMileage)
	}
	carImages := []string{}
	if len(images) != 0 {
		for _, im := range images {
			carImages = append(carImages, im.Encoded64Image)
		}
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
		Rating:          rating,
		Description:     car.Description,
		Images:          carImages,
		Place:           ad.Place,
		StartDate:       strings.Split(ad.StartDate.String(), " ")[0],
		EndDate:         strings.Split(ad.EndDate.String(), " ")[0],
	}
}
