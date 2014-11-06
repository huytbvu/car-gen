#!/bin/bash

FLOCK_HOME="$HOME/git/flock-master"
FLOCK_JAR="$FLOCK_HOME/target/flock-master-0.0.1-SNAPSHOT.jar"
FLOCK_CFG="$FLOCK_HOME/config/flock-master.yaml"
LOG4J_CFG="$FLOCK_HOME/config/log4j.xml"

java -Dlog4j.configuration=$LOG4J_CFG -jar $FLOCK_JAR $FLOCK_CFG
