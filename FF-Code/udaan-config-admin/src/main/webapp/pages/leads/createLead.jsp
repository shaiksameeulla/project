<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
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
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" charset="utf-8" src="js/leads/createLead.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsCommon.js"></script>
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8">
		var userRole = '${userRole}';
		var salesExecutiveRole = '${createLeadForm.to.salesExecutiveRole}';
		var controlTeamRole = '${createLeadForm.to.controlTeamRole}';
		</script>
		</head>
		<body onload="loadDefaultObjects();">
		<!--wrapper-->
<div id="wraper">
<div class="clear"></div>
		
     <html:form  method="post" styleId="createLeadForm"> 
      <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
              <h1><bean:message key="label.leads.header"/></h1>
              <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.leads.FieldsAreMandatory"/></div>
      </div>
      <div class="formTable">
              
                <table border="0" cellpadding="0" cellspacing="2" width="100%">
                  <tr>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.CustomerName"/></td>
                    <td width="16%" ><html:text property="to.customerName" styleId="customerName" styleClass="txtbox width130" maxlength="50" value=""/></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.LeadNo"/></td>
                    <td width="16%"><html:text property="to.leadNumber" styleId="leadNumber" styleClass="txtbox width130" readonly="true" onchange="validateLeadNo();" value=""/></td>
                    <td width="24%" class="lable">&nbsp;</td>
                    <td width="19%">&nbsp;</td>
                  </tr>                            
                  <tr>
                  <td align="left" valign="top" colspan="6">                  	
                      <table class="myTable1" cellpadding="0" cellspacing="0" border="0" width="100%" id="leadCheck">
                         <tr>
                          <th width="24%" align="center"><bean:message key="label.leads.product"/></th>
                          <th width="24%" align="center"><bean:message key="label.leads.competitor"/></th>
                          <th width="24%" align="center"><bean:message key="label.leads.potential"/></th>
                          <th width="24%" align="center"><bean:message key="label.leads.expectedVolumeOfBusiness"/></th>
                         </tr>
                  
                         <tr>
                          <td class="row1" align="left"><input type="checkbox" id="check1" name="to.productCode" value="Courier" onclick="checkSelected(); checkCourier();"  /><bean:message key="label.leads.courier"/></td>
                          <td class="row1" align="center">
                          <html:select property="to.competitorIds" styleId="competitorId1" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(1);" >
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential1" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,1,'P');" value="" readonly="true" onblur="formatDecValue(1);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume1" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,1,'E'); " value="" readonly="true" onblur="checkExpectedVolume(1);"/></td>
                         </tr>
                         <tr>
                          <td class="row1" align="left">&nbsp;&nbsp;&nbsp;<input type="checkbox" name="to.productCode" value="Dox" onclick="checkSelected(); checkCourier();" id="check2"/> 
                          <bean:message key="label.leads.dox"/></td>
                          <td class="row1" align="center">
                          <html:select property="to.competitorIds" styleId="competitorId2" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(2);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential2" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,2,'P'); " value=""  readonly="true" onblur="formatDecValue(2);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume2" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,2,'E'); " value="" readonly="true" onblur="checkExpectedVolume(2);"/></td>
                        </tr>
                        <tr>
                          <td class="row1" align="left">&nbsp;&nbsp;&nbsp;<input type="checkbox" name="to.productCode" value="Non Dox" onclick="checkSelected(); checkCourier();" id="check3"/> 
                          <bean:message key="label.leads.nonDox"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId3" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(3);">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential3" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,3,'P'); " value=""  readonly="true" onblur="formatDecValue(3);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume3" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,3,'E'); " value="" readonly="true" onblur="checkExpectedVolume(3);"/></td>
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode" value="E-Commerce/Letter of Credit" onclick="checkSelected();" id="check4"/> 
                          <bean:message key="label.leads.ecommerceLeterOfCredit"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId4" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(4);">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential4" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,4,'P'); " value="" readonly="true" onblur="formatDecValue(4);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume4" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,4,'E'); " value="" readonly="true" onblur="checkExpectedVolume(4);"/></td>
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode" value="Air Cargo" onclick="checkSelected();" id="check5"/> 
                          <bean:message key="label.leads.airCargo"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId5" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(5);">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential5" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,5,'P');" value="" readonly="true" onblur="formatDecValue(5);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume5" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,5,'E'); " value="" readonly="true" onblur="checkExpectedVolume(5);"/></td>
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode" value="Train Cargo" onclick="checkSelected();" id="check6"/> 
                          <bean:message key="label.leads.trainCargo"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId6" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(6);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential6" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,6,'P'); " value="" readonly="true" onblur="formatDecValue(6);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume6" styleClass="txtbox width130"  maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,6,'E'); " value="" readonly="true" onblur="checkExpectedVolume(6);"/></td>
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode" value="International" onclick="checkSelected();" id="check7"/> 
                          <bean:message key="label.leads.international"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId7" styleClass="selectBox width130" disabled="true" onchange="checkProductChange(7);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential7" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,7,'P'); " value="" readonly="true" onblur="formatDecValue(7);"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume7" styleClass="txtbox width130" maxlength="10" onkeypress=" return allowOnlyNumbersAndDot(event,7,'E');" value="" readonly="true" onblur="checkExpectedVolume(7);"/></td>
                        </tr>
                        </table>
                  </td>
                  </tr>
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.contactPerson"/></td>
                    <td width="16%" ><html:text property="to.contactPerson" styleId="contactPerson" styleClass="txtbox width130" onkeypress="return charactersForSecondaryContactPerson(event);" maxlength="30" value="" onblur=" checkContactPerson(this);"/></td>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.phoneNo"/></td>
                    <td width="16%" ><html:text property="to.phoneNoSTD" styleId="phoneNoSTD" styleClass="txtbox width50" onkeypress="return allowOnlyNumbers(event);" onchange="validateStd(this);" maxlength="4" value=""/>&nbsp;<html:text property="to.phoneNo" styleId="phoneNo" styleClass="txtbox width75" onkeypress="return allowOnlyNumbers(event);" onchange="validatePhone(this);" maxlength="8" value="" /></td>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.mobileNo"/></td>
                    <td width="16%" ><html:text property="to.mobileNo" styleId="mobileNo" styleClass="txtbox width130" onkeypress="return allowOnlyNumbers(event);" onchange="validateMobile(this);" maxlength="10" value=""/></td>
                  </tr>

		  		  <tr>
                    <td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.doorNoBuilding"/></td>
                    <td width="16%" ><html:text property="to.doorNoBuilding" styleId="doorNoBuilding" styleClass="txtbox width130" maxlength="50" value=""/></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.streetRoad"/></td>
                    <td width="16%" ><html:text property="to.street" styleId="street" styleClass="txtbox width130" maxlength="50" value=""/></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.location"/></td>
                    <td width="16%" ><html:text property="to.location" styleId="location" styleClass="txtbox width130" maxlength="50" value=""/></td>
                  </tr>
                  
                  <tr>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.city"/></td>
                    <td width="16%" ><html:text property="to.city" styleId="city" styleClass="txtbox width130" onkeypress="return charactersForCity(event);" maxlength="25" value="" onblur="checkCity(this);"/></td>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.pincode"/></td>
                    <td width="16%" ><html:text property="to.pincode" styleId="pincode" styleClass="txtbox width130" onkeypress="return allowOnlyNumbers(event);" onchange="validatePincode(this);" maxlength="6" value=""/></td>
                    <td width="10%" class="lable"><bean:message key="label.leads.designation"/></td>
                    <td width="16%"><html:text property="to.designation" styleId="designation" styleClass="txtbox width130" onkeypress="return characterForDesignation(event);" maxlength="30" value="" onblur="checkDesignation(this)"/></td>
                  </tr>

                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.emailId"/></td>
                    <td width="16%"><html:text property="to.emailAddress" styleId="emailAddress" styleClass="txtbox width130" maxlength="50" onblur="validateEmailAddress();" value=""/></td>
                    <td width="24%" class="lable"><bean:message key="label.leads.leadSource"/></td>
                    <td width="19%">
                      <html:select property="to.leadSource.sourceId" styleId="leadSource" styleClass="selectBox width130">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>
                         <c:forEach var="leadSourceList" items="${leadSourceList}" varStatus="loop">
		              		<option value="${leadSourceList.stdTypeCode}" ><c:out value="${leadSourceList.description}"/></option>
		            		</c:forEach> 
                      </html:select>
		    		</td>
                    <td width="24%" class="lable"><bean:message key="label.leads.industryCategory"/></td>
                    <td width="19%"><html:select property="to.industryCategory.categoryId" styleId="industryCategory" styleClass="selectBox width130">
                      <html:option value=""><bean:message key="label.common.select"/></html:option>
                      <c:forEach var="industryCategoryList" items="${industryCategoryList}" varStatus="loop">
		              		<option value="${industryCategoryList.stdTypeCode}" ><c:out value="${industryCategoryList.description}"/></option>
		            		</c:forEach>
                      </html:select>
		    		</td>
                  </tr>
                  
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.businessType"/></td>
                    <td width="10%">
                     <html:select property="to.businessType" styleId="businessType" styleClass="selectBox width130">
                          <html:option value=""><bean:message key="label.common.select"/></html:option>  
                         <html:option  value="N">NORMAL </html:option>  
                         <html:option  value="E">E-Commerce</html:option>     
                      </html:select>
                    </td>
                    <td width="24%" class="lable"><bean:message key="label.leads.secondaryContact"/></td>
                    <td width="19%"><html:text property="to.secondaryContact" styleId="secondaryContact" styleClass="txtbox width130" onkeypress="return charactersForSecondaryContactPerson(event);" value="" onblur="checkSecondaryContact(this)" maxlength="30" >
                    </html:text></td>
                    <td width="24%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.branch"/></td>
                    <td width="19%">
                      <html:select property="to.branch.branchId" styleId="branch" styleClass="selectBox width130" onchange="getSalesDesignationList();">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>
                        <c:forEach var="officeTOList" items="${officeTOList}" varStatus="loop">
		              		<option value="${officeTOList.officeId}" ><c:out value="${officeTOList.officeName}"/></option>
		            		</c:forEach> 
                      </html:select>
                    </td>
                  </tr>
               
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.salesPersonTitle"/></td>
                    <td width="10%">
                     <html:select property="to.designation" styleId="salesDesignation" styleClass="selectBox width130" onchange="getSalesExecutiveList();">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>        
                      </html:select>
                    </td>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.assignedTo"/></td>
                    <td width="10%">
                     <html:select property="to.assignedTo.empUserId" styleId="assignedTo" styleClass="selectBox width130">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>        
                      </html:select>
                    </td>
                  </tr>

                 
                 <%--  <tr>
                  	<!-- <td colspan="4">&nbsp;</td> -->
                  	<td width="19%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.designation"/></td>
                  	<td width="19%">
                     <html:select property="to.designation" styleId="salesDesignation" styleClass="selectBox width130" onchange="getSalesExecutiveList();">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>        
                      </html:select>
                    </td>
                     <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.emailId"/></td>
                    <td width="16%"><html:text property="to.emailAddress" styleId="emailAddress" styleClass="txtbox width130" maxlength="50" onchange="validateEmail();" value=""/></td>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.pincode"/></td>
                    <td width="16%" ><html:text property="to.pincode" styleId="pincode" styleClass="txtbox width130" onkeypress="return onlyNumeric(event);" onchange="validatePincode(this);" maxlength="6" value=""/></td>
                  </tr>
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.emailId"/></td>
                    <td width="16%"><html:text property="to.emailAddress" styleId="emailAddress" styleClass="txtbox width130" maxlength="50" onchange="validateEmail();" value=""/></td>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.pincode"/></td>
                    <td width="16%" ><html:text property="to.pincode" styleId="pincode" styleClass="txtbox width130" onkeypress="return onlyNumeric(event);" onchange="validatePincode(this);" maxlength="6" value=""/></td>
                    <td width="24%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.assignedTo"/></td>
                    <td width="19%">
                     <html:select property="to.assignedTo.empUserId" styleId="assignedTo" styleClass="selectBox width130">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>        
                      </html:select>
                    </td>
                    <td width="24%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.businessType"/></td>
                    <td width="19%">
                     <html:select property="to.businessType" styleId="businessType" styleClass="selectBox width130">
                         <option value="N">NORMAL </option>
                            <option value="E">E-Commerce</option>    
                      </html:select>
                    </td>
                    
                   <!--  <td class="row1" align="center"><select name="select" class="selectBox width130">
                            <option value="N">NORMAL </option>
                            <option value="E">E-Commerce</option>
                     </select></td> -->
                          
                  </tr> --%>
                 
                 
                </table>
                <html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode" value="${loginOfficeCode}"/>
				<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" value="${loginOfficeId}"/>
				<html:hidden property="to.date" styleId="date" value="${date}"/>
				<html:hidden property="to.status.statusDescription" styleId="status" value="${leadStatusTO.statusDescription}"/>
				<html:hidden property="to.leadId" styleId="leadId"/>
				<html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId"/> 
				
                
</div>

              
<!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
         <div class="button_containerform">
    <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveLead();">
    <bean:message key="button.label.save"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="cancelDetails();">
    <bean:message key="button.label.Cancel"/></html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <!-- <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div> -->
          <!-- footer ends --> 
</html:form> 
</div>
<!--wrapper ends-->
		</body>
		</html>