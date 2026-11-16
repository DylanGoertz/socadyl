$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
New-Item -ItemType Directory -Force phase2/out | Out-Null
$files = @(Get-ChildItem phase2/src, phase2/test -Recurse -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -Xlint:all -d phase2/out $files
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
& java -cp phase2/out CoreTest
if ($LASTEXITCODE -ne 0) { throw 'Tests failed' }
& java -Djava.awt.headless=true -cp phase2/out IntegrationTest
if ($LASTEXITCODE -ne 0) { throw 'Integration tests failed' }
