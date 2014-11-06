#!/bin/bash

FLOCK_DIR="./"
FLOCK_INIT="${FLOCK_DIR}/flock-master.conf"
FLOCK_CFG="${FLOCK_DIR}/flock-master.yaml"
FLOCK_LOG4J="${FLOCK_DIR}/log4j.xml"
FLOCK_JAR="${FLOCK_DIR}/flock-master-0.0.1-SNAPSHOT.jar"
FLOCK_ETC_DIR="/etc/flock-master/"
FLOCK_INSTALL_DIR="/usr/lib/flock-master/"

cp ${FLOCK_INIT} /etc/init/
mkdir -p ${FLOCK_ETC_DIR}
cp ${FLOCK_CFG} ${FLOCK_ETC_DIR}
cp ${FLOCK_LOG4J} ${FLOCK_ETC_DIR}
mkdir -p ${FLOCK_INSTALL_DIR}
cp ${FLOCK_JAR} ${FLOCK_INSTALL_DIR}
