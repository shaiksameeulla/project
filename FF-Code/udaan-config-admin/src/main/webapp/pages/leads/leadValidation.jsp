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
		<script type="text/javascript" charset="utf-8" src="js/leads/leadValidate.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsView.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsCommon.js"></script>
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8">
		var SALES_EXECUTIVE = '${SALES_EXECUTIVE_ROLE}';
		/* var CONTROL_TEAM_MEMBER = '${CONTROL_TEAM_MEMBER_ROLE}'; */
		var PRODUCT_ROW_LIST = '${PRODUCT_ROW_LIST}';
		var productArray = new Array();
		var product;
		
		var userRole = '${userRole}';
		var salesExecutiveRole = '${createLeadForm.to.salesExecutiveRole}';
		var controlTeamRole = '${createLeadForm.to.controlTeamRole}';
		
		 function iterateProductList(){			
			<c:forEach var="productList" items="${PRODUCT_ROW_LIST}" varStatus="loop">
   			product='${productList.product.stdTypeCode}';
   			if(product=="Courier"){
   			document.getElementById("competitorId1").value='${productList.competitor.competitorId}';
   			document.getElementById("potential1").value='${productList.potential}';
   			document.getElementById("expectedVolume1").value='${productList.expectedVolume}';
   			document.getElementById("leadCompetitorId1").value='${productList.leadCompetitorId}';
   			}else if(product=="Dox"){
   				document.getElementById("competitorId2").value='${productList.competitor.competitorId}';
   				document.getElementById("potential2").value='${productList.potential}';
   	   			document.getElementById("expectedVolume2").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId2").value='${productList.leadCompetitorId}';
   			}else if(product=="Non Dox"){
   				document.getElementById("competitorId3").value='${productList.competitor.competitorId}';
   				document.getElementById("potential3").value='${productList.potential}';
   	   			document.getElementById("expectedVolume3").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId3").value='${productList.leadCompetitorId}';
   			}else if(product=="E-Commerce/Letter of Credit"){
   				document.getElementById("competitorId4").value='${productList.competitor.competitorId}';
   				document.getElementById("potential4").value='${productList.potential}';
   	   			document.getElementById("expectedVolume4").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId4").value='${productList.leadCompetitorId}';
   			}else if(product=="Air Cargo"){
   				document.getElementById("competitorId5").value='${productList.competitor.competitorId}';
   				document.getElementById("potential5").value='${productList.potential}';
   	   			document.getElementById("expectedVolume5").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId5").value='${productList.leadCompetitorId}';
   			}else if(product=="Train Cargo"){
   				document.getElementById("competitorId6").value='${productList.competitor.competitorId}';
   				document.getElementById("potential6").value='${productList.potential}';
   	   			document.getElementById("expectedVolume6").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId6").value='${productList.leadCompetitorId}';
   			}else if(product=="International"){
   				document.getElementById("competitorId7").value='${productList.competitor.competitorId}';
   				document.getElementById("potential7").value='${productList.potential}';
   	   			document.getElementById("expectedVolume7").value='${productList.expectedVolume}';
   	   			document.getElementById("leadCompetitorId7").value='${productList.leadCompetitorId}';
   			}
   		</c:forEach> 
   		
 		leadValidationStartUp();
 		loadDefaultObjects();
	
		}
		</script>
		</head>
<body onload="iterateProductList()">
		<!--wrapper-->
<div id="wraper">
<div class="clear"></div>
		
    	
     <html:form  method="post" styleId="createLeadForm"> 
      <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
              <h1><bean:message key="label.leads.leadValidation"/></h1>
              <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.leads.FieldsAreMandatory"/></div>
      </div>
      <div class="formTable">
              
                <table border="0" cellpadding="0" cellspacing="2" width="100%">
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.CustomerName"/></td>
                    <td width="16%" ><html:text property="to.customerName" styleId="customerName" styleClass="txtbox width130" maxlength="50" /></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.LeadNo"/></td>
                    <td width="16%"><html:text property="to.leadNumber" styleId="leadNumber" styleClass="txtbox width130" readonly="true" onblur="validateLeadNo();"  /></td>
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
                          <td class="row1" align="left"><input type="checkbox" id="check1" name="to.productCode"  value="Courier" onclick="checkSelected(); checkCourier();" /><bean:message key="label.leads.courier"/></td>
                          <td class="row1" align="center">
                          <html:select property="to.competitorIds" styleId="competitorId1" styleClass="selectBox width130" onchange="checkProductChange(1);">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential1" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" /></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume1" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(1);"/></td>
                          <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId1"/> 
                         </tr>
                         
                         <tr>
                          <td class="row1" align="left">&nbsp;&nbsp;&nbsp;<input type="checkbox" name="to.productCode" value="Dox" onclick="checkSelected(); checkCourier();" id="check2"/> 
                          <bean:message key="label.leads.dox"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId2" styleClass="selectBox width130" onchange="checkProductChange(2);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td> 
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential2" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume2" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(2);"/></td>
                       <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId2"/> 
                        </tr>
                        <tr>
                          <td class="row1" align="left">&nbsp;&nbsp;&nbsp;<input type="checkbox" name="to.productCode"  value="Non Dox" onclick="checkSelected(); checkCourier();" id="check3"/> 
                          <bean:message key="label.leads.nonDox"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId3" styleClass="selectBox width130" onchange="checkProductChange(3);">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential3" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume3" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(3);"/></td>
                        <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId3"/> 
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode"  value="E-Commerce/Letter of Credit" onclick="checkSelected();" id="check4"/> 
                          <bean:message key="label.leads.ecommerceLeterOfCredit"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId4" styleClass="selectBox width130" onchange="checkProductChange(4);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential4" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" maxlength="10" value="" /></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume4" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(4);"/></td>
                        <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId4"/> 
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode"  value="Air Cargo" onclick="checkSelected();" id="check5"/> 
                          <bean:message key="label.leads.airCargo"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId5" styleClass="selectBox width130" onchange="checkProductChange(5);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                           <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential5" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume5" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(5);"/></td>
                        <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId5"/> 
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode"  value="Train Cargo" onclick="checkSelected();" id="check6"/> 
                          <bean:message key="label.leads.trainCargo"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId6" styleClass="selectBox width130" onchange="checkProductChange(6);">
                             <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}"><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential6" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume6" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(6);"/></td>
                        <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId6"/> 
                        </tr>
                        <tr>
                          <td class="row1" align="left"><input type="checkbox" name="to.productCode" value="International" onclick="checkSelected();" id="check7"/> 
                          <bean:message key="label.leads.international"/></td>
                          <td class="row1" align="center"><html:select property="to.competitorIds" styleId="competitorId7" styleClass="selectBox width130" onchange="checkProductChange(7);">
                           <html:option value=""><bean:message key="label.common.select"/></html:option>
                            <c:forEach var="competitorTO" items="${competitorTOs}" varStatus="loop">
		              		<html:option value="${competitorTO.competitorId}" ><c:out value="${competitorTO.competitorName}"/></html:option>
		            		</c:forEach>
                          </html:select></td>
                          <td class="row1" align="center"><html:text property="to.potential" styleId="potential7" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10"/></td>
                          <td class="row1" align="center"><html:text property="to.expectedVolume" styleId="expectedVolume7" styleClass="txtbox width130" onkeypress=" return allowOnlyNumbersAndDot(event);" value="" maxlength="10" onblur="checkExpectedVolume(7);"/></td>
                        <html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId7"/> 
                        </tr> 
                        </table>
                  </td>
                  </tr>
                  <tr>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.contactPerson"/></td>
                    <td width="16%" ><html:text property="to.contactPerson" styleId="contactPerson" styleClass="txtbox width130" onkeypress="return charactersForSecondaryContactPerson(event);" maxlength="30" /></td>
                     <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.phoneNo"/></td>
                    <td width="16%" ><html:text property="to.phoneNoSTD" styleId="phoneNoSTD" styleClass="txtbox width50" onkeypress="return allowOnlyNumbers(event);" maxlength="4" onblur="validateStd(this);"/>&nbsp;<html:text property="to.phoneNo" styleId="phoneNo" styleClass="txtbox width75" onkeypress="return allowOnlyNumbers(event);" maxlength="8" onblur="validatePhone(this);" /></td>
                    <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.mobileNo"/></td>
                    <td width="16%" ><html:text property="to.mobileNo" styleId="mobileNo" styleClass="txtbox width130" onkeypress="return allowOnlyNumbers(event);" maxlength="10" onblur="validateMobile(this);" /></td>
                  </tr>
                  <tr>
                   <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.doorNoBuilding"/></td>
                    <td width="16%" ><html:text property="to.doorNoBuilding" styleId="doorNoBuilding" styleClass="txtbox width130" maxlength="50" /></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.streetRoad"/></td>
                    <td width="16%" ><html:text property="to.street" styleId="street" styleClass="txtbox width130" maxlength="50" /></td>
                    <td width="15%" class="lable"><bean:message key="label.leads.location"/></td>
                    <td width="16%" ><html:text property="to.location" styleId="location" styleClass="txtbox width130" maxlength="50" /></td>
                  </tr>
                  <tr>
                   
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.city"/></td>
                    <td width="16%" ><html:text property="to.city" styleId="city" styleClass="txtbox width130" maxlength="25" onkeypress="return charactersForCity(event);" onblur="checkCity(this);"/></td>
                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.leads.pincode"/></td>
                    <td width="16%" ><html:text property="to.pincode" styleId="pincode" styleClass="txtbox width130" onkeypress="return allowOnlyNumbers(event);" maxlength="6" onblur="validatePincode(this);" /></td>
                   <td width="10%" class="lable"><bean:message key="label.leads.designation"/></td>
                    <td width="16%"><html:text property="to.contPersonDesig" styleId="designation" styleClass="txtbox width130" maxlength="30" onkeypress="return characterForDesignation(event);"  onchange="getSalesDesignationList();" onblur="checkDesignation(this)"/></td>
                  </tr>
                  <tr>
                     <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.emailId"/></td>
                    <td width="16%"><html:text property="to.emailAddress" styleId="emailAddress" styleClass="txtbox width130" maxlength="50" onblur="validateEmailAddress();" /></td>
                   <td width="24%" class="lable"><bean:message key="label.leads.leadSource"/></td>
                    <td width="19%">
                      <html:select property="to.leadSourceCode" styleId="leadSource" styleClass="selectBox width130">
                        <html:option value=""><bean:message key="label.common.select"/></html:option>
                        <c:forEach var="leadSourceList" items="${leadSourceList}" varStatus="loop">
		              		<html:option value="${leadSourceList.stdTypeCode}"><c:out value="${leadSourceList.description}"/></html:option>
		              		</c:forEach> 
                      </html:select></td>
                    <td width="24%" class="lable"><bean:message key="label.leads.industryCategory"/></td>
                     <td width="19%"><html:select property="to.industryCategoryCode" styleId="industryCategory" styleClass="selectBox width130">
                       <html:option value=""><bean:message key="label.common.select"/></html:option>
                      <c:forEach var="industryCategoryList" items="${industryCategoryList}" varStatus="loop">
		              		<html:option value="${industryCategoryList.stdTypeCode}" ><c:out value="${industryCategoryList.description}"/></html:option>
		            		</c:forEach>
                    </html:select></td>
                  </tr>
                   <tr>
                   <td width="11%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.businessType"/></td>
                    <td width="10%">
                     <html:select property="to.businessType" styleId="businessType" styleClass="selectBox width130">
                          <html:option value=""><bean:message key="label.common.select"/></html:option>  
                         <html:option  value="N">NORMAL </html:option>  
                         <html:option  value="E">E-Commerce</html:option>     
                      </html:select>
                    </td>
                    <td width="24%" class="lable"><bean:message key="label.leads.secondaryContact"/></td>
                   <td width="19%">
                    <html:text property="to.secondaryContact" styleId="secondaryContact" styleClass="txtbox width130" onkeypress="return charactersForSecondaryContactPerson(event);" maxlength="30">
                    </html:text></td> 
                    <td width="24%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.branch"/></td>
                    <td width="19%">
                      <html:select property="to.branch.branchId" styleId="branch" styleClass="selectBox width130" onchange="getSalesDesignationList();">
                        <c:forEach var="officeTOList" items="${officeTOList}" varStatus="loop">
		              		<html:option value="${officeTOList.officeId}" ><c:out value="${officeTOList.officeName}"/></html:option>
		            		</c:forEach> 
                      </html:select>
                    </td> 
                  </tr>
                   <tr>
                  	<!-- <td colspan="4">&nbsp;</td> -->
                  	
                  	<td width="19%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.salesPersonTitle"/></td>
                  	<td width="19%">
                     <html:select property="to.designation" styleId="salesDesignation" styleClass="selectBox width130" onchange="getSalesExecutiveList();">
                        <c:forEach var="salesPersonDesignationSet" items="${salesPersonDesignationSet}" varStatus="loop">
		              		<html:option value="${salesPersonDesignationSet.designation}" ><c:out value="${salesPersonDesignationSet.designation}" /></html:option>
		            		</c:forEach>      
                      </html:select>
                    </td>
                
                    <td width="24%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.assignedTo"/></td>
                    <td width="19%">
                    <html:select property="to.assignedTo.empUserId" styleId="assignedTo" styleClass="selectBox width130">
                       <c:forEach var="salesPersonList" items="${salesPersonList}" varStatus="loop">
		              		<html:option value="${salesPersonList.empUserDo.empUserId}" ><c:out value="${salesPersonList.userName}-${salesPersonList.empUserDo.empDO.firstName}"/></html:option>
		            		</c:forEach>        
                      </html:select>
                    </td>
                  </tr>
                 
                 
                </table>
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
         
         <c:if test="${userRole == createLeadForm.to.salesExecutiveRole}">
         <html:button property="FeedBack" styleClass="btnintform" styleId="feedbackBtn" onclick="leadFeedbackPopUp();">
        <bean:message key="button.label.FeedBack"/></html:button>
        
        <html:button property="Plan" styleClass="btnintform" styleId="planBtn" onclick="leadPlanPopUp();">
        <bean:message key="button.label.Plan"/></html:button>
        
        <html:button property="Generate Quotation" styleClass="btnintformbig1" styleId="generateQuotationBtn" onclick="generateQuotation();">
        <bean:message key="button.label.GenerateQuotation"/></html:button>
        
         <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="cancelDetails();">
        <bean:message key="button.label.Cancel"/></html:button>
         </c:if>
         
          <c:if test="${userRole == createLeadForm.to.controlTeamRole}">
         <html:button property="Approve" styleClass="btnintform" styleId="approveBtn" onclick="approveLead('approve');">
    <bean:message key="button.label.Approve"/></html:button>
    <html:button property="Reject" styleClass="btnintform" styleId="rejectBtn" onclick="rejectLead('reject');">
    <bean:message key="button.label.Reject"/></html:button>
    <html:button property="On Hold" styleClass="btnintform" styleId="onHoldBtn" onclick = "putOnHold('onHold');">
    <bean:message key="button.label.OnHold"/></html:button>
  	 <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick = "cancelDetails();">
    <bean:message key="button.label.Cancel"/></html:button>
     <html:button property="Send SMS" styleClass="btnintform" styleId="sendSmsBtn" onclick = "SMSPopUp();">
    <bean:message key="button.label.SendSms"/></html:button>
  	<html:button property="Send Email" styleClass="btnintform" styleId="sendEmailBtn" onclick = "emailPopUp();">
    <bean:message key="button.label.SendEmail"/></html:button>
         </c:if>
         
    
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          
         <html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode" value="${loginOfficeCode}"/>
		<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" value="${loginOfficeId}"/>
		<html:hidden property="to.date" styleId="date"/>
		<html:hidden property="to.dateOfUpdate" styleId="dateOfUpdate"/>
		<html:hidden property="to.status.statusDescription" styleId="status"/>
    	<html:hidden property="to.userRoles" styleId="userRoles"/> 
    	<html:hidden property="to.createdBy.employeeId" styleId="createdBy"/> 
    	<html:hidden property="to.leadId" styleId="leadId"/> 
    	<html:hidden property="to.leadCompetitorId" styleId="leadCompetitorId"/> 
    	
    	 <!-- hidden fields for generate quotation -->
			<html:hidden property="to.customerName" styleId="customerName" />
			<html:hidden property="to.leadNumber" styleId="leadNumber" />
			<html:hidden property="to.contactPerson" styleId="contactPerson" />
			<html:hidden property="to.phoneNoSTD" styleId="phoneNoSTD" />
			<html:hidden property="to.phoneNo" styleId="phoneNo" />
			<html:hidden property="to.mobileNo" styleId="mobileNo" />
			<html:hidden property="to.doorNoBuilding" styleId="doorNoBuilding" />
			<html:hidden property="to.street" styleId="street" />
			<html:hidden property="to.location" styleId="location" />
			<html:hidden property="to.city" styleId="city" />
			<html:hidden property="to.pincode" styleId="pincode" />
			<html:hidden property="to.designation" styleId="designation" />
			<html:hidden property="to.emailAddress" styleId="emailAddress" />
			<html:hidden property="to.industryCategoryCode" styleId="industryTypeCode" />
			<html:hidden property="to.secondaryContact" styleId="secondaryContact" />
			<input type="hidden" id="sales" name="sales" value="E"/>
			<input type="hidden" id="submitName" name="submitName" value="viewEcommerceRateQuotation" />
			<%-- <html:hidden property="to.businessType" styleId="businessType" /> --%>


			<!-- footer -->
         <!--  <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div> -->
          <!-- footer ends --> 
</html:form> 
</div>
<!--wrapper ends-->
		</body>
		</html>