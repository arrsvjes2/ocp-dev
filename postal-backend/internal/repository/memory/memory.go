package memory

import (
	"context"
	"postal/internal/repository"
	model "postal/pkg"
	"sync"
)

// Repository defines a memory shipper repository
type Repository struct {
	sync.RWMutex
	data map[string]*model.Postal
}
// New creates a new memory repository
func New() *Repository {
	r := &Repository{data: map[string]*model.Postal{}}
	// Prefill data
	r.data["0201"] = &model.Postal{ID:"0201",Company:"Royal Mail",Phone:"+44 1234 965945"}
	r.data["0210"] = &model.Postal{ID:"0210",Company:"Postal Service",Phone:"+1-417-555-9464"}
	return r
}
// Get retrieves a postal for the specified id
func (r *Repository) Get(_ context.Context, id string) (*model.Postal, error) {
	r.Lock()
	defer r.Unlock()
	m, ok := r.data[id]
	if (!ok) {
		return nil, repository.ErrNotFound
	}
	return m, nil
}
// Put adds shipper information for the given postal id
func (r *Repository) Put(_ context.Context, id string, postal *model.Postal) error {
	r.Lock()
	defer r.Unlock()
	r.data[id] = postal
	return nil
}