#!/bin/bash

VERSION="0.0.1-SNAPSHOT"

cp ../../config/* ./flock-master/
cp -R ../../services ./flock-master/
cp ../../target/flock-master-${VERSION}.jar ./flock-master/

tar -pczf flock-master-${VERSION}.tar.gz ./flock-master
