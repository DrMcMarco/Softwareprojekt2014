@echo off
set DERBY_HOME=%CD%\db
call %CD%\db\bin\setNetworkServerCP.bat
call %CD%\db\bin\ij.bat data.txt