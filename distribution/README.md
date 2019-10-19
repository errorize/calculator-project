# calculator

The project named calculator is designed to convert digits to letters based on the self-defined mapping. It's conditions are as follows: 

1、JDK 1.6 or later.

2、Both Windows and Linux is OK.

3、4G or higher memory.

# How to run this project

1、install JDK as well as Maven.

2、mvn clean package -DskipTests.
Or you can double click the file r.cmd.

3、these files in need are as follows:
    calculator.jar: the compiled program running on JDK.
    log4j.properties: the file contains configurations of initializing log4j.
    startApplication.cmd: start a window. By the way, you can test the program with an UI.
    startCommand.cmd: As is known to us, command line contains parameters.
    system.properties: the file contains mapping between digits and letters and other parameters.

4、if you get the jar, you can run it using the ways:
    UI:
        file: startApplication.cmd
        script: java -Xms1024m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=1536m -XX:MaxNewSize=1536m -jar calculator.jar --ui
        description: the parameter --ui tells that program shows a window.
    No UI:
        file: startCommand.cmd
        script: java -Xms1024m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=1536m -XX:MaxNewSize=1536m -jar calculator.jar --systemProperties "E:\system.properties" --outputPath "C:\calc-result.txt" --digits "2 3 4 5 6 7 8"
        description: 
            --systemProperties indicates where the mapping file is and format like this: "E:\system.properties"  
            --outputPath indicates where to output the result and format like this: "C:\calc-result.txt"
            --digits indicates that input your digits and format like this: "2 3 4 5 6 7 8"
            
5、
Although I have tried my best to avoid bugs, I still look forward to your feedback if you find bugs.
And my email is 1024639401@qq.com. Thank you all.




