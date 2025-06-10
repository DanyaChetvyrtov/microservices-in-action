#!/usr/bin/env bash

# Build config-server image
echo "Building config-server image..."
cd config-server || { echo "Error entering config-server directory"; exit 1; }
docker build -t danilchet/config-server-ms . || { echo "Error building config-server image"; exit 1; }
cd ..
echo "Successfully built config-server image"

# Build license-ms image
echo "Building license-ms image..."
cd license-ms || { echo "Error entering license-ms directory"; exit 1; }
docker build -t danilchet/license-ms . || { echo "Error building license-ms image"; exit 1; }
cd ..
echo "Successfully built license-ms image"

echo "All images built successfully"
