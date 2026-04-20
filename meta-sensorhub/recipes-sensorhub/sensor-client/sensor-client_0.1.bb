SUMMARY = "Sensorhub C++ sensor client"
DESCRIPTION = "A C++ sensor simulator that connects to the sensorhub backend and sends telemetry readings"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/ssukumar2/sensorhub.git;branch=master;protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/client-cpp"

inherit cmake

DEPENDS = "curl openssl nlohmann-json"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/sensor_client ${D}${bindir}/sensor_client
}
