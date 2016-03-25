<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/notification/bulkSmsOnDemand.js"></script>

<script type="text/javascript">
var TODAY_DATE = '${TODAY_DATE}';
</script>
</head>
<body>
<div id="wraper">
	<html:form action="/bulkSmsOnDemand.do" method="post" styleId="bulkSmsOnDemandForm" >
		<div class="clear"></div>
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
        			<h1><bean:message key="label.title.bulkSmsOnDemand"/></h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.common.FieldsareMandatory"/></div>
        		</div>
        		<div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="3" width="100%">
        		<tr>
        			<td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.region"/></td>
        			<td>
        				<html:select property="to.regionId" styleId="region" onchange="getCitiesByRegion();" styleClass="selectBox width130"
            				onkeypress = "return callEnterKey(event, document.getElementById('city'));">
               				<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
                 			<logic:present name="regionList" scope="request">
								<logic:iterate id="region" name="regionList">
									<c:choose>
										<c:when test="${region.regionId == regionId}">
											<option value="${region.regionId}" selected="selected"><c:out value="${region.regionName}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${region.regionId}"><c:out value="${region.regionName}" /></option>
										</c:otherwise>
									</c:choose>
								</logic:iterate>
							</logic:present>
               			</html:select>
               		</td>
            		<td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp; <bean:message key="label.station"/></td>
                    <td>  	
                    	<html:select property="to.cityId" styleId="city" onchange="getOfficesByCity();" styleClass="selectBox width130"
                      		onkeypress = "return callEnterKey(event, document.getElementById('office'));">
                        	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
                        	<c:forEach var="city" items="${cityList}" varStatus="loop">
                            	<option value="${city.cityId}" ><c:out value="${city.cityName}"/></option>
                            </c:forEach>
                      	</html:select>
					</td>
            		<td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.branch"/></td>
                    <td>
            	   		<html:select property="to.officeId" styleId="office" styleClass="selectBox width130" 
            	   			 onkeypress = "return callEnterKey(event, document.getElementById('fromDate'));">
                          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
                          	<c:forEach var="office" items="${officeList}" varStatus="loop">
                            	<option value="${office.officeId}" ><c:out value="${office.officeName}"/></option>
                            </c:forEach>
                    	</html:select>
                    </td>
        		</tr>
        		<tr>
        			<td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.fromDate"/></td>
                    <td>
            	   		<html:text property="to.fromDate" styleId="fromDate" styleClass="txtbox width130" maxlength="10" readonly="true" onfocus="validateDate(this,'fromDate');" value="" onkeypress = "return callEnterKey(event, document.getElementById('toDate'));"/>&nbsp;
						<!-- <a href="#" onclick="setDate(this,'fromDate');" title="Select Date" id="fromDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a> -->						
						<img width="14" border="0" height="17" onclick="setYears(1980, 2030);showCalender(this, 'fromDate');" longdesc="#" alt="Select Date" src="images/icon_calendar.gif"/>
                    </td>
                    <td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.toDate"/></td>
                    <td>
            	   		<html:text property="to.toDate" styleId="toDate" styleClass="txtbox width130" maxlength="10" readonly="true" onfocus="validateDate(this,'toDate');" value="" onkeypress = "return callEnterKey(event, document.getElementById('cnStatus'));"/>&nbsp;
						<!-- <a href="#" onclick="setDate(this,'toDate');" title="Select Date" id="toDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a> -->
						<img width="14" border="0" height="17" onclick="setYears(1980, 2030);showCalender(this, 'toDate');" longdesc="#" alt="Select Date" src="images/icon_calendar.gif"/>
                    </td>
        		</tr>
        		<tr>
        			<td align="left" class="lable" width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.cnStatus"/></td>
        			<td>
        				<html:select property="to.cnStatus" styleId="cnStatus" styleClass="selectBox width130" onkeypress = "return callEnterKey(event, document.getElementById('searchBtn'));">
                          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option> 
                          	<c:forEach var="statusMap" items="${cnStatusList}" varStatus="loop">
            					<option value="${statusMap.key}" ><c:out value="${statusMap.value}"/></option>
            	 			</c:forEach>
                    	</html:select>&nbsp;
                    	<html:button property="search" styleClass="btnintgrid" styleId="searchBtn" onclick="fnGetConsignmentDetails();">
						<bean:message key="button.search"/></html:button>
                    </td>
        		</tr>
        		</table> 
        		</div>
        		<div id="demo" >
					<div class="title">
						<div class="title2">
							<bean:message key="label.bulkSms.consignmentDtls" locale="display" />
						</div>
					</div>
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataGrid" width="100%">
						<thead>
							<tr>
								<th width="5%"><bean:message key="common.label.serialNo" locale="display" /></th>
								<th width="25%"><bean:message key="label.cnNo" locale="display" /></th>
								<th width="15%"><bean:message key="label.bookingDate" locale="display" /></th>
								<th><bean:message key="label.reason" locale="display" /></th>
								<th width="20%"><bean:message key="label.mobileNo" locale="display" /></th>
							</tr>
						</thead>
					</table>											
				</div> 
        	</div>
        </div>
        <div class="button_containerform">
				<html:button property="sendSms" styleClass="btnintform" styleId="sendSms" onclick="fnSendBulkSMS();">
					<bean:message key="button.label.SendSms" />
				</html:button>
				<html:button property="clear" styleClass="btnintform" styleId="clear" onclick="fnClear();">
					<bean:message key="button.clear" />
				</html:button>
			</div>
	</html:form> 
</div>
</body>
</html>