package main

import (
	"flag"
	"fmt"
	"github.com/joho/godotenv"
	//"github.com/labstack/echo/middleware"
	"log"
	"net/http"
	"os"
	"strconv"
	"xml-web-services/cars/store/postgres"

	//"os"
	"time"
	"xml-web-services/cars/handler"
	"xml-web-services/cars/service"

	//"xml-web-services/cars/store/postgres"

	"github.com/labstack/echo"
	"gopkg.in/tylerb/graceful.v1"

	consulapi "github.com/hashicorp/consul/api"
)

func main() {
	var develop = flag.Bool("dev",false, "Is this develop version?")
	flag.Parse()

	var store *postgres.Store
	var err error
	var port string

	if *develop{
		err := godotenv.Load()
		if err != nil {
			panic(err)
		}
		port = os.Getenv("PORT")
		dbport, err := strconv.Atoi(os.Getenv("DB_PORT"))
		if err != nil {
			panic(err)
		}
		fmt.Printf(os.Getenv("DB_HOST")+" "+os.Getenv("DB_PORT")+" %d", dbport)
		config := postgres.Config{
			Host:     os.Getenv("DB_HOST"),
			Port:     dbport,
			User:     os.Getenv("DB_USER"),
			Password: os.Getenv("DB_PASSWORD"),
			Name:     os.Getenv("DB_NAME"),
		}
		store, err = postgres.OpenWithUrl(config)
		if err != nil {
			panic(err)
		}
	}

	if *develop==false{
		registerServiceWithConsul()
		port = os.Getenv("PORT")
		store, err = postgres.Open(os.Getenv("DB_PATH"))
		if err != nil {
			panic(err)
		}

	}
	fmt.Println("Connected ... ")

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
	/*e.Use(middleware.CORS())

	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowHeaders: []string{echo.HeaderOrigin, echo.HeaderContentType, echo.HeaderAccept},
		AllowMethods: []string{http.MethodGet, http.MethodPut, http.MethodPost, http.MethodDelete},
	}))*/

	//   ADS
	e.GET("cars/api/ads", carHandler.FindAll)
	e.POST("cars/api/ads/", carHandler.SearchAds)
	e.GET("cars/api/ads/:id", carHandler.GetAdById)

	//   SPECIFICATIONS
	e.GET("cars/api/brands", carHandler.AllBrands)
	e.GET("cars/api/models", carHandler.AllModels)
	e.GET("cars/api/fuels", carHandler.AllFuels)
	e.GET("cars/api/transmissions", carHandler.AllTransmissions)
	e.GET("cars/api/classes", carHandler.AllClasses)

	// HEALTH CHECK
	e.GET("/health", health)

	e.Server.Addr = ":"+port
	portInt, err := strconv.Atoi(port)
	if err != nil{
		panic(err)
	}
	graceful.DefaultLogger().Println("Application has successfully started at port: ", portInt)
	graceful.ListenAndServe(e.Server, 5*time.Second)

}

func health(c echo.Context) error {
	return c.String(http.StatusOK, "OK")
}

func registerServiceWithConsul() {
	config := consulapi.DefaultConfig()
	config.Address = os.Getenv("CONSUL_ADDR")
	consul, err := consulapi.NewClient(config)
	if err != nil {
		log.Fatalln("XXY: => ", err)
	}

	registration := new(consulapi.AgentServiceRegistration)
	registration.ID = "cars"
	registration.Name = "cars"
	address := os.Getenv("MY_ADDR")
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
