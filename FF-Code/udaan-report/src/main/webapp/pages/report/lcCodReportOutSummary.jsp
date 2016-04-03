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
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		
		<!-- <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script> -->
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/billing/billPrinting.js"></script> -->
<script type="text/javascript" charset="utf-8"
	src="js/report/commonReport.js"></script>
<script type="text/javascript" charset="utf-8">
/* $(document).ready( function () {
	billPrintingStartup();
});
 */
</script>
</head>
<body >
	<html:form  method="post" styleId="lcCodReportForm">
	<div id="wraper">
	<div class="clear"></div>
	
	<div id="maincontent">
	<div class="mainbody">
	<div class="formbox">
	<h1><bean:message key="label.lcCodReport.printHeaderOutSummary" /></h1>
	<div class="mandatoryMsgf"><span class="mandatoryf"><bean:message key="symbol.common.star" /></span>
	&nbsp;<bean:message key="label.common.FieldsareMandatory" />
	</div>

	</div>
	
			<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
							<td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.type" /></td>
								<td width="16%">
									<html:select property="to.type" styleId="typeList" 
										styleClass="selectBox width130" >
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<html:option value="ALL"><bean:message key="label.lcCodReport.all"/></html:option>
										<c:forEach var="type" items="${typeTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td>
							
							
							
							<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.product" /></td>
								<td width="25%"><html:select property="to.productTO"
										styleId="productTo" styleClass="selectBox width140" onchange="clearFieldsOnProduct();">
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<html:option value="D,L"><bean:message key="label.lcCodReport.all"/></html:option>
										<c:forEach var="type" items="${productTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									  </html:select>
									  
							  </td>
							
							
							<td width="10%" class="lable"><div id="summaryOptionLabel"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.summaryOption" /></div></td>
								<td width="16%">
									<html:select property="to.summaryOption" styleId="summaryOptionList" 
										styleClass="selectBox width130" >
										<c:forEach var="type" items="${summaryOptionTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td>
							
							
							<%-- 	<td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.sortingOrder" /></td>
								<td width="16%">
									<html:select property="to.sortOrder" styleId="sortOrderList" 
										styleClass="selectBox width130" >
										<c:forEach var="type" items="${sortingOrderTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td> --%>
								
								<%-- <td height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.StartDate" /></td>
								
								<td><html:text property="to.startDate" styleClass="txtbox width100" styleId="startDate" readonly="true"/> 
  										<a href="#" onclick="javascript:show_calendar('startDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
								</td>
								
								<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.EndDate" /></td>
										
										 <td><html:text property="to.endDate" styleClass="txtbox width100" styleId="endDate" readonly="true"/> 
  												<a href="#" onclick="javascript:show_calendar('endDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
							    </td> --%>
								
								
								
								
								
								<%-- <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.billingPrint.stations" /></td>
								<td width="14%"><html:select property="to.productTo"
										styleId="stationList" styleClass="selectBox width140" onchange="getBranchesList();clearFields();">
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<c:if test="${(OFFICE_TYPE eq BR_OFFICE) ||(OFFICE_TYPE eq HUB_OFFICE) }">
										<option value="${cityTo.cityId } " selected="selected">
												<c:out value=" ${cityTo.cityName }" />
											</option>
										</c:if>	
										<c:if test="${OFFICE_TYPE eq RHO_OFFICE }">
										<c:forEach var="type" items="${cityTo}" varStatus="loop">
											<html:option value="${type.cityId } ">
												<c:out value="${type.cityName } " />
											</html:option>
										</c:forEach> 
										</c:if>	
										
									</html:select></td> --%>
								
							</tr>
						<tr>
							
							<td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.sortingOrder" /></td>
								<td width="16%">
									<html:select property="to.sortOrder" styleId="sortOrderList" 
										styleClass="selectBox width130" >
										<c:forEach var="type" items="${sortingOrderTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td>
							
							
							<td height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.StartDate" /></td>
								
								<td><html:text property="to.startDate" styleClass="txtbox width100" styleId="startDate" readonly="true"/> 
  										<a href="#" onclick="javascript:show_calendar('startDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
								</td>
								
								<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.EndDate" /></td>
										
										 <td><html:text property="to.endDate" styleClass="txtbox width100" styleId="endDate" readonly="true"/> 
  												<a href="#" onclick="javascript:show_calendar('endDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
							    </td>
							    
							<%-- <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
										key="label.lcCodReport.product" /></td>
								<td width="25%"><html:select property="to.productTO"
										styleId="productTo" styleClass="selectBox width140" onchange="clearFieldsOnProduct();">
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<html:option value="D,L"><bean:message key="label.lcCodReport.all"/></html:option>
										<c:forEach var="type" items="${productTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									  </html:select>
									  
							  </td> --%>
							
							<%-- <td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.region" /></td>
								<td width="16%">
									<html:select property="to.regionTO" styleId="regionList" 
										styleClass="selectBox width130" >
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<c:forEach var="type" items="${regionList}" varStatus="loop">
											<html:option value="${type.regionId } ">
												<c:out value="${type.regionName } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td> --%>
							
							
							<%-- <td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.type" /></td>
								<td width="16%">
									<html:select property="to.type" styleId="typeList" 
										styleClass="selectBox width130" >
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<html:option value="ALL"><bean:message key="label.lcCodReport.all"/></html:option>
										<c:forEach var="type" items="${typeTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td> --%>
							
							
							</tr>
					<tr>
								
								 
								<%-- <td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.summaryOption" /></td>
								<td width="16%">
									<html:select property="to.summaryOption" styleId="summaryOptionList" 
										styleClass="selectBox width130" >
										<c:forEach var="type" items="${summaryOptionTo}" varStatus="loop">
											<html:option value="${type.stdTypeCode } ">
												<c:out value="${type.description } " />
											</html:option>
										</c:forEach>
									</html:select>
								</td> --%>
									
							 
								
							<td width="10%" class="lable"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.region" /></td>
								<td width="16%">
									<%-- <html:select property="to.regionTO" styleId="regionList" 
										styleClass="selectBox width130" onchange="getCustomerByRegionAndProduct();" >
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<c:forEach var="type" items="${regionList}" varStatus="loop">
											<html:option value="${type.regionId } ">
												<c:out value="${type.regionName } " />
											</html:option>
										</c:forEach>
									</html:select> --%>
									<select multiple="multiple" name="regionList" id="regionList"
																class="selectBox width200"  onchange="getCustomerByRegionAndProduct();">
																	<logic:present name="regionList" scope="request">
																	<logic:iterate id="type" name="regionList">
																		<option value="${type.regionId }">
																			<c:out value="${type.regionName}" />
																		</option>
																	</logic:iterate>
																</logic:present>
															</select>
															
															
															
															<%-- <c:choose>
			<c:when test="${wrapperReportTO.getRegionTO().size() == 1}">

				<select name="lcCodTO.regionTo" id="regionList"
					class="selectBox width130" disabled="disabled">
					<option
						value="${wrapperReportTO.getRegionTO().get(0).getRegionId() }"
						selected="selected">${
						wrapperReportTO.getRegionTO().get(0).getRegionName()}</option>
				</select>
			</c:when>
			<c:when test="${wrapperReportTO.getRegionTO().size() == 0}">
				<select name="lcCodTO.regionTo" id="regionList"
					class="selectBox width130"  multiple>
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="lcCodTO.regionTo" id="regionList"
					class="selectBox width130" multiple>
					<option value="Select">Select</option>
					<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
						<option value="${regions.regionId }">${regions.regionName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose> --%>
															
															
															
															
															
								</td>	
									
									
							 <td width="10%" class="lable"><div id="partyNameLabel"><sup class="star">* </sup>
								<bean:message key="label.lcCodReport.partyName" /></div></td>
								<td width="16%">
<%-- 									<html:select property="to.partyName" styleId="partyNameList"  --%>
<%-- 										styleClass="selectBox width130" > --%>
<%-- 										<html:option value=""><bean:message key="label.common.select"/></html:option> --%>
<%-- 										<c:forEach var="type" items="${customerList}" varStatus="loop"> --%>
<%-- 											<html:option value="${type.customerId } "> --%>
<%-- 												<c:out value="${type.customerName } " /> --%>
<%-- 											</html:option> --%>
<%-- 										</c:forEach> --%>
<%-- 									</html:select> --%>
															<div style="overflow-x:scroll; width:300px;overflow: -moz-scrollbars-horizontal;">
									<select size = "10" multiple="multiple" name="partyNameList" id="partyNameList" >
																	<logic:present name="partyNameList" scope="request">
																	<logic:iterate id="type" name="partyNameList">
																		<option value="${type.customerId }">
																			<c:out value="${type.customerName }" />
																		</option>
																	</logic:iterate>
																</logic:present>
															</select>
															</div>
							  </td>		
									
							</tr>
						</table>
					</div>

			<!-- Button -->
			<div class="button_containerform">
				<html:button property="print" styleClass="btnintform"
					styleId="printBtn" onclick="printLcCodReportOutSummary();">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="cancelLcCodReport();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		    <input type="hidden" name = "reportName" id="reportName"  value="lcCodOutSummary"/>
	</div>
	</div>  
</html:form>
</body>
</html>
