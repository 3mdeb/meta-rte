#!/bin/sh

PFX="api/v1"

CMD_ID_RELAY=0
CMD_ID_RESET=8
CMD_ID_POWER=9

# TODO add host address as positional argument
# HOST=http://192.168.4.38:8000
HOST=http://127.0.0.1:8000

get() {
    curl --silent -X GET $HOST/$PFX/$1
}

patch() {
    curl -X PATCH $HOST/$PFX/$1 -d "$2"
}

put() {
    curl -X PUT $HOST/$PFX/$1 -d "$2"
}

post_file() {
    curl -X POST $HOST/$PFX/$1 -F "file=@$2"
}

delete() {
    curl -X DELETE $HOST/$PFX/$1
}

post() {
    curl -X POST $HOST/$PFX/$1
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
    post_file "flash/file" "$2"
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
rel)
    gpio_state=$(get "gpio/0" | python3 -c "import sys, json; print(json.load(sys.stdin)['state'])")

    case $gpio_state in
    0)
        patch "gpio/$CMD_ID_RELAY" "{\"state\":1}"
        ;;
    1)
        patch "gpio/$CMD_ID_RELAY" "{\"state\":0}"
        ;;
    esac
    ;;
pon)
    patch "gpio/$CMD_ID_POWER" "{\"state\":1}"

    sleep 1

    patch "gpio/$CMD_ID_POWER" "{\"state\":0}"
    ;;
poff)
    patch "gpio/$CMD_ID_POWER" "{\"state\":1}"

    sleep 6

    patch "gpio/$CMD_ID_POWER" "{\"state\":0}"
    ;;
reset)
    patch "gpio/$CMD_ID_RESET" "{\"state\":0}"

    sleep 1

    patch "gpio/$CMD_ID_RESET" "{\"state\":1}"

    ;;
esac
