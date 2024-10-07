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

# Variables to customize the container approach
PGPASSWORD ?= emploYeah123
PGUSER ?= employee
PGDATABASE ?= employees
PGIMAGE=registry.redhat.io/rhel9/postgresql-15

run-db:
	podman run -d --name postgresql_employees -e POSTGRESQL_USER=$(PGUSER) -e POSTGRESQL_PASSWORD=$(PGPASSWORD) -e POSTGRESQL_DATABASE=$(PGDATABASE) -p 5432:5432 $(PGIMAGE)

init-db:
	podman run -i --net host --name postgresql_employees_client --rm -e PGPASSWORD=$(PGPASSWORD) $(PGIMAGE) psql -U $(PGUSER) -h localhost $(PGDATABASE) <employees.sql

client-db:
	podman run -it --net host --name postgresql_employees_client --rm -e PGPASSWORD=$(PGPASSWORD) $(PGIMAGE) psql -U $(PGUSER) -h localhost $(PGDATABASE)

# Variables to customize the openshift approach
OCP_NAMESPACE ?= turquoise
OCP_IMAGE ?= 13-el9
OCP_DBUSER ?= employee-user
OCP_DBPASS ?= rzcybLrnu543
OCP_DBNAME ?= employeedb
OCP_SERVICE_NAME ?= employeedb

conf:
	@oc new-project $(OCP_NAMESPACE) || oc project $(OCP_NAMESPACE)

deploy: conf
	oc process -n $(OCP_NAMESPACE) -f postgresql-persistent.template.yaml -p POSTGRESQL_VERSION=$(OCP_IMAGE) -p POSTGRESQL_DATABASE=$(OCP_DBNAME) -p POSTGRESQL_PASSWORD=$(OCP_DBPASS) -p POSTGRESQL_USER=$(OCP_DBUSER) -p DATABASE_SERVICE_NAME=$(OCP_SERVICE_NAME) -p VOLUME_CAPACITY=100Mi | oc apply -n $(OCP_NAMESPACE) -f -

load: conf
	oc rsh -n $(OCP_NAMESPACE) $(shell oc get pods -l "deploymentconfig=$(OCP_SERVICE_NAME),name=$(OCP_SERVICE_NAME)" -o 'custom-columns=:metadata.name' --no-headers) psql -U $(OCP_DBUSER) -h localhost $(OCP_DBNAME) <employees.sql

connect: conf
	oc rsh -n $(OCP_NAMESPACE) $(shell oc get pods -l "deploymentconfig=$(OCP_SERVICE_NAME),name=$(OCP_SERVICE_NAME)" -o 'custom-columns=:metadata.name' --no-headers) psql -U $(OCP_DBUSER) -h localhost $(OCP_DBNAME)

undeploy: conf
	oc process -n $(OCP_NAMESPACE) -f postgresql-persistent.template.yaml -p POSTGRESQL_VERSION=$(OCP_IMAGE) -p POSTGRESQL_DATABASE=$(OCP_DBNAME) -p POSTGRESQL_PASSWORD=beqrE321c -p POSTGRESQL_USER=$(OCP_DBUSER) -p DATABASE_SERVICE_NAME=$(OCP_SERVICE_NAME) -p VOLUME_CAPACITY=100Mi | oc delete -n $(OCP_NAMESPACE) -f -
