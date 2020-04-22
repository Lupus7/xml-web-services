package postgres

import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"time"
	"xml-web-services/cars/model"
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

func Open(config Config) (*Store, error) {
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
	return store.db.AutoMigrate(&model.Image{},&model.Ad{}, &model.Car{}, &model.Brand{}, &model.Class{}, &model.Transmission{}, &model.Fuel{}, &model.Model{}).Error
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

func (store *Store) FindAllAdsBetweenDates(start time.Time, end time.Time) ([]*model.Ad, error) {
	ads := []*model.Ad{}
	if err := store.db.Set("gorm:auto_preload", true).Where("start_date <= ? and end_date >= ?", start, end).Find(&ads).Error; err != nil {
		return nil, err
	}
	return ads, nil
}
