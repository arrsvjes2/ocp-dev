# Database generation for catalog app

## Run locally

To run the containerized version call the make command with goals `run-db` and `init-db`.

```sh
make run-db
make init-db
```

NOTE: the initialization of the database does not wait for the container to be up and running, so it will be better to launch both commands separatelly

## Run OpenShift

The template file `mongodb-permanent.template.yaml` has been generated from a cluster OCP v4.12 to create the MongoDB database for the _catalog_ application.


To run the database simply run the goals `deploy` and then `load` in the project Makefile.

_NOTE:_ The template needs the user to have enough permissions to create the RBAC authorization with scc anyuid

```sh
make deploy
make load
```

_NOTE:_ the load of the database does not wait for the remote pod to be up and ready, so it will be better to launch both commands separatelly

To verify the execution has been successful connnect to the database and verify the schemas were created.

```sh
$ make connect
oc rsh -n petrol catalogdb-0 \
	mongo -u catalog -p "catalog-pwd" --authenticationDatabase admin catalog
MongoDB shell version v5.0.18
connecting to: mongodb://127.0.0.1:27017/catalog?authSource=admin&compressors=disabled&gssapiServiceName=mongodb
MongoDB server version: 5.0.18
Welcome to the MongoDB shell.

> db.getCollectionNames();
[ "category", "product" ]

> quit();
```

_Troubleshooting:_ In case the user does not have enough permissions to create the _Role_ it will show the error

```sh
Error from server (Forbidden): error when creating "STDIN": roles.rbac.authorization.k8s.io "catalogdb-scc-anyuid" is forbidden: user "developer" (groups=["system:authenticated:oauth" "system:authenticated"]) is attempting to grant RBAC permissions not currently held:
{APIGroups:["security.openshift.io"], Resources:["securitycontextconstraints"], ResourceNames:["anyuid"], Verbs:["use"]}
```


## Generated Components

The template creates the secret `catalogdb` with the following default values :

```json
oc get secret catalogdb -o json | jq '.data| map_values(@base64d)'
{
  "database-name": "catalog",
  "database-password": "catalog-pwd",
  "database-user": "catalog"
}
```

The application to work would need to plug the database service name as an environment :

```json
oc get dc catalog-backend -o json | jq '.spec.template.spec.containers[0].env'
[
  {
    "name": "KUBERNETES_NAMESPACE",
    "valueFrom": {
      "fieldRef": {
        "apiVersion": "v1",
        "fieldPath": "metadata.namespace"
      }
    }
  },
  {
    "name": "DATABASE_HOST",
    "value": "catalogdb"
  }
]
```

One possible task is to call the catalog-app template (frontend and backend) with the parameter to connect to the database_host

Example given:

```sh
$ oc get service -l template=mongodb-persistent-template -o name
service/catalogdb
$ oc process -f catalog-app.template.yaml -p MONGODB_DATABASE_SERVICE=catalogdb ...
```

## Test deployment

To test the database was deployed successfully and the environment is prepared for the task, the application _catalog-backend_ has to be deployed. To do that simply build and publish the image, and then deploy the application in kubernetes.

```sh
$ podman build -f ./src/main/docker/Dockerfile.jvm -t catalog-backend:latest .
...
$ skopeo copy --dest-creds="developer:$(oc whoami -t)"  --dest-tls-verify=false containers-storage:localhost/catalog-backend:latest docker://default-route-openshift-image-registry.apps-crc.testing/petrol/catalog-backend:latest
...
$ oc apply -f src/main/openshift/
deploymentconfig.apps.openshift.io/catalog-backend created
route.route.openshift.io/catalog-backend created
```

The application pod should be running amd the route connected to the service ...

```sh
$ oc get pods
NAME                       READY   STATUS      RESTARTS   AGE
catalog-backend-1-deploy   0/1     Completed   0          12m
catalog-backend-1-jh2cm    1/1     Running     0          12m
catalogdb-0                1/1     Running     0          20m
$ oc get route
NAME              HOST/PORT                                 PATH   SERVICES          PORT   TERMINATION   WILDCARD
catalog-backend   catalog-backend-petrol.apps-crc.testing          catalog-backend   8080                 None
```

Finally, to verify the application has connected to the database, request the categories or the products from the application.

```json
$ http "http://catalog-backend-petrol.apps-crc.testing/category" | jq '.[-1]'
{
  "id": "648c81ad4b8e992f7556aad1",
  "description": "Seaweed and fish",
  "picture": "crank",
  "category_name": "Seafood"
}
$ http "http://catalog-backend-petrol.apps-crc.testing/product" | jq '.[0]'
{
  "id": "648fe9c7fd3100542762f90a",
  "supplier": "7",
  "categoryId": "648c81ad4b8e992f7556aacf",
  "categoryName": "Meat/Poultry",
  "quantityPerUnit": "20 - 1 kg tins",
  "unitPrice": 39.00,
  "unitsInStock": 0,
  "unitsInOrder": 0,
  "reorderLevel": 0,
  "discontinued": true,
  "product_name": "Alice Mutton"
}
```

## Catalog deployment

### Frontend Build and Deployment

To launch the build in openshift :

```sh
oc process -f openshift/templates/catalog-frontend-build.template.json -p NAME=catalog-frontend -p "SOURCE_REPOSITORY_URL=http://repo2.example.com:5000/finesmart/catalog-frontend"  -p "APPLICATION_SERVICE=http://catalog-backend-petrol.apps-crc.testing/" -p "NPM_MIRROR=http://nexus2.example.com:8081/repository/npm-repo/" -p "SOURCE_USERNAME=git-ci" -p "SOURCE_PASSWORD=git-pass" |oc apply -n petrol -f -
```

To launch the deployment in openshift :

```sh
oc process -f openshift/templates/catalog-frontend-deploy.template.json -p NAME=catalog-frontend  | oc apply -f -
```

### Backend Build and deployment

Direct deploy resources from the backend project.

```sh
oc apply -f src/main/openshift/
```

Build using the project template ... (from the template project root directory)

```sh
oc process -f ./backend/catalog-backend-build.template.json -p NAME=catalog-backend -p "SOURCE_REPOSITORY_URL=http://repo2.example.com:5000/finesmart/catalog-backend"  -p "MAVEN_REPO=http://nexus2.example.com:8081/repository/maven-public/" -p "SOURCE_USERNAME=git-ci" -p "SOURCE_PASSWORD=git-pass" |oc apply -n petrol -f -
```

Deploy using the project template ... (from the tempalte project root directory)

```sh
oc process -f ./backend/catalog-backend-deploy.template.yaml -p NAME=catalog-backend -p "APPLICATION_UI=http://catalog-frontend-petrol.apps-crc.testing" |oc apply -n petrol -f -
```


## More information

More information on the image base can be found in :

https://github.com/docker-library/mongo/blob/master/5.0/Dockerfile


Also, for developing an Java Quarkus application :

https://quarkus.io/guides/mongodb-panache

and extra detail for connecting to a mongo database :

https://quarkus.io/guides/mongodb#quarkus-mongodb_quarkus.mongodb.credentials.auth-source
