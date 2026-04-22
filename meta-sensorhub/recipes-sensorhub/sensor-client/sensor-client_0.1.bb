SUMMARY = "Sensorhub C++ sensor client"
DESCRIPTION = "IoT sensor simulator for the sensorhub backend"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/ssukumar2/sensorhub.git;branch=master;protocol=https \
           file://sensor-client.service"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/client-cpp"

inherit cmake systemd

DEPENDS = "curl openssl nlohmann-json"

SYSTEMD_SERVICE:${PN} = "sensor-client.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/sensor_client ${D}${bindir}/sensor_client

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sensor-client.service ${D}${systemd_system_unitdir}/sensor-client.service
}
