<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <head>
        <script type="text/javascript" language="JavaScript" src="js/complaints/complaintsBacklineSummary.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/complaints/serviceRequestFollowup.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/complaints/complaint.js"></script>
 </head>
        <body>
  <div class="formTable">
  <html:form action="/serviceRequestFollowup.do" method="post" styleId="serviceRequestFollowupForm" >
  
  			<html:hidden property="to.complaintId" styleId="complaintId" value= "${complaintId}"/> 
           <table border="0" cellpadding="0" cellspacing="2" width="100%">
             <tr>
              <td width="10%" class="lable"><bean:message key="label.complaint.date"/> <strong>/</strong> <bean:message key="label.followup.complaint.time"/></td>
              <td width="25%"  align = "left"><html:text property="to.followUpDate" styleId="followUpDate" styleClass="txtbox width130"  readonly="true" tabindex="-1" value=""/></td>
              <td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.followup.complaintNo"/></td>
              <td width="14%" align = "left"><html:text property="to.complaintNo" styleId="complaintNo" styleClass="txtbox width130" value="${complaintNumber}" readonly="true" tabindex="-1" /></td>
              <td width="12%" class="lable"><sup class="star">*</sup><bean:message key="label.followup.callfrom"/></td>
              <td width="23%" align = "left"><html:radio property="to.callFrom" value="EMAIL" styleClass="radio" styleId= "mail"/><bean:message key="label.followup.followup.mail"/>
              <html:radio property="to.callFrom" value="PHONE" styleClass="radio" styleId= "phone"/><bean:message key="label.followup.phone"/>
              <!-- <input name="textfield3" type="text" class="txtbox width100" value=""/> --></td>
            </tr>
             <tr>
               <td class="lable"><sup class="star">*</sup><bean:message key="label.followup.caller"/></td>
               <td align = "left"><html:radio property="to.caller" value="CUSTOMER" styleClass="radio" styleId= "customer"/>
                 <bean:message key="label.followup.customer"/>
               	 <html:radio property="to.caller" value="ORIGIN" styleClass="radio" styleId= "origin"/> 
                 <bean:message key="label.followup.destination"/>
                 <html:radio property="to.caller" value="BRANCH" styleClass="radio" styleId= "callerBranch"/>
                 <bean:message key="label.followup.branch"/></td>
               <td class="lable"><sup class="star">*</sup><bean:message key="label.followup.customer.name"/></td>
               <td align = "left"><html:text property="to.customerName" styleId="customerName" styleClass="txtbox width130" maxlength="30" value=""/></td>
               <td class="lable"><bean:message key="label.followup.email"/></td>
               <td align = "left"><html:text property="to.email" styleId="email" styleClass="txtbox width130"  maxlength="30" value="" onchange="validateEmailId(this);"/></td>
             </tr>
            <tr>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.followup.followup"/></td>
              <td align = "left">
              <textarea name="to.followupNote" id="followupNote" class="txtbox width130" style="width: 251px; height: 76px; resize:none" maxlength="500"  ></textarea>
              <%-- <html:textarea property="to.followupNote" styleId="followupNote" styleClass="txtbox width130" value="" style="width: 251px; height: 76px; resize:none"/> --%>
              </td>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.followup.status"/></td>
              <td align = "left"><html:text property="to.status" styleId="status" styleClass="txtbox width130" value="FOLLOWUP" readonly="true" tabindex="-1" /></td>
              <td class="lable"><bean:message key="label.critical.complaint.consgNo"/> </td>
              <td align = "left"><a href="#" onclick="populateConsignmentDetails('${consignmentNumber}','${bookingNoType}');" style="color:#1c449c;"><c:out value="${consignmentNumber}"/></a> </td>
            </tr>
         </table>
         <div class="columnuni">
		<div class="columnleftcaller">
			<fieldset>
				<legend>&nbsp;<bean:message key="label.followup.request.transfer.to" />&nbsp;</legend>
				<table border="0" cellpadding="2" cellspacing="5" width="100%">
				<tr>
	           		<td width="15%" class="lable"><bean:message key="label.transshipmentRegion"/>:</td>
	           		<td width="19%" align = "left">
	   					<html:select property="to.regionId" styleId="regionId" styleClass="selectBox width130" onchange="getAllCities();">
	 						<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
	  					</html:select>
	          		</td>
	          		<td class="lable"><bean:message key="label.servicedStation"/>:</td>
	              	<td align = "left">
	              		<html:select property="to.cityId" styleClass="selectBox width130"  styleId="cityId" onchange="getAllOffices();">
	              			<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
	           			</html:select>
					</td>
	              	<td class="lable"><bean:message key="label.followup.branch"/>:</td>
	              	<td align = "left">
						<html:select property="to.officeId" styleClass="selectBox width130" styleId="officeId" onchange="getAllEmployeeByOfficeAndRole();">
	           				<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
	           			</html:select>
					</td>
					<td class="lable"><bean:message key="label.complaints.service.employeeName"/></td>
		              <td align = "left">
						<html:select property="to.employeeId" styleClass="selectBox width130" styleId="employeeId" onblur="">
		           				<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		           		</html:select>
					  </td>
          		</tr>
          		<!-- <tr>
          	          
					  <td width="15%" class="lable"></td>
		           	  <td width="19%" align = "left"></td>
		          	  <td class="lable"></td>
		              <td align = "left"></td>
          		 </tr> -->
					
				</table>
			</fieldset>
		</div>
		</div>
         
            <!-- Button -->
            <div class="button_containerform">   
            <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick = "saveOrUpdateFollowup();"><bean:message key="button.label.save"/></html:button>   
			<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>       
            </div><!-- Button ends --> 
       </html:form>
  </div>
  </body>
