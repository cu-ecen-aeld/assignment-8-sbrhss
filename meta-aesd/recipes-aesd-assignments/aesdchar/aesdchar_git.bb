SUMMARY = "AESD char driver kernel module"
DESCRIPTION = "${SUMMARY}"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
# Use local source files from the files directory
SRC_URI = "file://main.c \
           file://aesd-circular-buffer.c \
           file://aesd-circular-buffer.h \
           file://aesdchar.h \
           file://Makefile \
           file://aesdchar-init"

# Modify these as desired
PV = "1.0"

# The source directory - source files are in WORKDIR
S = "${WORKDIR}"

inherit module

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-init"
INITSCRIPT_PARAMS:${PN} = "defaults 90"

FILES:${PN} += "${sysconfdir}/*"

MAKE_TARGETS = "modules"

do_compile () {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake -C ${STAGING_KERNEL_DIR} \
                   M=${S} \
                   KERNEL_VERSION=${KERNEL_VERSION} \
                   CC="${KERNEL_CC}" \
                   LD="${KERNEL_LD}" \
                   AR="${KERNEL_AR}" \
                   O=${STAGING_KERNEL_BUILDDIR} \
                   KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS}" \
                   modules
}

do_install () {
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/aesdchar-init ${D}${sysconfdir}/init.d

        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
        install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
