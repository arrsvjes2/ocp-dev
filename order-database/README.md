# Database generation for orders app

## Run locally

To run the containerized version call the make command with goals `run-db` and `init-db`.

```sh
make run-db
make init-db
```

NOTE: the initialization of the database does not wait for the container to be up and running, so it will be better to launch both commands separatelly

## Run OpenShift

The template file `postresql-permanent.template.yaml` has been downloaded from a cluster OCP v4.12 and modified to make the secret generated to be compatible with the _orders_ application.

```sh
# Download command
oc get template postgresql-persistent -n openshift -o yaml >postgresql-persistent.template.yaml
```

To run the database simply run the goals `deploy` and then `load` in the project Makefile.

```sh
make deploy
make load
```

NOTE: the load of the database does not wait for the remote pod to be up and ready, so it will be better to launch both commands separatelly

To verify the execution has been successful connnect to the database and verify the schemas were created.

```sh
$ make connect
oc rsh -n acid ordersdb-1-6n8g5 psql -U orders-user -h localhost ordersdb
psql (13.10)
Type "help" for help.

ordersdb=> \dt
              List of relations
 Schema |     Name      | Type  |    Owner
--------+---------------+-------+-------------
 public | order_details | table | orders-user
 public | orders        | table | orders-user
(2 rows)

ordersdb=> select count(*) from orders;
 count
-------
   830
(1 row)

ordersdb=> select count(*) from order_details;
 count
-------
  2155
(1 row)

ordersdb=> exit
```

## Generated Components

The template creates the secret `ordersdb` with the following default values :

```json
$ oc get secret ordersdb -o json | jq '.data | map_values(@base64d)'
{
  "DATABASE_NAME": "ordersdb",
  "DATABASE_PASSWORD": "beqrE321c",
  "DATABASE_USER": "orders-user"
}
```

The application to work would need to plug the secret as an environment and a configmap with the following keys :

```yaml
$ oc get configmap orders -o yaml
apiVersion: v1
data:
  DATABASE_HOST: ordersdb
  DATABASE_PORT: "5432"
kind: ConfigMap
metadata:
  name: orders
```

As a complement they may need to add a specific environment variable:

```sh
oc set env dc/order APP_DATA=orders-backend
```

Then the deployment config will need the following entries to be fully configured.

```yaml
...
  containers:
    - name: quarkus
      env:
        - name: APP_DATA
          value: orders-backend
      envFrom:
        - secretRef:
            name: ordersdb
        - configMapRef:
            name: orders
...
```

Other possible questions may be to modify the

```
                - name: POD_NAME
                  valueFrom:
                    fieldRef:
                      fieldPath: metadata.name
                - name: POD_IP
                  valueFrom:
                    fieldRef:
                      fieldPath: status.podIP
                - name: KUBERNETES_NAMESPACE
                  valueFrom:
                    fieldRef:
                      apiVersion: v1
                      fieldPath: "metadata.namespace"
                - name: "JAVA_OPTIONS"
                  value: >-
                    -Dquarkus.http.host=0.0.0.0
                    -Dquarkus.log.category."com.example.repartman".level=DEBUG
```