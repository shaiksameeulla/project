$(document).ready(function() {	
	
	printpage();
	//setData4Print();
		
} );



/**
 * printpage
 *
 * @author 
 */
function printpage() {	
	window.print();
	self.close();
	//back();
}
/**
 * back
 *
 * @author 
 */
function back(){
	var url = "/udaan-web/prepCodLcDoxDrs.do?submitName=viewPrepareDrsPage";
	document.deliveryDrsForm.action=url;
	document.deliveryDrsForm.submit();
	
}

/**
 * setData4Print
 *
 * @author 
 */
function setData4Print(){
	
	var bizAssociateCode = document.getElementById("bizAssociateCode").value;
	document.getElementById("baCode").innerHTML = bizAssociateCode.split("-")[0];
	document.getElementById("baName").innerHTML  = bizAssociateCode.split("-")[1];
	
}

function hideUrl(){
		var url = "./deliveryPrintDrs.do?submitName=printCodLcDoxDrs";
		 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=1100,height=800');
}


