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
  echo "${0} command [FILE]"
  echo "Available commands:"
  echo "  - probe - probe flash"
  echo "  - read FILE - read flash to FILE"
  echo "  - write FILE - write flash from FILE"
  echo "For example:"
  echo "${0} probe"
  echo "${0} read flash.rom"
  echo "${0} write flash.rom"
  exit 0
}

SPI_DEV="/dev/spidev1.0"
SPI_SPEED="16000"
FLASHROM_PROGRAMMER="linux_spi:dev=$SPI_DEV,spispeed=$SPI_SPEED"

CHIP_THINKPAD_SPI1="MX25L6406E/MX25L6408E"
CHIP_THINKPAD_SPI2="MX25L3206E/MX25L3208E"
CHIP_PROTECTLI_FW6="MX25L6436E/MX25L6445E/MX25L6465E"

# FLASHROM_CHIP="-c $CHIP_PROTECTLI_FW6"

WP_RANGE="0,0x00800000"

[ $# -eq 0 ] && usage

spiON() {
  echo "set SPI Vcc to 3.3V"
  # 0 - 1.8V   ,   1 - 3.3V
  echo 1 > /sys/class/gpio/gpio517/value
  sleep 1
  echo "SPI lines ON"
  echo 1 > /sys/class/gpio/gpio516/value
  sleep 1
  echo "SPI Vcc ON"
  echo 1 > /sys/class/gpio/gpio518/value
  sleep 2
}

spiOFF() {
  echo "SPI Vcc OFF"
  echo 0 > /sys/class/gpio/gpio518/value
  echo "SPI lines OFF"
  echo 0 > /sys/class/gpio/gpio516/value
  echo 0 > /sys/class/gpio/gpio517/value
}

CMD="$1"
FILE="$2"

case "$CMD" in
  "probe")
    spiON
    echo "Probing ..."
    flashrom -p "$FLASHROM_PROGRAMMER" $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "read")
    [ -z "$FILE" ] && errorExit "FILE not given"
    spiON
    echo "Reading ..."
    flashrom -p "$FLASHROM_PROGRAMMER" -r "$FILE" $FLASHROM_CHIP -V
    sleep 1
    spiOFF
  ;;
  "write")
    [ -z "$FILE" ] && errorExit "FILE not given"
    spiON
    echo "Writing ..."
    flashrom -p "$FLASHROM_PROGRAMMER" -w "$FILE" $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "wp-list")
    spiON
    echo "Checking WP list ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-list $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "wp-status")
    spiON
    echo "Checking WP status ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-status $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "wp-enable")
    spiON
    echo "Setting WP region ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-range="$WP_RANGE" $FLASHROM_CHIP
    sleep 1
    echo "Enabling WP region ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-enable $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "wp-disable")
    spiON
    echo "Disabling WP protection ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-disable $FLASHROM_CHIP
    sleep 1
    echo "Clearing WP region ..."
    flashrom -p "$FLASHROM_PROGRAMMER" --wp-range=0,0 $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "erase")
    spiON
    echo "Erasing chip ..."
    flashrom -p "$FLASHROM_PROGRAMMER" -E $FLASHROM_CHIP
    sleep 1
    spiOFF
  ;;
  "wp-high")
    echo "Setting WP pin HIGH ..."
    echo "out" > /sys/class/gpio/gpio514/direction
    # deassert WP pin to disable hardware protection of status registers
    echo "1" > /sys/class/gpio/gpio514/value
  ;;
  "wp-low")
    echo "Setting WP pin LOW ..."
    echo "out" > /sys/class/gpio/gpio514/direction
    # assert WP pin to enable hardware protection of status registers
    echo "0"  > /sys/class/gpio/gpio514/value
  ;;
  *)
    echo "Command \"$CMD\" is not supported"
    usage
  ;;
esac

