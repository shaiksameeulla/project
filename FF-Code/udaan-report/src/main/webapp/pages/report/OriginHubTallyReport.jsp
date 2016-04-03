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
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/OriginHubTally.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/commonReport.js"></script>
</head>
<body>
<form method="post" id="consignmentReportForm"
		action="/udaan-report/originHubTallyReport.do?submitName=viewFormDetails">
<div id="wraper"> 
	<div class="clear">
	</div>
	<div id="maincontent">
		<div class="mainbody">
  
		  <div class="formbox">
		       <h1>ORIGIN HUB TALLY</h1>
		       <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.common.FieldsareMandatory"/></div>
		  </div>
 		  <div class="formTable">
		  		<table border="0" cellpadding="0" cellspacing="7" width="100%">
		  		<tr>
					<td class="lable"><sup class="star">* </sup> <bean:message
							key="label.consgBookingReport.region" />
					</td>
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
								onchange="getHubStationsList('regionList', 'stationList,branchList,destRegionList');">
								<option value="Select">Select</option>
								<c:forEach var="regions" items="${wrapperReportTO.getRegionTO()}">
									<option value="${regions.regionId }">${regions.regionName}</option>
								</c:forEach>
							</select>
						</c:otherwise>
						</c:choose>
					</td>
					<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
						key="label.consgBookingReport.station" />
					</td>
					<td><c:choose>
						<c:when test="${wrapperReportTO.getCityTO().size() == 1}">
							<select name="to.station" id="stationList"
								class="selectBox width130" onchange="getHubOfficeList();" onblur="getCountOfHubs()">
								<option value="Select">Select</option>
								<option value="${wrapperReportTO.getCityTO().get(0).getCityId() }">
									${wrapperReportTO.getCityTO().get(0).getCityName()}
								</option>
							</select>
						</c:when>
						<c:when test="${wrapperReportTO.getCityTO().size() == 0}">
							<select name="to.station" id="stationList"
								class="selectBox width130" onchange="getHubOfficeList();" onblur="getCountOfHubs()">
								<option value="Select">Select</option>
							</select>
						</c:when>
					<c:otherwise>
							<select name="to.station" id="stationList"
								class="selectBox width130" onchange="getHubOfficeList();" onblur="getCountOfHubs()">
								<option value="Select">Select</option>
								<c:forEach var="city" items="${wrapperReportTO.getCityTO()}" >
								<option value="${city.cityId }" >
									${city.cityName}</option>
								</c:forEach>
							</select>
					</c:otherwise>
				</c:choose>
			</td>
	         <!-- tr> <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.region" /></td>
               			<td>
            			<select name="region" id="region" class="selectBox width130" onchange="getStationsList();">
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
              			<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.station" /></td>
            			<td>
	               			<select name="station" id="station" class="selectBox width130" onchange="getHubOfficeList();">
								<option value=""><bean:message key="label.common.select"/></option>
							</select>
               			</td>
               		</tr-->
               	<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.originhubtally.hub" />
               	</td>
            	<td>
	               			<select name="office" id="office" class="selectBox width130" onchange="getClientList();">
								<option value=""><bean:message key="label.common.select"/></option>
							</select>
               	</td>
             <tr>
		         <td class="lable"><%-- <bean:message key="dispatch.label.originhubtally.client" /> --%></td>
            			<td>
            				<%-- <select name="client" id="client" class="selectBox width130">
								<option value=""><bean:message key="label.common.select"/></option>
							</select> --%>
	               		 	
               			</td>
  		                    	        
		            
			           	<td class="lable" ><%-- <sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.fromdate" /> --%>
        		 		</td>
        		 		
            			<td>
            			  <!-- <input type="text" name="fromdate" style="height: 20px" value="" 
										id="fromdate" class="txtbox width130" readonly="readonly"/>
											 <a href="#"
											onclick="javascript:show_calendar('fromdate', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a> -->
            			            			
            			</td>
            			<td class="lable" ><sup class="star">*</sup>&nbsp;<bean:message key="dispatch.label.originhubtally.date" />
        		 		</td>
        		 		
            			<td >
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
</div>
   <div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="getOrginhubTally()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearOriginHubScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
</div>
<!--wraper ends-->
</form>
</body>
</html>
