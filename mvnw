#!/bin/sh 
MAVEN_PROJECTBASEDIR=%%~dp0 
MAVEN_HOME=%%~dp0.mvn/wrapper 
exec "$MAVEN_HOME/mvn" "$@" 
