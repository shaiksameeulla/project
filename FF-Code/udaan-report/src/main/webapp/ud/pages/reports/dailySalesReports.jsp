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


<script type="text/javascript">
	function validate() {
		// validate

		<!--
		var region = document.getElementById('region').value;

		if (region == "0" || region == " " || region == null) {
			alert('Please select region');
			return false;
		}

		var cityId = document.getElementById('city1').value;

		if (cityId == "0" || cityId == " " || cityId == null) {
			alert('Please select city');
			return false;
		}

		var officeCode = document.getElementById('officeCode').value;

		if (officeCode == "0" || officeCode == "" || officeCode == null) {
			alert('Please select offices');
			return false;
		}

		var date1 = document.getElementById('datepicker').value;
		var date2 = document.getElementById('datepicker2').value;

		if (date1 == "" || date1 == " " || date1 == null) {
			/* alert('Please enter the start Date.'); */
			return false;
		}
		if (date2 == "" || date2 == null) {
			alert('Please enter the end Date.');
			return false;
		}
		var startDate = date1.split("/");
		var endDate = date2.split("/");
		var date3 = new Date(startDate[2], startDate[0] - 1, startDate[1]);
		var date4 = new Date(endDate[2], endDate[0] - 1, endDate[1]);

		if (date3 > date4) {
			alert('End date cannot be less than Start date');
			return false;
		}
		//window.location ="/udaan-report/ud/controller/DeliveryRunsheetReportController?userName="+${user.officeTo.officeName};

	}
</script>

<script>

/* 
var BRANCH_NAME= '${brachName}';

function getOffices(cityId,cityName){

	var branchCode= new Array();
	var isAvailable = false;
	var branchCode;
	$.getJSON("getOfficesByCityId.htm", {
		cityId : cityId,
		
	}, function(data) {
		var options = '';
		var optionsValues = '';
		var len = data.length;
		for(var i = 0; i < len; i++){
			var branch=data[i].officeCode;
			   branchCode.push(branch);
		}
		options += '<option value="0" selected="selected" >Select</option>';
		options = '<option value="' + branchCode + '">ALL</option>';
		
		
		 for ( var i = 0; i < len; i++) {
			var dbValue =data[i].officeCode.toUpperCase();
			var loginValue =BRANCH_NAME.toUpperCase();
			var branchName=data[i].officeName.toUpperCase();
			var value=data[i].officeCode;
			 
			  /*  options = '<option value="' + branchCode + '">ALL</option>';  */
			/*if(data[i].officeName.toUpperCase() == BRANCH_NAME.toUpperCase()) {
				isAvailable = true;
				branchCode = data[i].officeCode;
			}
			
			
			options += '<option value="' + data[i].officeCode + '">'
            + data[i].officeName + '</option>';
			//alert(options);
		}
		
		
		/* if(isAvailable) {
			//options = '<option value=' + branchCode + 'selected="selected">'+BRANCH_NAME+'</option>' ;
			options = '<option value=' + branchCode + '>'+BRANCH_NAME+'</option>' ;
		}  
		//alert(options);
		$("#officeCode").html(options); 
		
	});

	
} */

var BRANCH_NAME= '${brachName}';

function getOffices(cityId,cityName){

	var branchCode= new Array();
	var isAvailable = false;
	var branchCode;
	$.getJSON("getOfficesByCityId.htm", {
		cityId : cityId,
		
	}, function(data) {
		var options = '';
		var optionsValues = '';
		var len = data.length;
		for(var i = 0; i < len; i++){
			var branch=data[i].officeCode;
			   branchCode.push(branch);
		}
		options += '<option value="0" selected="selected" >Select</option>';
	    options = '<option value="' + branchCode + '">ALL</option>';
		
		
		 for ( var i = 0; i < len; i++) {
			var dbValue =data[i].officeCode.toUpperCase();
			var loginValue =BRANCH_NAME.toUpperCase();
			var branchName=data[i].officeName.toUpperCase();
			var value=data[i].officeCode;
			 
			  /*  options = '<option value="' + branchCode + '">ALL</option>';  */
			/*if(data[i].officeName.toUpperCase() == BRANCH_NAME.toUpperCase()) {
				isAvailable = true;
				branchCode = data[i].officeCode;
			}*/
			
			
			options += '<option value="' + data[i].officeCode + '">'
            + data[i].officeName + '</option>';
			//alert(options);
		}
		
		
		/* if(isAvailable) {
			//options = '<option value=' + branchCode + 'selected="selected">'+BRANCH_NAME+'</option>' ;
			options = '<option value=' + branchCode + '>'+BRANCH_NAME+'</option>' ;
		}  */
		//alert(options);
		$("#officeCode").html(options); 
		
	});
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

		var destRegion1 = $("#destOrigin").val();
		var destStation = $("#city1").val();
		var destBranch = $("#officeCode").val();
		var Date = $("#fromDate").val();
		var Date1 = $("#toDate").val();
		
		/* alert("Start Date:"+Date);
		alert("End Date:"+Date1);
		alert("Region:"+destRegion1);
		alert("Origin City:"+destStation);
		alert("Branch:"+destBranch);
 */
		var destRegionName = document.getElementById('destOrigin').options[document
				.getElementById('destOrigin').selectedIndex].text;
		var destStationName = document.getElementById('city1').options[document
				.getElementById('city1').selectedIndex].text;
		var destBranchName = document.getElementById('officeCode').options[document
				.getElementById('officeCode').selectedIndex].text;
	     


			

		var destRegion1 = document.getElementById('destOrigin').value;
		if (destRegion1 == "0" || destRegion1 == '' || destRegion1 == null) {
			alert('Please select Region');
			return false;
		}

		var destStation = document.getElementById('city1').value;
		if (destStation == "0" || destStation == '' || destStation == null) {
			alert('Please select Station');
			return false;
		}
		var destBranch = document.getElementById('officeCode').value;
		if (destBranch == "0" || destBranch == '' || destBranch == null) {
			alert('Please select Branch');
			return false;
		}

		var Date = document.getElementById('fromDate').value;
		if (Date == "0" || Date == '' || Date == null) {
			alert('Please select Start Date');
			return false;
		}
		
		var Date1 = document.getElementById('toDate').value;
		if (Date1 == "0" || Date1 == '' || Date1 == null) {
			alert('Please select End Date');
			return false;
		}
       

		var noOfDays=calculateDateDiff(Date,Date1);
		if (noOfDays > 6) {
			alert("User Restriction:Date Range should not be greated than 7 days:");

		    $("#fromDate").val("");

			$("#fromDate").focus(); 
			return false;
		}
		

		var url = '/udaan-report/ud/pages/reportveiwer/dailySalesReportViewer.jsp?'
				+ "fromDate="
				+ Date
				+ "&endDate="
				+ Date1
				+ "&originRegion="
				+ destRegion1
				+ "&branch="
				+ destBranch
				+ "&originRegionName="
				+ destRegionName
				+ "&branchName="
				+ destBranchName
				+ "&stationName="
				+ destStationName;

		/* alert(url); */ 
		
		window.open(url, "_blank");
		
		

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
							<h1>Daily Sales Report - Short</h1>
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



				<form:form name="frm" method="post" action="dailySalesReports.htm" commandName="branchDO">

	<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">

							<tr>
		<td width="24%" class="lable"><span class="mandatoryf">*</span> Region</td>
		<td width="17%">
		                  
		 <c:choose>
               <c:when test="${officeId eq  '3' && regionList.size()==1 }">
				<form:select path="region" id="destOrigin" class="selectBox width130"    disabled="true">
				 <!-- 	<option value="0">Select</option>  -->
				<form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				</form:select></td>
               </c:when>
                                     
			    <c:when test="${officeId eq  '3'}">
				 <form:select path="region" id="destOrigin" class="selectBox width130" onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>
								      
				<c:when test="${officeId eq '5' && regionList.size()==1}">
				 <form:select path="region" id="destOrigin" class="selectBox width130"  disabled="true">
				 <!-- <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
		        </c:when>
								     
			    <c:when test="${officeId eq '5'}">
				 <form:select path="region" id="destOrigin" class="selectBox width130" onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
			    </c:when>
								      
				<c:when test="${regionList.size()==1}">
				 <form:select path="region" id="destOrigin"	class="selectBox width130"  disabled="true">
				 <!--   <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>      
			    <c:otherwise>
				 <form:select path="region" id="destOrigin"	class="selectBox width130" onchange="getCitiesByRegCode(this.value,1)" >
				  <option value="0">Select</option> 
				  <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				  </form:select></td>
				</c:otherwise>
		  </c:choose>
		  
		  <td width="24%" class="lable"><span class="mandatoryf">*</span> Station</td>
          <td width="17%">
          <c:choose>
			<c:when test="${regionList.size()==1 && cityDTO.size()>1 }" >
			   <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)" >
			   <option value="0">Select</option>
			   <form:options items="${cityDTO}" itemValue="cityId" itemLabel="cityName" />
	           </form:select></td>
		    </c:when>
            <c:when test="${cityList.size()==1}">
			  <form:select path="cityId" id="city1" class="selectBox width130"  disabled="true" >
			  <form:options items="${cityList}" itemValue="cityId" itemLabel="cityName" />
	          </form:select></td>
			</c:when>
			<c:when test="${regionList.size()==1}" >
			   <form:select path="cityId" id="city1" class="selectBox width130" onchange="getOffices(this.value)" >
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
         </td>  
         
         <c:choose>
				   <c:when test="${offices.size() > 1 && cityList.size() == 1}">
					  <td width="24%" class="lable"><span class="mandatoryf">*</span> Branch</td>
					  <td width="17%">
					  <form:select path="branchCode"	id="officeCode" class="selectBox width130">
					  <option value="ALL">select</option>
					  <form:options items="${offices}" itemValue="officeCode" itemLabel="officeName"/>
					  </form:select></td>
				  </c:when>
				  <c:when test="${cityList.size() == 1}">
					 <td width="24%" class="lable"><span class="mandatoryf">*</span> Branch</td>
					 <td width="17%">
					 <form:select path="branchCode"	id="officeCode" class="selectBox width130" disabled="true">
					 <option value="${officeCode}">${officeName}</option>
					 </form:select></td>
				 </c:when>
				 <c:otherwise>
					<td width="24%" class="lable"><span class="mandatoryf">*</span> Branch</td>
					<td width="17%">
					<form:select path="branchCode"	id="officeCode" class="selectBox width130">
			        <option value="0">Select</option>
					</form:select></td>
                  </c:otherwise>
                 </c:choose>
		                         <%--    <c:choose>
										<c:when test="${officeId eq  '3'}">
											<form:select path="region" id="destOrigin"
												class="selectBox width130"
												onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
												<option value="0">Select</option>
												<form:options items="${regionList}" itemValue="regionCode"
													itemLabel="regionName" />
											</form:select></td>
								</c:when>
								<c:when test="${officeId eq '5'}">
									<form:select path="region" id="destOrigin"
										class="selectBox width130"
										onchange="getCitiesByRegCodeFindOriginSta(this.value,1)">
										<option value="0">Select</option>
										<form:options items="${regionList}" itemValue="regionCode"
											itemLabel="regionName" />
									</form:select>
									</td>
								</c:when>
								<c:otherwise>
									<form:select path="region" id="destOrigin"
										class="selectBox width130"
										onchange="getCitiesByRegCode(this.value,1)">
										<option value="0">Select</option>
										<form:options items="${regionList}" itemValue="regionCode"
											itemLabel="regionName" />
									</form:select>
									</td>
								</c:otherwise>
								</c:choose> --%>
								
								
								<%-- <td width="24%" class="lable"><span class="mandatoryf">*</span>Station</td>
								<td width="17%">
								<form:select path="cityId" id="city1"
										class="selectBox width130" onchange="getOffices(this.value)">
										<option value="0">Select</option>
										<!-- <option value="ALL" >ALL</option> -->
										<form:options items="${cityList}" itemValue="cityId" itemLabel="cityName"/>
									</form:select></td> --%>
									
								<%-- <td width="24%" class="lable"><span class="mandatoryf">*</span>Branch</td>
								<td width="17%"><form:select path="branchCode"
										id="officeCode" class="selectBox width130">
										<option value="0">Select</option>
										<option value="ALL">ALL</option> 
									</form:select></td> --%>

							</tr>


							<tr>
								<td class="lable"><sup class="star">* </sup>Start Date<bean:message/></td>
								<td>&nbsp;<input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly"> <a
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="11" height="16" border="0"
											class="imgsearch" /></a></td>
								<td class="lable"><sup class="star">* </sup>End Date<bean:message
										key="stock.label.req.to.date" /></td>
								<td>&nbsp;<input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> <a
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="11" height="16" border="0"
											class="imgsearch" /></a></td>
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
						onclick="clearScreen();clearMultiSelect('city1');clearFields('officeCode');"
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




