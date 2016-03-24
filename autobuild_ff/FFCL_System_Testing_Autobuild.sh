clear
echo Automated Process
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cd /Source/Udaan_Main_Trunk
svn --username=mohammal --password=Pattw0rd@12341 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *
echo  Will start SVN update before that let me take some rest!!!!!
sleep 1
cp -rf /Source/Build_Config/systest/admin/jdbc.properties /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/systest/admin/log4j.xml /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/resources/

cp -rf /Source/Build_Config/systest/web/jdbc.properties /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/systest/web/log4j.xml /Source/Udaan_Main_Trunk/udaan-web/src/main/resources/

cp -rf /Source/Build_Config/systest/central_server/jdbc.properties /Source/Udaan_Main_Trunk/udaan-central-server/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/systest/central_server/log4j.xml /Source/Udaan_Main_Trunk/udaan-central-server/src/main/resources/

cp -rf /Source/Build_Config/systest/cg/sap_Integration/jdbc.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/systest/cg/sap_Integration/log4j.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/sap_Integration/cxf-service-beans.xml /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/webapp/WEB-INF/cxf/cxf-service-beans.xml
cp -rf /Source/Build_Config/systest/cg/sap_Integration/csd-sap-config.properties /Source/Udaan_Main_Trunk/udaan-sap-integration/src/main/resources/csd-sap-config.properties

cp -rf /Source/Build_Config/report/jdbc.properties /Source/Udaan_Main_Trunk/udaan-report/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/report/context.xml /Source/Udaan_Main_Trunk/udaan-report/src/main/webapp/META-INF/

cp -rf /Source/Build_Config/systest/cg/Bcun.properties /Source/Udaan_Main_Trunk/bcun-data-sync/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/ftp.properties /Source/Udaan_Main_Trunk/bcun-data-sync/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/email.properties /Source/Udaan_Main_Trunk/udaan-universal-routine/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/email.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/messagequeue.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/messagequeuelistener.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/queueconfiguration.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/systest/cg/splitmodel.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources/

cp -rf /Source/Udaan_Main_Trunk/udaan-web/src/main/webapp/login /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/
cp -rf /Source/Udaan_Main_Trunk/udaan-rate-calculation/src/main/resources/META-INF/resources/rate/* /Source/Udaan_Main_Trunk/udaan-config-admin/src/main/webapp/rate/

echo  Go and have a hot sip ....... Slow Build Process in progress come back after 5 mins
mvn clean compile package install -DdryRun=true

cd /Source/Current_Build
rm -f *.jar
rm -f *.war

mytimestamp=FF-SYS-$(date +%Y%m%d-%H%M)
mkdir  /Source/Current_Build/$mytimestamp

echo assembling all the deployables in Current_Build folder....
cp -rf /Source/Udaan_Main_Trunk/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/
cp -rf /Source/Udaan_Main_Trunk/udaan-config-admin/target/udaan-config-admin.war /Source/Current_Build/$mytimestamp/
cp -rf /Source/Udaan_Main_Trunk/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/
cp -rf /Source/Udaan_Main_Trunk/udaan-report/target/udaan-report.war /Source/Current_Build/$mytimestamp/
cp -rf /Source/Udaan_Main_Trunk/udaan-sap-integration/target/udaan-sap-integration.war /Source/Current_Build/$mytimestamp/udaan-sap-integration.war

cp -rf /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/target/bcun-asynch-queue-consumer-1.0.jar /Source/Current_Build/$mytimestamp/
cp -rf /Source/Udaan_Main_Trunk/bcun-data-sync/target/bcun-data-sync.jar /Source/Current_Build/$mytimestamp/bcun-data-sync-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-framework/target/udaan-framework.jar /Source/Current_Build/$mytimestamp/udaan-framework-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-domain/target/udaan-domain-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-domain-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-transfer-object/target/udaan-transfer-object-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-transfer-object-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-notification-service/target/udaan-notification-service-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-notification-service-1.0-SNAPSHOT.jar



######################
#echo Building Asynch Queue

#cd /Source/Udaan_Main_Trunk/udaan-asynch-queue-consumer/mavenscriptForear
#mvn  clean compile package DdryRun=true

