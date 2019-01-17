#!/bin/bash

function errorExit {
    errorMessage="$1"
    echo "$errorMessage"
    exit 1
}

function errorCheck {
    errorCode=$?
    errorMessage="$1"
    [ "$errorCode" -ne 0 ] && errorExit "$errorMessage : ($errorCode)"
}

VERSION="${TAG}"
[ -z "$TAG" ] && errorExit "TAG not present in env"

[ -z "$UPLOADER_USERNAME" ] && errorExit "UPLOADER_USERNAME not present in env"
[ -z "$UPLOADER_PASSWORD" ] && errorExit "UPLOADER_PASSWORD not present in env"
[ -z "$UPLOADER_URL" ] && errorExit "UPLOADER_URL not present in env"
[ -z "$REMOTE_DIR" ] && errorExit "REMOTE_DIR not present in env"
REMOTE_DIR="${REMOTE_DIR}/${VERSION}"

BASE_IMAGE_NAME="core-image-minimal"
MACHINE="orange-pi-zero"
IMAGE_EXTENSION="wic.gz"
BUILD_DIR="${PWD}/build"
DEPLOY_DIR="${BUILD_DIR}/tmp/deploy/images/${MACHINE}"
LOCAL_IMAGE_NAME="${DEPLOY_DIR}/${BASE_IMAGE_NAME}-${MACHINE}.${IMAGE_EXTENSION}"
REMOTE_IMAGE_NAME="${BASE_IMAGE_NAME}-${MACHINE}-${VERSION}.${IMAGE_EXTENSION}"

function prepare {
    wget -O ./kas-docker https://raw.githubusercontent.com/siemens/kas/master/kas-docker
    errorCheck "Failed to download kas-docker script"
    chmod +x ./kas-docker
    errorCheck "Failed to change kas-docker permissions"
}

function build {
    local kas_file="$1"
    [ -z "$SSH_DIR" ] && errorExit "SSH_DIR not present in env"
    ./kas-docker --ssh-dir $SSH_DIR build $kas_file
    errorCheck "Build failed"
}

function uploadImage {
    local local_file="$1"
    local remote_file="$2"

    # make sure that the target remote directory exists
    curl -u $UPLOADER_USERNAME:$UPLOADER_PASSWORD -X MKCOL "${UPLOADER_URL}${REMOTE_DIR}"
    # upload image
    curl --fail -u $UPLOADER_USERNAME:$UPLOADER_PASSWORD -T $local_file "${UPLOADER_URL}${REMOTE_DIR}/$remote_file"
}

prepare
build "kas.yml"
uploadImage $LOCAL_IMAGE_NAME $REMOTE_IMAGE_NAME
