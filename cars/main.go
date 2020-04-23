package main

import (
	"fmt"
	"github.com/joho/godotenv"
	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
	"gopkg.in/tylerb/graceful.v1"
	"net/http"
	"os"
	"strconv"
	"time"
	"xml-web-services/cars/handler"
	"xml-web-services/cars/service"
	"xml-web-services/cars/store/postgres"
)

func main() {
	err := godotenv.Load()
	if err != nil {
		panic(err)
	}
	port := os.Getenv("PORT")
	dbport, err := strconv.Atoi(os.Getenv("DB_PORT"))
	if err != nil {
		panic(err)
	}
	config := postgres.Config{
		Host:     os.Getenv("DB_HOST"),
		Port:     dbport,
		User:     os.Getenv("DB_USER"),
		Password: os.Getenv("DB_PASSWORD"),
		Name:     os.Getenv("DB_NAME"),
	}
	fmt.Println("Connected ... ")
	//Create database
	store, err := postgres.Open(config)
	if err != nil {
		panic(err)
	}
	err = store.AutoMigrate()
	if err != nil {
		panic(err)
	}
	defer store.Close()
	//create service
	carService := service.NewCarService(store)
	//handlers - controllers
	carHandler := handler.NewCarHandler(carService)

	e := echo.New()
	e.Use(middleware.CORS())

	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowHeaders: []string{echo.HeaderOrigin, echo.HeaderContentType, echo.HeaderAccept},
		AllowMethods: []string{http.MethodGet, http.MethodPut, http.MethodPost, http.MethodDelete},
	}))

	//   ADS
	e.GET("/api/ads", carHandler.FindAll)
	e.POST("/api/ads/", carHandler.SearchAds)

	//   SPECIFICATIONS
	e.GET("/api/brands", carHandler.AllBrands)
	e.GET("/api/models",carHandler.AllModels)
	e.GET("/api/fuels",carHandler.AllFuels)
	e.GET("/api/transmissions", carHandler.AllTransmissions)
	e.GET("/api/classes", carHandler.AllClasses)

	e.Server.Addr = ":" + port
	graceful.DefaultLogger().Println("Application has successfully started at port: ", 8080)
	graceful.ListenAndServe(e.Server, 5*time.Second)
}
