/**
 * select al the check boxes if the header check box is checked
 */
$(document).ready(function() {
	$('#selectall').click('click', function() {
		checked = ($(this).attr('checked') == 'checked') ? 'true' : false;
		$(':checkbox').each(function() {
			$(this).attr('checked', checked);
		});
	});
});


/**
 * accept all selected the requests 
 */
function acceptDetails() {
	if(isCheckBoxSelectedWithMessage("to.checkbox", "Please select the check box to accept the order")){
		document.confirmPickupOrderForm.action = "/udaan-web/confirmPickupOrder.do?method=acceptPickupDetails&Status=A";
		document.confirmPickupOrderForm.submit();
	}	
}


/**
 * decline the selected requests
 */
function declineDetails() {
	document.confirmPickupOrderForm.action = "/udaan-web/confirmPickupOrder.do?method=declinePickupDetails&Status=D";
	document.confirmPickupOrderForm.submit();
}



/**
 * get the create pickup order details for the selected order number
 * @param rowId
 * @param orderNum
 */
function createPickupOrder(rowId, orderNum) {

	url = './createPickupOrder.do?method=getPickupOrderDetail&orderNumber='
			+ orderNum;
	window.location = url;
}
