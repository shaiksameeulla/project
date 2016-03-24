clear
echo Automated Process for Production Build
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cd /Source/Udaan_Production_Post_Warranty
#svn update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Production_Post_Warranty/ *
echo  SVN update completed now
echo  copying Production specific properties/resources/log4j 


cp -rf /Source/Build_Config/production/admin/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-config-admin/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/production/admin/log4j.xml /Source/Udaan_Production_Post_Warranty/udaan-config-admin/src/main/resources/

cp -rf /Source/Build_Config/production/web/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-web/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/production/web/log4j.xml /Source/Udaan_Production_Post_Warranty/udaan-web/src/main/resources/

cp -rf /Source/Build_Config/production/sap_Integration/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-sap-integration/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/production/sap_Integration/log4j.xml /Source/Udaan_Production_Post_Warranty/udaan-sap-integration/src/main/resources/
cp -rf /Source/Build_Config/production/sap_Integration/cxf-service-beans.xml /Source/Udaan_Production_Post_Warranty/udaan-sap-integration/src/main/webapp/WEB-INF/cxf/cxf-service-beans.xml
cp -rf /Source/Build_Config/production/sap_Integration/csd-sap-config.properties /Source/Udaan_Production_Post_Warranty/udaan-sap-integration/src/main/resources/csd-sap-config.properties

cp -rf /Source/Build_Config/production/central_server/blob/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/production/central_server/log4j.xml /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/resources/

cp -rf /Source/Build_Config/production/Queue/jdbc.properties /Source/Udaan_Production_Post_Warranty/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp -rf /Source/Build_Config/production/Queue/log4j.xml /Source/Udaan_Production_Post_Warranty/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp -rf /Source/Build_Config/production/Queue/jndi.properties /Source/Udaan_Production_Post_Warranty/bcun-asynch-queue-consumer/ejbs/src/main/resources

cp -rf /Source/Build_Config/production/Queue/messagequeuelistener.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/queueconfiguration.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/splitmodel.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/messagequeue.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/email.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources

cp -rf /Source/Build_Config/production/central_server/blob/bcun-central-schedulers.xml /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
cp -rf /Source/Build_Config/production/Bcun.properties /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
cp -rf /Source/Build_Config/production/ftp.properties /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
cp -rf /Source/Build_Config/production/email.properties /Source/Udaan_Production_Post_Warranty/udaan-universal-routine/src/main/resources/

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/src/main/webapp/login /Source/Udaan_Production_Post_Warranty/udaan-config-admin/src/main/webapp/
cp -rf /Source/Build_Config/production/report/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-report/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/production/report/context.xml /Source/Udaan_Production_Post_Warranty/udaan-report/src/main/webapp/META-INF/
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-rate-calculation/src/main/resources/META-INF/resources/rate/* /Source/Udaan_Production_Post_Warranty/udaan-config-admin/src/main/webapp/rate/
#cp -rf /Source/Build_Config/production/report/log4j.xml  /Source/Udaan_Production_Post_Warranty/udaan-report/src/main/webapp/WEB-INF/

echo  copied Production specific properties/resources/log4j 

echo  ahhh..... am ready to build UDAAN NOW
#mvn  clean compile package -DdryRun=true
mvn  clean compile package install -DdryRun=true

echo  ahhh..... Build has been taken now, see the console for other details

mytimestamp=PROD-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp
mkdir  /Source/Current_Build/$mytimestamp

echo assembling all the deployables in Current_Build folder....
#cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-config-admin/target/udaan-config-admin.war /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_blob
cp -rf /Source/Udaan_Production_Post_Warranty/bcun-asynch-queue-consumer/ejbs/target/bcun-asynch-queue-consumer-1.0.jar /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Production_Post_Warranty/bcun-data-sync/target/bcun-data-sync.jar /Source/Current_Build/$mytimestamp/bcun-data-sync-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-framework/target/udaan-framework.jar /Source/Current_Build/$mytimestamp/udaan-framework-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-domain/target/udaan-domain-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-domain-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-transfer-object/target/udaan-transfer-object-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-transfer-object-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-report/target/udaan-report.war /Source/Current_Build/$mytimestamp/udaan-report.war
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-sap-integration/target/udaan-sap-integration.war /Source/Current_Build/$mytimestamp/udaan-sap-integration.war
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-notification-service/target/udaan-notification-service-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-notification-service-1.0-SNAPSHOT.jar

cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/central_server/bcun-central-schedulers.xml /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-framework
cp -rf /Source/Build_Config/production/Queue/messagequeuelistener.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/queueconfiguration.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/splitmodel.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/Queue/messagequeue.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cp -rf /Source/Build_Config/production/email.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
mvn package install -DdryRun=true

echo  preparing udaan-central-server war file for Blob download

cd /Source/Udaan_Production_Post_Warranty/udaan-central-server
cp -rf /Source/Build_Config/production/central_server/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war

echo  preparing udaan-central-server war file for manual download

cd /Source/Udaan_Production_Post_Warranty/udaan-central-server
cp -rf /Source/Build_Config/production/central_server/manual_download/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_manual_download

############################## central server Build ### START ################
echo  preparing udaan-central-server war file for 144 server with port 8075 with DB user CS2
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/central_server/bcun_central_schedulers_commented/bcun-central-schedulers.xml /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-central-server
cp -rf /Source/Build_Config/production/central_server/CS2_8075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_144_8075_CS2
#######
echo  preparing udaan-central-server war file for 143 server with port 8075 with DB user CS3

cp -rf /Source/Build_Config/production/central_server/CS3_8075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_143_8075_CS3
#######
echo  preparing udaan-central-server war file for 117 server with port 8075 with DB user CS1

cp -rf /Source/Build_Config/production/central_server/CS1_8075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_117_8075_CS1

echo  preparing udaan-central-server war file for 136 server with port 8075 with DB user CS4

cp -rf /Source/Build_Config/production/central_server/CS4_8075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/

mvn  package -DdryRun=true

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_136_8075_CS4
##############for 9075 central server START################

echo  preparing udaan-central-server war file for 144 server with port 9075 with DB user CS2 start

cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/central_server/bcun-central-schedulers.xml /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true
cd /Source/Udaan_Production_Post_Warranty/udaan-central-server
cp -rf /Source/Build_Config/production/central_server/CS2_9075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_144_9075_CS2

echo  preparing udaan-central-server war file for 144 server with port 9075 with DB user CS3 end

echo  preparing udaan-central-server war file for 143 server with port 9075 with DB user CS3 start

cp -rf /Source/Build_Config/production/central_server/CS3_9075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_143_9075_CS3

echo  preparing udaan-central-server war file for 143 server with port 9075 with DB user CS3 end


echo  preparing udaan-central-server war file for 117 server with port 9075 with DB user CS1 start

cp -rf /Source/Build_Config/production/central_server/CS1_9075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_117_9075_CS1

echo  preparing udaan-central-server war file for 117 server with port 9075 with DB user CS1 END

echo  preparing udaan-central-server war file for 136 server with port 9075 with DB user CS4 start

cp -rf /Source/Build_Config/production/central_server/CS4_9075/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-central-server/src/main/webapp/WEB-INF/
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp/udaan-central-server.war_136_9075_CS4

echo  preparing udaan-central-server war file for 136 server with port 9075 with DB user CS4 END
##############for 9075 central server END  ################

############################## central server Build ### END ################
echo  ahhh..... Build War/jar files moved to /Source/Current_Build/$mytimestamp location

##### Preparing build for udaan web project for five diffrent category #########
mkdir  /Source/Current_Build/$mytimestamp/CATA
mkdir  /Source/Current_Build/$mytimestamp/CATB
mkdir  /Source/Current_Build/$mytimestamp/CATC
mkdir  /Source/Current_Build/$mytimestamp/CATD
mkdir  /Source/Current_Build/$mytimestamp/CATE
################### common framework changes starts from here for the udaan-web #########################################

echo ##***************************************************************** START ##############################
echo ################################################ copying email properties which are specific to udaan-web #################################
rm -f -v /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/email.properties
cp -rf /Source/Build_Config/production/web/email.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources
cd /Source/Udaan_Production_Post_Warranty/udaan-framework
mvn package install -DdryRun=true

echo ##***************************************************************** END ##############################

################### common framework changes ENDS  here for the udaan-web #########################################

##### CATEGORY A #######
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/CATA/* /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-web
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/CATA

##### CATEGORY B #######
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/CATB/* /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-web
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/CATB

##### CATEGORY C #######
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/CATC/* /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-web
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/CATC

##### CATEGORY D #######
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/CATD/* /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-web
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/CATD

##### CATEGORY E #######
cd /Source/Udaan_Production_Post_Warranty/bcun-data-sync
cp -rf /Source/Build_Config/production/CATE/* /Source/Udaan_Production_Post_Warranty/bcun-data-sync/src/main/resources/
mvn package install -DdryRun=true

cd /Source/Udaan_Production_Post_Warranty/udaan-web
mvn  package -DdryRun=true
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.war /Source/Current_Build/$mytimestamp/CATE