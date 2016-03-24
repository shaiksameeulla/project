clear
echo Automated Process
cd /Source/Udaan_Main_Trunk
echo "Let me take some rest before taking update..."
sleep 10
svn --username=kgajare --password=wine@28071986* update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *

sleep 30

echo  ahhh.....good morning ...!!! am ready to build

# copying resources of central

mvn -o clean compile package install

echo  Will start taking back up!!!!

mytimestamp=UdaanCentral-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp

mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Main_Trunk/udaan-central-server/target/udaan-central-server.war  /Source/Build_Backup/$mytimestamp/udaan-central-server.war

echo  Starting Deployed Back up for --- udaan central server
echo  Stopping ....udaan central server
/opt/apache-tomcat-7.0.30_8180_CentralServer/bin/shutdown.sh
echo  Stopped ....udaan central server
sleep 10

mkdir  /Source/Deployed_Build_Backup/$mytimestamp

cp /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/webapps/udaan-central-server.war  /Source/Deployed_Build_Backup/$mytimestamp/udaan-central-server.war
rm -dfr /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/work
rm -dfr /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/webapps/udaan-central-server.war
rm -dfr /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/webapps/udaan-central-server

mkdir  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_central_server
cp /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/logs/*.*  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_central_server/
rm -dfr /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/logs/*.*
cp /Source/Udaan_Main_Trunk/udaan-central-server/target/udaan-central-server.war /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/webapps/

echo   Starting server-- udaan central server		
cd /opt/apache-tomcat-7.0.30_8180_CentralServer_Sys/bin/
./startup.sh &

sleep 30

echo Complete
