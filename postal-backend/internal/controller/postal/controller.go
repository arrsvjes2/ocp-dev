package postal

import (
	"context"
	"errors"
	"github.com/google/uuid"
	"postal/internal/repository"
	model "postal/pkg"
)

// ErrNotFound is returned when a requested record is not found
var ErrNotFound = errors.New("not found")
type postalRepository interface {
	Get(ctx context.Context, id string) (*model.Postal, error)
}

// Controller defines a postal service controller
type Controller struct {
	repo postalRepository
}

// New creates a postal service controller
func New(repo postalRepository) *Controller {
	return &Controller{repo}
}

// Get returns postal data by id
func (c *Controller) Get(ctx context.Context, id string) (*model.Postal, error) {
	res, err := c.repo.Get(ctx, id)
	if err != nil && errors.Is(err, repository.ErrNotFound) {
		return nil, repository.ErrNotFound
	}
	return res, err
}

// Post returns the post id for the service
func (c *Controller) Post(ctx context.Context, id string) (string, error) {
	res, err := c.repo.Get(ctx, id)
	if err != nil && errors.Is(err, repository.ErrNotFound) {
		return "", repository.ErrNotFound
	}
	post_uuid := uuid.New()
	return res.ID+"-"+post_uuid.String(), err
}