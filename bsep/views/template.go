package views

import (
	"github.com/labstack/echo/v4"
	"html/template"
	"io"
)

type TemplateRendered struct{
	Templates *template.Template
}

func(t *TemplateRendered) Render(w io.Writer,name string,data interface{},c echo.Context)error{
	if viewContext, isMap := data.(map[string]interface{}); isMap {
		viewContext["reverse"] = c.Echo().Reverse
	}

	return t.Templates.ExecuteTemplate(w, name, data)
}
