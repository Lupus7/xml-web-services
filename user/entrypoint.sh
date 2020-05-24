#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar user-0.0.1-SNAPSHOT.jar