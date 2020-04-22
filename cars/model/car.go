package model

type Car struct {
	ID                    int          `gorm:"primary_key"`
	Advertiser            string
	Brand                 Brand        `gorm:"foreignkey:BrandId"`
	BrandId               int          `gorm:"not null"`
	Model                 Model        `gorm:"foreignkey:ModelId"`
	ModelId               int          `gorm:"not null"`
	Fuel                  Fuel         `gorm:"foreignkey:FuelId"`
	FuelId                int          `gorm:"not null"`
	Transmission          Transmission `gorm:"foreignkey:TransmissionId"`
	TransmissionId        int          `gorm:"not null"`
	Class                 Class        `gorm:"foreignkey:ClassId"`
	ClassId               int          `gorm:"not null"`
	Price                 float32
	AllowedMileAge        float32 //if its 0 that means its unlimited
	MileAgeInTotal        float32
	CollisionDamageWaiver bool
	NumberOfSeats         int
	Rating                float32
	Description           string
	Images                []Image      `gorm:"foreignkey:"CarId"`
}
