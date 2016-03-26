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
formId='bulkCnPendingDrsForm';
/** refer drsCommon.js */
savePromptMsg="Do you want to update DRS ?";
/** for Clear screen */
pageLoadUrl= "./bulkCnPendingDrs.do?submitName=viewBulkPendingDrsPageForHub";

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
				
				/**DRS Details Loads from DB, & if requested user is not allowed to modify the details then it disables all fields*/
				disableForUser();
				
				
} );


/**
 * addButtonsEvents :: 
 *  Events to be applied to the Buttons ,it'll execute while user load the page
 */
function applyButtonEvents(){
	
	
	/** On click of the save button*/
	$('#Save').click(function() {
		/** saveUndeliveredDrsConsg*/
		saveUndeliveredDrsConsg();
	});
	
	$('#Cancel').click(function() {
		/** Clear screen  call*/
		clearScreen();
	});
	
}






/**
 * find :: find functionality to load existing DRS Details
 */
function find(){
	if(validationsForFind()){
		var url="./bulkCnPendingDrs.do?submitName=findDrsDetailsByDrsNumber";
		globalFormSubmit(url,formId);
	}
}
function disableHeader(){
	if(isNewDrs()  && isNewBulkDrs()){
		var drsDom= getDomElementById('drsNumber');
		drsDom.setAttribute("readOnly",true);
		drsDom.setAttribute("tabindex","-1");
		getDomElementById("Save").disabled=false;
		setFocusOnDomId('pendingReasonForBulkCn');
	}else if (isNewDrs()){
		setFocusOnDomId('drsNumber');
	}
}
function disableGrid(){
	if(!isNewDrs()){
		/*var tableee = getDomElementById(tableId);
		var totalRowCount =  tableee.rows.length;*/
		var consgDom=document.getElementsByName("to.rowConsignmentNumber");
		var originCityDom=document.getElementsByName("to.rowOriginCityName");
		
		var remarksDom= document.getElementsByName("to.rowRemarks");
		
		for(var i=0;i<consgDom.length;i++) {
			if(consgDom==null || consgDom[i]==null){
				break;
			}
			consgDom[i].setAttribute("readOnly",true);
			consgDom[i].setAttribute("tabindex","-1");
			
			originCityDom[i].setAttribute("readOnly",true);
			originCityDom[i].setAttribute("tabindex","-1");

			remarksDom[i].setAttribute("readOnly",true);
			remarksDom[i].setAttribute("tabindex","-1");
		}
	}
}

function isNewBulkDrs(){
	var consgDom=document.getElementsByName("to.rowConsignmentNumber");
	var isNew=false;
	if(consgDom!=null && consgDom.length>0){
		isNew=true;
	}
	
	return isNew;
}

function focusOnNextBulkRowOnEnter(keyCode,domElement,currentElmId,elementId){
	 if(enterKeyNavWithoutFocus(keyCode)){
		 var curRcount= getRowId(domElement,currentElmId);
		 curRcount=parseNumber(curRcount);
		var  nxtRid=curRcount+1;
		 var nextDom=getDomElementById(elementId+nxtRid);
		 if(!isNull(nextDom)){
			 setFocusOnDom(nextDom);
		 }else{
			 var saveDom=getDomElementById('Save');
			 if(saveDom!=null){
				 setFocusOnDom(saveDom);
			 }
			 }
	 }
}

function validateGridValidationsForHub(){
	var cNumber=document.getElementsByName("to.rowConsignmentNumber");
	var cNId=document.getElementsByName("to.rowConsignmentId");
	var checkBoxDom= document.getElementsByName("to.rowId");
	if(checkBoxDom !=null && checkBoxDom.length >=1 && cNumber!=null && cNumber.length>=1 ){
		for(var i=0;i<cNumber.length;i++){
			if(checkBoxDom[i].checked){
				if(i!=0 && i==cNumber.length-1){
					continue;
//					}else if(isNull(cNumber[i].value) || isNull(cNId[i].value)){
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
		}
	}else{
		alert("Please add atleast one consignment details");
		return false;
	}
	return true;
}
function saveUndeliveredDrsConsg(){
	var pendingReasonDom= getDomElementById('pendingReasonForBulkCn');
	if(isNull(pendingReasonDom.value)){
		alert("Please select pending reason");
		setFocusOnDom(pendingReasonDom);
		return false;
	}
	if(isCheckBoxSelected("to.rowId") && validateGridValidationsForHub()&& promptConfirmation(savePromptMsg)){
		var url="./bulkCnPendingDrs.do?submitName=savePendingDrsForHub";
		enableAll();
		globalFormSubmit(url,formId);
	}
}
