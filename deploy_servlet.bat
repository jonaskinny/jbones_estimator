set APP_DEPLOY=.\deploy
set SERVLET_DEPLOY=C:\DATA\servers\apache-tomcat-6.0.16_jbones_estimator\webapps

RMDIR /S /Q %SERVLET_DEPLOY%\jbones_estimator

copy %APP_DEPLOY%\jbones_estimator.war %SERVLET_DEPLOY%\jbones_estimator.war

echo [JBONES_SERVLET_DEPLOYED]

pause

