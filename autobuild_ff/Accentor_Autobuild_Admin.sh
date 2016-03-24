clear
echo Automated Process
cd /Source/Udaan_Accentor_Workspace
svn --username=mohammal --password=Pattw0rdAK123 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Accentor_Workspace/ *
echo  Will start SVN update before that let me take some rest!!!!!

sleep 30

echo  ahhh.....good morning ...!!! am ready to build

cp /Source/Build_Config/admin/jdbc.properties /Source/Udaan_Accentor_Workspace/udaan-config-admin/src/main/webapp/WEB-INF/
cp /Source/Build_Config/admin/log4j.xml /Source/Udaan_Accentor_Workspace/udaan-config-admin/src/main/resources/

#cp /Source/Udaan_Accentor_Workspace/udaan-web/src/main/webapp/login/*.* /Source/Udaan_Accentor_Workspace/udaan-config-admin/src/main/webapp/login/

mvn -o clean compile package install

echo  Will start taking back up!!!!

mytimestamp=$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp

mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Accentor_Workspace/udaan-config-admin/target/udaan-config-admin.war  /Source/Build_Backup/$mytimestamp/udaan-config-admin.war  

mkdir  /Source/Deployed_Build_Backup/$mytimestamp

echo  Starting Deployed Back up for -- udaan config admin 
echo  Stopping ....config admin
/opt/apache-tomcat-7.0.30_8089/bin/shutdown.sh
echo  Stopped ....config admin	

sleep 10

cp /opt/apache-tomcat-7.0.30_8089/webapps/udaan-config-admin.war  /Source/Deployed_Build_Backup/$mytimestamp/udaan-config-admin.war
rm -dfr /opt/apache-tomcat-7.0.30_8089/work
rm -dfr /opt/apache-tomcat-7.0.30_8089/webapps/udaan-config-admin.war
rm -dfr /opt/apache-tomcat-7.0.30_8089/webapps/udaan-config-admin

mkdir  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_config_admin
cp /opt/apache-tomcat-7.0.30_8089/logs/*.*  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan_config_admin/
rm -dfr /opt/apache-tomcat-7.0.30_8089/logs/*.*
cp /Source/Udaan_Accentor_Workspace/udaan-config-admin/target/udaan-config-admin.war  /opt/apache-tomcat-7.0.30_8089/webapps

echo    Starting server-- udaan config admin     
/opt/apache-tomcat-7.0.30_8089/bin/startup.sh
sleep 30

echo Complete
