#!/usr/bin/expect -f

#define password
export pass = "upsmart"

spawn ssh upsmart@192.168.88.110

# detect password prompt
expect "*?assword:*"

# send $password
send -- "$pass\r"

#return
send -- "\r"
expect eof
