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
}
/**
 * back
 *
 * @author 
 */
/*function back(){
	var url = "/udaan-web/outManifestDox.do?submitName=viewOutManifestDox";
	document.outManifestDoxForm.action=url;
	document.outManifestDoxForm.submit();
	
}*/

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

/*function hideurl(){
	var url = "./outManifestDox.do?submitName=printOutManifestDoxDtls";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}*/

