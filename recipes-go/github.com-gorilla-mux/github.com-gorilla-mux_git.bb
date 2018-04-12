DESCRIPTION = "A powerful URL router and dispatcher for golang"
SECTION = "net"
HOMEPAGE = "http://www.gorillatoolkit.org/pkg/mux"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/src/github.com/gorilla/mux/LICENSE;md5=c50f6bd9c1e15ed0bad3bea18e3c1b7f"

SRC_URI = "git://${GO_IMPORT}"
SRCREV = "53c1911da2b537f792e7cafcb446b05ffe33b996"
PV = "1.6.1"

GO_IMPORT = "github.com/gorilla/mux"
GO_INSTALL = "${GO_IMPORT}"

inherit go
