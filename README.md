# xml-web-services

#Cars -- Golang service

1. If you dont have, install golang : https://golang.org/dl/
2. We are using postgres database, gorm ( Golang's object mapper).
3. Create database in postgres, enter your credentials in .env file 
4. enter cars folder ( cd cars)
5. run command: $ go build 
6. To populate database with some informations, go to cars/seed folder, and run $ go run main.go
7. To run service, go to cars folder, and run $ go run main.go