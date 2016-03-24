clear
echo Automated Process
PATH=$PATH:/opt/apache-maven-3.0.4/bin:/opt/CollabNet_Subversion/bin:/usr/java/jdk1.7.0_09 

JAVA_HOME=/usr/java/jdk1.7.0_09
SVN_HOME=/opt/CollabNet_Subversion
MAVEN_HOME=/opt/apache-maven-3.0.4


cd /Source/Udaan_Cygnet
svn --username=sraghave --password=August2013 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Cygnet/ *
echo  Will start SVN update before that let me take some rest!!!!!
sleep 45
cp /Source/Build_Config/uat/admin/jdbc.properties /Source/Udaan_Cygnet/udaan-config-admin/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/admin/log4j.xml /Source/Udaan_Cygnet/udaan-config-admin/src/main/resources/

cp /Source/Build_Config/uat/web/jdbc.properties /Source/Udaan_Cygnet/udaan-web/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/web/log4j.xml /Source/Udaan_Cygnet/udaan-web/src/main/resources/

cp /Source/Build_Config/uat/sap_Integration/jdbc.properties /Source/Udaan_Cygnet/udaan-sap-integration/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/sap_Integration/log4j.xml /Source/Udaan_Cygnet/udaan-sap-integration/src/main/resources/

cp /Source/Build_Config/uat/central_server/jdbc.properties /Source/Udaan_Cygnet/udaan-central-server/src/main/webapp/WEB-INF/
cp /Source/Build_Config/uat/central_server/log4j.xml /Source/Udaan_Cygnet/udaan-central-server/src/main/resources/

cp /Source/Build_Config/uat/Bcun.properties /Source/Udaan_Cygnet/bcun-data-sync/src/main/resources/
cp /Source/Build_Config/uat/ftp.properties /Source/Udaan_Cygnet/bcun-data-sync/src/main/resources/
cp /Source/Build_Config/uat/email.properties /Source/Udaan_Cygnet/udaan-universal-routine/src/main/resources/

cp -rf /Source/Udaan_Cygnet/udaan-web/src/main/webapp/login /Source/Udaan_Cygnet/udaan-config-admin/src/main/webapp/
cp -rf /Source/Udaan_Cygnet/udaan-rate-calculation/src/main/resources/META-INF/resources/rate /Source/Udaan_Cygnet/udaan-config-admin/src/main/webapp/



echo  ahhh.....good morning ...!!! am ready to build
mvn  clean compile package -DdryRun=true



mytimestamp=Cygnet_UAT-$(date +%Y%m%d-%H%M)
echo   Folders are created with this Time Stamp is $mytimestamp
mkdir  /Source/Build_Backup/$mytimestamp
cp /Source/Udaan_Cygnet/udaan-web/target/udaan-web.war /Source/Build_Backup/$mytimestamp/
cp /Source/Udaan_Cygnet/udaan-config-admin/target/udaan-config-admin.war /Source/Build_Backup/$mytimestamp/
cp /Source/Udaan_Cygnet/udaan-sap-integration/target/udaan-sap-integration.war /Source/Build_Backup/$mytimestamp/

