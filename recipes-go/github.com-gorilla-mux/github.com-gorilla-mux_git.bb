DESCRIPTION = "A powerful URL router and dispatcher for golang"
SECTION = "net"
HOMEPAGE = "http://www.gorillatoolkit.org/pkg/mux"

LICENSE = "gorilla-mux-license"
NO_GENERIC_LICENSE[gorilla-mux-license] = "src/github.com/gorilla/mux/LICENSE"
LIC_FILES_CHKSUM = "file://${S}/src/github.com/gorilla/mux/LICENSE;md5=c30eee78985cf2584cded5f89ba3d787"

SRC_URI = "git://${GO_IMPORT};protocol=https;branch=main"
SRCREV = "b4617d0b9670ad14039b2739167fd35a60f557c5"
PV = "1.8.1"

GO_IMPORT = "github.com/gorilla/mux"
GO_INSTALL = "${GO_IMPORT}"

inherit go-mod
