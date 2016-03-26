/**
 * validate if the logged in user has permission to operate this screen ,
 * otherwise blocks the screen
 */
$(document).ready( function () {
	checkForValidUser();
	//Setting focus to pickup runsheet type
	document.getElementById("runsheetType").focus();
} );

var rowCount=1;
var currentCustomerList=null;

/**
 * on change of Pickup Run	Sheet Type OR On change of Radio button OR emlpoyee
 */
function clearItemGridRows() {
	//alert("clearing table");
	rowCount=1;
	$('#createRunsheetHub > tbody').empty();
	clearHeaderId();
	clearBranch();

	clearDropDown('customerList');
	clearDropDown('selectedCustomerList');
}
/**
 * Clear the Employee Dropdown
 */
function clearEmployee(){
	$('#employee').val('');
}

//call createEmptyDropDown() for clearing dropdown.

//call customer dropdown
/**
 * getCustomerListForBranch : get Customer List for the branch
 * @param domObject
 * 
 */
function getCustomerListForBranch(domObject){
	//check user should have selected Branch and not null
	clearDropDown('customerList');
	clearDropDown('selectedCustomerList');
	currentCustomerList=null;
	if(!isNull(domObject.value)){
		var url = "./pickupAssignmentAction.do?method=getCustomerListForCreatePickupRunsheetForHub";
		ajaxJquery(url,"createRunsheetAssignmentForm",ajaxResponseToPopulateCustomerList);
	}
}

/**
 * ajaxResponseToPopulateCustomerList :: Ajax response for Customer list
 * @param resp
 */
function ajaxResponseToPopulateCustomerList(resp){

	if(!isNull(resp)){
		var assignmentHeader=jsonJqueryParser(resp);
		if(!isNull(assignmentHeader)){
			currentCustomerList = assignmentHeader.customerList;
			if(!isNull(currentCustomerList)){
				//process list in  for style id=='customerList'
				clearDropDown('customerList');
				createOnlyAllOption('customerList');
				$.each(currentCustomerList, function(k, customer) {
					//var customerAppender=customer.pickupType+getRevPickupDtlId(customer)+getContractId(customer);
					var customerAppender=getCustomerIdentifier(customer);
					if(!isNull(customer.orderNumber)){
						$('#customerList').append("<option value='" + customerAppender + "'>" + customer.businessName+"-"+customer.orderNumber +" ["+customer.pickupLocation +"]"+ "</option>");
					}else{
						$('#customerList').append("<option value='" + customerAppender + "'>" + customer.businessName+" ["+customer.pickupLocation +"]"+ "</option>");
					}
				});
				populatedSelectedCustomerList();
				checkAssignmentGenerated(assignmentHeader);
				//populateFilteredCustomerList();
			}else{
				alert("Customer list Does not exist");
			}
		}			
	}else{
		alert("Customer list Does not exist");
	}
}
function checkAssignmentGenerated(assignmentHeader) {
	var moveRight = getDomElementById("moveRight");
	var moveLeft = getDomElementById("moveLeft");
	if (assignmentHeader.assignmentGenerated == 'G') {
		$("#Save").attr('disabled', true);
		moveRight.disabled = true;
		moveLeft.disabled = true;
		alert('Assignment has got generated for this employee');
	} else {
		$("#Save").attr('disabled', false);
		moveRight.disabled = false;
		moveLeft.disabled = false;
	}
}
/**
 * getCustomerIdentifier :: it's unique identifier 
 * to distinguish  a customer with its contract/reversepickup id And it operates On Object
 * @param customer
 * @returns
 */
function getCustomerIdentifier(customer){
	//alert("customer.pickupType :"+customer.pickupType+"\t"+"getRevPickupDtlId(customer):"+getRevPickupDtlId(customer)+"\t"+"getContractId(customer):"+getContractId(customer)+"getCustomerId(customer):"+getCustomerId(customer));
	return customer.pickupType+getRevPickupDtlId(customer)+getPickupLocationId(customer)+getCustomerId(customer);
}

/**
 * getCustomerIdentifierFromArray :: It's same as getCustomerIdentifier method but it operates from the array.
 * @param counterFlag
 * @returns
 */
function getCustomerIdentifierFromArray(counterFlag){
	var customerIds = document.getElementsByName('to.rowCustomerId');
	var pickupTypes = document.getElementsByName('to.rowPickupType');
	var revPickupDtlsId = document.getElementsByName('to.rowRevPickupDtlId');
	var pickupLocId = document.getElementsByName('to.rowPickupLocationId');
	return pickupTypes[counterFlag].value+revPickupDtlsId[counterFlag].value+pickupLocId[counterFlag].value+customerIds[counterFlag].value;
}

/*function populateFilteredCustomerList(){
		//get Customer list which is not available in neither customerList not selected list for that branch

		var tableee = document.getElementById('createRunsheetHub');
		var cntofRow = tableee.rows.length; 
		var customerBranchIds = document.getElementsByName('to.rowCustomerBranchId');
		var assignmentDtlsIds = document.getElementsByName('to.rowAssignmentDetailId');
		var domElement = getDomElementById('selectedCustomerList');
		var brId=getBranchId();
		for(var i=1;i<cntofRow;i++){
			var k=i-1;
			if(parseInt(brId) == parseInt(customerBranchIds[k].value)&& !isNull(assignmentDtlsIds[k].value)){
				var customerIdentifier=getCustomerIdentifierFromArray(k);

				var no = new Option();
				no.value = customerIdentifier;
				no.text = getGivenTableTDData('createRunsheetHub',i,1);
				domElement.add(no,null);
				k++;
			}

		}
	}*/
/**
 * populatedSelectedCustomerList :: It does auto select  the customer list from left select Box(& which are in the Grid for the Branch) 
 * and move it to the right select box
 */
function populatedSelectedCustomerList(){
	var tableee = document.getElementById('createRunsheetHub');
	var cntofRow = tableee.rows.length; 
	if(cntofRow >= 2){
		var existingCustomerList=getExistingCustomerListForBranch();
		if(existingCustomerList!=null && existingCustomerList.length>0){
			autoSelectCustomerList(existingCustomerList);
			var leftDropDownDom=document.getElementById('customerList');
			var rightDropDownDom=document.getElementById('selectedCustomerList');
			move(leftDropDownDom,rightDropDownDom);
		}
	}
}
/**
 * getExistingCustomerListForBranch ::Helper method for populatedSelectedCustomerList
 * @returns {Array}
 */
function getExistingCustomerListForBranch(){
	var tableee = document.getElementById('createRunsheetHub');
	var cntofRow = tableee.rows.length; 
	var customerBranchIds = document.getElementsByName('to.rowCustomerBranchId');
	var brId=getBranchId();
	var existingCustomerList=new Array();
	var j=0;
	for(var i=1;i<cntofRow;i++){
		var k=i-1;
		var customerIdentifier=getCustomerIdentifierFromArray(k);
		if(parseInt(brId) == parseInt(customerBranchIds[k].value)){
			existingCustomerList[j]=customerIdentifier;
			j++;
			continue;
		}

	}
	return existingCustomerList;
}
/**
 * autoSelectCustomerList ::Helper method for populatedSelectedCustomerList
 * @param customerIdArray
 */
function autoSelectCustomerList(customerIdArray){
	var leftDropDownDom=document.getElementById('customerList');
	var i;
	for (i = 0; i < leftDropDownDom.options[i]!=null &&i<leftDropDownDom.options.length; i++) {
		if (isCustomerIdExist(leftDropDownDom.options[i].value,customerIdArray)) {
			leftDropDownDom.options[i].selected=true;
		}
	}
}

/**
 * Duplicate function
 * onclickForMoveRight
 * @returns {Boolean}
 */
/*function onclickForMoveRight(){
	var leftDropDownDom=document.getElementById('customerList');
	var rightDropDownDom=document.getElementById('selectedCustomerList');
	var selectedIndex= leftDropDownDom.selectedIndex;
	if(selectedIndex <0){
		alert("Please select customer(s)");
		return false;
	}
	//preserve selected values
	//var selectedArray=getSelectedArray(leftDropDownDom);
	var selectedArray= selectAllForCustomer(leftDropDownDom);
	if(isNull(selectedArray)|| selectedArray.length==0){
		alert("Customer(s) does not exist ");
		return false;
	}
	//alert("selectedArray"+selectedArray);
	//move customers to right box
	move(leftDropDownDom,rightDropDownDom);

	//add selected list to grid;

	//get each customerId from global list(currentCustomerList) and create a list
	var _customerDtls=null;
	if(currentCustomerList!=null){
		for(var i=0;i<currentCustomerList.length ;i++){
			_customerDtls=null;
			_customerDtls = currentCustomerList[i];
			var customerAppender=getCustomerIdentifier(_customerDtls);
			//customerAppender =customerAppender+'';
			if(isCustomerIdExist(customerAppender,selectedArray)){
				createAssignmentGridRow(_customerDtls);
				continue;
			}
		}
	}
}*/
function onclickForMoveRight(){
	var leftDropDownDom=document.getElementById('customerList');
	var rightDropDownDom=document.getElementById('selectedCustomerList');
	var selectedIndex= leftDropDownDom.selectedIndex;
	if(selectedIndex <0){
		alert("Please select customer(s)");
		return false;
	}
	//preserve selected values
	//var selectedArray=getSelectedArray(leftDropDownDom);
	var selectedArray= selectAllForCustomer(leftDropDownDom);
	if(isNull(selectedArray)|| selectedArray.length==0){
		//artf3106698 : Incorrect error message: “Customer(s) does not exist”
		alert("No customer(s) found to move");
		return false;
	}
	//alert("selectedArray"+selectedArray);
	//move customers to right box
	move(leftDropDownDom,rightDropDownDom);

	//add selected list to grid;

	//get each customerId from global list(currentCustomerList) and create a list
	var _customerDtls=null;
	if(currentCustomerList!=null){
		for(var i=0;i<currentCustomerList.length ;i++){
			_customerDtls=null;
			_customerDtls = currentCustomerList[i];
			var customerAppender=getCustomerIdentifier(_customerDtls);
			if(isCustomerIdExist(customerAppender,selectedArray)){
				createAssignmentGridRow(_customerDtls);

				continue;
			}
		}
	}
}
/**
 * onclickForMoveLeft
 * @returns {Boolean}
 */
function onclickForMoveLeft(){
	var leftDropDownDom=document.getElementById('customerList');
	var rightDropDownDom=document.getElementById('selectedCustomerList');
	var selectedIndex= rightDropDownDom.selectedIndex;
	if(selectedIndex <0){
		alert("Please select customer(s)");
		return false;
	}
	//preserve selected values
	var selectedArray = selectAllForCustomer(rightDropDownDom);
	//alert("selectedArray"+selectedArray);

	//move customers to left box
	move(rightDropDownDom,leftDropDownDom);

	//delete each row for selected customerId 
	deleteRow(selectedArray);

}
//new 
/**
 * createAssignmentGridRow Create new row from the Customer list (which is retrieved from the db )
 */
function createAssignmentGridRow(customerDtls) {
	var trStyle=rowCount % 2 == 0 ? 'even' : 'odd';
	var row = "<tr class='"+trStyle+"'>"+
	"<td>" +rowCount + "</td>" +
	"<td>"+getCustomerName(customerDtls)+"</td>" +
	/*"<td>"+getCustomerCode(customerDtls)+"</td>" +*/
	"<td>"+getShippedToCode(customerDtls)+"</td>" +
	"<td>"+getPickupLocation(customerDtls)+"</td>" +
	"<td>"+getOrderNumber(customerDtls)+"</td>" +
	"<td>"+getBranchCode()+"" +
	"<input type='hidden' id='rowCustomerId"+rowCount+"' name='to.rowCustomerId' value='"+getCustomerId(customerDtls)+"'/>" +
	"<input type='hidden' id='rowCustomerBranchId"+rowCount+"' name='to.rowCustomerBranchId' value='"+getBranchId()+"'/>" +	
	"<input type='hidden' id='rowAssignmentDetailId"+rowCount+"' name='to.rowAssignmentDetailId' value='"+getAssignmentDtlsId(customerDtls)+"'/>" +
	"<input type='hidden' id='rowPickupLocationId"+rowCount+"' name='to.rowPickupLocationId' value='"+getPickupLocationId(customerDtls)+"'/>" +
	"<input type='hidden' id='rowRevPickupHeaderId"+rowCount+"' name='to.rowRevPickupHeaderId' value='"+getRevPickupHeaderId(customerDtls)+"'/>" +
	"<input type='hidden' id='rowRevPickupDtlId"+rowCount+"' name='to.rowRevPickupDtlId' value='"+getRevPickupDtlId(customerDtls)+"'/>" +
	"<input type='hidden' id='rowOrderNumber"+rowCount+"' name='to.rowOrderNumber' value='"+customerDtls.orderNumber+"'/>" +
	"<input type='hidden' id='rowPickupType"+rowCount+"' name='to.rowPickupType' value='"+customerDtls.pickupType+"'/>" +
	"</td></tr>" ;
	$('#createRunsheetHub').append(row);
	rowCount++;
	updateGrid('createRunsheetHub');
} 
/**
 * createAssignmentDetailsGridRow :: to populate Grid List which is already available data from the db
 * @param customerDtls
 */
//populating existing details
function createAssignmentDetailsGridRow(customerDtls) {
	var trStyle=rowCount % 2 == 0 ? 'even' : 'odd';
	var row = "<tr class='"+trStyle+"'>"+
	"<td>" +rowCount + "</td>" +
	"<td>"+customerDtls.customerName+"</td>" +
	"<td>"+customerDtls.customerCode+"</td>" +
	"<td>"+customerDtls.pickupLocation+"</td>" +
	"<td>"+customerDtls.reversePickupOrderNumber+"</td>" +
	"<td>"+customerDtls.pickupBranchCode + "-"+customerDtls.pickupBranchName+"" +
	"<input type='hidden' id='rowCustomerId"+rowCount+"' name='to.rowCustomerId' value='"+customerDtls.customerId+"'/>" +
	"<input type='hidden' id='rowCustomerBranchId"+rowCount+"' name='to.rowCustomerBranchId' value='"+customerDtls.pickupBranchId+"'/>" +	
	"<input type='hidden' id='rowAssignmentDetailId"+rowCount+"' name='to.rowAssignmentDetailId' value='"+(!isNull(customerDtls.assignmentDetailId)?customerDtls.assignmentDetailId:0)+"'/>" +	
	"<input type='hidden' id='rowPickupLocationId"+rowCount+"' name='to.rowPickupLocationId' value='"+(!isNull(customerDtls.pickupLocationId)?customerDtls.pickupLocationId:0)+"'/>" +
	"<input type='hidden' id='rowRevPickupHeaderId"+rowCount+"' name='to.rowRevPickupHeaderId' value='"+(!isNull(customerDtls.revPickupId)?customerDtls.revPickupId:0)+"'/>" +
	"<input type='hidden' id='rowRevPickupDtlId"+rowCount+"' name='to.rowRevPickupDtlId' value='"+(!isNull(customerDtls.reversePickupOrderDetailId)?customerDtls.reversePickupOrderDetailId:0)+"'/>" +
	"<input type='hidden' id='rowOrderNumber"+rowCount+"' name='to.rowOrderNumber' value='"+customerDtls.reversePickupOrderNumber+"'/>" +
	"<input type='hidden' id='rowPickupType"+rowCount+"' name='to.rowPickupType' value='"+customerDtls.pickupType+"'/>" +
	"</td></tr>" ;
	$('#createRunsheetHub').append(row);
	rowCount++;
} 

/**
 * getCustomerName
 * @param customerDtl
 * @returns businessName
 */
function getCustomerName(customerDtl) {
	return customerDtl.businessName;
}
/**getCustomerId
 * getCustomerId
 * @param customerDtl
 * @returns CustomerId
 */
function getCustomerId(customerDtl) {
	return customerDtl.customerId;
}
/**getCustomerCode
 * @param customerDtl
 * @returns {String}
 */
function getCustomerCode(customerDtl) {
	var customerCode = "";
	customerCode = customerDtl.customerCode;
	return customerCode;
}
function getShippedToCode(customerDtl) {
	var shippedToCode = "";
	var customerCode = "";
	customerCode = customerDtl.customerCode;
	shippedToCode = customerDtl.shippedToCode;
	if(isNull(shippedToCode)){
		shippedToCode=customerCode;
	}
	return shippedToCode;
}
/**getOrderNumber
 * @param customerDtl
 * @returns {String}
 */
function getOrderNumber(customerDtl) {
	var orderNum = "";
	orderNum = customerDtl.orderNumber;
	if(isNull(orderNum)){
		orderNum="";
	}
	return orderNum;
}
/**getOrderNumber
 * @param customerDtl
 * @returns {String}
 */
function getPickupLocation(customerDtl) {
	var pickupLoc = "";
	pickupLoc = customerDtl.pickupLocation;
	if(isNull(pickupLoc)){
		pickupLoc="";
	}
	return pickupLoc;
}


/**getPickupLocationId
 * @param customerDtl
 * @returns ContractId
 */
function getPickupLocationId(customerDtl) {
	return (!isNull(customerDtl.pickupLocationId)?customerDtl.pickupLocationId:0);
}
/**getAssignmentDtlsId
 * @param customerDtl
 * @returns AssignmentDtlsId
 */
function getAssignmentDtlsId(customerDtl) {
	return (!isNull(customerDtl.assignmentDetailId)?customerDtl.assignmentDetailId:0);
}

//private Integer detailId;
//private Integer headerId;
//private String orderNumber;
//private Integer contractId;

/**getRevPickupDtlId
 * @param customerDtl
 * @returns RevPickupDtlId
 */
function getRevPickupDtlId(customerDtl) {
	return (!isNull(customerDtl.detailId)?customerDtl.detailId:0);
}
/** getRevPickupHeaderId
 * @param customerDtl
 * @returns RevPickupHeaderId
 */
function getRevPickupHeaderId(customerDtl) {
	return  customerDtl.headerId;
}
/**getBranchCode
 * @returns Office Code
 */
function getBranchCode() {
	var dropDownDom=document.getElementById('branch');
	var selectedIndex= dropDownDom.selectedIndex;
	return dropDownDom.options[selectedIndex].text;

}
/**getBranchId
 * @returns BranchId
 */
function getBranchId() {
	var dropDownDom=document.getElementById('branch');
	return dropDownDom.value;

}

/**getSelectedArray
 * @param selectedDom
 * @returns {Array}
 */
function getSelectedArray(selectedDom){
	var selectedArray = new Array();
	var i,j=0;
	for (i = 0; i < selectedDom.options[i]!=null &&i<selectedDom.options.length; i++) {
		if (selectedDom.options[i].selected && selectedDom.options[i].value != "") {
			selectedArray[j] = selectedDom.options[i].value;
			j++;
		}
	}
	return selectedArray;
}
/**
 * deleteRow
 * @param customerIdArray
 */

function deleteRow(customerIdArray){
	var tableee = document.getElementById('createRunsheetHub');
	var cntofRow = tableee.rows.length; 
	var customerBranchIds = document.getElementsByName('to.rowCustomerBranchId');
	for(var i=1;i<cntofRow;i++){
		var j=i-1;
		var customerIdentifier=getCustomerIdentifierFromArray(j);
		if((isExistInArray (customerIdentifier,customerIdArray)>=0)&& getBranchId()==customerBranchIds[j].value){
			tableee.deleteRow(i);
			cntofRow--;
			i--;
		}
	}
	updateGrid('createRunsheetHub');
}

/**updateGrid
 * @param tableID
 */
function updateGrid(tableID) {
	var tableRows = document.getElementById(tableID).rows;
	for(var i=1; i < tableRows.length; i++) { 
		var rowCells=tableRows[i].cells;
		rowCells[0].innerHTML=i;
	}
}
/**getGivenTableTDData
 * @param tableID
 * @param rowId
 * @param tdId
 * @returns
 */
function getGivenTableTDData(tableID,rowId,tdId){
	var tableRows = document.getElementById(tableID).rows;
	var rowCells=tableRows[rowId].cells;
	//alert(rowCells[tdId].innerHTML);
	var name=rowCells[tdId].innerHTML;
	var code=rowCells[(tdId+2)].innerHTML;
	if(!isNull(code)){
		name = name+"-"+code;
	}
	return name;
}

/**
 * isCustomerIdExist
 * @param value
 * @param customerArray
 * @returns {Boolean}
 */
function isCustomerIdExist(value,customerArray){
	if(isExistInArray(value,customerArray)>=0){
		return true;
	}
	return false;
}
/**getAssignmentDetailsForHub
 * @returns {Boolean}
 */
function getAssignmentDetailsForHub(){
	//check user should have selected Branch and not null
	clearDropDown('customerList');
	clearDropDown('selectedCustomerList');
	document.getElementById("branch").value="";
	//check user has selected Employee Id
	var employeeDom = document.getElementById("employee");
	if(employeeDom !=null && !isNull(employeeDom.value)){
		var url = "./pickupAssignmentAction.do?method=getCreatedAssignmentDetailsForHub";
		ajaxJquery(url,"createRunsheetAssignmentForm",ajaxResponseToPopulateGrid);
	}else{
		//artf3106707 : Incorrect error message “please select employee” - As discussed with tester removed the msg on change of the emp name.
		//alert("please select employee");
		employeeDom.value = "";
		employeeDom.focus();
		return false;
	}
}
/**ajaxResponseToPopulateGrid
 * @param resp
 * @returns {Boolean}
 */
function ajaxResponseToPopulateGrid(resp){
	var assignmentDtlsList=null;
	if(!isNull(resp)){
		assignmentDtlsList=jsonJqueryParser(resp);
		if(!isNull(assignmentDtlsList)){
			rowCount=1;
			$.each(assignmentDtlsList, function(k, assignmentDtls) {
				createAssignmentDetailsGridRow(assignmentDtls);
			});
			$('#assignmentHeaderId').val(assignmentDtlsList[0].assignmentHeaderId);
			var status=assignmentDtlsList[0].status;
			$('#runsheetStatus').val(status);
			var type= $('#runsheetType').val();

			if(!isNull(status)&& (type=="1")&& status== $('assignmentStatusGenerated').val()){
				alert(" Pickup run sheet already generated ");
				blockScreen();
				return false;
			}

		}
	}
}
/**
 * clearHeaderId
 */
function clearHeaderId(){
	$('#assignmentHeaderId').val('');
}
/**
 * clearBranch
 */
function clearBranch(){
	$('#branch').val('');
}
/**isValidDataForSave
 * @returns {Boolean}
 */
function isValidDataForSave(){
	var tabLength= $('#createRunsheetHub >tbody >tr').length;
	//artf3106702 : Incorrect error message “Nothing to save”
	if(tabLength<=0 && isNewRunsheet()){
		alert("Please select customers to create pickup run sheet");
		return false;
	}
	return true;
}
/**validateFormDetails
 * @returns {Boolean}
 */
function validateFormDetails(){
	var runsheetType=$('#runsheetType');
	var isChecked = jQuery('#radioButtonType').attr('checked');
	var employee=$('#employee');

	/*if(!isChecked){
		alert("Please select the radion button");
		return false;
	}*/
	if(isNull(runsheetType.val())){
		alert("Please select pickup runsheet type");
		runsheetType.focus();
		return false;
	}
	if(isNull(employee.val())){
		alert("Please select Employee");
		employee.focus();
		return false;
	}
	if(!isValidDataForSave()){
		return false;
	}
	return true;
}
/**
 * save functionality 
 */
function save(){
	if(validateFormDetails()&& savePrompt()){
		var url = "./pickupAssignmentAction.do?method=savePickupAssignmentForHub";
		ajaxCall(url,"createRunsheetAssignmentForm",saveCallBack);
	}
}

/**saveCallBack : called after getting response from the Server
 * @param response
 */
function saveCallBack(response){
	if(!isNull(response)){
		var result= jsonJqueryParser(response);
		var success= result["SUCCESS"];
		var failure= result["ERROR"];
		if(!isNull(failure)){
			alert(failure);
		}else if(!isNull(success)){
			alert(success);
			blockScreen();
		}else{
			aler("Problem in saving");
		}
	}

}
/**
 * clearScreen
 */
function clearScreen(){
	var url = "./pickupAssignmentAction.do?method=preparePageForHub";
	if(confirm("Do you want to Clear the details ?")) {
		dropdownEnable();
		document.createRunsheetAssignmentForm.action = url;
		document.createRunsheetAssignmentForm.submit();
	}
}
/**
 * selectAllForCustomer
 * @param selectedDom
 * @returns {Array}
 */
function selectAllForCustomer(selectedDom){
	var selectedArray = new Array();
	var j=0;
	var isAllSelected=(getSelectedValue(selectedDom)=="-1" )?true:false;
	if(isAllSelected){
		for (var i = 0; i < selectedDom.options[i]!=null && i<selectedDom.options.length; i++) {
			/*if(selectedDom.options[i].value =="-1"){
				selectedDom.options[i].selected=false;
			}else{*/
				selectedDom.options[i].selected=true;
				selectedArray[j]=selectedDom.options[i].value;
				j++;
			//}
		}
	}else{
		selectedArray= getSelectedArray(selectedDom);
	}
	return selectedArray;
}
/**
 * getSelectedValue
 * @param selectedDom
 * @returns
 */
function getSelectedValue(selectedDom){
	return selectedDom.value;
}
/**savePrompt
 * @returns {Boolean}
 */
function savePrompt(){
	//artf3106701 : Incorrect error message “Do you want to Create pickrunsheet ?”
	var isNew= isNewRunsheet();
	var msg= "Do you want to update  pickup run sheet?";
	if(isNew){
		msg= "Do you want to create pickup run sheet?";
	}
	if(confirm(msg)){
		return true;
	}
	return false;
}
/**isNewRunsheet : check whether existing Assignment details are new or old
 * @returns
 */
function isNewRunsheet(){
	var headerId= $('#assignmentHeaderId').val();
	var isNew= isNull(headerId)?true:false;
	return isNew;
}
/**
 * blockScreen
 */
function blockScreen(){
	disableAll();
	document.getElementById('Clear').disabled=false;
	jQuery("#" + "Clear").removeClass(BUTTON_DISABLE_CLASS);
	jQuery("#" + "Clear").addClass(BUTTON_ENABLE_CLASS);
}
/**
 * checkForValidUser
 */
function checkForValidUser(){
	var user= $('#isValidUser').val();
	if(!isNull(user)&& user =="false"){
		blockScreen();
	}
}