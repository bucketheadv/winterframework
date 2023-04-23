#!/usr/bin/env sh
java \
  -Xloggc:./gc.log -XX:+PrintGCDetails \
  -Xms=128m -Xmx=1G -Xss=128m -Xmn=128m -XX:MetaspaceSize=256M \
  -jar target/winter-admin-web-0.0.1-SNAPSHOT.jar