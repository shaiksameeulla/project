$(document).ready(function() {	
	
	printpage();
	//setData4Print();
		
} );



function printpage() {	
	window.print();
	back();
}
function back(){
	var url = "/udaan-web/baBookingParcel.do?submitName=getBABookingsDtls";
	document.baBookingParcelForm.action=url;
	document.baBookingParcelForm.submit();
}

/*function setData4Print(){
	
	var bizAssociateCode = document.getElementById("bizAssociateCode").value;
	document.getElementById("baCode").innerHTML = bizAssociateCode.split("-")[0];
	document.getElementById("baName").innerHTML  = bizAssociateCode.split("-")[1];
	
}*/


