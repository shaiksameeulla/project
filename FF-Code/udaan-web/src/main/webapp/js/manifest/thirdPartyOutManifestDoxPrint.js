$(document).ready(function() {	
	//back();
	printpage();
	
		
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
	var url = "/udaan-web/thirdPartyOutManifestDox.do?submitName=viewThirdPartyOutManifestDox";
	document.thirdPartyOutManifestDoxForm.action=url;
	document.thirdPartyOutManifestDoxForm.submit();
	
}
function hideurl(){
	var url = "./thirdPartyOutManifestDox.do?submitName=printThirdPartyOutManifestDox";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
}


