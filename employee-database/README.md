# Database generation for employee app

## Run locally

To run the containerized version call the make command with goals `run-db` and `init-db`.

```sh
make run-db
make init-db
```

NOTE: the initialization of the database does not wait for the container to be up and running, so it will be better to launch both commands separatelly

## Run OpenShift

The template file `postresql-permanent.template.yaml` has been downloaded from a cluster OCP v4.12 and modified to make the secret generated to be compatible with the _employee_ application.

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
oc rsh -n turquoise employeedb-1-6n8g5 psql -U employee-user -h localhost employeedb
psql (13.10)
Type "help" for help.

employeedb=> \dt
                   List of relations
 Schema |         Name         | Type  |     Owner
--------+----------------------+-------+---------------
 public | employee_territories | table | employee-user
 public | employees            | table | employee-user
 public | region               | table | employee-user
 public | territories          | table | employee-user
(4 rows)

employeedb=> select count(*) from employees;
 count
-------
     9
(1 row)
```

## Generated Components

The template creates the secret `employeedb` with the following default values :

```json
$ oc get secret employeedb -o json | jq '.data | map_values(@base64d)'
{
  "database-name": "employeedb",
  "database-password": "emploYeah123",
  "database-user": "employee-user"
}
```
