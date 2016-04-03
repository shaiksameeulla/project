//window.onbeforeunload = confirmExit;
window.onload =myOnload;

function confirmExit()
{
	//progressBar();
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
}	
function myOnload() {
	jQuery.unblockUI();
}

$(window).bind('beforeunload', function(){
	confirmExit();
});
