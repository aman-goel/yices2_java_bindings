#!/bin/bash

#uses the build.sh to mimic what 'ant install' does without 'ant'
REPO_ROOT=${PWD}

YC=${REPO_ROOT}/build/classes
YI=${REPO_ROOT}/dist/lib

YICES_CLASSPATH=${YC} YICES_JNI=${YI} ./build.sh

cd ${YC}
jar -cvfm ${YI}/yices.jar ${REPO_ROOT}/MANIFEST.txt com/sri/yices/*.class

cd ${YI}
java -Djava.library.path=. -jar yices.jar

cd ${REPO_ROOT}
