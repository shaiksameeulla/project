#********************************************************************************
# * **   
# *  **    Copyright: (c) 8/15/2014 Capgemini All Rights Reserved.
# * **------------------------------------------------------------------------------
# * ** Capgemini India Private Limited  |  No part of this file may be reproduced
# * **                                  |  or transmitted in any form or by any
# * **                                  |  means, electronic or mechanical, for the
# * **                                  |  purpose, without the express written
# * **                                  |  permission of the copyright holder.
# * * 
#********************************************************************************
clear
echo Udaan Deployment Downloader

#--------------------------------------------------------------------------
DownloadLink='http://172.16.10.136:8075/udaan-web-downloader-CATB.jnlp'
Download_Home='udaan'
ud_folder='/opt/ud/udaan/libs/udaan-web-downloader.jar'
depScrpit='/opt/ud/PlainDeploy.sh'
#---------------------------------------------------------------------------

echo "********************************************************************"
echo "********************************************************************"
echo "******Downloading from Site:                                   *****"
echo "******$DownloadLink *****"
echo "********************************************************************"
echo "********************************************************************"

#Downloading Latest files from Server
java -jar jnlpdownloader.jar $Download_Home $DownloadLink
if [ "$?" = "0" ]; then
echo "Download Sucessfull!" 1>&2
else
echo "********************************************************************"
echo "Can't connect to Deployment Site!" 1>&2
echo "Please Contact IT Support!" 1>&2
echo "********************************************************************"
exit 1
fi

echo "*****************************************************"
echo "******Updating the Branch Specific Customization*****"
echo "*****************************************************"
java -cp $ud_folder com.cg.framework.bcun.jnlp.BcunDownloaderJnlp
if [ "$?" = "0" ]; then
echo "Customization is Sucessfull!" 1>&2
else
echo "********************************************************************"
echo "Can't Run XServer!" 1>&2
echo "Please Contact IT Support to setup XServer for logged user!" 1>&2
echo "********************************************************************"
exit 1
fi

echo Checking Build Dir
if [ -f $depScrpit ];then
echo "Deployment $depScrpit exists"
else
echo "********************************************************************"
echo "File $depScrpit does not exists"
echo "Please Contact IT Support to setup deployment client !" 1>&2
echo "********************************************************************"
exit 1
fi
sh $depScrpit
