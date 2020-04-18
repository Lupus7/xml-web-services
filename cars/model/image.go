package model

import (
	"fmt"
	"net/url"
)

type Image struct {
	CarId    uint
	Filename string
}

func (i *Image) Path() string {
	temp := url.URL{
		Path: "/" + i.RelPath(),
	}

	return temp.String()
}

func (i *Image) RelPath() string {
	return fmt.Sprintf("images/cars/%v/%v", i.CarId, i.Filename)
}
