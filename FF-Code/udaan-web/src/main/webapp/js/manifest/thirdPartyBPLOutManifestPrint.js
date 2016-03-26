$(document).ready(function() {	
	//back();
	printpage();
	
	//setData4Print();
		
} );

function printpage() {	
	window.print();
	self.close();
	//back();
}

function back(){
	var url = "/udaan-web/thirdPartyBPLOutManifest.do?submitName=viewThirdPartyBPL";
	document.thirdPartyBPLOutManifestForm.action=url;
	document.thirdPartyBPLOutManifestForm.submit();
	
}

function setData4Print(){
	
	var bizAssociateCode = document.getElementById("bizAssociateCode").value;
	document.getElementById("baCode").innerHTML = bizAssociateCode.split("-")[0];
	document.getElementById("baName").innerHTML  = bizAssociateCode.split("-")[1];
	
}

function hideurl(){
	var url = "./thirdPartyBPLOutManifest.do?submitName=printThirdPartyDtls";
	 window.open(url, "myPopup", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=120,height=120');
	 self.close();
}
