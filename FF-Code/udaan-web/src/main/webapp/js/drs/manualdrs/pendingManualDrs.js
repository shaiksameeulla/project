// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 * -------------
 *  Business Rules:
 * --------------
 *  
 *  5.	The Pending Reason Code field is a drop down entry and the format of the entries in
 *  	 the list should be ‘Code-Pending Reason’ (Eg: 110 –Door locked).
*	6.	Missed You Card No. is enabled/entered only 
*	when the Pending Reason is ‘Door Locked’ or Person not available
 * 
 * 
 * 
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdate';
/** refer drsCommon.js */
formId='pendingDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to save pending DRS ?";
/** for Clear screen */
pageLoadUrl= "./pendingManualDrs.do?submitName=viewPendingDrsPage";

/** mentioning no of rows (as 30) in illogical here , 
 * but no of rows should fetch from db ie no of child records for given a drs number to be set here  */
maxAllowedRows=30;
var isMissedCardVldated=false;

$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
						} );
				new FixedColumns( oTable, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
				/**Apply events to buttons*/
				applyButtonEvents();
				disableHeader();
				disableGrid();
				setAddedRowCount();
				addRowForPendingConsg();
				/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
				disableForUser();
				setMaxRowsForDrs();
				setFocusOnFsIntime();
				disabledForTPmanifest();
				
} );


/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){
	/** On click of the Delete button*/
	$('#Delete').click(function() {
		/** delete function call*/
		deletePendingDrsRow();
	});
	
	/** On click of the save button*/
	$('#Save').click(function() {
		/** updateUndeliveredDrsConsg*/
		saveUndeliveredDrsConsg();
	});
	
	$('#Cancel').click(function() {
		/** Clear screen  call*/
		clearScreen();
	});
}

function addRowForPendingConsg(){
	if(isValidToAddRowsForMDRS()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		addRow();
	}
}

/**
 * fnClickAddRow : for Add row functionality
 */
function addRow(){
	var tempRow=rowCount;
	try{
	$('#'+tableId).dataTable().fnAddData( [
	                                           '<input type="checkbox" id="checkbox'+ rowCount +'" name="chkbx" value="'+rowCount+'" tabindex="-1" />',
	                                           rowCount+"",
	                                           '<input type="text" id="rowConsignmentNumber'+ rowCount +'" name="to.rowConsignmentNumber" maxlength="12" onchange="validateConsignmentByDrsNumber(\''+ tempRow +'\')" class="txtbox width145" onkeypress = "return enterKeyNav(\'rowPendingReasonId'+ rowCount +'\',event.keyCode);" onblur="addNewPendingRow(\''+ tempRow +'\')"/>',
	                                           '<input type="text" id="rowOriginCityName'+ rowCount +'" name="to.rowOriginCityName" class="txtbox width140" readOnly="true" tabindex="-1"/> \
	                                           <input type="hidden" id="rowOriginCityId'+ rowCount +'" name="to.rowOriginCityId"/> \
	                                           <input type="hidden" id="rowConsignmentId'+ rowCount +'" name="to.rowConsignmentId"/> \
	                                           <input type="hidden" id="rowAttemptNumber'+ rowCount +'" name="to.rowAttemptNumber"/> \
	                                           <input type="hidden" id="rowOriginCityCode'+ rowCount +'" name="to.rowOriginCityCode" />',
	                                           '<select name="to.rowPendingReasonId" id="rowPendingReasonId'+ rowCount +'"  style="width: 100px" onchange = "clearMissedCardRem(\''+ tempRow +'\');validatePendingReason(\''+ tempRow +'\')" onblur="addNewPendingRow(\''+ tempRow +'\')" onkeydown = "return focusOnPendingDrs(event.keyCode,\''+ tempRow +'\');" class="selectBox"><option value="">--Select--</option></select>',
	                                           '<input type="text" id="rowMissedCardNumber'+ rowCount +'" name="to.rowMissedCardNumber" onchange = "validatePendingReason(\''+ tempRow +'\')" onkeypress = "return enterKeyNav(\'rowRemarks'+ rowCount +'\',event.keyCode);" onblur="addNewPendingRow(\''+ tempRow +'\')" class="txtbox width100" maxlength="25" readOnly="true" tabindex="-1"/> ',
	                                           '<input type="text" id="rowRemarks'+ rowCount +'" name="to.rowRemarks" class="txtbox width100" maxlength="30" tabindex="-1" onkeypress="return enterKeyNavigationForPendingDrs(event.keyCode,\''+rowCount+'\',\'rowRemarks\')"/> \
	                                           <input type="hidden" id="rowAlreadyAddedRow'+ rowCount +'" name="to.rowAlreadyAddedRow" /> \
	                                           <input type="hidden" id="rowParentChildCnType'+ rowCount +'" name="to.rowParentChildCnType" />',
	                                           ] );
	}catch(e){
		//alert(e);
	}
	rowCount++;
	/**inspecting Whether delete functionality 
	 * executed at least once. 
	 * if so start calling updateGrid method for updating serial numbers */
	if(isDeleted){
		updateGrid(tableId);
	}
	focusOnElementWithId('rowConsignmentNumber',tempRow);
	setCompnaySealForDlvType(tempRow);
	populatePendingReasons(tempRow);
	
	
}

/**
 * deleteNpDoxRow
 */
function deletePendingDrsRow(){
	if(isCheckBoxSelectedWithMessage('chkbx','Please select the check box to be deleted')){
		deleteRow(tableId,'chkbx');
	}
}
/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./pendingManualDrs.do?submitName=searchPendingConsignments";
		globalFormSubmit(url,formId);
	}
}
function disableHeader(){
	if(!isNewDrs()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		dropdownDisable();
		getDomElementById("Cancel").disabled=false;
		getDomElementById("Save").disabled=false;
		getDomElementById("Delete").disabled=true;
		if(!isDrsOpend()){
			disableFsInOutTime();
		}
	}
}
function disableGrid(){
	if(!isNewDrs()){
		/*var tableee = getDomElementById(tableId);
		var totalRowCount =  tableee.rows.length;*/
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var reasonDom=  document.getElementsByName("to.rowPendingReasonId");
		var missedCardNumDom= document.getElementsByName("to.rowMissedCardNumber");
		var remarksDom= document.getElementsByName("to.rowRemarks");
		
		for(var i=0;i<consgDom.length;i++) {
			if(consgDom==null || consgDom[i]==null){
				break;
			}
			consgDom[i].setAttribute("readOnly",true);
			consgDom[i].setAttribute("tabindex","-1");
			
			reasonDom[i].disabled=true;
			missedCardNumDom[i].setAttribute("readOnly",true);
			missedCardNumDom[i].setAttribute("tabindex","-1");
			
			remarksDom[i].setAttribute("readOnly",true);
			remarksDom[i].setAttribute("tabindex","-1");
		}
	}
}

function clearGridValuesForPending(rowId){
	getDomElementById("rowOriginCityId"+rowId).value="";
	getDomElementById("rowOriginCityCode"+rowId).value="";
	getDomElementById("rowOriginCityName"+rowId).value="";
	getDomElementById("rowConsignmentNumber"+rowId).value="";
	getDomElementById("rowAttemptNumber"+rowId).value="";
	getDomElementById("rowConsignmentId"+rowId).value="";
	getDomElementById("rowParentChildCnType"+rowId).value="";
	var missedCardDom = getDomElementById("rowMissedCardNumber"+rowId);
	if(missedCardDom!=null){
		missedCardDom.value="";
		missedCardDom.setAttribute("readOnly",true);
		missedCardDom.setAttribute("tabindex","-1");
	}
	getDomElementById("rowPendingReasonId"+rowId).value="";
	getDomElementById("rowRemarks"+rowId).value="";
}


function disableHeaderDetailsAfterCnValidations(){
	var drsDom= getDomElementById('drsNumber');
	var  manifestDrsType =getDomElementById('manifestDrsType');
	var  cnType =getDomElementById('consignmentType');
	
	
	drsDom.setAttribute("disabled",true);
	drsDom.setAttribute("tabindex","-1");
	
	manifestDrsType.setAttribute("disabled",true);
	manifestDrsType.setAttribute("tabindex","-1");
	
	cnType.setAttribute("disabled",true);
	cnType.setAttribute("tabindex","-1");
	
}

function disableForManifestDrs(){
	
	var  manifestDrsType =getDomElementById('manifestDrsType');
	var  manifestType =getDomElementById('manifestDrsTypeMnfst');
	
	if(manifestDrsType!=null && manifestType!=null && manifestType.value== manifestDrsType.value &&  isValidToAddRowsForMDRS()){
		manifestDrsType.setAttribute("readOnly",true);
		manifestDrsType.setAttribute("tabindex","-1");
		
		manifestType.setAttribute("readOnly",true);
		manifestType.setAttribute("tabindex","-1");
	}
	
}


function isValidateForSaveManualDrs(){
	
	if(validateMandatoryForManualDrsCNScan()&& validateFSInTime('drs') && headerValidationsForSave()){
		return true;
	}else{
		return false;
	}
	return true;
}
/**
 * validateConsignmentByDrsNumber
 * @param rId
 */
function validateConsignmentByDrsNumber(rId){
	var rowId=parseInt(rId);
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var drsDom= getDomElementById('drsNumber');
	
	if(isNull(consignmentNumber.value)){
		//alert("Please enter consignment number");
		clearGridValuesForPending(rId);
		setFocusOnDom(consignmentNumber);
		return;
	}
	
	if(cnotelength()!= getGivenCnLength(consignmentNumber.value)){
		alert("Invalid Consignment number length");
		consignmentNumber.value='';
		setFocusOnDom(consignmentNumber);
		return ;
	}
	
	
	/** verify if duplicate Consignment exist in the Grid*/
	if(checkAllowedConsignments(consignmentNumber)&& validateMandatoryForManualDrsCNScan() && checkDuplicateConsignments(consignmentNumber)){
		
		/** Ajax call to validate Consignment number by DRS number*/
		var url="./pendingManualDrs.do?submitName=ajaxValidateConsignmentByDrsNumberForManualDrs&consgNumber="+consignmentNumber.value+"&drsNumber="+drsDom.value;
		enableDisableForCnAjaxCall(false);
		ajaxJqueryWithRow(url,formId,ajaxResponseForConsignment,rId);
	}else{
		consignmentNumber.value="";
		clearGridValuesForPending(rId);
		setFocusOnDom(consignmentNumber);
		return;
	}
}
/**
 * ajaxResponseForConsignment
 * @param ajaxResp
 * @param rowId
 */
function ajaxResponseForConsignment(ajaxResp,rowId){
	//alert("ajaxResp"+ajaxResp);
	enableDisableForCnAjaxCall(true);
	var consgDetails=null;
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			clearGridValuesForPending(rowId);
			setFocusOnDom(consignmentNumber);
		}else{
			consgDetails=responseText;
			//populate Consignment details
			populateGridForManualDrs(consgDetails,rowId);
			//disableHeaderDetailsAfterCnValidations();
			setFocusOnDom(reasonDom);
		}
	}else{
		alert("Invalid response,please Re-try...");
		clearGridValuesForPending(rowId);
		setFocusOnDom(consignmentNumber);
	}

}

function clearMissedCardRem(rowId){
	var missedCardDom = getDomElementById("rowMissedCardNumber"+rowId);
	if(missedCardDom!=null){
		missedCardDom.value="";
		
	}
	getDomElementById("rowRemarks"+rowId).value="";
}

/**
 * updateUndeliveredDrsConsg 
 */
function saveUndeliveredDrsConsg(){
	if(isValidateForSaveManualDrs()&& validateGridValidations()&& isAtleastOneRowNewlyAdded() && promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./pendingManualDrs.do?submitName=saveUndeliveredDrs";
		ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}


function ajaxResponseAfterSave(ajaxResp){
		var drsToObject=null;
		if (!isNull(ajaxResp)) {
			var responseText =jsonJqueryParser(ajaxResp); 
			var error = responseText[ERROR_FLAG];
			if(responseText!=null && error!=null){
				alert(error);
			}else {
				disableGrid();
				getDomElementById("Save").disabled=true;
				drsToObject=responseText;
				alert(drsToObject.successMessage);
				var fsInTimeDom= getDomElementById('fsInTime');
				if(fsInTimeDom!=null){
					fsInTimeDom.setAttribute("readOnly",true);
					fsInTimeDom.setAttribute("tabindex","-1");
				}
				populatePendingmanualDrsAfterSave(drsToObject);
			}
		}
}
	
function populatePendingmanualDrsAfterSave(drsToObject){
		
		if(isNewDrs()){
			getDomElementById("deliveryId").value=drsToObject.deliveryId;
			var navLink= getDomElementById('navigatorlink');
			var linkUrl=drsToObject.updateDeliveredUrl;
			navLink.setAttribute("onclick",'navigateToDrsPage(\''+linkUrl+'\')');
		}
		disableAfterUpdate();
		
	}
function validateGridValidations(){
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var reasonDom=  document.getElementsByName("to.rowPendingReasonId");
	var missedCardNumDom= document.getElementsByName("to.rowMissedCardNumber");
//	var remarksDom= document.getElementsByName("to.rowRemarks");

	if(cNumber!=null && cNumber.length>=1 ){
		for(var i=0;i<cNumber.length;i++){
			var rId= getRowId(cNumber[i],'rowConsignmentNumber');
			if(i!=0 && i==cNumber.length-1 && isNull(cNumber[i].value)){
				continue;
			}
			 if(isNull(cNumber[i].value)){
				alert("Please provide consignment number at line :"+(i+1));
				cNumber[i].value="";
				setFocusOnDom(cNumber[i]);
				return false;
			}
			if(isNull(reasonDom[i].value)){
				alert("Please provide Pending Reasons at line :"+(i+1));
				reasonDom[i].value="";
				
				setFocusOnDom(reasonDom[i]);
				return false;
			}
			
			if(isMissedCardNumRequired(rId)){
				if(isNull(missedCardNumDom[i].value)){
					alert("Please provide Missed card number  at line :"+(i+1));
					missedCardNumDom[i].value="";
					setFocusOnDom(missedCardNumDom[i]);
					return false;
				}
			}
			
		}
	}else{
		alert("Please add atleast one consignment details");
		return false;
	}
	return true;
}


function validatePendingReason(rowId){
	enableMissedCardNumber(rowId);
	var missedCardDom= getDomElementById("rowMissedCardNumber"+rowId);
	var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
	if(missedCardDom!=null && !missedCardDom.readOnly && isNull(missedCardDom.value)){
		//alert("Please provide Missed you card number ");
		//setFocusOnDom(missedCardDom);
		setFocusOnDom(missedCardDom);
		return false;
	}else{
		if(!isNull(reasonDom.value)){
			addRowAfterResponse(rowId,tableId);
		}else{
			alert("Please select Pending Reason");
			setFocusOnDom(reasonDom);
			return false;
		}
	}
	
}
var globalMissCardCount=0;
function validateMandatoryForAdd(rowId){
	var consignmentNumber = getDomElementById("rowConsignmentNumber"+rowId);
	var reasonDom = getDomElementById("rowPendingReasonId"+rowId);
	
	var missedCardDom= getDomElementById("rowMissedCardNumber"+rowId);
	
	
	if(isNull(consignmentNumber.value)){
		return false;
	}
	if(isNull(reasonDom.value)){
		//alert("Please select Pending Reason");
		return false;
	}
	if(isMissedCardNumRequired(rowId)){
		if(isNull(missedCardDom.value)){
			globalMissCardCount++;
			if(globalMissCardCount==2){
				alert("Please provide missed card number");
				setFocusOnDom(missedCardDom);
			}
			missedCardDom.focus();
			return false;
		}
	}
	return true;
}

function addRowAfterResponseForMDrs(rowId,tableId){
	if(rowId!=null ){
		var cn=getDomElementById("rowConsignmentNumber"+rowId);
		var attmptNumber=getDomElementById("rowAttemptNumber"+rowId);
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
						alert("Maximum CN number allowed per DRS No. is :"+maxAllowedRows);
						isMaxAllowdRowsVldtd=true;
					}
					/** set  a flag ,to make sure that alert the message only one time*/
					
					return false;
				}
				//make sure current row consgId and last  row Consid is not null
				if((!isConsgNumNull(rowId)&& !isConsgNumNull(lastRowId) )&&(!isConsigmentIdNull(rowId) && !isConsigmentIdNull(lastRowId))|| (!isNull(attmptNumber)&& !isNull(attmptNumber.value))){
					addRow();
				}
			}

		}

	}
}

function addNewPendingRow(rowId){
	if(validateMandatoryForAdd(rowId)){
		addRowAfterResponseForMDrs(rowId,tableId);
	}
}

function find(){
	if(validationsForFind()){
		var url="./pendingManualDrs.do?submitName=searchPendingConsignments";
		globalFormSubmit(url,formId);
	}
}

function isAtleastOneRowNewlyAdded(){
var isValidFlag=false;
	if(!isNewDrs()){
		var cNumber=document.getElementsByName("to.rowConsignmentNumber");
		var newlyAdded=  document.getElementsByName("to.rowAlreadyAddedRow");
		if(cNumber!=null && cNumber.length>=1 ){
			for(var i=0;i<cNumber.length;i++){
				var newlyAddedRow=newlyAdded[i].value;
				if(isNull(newlyAddedRow) || newlyAddedRow==flagNo()){
					if(!isNull(cNumber[i].value)){
						isValidFlag=true;
						break;
					}
				}
			}
		}
	}else{
		isValidFlag=true;
	}
	if(!isValidFlag){
		alert("Please enter new consignment details for pending");
	}
	return isValidFlag;
}
