package shipper

import (
	"context"
	"errors"
	"shipper/internal/repository"
	model "shipper/pkg"
)

// ErrNotFound is returned when a requested record is not found
var ErrNotFound = errors.New("not found")
type shipperRepository interface {
	Get(ctx context.Context, id string) (*model.Shipper, error)
}
// Controller defines a shipper service controller
type Controller struct {
	repo shipperRepository
}
// New creates a shipper service controller
func New(repo shipperRepository) *Controller {
	return &Controller{repo}
}
// Get returns shipper data by id
func (c *Controller) Get(ctx context.Context, id string) (*model.Shipper, error) {
	res, err := c.repo.Get(ctx, id)
	if err != nil && errors.Is(err, repository.ErrNotFound) {
		return nil, repository.ErrNotFound
	}
	return res, err
}