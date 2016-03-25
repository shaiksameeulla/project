<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<head>
<script type="text/javascript" charset="utf-8" src="js/complaints/criticalClaimComplaint.js"></script>
<script type="text/javascript" charset="utf-8" src="js/complaints/complaint.js"></script>
<script type="text/javascript" src="js/common.js" ></script>
<script type="text/javascript" charset="utf-8">
var YES = 'Y';
var NO = 'N';
var LOCAL = 'LOCAL';
var CORP = 'CORP';
var TODAY_DATE = '${TODAY_DATE}';
/* var complaintNumber = "${complaintNumber}";
var complaintId = "${complaintId}";
var consignmentNumber = "${consignmentNumber}"; */
var criticalClaimComplaintForm = "criticalClaimComplaintForm";
</script>
</head>
<body>
<html:form action ="/criticalClaimComplaint.do" method="post" styleId="criticalClaimComplaintForm">
<div class="formTable">
	<table border="0" cellpadding="1" cellspacing="1" width="100%">
		<tr>
			<td width="18%" class="lable">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.claim.complaint.actualClaim" />
			</td>
			<td width="17%" align="left">
				<input type="radio" class="radio" name="isActualClaim" id="actualClaimYes" value="Y" onclick="setActualClaim(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
				<input type="radio" class="radio" name="isActualClaim" id="actualClaimNo" value="N" onclick="setActualClaim(this);"/><bean:message key="label.critical.complaint.NO" />
				<html:hidden property="to.isActualClaim" styleId="isActualClaim" />
			</td>	
			<td width="12%" class="lable"><bean:message key="label.critical.claim.complaint.claimAmt" /></td>
			<td width="22%"><html:text property="to.actualClaimAmt" styleId="actualClaimAmt"  onkeypress="return onlyDecimal(event); isEmptyAmount(event);" styleClass="txtbox width130" /></td>
			<td width="17%" class="lable"><bean:message key="label.critical.claim.complaint.paperWork" /></td>
			<td width="27%"><html:text property="to.paperWork" styleId="paperWork" maxlength="30" styleClass="txtbox width130" /></td>
		</tr>
		
		<tr>
			<td class="lable">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.claim.complaint.negotiableClaim" />
			</td>
			<td align="left">
				<input type="radio" class="radio" name="isNegotiableClaim" id="negotiableClaimYes" value="Y"  onclick="setNegotiableClaim(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
				<input type="radio" class="radio" name="isNegotiableClaim" id="negotiableClaimNo" value="N"  onclick="setNegotiableClaim(this);"/><bean:message key="label.critical.complaint.NO" />
				<html:hidden property="to.isNegotiableClaim" styleId="isNegotiableClaim" />
			</td>
			<td class="lable"><bean:message key="label.critical.claim.complaint.claimAmt" /></td>
			<td><html:text property="to.negotiableClaimAmt" styleId="negotiableClaimAmt"  onkeypress="return onlyDecimal(event);" styleClass="txtbox width130" /></td>
			<td class="lable"><bean:message key="label.critical.claim.complaint.department" /></td>
			<td><html:text property="to.accountability" styleId="accountability" maxlength="50" styleClass="txtbox width130" /></td>
		</tr>
		
		<tr>
			<td class="lable">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.complaint.cof" />
			</td>
			<td align="left">
				<input type="radio" class="radio" name="isCof" id="cofYes" value="Y" onclick="setCof(this);"/><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
				<input type="radio" class="radio" name="isCof" id="cofNo" value="N" onclick="setCof(this);"/><bean:message key="label.critical.complaint.NO" />
				<html:hidden property="to.isCof" styleId="isCof" />
			</td>
			<td class="lable">Salvage Amount:<%-- <bean:message key="label.critical.claim.complaint.claimAmt" /> --%></td>
			<td><html:text property="to.cofAmt" styleId="cofAmt" onkeypress="return onlyDecimal(event);" styleClass="txtbox width130" /></td>
			<td class="lable"><bean:message key="label.critical.claim.complaint.clientPolicy" /></td>
			<td><html:text property="to.clientPolicy" styleId="clientPolicy" maxlength="50" styleClass="txtbox width130" /></td>
		</tr>
		
		<tr>
			<td class="lable">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.claim.complaint.settlement" />
			</td>
			<td align="left">
				<input type="radio" class="radio" name="isSettlement" id="settlementYes" value="LOCAL"  onclick="setSettlement(this);" /><bean:message key="label.critical.claim.complaint.LOCAL" />&nbsp;&nbsp;
				<input type="radio" class="radio" name="isSettlement" id="settlementNo" value="CORP"  onclick="setSettlement(this);" /><bean:message key="label.critical.claim.complaint.CORP" />
				<html:hidden property="to.isSettlement" styleId="isSettlement" />
			</td>
			<td></td>
			<td></td>
			<td class="lable"><bean:message key="label.critical.claim.complaint.missingCertificate" /></td>
			<td><html:text property="to.missingCertificate" styleId="missingCertificate" maxlength="50" styleClass="txtbox width130" /></td>
		</tr>
		
		<tr>
			<td class="lable">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.critical.claim.complaint.settled" />
			</td>
			<td align="left">
				<input type="radio" class="radio" name="isSettled" id="isSettledYes" value="Y" onclick="setSettled(this);" /><bean:message key="label.critical.complaint.YES" />&nbsp;&nbsp;
				<input type="radio" class="radio" name="isSettled" id="isSettledNo" value="N" onclick="setSettled(this);" /><bean:message key="label.critical.complaint.NO" />
				<html:hidden property="to.isSettled" styleId="isSettled" />
			</td>
			<td></td>
			<td></td>
			<td rowspan="2" class="lable"><bean:message key="label.expense.remark" /></td>
			<td rowspan="2"><textarea maxlength="500" name="to.remark" id="claimRemark" class="width130" style="width: 251px; height: 76px; resize:none"></textarea>
			<%-- <html:textarea maxlength="500" property="to.remark" styleId="claimRemark" styleClass="width130" style="width: 251px; height: 76px; resize:none" /> --%>
			</td>
		</tr>
		
		<tr>
			<td class="lable" valign="top">
				<sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.followup.status"/>
			</td>
			<td align="left" valign="top">
				<html:select property="to.claimComplaintStatus" styleId="claimComplaintStatus" styleClass="selectBox width150">
					<logic:present scope="request" name="claimComplaintStatusList">
						<c:forEach var="status" items="${claimComplaintStatusList}" varStatus="loop">
							<html:option value="${status.stdTypeCode}">${status.description}</html:option>
						</c:forEach>
					</logic:present>
				</html:select>
			</td>
			<td></td>
			<td></td>
			<td class="lable"></td>
			<td></td>
		</tr>
	</table>	
	
	<div class="columnuni">
		<div class="columnleftcaller">
			<fieldset>
				<legend>&nbsp;<bean:message key="label.critical.claim.complaint.feedBackForm" />&nbsp;</legend>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
					
						<td width="17%" class="lable"><bean:message key="label.critical.claim.complaint.salesManagerFeedback" /></td>
						<td width="15%"><textarea name="to.salesManagerFeedback" id="salesManagerFeedback" maxlength="500" class="txtbox width130"></textarea>
						<%-- <html:text property="to.salesManagerFeedback" styleId="salesManagerFeedback"  maxlength="500" styleClass="txtbox width130" /> --%>
						</td>
					
						<td width="6%" class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td width="15%">
							<html:text property="to.salesManagerFeedbackDate" styleId="salesManagerFeedbackDate" styleClass="txtbox width130" maxlength="10" readonly="true" onfocus="validateDate(this,'salesManagerFeedbackDate');" />&nbsp;
							<a href="#" onclick="setDate(this,'salesManagerFeedbackDate');" title="Select Date" id="salesManagerFeedbackDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						
						<td width="17%" class="lable"><bean:message key="label.critical.claim.complaint.csManagerFeedback" /></td>
						<td width="10%"><textarea maxlength="500" name="to.csManagerFeedback" id="csManagerFeedback" class="txtbox width130"></textarea>
						<%-- <html:text property="to.csManagerFeedback" styleId="csManagerFeedback"  maxlength="500" styleClass="txtbox width130" /> --%>
						</td>
					
						<td width="5%" class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td width="15%">
							<html:text property="to.csManagerFeedbackDate" styleId="csManagerFeedbackDate" styleClass="txtbox width130" maxlength="10"  readonly="true" onfocus="validateDate(this,'csManagerFeedbackDate');"/>&nbsp;
							<a href="#" onclick="setDate(this,'csManagerFeedbackDate');" title="Select Date" id="csManagerFeedbackDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
					</tr>
					
					<tr>
					
						<td  class="lable"><bean:message key="label.critical.claim.complaint.agmFeedback" /></td>
						<td ><textarea maxlength="500" name="to.agmFeedback" id="agmFeedback" class="txtbox width130"></textarea>
						<%-- <html:text property="to.agmFeedback" styleId="agmFeedback" styleClass="txtbox width130"  maxlength="500" /> --%>
						</td>
					
						<td  class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.agmFeedbackDate" styleId="agmFeedbackDate" styleClass="txtbox width130" maxlength="10" readonly="true" onfocus="validateDate(this,'agmFeedbackDate');" />&nbsp;
							<a href="#" onclick="setDate(this,'agmFeedbackDate');" title="Select Date" id="agmFeedbackDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						
						<td class="lable"><bean:message key="label.critical.claim.complaint.vpFeedback" /></td>
						<td><textarea maxlength="500" name="to.vpFeedback" id="vpFeedback" class="txtbox width130"></textarea>
						<%-- <html:text property="to.vpFeedback" styleId="vpFeedback" styleClass="txtbox width130"  maxlength="500"/> --%>
						</td>
					
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.vpFeedBackDate" styleId="vpFeedBackDate" styleClass="txtbox width130" maxlength="10"  readonly="true" onfocus="validateDate(this,'vpFeedBackDate');"/>&nbsp;
							<a href="#" onclick="setDate(this,'vpFeedBackDate');" title="Select Date" id="vpFeedBackDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
					</tr>
					
					<tr>
						<td class="lable"><bean:message key="label.critical.claim.complaint.corporate" /></td>
						<td><html:text property="to.corporate" styleId="corporate" styleClass="txtbox width130" maxlength="150" /></td>
						<td class="lable"><bean:message key="label.critical.complaint.date" /></td>
						<td>
							<html:text property="to.corporateDate" styleId="corporateDate" styleClass="txtbox width130" maxlength="10" readonly="true" onfocus="validateDate(this,'corporateDate');" />&nbsp;
							<a href="#" onclick="setDate(this,'corporateDate');" title="Select Date" id="corporateDate" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
				</table>
			</fieldset>
		</div>
		
		<!-- Hidden Fields START -->
		<html:hidden property="to.createdBy" styleId="createdBy"/>
		<html:hidden property="to.updatedBy" styleId="updatedBy" />
		<html:hidden property="to.complaintId" styleId="complaintId" value="${complaintId}"/>
		<html:hidden property="to.serviceRequestComplaintId" styleId="serviceRequestComplaintId1" value = "" />
		<html:hidden property="to.serviceReqClaimId" styleId="serviceReqClaimId" value = "" />
		<html:hidden property="to.complaintNo" styleId="complaintNo" value="${complaintNumber}"/>
		<html:hidden property="to.todaysDate" styleId="todaysDate1" value = ""/>
	</div>
		
	<!-- Button -->
	<div class="button_containerform"><br/>
		<html:button property="Save" styleId="Save" styleClass="btnintform" onclick="save('Save');">
					<bean:message key="button.save" /></html:button>
		<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>
	</div>
	<!-- Button ends -->
</div>
</html:form>
</body>


