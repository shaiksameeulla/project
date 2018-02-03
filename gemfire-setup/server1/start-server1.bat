rem set CLASSPATH=.;..\..\..\..\projects\cargo-dataservice\build\libs\cargo-dataservice-0.1.jar;%CLASSPATH%
rem set CLASSPATH=.;C:\LOGS\gemfire-routing-index-1.0.1.jar;%CLASSPATH%
cacheserver start mcast-port=0 -server-port=0 -dir=. -J-Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true -J-Dswa.gemfire.dataroot=D:\SWA_PROJECT\Gemfire-setup\gemfire-setup\logs