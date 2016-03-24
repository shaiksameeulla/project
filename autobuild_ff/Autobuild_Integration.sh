clear
echo Automated Process
cd /Source/Udaan_Main_Trunk
svn --username=mohammal --password=Pattw0rdAK123 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *
echo  Will start SVN update before that let me take some rest!!!!!

sleep 30

echo  ahhh.....good morning ...!!! am ready to build

mvn -o clean compile package install

echo  Will start taking back up!!!!

mytimestamp=$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp

mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Main_Trunk/udaan-sap-integration/target/udaan-sap-integration.war  /Source/Build_Backup/$mytimestamp/udaan-sap-integration.war

cp /Source/Build_Config/vm3/sap_Integration/jdbc.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/
cp /Source/Build_Config/vm3/sap_Integration/log4j.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/
cp /Source/Build_Config/vm3/sap_Integration/cxf-service-beans.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/cxf/cxf-service-beans.xml
cp /Source/Build_Config/vm3/sap_Integration/csd-sap-config.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/csd-sap-config.properties

echo  Starting Deployed Back up for --- udaan sap integration
echo  Stopping ....udaan sap integration
/opt/apache-tomcat-7.0.30_8081_sap_integration/bin/shutdown.sh
echo  Stopped ....udaan sap integration
sleep 10

mkdir  /Source/Deployed_Build_Backup/$mytimestamp

cp /opt/apache-tomcat-7.0.30_8081_sap_integration/webapps/udaan-sap-integration.war  /Source/Deployed_Build_Backup/$mytimestamp/udaan-sap-integration.war
rm -dfr /opt/apache-tomcat-7.0.30_8081_sap_integration/work
rm -dfr /opt/apache-tomcat-7.0.30_8081_sap_integration/webapps/udaan-sap-integration.war
rm -dfr /opt/apache-tomcat-7.0.30_8081_sap_integration/webapps/udaan-sap-integration

mkdir  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan-sap-integration
cp /opt/apache-tomcat-7.0.30_8081_sap_integration/logs/*.*  /Source/Deployed_Build_Backup/$mytimestamp/logs_udaan-sap-integration/
rm -dfr /opt/apache-tomcat-7.0.30_8081_sap_integration/logs/*.*
cp /Source/Udaan_Main_Trunk/udaan-sap-integration/target/udaan-sap-integration.war /opt/apache-tomcat-7.0.30_8081_sap_integration/webapps/

echo   Starting server-- udaan sap integration		
/opt/apache-tomcat-7.0.30_8081_sap_integration/bin/startup.sh
sleep 30

echo Complete
