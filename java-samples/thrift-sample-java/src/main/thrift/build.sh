#!/bin/sh

rm -rf gen-java
## build thrift files to java files using thrift:
find . -name "*.thrift" -type f -exec thrift --gen java {} \;
