echo %JAVA_HOME%
echo off
call setenv_1.8.0_73
set CLASSPATH=%CLASSPATH%;.\deploy\jbones_estimator.jar;.\deploy\jbones_estimator-config.jar
echo using classpath ...
echo %CLASSPATH%

"%JAVA_HOME%\bin\java" -classpath %CLASSPATH% org.jbones.estimator.Main

pause
