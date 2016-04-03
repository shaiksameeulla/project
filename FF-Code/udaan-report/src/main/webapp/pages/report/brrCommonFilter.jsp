<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8"  src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/brrCommonReport.js"></script>
<script type="text/javascript" charset="utf-8" 	src="js/report/commonReport.js"></script>
</head>
<body>
<html:form  method="post" styleId="consignmentReportForm">
<%String reportName = (String) request.getParameter("reportName");
System.out.println("ReportName - " + reportName);
%>  
<div id="wraper"> 
	<div id="maincontent">
		<div class="mainbody">
  			<div class="formbox">
  			<% if(reportName.equals("Detailed")){  %>
	       		<h1>
							<bean:message
								key="label.brrreport.brrDetailedHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
				<%} %>
				
				<% if(reportName.equals("Summary")){  %>
	       		<h1>
							<bean:message
								key="label.brrreport.brrAllIndiaHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
				<%} %>
						
		  	</div>
		  
		    <div class="formTable">
		  		<table border="0" cellpadding="0" cellspacing="7" width="100%">
		  		<% if(reportName.equals("Detailed")){  %>
		  			<tr>
		  				<td class="lable"><sup class="star">* </sup><bean:message key="label.brrreport.reports"/></td>
					    <td>
					       <select name="status" id="status" class="selectBox width130"   >
							    <option value="DEL"><bean:message key="label.brrreport.delivered"/></option>
							    <option value="PWR"><bean:message key="label.brrreport.pr"/></option>
							    <option value="PWOR"><bean:message key="label.brrreport.pnr"/></option>
							    <option value="EXS"><bean:message key="label.brrreport.excesscn"/></option>
							    <option value="RTH"><bean:message key="label.brrreport.rth"/></option>
							    <option value="RTO"><bean:message key="label.brrreport.rto"/></option>
							    <option value="LST"><bean:message key="label.brrreport.lost"/></option>
							    <option value="RTN"><bean:message key="label.brrreport.rtc"/></option>
							    <option value="MSR"><bean:message key="label.brrreport.misrouted"/></option>
							    <option value="RCVD"><bean:message key="label.brrreport.received"/></option>
				   	        </select>
					 	</td>
		  			</tr>
		  			<%} %>
		  			<tr>
						<td class="lable"><sup class="star">* </sup><bean:message key="label.servicedRegion"/></td>
						<td><c:choose>
								<c:when test="${wrapperReportTO.getRegionTO().size() == 1}">
									<select name="to.regionTo" id="regionList"
										class="selectBox width130" disabled="disabled">
										<option
											value="${wrapperReportTO.getRegionTO().get(0).getRegionId() }"
											selected="selected">${
											wrapperReportTO.getRegionTO().get(0).getRegionName()}
										</option>
									</select>
								</c:when>
								<c:when test="${wrapperReportTO.getRegionTO().size() == 0}">
									<select name="to.regionTo" id="regionList"
										class="selectBox width130" >
										<option value=""><bean:message key="label.common.select"/></option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="to.regionTo" id="regionList"
										class="selectBox width130"
										onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
										<option value=""><bean:message key="label.common.select"/></option>
										<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
											<option value="${regions.regionId }">${regions.regionName}</option>
										</c:forEach>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
						<td class="lable"><sup class="star">* </sup><bean:message key="label.servicedStation"/></td>
						<% if(reportName.equals("Detailed")){  %>
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
										<option value="0"><bean:message key="label.all"/></option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="to.station" id="stationList"
										class="selectBox width130" onchange="getBranchList('stationList')">
										<option value="0"><bean:message key="label.all"/></option>
										<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
											<option value="${city.cityId }" >
												${city.cityName}</option>
										</c:forEach>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
						<%} %>
						<% if(reportName.equals("Summary")){  %>
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
										class="selectBox width130" >
										<option value="0"><bean:message key="label.all"/></option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="to.station" id="stationList"
										class="selectBox width130">
										<option value="0"><bean:message key="label.all"/></option>
										<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
											<option value="${city.cityId }" >
												${city.cityName}</option>
										</c:forEach>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
						<%} %>
						
						
						
						<%if(reportName.equals("Detailed")){%>
						<td class="lable"><sup class="star">* </sup><bean:message key="stock.label.payment.branch"/></td>
						<td><c:choose>
								<c:when test="${wrapperReportTO.getOfficeTO().size() == 1 }">
									<select name="to.station" id="branchList" class="selectBox width130"
										disabled="disabled">
										<option value="${reportTO.cityID }" selected="selected">
											${reportTO.city}</option>
									</select>
								</c:when>
								<c:when test="${wrapperReportTO.getOfficeTO().size() == 0 }">
									<select name="to.station" id="branchList" class="selectBox width130"
										onchange="getClientList('branchList')">
										<option value="0"><bean:message key="label.all"/></option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="to.station" id="branchList" class="selectBox width130"
										onchange="getClientList('branchList')">
										<option value="0"><bean:message key="label.all"/></option>
										<c:forEach var="office" items="${wrapperReportTO.getOfficeTO()}">
											<option value="${office.officeId }">
												${office.officeName}</option>
										</c:forEach>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
						<%} %>
					</tr>
					<tr>
					 	<td class="lable"><sup class="star">* </sup><bean:message key="label.brrDateWiseReport.series"/></td>
					    <td>
					    <select multiple="multiple" name="series" id="series"
																class="selectBox width200" >
																
							    <c:forEach var="product" items="${products}">
							    	<c:choose>
					          			<c:when test="${product.consgSeries != 'E'}">
							    				<option value="${product.consgSeries}">${product.consgSeries}</option>
							    		</c:when>
									</c:choose>
								</c:forEach>
							 </select>
					 	</td>
					 	<td class="lable"><sup class="star">* </sup><bean:message key="label.brrDateWiseReport.type"/></td>
					    <td>
					       <select name="type" id="type" class="selectBox width130"   >
							    <option value="ALL"><bean:message key="label.all"/></option>
							    <c:forEach var="type" items="${consignmentType}">
									<option value="${type.consignmentCode}">${type.consignmentName}</option>
								 </c:forEach>
					        </select>
					 	</td>
					 	<td class="lable"><sup class="star">* </sup><bean:message key="label.brrDateWiseReport.category"/></td>
					    <td>
					   		<select name="category" id="category" class="selectBox width130"   >
							     <option value="ALL"><bean:message key="label.all"/></option>
							     <c:forEach var="category" items="${category}">
									<option value="${category.categoryCode}">${category.categoryDesc}</option>
								 </c:forEach>
					        </select>
					 	 </td>
					 </tr>
 					<tr>
 					<%if(reportName.equals("Detailed")){%>
 						<td class="lable"><sup class="star">* </sup><bean:message key="label.brrDateWiseReport.load"/></td>
					    <td>
					   		<select name="load" id="load" class="selectBox width130"   >
							     <option value="ALL"><bean:message key="label.all"/></option>
							     <c:forEach var="loadval" items="${load}">
									<option value="${loadval.loadNo}">${loadval.loadDesc}</option>
								 </c:forEach>
					        </select>
					 	 </td>
					 <%} %>
			           	<td class="lable" ><sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.fromdate" />
        		 		</td>
               			<td>
            			  <input type="text" name="fromdate" style="height: 20px" value="" 
										id="fromdate" class="txtbox width130" readonly="readonly"/>
										 <a href="#"
											onclick="javascript:show_calendar('fromdate', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a>
               			</td>
            			<td class="lable" ><sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.todate" />
        		 		</td>
              			<td>
            			  <input type="text" name="todate" style="height: 20px" value="" 
										id="todate" class="txtbox width130" readonly="readonly"/> 
										<a href="#"
											onclick="javascript:show_calendar('todate', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a>
            			            			
            			</td>
		            </tr>
				</table>
			</div>
		</div>
  <div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="getBrrReport()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearBRRSummaryScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
  	 <input type="hidden" name = "Name" id="Name" value="<%=reportName %>" />
	</div> 
</div>
</html:form >     
</body>
</html>
