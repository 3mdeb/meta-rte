FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-Makefile-Don-t-error-on-stringop-overflow.patch;patchdir=3rdparty/vboot \
    "
