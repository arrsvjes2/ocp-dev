package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"postal/internal/controller/postal"
	httphandler "postal/internal/handler/http"
	"postal/internal/repository/memory"
)

var Version string

func getenv(key, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}

func main() {
	log.Println("Starting the postal service")
	port := getenv("SERVER_PORT", "8081")
	repo := memory.New()
	ctrl := postal.New(repo)
	h := httphandler.New(ctrl)
	http.HandleFunc("/health", HealthResponse)
	http.HandleFunc("/postal/post/", h.PostMail)
	http.HandleFunc("/postal", h.GetPostal)
	http.HandleFunc("/version", VersionResponse)
	http.HandleFunc("/", HelloServer)
	http.ListenAndServe("0.0.0.0:"+port, nil)
}

func HelloServer(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, %s!", r.URL.Path[1:])
}

func HealthResponse(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "OK")
}

func VersionResponse(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Version: %s", Version)
}
