#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar community-0.0.1-SNAPSHOT.jar