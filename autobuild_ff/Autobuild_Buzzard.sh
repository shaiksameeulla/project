clear
echo Automated Process
cd /Source/Udaan_Buzzard
svn --username=rmaladi --password=Sony@123 update https://coconet-svn-in-01.capgemini.com/svn/repos/first_flight_udaan_project/Udaan_Buzzard/ *
echo  Will start SVN update before that let me take some rest!!!!!

sleep 30

echo  ahhh.....good morning ...!!! am ready to build

cp /Source/Build_Config/buzzard/Bcun.properties /Source/Udaan_Buzzard/udaan-web/src/main/resources/
cp /Source/Build_Config/buzzard/Bcun.properties /Source/Udaan_Buzzard/udaan-central-server/src/main/resources/
cp /Source/Build_Config/buzzard/email.properties /Source/Udaan_Buzzard/udaan-universal-routine/src/main/resources/
cp /Source/Build_Config/buzzard/ftp.properties /Source/Udaan_Buzzard/bcun-data-sync/src/main/resources/

cp /Source/Build_Config/buzzard/web/jdbc.properties /Source/Udaan_Buzzard/udaan-web/src/main/webapp/WEB-INF/
cp /Source/Build_Config/buzzard/web/log4j.xml /Source/Udaan_Buzzard/udaan-web/src/main/resources/

cp /Source/Build_Config/buzzard/admin/jdbc.properties /Source/Udaan_Buzzard/udaan-config-admin/src/main/webapp/WEB-INF/
cp /Source/Build_Config/buzzard/admin/log4j.xml /Source/Udaan_Buzzard/udaan-config-admin/src/main/resources/

cp /Source/Build_Config/buzzard/central_server/jdbc.properties /Source/Udaan_Buzzard/udaan-central-server/src/main/webapp/WEB-INF/
cp /Source/Build_Config/buzzard/central_server/log4j.xml /Source/Udaan_Buzzard/udaan-central-server/src/main/resources/


cp -rf /Source/Udaan_Buzzard/udaan-web/src/main/webapp/login /Source/Udaan_Buzzard/udaan-config-admin/src/main/webapp/

mvn -o clean compile package install

echo Complete
