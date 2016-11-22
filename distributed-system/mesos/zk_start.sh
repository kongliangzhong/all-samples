#!/bin/sh

ZK_HOME=/usr/local/klzhong/tools/zookeeper-3.4.8
cd $ZK_HOME

if [ -f ${ZK_HOME}/conf/zoo.cfg ] ## conf/zoo.cfg as default config file.
then
    echo "use conf/zoo.cfg as config file."
else
    echo "create config file zoo.cfg..."
    cp ${ZK_HOME}/conf/zoo_sample.cfg ${ZK_HOME}/conf/zoo.cfg
fi

bin/zkServer.sh start
