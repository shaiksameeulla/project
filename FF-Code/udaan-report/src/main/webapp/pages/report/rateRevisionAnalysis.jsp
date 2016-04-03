<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>
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
<script type="text/javascript" charset="utf-8"
	src="js/jquery.mtz.monthpicker.js"></script>
</head>
<body onload="$('.monthpicker').monthpicker();">
	<form method="post" id="rateRevisionAnalysisReportForm"
		action="/udaan-report/rateRevisionAnalysisReport.do?submitName=getRateRevisionAnalysisDetailsReport" target="NewWindow">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message
								key="label.rrareport.rateRevisionAnalysisReportHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.rrareport.FieldsareMandatory" />
						</div>
					</div>
					<table border="0" cellpadding="0" cellspacing="3" width="100%">
						<tr>
							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.region" /></td>
							<td><c:choose>
									<c:when test="${wrapperReportTO.getRegionTO().size() == 1}">
										<select name="to.regionTo" id="region"
											class="selectBox width130" disabled="disabled">
											<option
												value="${wrapperReportTO.getRegionTO().get(0).getRegionId() }"
												selected="selected">${
												wrapperReportTO.getRegionTO().get(0).getRegionName()}</option>
										</select>
									</c:when>
									<c:when test="${wrapperReportTO.getRegionTO().size() == 0}">
										<select name="to.regionTo" id="region"
											class="selectBox width130">
											<option value="Select">Select</option>
										</select>

									</c:when>
									<c:otherwise>
										<select name="to.regionTo" id="region"
											class="selectBox width130"
											onchange="getStationsList('region','stationList');">
											<option value="Select">Select</option>
											<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
												<option value="${regions.regionId }">${regions.regionName}
													</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose></td>

							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.station" /></td>
							<td><c:choose>
									<c:when test="${wrapperReportTO.getCityTO().size() == 1}">
										<select name="to.station" id="stationList"
											onchange="getRraCustomerList('station','customerList')"
											class="selectBox width130" disabled="disabled">
											<option
												value="${wrapperReportTO.getCityTO().get(0).getCityId() }"
												selected="selected">
												${wrapperReportTO.getCityTO().get(0).getCityName()}</option>
										</select>
									</c:when>
									<c:when test="${wrapperReportTO.getCityTO().size() == 0}">
										<select name="to.station" id="stationList"
											onchange="getRraCustomerList('station','customerList')"
											class="selectBox width130">
											<option value="Select">Select</option>
										</select>

									</c:when>
									<c:otherwise>
										<select name="to.station" id="stationList"
											class="selectBox width130"
											onchange="getRraCustomerList('station','customerList')">
											<option value="Select">Select</option>
											<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
												<option value="${city.cityId }">${city.cityName}</option>
											</c:forEach>

										</select>
									</c:otherwise>

								</c:choose></td>

							<td class="lable"></td>
							<td></td>
						</tr>

						<tr>
							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.month1" /></td>
							<td><input class="monthpicker" type="text"
								name="to.month1" style="height: 20px" value="" id="month1"
								class="txtbox width130" readonly="readonly"></td>

							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.month2" /></td>
							<td><input class="monthpicker" type="text"
								name="to.month2" style="height: 20px" value="" id="month2"
								class="txtbox width130" readonly="readonly"></td>

							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.sector" /></td>
							<td><select name="sector" id="sector"
								class="selectBox width130">
									<%-- <option value="All">
										<bean:message key="label.rrareport.dropdown.select" />
									</option> --%>
									<option value="All">
										<bean:message key="label.rrareport.dropdown.all" />
									</option>
									<c:forEach var="sector" items="${sectorTO}">
										<option value="${sector.sectorId }">${sector.sectorName}</option>
									</c:forEach>
							</select></td>
						</tr>

						<tr>
							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.fuelPercent" /></td>
							<td><select name="fuelPercent" id="fuelPercent"
								class="selectBox width130">
									<c:forEach var="fuel" items="${fuelTO}">
												<option value="${fuel.description }">${fuel.description}</option>
									</c:forEach>
							</select></td>

							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.customer" /></td>
							<td><select name="customerList" id="customerList"
								class="selectBox width250" multiple="multiple" onChange="getProductByCustomers();">
							</select></td>

							<td class="lable"><sup class="star">* </sup> <bean:message
									key="label.rrareport.product" /></td>
							<td><select name="product" id="product"
								class="selectBox width130">
									<option value="Select">
										<bean:message key="label.rrareport.dropdown.select" />
									</option>
									<%-- <c:forEach var="product" items="${productTo}">
										<option value="${product.productId }">${product.consgSeries}</option>
									</c:forEach> --%>
							</select></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					onclick="showRateRevisionAnalysis();" styleId="Submit">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearRateRevisionScreen()">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		</div>
	</form>
</body>
</html>