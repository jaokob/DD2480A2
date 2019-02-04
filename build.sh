#!/bin/bash

javac $1/src/*.java
if [ -f $1/src/*.class ]; then
    rm $1/src/*.class
fi

