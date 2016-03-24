clear
echo Automated Process
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cd /Source/Udaan_Main_Trunk
svn update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *
echo  Will start SVN update before that let me take some rest!!!!!

cp /Source/Build_Config/uat/admin/jdbc.properties /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/admin/log4j.xml /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/resources/

cp /Source/Build_Config/uat/web/jdbc.properties /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/web/log4j.xml /Source/Udaan_Main_Trunk/udaan-web/src/main/resources/

cp /Source/Build_Config/uat/sap_Integration/jdbc.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/sap_Integration/log4j.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/
cp /Source/Build_Config/uat/sap_Integration/cxf-service-beans.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/cxf/cxf-service-beans.xml
cp /Source/Build_Config/uat/sap_Integration/csd-sap-config.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/csd-sap-config.properties

cp /Source/Build_Config/uat/central_server/jdbc.properties /Source/Udaan_Main_Trunk/udaan-central-server/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/central_server/log4j.xml /Source/Udaan_Main_Trunk/udaan-central-server/src/main/resources/

cp /Source/Build_Config/uat/Queue/jdbc.properties /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp /Source/Build_Config/uat/Queue/log4j.xml /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp /Source/Build_Config/uat/Queue/jndi.properties /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources

cp /Source/Build_Config/uat/Queue/messagequeuelistener.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/uat/Queue/queueconfiguration.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/uat/Queue/splitmodel.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/uat/Queue/messagequeue.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/uat/email.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources

cp /Source/Build_Config/uat/Bcun.properties /Source/Udaan_Main_Trunk/bcun-data-sync/src/main/resources/
cp /Source/Build_Config/uat/ftp.properties /Source/Udaan_Main_Trunk/bcun-data-sync/src/main/resources/
cp /Source/Build_Config/uat/email.properties /Source/Udaan_Main_Trunk/udaan-universal-routine/src/main/resources/

cp -rf /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/login /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/
cp /Source/Build_Config/uat/report/jdbc.properties /Source/Udaan_Main_Trunk/udaan-report/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/report/context.xml /Source/Udaan_Main_Trunk/udaan-report/src/main/webapp/META-INF/
cp /Source/Build_Config/uat/report/log4j.xml  /Source/Udaan_Main_Trunk/udaan-report/src/main/webapp/WEB-INF/


echo  ahhh.....good morning ...!!! am ready to build
mvn  clean compile package -DdryRun=true


mytimestamp=UAT-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp
mkdir  /Source/Current_Build/$mytimestamp

echo assembling all the deployables in Current_Build folder....
cp -rf /Source/Udaan_Main_Trunk/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Main_Trunk/udaan-config-admin/target/udaan-config-admin.war /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Main_Trunk/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/target/bcun-asynch-queue-consumer-1.0.jar /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Main_Trunk/bcun-data-sync/target/bcun-data-sync.jar /Source/Current_Build/$mytimestamp/bcun-data-sync-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-framework/target/udaan-framework.jar /Source/Current_Build/$mytimestamp/udaan-framework-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-domain/target/udaan-domain-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-domain-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-transfer-object/target/udaan-transfer-object-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-transfer-object-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-report/target/udaan-report.war /Source/Current_Build/$mytimestamp/udaan-report.war
cp -rf /Source/Udaan_Main_Trunk/udaan-sap-integration/target/udaan-sap-integration.war /Source/Current_Build/$mytimestamp/udaan-sap-integration.war





