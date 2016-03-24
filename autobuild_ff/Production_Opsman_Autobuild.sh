clear
echo Automated Process
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cp -rf /Source/Build_Config/production/opsman/opsq/messagequeuelistener.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/queueconfiguration.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/splitmodel.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/messagequeue.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/
cp -rf /Source/Build_Config/production/email.properties /Source/Udaan_Production_Post_Warranty/udaan-framework/src/main/resources/

cd /Source/Udaan_Production_Post_Warranty/udaan-framework
echo  ahhh.....good morning ...!!! am ready to build
mvn  package install -DdryRun=true

cp -rf /Source/Build_Config/production/opsman/opsq/resources/META-INF /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/resources/springContext /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/resources/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/opsq/resources/jndi.properties /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/

cp -rf /Source/Build_Config/production/opsman/jdbc.properties /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/webapp/WEB-INF/
cp -rf /Source/Build_Config/opsman/log4j.xml /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/

cp -rf /Source/Build_Config/production/opsman/Bcun.properties /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/
cp -rf /Source/Build_Config/production/opsman/ftp.properties /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/src/main/resources/

cd /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration
echo  ahhh.....good morning ...!!! am ready to build
mvn  package install -DdryRun=true


mytimestamp=PROD-OPSMAN-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp
mkdir  /Source/Current_Build/$mytimestamp

echo assembling all the deployables in Current_Build folder....
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/target/udaan-opsman-integration.war /Source/Current_Build/$mytimestamp

cp -rf /Source/Udaan_Production_Post_Warranty/udaan-framework/target/udaan-framework.jar /Source/Current_Build/$mytimestamp/udaan-framework-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-domain/target/udaan-domain-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-domain-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-transfer-object/target/udaan-transfer-object-1.0-SNAPSHOT.jar /Source/Current_Build/$mytimestamp/udaan-transfer-object-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-universal-routine/target/udaan-universal-routine.jar /Source/Current_Build/$mytimestamp/udaan-universal-routine-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-rate-calculation/target/udaan-rate-calculation.jar /Source/Current_Build/$mytimestamp/udaan-rate-calculation-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-web/target/udaan-web.jar /Source/Current_Build/$mytimestamp/udaan-web-1.0-SNAPSHOT.jar
cp -rf /Source/Udaan_Production_Post_Warranty/udaan-opsman-integration/target/udaan-opsman-integration.jar /Source/Current_Build/$mytimestamp/udaan-opsman-integration-1.0-SNAPSHOT.jar

