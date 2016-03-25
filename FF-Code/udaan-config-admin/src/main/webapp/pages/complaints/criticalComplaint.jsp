<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/complaints/criticalComplaint.js"></script>
<script type="text/javascript" charset="utf-8" src="js/complaints/complaint.js"></script>

<script type="text/javascript" charset="utf-8">
var criticalComplaintForm = "criticalComplaintForm";
var YES = "Y";
var NO = "N";
var TODAY_DATE = '${TODAY_DATE}';

$(document).ready( function () {
	var ccURL = '${criticalComplaintURL}';
	if(!isNull(ccURL)){
		window.location = ccURL;
	}
	criticalComplaintStartup();
});

</script>
</head>
<body>
<html:form action="/criticalComplaint.do" method="post" enctype="multipart/form-data" styleId="criticalComplaintForm">
<div class="formTable">
	<table border="0" cellpadding="2" cellspacing="5" width="100%">
		<tr>
			<td width="12%" class="lable"><bean:message key="label.critical.complaint.dateTime" /></td>
			<td width="25%"><html:text property="to.complaintCreationDateStr" styleId="complaintCreationDateStr" styleClass="txtbox width160" readonly="true" tabindex="-1" /></td>
			<td width="12%" class="lable"><bean:message key="label.critical.complaintNo" /></td>
			<td width="16%"><html:text property="to.complaintNo" styleId="cmplntNo" styleClass="txtbox width160" readonly="true" maxlength="12" tabindex="-1" /></td>
			<td width="15%" class="lable"><bean:message key="label.critical.complaint.consgNo" /></td>
			<td width="20%"><html:text property="to.consignmentNumber" styleId="consignmentNumber" styleClass="txtbox width160" readonly="true" maxlength="12" tabindex="-1" /></td>
		</tr>
		<tr>
			<td class="lable"><bean:message key="label.critical.complaint.branch" /></td>
			<td><html:text property="to.branch" styleId="branch" styleClass="txtbox width160" readonly="true" maxlength="50" tabindex="-1" /></td>
			<td class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.complaint.remark" /></td>
			<td><textarea name="to.reason" id="reason" maxlength="500" class="txtbox width155" ></textarea></td>
			<td class="lable"><bean:message key="label.critical.complaint.decValue" /></td>
			<td><html:text property="to.declaredValue" styleId="declaredValue" styleClass="txtbox width160" readonly="true" maxlength="10" tabindex="-1" /></td>
		</tr>
		<tr>
			<td class="lable"><bean:message key="label.critical.complaint.consignerName" /></td>
			<td><html:text property="to.consignerName" styleId="consignerName" styleClass="txtbox width160" readonly="true" maxlength="50" tabindex="-1" /></td>
			<td class="lable"><bean:message key="label.critical.complaint.custCode" /></td>
			<td><html:text property="to.customerCode" styleId="customerCode" styleClass="txtbox width160" readonly="true" maxlength="20" tabindex="-1" /></td>
			<td class="lable"><bean:message key="label.critical.complaint.address" /></td>
			<td><html:text property="to.customerAddress" styleId="customerAddress" styleClass="txtbox width160" readonly="true" maxlength="50" tabindex="-1" /></td>
		</tr>
		<tr>
			<td class="lable"><bean:message key="label.critical.complaint.phone" /></td>
			<td><html:text property="to.customerPhone" styleId="customerPhone" styleClass="txtbox width160" readonly="true" maxlength="12" tabindex="-1" /></td>
			<td class="lable"><bean:message key="label.critical.complaint.email" /></td>
			<td><html:text property="to.customerEmail" styleId="customerEmail" styleClass="txtbox width160" maxlength="50" onchange="validateEmailId(this);"/></td>
			<td class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.complaint.infoGivenTo" /></td>
			<td>
				<%-- <html:select property="to.informationGivenTo" styleId="informationGivenTo" styleClass="selectBox width150">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<logic:present scope="request" name="infoGivenTo">
						<c:forEach var="info" items="${infoGivenTo}" varStatus="loop">
							<html:option value="${info.stdTypeCode}">${info.description}</html:option>
						</c:forEach>
					</logic:present>
				</html:select> --%>
				<html:text property="to.informationGivenTo" styleId="informationGivenTo" styleClass="txtbox width160" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td class="lable"><bean:message key="label.critical.complaint.mailer" /></td>
			<td>
				<html:file property="to.mailerFile" styleId="mailerFile" styleClass="txtbox width150" />
			</td>
			<td class="lable"><sup class="star"><bean:message key="symbol.common.star" /></sup><bean:message key="label.complaints.service.status" /></td>
			<td>
				<html:select property="to.criticalCmpltStatus" styleId="criticalCmpltStatus" styleClass="selectBox width150">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<logic:present scope="request" name="criticalCmpltStatus">
						<c:forEach var="crCmpltStatus" items="${criticalCmpltStatus}" varStatus="loop">
							<html:option value="${crCmpltStatus.stdTypeCode}">${crCmpltStatus.description}</html:option>
						</c:forEach>
					</logic:present>
				</html:select>
			</td>
			<td class="lable">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>

	<div class="columnuni">
		<div class="columnleftcaller">
			<fieldset>
				<legend>&nbsp;<bean:message key="label.critical.complaint.FIRFollowUp" />&nbsp;</legend>
				<table border="0" cellpadding="2" cellspacing="5" width="100%">
					<tr>
						<td width="25%" class="lable"><bean:message key="label.critical.complaint.FIRCopyRcvd" /></td>
						<td width="10%">
							<html:radio styleClass="radio" property="to.firCopy" styleId="firCopyYes" value="Y" onclick="setFIRCopyValue(this);" /><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
							<html:radio styleClass="radio" property="to.firCopy" styleId="firCopyNo" value="N" onclick="setFIRCopyValue(this);" /><bean:message key="label.critical.complaint.NO" />
						</td>
						<td width="12%" class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td width="18%">
							<html:text property="to.firDateStr" styleId="firDateStr" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this,'firDateStr');"/>&nbsp;
							<a href="#" onclick="setSelectedDate(this,'firDateStr');" title="Select Date" id="firDtStr" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td width="10%" class="lable"><bean:message key="label.critical.complaint.remark" /></td>
						<td width="25%"><textarea name="to.remark" id="remark" class="txtbox width160" maxlength="500" ></textarea></td>
					</tr>
					<tr>
						<td class="lable"><bean:message key="label.critical.complaint.custType" /></td>
						<td>
							<html:radio styleClass="radio" property="to.customerType" styleId="customerTypeYes" value="Y" onclick="setCustomerTypeValue(this);" /><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
							<html:radio styleClass="radio" property="to.customerType" styleId="customerTypeNo" value="N" onclick="setCustomerTypeValue(this);" /><bean:message key="label.critical.complaint.NO" />
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.typeDateStr" styleId="typeDateStr" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this,'typeDateStr');"/>&nbsp;
							<a href="#" onclick="setSelectedDate(this,'typeDateStr');" title="Select Date" id="typeDtStr" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.FOC" /></td>
						<td><html:text property="to.focNumber" styleId="focNumber" styleClass="txtbox width160" maxlength="30" onkeypress="return alphaNumAllow(event);" /></td>
					</tr>
					<tr>
						<td class="lable"><bean:message key="label.critical.complaint.LIRCopyReceived" /></td>
						<td>
							<html:radio styleClass="radio" property="to.lirCopy" styleId="lirCopyYes" value="Y" onclick="setLIRCopyValue(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
							<html:radio styleClass="radio" property="to.lirCopy" styleId="lirCopyNo" value="N" onclick="setLIRCopyValue(this);"/><bean:message key="label.critical.complaint.NO" />
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.lirDateStr" styleId="lirDateStr" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this,'lirDateStr');"/>&nbsp;
							<a href="#" onclick="setSelectedDate(this,'lirDateStr');" title="Select Date" id="lirDtStr" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.lirRemarks" /></td>
						<td><textarea name="to.lirRemarks" id="lirRemarks" class="txtbox width160" maxlength="500" ></textarea></td>
					</tr>
					<tr>
						<td class="lable"><bean:message key="label.critical.complaint.LostLetter" /></td>
						<td>
							<html:radio styleClass="radio" property="to.lostLetter" styleId="lostLetterYes" value="Y" onclick="setLostLetterValue(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
							<html:radio styleClass="radio" property="to.lostLetter" styleId="lostLetterNo" value="N" onclick="setLostLetterValue(this);"/><bean:message key="label.critical.complaint.NO" />
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.lostLetterDateStr" styleId="lostLetterDateStr" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this,'lostLetterDateStr');"/>&nbsp;
							<a href="#" onclick="setSelectedDate(this,'lostLetterDateStr');" title="Select Date" id="lostLetterDtStr" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.LostLetterRemarks" /></td>
						<td><textarea name="to.lostLetterRemarks" id="lostLetterRemarks" class="txtbox width160" maxlength="500" ></textarea></td>
					</tr>
					<tr>
						<td class="lable"><bean:message key="label.critical.complaint.COFCopyReceived" /></td>
						<td>
							<html:radio styleClass="radio" property="to.cofCopy" styleId="cofCopyYes" value="Y" onclick="setCOFCopyValue(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
							<html:radio styleClass="radio" property="to.cofCopy" styleId="cofCopyNo" value="N" onclick="setCOFCopyValue(this);"/><bean:message key="label.critical.complaint.NO" />
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.cofDateStr" styleId="cofDateStr" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" onfocus="validateSelectedDate(this,'cofDateStr');"/>&nbsp;
							<a href="#" onclick="setSelectedDate(this,'cofDateStr');" title="Select Date" id="cofDtStr" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td class="lable"><bean:message key="label.critical.complaint.cofRemarks" /></td>
						<td><html:text property="to.cofRemarks" styleId="cofRemarks" styleClass="txtbox width160" maxlength="150" /></td>
					</tr>
				</table>
			</fieldset>
		</div>
		<!-- Hidden Fields START -->
		<html:hidden property="to.createdBy" styleId="createdBy" />
		<html:hidden property="to.updatedBy" styleId="updatedBy" />
		<html:hidden property="to.complaintId" styleId="cmplntId" />
		<html:hidden property="to.serviceRequestComplaintId" styleId="serviceRequestComplaintId" />
		<html:hidden property="to.mailerId" styleId="mailerId" />
		<html:hidden property="to.mailerFileName" styleId="mailerFileName" />
		<html:hidden property="to.mailerCreatedDateStr" styleId="mailerCreatedDateStr" />
		<html:hidden property="to.todaysDate" styleId="todaysDate" />
		<!-- Hidden Fields END -->
	</div>
	<!-- Button -->
	<div class="button_containerform"><br/>
		<html:button property="Save" styleId="Save" styleClass="btnintform" onclick="saveCriticalComplaintDtls();">
					<bean:message key="button.save" /></html:button>
		<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>
	</div>
	<!-- Button ends -->
</div>
</html:form>
</body>
</html>