#!/bin/bash

mvn package -Ptar-pkg -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
