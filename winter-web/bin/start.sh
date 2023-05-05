#!/usr/bin/env sh
java -jar \
  -Denv=DEV \
  -Dapollo.configService=http://localhost:8080 \
  target/winter-web-0.0.1-SNAPSHOT.jar