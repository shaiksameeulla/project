<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 <head>
        <script type="text/javascript" charset="utf-8" src="js/complaints/solveComplaint.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/complaints/complaint.js"></script>
 </head>
<body>
 <div class="formTable">
 <html:form  action = "/solveComplaint.do" method="post" styleId="solveComplaintForm"> 
         <table border="0" cellpadding="0" cellspacing="2" width="100%">
             <tr>
              <td width="21%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.solveComplaint.dateTime"/></td>
              <td width="19%" align = "left">
              <html:text property="to.updateDateStr" styleClass="txtbox width130" styleId="updateDateStr" readonly="true" tabindex="-1" />
              </td>
              <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.solveComplaint.consignmentNote"/></td>
              <td width="16%" align = "left">
              <html:text property="to.bookingNo" styleClass="txtbox width130" styleId="bookingNo"  maxlength="15" readonly="true" tabindex="-1" />
              </td>
              <td width="12%" class="lable"><sup class="star">*</sup><bean:message key="label.followup.complaintNo"/></td>
              <td width="17%" align = "left"><html:text property="to.complaintNo" styleId="complaintNo" styleClass="txtbox width130" value="${complaintNumber}" readonly="true" tabindex="-1" /></td>
            </tr>
            <tr>
              <td class="lable">Delivery Date & Time :</td>
              <td align = "left"> <html:text property="to.consgDeliveryDate" styleId="consgDeliveryDate" styleClass="txtbox width130"  maxlength="30" value="" readonly="true"/> </td>
              <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.solveComplaint.transferToIcc"/></td>
              <td align = "left">
              		<html:select  property="to.serviceRequestTranferTO.serviceRequestTransfertoId" styleId="serviceRequestTranferTO" styleClass="selectBox width130">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
              </td>
              <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.solveComplaint.actualReason"/></td>
              <td align = "left">
              <textarea name="to.remark" id="solveComplntremark" style="width: 251px; height: 76px; resize:none" rows="3" cols="30" maxlength="500" ></textarea>
              <%-- <html:textarea property="to.remark" styleId="solveComplntremark" cols="30" rows="3" value="" style="width: 251px; height: 76px; resize:none"/> --%>
              </td>
              
              </tr>
            <tr>
              <td class="lable"><bean:message key="label.complaints.emailToCaller"/></td>
              <td align = "left"> <input type="checkbox" name="to.isEmailSend" id="isEmailSend" class="checkbox" value="N" onclick="setEmailId(this);" />
                    <html:text property="to.callerEmail" styleId="callerEmail" styleClass="txtbox width170"  maxlength="30" value="" onchange="validateEmailId(this);"/>
              </td>
               <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.solveComplaint.status"/></td>
              <td align = "left">
              		<html:select  property="to.serviceRequestStatusTO.serviceRequestStatusId" styleId="serviceRequestStatusTO" styleClass="selectBox width130">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
              </td>
              <td class="lable">&nbsp;</td>
              <td> </td>
              </tr>
         </table>   
            <!-- Button -->
            <div class="button_containerform">    
            <html:button property="Save" styleClass="btnintform" styleId="Save" onclick="saveServiceRequestDetails();"><bean:message key="button.save"/></html:button>            
            <html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>       
            </div><!-- Button ends --> 
  		</div>
  		 <!-- hidden fields start-->
  		 <html:hidden property="to.serviceRequestId" styleId="serviceRequestId"/>
  		 
  		 
  		 <!-- hidden fields end -->
  		</html:form>
</body>