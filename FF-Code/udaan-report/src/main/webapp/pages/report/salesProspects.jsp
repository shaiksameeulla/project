<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/report/commonReport.js"></script>
<script type="text/javascript" src="js/report/ClientGained.js"></script>
<script type="text/javascript" src="js/report/salesProspectReport.js"></script>
<script type="text/javascript" charset="utf-8"
 src="js/calendar/ts_picker.js"></script>
<!-- DataGrids /-->
<!-- <script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		transferStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'consignmentReportForm');
		}
	});
</script> -->
</head>
<body>
<!--wraper-->

		
			<form method="post" id="consignmentReportForm" name="consignmentReportForm"
		action="/udaan-report/salesProspectsReport.do?submitName=getSalesProspectsReport"> 
<div id="wraper">
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
           	<div class="formbox">
       			<h1><bean:message key="label.salesreport.salesProspectReport" /></h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.sales.FieldsAreMandatory" /></div>
    		</div>
           	<div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="3" width="100%">
                
          			

<tr>
	<td class="lable"><sup class="star">* </sup> <bean:message
			key="dispatch.label.originhubtally.region" /></td>

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
					onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
					<option value="Select">Select</option>
					<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
						<option value="${regions.regionId }">${regions.regionName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>

	<td class="lable"><sup class="star">* </sup><bean:message
			key="dispatch.label.originhubtally.station" /></td>


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
					class="selectBox width130" onchange="getBranchList('stationList')" onblur="getSalesPersonList(this)">
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.station" id="stationList"
					class="selectBox width130" onchange="getBranchList('stationList')" onblur="getSalesPersonList(this)">
					<option value="Select">Select</option>
					<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
						<option value="${city.cityId }" >
							${city.cityName}</option>
					</c:forEach>

				</select>
			</c:otherwise>

		</c:choose></td>

	<td class="lable"><bean:message
			key="label.stockreport.branch" /></td>

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
					onchange="getSalesPersonList(this)">
					<option value="Select">Select</option>
				</select>
			</c:when>
			<c:otherwise>
				<select name="to.station" id="branchList" class="selectBox width130"
					onchange="getSalesPersonList(this)">
					<option value="">Select</option>
					<c:forEach var="office" items="${wrapperReportTO.getOfficeTO()}">
						<option value="${office.officeId }">
							${office.officeName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>
</tr>
<tr>
									<td colspan="3"></td>
							</tr>
						
							<tr>
								<td class="lable"><bean:message key="label.salesreport.salesPerson" /></td>
								<td>
									<select name="salesPerson" id="salesPerson" class="selectBox width130" >
									<option value=""><bean:message key="sales.label.all" /></option><c:forEach var="product" items="${salesPersonTO}" varStatus="loop"><option value="${product.employeeId}"><c:out value="${product.firstName} ${product.lastName}"/></option></c:forEach></select>
								</td>
								
								<td class="lable"><sup class="star">* </sup>
								<bean:message key="label.salesreport.Product" /></td>
								<td><select name="product" id="product"
									onchange="getSalesSlabList(this);" class="selectBox width130">
										<option value="">Select</option>
										<c:forEach var="product" items="${productTo}">
											<option value="${product.rateProductCategoryId}">${product.rateProductCategoryName}</option>
										</c:forEach>

								</select></td>								
								
								
							</tr>
							<tr>
									<td colspan="3"></td>
							</tr>
						
							<tr>
							<td class="lable">Volume of Business</td>
								<td>
							<select name="slabList" id="slab"
									class="selectBox width130">
									<option value=""><bean:message key="sales.label.all"/></option>
								</select>
								</td>
								<td class="lable"><bean:message key="label.salesreport.business" /></td>
								<td>
									<select name="business" id="business" class="selectBox width130" >
									<option value=""><bean:message key="sales.label.all" /></option><c:forEach var="product" items="${businessTypeTO}" varStatus="loop"><option value="${product.rateIndustryTypeId}"><c:out value="${product.rateIndustryTypeName}"/></option></c:forEach></select>
																	
								</td>
								
								
							</tr>            			
          			
          			
                </table>
      		</div>
         
		</div>
    </div>
	<!-- Button -->
	<div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="printReport()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
</div>
</form>
<!--wraper ends-->
</body>
</html>
