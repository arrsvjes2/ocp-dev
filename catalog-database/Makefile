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
MONGODB_IMAGE=docker.io/library/mongo:5.0
MONGODB_DATABASE=catalog
MONGODB_USER=catalog
MONGODB_PASSWORD=catalog-pwd

run-db:
	podman run --rm -d --name mongodb_catalog -p 27017:27017 \
		-e MONGO_INITDB_ROOT_USERNAME=$(MONGODB_USER) \
		-e MONGO_INITDB_ROOT_PASSWORD=$(MONGODB_PASSWORD) \
		-e MONGO_INITDB_DATABASE=$(MONGODB_DATABASE) \
		$(MONGODB_IMAGE)

init-db:
	podman run --net host -t --name mongodb_client --rm -v ./collections:/data:Z $(MONGODB_IMAGE) \
		mongoimport -u $(MONGODB_USER) -p "$(MONGODB_PASSWORD)" --authenticationDatabase admin \
							  -h localhost --db $(MONGODB_DATABASE) --collection category --drop --type json --file /data/catalog/category.json
	podman run --net host -t --name mongodb_client --rm -v ./collections:/data:Z $(MONGODB_IMAGE) \
		mongoimport -u $(MONGODB_USER) -p "$(MONGODB_PASSWORD)" --authenticationDatabase admin \
								-h localhost --db $(MONGODB_DATABASE) --collection product --drop --type json --file /data/catalog/product.json

client-db:
	podman run -it --net host --name mongodb_client --rm $(MONGODB_IMAGE) \
		mongo -u $(MONGODB_USER) -p "$(MONGODB_PASSWORD)" --authenticationDatabase admin $(MONGODB_DATABASE)

# Variables to customize the openshift approach
OCP_NAMESPACE ?= petrol
OCP_IMAGE ?= 5.0
OCP_DBUSER ?= catalog
OCP_DBPASS ?= catalog-pwd
OCP_DBNAME ?= catalog
OCP_SERVICE_NAME ?= catalogdb

define pod_name
	$(shell oc get pods -n $(OCP_NAMESPACE) -l "statefulset.kubernetes.io/pod-name,name=$(OCP_SERVICE_NAME)" -o 'custom-columns=:metadata.name' --no-headers)
endef

conf:
	@oc new-project $(OCP_NAMESPACE) || oc project $(OCP_NAMESPACE)

deploy: conf
	oc process -n $(OCP_NAMESPACE) -f mongodb-persistent.template.yaml -p MONGODB_VERSION=$(OCP_IMAGE) -p MONGODB_DATABASE=$(OCP_DBNAME) -p MONGODB_PASSWORD=$(OCP_DBPASS) -p MONGODB_USER=$(OCP_DBUSER) -p DATABASE_SERVICE_NAME=$(OCP_SERVICE_NAME) -p VOLUME_CAPACITY=100Mi | oc apply -n $(OCP_NAMESPACE) -f -

_sync:
	oc rsync ./collections/catalog  $(call pod_name):/data

load: conf _sync
	oc rsh -n $(OCP_NAMESPACE) $(call pod_name) \
		mongoimport -u $(OCP_DBUSER) -p "$(OCP_DBPASS)" --authenticationDatabase admin \
							  -h localhost --db $(OCP_DBNAME) --collection category --drop --type json --file /data/catalog/category.json
	oc rsh -n $(OCP_NAMESPACE) $(call pod_name) \
		mongoimport -u $(OCP_DBUSER) -p "$(OCP_DBPASS)" --authenticationDatabase admin \
							  -h localhost --db $(OCP_DBNAME) --collection product --drop --type json --file /data/catalog/product.json

connect: conf
	oc rsh -n $(OCP_NAMESPACE) $(shell oc get pods -l "statefulset.kubernetes.io/pod-name,name=$(OCP_SERVICE_NAME)" -o 'custom-columns=:metadata.name' --no-headers) \
		mongo -u $(OCP_DBUSER) -p "$(OCP_DBPASS)" --authenticationDatabase admin $(OCP_DBNAME)

pod-name: conf
	@echo $(call pod_name)
	# oc get pods  -n $(OCP_NAMESPACE) -l "statefulset.kubernetes.io/pod-name,name=$(OCP_SERVICE_NAME)" -o 'custom-columns=:metadata.name' --no-headers

undeploy: conf
	oc process -n $(OCP_NAMESPACE) -f mongodb-persistent.template.yaml -p MONGODB_VERSION=$(OCP_IMAGE) -p MONGODB_DATABASE=$(OCP_DBNAME) -p MONGODB_PASSWORD=$(OCP_DBPASS) -p MONGODB_USER=$(OCP_DBUSER) -p DATABASE_SERVICE_NAME=$(OCP_SERVICE_NAME) -p VOLUME_CAPACITY=100Mi | oc delete -n $(OCP_NAMESPACE) -f -
