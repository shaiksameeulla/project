var rowNumber;
var isSubmitted=true;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";

/*@Desc:validates the mandatory fields*/
function validateDetails() {
	var regionId = document.getElementById("regionId").value;

	for ( var rowId = 1; rowId <= regionId.split(",").length; rowId++) {

		var discount = document.getElementById("discount" + rowId).value;
		var empName = document.getElementById("empName" + rowId).value;
		var empCode = document.getElementById("empCode" + rowId).value;
		var rowNo = rowId--;
		var regionName = document.getElementById("regionName" + rowNo).innerHTML;
		var regionNum = "for  " + (regionName) + "  RHO .";
		rowId++;
		if (isNull(discount)) {
			alert("Please Enter Discount percentage " + regionNum);
			setTimeout(function() {
				document.getElementById("discount" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (isNull(empCode)) {
			alert("Please Enter Employee Code " + regionNum);
			setTimeout(function() {
				document.getElementById("empCode" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (isNull(empName)) {
			alert("Please Enter Employee Name " + regionNum);
			setTimeout(function() {
				document.getElementById("empName" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}

	}
	return true;

}


/*@Desc:For saving and submitting the discount percent*/
function saveAndSubmitDiscount(action) {
	if (validateDetails()) {
		if(isSubmitted){
		discountApproved=document.getElementById("discountApproved").value;
		if(discountApproved=="Y"){
			alert("Data already submitted.");
			return false;
		}
		}
		if (action == "N") {
			document.getElementById("discountApproved" ).value = "N";
		} else if (action == "Y") {
			document.getElementById("discountApproved").value = "Y";
		}
		editDiscountDetails();
		isSubmitted=true;
		var url = './rateBenchMarkDiscount.do?submitName=saveOrUpdateDiscount';
		jQuery.ajax({
			url : url,
			data : jQuery("#regionRateBenchMarkDiscountForm").serialize(),
			success : function(req) {
				printCallBackSave(req, action);
			}
		});

	}
}


/*@Desc:response method for save and submit*/
function printCallBackSave(data, action) {
	var regionId = document.getElementById("regionId").value;

	for ( var i = 1; i <= regionId.split(",").length; i++) {

	
	document.getElementById("discount" + (i)).disabled = true;
	document.getElementById("empCode" + (i)).disabled = true;
	
	}
	if (action == "N") {
		
		 buttonEnabled("saveBtn","btnintform");
		 jQuery("#" +"saveBtn").removeClass("btnintformbigdis");
		 
		 
		 buttonEnabled("submitBtn","btnintform");
		 jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
		
		 buttonDisabled("editBtn","btnintform");
		 jQuery("#" +"editBtn").addClass("btnintformbigdis");
		 
		 if (data != null) {
			 	if(getDiscountDetails()){
				alert('Data Saved successfully.');
			 	}

			} else {
				alert('Data Saved Unsuccessfully.');
			}
	} else if (action == "Y") {
		
		 buttonDisabled("saveBtn","btnintform");
		 jQuery("#" +"saveBtn").addClass("btnintformbigdis");
		 
		 buttonEnabled("editBtn","btnintform");
		 jQuery("#" +"editBtn").removeClass("btnintformbigdis");
		 
		 buttonEnabled("submitBtn","btnintform");
		 jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
		
		if (data != null) {
			if(getDiscountDetails()){
			alert('Data Submitted successfully.');
			}

		} else {
			alert('Data Submitted Unsuccessfully.');
		}
	}

}


/*@Desc:For getting the employee details by the employee code*/
function getEmployeeDetails(empObj, rowId) {
	var empCode = empObj.value;
	rowNumber = rowId;
	if (!isNull(empCode)) {
		var url = "./rateBenchMarkDiscount.do?submitName=getEmpDetails&empCode="
				+ empCode;
		ajaxCall(url, "regionRateBenchMarkDiscountForm", populateDetails);
	}
}

/*@Desc:For displaying the employee details by the employee code*/
function populateDetails(data) {
	/*if (!isNull(data)) {
		resp = eval('(' + data + ')');
		getDomElementById("empName" + rowNumber).value = resp.firstName + " "
				+ resp.lastName;
		getDomElementById("empId" + rowNumber).value = resp.employeeId;
	} else {
		alert("Employee does not exist");
		getDomElementById("empName" + rowNumber).value = "";
		getDomElementById("empCode" + rowNumber).value = "";
		getDomElementById("empCode" + rowNumber).focus();
		getDomElementById("empId" + rowNumber).value = "";
	}*/
	
	if (!isNull(data)) {
		var resp = jsonJqueryParser(data);
		var error = resp[ERROR_FLAG];
		if (data != null && error != null) {
			alert(error);
			getDomElementById("empName" + rowNumber).value = "";
			getDomElementById("empCode" + rowNumber).value = "";
			getDomElementById("empCode" + rowNumber).focus();
			getDomElementById("empId" + rowNumber).value = "";
		} else {
			getDomElementById("empName" + rowNumber).value = resp.firstName + " "
			+ resp.lastName;
			getDomElementById("empId" + rowNumber).value = resp.employeeId;
		}

	}
}


/*@Desc:For getting the the discount details on load of page*/
function getDiscountDetails() {
	var industryCategoryId = getDomElementById("rateIndustryCategoryId").value;
	if (!isNull(industryCategoryId)) {
		var url = "./rateBenchMarkDiscount.do?submitName=getDiscountDetails&industryCategoryId="
				+ industryCategoryId;
		ajaxCall(url, "regionRateBenchMarkDiscountForm",
				populateDiscountDetails);
	}
	return true;
}


/*@Desc:For displaying the the discount details on load of page*/
function populateDiscountDetails(data) {
	//isSubmitted=true;
	var approved ="";
	if (!isNull(data)) {
		resp = jsonJqueryParser(data);
		for ( var i = 0; i < resp.length; i++) {

			document.getElementById("discount" + (i + 1)).value = resp[i].discountPercent;
			document.getElementById("empName" + (i + 1)).value = resp[i].employeeTO.firstName
					+ " " + resp[i].employeeTO.lastName;
			document.getElementById("empCode" + (i + 1)).value = resp[i].employeeTO.empCode;
			document.getElementById("empId" + (i + 1)).value = resp[i].employeeTO.employeeId;
			document.getElementById("regionRateBenchMarkDiscount" + (i + 1)).value = resp[i].regionRateBenchMarkDiscount;
			//document.getElementById("discount" + (i + 1)).disabled = true;
			//document.getElementById("empCode" + (i + 1)).disabled = true;
			document.getElementById("discountApproved").value = resp[i].discountApproved;
			approved=resp[i].discountApproved;
			if(approved == 'Y'){
				document.getElementById("discount" + (i + 1)).disabled = true;
				document.getElementById("empCode" + (i + 1)).disabled = true;
			}
		}
		if (approved == "N") {

			 buttonEnabled("saveBtn","btnintform");
			 jQuery("#" +"saveBtn").removeClass("btnintformbigdis");
			 
			 buttonEnabled("submitBtn","btnintform");
			 jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
			
			 buttonDisabled("editBtn","btnintform");
			 jQuery("#" +"editBtn").addClass("btnintformbigdis");
		
		}else if (approved == "Y") {
			 buttonEnabled("editBtn","btnintform");
			 jQuery("#" +"editBtn").removeClass("btnintformbigdis");
			 
			 buttonEnabled("submitBtn","btnintform");
			 jQuery("#" +"submitBtn").removeClass("btnintformbigdis");
			
			 buttonDisabled("saveBtn","btnintform");
			 jQuery("#" +"saveBtn").addClass("btnintformbigdis");
			}
		
			
	
	} else {
		var regionId = document.getElementById("regionId").value;

		for ( var i = 0; i < regionId.split(",").length; i++) {
			document.getElementById("discount" + (i + 1)).value = "";
			document.getElementById("empName" + (i + 1)).value = "";
			document.getElementById("empCode" + (i + 1)).value = "";
			document.getElementById("empId" + (i + 1)).value = "";
			document.getElementById("regionRateBenchMarkDiscount" + (i + 1)).value = "";
			document.getElementById("discountApproved").value = "";
			document.getElementById("discount" + (i + 1)).disabled = false;
			document.getElementById("empCode" + (i + 1)).disabled = false;
			
		}
		
		 buttonEnabled("saveBtn","btnintform");
		 jQuery("#" +"saveBtn").removeClass("btnintformbigdis");

		  buttonDisabled("submitBtn","btnintform");
		  buttonDisabled("editBtn","btnintform");
		  
		  jQuery("#" + "submitBtn").addClass("btnintformbigdis"); 
		  jQuery("#" +	"editBtn").addClass("btnintformbigdis");
		 

	
		
	}
}

/*@Desc:For editing the details of discount*/
function editDiscountDetails() {
	var regionId = document.getElementById("regionId").value;

	for ( var i = 0; i < regionId.split(",").length; i++) {

		document.getElementById("discount" + (i + 1)).disabled = false;
		document.getElementById("empCode" + (i + 1)).disabled = false;
	}
	isSubmitted=false;
}


/*@Desc:called on load of page*/
function loadDefaultValues() {
	getDiscountDetails();
}


/*@Desc:For validating the format of discount field*/
function validateDiscount(obj) {
	var x = obj.value;
	var parts = x.split(".");
	var n = parseFloat(x);

	if (typeof parts[1] == "string"
			&& (parts[1].length == 0 || parts[1].length > 2)) {
		alert("Enter valid discount percentage.");
		obj.value = "";
		obj.focus();
	}

	if (n < 0 || n >= 100) {
		alert("Enter valid discount percentage.");
		obj.value = "";
		obj.focus();
	}

	return true;
}

