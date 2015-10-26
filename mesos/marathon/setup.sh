#!/bin/sh

## marathon setup script for osx.
MARATHON_HOME=/usr/local/klzhong/tools/marathon-0.10.0

cd $MARATHON_HOME

nohup bin/start --master zk://localhost:2181/mesos --zk zk://localhost:2181/marathon &

