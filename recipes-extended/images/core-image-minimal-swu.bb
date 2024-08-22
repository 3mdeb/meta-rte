# nooelint: oelint.file.underscores
SUMMARY = "Recipe generating SWU image for RTE"
HOMEPAGE = "https://github.com/sbabic/swupdate/tree/master"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or whatever you need
SRC_URI = " \
    file://sw-description \
    "

# images to build before building swupdate image
IMAGE_DEPENDS = " \
    core-image-minimal \
    "

# images and files that will be included in the .swu image
SWUPDATE_IMAGES = " \
    core-image-minimal \
    "

# a deployable image can have multiple format, choose one
SWUPDATE_IMAGES_FSTYPES[core-image-minimal] = ".direct.p2.gz"

inherit swupdate
