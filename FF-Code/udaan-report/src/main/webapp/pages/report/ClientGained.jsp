<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/report/ClientGained.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/commonReport.js"></script>
</head>
<body>
	<form method="post" id="consignmentReportForm">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.clientgainedReport.clientgainedReportHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
						   <tr>
							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.consgBookingReport.region" /></td>
						
							<td><c:choose>
									<c:when test="${wrapperReportTO.getRegionTO().size() == 1}">
						
										<select name="to.regionTo" id="regionList"
											class="selectBox width130" disabled="disabled">
											<option
												value="${wrapperReportTO.getRegionTO().get(0).getRegionId() }"
												selected="selected">${
												wrapperReportTO.getRegionTO().get(0).getRegionName()}</option>
										</select>
									</c:when>
									<c:when test="${wrapperReportTO.getRegionTO().size() == 0}">
										<select name="to.regionTo" id="regionList"
											class="selectBox width130" >
											<option value="Select">Select</option>
										</select>
						
									</c:when>
									<c:otherwise>
										<select name="to.regionTo" id="regionList"
											class="selectBox width130"
											onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');"
											onblur="getSalesPersonList(this);" >
											<option value="Select">Select</option>
											<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
												<option value="${regions.regionId }">${regions.regionName}</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose></td>
						
							<td class="lable"><bean:message
									key="label.consgBookingReport.station" /></td>
						
						
							<td><c:choose>
									<c:when test="${wrapperReportTO.getCityTO().size() == 1}">
										<select name="to.station" id="stationList"
											class="selectBox width130" disabled="disabled">
											<option value="${wrapperReportTO.getCityTO().get(0).getCityId() }"
												selected="selected">
												${wrapperReportTO.getCityTO().get(0).getCityName()}</option>
										</select>
									</c:when>
									<c:when test="${wrapperReportTO.getCityTO().size() == 0}">
										<select name="to.station" id="stationList"
											class="selectBox width130" onchange="getBranchList('stationList');"
											onblur="getSalesPersonList(this);">
											<option value="Select">Select</option>
										</select>
						
									</c:when>
									<c:otherwise>
										<select name="to.station" id="stationList"
											class="selectBox width130" onchange="getBranchList('stationList');"
											onblur="getSalesPersonList(this);">
											<option value="Select">Select</option>
											<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
												<option value="${city.cityId }" >
													${city.cityName}</option>
											</c:forEach>
						
										</select>
									</c:otherwise>
						
								</c:choose></td>
						
							<td class="lable"><bean:message
									key="label.consgBookingReport.branch" /></td>
						
							<td><c:choose>
									<c:when test="${wrapperReportTO.getOfficeTO().size() == 1 }">
										<select name="to.station" id="branchList" class="selectBox width130"
											disabled="disabled">
											<option value="${wrapperReportTO.getOfficeTO().get(0).getOfficeId() }"
											selected="selected">
											${wrapperReportTO.getOfficeTO().get(0).getOfficeName()}</option>
										</select>
									</c:when>
									<c:when test="${wrapperReportTO.getOfficeTO().size() == 0 }">
										<select name="to.station" id="branchList" class="selectBox width130"
											onchange="getClientList('branchList')"
											onblur="getSalesPersonList(this);">
											<option value="Select">Select</option>
										</select>
									</c:when>
									<c:otherwise>
										<select name="to.station" id="branchList" class="selectBox width130"
											onchange="getClientList('branchList');"
											onblur="getSalesPersonList(this);">
											<option value="Select">Select</option>
											<c:forEach var="office" items="${wrapperReportTO.getOfficeTO()}">
												<option value="${office.officeId }">
													${office.officeName}</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose></td>
						</tr>
							<!-- tr>
								<td class="lable">
									<bean:message key="label.stockreport.region" />
								</td>
								<td>
									<select name="to.regionTo" id="regionList" class="selectBox width130"
										onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');getSalesPersonList(this);">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId }">
												${regions.regionName}</option>
										</c:forEach>
									</select>
								</td>
								<td class="lable">
									<bean:message key="label.stockreport.station" />
								</td>
								<td>
									<select name="to.station" id="stationList" class="selectBox width130"
										onchange="getBranchList('stationList');getSalesPersonList(this);">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
									</select>
								</td>
								<td class="lable">
									<bean:message key="label.stockreport.branch" />
								</td>
								<td>
									<select name="to.branch" id="branchList" class="selectBox width130"
										onchange="getSalesPersonList(this);">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
									</select>
								</td>
							</tr>
							<tr-->
								<td class="lable">
									<sup class="star">* </sup> <bean:message key="dispatch.label.originhubtally.fromdate" />
								</td>
								<td>
									<input type="text" name="to.fromDate" style="height: 20px" value="" id="fromDate"
										class="txtbox width130" readonly="readonly">
									<a href="#" onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" />
									</a>
								</td>
								<td class="lable">
									<sup class="star">* </sup> <bean:message key="dispatch.label.originhubtally.todate" />
								</td>
								<td>
									<input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> 
									<a href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" />
									</a>
								</td>
						
								<td class="lable"> 
									<bean:message key="label.salesreport.Product" />
								</td>
								<td><select name="product" id="product" class="selectBox width130">
										<!-- <option value="Select">Select</option> -->
										<option value="All">
											<bean:message key="label.all" />
										</option>
										<c:forEach var="product" items="${rateProductTo}">
											<c:choose>
					          					<c:when test="${product.rateProductCategoryId != 6}">
														<option value="${product.rateProductCategoryId}">${product.rateProductCategoryName}</option>
												</c:when>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td colspan="2">
									<br /> <br />
								</td>
							</tr>
							<tr>
								<td class="lable"> 
									<bean:message key="label.ListView.SalesPerson" />
								</td>
								<td>        				
              				        <select name="salesPerson" id="salesPerson" class="selectBox width130" >
              				        	<option value="Select">Select</option>
	              				        <option value="All">
												<bean:message key="label.all" />
										</option>
										<c:forEach var="salesPerson" items="${salesPersonTO}" varStatus="loop">
											<c:choose>
					          				<c:when test="${salesPerson.firstName.trim().length()>0}">
											<option value="${salesPerson.employeeId}">
												<c:out value="${salesPerson.firstName}"/>
											</option>
											</c:when>
											</c:choose>
										</c:forEach>
									</select>
                      			</td>
                      			
<!--                       			<td class="lable">  -->
<%-- 									<bean:message key="label.ListView.ReportType" /> --%>
<!-- 								</td> -->
<!-- 								<td>        				 -->
<!--               				        <select name="reportType" id="reportType" class="selectBox width130" > -->
<!--               				        	<option value="Select">Select</option> -->
<!-- 	              				        <option value="Summary">Summary</option> -->
<!-- 	              				        <option value="Detail">Detail</option> -->
<!-- 									</select> -->
<!--                       			</td> -->
              					
							</tr>
							
							
								
<%-- 												<bean:message key="label.ListView.Detail" /> --%>
										 
<%-- 										<c:forEach var="salesPerson" items="${salesPersonTO}" varStatus="loop"> --%>
<%-- 											<c:choose> --%>
<%-- 					          				<c:when test="${salesPerson.firstName.trim().length()>0}"> --%>
<%-- 											<option value="${salesPerson.firstName}"> --%>
<%-- 												<c:out value="${salesPerson.firstName}"/> --%>
<!-- 											</option> -->
<%-- 											</c:when> --%>
<%-- 											</c:choose> --%>
<%-- 										</c:forEach> --%>
									
							
						</table>
					</div>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					onclick="clientGainedDetails();" styleId="Submit">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearFilterScreen();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		</div>
	</form>
</body>
</head>
</html>