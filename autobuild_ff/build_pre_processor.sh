main_trunk_code_location=/Source/Udaan_Main_Trunk
production_code_location=/Source/Udaan_Production_Post_Warranty

main_trunk_bcun_location=$main_trunk_code_location/bcun-data-sync/src/main/resources
production_bcun_location=$production_code_location/bcun-data-sync/src/main/resources

echo thanks for executing build pre processor script
echo cleaning files from the location $production_bcun_location
rm -f -v $production_bcun_location/*.*
echo cleaning files from the location $main_trunk_bcun_location
rm -f -v $main_trunk_bcun_location/*.*
cd $production_bcun_location
echo taking latest files for the location $production_bcun_location
svn update 
cd $main_trunk_bcun_location
echo taking latest files for the location $main_trunk_bcun_location
svn update 
main_trunk_framework_location=$main_trunk_code_location/udaan-framework/src/main/resources
prduction_trunk_framework_location=$production_code_location/udaan-framework/src/main/resources
echo cleaning main trunk framework properties from the location $main_trunk_framework_location
rm -f -v $main_trunk_framework_location/*.*

cd $main_trunk_framework_location
svn update 
echo cleaning productionframework properties from the location $prduction_trunk_framework_location
rm -f -v $prduction_trunk_framework_location/*.*
cd $prduction_trunk_framework_location
svn update 

cd /Source/AutoBuild