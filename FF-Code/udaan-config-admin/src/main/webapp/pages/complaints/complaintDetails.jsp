<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- <script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/complaints/serviceRequestForService.js"></script>
<script type="text/javascript" charset="utf-8" src="js/complaints/serviceRequestCommon.js"></script> -->

</head>
<body>
<!--wraper-->
<div id="wraper">
	<html:form styleId="serviceRequestForServiceForm" action="/serviceRequestForService.do" method="post">
	<div class="clear"></div>
	<!-- main content -->
	<div id="maincontent">
		<div class="mainbody">
			<%-- <div class="formbox">
          		<h1><bean:message key="label.complaints.service.header"/></h1>
          		<div class="mandatoryMsgf">&nbsp;</div>
        	</div> --%>
        	<br />
           	<table border="0" cellpadding="0" cellspacing="2" width="100%">
            	<tr>
              		<td width="10%" class="lable">Service Type:</td>
              		<td width="17%" align="left">
						<html:select styleId="serviceType_cd" property="to.serviceType" styleClass="selectBox width130">
							<html:option value=""><bean:message key="label.common.select" /></html:option>
						</html:select>
      				</td>
              		<td width="17%" class="lable"><bean:message key="label.complaints.service.number"/></td>
             		<td align="left"><html:text property="to.bookingNo" styleClass="txtbox width130" styleId="number_cd" readonly="true" tabindex="-1" maxlength="12" /></td>
            	</tr>
                <tr>
                	<td width="12%" class="lable">Reference Number:</td>
                	<td align="left"><html:text property="to.serviceRequestNo" styleClass="txtbox width130" styleId="serviceRequestNo_cd" readonly="true" tabindex="-1"/></td>
                	<td width="13%" class="lable">Linked with:<html:checkbox  property="to.isLinkedWith" value="Y" styleId="isLinkedWith_cd" tabindex="-1"/></td>
               		<td align="left"><html:text property="to.linkedServiceReqNo" styleClass="txtbox width130" styleId="linkedServiceReqNo_cd" readonly="true" maxlength="15" tabindex="-1" /></td>
              	</tr>
			</table>

     		<fieldset>
          		<legend ><strong><bean:message key="label.complaints.service.callersDetails" /></strong></legend>
      			<table border="0" cellpadding="0" cellspacing="2" width="100%">
          			<tr>
                		<td width="12%" class="lable"><bean:message key="label.complaints.service.name"/></td>
                    	<td align="left"><html:text property="to.callerName" styleClass="txtbox width130" styleId="callerName_cd" maxlength="30" readonly="true" tabindex="-1" /></td> 
                    	<td width="16%" class="lable"><bean:message key="label.complaints.service.phone"/></td>
                     	<td align="left"><html:text property="to.callerPhone" styleClass="txtbox width130" size="13" styleId="callerPhone_cd" maxlength="11" readonly="true" tabindex="-1" /></td> 
                    	<td class="lable"><bean:message key="label.complaints.service.email"/></td>
                     	<td align="left"><html:text property="to.callerEmail" styleClass="txtbox width130" styleId="callerEmail_cd" maxlength="50" readonly="true" tabindex="-1" /></td> 
                  	</tr>
                  </table>
     		</fieldset>
     		
     		<fieldset>
          		<legend><strong>CREATE SERVICE REQUEST</strong></legend>
          			<table border="0" cellpadding="0" cellspacing="2" width="100%">
          				<tr>
              				<td class="lable" width="18%">&nbsp;<bean:message key="label.complaints.service.serviceRelated"/></td>
               				<td width="18%" align="left">
								<html:select styleId="serviceRelated_cd" property="to.serviceRelated" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
							<td width="18%" class="lable">Complaint Category:</td>
							<td width="20%" align="left">
								<html:select styleId="complaintCategory_cd" property="to.complaintCategory" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
							<td width="18%" class="lable"><bean:message key="label.complaints.service.customerType"/></td>
							<td width="14%" align="left">
								<html:select styleId="custType_cd" property="to.customerType" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="lable"><label id="originName"><bean:message key="label.complaints.service.origin"/></label></td>
							<td align="left">
								<html:select styleId="originCity_cd" property="to.originCityId" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td> 
							<td class="lable"><bean:message key="label.complaints.service.product"/></td>
							<td align="left">
								<html:select styleId="product_cd" property="to.productId" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
							<td class="lable"><bean:message key="label.complaints.service.pincode"/> </td>
							<td align="left"> 
								<html:select styleId="pincode_cd" property="to.pincodeId" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td> 
						</tr>
						<tr>
							<td class="lable"><bean:message key="label.complaints.service.weight"/> </td>
							<td align="left">&nbsp;
								<html:text property="to.weightKgs" styleClass="txtbox width70" styleId="weightKgs_cd" maxlength="7" readonly="true" tabindex="-1" />.
								<html:text property="to.weightGrm" styleClass="txtbox width30" styleId="weightGrm_cd" maxlength="7" readonly="true" tabindex="-1" />
							</td>
							<td class="lable"><bean:message key="label.complaints.service.type"/></td>
							<td align="left">
								<html:select styleId="consgTypes_cd" property="to.consignmentType" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
							<td class="lable"><bean:message key="label.complaints.industrytype"/></td>
							<td align="left">
								<html:select styleId="industryType_cd" property="to.industryType" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
						</tr>
          				<tr>
          					<td class="lable"><bean:message key="label.complaints.service.employeeName"/></td>
          					<td align="left">
	 							<html:select styleId="employeeId_cd" property="to.employeeId" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
	 							</html:select>
            				<td class="lable">Employee <bean:message key="label.complaints.service.email"/></td>
            				<td align="left"><html:text property="to.empEmailId" styleClass="txtbox width130" styleId="empEmailId_cd" readonly="true" tabindex="-1" /></td> 
            				<td class="lable">Employee <bean:message key="label.complaints.service.mobileNo"/></td>
           					<td align="left"><html:text property="to.empPhone" styleClass="txtbox width130" styleId="empPhone_cd" readonly="true" tabindex="-1" /></td> 
          				</tr>
          				<tr>
                    		<td class="lable">&nbsp;</td>
                    		<td align="left">&nbsp;</td>
                    		<td class="lable"><bean:message key="label.complaints.service.result"/>:</td>
                    		<td align="left"><html:textarea  property="to.serviceResult" cols="20" rows="3" styleId="result_cd" readonly="true" tabindex="-1" style="width: 151px; height: 76px; resize:none">Text here</html:textarea></td>
                    		<td class="lable">Source Of Query :</td>
                    		<td align="left">
                    			<html:select styleId="sourceOfQuery_cd" property="to.sourceOfQuery" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
							</td>
                  		</tr>
          				<tr>
                    		<td class="lable"><bean:message key="label.complaints.service.status"/></td>
                    		<td align="left">
                				<html:select styleId="status_cd" property="to.status" styleClass="selectBox width130">
									<html:option value=""><bean:message key="label.common.select" /></html:option>
								</html:select>
                  			</td>
                    		<td class="lable"><bean:message key="label.complaints.service.remark"/></td>
                    		<td align="left">
                    			<html:textarea property="to.remark" cols="20" rows="3" styleId="remark_cd" style="width: 151px; height: 76px; resize:none" readonly="true" tabindex="-1" >&nbsp;</html:textarea>
                    		</td>
                     		<td class="lable" valign="top"><bean:message key="label.complaints.consigcomm"/></td>
							<td class="lable1" valign="top">
            					<html:checkbox property="to.smsToConsignor" value="Y" styleId="smsToConsignor_cd" />&nbsp;<bean:message key="label.complaints.consignorSMS"/><br/>
            					<html:checkbox property="to.smsToConsignee" value="Y" styleId="smsToConsignee_cd" />&nbsp;<bean:message key="label.complaints.consigneeSMS"/><br/>
           						<html:checkbox property="to.emailToCaller" value="Y" styleId="emailToCaller_cd" />&nbsp;<bean:message key="label.complaints.emailToCaller"/><br/>
           					</td>
           				</tr>
        			</table>
              	</fieldset>
			</div>
		</div>
	</html:form>
</div>
</body>
</html>
