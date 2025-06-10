#!/usr/bin/env bash
docker build -t danilchet/license-ms .
docker run -p 8080:8080 --name stock_test_container danilchet/license-ms