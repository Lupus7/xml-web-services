#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar admin-0.0.1-SNAPSHOT.jar