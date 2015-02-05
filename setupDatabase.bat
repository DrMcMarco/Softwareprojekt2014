@echo off
set DERBY_HOME=%CD%\db
call "%CD%"\db\bin\setNetworkServerCP.bat
@echo "---Starte Datenbankserver---"
@echo "---Erstelle Datenbank---"
start /B CMD /C call "%CD%"\db\bin\startNetworkServer.bat -noSecurityManager
call "%CD%"\db\bin\ij.bat commands.txt
@echo "---Erfolg---"
@echo "---Stoppe Datenbankserver"
call "%CD%"\db\bin\stopNetworkServer.bat