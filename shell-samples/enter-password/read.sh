#!/bin/bash
# Read Password
echo -n Password:
read -s password
echo
# Run Command
echo $password

read -s -p "Password: " password
echo $password
