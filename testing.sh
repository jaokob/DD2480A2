#!/bin/bash

if [ ! -d $1/out ]; then
	mkdir $1/out
fi
javac -d $1/out $1/src/Decide.java
javac -d $1/out -cp "lib/junit-platform-console-standalone-1.3.2.jar" $1/test/DecideTest.java $1/src/Decide.java
java -jar "lib/junit-platform-console-standalone-1.3.2.jar" --class-path $1/out --scan-class-path
