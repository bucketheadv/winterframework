#!/usr/bin/env sh
java -jar -Dserver.port=8081 -Dtio.server.port=9092 target/winter-starter-tio-0.0.1-SNAPSHOT.jar
