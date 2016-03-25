######################
clear
echo Deploy the New Build for Udaan Web

Udaan_Home='/opt/Scripts'
Udaan_Tomcat_Home='/opt/apache-tomcat-7.0.32'
ud_folder='/opt/ud/udaan/libs'
Build_Backup='/Build_Backup'
toBeDeployed=udaan-web.war

### Make Build Dir
echo Checking Build Dir
if [[ ! -e $Build_Backup ]]; then
    mkdir $Build_Backup
elif [[ ! -d $Build_Backup ]]; then
    echo "$dir already exists but is not a directory" 1>&2
fi

echo Shuting Down Tomcat............!!!! 
echo ***********************************
echo ********  Wait for 25 secs.   *****
echo ***********************************
cd $Udaan_Tomcat_Home/bin
$Udaan_Tomcat_Home/bin/shutdown.sh

##   Wait for Shutdown of Tomcat

 i=1
 while [ $i -le 20 ]
    do
    ps -ef | grep catalina.startup.Bootstrap > UdaanRel.txt
    if ls -l UdaanRel.txt | grep 81
    then
       i=21
    else 
       i=`expr $i + 1`
       sleep 1
    fi 
 done

### Tomcat Closed

##### - Copy the Existing Build #####
cd $Udaan_Tomcat_Home/webapps
mytimestamp=$(date +%Y%m%d-%H%M)
mkdir  /$Build_Backup/$mytimestamp
mkdir  /$Build_Backup/$mytimestamp/logs
echo   Folders are created with this Time Stamp: $mytimestamp  and now backing up...!!!
cp -R $Udaan_Tomcat_Home/webapps/$toBeDeployed  $Build_Backup/$mytimestamp
cp -R $Udaan_Tomcat_Home/logs/*.* $Build_Backup/$mytimestamp/logs

# cp -R $Udaan_Tomcat_Home/logs/*.* $Build_Backup/$mytimestamp/logs

echo   Deleting old Deployment...!!!!!
rm -rf $Udaan_Tomcat_Home/webapps/*udaan*
rm -rf $Udaan_Tomcat_Home/logs/*.*
rm -rf $Udaan_Tomcat_Home/work/*.*
echo   Copied previous build to location: $mytimestamp
##### - Copy the Existing Build #####


##### - Copy the New Build Build #####
cp $ud_folder/$toBeDeployed $Udaan_Tomcat_Home/webapps/
##### - Copy the New Build Build #####

##### - Copy the New Build Build #####
cd $Udaan_Tomcat_Home/bin
sh startup.sh &
##### - Copy the New Build Build #####

echo   Started Udaan Web...!!!

sleep 5 
clear 
exit