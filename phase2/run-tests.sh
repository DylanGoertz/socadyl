#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/.."
mkdir -p phase2/out
find phase2/src phase2/test -type f -name '*.java' -print | sed 's/.*/"&"/' > phase2/out/sources.txt
javac -encoding UTF-8 -Xlint:all -d phase2/out @phase2/out/sources.txt
java -cp phase2/out CoreTest
java -Djava.awt.headless=true -cp phase2/out IntegrationTest
