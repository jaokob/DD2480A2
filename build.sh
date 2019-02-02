#!/bin/bash

javac commits/$1/src/*.java
jar -cvf commits/$1/comp.jar commits/$1/src/*.class
rm commits/$1/src/*.class

#-d commits/$1
