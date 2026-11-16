$ErrorActionPreference = 'Stop'
Set-Location (Join-Path $PSScriptRoot '..')
New-Item -ItemType Directory -Force phase2/out | Out-Null
$files = @(Get-ChildItem phase2/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 -Xlint:all -d phase2/out $files
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
& java -cp phase2/out gui.ChessApplication
if ($LASTEXITCODE -ne 0) { throw 'Application failed' }
