#!/bin/sh

curl -H "Content-Type: application/json" -X POST -d '{"id": "basic-0","cmd": "while [ true ] ; do echo \"Hello Marathon\" ; sleep 5 ; done","cpus": 0.1,"mem": 10.0,"instances": 1}' http://127.0.0.1:8080/v2/apps







