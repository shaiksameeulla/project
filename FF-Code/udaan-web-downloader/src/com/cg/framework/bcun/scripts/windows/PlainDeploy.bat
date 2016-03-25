
REM ######################
cls
echo Deploy the new build for Udaan Web

SET "Udaan_Home=D:\opt\Scripts"
SET "Udaan_Tomcat_Home=D:\apache-tomcat-7.0.21_8088"

rem ## Folder Where Release will be downloaded.
SET "ud_folder=D:\opt\ud\udaan\libs"
SET "Build_Backup=D:\Build_Backup"
SET "toBeDeployed=udaan-web.war"
rem toBeDeployed=udaan-central-server.war



set DATESTAMP=%DATE:~10,4%_%DATE:~4,2%_%DATE:~7,2%
set TIMESTAMP=%TIME:~0,2%_%TIME:~3,2%_%TIME:~6,2%
set mytimestamp=%DATESTAMP%_%TIMESTAMP%
echo %mytimestamp% 

rem ### Make Build Dir
echo Checking Build Dir
IF not exist %Build_Backup% (mkdir %Build_Backup%)

CLS
echo Shuting Down Tomcat............!!!! 
echo ***********************************
echo ********  Wait for 25 secs.   *****
echo ***********************************
CD D:\
CD %Udaan_Tomcat_Home%

rem PAUSE
 rem call %Udaan_Tomcat_Home%\bin\tomcat6.exe stop
call  %Udaan_Tomcat_Home%\bin\shutdown.bat
rem pause

rem ##   Wait for Shutdown of Tomcat
rem ### Tomcat Closed

rem ##### - Copy the Existing Build Start #####
cd %Udaan_Tomcat_Home%\webapps


mkdir  %Build_Backup%\%mytimestamp%
mkdir  %Build_Backup%\%mytimestamp%\logs


echo   Folders are created with this Time Stamp: %mytimestamp%  and now backing up...!!

echo Tomcat Home : %Udaan_Tomcat_Home%
echo Build BackUp : %Build_Backup%
echo New Folder : %mytimestamp%

set "src=%Udaan_Tomcat_Home%\webapps\*udaan*.*" 
set "des=%Build_Backup%\%mytimestamp%\"

echo  src %src%
echo des %des%
rem ##### -  Build Backup - Start #####
xcopy /S %src% %des%
echo   logs backup...!!!!
xcopy /S %Udaan_Tomcat_Home%\logs\*.* %des%\logs


rem ##### - Build Backup - End #####
echo   Deleteing old Deployment...!!!!!

del /Q "%Udaan_Tomcat_Home%\webapps\*udaan*"
del /Q "%Udaan_Tomcat_Home%\logs\*.*"
del /Q "%Udaan_Tomcat_Home%\work"

echo   Copied previous build to location: %mytimestamp%
rem ##### - Copy the Existing Build End #####


set "srcWar=%ud_folder%\%toBeDeployed%" 
set "desWar=%Udaan_Tomcat_Home%\webapps\"

echo  srcWar %srcWar%
echo desWar %desWar%
rem ##### - Copy the New Build Build - Start #####
xcopy /S %srcWar% %desWar%
rem ##### - Copy the New Build Build - End #####
echo  new Build copied



rem ##### - Start Tomcat #####
cd %Udaan_Tomcat_Home%\bin
startup.bat
rem ##### - Start Tomcat #####

echo   Started Udaan Web...!!!

