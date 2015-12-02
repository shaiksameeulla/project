var myRowId;

function disableEnterKey(e, objectCn) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13)
		return false;
	else
		document.onkeypress = disableEnterKey;
}

function getWeight(obj) {

	var par = obj.parentNode;
	while (par.nodeName.toLowerCase() != 'tr') {
		par = par.parentNode;
	}
	myRowId = (par.rowIndex);

	ClsMsComm.showWeight();
	ClsMsComm.setweight();
	strwght = ClsMsComm.getweight();
	document.getElementById("weight" + myRowId).value = strwght;

}


function disableKey(event) {
	if (!event)
		event = window.event;
	if (!event)
		return;

	var keyCode = event.keyCode ? event.keyCode : event.charCode;
	if (keyCode == 116) {
		window.status = "F5 key detected! Attempting to disabling default response.";
		window.setTimeout("window.status='';", 2000);

		// Standard DOM (Mozilla):
		if (event.preventDefault)
			event.preventDefault();

		// IE (exclude Opera with !event.preventDefault):
		if (document.all && window.event && !event.preventDefault) {
			event.cancelBubble = true;
			event.returnValue = false;
			event.keyCode = 0;
		}

		return false;
	}
}

function setEventListener(eventListener) {
	if (document.addEventListener)
		document.addEventListener('keypress', eventListener, true);
	else if (document.attachEvent)
		document.attachEvent('onkeydown', eventListener);
	else
		document.onkeydown = eventListener;

	if (!document.getElementById)
		return;
	var el = document.getElementById("Msg");
	if (el)
		el.innerHTML = "Event handler added.";
}

