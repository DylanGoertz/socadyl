$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
New-Item -ItemType Directory -Force phase3/out | Out-Null
$files = @(Get-ChildItem phase3/src, phase3/test -Recurse -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -Xlint:all -d phase3/out $files
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
& java -cp phase3/out RulesTest
if ($LASTEXITCODE -ne 0) { throw 'Tests failed' }
