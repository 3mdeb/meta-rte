# go commands
GOCMD ?= go
GOBUILD = $(GOCMD) build
GOCLEAN = $(GOCMD) clean
GOGET = $(GOCMD) get

# binaries names
BINARY_NAME=RteCtrl

# versioning
VERSION_MAJOR = 0
VERSION_MINOR = 5
VERSION_PATCH = 0
VERSION_BASE = $(VERSION_MAJOR).$(VERSION_MINOR).$(VERSION_PATCH)
VERSION_GIT := $(shell git describe --always --long --dirty)
VERSION_FULL = $(VERSION_BASE)+$(VERSION_GIT)
VERSION_LDFLAGS = -ldflags="-X main.version=$(VERSION_FULL)"


all: build-host
build-arm: deps
	CGO_ENABLED=0 GOOS=linux GOARCH=arm $(GOBUILD) $(VERSION_LDFLAGS) -o $(BINARY_NAME) -v

build-amd64: deps
	CGO_ENABLED=0 GOOS=linux GOARCH=amd64 $(GOBUILD) $(VERSION_LDFLAGS) -o $(BINARY_NAME) -v

build-host: deps
	$(GOBUILD) $(VERSION_LDFLAGS) -o $(BINARY_NAME) -v

clean:
	$(GOCLEAN)
	rm -f $(BINARY_NAME)

deps:
	$(GOGET) github.com/gorilla/mux
