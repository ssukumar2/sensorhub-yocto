# sensorhub-yocto

A custom Yocto layer that packages the sensorhub C++ sensor client into a bootable embedded Linux image. The image boots in QEMU and auto-starts the sensor client as a systemd service.

I built this to learn how real embedded Linux deployment works — taking a C++ application and packaging it into a minimal Linux image using the Yocto build system, the industry standard for custom embedded Linux distributions.

## How it works

The project uses the Yocto Project (kirkstone LTS release) to build a minimal Linux image for QEMU x86-64. A custom layer called meta-sensorhub contains a BitBake recipe that fetches, builds, and installs the sensorhub C++ client. When the image boots, the client starts automatically via a systemd service and begins sending telemetry to a configured backend.

## Stack

- Yocto Project (kirkstone branch)
- BitBake build system
- QEMU x86-64 for emulation
- Custom meta-sensorhub layer
- systemd for service management

## Building

Set up the build environment:

    source poky/oe-init-build-env build

Build the image:

    bitbake core-image-minimal

Boot in QEMU:

    runqemu qemux86-64

## Status

Setting up the initial build environment and custom layer.

## License

MIT
