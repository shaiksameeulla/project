var manageAwbCntrl = '';

var saveAwbMockData = {
	isAWBSaved: false,
	saveAwb: {
		fromShipper: {
			fromShipperIsSaved: false
		},
		toConsignee: {
			toConsigneeIsSaved: false
		},
		shipmentDetails: {
			shipmentDetailsIsSaved: false
		}
	}
};

/** Manage Capacity Parameter Controller & Their Methods* */
app_controllers.controller('ManageAWBController', function($scope, $filter, $http, $modal, $log, manageAWBService, $localStorage, ngTableParams, $sce, $timeout, $rootScope, ConsigneeService,$location,referenceDataDropDown,$compile,ERRORMESSAGES,roles,$route,focus,ServiceURL) {
    var baseurl = ServiceURL.baseUrl;
	manageAwbCntrl = $scope;	
	$rootScope.loadingIcon=true;
	$scope.saveAwb = {};
	$rootScope.mastNbr='';
	$rootScope.locNbr='';
    $scope.currentPrivilegeCode='';
	$scope.saveAwb.isAWBSaved = false;//boolean variable to check whether the AWB object is saved or not
	$scope.saveAwb.fromShipper = {};
	$scope.saveAwb.fromShipperIsOpen = false;//boolean variable to check whether the accordion panel is open or closed and save its data - auto save feature, when the user tabs out of the last field
	$scope.saveAwb.fromShipperIsSaved = false;//boolean variable to check whether the individual accordion data is saved or not
	$scope.saveAwb.toConsignee = {};
	$scope.saveAwb.toConsigneeIsOpen = false;
	$scope.saveAwb.toConsigneeIsSaved = false;
	$scope.shipmentDetailsBase = false;
	$scope.saveAwb.shipmentDetails = {};
	$scope.saveAwb.shipmentDetailsIsOpen = false;
	$scope.saveAwb.shipmentDetailsIsSaved = false;
	$rootScope.testVar = 1;
	$scope.headerData={};
	$scope.dispErrorToConsignee = ConsigneeService.getCntrVal();
	$scope.dispAccHdrError = false;
	$scope.fieldName = '';
	$scope.errorFieldNameFs = '';
	$scope.dispErrorShipmentDetails = ConsigneeService.getCntrValSd();
	$scope.dispAccHdrErrorSd = false;
	$scope.fieldNameSd = '';
	$scope.getAwbInfo = {};
	$scope.awbContent={"versionNumber":""};
	$scope.constant=ERRORMESSAGES;
	var previous_headerData_record='';
	$scope.headerAwbOrigin = "DAL";//$scope.displayDivErrorBar = false;
	//$localStorage.isSearchPageCall=false;
	$rootScope.role=roles;
	$scope.toPutNoOfPiecesInFocus = false;
	$scope.displayDivErrorInGeneral = false;
	$scope.lotLabelVersion = '';
	$scope.releaseFormComplete = false;
	$scope.isEmerLocked = false;
	$scope.updatedVersionNumber = "";
	
	$scope.init = function()
	{
		//Call the "GetAWBInfo" web service method to check for the status of the particular AWB
		
		/****Mock data to be replaced with web service data******/
		$scope.getAwbInfo = saveAwbMockData;
		/*******************************************************/
		
		setTimeout(function()
		{ 
			var panelId = '';
			if(!$scope.getAwbInfo.isAWBSaved)
			{
				if(!$scope.getAwbInfo.saveAwb.fromShipper.fromShipperIsSaved)
				{
					if(panelId === '')
					panelId = "#fromShipperBody";
				}
				if(!$scope.getAwbInfo.saveAwb.toConsignee.toConsigneeIsSaved)
				{
					if(panelId === '')
					panelId = "#toConsigneeBody";
				}
				if(!$scope.getAwbInfo.saveAwb.shipmentDetails.shipmentDetailsIsSaved)
				{
					if(panelId === '')
					panelId = "#shipmentDetailsBody";
				}
			}
			//Open this accordion panel only for the first time when the user has not saved any accordion info
			//$(panelId).attr("style", "display:block;");
			
			if($localStorage.viewMode)
			{
				$scope.disablePage();
			}
			//setTimeout(function(){
			if($localStorage.isSearchPageCall == true)
			{
				if($rootScope.awbContent!=null && $rootScope.awbContent!=null && $rootScope.awbContent!=null){
					if($rootScope.awbContent.locked == true)
					{
						$scope.isEmerLocked = true
					}
					if($rootScope.awbContent.screening!=null && $rootScope.awbContent.screening!=null && $rootScope.awbContent.screening!=null){
						
						if($rootScope.awbContent.screening.shipmentReleaseForm!=null && $rootScope.awbContent.screening.shipmentReleaseForm!=null && $rootScope.awbContent.screening.shipmentReleaseForm!=null){
							if($rootScope.awbContent.screening.shipmentReleaseForm.isComplete == 'YES')
							{
								// $('.accordionWrapper>li:eq(0)').addClass('disabled');
								// $('.accordionWrapper>li:eq(1)').addClass('disabled');
								// $('.accordionWrapper>li:eq(2)').addClass('disabled');
								// $('.accordionWrapper>li:eq(3)').addClass('disabled');
								// $('.accordionWrapper>li:eq(4)').addClass('disabled');
								// $('.accordionWrapper>li:eq(5)').addClass('disabled');
								// $('.accordionWrapper>li:eq(6)').addClass('disabled');
								// $('.accordionWrapper>li:eq(7)').addClass('disabled');
								// $scope.collapseAllSections()
								// manageAwbCntrl.triggerNxtSection('8','comments');
								$scope.releaseFormComplete = true;
							}
						}
					}	
				}
			}
			//},1000)
		}, 10);
	}
	
	/***SET SECTION WISE PRIVILEGE***/
	$scope.isSectionHasPrivileges=function(awbStatus)
	{  
		$scope.sectionPrivilege=[];
		$scope.currentPrivilegeCode="";
		$scope.getUpatedAWBPrivileges(awbStatus);
	}
	
	$scope.getUpatedAWBPrivileges=function(awbStatus)
	{
		$scope.sectionPrivilege=[];$scope.sectionAWBPrivilege=[];
		/***MAKE CALL TO GET UPDATED PRIVILEGE***/
		var data={"awbStatus":awbStatus,groupName:$localStorage.userObject.authorizationView.roleList};
		manageAWBService.getAWBSectionPrivileges(data).then(function(responseData){ 
            /***SECTION PRIVILEGE ASSIGNED TO THE EACH SECTION**/
			for(i=1;i<=10;i++){
				$scope.sectionPrivilege.push("AWB_"+awbStatus+"_SECTION"+i+"_UPDATE");
			}		 
			$scope.sectionAWBPrivilege=$.map(angular.copy(responseData.data),$.trim);
		});			
	}
	
	
	/***REMOVE***/
	$scope.removeFlagMsg=function(){
	  $scope.showBar=false;
	}
			
	/**DECIDE SECTION PRIVILEGE TO OPEN**/
	$scope.isSectionCollapse=function(sectionName)
	{  
	
		if($scope.sectionAWBPrivilege && sectionName){
			var sectionPrivilege = $filter('filter')($scope.sectionAWBPrivilege,sectionName);
			return (sectionPrivilege.length>0)?true:false;
		}
	}
	
	/****VOID BUTTON CONDITION***/     
	$scope.isVoidButtonEnable=function()
	{
		if($scope.currentPrivilegeCode!=$scope.updatedCode){
			switch ($scope.currentPrivilegeCode){
				case 'DRFT':
				case 'SLI':
				case 'ADV':
				case 'FOH':
				$scope.voidStatus=false;
				$scope.cancelVoid=true;
				break;
				case 'MAN':
				case 'DEP':
				case 'ARR':
				$scope.voidStatus=false;
				$scope.cancelVoid=false;
				break;
				default:
				$scope.voidStatus=true;
				break;			
			}
			$scope.updatedCode=$scope.currentPrivilegeCode;
		};
	}
	
	/***MAP THE AWB STATUS ***/
	$scope.setAWBHeaderStatus=function(orignalStatus)
	{  
		if(orignalStatus && $scope.currentPrivilegeCode!=orignalStatus){
			angular.forEach($scope.constant.awb_status_header_cons,function(key,value){
				if(angular.uppercase(orignalStatus)===key.key)
				{
					$scope.headerData.Fullstatus=key.value;
				}
			});
			$scope.currentPrivilegeCode=orignalStatus;
			$scope.getUpatedAWBPrivileges($scope.currentPrivilegeCode);
			$scope.isVoidButtonEnable();
		}
	}
	$scope.prevAccType = "fromshipper";
	/**AUT0 SAVE**/
	$scope.autoSaveData = function(accType,SectionNo) 
	{
		/***APPEND HTML ON CLICK**/
		if(accType)
		{  
			switch (accType)
			{
				case 'shipmentdetails':
				if($scope.shipmentdetails!=true){
					if(!$("#shipmentDetailsAcrdn").hasClass("disabled"))
					{
						var template = ($compile("<div awb-shipment-details></div>"))($scope);
						$("#shipmentDetailsBody").html(template);
						$scope.shipmentdetails=true;
					}
				}
				if(!$("#shipmentDetailsAcrdn").hasClass("disabled"))
					{
						$timeout(function(){
						shipmentDetailsScope.init();
						},500);
					}
				break;
				
				case 'toconsignee':
				if($scope.toConsignee!=true)
				{
					if(!$("#toConsigneeAcrdn").hasClass("disabled"))
					{
						var template = ($compile("<div awb-to-consignee></div>"))($scope);
						$("#toConsigneeBody").html(template);
						$scope.toConsignee=true;
						$timeout(function(){focus('consigneeId');},500);
					}
				}
				else{ 
				if(!$("#toConsigneeAcrdn").hasClass("disabled"))
					focus('consigneeId');				
				}
				break;
				
				case 'cargoScreen':
				if($scope.cargoScreen!=true){
					if(!$("#cargoScreeningAcrdn").hasClass("disabled"))
					{
						var template = ($compile("<div cargo-screening></div>"))($scope);
						$("#cargoScreenBody").html(template);
						$scope.cargoScreen=true;
						$rootScope.cargoScreen=true;
						$timeout(function(){focus('noOfPieces');},500);
					}
				}else{
					if(!$("#cargoScreeningAcrdn").hasClass("disabled"))
						focus('noOfPieces');
					}
				break; 
				case 'routing':
				if($scope.routing!=true){
					if(!$("#routingAcrdn").hasClass("disabled"))
					{
						var template = ($compile("<div awb-routing ng-cloak></div>"))($scope);
						$("#routingBody").html(template);
						$scope.routing=true;
						$timeout(function () {
							if($rootScope.awbContent !== undefined && !$("#routingBody").parent().hasClass("disabled"))
							$("#routingBody").attr("style", "display:block;");
							var date = new Date();
							var currMonth = (date.getMonth() + 1) < 10? ("0" + (date.getMonth() + 1).toString()): (date.getMonth() + 1).toString();
							var currDay = date.getDate() < 10? ("0" + date.getDate().toString()): date.getDate().toString();
							var currYear = date.getFullYear().toString();
							var currHours = date.getHours() < 10? ("0" + date.getHours().toString()): date.getHours().toString();
							var currMinutes = date.getMinutes() < 10? ("0" + date.getMinutes().toString()): date.getMinutes().toString();
							
							var currentDate = currMonth + '/' + currDay + '/' + currYear;
							var currentTime = currHours + currMinutes;
							if(routingScope.initialSearch === undefined)
							routingScope.initialSearch = {};
							routingScope.initialSearch.showOnlyFlightsWithAvailableCapacity = false;
							routingScope.initialSearch.shipDateTime = currentDate + " " + currentTime;
							routingScope.initialSearch.serviceLevel = manageAwbCntrl.headerData.serviceLevel;
							
							routingScope.initialSearch.origin = manageAwbCntrl.headerData.origin;
							routingScope.initialSearch.destination = manageAwbCntrl.headerData.destination;
							routingScope.initialSearch.shipDateTime = currentDate + " " + currentTime;
							
							routingScope.searchValues.findRoutes.origin = {key: manageAwbCntrl.headerData.origin, value: manageAwbCntrl.headerData.origin};
							routingScope.searchValues.findRoutes.destination = {key: manageAwbCntrl.headerData.destination, value: manageAwbCntrl.headerData.destination};
							
							routingScope.performRouteSearch('initial');
							/*
							if(shipmentDetailsScope.shipmentInfo !== undefined)
							{
								if(shipmentDetailsScope.shipmentInfo.actualWeightUnit === "LBS")
									$("#lbsRadio").prop("checked", true);
								else
									$("#kgRadio").prop("checked", true);
							}
							*/
						}, 100)
						$timeout(function(){focus('routeNoForLot1');},500);
					}
				}
				else
				{
					if(!$("#routingAcrdn").hasClass("disabled"))
					{
						if($(".status.txtStatusOne")[0].innerHTML === "CANCELLED" || $(".status.txtStatusOne")[0].innerHTML === "CXLD" || $(".status.txtStatusOne")[0].innerHTML === "VOID")
						{
							for(var x = 0; x < routingScope.getLots.routedLot.length; x++)
							{
								routingScope.getLots.routedLot[x].flightLegs = [];
								$(".clubbedLine" + routingScope.getLots.routedLot[x].lotNumber.toString()).find("tbody")[0].innerHTML = "";
								routingScope.getLots.routedLot[x].fltNbr = "";
								routingScope.getLots.routedLot[x].lotsOrig = "";
								routingScope.getLots.routedLot[x].destination = "";
								routingScope.getLots.routedLot[x].scheduledDepartureDate = "";
								routingScope.getLots.routedLot[x].scheduledDepartureTime = "";
								routingScope.getLots.routedLot[x].scheduledArrivalTime = "";
								routingScope.getLots.routedLot[x].scheduledArrivalDate = "";
							}
							setTimeout(function()
							{ 
								$("#btnDoReRouting").attr("disabled", "disabled");
							}, 10);
						}
						else
						{
						$timeout(function () {
							if($rootScope.awbContent !== undefined && !$("#routingBody").parent().hasClass("disabled"))
							$("#routingBody").attr("style", "display:block;");
							$(".mainLineClass").addClass("ng-hide");
							$(".toggleLineClass").removeClass("minus");
							
							var date = new Date();
							var currMonth = (date.getMonth() + 1) < 10? ("0" + (date.getMonth() + 1).toString()): (date.getMonth() + 1).toString();
							var currDay = date.getDate() < 10? ("0" + date.getDate().toString()): date.getDate().toString();
							var currYear = date.getFullYear().toString();
							var currHours = date.getHours() < 10? ("0" + date.getHours().toString()): date.getHours().toString();
							var currMinutes = date.getMinutes() < 10? ("0" + date.getMinutes().toString()): date.getMinutes().toString();
							
							var currentDate = currMonth + '/' + currDay + '/' + currYear;
							var currentTime = currHours + currMinutes;	
							routingScope.initialSearch.showOnlyFlightsWithAvailableCapacity = false;
							routingScope.initialSearch.shipDateTime = currentDate + " " + currentTime;
							routingScope.initialSearch.serviceLevel = manageAwbCntrl.headerData.serviceLevel;
							
							routingScope.initialSearch.origin = manageAwbCntrl.headerData.origin;
							routingScope.initialSearch.destination = manageAwbCntrl.headerData.destination;
							routingScope.initialSearch.shipDateTime = currentDate + " " + currentTime;
							
							routingScope.searchValues.findRoutes.origin = {key: manageAwbCntrl.headerData.origin, value: manageAwbCntrl.headerData.origin};
							routingScope.searchValues.findRoutes.destination = {key: manageAwbCntrl.headerData.destination, value: manageAwbCntrl.headerData.destination};
							
							routingScope.performRouteSearch('initial');
							if(manageAwbCntrl.headerData.awbNumber !== undefined)
							routingScope.getAwbDetailsFromService(manageAwbCntrl.headerData.awbNumber.toString());
							
							$.each($(".mainLineClass"), function(index){							
								if(!$(this).find("tbody").hasClass("ng-hide"))
								{
									$(this).find("tbody").addClass("ng-hide");
								}
							});
							/*
							if(shipmentDetailsScope.shipmentInfo !== undefined)
							{
								if(shipmentDetailsScope.shipmentInfo.actualWeightUnit === "LBS")
									$("#lbsRadio").prop("checked", true);
								else
									$("#kgRadio").prop("checked", true);
							}
							*/
							/*
							if(cargoScreeningDirective.cargoScreen.screeningFailed === true)
							{
								for(var x = 0; x < routingScope.getLots.routedLot.length; x++)
								{
									var toBeSplit = routingScope.getLots.routedLot[x].currentLotStatus.split(" - ");
									toBeSplit[0] = "FOH";
									routingScope.getLots.routedLot[x].currentLotStatus = toBeSplit[0] + " - " + toBeSplit[1];
								}
							}
							*/
							
						}, 100)
						$timeout(function(){focus('routeNoForLot1');},500);
						}
					}
				}
				break;
				case 'signatures':
				if($scope.signatures!=true){
					if(!$("#signatureAccn").hasClass("disabled"))
					{
						var template = ($compile("<div awb-sign></div>"))($scope);
						$("#signatureBody").html(template);
						$scope.signatures=true;
						$timeout(function(){focus('signatureRText');},500);
					}
				}else{
					if(!$("#signatureAccn").hasClass("disabled"))
						focus('signatureRText');
				}
				break;
				case 'comments':
				if($scope.comments!=true){
					if(!$("#commentsBody1").hasClass("disabled"))
					{
						var template = ($compile("<div awb-comments></div>"))($scope);
						$("#commentsBody").html(template);
						$scope.comments=true;
						$timeout(function(){focus('newComment');},500);
					}					
				}else{
					if(!$("#commentsBody1").hasClass("disabled"))
						focus('newComment');
				}
				break;
				case 'documents':
				if($scope.documents!=true){
					if(!$("#documentsAccordion").hasClass("disabled"))
					{
						var template = ($compile("<div awb-documents></div>"))($scope);
						$("#documentsBody").html(template);
						$scope.documents=true;
					}					
				}
				break;
				case 'lotlables':
				if($scope.lotlables!=true){
					if(!$("#lotLabelsAcrdn").hasClass("disabled"))
					{
						var template = ($compile("<div awb-lotlabels></div>"))($scope);
						$("#lotLablesBody").html(template);
						$scope.lotlables=true;
						$timeout(function(){
							focus('consigneeId');
							lotLablesScope.init();
						},500);
					}					
				}
				else
				{
					if(!$("#lotLabelsAcrdn").hasClass("disabled"))
					{
						$timeout(function(){
						lotLablesScope.init();
						},500);
					}					
				}
				break;
				case 'fromshipper':
				if($scope.fromshipper!=false)
				{
					if(manageAwbCntrl.alertCoCFailure){
						//set focus on COC Failure Reason field
						$timeout(function () {$('#cocFailureId .ui-select-focusser').trigger('focus');}, 500);
					} else if(!$("#fromShipperAcrdn").hasClass("disabled")) {
						focus('masterActNumber');
					}
				}
				break;
			}
		}
	    /*Start TO save date on basis of the previous account type*/
		if ($scope.prevAccType==="fromshipper" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="1" && (!$("#fromShipperAcrdn").hasClass("disabled"))) 
	    {
	     	$scope.saveShipperDetails();    
		}
		else if( !manageAwbCntrl.awbSection2Invalid && $scope.prevAccType==="toconsignee" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="2" && (!$("#toConsigneeAcrdn").hasClass("disabled")))
	    {
			if(toConsigneeScope!=''){
			  toConsigneeScope.baseConsignee();
	   	      $scope.saveConsigneeDetails();
			}
		}
		else if($scope.prevAccType==="cargoScreen" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="4" && (!$("#cargoScreeningAcrdn").hasClass("disabled")))
	    {
	   	    $scope.saveScreeningDetails();
		}
		else if($scope.displayDivErrorBarCom != true && typeof($scope.displayDivErrorBarCom)!= "undefined" && $scope.prevAccType==="comments" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="9" && (!$("#commentsBody1").hasClass("disabled")))
	    {
	   	    $scope.saveCommentsDetails();
		}
		else if($scope.prevAccType==="routing" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="5" && $rootScope.isRoutingSave!=true && (!$("#routingAcrdn").hasClass("disabled")))
	    {
	   	    routingScope.saveRouteInfoOnTabOut();//Currently keeping it as commented
		}
		else if($scope.prevAccType==="shipmentdetails" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="3" && (!$("#shipmentDetailsAcrdn").hasClass("disabled")))
	    {
			if($localStorage.isSearchPageCall===true && $rootScope.awbContent.shipmentInfo !== null)
			{
				manageAWBService.getGnaSearch($rootScope.awbContent.awbNumber).then(function(responseData)
				{ 					
					if(responseData.data.shipmentAirwayBill.routeInfo !== undefined && responseData.data.shipmentAirwayBill.routeInfo !== null)
					{  
						$rootScope.awbContent.routeInfo = responseData.data.shipmentAirwayBill.routeInfo;
						var template = ($compile("<div awb-routing ng-cloak></div>"))($scope);
						$("#routingBody").html(template);
						$scope.routing=true;
						$timeout(function () {
							shipmentDetailsScope.baseControl();
							routingScope.initializeGetLots();
							$scope.saveShipmentDetails();
						}, 100);
					}
				});
			}
			else
			{
				if(shipmentDetailsScope!=''){
				 shipmentDetailsScope.baseControl();
				 $scope.saveShipmentDetails();//Currently keeping it as commented
				}
			}
		}
		else if($scope.displayDivErrorBarSig != true && typeof($scope.displayDivErrorBarSig)!= "undefined" && $scope.prevAccType==="signatures" && $scope.isAWBAutoSave!="TRUE" && SectionNo!="7" && (!$("#signatureAccn").hasClass("disabled")))
		{
			$scope.saveSignatureDetails();
		}
		
		/***SET CURRENT OPEN ACCORDION***/
	    if ($scope.prevAccType != accType) 
		{
	    	$scope.prevAccType = accType;
		}
	}
	
	/**UPDATE HEADER STATUS**/
	$scope.updateHeaderStatus=function(data,awbError)
	{
	    if(data)
		{
			if(data.comments.length>0)
			{
				manageAwbCntrl.dispAccHdrErrorCom = false;
				manageAwbCntrl.displayDivErrorBarCom = false;
				$scope.commentsStatus=true;
			}
			if(data.shipmentInfo!='' && data.shipmentInfo!=null && data.shipmentInfo!=undefined && data.shipmentInfo.shipmentlines.length>0)
			{
				manageAwbCntrl.dispAccHdrErrorSd = false;
				manageAwbCntrl.displayDivErrorBarSd = false;
			}
			
			if(data.screening!=null && data.screening.screeningLines.length>0)
			{ 
				manageAwbCntrl.displayDivErrorBarCS=false;
				$scope.screeningStatus=true;
				$scope.totalScreenData={"screeningFailed":""};
				$scope.totalScreenData.screeningFailed=$rootScope.awbContent.screening.screeningFailed;
			}
			
			if(data.receivedFromCustSig.length>0 || data.deliveredToCustSignature.length>0)
			{ 
				manageAwbCntrl.displayDivErrorBarSig=false;
			}
			
			if(data.consignee!=null && data.consignee.consigneeName)
			{ 
				manageAwbCntrl.displayDivErrorBarTc=false;
			}
			
			if(data.lotLabelsPrinted == true)
			{
				
				manageAwbCntrl.dispAccHdrErrorRLotLabels = false;
				manageAwbCntrl.displayDivErrorBarLotLabels = false;
				$rootScope.printState=true;
			}
			if(data.documents.length){
		  
			 manageAwbCntrl.displayDivErrorBarDoc=false; 
			 
			}
		   

			if(data.routeInfo !== null)
			{
				var chkAllLotsRouted = true;
				for(var ct1 = 0; ct1 < data.routeInfo.lots.length; ct1++)
				{
					if(data.routeInfo.lots[ct1].flightLegs !== null && data.routeInfo.lots[ct1].flightLegs !== undefined)
					{
						if(data.routeInfo.lots[ct1].flightLegs.length === 0)
						{
							chkAllLotsRouted = false;
							break;
						}
					}
					else
					{
						chkAllLotsRouted = false;
						break;
					}
				}
				if(chkAllLotsRouted)
				manageAwbCntrl.displayDivErrorBarRt=false;
			}
			manageAwbCntrl.displayDivErrorBarFs=false;
			/***IF AWB ERROR**/
			if(awbError.length>0)
			{
				for(var i=0;i<awbError.length;i++){
					switch(awbError[i]){
						case '1':
						manageAwbCntrl.displayDivErrorBarFs=true;
						break;
						case '2':
						manageAwbCntrl.displayDivErrorBarTc=true;
						break;
						case '3':
						manageAwbCntrl.displayDivErrorBarSd=true;
						break;
						case '4':
						manageAwbCntrl.displayDivErrorBarCS=true;
						break;
					}
					var sectionConstant=[{"key":"0","value":"fromShipper"},{"key":"1","value":"toconsignee"},{"key":"2","value":"shipmentdetails"},
					{"key":"6","value":"signatures"},{"key":"7","value":"lotlables"},{"key":"8","value":"comments"},{"key":"9","value":"documents"},{"key":"3","value":"cargoScreen"},{"key":"4","value":"routing"}];
						angular.forEach(sectionConstant,function(key){
							if(key.key==(parseInt(awbError[0])-1)){
							   if(awbError!=1){
									$scope.collapseSec1=true;
								}
								$scope.prevAccType = "+key.value+";
								$scope.autoSaveData(key.value,key.key);
								$('.accordionWrapper li').removeClass("active");
								$('.accordionWrapper .accordion .accordionBody').slideUp(200);
								
								$timeout(function(){
								
							
								var data={"awbStatus":$rootScope.awbContent.awbStatus,groupName:$localStorage.userObject.authorizationView.roleList};
								manageAWBService.getAWBSectionPrivileges(data).then(function(responseData){ 
								
									for(i=1;i<=10;i++){
										$scope.sectionPrivilege.push("AWB_"+$rootScope.awbContent.awbStatus+"_SECTION"+i+"_UPDATE");
									}		 
									$scope.sectionAWBPrivilege=$.map(angular.copy(responseData.data),$.trim);
									
									if($scope.isSectionCollapse('SECTION'+(parseInt(key.key)+1)+"_")){
									$('.accordionWrapper li:eq('+(parseInt(key.key))+')').addClass("active");
									$('.accordionWrapper .accordion:eq('+(parseInt(key.key))+') .accordionBody').slideDown(200);
												}
								});
								
								
									
									
								},500);
							}
						});
					
				}
			}
			/*** IS ALL STATUS ARE IN GREEN COLOR**/
			$timeout(function(){
				if(manageAwbCntrl.displayDivErrorBarFs==false && manageAwbCntrl.displayDivErrorBarTc==false && manageAwbCntrl.displayDivErrorBarRt==false && manageAwbCntrl.dispAccHdrErrorCom==false && manageAwbCntrl.dispAccHdrErrorSd==false && manageAwbCntrl.displayDivErrorBarCS==false && manageAwbCntrl.displayDivErrorBarSig==false)	   
				{ 
					$scope.collapseSec1=true;
				}
			},500);
		}
	}

	/***AWB HEADER DETAILS***/
	if(!$localStorage.isRedirect && ($localStorage.awbContent=='' || $localStorage.awbContent==undefined))
	{  
		if(!$localStorage.isAWBNumberExit && ($localStorage.isAWBNumber=='' || $localStorage.isAWBNumber==undefined)){
		   var data={"awbNbr":"0"};
			manageAWBService.getAwbHeader(data).success(function(responseData){
		        $scope.headerData=responseData; 
				$localStorage.commonData=responseData;
				$rootScope.tabAwbNumber=$scope.headerData.awbNumber.toString().substr(4,8);
				$scope.headerData.Fullstatus="DRAFT";
			});
		}
		else if($localStorage.isAWBNumberExit && ($localStorage.isAWBNumber!='' && $localStorage.isAWBNumber!=undefined)){
			var data={"awbNbr":parseInt($localStorage.isAWBNumber)};
			manageAWBService.getAwbHeader(data).success(function(responseData){
		        $scope.headerData=responseData; 
				$localStorage.commonData=responseData;
				$rootScope.tabAwbNumber=$scope.headerData.awbNumber.toString().substr(4,8);
				$scope.headerData.Fullstatus="DRAFT";
				$timeout(function(){$localStorage.isAWBNumberExit=false;$localStorage.isAWBNumber=''},4000);
			});
		}
		$localStorage.isRedirect=false;
		$localStorage.isSearchPageCall=false;
		$scope.setAWBHeaderStatus("DRFT");
		$scope.isAWBAutoSave="FALSE";
		//$scope.headerData=referenceAWBData.data;
		//$rootScope.tabAwbNumber=$scope.headerData.awbNumber.toString().substr(4,8);
		//$localStorage.commonData=referenceAWBData.data;
		//$scope.isSectionHasPrivileges("DRFT");
	}
	else{
		if(($localStorage.awbContent!='' || $localStorage.awbContent!=undefined) && angular.isObject($localStorage.awbContent)){
			$rootScope.awbContent=$localStorage.awbContent;
			$localStorage.isSearchPageCall=true;
		}
		$scope.headerData.awbNumber = ($rootScope.awbContent.awbNumber!='')?$rootScope.awbContent.awbNumber:'';
		$scope.headerData.awbStatus=$rootScope.awbContent.awbStatus;
		//$scope.isSectionHasPrivileges($scope.headerData.awbStatus);
		$scope.setAWBHeaderStatus($scope.headerData.awbStatus);
		$scope.isAWBAutoSave=$rootScope.awbContent.isAutoSave;
		/**SHIPPER**/
		if($rootScope.awbContent.shipper!=null && $rootScope.awbContent.shipper.masterActNumber!=null && $rootScope.awbContent.shipper.locationId!=null){
			$rootScope.mastNbr=$rootScope.prependZero($rootScope.awbContent.shipper.masterActNumber,"master");
			$rootScope.locNbr=$rootScope.prependZero($rootScope.awbContent.shipper.locationId,"location");
			$rootScope.tabAwbNumber= $rootScope.awbContent.awbNumber.toString().substr(4,8)+" "+(($rootScope.awbContent.shipper.shipperName!=null)?$rootScope.awbContent.shipper.shipperName:"");
			}else{
			$rootScope.mastNbr=$rootScope.locNbr='';
			$rootScope.tabAwbNumber= $rootScope.awbContent.awbNumber.toString().substr(4,8);
		}
		/**CONSIGNEE**/
		if($rootScope.awbContent.consignee!=null && $rootScope.awbContent.consignee.locationId!=null && $rootScope.awbContent.consignee.consigneeId!=null){
			$rootScope.locConsigneeNbr=$rootScope.prependZero($rootScope.awbContent.consignee.locationId,"location");
			$rootScope.mastConsigneeNbr=$rootScope.prependZero($rootScope.awbContent.consignee.consigneeId,"master");
			}else{
			$rootScope.locConsigneeNbr=$rootScope.mastConsigneeNbr='';
		}
		$scope.headerData.origin=(($rootScope.awbContent.shipmentInfo==null || $rootScope.awbContent.shipmentInfo.origin==null)? '':$rootScope.awbContent.shipmentInfo.origin.code);
		$scope.headerData.destination=(($rootScope.awbContent.shipmentInfo==null || $rootScope.awbContent.shipmentInfo.destination==null)? '':$rootScope.awbContent.shipmentInfo.destination.code);
		$scope.headerData.serviceLevel=(($rootScope.awbContent.shipmentInfo==null || $rootScope.awbContent.shipmentInfo.serviceLevel==null)? '':$rootScope.awbContent.shipmentInfo.serviceLevel.code);
		//$timeout(function(){
		$scope.updateHeaderStatus($rootScope.awbContent,$rootScope.awbContent.awbError)
		//}, 200)
		$scope.tenderDateGeneration=($rootScope.awbContent.tenderDate!="undefined" && $rootScope.awbContent.tenderDate!='')?$rootScope.awbContent.tenderDate:'';
		var origin='';
		/**CALL SCREENING DATA**/
		if($rootScope.awbContent.shipmentInfo!=null)
		{
			origin=($rootScope.awbContent.shipmentInfo.origin!=null)?$rootScope.awbContent.shipmentInfo.origin.code:'';
		}
		else
		{
			origin=$localStorage.userObject.swaLocation;
		}
		var reqData={"origin":origin};
		if(origin!=null){
			manageAWBService.getScreeningData(reqData).success(function(respData){
				if(angular.isObject(respData.refValueList[0]))
				{
					$rootScope.TempscreeningMethod=angular.copy(respData.refValueList);
					}else{
					$rootScope.TempscreeningMethod='';
				}
			});
		}
		//$localStorage.isSearchPageCall= false;
	}  
	$rootScope.awbNo=$scope.headerData.awbNumber;	
	
	/***REFERNECE DATA DROPDOWNS***/
	if(referenceDataDropDown && angular.isObject(referenceDataDropDown.data.referenceDataHolder.country[0]))
	{
		$localStorage.countryOption=JSON.parse(referenceDataDropDown.data.referenceDataHolder.country[0].value);
		$localStorage.csStatesOption=JSON.parse(referenceDataDropDown.data.referenceDataHolder.caStates[0].value);
		$localStorage.usStatesOption=JSON.parse(referenceDataDropDown.data.referenceDataHolder.usStates[0].value);
		$localStorage.secondarySecurityOption=JSON.parse(referenceDataDropDown.data.referenceDataHolder.secondarySecurity[0].value);
		$localStorage.cocFailureOptions=JSON.parse(referenceDataDropDown.data.referenceDataHolder.cocFailure[0].value);
		$localStorage.altsecurityOptions=JSON.parse(referenceDataDropDown.data.referenceDataHolder.altSecurity[0].value);
		$localStorage.serviceData = JSON.parse(referenceDataDropDown.data.referenceDataHolder.serviceLevel[0].value);
		$localStorage.specialData  = JSON.parse(referenceDataDropDown.data.referenceDataHolder.specialHandlingType[0].value);
		$scope.voidReasonData  = JSON.parse(referenceDataDropDown.data.referenceDataHolder.awbVoidReason[0].value);
		/**CALL SCREENING DATA**/
		var reqData={"origin":$localStorage.userObject.swaLocation};
		if($localStorage.userObject.swaLocation!=null && !$localStorage.isSearchPageCall){
			manageAWBService.getScreeningData(reqData).success(function(respData){
				if(angular.isObject(respData.refValueList[0]))
				{
					$rootScope.TempscreeningMethod=angular.copy(respData.refValueList);
					}else{
					$rootScope.TempscreeningMethod='';
				}
			});
		}
	}
	$scope.disablePage = function()
	{
		$(document).find("select").attr("disabled", "disabled");
		$(document).find("input").attr("disabled", "disabled");
	}
	
	/**SLIDE**/
	$scope.autoConsigneeAppend=function()
	{ 
		var template = ($compile("<div awb-to-consignee></div>"))($scope);
		$("#toConsigneeBody").html(template);
	}
	//setTimeout(function(){$scope.autoConsigneeAppend();},1000); 
	
	$scope.openAccordionPanel = function(accType)
	{
		/********************
			//Call the save accordion web service method for the accordion that was open with unsaved changes
			
		********************/
		if($scope.shipmentDetailsBase)
		{
			//shipmentDetailsScope.baseControl();
			//routingScope.getLots = shipmentDetailsScope.generateRoutingData();
			//routingScope.updateAllArrays();
			$scope.shipmentDetailsBase = false;
		}
		
		
		if(accType === "routing")
		{
			//Call the web service to fetch the routing info
			
			/*
				$scope.displayDivErrorBarRt = true;
				$scope.dispAccHdrErrorRt = true;
				$scope.fieldNameRt = "All lots are not routed";
			*/
			//routingScope.onRoutePanelOpen();
			
		}
		else if(accType === "shipmentdetails")
		{
			$scope.shipmentDetailsBase = true;
		}
		$timeout(function () {
			if($localStorage.viewMode)
			{
				$scope.disablePage();
			}
		}, 10);
	}
	
	$rootScope.$on('emitEvent', function(e, data) {
		$scope.dispErrorToConsignee = data;
		e.stopPropagation();
	});
	
	$rootScope.$on('errorEvent', function(e, data) {	
		if(data === '')
		{
			$scope.displayDivErrorBar = false;
			$scope.fieldName = '';
			$scope.dispAccHdrError = false;
		}
		else
		{
			$scope.displayDivErrorBar = true;
			$scope.fieldName = 'Invalid ' + data;
			$scope.dispAccHdrError = true;
		} 
		e.stopPropagation();
	});
	
	$rootScope.$on('emitEventSd', function(e, data) {
		$scope.dispErrorShipmentDetails = data;
		e.stopPropagation();
	});
	
	$rootScope.$on('errorEventSd', function(e, data) {	
		if(data === '')
		{
			$scope.displayDivErrorBarSd = false;
			$scope.fieldNameSd = '';
			$scope.dispAccHdrErrorSd = false;
		}
		else
		{
			$scope.displayDivErrorBarSd = true;
			$scope.fieldNameSd = 'Invalid ' + data;
			$scope.dispAccHdrErrorSd = true;
		}
		e.stopPropagation();   
	});
	
	$scope.saveAwbDetails = function()
	{
		var tmpString = '';
		tmpString = tmpString + "To Consignee: " + JSON.stringify($scope.saveAwb.toConsignee);
		tmpString = tmpString + "Shipment Details: " + JSON.stringify($scope.saveAwb.shipmentDetails);
		window.alert(tmpString);
	}
	
	$(".toggleDefault" ).on('click',function(){
		var clickedTR = $(this).parent().parent();
		var nToggle; var toggleState;
		var second = 'secondtoggle', third = 'thirdtoggle';
		
		if($(this).hasClass("minus")) { toggleState="none"; $(this).removeClass("minus");}
		else { toggleState="table-row"; $(this).addClass("minus"); }
		
		function findToggle(nToggle,toggleState){
			if(clickedTR.next().attr(nToggle)=="")
			{
				clickedTR.next().css('display',toggleState);
				clickedTR = clickedTR.next();
				findToggle(nToggle,toggleState);
			}
		}
		
		if(clickedTR.attr('firsttoggle')==""){
			findToggle(second,toggleState);
		}
		else if(clickedTR.attr('secondtoggle')==""){
			findToggle(third,toggleState);
		}
		
	});
	
	$('.tabWrapper .nav-tabs li').on('click',function () {
		
		if($(this).hasClass("disable")==0)
		{
			index = $(this).index();
			$(".tabWrapper .nav-tabs li").removeClass('active');
			$(".tabWrapper .nav-tabs li:eq("+index+")").addClass('active');
			$(".tabWrapper .tab-content .tab-pane").hide();
			$(".tabWrapper .tab-content .tab-pane:eq("+index+")").show();             
		}
	});
	
	$scope.fromshipper = true;
	$scope.showErrorBar=function(formStatus)
	{
		$scope.showBar=(formStatus==true)?true:false;    
	}
	
	$scope.awbhistory=false;
	$scope.awbHistoryClick = function(sectionId)
	{
		$scope.awbhistory=true;
		setTimeout(function()
		{
			awbHistoryScope.awbHistorySectionClick(sectionId);
			awbHistoryScope.focusTimeStamp();
		}, 100);
	}	
	
	
	$scope.generateCommentsTemplateForReRouting = function(reroutingReason, htmlGenerated)
	{
		if(htmlGenerated === false)
		{
			var template = ($compile("<div awb-comments></div>"))($scope);
			$("#commentsBody").html(template);
			$scope.comments=true;
			$timeout(function()
			{
				commentsScope.commentText = reroutingReason;
				commentsScope.saveComments();
			},100);
		}
		else
		{
			commentsScope.commentText = reroutingReason;
			commentsScope.saveComments();
		}			
		$timeout(function(){focus('newComment');},500);	
	}
	
	$scope.assignFocusToLots = function(nextLot)
	{
		$timeout(function(){focus('routeNoForLot' + nextLot);},500);
	}
	
	$scope.assignFocusToElements = function(focusElement)
	{
		$timeout(function(){focus(focusElement);},500);
	}
	
	/**SAVE METHOD***/
	$scope.saveShipperDetails=function(callFrom)
	{  
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var awbNumber = $scope.headerData.awbNumber;
			var ccsfId="";
			if(manageAwbCntrl.fromShipperData!="undefined" && manageAwbCntrl.fromShipperData!=null)
			{
				ccsfId=manageAwbCntrl.fromShipperData.ccsf1+""+manageAwbCntrl.fromShipperData.ccsf2+""+manageAwbCntrl.fromShipperData.ccsf3+""+manageAwbCntrl.fromShipperData.ccsf4+""+manageAwbCntrl.fromShipperData.ccsf5+""+manageAwbCntrl.fromShipperData.ccsf6;
				ccsfId=(ccsfId=="undefinedundefinedundefinedundefinedundefinedundefined")?"":ccsfId;
			}
			else
			{
				ccsfId="";
			}
			var data={"awbNumber":awbNumber,"awbPrefix":"526","awbError":($scope.displayDivErrorBarFs==true)?"1":"",
				"shipper":{"masterActNumber":manageAwbCntrl.fromShipperData.masterActNumber,
					"locationId":manageAwbCntrl.fromShipperData.locationId,
					"primarySecurityType":manageAwbCntrl.fromShipperData.primarySecurityType,"shipperName":manageAwbCntrl.fromShipperData.shipperName,
					"shipperAddress":manageAwbCntrl.fromShipperData.shipperAddress,"iacNumber":manageAwbCntrl.fromShipperData.iacNumber,
					"ccsfId":ccsfId,"employeeNumber":manageAwbCntrl.fromShipperData.employeeNumber,
					"employeeHireDate":manageAwbCntrl.fromShipperData.employeeHireDate,
					"idVerificationNumber":manageAwbCntrl.fromShipperData.idVerificationNumber,
					"shipperReference":manageAwbCntrl.fromShipperData.shipperReference,
					"cocFailure":(manageAwbCntrl.fromShipperData.cocFailure!="YES")?false:true,
					"cocFailureReason":(manageAwbCntrl.fromShipperData.cocFailureReason!="undefined" && manageAwbCntrl.fromShipperData.cocFailureReason!="")?manageAwbCntrl.fromShipperData.cocFailureReason:{}
				}
			};
			manageAWBService.saveFromShipperData(data).then(function(responseData)
			{  
			   if(responseData.data.airwaybill!=null){ 
				$scope.awbContent.versionNumber= responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				$scope.saveShipper=true;
				$rootScope.assignedShipperId=false;
				$scope.assignedShipperMsg='';
				$rootScope.mastNbr=$rootScope.prependZero(responseData.data.airwaybill.shipper.masterActNumber,"master");
				$rootScope.locNbr=$rootScope.prependZero(responseData.data.airwaybill.shipper.locationId,"location");
				//$scope.changeHeaderStatus();
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				}else{
				  $rootScope.assignedShipperId=true;
				  $scope.displayDivErrorBarFs=true;
				  $scope.assignedShipperMsg=responseData.data.errorMessage;
				}
				changeOrNotManageawb = 0;
			});
		}
	}
	
	$scope.getShipmentObjectForSave = function()
	{
		shipmentDetailsScope.baseControl();
		$scope.saveShipmentDetails();
		return $scope.mainShipmentObject;
	}
	
	/**Save Shipment Details Method**/
	$scope.saveShipmentDetails =function()
	{
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			$scope.addingNewLine = false;
			$scope.editingSingleLineWithNoPieces = false;
			var data={
				"awbNumber": $scope.headerData.awbNumber,"awbError":($scope.displayDivErrorBarSd==true)?"3":"",
				"awbPrefix": "526",
				"shipment":
				saveShipmentScope
				
			}
			
			////////////////////////Adding the below code to update the lots when new lines are added////////////////////
			if(routingScope.getLots === undefined && shipmentDetailsScope.generatedLots.routedLot !== undefined)
			{
				shipmentDetailsScope.generatedLots = {};
				shipmentDetailsScope.generatedLots = shipmentDetailsScope.generateRoutingData();
			}
			else if(routingScope.getLots !== undefined)
			{
				if(routingScope.getLots.routedLot.length === 1 && routingScope.getLots.routedLot[0].fltNbr === undefined)
				{
					routingScope.getLots = {};
					routingScope.getLots = shipmentDetailsScope.generateRoutingData();
					routingScope.updateAllArrays();
				}
				else if(routingScope.getLots.routedLot.length === 1 && routingScope.getLots.routedLot[0].fltNbr !== undefined)
				{
					//if($("#shipmentDetailsBody").attr("style") === "display: block;")
					//{
						var newShipmentLine = [];
						var editedShipmentLine = [];
						var deletedShipmentLine = [];
						var maxLotNo = 0;
						var previousPiecesCount = 0;
						for(var a = 0; a < shipmentDetailsScope.shipmentInfo.shipmentlines.length; a++)
						{
							for(var b = 0; b < parseInt(shipmentDetailsScope.shipmentInfo.shipmentlines[a].noOfPieces); b++)
							{
								previousPiecesCount = previousPiecesCount + 1;
							}
						}
						for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
						{
							var decideForDupLine = false;
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								var breakFromRouteCounter = false;
								maxLotNo = maxLotNo < routingScope.getLots.routedLot[cntr1].lotNumber? routingScope.getLots.routedLot[cntr1].lotNumber: maxLotNo;
								for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
								{
									if(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber !== routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo)
									{
										decideForDupLine = false;
									}
									else
									{
										decideForDupLine = true;
										breakFromRouteCounter = true;
										break;
									}
								}
								if(breakFromRouteCounter)
								break;
							}
							if(!decideForDupLine)
							newShipmentLine.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
							else
							editedShipmentLine.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
						}
						
						var checkShipmentLines = [];
						var checkLotLines = [];
						for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
						{
							checkShipmentLines.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
						}
						for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
						{
							for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
							{
								var isLinePresent = false;
								for(var cntr3 = 0; cntr3 < checkLotLines.length; cntr3++)
								{
									if(checkLotLines[cntr3].uniqueLineNo === routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo)
									isLinePresent = true;
								}
								if(!isLinePresent)
								checkLotLines.push(routingScope.getLots.routedLot[cntr1].lines[cntr2]);
							}
						}
						checkShipmentLines = checkShipmentLines.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
						checkLotLines = checkLotLines.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
						
						if(newShipmentLine.length > 0)
						{
							$scope.addingNewLine = true;
							newShipmentLine = newShipmentLine.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
							for(var i = 0; i < newShipmentLine.length; i++)
							{
								newShipmentLine[i].newLineNumber = i + 1;
								newShipmentLine[i].commodityCode = {};
								newShipmentLine[i].commodityCode.commodityCode = shipmentDetailsScope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode; 							
							}
							var newlyGeneratedLot = {};
							newlyGeneratedLot = shipmentDetailsScope.generateRoutingDataUpdateLots(newShipmentLine, maxLotNo, previousPiecesCount);
							routingScope.getLots.routedLot.push(newlyGeneratedLot.routedLot[0]);
							
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								if((routingScope.getLots.routedLot[cntr1].currentLotStatus === "FOH" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "SCRN" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "RCS") && (newlyGeneratedLot.routedLot[0].lotNumber === routingScope.getLots.routedLot[cntr1].lotNumber))
								routingScope.getLots.routedLot[cntr1].currentLotStatus = "FOH";
								
								if(routingScope.getLots.routedLot[cntr1].contentDesc === "" && cntr1 !== 0)
								routingScope.getLots.routedLot[cntr1].contentDesc = routingScope.getLots.routedLot[0].contentDesc;
							}
							
							routingScope.updateAllArrays();
							$("#searchRoutePanel").removeClass("ng-hide");
							$("#routesSummary").removeClass("ng-hide");
							$("#routingBody").attr("style", "display:block;");
							routingScope.areLotsRouted = false;
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = "";
						}
						else if (editedShipmentLine.length > 0 && checkShipmentLines.length === checkLotLines.length)
						{
							editedShipmentLine = editedShipmentLine.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
							var newlyUpdatedLot = {};
							newlyUpdatedLot = shipmentDetailsScope.generateRoutingData();
							newlyUpdatedLot.routedLot[0].currentLotStatus = angular.copy(routingScope.getLots.routedLot[0].currentLotStatus);
							newlyUpdatedLot.routedLot[0].flightLegs = angular.copy(routingScope.getLots.routedLot[0].flightLegs);
							newlyUpdatedLot.routedLot[0].fltNbr = angular.copy(routingScope.getLots.routedLot[0].fltNbr);
							newlyUpdatedLot.routedLot[0].lotsOrig = angular.copy(routingScope.getLots.routedLot[0].lotsOrig);
							newlyUpdatedLot.routedLot[0].destination = angular.copy(routingScope.getLots.routedLot[0].destination);
							newlyUpdatedLot.routedLot[0].scheduledDepartureDate = angular.copy(routingScope.getLots.routedLot[0].scheduledDepartureDate);
							newlyUpdatedLot.routedLot[0].scheduledArrivalTime = angular.copy(routingScope.getLots.routedLot[0].scheduledArrivalTime);
							newlyUpdatedLot.routedLot[0].estimatedArrivalTime = angular.copy(routingScope.getLots.routedLot[0].estimatedArrivalTime);
							
							var isOriginChanged = false;
							var isDestinationChanged = false;
							//var isShcChanged = false;
							var isServiceLevelChanged = false;
							
							if(shipmentDetailsScope.shipmentInfo.origin.code !== routingScope.getLots.routedLot[0].lotsOrig)
								isOriginChanged = true;
							if(shipmentDetailsScope.shipmentInfo.destination.code !== routingScope.getLots.routedLot[0].destination)
								isDestinationChanged = true;
							if($rootScope.awbContent.serviceLevel.code !== shipmentDetailsScope.shipmentInfo.serviceLevel)
								isServiceLevelChanged = true;
							
							if(newlyUpdatedLot.routedLot[0].noOfPieces !== routingScope.getLots.routedLot[0].noOfPieces || newlyUpdatedLot.routedLot[0].actualWeight !== routingScope.getLots.routedLot[0].actualWeight || newlyUpdatedLot.routedLot[0].volume !== routingScope.getLots.routedLot[0].volume || newlyUpdatedLot.routedLot[0].commodityCode !== routingScope.getLots.routedLot[0].commodityCode || isOriginChanged === true || isDestinationChanged === true || isServiceLevelChanged === true)
							{
								routingScope.getLots = {};
								routingScope.getLots = angular.copy(newlyUpdatedLot);
								
								// if(routingScope.getLots.routedLot[0].currentLotStatus === "FOH" || routingScope.getLots.routedLot[0].currentLotStatus === "SCRN" || routingScope.getLots.routedLot[0].currentLotStatus === "RCS")
								// routingScope.getLots.routedLot[0].currentLotStatus = "FOH";
								
								routingScope.updateAllArrays();
								
								// manageAwbCntrl.displayDivErrorBarRt = true;
								// manageAwbCntrl.dispAccHdrErrorRt = true;
								// manageAwbCntrl.fieldNameRt = "";
								setTimeout(function()
								{ 
									if(routingScope.getLots.routedLot[0].noOfPieces !== "")
									{
										if($localStorage.isSearchPageCall===true)
										{
											routingScope.doConditionalSelectRoute('shipment');
										}
										else
										{
											routingScope.doConditionalSelectRoute('routing');
											routingScope.selectRoute('shipment');
										}
									}
									else
									{
										$scope.editingSingleLineWithNoPieces = true;
									}
									//$("tr[name='lots" + routingScope.getLots.routedLot[0].lotNumber.toString() + "']").find('td:eq(0)').removeClass("rowStatusPositive");
									//$("tr[name='lots" + routingScope.getLots.routedLot[0].lotNumber.toString() + "']").find('td:eq(0)').addClass("rowStatusAlert");							
								}, 100);
							}
						}
						else
						{
							var lotsToBeRemoved = [];
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								var linesTobeRemovedFromLot = [];
								for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
								{
									var decideForDupLine = false;
									for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
									{
										if(routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo !== shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber)
										{
											decideForDupLine = false;
										}
										else
										{
											decideForDupLine = true;
											break;
										}
									}
									if(!decideForDupLine)
									linesTobeRemovedFromLot.push(routingScope.getLots.routedLot[cntr1].lines[cntr2]);
								}
								linesTobeRemovedFromLot = linesTobeRemovedFromLot.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
								routingScope.getLots.routedLot[cntr1].lines = routingScope.getLots.routedLot[cntr1].lines.filter(function(x) { return linesTobeRemovedFromLot.indexOf(x) < 0 });
								if(routingScope.getLots.routedLot[cntr1].lines.length === 0)
								{
									lotsToBeRemoved.push(routingScope.getLots.routedLot[cntr1]);
								}
							}
							routingScope.getLots.routedLot = routingScope.getLots.routedLot.filter(function(x) { return lotsToBeRemoved.indexOf(x) < 0 });
							
							// for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							// {
							// if(routingScope.getLots.routedLot[cntr1].currentLotStatus === "FOH" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "SCRN" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "RCS")
							// routingScope.getLots.routedLot[cntr1].currentLotStatus = "FOH";
							// }
							
							routingScope.updateAllArrays();
						}
					//}
				}
				else if (routingScope.getLots.routedLot.length > 1)
				{
					//if($("#shipmentDetailsBody").attr("style") === "display: block;")
					//{
						var newShipmentLine = [];
						var editedShipmentLine = [];
						var deletedShipmentLine = [];
						var maxLotNo = 0;
						var previousPiecesCount = 0;
						for(var a = 0; a < shipmentDetailsScope.shipmentInfo.shipmentlines.length; a++)
						{
							for(var b = 0; b < parseInt(shipmentDetailsScope.shipmentInfo.shipmentlines[a].noOfPieces); b++)
							{
								previousPiecesCount = previousPiecesCount + 1;
							}
						}
						for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
						{
							var decideForDupLine = false;
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								var breakFromRouteCounter = false;
								maxLotNo = maxLotNo < routingScope.getLots.routedLot[cntr1].lotNumber? routingScope.getLots.routedLot[cntr1].lotNumber: maxLotNo;
								for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
								{
									if(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber !== routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo)
									{
										decideForDupLine = false;
									}
									else
									{
										decideForDupLine = true;
										breakFromRouteCounter = true;
										break;
									}
								}
								if(breakFromRouteCounter)
								break;
							}
							if(!decideForDupLine)
							newShipmentLine.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
							else
							editedShipmentLine.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
						}
						
						var checkShipmentLines = [];
						var checkLotLines = [];
						for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
						{
							checkShipmentLines.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
						}
						for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
						{
							for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
							{
								var isLinePresent = false;
								for(var cntr3 = 0; cntr3 < checkLotLines.length; cntr3++)
								{
									if(checkLotLines[cntr3].uniqueLineNo === routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo)
									isLinePresent = true;
								}
								if(!isLinePresent)
								checkLotLines.push(routingScope.getLots.routedLot[cntr1].lines[cntr2]);
							}
						}
						checkShipmentLines = checkShipmentLines.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
						checkLotLines = checkLotLines.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
						
						if(newShipmentLine.length > 0)
						{
							$scope.addingNewLine = true;
							newShipmentLine = newShipmentLine.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
							for(var i = 0; i < newShipmentLine.length; i++)
							{
								newShipmentLine[i].newLineNumber = i + 1;
								newShipmentLine[i].commodityCode = {};
								newShipmentLine[i].commodityCode.commodityCode = shipmentDetailsScope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode;
							}
							var newlyGeneratedLot = {};
							newlyGeneratedLot = shipmentDetailsScope.generateRoutingDataUpdateLots(newShipmentLine, maxLotNo, previousPiecesCount);
							routingScope.getLots.routedLot.push(newlyGeneratedLot.routedLot[0]);
							
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								if((routingScope.getLots.routedLot[cntr1].currentLotStatus === "FOH" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "SCRN" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "RCS") && (newlyGeneratedLot.routedLot[0].lotNumber === routingScope.getLots.routedLot[cntr1].lotNumber))
								routingScope.getLots.routedLot[cntr1].currentLotStatus = "FOH";
								
								if(routingScope.getLots.routedLot[cntr1].contentDesc === "" && cntr1 !== 0)
								routingScope.getLots.routedLot[cntr1].contentDesc = routingScope.getLots.routedLot[0].contentDesc;
							}
							
							routingScope.updateAllArrays();
							
							$("#searchRoutePanel").removeClass("ng-hide");
							$("#routesSummary").removeClass("ng-hide");
							$("#routingBody").attr("style", "display:block;");
							routingScope.areLotsRouted = false;
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = "";
						}
						else if (editedShipmentLine.length > 0 && checkShipmentLines.length === checkLotLines.length)
						{
							var routedLotsUpdated = [];
							var searchForUniqueLine = [];
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								var isTheLotRouted = false;
								for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
								{
									for(var cntr4 = 0; cntr4 < editedShipmentLine.length; cntr4++)
									{
										if(routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo === editedShipmentLine[cntr4].lineNumber)
										{
											for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
											{
												var breakShipmentCntr = false;
												if(routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo === shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber)
												{
													var isLinePresent = false;
													for(var cntr3 = 0; cntr3 < searchForUniqueLine.length; cntr3++)
													{
														if(searchForUniqueLine[cntr3] === shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber)
														{
															isLinePresent = true;
															break;
														}
													}
													if(!isLinePresent)
													{
														searchForUniqueLine.push(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber);
														routingScope.getLots.routedLot[cntr1].lines[cntr2] = angular.copy(shipmentDetailsScope.shipmentInfo.shipmentlines[cntr]);
														breakShipmentCntr = true;
														isTheLotRouted = true;
													}
												}
												if(breakShipmentCntr)
													break;
											}
											break;
										}
									}
								}
								if(isTheLotRouted && routingScope.getLots.routedLot[cntr1].fltNbr !== undefined)
									routedLotsUpdated.push(routingScope.getLots.routedLot[cntr1]);
							}
							routingScope.updateAllArrays();
							if(routedLotsUpdated.length !== 0)
							{
								if($localStorage.isSearchPageCall===true)
								{
									routingScope.doConditionalSelectRoute('shipment');
								}
								else
								{
									routingScope.doConditionalSelectRoute('routing');
									routingScope.selectRoute('shipment');
								}
							}
						}
						else
						{
							var lotsToBeRemoved = [];
							for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							{
								var linesTobeRemovedFromLot = [];
								for(var cntr2 = 0; cntr2 < routingScope.getLots.routedLot[cntr1].lines.length; cntr2++)
								{
									var decideForDupLine = false;
									for(var cntr = 0; cntr < shipmentDetailsScope.shipmentInfo.shipmentlines.length; cntr ++)
									{
										if(routingScope.getLots.routedLot[cntr1].lines[cntr2].uniqueLineNo !== shipmentDetailsScope.shipmentInfo.shipmentlines[cntr].lineNumber)
										{
											decideForDupLine = false;
										}
										else
										{
											decideForDupLine = true;
											break;
										}
									}
									if(!decideForDupLine)
									linesTobeRemovedFromLot.push(routingScope.getLots.routedLot[cntr1].lines[cntr2]);
								}
								linesTobeRemovedFromLot = linesTobeRemovedFromLot.filter(function(item, i, ar){ return ar.indexOf(item) === i; });
								routingScope.getLots.routedLot[cntr1].lines = routingScope.getLots.routedLot[cntr1].lines.filter(function(x) { return linesTobeRemovedFromLot.indexOf(x) < 0 });
								if(routingScope.getLots.routedLot[cntr1].lines.length === 0)
								{
									lotsToBeRemoved.push(routingScope.getLots.routedLot[cntr1]);
								}
							}
							routingScope.getLots.routedLot = routingScope.getLots.routedLot.filter(function(x) { return lotsToBeRemoved.indexOf(x) < 0 });
							
							// for(var cntr1 = 0; cntr1 < routingScope.getLots.routedLot.length; cntr1++)
							// {
							// if(routingScope.getLots.routedLot[cntr1].currentLotStatus === "FOH" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "SCRN" || routingScope.getLots.routedLot[cntr1].currentLotStatus === "RCS")
							// routingScope.getLots.routedLot[cntr1].currentLotStatus = "FOH";
							// }
							
							routingScope.updateAllArrays();
						}
					//}
				}
				shipmentDetailsScope.generatedLots = angular.copy(routingScope.getLots);
				for(var ct1 = 0; ct1 < routingScope.getLots.routedLot.length; ct1++)
				{
					routingScope.getLots.routedLot[ct1].actualWeightKg = routingScope.lbsToKgConversion(routingScope.getLots.routedLot[ct1].actualWeight);
				}
			}			
			else
			{
				if($scope.displayDivErrorBarRt === false)
				$scope.displayDivErrorBarRt = true;
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(shipmentDetailsScope.generatedLots.routedLot === undefined)
			{
				shipmentDetailsScope.generatedLots = {};
				shipmentDetailsScope.generatedLots = shipmentDetailsScope.generateRoutingData();
			}
			var tempRequestInfo = angular.copy(shipmentDetailsScope.generatedLots);	
			if(tempRequestInfo.lots !== undefined)
			delete tempRequestInfo["lots"];	
			angular.forEach(tempRequestInfo.routedLot, function(data0){
				var chkForBlankPiecesInLine = [];
				for(var a = 0; a < data0.lines.length; a++)
				{
					if(data0.lines[a].noOfPieces > 0)
						chkForBlankPiecesInLine.push(angular.copy(data0.lines[a]));
				}
				data0.lines = angular.copy(chkForBlankPiecesInLine);
				//if(data0.rowType !== undefined)
				delete data0["rowType"];
				
				delete data0["destination"];				
				delete data0["estimatedArrivalTime"];								
				delete data0["lotsOrig"];				
				delete data0["scheduledArrivalTime"];				
				delete data0["scheduledDepartureDate"];
				delete data0["scheduledDepartureTime"];
				delete data0["scheduledArrivalDate"];
				delete data0["estimatedArrivalDate"];
				delete data0["estimatedDepartureDate"];
				delete data0["estimatedDepartureTime"];
				delete data0["fltNbr"];
				delete data0["contentDesc"];
				delete data0["actualWeightKg"];
				if(data0.currentLotStatus.toString().indexOf(" ") !== -1)
				data0.currentLotStatus = data0.currentLotStatus.toString().split(" ")[0];
				
				angular.forEach(data0.flightLegs, function(data3){
					if(data3.$$hashKey !== undefined)
					delete data3["$$hashKey"];
					if(data3.acType !== undefined)
					delete data3["acType"];
					if(data3.authorizedFlightCapacity !== undefined)
					delete data3["authorizedFlightCapacity"];
					if(data3.availableBucketCapacity !== undefined)
					delete data3["availableBucketCapacity"];
					if(data3.availableFlightCapacity !== undefined)
					delete data3["availableFlightCapacity"];
					if(data3.bookedBucketCapacity !== undefined)
					delete data3["bookedBucketCapacity"];
					
					if(data3.noOfConnections !== undefined)
					delete data3["noOfConnections"];
					if(data3.noOfLegs !== undefined)
					delete data3["noOfLegs"];
					if(data3.routeId !== undefined)
					delete data3["routeId"];
					if(data3.transitTime !== undefined)
					delete data3["transitTime"];
					if(data3.limitationReasons !== undefined)
					delete data3["limitationReasons"];
					
					delete data3["arrivalRecoveryTime"];
					delete data3["limitationReasons"];
					delete data3["outsideOperatingHours"];
					
					data3.seqNum = data3.routeSeqNumber;
					delete data3["routeSeqNumber"];
					data3.schedDepDate = data3.scheduledDepartureDate;
					delete data3["scheduledDepartureDate"];
					data3.estimatedDepDate = data3.estimatedDepartureDate;
					delete data3["estimatedDepartureDate"];
					data3.schedArrDate = data3.scheduledArrivalDate;
					delete data3["scheduledArrivalDate"];
					data3.estimatedArrDate = data3.estimatedArrivalDate;
					delete data3["estimatedArrivalDate"];
					data3.scheduledDepTime = data3.scheduledDepartureTime;
					delete data3["scheduledDepartureTime"];
					data3.scheduledArrTime = data3.scheduledArrivalTime;
					delete data3["scheduledArrivalTime"];
					data3.estArrTime = data3.estimatedArrivalTime;
					delete data3["estimatedArrivalTime"];
					data3.estDepTime = data3.estimatedDepartureTime;
					delete data3["estimatedDepartureTime"];
					data3.carrierCode = {code: data3.carrier, name: data3.carrier, description: data3.carrier};
					delete data3["carrier"];
					data3.origin = {code: data3.origin, name: data3.origin, description: data3.origin};
					data3.destination = {code: data3.destination, name: data3.destination, description: data3.destination};
					
				});
				
				var maxLine = 0;
				angular.forEach(data0.lines, function(data1){
					if(data1.$$hashKey !== undefined)
					delete data1["$$hashKey"];
					
					var shcArray = data1.lineSHC.split(",");
					var shcArrayList = [];
					for(var ct = 0; ct < shcArray.length; ct++)
					{
						shcArrayList.push({code: shcArray[ct], name: shcArray[ct], description: shcArray[ct]});
					}
					if(shcArrayList.length > 0)
					data1.specialHandlingCode = shcArrayList;
					
					if(data1.lineSHC !== undefined)
					delete data1["lineSHC"];
					if(data1.lineOrig !== undefined)
					delete data1["lineOrig"];
					if(data1.lineDest !== undefined)
					delete data1["lineDest"];
					if(data1.lotNumber !== undefined)
					delete data1["lotNumber"];
					//if(data1.uniqueLineNo !== undefined)
					//delete data1["uniqueLineNo"];
					
					//Remove the below lines when toggling between LBS to KG is done
					if(data1.lineActWtKG !== undefined)
					delete data1["lineActWtKG"];
					if(data1.lineActWtLBS !== undefined)
					delete data1["lineActWtLBS"];
					
					if(data1.seqNum.toString().indexOf("LN") !== -1)
					data1.seqNum = data1.seqNum.toString().replace("LN", "");
					
					////////////////////////
					
					if(data1.contentDesc === undefined)
					{
						data1.contentDesc = {code: "", name: "", description: ""};
					}
					else
					{
						data1.contentDesc = {code: data1.contentDesc, name: data1.contentDesc, description: data1.contentDesc};
					}
					
					angular.forEach(data1.pieces, function(data2){
						if(data2.$$hashKey !== undefined)
						delete data2["$$hashKey"];
						if(data2.seqNum !== undefined)
						delete data2["seqNum"];
						
						//Remove the below lines when toggling between LBS to KG is done
						if(data2.piecesActWtKg !== undefined)
						{
							data2.actualWeightKg = angular.copy(data2.piecesActWtKg);
							delete data2["piecesActWtKg"];
						}
						if(data2.piecesActWtLbs !== undefined)
						delete data2["piecesActWtLbs"];	
						///////////////////////////
						if(data2.displayName.toString().indexOf("PCID") === -1)
						data2.displayName = "PCID " + data2.displayName.toString();
					});
				});
			});
			
			data.routedLot = angular.copy(tempRequestInfo.routedLot);
			
			angular.forEach(data.shipment.shipmentlines, function(data5){
				delete data5["newLineNumber"];
			});
			
			$scope.mainShipmentObject = angular.copy(data); 
			delete $scope.mainShipmentObject["awbNumber"];
			delete $scope.mainShipmentObject["awbPrefix"];
			
			manageAWBService.saveShipmentDetailsData(data).then(function(responseData)
			{
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				
				console.log("saved shipment Details");
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				$scope.saveShippment=true;
				setTimeout(function()
				{
					if($scope.addingNewLine === true)
					{
						$scope.toPutNoOfPiecesInFocus = true;
						$("#searchRoutePanel").removeClass("ng-hide");
						$("#routesSummary").removeClass("ng-hide");
						//$("#routingBody").attr("style", "display:block;");
						routingScope.areLotsRouted = false;
						for(var x = 0; x < routingScope.getLots.routedLot.length; x++)
						{
							if(routingScope.getLots.routedLot[x].fltNbr !== undefined)
							{
								$("tr[name='lots" + routingScope.getLots.routedLot[x].lotNumber.toString() + "']").find('td:eq(0)').removeClass("rowStatusAlert");
								$("tr[name='lots" + routingScope.getLots.routedLot[x].lotNumber.toString() + "']").find('td:eq(0)').addClass("rowStatusPositive");
							}
							else
							{
								$("#lotRadioWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).addClass("ng-hide");
								$("#lotTetBoxWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).removeClass("ng-hide");
								$("#lineRadioWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).addClass("ng-hide");
								$("#lineTextBoxWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).removeClass("ng-hide");
								$("#pieceRadioWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).addClass("ng-hide");
								$("#pieceTextBoxWithId" + routingScope.getLots.routedLot[x].lotNumber.toString()).removeClass("ng-hide");
							}
						}
						$("#cargoScreeningAcrdn").addClass("active");
						$("#cargoScreeningAcrdn").find(".accordionBody").attr("style", "display:block;");
						$("#shipmentDetailsBody").attr("style", "display:none;");
						$scope.displayDivErrorBarCS = true;
						$scope.autoSaveData("cargoScreen");
						focus("noOfPieces");
					}
					else if($scope.editingSingleLineWithNoPieces === true)
					{
						$scope.toPutNoOfPiecesInFocus = true;
						$("#searchRoutePanel").removeClass("ng-hide");
						$("#routesSummary").removeClass("ng-hide");
						//$("#routingBody").attr("style", "display:block;");
						routingScope.areLotsRouted = false;
						if(routingScope.getLots.routedLot[0].fltNbr !== undefined)
						{
							$("tr[name='lots" + routingScope.getLots.routedLot[0].lotNumber.toString() + "']").find('td:eq(0)').removeClass("rowStatusPositive");
							$("tr[name='lots" + routingScope.getLots.routedLot[0].lotNumber.toString() + "']").find('td:eq(0)').addClass("rowStatusAlert");
						}
						$("#cargoScreeningAcrdn").addClass("active");
						$("#cargoScreeningAcrdn").find(".accordionBody").attr("style", "display:block;");
						$("#shipmentDetailsBody").attr("style", "display:none;");
						$scope.displayDivErrorBarCS = true;
						$scope.autoSaveData("cargoScreen");
						focus("noOfPieces");
						$scope.displayDivErrorBarRt = true;
						$scope.dispAccHdrErrorRt = true;
						$scope.fieldNameRt = "";
					}
				}, 100);
				changeOrNotManageawb = 0;
			});
		}
	}
	
	/**SAVE CONSIGNEE METHOD**/
	$scope.saveConsigneeDetails =function()
	{  
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var data={
				"awbNumber": $scope.headerData.awbNumber,"awbError":($scope.displayDivErrorBarTc==true)?"2":"",
				"awbPrefix": "526",
				"consignee": {
					"consigneeId": (manageAwbCntrl.fromConsigneeData!=undefined)?manageAwbCntrl.fromConsigneeData.consigneeId:"",
					"locationId":   (manageAwbCntrl.fromConsigneeData!=undefined)?manageAwbCntrl.fromConsigneeData.locationId:'',
					"consigneeName":  (manageAwbCntrl.fromConsigneeData!=undefined)?manageAwbCntrl.fromConsigneeData.consigneeName:'',
					"consigneeAddress":  (manageAwbCntrl.fromConsigneeData!=undefined)?manageAwbCntrl.fromConsigneeData.consigneeAddress:{},
					"consigneeReference": (manageAwbCntrl.fromConsigneeData!=undefined)?manageAwbCntrl.fromConsigneeData.consigneeReference:''
				}
			}
			
			manageAWBService.saveToConsigneeData(data).then(function(responseData)
			{
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				
				$scope.saveConsignee=true;
				//$scope.changeHeaderStatus();
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				console.log("saved consignee");
				changeOrNotManageawb = 0;
			});
		}
	}
	/**SAVE COMMENTS METHOD**/
	$scope.saveCommentsDetails =function()
	{  
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var data={
				"awbNumber": $scope.headerData.awbNumber,
				"awbPrefix": "526",
				"comments": manageAwbCntrl.fromComments
			}
			
			manageAWBService.saveToCommentsData(data).then(function(responseData)
			{
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				$scope.commentsStatus=true;
				//console.log("saved comments");
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				changeOrNotManageawb = 0;
			});
		}
	}
	/**SAVE signature METHOD**/
	$scope.saveSignatureDetails = function(){
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var data={
				"awbNumber": $scope.headerData.awbNumber,
				"awbPrefix": "526",
				"receivedFromCustSig": manageAwbCntrl.recSignature,
				"deliveredToCustSignature":manageAwbCntrl.delSignature
			}
			
			manageAWBService.saveToSignatureData(data).then(function(responseData)
			{
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				if(!$scope.tenderDateGeneration)
				{
					$scope.saveSignature=true;
					$scope.tenderDateGeneration=$rootScope.currentDateTime;
				}
				console.log("saved signature");
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				changeOrNotManageawb = 0;
			});
		}
	}
	
	
	/**SAVE METHOD***/
	$scope.saveScreeningDetails=function()
	{
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var awbNumber = $scope.headerData.awbNumber;
			if(manageAwbCntrl.headerData.origin){
			manageAwbCntrl.shipmentReleaseFormData.origin={code:manageAwbCntrl.headerData.origin, name:manageAwbCntrl.headerData.origin} 
			manageAwbCntrl.shipmentReleaseFormData.destination={code:manageAwbCntrl.headerData.destination, name:manageAwbCntrl.headerData.destination}
			}
			var data={
				"awbNumber":awbNumber,"awbError":($scope.displayDivErrorBarCS==true)?"4":"",
				"awbPrefix": "526",
				"screening":{
					"totalPieces":(manageAwbCntrl.totalScreenData!=undefined)?manageAwbCntrl.totalScreenData.totalPieces:'',
					"screeningFailed": (manageAwbCntrl.totalScreenData!=undefined)?manageAwbCntrl.totalScreenData.screeningFailed:false,
					"screeningLines": (manageAwbCntrl.cargoScreeningData!=undefined)?manageAwbCntrl.cargoScreeningData:[],
					"shipmentReleaseForm": manageAwbCntrl.shipmentReleaseFormData
				}
			}
			manageAWBService.saveCargoScreening(data).then(function(responseData)
			{   
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.screeningStatus=true;
				console.log("saved");
				if(responseData.data.airwaybill!=null){
				 $scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				 $scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				}
				////////Adding the below code to update the lot status to SCRN if all the pieces are screened////////////////////
				
				var totalScreenedPieces = cargoScreeningDirective.cargoScreen.totalPieces;
				var totalEnteredPieces = 0;
				if(shipmentDetailsScope.generatedLots !== undefined && shipmentDetailsScope.generatedLots !== null)
				{
					if(shipmentDetailsScope.generatedLots.routedLot === undefined)
						shipmentDetailsScope.generatedLots = shipmentDetailsScope.generateRoutingData();
				}
				else
				{
					shipmentDetailsScope.generatedLots = {};
					shipmentDetailsScope.generatedLots = shipmentDetailsScope.generateRoutingData();					
				}
				for(var cntr = 0; cntr < shipmentDetailsScope.generatedLots.routedLot.length; cntr++)
				{
					for(var cntrLine = 0; cntrLine < shipmentDetailsScope.generatedLots.routedLot[cntr].lines.length; cntrLine++)
					{
						for(var cntrPiece = 0; cntrPiece < shipmentDetailsScope.generatedLots.routedLot[cntr].lines[cntrLine].pieces.length; cntrPiece++)
						{
							totalEnteredPieces = totalEnteredPieces + 1;
						}
					}
				}
				
				/*
				if(totalScreenedPieces === totalEnteredPieces)
				{
					for(var cntr = 0; cntr < shipmentDetailsScope.generatedLots.routedLot.length; cntr++)
					{
						shipmentDetailsScope.generatedLots.routedLot[cntr].currentLotStatus = "SCRN";
					}
				}
				*/
				
				if($("#routingAcrdn").hasClass("disabled"))
				$("#routingAcrdn").removeClass("disabled");
				
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				changeOrNotManageawb = 0;
			});
		}
	}
	
	/***VALIDATE HEADER STATUS***/
	$scope.changeHeaderStatus=function()
	{
		if($scope.saveShipper==true && $scope.saveShippment==true && $scope.saveConsignee==true)
		{ 
			if(manageAwbCntrl.headerData.awbStatus!="undefined" && manageAwbCntrl.headerData.awbStatus!="" && manageAwbCntrl.headerData.awbStatus!="Manifested")
			{  
				manageAwbCntrl.headerData.awbStatus = "FOH";
			}
		}
	}
	
	/**LOT LABELS HEADER STATUS CHANGES**/
	$scope.lotLabelHeaderStatus=function()
	{
		
		if($scope.saveShipper && $scope.saveShippment && $scope.saveConsignee && $scope.screeningStatus && $scope.saveSignature)
		{ 
			manageAwbCntrl.headerData.awbStatus!="Manifested";
			
			
		}
	}
	
	//Any changes made or not
	var changeOrNotManageawb = 0;
	
	$('div[name="manageAwbForm"]').change(function() {
		changeOrNotManageawb = 1;
	});
	
	
	//Awb is locked or not
	$scope.awbIsLocked = false;
	
	$scope.checkawbIsLocked = function()
	{
		var awbNumber='';
		awbNumber = "526" +$scope.headerData.awbNumber;
		manageAWBService.awbLockedStatus(awbNumber).then(function(responseData)
		{
			if(responseData.data.errorCode=="71634")
			{
				$scope.awbIsLocked = true
				$rootScope.warningMethod("Data is currently unavailable. Please contact your CCSM for further information");
			}
			else
			{
				$scope.awbIsLocked = false
			}
		});
	}
	/**PRINT AWB REPORT**/
	$scope.printAWB=function()
	{
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			setTimeout(function(){
				if(changeOrNotManageawb == 1)
				{
					$scope.printModal();
				}
				else
				{
					var awbNumber='';
					awbNumber+="awbNumber="+$scope.headerData.awbNumber;
					window.open(baseurl + '/manageAirwaybillAcceptance/printAWB?'+awbNumber);
				}
			}, 100)
		}
	}
	
	/****SECTION WISE PRIVILEGES***/
	$scope.isSectionHaveRWPermission=function(isEligible,sectionIdName)
	{
		if($scope.isEmerLocked == true)
		{
			if(sectionIdName){
					angular.element("#"+sectionIdName+" "+":input").prop("disabled",true);
				}
				return false;
		}
		if($scope.releaseFormComplete == true)
		{
			if(sectionIdName!='awbCommentsForm')
			{
				if(sectionIdName){
				angular.element("#"+sectionIdName+" "+":input").prop("disabled",true);
				}
				return false;
			}
		}
		if(isEligible && $scope.sectionAWBPrivilege)
		{  
			if($scope.sectionAWBPrivilege.indexOf(isEligible)!=-1){
				if(sectionIdName){
					return true;
				}
			}
			else{
				if(sectionIdName){
					angular.element("#"+sectionIdName+" "+":input").prop("disabled",true);
				}
				return false;
			}
		}
	}
	
	$scope.isSectionHaveRCF = function(isEligible,sectionIdName)
	{
		angular.element("#"+sectionIdName+" "+":input").prop("disabled",true);
	}
	
	/**CLICK TRIGGER SECTION***/
	$('.accordionWrapper').on('click','li .accrdName',function () {
		Index = $(this).parent().parent().index();
		This= $(this).parent().parent();
		if($(This).hasClass("disabled")==1){  }	
		else{
			if($(This).hasClass("active")==1){
				$(This).removeClass("active");
				$('.accordionWrapper .accordion .accordionBody').slideUp(200);
			}
			else{
				$('.accordionWrapper li').removeClass("active");
				$(This).addClass("active");
				
				$('.accordionWrapper .accordion .accordionBody').slideUp(200);
				$('.accordionWrapper .accordion:eq('+Index+') .accordionBody').slideDown(200);
			}
		}	
	});
	
	
	/**section icon click to go history***/
	$scope.moveHistory = function(moveTo){
		$(".tabWrapper .nav-tabs li").removeClass('active');
		$(".tabWrapper .nav-tabs li:eq(1)").addClass('active');
		$(".tabWrapper .tab-content .tab-pane").hide();
		$(".tabWrapper .tab-content .tab-pane:eq(1)").show();
		$(".awbHistoryList li:eq("+moveTo+") .accordionTitle").trigger('click');
	}
	
	/***TAB OUT TRIGGER**/
	$scope.triggerNxtSection=function(nxtIndex,nxtSection)
	{ 
		$scope.autoSaveData(nxtSection);
		var sectionActIndex=parseInt(nxtIndex)+1;
		$('.accordionWrapper li').removeClass("active");
		$('.accordionWrapper .accordion .accordionBody').slideUp(200);
		changeOrNotManageawb = 0;
		if($scope.isSectionCollapse('SECTION'+sectionActIndex)){
			$('.accordionWrapper li:eq('+nxtIndex+')').addClass("active");
			$('.accordionWrapper .accordion:eq('+nxtIndex+') .accordionBody').slideDown(200);
			changeOrNotManageawb = 0;
		}
	}
	
	/***AWB DISCRPANCY CALL**/
	$scope.getDiscrepancyData=function()
	{
		var data={"awbNumber":$scope.headerData.awbNumber};
		if(!$scope.isServiceInvoke){
			manageAWBService.getDiscrepancyData(data).success(function(responseData){
				$scope.discrepancyData=responseData.awbDiscrepancyList;
				$scope.isServiceInvoke=true;
			});
		}
	}
	
	/**SECTION WISE SAVE AND NEXT SECTION***/
	$scope.saveSectionWise=function(callFrom)
	{  
		switch($scope.prevAccType)
		{
			case 'fromshipper':
			$scope.triggerNxtSection('1','toconsignee');
			break;
			case 'toconsignee':
			$scope.triggerNxtSection('2','shipmentdetails');
			break;
			case 'shipmentdetails':
			$scope.triggerNxtSection('3','cargoScreen');
			break;
			case 'cargoScreen':
			$scope.triggerNxtSection('4','routing');
			break;
			case 'routing':
			$scope.triggerNxtSection('5','charges');
			break;
			case 'charges':
			$scope.triggerNxtSection('6','signatures');
			break;
			case 'signatures':
			$scope.triggerNxtSection('7','lotlables');
			break;
			case 'lotlables':
			$scope.triggerNxtSection('8','comments');
			break;
			case 'comments':
			$scope.triggerNxtSection('9','documents');
			break;
		}
		if(callFrom == 'CLOSE')
		{
			window.close();
		}
		changeOrNotManageawb = 0;
	}
	
	/**SAVE TEN SECTION***/ 
	$scope.awbAllSectionSave=function(data)
	{
		switch(data)
		{
			case 'fromshipper':
			var shipperData='';
			if($scope.fromshipper!=true){
				if($rootScope.awbContent.shipper!=null){
					shipperData={
					"masterActNumber":$rootScope.awbContent.shipper.masterActNumber,"locationId":$rootScope.awbContent.shipper.locationId,"primarySecurityType":$rootScope.awbContent.shipper.primarySecurityType,"shipperName":$rootScope.awbContent.shipper.shipperName,"shipperAddress":$rootScope.awbContent.shipper.shipperAddress,"iacNumber":$rootScope.awbContent.shipper.iacNumber,"ccsfId":$rootScope.awbContent.shipper.ccsfId,"employeeNumber":$rootScope.awbContent.shipper.employeeNumber,"employeeHireDate":$rootScope.awbContent.shipper.employeeHireDate,"idVerificationNumber":$rootScope.awbContent.shipper.idVerificationNumber,"shipperReference":$rootScope.awbContent.shipper.shipperReference,"cocFailure":($rootScope.awbContent.shipper.cocFailure!="YES")?false:true,"cocFailureReason":($rootScope.awbContent.shipper.cocFailureReason!="undefined" && $rootScope.awbContent.shipper.cocFailureReason!="")?$rootScope.awbContent.shipper.cocFailureReason:{} }
					}else{
					shipperData={};
				}
			}
			else{
				var ccsfId="";
				if(manageAwbCntrl.fromShipperData.ccsf1 && manageAwbCntrl.fromShipperData.ccsf1!="undefined"){
					ccsfId=manageAwbCntrl.fromShipperData.ccsf1+""+manageAwbCntrl.fromShipperData.ccsf2+""+manageAwbCntrl.fromShipperData.ccsf3+""+manageAwbCntrl.fromShipperData.ccsf4+""+manageAwbCntrl.fromShipperData.ccsf5+""+manageAwbCntrl.fromShipperData.ccsf6;
					}else{
					ccsfId="";
				}
				shipperData={
				"masterActNumber":manageAwbCntrl.fromShipperData.masterActNumber,"locationId":manageAwbCntrl.fromShipperData.locationId,"primarySecurityType":manageAwbCntrl.fromShipperData.primarySecurityType,"shipperName":manageAwbCntrl.fromShipperData.shipperName,"shipperAddress":manageAwbCntrl.fromShipperData.shipperAddress,"iacNumber":manageAwbCntrl.fromShipperData.iacNumber,"ccsfId":ccsfId,"employeeNumber":manageAwbCntrl.fromShipperData.employeeNumber,"employeeHireDate":manageAwbCntrl.fromShipperData.employeeHireDate,"idVerificationNumber":manageAwbCntrl.fromShipperData.idVerificationNumber,"shipperReference":manageAwbCntrl.fromShipperData.shipperReference,"cocFailure":(manageAwbCntrl.fromShipperData.cocFailure!="YES")?false:true,"cocFailureReason":(manageAwbCntrl.fromShipperData.cocFailureReason!="undefined" && manageAwbCntrl.fromShipperData.cocFailureReason!="")?manageAwbCntrl.fromShipperData.cocFailureReason:{} }
			}
			return shipperData;
			break;
			case 'toconsignee':
			var consigneeData='';
			if($scope.toConsignee!=true)
			{
				if($rootScope.awbContent.consignee!=null)
				{
					consigneeData={
						"consigneeId": $rootScope.awbContent.consignee.consigneeId,"locationId":  $rootScope.awbContent.consignee.locationId,"consigneeName": $rootScope.awbContent.consignee.consigneeName,"consigneeAddress": $rootScope.awbContent.consignee.consigneeAddress,"consigneeReference": $rootScope.awbContent.consignee.consigneeReference
					};
				}
				else{
					consigneeData={};
				}
			}
			else{
			toConsigneeScope.baseConsignee();
				consigneeData={
					"consigneeId": manageAwbCntrl.fromConsigneeData.consigneeId,"locationId":  manageAwbCntrl.fromConsigneeData.locationId,"consigneeName": manageAwbCntrl.fromConsigneeData.consigneeName,"consigneeAddress": manageAwbCntrl.fromConsigneeData.consigneeAddress,"consigneeReference": manageAwbCntrl.fromConsigneeData.consigneeReference
				};
			}
			return consigneeData;
			break;
			case 'shipmentdetails':
			var shipmentData={};
			if($scope.shipmentdetails!=true)
			{
				if($rootScope.awbContent.shipmentInfo!=null)
				{
					shipmentData=$rootScope.awbContent.shipmentInfo;
				}
				else{
					shipmentData={};
				}
			}
			else{
				manageAWBService.getGnaSearch($rootScope.awbContent.awbNumber).then(function(responseData)
				{ 					
					if(responseData.data.shipmentAirwayBill.routeInfo !== undefined && responseData.data.shipmentAirwayBill.routeInfo !== null)
					{  
						$rootScope.awbContent.routeInfo = responseData.data.shipmentAirwayBill.routeInfo;
						//routingScope.initializeGetLots();
						//shipmentData = manageAwbCntrl.getShipmentObjectForSave();
						var template = ($compile("<div awb-routing ng-cloak></div>"))($scope);
						$("#routingBody").html(template);
						$scope.routing=true;
						$timeout(function () {
							//shipmentDetailsScope.baseControl();
							routingScope.initializeGetLots();
							$scope.saveShipmentDetails();
						}, 100);
					}
				});
				shipmentDetailsScope.baseControl();
				shipmentData = saveShipmentScope;
				
			}
			return shipmentData;
			break;
			case 'cargoScreen':
			var cargoScreeningData='';
			if($scope.cargoScreen!=true){
				if($rootScope.awbContent.screening!=null)
				{
					cargoScreeningData={
					"totalPieces":$rootScope.awbContent.screening.totalPieces,"screeningFailed": $rootScope.awbContent.screening.screeningFailed,"screeningLines":$rootScope.awbContent.screening.screeningLines,"shipmentReleaseForm":($rootScope.awbContent.screening.shipmentReleaseForm!=null)? $rootScope.awbContent.screening.shipmentReleaseForm:{} }
				}else
				{
					cargoScreeningData={};
				}
				}else{
				manageAwbCntrl.shipmentReleaseFormData.origin={code:manageAwbCntrl.headerData.origin, name:manageAwbCntrl.headerData.origin} 
				manageAwbCntrl.shipmentReleaseFormData.destination={code:manageAwbCntrl.headerData.destination, name:manageAwbCntrl.headerData.destination}
				cargoScreeningData={
					"totalPieces":manageAwbCntrl.totalScreenData.totalPieces,
					"screeningFailed": manageAwbCntrl.totalScreenData.screeningFailed,
					"screeningLines":manageAwbCntrl.cargoScreeningData,
					"shipmentReleaseForm": manageAwbCntrl.shipmentReleaseFormData
				}
			}
			return cargoScreeningData;
			break;
			case 'routing':
			var routingData='';
			if($scope.routing!=true)
			{
				if($rootScope.awbContent.routeInfo!=null)
				{
					routingData={};
					routingData.lots = $rootScope.awbContent.routeInfo.lots;
				}
				else{
					routingData={};
				}
			}
			else{
				routingData = routingScope.getRoutingObjectForGeneralSave();
			}
			return routingData;
			break;
			/*
				case 'charges':
				$scope.saveShipperDetails();
				$scope.triggerNxtSection('6','signatures');
				break;
			*/
			case 'recSignature':
			var receivedFromCustSig='';
			if($scope.signatures!=true){
				if($rootScope.awbContent.receivedFromCustSig.length>0){
					receivedFromCustSig=$rootScope.awbContent.receivedFromCustSig;
					}else{
					receivedFromCustSig=[];
				}
			}
			else{
				receivedFromCustSig=manageAwbCntrl.recSignature;
			}
			return receivedFromCustSig;
			break;
			case 'delSignature':
			var delSignature='';
			if($scope.signatures!=true){
				if($rootScope.awbContent.deliveredToCustSignature.length>0){
					delSignature=$rootScope.awbContent.deliveredToCustSignature;
					}else{
					delSignature=[];
				}
			}
			else{
				delSignature=manageAwbCntrl.delSignature;
			}
			return delSignature;
			break;
			/*case 'lotlables':
				$scope.saveShipperDetails();
				$scope.triggerNxtSection('8','comments');
			break;*/
			case 'comments':
			var awbComments='';
			if($scope.comments!=true){
				if($rootScope.awbContent.comments.length>0){
					awbComments=$rootScope.awbContent.comments;
					}else{
					awbComments=[];
				}
			}
			else{
				awbComments=manageAwbCntrl.fromComments;
			}
			return awbComments;
			break; 
		}
	}
	
	/****COMMMON SAVE***/
	$scope.saveAirWayBill=function(callFrom)
	{
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var checkForSaveCondition = true;
			if($scope.headerData.awbStatus.toString() !== "DRAFT" && $scope.headerData.awbStatus.toString() !== "FOH")
			{
				if($scope.displayDivErrorBarFs===true || $scope.displayDivErrorBarCS===true || $scope.displayDivErrorBarTc===true || $scope.displayDivErrorBarSd === true)
				{
					checkForSaveCondition = false;
					$scope.displayDivErrorInGeneral = true;
					$scope.showErrorBar(true);
					$(".accordion.active input:text.ng-touched.ng-invalid").first().focus();
				}
			}
			if(checkForSaveCondition === true)
			{
				var data={"awbNumber":$scope.headerData.awbNumber,"awbPrefix":"526","awbStatus":$scope.headerData.awbStatus,
					"shipper": $scope.awbAllSectionSave('fromshipper'),
					"consignee": $scope.awbAllSectionSave('toconsignee'), 
					"shipmentInfo": $scope.awbAllSectionSave('shipmentdetails'),
					"screening":$scope.awbAllSectionSave('cargoScreen'), 
					"routeInfo": $scope.awbAllSectionSave('routing'),
					"receivedFromCustSig": $scope.awbAllSectionSave('recSignature'),
					"deliveredToCustSignature":$scope.awbAllSectionSave('delSignature'), 
					"comments": $scope.awbAllSectionSave('comments'),		 
					"versionNumber":($scope.updatedVersionNumber!='')?$scope.updatedVersionNumber:$rootScope.awbContent.versionNumber
				}
				console.log(data);
				manageAWBService.saveAirwayBill(data).then(function(responseData){
					if(callFrom=="CLOSE"){
						window.close();
					}
					else if(responseData.data.errorCode!=null){
					 $rootScope.assignedShipperId=true;
					 $scope.displayDivErrorBarFs=true;
					 $scope.assignedShipperMsg=responseData.data.errorMessage;
					}
					else
					{
						$scope.updatedVersionNumber = responseData.data.airwaybill.versionNumber;
						$scope.displayMessage = "Air Waybill successfully updated";
						$scope.savepopup();
					}
					console.log("saved");
					changeOrNotManageawb = 0;
				});
			}
		}
	}
	
	/**EXPAND ALL SECTION***/
	$scope.expandAllSections=function(){
		var sectionConstant=[{"key":"0","value":"fromShipper"},{"key":"1","value":"toconsignee"},{"key":"2","value":"shipmentdetails"},
			{"key":"6","value":"signatures"},{"key":"7","value":"lotlables"},{"key":"8","value":"comments"},{"key":"9","value":"documents"},{"key":"3","value":"cargoScreen"},
		{"key":"4","value":"routing"}];
		angular.forEach(sectionConstant,function(key){
		   $timeout(function(){;
		      console.log(key.value);
			  $scope.autoSaveData(key.value);
			  $('.accordionWrapper li:eq('+key.key+')').addClass("active");
			  $('.accordionWrapper .accordion:eq('+key.key+') .accordionBody').slideDown(200);
			},500);
		});
	}
	
	/***COLLAPSE ALL SECTION***/
	$scope.collapseAllSections=function(){
		$('.accordionWrapper li').removeClass("active");
		$('.accordionWrapper .accordion .accordionBody').slideUp(200);
	}
	
	//Save POPUP**/
	$scope.savepopup = function()
	{
		var modalInstance = $modal.open({
			templateUrl: 'saveAwbModal.html',
			controller: 'saveAwbController',
			size: 'sm',
			backdrop: 'static',
			windowClass: 'Css-Center-Modal',
			keyboard: false,
			resolve: {
				infoDisp: function () {
					return $scope.displayMessage;
				}
				
			}
			
		})
		
	}
	
	/**VOID AWB BUTTON***/
	$scope.voidAWB=function()
	{
		$scope.checkawbIsLocked();
		if($scope.awbIsLocked == false)
		{
			var data={"reasonForVoid":$rootScope.awbVoidReason,"awbNumber":$scope.headerData.awbNumber,"awbPrefix":"526"};
			manageAWBService.setVoidAirwayBill(data).then(function(responseData){
				$scope.awbContent.versionNumber = responseData.data.versionNumber;
				$scope.lotLabelVersion = responseData.data.airwaybill.versionNumber;
				$scope.setAWBHeaderStatus(responseData.data.airwaybill.awbStatus);
				if(manageAwbCntrl.fromComments!=undefined){
					var defaultDetails={"comment":$rootScope.awbVoidReason,"updatedBy":{"userId":$localStorage.userObject.userId,"firstName":$localStorage.userObject.firstName,
					"lastName":$localStorage.userObject.lastName},"localDate":$rootScope.currentDateTime,"updatedDateTime":$rootScope.currentDateTime};
					manageAwbCntrl.fromComments.push(defaultDetails);
					manageAwbCntrl.displayDivErrorBarCom=false;
				}
				$rootScope.voidAwbClicked=true;
				if(responseData.data.errorMessage === null)
				{
					for(var x = 0; x < routingScope.getLots.routedLot.length; x++)
					{
						routingScope.getLots.routedLot[x].flightLegs = [];
						$(".clubbedLine" + routingScope.getLots.routedLot[x].lotNumber.toString()).find("tbody")[0].innerHTML = "";
						routingScope.getLots.routedLot[x].fltNbr = "";
						routingScope.getLots.routedLot[x].lotsOrig = "";
						routingScope.getLots.routedLot[x].destination = "";
						routingScope.getLots.routedLot[x].scheduledDepartureDate = "";
						routingScope.getLots.routedLot[x].scheduledDepartureTime = "";
						routingScope.getLots.routedLot[x].scheduledArrivalTime = "";
						routingScope.getLots.routedLot[x].scheduledArrivalDate = "";
					}
					setTimeout(function()
					{ 
						$("#btnDoReRouting").attr("disabled", "disabled");
					}, 10);
				}
			});
		}
	}
	
	/**VOID POPUP**/
	$scope.voidPopUpAWB=function()
	{
		$modal.open({
			templateUrl: 'confirmBox/void_awb.html',
			size: 'sm',
			backdrop: 'static',
			windowClass: '',
			keyboard: false,
			scope: $scope,
			controller: function ($scope, $modalInstance) {
				$scope.submit = function () {
					$modalInstance.dismiss('ok');
					$rootScope.awbVoidReason=$scope.voidReason;
					$scope.voidAWB();
				}
				$scope.cancel = function () {
					$modalInstance.dismiss('ok');
				}
			}
		});
	}
	
	//Exit Click
	$scope.exitClick = function()
	{
		if(changeOrNotManageawb == 1)
		{
			var modalInstance = $modal.open({
				templateUrl: 'exitAwbModal.html',
				controller: 'exitAwbController',
				size: 'sm',
				backdrop: 'static',
				windowClass: 'Css-Center-Modal',
				keyboard: false
			});
		}
		else
		{
			//window.close();
			var objWin = window.self;
			objWin.open('','_self','');
			objWin.close();
		}
	}
	
	/**DELETE CARGO SCREENING FROM S-1***/
	$rootScope.deleteCargoScreening=function(){
	  var data={"awbNumber":$localStorage.commonData.awbNumber,"awbPrefix":526};
	  manageAWBService.deleteCargoScreening(data).then(function(responseData){
		 $rootScope.deleteCargoScreeningData=true;
		 manageAwbCntrl.displayDivErrorBarCS=true;
	  });
	}
	
	$scope.exitClickAutoSave = function()
	{
		$scope.saveSectionWise();
		//window.close();
		var objWin = window.self;
		objWin.open('','_self','');
		objWin.close();		
	}
	
	//print click
	//Exit Click
	$scope.printModal = function()
	{
		var modalInstance = $modal.open({
			templateUrl: 'printAwbModal.html',
			controller: 'printAwbController',
			size: 'sm',
			backdrop: 'static',
			windowClass: 'Css-Center-Modal',
			keyboard: false
		});
	}
	
	
});

app_controllers.controller('exitAwbController', function ($scope, $modalInstance, $modal, $log, $route, $location) {
	$scope.yes = function () {
		$modalInstance.close();
		window.close()
	};
	$scope.no = function () {
		$modalInstance.dismiss('cancel');
	};
});

app_controllers.controller('printAwbController', function ($scope,$localStorage, $rootScope, manageAWBService, $modalInstance, $modal, $log, $route, $location, ServiceURL) {
	$scope.yes = function () {
		
		$modalInstance.close();
		var baseurl = ServiceURL.baseUrl;
		//manageAwbCntrl.saveAirWayBill();
		var awbNumber='';
	    awbNumber+="awbNumber="+manageAwbCntrl.headerData.awbNumber;
		window.open(baseurl + '/manageAirwaybillAcceptance/printAWB?'+awbNumber);
	};
	$scope.no = function () {
		$modalInstance.dismiss('cancel');
		var awbNumber='';
	    awbNumber=manageAwbCntrl.headerData.awbNumber;
		if(awbNumber!=undefined && awbNumber!=''){
			  manageAWBService.getGnaSearch(awbNumber).then(function(responseData)
			  { 
				$localStorage.awbContent='';
				if(angular.isObject(responseData.data) && responseData.data!='')
				{  
				  if(angular.isObject(responseData.data.shipmentAirwayBill) && (responseData.data.shipmentAirwayBill!=''))
				  {			  
					$localStorage.isRedirect=true;
					$rootScope.awbContent=responseData.data.shipmentAirwayBill;
					$rootScope.awbContent.isAutoSave=responseData.data.awbedit;
					var redirectUrlStr = $location.absUrl();
					$localStorage.awbContent=$rootScope.awbContent;
					$window.open(redirectUrlStr.substring(0, redirectUrlStr.lastIndexOf("#/") + 1)+'manage_awb', '_blank');
				  }
				  else if(angular.isObject(responseData.data.shippers) && (responseData.data.shippers!=''))
				  {
					 $location.path("/search_airwaybill");
					 $scope.shipperDetails=[];
					 $scope.shipperDetails=responseData.shippers;
					 $scope.showResult=true;			
					 $scope.noData=false;
				  }
				  else if(responseData.data.shipmentAirwayBill==null)
				  {
					 if(responseData.data.errorCode=="71633" || responseData.data.errorCode=="9999"){
					   $rootScope.warningMethod("Entered number is not valid MOD7 Number");
					 }else if(responseData.data.errorCode=="71634"){
						$rootScope.warningMethod("Data is currently unavailable. Please contact your CCSM for further information");
					 }
					 else if(responseData.data.errorCode!=null && responseData.data.errorCode!="71634" && responseData.data.errorCode!="71633" && responseData.data.errorCode!="9999" ){
					   if($rootScope.has_permission($scope.role.role_CREATE_AWB)){
						 $rootScope.awbFocus=false;
						 $scope.errrorPopup("Air Waybill / Reference Number "+ responseData.data.errorCode +" is not found, would you like to create one?");
						 $localStorage.isAWBNumberExit=true;$localStorage.isAWBNumber=responseData.data.errorCode;$localStorage.isRedirect=false;
					   }else{
						 $rootScope.warningMethod("No shipment details were found for the Air Waybill "+responseData.data.errorCode);
						 $rootScope.awbFocus=true;
					   }
					 }
				  }
				}
				else
				{  
					 $localStorage.isRedirect=false;
				}
				});
	}
	}
});

app_controllers.controller('saveAwbController', function ($scope, $modalInstance, infoDisp) {
	$scope.infoMsg = infoDisp;
	$scope.cancel = function () {
		$modalInstance.close();
		manageAwbCntrl.collapseAllSections();
		$('#exitButton').focus();
		if(manageAwbCntrl.toPutNoOfPiecesInFocus)
		{
			setTimeout(function()
			{
				manageAwbCntrl.displayDivErrorBarCS = true;
				$("#cargoScreeningAcrdn").addClass("active");
				$("#cargoScreeningAcrdn").find(".accordionBody").attr("style", "display:block;");
				manageAwbCntrl.assignFocusToElements("noOfPieces");
				manageAwbCntrl.toPutNoOfPiecesInFocus = false;
			}, 500);
		}
	};
});	