SUMMARY = "Sensorhub OTA firmware update agent"
DESCRIPTION = "Polls the backend for firmware updates, downloads and \
verifies the image signature, and applies the update with a rollback \
mechanism using dual A/B root partitions."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://ota-agent.sh \
           file://ota-agent.service"

inherit systemd

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/ota-agent.sh ${D}${bindir}/ota-agent

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ota-agent.service ${D}${systemd_system_unitdir}/
}

SYSTEMD_SERVICE:${PN} = "ota-agent.service"
SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} = "curl bash"