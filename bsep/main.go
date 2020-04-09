package main

import (
	"bsep/handler"
	"bsep/repository"
	"bsep/service"
	"fmt"
	"github.com/labstack/echo/v4"
	echomiddleware "github.com/labstack/echo/v4/middleware"
	"github.com/joho/godotenv"
	"os"
	"strconv"
)

func main() {
	err := godotenv.Load()
	if err != nil{
		panic(err)
	}
	port, err := strconv.Atoi(os.Getenv("DB_PORT"))
	if err != nil{
		panic(err)
	}
	config := repository.PostgresConfig{
		Host:     os.Getenv("DB_HOST"),
		Port:     port,
		User:     os.Getenv("DB_USER"),
		Password: os.Getenv("DB_PASSWORD"),
		Name:     os.Getenv("DB_NAME"),
	}

	store, err := repository.Open(config)
	if err != nil {
		panic(err)
	}
	defer store.Close()
	err = store.AutoMigrate()
	if err != nil {
		panic(err)
	}

	certificateService := &service.CertificateService{CertificateDB: store}
	certificateHandler := handler.NewCertificateHandler(certificateService)
	loginHandler := handler.NewLoginHandler(certificateService)
	e := echo.New()
	e.Use(echomiddleware.Logger())
	fmt.Println("Server started")

	e.GET("/login", loginHandler.Login)
	e.POST("/loging",loginHandler.Loging)
	e.GET("/createnew", certificateHandler.CreateNew)
	e.POST("/create", certificateHandler.Create)
	e.GET("/readAll", certificateHandler.ReadAllInfo)
	e.GET("/home", certificateHandler.Home)
	e.POST("/revoke/:number", certificateHandler.Revoke)
	e.POST("/download/:number", certificateHandler.Download)
	e.Server.Addr = ":8080"
	e.Logger.Fatal(e.Start(":8080"))

}
