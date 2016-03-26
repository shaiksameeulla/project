// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdateRtoCod';
/** refer drsCommon.js */
formId='rtoCodDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to Update DRS ?";
/** for Clear screen */
pageLoadUrl= "./updateRtoCodDrs.do?submitName=viewUpdateDrsPage";
/** called on Load */
$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "205",
					"sScrollX": "100%",
					"sScrollXInner":"140%",
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
				load();
			} );

/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./updateRtoCodDrs.do?submitName=findDrsNumberForUpdate";
		globalFormSubmit(url,formId);
	}
}

function load(){
	if(!isNewDrs()){
		buttonDisabled('Print','disabled');
	}

	/**If on page load of Disable the entire header*/
	disableHeaderForUpdate();
	
	/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
	disableForUser();
	applyButtonEvents();
	setFocusOnFsIntime();
	focusOnDrsForUpdate();
	removeFsInEventsIfDrsNotOpnd();
	
} 

/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){

	/** On click of the save button*/
	$('#Save').click(function() {
		/** Save function call*/
		saveDrsDetails();
	});


	$('#Cancel').click(function() {
		clearScreen();
	});
	
	getDomElementById('fsInTimeMMStr').removeAttribute("onkeydown");
	
	$('#fsInTimeMMStr').keydown(function(event) {
		setFocusOnFirstDeliveryTimeOnEnterKey(event.keyCode);
	});
	
	
}



/**
 * Check mandatory for save
 */
function validateGridForSaveDrs(){
	if(!isNewDrs()){
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		
		var deliveryTimeDomHH=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeDomMM=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		var pendingReasonDom= document.getElementsByName("to.rowPendingReasonId");
		var remarksDom= document.getElementsByName("to.rowRemarks");
		var lineNum=null;
		for(var i=0;i<deliveryTypeDom.length;i++) {
			lineNum=" at Line :"+(i+1);
			
			if(isNull(deliveryTypeDom[i].value)){
				alert("Please provide Delivery Type "+lineNum);
				setFocusOnDom(deliveryTypeDom[i]);
				return false;
			}
			
			if(!isNull(deliveryTypeDom[i].value) && deliveryTypeDom[i].value == getPendingStatus()){
				if(isNull(pendingReasonDom[i].value)){
					alert("Please Select pending Reason "+lineNum);
					setFocusOnDom(pendingReasonDom[i]);
					return false;
				}
				deliveryTimeDomHH[i].value="";
				deliveryTimeDomMM[i].value="";
			}else{
				if(isNull(deliveryTimeDomHH[i].value)){
					alert("Please provide Delivery Time in HH "+lineNum);
					setFocusOnDom(deliveryTimeDomHH[i]);
					return false;
				}
				
				if(isNull(deliveryTimeDomMM[i].value)){
					alert("Please provide Delivery Time in MM "+lineNum);
					setFocusOnDom(deliveryTimeDomMM[i]);
					return false;
				}
				remarksDom[i].value='';
			}
			
		}
		return true;
	}else{
		alert("Please search the data for DRS Number");
	}
	return false;
}
/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(validateFSInTime() && validateGridForSaveDrs()&& promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./updateRtoCodDrs.do?submitName=updateDrs";
		//globalFormSubmit(url,formId);
		ajaxJquery(url,formId,ajaxResponseAfterSave);
	}
}
function ajaxResponseAfterSave(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	var drsToObject=null;
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}else {
			drsToObject=responseText;
			alert(drsToObject.successMessage);
			disableAfterUpdate();
			if(isConfirmedForAnotherUpdate()){
				clearScreenWithoutConfirmation();
			}
		}
	}
}
function enterKeynavForDlvTime(rowId,keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		var dlvStatusDom=getDomElementById("rowDeliveryType"+rowId);
		if(dlvStatusDom!=null&& !dlvStatusDom.readOnly){
			setFocusOnDom(dlvStatusDom);
		}
	}
	
}
function validateDeliveryStatus(rowId){
	var dlvStatusDom=getDomElementById("rowDeliveryType"+rowId);
	var reasonDom=getDomElementById("rowPendingReasonId"+rowId);
	var remarksDom=getDomElementById("rowRemarks"+rowId);
	var dlvTimHHDom=getDomElementById("rowDeliveryTimeInHH"+rowId);
	var dlvTimMMDom=getDomElementById("rowDeliveryTimeInMM"+rowId);
	if(!isNull(dlvStatusDom)){
		
		if(dlvStatusDom.value == getPendingStatus()){
			reasonDom.value="";
			reasonDom.disabled =false;
			remarksDom.value="";
			remarksDom.removeAttribute("tabindex");
			reasonDom.removeAttribute("tabindex");
			remarksDom.readOnly=false;
			dlvTimHHDom.value="";
			dlvTimMMDom.value="";
			
		}else{
			reasonDom.value="";
			reasonDom.disabled =true;
			remarksDom.value="";
			remarksDom.setAttribute("readOnly",true);
			remarksDom.setAttribute("tabindex","-1");
			reasonDom.setAttribute("tabindex","-1");
		}
	}
}
function cursorNavigationForRto(keyCode,rowId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var dlvStatusDom=getDomElementById("rowDeliveryType"+rowId);
		var reasonDom=getDomElementById("rowPendingReasonId"+rowId);
		var remarksDom=getDomElementById("rowRemarks"+rowId);
		
		reasonDom.removeAttribute("onkeypress");
		remarksDom.removeAttribute("onkeypress");
		
		if(isNull(dlvStatusDom.value)){
			focusOnNextRowForRtoDrsStatus(keyCode,rowId,'rowDeliveryType');
		}else if(dlvStatusDom.value == getPendingStatus()){
			
			reasonDom.setAttribute("onkeypress","enterKeyNav('rowRemarks"+rowId +"',event.keyCode)");
			remarksDom.setAttribute("onkeypress","focusOnNextRowForRtoDrsStatus(event.keyCode,'"+rowId+"','rowRemarks')");
			setFocusOnDom(reasonDom);
		}else{
			dlvStatusDom.setAttribute("onkeypress","focusOnNextRowForRtoDrsStatus(event.keyCode,'"+rowId+"','rowDeliveryType')");
			reasonDom.removeAttribute("onkeypress");
			remarksDom.removeAttribute("onkeypress");
			
		}
		
	}
}
function focusOnFirstRowDeliveryTime(keyCode){
	if(enterKeyNavWithoutFocus(keyCode)){
		setFocusOnFirstDeliveryTime();
	}
	
}

function focusOnNextRowForRtoDrsStatus(keyCode,rowId,domElementId){
	if(enterKeyNavWithoutFocus(keyCode)){
		var domElement=null;
		var nextDom=document.getElementsByName("to.rowDeliveryTimeInHH");
		if(domElementId=="rowRemarks"){
			domElement="to.rowRemarks";
		}else if(domElementId =="rowDeliveryType"){
			domElement="to.rowDeliveryType";
		}
		var currentDomList = document.getElementsByName(domElement);
		var currentDom = getDomElementById(domElementId+rowId);

		if(currentDomList!=null && currentDomList.length>0){
			for(var i=0;i<currentDomList.length;i++){
				if(currentDomList[i]==currentDom){
					//alert("Eql"+domElement.value)
					if(i == currentDomList.length-1){
						setFocusOnDomId('Save');
						break;
					}else if((i+1)<currentDomList.length){
						var j=i+1;
						setFocusOnDom(nextDom[j]);
						break;
					}
				}
			}

		}


	}

}