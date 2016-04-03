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


function getCustomerList(id) {
	var region = id;
	if (!isNull(region)) {
		var regId = region.split("-", 1);
		var url = 'getCustomerListByRegionId.htm?regionId='
			+ regId;
		ajaxCallWithoutForm(url, populateCustomerList);
	} else {
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
	}

}

function populateCustomerList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('customerList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		
		var branchCode = new Array();
		var len = data.length;
		for ( var i = 0; i < len; i++) {
			var branch = data[i].customerId;
			branchCode.push(branch);
			
		}
		
		
		var defOption1;
		defOption1 = document.createElement("option");
		defOption1.value = branchCode;
		//defOption1.value = '00001';
		defOption1.appendChild(document.createTextNode("ALL"));
		content.appendChild(defOption1);
		
		
		
		
		
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.customerId;
			var ch = this.bussinessName ;
			option.appendChild(document.createTextNode(ch));
			content.appendChild(option);
		});
	}
}
	

	function calculateDateDiff1(fromDate,toDate){
		var _fromDate =fromDate.split("/");
		var  _toDate = toDate.split("/");
	//	alert(_fromDate+" _heldUpDate  &&&&&   "+_toDate);
		var _fromDateObj = new Date(_fromDate[2],_fromDate[1]-1,_fromDate[0], 00);
		var _toDateObj = new Date(_toDate[2],_toDate[1]-1,_toDate[0], 00);
		var oneday = 24*60*60*1000;
		var calculateDate=_toDateObj-_fromDateObj;
		var dateDiff=Math.ceil(calculateDate/oneday);
		//alert(dateDiff);
		return dateDiff;
	}

	function getchequeNumbers() { 
		var ptype = $("#paymentType").val();
		if (!isNull(ptype) && (ptype == "2" || ptype == "1")) {
			var custId = $("#customerList").val();
			var fromDate = $("#fromDate").val();// from date
			var toDate = $("#toDate").val();// to date
			var regId = "0";
			var msgAlert = "";
			if (isNull(custId)) {
				msgAlert = msgAlert + "Select Customer \n";
			}
			if (fromDate == null || $.trim(fromDate).length <= 0) {
				msgAlert = msgAlert + "Select From Date \n";
			} 
			if (toDate == null || $.trim(toDate).length <= 0) {
				msgAlert = msgAlert + "Select To Date \n";
			} 		
			if (msgAlert != "") {
				alert(msgAlert);
				document.getElementById('paymentType').selectedIndex = 0;
				return;
			} else if (1 < calculateDateDiff1(fromDate, toDate)) {
				if (ptype == '1'  || ptype == "2") { 
					fromDate = changeDateFormat(fromDate);
					toDate = changeDateFormat(toDate);
					document.getElementById("chequeNo").disabled = false;
					var url = 'getChequeNumbersByCustomerId.htm?customerId='
							+ custId
							+ "&fromDate="
							+ fromDate
							+ "&toDate="
							+ toDate;
					ajaxCallWithoutForm(url, populateChequeNumbers);
				} else {
					$("#chequeNo").empty();
					document.getElementById("chequeNo").disabled = true;
				}

			}else {
				alert("Please Check Date Range");

			    $("#fromDate").val("");

				$("#fromDate").focus(); 
				return false;
			}
				
		}else {
		$("#chequeNo").empty();
		document.getElementById("chequeNo").disabled = true;
	}

	}


	function populateChequeNumbers(data) {
		if (!isNull(data)) {
			var content = document.getElementById('chequeNo');
			content.innerHTML = "";
			
			var branchCode = new Array();
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				var branch = data[i].chequeNumber;
				branchCode.push(branch);
				
			}
			
			/* var defOption;
			defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption); */
			
			var defOption1;
			defOption1 = document.createElement("option");
			defOption1.value = branchCode;
			defOption1.appendChild(document.createTextNode("ALL"));
			content.appendChild(defOption1);
			
			
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				
				option.value = this.chequeNumber;
				var ch = this.chequeNumber ;
				option.appendChild(document.createTextNode(ch));
				
				
				
				content.appendChild(option);
			});
		}
	}

	function showChequeSummery() {
		
		var ptype = $("#paymentType").val();
		var paymentMode = "";
		if (!isNull(ptype) && ptype == "2") {
			paymentMode = "'C'";
		}
		if (!isNull(ptype) && ptype == "3") {
			paymentMode = "'F'";
		}
		if ( ptype == "1") {
			paymentMode = " 'C,F' ";
		}
		
		var msg = "";
		var fromDate = $("#fromDate").val();// from date
		var toDate = $("#toDate").val();// to date
		var region = $("#region").val();
		var customerid = $("#customerList").val();// customer id
		
		var chequeNo = $("#chequeNo").val();
		var chequeName = '';
		if (isNull(chequeNo)){
			chequeNo=null;
			chequeName= null;
		}else{
			chequeName = document.getElementById('chequeNo').options[document.getElementById('chequeNo').selectedIndex].text;
		}
		var r_id = null;
		var regionName = null;
		var c_name = null;
		var c_code = null;
		var customer = document.getElementById('customerList').options[document
				.getElementById('customerList').selectedIndex].text;
		if (isNull(region)) {
			msg = msg + "Select Origin Region \n";
		} else {
			
			
			var originRegionName = document.getElementById('region').options[document.getElementById('region').selectedIndex].text;
			
			var regDt = region.split("-");
			r_id = regDt[0];
			regionName = originRegionName;
			
			
		}
		if (isNull(customerid)) {
			msg = msg + "Select Customer \n";
		} else {
			var custDt = customer.split("-");
			c_name = custDt[0];
			c_code = custDt[1];
			c_name = trimString(c_name);
			c_code = trimString(c_code);
			if(c_name=="ALL"){
				customerid = '00001';
			}
		}
		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		} 
       if (1 > calculateDateDiff1(fromDate, toDate)) {
    	   alert("Please Check Date Range");

  		 //   $("#fromDate").val("");

  			$("#fromDate").focus(); 
  			return false;
		}
		if (fromDate == null || $.trim(fromDate).length <= 0) {
			msg = msg + "Select From Date \n";
		} else {
			fromDate = changeDateFormat(fromDate);
		}
		if (toDate == null || $.trim(toDate).length <= 0) {
			msg = msg + "Select To Date \n";
		} else {
			toDate = changeDateFormat(toDate);
		}
		
			if (isNull(ptype)) {
				msg = msg + "Select PaymentType \n";
			} 
		if (msg != "") {
			alert(msg);
			return;
		} else  {
			//alert(paymentMode);
			var url = '/udaan-report/ud/pages/reportveiwer/chequeSummeryViewer.jsp?'
					+ "&regionName=" + regionName +"&region=" + r_id +"&c_id=" + customerid
					+ "&c_name=" + c_name + "&c_code=" + c_code
					+ "&checkNo=" + chequeNo + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&paymentMode=" + paymentMode  + "&chequeName=" + chequeName ;
			//alert(url);
			window.open(url, "_blank");
		}
		
	}

	function changeDateFormat(date) {
		var arrStartDate = date.split("/");
		var newDate = arrStartDate[2] + "-" + arrStartDate[1] + "-"
				+ arrStartDate[0];
		return newDate;
	}

	function resetFieldsOnCustomer() {
		document.getElementById('paymentType').selectedIndex = 0;
		$("#chequeNo").empty();
		document.getElementById("chequeNo").disabled = true;
	}
	function clearChequeSummeryScreen1() {
		var url = "./chequeSummeryReport.htm";
		submitTransfer(url, 'Clear');
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
							<h1>Cheque Summery Report</h1>
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
		<td  class="lable"><span class="mandatoryf">*</span>Region</td>
		<td x>
					
		 <c:choose>
		 
               <c:when test="${officeId eq  '3' && regionList.size()==1 }">
				<form:select path="region" id="region" class="selectBox width130"  onchange="getCustomerList(this.value)"  disabled="true">
				 <!-- 	<option value="0">Select</option>  -->
				<form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				</form:select></td>
               </c:when>
                                     
			    <c:when test="${officeId eq  '3'}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCustomerList(this.value)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>
								      
				<c:when test="${officeId eq '5' && regionList.size()==1}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCustomerList(this.value)" disabled="true">
				 <!-- <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
		        </c:when>
								     
			    <c:when test="${officeId eq '5'}">
				 <form:select path="region" id="region" class="selectBox width130" onchange="getCustomerList(this.value)">
				 <option value="0">Select</option>
				 <form:options items="${regionList}" itemValue="regionCode"	itemLabel="regionName" />
				 </form:select></td>
			    </c:when>
								      
				<c:when test="${regionList.size()==1}">
				 <form:select path="region" id="region"	class="selectBox width130" onchange="getCustomerList(this.value)" disabled="true">
				 <!--   <option value="0">Select</option> -->
				 <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				 </form:select></td>
				</c:when>      
			    <c:otherwise>
				 <form:select path="region" id="region"	class="selectBox width130" onchange="getCustomerList(this.value)" >
				  <option value="0">Select</option> 
				  <form:options items="${regionList}" itemValue="regionCode" itemLabel="regionName" />
				  </form:select></td>
				</c:otherwise>
		  </c:choose>
								
								
		<td  class="lable"><span class="mandatoryf">*</span>Customer</td>
		<td >
				<select name="to.customerTo" id="customerList"
										class="selectBox width130" onchange="resetFieldsOnCustomer();">
											<option value="">Select</option>
									</select></td>				
								 
	
						<td  class="lable"><span class="mandatoryf">*</span>From Date</td>
			
									<td><input type="text" name="to.fromDate"
										style="height: 20px" value="" id="fromDate"
										class="txtbox width130" readonly="readonly" /> <a href="#"
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>			
									
									
								
							</tr>

							<tr>
								<td class="lable"><sup class="star">* </sup>To Date<bean:message />
								</td>
							
									<td><input type="text" name="to.toDate"
										style="height: 20px" value="" id="toDate"
										class="txtbox width130" readonly="readonly" /> <a href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
										
										
							<td class="lable"><sup class="star">* </sup>Payment Type<bean:message />
									<td><select name="to.paymentTypeTo" id="paymentType"
										class="selectBox width130" onchange="getchequeNumbers();">
											<option value="">Select</option>
											<option value="1">ALL</option>
											<option value="2">Cheque</option>
											<option value="3">Freight</option>
									</select></td>

									<td class="lable"><sup class="star">* </sup>ChequeNo<bean:message />
									<td><select name="to.chequeNoTo" id="chequeNo"
										class="selectBox width130">
									</select></td>					
						
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
						value='Submit' title="Submit" onclick="showChequeSummery();"> <input
						name="cancel" value="Cancel"
						onclick="clearScreen();"
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




