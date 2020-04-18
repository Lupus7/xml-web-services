package postgres

import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
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
	return store.db.AutoMigrate(&model.Car{}).Error
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
	if err := store.db.Where("advertiser = ?", adv).Find(&cars).Error; err != nil {
		return nil, err
	}
	return cars, nil
}

func (store *Store) FindAllCars() ([]*model.Car, error) {
	cars := []*model.Car{}
	if err := store.db.Find(&cars).Error; err != nil {
		return nil, err
	}
	return cars, nil
}

func (store *Store) FindAvailableCars() ([]*model.Car, error) {
	cars := []*model.Car{}
	if err := store.db.Where("available = true").Find(&cars).Error; err != nil {
		return nil, err
	}
	return cars, nil
}

func (s *Store) DB() *gorm.DB {
	return s.db
}
