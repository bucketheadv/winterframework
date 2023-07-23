#!/usr/bin/env sh
java \
  -Xlog:gc:./gc.log -XX:+PrintGCDetails \
  -Xms128m -Xmx1G -Xss128m -Xmn128m -XX:MetaspaceSize=256M \
  -jar target/winter-admin-web-0.0.1-SNAPSHOT.jar