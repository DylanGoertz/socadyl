#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/.."
mkdir -p phase3/out
find phase3/src -type f -name '*.java' -print | sed 's/.*/"&"/' > phase3/out/app-sources.txt
javac -encoding UTF-8 -Xlint:all -d phase3/out @phase3/out/app-sources.txt
java -cp phase3/out gui.ChessApplication
