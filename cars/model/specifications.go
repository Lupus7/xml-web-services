package model

type Brand struct {
	ID   int `gorm:"primary_key"`
	Name string
}

type Model struct {
	ID   int `gorm:"primary_key"`
	Name string
}

type Fuel struct {
	ID   int `gorm:"primary_key"`
	Name string
}

type Transmission struct {
	ID   int `gorm:"primary_key"`
	Name string
}

type Class struct {
	ID   int `gorm:"primary_key"`
	Name string
}
