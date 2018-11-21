FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://adjust-default-dram-clockspeeds.patch\
    file://adjust-small-boards-cpufreq.patch \
"
