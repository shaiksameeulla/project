<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
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
	<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		triggeringBranches();
	});
</script>
<!-- DataGrids /-->
</head>
<body>
	<form method="post" id="consignmentReportForm" action="">
	<%String reportName = (String) request.getParameter("reportName");
System.out.println("ReportName - " + reportName);
%> 
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
				<% if(reportName.equals("BranchWise")){  %>
	       		<h1>
							<bean:message
								key="label.hitRatioBranchwiseReport.hitRatioBranchwiseReportHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
				<%} %>
				
				<% if(reportName.equals("DateWise")){  %>
	       		<h1>
							<bean:message
								key="label.hitRatioDatewiseReport.hitRatioDatewiseReportHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
				<%} %>
					<%-- </div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
						<%@include file="commonReportInclude.jsp" %> 
						<tr>--%>
						<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">					
						<tr>
						<td class="lable"><sup class="star">* </sup> <bean:message
													key="label.consgBookingReport.destRegion" /></td>
										
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
															onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');clearItem();">
															<option value="Select">Select</option>
															<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
																<option value="${regions.regionId }">${regions.regionName}</option>
															</c:forEach>
														</select>
													</c:otherwise>
												</c:choose></td>
										
											<td class="lable"><sup class="star">* </sup><bean:message
													key="label.consgBookingReport.destStation" /></td>
										
										
											<td><c:choose>
													<c:when test="${wrapperReportTO.getCityTO().size() == 1}">
														<select name="to.station" id="stationList" onchange="getBranchListForBrr('stationList');clearItem();"
															class="selectBox width130" disabled="disabled">
															<option value="${wrapperReportTO.getCityTO().get(0).getCityId() }"
																selected="selected">
																${wrapperReportTO.getCityTO().get(0).getCityName()}</option>
														</select>
													</c:when>
													<c:when test="${wrapperReportTO.getCityTO().size() == 0}">
														<select name="to.station" id="stationList"
															class="selectBox width130" onchange="getBranchListForBrr('stationList');clearItem();">
															<option value="Select">Select</option>
														</select>
										
													</c:when>
													<c:otherwise>
														<select name="to.station" id="stationList"
															class="selectBox width130" onchange="getBranchListForBrr('stationList');clearItem();">
															<option value="Select">Select</option>
															<option value="All">All</option>
															<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
																<option value="${city.cityId }" >
																	${city.cityName}</option>
															</c:forEach>
										
														</select>
													</c:otherwise>
													</c:choose>
													</td>
													</tr>
										<tr>
												 <td class="lable"><sup class="star">* </sup><bean:message
													key="label.consgBookingReport.destBranch" /></td>
										
										<td><select size="10" multiple="multiple"
										name=branchList1 id="branchList" class="selectBox"
										style="width: 230;" >
										<option value="Select">Select</option>
										
										<logic:present name="branchList" scope="request">
											<logic:iterate id="branchList" name="branchList">
												<option value="${wrapperReportTO.getOfficeTO().get(0).getOfficeId() }"
																selected="selected">
																${wrapperReportTO.getOfficeTO().get(0).getOfficeName()}</option>
											</logic:iterate>
										</logic:present>
									</select>
								</td>
								 <td style="width: 70; float: left; margin-top: 65px;">
									<input name="Add" type="button" value=""
										class="btnintmultiselectr" title="Add"
										onclick="getCustomer1();" /> <br /> <input name="Add"
										type="button" value="" class="btnintmultiselectl" title="Add"
										onclick="removeCustomers();" />
								</td>
								<td style="width: 100; float: left;">
									
									<select size="10" style="width: 230;"
										multiple="multiple" name="branchList1" id="branchList1"
										class="selectBox width300" >
										<logic:present name="branchList1" scope="request">
											<logic:iterate id="branchList1" name="branchList1">
												<option value="${wrapperReportTO.getOfficeTO().get(0).getOfficeId() }"
																selected="selected">
																${wrapperReportTO.getOfficeTO().get(0).getOfficeName()}</option>
											</logic:iterate>
										</logic:present>

									</select>
								</td> 
							</tr>
									<%-- <c:when test="${wrapperReportTO.getOfficeTO().size() == 1 }">
														<select name="to.station" id="branchList" class="selectBox width130" onchange="getConsignmentBookingClientList('branchList')"
															disabled="disabled">
															<option value="${wrapperReportTO.getOfficeTO().get(0).getOfficeId() }"
																selected="selected">
																${wrapperReportTO.getOfficeTO().get(0).getOfficeName()}</option>
														</select>
													</c:when>
													<c:when test="${wrapperReportTO.getOfficeTO().size() == 0 }">
														<select name="to.station" id="branchList" class="selectBox width130"
															onchange="getConsignmentBookingClientList('branchList')">
															<option value="Select">Select</option>
														</select>
													</c:when>
													<c:otherwise>
														<select name="to.station" id="branchList" class="selectBox width130"
															onchange="getConsignmentBookingClientList('branchList')">
															<option value="">Select</option>
															<c:forEach var="office" items="${wrapperReportTO.getOfficeTO()}">
																<option value="${office.officeId }">
																	${office.officeName}</option>
															</c:forEach>
														</select>
													</c:otherwise>
													</c:choose> --%> 
											
											
												</tr>
												<tr>									
						
						
							<%-- <tr>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.region" /></td>
								<td><select name="to.regionTo" id="regionList"
									class="selectBox width130"
									onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId}">
												${regions.regionName} - ${regions.regionCode}</option>
										</c:forEach>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.station" /></td>
								<td><select name="to.station" id="stationList"
									class="selectBox width130"
									onchange="getBranchList('stationList')">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.branch" /></td>
								<td><select name="to.branch" id="branchList"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>
							</tr> --%>
							

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.from.date" /></td>
								<td><input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>



								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.to.date" /></td>
								<td><input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>



								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.category" /></td>
								<td><select name="to.category" id="category"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								<option value="ALL"><bean:message key="label.all"/></option>		
								 <c:forEach var="category" items="${category}">
									<option value="${category.categoryCode}">${category.categoryDesc}</option>
								 </c:forEach>
								</select></td>
								<td colspan="2"><br /> <br /></td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.series" /></td>
								<td><select name="to.series" id="series"
									class="selectBox width130">
										<option>
										<bean:message key="label.common.select" />
										</option>
										<option value="ALL"><bean:message key="label.all"/></option>
										<c:forEach var="product" items="${productTo}">
							    				<option value="${product.consgSeries}">${product.consgSeries}</option>
								</c:forEach>
								</select></td>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.type" /></td>
								<td><select name="to.type" id="type"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								<option value="ALL"><bean:message key="label.all"/></option>		
								<c:forEach var="type" items="${consignmentType}">
									<option value="${type.consignmentCode}">${type.consignmentName}</option>
								 </c:forEach>
								</select></td>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.load" /></td>
								<td><select name="to.load" id="load"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								<option value="ALL"><bean:message key="label.all"/></option>		
								 <c:forEach var="loadval" items="${load}">
									<option value="${loadval.loadNo}">${loadval.loadDesc}</option>
								 </c:forEach>
								</select></td>

								<td colspan="2"><br /> <br /></td>
							</tr>
						</table>

					</div>
				</div>
				<input type="hidden" id="officeType" name="officeType" value="${officeType}" />
				<input type="hidden" id="branchOfficeType" name="branchOfficeType" value="${branchOfficeType}" />
				<input type="hidden" id="hubOfficeType" name="hubofficeType" value="${hubofficeType}" />
               <input type="hidden" id="rhoOfficeType" name="rhoOfficeType" value="${rhoOfficeType}" />
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					styleId="Submit" onclick="showHitRatioBranchwiseReport();">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearHitRatioDatewiseReport();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
			<input type="hidden" name = "Name" id="Name" value="<%=reportName %>" />
		</div>
	</form>
</body>
</html>