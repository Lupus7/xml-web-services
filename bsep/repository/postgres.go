package repository

import (
	"bsep/model"
	"errors"
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"time"
)

type CertificateDB struct {
	db *gorm.DB
}

type PostgresConfig struct {
	Host     string
	Port     int
	User     string
	Password string
	Name     string
}

func NewCertificateDB(db *gorm.DB) *CertificateDB {
	return &CertificateDB{db: db}
}

func Open(config PostgresConfig) (*CertificateDB, error) {
	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable",
		config.Host, config.Port, config.User, config.Password, config.Name)

	db, err := gorm.Open("postgres", psqlInfo)
	if err != nil {
		return nil, err
	}

	store := NewCertificateDB(db)
	return store, nil
}

func (cdb *CertificateDB) AutoMigrate() error {
	if err := cdb.db.AutoMigrate(&model.Certificate{}).Error; err != nil {
		return err
	}
	if err := cdb.db.AutoMigrate(&model.User{}).Error; err != nil {
		return err
	}
	var count int
	if err := cdb.db.Table("users").Count(&count).Error; err != nil {
		return err
	}
	if count == 0 {
		user := &model.User{
			Username: "admin",
			Password: "admin",
		}
		err := cdb.db.Create(&user).Error
		if err != nil {
			return err
		}
	}
	if err := cdb.db.AutoMigrate(&model.Revoked{}).Error; err != nil {
		return err
	}

	return nil
}

func (cdb *CertificateDB) Create(cert *model.Certificate) error {
	return cdb.db.Create(cert).Error
}

func (cdb *CertificateDB) Close() error {
	return cdb.db.Close()
}

func (cdb *CertificateDB) ValidateUser(username string, pass string) error {
	user := &model.User{}
	err := cdb.db.Find(&user, "username = $1", username).Error
	if err != nil {
		return err
	}
	if user.Password != pass {
		return errors.New("Invalid password")
	}
	return nil
}

func (cdb *CertificateDB) RevokeCertificat(i int) error {
	revoken := &model.Revoked{
		CertificatID:   i,
		RevocationTime: time.Now(),
	}
	return cdb.db.Create(revoken).Error
}

func (cdb *CertificateDB) GetAllRevoked() ([]*model.Revoked, error) {
	revoked := []*model.Revoked{}
	if err := cdb.db.Find(&revoked).Error; err != nil {
		return nil, err
	}
	return revoked, nil
}
