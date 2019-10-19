
@echo off
set OPTS=-Xms1024m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=1536m -XX:MaxNewSize=1536m
set sysPath=E:\workspace\calculator\system.properties
set outputPath=C:\Users\Administrator\Desktop\tester\calc-result199.txt

java %OPTS% -jar calculator.jar --digits "2 3 4 5 6 7 8" --systemProperties "%sysPath%" --outputPath "%outputPath%"

pause