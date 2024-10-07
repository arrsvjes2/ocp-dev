# @(#) Makefile
#

# https://tech.davis-hansson.com/p/make/
SHELL := bash
.ONESHELL:
.SHELLFLAGS := -eu -o pipefail -c
# .DELETE_ON_ERROR:
MAKEFLAGS += --warn-undefined-variables
MAKEFLAGS += --no-builtin-rules

# Build version
VERSION ?= 1.0

# Disabling via error the default goal
.DEFAULT_GOAL=error
.PHONY: error
error:
	@echo "Invalid call with no rules"
	@exit 2

.goconf:
	@go env -w GOPATH=$(PWD)/.go
	@go env -w GOCACHE=$(PWD)/.cache

.deps: .goconf
	@go get github.com/google/uuid

conf:
	go env GOPATH GOCACHE

run: .deps
	go run ./cmd/main.go

build: .deps
	go build -o postal-$(VERSION) -ldflags="-X  'main.Version=$(VERSION)'" ./cmd/main.go
