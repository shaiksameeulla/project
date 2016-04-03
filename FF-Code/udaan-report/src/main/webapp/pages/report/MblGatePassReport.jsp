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
<script type="text/javascript" charset="utf-8" src="js/report/MblGatePassReport.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/commonReport.js"></script>
</head>
<body>
<form method="post" name="consignmentReportForm" id="consignmentReportForm"
		action="/udaan-report/mblGatePassReport.do?submitName=viewFormDetails">
<div id="wraper">
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
           	<div class="formbox">
       			<h1>MBPL GATEPASS REPORT</h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="dispatch.label.issue.mandatory" /></div>
    		</div>
           	<div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="10" width="100%">
        		
        		  <tr>
          		 		<td class="lable" >
        		 			<bean:message key="dispatch.label.mblgatepass.date" />
        		 		</td>
   	
            			<td >
            			  <input type="text" name="dispatchDate" style="height: 20px" value="" 
										id="dispatchDate" class="txtbox width130" readonly="readonly"> <a href="#"
											onclick="javascript:show_calendar('dispatchDate', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a>
            			            			
            			</td>
        		       <%-- 	<td class="lable" ><bean:message key="dispatch.label.mblgatepass.mblGatepassNo" /></td>
            			<td>
            				<input type="text" name="mblGatepassNo" style="height: 20px" id="mblGatepassNo" value="" class="txtbox width145" maxlength = "12" onkeypress="return onlyAlphabet(event)"/>            				            			
            			</td> --%>
            			     			
            			
            			<td class="lable"><bean:message key="dispatch.label.mblgatepass.reportType" /></td>
            			<td>
            			    <select name="reportType" id="reportType" class="selectBox width130"   >
            					<option value=""><bean:message key="dispatch.label.select" /></option>
                				<option value="D"><bean:message key="dispatch.label.mblgatepass.dispatch" /></option>
                				<option value="R"><bean:message key="dispatch.label.mblgatepass.received" /></option>
                             </select>
              				
              			</td>
    
              	 </tr>
              	 <!-- tr>
            			<td class="lable"><sup class="star">* </sup><bean:message key="dispatch.label.mblgatepass.originRegion" /></td>
               			<td>
            			<select name="originRegion" id="originRegion" class="selectBox width130" onchange="getOrgStationsList();">
                   				<option value=""><bean:message key="dispatch.label.select" /></option>
                          		
                          			<logic:present name="regionList" scope="request">
											<logic:iterate id="regions" name="regionList">
												<c:choose>
													<c:when test="${ regions.regionId==branchUserRegion}">
														<option value="${regions.regionId}" selected="selected" >
															<c:out value="${regions.regionName}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${regions.regionId}">
															<c:out value="${regions.regionName}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
                				
              				</select>
              			</td>
              			
            			<td class="lable"><sup class="star">* </sup><bean:message key="dispatch.label.mblgatepass.originStation" /></td>
            			<td>
	               			<select name="originStation" id="originStation" class="selectBox width130">
								<option value=""><bean:message key="label.common.select"/></option>
							</select>
               			</td>
              	</tr-->
              	<tr>	
              		<%--  <%@include file="commonFilter.jsp" %>	 --%>
              		 
              			<td class="lable"><sup class="star">* </sup> <bean:message
			key="label.consgBookingReport.region" /></td>

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
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.regionTo" id="regionList"
					class="selectBox width130"
					onchange="getMbplStationsList('regionList', 'stationList,destRegionList');">
					<option value="Select">Select</option>
					<option value="All">All</option>
					<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
						<option value="${regions.regionId }">${regions.regionName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>

	<td class="lable"> </sup> <bean:message
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
					class="selectBox width130" >
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.station" id="stationList"
					class="selectBox width130" >
					<option value="Select">Select</option>
					<option value="All">All</option>
					<c:forEach var="city" items="${wrapperReportTO.getCityTO()}">
						<option value="${city.cityId }" >
							${city.cityName}</option>
					</c:forEach>

				</select>
			</c:otherwise>

		</c:choose></td>

              		 
              		 
              			<td class="lable"><sup class="star">* </sup><bean:message key="dispatch.label.mblgatepass.destinationRegion" /></td>
            			<td>
            			    <select name="destinationRegion" id="destinationRegion" class="selectBox width130" onchange="getDestStationsList();">
            				  <option value=""><bean:message key="dispatch.label.select" /></option>
                          		<logic:present name="destRegionList" scope="request">
                          		<option value="All">All</option>
											<logic:iterate id="regions" name="destRegionList">
												<c:choose>
												
													<c:when test="${ regions.regionId==branchUserRegion}">
														<option value="${regions.regionId}" selected="selected" >
															<c:out value="${regions.regionName}" />
														</option>
													</c:when>
													<c:otherwise>
														
														<option value="${regions.regionId}">
															<c:out value="${regions.regionName}" />
														</option>
														
													</c:otherwise>
												</c:choose>
											</logic:iterate>
								</logic:present>
              			</td>
       			
            			
          		</tr>
          		<tr>	
          				<td class="lable" ><bean:message key="dispatch.label.mblgatepass.destinationStation" /></td>
            			<td>
          					<select name="destinationStation" id="destinationStation" class="selectBox width130">
									<option value=""><bean:message key="label.common.select"/></option>
							</select>
              			</td>	
              			<%-- <td class="lable"><bean:message key="dispatch.label.mblgatepass.mode" /></td> --%>
            			<%-- <td>
               				<select name="mode" id="mode" class="selectBox width130"   >
            			
                				<option value=""><bean:message key="dispatch.label.select" /></option>
                				<option value="1">Air</option>
                				<option value="2">Rail</option>
                				<option value="3">Road</option>
              				</select>
              			</td> --%>
            			<%-- <td class="lable" ><bean:message key="dispatch.label.mblgatepass.loadForwardedBy" /></td>
            			<td><input type="text" name="loadForwardedBy" style="height: 20px" id="loadForwardedBy" value="" class="txtbox width145"/></td> --%>
            			
           		</tr>
          		<%-- <tr>
          				<td class="lable" ><bean:message key="dispatch.label.mblgatepass.flightTrainNo" /></td>
            			<td><input type="text" name="flightTrainNo" style="height: 20px" id="flightTrainNo" value="" class="txtbox width145"/></td>
            			<td class="lable"><bean:message key="dispatch.label.mblgatepass.cdAwbRr" /></td>
                	    <td><input type="text" name="cdAwbRr" style="height: 20px" id="cdAwbRr" value="" class="txtbox width145"/></td>
                	  	<td class="lable"><bean:message key="dispatch.label.mblgatepass.std" /></td>
            			<td><input type="text" name="std" style="height: 20px" id="std" value="" class="txtbox width145"/></td>
            		
            			
            			
          		</tr>
                <tr>
                		<td class="lable"><bean:message key="dispatch.label.mblgatepass.sta" /></td>
            			<td><input type="text" name="sta" style="height: 20px" id="sta" value="" class="txtbox width145"/></td>
                		<td class="lable"><bean:message key="dispatch.label.mblgatepass.vehicleNo" /></td>
            			<td><input type="text" name="vehicleNo" style="height: 20px" id="vehicleNo" value="" class="txtbox width145"/></td>
            			<td class="lable"><bean:message key="dispatch.label.mblgatepass.driverName" /></td>
                	    <td><input type="text" name="driverName" style="height: 20px" id="driverName" value="" class="txtbox width145"/></td>
                </tr>
                <tr>
                		<td class="lable"><bean:message key="dispatch.label.mblgatepass.departueTime" /></td>
            			<td><input type="text" name="departueTime" style="height: 20px" id="departueTime" value="" class="txtbox width145" onkeypress="return onlyNumeric(event)" maxlength="5" /></td>
            			<td class="lable"><bean:message key="dispatch.label.mblgatepass.arrivalTime" /></td>
            			<td><input type="text" name="arrivalTime" style="height: 20px" id="arrivalTime" value="" class="txtbox width145" onkeypress="return onlyNumeric(event)" maxlength="5"/></td>	   	
            			
          		</tr> --%>
  	
                </table>
      		</div>
  		</div>
    </div>
	<div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="getMblGatePassDetails()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearFilterScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
</div>
</form>
</body>
</html>
