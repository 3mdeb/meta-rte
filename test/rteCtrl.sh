#!/bin/sh

HOST=http://192.168.3.151:8000
PFX="api/v1"

get() {
    curl -X GET $HOST/$PFX/$1
}

patch() {
    curl -X PATCH $HOST/$PFX/$1 -d "$2"
}

put() {
    curl -X PUT $HOST/$PFX/$1 -d "$2"
}

put_file() {
    curl -X PUT $HOST/$PFX/$1 -F "file=@$2"
}

delete() {
    curl -X DELETE $HOST/$PFX/$1
}

case $1 in
list_gpios)
    get "gpio"
    ;;
get_gpio)
    get "gpio/$2"
    ;;
set_gpio)
    patch "gpio/$2" "{\"state\": $3}"
    ;;
upload)
    put_file "flash/file" "$2"
    ;;
get_file)
    get "flash/file"
    ;;
flash)
    out=`get "flash/file"`
    put "flash" "$out"
    ;;
get_flash)
    get "flash"
    ;;
esac

