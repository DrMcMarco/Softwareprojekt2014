@echo off

set DERBY_HOME=%CD%\db
call "%CD%"\db\bin\setNetworkServerCP.bat
@echo "---Starte Datenbankserver---"
start /B CMD /C call "%CD%"\db\bin\startNetworkServer.bat -noSecurityManager

@echo "---Starte Programm---"
java -javaagent:"%CD%\Libs\openjpa-all-2.2.1.jar" -cp "%CD%\Libs\beansbinding-1.2.1.jar;%CD%\Libs\derby.jar;%CD%\Libs\derbyclient.jar;%CD%\Libs\derbynet.jar;%CD%\Libs\derbyclient.jar;%CD%\Libs\openjpa-all-2.2.1.jar;%CD%\dist\Softwareprojekt2014.jar" JFrames.Start

@echo "---Programm beendet---"
@echo "---Stoppe Datenbankserver---"
call "%CD%"\db\bin\stopNetworkServer.bat
