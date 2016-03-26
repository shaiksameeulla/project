
function inboundPageRefresh(){
		window.location="./inboundUtility.do?submitName=preparePage";
	}

/**
 * @desc validates the  grid consignment
 * @param rId
 */
function prepareZipFile(){
	
		var url="./inboundUtility.do?submitName=prepareInboundZip";
		ajaxJquery(url,"jobServiceForm",ajaxResponseForPrepareInboundZip);
	
}

function ajaxResponseForPrepareInboundZip(ajaxResp){
	//alert("ajaxResp"+ajaxResp);
	var domElementIdMessage=getDomElementById('zipFileMessage');
	var domElementIdPath=getDomElementById('zipFilePath');
	var noteMsgDom=getDomElementById('noteMsg');
	
	var message="Archive File is created At location :";
	if (!isNull(ajaxResp)) {
		var responseText =jsonJqueryParser(ajaxResp); 
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			domElementIdMessage.innerHTML=error;
		}else {
			alert(message +"["+success+"]");
			domElementIdMessage.innerHTML="Archive File is created At location :";
			domElementIdPath.innerHTML=success;
			noteMsgDom.innerHTML="Please upload this file to central server";
			document.getElementById("downloadLink").style.display ="inline";
			//disableAll();
			disableGlobalButton('Save');
			enableGlobalButton('cancel');
			//if(confirm("Do you want to clear the page ?")){inboundPageRefresh();}
		}
	}else{
		alert(" Error in generating Archive ");
	}
}
	

