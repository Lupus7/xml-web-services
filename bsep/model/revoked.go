package model

import (
	"github.com/jinzhu/gorm"
	"time"
)

type Revoked struct {
	gorm.Model
	CertificatID   int
	RevocationTime time.Time
}
