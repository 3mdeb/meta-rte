#!/bin/sh

errorExit() {
  errorMessage="$1"
  echo "$errorMessage"
  exit 1
}

errorCheck() {
  errorCode=$?
  errorMessage="$1"
  [ "$errorCode" -ne 0 ] && errorExit "$errorMessage : ($errorCode)"
}

usage() {
  echo "${0} [COMMAND] [OPTIONS]"
  echo "[COMMAND]:"
  echo "    help - show this help"
  echo "    probe - probe flash"
  echo "    read [FILE] - read flash to FILE"
  echo "    write [FILE] - write flash from FILE"
  echo "    wp-list - check WP list"
  echo "    wp-status - check WP status"
  echo "    wp-enable - enable WP"
  echo "    wp-disable - disable WP"
  echo "    wp-high - enable hardware protection of status registers"
  echo "    wp-low -disable hardware protection of status registers"
  echo "[OPTIONS]:"
  echo "    -V --voltage [VOLTAGE] - specify SPI voltage to be used: 1.8V or 3.3V"
  echo "    --flashrom-params \"[PARAMS]\" - specify additional flashrom params surrounded"
  echo "    with double quotes"
  echo "    -v --verbose - verbose mode"
  echo "    -d --dry-run - run skript without executing commands"
  echo "Examples:"
  echo "${0} probe"
  echo "${0} read flash.rom"
  echo "${0} write flash.rom"
  exit 0
}

SPI_DEV="/dev/spidev1.0"
SPI_SPEED="16000"
FLASHROM_PROGRAMMER="linux_spi:dev=$SPI_DEV,spispeed=$SPI_SPEED"

WP_RANGE="0,0x00800000"

[ $# -eq 0 ] && usage

spiON() {
  if [ "$VOLTAGE" = "3.3V" ]; then
    echo "Setting SPI VCC to 3.3V"
    # 0 - 1.8V, 1 - 3.3V:
    if [ -n "$DRY_RUN" ]; then
      echo "echo 1 > /sys/class/gpio/gpio517/value"
    else
      echo 1 > /sys/class/gpio/gpio517/value
    fi
  else
    echo "Setting SPI VCC to 1.8V"

    # 0 - 1.8V, 1 - 3.3V:
    if [ -n "$DRY_RUN" ]; then
      echo "echo 0 > /sys/class/gpio/gpio517/value"
    else
      echo 0 > /sys/class/gpio/gpio517/value
    fi
  fi

  sleep 1

  echo "SPI lines ON"

  if [ -z "$DRY_RUN" ]; then
    echo 1 > /sys/class/gpio/gpio516/value
  else
    echo 'echo 1 > /sys/class/gpio/gpio516/value'
  fi

  sleep 1
  echo "SPI Vcc ON"

  if [ -z "$DRY_RUN" ]; then
    echo 1 > /sys/class/gpio/gpio518/value
  else
    echo 'echo 1 > /sys/class/gpio/gpio518/value'
  fi

  sleep 2

  return 0
}

spiOFF() {
  echo "SPI Vcc OFF"

  if [ -z "$DRY_RUN" ]; then
    echo 0 > /sys/class/gpio/gpio518/value
  else
    echo 'echo 0 > /sys/class/gpio/gpio518/value'
  fi

  echo "SPI lines OFF"

  if [ -z "$DRY_RUN" ]; then
    echo 0 > /sys/class/gpio/gpio516/value
    echo 0 > /sys/class/gpio/gpio517/value
  else
    echo 'echo 0 > /sys/class/gpio/gpio516/value'
    echo 'echo 0 > /sys/class/gpio/gpio517/value'
  fi

  return 0
}

prase_args(){
while [ "$#" != "0" ]; do
  case $1 in
    "help")
      CMD="help"
      return 0
      ;;
    "probe")
      CMD="probe"
      shift
      ;;
    "read")
      CMD="read"
      FILE="$2"

      [ -z "$FILE" ] && errorExit "No file provided, check help, exiting..."

      shift 2
      ;;
    "write")
      CMD="write"
      FILE="$2"

      [ -z "$FILE" ] && errorExit "No file provided, check help, exiting..."
      [ -f "$FILE" ] || errorExit "Cannot read the $FILE file, exiting..."

      shift 2
      ;;
    "wp-list")
      CMD="wp-list"
      shift
      ;;
    "wp-status")
      CMD="wp-status"
      shift
      ;;
    "wp-enable")
      CMD="wp-enable"
      shift
      ;;
    "wp-disable")
      CMD="wp-disable"
      shift
      ;;
    "erase")
      CMD="erase"
      shift
      ;;
    "wp-high")
      CMD="wp-high"
      shift
      ;;
    "wp-low")
      CMD="wp-low"
      shift
      ;;
    "-V" | "--voltage")
      VOLTAGE="$2"
      shift 2
      ;;
    "--flashrom-params")
      FLASHROM_PARAMS="$2"
      shift 2
      ;;
    "-v" | "--verbose")
      set -o xtrace
      PS4='+(${BASH_SOURCE}:${LINENO}): ${FUNCNAME[0]:+${FUNCNAME[0]}(): }'
      shift
      ;;
    "-d" | "--dry-run")
      DRY_RUN="true"
      shift
      ;;
    *)
      errorExit "$1: unknown argument, check help, exiting..."
      ;;
    esac
  done

  return 0
}

prase_args "$@"

case "$CMD" in
  "help")
    usage
    ;;
  "probe")
    spiON
    echo "Probing ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS"
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS"
    fi

    sleep 1
    spiOFF
  ;;
  "read")
    spiON
    echo "Reading flash and writing it into $FILE..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -r "$FILE" -V
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -r "$FILE" -V
    fi

    sleep 1
    spiOFF
  ;;
  "write")
    spiON
    echo "Writing data from $FILE into flash..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -w "$FILE"
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -w "$FILE"
    fi

    sleep 1
    spiOFF
  ;;
  "wp-list")
    spiON
    echo "Checking WP list ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-list
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-list
    fi

    sleep 1
    spiOFF
  ;;
  "wp-status")
    spiON
    echo "Checking WP status ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-status
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-status
    fi

    sleep 1
    spiOFF
  ;;
  "wp-enable")
    spiON
    echo "Setting WP region ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-range="$WP_RANGE"
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-range="$WP_RANGE"
    fi

    sleep 1
    echo "Enabling WP region ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-enable
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-enable
    fi

    sleep 1
    spiOFF
  ;;
  "wp-disable")
    spiON
    echo "Disabling WP protection ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-disable
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-disable
    fi

    sleep 1
    echo "Clearing WP region ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-range=0,0
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" --wp-range=0,0
    fi

    sleep 1
    spiOFF
  ;;
  "erase")
    spiON
    echo "Erasing chip ..."

    if [ -z "$DRY_RUN" ]; then
      flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -E
    else
      echo flashrom -p "$FLASHROM_PROGRAMMER" "$FLASHROM_PARAMS" -E
    fi

    sleep 1
    spiOFF
  ;;
  "wp-high")
    echo "Setting WP pin HIGH ..."
    # deassert WP pin to disable hardware protection of status registers:
    if [ -z "$DRY_RUN" ]; then
      echo "out" > /sys/class/gpio/gpio514/direction
      echo "1" > /sys/class/gpio/gpio514/value
    else
      echo 'echo "out" > /sys/class/gpio/gpio514/direction'
      echo 'echo "1" > /sys/class/gpio/gpio514/value'
    fi
    ;;
  "wp-low")
    echo "Setting WP pin LOW ..."
    # assert WP pin to enable hardware protection of status registers:
    if [ -z "$DRY_RUN" ]; then
      echo "out" > /sys/class/gpio/gpio514/direction
      echo "0"  > /sys/class/gpio/gpio514/value
    else
      echo 'echo "out" > /sys/class/gpio/gpio514/direction'
      echo 'echo "0"  > /sys/class/gpio/gpio514/value'
    fi
    ;;
  esac
