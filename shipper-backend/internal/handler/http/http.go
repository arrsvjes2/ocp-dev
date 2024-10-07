package http

import (
	"encoding/json"
	"errors"
	"log"
	"net/http"
	"shipper/internal/controller/shipper"
	"shipper/internal/repository"
)

// Handler defines a shipper HTTP handler
type Handler struct {
	ctrl *shipper.Controller
}
// new creates a nwe shipper HTTP handler
func New(ctrl *shipper.Controller) *Handler {
	return &Handler{ctrl}
}
// GetShipper handles GET /shipper requests
func (h *Handler) GetShipper (w http.ResponseWriter, req *http.Request) {
	id := req.URL.Query().Get("id")
	if id == "" {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	ctx := req.Context()
	m, err := h.ctrl.Get(ctx, id)
	if (err != nil && errors.Is(err, repository.ErrNotFound)) {
		w.WriteHeader(http.StatusNotFound)
		return
	} else if err != nil {
		log.Printf("Repository got error : %v\n", err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-type","application/json")
	if err := json.NewEncoder(w).Encode(m); err != nil {
		log.Printf("Response encode error: %v\n", err)
	}
}