# @(#) Makefile
#

# https://tech.davis-hansson.com/p/make/
SHELL := bash
.ONESHELL:
.SHELLFLAGS := -eu -o pipefail -c
# .DELETE_ON_ERROR:
MAKEFLAGS += --warn-undefined-variables
MAKEFLAGS += --no-builtin-rules

# Disabling via error the default goal
.DEFAULT_GOAL=error
.PHONY: error
error:
	@echo "Invalid call with no rules"
	@exit 2

mode ?= localbuild
dest ?= .
destapp ?= gobinary

.goconf:
	@go env -w GOPATH=$(PWD)/.go
	@go env -w GOCACHE=$(PWD)/.cache

.conf:
	go env GOPATH GOCACHE

conf:
	if  [ "$(mode)" == "localbuild" ]; then
		$(MAKE) .goconf
	else
		$(MAKE) .conf
	fi

run: conf
	go run ./cmd/main.go

build: conf
	go build -o main ./cmd/main.go

install:
	mv ./main $(dest)/$(destapp)