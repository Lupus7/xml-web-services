package dto

type SearchResponse struct {
	Advertiser      string
	Brand           string
	Model           string
	Fuel            string
	Transmission    string
	Class           string
	Price           float32
	TotalMileage    float32
	AllowedMileage  string
	SeatsNumber     int
	CollisionDamage string
	Rating          float32
	Description     string
	Images          []string
}
