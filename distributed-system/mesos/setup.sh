#!/bin/sh

MESOS_HOME=/usr/local/klzhong/tools/mesos-0.28.1

nohup mesos-master  --ip=127.0.0.1 --work_dir=/var/lib/mesos &
sleep 3
nohup mesos-slave --master=127.0.0.1:5050 --port=5051 --work_dir=/tmp/mesos-slave-01 --resources="cpus(*):1; mem(*):1024; disk(*):10534" &
sleep 3
nohup mesos-slave --master=127.0.0.1:5050 --port=5052 --work_dir=/tmp/mesos-slave-02 --resources="cpus(*):1; mem(*):1024; disk(*):10534" &
