# catalog-backend

This project is the codebase for managing the catalog backend.

NOTE: The application has been developed as part of the codebase suite for the Red Hat Certified OpenShift Application Developer exam.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Example Requests

- Create category

```json
$ http ":8080/category" "category_name=Beverages" "description=Soft drinks, coffees, teas, beers, and ales"
http ":8080/category"
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "category_name": "Beverages",
    "description": "Soft drinks, coffees, teas, beers, and ales",
    "id": "648b0fee27e1a05fe39325b2",
    "picture": null
}

```

- Get categories

```json
$ http ":8080/category"
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
content-length: 138

[
    {
        "category_name": "Beverages",
        "description": "Soft drinks, coffees, teas, beers, and ales",
        "id": "648b0fee27e1a05fe39325b2",
        "picture": null
    }
]
```

- Create product

```json
$ http ":8080/product" "product_name=chai" "supplier=0100" "categoryId=648b0fee27e1a05fe39325b2" "quantityPerUnit=10 boxes x 30 bags" "unitPrice=18.00" "unitsInStock=39" "unitsInOrder=0" "reorderLevel=10" "discontinued=false"
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
content-length: 264

{
    "categoryId": "648b0fee27e1a05fe39325b2",
    "categoryName": null,
    "discontinued": false,
    "id": "648b102f27e1a05fe39325b3",
    "product_name": "chai",
    ...
}
```

- Get product by id

```json
$ http -b get ":8080/product/648b102f27e1a05fe39325b3"
{
    "categoryId": "648b0fee27e1a05fe39325b2",
    "categoryName": "Beverages",
    "discontinued": false,
    "id": "648b102f27e1a05fe39325b3",
    "product_name": "chai",
    "quantityPerUnit": "10 boxes x 30 bags",
    "reorderLevel": 10,
    "supplier": "0100",
    "unitPrice": 18.0,
    "unitsInOrder": 0,
    "unitsInStock": 39
}
```

- Get products paged list

```json
$ http -b get ":8080/product?page=0&size=10"
[
    {
        "categoryId": "648ae14d81d88c3ab49c2339",
        "categoryName": "Beverages",
        "discontinued": false,
        "id": "648b0ce327e1a05fe39325ac",
        "product_name": "chai",
        "quantityPerUnit": "10 boxes x 30 bags",
        "reorderLevel": 10,
        "supplier": "0100",
        "unitPrice": 18.0,
        "unitsInOrder": 0,
        "unitsInStock": 39
    },
]
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Containerization

To build the image provided by quarkus community use the command :

```sh
podman build -f ./src/main/docker/Dockerfile.jvm -t catalog-backend:latest .
```

To build the image as openshift build should do use the command :

```sh
podman build \
        --volume ${PWD}/settings:/settings:Z \
        --build-arg "MAVEN_REPO=http://nexus.example.com:8081/repository/maven-public/" \
        -f ./Containerfile.jvm \
        -t catalog-backend:latest \
        .
```

To run the application use the command :

```sh
podman run -it --rm --name catalog-backend -p 8080:8080 catalog-backend:latest
```

## References

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: [quarkus.io](https://quarkus.io/).
