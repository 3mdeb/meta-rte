require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

# Remove patch which is unnecessary for u-boot 2020.04
# https://github.com/u-boot/u-boot/blob/v2020.04/scripts/dtc/dtc-lexer.l#L39
SRC_URI_remove = " \
    file://remove-redundant-yyloc-global.patch \
"

SRCREV = "36fec02b1f90b92cf51ec531564f9284eae27ab4"
