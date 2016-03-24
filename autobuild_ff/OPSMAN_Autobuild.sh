clear
echo Automated Process
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cd /Source/Udaan_Main_Trunk
svn --username=tekulkar --password=Illidan47 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Main_Trunk/ *
echo  Will start SVN update before that let me take some rest!!!!!

cp /Source/Build_Config/opsman/jdbc.properties /Source/Udaan_Main_Trunk/udaan-opsman-integration/src/main/webapp/WEB-INF/
cp /Source/Build_Config/opsman/log4j.xml /Source/Udaan_Main_Trunk/udaan-opsman-integration/src/main/resources/

cp /Source/Build_Config/opsman/Bcun.properties /Source/Udaan_Main_Trunk/udaan-opsman-integration/src/main/resources/
cp /Source/Build_Config/opsman/ftp.properties /Source/Udaan_Main_Trunk/udaan-opsman-integration/src/main/resources/

cp /Source/Build_Config/opsman/jdbc.properties /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp /Source/Build_Config/opsman/log4j.xml /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources
cp /Source/Build_Config/opsman/jndi.properties /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/src/main/resources

cp /Source/Build_Config/opsman/Queue/messagequeuelistener.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/opsman/Queue/queueconfiguration.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/opsman/Queue/splitmodel.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/opsman/Queue/messagequeue.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources
cp /Source/Build_Config/opsman/email.properties /Source/Udaan_Main_Trunk/udaan-framework/src/main/resources

echo  ahhh.....good morning ...!!! am ready to build
mvn  clean compile package -DdryRun=true

mytimestamp=OPSMAN-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp
mkdir  /Source/Current_Build/$mytimestamp

echo assembling all the deployables in Current_Build folder....
cp -rf /Source/Udaan_Main_Trunk/udaan-central-server/target/udaan-central-server.war /Source/Current_Build/$mytimestamp

cp -rf /Source/Udaan_Main_Trunk/bcun-asynch-queue-consumer/ejbs/target/bcun-asynch-queue-consumer-1.0.jar /Source/Current_Build/$mytimestamp
cp -rf /Source/Udaan_Main_Trunk/bcun-data-sync/target/bcun-data-sync.jar /Source/Current_Build/$mytimestamp/bcun-data-sync-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-framework/target/udaan-framework.jar /Source/Current_Build/$mytimestamp/udaan-framework-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-domain/target/udaan-domain-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-domain-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Main_Trunk/udaan-transfer-object/target/udaan-transfer-object-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-transfer-object-1.0-SNAPSHOT.jar

