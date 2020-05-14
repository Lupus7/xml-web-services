#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar api-gateway-0.0.1-SNAPSHOT.jar