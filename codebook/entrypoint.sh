#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar codebook-0.0.1-SNAPSHOT.jar