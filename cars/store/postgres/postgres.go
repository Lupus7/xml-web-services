package postgres

import (
	"fmt"
	"time"
	"xml-web-services/cars/model"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type Config struct {
	Host     string
	Port     int
	User     string
	Password string
	Name     string
}

type Store struct {
	db *gorm.DB
}

func NewStore(db *gorm.DB) *Store {
	return &Store{db: db}
}

func Open(config string) (*Store, error) {
	// psqlInfo := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable",
	// 	config.Host, config.Port, config.User, config.Password, config.Name)

	db, err := gorm.Open("postgres", config+"?sslmode=disable")
	if err != nil {
		return nil, err
	}

	store := NewStore(db)
	return store, nil
}

func OpenWithUrl(config Config) (*Store, error) {
	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable",
		config.Host, config.Port, config.User, config.Password, config.Name)

	db, err := gorm.Open("postgres", psqlInfo)
	if err != nil {
		return nil, err
	}

	store := NewStore(db)
	return store, nil
}

func (store *Store) AutoMigrate() error {
	store.db.SingularTable(true)
	return store.db.AutoMigrate(&model.Image{}, &model.Ad{}, &model.Brand{}, &model.Model{}, &model.Class{}, &model.Transmission{}, &model.Fuel{}, &model.Car{}).Error
}

func (store *Store) CheckStoreConnection() error {
	return store.db.DB().Ping()
}

func (store *Store) Close() error {
	return store.db.Close()
}

func (store *Store) CreateCar(car *model.Car) error {
	return store.db.Create(&car).Error
}

func (store *Store) FindCarsByAdvertiser(adv string) ([]*model.Car, error) {
	cars := []*model.Car{}
	if err := store.db.Set("gorm:auto_preload", true).Where("advertiser = ?", adv).Find(&cars).Error; err != nil {
		return nil, err
	}
	return cars, nil
}

func (store *Store) FindAllCars() ([]*model.Car, error) {
	cars := []*model.Car{}
	if err := store.db.Set("gorm:auto_preload", true).Find(&cars).Error; err != nil {
		return nil, err
	}
	return cars, nil
}

func (s *Store) DB() *gorm.DB {
	return s.db
}

func (s *Store) FindImagesByCarId(id int64) ([]*model.Image, error) {
	images := []*model.Image{}
	err := s.db.Find(&images, "car_id = ?", id).Error
	if err != nil {
		return nil, err
	}
	return images, nil
}

func (store *Store) CreateBrand(brand *model.Brand) error {
	return store.db.Create(&brand).Error
}

func (store *Store) FindBrandByID(id int) (*model.Brand, error) {
	brand := &model.Brand{}
	err := store.db.Where("id = ?", id).Find(&brand).Error
	if err != nil {
		return nil, err
	}
	return brand, nil
}

func (store *Store) FindModelByID(id int) (*model.Model, error) {
	model := &model.Model{}
	err := store.db.Where("id = ?", id).Find(&model).Error
	if err != nil {
		return nil, err
	}
	return model, nil
}

func (store *Store) FindFuelByID(id int) (*model.Fuel, error) {
	fuel := &model.Fuel{}
	err := store.db.Where("id = ?", id).Find(&fuel).Error
	if err != nil {
		return nil, err
	}
	return fuel, nil
}

func (store *Store) FindAllAds() ([]*model.Ad, error) {
	ads := []*model.Ad{}
	if err := store.db.Set("gorm:auto_preload", true).Find(&ads).Error; err != nil {
		return nil, err
	}
	return ads, nil
}

func (store *Store) FindAllActiveAds() ([]*model.Ad, error) {
	ads := []*model.Ad{}
	if err := store.db.Set("gorm:auto_preload", true).Where("active = true").Find(&ads).Error; err != nil {
		return nil, err
	}
	return ads, nil
}

func (store *Store) FindAllAdsBetweenDates(start time.Time, end time.Time) ([]*model.Ad, error) {
	ads := []*model.Ad{}
	if err := store.db.Set("gorm:auto_preload", true).Where("start_date <= ? and end_date >= ? and active = true", start, end).Find(&ads).Error; err != nil {
		return nil, err
	}
	return ads, nil
}

func (store *Store) FindAllBrands() ([]*model.Brand, error) {
	brands := []*model.Brand{}
	if err := store.db.Set("gorm:auto_preload", true).Find(&brands).Error; err != nil {
		return nil, err
	}
	return brands, nil
}

func (store *Store) FindAllFuels() ([]*model.Fuel, error) {
	fuels := []*model.Fuel{}
	if err := store.db.Find(&fuels).Error; err != nil {
		return nil, err
	}
	return fuels, nil
}

func (store *Store) FindAllModels() ([]*model.Model, error) {
	models := []*model.Model{}
	if err := store.db.Find(&models).Error; err != nil {
		return nil, err
	}
	return models, nil
}

func (store *Store) FindAllTransmissions() ([]*model.Transmission, error) {
	trans := []*model.Transmission{}
	if err := store.db.Find(&trans).Error; err != nil {
		return nil, err
	}
	return trans, nil
}

func (store *Store) FindAllClasses() ([]*model.Class, error) {
	classes := []*model.Class{}
	if err := store.db.Find(&classes).Error; err != nil {
		return nil, err
	}
	return classes, nil
}

func (store *Store) FindAdById(id int) (*model.Ad, error) {
	ad := &model.Ad{}
	if err := store.db.Set("gorm:auto_preload", true).Find(&ad, id).Error; err != nil {
		return nil, err
	}
	return ad, nil
}

func (store *Store) FindCarById(id int64) (*model.Car, error) {
	car := &model.Car{}
	if err := store.db.Find(&car, id).Error; err != nil {
		return nil, err
	}
	return car, nil
}

func (store *Store) FindModelsByBrandID(id int64) []*model.Model {
	models := []*model.Model{}
	if err := store.db.Find(&models, "brand_id = ?", id).Error; err != nil {
		return nil
	}
	return models
}
