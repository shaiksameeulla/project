
function getCitiesByRegCode(regCode,index){
	
	//alert(regCode+'----'+index);
	showProcessing();
	
	$.getJSON("getCitiesByRegCodes1.htm", {
		regCode : regCode,
		
	}, function(data) {
		var options = '';
		options += '<option value="0" selected="selected">Select</option>';
		options += '<option value="1000'+regCode+'">ALL</option>';
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].cityId + '">'
					+ data[i].cityName + '</option>';
		}
		
		$("#city" + index).html(options); 
		
		hideProcessing();
	});

	
}


/*function getCitiesByRegCode(regCode,index){
	
	var branchCode= new Array();
	var branchName= new Array();
	$.getJSON("getCitiesByRegCodes1.htm", {
		regCode : regCode,
		
	}, function(data) {
		var options = '';
		options += '<option value="0" selected="selected">Select</option>';
		
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			 
			 var branch=data[i].cityId;
			 branchCode.push(branch);
			 var officeName=data[i].cityName;
			 options += '<option value="' + branchCode + '">'+ officeName +'</option>';
			
		}
		 options += '<option value="'+branchCode+'">ALL</option>';
		$("#city" + index).html(options); 
	});

	
}*/

function getCitiesByCityId(regCode,index){
	
	
	showProcessing();
	
	$.getJSON("getCitiesByRegCodes1.htm", {
		regCode : regCode,
		
	}, function(data) {
		var options = '';
		options += '<option value="0" selected="selected">Select</option>';
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].cityId + '">'
					+ data[i].cityName + '</option>';
		}
		
		$("#city" + index).html(options); 
		
		hideProcessing();
		
	});

	
}


function getRegionForDestnationCity(regcode,index){
	
	showProcessing();
	
	$.getJSON("getRegionForDestnationCity.htm", {
		regCode : regCode,
		
	}, function(data) {
		var options = '';
		options += '<option value="0" selected="selecte">Select</option>';
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].cityId + '">'
					+ data[i].cityName + '</option>';
		}
		
		$("#city" + index).html(options); 
		
		hideProcessing();
	});

	
}


/*function getOfficesForDestination(cityId){
	var isAvailable = false;
	var branchCode= new Array();
	var branchName= new Array();
	var length=cityId.length
	alert(cityId+'----'+cityId);
	$.getJSON("getOfficesByCityId.htm", {
		cityId : cityId,
		
	}, function(data) {
		var options = '';
	
			options += '<option value="0" selected="selected" >Select</option>';
			options += '<option value="ALL">ALL</option>';

		var len = data.length;
		 for ( var i = 0; i < len; i++) {
				 var branch=data[i].officeCode;
				 branchCode.push(branch);
				 var officeName=data[i].officeName;
				 options += '<option value="' + branchCode + '">'+ officeName +'</option>';
		 }
		$("#officeCodeValue").html(options); 
		 
		
	});

	
}*/


function getOfficeCodesByOfficeName(theObject){
    
	//$("#officeCodeValue").values
	var individualVal;
	var selectedValue = theObject.value;
	var values = new Array();
	alert(theObject);
	if(selectedValue=='ALL') {
		$('#officeCodeValue option').each(function() { 
			individualVal = $(this).val();
			alert("individualVal: " + individualVal);
			values.push(individualVal);
		});
	}
	alert("selectedValue: " + selectedValue);
	alert("values are: " + values);
	alert("individualVal ALL: " + individualVal);
}




function getOfficesForDestination(cityId){
	var isAvailable = false;
	var branchCode= new Array();
	
	/*alert("selectedValue: " + cityId);*/
	
	showProcessing();
	
	$.getJSON("getOfficesByCityId.htm", {
		cityId : cityId,
		
	}, function(data) {
		var options = '';

			options += '<option value="0" selected="selected">Select</option>';
			options += '<option value="-1">ALL</option>';
		var len = data.length;
		
		 for ( var i = 0; i < len; i++) {
			 
			 /*if(optionsForAll =='-1'){
				 isAvailable=true;
				 alert("isAvailable: " + isAvailable);
				 var branch=data[i].officeCode;
				   branchCode.push(branch);
				 options = '<option value="' + branchCode + '">ALL</option>';
			 }*/
			 
			 options += '<option value="' + data[i].officeCode + '">'
				+ data[i].officeName + '</option>';
		 } 
		   $("#officeCodeValue").html(options); 
		
		   hideProcessing();
	});

	
}



function getCitiesByRegCodeFindOriginSta(regCode,index){
	
	showProcessing();
	
	$.getJSON("getCitiesByRegCodeFindOriginSta.htm", {
		regCode : regCode,
		
	}, function(data) {
		var options = '';
		var isAvailable = false;
		var len=data.length;
		var cityId
		
		options += '<option value="0" selected="selected">Select</option>';
		if(len !=1){
		   options += '<option value="1000'+regCode+'">ALL</option>';
		}
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].cityId + '">'
					+ data[i].cityName + '</option>';
		}
		
		$("#city" + index).html(options); 
		
		hideProcessing();
	});

	
}


function getCustomerNamesForLCCOD(officeCode,region,station){
	
	//alert(officeCode+'----------'+region+'---------'+station);
	var officeLength=officeCode.length;
	if(station=="10001000"){
		officeCode="100010001000";
		
	}
	
	var branchCode = new Array();
	var branchCode;
	
	showProcessing();
	
	$.getJSON("getCustomerNamesForLCCOD.htm", {
		officeCode : officeCode,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		for ( var i = 0; i < len; i++) {
			var branch = data[i].customerId;
			branchCode.push(branch);
		}
		
		options += '<option value="0" selected="selected">Select</option>';
		options += '<option value="00001">ALL</option>';
		
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].customerId + '">'+ data[i].bussinessName + '</option>';
		}
		
		$("#bussinessName").html(options); 
		
		hideProcessing();
	});

	
}

function getCustomerNameFromOfficeCode(officeCode){
	var branchCode = new Array();
	var branchCode;
	
	showProcessing();
	
	$.getJSON("getCustomerNameFromOfficeCode.htm", {
		officeCode : officeCode,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		for ( var i = 0; i < len; i++) {
			var branch = data[i].customerId;
			branchCode.push(branch);
		}
		
		options += '<option value="0" selected="selected">Select</option>';
		//options += '<option value="0">ALL</option>';
		
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].customerId + '">'+ data[i].bussinessName + '</option>';
		}
		
		$("#bussinessName").html(options); 
		
		hideProcessing();
	});

	
}


	function getVendorNameByVendorType(vendorId,officeCode){
		//alert(vendorId+'----'+officeCode);
		var branchCode = new Array();
		var branchCode;
		
		showProcessing();
		
		$.getJSON("getVendorNameByVendorId.htm",{
			vendorId : vendorId,
			officeCode : officeCode,
			
		}, function(data) {
			var options = '';
			var len = data.length;
			
			for ( var i = 0; i < len; i++) {
				var branch = data[i].vendorId;
				branchCode.push(branch);
			}
			
			options += '<option value="0" selected="selected">Select</option>';
			options = '<option value="'+branchCode+'">ALL</option>';
			
			
			 for ( var i = 0; i < len; i++) {
				options += '<option value="' + data[i].vendorId + '">'+ data[i].vendorName + '</option>';
			}
			$("#vendorName").html(options); 
			
			hideProcessing();
		});
	
		
	}

/*
 * This method use for Only BA customer
 */
function getBACustomerNameFromOfficeCode(officeCode){
	
	//alert(officeCode);
	showProcessing();
	
	$.getJSON("getBACustomerNameFromOfficeCode.htm", {
		officeCode : officeCode,
		
	}, function(data) {
		var options = '';
		options += '<option value="0" selected="selected">Select</option>';
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].customerId + '">'+ data[i].bussinessName + '</option>';
		}
		
		$("#bussinessName").html(options); 
		
		hideProcessing();
	});

	
}


/*
 * This is for get customer on product name
 */


function getCustomerNameFromProductId(productId){

	showProcessing();
	
	$.getJSON("getCustomerNameFromProductId.htm", {
		productId : productId,
		
	}, function(data) {
		var options = '';
		
		options += '<option value="0" selected="selected">Select</option>';
		var len = data.length;
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].customerId + '">'+ data[i].bussinessName + '</option>';
		}
		
		$("#bussinessName").html(options); 
		
		hideProcessing();
	});

	
}

/*
This fuction use for get customer on productId and officeCode
 */
function getCustomerByProductId(productId,officeCode){
	//alert(productId+'----'+officeCode);
	var branchCode = new Array();
	var branchCode;

	showProcessing();
	
	$.getJSON("getCustomerByProductId.htm",{
		productId : productId,
		officeCode : officeCode,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		if(len==0)
			{
			alert("No Customer Available");
			}
		
		for ( var i = 0; i < len; i++) {
			var branch = data[i].customerId;
			branchCode.push(branch);
		}
		
		options += '<option value="0" selected="selected">Select</option>';
		/*options += '<option value="' + branchCode + '">ALL</option>';*/
		
	
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].customerId + '">'+ data[i].bussinessName + '</option>';
		}
		 
		// hideProcessing();
		 
		$("#bussinessName").html(options); 
		
		 hideProcessing();
	});

	
}

function getMonthValues(values){
	//alert(values+'----'+values);
	var branchCode = new Array();
	var branchCode;

	showProcessing();
	
	$.getJSON("getMonthWiseDate.htm",{
		
		values : values,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		if(len==0)
			{
			alert("Only Current Four Months Data is Available");
			}
		
		for ( var i = 0; i < len; i++) {
			var branch = data[i].startdate;
		//	branchCode.push(branch);
		}
		
		options += '<option value="0" selected="selected">Select</option>';
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].startdate + '">'+ data[i].startdate + '</option>';
		}
		 
		// hideProcessing();
		 
		$("#startdate").html(options); 
		
		 hideProcessing();
	});

	
}

function getMonthValuesForEndDate(values){
	//alert(values+"getMonthValuesForEndDate"+values);
	var branchCode = new Array();
	var branchCode;
	
	showProcessing();
	
	$.getJSON("getMonthWiseDate.htm",{
		
		values : values,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		for ( var i = 0; i < len; i++) {
			var branch = data[i].enddate;
		
		}
		
		options += '<option value="0" selected="selected">Select</option>';
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].enddate + '">'+ data[i].enddate + '</option>';
		}
		 
		
		 
		$("#enddate").html(options); 
		
		 hideProcessing();
	});

	
}

function getVendorTypes(values){
	//alert(values+"values"+values);
	showProcessing();
	
	$.getJSON("getVendorTypes.htm",{
		
		values : values,
		
	}, function(data) {
		var options = '';
		var len = data.length;
		
		
		
		options += '<option value="0" selected="selected">Select</option>';
		options += '<option value="00000">Field Staff</option>';
		
		 for ( var i = 0; i < len; i++) {
			options += '<option value="' + data[i].vendorId + '">'+ data[i].vendorName + '</option>';
		}
		 
		
		// alert(values+"options"+options);
		$("#vendorTypeId").html(options); 
		
		 hideProcessing();
	});

	
}


/**
 * To show processing image
 */
function showProcessing() {
	jQuery.blockUI({
		message : '<img src="ud/images/loading_animation.gif"/>'
	});
}


/**
 * To hide processing image
 */
function hideProcessing() {
	jQuery.unblockUI();
}
