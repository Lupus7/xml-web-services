package dto

import "time"

type SearchDTO struct {
	Place           string     `json:"place"`
	TakeOverDate    *time.Time `json:"take_over"`
	ReturnDate      *time.Time `json:"return"`
	Brand           string     `json:"brand"`
	Model           string     `json:"model"`
	Fuel            string     `json:"fuel"`
	Transmission    string     `json:"transmission"`
	Class           string     `json:"class"`
	PriceMin        float32    `json:"price_min"`
	PriceMax        float32    `json:"price_max"`
	TotalMileAge    float32    `json:"total_mileage"`
	PlannedMileAge  string     `json:"planned_mileage"`
	CollisionDamage *bool      `json:"collision_damage"`
	SeatsNumber     int        `json:"seats_number"`
}
