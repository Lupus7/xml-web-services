#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar authentication-app-0.0.1-SNAPSHOT.jar