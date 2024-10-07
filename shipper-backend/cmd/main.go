package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"shipper/internal/controller/shipper"
	httphandler "shipper/internal/handler/http"
	"shipper/internal/repository/memory"
)
func getenv(key, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}
func main() {
	port := getenv("SERVER_PORT", "8080")
	log.Println("Starting the shipper service on port "+port)
	repo := memory.New()
	ctrl := shipper.New(repo)
	h := httphandler.New(ctrl)
	http.HandleFunc("/health", HealthResponse)
	http.HandleFunc("/shipper", h.GetShipper)
	http.HandleFunc("/", HelloServer)
	http.ListenAndServe("0.0.0.0:"+port, nil)
}

func HelloServer(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, %s!", r.URL.Path[1:])
}

func HealthResponse(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "OK")
}
