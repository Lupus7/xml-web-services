package main

import (
	"bsep/handler"
	"bsep/repository"
	"bsep/service"
	"fmt"
	"github.com/labstack/echo/v4"
	echomiddleware "github.com/labstack/echo/v4/middleware"
)

func main() {
	config := repository.PostgresConfig{
		Host:     "localhost",
		Port:     5432,
		User:     "bojan",
		Password: "bojan",
		Name:     "bsep",
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
