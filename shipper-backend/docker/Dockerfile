FROM registry.access.redhat.com/ubi9/go-toolset:latest as builder

WORKDIR /app

COPY --chown=1001:1001 go.mod ./
RUN go mod download

COPY --chown=1001:1001 ./ ./
RUN go build -o ./main ./cmd/main.go

FROM registry.access.redhat.com/ubi9-micro
COPY --from=builder /app/main /app/

WORKDIR /app

EXPOSE 8081
ENTRYPOINT ["/app/main"]
CMD [ "-p=8081"]
