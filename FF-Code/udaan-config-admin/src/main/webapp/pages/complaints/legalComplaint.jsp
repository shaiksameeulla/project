<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="formTable">
 <html:form action="/legalComplaint.do?submitName=prepareLegalComplaintAction" styleId="legalComplaintForm" method="post" enctype="multipart/form-data">
	<table border="0" cellpadding="0" cellspacing="2" width="100%">
		<tr>
			<td width="20%" class="lable"><sup class="mandatoryf">*</sup>Advocate Notice From Client:</td>
			<td width="40%" align="left">
				<html:file property="to.advocateNoticFromClient" styleId="advocateNoticFromClient" styleClass="txtbox width150"/>
				<!-- <input name="Upload5" type="button" value="Upload" class="button" title="Upload" /> -->
			Date:<input name="to.advocateNoticeFileDate" type="text" class="txtbox width100" id="date1"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date1', this.value)" id="calendar1" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg1"  /></a>
			</td>
			<td width="10%" class="lable">Hearing 1:</td>
			<td width="25%" align="left"><textarea name="to.hearing1" maxlength="500" type="area" class="txtbox width130" id="hearing1"> </textarea>
		Date:<input name="to.hearing1_date" type="text" class="txtbox width100" id="date2"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date2', this.value)" id="calendar2" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg2"  /></a></td>
		
		</tr>
		<tr>
			<td class="lable"><sup class="mandatoryf">*</sup>Investigation Feedback:</td>
			<td align="left"><textarea name="to.investigFeedback" type="text" class="txtbox width130" maxlength="500" id="investigFeedback"></textarea>
			<td class="lable">Hearing 2:</td>
			<td align="left"><textarea name="to.hearing2" maxlength="500" type="text" class="txtbox width130" id="hearing2"></textarea>
		Date:<input name="to.hearing2_date" type="text" class="txtbox width100" id="date3"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date3', this.value)" id="calendar3" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg3"  /></a></td>
		</tr>
		<tr>
			<td class="lable"><sup class="mandatoryf">*</sup>Forwarded to FFCL lawyer:</td>
			<td align="left">
				<input type="radio" class="radio" name="frwd1" id="forwardedToFFclLawyerYes" onclick="enableDisableDate('Y');" /> Yes&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="radio" class="radio" name="frwd1" id="forwardedToFFclLawyerNo" onclick="enableDisableDate('N');" />No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date:&nbsp;&nbsp;
				<input name="to.date" type="text" class="txtbox width100" id="date"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date', this.value)" id="calendar" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg" hidden="true" /></a></td>
			<td class="lable">Hearing 3:</td>
			<td align="left"><textarea name="to.hearing3" type="text" maxlength="500" class="txtbox width130" id="hearing3"></textarea>
		Date:<input name="to.hearing3_date" type="text" class="txtbox width100" id="date4"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date4', this.value)" id="calendar4" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg4"  /></a></td>
		</tr>
		<tr>
			<td class="lable"><sup class="mandatoryf">*</sup>Remarks:</td>
			<td align="left"><textarea name="to.remarks" type="text"  maxlength="500" class="txtbox width130" id="remarks"></textarea>
			<td class="lable">Hearing 4:</td>
			<td align="left"><textarea name="to.hearing4" maxlength="500" type="text" class="txtbox width130" id="hearing4"></textarea>
			Date:<input name="to.hearing4_date" type="text" class="txtbox width100" id="date5"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date5', this.value)" id="calendar5" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg5"  /></a></td>
		</tr>
		<tr>
			<td class="lable"><sup class="mandatoryf">*</sup>Lawyers Fees:</td>
			<td align="left"><input name="to.lawyerFees" type="text" class="txtbox width130" id="lawyerFees" onchange="validateLawyerfees(this);" /></td>
			<td class="lable">Hearing 5:</td>
			<td align="left"><textarea name="to.hearing5" maxlength="500" type="text" class="txtbox width130" id="hearing5" ></textarea>
			Date:<input name="to.hearing5_date" type="text" class="txtbox width100" id="date6"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date6', this.value)" id="calendar6" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg6"  /></a></td>
			
		</tr>
		
		<tr>
			<td class="lable"><sup class="mandatoryf"><bean:message key="symbol.common.star" /></sup><bean:message key="label.followup.status"/></td>
			<td align="left">
				<html:select property="to.legalComplaintStatus" styleId="legalComplaintStatus" styleClass="selectBox width150">
					<logic:present scope="request" name="legalComplaintStatusList">
						<c:forEach var="status" items="${legalComplaintStatusList}" varStatus="loop">
							<html:option value="${status.stdTypeCode}">${status.description}</html:option>
						</c:forEach>
					</logic:present>
				</html:select>
				
				<td class="lable">Hearing 6:</td>
			<td align="left"><textarea name="to.hearing6" maxlength="500" type="text" class="txtbox width130" id="hearing6" ></textarea>
			Date:<input name="to.hearing6_date" type="text" class="txtbox width100" id="date7"  readonly="readonly" onblur="compareDatefromSysDate();"/> &nbsp;<a href="#" title="Select Date"  onclick="javascript:show_calendar('date7', this.value)" id="calendar7" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" id="calendarImg7"  /></a></td>
			</td>
			<td class="lable"></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<!-- Hidden Parameters -->
	<input type="hidden" name="to.forwardedToFFclLawyer"  class="txtbox width130" id="hiddenForwardedFfclLayer" />
	<input type="hidden" name="to.serviceRequestComplaintId"  class="txtbox width130" id="serviceRequestComplaintId" />
	<input type="hidden" name="to.systemDateAndTime"  class="txtbox width130" id="systemDateAndTime" />
	<input type="hidden" name="to.complaintNo"  class="txtbox width130" id="complaintNo" />
	<input type="hidden" name="to.consignmentNo"  class="txtbox width130" id="consignmentNo" />
	<input type="hidden" name="to.complaintStatus"  class="txtbox width130" id="complaintStatus" />
	
	<!-- Button -->
	<div class="button_containerform">
		<input name="Save" type="button" value="Save" id="save" class="btnintform" title="Save" onclick="saveOrUpdateLegalComplaint();"/> 
		<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>
		
	</div>
	<!-- Button ends -->
	</html:form>
</div>


