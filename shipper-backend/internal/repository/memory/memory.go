package memory

import (
	"context"
	"shipper/internal/repository"
	model "shipper/pkg"
	"sync"
)

// Repository defines a memory shipper repository
type Repository struct {
	sync.RWMutex
	data map[string]*model.Shipper
}
// New creates a new memory repository
func New() *Repository {
	r := &Repository{data: map[string]*model.Shipper{}}
	// Prefill data
	r.data["0001"] = &model.Shipper{ID:"0001",Company:"Parcellite",Phone:"+1-407-555-0111"}
	r.data["0010"] = &model.Shipper{ID:"0010",Company:"MO bee",Phone:"+1-417-555-9464"}
	return r
}
// Get retrieves a shipper for the specified id
func (r *Repository) Get(_ context.Context, id string) (*model.Shipper, error) {
	r.Lock()
	defer r.Unlock()
	m, ok := r.data[id]
	if (!ok) {
		return nil, repository.ErrNotFound
	}
	return m, nil
}
// Put adds shipper information for the given shipper id
func (r *Repository) Put(_ context.Context, id string, shipper *model.Shipper) error {
	r.Lock()
	defer r.Unlock()
	r.data[id] = shipper
	return nil
}