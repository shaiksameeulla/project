/*var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";
 */
var weightSlabVal = "";
var sectorList;
var wtList;
var matrixList;
var vobList;
var prodList;
var prodCatId;
var vobSlabId;
var minChrgList;
var orgSecId;
var pId;
var load = false;
var action = "";
var indCatId;
var custCatId;
var benchMarkType;
var rateApproved;
var stwtSlb = new Array();
var rowNo;
var prodCode = "";
var prodCatTO;
var productCode = "";
var secLen;
var wtLen;
var regionCode = ""; 
var custId = "";	
var count1 = 1;
var count2 = 1;
var count3 = 1;
var count4 = 1;
var gridId = 1;
var prodCatAry = new Array();
var prodCatCodeAry = new Array();
var splDestAry = new Array();
var statesList;
var configuredType = "";
var ppxKUVal = 0;
var slabValKU = "";
var slabValK = "";
var addSlabFlag = false;
var flagAddSlab = false;
var DOX = "D";
var PPX = "P";
var BOTH = "B";
var DA = "DA";
var DOXDD = "DD";
var KU = "KU";
var KT = "KT";
var KA = "KA";
var PA = "PA";
var PD = "PD";
var AR = "AR";
var TR = "TR";
var CO = "CO";
var EC = "EC";

function saveRateQuotation(val) {
	action = val;

	getDomElementById("rateProdCatId").value = prodCatId;
	getDomElementById("rateQuotId").value = getDomElementById("rateQuotationId").value;

	secLen = sectorList.length;
	wtLen = wtList.length;

	setupDropdownValues();
	prepareSectorArr();

	getDomElementById("rowNo").value = rowCount-1;

	if(productCode == 'CO' && (configuredType == PPX || configuredType == BOTH)){
		if(!checkFinalWeightSlabs()){
			return;
		}
		if(!checkAddWtSlabs()){
			return;
		}
	}


	var cols = 5;
	var wtCols = 0; 

	var conType = getDomElementById("configuredType").value;
	var coCols = 0;
	if(conType == PPX){
		coCols = 7;
	}else if(conType == BOTH){
		coCols = 9;
	}else{
		coCols = 5;
	}

	for(var j =0 ;j<secLen;j++){
		if(sectorList[j].sectorType == "D" 
			&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
			for(var p =0;p<prodCatAry.length;p++){
				if(prodCatCodeAry[p] ==  "CO"){
					wtCols = coCols;
				}else{
					wtCols = cols;
				}
				for(var n = 1 ; n <= wtCols ; n++){
					if(!isNull(getDomElementById("rate"+prodCatAry[p]+sectorList[j].sectorTO.sectorId+n))){
						getDomElementById("rate"+prodCatAry[p]+sectorList[j].sectorTO.sectorId+n).disabled = false;
					}
				}
			}
		}
	}
	for(var i=1;i<rowCount;i++){
		for(var k=1;k<=coCols;k++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+k+i))){
				getDomElementById("specialDestRate"+prodCatId+k+i).disabled = false;
			}
		}
	}

	assignROIValuesToZones();

	//to check if flat rate is applicable
	if(!isNull(document.getElementById("flatRate"))){
		document.getElementById("flatRate").value = 'P';
	}
	if(!isNull(document.getElementById("flatRateChk"))){
		if(productCode == "TR"){
			if(document.getElementById("flatRateChk").checked == true){
				document.getElementById("flatRate").value = 'F';
			}else{
				document.getElementById("flatRate").value = 'P';
			}
		}
	}

	//if(validAboveWeightSlabs() && checkGridFieldsData()){
	if(checkGridFieldsData()){
		getDomElementById("btnProposedRatesSave" + prodCatId).disabled = true;
		var url = "./rateQuotation.do?submitName=saveOrUpdateRateQuotationProposedRates";
		ajaxCall1(url, "rateQuotationForm", saveCallbackRatesfun);
		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function ajaxCall1(url,formId,ajaxResponse){
	ajaxCallWithParam1(url,formId,ajaxResponse,null);
}
/**
 * ajaxCallWithParam
 *@param: url : url to be called.
 *@param: formId : Form to be serialized.
 *@param: ajaxResponse : function to be called as call back method after getting response.
 *@param: rowNum : Row number id of the table in the given form
 @return: 
 */
function ajaxCallWithParam1(url,formId,ajaxResponse,rowNum){
	// jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	progressBar();
	//alert(url+"\t"+formId+"\t");
	jQuery.ajax({
		url: url,
		type: "POST",
		data:jQuery("#"+formId).serialize(),
		context: document.body,
		success: function (data){
			jQuery.unblockUI();
			if(isNull(rowNum)){
				ajaxResponse(data);
			}else{
				ajaxResponse(data, rowNum);}			
		},
		error: function( xhr, ajaxOptions, thrownError ) {
			jQuery.unblockUI();
			//alert('Server Un-available(network error)');
			//alert( "xhr :"+xhr+"\tajaxOptions:"+ajaxOptions+"\tthrownError:"+thrownError);
		}
	});
	jQuery.unblockUI();
}
/**
 * This Method popup message whether the data has been stored in database or not
 * 
 * @param data
 */
function saveCallbackRatesfun(ajaxResp) {
	jQuery.unblockUI();
	getDomElementById("btnProposedRatesSave" + prodCatId).disabled = false;
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];	
		if (responseText != null && error != null) {
			alert(error);
		} else {
			var rbmhTO  = responseText;
			if(assignVal(ajaxResp)){
				if(!isNull(rbmhTO.productCatHeaderTO)){					
					if(productCode == "CO"){
						getDomElementById("proposedRatesCO").value = "Y";
						if(configuredType == DOX){
							getDomElementById("proposedRatesD").value = 'Y';
						}else if(configuredType == PPX){
							getDomElementById("proposedRatesP").value = 'Y';
						}else if(configuredType == BOTH){
							getDomElementById("proposedRatesB").value = 'Y';
						}
					}
					alert(rbmhTO.transMsg);
				}
			}
		}
	}
}
/*if(!isNull(data)){
	if(data == "FAILURE"){
		if(action == 'submit')
			alert("Data not saved successfully");
		else
		alert("Data not saved successfully");
	}
	else{
		rbmhTO = jsonJqueryParser(data);
		if(!(rbmhTO.slabRateStatus == "FAILURE")){
		if(assignVal(data)){
			jQuery.unblockUI();
			if(!isNull(rbmhTO.productCatHeaderTO)){
			alert("Data saved successfully.");
			}
			else{
				alert("Data not saved successfully");
			}
		}
		}else{
			alert("Data can not Saved. Slab Rates are Lower than Rate Bench Mark Slab Rates");
			return;
		}
	}
	}



}*/

function loadAllGridValues(val,pCode,tableId,index){
	qNo = getDomElementById("rateQuotationId").value;
	if(!isNull(qNo)){
		prodCatId = val;
		productCode = pCode;
		prodCode = pCode;
		gridId = tableId;
		exist = false;
		if(!isNull(prodCatAry)){
			for(var p=0;p<prodCatAry.length;p++){
				if(!isNull(prodCatAry[p]) && prodCatAry[p] == val){
					exist = true;
				}
			}
			if(!exist){
				var h = 0;
				for(var k=0;k<prodCatAry.length;k++){
					if(!isNull(prodCatAry[k])){
						h++;
					}
				}
				prodCatAry[h] = val;
				prodCatCodeAry[h] = pCode;
			}
		}else{
			prodCatAry[0] = val;
			prodCatCodeAry[0] = pCode;
		}
		$("#tabsnested").tabs("option", "active", index);

		if(!isNull(getDomElementById("IndustryCategory"))){
			var tempCustId = getDomElementById("custCat"+getDomElementById("IndustryCategory").value).value;
			custId = parseInt(tempCustId);
			getDomElementById("custCatId").value = custId;
		}else{
			custId = getDomElementById("custCatId").value;
		}
		getDomElementById("rateProdCatCode").value = productCode;
		getDomElementById("rateQuotationProdCatHeaderId").value = "";
		if(productCode != "CO"){
			configuredType = "";
			getDomElementById("rateConfiguredType").value = "";	
		}else{
			getDomElementById("configuredType").options[0].selected = true;
			configuredType = getDomElementById("configuredType").value;
			getDomElementById("rateConfiguredType").value = configuredType;	
		}

		var url = "./rateQuotation.do?submitName=getValues&quotationId="+getDomElementById("rateQuotationId").value+"&productCategoryId="+prodCatId+"&quotType="+getDomElementById("rateQuotationType").value+"&custId="+custId;
		ajaxCall1(url, "rateQuotationForm", populateValues);

		jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}


function populateValues(data){

	//deleteGridRows();
	orgSecId = null;
	if(assignVal(data)){
		jQuery.unblockUI();
	}	
}


function assignVal(data){


	//prodCode = "";

	rbmhTO = jsonJqueryParser(data);
	if(!isNull(rbmhTO)){
		sectorList = rbmhTO.rateSectorsList;
		wtList = rbmhTO.rateWtSlabsList;
		matrixList = rbmhTO.rateMatrixMap;
		vobList = rbmhTO.rateVobSlabsList;
		prodList = rbmhTO.rateProdCatList;
		minChrgList = rbmhTO.rateMinChargWtList;
		if(!isNull(rbmhTO.statesList)){
			statesList = rbmhTO.statesList;
		}

		if(!isNull(rbmhTO.regionCode)){
			regionCode = rbmhTO.regionCode;
		}

		secLen = sectorList.length;
		wtLen = wtList.length;

		/*if(productCode == "TR" || productCode == "AR" || productCode == "EC"){

		document.getElementById("example"+gridId).style.width = '90%';		
	}*/

		if(count1 == 1 && productCode == "CO"){
			addRateRows(sectorList,wtList);
			count1++;
		}
		else if(count2 == 1 && productCode == "TR"){
			prepareGrid(gridId);
			addRateRows(sectorList,wtList);
			count2++;
		}
		else if(count3 == 1 && productCode == "AR"){
			prepareGrid(gridId);
			addRateRows(sectorList,wtList);
			count3++;
		}
		else if(count4 == 1 && productCode == "EC"){
			prepareGrid(gridId);
			addRateRows(sectorList,wtList);
			count4++;
		}

		if(isNull(orgSecId)){

			if(!isNull(getDomElementById("rateOrgSec"+prodCatId))){
				getDomElementById("rateOrgSec"+prodCatId).options[0].selected = true;
				if(!isNull(regionCode)){
					for(var j =0 ;j<secLen;j++){
						if(sectorList[j].sectorType == "O" && sectorList[j].sectorTO.sectorCode == regionCode
								&& (sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode != "CO" ||
										sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode != "EC")){
							getDomElementById("rateOrgSec"+prodCatId).value = sectorList[j].sectorTO.sectorId;
							break;
						}
					}
				}
				orgSecId = getDomElementById("rateOrgSec"+prodCatId).value ;
			}else
				orgSecId = null;
		}

		var m=-1;

		if(!isNull(prodCode) && prodCode != "CO"){
			for(var n = 1; n<5;n++){
				getDomElementById("rateStartWeightSlab"+prodCatId+n).innerHTML = '';
				getDomElementById("rateQuotStartWeight"+n).value = "";
				getDomElementById("rateSpecialEndWeightSlab"+prodCatId+n).innerHTML = '';
				getDomElementById("rateSpecialStartWeightSlab"+prodCatId+n).innerHTML = '';
				clearDropDownList("rateQuotEndWeight"+prodCatId+n);
			}
			clearDropDownList("rateQuotAddWeight"+prodCatId);
			getDomElementById("rateQuotAddWeight").value = "";
			getDomElementById("rateSpecialAddWeightSlab"+prodCatId).value = "";



			for(var n=1;n<=5;n++){
				for(var i = 0; i< secLen ; i++){
					if(sectorList[i].sectorType == "D" &&  sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)
					{

						if(!isNull(getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n))){
							getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n).value = "";
							getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n).disabled = true;
						}
					}
				}
			}

			for(var i =0 ;i<wtLen;i++){

				if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(m==-1)
						m = i;

					if(wtList[i].weightSlabTO.additional != 'Y'){
						getDomElementById("rateStartWeightSlab"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlab"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
						getDomElementById("rateQuotStartWeight1").value = wtList[m].weightSlabTO.weightSlabId;
						addOptionTODropDown("rateQuotEndWeight"+prodCatId+"1",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else{
						if(prodCode != "AR" && prodCode != "TR"){
							addOptionTODropDown("rateQuotAddWeight"+prodCatId,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}
					}

				}
			}


			for(var i=0;i<4;i++){
				if(!isNull(getDomElementById("specialGrid"+i)))
					deleteTableRow("specialGrid"+i);
			}

			if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
			{
				getDomElementById("rateMinChargWt"+prodCatId).options[0].selected = true;
			}

			if(!isNull(rbmhTO.productCatHeaderTO)){

				prodCatTO = rbmhTO.productCatHeaderTO;
				assignRateValues();
				enableTabs(2);
			}else{
				prodCatTO = null;
			}
		}else{
			assignConfiguredWeightSlabs();
		}


		if(productCode != "CO"){ 
			enableGridFields(5);
			enableSplGridFields(5);
			disableGridFields(5);
			disableSplGridFields(5);
			disableFieldsBasedonStatus();
		}
		
		var userType = getDomElementById("userType");
		if (!isNull(userType)) {
			if (userType.value != "" && userType.value == "S") {
				disableVobAndWeights();
				disableProposedRatesGridFields();
				disableSpecialDestGridFields();
				diabledProposedRatesButtons();
			}
		}
		
		/* The below method is called to restrict user from modifying proposed rates tab using super-edit functionality */
		restrictRateModificationViaSuperEdit("assignVal");
	}
	return true;
}

function disableFieldsBasedonStatus(){
	var status = getDomElementById("quotationStatus").value;
	var module = getDomElementById("module").value;
	var page =  getDomElementById("page").value;
	var contractStatus="";
	var isNew="";
	var userType = "";
	if(!isNull(getDomElementById("contractStatus"))){
		contractStatus = getDomElementById("contractStatus").value;
		isNew = getDomElementById("isNew").value;
		userType = getDomElementById("userType").value;
	}
	if((!isNull(contractStatus) && contractStatus == "C" && !isNull(isNew) && isNew == "true") 
			|| (!isNull(userType) && userType == "S" && !isNull(contractStatus) && 
					( contractStatus == "S" || contractStatus == "A"))
	)
	{
		if(productCode == 'CO'){
			disableOrEnableCourierGridFields();
		}
		enableProposedRatesButtons();		
	}else if(module == "view" && status == 'S' && isNull(page)){
		if((aprvdAt == "RO") && aprvrLevel == "R"){
			disableVobAndWeights();
			if(productCode == 'CO'){
				disableCourierGridFields();
			}else{
				disableProposedRatesGridFields();
				disableSpecialDestGridFields();
			}
			diabledProposedRatesButtons();			
		}else{
			if(productCode == 'CO'){
				disableOrEnableCourierGridFields();
			}
			enableProposedRatesButtons();
		}
	}else if(!isNull(status) && status != 'N'){
		disableVobAndWeights();
		if(productCode == 'CO'){
			disableCourierGridFields();
		}else{
			disableProposedRatesGridFields();
			disableSpecialDestGridFields();
		}
		diabledProposedRatesButtons();
	}else if(status == 'N' && productCode == 'CO'){
		disableOrEnableCourierGridFields();
	}
}

function clearDropDownList(selectId) {	
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "--Select--", "");
}

function clearCityDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "ALL", "");
}



function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

/*function setWeightSlab(val, selDropdown){
	chngWt = false;
	if(weightSlabVal != "" && val != weightSlabVal){
		flag = confirm("Do u want to change the Weight Slab Details");
	if (!flag) {
		chngWt = false;
		getDomElementById(selDropdown).value = weightSlabVal;
		weightSlabVal = "";
		return;
	}else{
		if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)){
			for(var i = 1; i<5; i++)
				getDomElementById("rateWtSlabId"+prodCatVal+i).value = "";

			getDomElementById("rateAddWtSlab"+prodCatVal).value = "";
		}
	}
	}else
		weightSlabVal = val;
	chngWt = true;

}*/

function assignAddWeightSlab(selSlab){
	var prevVal = getDomElementById("ratehdnAddWtSlab"+prodCatId).value;
	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)  && !isNull(getValueByElementId(selSlab)) && !isNull(prevVal)){
		flag = confirm("Do you want to change weight slab");
		if (!flag) {
			getDomElementById(selSlab).value = prevVal;
			return;
		}
	}

	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)){
		for(var i = 1; i<5; i++)
			getDomElementById("rateWtSlabId"+prodCatId+i).value = "";

		getDomElementById("rateAddWtSlab"+prodCatId).value = "";
	}

	var lst = getDomElementById(selSlab);
	if(isNull(lst.value)){

		clearAddAboveWegihtSlabs();
	}
	else{
		getDomElementById("rateSpecialAddWeightSlab"+prodCatId).innerHTML = lst.options[lst.selectedIndex].text;
		//validAboveWeightSlabs();
	}

	if(!isNull(getValueByElementId(selSlab))){
		enableOrDisableRateFields(selSlab,prodCatId,5,false);
	}
}

function assignWeightSlab(selSlab,startWt,endWt, specialStartWt, prodCatVal, curPos){

	var prevVal = getDomElementById("ratehdnWtId"+prodCatVal+curPos).value;

	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value) && !isNull(getValueByElementId(selSlab)) && !isNull(prevVal)){
		flag = confirm("Do you want to change weight slab");
		if (!flag) {
			getDomElementById(selSlab).value = prevVal;
			return;
		}else{
			for(var i = 1; i<5; i++)
				getDomElementById("rateWtSlabId"+prodCatVal+i).value = "";

			getDomElementById("rateAddWtSlab"+prodCatVal).value = "";
		}
	}

	if(prodCode == "AR" || prodCode == "TR"){
		assignAboveWeightSlabs(selSlab,startWt,endWt, specialStartWt, prodCatVal, curPos);
	}

	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)){
		for(var i = 1; i<5; i++)
			getDomElementById("rateWtSlabId"+prodCatVal+i).value = "";

		getDomElementById("rateAddWtSlab"+prodCatVal).value = "";
	}
	var lst = getDomElementById(selSlab);
	var selElement = true;
	if(isNull(lst.value)){

		getDomElementById("rateSpecialEndWeightSlab"+prodCatId+curPos).innerHTML = "";
		getDomElementById("ratehdnWtId"+prodCatVal+curPos).value = "";

		clearGridFieldsData(curPos, curPos,prodCatVal,"");


		for(var i =0 ;i<wtLen;i++){

			if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[i].weightSlabTO.additional != 'Y'){
					if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
						selElement = false;
						for(var j=i+1;j<wtLen;j++){
							if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
									&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
								if(wtList[j].weightSlabTO.additional != 'Y'){
									addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
								}

							}
						}
						break;
					}	 
				}
			}
		}
		if(selElement){
			for(var i =0 ;i<wtLen;i++){

				if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(wtList[i].weightSlabTO.additional != 'Y'){
						addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}

				}
			}
		}



	}else{		
		getDomElementById("rateSpecialEndWeightSlab"+prodCatId+curPos).innerHTML = lst.options[lst.selectedIndex].text;
	}

	if(!isNull(getDomElementById(startWt))){
		clearGridFieldsData(curPos, (curPos+1),prodCatVal,"");

		for(var i =0 ;i<wtLen;i++){

			if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[i].weightSlabTO.additional != 'Y'){
					if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
						for(var j=(i+1);j<wtLen;j++){
							if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
									&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
								if(wtList[j].weightSlabTO.additional != 'Y'){
									getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

									getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

									getDomElementById("rateQuotStartWeight"+(curPos+1)).value = wtList[j].weightSlabTO.weightSlabId;
									break;
								}

							}
						}
						break;
					}
				}
			}    

		}


		for(var i =0 ;i<wtLen;i++){

			if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[i].weightSlabTO.additional != 'Y'){
					if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
						for(var j=i+1;j<wtLen;j++){
							if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
									&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
								if(wtList[j].weightSlabTO.additional != 'Y'){
									addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
								}

							}
						}
						break;
					}	 
				}
			}
		}


	}
	if(!isNull(getValueByElementId(selSlab))){
		enableOrDisableRateFields(selSlab, prodCatVal, curPos, false);
	}

}

function enableOrDisableRateFields(selSlab, prodCatVal, curPos, flag){

	var secLen = sectorList.length;
	for(var j = 0; j< secLen ; j++){
		if(sectorList[j].sectorType == "D" &&  sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(!isNull(configuredType) && ((configuredType == PPX && curPos == 4) || (configuredType == BOTH && curPos == 6))){
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+curPos).disabled = true;
			}else{
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+curPos).disabled = flag;
			}
		}
	}
	if(prodCode != 'CO'){
		for(var i=1;i<rowCount;i++){
			enableOrDisableSplDestRateFields(i,flag);
		}
	}else{	
		if(flag == false){
			for(var i=1;i<rowCount;i++){
				enableCOSplDestRateFields(i);
			}
		}else{
			for(var i=1;i<rowCount;i++){
				disableCOSplDestRateFields(i);
			}
		}
	}
}

rowCount = 1;
var splRow = new Array();
function addRow(tabId){

	splRow = new Array();
	var cols = 0;
	if(configuredType == PPX){
		cols = 10;
	}else if(configuredType == BOTH){
		cols = 12;
	}else{
		cols = 8;
	}


	serialNo = 1;

	addSPlDestRow(cols);
	$('#specialGrid'+tabId)
	.dataTable()
	.fnAddData(splRow);

	loadStateDropDown("stateId"+prodCatId + rowCount, "", statesList);
	clearDropDownList("cityId"+prodCatId+rowCount);	
	if(prodCode != "CO" ){
		enableOrDisableSplDestRateFields(rowCount,false);
	}else{
		enableCOSplDestRateFields(rowCount);
	}
	rowCount++;
}


function addSPlDestRow(cols) {


	serialNo = 1;



	splRow[0]= '<span id="serial'+rowCount+prodCatId+'">'+rowCount+'</span>';
	splRow[1]= '<select  id="stateId'+prodCatId + rowCount
	+'" name="proposedRatesTO.stateId" class="selectBox width145" onchange="getCityByState(this.value,'+rowCount+');" />';

	splRow[2]= '<select id="cityId'+prodCatId
	+ rowCount
	+ '" class="selectBox width145" name="proposedRatesTO.cityId" onchange="validateCities('
	+prodCatId +','+ rowCount
	+ ');"/><input type="hidden" id="city'
	+ prodCatId+rowCount
	+ '" name="city" value = ""/><input type="hidden" name="proposedRatesTO.pinProdCat" id="pinProdCat" value="'+prodCatId+'"/>';
	for(var j=3;j<cols;j++){
		if(configuredType == PPX && j == 6){
			splRow[j]= '<span></span><input type="hidden" id="specialDestRate'
				+ prodCatId+(serialNo++)+rowCount
				+ '" name="specialDestRate" value = "" size="6" disabled class="txtbox"  onfocus="validateStates('+prodCatId+','+rowCount+');"';
			splRow[j]= splRow[j]+' onkeypress="return validateSlabRate(event,'+(serialNo-1)+','+rowCount+');"  onblur="validateFormat(this);" /><input type="hidden" name="destProdCat" id="destProdCat" value="'+prodCatId+'"/>';
		}else if(configuredType == BOTH && j == 8){
			splRow[j]= '<span></span><input type="hidden" id="specialDestRate'
				+ prodCatId+(serialNo++)+rowCount
				+ '" name="specialDestRate" value = "" size="6" disabled class="txtbox"  onfocus="validateStates('+prodCatId+','+rowCount+');"';
			splRow[j]=  splRow[j] + 'onkeypress="return validateSlabRate(event,'+(serialNo-1)+','+rowCount+');"  onblur="validateFormat(this);" /><input type="hidden" name="destProdCat" id="destProdCat" value="'+prodCatId+'"/>';
		}else{
			splRow[j]= '<input type="text" id="specialDestRate'
				+ prodCatId+(serialNo++)+rowCount
				+ '" name="proposedRatesTO.specialDestRate" value = "" size="6" disabled class="txtbox"  onfocus="validateStates('+prodCatId+','+rowCount+');"';
			splRow[j]=  splRow[j] + 'onkeypress="return validateSlabRate(event,'+(serialNo-1)+','+rowCount+');"  onblur="validateFormat(this);" /><input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/>';
		} 

	}

	//splRow[cols-1] = splRow[cols-1]+'<input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/><input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/><input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/><input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/><input type="hidden" name="proposedRatesTO.destProdCat" id="destProdCat" value="'+prodCatId+'"/>';

}

function enableOrDisableSplDestRateFields(rowCount, flag){
	for(var i=1;i<5;i++){
		if(!isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			getDomElementById("specialDestRate"+prodCatId+i+rowCount).disabled = flag;

		}
	}

	if(!isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		getDomElementById("specialDestRate"+prodCatId+"5"+rowCount).disabled = flag;
	}
}


function getCityByState(state,rowId){

	clearDropDownList("cityId"+prodCatId+rowId);
	if(productCode != 'CO'){
		j = 1;
		while(j < 5){
			if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+j).value)){
				getDomElementById("specialDestRate"+prodCatId+j+rowId).value = "";
			}else{
				j = 5;
				break;
			}
			j++;
		}if(j == 5){
			if(!isNull(getDomElementById("rateQuotAddWeight"+prodCatId).value)){
				getDomElementById("specialDestRate"+prodCatId+j+rowId).value = "";

			}

		}
	}else{
		var cols = 0;
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		}
		for(var i = 1; i<=cols; i++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+rowId))){
				getDomElementById("specialDestRate"+prodCatId+i+rowId).value = "";				
			}
		}
	}

	if(!isNull(state)){	
		rowNo = rowId;
		var url = "./rateQuotation.do?submitName=getCityListByStateId&stateId="+state;
		ajaxCall1(url, "rateQuotationForm", populateCity);		
	}
}

function populateCity(data){
	clearCityDropDownList("cityId"+prodCatId+rowNo);	
	if (!isNull(data)) {
		city = jsonJqueryParser(data);
		var error = city[ERROR_FLAG];
		if (data != null && error != null) {
			alert(error);
			getDomElementById("cityId"+prodCatId+rowNo).value = "";			
			//getDomElementById("stateId"+prodCatId+rowNo).value = "";
			//getDomElementById("stateId"+prodCatId+rowNo).focus();
		} else {
			loadCityDropDown("cityId"+prodCatId + rowNo, "", city);
		}

	}
}


function assignOrgSector(){

	if(!isNull(getDomElementById("rateOrgSec"+prodCatId)))
		orgSecId = getDomElementById("rateOrgSec"+prodCatId).value ;
	else
		orgSecId = null;

	for(var n=1;n<=5;n++)
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == "D" &&  sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

				getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n).value = "";

			}
		}

	assignRateValues();
	disableFieldsBasedonStatus();
	
	/* The below method is called to restrict user from modifying proposed rates tab using super-edit functionality */
	restrictRateModificationViaSuperEdit("assignOrgSector");
}


function assignRateValues(){

	k=1;
	if(!isNull(prodCatTO)){
		vobSlabId = prodCatTO.vobSlab;
		getDomElementById("rateVob"+prodCatId).value = vobSlabId;
		if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
		{
			getDomElementById("rateMinChargWt"+prodCatId).value = prodCatTO.rateMinChargeableWeight;
		}

		getDomElementById("rateQuotationProdCatHeaderId").value = prodCatTO.rateQuotationProductCategoryHeaderId;

		secLen = sectorList.length;	
		slabWtLen = prodCatTO.weightSlabTO.length;

		if(!isNull(document.getElementById("flatRateChk"))){
			if(productCode == "TR"){			
				if(prodCatTO.flatRate == 'F'){
					document.getElementById("flatRateChk").checked = true;
				}else{
					document.getElementById("flatRateChk").checked = false;
				}
			}
		}

		if(productCode != "CO"){
			assignGridWeightSlabValues(k,slabWtLen);
			assignGridRateValues(slabWtLen);
			deleteTableRow("specialGrid"+gridId);
			assignSpecialDestGridRateValues(slabWtLen);
		}else{
			assignCOGridWeightSlabValues(k,slabWtLen);
			assignCOGridRateValues(slabWtLen);
			deleteTableRow("specialGrid"+gridId);
			assignCOSpecialDestGridRateValues(slabWtLen);
		}
	}
}

function assignGridWeightSlabValues(k,slabWtLen){
	var slabVal = 0;
	for(var j = 0; j< slabWtLen ; j++){
		if(!isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
			if(k<5){
				getDomElementById("rateStartWeightSlab"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
				getDomElementById("rateSpecialStartWeightSlab"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
				getDomElementById("rateQuotStartWeight"+k).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
				getDropdown("rateQuotEndWeight"+prodCatId+k, k,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel);
				getDomElementById("rateWtSlabId"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
				getDomElementById("ratehdnWtId"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
				slabVal = prodCatTO.weightSlabTO[j].endWeightTO.endWeight;
				k++;
			}
		}else{
			if(isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
				getAddDropdown("rateQuotAddWeight"+prodCatId, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,slabVal);
				getDomElementById("rateAddWtSlab"+prodCatId).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
				getDomElementById("ratehdnAddWtSlab"+prodCatId).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
			}
		}
	}

	assignGridEndWeightSlabValues(k,wtLen);
}


function assignGridEndWeightSlabValues(k,wtSlabLen){
	if(!isNull(getDomElementById("rateStartWeightSlab"+prodCatId+k))){
		for(var j = 0 ;j<wtSlabLen;j++){
			if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[j].weightSlabTO.additional != 'Y'  && !isNull(getDomElementById("rateQuotEndWeight"+prodCatId+(k-1)))){

					if(getDomElementById("rateQuotEndWeight"+prodCatId+(k-1)).value ==  wtList[j].weightSlabTO.weightSlabId){
						if(!isNull(wtList[j+1]) && !isNull(wtList[j+1].weightSlabTO) && (wtList[j+1].weightSlabTO.additional != 'Y')){
							getDomElementById("rateStartWeightSlab"+prodCatId+k).innerHTML = wtList[j+1].weightSlabTO.startWeightLabel;
							getDomElementById("rateSpecialStartWeightSlab"+prodCatId+k).innerHTML = wtList[j+1].weightSlabTO.startWeightLabel;
							getDomElementById("rateQuotStartWeight"+k).value = wtList[j+1].weightSlabTO.weightSlabId;
							clearDropDownList("rateQuotEndWeight"+prodCatId+k);
							for(var n = j+1 ;n<wtSlabLen;n++){
								if(wtList[n].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[n].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[n].weightSlabTO.additional != 'Y'){
										addOptionTODropDown("rateQuotEndWeight"+prodCatId+k,wtList[n].weightSlabTO.endWeightLabel,wtList[n].weightSlabTO.weightSlabId);
									}
								}
							}
						}
						break;
					}

				}
			}
		}
	}
}


function assignGridRateValues(slabWtLen){
	m = 1;
	for(var j = 0; j< slabWtLen ; j++){
		for(var i = 0; i< secLen ; i++){

			if(sectorList[i].sectorType == "D" &&  sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

				if(!isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) )
				{
					assignProductGridRateValues(i,j,m);
				}else if(isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
					assignProductGridRateValues(i,j,5);
				}				
			}
		}
		m++;
	}
}

function assignProductGridRateValues(i,j,m){
	var slabRateLength = prodCatTO.weightSlabTO[j].slabRateTO.length;
	for(var n=0;n<slabRateLength;n++){
		if(sectorList[i].sectorTO.sectorId == prodCatTO.weightSlabTO[j].slabRateTO[n].destinationSector){
			if(isNull(orgSecId) || (!isNull(orgSecId) && orgSecId == prodCatTO.weightSlabTO[j].slabRateTO[n].originSector)){

				if(prodCatTO.weightSlabTO[j].slabRateTO[n].valueFromROI != 'Y'){
					if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
						getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
					}
				}else{
					for(var p = 0; p< secLen ; p++){
						if(sectorList[p].sectorType == "D" 
							&&  sectorList[p].sectorTO.sectorCode == "ROI" 
								&&	(sectorList[p].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "CO"
									|| sectorList[p].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "EC")
									&& sectorList[p].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m))){
								getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
							}
						}
					}
				}
				if(!isNull(getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
					getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = 'N';
				}
			}
		}
	}


}

function assignSpecialDestGridRateValues(slabWtLen){
	var splRateLen = 0;
	if(!isNull(prodCatTO.weightSlabTO[0].splDestRateTO) && prodCatTO.weightSlabTO[0].splDestRateTO.length >0){
		cnt = 0;
		lenSlab = prodCatTO.weightSlabTO.length;

		for(var l=0;l<lenSlab;l++){
			cnt = cnt+prodCatTO.weightSlabTO[l].splDestRateTO.length;
		}

		splRateLen = cnt/lenSlab;
		n = 1;

		for(var i = 0; i< splRateLen ; i++){
			if(isNull(orgSecId) || (!isNull(orgSecId) && orgSecId == prodCatTO.weightSlabTO[0].splDestRateTO[i].originSector)){
				addRow(gridId);
				getDomElementById("stateId"+prodCatId+n).value = prodCatTO.weightSlabTO[0].splDestRateTO[i].stateId;

				clearCityDropDownList("cityId"+prodCatId+n);	

				loadCityDropDown("cityId"+prodCatId+n, "", prodCatTO.weightSlabTO[0].splDestRateTO[i].cityList);
				if(!isNull(prodCatTO.weightSlabTO[0].splDestRateTO[i].specialDestinationCityTO)){
					getDomElementById("cityId"+prodCatId+n).value = prodCatTO.weightSlabTO[0].splDestRateTO[i].specialDestinationCityTO.cityId;
				}
				m = 1;
				var city = getDomElementById("cityId"+prodCatId+n).value;
				for(var j = 0; j< slabWtLen ; j++){
					if(!isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						for(var k=0;k<prodCatTO.weightSlabTO[j].splDestRateTO.length;k++){					
							if((isNull(orgSecId) || (!isNull(orgSecId) && orgSecId == prodCatTO.weightSlabTO[j].splDestRateTO[k].originSector))
									&& (getDomElementById("stateId"+prodCatId+n).value == prodCatTO.weightSlabTO[j].splDestRateTO[k].stateId)
									&& ((isNull(city) && isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO)) 
											|| (!isNull(city) && !isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO)
													&& (city == prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO.cityId)))){

								getDomElementById("specialDestRate"+prodCatId+m+n).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
								break;
							}
						}				
					}else if(isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						for(var k=0;k<prodCatTO.weightSlabTO[j].splDestRateTO.length;k++){
							if((isNull(orgSecId) || (!isNull(orgSecId) && orgSecId == prodCatTO.weightSlabTO[j].splDestRateTO[k].originSector))
									&& (getDomElementById("stateId"+prodCatId+n).value == prodCatTO.weightSlabTO[j].splDestRateTO[k].stateId)
									&& ((isNull(city) && isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO)) 
											|| (!isNull(city) && !isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO)
													&& (city == prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO.cityId))) ){
								getDomElementById("specialDestRate"+prodCatId+"5"+n).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
								break;
							}
						}
					}
					m++;
				}
				n++;
			}
		}
	}
}



function getDropdown(field, k, val, label){

	clearDropDownList("rateQuotEndWeight"+prodCatId+k);
	wtDropDown(field,k,val);
	getDomElementById(field).value = val;
	getDomElementById("rateSpecialEndWeightSlab"+prodCatId+k).innerHTML = label;

}


function wtDropDown(field,k,val){
	wtListLength = wtLen;
	for(var i = 0 ;i<wtListLength;i++){
		if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(wtList[i].weightSlabTO.additional != 'Y'){
				if(k==1){
					addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
				}else{
					if(getDomElementById("rateQuotEndWeight"+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
						for(var j = ++i ;j<wtListLength;j++){
							if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
									&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
								if(wtList[j].weightSlabTO.additional != 'Y'){
									addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
								}
							}
						}
						break;
					}
				}
			}
		}
	}
}

function getAddDropdown(field, val, label,slabVal){

	clearDropDownList("rateQuotAddWeight"+prodCatId);
	addWtDropDown(field,val,slabVal);
	getDomElementById(field).value = val;
	getDomElementById("rateSpecialAddWeightSlab"+prodCatId).innerHTML = label;

}


function addWtDropDown(field,val,slabVal){
	wtListLength = wtLen;
	for(var i = 0 ;i<wtListLength;i++){
		if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(wtList[i].weightSlabTO.additional == 'Y'){
				if(prodCode == "AR" || prodCode == "TR"){
					if(wtList[i].weightSlabTO.startWeight >= parseFloat(slabVal)){
						addOptionTODropDown("rateQuotAddWeight"+prodCatId,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						break;
					}
				}else{
					addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
				}
			}
		}
	}
}


/**
 * This Method Deleted the selected row from the Grid
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}


function deleteTableRow(tableId) {
	//$("#example4 > tbody > tr").remove();
	//$("#example4 > tbody").empty();
	try {
		var table = getDomElementById(tableId);

		var tableRowCount = table.rows.length;
		for ( var i = 0; i < tableRowCount; i++) {
			deleteRow(tableId, i);
			tableRowCount--;
			i--;
		}
	}catch(e){
		alert(e);
	}
	rowCount = 1;

}


function validateFormat(obj){
	val = obj.value;
	/*if(!isNull(val)){
		if (val.indexOf(".") > 0  || val.length <= 3) {
		var parts = val.split(".");

		if (typeof parts[0] == "string"
				&& (parseInt(parts[0],10) < 0 && parseInt(parts[0],10) >= 1000)) {
			alert("Pelase enter valid Rate value.");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}

		if (parseInt(parts[1],10) < 0 && parseInt(parts[1],10) >= 100) {
			alert("Please enter valid Rate value.");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
			}
		}else{
			 alert("Please enter the Rate value in 999.99 format");
			 obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
		}

	}*/
	if(!isNull(val)){
		var num = new Number(val);
		if(/^[0-9]{0,3}(\.[0-9]{0,2})?$/.test(val) && num > 0){
			return true;
		} else {
			alert('Please enter the Rate value in 999.99 format');
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}
	}

	return true;
}

function validateSlabRate(event,i, j){

	if(i==5)
		field  = getDomElementById("rateQuotAddWeight"+prodCatId);	
	else	
		field  = getDomElementById("rateQuotEndWeight"+prodCatId+i);


	//if(isNull(field.value)){
	//alert("Please select WeightSlab");
	//return false;
	//}
	if(!isNull(j)){
		if(isNull(getDomElementById("stateId"+prodCatId+j).value)){
			alert("Please select state");
			setTimeout(function() {
				getDomElementById("stateId"+prodCatId+j).focus();
			}, 10);
			return false;
		}else
			j = "";
	}
	if(isNull(j)){

		var charCode;
		if (window.event)
			charCode = window.event.keyCode; // IE
		else
			charCode = event.which; 

		if(charCode == 13 || charCode == 9 || charCode == 8 || charCode == 0)
			return true;
		else return onlyDecimal(event);
	}
}



function checkGridFieldsData(){
	check  = true;

	secLen = sectorList.length;
	wtLen = wtList.length;

	if(checkGridHeaderData() && checkProductGridData()){

		//if(productCode == "CO" || productCode == "EC"){
		if(!checkSpecialDestGridData()){
			if(productCode != 'CO'){
				disableGridFields(5);
				disableSplGridFields(5);
			}else{
				disableOrEnableCourierGridFields();		
				/*for(var k=1;k<rowCount;k++){
							disableCOSplDestRateFields(k);
						}*/
			}


			return false;
		}
		//}

		if(!isNull(getDomElementById("rateMinChargWt"+prodCatId))){
			if(isNull(getDomElementById("rateMinChargWt"+prodCatId).value)){
				alert("Please Select Minimum Chargeable Weight");
				getDomElementById("rateMinChargWt"+prodCatId).focus();
				if(productCode != 'CO'){
					disableGridFields(5);
					disableSplGridFields(5);
				}else{
					disableOrEnableCourierGridFields();		
					/*for(var k=1;k<rowCount;k++){
							disableCOSplDestRateFields(k);
						}*/
				}


				return false;
			}
		}
	}else {
		if(productCode != 'CO'){
			disableGridFields(5);
			disableSplGridFields(5);
		}else{
			disableOrEnableCourierGridFields();		
			/*for(var k=1;k<rowCount;k++){
					disableCOSplDestRateFields(k);
				}*/
		}


		return false;
	}

	return true;
}

function checkGridHeaderData(){
	if(productCode != 'CO'){
		for(var i=1;i<5;i++){
			if(!isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
				return true;
			}
		}

		/*if(!isNull(getValueByElementId("rateQuotAddWeight"+prodCatId)))
			return true;*/
	}else{
		var cols = 5;
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		} 
		if(configuredType == DOX){
			for(var i=1;i<cols;i++){
				if(!isNull(getDomElementById("rateQuotEndWeightDOX"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightDOX"+prodCatId+i))){
					return true;
				}
			}

		}else if(configuredType == PPX){
			for(var i=1;i<=cols;i++){
				if(i<3 && !isNull(getDomElementById("rateQuotEndWeightPPXPA"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightPPXPA"+prodCatId+i))){
					return true;
				}else if(i < 7 && !isNull(getDomElementById("rateQuotEndWeightPPXKT"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightPPXKT"+prodCatId+i))){
					return true;
				}			

			}

		}else if(configuredType == BOTH){
			for(var i=1;i<=cols;i++){

				if((i<5) && !isNull(getDomElementById("rateQuotEndWeightBOTH"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightBOTH"+prodCatId+i))){				
					return true;
				}else if((i < 9) && !isNull(getDomElementById("rateQuotEndWeightBOTHKT"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightBOTHKT"+prodCatId+i))){				
					return true;
				}			

			}

		}
	}

	alert("Please select atleast one weight slab");
	return false;
}

function prepareSectorArr(){
	secArr = "";
	for(var i = 0; i< secLen ; i++){
		if(sectorList[i].sectorType == "D" && sectorList[i].sectorTO.sectorType == "A" && 
				sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(secArr.length >0)
				secArr = secArr+",";	
			secArr = secArr + sectorList[i].sectorTO.sectorId;
		}
	}

	getDomElementById("secArrStr").value = secArr;
}


function setupDropdownValues(){

	getDomElementById("rateVobSlabsId").value = getDomElementById("rateVob"+prodCatId).value;


	if(!isNull(getDomElementById("rateOrgSec"+prodCatId)))
		getDomElementById("rateOriginSectorId").value = getDomElementById("rateOrgSec"+prodCatId).value;	
	else
		getDomElementById("rateOriginSectorId").value = null;

	if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
		getDomElementById("rateMinChargWtId").value = getDomElementById("rateMinChargWt"+prodCatId).value;
	else
		getDomElementById("rateMinChargWtId").value = null;

	if(!isNull(getDomElementById("rateQuotAddWeight"+prodCatId)))
		getDomElementById("rateQuotAddWeight").value = getDomElementById("rateQuotAddWeight"+prodCatId).value;

	if(!isNull(getDomElementById("rateAddWtSlab"+prodCatId)))
		getDomElementById("rateAddWtSlabId").value = getDomElementById("rateAddWtSlab"+prodCatId).value;
}

function assignROIValuesToZones(){

	var cols = 5;

	if(configuredType == DOX || productCode == "EC"){
		cols = 5;
	}else if(configuredType == PPX){
		cols = 7;
	}else if(configuredType == BOTH){
		cols = 9;
	} 

	if(productCode == "CO" || productCode == "EC"){


		for(var i = 0; i< secLen ; i++){

			if(sectorList[i].sectorType == "D" &&  sectorList[i].sectorTO.sectorCode == "ROI" 
				&& ( sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "CO" ||
						sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "EC")
						&& sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

				for(var n=1;n<=cols;n++){
					for(var j = 0; j< secLen ; j++){
						if(sectorList[j].sectorType == "D"  && (sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "CO" ||
								sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "EC")
								&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

							//if(sectorList[j].sectorTO.sectorCode != regionCode &&

							if(sectorList[j].sectorTO.representSectorCode == "ROI" && isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+n).value)
									&& !isNull(getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n).value)){
								getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+n).value = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+n).value;
								getDomElementById("isROI"+prodCatId+sectorList[j].sectorTO.sectorId+n).value = 'Y';
							}

						}
					}
				}

				break;
			}
		}
	}/*else{
			for(var j = 0; j< secLen ; j++){
				if(sectorList[j].sectorType == "D"  
					&& (sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "CO"
						|| sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryCode == "EC")
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(sectorList[j].sectorTO.sectorCode == regionCode){
						for(var n=1;n<=5;n++){
						getDomElementById("rate"+sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId+sectorList[j].sectorTO.sectorId+n).disabled = false;
						}
					break;
					}
				}
			}
		}*/
}

function clearGridFieldsData(curPos, nextPos,prodCatVal,type){

	var pos;

	if(curPos == nextPos)
		pos = curPos;
	else
		pos = nextPos;	

	var col = 0;
	if(prodCode == "EC" || prodCode == "TR" || prodCode == "AR" || type == "DOX"){
		col = 5;
	}else if(type == "PPX" || type == "PPXKT" || type == "PPXKU"){
		col = 7;
	}

	for(var i= pos ;i<col;i++){

		if((pos == curPos && i != curPos) || (curPos != nextPos)){
			if(!isNull(getDomElementById("rateStartWeightSlab"+type+prodCatVal+i))){
				getDomElementById("rateStartWeightSlab"+type+prodCatVal+i).innerHTML = "";
			}	

			getDomElementById("rateQuotStartWeight"+i).value = "";
		}
		clearDropDownList("rateQuotEndWeight"+type+prodCatVal+i);
		//if(productCode == "CO" || productCode == "EC"){
		if((pos == curPos && i != curPos) || (curPos != nextPos)){
			getDomElementById("rateSpecialStartWeightSlab"+prodCatId+i).innerHTML = "";
			getDomElementById("rateSpecialEndWeightSlab"+prodCatId+i).innerHTML = "";
		}
		//}
		for(var j = 0; j< secLen ; j++){
			if(sectorList[j].sectorType == "D" &&  sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).value = "";
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
			}
		}

		//if(productCode == "CO" || productCode  == "EC"){
		for(var k = 1; k<rowCount; k++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+k))){
				getDomElementById("specialDestRate"+prodCatId+i+k).value = "";
				getDomElementById("specialDestRate"+prodCatId+i+k).disabled = true;
			}
		}
		//}
	}


}


function checkProductGridData(){

	var regionRate = false;
	var regRate = false;
	var gRate = false;
	var rCol = 0;
	if(productCode != 'CO'){
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == "D"
				&& !(sectorList[i].sectorTO.sectorCode  == "ROI") 
				&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)
			{ 

				if(productCode == "EC" && !isNull(regionCode)){
					if(regionRate == true && regRate == true){
						gRate = true;
						i = rCol;
					}else{
						rCol = i;
					}
				}

				for(var j = 1; j <= 5 ; j++){
					if(j<4){
						if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+j).value)){

							field = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+j);
							if(isNull(field.value)){
								if(!isNull(regionCode) && (productCode == "EC") && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
									regRate = true;
									continue;
								}
								clearZonesData();
								disableGridFields(5);
								disableSplGridFields(5);
								lst = getDomElementById("rateQuotEndWeight"+prodCatId+j);
								alert("Please fill the Grid details for Weight Slab: "+getDomElementById("rateStartWeightSlab"+prodCatId+j).innerHTML+" - "+ lst.options[lst.selectedIndex].text + " Sector: "+ sectorList[i].sectorTO.sectorName);
								field.focus();
								return false;
							}else{
								if(!isNull(regionCode) && (productCode == "EC") && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
									regionRate = true;
								}
							}
						}
					}if(j==5){
						if(!isNull(getDomElementById("rateQuotAddWeight"+prodCatId).value)){
							field = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+j);
							if(isNull(field.value)){
								if(!isNull(regionCode) && (productCode == "EC") && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
									regRate = true;
									continue;
								}
								clearZonesData();
								disableGridFields(5);
								disableSplGridFields(5);
								lst = getDomElementById("rateQuotAddWeight"+prodCatId);
								alert("Please fill the Grid details for Weight Slab: "+lst.options[lst.selectedIndex].text+ " Sector: "+ sectorList[i].sectorTO.sectorName);
								field.focus();
								return false;
							}else{
								if(!isNull(regionCode) && (productCode == "EC") && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
									regionRate = true;
								}
							}
						}
					}
				}
			}
		}
	}else{

		var cols = 5;
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		} 
		var add = true;
		var field = "";
		var secField = "";
		var satrtWtField = "";
		for(var i = 0; i< secLen ; i++){
			if(sectorList[i].sectorType == "D"
				&& !(sectorList[i].sectorTO.sectorCode  == "ROI") 
				&& sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)
			{ 


				if(regionRate == true && regRate == true && !isNull(regionCode)){
					gRate = true;
					i = rCol;
				}else{
					rCol = i;
				}

				j =1;

				for(j; j <= cols;j++){
					satrtWtField = "";
					field = "";
					if(configuredType == DOX && j<5){
						field = "rateQuotEndWeightDOX";
						satrtWtField = "rateStartWeightSlabDOX";
						add = false;
					}else if(configuredType == DOX && j == 5){
						field = "rateQuotAddWeightDOX";
						add = true;
					}else if(configuredType == PPX && j < 3){
						field = "rateQuotEndWeightPPXPA";
						satrtWtField = "rateStartWeightSlabPPXPA";
						add = false;
					}else if(configuredType == PPX && j == 3){
						field = "rateQuotAddWeightPPXPD";
						add = true;
					}else if(configuredType == PPX && j == 4){
						field = "rateQuotEndWeightPPXKU";
						add = false;
						continue;
					}else if(configuredType == PPX && j > 4 && j < 7){
						field = "rateQuotEndWeightPPXKT";
						if(j == 5){
							satrtWtField = "rateStartWeightSlabPPXKU";
						}else{
							satrtWtField = "rateStartWeightSlabPPXKT";
						}
						add = false;
					}else if(configuredType == PPX && j == 7){
						field = "rateQuotAddWeightPPXKA";
						add = true;
					}else if(configuredType == BOTH && j < 5){
						field = "rateQuotEndWeightBOTH";
						satrtWtField = "rateStartWeightSlabBOTH";
						add = false;
					}else if(configuredType == BOTH && j == 5){
						field = "rateQuotAddWeightBOTH";
						add = true;
					}else if(configuredType == BOTH && j == 6){
						field = "rateQuotEndWeightBOTHKU";
						add = false;
						continue;
					}else if(configuredType == BOTH && j > 6 && j < 9){
						field = "rateQuotEndWeightBOTHKT";
						if(j == 7){
							satrtWtField = "rateStartWeightSlabBOTHKU";
						}else{
							satrtWtField = "rateStartWeightSlabBOTHKT";
						}
						add = false;
					}else if(configuredType == BOTH && j == 9){
						field = "rateQuotAddWeightBOTHKA";
						add = true;
					}

					field = field+prodCatId+j;
					satrtWtField = satrtWtField+prodCatId+j;

					if((add == false) && !isNull(getDomElementById(field)) && !isNull(getDomElementById(field).value)){

						secField = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+j);
						if(isNull(secField.value)){

							if(!isNull(regionCode) && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
								regRate = true;
								continue;
							}
							clearZonesData();

							disableOrEnableCourierGridFields();

							lst = getDomElementById(field);
							gRate = true;
							alert("Please fill the Grid details for Weight Slab: "+getDomElementById(satrtWtField).innerHTML+" - "+ lst.options[lst.selectedIndex].text + " Sector: "+ sectorList[i].sectorTO.sectorName);
							secField.focus();
							return false;
						}else{
							if(!isNull(regionCode) && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
								regionRate = true;
							}
						}
					}else if(add == true && !isNull(getDomElementById(field)) && !isNull(getDomElementById(field).value)){
						secField = getDomElementById("rate"+prodCatId+sectorList[i].sectorTO.sectorId+j);
						if(isNull(secField.value)){
							if(!isNull(regionCode) && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
								regRate = true;
								continue;
							}
							clearZonesData();

							disableOrEnableCourierGridFields();

							lst = getDomElementById(field);
							gRate = true;
							alert("Please fill the Grid details for Weight Slab: "+lst.options[lst.selectedIndex].text+ " Sector: "+ sectorList[i].sectorTO.sectorName);
							secField.focus();
							return false;
						}else{
							if(!isNull(regionCode) && (gRate == false) && (sectorList[i].sectorTO.sectorCode == regionCode)){
								regionRate = true;
							}
						}
					}					
				}
			}
		}
	}
	return true;
}


function clearZonesData(){
	var n = 0;
	if(prodCode == "EC"){
		for(var m = 0; m< secLen ; m++){
			if(sectorList[m].sectorType == "D"  && sectorList[m].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[m].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				n = 1;
				while(n < 5){
					if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+n).value)){
						if(getDomElementById("isROI"+prodCatId+sectorList[m].sectorTO.sectorId+n).value == 'Y'){
							getDomElementById("rate"+prodCatId+sectorList[m].sectorTO.sectorId+n).value = "";
						}

					}else {
						n = 5;
						break;
					}
					n++;
				}if(n==5){
					if(getDomElementById("isROI"+prodCatId+sectorList[m].sectorTO.sectorId+n).value == 'Y'){
						getDomElementById("rate"+prodCatId+sectorList[m].sectorTO.sectorId+n).value = "";
					}
				}
			}
		}
	}else if(productCode == "CO"){
		var cols = 5;
		var field = "";
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		} 
		for(var m = 0; m< secLen ; m++){
			if(sectorList[m].sectorType == "D"  && sectorList[m].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[m].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				n = 1;


				for(n;n <= cols;n++){
					field = "";
					if(configuredType == DOX && n<5){
						field = "rateQuotEndWeightDOX";						
					}else if(configuredType == DOX && n == 5){
						field = "rateQuotAddWeightDOX";						
					}else if(configuredType == PPX && n < 3){
						field = "rateQuotEndWeightPPXPA";						
					}else if(configuredType == PPX && n == 3){
						field = "rateQuotAddWeightPPXPD";						
					}else if(configuredType == PPX && n == 4){
						field = "rateQuotEndWeightPPXKU";
						continue;
					}else if(configuredType == PPX && n > 4 && n < 7){
						field = "rateQuotEndWeightPPXKT";						
					}else if(configuredType == PPX && n == 7){
						field = "rateQuotAddWeightPPXKA";						
					}else if(configuredType == BOTH && n < 5){
						field = "rateQuotEndWeightBOTH";
					}else if(configuredType == BOTH && n == 5){
						field = "rateQuotAddWeightBOTH";
					}else if(configuredType == BOTH && n == 6){
						field = "rateQuotEndWeightBOTHKU";
						continue;
					}else if(configuredType == BOTH && n > 6 && n < 9){
						field = "rateQuotEndWeightBOTHKT";
					}else if(configuredType == BOTH && n == 9){
						field = "rateQuotAddWeightBOTHKA";
					}

					field = field+prodCatId+n;

					if(!isNull(field) && !isNull(getDomElementById(field)) && !isNull(getDomElementById(field).value)){
						if(getDomElementById("isROI"+prodCatId+sectorList[m].sectorTO.sectorId+n).value == 'Y'){
							getDomElementById("rate"+prodCatId+sectorList[m].sectorTO.sectorId+n).value = "";
						}

					}					
				}
			}
		}
	} 
}


function checkSpecialDestGridData(){
	var j = 1;
	if(productCode != 'CO'){

		for(var i = 1; i<rowCount; i++){
			if(!isNull(getDomElementById("stateId"+prodCatId+i).value)){

				j = 1;
				while(j < 5){

					if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+j).value)){
						field = getDomElementById("specialDestRate"+prodCatId+j+i);

						if(isNull(field.value)){
							clearZonesData();
							disableGridFields(5);
							disableSplGridFields(5);
							lst = getDomElementById("rateQuotEndWeight"+prodCatId+j);
							alert("Row number : "+ i + " Please fill the special Destination Grid details for Weight Slab: "+getDomElementById("rateStartWeightSlab"+prodCatId+j).innerHTML+" - "+ lst.options[lst.selectedIndex].text);
							field.focus();
							return false;	
						} 
					}else{
						j = 5;
						break;
					}
					j++;
				}if(j == 5){
					if(!isNull(getDomElementById("rateQuotAddWeight"+prodCatId).value)){
						field = getDomElementById("specialDestRate"+prodCatId+j+i);
						if(isNull(field.value)){
							clearZonesData();
							disableGridFields(5);
							disableSplGridFields(5);
							lst = getDomElementById("rateQuotAddWeight"+prodCatId);
							alert("Row number : "+ i + " Please fill the special Destination Grid details for Weight Slab: "+ lst.options[lst.selectedIndex].text);
							field.focus();
							return false;	
						}
					}

				}

			}
		}
	}else{

		var cols = 5;
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		} 
		var add = true;
		var field = "";
		var secField = "";
		var satrtWtField = "";

		for(var i = 1; i<rowCount; i++){
			if(!isNull(getDomElementById("stateId"+prodCatId+i).value)){

				j = 1;

				for(j;j <= cols;j++){
					satrtWtField = "";
					if(configuredType == DOX && j<5){
						field = "rateQuotEndWeightDOX";
						satrtWtField = "rateStartWeightSlabDOX";
						add = false;
					}else if(configuredType == DOX && j == 5){
						field = "rateQuotAddWeightDOX";
						add = true;
					}else if(configuredType == PPX && j < 3){
						field = "rateQuotEndWeightPPXPA";
						satrtWtField = "rateStartWeightSlabPPXPA";
						add = false;
					}else if(configuredType == PPX && j == 3){
						field = "rateQuotAddWeightPPXPD";
						add = true;
					}else if(configuredType == PPX && j == 4){
						field = "rateQuotEndWeightPPXKU";
						add = false;
						continue;
					}else if(configuredType == PPX && j > 4 && j < 7){
						field = "rateQuotEndWeightPPXKT";
						if(j == 5){
							satrtWtField = "rateStartWeightSlabPPXKU";
						}else{
							satrtWtField = "rateStartWeightSlabPPXKT";
						}
						add = false;
					}else if(configuredType == PPX && j == 7){
						field = "rateQuotAddWeightPPXKA";
						add = true;
					}else if(configuredType == BOTH && j < 5){
						field = "rateQuotEndWeightBOTH";
						satrtWtField = "rateStartWeightSlabBOTH";
						add = false;
					}else if(configuredType == BOTH && j == 5){
						field = "rateQuotAddWeightBOTH";
						add = true;
					}else if(configuredType == BOTH && j == 6){
						field = "rateQuotEndWeightBOTHKU";
						add = false;
						continue;
					}else if(configuredType == BOTH && j > 6 && j < 9){
						field = "rateQuotEndWeightBOTHKT";
						if(j == 7){
							satrtWtField = "rateStartWeightSlabBOTHKU";
						}else{
							satrtWtField = "rateStartWeightSlabBOTHKT";
						}
						add = false;
					}else if(configuredType == BOTH && j == 9){
						field = "rateQuotAddWeightBOTHKA";
						add = true;
					}

					field = field+prodCatId+j;
					satrtWtField = satrtWtField+prodCatId+j;

					if(add == false && !isNull(getDomElementById(field) && !isNull(getDomElementById(field).value))){
						secField = getDomElementById("specialDestRate"+prodCatId+j+i);

						if(isNull(secField.value)){
							clearZonesData();
							disableOrEnableCourierGridFields();
							/*for(var k=1;k<rowCount;k++){
							disableCOSplDestRateFields(k);
						}*/
							lst = getDomElementById(field);
							alert("Row number : "+ i + " Please fill the special Destination Grid details for Weight Slab: "+getDomElementById(satrtWtField).innerHTML+" - "+ lst.options[lst.selectedIndex].text);
							secField.focus();
							return false;	
						} 
					}else if(add == true && !isNull(getDomElementById(field) && !isNull(getDomElementById(field).value))){
						secField = getDomElementById("specialDestRate"+prodCatId+j+i);
						if(isNull(secField.value)){
							clearZonesData();
							disableOrEnableCourierGridFields();
							/*for(var k=1;k<rowCount;k++){
							disableCOSplDestRateFields(k);
						}*/
							lst = getDomElementById(field);
							alert("Row number : "+ i + " Please fill the special Destination Grid details for Weight Slab: "+ lst.options[lst.selectedIndex].text);
							secField.focus();
							return false;	
						}
					}
				}

			}
		}
	}
	return true;
}
function addRateRows(){

	var vbLen = vobList.length;
	var sectLen = sectorList.length;
	var minChrgWtLen = minChrgList.length;

	for(var i=0;i<vbLen;i++){
		if(	vobList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	vobList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(!isNull(vobList[i].vobSlabTO.upperLimit)){
				addOptionTODropDown("rateVob"+prodCatId, vobList[i].vobSlabTO.lowerLimitLabel +" - "+vobList[i].vobSlabTO.upperLimitLabel, vobList[i].vobSlabTO.vobSlabId);
			}else{
				addOptionTODropDown("rateVob"+prodCatId, vobList[i].vobSlabTO.lowerLimitLabel, vobList[i].vobSlabTO.vobSlabId);
			}
		}
	}
	getDomElementById("rateVob"+prodCatId).options[0].selected = true;


	for(var j=0;j<sectLen;j++){

		if(	sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId
		){
			if( sectorList[j].sectorType == "D" ){
				i=1;

				if(productCode != "CO"){
					if(sectorList[j].sectorTO.sectorCode != "ROI"){ 
						$('#example'+gridId)
						.dataTable()
						.fnAddData(
								[

								 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',

								 ]);
					}else{
						$('#example'+gridId)
						.dataTable()
						.fnAddData(
								[

								 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
								 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',

								 ]);
					}
				}else{
					if(getDomElementById("configuredType").value == DOX){
						if(sectorList[j].sectorTO.sectorCode != "ROI"){ 
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',

									 ]);
						}else{
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',

									 ]);
						}

					}else if(getDomElementById("configuredType").value == PPX){
						if(sectorList[j].sectorTO.sectorCode != "ROI"){ 
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<span></span><input type="hidden" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1)+'" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',

									 ]);
						}else{
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<span></span><input type="hidden" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',

									 ]);
						}

					}else if(getDomElementById("configuredType").value == BOTH){
						if(sectorList[j].sectorTO.sectorCode != "ROI"){
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<span></span><input type="hidden" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1)+'" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',


									 ]);

						}else{
							$('#example1')
							.dataTable()
							.fnAddData(
									[

									 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<span></span><input type="hidden" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',
									 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="rate" size="6"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="isROI" value="N" />',


									 ]);
						}
					}
				}
			}else{
				addOptionTODropDown("rateOrgSec"+prodCatId,sectorList[j].sectorTO.sectorName,sectorList[j].sectorTO.sectorId);
			}
		}
	}

	if(!isNull(getDomElementById("rateMinChargWt"+prodCatId)))
		clearDropDownList("rateMinChargWt"+prodCatId);

	for(var i=0;i<minChrgWtLen;i++){
		if(	minChrgList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	minChrgList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)
		{

			addOptionTODropDown("rateMinChargWt"+prodCatId,minChrgList[i].minChargeableWeight,minChrgList[i].rateMinChargeableWeightId);
		}
	}

}



function addCourierRateRows(){
	var vbLen = vobList.length;
	var sectLen = sectorList.length;


	for(var i=0;i<vbLen;i++){
		if(	vobList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	vobList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(!isNull(vobList[i].vobSlabTO.upperLimit)){
				addOptionTODropDown("rateVob"+prodCatId, vobList[i].vobSlabTO.lowerLimit +" - "+vobList[i].vobSlabTO.upperLimit, vobList[i].vobSlabTO.vobSlabId);
			}else{
				addOptionTODropDown("rateVob"+prodCatId, vobList[i].vobSlabTO.lowerLimit +" - ", vobList[i].vobSlabTO.vobSlabId);
			}
		}
	}
	getDomElementById("rateVob"+prodCatId).options[0].selected = true;

	for(var j=0;j<sectLen;j++){

		if(	sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId
		){
			if( sectorList[j].sectorType == "D" ){
				i=1;

				$('#example'+gridId)
				.dataTable()
				.fnAddData(
						[

						 '<span id="serial'+rowCount+'">'+sectorList[j].sectorTO.sectorName+'</span>',
						 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
						 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
						 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
						 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',
						 '<input type="text" id="rate'+prodCatId+sectorList[j].sectorTO.sectorId+(i++) +'" name="proposedRatesTO.rate" size="11"	class="txtbox width120" onkeypress="return validateSlabRate(event,'+(i-1)+',\'\');" onblur="validateFormat(this);" /> <input type="hidden" id="rateProdId'+(i-1)+'"	name="proposedRatesTO.rateProdId" value="'+prodCatId+'" /> <input type="hidden" id="isROI'+prodCatId+sectorList[j].sectorTO.sectorId+(i-1) +'" name="proposedRatesTO.isROI" value="N" />',

						 ]);
				//}
			}
		}
	}


}



function disableProposedRatesGridFields(){
	for(var j=0;j<sectorList.length;j++){

		if(	sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&	sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId
				&& sectorList[j].sectorType == "D" ){
			for(var i=1; i<=5; i++){
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;	
			}
		}
	}	
}

function disableSpecialDestGridFields(){
	for(var j=1;j<rowCount;j++){
		var i = 1;
		getDomElementById("stateId"+prodCatId+j).disabled = true;
		getDomElementById("cityId"+prodCatId+j).disabled = true;	
		getDomElementById("specialDestRate"+prodCatId+(i++)+j).disabled = true;
		getDomElementById("specialDestRate"+prodCatId+(i++)+j).disabled = true;
		getDomElementById("specialDestRate"+prodCatId+(i++)+j).disabled = true;
		getDomElementById("specialDestRate"+prodCatId+(i++)+j).disabled = true;
		getDomElementById("specialDestRate"+prodCatId+(i++)+j).disabled = true;
	}
}

function disableVobAndWeights(){
	getDomElementById("rateVob"+prodCatId).disabled = true;
	if(!isNull(getDomElementById("rateMinChargWt"+prodCatId))){
		getDomElementById("rateMinChargWt"+prodCatId).disabled = true;
	}
	if(productCode != 'CO'){
		for(var j=1;j<5;j++){
			getDomElementById("rateQuotEndWeight"+prodCatId+j).disabled = true;
		}
		getDomElementById("rateQuotAddWeight"+prodCatId).disabled = true;
	}else{

		var cols = 5;
		if(configuredType == DOX){
			cols = 5;
		}else if(configuredType == PPX){
			cols = 7;
		}else if(configuredType == BOTH){
			cols = 9;
		} 
		var field = "";
		for(j = 1; j <= cols;j++){			
			field = "";
			if(configuredType == DOX && j<5){
				field = "rateQuotEndWeightDOX";			
			}else if(configuredType == DOX && j == 5){
				field = "rateQuotAddWeightDOX";			
			}else if(configuredType == PPX && j < 3){
				field = "rateQuotEndWeightPPXPA";			
			}else if(configuredType == PPX && j == 3){
				field = "rateQuotAddWeightPPXPD";			
			}else if(configuredType == PPX && j == 4){
				field = "rateQuotEndWeightPPXKU";			
			}else if(configuredType == PPX && j > 4 && j < 7){
				field = "rateQuotEndWeightPPXKT";			
			}else if(configuredType == PPX && j == 7){
				field = "rateQuotAddWeightPPXKA";			
			}else if(configuredType == BOTH && j < 5){
				field = "rateQuotEndWeightBOTH";			
			}else if(configuredType == BOTH && j == 5){
				field = "rateQuotAddWeightBOTH";			
			}else if(configuredType == BOTH && j == 6){
				field = "rateQuotEndWeightBOTHKU";
			}else if(configuredType == BOTH && j > 6 && j < 9){
				field = "rateQuotEndWeightBOTHKT";
			}else if(configuredType == BOTH && j == 9){
				field = "rateQuotAddWeightBOTHKA";
			}

			field = field+prodCatId+j;

			if(!isNull(getDomElementById(field))){
				getDomElementById(field).disabled = true;
			}
		}
	}

}

function diabledProposedRatesButtons(){
	buttonDisabled("btnProposedRatesSave"+prodCatId,"btnintform");
	jQuery("#" +"btnProposedRatesSave"+prodCatId).addClass("btnintformbigdis");

	buttonDisabled("btnProposedRatesClear"+prodCatId,"btnintform");
	jQuery("#" +"btnProposedRatesClear"+prodCatId).addClass("btnintformbigdis");

	buttonDisabled("btnAdd"+prodCatId,"btnintform");
	jQuery("#" +"btnAdd"+prodCatId).addClass("btnintformbigdis");
}

function enableProposedRatesButtons(){
	buttonEnabled("btnProposedRatesSave"+prodCatId,"btnintform");
	jQuery("#" +"btnProposedRatesSave"+prodCatId).removeClass("btnintformbigdis");

	buttonEnabled("btnProposedRatesClear"+prodCatId,"btnintform");
	jQuery("#" +"btnProposedRatesClear"+prodCatId).removeClass("btnintformbigdis");

	buttonEnabled("btnAdd"+prodCatId,"btnintform");
	jQuery("#" +"btnAdd"+prodCatId).removeClass("btnintformbigdis");
}


function clearRateFields(index){
	loadAllGridValues(prodCatId,productCode,gridId,index);
}

function checkPinCode(pincode,rowId){
	var field = "";
	for(var i=1;i<rowCount;i++){
		if(i != rowId){
			field = getDomElementById('pincode'+prodCatId + i);
			if(!isNull(field.value) && field.value == pincode){
				alert("Duplicate Pincode has been entered");
				return false;
			}
		}
	}
	return true;
}

function disableGridFields(noCols){
	for(var i=1;i<noCols;i++){
		if(isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i)))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
				}
			}
		}
	}

	if(isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		for(var j =0 ;j<secLen;j++){
			if(sectorList[j].sectorType == "D" 
				&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
				if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
					getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = true;
			}
		}
	}
}

function disableSplGridFields(noCols){
	for(var i=1;i<noCols;i++){
		if(isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			for(var j=1;j<rowCount;j++){
				if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+j)))
					getDomElementById("specialDestRate"+prodCatId+i+j).disabled = true;
			}	
		}
	}
	if(isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		for(var j=1;j<rowCount;j++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+"5"+j)))
				getDomElementById("specialDestRate"+prodCatId+"5"+j).disabled = true;
		}
	}
}

function enableGridFields(noCols){
	for(var i=1;i<noCols;i++){
		if(!isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i)))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = false;
				}
			}
		}
	}

	if(!isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		for(var j =0 ;j<secLen;j++){
			if(sectorList[j].sectorType == "D" 
				&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
				if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
					getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = false;
			}
		}
	}
}
function enableSplGridFields(noCols){
	for(var i=1;i<noCols;i++){
		if(!isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			for(var j=1;j<rowCount;j++){
				if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+j)))
					getDomElementById("specialDestRate"+prodCatId+i+j).disabled = false;
			}	
		}
	}
	if(!isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		for(var j=1;j<rowCount;j++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+"5"+j)))
				getDomElementById("specialDestRate"+prodCatId+"5"+j).disabled = false;
		}
	}
}

function loadStateDropDown(selectId, val, datalist){

	var obj = getDomElementById(selectId);
	clearDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].stateId;
			opt.text = datalist[i].stateName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function loadCityDropDown(selectId, val, datalist){
	var obj = getDomElementById(selectId);
	clearCityDropDownList(selectId);
	if (datalist.length > 0) {
		for ( var i = 0; i < datalist.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = datalist[i].cityId;
			opt.text = datalist[i].cityName;
			obj.options.add(opt);
		}
		obj.value = val;
	} 
}

function validateCities(prodId, rowId) {

	cityObj1 = getDomElementById("cityId" +prodId+ rowId);
	cityObjVal1 = getDomElementById("cityId" +prodId+ rowId).value;
	stateObj1 = getDomElementById("stateId" +prodId+ rowId);
	stateObjVal1 = getDomElementById("stateId" +prodId+ rowId).value;

	for ( var i = 1; i < rowCount; i++) {
		if (i != rowId) {
			cityObj2 = getDomElementById("cityId" +prodId+ i);
			stateObj2 = getDomElementById("stateId" +prodId + i);

			cityObjVal2 = cityObj2.value;
			stateObjVal2 = stateObj2.value;
			if (!isNull(stateObjVal1) && !isNull(stateObjVal2) && !isNull(cityObjVal1) && !isNull(cityObjVal2) ) {			
				if ((stateObjVal1 == stateObjVal2) && (cityObjVal1 == cityObjVal2)) {
					alert("State row number " + i + " and "	+ rowId
							+ " are same. Cities should not be same");
					cityObj1.value = "";
					setTimeout(function() {
						cityObj1.focus();
					}, 10);
					return false;
				}
			}
		}
	}


	return true;
}


function validateStates(prodId, rowId) {

	if(isNull(getDomElementById("stateId" +prodId+ rowId).value)){
		alert("Please select state");
		setTimeout(function() {
			getDomElementById("stateId" +prodId+ rowId).focus();
		}, 10);
		return false;
	}

	cityObj1 = getDomElementById("cityId" +prodId+ rowId);
	cityObjVal1 = getDomElementById("cityId" +prodId+ rowId).value;
	stateObj1 = getDomElementById("stateId" +prodId+ rowId);
	stateObjVal1 = getDomElementById("stateId" +prodId+ rowId).value;

	for ( var i = 1; i < rowCount; i++) {
		if (i != rowId) {
			cityObj2 = getDomElementById("cityId" +prodId+ i);
			stateObj2 = getDomElementById("stateId" +prodId + i);

			cityObjVal2 = cityObj2.value;
			stateObjVal2 = stateObj2.value;
			if (!isNull(stateObjVal1) && !isNull(stateObjVal2) && isNull(cityObjVal1) && isNull(cityObjVal2) ) {	
				if ((stateObjVal1 == stateObjVal2)) {
					alert("State row number " + i + " and "	+ rowId
							+ " should not be same");
					stateObj1.value = "";
					getCityByState(stateObj1.value,rowId);
					setTimeout(function() {
						stateObj1.focus();
					}, 10);
					return false;
				}
			}
		}
	}


	return true;
}

function assignConfiguredWeightSlabs(){
	var m = -1;
	var asnSlabs = true;
	var header = '<thead><tr>';
	var splheader = '<thead><tr>';

	splheader = splheader+'<th width = "3%" align="center">SrNo.</th>';
	splheader = splheader+'<th width = "9%" align="center">State</th>';
	splheader = splheader+'<th width = "9%" align="center">City</th>';



	var cType = getDomElementById("configuredType").value;


	var quotStatus = getDomElementById("quotationStatus").value;
	var page =  getDomElementById("page").value;
	var contractStatus="";
	var uType = "";
	if(!isNull(getDomElementById("contractStatus"))){
		contractStatus = getDomElementById("contractStatus").value;
		uType = getDomElementById("userType").value;
	}

	if(cType == BOTH){
		if(getValueByElementId("proposedRatesP") == 'Y' || getValueByElementId("proposedRatesD") == 'Y'){
			if(getValueByElementId("proposedRatesP") == 'Y' && getValueByElementId("proposedRatesD") == 'N'){
				if((quotStatus == "N") || (quotStatus == "S" && isNull(page)) || (!isNull(uType) && uType == "S") || (!isNull(contractStatus) && contractStatus == "C")){
					alert("Rates for Parcel type configured, so please configure rates for Documents");
				}
				asnSlabs = false;
			}else if(getValueByElementId("proposedRatesP") == 'N' && getValueByElementId("proposedRatesD") == 'Y'){
				if((quotStatus == "N") || (quotStatus == "S" && isNull(page)) || (!isNull(uType) && uType == "S") || (!isNull(contractStatus) && contractStatus == "C")){
					alert("Rates for Document type configured, so please configure rates for Parcel");
				}
				asnSlabs = false;
			}else{
				if((quotStatus == "N") || (quotStatus == "S" && isNull(page)) || (!isNull(uType) && uType == "S") || (!isNull(contractStatus) && contractStatus == "C")){
					alert("Rates for Document and Parcel configured, so could not configure for Both type");
				}
				asnSlabs = false;
			}
			/*if(!isNull(configuredType)){
				getDomElementById("configuredType").value = configuredType;
			}else{
				getDomElementById("configuredType").value = DOX;
			}*/

			//return;
		}
	}else if(cType == PPX || cType == DOX){
		if(getValueByElementId("proposedRatesB") == 'Y'){
			if(cType == PPX){
				if((quotStatus == "N") || (quotStatus == "S" && isNull(page)) || (!isNull(uType) && uType == "S") || (!isNull(contractStatus) && contractStatus == "C")){
					alert("Rates for Both type configured, so could not configure for Parcel type");
				}
				asnSlabs = false;
			}else if(cType == DOX){
				if((quotStatus == "N") || (quotStatus == "S" && isNull(page)) || (!isNull(uType) && uType == "S") || (!isNull(contractStatus) && contractStatus == "C")){
					alert("Rates for Both type configured, so could not configure for Documents type");
				}
				asnSlabs = false;
			}
			/*if(!isNull(configuredType)){
				getDomElementById("configuredType").value = configuredType;
			}else{
				getDomElementById("configuredType").value = DOX;
			}*/

			//return;
		}
	}


	configuredType = getDomElementById("configuredType").value;
	getDomElementById("rateConfiguredType").value = configuredType;	

	getDomElementById("rateVob"+prodCatId).options.length = 0;

	clearCOValues();

	if(configuredType == DOX){
		header = header+'<th align="center" width="21%">Sector / Weight Slab (Kg)</th>';



		var i=1;
		for(i=1; i<5; i++){
			header = header+'<th align="center"><span id="rateStartWeightSlabDOX'+prodCatId+i+'"></span>	-';
			header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeightDOX'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightDOX'+prodCatId+i+'\',\'rateStartWeightSlabDOX'+prodCatId+(i+1)+'\',\'rateQuotEndWeightDOX'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabDOX'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabDOX'+prodCatId+i+'\','+prodCatId+','+i+',\'DOX\')">';
			header = header+'<option value="">--select--</option></select>';
			header = header+'<input type="hidden" id="rateWtSlabIdDOX'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdDOX" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
			header = header+'<input type="hidden" id="ratehdnWtIdDOX'+prodCatId+i+'" name="ratehdnWtId'+prodCatId+i+'" /></th>';

			splheader = splheader+'<th align="center" width="15%"><span id="rateSpecialStartWeightSlabDOX'+prodCatId+i+'"></span> - <span id="rateSpecialEndWeightSlabDOX'+prodCatId+i+'"></span></th>';
		}

		header = header+'<th><select name="proposedRatesTO.rateQuotCOAddWeight" id="rateQuotAddWeightDOX'+prodCatId+'5" class="selectBox width90" onchange="assignCOAddWeightSlab(\'rateQuotAddWeightDOX'+prodCatId+'5\',\'rateSpecialAddWeightSlabDOX'+prodCatId+'5\',\'DOX\',5);">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateAddWtSlabDOX'+prodCatId+'5" name="proposedRatesTO.rateCOAddWtSlab" class="txtbox width130" />';
		header = header+'<input type="hidden" id="ratehdnAddWtSlabDOX'+prodCatId+'5" name="ratehdnAddWtSlabDOX'+prodCatId+'5"/></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlabDOX'+prodCatId+'5"> - </span></th>';

	}else if(configuredType == PPX){

		header = header+'<th align="center" width="15%">Sector / Weight Slab (Kg)</th>';
		var i=0;
		for(i=1; i<3; i++){
			header = header+'<th align="center"><span id="rateStartWeightSlabPPXPA'+prodCatId+i+'"></span>	-';
			header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeightPPXPA'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightPPXPA'+prodCatId+i+'\',\'rateStartWeightSlabPPXPA'+prodCatId+(i+1)+'\',\'rateQuotEndWeightPPXPA'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabPPXPA'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabPPXPA'+prodCatId+i+'\','+prodCatId+','+i+',\'PPXPA\')">';
			header = header+'<option value="">--select--</option></select>';
			header = header+'<input type="hidden" id="rateWtSlabIdPPXPA'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdPPXPA" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
			header = header+'<input type="hidden" id="ratehdnWtIdPPXPA'+prodCatId+i+'" name="ratehdnWtId'+prodCatId+i+'" /></th>';
			splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabPPXPA'+prodCatId+i+'"></span> - <span id="rateSpecialEndWeightSlabPPXPA'+prodCatId+i+'"></span></th>';
		}

		header = header+'<th  align="center">ADD1 <select name="proposedRatesTO.rateQuotCOAddWeight" id="rateQuotAddWeightPPXPD'+prodCatId+'3" class="selectBox width90" onchange="assignCOAddWeightSlab(\'rateQuotAddWeightPPXPD'+prodCatId+'3\',\'rateSpecialAddWeightSlabPPXPD'+prodCatId+'3\',\'PPXPD\',3);">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateAddWtSlabPPXPD'+prodCatId+'3" name="proposedRatesTO.rateCOAddWtSlab" class="txtbox width130" />';
		header = header+'<input type="hidden" id="ratehdnAddWtSlabPPXPD'+prodCatId+'3" name="ratehdnAddWtSlabPPXPD'+prodCatId+'3"/></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlabPPXPD'+prodCatId+'3"> - </span></th>';

		i = 4;

		header = header+'<th align="center">';
		header = header+'<select name="rateQuotEndWeightPPXKU'+prodCatId+i+'" id="rateQuotEndWeightPPXKU'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightPPXKU'+prodCatId+i+'\',\'rateStartWeightSlabPPXKU'+prodCatId+(i+1)+'\',\'rateQuotEndWeightPPXKT'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabPPXKU'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabPPXKU'+prodCatId+i+'\','+prodCatId+','+i+',\'PPXKU\')">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateWtSlabIdPPXKU'+prodCatId+i+'" name="rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdPPXKU" name="rateWtId" value="'+prodCatId+'"/>';
		header = header+'<input type="hidden" id="ratehdnWtIdPPXKU'+prodCatId+i+'" name="ratehdnWtIdPPXKU'+prodCatId+i+'" /></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialEndWeightSlabPPXKU'+prodCatId+i+'">-</span></th>';

		for(i=5; i<7; i++){
			if(i==5){
				header = header+'<th align="center"><span id="rateStartWeightSlabPPXKU'+prodCatId+i+'"></span>	-';
				splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabPPXKU'+prodCatId+i+'"></span> - ';
			}
			else{
				header = header+'<th align="center"><span id="rateStartWeightSlabPPXKT'+prodCatId+i+'"></span>	-';
				splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabPPXKT'+prodCatId+i+'"></span> - ';
			}

			header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeightPPXKT'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightPPXKT'+prodCatId+i+'\',\'rateStartWeightSlabPPXKT'+prodCatId+(i+1)+'\',\'rateQuotEndWeightPPXKT'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabPPXKT'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabPPXKT'+prodCatId+i+'\','+prodCatId+','+i+',\'PPXKT\')">';
			header = header+'<option value="">--select--</option></select>';
			header = header+'<input type="hidden" id="rateWtSlabIdPPXKT'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId"/><input type="hidden" id="rateWtIdPPXKT" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
			header = header+'<input type="hidden" id="ratehdnWtIdPPXKT'+prodCatId+i+'" name="ratehdnWtIdPPXKT'+prodCatId+i+'" /><input type="hidden" id="rateStartWtSlabIdPPXKU'+prodCatId+'" name="rateWtSlabId"/></th>';
			splheader = splheader+'<span id="rateSpecialEndWeightSlabPPXKT'+prodCatId+i+'"></span></th>';
		}	

		header = header+'<th  align="center">ADD2 <select name="proposedRatesTO.rateQuotCOAddWeight" id="rateQuotAddWeightPPXKA'+prodCatId+'7" class="selectBox width90" onchange="assignCOAddWeightSlab(\'rateQuotAddWeightPPXKA'+prodCatId+'7\',\'rateSpecialAddWeightSlabPPXKA'+prodCatId+'7\',\'PPXKA\',7);">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateAddWtSlabPPXKA'+prodCatId+'7" name="proposedRatesTO.rateCOAddWtSlab" />';
		header = header+'<input type="hidden" id="ratehdnAddWtSlabPPXKA'+prodCatId+'7" name="ratehdnAddWtSlabPPXKA'+prodCatId+'"/></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlabPPXKA'+prodCatId+'7"> - </span></th>';

	}else if(configuredType == BOTH){

		header = header+'<th align="center" width="15%">Sector / Weight Slab (Kg)</th>';
		var i=1;
		for(i=1; i<5; i++){
			header = header+'<th align="center"><span id="rateStartWeightSlabBOTH'+prodCatId+i+'"></span>	-';
			header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeightBOTH'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightBOTH'+prodCatId+i+'\',\'rateStartWeightSlabBOTH'+prodCatId+(i+1)+'\',\'rateQuotEndWeightBOTH'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabBOTH'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabBOTH'+prodCatId+i+'\','+prodCatId+','+i+',\'BOTH\')">';
			header = header+'<option value="">--select--</option></select>';
			header = header+'<input type="hidden" id="rateWtSlabIdBOTH'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdBOTH" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
			header = header+'<input type="hidden" id="ratehdnWtIdBOTH'+prodCatId+i+'" name="ratehdnWtIdBOTH'+prodCatId+i+'" /></th>';
			splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabBOTH'+prodCatId+i+'"></span> - <span id="rateSpecialEndWeightSlabBOTH'+prodCatId+i+'"></span></th>';
		}

		header = header+'<th  align="center">ADD1 <select name="proposedRatesTO.rateQuotCOAddWeight" id="rateQuotAddWeightBOTH'+prodCatId+'5" class="selectBox width90" onchange="assignCOAddWeightSlab(\'rateQuotAddWeightBOTH'+prodCatId+'5\',\'rateSpecialAddWeightSlabBOTH'+prodCatId+'5\',\'BOTH\',5);">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateAddWtSlabBOTH'+prodCatId+'5" name="proposedRatesTO.rateCOAddWtSlab" class="txtbox width130" />';
		header = header+'<input type="hidden" id="ratehdnAddWtSlabBOTH'+prodCatId+'5" name="ratehdnAddWtSlabBOTH'+prodCatId+'5"/></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlabBOTH'+prodCatId+'5"> - </span></th>';

		i = 6;

		header = header+'<th align="center">';
		header = header+'<select name="rateQuotEndWeightBOTHKU'+prodCatId+i+'" id="rateQuotEndWeightBOTHKU'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightBOTHKU'+prodCatId+i+'\',\'rateStartWeightSlabBOTHKU'+prodCatId+(i+1)+'\',\'rateQuotEndWeightBOTHKT'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabBOTHKU'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabBOTHKU'+prodCatId+i+'\','+prodCatId+','+i+',\'BOTHKU\')">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateWtSlabIdBOTHKU'+prodCatId+i+'" name="rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdBOTHKU" name="rateWtId" value="'+prodCatId+'"/>';
		header = header+'<input type="hidden" id="ratehdnWtIdBOTHKU'+prodCatId+i+'" name="ratehdnWtIdBOTHKU'+prodCatId+i+'" /></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialEndWeightSlabBOTHKU'+prodCatId+i+'"> - </span></th>';

		for(i=7; i<9; i++){
			if(i==7){
				header = header+'<th align="center"><span id="rateStartWeightSlabBOTHKU'+prodCatId+i+'"></span>	-';
				splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabBOTHKU'+prodCatId+i+'"></span> - ';
			}
			else{
				header = header+'<th align="center"><span id="rateStartWeightSlabBOTHKT'+prodCatId+i+'"></span> -';
				splheader = splheader+'<th align="center"><span id="rateSpecialStartWeightSlabBOTHKT'+prodCatId+i+'"></span> - ';
			}

			header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeightBOTHKT'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlabCO(\'rateQuotEndWeightBOTHKT'+prodCatId+i+'\',\'rateStartWeightSlabBOTHKT'+prodCatId+(i+1)+'\',\'rateQuotEndWeightBOTHKT'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlabBOTHKT'+prodCatId+(i+1)+'\',\'rateSpecialEndWeightSlabBOTHKT'+prodCatId+i+'\','+prodCatId+','+i+',\'BOTHKT\')">';
			header = header+'<option value="">--select--</option></select>';
			header = header+'<input type="hidden" id="rateWtSlabIdBOTHKT'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtIdBOTHKT" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
			header = header+'<input type="hidden" id="ratehdnWtIdBOTHKT'+prodCatId+i+'" name="ratehdnWtIdBOTHKT'+prodCatId+i+'" /><input type="hidden" id="rateStartWtSlabIdBOTHKU'+prodCatId+'" name="rateWtSlabId"/></th>';
			splheader = splheader+'<span id="rateSpecialEndWeightSlabBOTHKT'+prodCatId+i+'"></span></th>';
		}	

		header = header+'<th  align="center">ADD2 <select name="proposedRatesTO.rateQuotCOAddWeight" id="rateQuotAddWeightBOTHKA'+prodCatId+'9" class="selectBox width90" onchange="assignCOAddWeightSlab(\'rateQuotAddWeightBOTHKA'+prodCatId+'9\',\'rateSpecialAddWeightSlabBOTHKA'+prodCatId+'9\',\'BOTHKA\',9);">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateAddWtSlabBOTHKA'+prodCatId+'9" name="proposedRatesTO.rateCOAddWtSlab" class="txtbox width130" />';
		header = header+'<input type="hidden" id="ratehdnAddWtSlabBOTHKA'+prodCatId+'9" name="ratehdnAddWtSlabBOTHKA'+prodCatId+'9"/></th>';
		splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlabBOTHKA'+prodCatId+'9"> - </span></th>';
	}


	header = header+'</tr></thead>';
	splheader = splheader+'</tr></thead>';

	deleteTableRow("example1");
	$('#example1').dataTable().fnDestroy(true);
	$('#example1').children('thead').remove();
	$('#example1').append(header);
	$('#example1').dataTable( {
		"sScrollY": "100",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,					
		"sPaginationType": "full_numbers"

	} );
	document.getElementById("example1").style.width = '98%';

	deleteTableRow("specialGrid1");
	$('#specialGrid1').dataTable().fnDestroy(true);
	$('#specialGrid1').children('thead').remove();
	$('#specialGrid1').append(splheader);
	$('#specialGrid1').dataTable( {
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"		
	} );

	document.getElementById("specialGrid1").style.width = '98%';



	addRateRows();
	if(asnSlabs){ 
		for(var i =0 ;i<wtLen;i++){

			if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){


				if(configuredType == DOX){
					if(m==-1)
						m = i;
					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(wtList[m].weightSlabTO.weightSlabCategory == DA){
							getDomElementById("rateStartWeightSlabDOX"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateSpecialStartWeightSlabDOX"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateQuotCOStartWeight1").value = wtList[m].weightSlabTO.weightSlabId;
						}else{
							m = -1;
						}
						if(wtList[i].weightSlabTO.weightSlabCategory == DA){
							addOptionTODropDown("rateQuotEndWeightDOX"+prodCatId+"1",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}
					}else if(wtList[i].weightSlabTO.weightSlabCategory == DOXDD){
						addOptionTODropDown("rateQuotAddWeightDOX"+prodCatId+"5",wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}
				}else if(configuredType == PPX){
					if(m==-1)
						m = i;

					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(wtList[m].weightSlabTO.weightSlabCategory == PA){
							getDomElementById("rateStartWeightSlabPPXPA"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateSpecialStartWeightSlabPPXPA"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateQuotCOStartWeight1").value = wtList[m].weightSlabTO.weightSlabId;				 		
						}else{
							m = -1;
						}
						if(wtList[i].weightSlabTO.weightSlabCategory == PA){
							addOptionTODropDown("rateQuotEndWeightPPXPA"+prodCatId+"1",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else if(wtList[i].weightSlabTO.weightSlabCategory == KU){
							if(!isNull(wtList[i].weightSlabTO.endWeight)){
								addOptionTODropDown("rateQuotEndWeightPPXKU"+prodCatId+"4",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}else if(wtList[i].weightSlabTO.weightSlabCategory == PD){
						addOptionTODropDown("rateQuotAddWeightPPXPD"+prodCatId+"3",wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else if(wtList[i].weightSlabTO.weightSlabCategory == KA){
						addOptionTODropDown("rateQuotAddWeightPPXKA"+prodCatId+"7",wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}
				}else if(configuredType == BOTH){
					if(m==-1)
						m = i;

					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(wtList[m].weightSlabTO.weightSlabCategory == DA){
							getDomElementById("rateStartWeightSlabBOTH"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateSpecialStartWeightSlabBOTH"+prodCatId+"1").innerHTML = wtList[m].weightSlabTO.startWeightLabel;
							getDomElementById("rateQuotCOStartWeight1").value = wtList[m].weightSlabTO.weightSlabId;				 		
						}else{
							m = -1;
						}
						if(wtList[i].weightSlabTO.weightSlabCategory == DA){
							addOptionTODropDown("rateQuotEndWeightBOTH"+prodCatId+"1",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else if(wtList[i].weightSlabTO.weightSlabCategory == KU){
							if(!isNull(wtList[i].weightSlabTO.endWeight)){
								addOptionTODropDown("rateQuotEndWeightBOTHKU"+prodCatId+"6",wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}else if(wtList[i].weightSlabTO.weightSlabCategory == DOXDD){					
						addOptionTODropDown("rateQuotAddWeightBOTH"+prodCatId+"5",wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else if(wtList[i].weightSlabTO.weightSlabCategory == KA){
						addOptionTODropDown("rateQuotAddWeightBOTHKA"+prodCatId+"9",wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}
				}

			}
		}

		if(!isNull(rbmhTO.productCatHeaderTO)){

			prodCatTO = rbmhTO.productCatHeaderTO;
			assignRateValues();
			enableTabs(2);
		}else{
			prodCatTO = null;
		}
		//disableOrEnableCourierGridFields();
	}
	disableFieldsBasedonStatus();

	/* The below method is called to restrict user from modifying proposed rates tab using super-edit functionality */
	restrictRateModificationViaSuperEdit("assignConfiguredWeightSlabs");
}


function assignWeightSlabCO(selSlab,startWt,endWt, specialStartWt, specialEndWt, prodCatVal, curPos, type){

	var prevVal = getDomElementById("ratehdnWtId"+type+prodCatVal+curPos).value;
	var lst = getDomElementById(selSlab);
	addSlabFlag == false;
	flagAddSlab = false;
	slabValKU = "";
	slabValK = "";
	if(configuredType == PPX || configuredType == BOTH){
		if(!isNull(lst.value) && !checkWeightSlabValues()){
			if(addSlabFlag == false && flagAddSlab == false){		
				if((configuredType == PPX && curPos == 4) || (configuredType == BOTH && curPos == 6)){
					alert("Weight slab value should be greater than or equal to "+slabValKU);
				}else{
					alert("Weight slab value should be less than or equal to "+slabValK);
				}
				getDomElementById(selSlab).value = prevVal;
				getDomElementById(selSlab).focus();
				return;
			}
		}
	}

	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value) && !isNull(getValueByElementId(selSlab) && !isNull(prevVal))){
		flag = confirm("Do you want to change weight slab");

		if (!flag) {
			getDomElementById(selSlab).value = prevVal;
			return;
		}
	}

	var selElement = true;
	var wtLen = wtList.length;

	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)){
		clearCOWtSlabs();
	} 

	if(isNull(lst.value)){
		getDomElementById(specialEndWt).innerHTML = "";

		if(type == "PPXKU"){
			getDomElementById(specialEndWt).innerHTML = " - ";
			clearCourierGridFieldsData(curPos, curPos,prodCatVal,"PPXKU");
		}else if(type == "BOTHKU"){
			getDomElementById(specialEndWt).innerHTML = " - ";
			clearCourierGridFieldsData(curPos, curPos,prodCatVal,"BOTHKU");					
		}else{
			clearCourierGridFieldsData(curPos, curPos,prodCatVal,type);
		}

		if(configuredType == DOX){
			for(var i =0 ;i<wtLen;i++){

				if((wtList[i].weightSlabTO.weightSlabCategory == DA) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
						&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
							selElement = false;
							for(var j=i+1;j<wtLen;j++){
								if((wtList[j].weightSlabTO.weightSlabCategory == DA) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
										&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
									if(wtList[j].weightSlabTO.additional != 'Y'){
										addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
									}

								}
							}
							break;
						}	 
					}
				}
			}
			if(selElement){
				for(var i =0 ;i<wtLen;i++){

					if((wtList[i].weightSlabTO.weightSlabCategory == DA) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}

					}
				}
			}

		}else if(configuredType == PPX){
			if(type == "PPXPA"){
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == PA) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								for(var j=i+1;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == PA) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == PA) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y'){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			}else if(type == "PPXKU"){
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == KU) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								for(var j=i+1;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == KU) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == KU) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y'){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			}else if(type == "PPXKT"){
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == KT) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								var j = 0;

								if(curPos != 5){
									j = i+1;
								}
								for(j;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == KT) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y'  &&  (curPos != 5 || (curPos == 5 && wtList[i].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value)))){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}

				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == KT) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y' && (curPos != 5 || (curPos == 5 && wtList[i].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value)))){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			} 

		}else if(configuredType == BOTH){
			if(type == "BOTHKU"){
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == KU) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								for(var j=i+1;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == KU) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == KU) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y'){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			}else if(type == "BOTHKT"){
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == KT) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								var j = 0;
								if(curPos != 7){
									j = i+1;
								}
								for(j;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == KT) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y' && (curPos != 7 || (curPos == 7 && wtList[j].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value)))){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == KT) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y' && (curPos != 7 || (curPos == 7 && wtList[i].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value)))){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			}else{
				for(var i =0 ;i<wtLen;i++){
					if((wtList[i].weightSlabTO.weightSlabCategory == DA) && (wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
							&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(!isNull(getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1))) && (getDomElementById("rateQuotEndWeight"+type+prodCatId+(curPos-1)).value == wtList[i].weightSlabTO.weightSlabId)){
								selElement = false;
								for(var j=i+1;j<wtLen;j++){
									if((wtList[j].weightSlabTO.weightSlabCategory == DA) && (wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
											&&  (wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId)){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(selSlab,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
				if(selElement){
					for(var i =0 ;i<wtLen;i++){

						if((wtList[i].weightSlabTO.weightSlabCategory == DA) && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
								&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(wtList[i].weightSlabTO.additional != 'Y'){
								addOptionTODropDown(selSlab,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}
					}
				}
			}

		}

	}else{		
		getDomElementById(specialEndWt).innerHTML = lst.options[lst.selectedIndex].text;
	}

	if(!isNull(getDomElementById(startWt))){

		if(type == "PPXKU"){
			getDomElementById("rateStartWeightSlab"+type+prodCatVal+(curPos+1)).innerHTML = "";
			clearCourierGridFieldsData(curPos, (curPos+1),prodCatVal,"PPXKU");			
		}else if(type == "BOTHKU"){
			getDomElementById("rateStartWeightSlab"+type+prodCatVal+(curPos+1)).innerHTML = "";
			clearCourierGridFieldsData(curPos, (curPos+1),prodCatVal,"BOTHKU");
		}else{
			clearCourierGridFieldsData(curPos, (curPos+1),prodCatVal,type);
		}



		for(var i =0 ;i<wtLen;i++){

			if(configuredType == DOX){
				if(wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
							for(var j=(i+1);j<wtLen;j++){
								if(wtList[j].weightSlabTO.weightSlabCategory == DA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[j].weightSlabTO.additional != 'Y'){
										getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

										getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

										getDomElementById("rateQuotCOStartWeight"+(curPos+1)).value = wtList[j].weightSlabTO.weightSlabId;
										break;
									}

								}
							}
							break;
						}
					}
				}    
			}else if(configuredType == PPX){
				if(type == "PPXPA"){
					if(wtList[i].weightSlabTO.weightSlabCategory == PA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == PA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById("rateQuotCOStartWeight"+(curPos+1)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					}
				}else if(type == "PPXKU"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KU && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											ppxKUVal = wtList[j].weightSlabTO.startWeight;
											getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value = wtList[j].weightSlabTO.startWeight;
											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById("rateQuotCOStartWeight"+(curPos)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					} 
				}else if(type == "PPXKT"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KT && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											getDomElementById("rateQuotCOStartWeight"+(curPos)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					} 
				}
			}else if(configuredType == BOTH){

				if(type == "BOTHKU"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KU && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											ppxKUVal = wtList[j].weightSlabTO.startWeight;
											getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value = wtList[j].weightSlabTO.startWeight;  
											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById("rateQuotCOStartWeight"+(curPos)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					} 
				}else if(type == "BOTHKT"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KT && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;
											getDomElementById("rateQuotCOStartWeight"+(curPos)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					} 
				}else{
					if(wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){
								for(var j=(i+1);j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == DA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											getDomElementById(startWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById(specialStartWt).innerHTML = wtList[j].weightSlabTO.startWeightLabel;

											getDomElementById("rateQuotCOStartWeight"+(curPos+1)).value = wtList[j].weightSlabTO.weightSlabId;
											break;
										}

									}
								}
								break;
							}
						}
					}
				} 
			}
		}


		for(var i =0 ;i<wtLen;i++){
			if(configuredType == DOX){
				if(wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(wtList[i].weightSlabTO.additional != 'Y'){
						if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){

							for(var j=i+1;j<wtLen;j++){
								if(wtList[j].weightSlabTO.weightSlabCategory == DA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[j].weightSlabTO.additional != 'Y'){
										addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
									}

								}
							}
							break;
						}	 
					}
				}
			}else if(configuredType == PPX){
				if(type == "PPXPA"){
					if(wtList[i].weightSlabTO.weightSlabCategory == PA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){

								for(var j=i+1;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == PA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}else if(type == "PPXKU"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){


								for(var j=0;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y' && wtList[j].weightSlabTO.endWeight >= parseFloat(ppxKUVal)){			 						
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}else if(type == "PPXKT"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KT && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){

								for(var j=i+1;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
			}else if(configuredType == BOTH){
				if(type == "BOTHKU"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){


								for(var j=0;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y' && wtList[j].weightSlabTO.endWeight >= parseFloat(ppxKUVal)){			 						
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}else if(type == "BOTHKT"){
					if(wtList[i].weightSlabTO.weightSlabCategory == KT && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){

								for(var j=i+1;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == KT && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}else{
					if(wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(getDomElementById(selSlab).value == wtList[i].weightSlabTO.weightSlabId){

								for(var j=i+1;j<wtLen;j++){
									if(wtList[j].weightSlabTO.weightSlabCategory == DA && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'){
											addOptionTODropDown(endWt,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}

									}
								}
								break;
							}	 
						}
					}
				}
			}
		}


	}


	if(!isNull(getValueByElementId(selSlab))){
		enableOrDisableRateFields(selSlab, prodCatVal, curPos, false);
	}else{
		enableOrDisableRateFields(selSlab, prodCatVal, curPos, true);
	}

	var col = 0;
	if(type == "DOX"){
		col = 5;
	}else if(type == "PPXPA"){
		col = 3;
	}else if(type == "PPXKU"){
		col = 7;
	}else if(type == "PPXKT"){
		col = 7;
	}else if(type == "BOTH"){
		col = 5;
	}else if(type == "BOTHKU"){
		col = 9;
	}else if(type == "BOTHKT"){
		col = 9;
	}
	for(var i= curPos ;i<col;i++){
		if(!isNull(getDomElementById("ratehdnWtId"+type+prodCatVal+i))){
			getDomElementById("ratehdnWtId"+type+prodCatVal+i).value = "";
			if(type == 'PPXKU'){
				type = 'PPXKT';
			}else if(type == 'BOTHKU'){
				type = 'BOTHKT';
			}
		}
	}

	if(!isNull(lst.value)){
		if(addSlabFlag){
			alert("please select additional weight slab");
			return;
		}else if(flagAddSlab){
			alert("Please unselect the additional weight");
			if(configuredType == PPX){
				getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3").focus();
				return;
			}else if(configuredType == BOTH){
				getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5").focus();
				return;
			}

		}
	}
}


function clearCourierGridFieldsData(curPos, nextPos,prodCatVal,type){

	var pos;

	if(curPos == nextPos)
		pos = curPos;
	else
		pos = nextPos;	

	var col = 0;
	if(type == "DOX"){
		col = 5;
	}else if(type == "PPXPA"){
		col = 3;
	}else if(type == "PPXKU"){
		col = 7;
	}else if(type == "PPXKT"){
		col = 7;
	}else if(type == "BOTH"){
		col = 5;
	}else if(type == "BOTHKU"){
		col = 9;
	}else if(type == "BOTHKT"){
		col = 9;
	}



	for(var i= pos ;i<col;i++){

		if(type == "BOTHKU" && i>6){
			type = "BOTHKT";
		}else if(type == "PPXKU" && i>4){
			type = "PPXKT"; 
		}
		if(!isNull(getDomElementById("rateQuotCOStartWeight"+i))){
			getDomElementById("rateQuotCOStartWeight"+i).value = "";
		}


		if((pos == curPos && i != curPos) || (curPos != nextPos)){
			if(!isNull(getDomElementById("rateStartWeightSlab"+type+prodCatVal+i))){
				getDomElementById("rateStartWeightSlab"+type+prodCatVal+i).innerHTML = "";				
			}			
		}

		clearDropDownList("rateQuotEndWeight"+type+prodCatVal+i);

		if((pos == curPos && i != curPos) || (curPos != nextPos)){

			if(type == "PPXKT" && i == 5){
				getDomElementById("rateSpecialStartWeightSlabPPXKU"+prodCatId+i).innerHTML = "";
			}else if(type == "BOTHKT" && i == 7){
				getDomElementById("rateSpecialStartWeightSlabBOTHKU"+prodCatId+i).innerHTML = "";
			}else{
				getDomElementById("rateSpecialStartWeightSlab"+type+prodCatId+i).innerHTML = "";	
			}

			getDomElementById("rateSpecialEndWeightSlab"+type+prodCatId+i).innerHTML = "";
		}


		for(var j = 0; j< secLen ; j++){
			if(sectorList[j].sectorType == "D" &&  sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){				
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).value = "";
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
			}
		}


		for(var k = 1; k<rowCount; k++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+k))){
				getDomElementById("specialDestRate"+prodCatId+i+k).value = "";
				getDomElementById("specialDestRate"+prodCatId+i+k).disabled = true;
			}
		}

	}


}


function disableOrEnableCourierGridFields(){
	var field="";
	var noCols = 0;

	if(configuredType == DOX){
		noCols = 5;
	}else if(configuredType == PPX){
		noCols = 7;
	}else if(configuredType == BOTH){
		noCols = 9;
	}

	for(var i=1;i<noCols;i++){
		if(configuredType == DOX){
			field = "rateQuotEndWeightDOX";
		}else if(configuredType == PPX && i == 4){
			field = "rateQuotEndWeightPPXKU";
		}else if(configuredType == PPX && i > 4){
			field = "rateQuotEndWeightPPXKT";
		}else if(configuredType == PPX){
			field = "rateQuotEndWeightPPXPA";
		}else if(configuredType == BOTH && i == 6){
			field = "rateQuotEndWeightBOTHKU";
		}else if(configuredType == BOTH && i > 6){
			field = "rateQuotEndWeightBOTHKT";
		}else if(configuredType == BOTH){
			field = "rateQuotEndWeightBOTH";
		}


		if(!isNull(getDomElementById(field+prodCatId+i)) && !isNull(getValueByElementId(field+prodCatId+i))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i))){
						if(field == "rateQuotEndWeightPPXKU" || field == "rateQuotEndWeightBOTHKU"){
							getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
						}else{
							getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = false;
						}
					}
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i))){
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
					}
				}
			}
		}
	}

	if(configuredType == DOX){
		if(!isNull(getDomElementById("rateQuotAddWeightDOX"+prodCatId+"5")) &&  !isNull(getValueByElementId("rateQuotAddWeightDOX"+prodCatId+"5"))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = false;
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = true;
				}
			}
		}
	}else if(configuredType == PPX){
		if(!isNull(getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3")) && !isNull(getValueByElementId("rateQuotAddWeightPPXPD"+prodCatId+"3"))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"3")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"3").disabled = false;
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"3")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"3").disabled = true;
				}
			}
		}

		if(!isNull(getDomElementById("rateQuotAddWeightPPXKA"+prodCatId+"7")) && !isNull(getValueByElementId("rateQuotAddWeightPPXKA"+prodCatId+"7"))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"7")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"7").disabled = false;
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"7")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"7").disabled = true;
				}
			}
		}
	}else if(configuredType == BOTH){
		if(!isNull(getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5")) && !isNull(getValueByElementId("rateQuotAddWeightBOTH"+prodCatId+"5"))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = false;
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = true;
				}
			}
		}

		if(!isNull(getDomElementById("rateQuotAddWeightBOTHKA"+prodCatId+"9")) && !isNull(getValueByElementId("rateQuotAddWeightBOTHKA"+prodCatId+"9"))){
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"9")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"9").disabled = false;
				}
			}
		}else{
			for(var j =0 ;j<secLen;j++){
				if(sectorList[j].sectorType == "D" 
					&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
					if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"9")))
						getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"9").disabled = true;
				}
			}
		}
	}


	for(var k=1;k<rowCount;k++){
		disableCOSplDestRateFields(k);
	}
}


function disableCourierGridFields(){

	var noCols = 0;

	if(configuredType == DOX){
		noCols = 5;
	}else if(configuredType == PPX){
		noCols = 7;
	}else if(configuredType == BOTH){
		noCols = 9;
	}

	for(var i=1;i<=noCols;i++){

		for(var j =0 ;j<secLen;j++){
			if(sectorList[j].sectorType == "D" 
				&& sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId ){
				if(!isNull(getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i))){
					getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
				}
			}
		}
	}



	for(var k=1;k<rowCount;k++){
		getDomElementById("stateId"+prodCatId+k).disabled = true;	
		getDomElementById("cityId"+prodCatId+k).disabled = true;
		for(var i=1;i<=noCols;i++){
			getDomElementById("specialDestRate"+prodCatId+i+k).disabled = true;			
		}
	}
}



function disableSplGridFields(noCols){
	for(var i=1;i<noCols;i++){
		if(isNull(getValueByElementId("rateQuotEndWeight"+prodCatId+i))){
			for(var j=1;j<rowCount;j++){
				if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+j)))
					getDomElementById("specialDestRate"+prodCatId+i+j).disabled = true;
			}	
		}
	}
	if(isNull(getValueByElementId("rateQuotAddWeight"+prodCatId))){
		for(var j=1;j<rowCount;j++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+"5"+j)))
				getDomElementById("specialDestRate"+prodCatId+"5"+j).disabled = true;
		}
	}
}


function assignCOAddWeightSlab(selSlab,splSlab,type, curPos){
	var prevVal = getDomElementById("ratehdnAddWtSlab"+type+prodCatId+curPos).value;
	if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)  && !isNull(getValueByElementId(selSlab)) && !isNull(prevVal)){
		flag = confirm("Do you want to change weight slab");
		if (!flag) {
			getDomElementById(selSlab).value = prevVal;
			return;
		}else{
			clearCOWtSlabs();
		}
	}

	var i = 0;
	if(type == "DOX"){
		i = 5;
	}else if(type == "PPXPD"){
		i = 3;
	}else if(type == "PPXKA"){
		i = 7;
	}else if(type == "BOTH"){
		i = 5;
	}else if(type == "BOTHKA"){
		i = 9;
	}


	var lst = getDomElementById(selSlab);
	if(isNull(lst.value)){
		if(!isNull(getDomElementById("rateQuotationProdCatHeaderId").value)){
			clearCOWtSlabs();
		} 
		getDomElementById(splSlab).innerHTML = " - ";
		//getDomElementById("rateAddWtSlab"+prodCatId).value = "";
		for(var j = 0; j< secLen ; j++){
			if(sectorList[j].sectorType == "D" &&  sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).value = "";
				getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+i).disabled = true;
			}
		}
		for(var k = 1; k<rowCount; k++){
			if(!isNull(getDomElementById("specialDestRate"+prodCatId+i+k))){
				getDomElementById("specialDestRate"+prodCatId+i+k).value = "";
				getDomElementById("specialDestRate"+prodCatId+i+k).disabled = true;
			}
		}
	}
	else{
		getDomElementById(splSlab).innerHTML = lst.options[lst.selectedIndex].text;
	}

	if(!isNull(getValueByElementId(selSlab))){
		enableOrDisableRateFields(selSlab,prodCatId,i,false);
	}
}


function assignCOGridWeightSlabValues(k,slabWtLen){
	k = 1;
	var l = 0;
	var m = 0;
	var n = 0;
	var p = 0;
	if(configuredType == PPX){
		l = 5;
		m = 6;
		n = 3;
		p = 7;
	}else if(configuredType == BOTH){
		l = 7;
		m = 8;
		n = 5;
		p = 9;
	}

	for(var j = 0; j< slabWtLen ; j++){
		if(prodCatTO.weightSlabTO[j].rateConfiguredType == configuredType){
			if((!isNull(prodCatTO.weightSlabTO[j].endWeightTO) || (isNull(prodCatTO.weightSlabTO[j].endWeightTO) && (prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'))) 
					&& !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){

				if(configuredType == DOX){
					if(k<5 && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'DA'){					
						getDomElementById("rateStartWeightSlabDOX"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabDOX"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateQuotCOStartWeight"+k).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightDOX"+prodCatId+k, k,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"DOX");
						getDomElementById("rateWtSlabIdDOX"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnWtIdDOX"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						k++;
						assignCOGridEndWeightSlabValues(k,k,wtLen,'DOX','DA');
					}
				}else if(configuredType == PPX){
					if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'PA'){
						getDomElementById("rateStartWeightSlabPPXPA"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabPPXPA"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateQuotCOStartWeight"+k).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightPPXPA"+prodCatId+k, k,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"PPXPA");
						getDomElementById("rateWtSlabIdPPXPA"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnWtIdPPXPA"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						k++;
						assignCOGridEndWeightSlabValues(k,k,wtLen,'PPXPA','PA');
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'){
						getDomElementById("rateStartWeightSlabPPXKU"+prodCatId+l).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabPPXKU"+prodCatId+l).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value = prodCatTO.weightSlabTO[j].startWeightTO.startWeight;

						getDomElementById("rateQuotCOStartWeight"+(l-1)).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightPPXKU"+prodCatId+(l-1), (l-1), prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId, null,"PPXKU");
						if(!isNull(prodCatTO.weightSlabTO[j].endWeightTO)){
							getCODropdown("rateQuotEndWeightPPXKT"+prodCatId+l, l,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"PPXKT");
							getDomElementById("ratehdnWtIdPPXKT"+prodCatId+l).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						}else{
							getCODropdown("rateQuotEndWeightPPXKT"+prodCatId+l, l,  "", "","PPXKT");
							getDomElementById("ratehdnWtIdPPXKT"+prodCatId+l).value =  "";
						}
						getDomElementById("rateWtSlabIdPPXKT"+prodCatId+l).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						assignCOGridEndWeightSlabValues((l+1),l,wtLen,'PPXKT','KT');

					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KT'){
						getDomElementById("rateStartWeightSlabPPXKT"+prodCatId+m).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabPPXKT"+prodCatId+m).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateQuotCOStartWeight"+(m-1)).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightPPXKT"+prodCatId+m, m,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"PPXKT");
						getDomElementById("rateWtSlabIdPPXKT"+prodCatId+m).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnWtIdPPXKT"+prodCatId+m).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						assignCOGridEndWeightSlabValues((m+1),m,wtLen,'PPXKT','KT');				
					}/*else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'PD' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
							getCOAddDropdown("rateQuotAddWeightPPXPD"+prodCatId+n, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,n,"PPXPD");
							getDomElementById("rateAddWtSlabPPXPD"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
							getDomElementById("ratehdnAddWtSlabPPXPD"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KA' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightPPXKA"+prodCatId+p, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,p,"PPXKA");
						getDomElementById("rateAddWtSlabPPXKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabPPXKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
					}*/

				}else if(configuredType == BOTH){
					if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'DA'){
						getDomElementById("rateStartWeightSlabBOTH"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabBOTH"+prodCatId+k).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateQuotCOStartWeight"+k).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightBOTH"+prodCatId+k, k,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"BOTH");
						getDomElementById("rateWtSlabIdBOTH"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnWtIdBOTH"+prodCatId+k).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						k++;
						assignCOGridEndWeightSlabValues(k,k,wtLen,'BOTH','DA');	
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'){
						getDomElementById("rateStartWeightSlabBOTHKU"+prodCatId+l).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabBOTHKU"+prodCatId+l).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value = prodCatTO.weightSlabTO[j].startWeightTO.startWeight;
						getDomElementById("rateQuotCOStartWeight"+(l-1)).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightBOTHKU"+prodCatId+(l-1), (l-1),  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId, null,"BOTHKU");
						if(!isNull(prodCatTO.weightSlabTO[j].endWeightTO)){
							getCODropdown("rateQuotEndWeightBOTHKT"+prodCatId+l, l,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"BOTHKT");
							getDomElementById("ratehdnWtIdBOTHKT"+prodCatId+l).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						}else{
							getCODropdown("rateQuotEndWeightBOTHKT"+prodCatId+l, l,  "", "","BOTHKT");
							getDomElementById("ratehdnWtIdBOTHKT"+prodCatId+l).value =  "";
						}
						getDomElementById("rateWtSlabIdBOTHKT"+prodCatId+l).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						assignCOGridEndWeightSlabValues((l+1),l,wtLen,'BOTHKT','KT');	

					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KT'){
						getDomElementById("rateStartWeightSlabBOTHKT"+prodCatId+m).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateSpecialStartWeightSlabBOTHKT"+prodCatId+m).innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
						getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value = prodCatTO.weightSlabTO[j].startWeightTO.startWeight;
						getDomElementById("rateQuotCOStartWeight"+(m-1)).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getCODropdown("rateQuotEndWeightBOTHKT"+prodCatId+m, m,  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId, prodCatTO.weightSlabTO[j].endWeightTO.endWeightLabel,"BOTHKT");
						getDomElementById("rateWtSlabIdBOTHKT"+prodCatId+m).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnWtIdBOTHKT"+prodCatId+m).value =  prodCatTO.weightSlabTO[j].endWeightTO.weightSlabId;
						assignCOGridEndWeightSlabValues((m+1),m,wtLen,'BOTHKT','KT');					
					}/*else if(!isNull(prodCatTO.weightSlabTO[j].startWeightTO) && (prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'DOXDD') && isNull(prodCatTO.weightSlabTO[j].endWeightTO)){
							getCOAddDropdown("rateQuotAddWeightBOTH"+prodCatId+n, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,n,"BOTH");
							getDomElementById("rateAddWtSlabBOTH"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
							getDomElementById("ratehdnAddWtSlabBOTH"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KA' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightBOTHKA"+prodCatId+p, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,p,"BOTHKA");
						getDomElementById("rateAddWtSlabBOTHKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabBOTHKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
					}*/

				}
			}else if(isNull(prodCatTO.weightSlabTO[j].endWeightTO)	&& !isNull(prodCatTO.weightSlabTO[j].startWeightTO)){
				if(configuredType == DOX){

					if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightDOX"+prodCatId+"5", prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,5,"DOX");
						getDomElementById("rateAddWtSlabDOX"+prodCatId+"5").value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabDOX"+prodCatId+"5").value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getDomElementById("rateSpecialAddWeightSlabDOX"+prodCatId+"5").innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;

					}

				}else if(configuredType == PPX){
					if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'PD' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightPPXPD"+prodCatId+n, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,n,"PPXPD");
						getDomElementById("rateAddWtSlabPPXPD"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabPPXPD"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getDomElementById("rateSpecialAddWeightSlabPPXPD"+prodCatId+"3").innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KA' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightPPXKA"+prodCatId+p, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,p,"PPXKA");
						getDomElementById("rateAddWtSlabPPXKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabPPXKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getDomElementById("rateSpecialAddWeightSlabPPXKA"+prodCatId+"7").innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
					}

				}else if(configuredType == BOTH){
					if(!isNull(prodCatTO.weightSlabTO[j].startWeightTO) && (prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD) && isNull(prodCatTO.weightSlabTO[j].endWeightTO)){
						getCOAddDropdown("rateQuotAddWeightBOTH"+prodCatId+n, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,n,"BOTH");
						getDomElementById("rateAddWtSlabBOTH"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabBOTH"+prodCatId+n).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getDomElementById("rateSpecialAddWeightSlabBOTH"+prodCatId+"5").innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
					}else if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KA' && isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						getCOAddDropdown("rateQuotAddWeightBOTHKA"+prodCatId+p, prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId,prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel,p,"BOTHKA");
						getDomElementById("rateAddWtSlabBOTHKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].rateQuotationWeightSlabId;
						getDomElementById("ratehdnAddWtSlabBOTHKA"+prodCatId+p).value =  prodCatTO.weightSlabTO[j].startWeightTO.weightSlabId;
						getDomElementById("rateSpecialAddWeightSlabBOTHKA"+prodCatId+"9").innerHTML = prodCatTO.weightSlabTO[j].startWeightTO.startWeightLabel;
					}

				}
			}
		}
	}


}


function assignCOGridEndWeightSlabValues(k,l,wtSlabLen,type,wtSlabCat){
	if(!isNull(getDomElementById("rateStartWeightSlab"+type+prodCatId+k))){
		for(var j = 0 ;j<wtSlabLen;j++){
			if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&& wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[j].weightSlabTO.additional != 'Y'){
					if(wtList[j].weightSlabTO.weightSlabCategory == wtSlabCat){	 
						if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value ==  wtList[j].weightSlabTO.weightSlabId){
							for(var n = j+1 ;n<wtSlabLen;n++){
								if(wtList[n].weightSlabTO.weightSlabCategory == wtSlabCat
										&& wtList[n].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&& wtList[n].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[n].weightSlabTO.additional != 'Y'){
										getDomElementById("rateStartWeightSlab"+type+prodCatId+k).innerHTML = wtList[n].weightSlabTO.startWeightLabel;
										getDomElementById("rateSpecialStartWeightSlab"+type+prodCatId+k).innerHTML = wtList[n].weightSlabTO.startWeightLabel;
										if(!isNull(getDomElementById("rateQuotCOStartWeight"+l))){
											getDomElementById("rateQuotCOStartWeight"+l).value = wtList[n].weightSlabTO.weightSlabId;
										}
										break;
									}
								}
							}

							clearDropDownList("rateQuotEndWeight"+type+prodCatId+k);
							for(var n = j+1 ;n<wtSlabLen;n++){
								if(wtList[n].weightSlabTO.weightSlabCategory == wtSlabCat
										&& wtList[n].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&& wtList[n].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[n].weightSlabTO.additional != 'Y'){
										addOptionTODropDown("rateQuotEndWeight"+type+prodCatId+k,wtList[n].weightSlabTO.endWeightLabel,wtList[n].weightSlabTO.weightSlabId);
									}
								}
							}

							break;
						}
					}
				}
			}
		}
	}
}


function assignCOGridRateValues(slabWtLen){
	var m = 1;	
	for(var j = 0; j< slabWtLen ; j++){
		if(prodCatTO.weightSlabTO[j].rateConfiguredType == configuredType){
			if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "KU"){
				if(configuredType == PPX){
					m = 5;
				}else if(configuredType == BOTH){
					m = 7;
				}

			}
			for(var i = 0; i< secLen ; i++){

				if(sectorList[i].sectorType == "D" &&  sectorList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  sectorList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

					if((!isNull(prodCatTO.weightSlabTO[j].endWeightTO) || (isNull(prodCatTO.weightSlabTO[j].endWeightTO) 
							&& (prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'))) 
							&& !isNull(prodCatTO.weightSlabTO[j].startWeightTO) )
					{
						assignCOProductGridRateValues(i,j,m);
					}else if(isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
						if(configuredType == DOX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD){
							assignCOProductGridRateValues(i,j,5);
						}else if(configuredType == PPX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "PD"){
							assignCOProductGridRateValues(i,j,3);
						}else if(configuredType == PPX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "KA"){
							assignCOProductGridRateValues(i,j,7);
						}else if(configuredType == BOTH && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD){
							assignCOProductGridRateValues(i,j,5);
						}else if(configuredType == BOTH && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "KA"){
							assignCOProductGridRateValues(i,j,9);
						}
					}				
				}
			}
			m++;
		}
	}
}

function assignCOProductGridRateValues(i,j,m){
	var slabRateLength = prodCatTO.weightSlabTO[j].slabRateTO.length;
	for(var n=0;n<slabRateLength;n++){
		if(sectorList[i].sectorTO.sectorId == prodCatTO.weightSlabTO[j].slabRateTO[n].destinationSector){

			if(configuredType == DOX && prodCatTO.weightSlabTO[j].slabRateTO[n].rateConfiguredType == DOX){
				if(prodCatTO.weightSlabTO[j].slabRateTO[n].valueFromROI != 'Y'){
					if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
						if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
							getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
						}
					}
				}else{
					for(var p = 0; p< secLen ; p++){
						if(sectorList[p].sectorType == "D" 
							&&  sectorList[p].sectorTO.sectorCode == "ROI" 								
								&& sectorList[p].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m))){
								if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
									getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
								}
							}
						}
					}
				}
				if(!isNull(getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
					getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = 'N';
				}
			}else if(configuredType == PPX && prodCatTO.weightSlabTO[j].slabRateTO[n].rateConfiguredType == PPX){
				if(prodCatTO.weightSlabTO[j].slabRateTO[n].valueFromROI != 'Y'){
					if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
						if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
							getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
						}
					}
				}else{
					for(var p = 0; p< secLen ; p++){
						if(sectorList[p].sectorType == "D" 
							&&  sectorList[p].sectorTO.sectorCode == "ROI" 								
								&& sectorList[p].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m))){
								if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
									getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
								}
							}
						}
					}
				}
				if(!isNull(getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
					getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = 'N';
				}
			}else if(configuredType == BOTH && prodCatTO.weightSlabTO[j].slabRateTO[n].rateConfiguredType == BOTH){
				if(prodCatTO.weightSlabTO[j].slabRateTO[n].valueFromROI != 'Y'){
					if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
						if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
							getDomElementById("rate"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
						}
					}
				}else{
					for(var p = 0; p< secLen ; p++){
						if(sectorList[p].sectorType == "D" 
							&&  sectorList[p].sectorTO.sectorCode == "ROI" 								
								&& sectorList[p].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
							if(!isNull(getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m))){
								if(!isNull(prodCatTO.weightSlabTO[j].slabRateTO[n].rate)){
									getDomElementById("rate"+prodCatId+(sectorList[p].sectorTO.sectorId)+ m).value = prodCatTO.weightSlabTO[j].slabRateTO[n].rate;
								}
							}
						}
					}
				}
				if(!isNull(getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m))){
					getDomElementById("isROI"+prodCatId+(sectorList[i].sectorTO.sectorId)+ m).value = 'N';
				}
			}
		}
	}


}

function assignCOSpecialDestGridRateValues(slabWtLen){
	var splRateLen = 0;

	cnt = 0;

	lenSlab = prodCatTO.weightSlabTO.length;
	var j= 0;
	for(var k=0;k<lenSlab;k++){
		if(prodCatTO.weightSlabTO[k].rateConfiguredType == configuredType){
			if(!isNull(prodCatTO.weightSlabTO[k].splDestRateTO)){
				cnt = cnt+prodCatTO.weightSlabTO[k].splDestRateTO.length;
				j++;
			}
		}
	}

	splRateLen = cnt/j;

	n = 1;
	l = 0;
	c = 1;

	for(var r=1; r<=splRateLen; r++){			

		addRow(gridId);
	}
	var r = 1;
	for(var k=0;k<slabWtLen;k++){
		isExist = false;
		if(prodCatTO.weightSlabTO[k].rateConfiguredType == configuredType){
			var splLen = prodCatTO.weightSlabTO[k].splDestRateTO.length;
			for(var i =0;i<splLen;i++){
				getDomElementById("stateId"+prodCatId+r).value = prodCatTO.weightSlabTO[k].splDestRateTO[i].stateId;
				clearCityDropDownList("cityId"+prodCatId+r);
				loadCityDropDown("cityId"+prodCatId+r, "", prodCatTO.weightSlabTO[k].splDestRateTO[i].cityList);
				if(!isNull(prodCatTO.weightSlabTO[k].splDestRateTO[i].specialDestinationCityTO)){
					getDomElementById("cityId"+prodCatId+r).value = prodCatTO.weightSlabTO[k].splDestRateTO[i].specialDestinationCityTO.cityId;
				}
				r++;
			}
			break;
		}
	}

	for(var r=1; r<=splRateLen; r++){
		m = 1;
		for(var j = 0; j<slabWtLen ; j++){

			if(prodCatTO.weightSlabTO[j].rateConfiguredType == configuredType){
				state = getDomElementById("stateId"+prodCatId+r).value;
				city = getDomElementById("cityId"+prodCatId+r).value;

				var splValLen  = prodCatTO.weightSlabTO[j].splDestRateTO.length;
				for(var k=0;k<splValLen;k++){
					if((state == prodCatTO.weightSlabTO[j].splDestRateTO[k].stateId) 
							&& ((isNull(city) && isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO)) 
									|| ((!isNull(city) && !isNull(prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO) && (city == prodCatTO.weightSlabTO[j].splDestRateTO[k].specialDestinationCityTO.cityId))))){
						if(prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'){
							if(configuredType == PPX){
								m = 5;
							}else if(configuredType == BOTH){
								m = 7;
							}

						}
						if((!isNull(prodCatTO.weightSlabTO[j].endWeightTO) || (isNull(prodCatTO.weightSlabTO[j].endWeightTO) 
								&& (prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == 'KU'))) 
								&& !isNull(prodCatTO.weightSlabTO[j].startWeightTO) )
						{
							getDomElementById("specialDestRate"+prodCatId+m+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;

						}else if(isNull(prodCatTO.weightSlabTO[j].endWeightTO) && !isNull(prodCatTO.weightSlabTO[j].startWeightTO) ){
							if(configuredType == DOX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD){
								getDomElementById("specialDestRate"+prodCatId+"5"+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
							}else if(configuredType == PPX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "PD"){
								getDomElementById("specialDestRate"+prodCatId+"3"+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
							}else if(configuredType == PPX && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "KA"){
								getDomElementById("specialDestRate"+prodCatId+"7"+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
							}else if(configuredType == BOTH && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == DOXDD){
								getDomElementById("specialDestRate"+prodCatId+"5"+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
							}else if(configuredType == BOTH && prodCatTO.weightSlabTO[j].startWeightTO.weightSlabCategory == "KA"){
								getDomElementById("specialDestRate"+prodCatId+"9"+r).value = prodCatTO.weightSlabTO[j].splDestRateTO[k].rate;
							}
						}	



						//getDomElementById("specialDestRate"+prodCatId+n+m).value = specialSlabsList[k].specialDestinationId;									
						break;
					}
				}

				m++;
			}
		}


		n++;

		//END FOR LOOP - cols

	}


}


function getCODropdown(field, k, val, label, type){
	var uVal = "";	
	var uLabel = "";
	clearDropDownList("rateQuotEndWeight"+type+prodCatId+k);
	wtCODropDown(field,k,val,type);
	if(type == "BOTHKU" || type == "PPXKU"){
		var wLen = wtLen;
		for(var j=0;j<wLen;j++){
			if(wtList[j].weightSlabTO.weightSlabCategory == KU && wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[j].weightSlabTO.additional != 'Y' && wtList[j].weightSlabTO.weightSlabId == val){						
					getDomElementById(field).value = uVal;
					getDomElementById("ratehdnWtId"+type+prodCatId+k).value =  uVal;
					getDomElementById("rateSpecialEndWeightSlab"+type+prodCatId+k).innerHTML = uLabel; 
					break;
				}else{
					uVal =  wtList[j].weightSlabTO.weightSlabId;
					uLabel = wtList[j].weightSlabTO.endWeightLabel;
				}

			}
		}
	}else{		
		getDomElementById(field).value = val;
		getDomElementById("rateSpecialEndWeightSlab"+type+prodCatId+k).innerHTML = label;
	}


}


function wtCODropDown(field,k,val,type){
	wtListLength = wtList.length;
	for(var i = 0 ;i<wtListLength;i++){
		if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){

			if(wtList[i].weightSlabTO.additional != 'Y'){		 		
				if(configuredType == DOX && wtList[i].weightSlabTO.weightSlabCategory == "DA"){
					if(k==1){
						addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else{
						if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
							for(var j = ++i ;j<wtListLength;j++){
								if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[j].weightSlabTO.additional != 'Y' && wtList[j].weightSlabTO.weightSlabCategory == "DA"){
										addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
									}
								}
							}
							break;
						}
					}
				}else if(configuredType == PPX){
					if(type == "PPXPA" && wtList[i].weightSlabTO.weightSlabCategory == "PA"){
						if(k==1){
							addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else{
							if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
								for(var j = ++i ;j<wtListLength;j++){
									if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y'  && wtList[j].weightSlabTO.weightSlabCategory == "PA"){
											addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}
									}
								}
								break;
							}
						}
					}else if(type == "PPXKU" && wtList[i].weightSlabTO.weightSlabCategory == "KU"){
						if(k==1){
							addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else{
							for(var j = 0 ;j<wtListLength;j++){
								if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[j].weightSlabTO.additional != 'Y' &&  wtList[j].weightSlabTO.weightSlabCategory == "KU"){
										addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
									}
								}
							}
							break;				 	 		
						}
					}else if(type == "PPXKT" && wtList[i].weightSlabTO.weightSlabCategory == "KT"){
						if(k==5){		 				
							if(wtList[i].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value)){
								addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}else{
							if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
								for(var j = ++i ;j<wtListLength;j++){
									if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y' &&  wtList[j].weightSlabTO.weightSlabCategory == "KT"
											&& wtList[j].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdPPXKU"+prodCatId).value)){
											addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}
									}
								}
								break;
							}
						}
					}
				}else if(configuredType == BOTH){		 			
					if(type == "BOTH" && wtList[i].weightSlabTO.weightSlabCategory == "DA"){
						if(k==1){
							addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else{
							if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
								for(var j = ++i ;j<wtListLength;j++){
									if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y' &&  wtList[j].weightSlabTO.weightSlabCategory == "DA"){
											addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}
									}
								}
								break;
							}
						}
					}else if(type == "BOTHKU" && wtList[i].weightSlabTO.weightSlabCategory == "KU"){
						if(k==1){
							addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
						}else{
							for(var j = 0 ;j<wtListLength;j++){
								if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
										&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
									if(wtList[j].weightSlabTO.additional != 'Y' &&  wtList[j].weightSlabTO.weightSlabCategory == "KU"){
										addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
									}
								}
							}
							break;				 	 		
						}
					}else if(type == "BOTHKT" && wtList[i].weightSlabTO.weightSlabCategory == "KT"){
						if(k==7){		 				
							if(wtList[i].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value)){
								addOptionTODropDown(field,wtList[i].weightSlabTO.endWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							}
						}else{
							if(getDomElementById("rateQuotEndWeight"+type+prodCatId+(k-1)).value == wtList[i].weightSlabTO.weightSlabId ) {
								for(var j = ++i ;j<wtListLength;j++){
									if(wtList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
											&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
										if(wtList[j].weightSlabTO.additional != 'Y' &&  wtList[j].weightSlabTO.weightSlabCategory == "KT"
											&& wtList[j].weightSlabTO.endWeight >= parseFloat(getDomElementById("rateStartWtSlabIdBOTHKU"+prodCatId).value)){
											addOptionTODropDown(field,wtList[j].weightSlabTO.endWeightLabel,wtList[j].weightSlabTO.weightSlabId );
										}
									}
								}
								break;
							}
						}
					}
				}
			}
		}
	}
}

function getCOAddDropdown(field, val, label, k, type){
	clearDropDownList("rateQuotAddWeight"+type+prodCatId+k);
	addCOWtDropDown(field,val,k,type);
	getDomElementById(field).value = val;
	//getDomElementById("rateSpecialAddWeightSlab"+prodCatId).innerHTML = label;

}


function addCOWtDropDown(field,val,k,type){
	wtListLength = wtList.length;
	for(var i = 0 ;i<wtListLength;i++){
		if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			if(wtList[i].weightSlabTO.additional == 'Y'){
				if(configuredType == DOX && wtList[i].weightSlabTO.weightSlabCategory == DOXDD){
					addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
				}else if(configuredType == PPX){
					if(type == "PPXPD" && wtList[i].weightSlabTO.weightSlabCategory == "PD"){
						addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else if(type == "PPXKA" && wtList[i].weightSlabTO.weightSlabCategory == "KA"){
						addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}
				}else if(configuredType == BOTH){
					if(type == "BOTH" && wtList[i].weightSlabTO.weightSlabCategory == DOXDD){
						addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}else if(type == "BOTHKA" && wtList[i].weightSlabTO.weightSlabCategory == "KA"){
						addOptionTODropDown(field,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
					}
				}
			}
		}
	}
}

function clearCOValues(){
	for(var i=1;i<8;i++){
		getDomElementById("rateQuotCOStartWeight"+i).value = "";
	}

}

function enableCOSplDestRateFields(rowNo){
	var flag = false;
	var cols = 5;
	if(configuredType == DOX){
		cols = 5;
	}else if(configuredType == PPX){
		cols = 7;
	}else if(configuredType == BOTH){
		cols = 9;
	} 
	if(configuredType == DOX){
		for(var i=1;i<cols;i++){
			if(!isNull(getDomElementById("rateQuotEndWeightDOX"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightDOX"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;

			}
		}
		if(!isNull(getDomElementById("rateQuotAddWeightDOX"+prodCatId+"5")) && !isNull(getValueByElementId("rateQuotAddWeightDOX"+prodCatId+"5"))){
			getDomElementById("specialDestRate"+prodCatId+"5"+rowNo).disabled = flag;
		}
	}else if(configuredType == PPX){
		for(var i=1;i<=cols;i++){
			if(i<3 && !isNull(getDomElementById("rateQuotEndWeightPPXPA"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightPPXPA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 3 && !isNull(getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotAddWeightPPXPD"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 4 && !isNull(getDomElementById("rateQuotEndWeightPPXKU"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightPPXKU"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = true;
			}else if(i < 7 && !isNull(getDomElementById("rateQuotEndWeightPPXKT"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightPPXKT"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 7 && !isNull(getDomElementById("rateQuotAddWeightPPXKA"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotAddWeightPPXKA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}			

		}

	}else if(configuredType == BOTH){
		for(var i=1;i<=cols;i++){

			if((i<5) && !isNull(getDomElementById("rateQuotEndWeightBOTH"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightBOTH"+prodCatId+i))){				
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 5) && !isNull(getDomElementById("rateQuotAddWeightBOTH"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotAddWeightBOTH"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 6) && !isNull(getDomElementById("rateQuotEndWeightBOTHKU"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightBOTHKU"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = true;
			}else if((i < 9) && !isNull(getDomElementById("rateQuotEndWeightBOTHKT"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotEndWeightBOTHKT"+prodCatId+i))){				
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 9) && !isNull(getDomElementById("rateQuotAddWeightBOTHKA"+prodCatId+i)) && !isNull(getValueByElementId("rateQuotAddWeightBOTHKA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}			

		}

	}

}

function disableCOSplDestRateFields(rowNo){
	var flag = true;
	var cols = 5;
	if(configuredType == DOX){
		cols = 5;
	}else if(configuredType == PPX){
		cols = 7;
	}else if(configuredType == BOTH){
		cols = 9;
	} 
	if(configuredType == DOX){
		for(var i=1;i<cols;i++){
			if(!isNull(getDomElementById("rateQuotEndWeightDOX"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightDOX"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;

			}
		}
		if(!isNull(getDomElementById("rateQuotAddWeightDOX"+prodCatId+"5")) && isNull(getValueByElementId("rateQuotAddWeightDOX"+prodCatId+"5"))){
			getDomElementById("specialDestRate"+prodCatId+"5"+rowNo).disabled = flag;
		}
	}else if(configuredType == PPX){
		for(var i=1;i<=cols;i++){
			if(i<3 && !isNull(getDomElementById("rateQuotEndWeightPPXPA"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightPPXPA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 3 && !isNull(getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+i)) && isNull(getValueByElementId("rateQuotAddWeightPPXPD"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 4 && !isNull(getDomElementById("rateQuotEndWeightPPXKU"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightPPXKU"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i < 7 && !isNull(getDomElementById("rateQuotEndWeightPPXKT"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightPPXKT"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if(i == 7 && !isNull(getDomElementById("rateQuotAddWeightPPXKA"+prodCatId+i)) && isNull(getValueByElementId("rateQuotAddWeightPPXKA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}			

		}

	}else if(configuredType == BOTH){
		for(var i=1;i<=cols;i++){

			if((i<5) && !isNull(getDomElementById("rateQuotEndWeightBOTH"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightBOTH"+prodCatId+i))){				
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 5) && !isNull(getDomElementById("rateQuotAddWeightBOTH"+prodCatId+i)) && isNull(getValueByElementId("rateQuotAddWeightBOTH"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 6) && !isNull(getDomElementById("rateQuotEndWeightBOTHKU"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightBOTHKU"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i < 9) && !isNull(getDomElementById("rateQuotEndWeightBOTHKT"+prodCatId+i)) && isNull(getValueByElementId("rateQuotEndWeightBOTHKT"+prodCatId+i))){				
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}else if((i == 9) && !isNull(getDomElementById("rateQuotAddWeightBOTHKA"+prodCatId+i)) && isNull(getValueByElementId("rateQuotAddWeightBOTHKA"+prodCatId+i))){
				getDomElementById("specialDestRate"+prodCatId+i+rowNo).disabled = flag;
			}			

		}

	}

}


function clearCOWtSlabs(){

	if(configuredType == DOX){
		for(var i = 1; i<5; i++){
			getDomElementById("rateWtSlabIdDOX"+prodCatId+i).value = "";
		}
		getDomElementById("rateAddWtSlabDOX"+prodCatId+"5").value = "";	
	}else if(configuredType == PPX){
		for(var i = 1; i<3; i++){
			getDomElementById("rateWtSlabIdPPXPA"+prodCatId+i).value = "";
		}
		getDomElementById("rateAddWtSlabPPXPD"+prodCatId+"3").value = "";
		getDomElementById("rateWtSlabIdPPXKU"+prodCatId+"4").value = "";
		for(var i = 5; i<7; i++){
			getDomElementById("rateWtSlabIdPPXKT"+prodCatId+i).value = "";
		}
		getDomElementById("rateAddWtSlabPPXKA"+prodCatId+"7").value = "";
	}else if(configuredType == BOTH){
		for(var i = 1; i<5; i++){
			getDomElementById("rateWtSlabIdBOTH"+prodCatId+i).value = "";
		}
		getDomElementById("rateAddWtSlabBOTH"+prodCatId+"5").value = "";
		getDomElementById("rateWtSlabIdBOTHKU"+prodCatId+"6").value = "";
		for(var i = 7; i<9; i++){			
			getDomElementById("rateWtSlabIdBOTHKT"+prodCatId+i).value = "";
		}
		getDomElementById("rateAddWtSlabBOTHKA"+prodCatId+"9").value = "";
	}
}

function checkWeightSlabValues(){
	addSlabFlag = false;
	slabValKU = "";
	slabValK = "";
	flagAddSlab = false;
	var slabVal1 = "";
	var slabVal2 = "";
	var slabVal3 = "";
	var slabVal4 = "";
	var slabVal5 = "";
	var wtLen = wtList.length;
	var wtSlabVal1 = null;
	var wtSlabVal2 = null;

	if(configuredType == PPX){
		slabVal1 = getDomElementById("rateQuotEndWeightPPXKU"+prodCatId+"4").value;
		slabVal2 = getDomElementById("rateQuotEndWeightPPXPA"+prodCatId+"2").value;
		slabVal3 = getDomElementById("rateQuotEndWeightPPXPA"+prodCatId+"1").value;

		if(!isNull(slabVal1) && (!isNull(slabVal2) || !isNull(slabVal3))){
			for(var i =0 ;i<wtLen;i++){
				if((wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
						&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId
								&& 	wtList[i].weightSlabTO.additional != 'Y')){

					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].weightSlabTO.weightSlabId == slabVal1){
						wtSlabVal1 = wtList[i].weightSlabTO.endWeight;
					}
					else if(!isNull(slabVal2) && wtList[i].weightSlabTO.weightSlabCategory == PA && wtList[i].weightSlabTO.weightSlabId == slabVal2){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}else if(isNull(slabVal2) && !isNull(slabVal3) && wtList[i].weightSlabTO.weightSlabCategory == PA && wtList[i].weightSlabTO.weightSlabId == slabVal3){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}
					if(!isNull(wtSlabVal1) && !isNull(wtSlabVal2)){
						slabValKU = wtSlabVal2;
						slabValK = wtSlabVal1;
						if(parseFloat(wtSlabVal1) < parseFloat(wtSlabVal2)){
							return false;
						}else if(parseFloat(wtSlabVal1) > parseFloat(wtSlabVal2)){
							if(isNull(getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3").value)){
								addSlabFlag = true;
								getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3").focus();
								return false;
							}
						}else if(parseFloat(wtSlabVal1) == parseFloat(wtSlabVal2)){
							if(!isNull(getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3").value)){
								flagAddSlab = true;
								return false;
							}
						}
						break;
					}	 
				}
			}

		}	 
	}else if(configuredType == BOTH){
		slabVal1 = getDomElementById("rateQuotEndWeightBOTHKU"+prodCatId+"6").value;
		slabVal2 = getDomElementById("rateQuotEndWeightBOTH"+prodCatId+"4").value;
		slabVal3 = getDomElementById("rateQuotEndWeightBOTH"+prodCatId+"3").value;
		slabVal4 = getDomElementById("rateQuotEndWeightBOTH"+prodCatId+"2").value;
		slabVal5 = getDomElementById("rateQuotEndWeightBOTH"+prodCatId+"1").value;

		if(!isNull(slabVal1) && (!isNull(slabVal2) || !isNull(slabVal3) || !isNull(slabVal4) || !isNull(slabVal5))){
			for(var i =0 ;i<wtLen;i++){
				if((wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId)
						&&  (wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId
								&& 	wtList[i].weightSlabTO.additional != 'Y')){

					if(wtList[i].weightSlabTO.weightSlabCategory == KU && wtList[i].weightSlabTO.weightSlabId == slabVal1){
						wtSlabVal1 = wtList[i].weightSlabTO.endWeight;
					}
					else if(!isNull(slabVal2) && wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].weightSlabTO.weightSlabId == slabVal2){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}else if(isNull(slabVal2) && !isNull(slabVal3) && wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].weightSlabTO.weightSlabId == slabVal3){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}else if(isNull(slabVal2) && isNull(slabVal3) && !isNull(slabVal4) && wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].weightSlabTO.weightSlabId == slabVal4){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}else if(isNull(slabVal2) && isNull(slabVal3) && isNull(slabVal4) && !isNull(slabVal5) && wtList[i].weightSlabTO.weightSlabCategory == DA && wtList[i].weightSlabTO.weightSlabId == slabVal5){
						wtSlabVal2 = wtList[i].weightSlabTO.endWeight;
					}

					if(!isNull(wtSlabVal1) && !isNull(wtSlabVal2)){
						slabValKU = wtSlabVal2;
						slabValK = wtSlabVal1;
						if(parseFloat(wtSlabVal1) < parseFloat(wtSlabVal2)){
							return false;
						}else if(parseFloat(wtSlabVal1) > parseFloat(wtSlabVal2)){
							if(isNull(getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5").value)){
								addSlabFlag = true;
								getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5").focus();
								return false;
							}
						}else if(parseFloat(wtSlabVal1) == parseFloat(wtSlabVal2)){
							if(!isNull(getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5").value)){
								flagAddSlab = true;
								return false;
							}
						}
						break;
					}	 
				}
			}

		}	 
	}

	return true;
}


function checkFinalWeightSlabs(){

	addSlabFlag = false;
	slabValKU = "";
	slabValK = "";
	flagAddSlab = false;

	if(!checkWeightSlabValues()){
		if(addSlabFlag){
			alert("please select additional weight slab");
		}else if(flagAddSlab){
			alert("Please unselect the additional weight");
		}
		if(configuredType == PPX){
			getDomElementById("rateQuotAddWeightPPXPD"+prodCatId+"3").focus();
			return false;
		}else if(configuredType == BOTH){
			getDomElementById("rateQuotAddWeightBOTH"+prodCatId+"5").focus();
			return false;
		}
	}
	return true;
}

function checkAddWtSlabs(){
	var slabVal1 = "";
	var slabVal2 = "";
	var slabVal3 = "";


	if(configuredType == PPX){
		slabVal1 = getDomElementById("rateQuotEndWeightPPXKU"+prodCatId+"4").value;
		slabVal2 = getDomElementById("rateQuotEndWeightPPXKT"+prodCatId+"5").value;
		slabVal3 = getDomElementById("rateQuotAddWeightPPXKA"+prodCatId+"7").value;

		if(!isNull(slabVal1) && isNull(slabVal2) && isNull(slabVal3)){
			alert("please select additional weight");
			getDomElementById("rateQuotAddWeightPPXKA"+prodCatId+"7").focus;
			return false;
		}else if(isNull(slabVal1) && isNull(slabVal2) && !isNull(slabVal3)){
			alert("please select upto weight slab");
			getDomElementById("rateQuotEndWeightPPXKU"+prodCatId+"4").focus;
			return false;
		}
	}else if(configuredType == BOTH){
		slabVal1 = getDomElementById("rateQuotEndWeightBOTHKU"+prodCatId+"6").value;
		slabVal2 = getDomElementById("rateQuotEndWeightBOTHKT"+prodCatId+"7").value;
		slabVal3 = getDomElementById("rateQuotAddWeightBOTHKA"+prodCatId+"9").value;

		if(!isNull(slabVal1) && isNull(slabVal2) && isNull(slabVal3)){
			alert("please select additional weight");
			getDomElementById("rateQuotAddWeightBOTHKA"+prodCatId+"9").focus;
			return false;
		}else if(isNull(slabVal1) && isNull(slabVal2) && !isNull(slabVal3)){
			alert("please select upto weight slab");
			getDomElementById("rateQuotEndWeightBOTHKU"+prodCatId+"6").focus;
			return false;
		}
	}

	return true;
}

function isProposedRatesConfigured(){

	if(!isNull(getDomElementById("proposedRatesCO")) && (getDomElementById("proposedRatesCO").value == "Y")){
		var doxVal = getDomElementById("proposedRatesD").value;
		var ppxVal = getDomElementById("proposedRatesP").value;
		var bothVal = getDomElementById("proposedRatesB").value;
		if(doxVal == "Y" && ppxVal == "Y"){
			return true;
		}else if(bothVal == "Y"){
			return true;
		}else{
			if(doxVal == 'Y' && ppxVal == 'N'){
				alert("Please enter Rate values for Parcel");
				return false;
			}else if(doxVal == 'N' && ppxVal == 'Y'){
				alert("Please enter Rate values for Documents");
				return false;
			}
		}
	}
	return true;
}

function prepareGrid(gridId){

	var header = '<thead><tr>';
	var splheader = '<thead><tr>';

	splheader = splheader+'<th width = "3%" align="center">SrNo.</th>';
	splheader = splheader+'<th width = "9%" align="center">State</th>';
	splheader = splheader+'<th width = "9%" align="center">City</th>';


	getDomElementById("rateVob"+prodCatId).options.length = 0;


	header = header+'<th align="center" width="21%">Sector / Weight Slab (Kg)</th>';





	var i=1;
	for(i=1; i<5; i++){
		header = header+'<th align="center"><span id="rateStartWeightSlab'+prodCatId+i+'"></span>	-';
		header = header+'<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeight'+prodCatId+i+'" class="selectBox width80" onchange="assignWeightSlab(\'rateQuotEndWeight'+prodCatId+i+'\',\'rateStartWeightSlab'+prodCatId+(i+1)+'\',\'rateQuotEndWeight'+prodCatId+(i+1)+'\',\'rateSpecialStartWeightSlab'+prodCatId+(i+1)+'\','+prodCatId+','+i+')">';
		header = header+'<option value="">--select--</option></select>';
		header = header+'<input type="hidden" id="rateWtSlabId'+prodCatId+i+'" name="proposedRatesTO.rateWtSlabId" class="txtbox width130"/><input type="hidden" class="txtbox width130" id="rateWtId" name="proposedRatesTO.rateWtId" value="'+prodCatId+'"/>';
		header = header+'<input type="hidden" id="ratehdnWtId'+prodCatId+i+'" name="ratehdnWtId'+prodCatId+i+'" /></th>';

		splheader = splheader+'<th align="center" width="15%"><span id="rateSpecialStartWeightSlab'+prodCatId+i+'"></span> - <span id="rateSpecialEndWeightSlab'+prodCatId+i+'"></span></th>';
	}

	header = header+'<th><select name="rateQuotAddWt" id="rateQuotAddWeight'+prodCatId+'" class="selectBox width90" onchange="assignAddWeightSlab(\'rateQuotAddWeight'+prodCatId+'\');">';
	header = header+'<option value="">--select--</option></select>';
	header = header+'<input type="hidden" id="rateAddWtSlab'+prodCatId+'" name="proposedRatesTO.rateAddWtSlab" class="txtbox width130" />';
	header = header+'<input type="hidden" id="ratehdnAddWtSlab'+prodCatId+'" name="ratehdnAddWtSlab'+prodCatId+'"/></th>';
	splheader = splheader+'<th align="center"><span id="rateSpecialAddWeightSlab'+prodCatId+'"> - </span></th>';



	header = header+'</tr></thead>';
	splheader = splheader+'</tr></thead>';

	deleteTableRow("example"+gridId);
	$('#example'+gridId).dataTable().fnDestroy(true);
	$('#example'+gridId).children('thead').remove();
	$('#example'+gridId).append(header);
	$('#example'+gridId).dataTable( {
		"sScrollY": "100",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,					
		"sPaginationType": "full_numbers"

	} );
	document.getElementById("example"+gridId).style.width = '100%';

	deleteTableRow("specialGrid"+gridId);
	$('#specialGrid'+gridId).dataTable().fnDestroy(true);
	$('#specialGrid'+gridId).children('thead').remove();
	$('#specialGrid'+gridId).append(splheader);
	$('#specialGrid'+gridId).dataTable( {
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"		
	} );

	document.getElementById("specialGrid"+gridId).style.width = '100%';


}

function validAboveWeightSlabs(){
	var addtnalId = 0;
	var addtnalVal = 0;
	var lastWtslabId = 0;
	var lastWtSlabVal = 0;
	var selWt = false;
	if(productCode == 'TR' || productCode == 'AR'){
		addtnalId = document.getElementById("rateQuotAddWeight"+prodCatId).value;

		if(!isNull(addtnalId)){
			for(var j=1;j < 5;j++){
				if(!isNull(getDomElementById("rateQuotEndWeight"+prodCatId+j).value)){
					lastWtslabId = getDomElementById("rateQuotEndWeight"+prodCatId+j).value;
					selWt = true;					
				}
			}

			var wtLen = wtList.length;
			if(selWt){
				for(var i =0 ;i<wtLen;i++){
					if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional != 'Y'){
							if(wtList[i].weightSlabTO.weightSlabId == lastWtslabId){
								lastWtSlabVal = wtList[i].weightSlabTO.endWeight;
								break;
							}
						}
					}
				}

				for(var i =0 ;i<wtLen;i++){
					if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
							&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
						if(wtList[i].weightSlabTO.additional == 'Y'){
							if(wtList[i].weightSlabTO.weightSlabId == addtnalId){
								addtnalVal = wtList[i].weightSlabTO.startWeight;
								break;
							}
						}
					}
				}
				lastWtSlabVal = lastWtSlabVal+0.001;

				if(addtnalVal != lastWtSlabVal){
					alert("Please select heighest weight slab");
					return false;
				}

			}else{
				alert("Please select heighest weight slab");
				return false;
			}

		}
	}

	return true;
}


function assignAboveWeightSlabs(selSlab,startWt,endWt, specialStartWt, prodCatVal, curPos){

	clearAboveWeightSlabs();
	var slabId = getDomElementById(selSlab).value;
	var slabVal = 0;
	if(!isNull(slabId)){
		for(var i =0 ;i<wtLen;i++){
			if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
					&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
				if(wtList[i].weightSlabTO.additional != 'Y'){
					if(wtList[i].weightSlabTO.weightSlabId == slabId){
						slabVal = wtList[i].weightSlabTO.endWeight;
						break;
					}
				}
			}
		}

		if(slabVal >= 0){
			for(var i =0 ;i<wtLen;i++){
				if(wtList[i].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
						&&  wtList[i].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
					if(wtList[i].weightSlabTO.additional == 'Y'){
						if(wtList[i].weightSlabTO.startWeight >= parseFloat(slabVal)){
							addOptionTODropDown("rateQuotAddWeight"+prodCatId,wtList[i].weightSlabTO.startWeightLabel,wtList[i].weightSlabTO.weightSlabId );
							break;
						}
					}
				}
			}
		}
	}
}

function clearAboveWeightSlabs(){
	clearDropDownList("rateQuotAddWeight"+prodCatId);
	clearAddAboveWegihtSlabs();
}


function clearAddAboveWegihtSlabs(){
	getDomElementById("rateSpecialAddWeightSlab"+prodCatId).innerHTML = "-";
	getDomElementById("rateAddWtSlab"+prodCatId).value = "";
	for(var j = 0; j< secLen ; j++){
		if(sectorList[j].sectorType == "D" &&  sectorList[j].rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == prodCatId
				&&  sectorList[j].rateCustomerProductCatMapTO.rateCustomerCategoryTO.rateCustomerCategoryId == custId){
			getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").value = "";
			getDomElementById("rate"+prodCatId+sectorList[j].sectorTO.sectorId+"5").disabled = true;
		}
	}
	for(var k = 1; k<rowCount; k++){
		if(!isNull(getDomElementById("specialDestRate"+prodCatId+"5"+k))){
			getDomElementById("specialDestRate"+prodCatId+"5"+k).value = "";
			getDomElementById("specialDestRate"+prodCatId+"5"+k).disabled = true;
		}
	}
}

/* The user should not be allowed to modify any part of the proposed rates tab using the super-edit functionality.
 * Currently, users modify the rates from any contract at will. This causes problems in the billing module when the bills get generated.
 * Hence, the below lines of code have been added*/
function restrictRateModificationViaSuperEdit (callingMethodName) {
	var userType = getDomElementById("userType");
	if (!isNull(userType)) {
		if (userType.value != "" && userType.value == "S") {
			if (callingMethodName == "assignVal") {
				disableVobAndWeights();
				disableProposedRatesGridFields();
			}
			else if (callingMethodName == "assignConfiguredWeightSlabs") {
				disableVobAndWeights();
			}
			disableSpecialDestGridFields();
			diabledProposedRatesButtons();
		}
	}
	return true;
}