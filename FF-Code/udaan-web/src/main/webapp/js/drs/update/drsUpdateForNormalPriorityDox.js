// Start of Changes by <31913> on 01/04/2013
/**
 * Allows you to tag each variable supported by a function.
 */
var rowCount = 1;
/** refer drsCommon.js */
tableId='drsUpdateNpDox';
/** refer drsCommon.js */
formId='npDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to Update DRS ?";
/** for Clear screen */
pageLoadUrl= "./updateNormDoxDrs.do?submitName=viewUpdateDrsPage";
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
		var url="./updateNormDoxDrs.do?submitName=findDrsNumberForUpdate";
		globalFormSubmit(url,formId);
	}
}

function load(){
	if(!isNewDrs()){
		buttonDisabled('Print','disabled');
	}

	/**If on page load of Disable the entire header*/
	disableHeaderForUpdate();
	
	disableGrid();
	
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
}
/**
 * disableGrid :on load
 */
function disableGrid(){
	if(!isNewDrs()){
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeHHDom=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeMMDom=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var isFocusGained=false;
		//var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		for(var i=0;i<deliveryTypeDom.length;i++) {
			if(deliveryTypeDom==null || deliveryTypeDom[i]==null){
				break;
			}
			/** if it's  NON-delivery (i.e already updated in Pending drs)then 
			 * make that row as NON-Editable*/
			if(deliveryTypeDom[i].value==getNonDlvType()){
				recvNameDom[i].setAttribute("readOnly",true);
				recvNameDom[i].setAttribute("tabindex","-1");
				
				contactNumDom[i].setAttribute("readOnly",true);
				contactNumDom[i].setAttribute("tabindex","-1");
				
				//deliveryTimeDom[i].setAttribute("readOnly",true);
				//deliveryTimeDom[i].setAttribute("tabindex","-1");
				
				deliveryTimeHHDom[i].setAttribute("readOnly",true);
				deliveryTimeHHDom[i].setAttribute("tabindex","-1");
				
				deliveryTimeMMDom[i].setAttribute("readOnly",true);
				deliveryTimeMMDom[i].setAttribute("tabindex","-1");
				
				deliveryTypeDom[i].disabled=true;
				
			}else{
			
				if(!disableDeliveredRowForDrs(i)){
					
					/** set company seal & sign as default for Office Dlv*/
					setCompnaySealForDlvType((i+1));
					if(!isFocusGained && !isDrsClosed()){
						setFocusOnDom(deliveryTypeDom[i]);
						isFocusGained=true;
					}
				}
			}//end of For loop
			
		}
	}
}

/**
 * Check mandatory for save
 */
function validateGridForSaveDrs(){
	if(!isNewDrs()){
		var deliveryTypeDom=document.getElementsByName("to.rowDeliveryType");
		//var deliveryTimeDom=  document.getElementsByName("to.rowDeliveryTime");
		var deliveryTimeHHDom=  document.getElementsByName("to.rowDeliveryTimeInHH");
		var deliveryTimeMMDom=  document.getElementsByName("to.rowDeliveryTimeInMM");
		
		var recvNameDom= document.getElementsByName("to.rowReceiverName");
		var contactNumDom= document.getElementsByName("to.rowContactNumber");
		var compnySealDom= document.getElementsByName("to.rowCompanySealSign");
		for(var i=0;i<deliveryTypeDom.length;i++) {
			if(deliveryTypeDom==null || deliveryTypeDom[i]==null){
				break;
			}
			if(isNull(deliveryTypeDom[i].value)){
				alert("Please provide Delivery Type at line :"+(i+1));
				setFocusOnDom(deliveryTypeDom[i]);
				return false;
			}
			if(deliveryTypeDom[i].value!=getNonDlvType()){
				if(isNull(deliveryTimeHHDom[i].value)){
					alert("Please provide Delivery Time in HH at line :"+(i+1));
					setFocusOnDom(deliveryTimeHHDom[i]);
					return false;
				}else if(isNull(deliveryTimeMMDom[i].value)){
					alert("Please provide Delivery Time in MM at line :"+(i+1));
					//setFocusOnDom(deliveryTimeDom[i]);
					setFocusOnDom(deliveryTimeMMDom[i]);
					return false;
				}
			}
			/**  if  delivery type is Office delivery*/
			if(deliveryTypeDom[i].value ==getOfficeDlvType()){
				if(isNull(compnySealDom[i].value)){
					alert("Please provide Company Seal/Sign at line :"+(i+1));
					setFocusOnDom(compnySealDom[i]);
					return false;
				}

			}
			/**  if  delivery type is not non-delivery*/
			if(deliveryTypeDom[i].value!=getNonDlvType() && deliveryTypeDom[i].value!=getOfficeDlvType()){
				recvNameDom[i].value=trimString(recvNameDom[i].value);
				if(isNull(recvNameDom[i].value)){
					alert("Please provide Receiver Name at line :"+(i+1));
					setFocusOnDom(recvNameDom[i]);
					return false;
				}
				
				contactNumDom[i].value=trimString(contactNumDom[i].value);
				if(isNull(contactNumDom[i].value)){
					alert("Please provide Contact Number at line :"+(i+1));
					setFocusOnDom(contactNumDom[i]);
					return false;
				}
				
			}

		}
		return true;
	}
}
/**
 * saveDrsDetails
 */
function saveDrsDetails(){
	if(validateFSInTime() && validateGridForSaveDrs()&& promptConfirmation(savePromptMsg)){
		enableAll();
		var url="./updateNormDoxDrs.do?submitName=updateDrs";
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
		var recvNameDom= getDomElementById("rowReceiverName"+rowId);
		var compnySealDom= getDomElementById("rowCompanySealSign"+rowId);
		if(recvNameDom!=null&& !recvNameDom.readOnly){
			setFocusOnDom(recvNameDom);
		}
		if(recvNameDom!=null&& recvNameDom.readOnly){
			setFocusOnDom(compnySealDom);
		}
	}
	
}