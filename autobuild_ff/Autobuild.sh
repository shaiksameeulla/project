clear
echo Automated Process
cd /Source/Udaan_Main_Trunk
echo Will start SVN update before that let me take some rest!!!!!
sleep 10
svn --username=mohammal --password=Pattw0rd@1234 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *

sleep 30

echo  ahhh.....i have taken the updated code...!!! am ready to build now

cp /Source/Build_Config/web/jdbc.properties /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/WEB-INF/
cp /Source/Build_Config/log4j.xml /Source/Udaan_Main_Trunk/udaan-web/src/main/resources/

mvn -o clean compile package install

echo  Will start taking back up!!!!

mytimestamp=$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp

mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Main_Trunk/udaan-web/target/udaan-web.war  /Source/Build_Backup/$mytimestamp/udaan-web.war  

echo  Starting Deployed Back up for --- udaan web
echo  Stopping ....udaan web
/opt/apache-tomcat-7.0.30_8088/bin/shutdown.sh
echo  Stopped ....udaan web
sleep 10

mkdir  /Source/Deployed_Build_Backup/$mytimestamp
cp /opt/apache-tomcat-7.0.30_8088/webapps/udaan-web.war  /Source/Deployed_Build_Backup/$mytimestamp/udaan-web.war

rm -dfr /opt/apache-tomcat-7.0.30_8088/work
rm -dfr /opt/apache-tomcat-7.0.30_8088/webapps/udaan-web.war
rm -dfr /opt/apache-tomcat-7.0.30_8088/webapps/udaan-web

mkdir  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_web
cp /opt/apache-tomcat-7.0.30_8088/logs/*.*  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_web/
rm -dfr /opt/apache-tomcat-7.0.30_8088/logs/*.*
cp /Source/Udaan_Main_Trunk/udaan-web/target/udaan-web.war /opt/apache-tomcat-7.0.30_8088/webapps/

echo   Starting ....udaan web server		
/opt/apache-tomcat-7.0.30_8088/bin/startup.sh
sleep 60

echo Build Complete!
