# Build for Postal application

The container image can be built using the command:

`podman build -f ./Containerfile --build-arg "BUILDREPO=http://repo.example.com/postal-1.0" -t postal:1.0 .`


To run the container image ...

`podman run -it --rm --name postal -p 8080:8080 -e SERVER_PORT=8080 postal:1.0`