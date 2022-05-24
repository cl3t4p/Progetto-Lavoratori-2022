#!/bin/bash
# a shorthand for compiling java files linked to java_fx module

#java and java_fx paths

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

fx=$SCRIPT_DIR/assets/javafx-sdk/lib
java=$SCRIPT_DIR/lib/jdk
jar=$SCRIPT_DIR/assets/jars/*
just_fx=0 #0 false 1 true

if [ $just_fx = 0 ]
then
		export JAVA_HOME=$java
		export PATH=$JAVA_HOME/bin:$PATH
fi

echo $1
export JAVA_JARS=$jar
export JAVAFX_LIB=$fx

javac -cp .:$JAVA_JARS --module-path $JAVAFX_LIB --add-modules javafx.controls,javafx.fxml -Xlint:unchecked $1