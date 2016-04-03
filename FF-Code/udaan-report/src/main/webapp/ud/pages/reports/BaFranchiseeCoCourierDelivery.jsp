<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="/ud/include/taglib_includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- <head> -->
<%-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome To UDAAN Web Report</title>
		<link href="${csspath}/css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="${csspath}/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="${csspath}/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="${csspath}/global.css" rel="stylesheet" type="text/css" />
		<link href="${csspath}/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<link href="${csspath}/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${javascriptpath}/jquerydropmenu.js"></script><!-- DataGrids -->
		<link rel="stylesheet" href="${csspath}/jquery-ui.css"></link> --%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="ud/css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="ud/css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/report/commonReport.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/report/consignmentBookingDetails.js"></script>
<script type="text/javascript" charset="utf-8"
	src="ud/static/js/commenScript.js"></script>
</head>


<script>
	var BRANCH_NAME = '${brachName}';

	function getOffices(cityId) {
		//showProcessing();
		var branchCode = new Array();
		var isAvailable = false;
		var branchCode;
		$
				.getJSON(
						"getOfficesByCityId.htm",
						{
							cityId : cityId,

						},
						
						function(data) {
							var options = '';
							var optionsValues = '';
							var len = data.length;
							for ( var i = 0; i < len; i++) {
								var branch = data[i].officeCode;
								branchCode.push(branch);
								
							}
							
							options += '<option value="0" selected="selected" >Select</option>';
							options += '<option value="' + branchCode + '">ALL</option>'; 
							
							for ( var i = 0; i < len; i++) {
								var dbValue = data[i].officeCode.toUpperCase();
								var loginValue = BRANCH_NAME.toUpperCase();
								var branchName = data[i].officeName
										.toUpperCase();
								var value = data[i].officeCode;

								/*  options = '<option value="' + branchCode + '">ALL</option>';  */
								if (data[i].officeName.toUpperCase() == BRANCH_NAME
										.toUpperCase()) {
									isAvailable = true;
									branchCode = data[i].officeCode;
								}
								
								options += '<option value="' + data[i].officeCode + '">'
										+ data[i].officeName + '</option>';
								//alert(options);
							}
							//alert(isAvailable);

							if (isAvailable) {
								options = '<option value="' + branchCode + '">'
										+ BRANCH_NAME + '</option>';
								
							}
							
							//alert(options);
							$("#officeCode").html(options);
							hideProcessing();

						});

	}

	function getOfficesForALLOriginBranchs(officeCode) {
		showProcessing();
		//alert(officeCode);
		if (officeCode == 'ALL') {
			var branchCode = new Array();
			$
					.getJSON(
							"getOfficesForALLOriginBranchs.htm",
							{
								officeCode : officeCode,

							},
							function(data) {
								var options = '';

								options = '<option value="0" selected="selected">Select</option>';

								var len = data.length;
								for ( var i = 0; i < len; i++) {
									var branch = data[i].officeCode;
									branchCode.push(branch);

									options = '<option value="' + branchCode + '">ALL</option>';

								}
								$("#officeCodeValue").html(options);
							});

		}

	}
	
	
	/**
	 * calculateDateDiff
	 *@param: fromDate : From date Object 
	 *@param: toDate : toDate date Object
	 @return: difference between two days (in days as int)
	 */
	function calculateDateDiff(fromDate,toDate){
		var _fromDate =fromDate.split("/");
		var  _toDate = toDate.split("/");
		//alert(" _heldUpDate  &&&&&   "+_heldUpDate);
		var _fromDateObj = new Date(_fromDate[2],_fromDate[1]-1,_fromDate[0], 00);
		var _toDateObj = new Date(_toDate[2],_toDate[1]-1,_toDate[0], 00);
		var oneday = 24*60*60*1000;
		var calculateDate=_toDateObj-_fromDateObj;
		var dateDiff=Math.ceil(calculateDate/oneday);
		//alert(dateDiff);
		return dateDiff;
	}
	

	function logoutUser() {
		var url = "./logout.do?submitName=logoutUser";
		window.location = url;
	}

	function returnLoginPage() {
		var url = "./home.do";
		window.location = url;

	}

	function printReport() {

		var originRegion=$("#region" ).val();
		var originCity=$("#city1" ).val();
		var branch=$("#officeCode" ).val(); 
		var vendorId=$("#vendorTypeId").val();
		var  StartDate=$("#fromDate" ).val();
		var  EndDate=$("#fromDate2" ).val();
		var  vendorCode=$("#vendorName" ).val();
		var  deliveryPickUpId=$("#deliveryPickUpId" ).val();
		   
		
		var originRegionName = document.getElementById('region').options[document.getElementById('region').selectedIndex].text;
		var originCityName = document.getElementById('city1').options[document.getElementById('city1').selectedIndex].text;
		var originBranchName = document.getElementById('officeCode').options[document.getElementById('officeCode').selectedIndex].text;	
		var vendorIdValue = document.getElementById('vendorTypeId').options[document.getElementById('vendorTypeId').selectedIndex].text;
		var vendorName = document.getElementById('vendorName').options[document.getElementById('vendorName').selectedIndex].text;
		var deliveryPickUpName = document.getElementById('deliveryPickUpId').options[document.getElementById('deliveryPickUpId').selectedIndex].text;
		
		/* var productName = document.getElementById('productId').options[document.getElementById('productId').selectedIndex].text; */
		

		        var originRegion1 = document.getElementById('region').value;
				  if(originRegion1 =="0" || originRegion1 =='' || originRegion1==null){
				          alert('Please select Origin region');
						  return false;
	               }
		
		      var originStation = document.getElementById('city1').value; 
				  if(originStation =="0" || originStation =='' || originStation==null){
				          alert('Please select Origin Station');
						  return false;
	               } 
		     var originBranch = document.getElementById('officeCode').value;
				  if(originBranch =="0" || originBranch =='' || originBranch==null){
				          alert('Please select Origin Branch');
						  return false;
	               }
				  
				  
		    
		
				  var StartDate = document.getElementById('fromDate').value;
				  if(StartDate =="0" || StartDate =='' || StartDate==null){
				          alert('Please select Start Date');
						  return false;
	               }
				  
				  var EndDate = document.getElementById('fromDate2').value;
				  if(EndDate =="0" || EndDate =='' || EndDate==null){
				          alert('Please select End Date');
						  return false;
	               }
				  
				  var bussinessName = document.getElementById('vendorTypeId').value;
				  if(bussinessName =='' || bussinessName==null || bussinessName=="0"){
				          alert('Please select VendorType');
						  return false;
	               }
				  
				  var statusId = document.getElementById('vendorName').value;
				  if(statusId =='' || statusId==null || statusId=="0"){
				          alert('Please select Vendor Name');
						  return false;
	               }
	               
	              var deliveryId = document.getElementById('deliveryPickUpId').value;
				  if(deliveryId =='' || deliveryId==null || deliveryId=="0"){
				          alert('Please select Type');
						  return false;
	               }
	               
	               
				  
				  var noOfDays=calculateDateDiff(StartDate,EndDate);
					if (noOfDays > 30) {
						alert("User Restriction:Date Range should not be greated than 31 days:");

					    $("#fromDate2").val("");

						$("#fromDate2").focus(); 
						return false;
					}
					
					
				/* alert(deliveryPickUpId+"=====>deliveryPickUpId");
				alert(deliveryPickUpName+"=====>deliveryPickUpName");
					
				 	alert(originRegion+"=====>originRegion");
					alert(originCity+"=====>originCity");
					alert(branch+"=====>branch");
					alert(vendorId+"=====>vendorId");
					alert(StartDate+"=====>StartDate");
					alert(EndDate+"=====>EndDate");
					alert(vendorCode+"=====>vendorCode");
					alert(originRegionName+"=====>originRegionName");
					alert(originCityName+"=====>originCityName");
					alert(originBranchName+"=====>originBranchName");
					alert(vendorIdValue+"=====>vendorIdValue");
					alert(vendorName+"=====>vendorName"); */
		 
		 
		 if(deliveryPickUpId=='1'){
		 
		     var url = '/udaan-report/ud/pages/reportveiwer/BaFranchiseeCoCourierDeliveryReportViewer.jsp?'
					 + "startDate="+StartDate
					 + "&EndDate="+EndDate
					 + "&originCity="+originCity          
					 + "&branch="+branch
					 + "&vendorId="+vendorId  
					 + "&vendorCode="+vendorCode 
					 + "&originRegionName="+originRegionName
					 + "&originCityName="+originCityName  
					 + "&originBranchName="+originBranchName  
					 + "&vendorIdValue="+vendorIdValue 
					 + "&vendorName="+vendorName
		             + "&deliveryPickUpName="+deliveryPickUpName; 
				  //alert(url);
					window.open(url, "_blank");  
					
					return true;
		 
		 }else{
			 
			  var url = '/udaan-report/ud/pages/reportveiwer/BaFranchiseeCoCourierPickupReportvViewer.jsp?'
				 + "startDate="+StartDate
				 + "&EndDate="+EndDate
				 + "&originCity="+originCity          
				 + "&branch="+branch
				 + "&vendorId="+vendorId  
				 + "&vendorCode="+vendorCode 
				 + "&originRegionName="+originRegionName
				 + "&originCityName="+originCityName  
				 + "&originBranchName="+originBranchName  
				 + "&vendorIdValue="+vendorIdValue 
				 + "&vendorName="+vendorName
			     + "&deliveryPickUpName="+deliveryPickUpName; 
			 // alert(url);
				window.open(url, "_blank");  
				
				return true;
			  
		 
		// alert("Comming soon ");
		 
		 }
		 
				  
					

	}
	
</script>
<script type="text/javascript" charset="utf-8"
	src="${javascriptpath}/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${javascriptpath}/FixedColumns.js"></script>
	

<!-- DataGrids /-->
<!-- </head> -->
<body>
	<!--wraper-->
	<div id="wraper">
		<div id="main-header-int">
			<div class="fflogo" title="First Flight Couriers Ltd."
				onclick="returnLoginPage();"></div>
			<div class="welcomeMsg">

				Welcome to UDAAN, <strong> <c:if test="${user ne null}">
						${welcomeUserName}&nbsp;|
						 <a href="#" title="Logout" style="color: red;"
							onclick="logoutUser();"><strong>Logout</strong></a>
						<br />
					</c:if>
				</strong> Logged In Office: <strong>${user.officeTo.officeTypeTO.offcTypeDesc}-${user.officeTo.officeName}</strong>&nbsp;|
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate value="${now}" pattern="EEE, d MMM yyyy hh:mm a" />
				<br /> <a href="changePassword.do?submitName=showChangePassword"
					style="color: white;"> Change Password</a>


			</div>
		</div>
		<div id="topPanel">
			<div id="top_nav">
				<ul id="top_nav_menu">

					<!-- ############# Header Menu Start ############### -->
					<c:forEach var="item" items="${udaanTreeMenus}">
						<li><a title="${item.key}"><strong><c:out
										value="${item.key}" /></strong></a>
							<ul>

								<!-- ############# Sub Menu1 Start ############### -->
								<c:forEach var="menuNode" items="${item.value.menuNodeDOs}">
									<li><c:if test="${empty menuNode.applScreenDO}">
											<!-- var1 is empty or null. -->
											<a title="${menuNode.menuLabel}"><strong><c:out
														value="${menuNode.menuLabel}" /></strong></a>

											<ul>
												<!-- ############# Sub Menu2 Start ############### -->
												<c:forEach var="subMenuNode" items="${menuNode.menuNodeDOs}">
													<li><c:if test="${empty subMenuNode.applScreenDO}">
															<!-- var1 is empty or null. -->
															<a title="${subMenuNode.menuLabel}"><strong><c:out
																		value="${subMenuNode.menuLabel}" /></strong></a>

															<ul>
																<!-- ############# Sub Menu3 Start ############### -->
																<c:forEach var="subMenuNode2"
																	items="${subMenuNode.menuNodeDOs}">
																	<li><c:if
																			test="${not empty subMenuNode2.applScreenDO}">
																			<c:choose>
																				<c:when
																					test="${subMenuNode2.applScreenDO.appName eq 'centralized'}">


																					<a
																						href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${subMenuNode2.applScreenDO.screenCode}'/>&moduleName=<c:out value='${subMenuNode2.applScreenDO.moduleName}'/>&appName=udaan-config-admin"
																						target="_blank"><c:out
																							value='${subMenuNode2.applScreenDO.screenName}' /></a>


																				</c:when>

																				<c:otherwise>
																					<a
																						href="<c:out value='${subMenuNode2.applScreenDO.urlName}'/>"><c:out
																							value='${subMenuNode2.applScreenDO.screenName}' /></a>
																				</c:otherwise>
																			</c:choose>

																		</c:if></li>
																</c:forEach>
																<!-- ############# Sub Menu3 End ############### -->
															</ul>

														</c:if> <c:if test="${not empty subMenuNode.applScreenDO}">
															<c:choose>
																<c:when
																	test="${subMenuNode.applScreenDO.appName eq 'centralized'}">


																	<a
																		href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${subMenuNode.applScreenDO.screenCode}'/>&moduleName=<c:out value='${subMenuNode.applScreenDO.moduleName}'/>&appName=udaan-config-admin"
																		target="_blank"><c:out
																			value='${subMenuNode.applScreenDO.screenName}' /></a>


																</c:when>

																<c:otherwise>
																	<a
																		href="<c:out value='${subMenuNode.applScreenDO.urlName}'/>"><c:out
																			value='${subMenuNode.applScreenDO.screenName}' /></a>
																</c:otherwise>
															</c:choose>
														</c:if></li>
												</c:forEach>
												<!-- ############# Sub Menu2 End ############### -->
											</ul>

										</c:if> <c:if test="${not empty menuNode.applScreenDO}">
											<c:choose>
												<c:when
													test="${menuNode.applScreenDO.appName eq 'centralized'}">


													<a
														href="/udaan-web/login.do?submitName=silentLoginToApp&screenCode=<c:out value='${menuNode.applScreenDO.screenCode}'/>&moduleName=<c:out value='${menuNode.applScreenDO.moduleName}'/>&appName=udaan-config-admin"
														target="_blank"><c:out
															value='${menuNode.applScreenDO.screenName}' /></a>


												</c:when>

												<c:otherwise>
													<a href="<c:out value='${menuNode.applScreenDO.urlName}'/>"><c:out
															value='${menuNode.applScreenDO.screenName}' /></a>
												</c:otherwise>
											</c:choose>
										</c:if></li>
								</c:forEach>
								<!-- ############# Sub Menu1 End ############### -->
							</ul></li>
					</c:forEach>
					<!-- ############# Header Menu End ############### -->

				</ul>
				<script type="text/javascript">
					var topLevelUL = "#top_nav_menu";
				</script>
				<script type="text/javascript" src="js/top-menu.js"></script>
			</div>
		</div>

		<div class="clear" />






		<%-- <%@include file="/ud/include/menubar.jsp"%> --%>
		<!--header ends-->
		<div class="clear"></div>
		<!-- main content -->
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<font size="3"><center>
							<h1>BA/Co-Couries/Franchisee/FS Delivery Report</h1>
						</center></font>
					<div class="mandatoryMsgf">
						<span class="mandatoryf">*</span> Fields are Mandatory
					</div>
				</div>
				<div></div>

				<div id="errorMessage">
					<div class="alert-box notice">
						<span>
							<!-- notice: -->
						</span>

					</div>
				</div>



				<form:form name="frm" method="post"
					action="BaFranchiseeCoCourierDelivery.htm" commandName="branchDO">
                   
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">

		<tr>
		<td width="24%" class="lable"><span class="mandatoryf">*</span>Region</td>
		<td width="17%">
					
		 <c:choose>
		 
               <c:when test="${officeId eq  '3' && regionList.size()==1 }">
				<form:select path="region" id="region" class="selectBox width130"  onchange="getCitiesByRegCodeFindOriginSta(this.value,1)"  disabled="true">
				 <!-- 	<option value="0">Select</option>  -->
				<form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				</form:select></td>
               </c:when>
                                     
			    <c:when test="${officeId eq  '3'}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>
								      
				<c:when test="${officeId eq '5' && regionList.size()==1}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCitiesByRegCodeFindOriginSta(this.value,1)" disabled="true">
				 <!-- <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
		        </c:when>
								     
			    <c:when test="${officeId eq '5'}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
			    </c:when>
								      
				<c:when test="${regionList.size()==1}">
				 <form:select path="region" id="region"	class="selectBox width130" onchange="getCitiesByRegCode(this.value,1)" disabled="true">
				 <!--   <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>      
			    <c:otherwise>
				 <form:select path="region" id="region"	class="selectBox width130" onchange="getCitiesByRegCode(this.value,1)" >
				  <option value="0">Select</option> 
				  <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				  </form:select></td>
				</c:otherwise>
		  </c:choose>
								
								
		<td width="24%" class="lable"><span class="mandatoryf">*</span>Station</td>
		<td width="17%">
								
								 
		 <c:choose>
			<c:when test="${regionList.size()==1 && cityDTO.size()>1 }" >
			   <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)" >
			   <option value="0">Select</option>
			   <form:options items="${cityDTO}" itemValue="cityId" itemLabel="cityName" />
	           </form:select></td>
		    </c:when>
            <c:when test="${cityList.size()==1}">
			  <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)" disabled="true" >
			  <!-- <option value="0">Select</option> -->
			  <form:options items="${cityList}" itemValue="cityId" itemLabel="cityName" />
	          </form:select></td>
			</c:when>
			<c:when test="${regionList.size()==1}" >
			   <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)"  >
                <option value="0">Select</option> 
			   <form:options items="${cityDTO}" itemValue="cityId" itemLabel="cityName" />
			   </form:select></td>
			</c:when>
										        
			<c:otherwise>
			  <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)" >
	          <option value="0">Select</option>
			  <form:options items="${cityDTO}" itemValue="cityId" itemLabel="cityName"/>
              </form:select></td>
			</c:otherwise>
	    </c:choose>
						
			 <c:choose>
				   <c:when test="${offices.size() > 1 && cityList.size() == 1}">
					  <td width="24%" class="lable"><span class="mandatoryf">*</span>Branch</td>
					  <td width="17%">
					  <form:select path="branchCode"	id="officeCode" class="selectBox width130">
					  <option value="ALL">select</option>
					  <form:options items="${offices}" itemValue="officeCode" itemLabel="officeName"/>
					  </form:select></td>
				  </c:when>
				  <c:when test="${cityList.size() == 1}">
					 <td width="24%" class="lable"><span class="mandatoryf">*</span>Branch</td>
					 <td width="17%">
					 <form:select path="branchCode"	id="officeCode" class="selectBox width130" >
					 <option value="${officeCode}">${officeName}</option>
					 </form:select></td>
				 </c:when>
				 <c:otherwise>
					<td width="24%" class="lable"><span class="mandatoryf">*</span>Branch</td>
					<td width="17%">
					<form:select path="branchCode"	id="officeCode" class="selectBox width130" onchange="getVendorTypes(this.value)">
			        <option value="0">Select</option>
				    <%-- <option value="ALL">ALL</option>  --%>
					</form:select></td>
                    </c:otherwise>
                 </c:choose> 			
									
									
								<td colspan="1"><br /> <br /></td>
							</tr>

							<tr>
								<td class="lable"><sup class="star">* </sup>Start Date<bean:message />
								</td>
								<td>&nbsp;<input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly" ><a
									onclick="javascript:show_calendar('fromDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="15" height="16" border="0"
										class="imgsearch" /></a></td>
										
										
							<td class="lable"><sup class="star">*</sup>End Date<bean:message/> </td>
								<td><input type="text" name="to.fromDate2"
									style="height: 20px" value="" id="fromDate2"
									class="txtbox width130" readonly="readonly" ><a
										onclick="javascript:show_calendar('fromDate2', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
											
							<td colspan="2"><br /> <br /></td>							
						
							</tr>
							
									
					<tr>
					
					<td width="24%" class="lable"> <span class="mandatoryf">*</span>Vendor Type</td>
              <td width="16%">		
              <form:select  path="vendorTypeId" id="vendorTypeId" class="selectBox width130" onchange="getVendorNameByVendorType(this.value,document.getElementById('officeCode').value)">
              <option value="0" >Select</option>
             <!--  <option value="00000" >Field Staff</option>
               <form:options items="${vendorType}" itemValue="vendorId" itemLabel="vendorName" /> -->
                 
             	</form:select></td>
             
                <td width="24%" class="lable"> <span class="mandatoryf">*</span>Vendor Name</td>
			  <td width="16%"> 
			 <form:select path="vendorTypeId" id="vendorName"  class="selectBox width130">
             		<option value="0">Select</option>    
                 </form:select> 
               </td>
               
				<td width="24%" class="lable"> <span class="mandatoryf">*</span>Type</td>
			  <td width="16%"> 
			 <form:select path="vendorTypeId" id="deliveryPickUpId"  class="selectBox width130">
             		<!--<option value="0">Select</option>--> 
             		<option value="1">Delivery</option>
             		<option value="2">PickUp</option>    
                 </form:select> 
               </td>		
										
								<td colspan="2"><br /> <br /></td>
							</tr>
							<!-- Button -->

						</table>
					</div>
					<!-- formTable End -->

				</form:form>
			</div>
		</div>
		<tr>

			<td>
				<div class="button_containerform">
					<!--  <input name="Submit" type="submit" class="btnintform" value='Submit' title="Submit" /> -->
					<input name="Submit" type="submit" class="btnintform"
						value='Submit' title="Submit" onclick="printReport();"> <input
						name="cancel" value="Cancel"
						onclick="clearScreen();clearMultiSelect('city1','region');clearFields('officeCode');"
						id="cancelBtn" class="btnintform" type="button">
				</div>
			</td>
			<!-- Button ends -->

		</tr>


		<div id="main-footer">
			<div id="footer">&copy; 2012 Copyright First Flight Couriers
				Ltd. All Rights Reserved. This site is best viewed with a resolution
				of 1024x768.</div>
		</div>
		<!-- footer ends -->

		<!-- wrapper ends -->
	</div>
</body>
</html>




