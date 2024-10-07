# Catalog Template

This repository hold the resources to deploy the Catalog project via OpenShift Templates

## Catalog backend

- Build

To build the backend image use the template _backend/catalog-backend-build.template.json_ with the params for the project source code repository and the credentials to access the project.

- Deploy

To deploy the backend project call the template _backend/catalog-backend-deploy.template.yaml_ with the params for the allowing cors to the ui project.


## Catalog frontend

- Build

To build the frontend image use the template _frontend/catalog-frontend-build.template.json_ with the params for the project source code repository, the credentials for accessing the code and the url of the backend project to request the catalog data.

- Deploy

To deploy the frontend project call the template _frontend/catalog-frontend-deploy.template.yaml_.