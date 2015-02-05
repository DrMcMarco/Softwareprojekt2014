@echo off
set DERBY_HOME="%CD%\db"
call "%CD%\db\bin\setNetworkServerCP.bat"
start /B CMD /C call "%CD%\db\bin\startNetworkServer.bat" -noSecurityManager
call "%CD%\db\bin\ij.bat" commands.txt
call "%CD%\db\bin\stopNetworkServer.bat"