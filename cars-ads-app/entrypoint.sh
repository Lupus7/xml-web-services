#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar cars-ads-app-0.0.1-SNAPSHOT.jar