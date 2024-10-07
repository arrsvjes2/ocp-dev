package model

// Shipper defines the shipper metadata
type Shipper struct {
	ID		string 	`json:"shipper_id"`
	Company	string	`json:"company_name"`
	Phone	string	`json:"phone"`
}