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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/report/stockUsedAndUnusedReport.js"></script>
<script type="text/javascript" src="js/report/commonReport.js"></script>
<script type="text/javascript" charset="utf-8"
 src="js/calendar/ts_picker.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		transferStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'consignmentReportForm');
		}
	});
	
	
</script>
</head>
<body >
<!--wraper-->



		
			<form method="post" id="consignmentReportForm" name="consignmentReportForm"
		action="/udaan-report/stockUsedNUnused.do?submitName=getStockUsedAndUnusedReport">
<div id="wraper">
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
           	<div class="formbox">
       			<h1><bean:message key="label.stockreport.stockUsedAndUnusedReport" /></h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory" /></div>
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
					onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
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
					class="selectBox width130" onchange="getBranchList('stationList')">
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.station" id="stationList"
					class="selectBox width130" onchange="getBranchList('stationList')">
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
				<select name="to.station" id="branchList" class="selectBox width130" onchange="getClientListForStock('branchList');">
					<option value="${wrapperReportTO.getOfficeTO().get(0).getOfficeId() }"
						selected="selected" >
						${wrapperReportTO.getOfficeTO().get(0).getOfficeName()}</option>
				</select>
			</c:when>
			<c:when test="${wrapperReportTO.getOfficeTO().size() == 0 }">
				<select name="to.station" id="branchList" class="selectBox width130"
					onchange="getClientListForStock('branchList');">
					<option value="Select">Select</option>
				</select>
			</c:when>
			<c:otherwise>
				<select name="to.station" id="branchList" class="selectBox width130"
					onchange="getClientListForStock('branchList');">
					<option value="Select">Select</option>
					<c:forEach var="office" items="${wrapperReportTO.getOfficeTO()}">
						<option value="${office.officeId }">
							${office.officeName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>
</tr>
						<tr>
									<td colspan="2"></td>
							</tr>
						
							<tr>
								<td class="lable"><sup class="star">* </sup><bean:message key="label.stockreport.periodFrom" /></td>
								<td>
									<input type="text" name="to.dispatchDate" style="height: 20px" value="" 
													id="startDate" class="txtbox width130" readonly="readonly"> <a href="#"
														onclick="javascript:show_calendar('startDate', this.value)"
														title="Select Date"><img src="images/icon_calendar.gif"
															alt="Search" width="16" height="16" border="0"
															class="imgsearch" /></a>	
								</td>
								
								<td class="lable"><sup class="star">* </sup><bean:message key="label.stockreport.periodTo" /></td>
								<td>
								<input type="text" name="to.dispatchDate1" style="height: 20px" value="" 
													id="endDate" class="txtbox width130" readonly="readonly"> <a href="#"
														onclick="javascript:show_calendar('endDate', this.value)"
														title="Select Date"><img src="images/icon_calendar.gif"
															alt="Search" width="16" height="16" border="0"
															class="imgsearch" /></a>
								</td>
								<td colspan="1"></td>
							</tr>
							<tr>
								<td class="lable"><bean:message key="label.stockreport.product" /></td>
								<td>
									<select name="to.productTo" id="productTo" class="selectBox width130" >
									<option value="8,9,10,11,12,13">All</option><c:forEach var="product" items="${productTo}" varStatus="loop"><option value="${product.itemTypeId}"><c:out value="${product.itemTypeName}"/></option></c:forEach></select>
								
								</td>
								
								<td class="lable"><bean:message key="label.stockreport.client" /></td>
								<td>
								<select name="to.customerList" id="client" class="selectBox width130" >
									
									
										
								</select>
								</td>
								<td colspan="2"><br /> <br /></td>
							</tr>	
          			
                </table>
      		</div>
         
		</div>
    </div>
    
   
	<!-- Button -->
	<div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="printReport()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearSUScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
</div>
</form>
<!--wraper ends-->
</body>
</html>
