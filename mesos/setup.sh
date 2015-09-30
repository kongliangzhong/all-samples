#!/bin/sh

nohup mesos-master  --ip=127.0.0.1 --work_dir=/var/lib/mesos &
sleep 5
nohup mesos-slave --master=127.0.0.1:5050 &
