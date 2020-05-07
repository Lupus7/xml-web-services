#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar carRent-0.0.1-SNAPSHOT.jar