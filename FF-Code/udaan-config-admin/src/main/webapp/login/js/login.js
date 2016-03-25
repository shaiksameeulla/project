//Added By sami

var IS_BUTTON_PRESSED=false;
function load(){
	/**don't let browser remember the password*/
	try{
	document.getElementById('password').autocomplete = 'off';
	}catch(e){
		alert("Browser does not support ,password auto complete off");
	}
}

function enterKeyNavigation(e) {
	
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("loginButton").click();
}
function clearDisplayedMsg(){
	var msgid=document.getElementById('msgcontainer');
	if(msgid!=null){
		msgid.innerHTML="";
	}
}

/* Enter key navigation */
function callEnterKey(e, objectCn) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		objectCn.focus();
		return false;
	} else {

	}
}

function submitLogin() {
	
	if(!IS_BUTTON_PRESSED){
		IS_BUTTON_PRESSED=true;
		clearDisplayedMsg();
		var url="./login.do?submitName=submitLoginForm";
		document.loginPage.action=url;
		document.loginPage.submit();
	}else{
		alert("Request already submitted, please wait while we process your request.");
	}
}

function changepassword() {
    var url="./login.do?submitName=showChangePassword";
    document.loginPage.action=url;
    document.loginPage.submit();
}
function forgotpassword() {
	var url="./forgotPassword.do?submitName=showForgotPassword";
    document.loginPage.action=url;
    document.loginPage.submit();

}

