package http

import (
	"fmt"
	"encoding/json"
	"errors"
	"strings"
	"log"
	"net/http"
	"postal/internal/controller/postal"
	"postal/internal/repository"
)

// Handler defines a postal HTTP handler
type Handler struct {
	ctrl *postal.Controller
}
// new creates a nwe postal HTTP handler
func New(ctrl *postal.Controller) *Handler {
	return &Handler{ctrl}
}
// GetPostal handles GET /postal requests
func (h *Handler) GetPostal (w http.ResponseWriter, req *http.Request) {
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

// PostMail handles POST /postal/{id} requests
func (h *Handler) PostMail (w http.ResponseWriter, req *http.Request) {
	// log.Printf("Reading id from path param : " + req.URL.Path)
	id := strings.TrimPrefix(req.URL.Path, "/postal/post/")
	if id == "" {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	ctx := req.Context()
	m, err := h.ctrl.Post(ctx, id)
	if (err != nil && errors.Is(err, repository.ErrNotFound)) {
		w.WriteHeader(http.StatusNotFound)
		return
	} else if err != nil {
		log.Printf("Repository got error : %v\n", err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-type","text/plain")
	fmt.Fprintf(w, m)
}
