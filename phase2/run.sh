#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/.."
mkdir -p phase2/out
find phase2/src -type f -name '*.java' -print | sed 's/.*/"&"/' > phase2/out/app-sources.txt
javac -encoding UTF-8 -Xlint:all -d phase2/out @phase2/out/app-sources.txt
java -cp phase2/out gui.ChessApplication
