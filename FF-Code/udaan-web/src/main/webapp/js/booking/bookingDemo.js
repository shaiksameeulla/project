$(document).ready(function() {     
			$('#baBooking').dataTable( {         
			"sScrollY": 200,         
			"sScrollX": "100%",         
			"sScrollXInner": "100%",
			"bPaginate": false,     
			"bSort": true 
			} ); } ); 


/** The rowCount */
var rowCount = 1;
/**
 * fnClickAddBABookingRow
 * Using data grid
 *
 * @author sdalli
 */
function fnClickAddBABookingRow() {
			$('#baBooking').dataTable().fnAddData( [
	'<input type="checkbox"   id="orderNumber'+ rowCount +'" name="chkBoxName" value=""/>',
	rowCount,
	'<input type="text"    id="consgNumber'+ rowCount +'" name="to.consgNumber" onblur="validateConsg(this);getCNContent();getPaperworks();" onkeypress = "return callEnterKey(event, document.getElementById(\'noOfPcs'+ rowCount +'\'),false);"/>',
	'<input type="text"    id="noOfPcs'+ rowCount +'" name="to.noOfPcs" onkeypress = "return callEnterKey(event, document.getElementById(\'baCode'+ rowCount +'\'),false);"/>',
	'<input type="text"    id="baCode'+ rowCount +'" name="to.baCode" onkeypress = "return callEnterKey(event, document.getElementById(\'pinCode'+ rowCount +'\'),false);"/>',
	'<input type="text"    id="pinCode'+ rowCount +'" name="to.pinCode" onkeypress = "return callEnterKey(event, document.getElementById(\'dest'+ rowCount +'\'),false);"/>',
	'<input type="text"    id="dest'+ rowCount +'" name="to.destination" onkeypress = "return callEnterKey(event, document.getElementById(\'chgWeight'+ rowCount +'\'),false);"/>',
	'<input type="text"    id="chgWeight'+ rowCount +'" name="to.chgWeight" onkeypress = "return callEnterKey(event, document.getElementById(\'content'+ rowCount +'\'),false);"/>',
	'<select name="content" id="content'+ rowCount +'" ><option value="select">--Select--</option></select>',
	'<select name="paperWorks" id="paperWorks'+ rowCount +'" ><option value="select">--Select--</option></select>',
	'<input type="text"  id="ref'+ rowCount +'" name="to.refNo" onkeypress = "return callEnterKey(event, document.getElementById(\'amount'+ rowCount +'\'),false);"/>',
	'<input type="text"  id="amount'+ rowCount +'"  name="to.amount" onkeypress ="return callEnterKey(event, null,true);"/>'

	] );

			rowCount++;
		}

/* Enter key navigation */
/**
 * callEnterKey
 *
 * @param e
 * @param objectCn
 * @param isLastRow
 * @returns {Boolean}
 * @author sdalli
 */
function callEnterKey(e, objectCn,isLastRow) {
var key;
if (window.event)
	key = window.event.keyCode; // IE
else
	key = e.which; // firefox
if (key == 13){
	if(isLastRow){
		fnClickAddBABookingRow();
		setFocus();
	}
	else 
		objectCn.focus();
	return false;
}else{
	
}
}

/**
 * setFocus
 *
 * @author sdalli
 */
function setFocus(){
	var cnCoount = parseInt(rowCount) - 1;
	document.getElementById("consgNumber"+cnCoount).focus();
}
/**
 * validateConsg
 *
 * @param obj
 * @author sdalli
 */
function validateConsg(obj){
	if(obj.value == null || obj.value==""){
	alert("Please enter consignment number.");
	setTimeout(function(){document.getElementById(obj.id).focus();},10);
	}
	else {
		url = './baBooking.do?method=validateConsignment&cnNo='+obj.value;
		jQuery.ajax({
			url: url,
			success: function(req){isValidCN(req,obj);}
		});
	}
	}

/**
 * isValidCN
 *
 * @param req
 * @param obj
 * @author sdalli
 */
function isValidCN(req,obj){
	var response = req.responseText;
	if(req == "INVALID"){
		alert("Consignment already booked.");
		obj.value="";
		setTimeout(function(){document.getElementById(obj.id).focus();},10);
		
	}
	
}

/**
 * getCNContent
 *
 * @author sdalli
 */
function getCNContent(){
	var cnCoount = parseInt(rowCount) - 1;
	var content= document.getElementById('content'+cnCoount);	
	var option ;	
	 option = document.createElement("option");
	 option.value ="Laptop";			 
	 option.appendChild(document.createTextNode("Laptop"));
	 content.appendChild(option);
	 option = document.createElement("option");
	 option.value ="Mobile";			 
	 option.appendChild(document.createTextNode("Mobile"));
	 content.appendChild(option);
	 option = document.createElement("option");
	 option.value ="Computer";			 
	 option.appendChild(document.createTextNode("Computer"));
	 content.appendChild(option);
}

/**
 * getPaperworks
 *
 * @author sdalli
 */
function getPaperworks(){
	var cnCoount = parseInt(rowCount) - 1;
	var paperWorks= document.getElementById('paperWorks'+cnCoount);	
	var option ;	
	 option = document.createElement("option");
	 option.value ="inv";			 
	 option.appendChild(document.createTextNode("Invoice"));
	 paperWorks.appendChild(option);
	 option = document.createElement("option");
	 option.value ="tx";			 
	 option.appendChild(document.createTextNode("Tax Copy"));
	 paperWorks.appendChild(option);
	 option = document.createElement("option");
	 option.value ="wbc";			 
	 option.appendChild(document.createTextNode("Way Bill Cop"));
	 paperWorks.appendChild(option);
}