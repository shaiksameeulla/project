clear
echo Automated Process
cd /Source/Udaan_Main_Trunk
svn --username=kgajare --password=wine@28071986* update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *
echo  Will start SVN update before that let me take some rest!!!!!

sleep 30

echo  ahhh.....good morning ...!!! am ready to build

# copying resources of admin
cp /Source/Build_Config/admin/jdbc.properties /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/WEB-INF/
cp /Source/Build_Config/admin/log4j.xml /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/resources/

cp -rf /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/login/* /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/login/
cp -rf /Source/Udaan_Main_Trunk/udaan-rate-calculation/src/main/resources/META-INF/resources/rate/* /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/rate/

# copying resources of central

mvn -o clean compile package install

echo  Will start taking back up!!!!

mytimestamp=UdaanConfig-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp

mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Main_Trunk/udaan-config-admin/target/udaan-config-admin.war  /Source/Build_Backup/$mytimestamp/udaan-config-admin.war  

mkdir  /Source/Deployed_Build_Backup/$mytimestamp

echo  Starting Deployed Back up for -- udaan config admin 
echo  Stopping ....config admin
/opt/apache-tomcat-7.0.30_8280_ConfigAdmin/bin/shutdown.sh
echo  Stopped ....config admin	

sleep 10
echo  taking deployed build backup to dir ..../Source/Deployed_Build_Backup/$mytimestamp/
cp /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/webapps/udaan-config-admin.war  /Source/Deployed_Build_Backup/$mytimestamp/udaan-config-admin.war
rm -dfr /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/work
rm -dfr /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/webapps/udaan-config-admin.war
rm -dfr /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/webapps/udaan-config-admin

mkdir  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_config_admin
cp /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/logs/*.*  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_config_admin/
rm -dfr /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/logs/*.*

echo  deploying new build to  ..../opt/apache-tomcat-7.0.30_8280_ConfigAdmin/webapps
cp /Source/Udaan_Main_Trunk/udaan-config-admin/target/udaan-config-admin.war  /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/webapps

echo    Starting server-- udaan config admin 
cd /opt/apache-tomcat-7.0.30_8280_ConfigAdmin/bin    
sh startup.sh &
sleep 30

echo Complete
