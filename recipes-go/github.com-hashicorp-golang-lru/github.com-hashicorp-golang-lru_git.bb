SUMMARY = "Package which implements a fixed-size thread safe LRU cache"
HOMEPAGE = "https://github.com/hashicorp/golang-lru"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/src/github.com/hashicorp/golang-lru/LICENSE;md5=f27a50d2e878867827842f2c60e30bfc"

GO_IMPORT = "github.com/hashicorp/golang-lru"

PV = "0.5.4"
SRC_URI = "git://${GO_IMPORT};protocol=https;branch=main"
SRCREV = "14eae340515388ca95aa8e7b86f0de668e981f54"

GO_INSTALL = "${GO_IMPORT}"

inherit go-mod
