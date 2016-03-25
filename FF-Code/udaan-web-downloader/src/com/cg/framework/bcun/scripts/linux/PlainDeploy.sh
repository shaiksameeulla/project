#********************************************************************************
# * **   
# *  **    Copyright: (c) 8/15/2014 Capgemini All Rights Reserved.
# * **------------------------------------------------------------------------------
# * ** Capgemini India Private Limited  |  No part of this file may be reproduced
# * **                                  |  or transmitted in any form or by any
# * **                                  |  means, electronic or mechanical, for the
# * **                                  |  purpose, without the express written
# * **                                  |  permission of the copyright holder.
# * * @ gujoshi
#********************************************************************************
echo Deploying the New Build for Udaan Web
#---------------------------------------------------------------------------------
Udaan_Home='/opt/Scripts'
ud_folder='/opt/ud/udaan/libs'
Build_Backup='/Build_Backup'
Udaan_Tomcat_Home='/opt/apache-tomcat-7.0.32'
toBeDeployed=udaan-web.war

#Udaan_Tomcat_Home='/opt/apache-tomcat-7.0.32_8075_CentSrv'
#toBeDeployed=udaan-central-server.war
#---------------------------------------------------------------------------------

### Make Build Dir  ######
echo Checking Build Backup Dir
if [[ ! -e $Build_Backup ]]; then
mkdir $Build_Backup
elif [[ ! -d $Build_Backup ]]; then
echo "$dir already exists but is not a directory" 1>&2
fi

echo "*****************************************"
echo "****Shuting Down Tomcat............!!!!**"
echo "********  Wait for about 25 secs.   *****"
echo "*****************************************"
#$Udaan_Tomcat_Home/bin/shutdown.sh
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
if [ $i -ge 20 ]; then
echo "Starting Force Shutdown"
pid=$(ps -ef | grep $Udaan_Tomcat_Home | grep 'Max' | cut -f 7 -d' ');
echo killing pid is $pid
if [ "${pid}" ]; then
eval "kill ${pid}"
echo "Killed by  Force Shutdown"
fi
else
echo "Normal Shutdown"
fi
### Tomcat Closed

##### - Copy the Existing Build #####
cd $Udaan_Tomcat_Home/webapps
mytimestamp=$(date +%Y%m%d-%H%M)
mkdir  /$Build_Backup/$mytimestamp
mkdir  /$Build_Backup/$mytimestamp/logs
echo "***************************************************************************************"
echo "****Folders are created with this Time Stamp: $mytimestamp  and now backing up...!!!***"
echo "********  Wait for about 25 secs.                                                 *****"
echo "***************************************************************************************"
cp -R $Udaan_Tomcat_Home/webapps/$toBeDeployed  $Build_Backup/$mytimestamp
cp -R $Udaan_Tomcat_Home/logs/*.* $Build_Backup/$mytimestamp/logs

echo "***************************************************************************************"
echo "***  Deleting old Deployment...!!!!!                                                ***"
echo "********  Wait for about 25 secs.                                                 *****"
echo "***************************************************************************************"
rm -rf $Udaan_Tomcat_Home/webapps/*udaan*
rm -rf $Udaan_Tomcat_Home/logs/*.*
rm -rf $Udaan_Tomcat_Home/work/*.*
rm -rf $Udaan_Tomcat_Home/work/Catalina/
rm -rf $Udaan_Tomcat_Home/logs/*.*
dpfile=$ud_folder/$toBeDeployed
if [ -f $dpfile ];then
echo "Old Deployment $ud_folder/$toBeDeployed exists"
else
echo "Old Deployment $ud_folder/$toBeDeployed doesn't exists"
cp $ud_folder/$toBeDeployed $Udaan_Tomcat_Home/webapps/
fi

cp $ud_folder/$toBeDeployed $Udaan_Tomcat_Home/webapps/
echo "***************************************************************************************"
echo "***  Copied previous build to location: $mytimestamp                             ******"
echo "***************************************************************************************"
cd $Udaan_Tomcat_Home/bin
sh startup.sh &
if [ "$?" = "0" ]; then
echo "***************************************************************************************"
echo "****  Started Udaan Web...!!!                                                 *********"
echo "****  Check by Login in application..!!!                                      *********"
echo "***************************************************************************************"
else
echo "********************************************************************"
echo "Can't Start Tomcat Server" 1>&2
echo "Please Contact IT Support !" 1>&2
echo "********************************************************************"
exit 1
fi
sleep 6
exit
