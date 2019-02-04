#!/bin/bash

javac $1/src/*.java
jar -cvf $1/comp.jar $1/src/*.class
rm $1/src/*.class
