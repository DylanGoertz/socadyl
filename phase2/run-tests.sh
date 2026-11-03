#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/.."
mkdir -p phase2/out
javac -Xlint:all -d phase2/out phase2/src/model/*.java phase2/src/controller/*.java phase2/test/CoreTest.java
java -cp phase2/out CoreTest
