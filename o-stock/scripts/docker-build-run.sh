#!/usr/bin/env bash
docker build -t danilchet/o-stock-ms .
docker run -p 8080:8080 --name stock_test_container danilchet/o-stock-ms