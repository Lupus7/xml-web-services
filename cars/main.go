package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"xml-web-services/cars/store/postgres"

	//"os"
	"time"
	"xml-web-services/cars/handler"
	"xml-web-services/cars/service"
	//"xml-web-services/cars/store/postgres"

	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
	"gopkg.in/tylerb/graceful.v1"

	consulapi "github.com/hashicorp/consul/api"
)

func registerServiceWithConsul() {
	config := consulapi.DefaultConfig()
	config.Address = "localhost:8500"
	consul, err := consulapi.NewClient(config)
	if err != nil {
		log.Fatalln("XXX: => ", err)
	}


	registration := new(consulapi.AgentServiceRegistration)
	registration.ID = "cars"
	registration.Name = "cars"
	address := "localhost"
	registration.Address = address
	port := 8080
	registration.Port = port
	registration.Check = new(consulapi.AgentServiceCheck)
	registration.Check.HTTP = fmt.Sprintf("http://%s:%v/health", address, port)
	registration.Check.Method = "GET"
	registration.Check.Interval = "10s"
	registration.Check.Timeout = "2s"
	log.Println(*registration.Check)
	err = consul.Agent().ServiceRegister(registration)
	if err != nil {
		log.Fatalln("XXX: => ", err)
	}
	fmt.Println(*registration.Check)
}

func main() {

	//err := godotenv.Load()
	//if err != nil {
	//	panic(err)
	//}
	// port := os.Getenv("PORT")
	// dbport, err := strconv.Atoi(os.Getenv("DB_PORT"))
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Printf(os.Getenv("DB_HOST")+" "+os.Getenv("DB_PORT")+" %d", dbport)
	// config := postgres.Config{
	// 	Host:     os.Getenv("DB_HOST"),
	// 	Port:     dbport,
	// 	User:     os.Getenv("DB_USER"),
	// 	Password: os.Getenv("DB_PASSWORD"),
	// 	Name:     "", //os.Getenv("DB_NAME"),
	// }

	fmt.Println("Connected ... ")
	registerServiceWithConsul()
	//Create database
	// fmt.Println(config)
	store, err := postgres.Open(os.Getenv("DB_PATH"))
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
	e.GET("/api/ads/:id", carHandler.GetAdById)

	//   SPECIFICATIONS
	e.GET("/api/brands", carHandler.AllBrands)
	e.GET("/api/models", carHandler.AllModels)
	e.GET("/api/fuels", carHandler.AllFuels)
	e.GET("/api/transmissions", carHandler.AllTransmissions)
	e.GET("/api/classes", carHandler.AllClasses)

	// HEALTH CHECK
	e.GET("/health", health)

	e.Server.Addr = ":8080"
	graceful.DefaultLogger().Println("Application has successfully started at port: ", 8080)
	graceful.ListenAndServe(e.Server, 5*time.Second)

}

func health(c echo.Context) error {
	return c.String(http.StatusOK, "OK")
}
