set APP_LIB=.\lib
set DEPENDENCY_LIB_CORE=C:\DATA\jbones\jbones_core\deploy
set DEPENDENCY_LIB_CORE_DATASTORE=C:\DATA\jbones\jbones_datastore\deploy
set DEPENDENCY_LIB_CORE_STRIPES=C:\DATA\jbones\jbones_stripes\deploy

copy %DEPENDENCY_LIB_CORE%\jbones_core.jar %APP_LIB%\jbones_core.jar
copy %DEPENDENCY_LIB_CORE_DATASTORE%\jbones_datastore.jar %APP_LIB%\jbones_datastore.jar
copy %DEPENDENCY_LIB_CORE_STRIPES%\jbones_stripes.jar %APP_LIB%\jbones_stripes.jar

echo [JBONES_DEPENDENCIES_UPDATED] 


