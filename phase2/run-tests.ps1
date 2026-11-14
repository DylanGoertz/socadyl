$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
New-Item -ItemType Directory -Force phase2/out | Out-Null
$files = @(Get-ChildItem phase2/src/model, phase2/src/controller, phase2/test -Filter *.java | ForEach-Object { $_.FullName })
& javac -Xlint:all -d phase2/out $files
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
& java -cp phase2/out CoreTest
if ($LASTEXITCODE -ne 0) { throw 'Tests failed' }
