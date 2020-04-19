package dto

type SearchDTO struct {
	Place           string  `json:"place"`
	TakeOverDate    string  `json:"take_over"`
	ReturnDate      string  `json:"return"`
	Brand           string  `json:"brand"`
	Model           string  `json:"model"`
	Fuel            string  `json:"fuel"`
	Transmission    string  `json:"transmission"`
	Class           string  `json:"class"`
	PriceMin        float32 `json:"price_min"`
	PriceMax        float32 `json:"price_max"`
	TotalMileAge    int     `json:"total_mileage"`
	PlannedMileAge  int     `json:"planned_mileage"`
	CollisionDamage bool    `json:"collision_damage"`
	SeatsNumber     int     `json:"seats_number"`
}
