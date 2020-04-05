package handler

import (
	"bsep/service"
	"github.com/labstack/echo/v4"
	"html/template"
	"net/http"
)

type LoginHandler struct {
	certificateService *service.CertificateService
}

func NewLoginHandler(cs *service.CertificateService) *LoginHandler {
	return &LoginHandler{certificateService: cs}
}

func (lg *LoginHandler) Login(c echo.Context) error {
	tpl := template.Must(template.ParseFiles("views/login.gohtml"))
	return tpl.Execute(c.Response().Writer, nil)
}

func (lg *LoginHandler) Loging(c echo.Context) error {
	username := c.FormValue("username")
	password := c.FormValue("password")
	if username == "" || password == "" {
		return echo.NewHTTPError(http.StatusBadRequest, "Username or password can not be empty")
	}
	err := lg.certificateService.CheckUser(username, password)
	if err != nil {
		return echo.NewHTTPError(http.StatusInternalServerError, "Something bad happened...")
	}
	return c.HTML(http.StatusOK, `<body><h3>Successfully logged in...</h3><br><a href="/createnew">Create certificate</a></body>`)
}
