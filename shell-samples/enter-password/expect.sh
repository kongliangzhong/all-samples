#!/usr/bin/expect

set timeout 30
spawn ssh -l upsmart 192.168.88.110
expect "password:"
send "upsmart\r"
interact 
