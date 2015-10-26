#!/bin/sh

## chronos setup script for osx.

CHRONOS_HOME=/usr/local/klzhong/tools/chronos-2.4.0
export MESOS_NATIVE_LIBRARY=/usr/local/lib/libmesos.dylib

cd $CHRONOS_HOME
## mvn package
nohup java -cp target/chronos*.jar org.apache.mesos.chronos.scheduler.Main --master zk://localhost:2181/mesos --zk_hosts localhost:2181 &
