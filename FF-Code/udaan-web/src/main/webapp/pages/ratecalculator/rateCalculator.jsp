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
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
		<script type="text/javascript" charset="utf-8" src="js/rateCalculator/rateCalculator.js"></script>
				
		</head>
		<body>
<!--wraper-->
<div id="wraper"> 
          
          <div class="clear"></div>
           <html:form  action="/rateCalculator.do" method="post" styleId="rateCalculatorForm"> 
           <html:hidden property="to.originOfficeCode" styleId="officeName" value="${originOfficeCode}"/> 
           <html:hidden property="to.originCityId" styleId="originCityId" value="${originCityId}"/> 
           <html:hidden property="to.insuredBy" styleId="insuredBy" value="F"/> 
           
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><strong><bean:message key="label.ratecalculator.header"/></strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="2" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.producttype"/></td>
                    <td width="10%" >
                    <html:select property="to.productType" styleId="productType" styleClass="selectBox width130" onchange = "checkIsPreferenceApplicable(this); fnServiceOnEditable();" onkeypress="enterKeyForProduct(event);">
		          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		          	 <c:forEach var="productTO" items="${productList}" varStatus="loop">
		              <option value="${productTO.productCode}" ><c:out value="${productTO.productName}"/></option>
		            </c:forEach>  
		           	</html:select>
               		</td>
               		<td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.preferences"/></td>
               		<td width="10%">
                    <html:select property="to.preferences" styleId="preferences" styleClass="selectBox width240"  onkeypress="enterKeyNavigationFocus(event,'serviceAt');" disabled = "true" >
		          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		          	 <c:forEach var="preference" items="${preferenceDetails}" varStatus="loop">
		              <option value="${preference.preferenceCode}" ><c:out value="${preference.description}"/></option>
		            </c:forEach>  
		           	</html:select>
               		</td>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.serviceAt"/></td>
                    <td width="17%" >
                     <html:select property="to.serviceAt" styleId="serviceAt" styleClass="selectBox width130"  onkeypress="enterKeyNavigationFocus(event,'CNtype');" disabled = "true" >
		          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		          	<c:forEach var="servicedOn" items="${servicedOnList}" varStatus="loop">
		              <option value="${servicedOn.stdTypeCode}" ><c:out value="${servicedOn.description}"/></option>
		            </c:forEach>  
		           	</html:select>
                   </td>
                  </tr>
                  <tr>
                   <td class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.CNtype"/></td>
                    <td>
                    <html:select property="to.CNtype" styleId="CNtype" styleClass="selectBox width130"  onkeypress="enterKeyForConsignmentType(event);" onchange="fnDeclaredValEditable();" >
		          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		          	 <c:forEach var="consgTypeTO" items="${consgTypeList}" varStatus="loop">
		              <option value="${consgTypeTO.consignmentCode}" ><c:out value="${consgTypeTO.consignmentName}"/></option>
		            </c:forEach>  
		           	</html:select>
                    </td>
                    	<td class="lable" ><bean:message key="label.Rate.DeclaredValue"/></td>
                 	<td>
                    <html:text property="to.declaredValue" styleId="declaredValue1" styleClass="txtbox width130" onchange="isValidDecValue();"  onkeypress="return onlyNumberNenterKeyNav(event, 'weightKg');" maxlength="10" size="11" />
                    </td>
                    <td class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.weight"/></td>
                    <td><span class="lable"><bean:message key="label.ratecalculator.kgs"/>&nbsp;</span>
                    <html:text property="to.weightKg" styleId="weightKg" styleClass="txtbox width30" value="" onkeypress="enterKeyNavigationFocus(event,'weightGrm');"/> .
                    <html:text property="to.weightGrm" styleId="weightGrm" styleClass="txtbox width30" value="" onkeypress="enterKeyNavigationFocus(event,'pincode');"/>&nbsp;<span class="lable"><bean:message key="label.ratecalculator.gms"/></span></td>
                  </tr>
                  <tr>
                  <td class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.pincode"/></td>
                    <td>
                    <html:text property="to.pincode" styleId="pincode" maxlength="6" styleClass="txtbox width130" onblur="getCityName(this);"  onkeypress="enterKeyNavigationFocus(event,'destinationCity');"/>
                    </td>
                    <td class="lable"><sup class="star">*</sup><bean:message key="label.ratecalculator.destination"/></td>
                    <td >
                     <html:text property="to.destination" styleId="destinationCity" styleClass="txtbox width130" value="" readonly="true" onkeypress="enterKeyNavigationFocus(event,'calculateBtn');"/>
                    </td>
                  </tr>
                </table>
          </div>
          
      <div>
        <div class="title">
                  <div class="title2"> <bean:message key="label.ratecalculator.calculatedrates"/></div>
                  
                  <!-- Button -->
          <div style="float:right;">
          
          <html:button property="Clear" styleClass="btnintform" styleId="cancelBtn" onclick = "clearFormData();"><bean:message key="button.label.clear"/></html:button>
          <html:button property="Calculate" styleClass="btnintform" styleId="calculateBtn"  onclick = "calculateRates();"><bean:message key="label.calculator.button.calculate"/></html:button>
          </div>
          <!-- Button ends --> 
            </div>
            &nbsp;
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                  <thead>
            <tr>
                      <th width="8%"><bean:message key="label.ratecalculator.components"/></th>
                      <th width="8%"><bean:message key="label.ratecalculator.amounts"/></th>
                    </tr>
          </thead>
                  <tbody>
            <tr class="gradeA">
                      <td class="center">Slab Rate</td>
                      <td class="center"><input name="slabRate" id="slabRate" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Risk Surcharge</td>
                      <td class="center"><input name="riskSurcharge" id="riskSurcharge" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Parcel Handling Charges</td>
                      <td class="center"><input name="parcelCharges" id="parcelCharges" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Document Handling Charges</td>
                      <td class="center"><input name="documentCharges" id="documentCharges" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Airport Handling Charges</td>
                      <td class="center"><input name="airportCharges" id="airportCharges" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Other Charges</td>
                      <td class="center"><input name="otherCharges" id="otherCharges" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">To Pay Charges</td>
                      <td class="center"><input name="toPayCharges" id="toPayCharges" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Fuel Surcharge</td>
                      <td class="center"><input name="fuelSurcharge" id="fuelSurcharge" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Service Tax</td>
                      <td class="center"><input name="serviceTax" id="serviceTax" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
                      <td class="center">Swachh Bharat Cess</td>
                      <td class="center"><input name="eduCess" id="eduCess" type="text" class="txtbox width75" size="6" value=""/></td>
                    </tr>
            <tr class="gradeA">
             <td class="center">Higher Education Cess</td>
             <td class="center"><input name="higherEduCess" id="higherEduCess" type="text" class="txtbox width75" size="6" value=""/></td>
           </tr>
            <tr class="gradeA">
             <td class="center">State Tax</td>
             <td class="center"><input name="stateTax" id="stateTax" type="text" class="txtbox width75" size="6" value=""/></td>
           </tr>
            <tr class="gradeA">
             <td class="center">Surcharge on State Tax</td>
             <td class="center"><input name="surchargeOnState" id="surchargeOnState" type="text" class="txtbox width75" size="6" value=""/></td>
           </tr>
             <tr class="gradeA">
             <td class="center"> COD Charges</td>
             <td class="center"><input name="codCharge" id="codCharge" type="text" class="txtbox width75" size="6" value=""/></td>
           </tr>
           
            <tr class="gradeA">
             <td class="center"><strong>Total</strong></td>
             <td class="center"><input name="total" id="total" type="text" class="txtbox width75" size="6" value=""/></td>
           </tr>
          </tbody>
            </table>
      </div>
              
<!-- Grid /--> 
            </div>
  </div>
          <!-- main content ends --> 
          </html:form>
         
        </div>
<!-- wrapper ends -->
</body>
</html>
