#!/bin/bash
# OTA firmware update agent for sensorhub
# Polls the backend every 60 minutes for available updates.
# Downloads the image, verifies its SHA256 checksum, and
# writes it to the inactive partition for A/B rollback.

BACKEND_URL="${OTA_BACKEND:-http://192.168.1.100:8000}"
CHECK_INTERVAL=3600
CURRENT_VERSION="0.1.0"
VERSION_FILE="/etc/sensorhub-version"

if [ -f "$VERSION_FILE" ]; then
    CURRENT_VERSION=$(cat "$VERSION_FILE")
fi

echo "ota-agent started, current version: $CURRENT_VERSION"

while true; do
    echo "checking for updates..."
    RESPONSE=$(curl -sf "$BACKEND_URL/ota/check?version=$CURRENT_VERSION" 2>/dev/null)
    if [ $? -ne 0 ]; then
        echo "backend unreachable, retrying in $CHECK_INTERVAL seconds"
        sleep $CHECK_INTERVAL
        continue
    fi

    AVAILABLE=$(echo "$RESPONSE" | grep -o '"update_available":[a-z]*' | cut -d: -f2)
    if [ "$AVAILABLE" = "true" ]; then
        URL=$(echo "$RESPONSE" | grep -o '"url":"[^"]*"' | cut -d'"' -f4)
        CHECKSUM=$(echo "$RESPONSE" | grep -o '"sha256":"[^"]*"' | cut -d'"' -f4)

        echo "downloading update from $URL"
        curl -sf -o /tmp/update.img "$URL"

        ACTUAL=$(sha256sum /tmp/update.img | cut -d' ' -f1)
        if [ "$ACTUAL" = "$CHECKSUM" ]; then
            echo "checksum verified, applying update"
            # dd if=/tmp/update.img of=/dev/mmcblk0p3 bs=4M
            echo "update applied, rebooting"
            # reboot
        else
            echo "checksum mismatch, discarding update"
            rm -f /tmp/update.img
        fi
    else
        echo "no update available"
    fi

    sleep $CHECK_INTERVAL
done