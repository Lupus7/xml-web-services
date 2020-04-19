package dto

type SearchResponse struct {
	Advertiser      string
	Brand           string
	Model           string
	Fuel            string
	Transmission    string
	Class           string
	Price           float32
	TotalMileage    int
	AllowedMileage  string
	SeatsNumber     int
	CollisionDamage string
	Rating          float32
	Description     string
}
