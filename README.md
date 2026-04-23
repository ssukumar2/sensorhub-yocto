# sensorhub-yocto

A custom Yocto layer that packages the sensorhub C++ sensor client into a bootable embedded Linux image. The image boots in QEMU and auto-starts the sensor client as a systemd service.

I built this to learn how real embedded Linux deployment works — taking a C++ application and packaging it into a minimal Linux image using the Yocto build system, the industry standard for custom embedded Linux distributions.

## How it works

The project uses the Yocto Project (kirkstone LTS release) to build a minimal Linux image for QEMU x86-64. A custom layer called meta-sensorhub contains a BitBake recipe that fetches the sensorhub C++ client source from GitHub, builds it with CMake, and installs the binary into the image. A systemd service file ensures the client starts automatically on boot and restarts on failure.

## Build results

The initial QEMU x86-64 image built successfully with Yocto kirkstone (4.0.35). The image boots to a login prompt, runs Linux 5.15.201, and supports networking out of the box. The custom meta-sensorhub layer is ready to be added to the build configuration to include the sensor client binary.

## What the layer contains

The meta-sensorhub layer has a BitBake recipe that handles the full lifecycle: fetching source from the sensorhub GitHub repo, building with CMake against the cross-compilation toolchain, installing the binary to /usr/bin, and registering a systemd service. The service is configured to connect to a backend at a configurable URL and automatically restart if the client crashes.

## Building

Clone poky and set up the environment:

    git clone git://git.yoctoproject.org/poky -b kirkstone
    source poky/oe-init-build-env build

Add the custom layer:

    bitbake-layers add-layer ../meta-sensorhub

Build the image:

    bitbake core-image-minimal

Boot in QEMU:

    runqemu qemux86-64

## Stack

- Yocto Project (kirkstone LTS)
- BitBake build system
- QEMU x86-64 for emulation
- systemd for service management
- CMake for cross-compiling the C++ client

## Companion project

The C++ client source lives at https://github.com/ssukumar2/sensorhub in the client-cpp directory.

## License

MIT
