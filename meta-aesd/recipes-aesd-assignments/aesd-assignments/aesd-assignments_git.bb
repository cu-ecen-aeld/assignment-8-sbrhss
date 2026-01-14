# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
# Use local source files from the files directory
SRC_URI = "file://aesdsocket.c \
           file://Makefile \
           file://aesdsocket-start-stop"

# Modify these as desired
PV = "1.0"

# The source directory - source files are in WORKDIR
S = "${WORKDIR}"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"
# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)

inherit update-rc.d 

INITSCRIPT_NAME = "aesdsocket-start-stop"
INITSCRIPT_PARAMS = "defaults 91"

do_compile () {
        oe_runmake CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}" LDLIBS="-lpthread"
}

# TODO: Install your binaries/scripts here.
# Be sure to install the target directory with install -d first
# Yocto variables ${D} and ${S} are useful here, which you can read about at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
# and
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb


do_install () {
        install -d ${D}${bindir}
        install -m 0755 ${S}/aesdsocket ${D}${bindir}/	
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket-start-stop
}
