//variables
// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each parameter supported by a function.
 */

var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
var tableId=null;
var formId=null;
var savePromptMsg="Do you want to generate DRS ?";
/** Global id to inspect whether  delete functionality executed at least one*/
var isDeleted=false;

var pageLoadUrl=null;
var dicardDrsUrl=null;
var modifyDrsUrl=null;
var printDrsUrl=null;
var nonDeliveryReasons=null;
/** Global const for each screen for No of rows to be allowed in the Grid*/
var maxAllowedRows=null;
/** set  a flag ,to make sure that alert the message only one time*/
var isMaxAllowdRowsVldtd=false;
var count=0;
var DRS_UPDATE_CONFIRMATION_AFTER_SAVE="Do you wish to update another runsheet ?";
/** Make sure that for add a new Row always use method Name as addRow() across the Module*/


function isConfirmedForAnotherUpdate(){
	if(confirm(DRS_UPDATE_CONFIRMATION_AFTER_SAVE)){
		return true;
	}
	return false;
}
/**
 * focusOnElement : it focus on the given Element with Id
 * @param element
 * @param rowId
 */
function focusOnElementWithId(element,rowId){
	var rowElement=isNull(rowId)?element:element+rowId;
	var domElement =getDomElementById(rowElement);
	setFocusOnDom(domElement);
}

/**
 * focusOnElement : it focus on the given Dom Element
 * @param element
 * @param rowId
 */
function focusOnElement(element){
	focusOnElementWithId(element,null);
}

/**
 * setFocusOnDom
 * @param domElement
 */
function setFocusOnDom(domElement){
	if(domElement!=null){
		setTimeout( /** make a focus on given element*/
				function(){domElement.focus();}
				,10);
	}
}


/**setFocusOnDomId
 * 
 * @param domElementId
 */
function setFocusOnDomId(domElementId){
	var domElement=getDomElementById(domElementId);
	if(domElement!=null){
	setFocusOnDom(domElement);
	}
}

/**
 * deleteRow
 * @param customerIdArray
 */
function deleteRow(tableId,chkBxName){
	var tableee = getDomElementById(tableId);
	var cntofRow = tableee.rows.length; 
	var chkBxNameList = document.getElementsByName(chkBxName);
	
	for(var i=1;i<cntofRow;i++){
		isDeleted=true;
		var j=i-1;
		
			if(!chkBxNameList[j].disabled && chkBxNameList[j].checked){
				if(cntofRow<=2){
					alert("Default row can not be deleted");
					chkBxNameList[j].checked=false;
					break;
				}
				//tableee.deleteRow(i);
				var oTable = $('#'+tableId).dataTable();
				oTable.fnDeleteRow(j); 
				cntofRow--;
				i--;
				//rowCount--;
			}
	}
	updateGrid(tableId);
	uncheckGlobalBox();
}

/**updateGrid
 * @param tableID
 */
function updateGrid(tableID) {
	var tableRows = document.getElementById(tableID).rows;
	var length=tableRows.length;
	if(length==1){/**table should have atleast one row*/
		return;
	}
	for(var i=1; i <length; i++) { 
		var rowCells=tableRows[i].cells;
		rowCells[1].innerHTML=i;
	}
}
/**
 * uncheckGlobalBox :it's uncheck the check box after deleting rows from the Grid
 */
function uncheckGlobalBox(){
	getDomElementById("chkAll").checked=false;
}

/**
 * isNewDrs :: check whether loaded DRS Sheet new or old By inspecting it's id
 * @returns {Boolean}
 */
function isNewDrs(){
	var dlvId=getDomElementById("deliveryId").value;
	if(!isNull(dlvId)){
		return false;
	}
	return true;
}
var drsForDom=null;
function getDRSForPartyDetails(domElemnt){
	drsForDom=domElemnt;
	createEmptyDropDown('drsPartyId');
	if(!isNull(domElemnt.value)){
		var url='./prepNormDoxDrs.do?submitName=ajaxGetPartyTypeDetailsForDRS&drsFor='+domElemnt.value;
		ajaxJquery(url,formId,ajxRespForDRSPartyDetails);
	}
	
}

/**
 * ajxRespForReceivingType : for IssuedTo dropdown
 * @param rspObject
 * @returns {Boolean}
 */
function ajxRespForDRSPartyDetails(rspObject){
	var partyTypeDetails=null;
	if(!isNull(rspObject)){
		if(!isJsonResponce(rspObject)){
		partyTypeDetails = rspObject;
		createDropDown('drsPartyId',partyTypeDetails);
		}else{
			drsForDom.value="";
			createEmptyDropDown('drsPartyId');
			setFocusOnDom(drsForDom);
		}
	}else{
		alert(getSelectedDropDownTextByDOM(drsForDom)+ " details does not exist");
		drsForDom.value="";
		createEmptyDropDown('drsPartyId');
		setFocusOnDom(drsForDom);
	}
	
}

function isJsonResponce(ObjeResp){
 	var responseText=null;
 	try{
 		responseText = jsonJqueryParser(ObjeResp);
 	}catch(e){
 		
 	}
 	if(!isNull(responseText)){
 		var error = responseText[ERROR_FLAG];
 		if(!isNull(error)){
 		alert(error);
 		return true;
 		}
 	}
 	return false;
 }
/**
 * findOnEnterKey
 * @param action
 * @param keyCode
 */
function findOnEnterKey(keyCode){
	
	if(enterKeyNavWithoutFocus(keyCode)){
	find();
	}
}
/**
 * focusOnFirstRowConsignment :: it will be called from Header for DRS Prepration
 * @param keyCode
 */
function focusOnFirstRowConsignment(keyCode){
	
	if(enterKeyNavWithoutFocus(keyCode)){
		var cNumber=document.getElementsByName("to.rowConsignmentNumber");
		if(cNumber!=null && cNumber.length>=1){
			setFocusOnDom(cNumber[0]);
			if(isThirdPartyManifestDrs()){
				focusOnDeliveryType(-1);
			}
		}
	}
	
}
/**
 * focusOnFirstRowDeliveryType :: it will be called from Header for DRS Update/Pending DRS
 * @param keyCode
 */
function focusOnFirstRowDeliveryType(keyCode){
	
	
	if(enterKeyNavWithoutFocus(keyCode)){
		var delvType=document.getElementsByName("to.rowDeliveryType");
		if(delvType!=null && delvType.length>=1){
			setFocusOnDom(delvType[0]);
		}else{
			var cNumber=document.getElementsByName("to.rowConsignmentNumber");
			if(cNumber!=null && cNumber.length>=1 && !cNumber[0].readOnly){
				setFocusOnDom(cNumber[0]);
			}
		}
	}
	
}
/**
 * checkDuplicateConsignments : check duplicate consignment numbers in the Grid 
 * 	and returns false if duplicate cnotes exists otherwise true
 * @param domElement
 * @returns {Boolean}
 */
function checkDuplicateConsignments(domElement){
	var currentRowId=getRowId(domElement,'rowConsignmentNumber');
	var currentCnote=convertToUpperCase(domElement.value);
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var isDuplicateExist=false;
	if(!validateConsignmentFormat(currentCnote)){
		domElement.value="";
		/** focus on cleared cnotes*/
		setFocusOnDom(domElement);
		isDuplicateExist=true;
		return false;
	}
	/** check only if more than one line item exists*/
	if(cNumber!=null && cNumber.length>1){
		$.each(cNumber, function(k, consignment) {
			var rowId=getRowId(consignment,'rowConsignmentNumber');
			var cnote=convertToUpperCase(consignment.value);
			if(currentRowId!=rowId){
				if(currentCnote == cnote ){
					alert("Given consignment number already exist at line number :"+(k+1));
					domElement.value="";
					//domElement.focus();
					/** focus on cleared cnotes*/
					setFocusOnDom(domElement);
					
					isDuplicateExist=true;
					return false;
				}

			}
		});

	}
	if(isDuplicateExist){
		return false;
	}
	return true;
}
/**
 * headerValidationsForFind :: For DRS Preparation
 */
function validationsForFind(){
	var manualDrsTypeDom= getDomElementById('manifestDrsType');
	var isMdrs=false;
	if(manualDrsTypeDom!=null){
		manualDrsTypeDom.disabled=false;
		var drstype=manualDrsTypeDom.value;
		//manualDrsTypeDom.disabled=true;
		if(isNull(drstype)){
			alert("Please Select Manual DRS-Type");
			setFocusOnDom(manualDrsTypeDom);
			return false;
		}
		if(drstype == getManualDrsType()){
			isMdrs=true;
		}
	}
	var drsDom= getDomElementById('drsNumber');
	if(isNull(drsDom.value)){
		alert("Please enter DRS number");
		setFocusOnDom(drsDom);
		return false;
	}else if(isMdrs){
		var loffice=getLoggedInBranchCode();
		var expFormat="M"+loffice;
		drsDom.value=convertToUpperCase(drsDom.value);
		var drsNumber=drsDom.value;
		if(drsNumber.length !=13){
			alert(" Manual Drs number length should be 13 digits");
			drsDom.value="";
			setFocusOnDom(drsDom);
			return false;
		}
		if(!checkStartsWith(drsNumber,expFormat)){
			alert("Manual DRS number should starts with " +expFormat);
			drsDom.value="";
			setFocusOnDom(drsDom);
			return false;
		}
		
	}
	
	return true;
}
/**
 * headerValidationsForSave :: For DRS Preparation
 */
function headerValidationsForSave(){
	var drsDom= getDomElementById('drsNumber');
	var drsForDom= getDomElementById('drsFor');
	var drPartyTypeDom= getDomElementById('drsPartyId');
	var ypDrsDom= getDomElementById('ypDrs');
	var loadNumberDom= getDomElementById('loadNumber');
	
	
	if(isNewDrs()){
		var  manifestDrsType =getDomElementById('manifestDrsType');
		if(isNull(manifestDrsType)){
			drsDom.value="";
		}
	}
	if(isNull(drsForDom.value)){
		alert("Please select DRS-FOR");
		setFocusOnDom(drsForDom);
		return false;
	}
	if(isNull(drPartyTypeDom.value)){
		alert("Please select "+getSelectedDropDownTextByDOM(drsForDom)+" Details");
		setFocusOnDom(drPartyTypeDom);
		return false;
	}
	if(ypDrsDom !=null && isNull(ypDrsDom.value)){
		alert("Please select YP-DRS Details");
		setFocusOnDom(ypDrsDom);
		return false;
	}
	
	if(isNull(loadNumberDom.value)){
		alert("Please select Load-Number ");
		setFocusOnDom(loadNumberDom);
		return false;
	}
	return true;
}
function validateBasicGridValidations(){
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var cNId=document.getElementsByName("to.rowConsignmentId");

	if(cNumber!=null && cNumber.length>=1 ){
		for(var i=0;i<cNumber.length;i++){
			if(i!=0 && i==cNumber.length-1){
				continue;
//				}else if(isNull(cNumber[i].value) || isNull(cNId[i].value)){
			}else if(isNull(cNumber[i].value) || isNull(cNId[i].value)){
				alert("Please provide consignment number at line :"+(i+1));
				cNId[i].value="";
				cNumber[i].value="";
				setFocusOnDom(cNumber[i]);
				return false;
			}else if(cNId!=null && isNull(cNId[i].value)){
				alert("Please provide valid consignment number at line :"+(i+1));
				cNId[i].value="";
				cNumber[i].value="";
				setFocusOnDom(cNumber[i]);
				return false;
			}
		}
	}else{
		alert("Please add atleast one consignment details");
		return false;
	}
	return true;
}
/**
 * globalFormSubmit
 * @param url
 * @param formId
 */
function globalFormSubmit(url,formId){

	 document.forms[formId].action=url;
	 document.forms[formId].submit();
}
/**
 * promptConfirmation
 * 
 */
function promptConfirmation(message){
	return confirm(message);
}
/**
 * getAllowedSeries
 *  */
function getAllowedSeries(){
	var series= getDomElementById('allowedSeries');
	var seriesVal=null;
	var arrayA= new Array();
	if(!isNull(series)){
		seriesVal=series.value;
		arrayA = convertStringToArray(seriesVal);
	}else{
		alert("Please add Hidden field for in DrsCommon.jsp for allowed series");
	}
	return arrayA;
}
function convertStringToArray(str){
	var arrayA=null;
	var seriesArray= str.split(",");
	if(seriesArray!=null){
		arrayA= new Array();
		for(var i=0;i<seriesArray.length;i++){
			arrayA[i]=trimString(seriesArray[i]);
		}
	}
	return arrayA;
}
/**
 * flagYes
 */
function flagYes(){
	 return getDomElementById('flagYes').value;
}
/**
 * flagNo
 */
function flagNo(){
	return getDomElementById('flagNo').value;
}
function checkAllowedConsignments(consgDom){
	var consgNumber=consgDom.value;
	consgNumber=convertToUpperCase(consgNumber);
	consgNumber= trimString(consgNumber);
	if(isComailConsg(consgNumber)){
		if(comailLength()!=consgNumber.length){
			alert("Invalid Co-Mail length ");
			return false;
		}
	}else if(isNormalCnote(consgNumber)){
		if(cnotelength()!=consgNumber.length){
			alert("Invalid  length  of Normal Cnote");
			return false;
		}
		if(!isNormalCnoteAllowed()){
			alert("Normal cnotes are not allowed here");
			return false;
		}
		
	} else if(isValidConsgment(consgNumber)){
		if(cnotelength()!=consgNumber.length){
			alert("Cnote length is not valid ");
			return false;
		}
	}else{
		alert("Given consignment is not allowed here ");
		return false;
	}
	
	return true;
	
}
/**
 * 
 * @param consgNumber
 */
function isNormalCnote(consgNumber){
	var charCode=consgNumber.charCodeAt(4);
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
		return false;
	}
	return true;
}
/**
 * getCnoteProduct ::
 * @param consgNumber
 * @returns
 */
function getCnoteProduct(consgNumber){
	var charCode=consgNumber.charAt(4);
	return charCode;
}

function isNormalCnoteAllowed(){
	 var flag=getDomElementById('isNormalCnoteAllowed').value;
	 if(flag == flagYes()){
		 return true;
	 }
	 return false;
}
/**
 * isComailConsg: validate whether given Serial number is Comail or not
 * @param consgNumber
 * @returns {Boolean}
 */
function isComailConsg(consgNumber){
	var isComail=false;
	var seriesArray=getAllowedSeries();
	var comail=consgNumber.substring(0,2);
	var isExistIn=-1;
	if(!isNull(comail)&& seriesArray!=null){
		isExistIn= isExistInArray(comail,seriesArray);
	}
	if(isExistIn<0){
		return false;
	}else{
		if(comail == getComailProduct()){
			isComail=true;
		}
	}
	return isComail;
}
/**
 * getComailProduct 
 * @returns
 */
function getComailProduct(){
	return  getDomElementById('comailStartsWith').value;
}

/**
 * isValidConsgment: checks if the consignment number product exist in allowed series
 * @param consgNumber
 * @returns {Boolean}
 */
function isValidConsgment(consgNumber){
	var seriesArray=getAllowedSeries();
	var procduct=getCnoteProduct(consgNumber);
	var isExistIn=-1;
	if(!isNull(procduct)&& seriesArray!=null){
		isExistIn= isExistInArray(procduct,seriesArray);
	}
	if(isExistIn<0){
		return false;
	}
	return true;
}
/**
 *  cnotelength : get length of the Cnote
 * @returns
 */
function cnotelength(){
	return getDomElementById('consgNumLength').value;
}
function getGivenCnLength(cnNumber){
	if(isNull(cnNumber)){
		return 0;
	}
	return cnNumber.length;
}
/**
 * comailLength : get length of the comail number
 * @returns
 */
function comailLength(){
	return getDomElementById('comailLength').value;
}
/**
 * disableForUser :: It inspects "canUpdate" property to disable
 * if it's 'N',it's not allowed for modifications
 */
function disableForUser(){
	 var canUpdateDom=getDomElementById("canUpdate");

	 if(canUpdateDom!=null && !isNull(canUpdateDom.value) && (canUpdateDom.value == flagNo()|| trimString(canUpdateDom.value) == flagNo())){
		 disableAll();
		// getDomElementById("Cancel").disabled=false;
		// getDomElementById("Print").disabled=false;
		 enableGlobalButton('Cancel');
		 enableGlobalButton('Print');
	 }
}
/**
 * disableHeaderForPreparation
 */
function disableHeaderForPreparation(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		dropdownDisable();
		getDomElementById('drsFor').disabled=false;
		getDomElementById('drsPartyId').disabled=false;
		enableGlobalButton('Cancel');
		enableGlobalButton('Modify');
		enableGlobalButton('Print');
	}
}
function disableForDrsPrepOnLoad(){
	if(isNewDrs()){
		addRow();
		disableGlobalButton('Modify');
		disableGlobalButton('Discard');
		disableGlobalButton('Print');
	}else{
		disableGlobalButton('Save');
		disableGlobalButton('Print');
		disableGlobalButton('Delete');
	}
}
function getDrsUpdatedStatus(){
	return getDomElementById("drsStatusUpdate").value;
}

function getDrsClosedStatus(){
	return getDomElementById("drsStatusClosed").value;
}
/**
 * isDrsClosed 
 * @returns {Boolean}
 */
function isDrsClosed(){
	var closed=false;
	var drsStatus=getDomElementById('drsStatus').value;
	if(drsStatus == getDrsClosedStatus()){
		closed =true;
	}
	return closed;
}
/**
 * isDrsOpend
 * @returns {Boolean}
 */
function isDrsOpend(){
	var opened=false;
	var drsStatus=getDomElementById('drsStatus').value;
	if(drsStatus != getDrsClosedStatus() && drsStatus != getDrsUpdatedStatus()){
		opened =true;
	}
	return opened;
}
/**
 * isDrsUpdated
 * @returns {Boolean}
 */
function isDrsUpdated(){
	var updated=false;
	var drsStatus=getDomElementById('drsStatus').value;
	if(drsStatus == getDrsUpdatedStatus()){
		updated =true;
	}
	return updated;
}

/**
 * disableHeaderForUpdate
 */
function disableHeaderForUpdate(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		getDomElementById('drsFor').disabled=true;
		getDomElementById('drsPartyId').disabled=true;
		var ypDrsDom= getDomElementById('ypDrs');
		getDomElementById('loadNumber').disabled=true;
		var drsStatus=getDomElementById('drsStatus').value;
		var manifestDrsTypeDom=getDomElementById('manifestDrsType');
		var consgTypeDom=getDomElementById('consignmentType');
		if(ypDrsDom!=null){
			ypDrsDom.disabled=true;
		}
		if(drsStatus == getDrsUpdatedStatus()){
			var fsInTime = getDomElementById('fsInTime');
			if(fsInTime!=null){
				fsInTime.setAttribute("readOnly",true);
				fsInTime.setAttribute("tabindex","-1");
			}
		}
		
		if(consgTypeDom!=null){
			consgTypeDom.disabled=true;
		}
		if(manifestDrsTypeDom!=null){
			/** for Manual DRS*/
			manifestDrsTypeDom.disabled=true;
		}
		getDomElementById("Print").disabled=true;
		disableFsInOutTime();
	}
}
/**
 * setFocusOnDrsFor :: on page load it force cursor to focus on DRS-FOR if it's new page
 */
function setFocusOnDrsFor(){
	if(isNewDrs()){
		var drsForDom= getDomElementById('drsFor');
		setFocusOnDom(drsForDom);
	}
}

/**
 * showSuccessAftersave::it do message alerts/flag sets after save
 * @param drsToObject
 */
function showSuccessAftersave(drsToObject){
	try{
		alert(drsToObject.successMessage);
	}catch(e){
		alert(e);
	}

	if(isNewDrs()){
		getDomElementById("deliveryId").value=drsToObject.deliveryId;
		getDomElementById('drsNumber').value=drsToObject.drsNumber;
		//getDomElementById('fsOutTime').value=drsToObject.fsOutTimeStr;
		getDomElementById('fsOutTimeDateStr').value=drsToObject.fsOutTimeDateStr;
		//getDomElementById('fsOutTimeMinStr').value=drsToObject.fsOutTimeMinStr;
		getDomElementById('fsOutTimeHHStr').value=drsToObject.fsOutTimeHHStr;
		getDomElementById('fsOutTimeMMStr').value=drsToObject.fsOutTimeMMStr;
	}
	disabeldButtonsAfterSave();
}

function disabeldButtonsAfterSave(){
	 disableAll();
	 enableGlobalButton("Cancel");
	 setFocusOnDomId('Print');
	// enableGlobalButton("Modify");
	 //enableGlobalButton("Discard");
	 enableGlobalButton("Print");
	 /** for modify functionality*/
	// disableHeaderForPreparation();
}
function disableAfterUpdate(){
	disableAll();
	enableGlobalButton("Cancel");
	enableGlobalButton("Print");
}
/**
 * clearScreen
 */
function clearScreen(){
	if(promptConfirmation('Do you want to Clear the screen?')){
		globalFormSubmit(pageLoadUrl,formId);
	}
}
function clearScreenWithoutConfirmation(){
		globalFormSubmit(pageLoadUrl,formId);
}



function getNonDeliveryReasons(rowId){
	if(isNull(nonDeliveryReasons)){
		var url='./prepNormDoxDrs.do?submitName=ajaxGetNonDeliveryReasons';
		ajaxJqueryWithRow(url,formId,ajxRespForNonDeliveryReasons,rowId);
	}
}
function ajxRespForNonDeliveryReasons(rspObject,rowId){
	if(!isNull(rspObject)){
		nonDeliveryReasons = rspObject;
		count++;
		populatePendingReasons(rowId);
	}else{
		alert("Non-Delivery Reason details does not exist");
	}

}
/**
 * 
 * @param rowId
 */
function populatePendingReasons(rowId){
	var reasonRow="rowPendingReasonId"+rowId;
	if(isNull(nonDeliveryReasons)){
		if(!isDrsClosed()){
			getNonDeliveryReasons(rowId);
		}
		
		if(!isNull(nonDeliveryReasons)){
			createDropDown(reasonRow,nonDeliveryReasons);
		}
	}else{
		createDropDown(reasonRow,nonDeliveryReasons);
	}
}
/**
 * navigateToDrsPage
 * @param url
 */
function navigateToDrsPage(url){
	if(!isNull(url)){
		window.location=url;
	}
}
/**
 * addRowAfterResponse & add new row if user get  success responce
 * @param rowId
 * @param tableId
 */
function addRowAfterResponse(rowId,tableId){
	if(rowId!=null ){
		var cn=getDomElementById("rowConsignmentNumber"+rowId);
		var attmptNumber=getDomElementById("rowAttemptNumber"+rowId);
		var dlvDtlsDom = getDomElementById("rowDeliveryDetailId"+rowId);
		var cnListdom=document.getElementsByName("to.rowConsignmentNumber");
		var rowsLength=null;
		if(!isNull(cnListdom)){
			rowsLength= cnListdom.length;
		}
		if(!isNull(rowsLength)&& rowsLength >0){
			var lastId=rowsLength-1;
			var lastRowId =getRowId(cnListdom[lastId],'rowConsignmentNumber');
			if(!isNull(cn.value)&& !isNull(cnListdom[lastId].value) && rowId == lastRowId ){
				if(rowsLength == maxAllowedRows ){
					if(!isMaxAllowdRowsVldtd){
						if(getDrsScreenCode()==getDrsPendingScreenCode()){
							alert("This DRS has been prepared for only "+maxAllowedRows+" consigments");
						}else{
							alert("Maximum CN number allowed per DRS No. is :"+maxAllowedRows);
						}

						isMaxAllowdRowsVldtd=true;
					}
					/** set  a flag ,to make sure that alert the message only one time*/
					
					return false;
				}
				//make sure current row consgId and last  row Consid is not null
				if((!isConsgNumNull(rowId)&&!isConsigmentIdNull(rowId))|| (!isNull(attmptNumber)&& !isNull(attmptNumber.value))||(!isNull(dlvDtlsDom)&& !isNull(dlvDtlsDom.value))){
					addRow();
				}
			}

		}

	}
}
function isEligibleToAddRow(rowId){
	addRowAfterResponse(rowId,tableId);
}

function isConsigmentIdNull(rowId){
	var consgIdDom =getDomElementById("rowConsignmentId"+rowId);
	if(!isNull(consgIdDom) && !isNull(consgIdDom.value)){
		return false;
	}
	return true;
}
function isConsgNumNull(rowId){
	var consgNumDom =getDomElementById("rowConsignmentNumber"+rowId);
	if(!isNull(consgNumDom) && !isNull(consgNumDom.value)){
		return false;
	}
	return true;
}
/**
 * getReasonForMissedCard
 * @returns
 */
function getReasonForMissedCard(){
	return getDomElementById("reasonsForMisCard").value;
}

/**
 * getSelectedReasonCode
 * @param rowId
 * @returns str
 */
function getSelectedReasonCode(rowId){
	var reasonDom=getDomElementById("rowPendingReasonId"+rowId);
	var reason=getSelectedDropDownTextByDOM(reasonDom);
	var seriesArray= reason.split("-");
	return seriesArray[0];
}
/**
 * isMissedCardNumRequired : it takes the Pending Reason as input and validate it 
 * @param rowId
 * @returns {Boolean}
 */
function isMissedCardNumRequired(rowId){
	var reasons=getReasonForMissedCard();
	if(isNull(reasons)){
		alert(" please add Reason code(s)(for which Missedcard entry required) Config-params table");
		return false;
	}
	var actualReasons= convertStringToArray(reasons);
	 var selectedRsn=getSelectedReasonCode(rowId);
	 var isExistIn = isExistInArray(selectedRsn, actualReasons);
		if(isExistIn<0){
			return false;
		}
		return true;
}
/**
 * enableMissedCardNumberFor valid Reasons Only
 * @param rowId
 */
function enableMissedCardNumber(rowId){
	var missedCardDom = getDomElementById("rowMissedCardNumber"+rowId);
	var remarksDom = getDomElementById("rowRemarks"+rowId);
	if(isMissedCardNumRequired(rowId)){
		if(missedCardDom!=null){
			missedCardDom.readOnly=false;
			missedCardDom.removeAttribute("tabindex");
			setFocusOnDom(missedCardDom);
			remarksDom.readOnly=false;
			remarksDom.removeAttribute("tabindex");
		}
	}else{
		if(missedCardDom!=null ){
			missedCardDom.setAttribute("readOnly",true);
			missedCardDom.setAttribute("tabindex","-1");
			remarksDom.setAttribute("readOnly",true);
			remarksDom.setAttribute("tabindex","-1");
		}
	}
}
/**
 * getModeOfPaymentType 
 * @returns
 */
function getModeOfPaymntTypeCheque(){
	return getDomElementById("modeOfPaymentCheque").value;
}

/**
 * @returns ModeOfPaymntType Cash
 */
function getModeOfPaymntTypeCash(){
	return getDomElementById("modeOfPaymentCash").value;
}
/**
 * getHomeDlvType : from Constant file
 * @returns
 */
function getOfficeDlvType(){
	return getDomElementById("dlvTypeOffice").value;
}
/**
 * getNonDlvType :from Constant file
 * @returns
 */
function getNonDlvType(){
	return getDomElementById("dlvTypeNonDlv").value;
}
/**
 * 
 * @param rowId
 * @returns {Boolean}
 */
function enableForDlvType(rowId){
	var dlvTypeDom = getDomElementById("rowDeliveryType"+rowId);
	var comSealDom = getDomElementById("rowCompanySealSign"+rowId);
	var contactNumberDom = getDomElementById("rowContactNumber"+rowId);
	var recvNameDom = getDomElementById("rowReceiverName"+rowId);
	var consgNumberDom = getDomElementById("rowConsignmentNumber"+rowId);
	var modeOfPaymntDom = getDomElementById("rowModeOfPayment"+rowId);
	
	var product=getCnoteProduct(consgNumberDom.value);
	/** Business Rules : 1. Seal or Sign of the Company (if Delivery Type is Office Delivery)*/
	/** Business Rules : 2.ContactNumber& ReceiverName :: Enabled only when Delivery Type is ‘Home Delivery’*/
	if(dlvTypeDom!=null){
		if(!isNull(dlvTypeDom.value)){

			/** validate whether user selected Non-Delivery, if selected then restrict it.*/
			if(dlvTypeDom.value == getNonDlvType()){
				alert("Please do not select Non-Delivery");
				dlvTypeDom.value="";
				/** Sets focus back to the Dlv Type Drop-Down*/
				setFocusOnDom(dlvTypeDom);
				return false;
			}

			/** if Delivery type is Office Delivery*/
			if(dlvTypeDom.value == getOfficeDlvType()){
				if(comSealDom!=null){

					//set company seal & sign as Default for Office Dlv
					comSealDom.value=getCompanySealAndSign();
					comSealDom.disabled=false;
					comSealDom.removeAttribute("tabindex");
					//FOR Manual Drs do not need to set this on key press event
					if(!isManualDrs()){
						comSealDom.setAttribute("onkeypress","focusOnNextRowOnEnter(event.keyCode,this,"+tableId+",'rowCompanySealSign','rowDeliveryTime')");
					}
					contactNumberDom.setAttribute("onkeypress","enterKeyNav('rowCompanySealSign"+rowId+"'\,event.keyCode)");
				}
				if(contactNumberDom!=null){
					/*** artf3011958 : DRS:Capturing receiver name*/
					//contactNumberDom.value="";
					//contactNumberDom.setAttribute("readOnly",true);
					//contactNumberDom.setAttribute("tabindex","-1");
				}
				if(recvNameDom!=null){
					/*** artf3011958 : DRS:Capturing receiver name*/
					//recvNameDom.value="";
					//recvNameDom.setAttribute("readOnly",true);
					//recvNameDom.setAttribute("tabindex","-1");
				}
			}else if(dlvTypeDom.value != getOfficeDlvType()){
				//need not to inspect Nondelivery again (as we did before)
				/** if Delivery type is not Office Delivery ie HOME delivery*/
				if(contactNumberDom!=null){
					contactNumberDom.value="";
					contactNumberDom.readOnly=false;
					contactNumberDom.removeAttribute("tabindex");
				}
				if(recvNameDom!=null){
					recvNameDom.value="";
					recvNameDom.readOnly=false;
					recvNameDom.removeAttribute("tabindex");
				}
				if(comSealDom!=null){
					comSealDom.value="";
					comSealDom.disabled=true;
					comSealDom.setAttribute("tabindex","-1");
					comSealDom.removeAttribute("onkeypress");
				}
				
				contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+rowId+"','rowContactNumber')");
				if(modeOfPaymntDom!=null && (product==getLseries() || product==getTseries() || product==getDseries())){
					if(!modeOfPaymntDom.disabled){
						if(isManualDrs()){
							contactNumberDom.setAttribute("onkeypress","enterKeyNav('rowModeOfPayment"+rowId+"'\,event.keyCode)");
							contactNumberDom.removeAttribute("onkeydown");
						}else{
							contactNumberDom.setAttribute("onkeydown","enterKeyNav('rowModeOfPayment"+rowId+"'\,event.keyCode)");
						}
					}else{
						if(isManualDrs()){
							contactNumberDom.setAttribute("onkeypress","enterKeyNav('rowModeOfPayment"+rowId+"'\,event.keyCode)");
							contactNumberDom.removeAttribute("onkeydown");
							//contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+rowId+"','rowContactNumber')");
						}else{
							contactNumberDom.setAttribute("onkeydown","enterKeyNav('rowModeOfPayment"+rowId+"'\,event.keyCode)");
						}
					}

				}else if(product==getQseries() || product==getCseries() ){
					if(product==getCseries()){
						var relationDom= getDomElementById("rowRelationId"+rowId);
						if(relationDom!=null){
							if(isManualDrs()){
								contactNumberDom.setAttribute("onkeypress","enterKeyNav('rowRelationId"+rowId+"'\,event.keyCode)");
								contactNumberDom.removeAttribute("onkeydown");
							}else{
								contactNumberDom.setAttribute("onkeydown","enterKeyNav('rowRelationId"+rowId+"'\,event.keyCode)");
							}
						}
						
					}else if (product==getQseries()){
						if(isManualDrs()){
						contactNumberDom.setAttribute("onkeydown","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+rowId+"','rowContactNumber')");
						}else{
							contactNumberDom.setAttribute("onkeypress","enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+rowId+"','rowContactNumber')");
						}
					}
				}
			}
		}else{
			/** if Delivery type is empty*/
			if(contactNumberDom!=null){
				contactNumberDom.value="";
				contactNumberDom.setAttribute("readOnly",true);
				contactNumberDom.setAttribute("tabindex","-1");
			}
			if(recvNameDom!=null){
				recvNameDom.value="";
				recvNameDom.setAttribute("readOnly",true);
				recvNameDom.setAttribute("tabindex","-1");
			}
			if(comSealDom!=null){
				comSealDom.disabled=true;
				comSealDom.value="";
				comSealDom.setAttribute("tabindex","-1");
				comSealDom.removeAttribute("onkeypress");
			}
		}
	}
}

function getCompanySealAndSign(){
	return getDomElementById('companySealAndSign').value;
}
/**
 * setCompnaySealForDlvType
 * @param rowId
 */
function setCompnaySealForDlvType(rowId){
	var dlvTypeDom = getDomElementById("rowDeliveryType"+rowId);
	var comSealDom = getDomElementById("rowCompanySealSign"+rowId);
	
	if(!isNull(dlvTypeDom) && !isNull(comSealDom) &&(isDrsOpend() || isDrsUpdated())){
		if(dlvTypeDom.value == getOfficeDlvType()&& isNull(comSealDom.value)){
			comSealDom.value=getCompanySealAndSign();
			comSealDom.disabled=false;
			comSealDom.removeAttribute("tabindex");
		}
	}
	
}
/**
 * focusOnDrsForUpdate
 */
function focusOnDrsForUpdate(){
	if(isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		setFocusOnDom(drsDom);
	}
}

/*function validateFSInTime(identifier){

	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	var fsOutTimeMinDom= getDomElementById('fsOutTimeMinStr');
	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	var fsInTimeMinDom= getDomElementById('fsInTimeMinStr');
	var fsInTimeImg = getDomElementById('fsInTimeImg');
	var fsOutTimeImg = getDomElementById('fsOutTimeImg');


	var fsOutTimeMins=null;
	var fsInTimeMins=null;
	var fsOutTimeDate =trimString(fsOutTimeDateDom.value);
	var fsInTimeDate =trimString(fsInTimeDateDom.value);
	
	
	if(isNull(fsInTimeDateDom.value)){
		alert("Please provide FS-IN time (date)");
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	//check whether Fs-In time is future date
	if(isFutureDateForDrs(fsInTimeDateDom,'fsInTimeMinStr')){
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	if(identifier =='date'){
		clearFsInMin();
		return false;
	}
	
	if(isNull(fsInTimeMinDom.value)){
		alert("Please Provides Fs-In time minutes");
		//fsInTimeMinDom.focus();
		setFocusOnDom(fsInTimeMinDom);
		return false;
	}
	
	
	//check whether Fs-In time is less than fs-out time
	var  fsInOutDateDiff=calculateDateDiff(fsOutTimeDate,fsInTimeDate);
	
	if(fsInOutDateDiff<0){
		alert("Fs-In date can not be greater than FS-Out Date");
		fsInTimeDateDom.value="";
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	
	if(!isNull(fsOutTimeDateDom)){
		if(isNull(fsOutTimeDateDom.value)){
			alert("Fs-Out Date should not be empty");
			fsInTimeDateDom.value="";
			fsInTimeMinDom.value="";
			setFocusOnDom(fsOutTimeImg);
			return false;
		}
		if(isNull(fsOutTimeMinDom.value)){
			alert("Fs-Out Time(Minutes) should not be empty");
			setFocusOnDom(fsOutTimeMinDom);
			return false;
		}
		fsOutTimeMins =trimString(fsOutTimeMinDom.value).split(":");

		if(!validateTime(fsOutTimeMinDom.value,'FS-OUT')){
			fsOutTimeMinDom.value="";
			//fsOutTimeMinDom.focus();
			setFocusOnDom(fsOutTimeMinDom);
			return false;
		}
	}else{
		alert("Please set FS-Out Time Fields");
		return false;
	}

	if(!isNull(fsInTimeDateDom)){
		fsInTimeMins =trimString(fsInTimeMinDom.value).split(":");

		if(!validateTime(fsInTimeMinDom.value,'FS-IN')){
			fsInTimeMinDom.value="";
			fsInTimeMinDom.focus();
			setFocusOnDom(fsInTimeMinDom);
			return false;
		}
	}else{
		alert("Please set FS-IN Time Fields");
		return false;
	}

	//check whether Fs-In time is equal fs-out time
	 if(fsInOutDateDiff==0){//same day Drs Update
		if(fsOutTimeMinDom.value == fsInTimeMinDom.value){
			alert("FS-Out time and FS-IN time can not be same");
			fsInTimeMinDom.value="";
			fsInTimeMinDom.focus();
			setFocusOnDom(fsInTimeMinDom);
			return false;
		}
		*//**validate fsOutTimeDom < fsInTimeDom*//*
		if (!(parseInt(fsOutTimeMins[0], 10) <= parseInt(fsInTimeMins[0], 10))) {
			alert("FS-In time can not be less than FS-Out time");
			fsInTimeMinDom.value="";
			fsInTimeMinDom.focus();
			setFocusOnDom(fsInTimeMinDom);
			return false;
		}else{
			if ((parseInt(fsOutTimeMins[0], 10)== parseInt(fsInTimeMins[0],10)) && (parseInt(fsOutTimeMins[1], 10)>= parseInt(fsInTimeMins[1],10) )) {
				alert("FS-In time can not be less than or equal to FS-Out time (Minutes)");
				fsInTimeMinDom.value="";
				fsInTimeMinDom.focus();
				setFocusOnDom(fsInTimeMinDom);
				return false;
			}
		}
		if(!validateFsTimeAndCurrentTime()){
			 return false;
		 }

	 }else {

		 //check whether given FS-In time (Min) & Current time
		 if(!validateFsTimeAndCurrentTime()){
			 return false;
		 }

	 }

	return true;
}*/


/**
 * validateTime
 * @param time
 * @param msg
 * @returns {Boolean}
 */
function validateTime(time,msg){
	var hours,minutes;
	var inTime=time.split(":");
	if(isNull(inTime) || inTime.length<2){
		alert("Please provide "+msg+" time in HH:MM format");
		return false;
	}
	hours=inTime[0];
	minutes=inTime[1];
	if(hours.length !=2 || minutes.length !=2){
		alert("Please provide "+msg+" time in HH:MM format");
		return false;
	}
	if(isNaN(hours) || isNaN(minutes)){
		alert("Please provide  valid "+msg+" time ");
		return false;
	}
	/**validate  Hours */
	if (!(parseInt(hours, 10) >= 0 && parseInt(hours, 10) < 24)) {
		alert(" Hours should be in between 0-23");
		return false;
	}
	/**validate minutes*/
	if (!(parseInt(minutes, 10) >= 0 && parseInt(minutes, 10) < 60)) {
		alert("Minutes should be in between 0-59");
		return false;
	}
	return true;
	
}
/**BR:
 * 10.	The Delivery time should 
 * be greater than out time of FS and less than the at the time of FS.
 * */


function validateFsTimeAndCurrentTime(){
	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	//var fsInTimeMinDom= getDomElementById('fsInTimeMinStr');
	
	var fsInTimeHHDom= getDomElementById('fsInTimeHHStr');
	var fsInTimeMMDom= getDomElementById('fsInTimeMMStr');
	
	var fsInTimeDate = trimString(fsInTimeDateDom.value);
	//var fsInTimeMin = trimString(fsInTimeMinDom.value);
	
	var fsInTimeHH = trimString(fsInTimeHHDom.value);
	var fsInTimeMM = trimString(fsInTimeMMDom.value);
	
	var currentTime= trimString(getDomElementById('currentTimeStr').value);
	var currentDate= trimString(getDomElementById('currentDateStr').value);
	
	var fsInCurrentDateDiff=calculateDateDiff(fsInTimeDate,currentDate);
	var curTime =trimString(currentTime).split(":");
	//var fsInTimeMins=trimString(fsInTimeMin).split(":");
	
	if(fsInCurrentDateDiff ==0){
		//Compare current date & FS-IN time
		/** validate Hours validate fsCurrentTime < fsInTime*/
		if ((parseInt(curTime[0], 10) < parseInt(fsInTimeHH, 10))) {
			alert("FS-In time can not be greater than current time (HOURS)");
			fsInTimeHHDom.value="";
			fsInTimeMMDom.value="";
			//fsInTimeHHDom.focus();
			setFocusOnDom(fsInTimeHHDom);
			return false;
		}
		/**validate fsCurrentTimeHOURS == fsInTime HOURS THEN COMPARE MINUTES*/
		if ((parseInt(curTime[0], 10)== parseInt(fsInTimeHH,10)) && (parseInt(curTime[1], 10)< parseInt(fsInTimeMM,10)) ) {
			alert("FS-In time can not be greater than current time (MINUTES)");
			fsInTimeMMDom.value="";
			fsInTimeHHDom.value="";
			//fsInTimeMMDom.focus();
			setFocusOnDom(fsInTimeHHDom);
			return false;
		}
	}
	
	return true;
}
function clearDeliveryTimeMinutes(rowId){
	var dlvTimeInMMDom= getDomElementById('rowDeliveryTimeInMM'+rowId);
	if(dlvTimeInMMDom!=null){
		dlvTimeInMMDom.value='';
	}
}
function validateDeliveryTime(rowId){
	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	
	var fsOutTimeHHDom = getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');

	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	
	var fsInTimeHHDom = getDomElementById('fsInTimeHHStr');
	var fsInTimeMMDom =getDomElementById('fsInTimeMMStr');

	var fsInTimeDate = trimString(fsInTimeDateDom.value);
	
	var fsInTimeHH = trimString(fsInTimeHHDom.value);
	var fsInTimeMM = trimString(fsInTimeMMDom.value);

	var fsOutTimeDate= trimString(fsOutTimeDateDom.value);
	
	var fsOutTimeHH= trimString(fsOutTimeHHDom.value);
	var fsOutTimeMM= trimString(fsOutTimeMMDom.value);

	var dlvTimeInHHDom= getDomElementById('rowDeliveryTimeInHH'+rowId);
	var dlvTimeInMMDom= getDomElementById('rowDeliveryTimeInMM'+rowId);

	var currentTime= trimString(getDomElementById('currentTimeStr').value);
	var currentDate= trimString(getDomElementById('currentDateStr').value);



	if(fsInTimeDateDom == null || isNull(fsInTimeDateDom.value)){
		alert("Please provide FS-IN time (Date) ");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		//dlvTimeDom.value="";
		setFocusOnDom(fsInTimeDateDom);
		return false;
	}
	if(fsInTimeHHDom == null || isNull(fsInTimeHHDom.value)){
		alert("Please provide FS-IN time (MIN) ");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		//dlvTimeDom.value="";
		setFocusOnDom(fsInTimeHHDom);
		return false;
	}
	if(dlvTimeInHHDom!=null && dlvTimeInMMDom!=null){
		if(isNull(dlvTimeInHHDom.value)&&isNull(dlvTimeInMMDom.value)){
			return false;
		}

		var dlvTimeHH= trimString(dlvTimeInHHDom.value);
		var dlvTimeMM= trimString(dlvTimeInMMDom.value);
		var dlvTime= dlvTimeHH +":"+dlvTimeMM;
		/**validate Delivery Time*/
		if(!validateTime(dlvTime,'Delivery ')){
			dlvTimeInHHDom.value="";
			dlvTimeInMMDom.value="";
			//dlvTimeDom.value="";
			setFocusOnDom(dlvTimeInHHDom);
			return false;
		}

		/**Apply BR for DLV time  FS-Out time <=DLVTime <=FS-IN time*/

		
		var curTime=currentTime.split(":");

		if(!validateFsTimeAndCurrentTime()){
			return false;
		}

		//check updated date(if already) and with the current date
		var  fsInCurrentDateDiff=calculateDateDiff(fsInTimeDate,currentDate);
		var  fsInOutDateDiff=calculateDateDiff(fsOutTimeDate,fsInTimeDate);
		//if upddateDiff >0 then Updated  in Pending DRS & Updated for Delivered DRS not on same date
		if(fsInOutDateDiff == 0 && !validateDlvTimeForDate(rowId)){
			return false;
		}
		if(fsInOutDateDiff == 1){
			var isValidTime=false;

			//Fs out time should be in between 23 :00 hrs OR less than FS-In time
			if((parseInt(dlvTimeInHHDom.value, 10)<= parseInt("23", 10) && parseInt(dlvTimeInHHDom.value, 10) >= parseInt(fsOutTimeHH,10)) ||( parseInt(dlvTimeInHHDom.value,10)>=parseInt("00",10) && parseInt(dlvTimeInHHDom.value,10)<=parseInt(fsInTimeHH,10)) ) {
				isValidTime=true;
			}
			if (!isValidTime) {
				alert("Delivery time should be between Fs-out time and FS-In time");
				dlvTimeInHHDom.value="";
				dlvTimeInMMDom.value="";

				//dlvTimeDom.value="";
				setFocusOnDom(dlvTimeInHHDom);
				return false;
			}

		
			if (isValidTime) {
				if(parseInt(dlvTimeInHHDom.value, 10)<= parseInt("23", 10) && parseInt(dlvTimeInHHDom.value, 10) >= parseInt(fsOutTimeHH,10)){
					if((parseInt(dlvTimeInHHDom.value, 10) == parseInt(fsOutTimeHH,10)  && parseInt(dlvTimeInMMDom.value, 10) < parseInt(fsOutTimeMM,10) )){
						alert("Delivery time(Min) should be between Fs-out time and FS-In time");
						//dlvTimeDom.value="";
						dlvTimeInHHDom.value="";
						dlvTimeInMMDom.value="";
						setFocusOnDom(dlvTimeInHHDom);
						return false;
					}
				}else if(parseInt(dlvTimeInHHDom.value,10)>=parseInt("00",10) && parseInt(dlvTimeInHHDom.value,10)<=parseInt(fsInTimeHH,10)){
					if((parseInt(dlvTimeInHHDom.value, 10) == parseInt(fsInTimeHH,10)  && parseInt(dlvTimeInMMDom.value, 10) > parseInt(fsInTimeMM,10) )){
						alert("Delivery time(Min) should not be greater than FS-In time");
						//dlvTimeDom.value="";
						dlvTimeInHHDom.value="";
						dlvTimeInMMDom.value="";

						setFocusOnDom(dlvTimeInHHDom);
						return false;
					}
				}
			}
		}
		if (fsInOutDateDiff ==0 && fsInCurrentDateDiff ==0){
			//if upddateDiff ==0 Updated  in Pending DRS & Updated for Delivered DRS on same date

			/**validate FS IN time >=Delivery Time*/
			if ((parseInt(dlvTimeInHHDom.value, 10) > parseInt(fsInTimeHH, 10))) {
				alert("Delivery time can not be greater than FS-In time(Hours)");
				//dlvTimeDom.value="";
				dlvTimeInHHDom.value="";
				dlvTimeInMMDom.value="";
				setFocusOnDom(dlvTimeInHHDom);
				return false;
			}
			/**if FS IN time hours == Delivery Time hours then validate minutes*/
			if ((parseInt(dlvTimeInHHDom.value, 10) == parseInt(fsInTimeHH, 10)) && (parseInt(dlvTimeInMMDom.value, 10) > parseInt(fsInTimeMM, 10))) {
				/**if FS IN time minutes > Delivery Time Minutes */
				alert("Delivery time  Minutes can not be greater than FS-In time (minutes)");
				//dlvTimeDom.value="";
				dlvTimeInHHDom.value="";
				dlvTimeInMMDom.value="";
				setFocusOnDom(dlvTimeInHHDom);
				return false;

			}
			/**validate DLV  time >=Current Time*/

			if ((parseInt(dlvTimeInHHDom.value, 10) > parseInt(curTime[0], 10))) {
				alert("Delivery time can not be greater than Current time(Hours)");
				//dlvTimeDom.value="";
				dlvTimeInHHDom.value="";
				dlvTimeInMMDom.value="";
				setFocusOnDom(dlvTimeInHHDom);
				return false;
			}else if ((parseInt(dlvTimeInHHDom.value, 10) == parseInt(curTime[0], 10)) && (parseInt(dlvTimeInMMDom.value, 10) > parseInt(curTime[1], 10))) {
				/**if FS IN time minutes > Delivery Time Minutes */
				alert("Delivery time  Minutes can not be greater than Current time (minutes)");
				//dlvTimeDom.value="";
				dlvTimeInHHDom.value="";
				dlvTimeInMMDom.value="";
				setFocusOnDom(dlvTimeInHHDom);
				return false;

			}
		}
		if(fsInOutDateDiff == 0  && fsInCurrentDateDiff ==0 && !validateDlvTimeForDate(rowId)){
			return false;
		}

	}else{
		alert("not able find Delivery Time Field ");
		return false;
	}
	return true;
}



/*function validateDlvTimeForDate(dlvTime,fsIn,fsOut,dlvDom){
	
	var currentTime= trimString(getDomElementById('currentTimeStr').value);
	var currentDate= trimString(getDomElementById('currentDateStr').value);
	
	var dlvTimeDom=dlvDom;
	if ((parseInt(dlvTime[0], 10) > parseInt(fsIn[0], 10)) || (parseInt(dlvTime[0], 10) < parseInt(fsOut[0], 10))) {
		alert("Delivery time Should be in between FS-Out and FS-In time");
		dlvTimeDom.value="";
		setFocusOnDom(dlvTimeDom);
		return false;
	}	
	//compare FS-Out time  and Dlv time ie DLV time > FS-Out time
	if ((parseInt(dlvTime[0], 10) < parseInt(fsOut[0], 10))) {
		alert("Delivery time can not be less than than FS-Out time(Hours)");
		dlvTimeDom.value="";
		setFocusOnDom(dlvTimeDom);
		return false;
	}
	if ((parseInt(dlvTime[0], 10) == parseInt(fsOut[0], 10))&& (parseInt(dlvTime[1], 10) < parseInt(fsOut[1], 10))) {
		alert("Delivery time can not be less than than FS-Out time (Minutes)");
		dlvTimeDom.value="";
		setFocusOnDom(dlvTimeDom);
		return false;
	}
	if ((parseInt(dlvTime[0], 10) > parseInt(fsIn[0], 10))) {
		alert("Delivery time can not be greater than than FS-In time(Hours)");
		dlvTimeDom.value="";
		setFocusOnDom(dlvTimeDom);
		return false;
	}
	
	if ((parseInt(dlvTime[0], 10) == parseInt(fsIn[0], 10))&& (parseInt(dlvTime[1], 10) > parseInt(fsIn[1], 10))) {
		alert("Delivery time can not be greater than than FS-IN time (Minutes)");
		dlvTimeDom.value="";
		setFocusOnDom(dlvTimeDom);
		return false;
	}
	
	
return true;
}*/
/**
 * discardDrs :: discard/soft delete/scrap/making inactive  existing DRS Details
 */
function discardDrs(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		if(isNull(drsDom.value)){
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if(isNull(dicardDrsUrl)){
			alert("Please initialise Discard DRS Url in your javascript");
			return false;
		}
		if(isNull(formId)){
			alert("Please initialise Form Id in your javascript");
			return false;
		}
		if(promptConfirmation('Do you want to Discard the DRS Details?')){
			enableAll();
		globalFormSubmit(dicardDrsUrl,formId);
		}
		
	}else{
		alert("Please find the details ");
		return false;
	}
}

/**
 * modifyDrs :: it's a global method across the module(s) and 
 * it's mandatory to have a method name :ajaxResponseAfterModify in the .js file in which modifyDrs is called (from modify button).
 * @returns {Boolean}
 */
function modifyDrs(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		if(isNull(drsDom.value)){
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		var drsForDom= getDomElementById('drsFor');
		var drPartyTypeDom= getDomElementById('drsPartyId');
		
		
		if(isNull(drsForDom.value)){
			alert("Please select DRS-For details");
			setFocusOnDom(drsForDom);
			return false;
		}
		if(isNull(drPartyTypeDom.value)){
			alert("Please select "+getSelectedDropDownTextByDOM(drsForDom)+" details");
			setFocusOnDom(drPartyTypeDom);
			return false;
		}
		if(isNull(modifyDrsUrl)){
			alert("Please initialise Modify DRS Url in your javascript");
			return false;
		}
		if(promptConfirmation('Do you want to Modify the DRS Details?')){
			enableAll();
			ajaxJquery(modifyDrsUrl,formId,ajaxResponseAfterModify);
		}
	}else{
		alert("Please find the details ");
		return false;
	}
	
}
/**
 * getQseries
 * @returns
 */
function getQseries(){
	return getDomElementById('seriesQ').value;
}
/**
 * getCseries
 * @returns
 */
function getCseries(){
	return getDomElementById('seriesC').value;
}
/**
 * getLseries
 * @returns
 */
function getLseries(){
	return getDomElementById('seriesL').value;
}
/**
 * getDseries
 * @returns
 */
function getDseries(){
	return getDomElementById('seriesD').value;
}
/**
 * getTseries
 * @returns
 */
function getTseries(){
	return getDomElementById('seriesT').value;
}
/**
 * clearGridForYpDrs:: when user modifies the YP drs Dropdown it'll clear the Grid
 */
function clearGridForYpDrs(){
	//alert("i")
		$('#'+tableId).dataTable().fnClearTable();
		rowCount = 1;
		isDeleted=false;
		// adds one default row
		addRow();
}
function clearTableGrid(){
	//alert("i")
		$('#'+tableId).dataTable().fnClearTable();
}
/**
 * populateContentAndPaperwork
 * @param consgDetails
 */
function populateContentAndPaperwork(consgDetails,rowId){
	var contents= consgDetails.cnContents;
	var cntDom = getDomElementById("rowContentId"+rowId);
	if(!isNull(contents)&& !isNull(cntDom)){
		getDomElementById("rowContentName"+rowId).value=contents.cnContentName;
		cntDom.value==contents.cnContentId;
	}
	var paperwrks= consgDetails.cnPaperWorks;
	var ppwrkDom = getDomElementById("rowPaperworkId"+rowId);
	if(!isNull(paperwrks)&& !isNull(ppwrkDom)){
		getDomElementById("rowPaperworkName"+rowId).value=paperwrks.cnPaperWorkName;
		ppwrkDom.value=paperwrks.cnPaperWorkId;
	}
}
/**
 * getMaxRowsForDrs : from config param table
 * @returns
 */
function getMaxRowsForDrs(){
	return getDomElementById("maxAllowedRows").value;
}
/**
 * setMaxRowsForDrs  for Each screen from config params table
 */
function setMaxRowsForDrs(){
	var configRows=getMaxRowsForDrs();
	if(!isNull(configRows)){
		maxAllowedRows=parseIntNumber(configRows);
	}
}
/**
 * setFocusOnFsIntime
 */
function setFocusOnFsIntime(){
	var fsInTimeImg = getDomElementById('fsInTimeImg');
	var fsOutTimeImg = getDomElementById('fsOutTimeImg');
	if(!isNewDrs ()&& (isDrsOpend() || isDrsUpdated())){
		var fsInTimeDom = getDomElementById('fsInTimeDateStr');
		if(!isNull(fsInTimeDom)&& !isNull(fsInTimeImg) && isNull(fsInTimeDom.value)){
			setFocusOnDom(fsInTimeImg);
		}
	}else{
		var drsForDom = getDomElementById('drsFor');
		var drsTypeDom = getDomElementById('manifestDrsType');
		if(drsTypeDom!=null && isNull(drsTypeDom.value)){
			setFocusOnDom(drsTypeDom);
		}else if(drsForDom!=null && isNull(drsForDom.value)){
				setFocusOnDom(drsForDom);
			}
	}
	if(!isDrsOpend()){
		fsInTimeImg.style.display='none';
	}
	if(fsOutTimeImg!=null && (!isNewDrs())){
		fsOutTimeImg.style.display='none';
	}
}

function focusForManualDrsOnStartup(){
	if(isNewDrs() && isManualDrs()){
		var consgNumberDomList = document.getElementsByName("to.rowConsignmentNumber");
		if(consgNumberDomList!=null && consgNumberDomList.length==1 && !consgNumberDomList[0].readOnly)
		setFocusOnFsIntime();
	}
}

/**
 * getDeliveryStatus
 * @returns
 */
function getDeliveryStatus(){
	return getDomElementById('deliveredStatus').value;
}
/**
 * getPendingStatus
 * @returns
 */
function getPendingStatus(){
	
	return getDomElementById('pendingStatus').value;
}
function focusOnNextRowOnEnter(keyCode,domElement,tableId,currentElmId,elementId){
	 if(enterKeyNavWithoutFocus(keyCode)){
		 var curRcount= getRowId(domElement,currentElmId);
		 curRcount=parseNumber(curRcount);
		var  nxtRid=curRcount+1;
		 var nextDom=getDomElementById(elementId+nxtRid);
		 if(!isNull(nextDom)){
			 setFocusOnDom(nextDom);
		 }
	 }
}


function focusOnNextRowOnEnterForDrsPrep(keyCode,domElement,tableId,currentElmId,elementId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var domByName = document.getElementsByName(domElement.name);
		if(domByName!=null && domByName.length>0){
			for(var i=0;i<domByName.length;i++){
				if(domByName[i]==domElement){
					//alert("Eql"+domElement.value)
					if(i == domByName.length-1){
						setFocusOnDomId('Save');
					}else if((i+1)<domByName.length){
						var j=i+1;
						setFocusOnDom(domByName[j]);
					}
				}
			}

		}

	}
}
/**
 * parseNumber
 * @param num
 * @returns parse integer
 */
function parseNumber(num){
	var inpNum =(isNull(num)?0:num);
	return parseInt(inpNum,10);
}

function validateContactNumber(domElement){
	if(!isValidContactNumber(domElement.value)){
		domElement.value="";
		setFocusOnDom(domElement);
		return false;
	}else{
		return true;
	}
}
function isValidContactNumber(phoneNumber){
		if(isNull(phoneNumber)) {
			alert("Contact number can not be empty");
			return false;
		}

		//Expression for numeric values 
		 var objRegExp  = /(^\d{9,11}$)/;//size 9 10 11

		 if(objRegExp.test(phoneNumber)) {
			return true;
		 } else {
			 alert("Contact number length should be in between 9 to 11 digits only");
			 return false; 
		 }	
	
}
function isFutureDateByServerDate(selectedDate,todayDate) {

	 var date = trimString(selectedDate);
	 if (date == "")
		 return false;

	 date = date.split("/");
	 var myDate = new Date();
	 myDate.setFullYear(date[2], date[1] - 1, date[0]);
	 ///

	 var currdate = trimString(todayDate);
	 if (currdate == "")
		 return false;

	 currdate = currdate.split("/");
	 var newDate = new Date();
	 newDate.setFullYear(currdate[2], currdate[1] - 1, currdate[0]);

	 if (myDate > newDate) {
		 return true;
	 }
	 return false;
}
function isFutureDateForDrs(selectedDom,domId){
	 var dateDom= selectedDom;
	 var nextDom=getDomElementById(domId);
	 var serverDate= getDomElementById('currentDateStr');
	 if(isNull(serverDate) && isNull(serverDate.value)){
		 alert("Server date is empty, please set the server date");
		 return false;
	 }
	 if(!isNull(dateDom) && isFutureDateByServerDate(dateDom.value,serverDate.value)){
		 dateDom.value="";
		 setFocusOnDom(dateDom);
		 alert("Date can not be future date");
		 //setTimeout(function(){dateDom.focus();},10);
		 return true;
	 }else{
		 if(!isNull(nextDom)){
			 //nextDom.value="";
			 setFocusOnDom(nextDom);
			 return false;
		 }
	 }
	 return false;
}
/**
 * populateParentChildCnType
 * @param consgDetails
 * @param rowId
 */
function populateParentChildCnType(consgDetails,rowId){
	if(isNull(consgDetails)){
		return;
	}
	var cnType=getDomElementById("rowParentChildCnType"+rowId);
	if(!isNull(cnType)){
		cnType.value=consgDetails.parentChildCnType;
	}
}

function isValidToAddRowsForMDRS(){
	var canAddRows=getDomElementById('canCreateRows');
	if(canAddRows!=null && !isNull(canAddRows.value) && canAddRows.value==flagYes()){
		return true;
	}
	return false;
}
function disableFsInOutTime(){
	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	var fsOutTimeHHDom =getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	
	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	
	var fsInTimeHHDom = getDomElementById('fsInTimeHHStr');
	var fsInTimeMMDom =getDomElementById('fsInTimeMMStr');
	
	if(fsOutTimeDateDom!=null && fsOutTimeHHDom!=null && fsInTimeDateDom!=null && fsInTimeHHDom!=null){
		var drsStatus=getDomElementById('drsStatus').value;
		if(drsStatus == getDrsUpdatedStatus() || isDrsClosed()){
			var fsInDateCal= getDomElementById('fsInDate');
			var fsOutDateCal= getDomElementById('fsOutDate');
			if(fsInDateCal!=null){
				fsInDateCal.style.display = "none";
			}
			if(fsOutDateCal!=null){
				fsOutDateCal.style.display = "none";
			}
			
			fsOutTimeHHDom.setAttribute("readOnly",true);
			fsOutTimeHHDom.setAttribute("tabindex","-1");
			fsOutTimeMMDom.setAttribute("readOnly",true);
			fsOutTimeMMDom.setAttribute("tabindex","-1");
			
			fsInTimeHHDom.setAttribute("readOnly",true);
			fsInTimeHHDom.setAttribute("tabindex","-1");
			fsInTimeMMDom.setAttribute("readOnly",true);
			fsInTimeMMDom.setAttribute("tabindex","-1");
			
		}
	}
}

/*function validateFsOutMinutes(fsOutTime){
	if(!isNull(fsOutTime.value)){
		var outTime=trimString(fsOutTime.value);
		if(!validateTime(outTime,'FS-Out')){
			fsOutTime.value="";
			setFocusOnDom(fsOutTime);
			return false;
		}
	}
	
}*/
function validateFsOutMinutes(){
	
	
	var fsOutTimeDateDom=getDomElementById('fsOutTimeDateStr');
	
	if(fsOutTimeDateDom!=null && isNull(fsOutTimeDateDom.value)){
		alert("Please provide FS-Out Date");
		setFocusOnDom(fsOutTimeDateDom);
		return false;
	}
	
	if(isFutureDateForDrs(fsOutTimeDateDom,'fsInTimeDateStr')){
		setFocusOnDom(fsOutTimeDateDom);
		return false;
	}
	
	var fsOutTimeHHDom =getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	
	var outTime = fsOutTimeHHDom.value+":"+fsOutTimeMMDom.value;
	
	
		if(!validateTime(outTime,'FS-Out')){
			fsOutTimeHHDom.value="";
			fsOutTimeMMDom.value='';
			setFocusOnDom(fsOutTimeHHDom);
			return false;
		}
	
	
}

/*function validateInOutTimeForManualDrs(){

	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	var fsOutTimeMinDom= getDomElementById('fsOutTimeMinStr');
	
	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	var fsInTimeMinDom= getDomElementById('fsInTimeMinStr');
	
	var currentTime= trimString(getDomElementById('currentTimeStr').value);
	var currentDate= trimString(getDomElementById('currentDateStr').value);
	
	
	if(fsOutTimeDateDom!=null && fsInTimeDateDom!=null){
		
		if(isNull(fsOutTimeDateDom.value)){
			alert("Please provide FS-Out Date");
			fsOutTimeDateDom.value="";
			fsOutTimeDateDom.focus();
			return false;
		}
		if(isNull(fsInTimeDateDom.value)){
			alert("Please provide FS-IN Date");
			fsInTimeDateDom.value="";
			fsInTimeDateDom.focus();
			return false;
		}
		
		

		var fsOutTime=trimString(fsOutTimeMinDom.value);
		var fsInTime=trimString(fsInTimeMinDom.value);
		 currentTime=trimString(currentTime);
		if(isNull(fsInTime)){
			alert("Please provide FS-In time");
			fsInTimeDom.value="";
			fsInTimeDom.focus();
			return false;
		}
		if(isNull(fsOutTime)){
			alert(" FS-Out time can not be empty");
			fsInTimeDom.value="";
			fsInTimeDom.focus();
			return false;
		}

		var outTime=fsOutTime.split(":");
		var inTime=fsInTime.split(":");
		var curTime=currentTime.split(":");

		if(!validateTime(fsOutTime,'FS-Out')){
			fsInTimeDom.value="";
			fsInTimeDom.focus();
			return false;
		}
		if(!validateTime(fsInTime,'FS-IN')){
			fsInTimeDom.value="";
			fsInTimeDom.focus();
			return false;
		}
		
		var  dateDifference=calculateDateDiff(fsOutTimeDateDom.value,fsInTimeDateDom.value);
		
		if(parseInt(dateDifference)==0){
			*//** fs_in time and out time both are same*//*

			if(fsOutTime == fsInTime){
				alert("FS-Out time and FS-IN time can not be same");
				fsInTimeDom.value="";
				fsInTimeDom.focus();
				return false;
			}

			*//**validate fsOutTimeDom < fsInTimeDom*//*
			if (!(parseInt(outTime[0], 10) <= parseInt(inTime[0], 10))) {
				alert("FS-In time can not be less than FS-Out time");
				fsInTimeMinDom.value="";
				fsInTimeMinDom.focus();
				return false;
			}else{
				if ((parseInt(outTime[0], 10)== parseInt(inTime[0],10)) && (parseInt(outTime[1], 10)>= parseInt(inTime[1],10) )) {
					alert("FS-In time can not be less than or equal to FS-Out time (Minutes)");
					fsInTimeMinDom.value="";
					fsInTimeMinDom.focus();
					return false;
				}
			}

			
		}else if(dateDifference<0){
			*//** fs_in < FS out DATE*//*
			alert("FS-out Date can not be grater than FS-In Date");
			fsInTimeDateDom.focus();
			return false;
			
		}else{
			*//** fs_in > FS out DATE*//*
			//validating FS-In Time with Current Date
			var  dateDifference=calculateDateDiff(fsInTimeDateDom.value,currentDate);
			if(dateDifference ==0){
				*//**validate CurrentDate == fsInTimeDom*//*
				if ((parseInt(curTime[0], 10) < parseInt(inTime[0], 10))) {
					alert("FS-In Time(HH:MM) can not be more than Server time");
					fsInTimeMinDom.value="";
					fsInTimeMinDom.focus();
					return false;
				}else{
					if ((parseInt(curTime[0], 10)== parseInt(inTime[0],10)) && (parseInt(inTime[1], 10)> parseInt(curTime[1],10) )) {
						alert("FS-In time can not More than server time (Minutes)");
						fsInTimeMinDom.value="";
						fsInTimeMinDom.focus();
						return false;
					}
				}
				
			}
		}
	}
	
	return true;
}
function validateTimeForManualDrs(domElm,msg){
	
	if(isNull(domElm.value)){
		return false;
	}
	if(!validateTime(domElm.value,msg)){
		domElm.focus();
	}
}*/

function isManualDrs(){
	var isMdrs=false;
	var  manifestDrsType =getDomElementById('manifestDrsType');
	if(manifestDrsType!=null){
		isMdrs=true;
	}
	return isMdrs;
}
function isThirdPartyManifestDrs(){
	var isMdrs=false;
	var  manfstDrsType =getDomElementById('manifestDrsType');
	if(manfstDrsType!=null){
		var isDisabld=false;
		if(manfstDrsType.disabled){
			isDisabld=true;
			manfstDrsType.disabled=false;
		}
		if(manfstDrsType.value==getThirdPartyMnfstType() || manfstDrsType.value==getManualDrsType()){
			isMdrs=true;
		}
		if(isDisabld){
			manfstDrsType.disabled=true;
		}
	}
	return isMdrs;
}

function validateMandatoryForManualDrsCNScan(){
	
	var drsDom= getDomElementById('drsNumber');
	var  manifestDrsType =getDomElementById('manifestDrsType');
	var  cnType =getDomElementById('consignmentType');

	
	if(isNull(manifestDrsType.value)){
		alert("Please Provide DRS Type");
		setFocusOnDom(manifestDrsType);
		return false;
	}
	if(isNull(cnType.value)){
		alert("Please Provide Consignment Type ");
		setFocusOnDom(cnType);
		return false;
	}
	if(isNull(drsDom.value)){
		alert("Please provide DRS number");
		drsDom.value="";
		setFocusOnDom(drsDom);
		return false;
	}
	return true;
}
function getDrsScreenCode(){
	return getDomElementById('drsScreenCode').value;
}
function getDrsPendingScreenCode(){
	return getDomElementById('pendingDrsScreenCode').value;
}
/**
 * populateGridForManualDrs 
 * @param deliveryDetlTO
 * @param rowId
 */
function populateGridForManualDrs(consgDetails,rowId){
	getDomElementById("rowOriginCityId"+rowId).value=consgDetails.cityId;
	getDomElementById("rowOriginCityCode"+rowId).value=consgDetails.cityCode;
	var cityName=getDomElementById("rowOriginCityName"+rowId);
	if(!isNull(cityName)){
		cityName.value=consgDetails.cityName;
	}
	getDomElementById("rowAttemptNumber"+rowId).value=consgDetails.attemptNumber;
	getDomElementById("rowConsignmentId"+rowId).value=consgDetails.consgId;
	populateParentChildCnType(consgDetails,rowId);
	var isPaymentReqDom=getDomElementById("rowIsPaymentAlreadyCaptured"+rowId);
	if(isPaymentReqDom!=null){
		isPaymentReqDom.value=consgDetails.isPaymentAlreadyCaptured;
	}
}
function focusOnModifyAfterPrepSave(keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		setFocusOnDomId('Modify');
	}
}
function applyEventForOpenDrsForPrep(){
	if(isDrsOpend() && !isNewDrs()){
		setFocusOnDomId('drsFor');
		var drPartyTypeDom= getDomElementById('drsPartyId');
		drPartyTypeDom.setAttribute("onkeydown","return focusOnModifyAfterPrepSave(event.keyCode)");
	}
}
function clearFsInMin(){
	var fsInMinHHDom= getDomElementById('fsInTimeHHStr');
	var fsInMinMMDom = getDomElementById('fsInTimeMMStr');
	if(fsInMinHHDom!=null&&fsInMinMMDom!=null){
		fsInMinHHDom.value='';
		fsInMinMMDom.value='';
	}
}
function removeFsInEventsIfDrsNotOpnd(){
	if(!isDrsOpend()){
		var fsInTimeDateStr= getDomElementById('fsInTimeDateStr');
		if(fsInTimeDateStr!=null){
			fsInTimeDateStr.removeAttribute("onblur");
		}
		var fsInMinHHDom= getDomElementById('fsInTimeHHStr');
		var fsInMinMMDom = getDomElementById('fsInTimeMMStr');
		if(fsInMinHHDom!=null && fsInMinMMDom!=null){
			fsInMinHHDom.removeAttribute("onchange");
			fsInMinMMDom.removeAttribute("onchange");
		}
	}
}
function enterKeyNavForModeofPaymnt(rowId,keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		var chequeNoDom= getDomElementById("rowChequeNo"+rowId);
		var chequDateDom= getDomElementById("rowChequeDate"+rowId);
		var modeOfpayment= getDomElementById("rowModeOfPayment"+rowId);
		var bankNamAndBranchDom= getDomElementById("rowBankNameAndBranch"+rowId);
		if(isNull(modeOfpayment.value) || modeOfpayment.value == getModeOfPaymntTypeCash()){
			chequeNoDom.removeAttribute("onblur");
			if(isManualDrs() && modeOfpayment!=null){
				modeOfpayment.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
			}
			enterKeyNavforDrsUpdateContactNum(keyCode,rowId,'rowModeOfPayment');
		}else if(chequeNoDom!=null&& !chequeNoDom.readOnly){
			chequeNoDom.setAttribute("onblur","validateChequDDNumber(this)");
			if(isManualDrs() && bankNamAndBranchDom!=null){
				if(!isThirdPartyManifestDrs()){
					bankNamAndBranchDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
					chequDateDom.setAttribute("onkeypress","enterKeyNav('rowBankNameAndBranch"+rowId+"',event.keyCode)");
				}else{
					chequeNoDom.removeAttribute("onblur");
					chequeNoDom.setAttribute("onchange","validateChequDDNumber(this)");
				}
			}
			setFocusOnDom(chequeNoDom);
		}
	}
	
}
function enterKeyNavforDrsUpdateContactNum(keyCode,rowId,currentDomId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var currentDom=null;
		var currentDomList=null;
		if(currentDomId=='rowContactNumber'){
			currentDom=getDomElementById('rowContactNumber'+rowId);
			currentDomList=document.getElementsByName("to.rowContactNumber");
		}else if(currentDomId=='rowCompanySealSign'){
			currentDom=getDomElementById('rowCompanySealSign'+rowId);
			currentDomList=document.getElementsByName("to.rowCompanySealSign");
		}else if(currentDomId=='rowIdNumber'){
			currentDom=getDomElementById('rowIdNumber'+rowId);
			currentDomList=document.getElementsByName("to.rowIdNumber");
		}else if(currentDomId=='rowModeOfPayment'){
			currentDom=getDomElementById('rowModeOfPayment'+rowId);
			currentDomList=document.getElementsByName("to.rowModeOfPayment");
		}else if(currentDomId=='rowBankNameAndBranch'){
			currentDom=getDomElementById('rowBankNameAndBranch'+rowId);
			currentDomList=document.getElementsByName("to.rowBankNameAndBranch");
		}else if(currentDomId=='rowIdNumber' ){
			currentDom=getDomElementById('rowIdNumber'+rowId);
			currentDomList=document.getElementsByName("to.rowIdNumber");
		}else if(currentDomId=='rowRelationId' ){
			currentDom=getDomElementById('rowRelationId'+rowId);
			currentDomList=document.getElementsByName("to.rowRelationId");
		}
		
		var deliveryTypesDom=document.getElementsByName("to.rowDeliveryType");
		
		var deliveryIdDom=getDomElementById('rowDeliveryDetailId'+rowId);
		if(!isNull(deliveryIdDom) && isNull(deliveryIdDom.value)){
			deliveryTypesDom=document.getElementsByName("to.rowConsignmentNumber");
			if(isThirdPartyManifestDrs()){
				if(deliveryTypesDom[rowId]!=null && deliveryTypesDom[rowId].readOnly){
					deliveryTypesDom=document.getElementsByName("to.rowDeliveryType");
				}
			}
		}
		
		
		for(var i=0;i<currentDomList.length;i++){
			if(currentDomList[i]==currentDom){
				if(i == currentDomList.length-1){
					setFocusOnDomId('Save');
					break;
				}else if((i+1)<currentDomList.length){
					var j=i+1;
					var dlvTypeDom=deliveryTypesDom[j];
					if(!dlvTypeDom.disabled){
						setFocusOnDom(deliveryTypesDom[j]);
						break;
					}else{
						focusOnDeliveryType(j);
						break;
					}
				}
			}
		}
	}
}
function focusOnDeliveryType(indexId){
	var focusGained=false;
	var deliveryTypesDom=document.getElementsByName("to.rowDeliveryType");
	if(indexId<deliveryTypesDom.length){
		for(var i=(indexId+1);i<deliveryTypesDom.length;i++){
			if(!deliveryTypesDom[i].disabled){
				setFocusOnDom(deliveryTypesDom[i]);
				focusGained=true;
				break;
			}
		}
	}else{
		setFocusOnDomId('Save');
	}
	if(!focusGained){
		setFocusOnDomId('Save');
	}
}
function focusOnPendingDrs(keyCode,rowId){
	if(enterKeyNavWithoutFocus(keyCode)){
		if(isMissedCardNumRequired(rowId)){
			setFocusOnDomId ('rowMissedCardNumber'+rowId);
		}else{
			var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
			var reasonDomByName = document.getElementsByName("to.rowPendingReasonId");
			var cnDomByName = document.getElementsByName("to.rowConsignmentNumber");
			for(var i=0;i<reasonDomByName.length;i++){
				if(reasonDomByName[i]==reasonDom){
					//alert("Eql"+domElement.value)
					if(i == reasonDomByName.length-1){
						setFocusOnDomId('Save');
					}else if((i+1)<reasonDomByName.length){
						var j=i+1;
						setFocusOnDom(cnDomByName[j]);
					}
				}
			}


		}
	}
}
function enterKeyNavigationForPendingDrs(keyCode,rowId,domElemetId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var currentDomId=null;
		var currentDomByName =null;
		if(domElemetId =="rowPendingReasonId"){
			currentDomId = getDomElementById("rowPendingReasonId"+rowId);
			currentDomByName = document.getElementsByName("to.rowPendingReasonId");
		}else	if(domElemetId =="rowMissedCardNumber"){
			currentDomId = getDomElementById("rowMissedCardNumber"+rowId);
			currentDomByName = document.getElementsByName("to.rowMissedCardNumber");
		}else if(domElemetId =="rowRemarks"){
			currentDomId = getDomElementById("rowRemarks"+rowId);
			currentDomByName = document.getElementsByName("to.rowRemarks");
		}
		
		var cnDomByName = document.getElementsByName("to.rowConsignmentNumber");
		for(var i=0;i<currentDomByName!=null && currentDomByName.length;i++){
			if(currentDomByName[i]==currentDomId){
				//alert("Eql"+domElement.value)
				if(i == currentDomByName.length-1){
					setFocusOnDomId('Save');
					break;
				}else if((i+1)<currentDomByName.length){
					var j=i+1;
					setFocusOnDom(cnDomByName[j]);
					break;
				}
			}
		}


	}
}
function validateChequDDNumber(domElmnt){
	var rowId=getRowId(domElmnt,'rowChequeNo');
	var cash=getDomElementById("modeOfPaymentCash");
	var cheque=getDomElementById("modeOfPaymentCheque");
	var rowModeOfPayment = getDomElementById("rowModeOfPayment"+rowId);
	var chequeNoDom= getDomElementById("rowChequeNo"+rowId);
	var chequDateDom= getDomElementById("rowChequeDate"+rowId);
	if(!isNull(rowModeOfPayment) && !isNull(rowModeOfPayment.value) && rowModeOfPayment.value != cash.value && isNull(chequeNoDom.value)){
		if(isNull(chequeNoDom.value)){
			alert("Please enter "+getSelectedDropDownTextByDOM(rowModeOfPayment)+" Number");
			setFocusOnDom(chequeNoDom);
			return false;
		}
		
		
	}else if(!isNull(domElmnt.value)&& !isNull(rowModeOfPayment) && !isNull(cash)&& !isNull(cheque) && domElmnt.value != cash){
		var selectedValue=trimString(domElmnt.value);
		if(rowModeOfPayment.value == cheque.value ){
			if(selectedValue.length !=6){
				alert("Cheque number length should be 6 digits");
				domElmnt.value="";
				//domElmnt.focus();
				setFocusOnDom(domElmnt);
				return false;
			}
		}else if(rowModeOfPayment.value != cash.value ){
			if(selectedValue.length !=6){
				alert("DD number length should be 6 digits");
				domElmnt.value="";
				setFocusOnDom(domElmnt);
				return false;
			}
		}
	}
	if(!isNull(rowModeOfPayment) && !isNull(rowModeOfPayment.value) && rowModeOfPayment.value != cash.value){
		if(isNull(chequDateDom.value)){
			if(isThirdPartyManifestDrs()){
				alert("Please enter "+getSelectedDropDownTextByDOM(rowModeOfPayment)+" Date");
			}
			setFocusOnDom(chequDateDom);
			return false;
		}
	}
	
}
function clearDrsNumber(){
	var drsDom= getDomElementById('drsNumber');
	if(isNewDrs() && !isNull(drsDom.value)){
		drsDom.value="";
	}
}

function clearGridForHeaderChange(){
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var reasonDom=  document.getElementsByName("to.rowPendingReasonId");
	var missedCardNumDom= document.getElementsByName("to.rowMissedCardNumber");
	var originCityIdDom= document.getElementsByName("to.rowOriginCityId");
	var originCityCodeDom= document.getElementsByName("to.rowOriginCityCode");
	var originCityNameDom= document.getElementsByName("to.rowOriginCityName");
	var attemptNumberDom= document.getElementsByName("to.rowAttemptNumber");
	var consgIdDom= document.getElementsByName("to.rowConsignmentId");
	var parentChildDom= document.getElementsByName("to.rowParentChildCnType");
	var remarksDom= document.getElementsByName("to.rowRemarks");

	if(cNumber!=null && cNumber.length>=1 ){
		for(var i=0;i<cNumber.length;i++){
			cNumber[i].value="";
			reasonDom[i].value="";
			missedCardNumDom[i].value="";
			if(originCityIdDom!=null && originCityIdDom[i]!=null){
			originCityIdDom[i].value="";
			}
			if(originCityCodeDom!=null && originCityCodeDom.length>0 && originCityCodeDom[i]!=null){
			originCityCodeDom[i].value="";
			}
			if(originCityNameDom!=null && originCityNameDom.length>0 && originCityNameDom[i]!=null){
			originCityNameDom[i].value="";
			}
			if(attemptNumberDom!=null && attemptNumberDom[i]!=null){
			attemptNumberDom[i].value="";
			}
			consgIdDom[i].value="";
			parentChildDom[i].value="";
			remarksDom[i].value="";

		}
	}
	return false;
}
function isValidateForSaveManualDrs(){
	
	if(validateMandatoryForManualDrsCNScan()&& validateFSInTime('drs') && headerValidationsForSave()){
		return true;
	}else{
		return false;
	}
	return true;
}
function enableForModeOfPaymntChequeType(rowId){
	var modeOfPaymntTypeDom = getDomElementById("rowModeOfPayment"+rowId);
	var chequeNumberDom = getDomElementById("rowChequeNo"+rowId);
	var chequeDateDom = getDomElementById("rowChequeDate"+rowId);
	var bankNameDom= getDomElementById("rowBankNameAndBranch"+rowId);
	var calImgId= getDomElementById('calImg'+rowId);
	
	var isMDrs=isManualDrs();
	
	
	if(calImgId!=null){
		calImgId.style.display="inline";
	}
	if(modeOfPaymntTypeDom!=null){
		bankNameDom.setAttribute("onkeydown","return enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+rowId+"','rowBankNameAndBranch')");
		if(!isNull(modeOfPaymntTypeDom.value)){

			/** if MOde of paymnt is Cheque*/
			if(modeOfPaymntTypeDom.value == getModeOfPaymntTypeCash()){
				
				if(chequeNumberDom!=null){
					chequeNumberDom.value="";
					chequeNumberDom.setAttribute("readOnly",true);
					chequeNumberDom.setAttribute("tabindex","-1");
				}
				if(chequeDateDom!=null){
					chequeDateDom.value="";
					chequeDateDom.setAttribute("readOnly",true);
					chequeDateDom.setAttribute("tabindex","-1");
				}
				if(bankNameDom!=null){
					bankNameDom.value="";
					bankNameDom.setAttribute("readOnly",true);
					bankNameDom.setAttribute("tabindex","-1");
					bankNameDom.removeAttribute("onkeydown");
				}
				if(calImgId!=null){
					calImgId.style.display="none";
				}
				
			}else if(modeOfPaymntTypeDom.value!= getModeOfPaymntTypeCash()) {
				
				if(chequeNumberDom!=null){
					chequeNumberDom.value="";
					chequeNumberDom.readOnly=false;
					chequeNumberDom.removeAttribute("tabindex");
					chequeNumberDom.setAttribute("onblur","validateChequDDNumber(this)");
					
				}
				if(chequeDateDom!=null){
					chequeDateDom.setAttribute("readOnly",true);
					chequeDateDom.value="";
					chequeDateDom.removeAttribute("tabindex");
					if(isMDrs){
						chequeDateDom.setAttribute("onkeydown","enterKeyNav('rowBankNameAndBranch"+rowId+"',event.keyCode)");
						bankNameDom.setAttribute("onblur","isValidToAddNewRow('"+rowId+"')");
					}
				}
				if(bankNameDom!=null){
					bankNameDom.value="";
					bankNameDom.readOnly=false;
					bankNameDom.removeAttribute("tabindex");
				}
				
			}
		}else{
			/** if MOde of payment Type is empty*/
			calImgId.style.display="none";
			if(chequeNumberDom!=null){
				chequeNumberDom.value="";
				chequeNumberDom.setAttribute("readOnly",true);
				chequeNumberDom.setAttribute("tabindex","-1");
			}
			if(chequeDateDom!=null){
				chequeDateDom.value="";
				chequeDateDom.setAttribute("readOnly",true);
				chequeDateDom.setAttribute("tabindex","-1");
			}
			if(bankNameDom!=null){
				bankNameDom.value="";
				bankNameDom.setAttribute("readOnly",true);
				bankNameDom.setAttribute("tabindex","-1");
			}
			
		}
	}
}
function getThirdPartyMnfstType(){
	return getDomElementById('manifestDrsTypeMnfst').value;
}
function getManualDrsType(){
	return getDomElementById('manifestDrsTypeDrs').value;
}

function enableDisableForCnAjaxCall(isEnable){
	var  manifestDrsType =getDomElementById('manifestDrsType');
	var  cnType =getDomElementById('consignmentType');
	var partyTypeDom=getDomElementById('drsPartyId');

	manifestDrsType.disabled=isEnable;
	if(isEnable){
		manifestDrsType.setAttribute("tabindex","-1");
	}

	cnType.disabled=isEnable;
	if(isEnable){
		partyTypeDom.setAttribute("onkeydown","return enterKeyNav('fsOutTimeDateStr',event.keyCode);");
		cnType.setAttribute("tabindex","-1");
	}else{
		partyTypeDom.setAttribute("onkeydown","return enterKeyNav('consignmentType',event.keyCode);");
	}
}
function disabledForTPmanifest(){
	var manifestDrsTypeDom=getDomElementById('manifestDrsType');
	var drsDom= getDomElementById('drsNumber');
	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	var fsOutTimeHHDom = getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	var fsOutTimeImg=getDomElementById('fsOutTimeImg');
	
	if(manifestDrsTypeDom!=null && drsDom !=null && manifestDrsTypeDom.value==getThirdPartyMnfstType() && !isNull(drsDom.value)){
		fsOutTimeImg.style.display='none';
		
		
		fsOutTimeMMDom.setAttribute("readOnly",true);
		fsOutTimeMMDom.setAttribute("tabindex","-1");
		fsOutTimeMMDom.removeAttribute("onblur");
		
		fsOutTimeHHDom.setAttribute("readOnly",true);
		fsOutTimeHHDom.setAttribute("tabindex","-1");
		fsOutTimeHHDom.removeAttribute("onblur");
		
		fsOutTimeDateDom.removeAttribute("onblur");
		fsOutTimeDateDom.removeAttribute("onchange");
		manifestDrsTypeDom.disabled=true;
		getDomElementById('drsFor').disabled=true;
		getDomElementById('drsPartyId').disabled=true;
		getDomElementById('drsPartyId').disabled=true;
		getDomElementById('consignmentType').disabled=true;
		drsDom.setAttribute("readOnly",true);
	}
	
}
function clearHeaderForManualDrs(domelmnt){

	if(isNull(domelmnt.value)){
		var  cnType =getDomElementById('consignmentType');
		if(cnType!=null){
			cnType.value="";
		}
		//var  drsFor =getDomElementById('drsPartyId');
		var  drsFor =getDomElementById('drsFor');
		if(drsFor!=null){
			drsFor.value="";
			createEmptyDropDown('drsPartyId');
		}

	}else {
		if(domelmnt.value==getManualDrsType()){
			var drsDom= getDomElementById('drsNumber');
			drsDom.value=getMdrsAutoGeneratedNumber();
		}
	}
}

function checkIsAmountAlreadyPopulated(currentRowId,drsToDtls){
	var consgList=document.getElementsByName('to.rowConsignmentId');
	var currentCnid=getDomElementById("rowConsignmentId"+currentRowId).value;
	if(!isNull(consgList) && consgList.length>1){
		var occurance=0;
		for(var i=0;i<consgList.length;i++){
			var consgElment=consgList[i];
			var counterRowId=getRowId(consgElment,'rowConsignmentId');
			var consgId=consgElment.value;
			if(consgId==currentCnid){
				++occurance;
			}
			if(occurance==1 && currentRowId==counterRowId){
				codlcToAmountPopulate(currentRowId,drsToDtls);
				break;
			}else if(occurance>1){
				break;
			}

		}
	}else{
		codlcToAmountPopulate(currentRowId,drsToDtls);
	}
}
function codlcToAmountPopulate(rowId,consgDetails){
	
	/*We need to implement below BR::

		1.	L- series
		a.	Need to display only  COD Amount and same to be stamped in Collection
		2.	D- series
		a.	Need to Display only  LC Amount and same to be sampled  in Collection
		3.	T- series
		a.	If it has BA amount, then only BA amount to be shown in DRS and in collection 
		b.	If it has COD amount, then need to display only COD amount and same will be populated in collection
		c.	If it has COD amount and To-pay amount, same as (3.b)
		d.	If it has  only To-pay amount then then to-pay amount will be shown in DRS and in collection module
		4.	If L,T,D series have Other Charges/Additional charges  then this will be populated in DRS/Collection in addition to the above BR.
		(in otherwords, if any of the series has other charges/Addition charges/other Amount then this will be shown in DRS/Collection in any case)*/

	
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var product=null;
	if(consignmentNumber!=null ){
		product=getCnoteProduct(consignmentNumber.value);
	}
	if(!isNull(consgDetails.otherAmount)){
		getDomElementById("rowOtherCharges"+rowId).value=consgDetails.otherAmount;
	}
//	var drsbaAmount = !isNull(consgDetails.baAmount) ?consgDetails.baAmount:0.0;
//	drsbaAmount= parseFloat(drsbaAmount);
	if(!isNull(consgDetails.baAmount)){
		getDomElementById("rowBaAmount"+rowId).value=consgDetails.baAmount;
	}else{
		if(!isNull(product)){
			if(product==getLseries()){
				if(!isNull(consgDetails.codAmount)){
					getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;
				}
			}else if(product==getDseries()){
				if(!isNull(consgDetails.lcAmount)){
					getDomElementById("rowLCAmount"+rowId).value=consgDetails.lcAmount;
				}
			}else if(product==getTseries()){
				if(!isNull(consgDetails.codAmount)){
					getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;
				} else if(!isNull(consgDetails.toPayAmount)){
					getDomElementById("rowToPayAmount"+rowId).value=consgDetails.toPayAmount;
				}
			}

		}//end of product
	}//end else block 

}
function enablePaymentModeOnlyOnce(){
	
	if(isDrsClosed()){
		return false;
	}
	var consgList=document.getElementsByName('to.rowConsignmentId');
	
	var consgListInner=document.getElementsByName('to.rowConsignmentId');
	var modeOfPaymntTypeDom = document.getElementsByName("to.rowModeOfPayment");
	var chequeNumberDom = document.getElementsByName("to.rowChequeNo");
	var chequeDateDom = document.getElementsByName("to.rowChequeDate");
	var bankNameDom= document.getElementsByName("to.rowBankNameAndBranch");
	var calImgId= document.getElementsByName('calImg');
	var dlvTypeDomList=document.getElementsByName("to.rowDeliveryType");
	
	var contactNumberDom=document.getElementsByName("to.rowContactNumber");
	var isPaymentCaptured=document.getElementsByName("to.rowIsPaymentAlreadyCaptured");
	
	var compnySealSigDom=document.getElementsByName("to.rowCompanySealSign");

	for(var i=0;i<consgList.length;i++){
		if(dlvTypeDomList[i].disabled){
			continue;
		}
		//alert(i +"Row");

		var outerconsgElment=consgList[i];
		var outerConsgId=outerconsgElment.value;

		var counter=0;
		for(var j=i;j<consgListInner.length ;j++){
			var paymentCaptured=false;
			if(dlvTypeDomList[j].disabled || modeOfPaymntTypeDom[j].disabled ){
				continue;
			}
			var innerconsgElment=consgListInner[j];
			var innerConsgId=innerconsgElment.value;
			if(outerConsgId==innerConsgId){
				counter++;
			}
			if(isPaymentCaptured[j].value==flagYes()){
				paymentCaptured=true;
			}
			if((outerConsgId==innerConsgId && counter>1) || paymentCaptured){
				//alert("Disabling"+j);
				var currentRowid=getRowId(innerconsgElment,'rowConsignmentId');
				//chequeNumberDom[j].value="duplicate";
				contactNumberDom[j].setAttribute("onkeydown","return enterKeyNavforDrsUpdateContactNum(event.keyCode,'"+currentRowid+"','rowContactNumber')");
				modeOfPaymntTypeDom[j].value="";
				modeOfPaymntTypeDom[j].disabled=true;
				chequeNumberDom[j].readOnly=true;
				bankNameDom[j].readOnly=true;
				calImgId[j].style.display="none";
				if(isManualDrs()){
					var rowIdentifier=getRowId(contactNumberDom[j], 'rowContactNumber');
					contactNumberDom[j].setAttribute("onblur","isValidToAddNewRow('"+rowIdentifier+"')");
					contactNumberDom[j].setAttribute("onkeypress","enterKeyNavforDrsUpdateContactNum(event.keyCode,"+rowIdentifier+",'rowContactNumber')");
				}
			}
		}


	}

}


function validateFSInTime(identifier){

	var fsOutTimeDateDom= getDomElementById('fsOutTimeDateStr');
	//var fsOutTimeMinDom= getDomElementById('fsOutTimeMinStr');
	var fsInTimeDateDom= getDomElementById('fsInTimeDateStr');
	
	//var fsInTimeMinDom= getDomElementById('fsInTimeMinStr');
	var fsInTimeHHDom = getDomElementById('fsInTimeHHStr');
	var fsInTimeMMDom =getDomElementById('fsInTimeMMStr');
	
	var fsOutTimeHHDom = getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	
	var fsInTimeImg = getDomElementById('fsInTimeImg');
	var fsOutTimeImg = getDomElementById('fsOutTimeImg');


	var fsOutTimeDate =trimString(fsOutTimeDateDom.value);
	var fsInTimeDate =trimString(fsInTimeDateDom.value);
	
	
	if(isNull(fsInTimeDateDom.value)){
		alert("Please provide FS-IN time (date)");
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	//check whether Fs-In time is future date
	if(isFutureDateForDrs(fsInTimeDateDom,'fsInTimeHHStr')){
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	
	//check whether Fs-In time is less than fs-out time
	var  fsInOutDateDiff=calculateDateDiff(fsOutTimeDate,fsInTimeDate);
	
	if(fsInOutDateDiff<0){
		alert("Fs-In date can not be less than FS-Out Date");
		fsInTimeDateDom.value="";
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	
	if(identifier =='date'){
		clearFsInMin();//TO_DO
		return false;
	}
	
	
	if(isNull(fsInTimeHHDom.value)){
		alert("Please Provides FS-In time in HH format");
		//fsInTimeMinDom.focus();
		setFocusOnDom(fsInTimeHHDom);
		return false;
	}
	if(isNull(fsInTimeMMDom.value)){
		alert("Please Provides Fs-In time in MM format");
		//fsInTimeMinDom.focus();
		setFocusOnDom(fsInTimeMMDom);
		return false;
	}
	
	
	//check whether Fs-In time is less than fs-out time
	//var  fsInOutDateDiff=calculateDateDiff(fsOutTimeDate,fsInTimeDate);
	
	if(fsInOutDateDiff<0){
		alert("Fs-In date can not be less than FS-Out Date");
		fsInTimeDateDom.value="";
		setFocusOnDom(fsInTimeImg);
		return false;
	}
	
	if(!isNull(fsOutTimeDateDom)){
		if(isNull(fsOutTimeDateDom.value)){
			alert("Fs-Out Date should not be empty");
			fsInTimeDateDom.value="";
			fsInTimeMinDom.value="";
			setFocusOnDom(fsOutTimeImg);
			return false;
		}
		if(isNull(fsOutTimeHHDom.value)){
			alert("Fs-Out Time(Hours) should not be empty");
			setFocusOnDom(fsOutTimeHHDom);
			return false;
		}
		if(isNull(fsOutTimeMMDom.value)){
			alert("Fs-Out Time(Minutes) should not be empty");
			setFocusOnDom(fsOutTimeMMDom);
			return false;
		}
		//fsOutTimeMins =trimString(fsOutTimeMinDom.value).split(":");
		//fsOutTimeMins[]={fsInTimeHHDom.value,fsInTimeMMDom.value};

		if(!validateTime(fsOutTimeHHDom.value+':'+fsOutTimeMMDom.value,'FS-OUT')){
			fsOutTimeMinDom.value="";
			//fsOutTimeMinDom.focus();
			setFocusOnDom(fsOutTimeMinDom);
			return false;
		}
	}else{
		alert("Please set FS-Out Time Fields");
		return false;
	}

	if(!isNull(fsInTimeDateDom)){
		//fsInTimeMins =trimString(fsInTimeMinDom.value).split(":");
		
		fsInTimeMins = new Array(fsInTimeHHDom.value,fsInTimeMMDom.value);
		
		
		if(!validateTime(fsInTimeHHDom.value+':'+fsInTimeMMDom.value,'FS-IN')){
			
			fsInTimeHHDom.value="";
			//fsInTimeHHDom.focus();
			
			fsInTimeMMDom.value="";
			setFocusOnDom(fsInTimeHHDom);
			return false;
		}
	}else{
		alert("Please set FS-IN Time Fields");
		return false;
	}

	//check whether Fs-In time is equal fs-out time
	 if(fsInOutDateDiff==0){//same day Drs Update
		if(fsOutTimeHHDom.value == fsInTimeHHDom.value && fsOutTimeMMDom.value == fsInTimeMMDom.value){
			alert("FS-Out time and FS-IN time can not be same");
			fsInTimeHHDom.value="";
			fsInTimeMMDom.value="";
			setFocusOnDom(fsInTimeHHDom);
			return false;
		}
		/**validate fsOutTimeDom < fsInTimeDom*/
		if (!(parseInt(fsOutTimeHHDom.value, 10) <= parseInt(fsInTimeHHDom.value, 10))) {
			alert("FS-In time can not be less than FS-Out time");
			
			fsInTimeHHDom.value="";
			fsInTimeMMDom.value="";
			setFocusOnDom(fsInTimeHHDom);
			return false;
		}else{
			if ((parseInt(fsOutTimeHHDom.value, 10)== parseInt(fsInTimeHHDom.value,10)) && (parseInt(fsOutTimeMMDom.value, 10)>= parseInt(fsInTimeMMDom.value,10) )) {
				alert("FS-In time can not be less than or equal to FS-Out time (Minutes)");
				fsInTimeHHDom.value="";
				fsInTimeMMDom.value="";
				setFocusOnDom(fsInTimeHHDom);
				
				return false;
			}
		}
		if(!validateFsTimeAndCurrentTime()){
			 return false;
		 }

	 }else {

		 //check whether given FS-In time (Min) & Current time
		 if(!validateFsTimeAndCurrentTime()){
			 return false;
		 }

	 }

	 if(identifier =='time'){
		 var delvType=document.getElementsByName("to.rowDeliveryType");
		 var cNumber=document.getElementsByName("to.rowConsignmentNumber");
		 if(isRTOCOdDrs()){//for RTO COD DRS
			 setFocusOnFirstDeliveryTime();
		 }else{// for Others
			 if(delvType!=null && delvType.length>=1){
				 if(isManualDrs()){
					 //	alert("cNumber[0].readonly"+cNumber[0].readOnly);
					 if(!cNumber[0].readOnly){
						 setFocusOnDom(cNumber[0]);
					 }else{
						 focusOnDeliveryType(-1);
					 }
				 }else {
					 setFocusOnDom(delvType[0]);
				 }
			 }else{
				 if(cNumber!=null && cNumber.length>=1 && !cNumber[0].readOnly){
					 if(isPendingManualDrs()){
						 setFocusOnDom(cNumber[0]);
						 return true;
					 }else if(isThirdPartyManifestDrs()){
						 focusOnDeliveryType(-1);
						 return true;
					 }else{
						 setFocusOnDom(cNumber[0]);
						 return true;
					 }
				 }else{
					 if(isThirdPartyManifestDrs()){
						 focusOnDeliveryType(-1);
					 }
				 }
			 }
		 }
	 }
	return true;
}

function clearFsInTimMM(){
	
	var fsInTimeMMDom =getDomElementById('fsInTimeMMStr');
	fsInTimeMMDom.value="";
	
}

function validateDlvTimeForDate(rowId){
	
	var currentTime= trimString(getDomElementById('currentTimeStr').value);
	var currentDate= trimString(getDomElementById('currentDateStr').value);
	
	var fsInTimeHHDom = getDomElementById('fsInTimeHHStr');
	var fsInTimeMMDom =getDomElementById('fsInTimeMMStr');
	
	var fsOutTimeHHDom = getDomElementById('fsOutTimeHHStr');
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	
	var dlvTimeInHHDom= getDomElementById('rowDeliveryTimeInHH'+rowId);
	var dlvTimeInMMDom= getDomElementById('rowDeliveryTimeInMM'+rowId);
	
	
	if ((parseInt(dlvTimeInHHDom.value, 10) > parseInt(fsInTimeHHDom.value, 10)) || (parseInt(dlvTimeInHHDom.value, 10) < parseInt(fsOutTimeHHDom.value, 10))) {
		alert("Delivery time Should be in between FS-Out and FS-In time");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		//dlvTimeDom.value="";
		setFocusOnDom(dlvTimeInHHDom);
		return false;
	}	
	//compare FS-Out time  and Dlv time ie DLV time > FS-Out time
	if ((parseInt(dlvTimeInHHDom.value, 10) < parseInt(fsOutTimeHHDom, 10))) {
		alert("Delivery time can not be less than than FS-Out time(Hours)");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		
		//dlvTimeDom.value="";
		setFocusOnDom(dlvTimeInHHDom);
		return false;
	}
	if ((parseInt(dlvTimeInHHDom.value, 10) == parseInt(fsOutTimeHHDom.value, 10))&& (parseInt(dlvTimeInMMDom.value, 10) < parseInt(fsOutTimeMMDom.value, 10))) {
		alert("Delivery time can not be less than than FS-Out time (Minutes)");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		
		//dlvTimeDom.value="";
		setFocusOnDom(dlvTimeInHHDom);
		return false;
	}
	if ((parseInt(dlvTimeInHHDom.value, 10) > parseInt(fsInTimeHHDom.value, 10))) {
		alert("Delivery time can not be greater than than FS-In time(Hours)");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		//dlvTimeDom.value="";
		setFocusOnDom(dlvTimeInHHDom);
		return false;
	}
	
	if ((parseInt(dlvTimeInHHDom.value, 10) == parseInt(fsInTimeHHDom.value, 10))&& (parseInt(dlvTimeInMMDom.value, 10) > parseInt(fsInTimeMMDom.value, 10))) {
		alert("Delivery time can not be greater than than FS-IN time (Minutes)");
		dlvTimeInHHDom.value="";
		dlvTimeInMMDom.value="";
		//dlvTimeDom.value="";
		setFocusOnDom(dlvTimeInHHDom);
		return false;
	}
	
	
return true;
}

function clearFsOutTimMM(){
	
	var fsOutTimeMMDom =getDomElementById('fsOutTimeMMStr');
	fsOutTimeMMDom.value="";
	
}

/*function populateCodLcAmountForDrs(rowId,consgDetails){
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	
	var consgProduct=getCnoteProduct(consignmentNumber.value);
	
	if(!isNull(consgDetails.codAmount)){
		getDomElementById("rowCodAmount"+rowId).value=consgDetails.codAmount;
	}

	if(!isNull(consgDetails.lcAmount)){
		getDomElementById("rowLCAmount"+rowId).value=consgDetails.lcAmount;
	}

	if(!isNull(consgDetails.toPayAmount)){
		getDomElementById("rowToPayAmount"+rowId).value=consgDetails.toPayAmount;
	}
}*/
function populateCodLcAmountIfRequired(rowId,dlvDtls){

	var consgList=document.getElementsByName('to.rowConsignmentId');
	if(consgList!=null && consgList.length==1){
		//populateCodLcAmountForDrs(rowId,dlvDtls);
		codlcToAmountPopulate(rowId,dlvDtls);
		return true;
	}
	var currentConsgId= getDomElementById("rowConsignmentId"+rowId).value;
	var counter=0;
	for(var i=0;i<consgList.length;i++){
		var consgnId= consgList[i].value;
		if(isNull(consgnId)){
			continue;
		}
		if(consgnId==currentConsgId){
			counter++;
		}
		if(counter>=2){
			break;
		}
	}
	if(counter <=1){
		//populateCodLcAmountForDrs(rowId,dlvDtls);
		codlcToAmountPopulate(rowId,dlvDtls);
	}

}

function isPendingManualDrs(){
	var mpd=getDomElementById('manualpending');
	if(mpd!=null){
		return true;
	}
	return false;
}
function isRTOCOdDrs(){
	var mpd=getDomElementById('rtoCodDrs');
	if(mpd!=null){
		return true;
	}
	return false;
	
}
function setFocusOnFirstDeliveryTime(){
	var delvType=document.getElementsByName("to.rowDeliveryTimeInHH");
	if(delvType!=null && delvType.length>=1){
		setFocusOnDom(delvType[0]);
	}
}

function setFocusOnFirstDeliveryTimeOnEnterKey(keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		var delvType=document.getElementsByName("to.rowDeliveryTimeInHH");
		if(delvType!=null && delvType.length>=1){
			setFocusOnDom(delvType[0]);
		}
	}
}
function checkIsOctroiAmountAlreadyPopulated(currentRowId,drsToDtls){
	var consgList=document.getElementsByName('to.rowConsignmentId');
	var currentCnid=getDomElementById("rowConsignmentId"+currentRowId).value;
	if(!isNull(consgList) && consgList.length>1){
		var occurance=0;
		for(var i=0;i<consgList.length;i++){
			var consgElment=consgList[i];
			var counterRowId=getRowId(consgElment,'rowConsignmentId');
			var consgId=consgElment.value;
			if(consgId==currentCnid){
				++occurance;
			}
			if(occurance==1 && currentRowId==counterRowId){
				OctroiAmountPopulate(currentRowId,drsToDtls);
				break;
			}else if(occurance>1){
				break;
			}

		}
	}else{
		OctroiAmountPopulate(currentRowId,drsToDtls);
	}
}
function OctroiAmountPopulate(rowId,consgDetails){
	if(!isNull(consgDetails.otherAmount)){
		var amount=getDomElementById("rowAmount"+rowId);
		if(amount!=null){
			amount.value=consgDetails.otherAmount;
		}
	}
}

function disableDeliveredRowForDrs(rowId){
	var resultFlag=false;
	var modeOfPaymentDom = null;
	var chequeNODom =null;
	var chequeDateDom =null;
	var banknameAndBranchDom =null;
	var relationDom = null;
	var idProofDom = null;
	var idNumDom = null;

	var dlvStatusDom=document.getElementsByName("to.rowDeliveryStatus");
	if(dlvStatusDom!=null && !isNull(dlvStatusDom[rowId].value)&& dlvStatusDom[rowId].value == getDeliveryStatus()){
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var compnySealDom= document.getElementsByName("to.rowCompanySealSign");

		var product=getCnoteProduct(consgDom[rowId].value);

		if(product==getLseries() || product==getTseries() || product==getDseries()){
			modeOfPaymentDom= document.getElementsByName("to.rowModeOfPayment");
			chequeNODom= document.getElementsByName("to.rowChequeNo");
			chequeDateDom= document.getElementsByName("to.rowChequeDate");
			banknameAndBranchDom= document.getElementsByName("to.rowBankNameAndBranch");
		}
		if(product==getQseries() || product==getCseries()){
			relationDom= document.getElementsByName("to.rowRelationId");
			idProofDom= document.getElementsByName("to.rowIdentityProofId");
			idNumDom= document.getElementsByName("to.rowIdNumber");
		}
		
		if(deliveryTypeDom!=null){
			deliveryTypeDom[rowId].disabled=true;
		}
		
		if(deliveryTimeDomHH!=null){
			deliveryTimeDomHH[rowId].setAttribute("readOnly",true);
			deliveryTimeDomHH[rowId].setAttribute("tabindex","-1");

			deliveryTimeDomMM[rowId].setAttribute("readOnly",true);
			deliveryTimeDomMM[rowId].setAttribute("tabindex","-1");
		}
		if(recvNameDom!=null){
			recvNameDom[rowId].setAttribute("readOnly",true);
			recvNameDom[rowId].setAttribute("tabindex","-1");
		}
		if(contactNumDom!=null){
			contactNumDom[rowId].setAttribute("readOnly",true);
			contactNumDom[rowId].setAttribute("tabindex","-1");
		}
		
		if(compnySealDom!=null){
			compnySealDom[rowId].setAttribute("readOnly",true);
			compnySealDom[rowId].setAttribute("tabindex","-1");
		}
		if(relationDom!=null){
			relationDom[rowId].setAttribute("readOnly",true);
			relationDom[rowId].setAttribute("tabindex","-1");
		}
		if(idProofDom!=null){
			idProofDom[rowId].setAttribute("readOnly",true);
			idProofDom[rowId].setAttribute("tabindex","-1");
		}
		if(idNumDom!=null){
			idNumDom[rowId].setAttribute("readOnly",true);
			idNumDom[rowId].setAttribute("tabindex","-1");
		}
		
		if(chequeNODom!=null){
			chequeNODom[rowId].setAttribute("readOnly",true);
			chequeNODom[rowId].setAttribute("tabindex","-1");
		}
		if(banknameAndBranchDom!=null){
			banknameAndBranchDom[rowId].setAttribute("readOnly",true);
			banknameAndBranchDom[rowId].setAttribute("tabindex","-1");
		}
		if(chequeDateDom!=null){
			var calImgId= document.getElementsByName('calImg');
			chequeDateDom[rowId].setAttribute("readOnly",true);
			chequeDateDom[rowId].setAttribute("tabindex","-1");
			if(calImgId!=null){
				calImgId[rowId].style.display="none";
			}
		}
		if(modeOfPaymentDom!=null){
			modeOfPaymentDom[rowId].disabled=true;
			modeOfPaymentDom[rowId].setAttribute("tabindex","-1");
		}
		resultFlag=true;
	}
	return resultFlag;
}
/** added for Pending DRS/Manual pending drs*/
function setAddedRowCount(){
	var addedCount=$('#addedRowCount').val();
	if(!isNull(addedCount)){
		rowCount= parseInt(addedCount,10)+1;
	}
}
/**
 * getLoggedInBranchCode
 */
function getLoggedInBranchCode(){
	return getDomElementById("loggedInOfficeCode").value;
}
function getMdrsAutoGeneratedNumber(){
	return getDomElementById("autoGeneratedManualDrsNumber").value;
}


function validateConsignmentFormat(consgNumber){
	var cnNumber=trimString(consgNumber);
	var firstCharAt=cnNumber.charAt(0);
	if(!checkStartsWith(firstCharAt,"[A-Z]")){
		alert("Consignment number should starts with a Character value");
		return false;
	}else if(cnotelength()!=cnNumber.length){
		alert("Length the Cnote should be :"+cnotelength());
		return false;
	}
	return true;
}
