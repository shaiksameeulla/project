<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Rate Quotation</title>

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/ratequotationproposedrates.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/rateQuotationFixedCharges.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/rateQuotationBasicInfo.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/rateQuotationRTOCharges.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/rateContractBasicInfo.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/rateQuotationEcommerce.js"></script>
<script type="text/javascript" charset="utf-8">
	var salesType=null;
 
   	var riskSurchrge="";
   	var fuelSurcharge="";
   	var airportCharges="";
   	var surchargeOnST="";
   	var otherCharges="";
   	var parcelCharges="";
   	var eduCharges="";
   	var HigherEduCharges="";
   	var documentCharges="";
   	var toPayCharges="";
   	var serviceTax="";
   	var stateTax="";
    var lcCharges="";
    var octroiServiceCharges="";
    var octroiBourneBy="";
    var government="";
   	var general="";
	var SUCCESS_FLAG = "SUCCESS";
	var ERROR_FLAG = "ERROR";
	
			$(document).ready( function () {
				var oTable1 = $('#example1').dataTable( {
					"sScrollY": "100",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,					
					"sPaginationType": "full_numbers"
					
				} );
				new FixedColumns( oTable1, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
			} );
			
			function loadDefaultObjects(){
						riskSurchrge="<bean:message key="label.Rate.RISK_SURCHARGE"/>";
                	    fuelSurcharge="<bean:message key="label.Rate.FUEL_SURCHARGE"/>";
                	    airportCharges="<bean:message key="label.Rate.AIRPORT_HANDLING_CAHRGES"/>";
                	   	 surchargeOnST="<bean:message key="label.Rate.SURCHARGE_ON_ST"/>";
                	   	 otherCharges="<bean:message key="label.Rate.OTHER_CHARGES"/>";
                	    parcelCharges="<bean:message key="label.Rate.PARCEL_HANDLING_CHARGES"/>";
                	   	 eduCharges="<bean:message key="label.Rate.EDUCATION_CESS"/>";
                	    HigherEduCharges="<bean:message key="label.Rate.HIGHER_EDUCATION_CESS"/>";
                	   documentCharges="<bean:message key="label.Rate.DOCUMENT_HANDLING_CAHRGES"/>";
                	   	 toPayCharges="<bean:message key="label.Rate.TO_PAY_CHARGES"/>";
                	    serviceTax="<bean:message key="label.Rate.SERVICE_TAX"/>";
                	   stateTax="<bean:message key="label.Rate.STATE_TAX"/>";
                	     lcCharges="<bean:message key="label.Rate.LC_CHARGES"/>";
                	    octroiServiceCharges="<bean:message key="label.Rate.OCTROI_SERVICE_CHARGE"/>";
                	     octroiBourneBy="<bean:message key="label.Rate.OCTROI_BOURNE_BY"/>";
                		salesType='${salesType}';
                		government='${government}';
                		general='${general}';
                		}
		</script>
<script type="text/javascript" charset="utf-8">
        
		$(document).ready( function () {
			ecommerceOnLoad('N');
		} );
	</script>
<script type="text/javascript" charset="utf-8">
        
$(document).ready( function () {
    var oTable =  $('#specialGrid1').dataTable({
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );
</script>
<script type="text/javascript" charset="utf-8">

$(document).ready( function () {
    var oTable6 =  $('#specialGrid3').dataTable({
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable6, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

</script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
    var oTable5 =  $('#specialGrid2').dataTable({
		"sScrollY" : "60",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable5, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

		</script>
<script type="text/javascript" charset="utf-8">
        $(document).ready( function () {
			var oTable2 = $('#example2').dataTable( {
				"sScrollY": "100",
				"sScrollX": "100%",
				"sScrollXInner":"100%",
				"bScrollCollapse": false,
				"bSort": false,
				"bInfo": false,
				"bPaginate": false,
				"sPaginationType": "full_numbers"
			} );
			new FixedColumns( oTable2, {
				"sLeftWidth": 'relative',
				"iLeftColumns": 0,
				"iRightColumns": 0,
				"iLeftWidth": 0,
				"iRightWidth": 0
			} );
        } );
        </script>
<script type="text/javascript" charset="utf-8">
        $(document).ready( function () {
				var oTable3 = $('#example3').dataTable( {
					"sScrollY": "90%",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable3, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
        } );
        $(document).ready( function () {
        $("#tabs").tabs({disabled: [1,2,3]});
        } );
        
        function enableTabs(index){
        	$("#tabs").tabs( "enable" , index ); 
        }
		</script>
<!-- DataGrids /-->

		<!-- Tabs-->
<!--<script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
<!--<link rel="stylesheet" href="/resources/demos/style.css" />  -->
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
<script>  
		$(function() {    $( "#tabsnested" ).tabs();  });  
		
	</script>
	<script>
		$(document).ready( function () {
			
			if(!isNull("${industryTypeCode}")){
				document.getElementById("IndustryType").value = "${industryTypeCode}";
			}
			var modVal = document.getElementById("module").value;
			if(modVal == "view"){
				errMsg = document.getElementById("errorMsg").value;
				if(!isNull(errMsg)){
					alert(errMsg);
				}
			}
		});
		</script>
<!-- Tabs ends /-->

</head>
<body onload="loadDefaultObjects(), searchQuotation();">
	<html:form action="/rateQuotation.do?submitName=viewRateQuotation"
		styleId="rateQuotationForm" method="post">
		<!--wraper-->
		<div id="wraper">

			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<strong><bean:message
											key="label.Rate.Quotation.Normal" /> </strong>
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span><bean:message
											key="label.mandatory" />
							<logic:present name="quotModule" scope="request"> 
							<u><a href="" onclick="closeWindow();">close</a></u>
							</logic:present>
						</div>
						
					</div>
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1" onclick="searchQuotation();"><bean:message key="label.Rate.BasicInfo"/></a></li>
							<li><a href="#tabs-2" onclick="loadAllGridValues(${productId},'${productCode}',1,0);"><bean:message key="label.Rate.ProposedRates"/></a></li>
							<li><a href="#tabs-3" onclick="loadFixedChargesDefault();setOctroiChargeValue();"><bean:message key="label.Rate.FixedCharges"/></a></li>
							<li><a href="#tabs-4" onclick="loadRTOChargesDefault();"><bean:message key="label.Rate.RTOCharges"/></a></li>
						</ul>
						<div id="tabs-1">
							<table border="0" cellpadding="0" cellspacing="3" width="100%">
								<tr>
									<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.QuotationNo" /></td>
									<td width="22%" class="lable1"><html:text
											property="to.rateQuotationNo" styleId="QuotationNo"
											styleClass="txtbox width130"></html:text>
											<html:button
										property="Search" styleId="Find" styleClass="btnintgrid"
										onclick="searchQuotation()">
										<bean:message key="button.search" />
									</html:button></td> 
									<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.CreatedDate" /></td>
									<td width="19%" class="lable1"><html:text
											property="to.createdDate" styleId="CreatedDate" readonly="true"
											styleClass="txtbox width170" value="${createdDate}"></html:text></td>
									<!--    &nbsp;<a href="#" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a></td> --%> -->
									<logic:notPresent name="quotRHOList" scope="request">
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Region" /></td>
									<td width="16%" class="lable1"><input name="textfield4"
										type="text" id="Region" class="txtbox width130"
										readonly="readonly" value="${loginRegionName}" /></td>
									</logic:notPresent>
									
									<c:if test="${salesType == 'C'}">
									<logic:present name="quotRHOList" scope="request">
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Region" /></td>
										<td class="lable1"><html:select
												property="to.rhoOfcId"
												styleClass="selectBox width130" styleId="rhoOfcId"
												onchange="getAllCities();">
												<option value="">----Select----</option>
												<c:forEach var="rhoOfcTO" items="${rhoOfcList}" varStatus="loop">

													<option value="${rhoOfcTO.officeId}">${rhoOfcTO.officeName}</option>

												</c:forEach>
											</html:select></td>
										</logic:present>
										</c:if>


								</tr>
								<tr>
									<c:if test="${salesType == 'E'}">
										<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.Station" /></td>
										<td class="lable1"><input name="textfield4"
											type="text" id="Station" class="txtbox width130"
											readonly="readonly" value="${cityName}" /></td>

										<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesOffice" /></td>
										<td class="lable1"><html:text
												property="to.customer.salesOffice.officeName"
												styleId="SalesOffice" styleClass="txtbox width170"
												disabled="true" value="${office}"></html:text></td>
										<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesPerson" /></td>
										<td class="lable1"><input name="textfield4"
											type="text" id="SalesPerson" class="txtbox width130"
											readonly="readonly" disabled="disabled"
											value="${employeeName}" /></td>
									</c:if>
									<c:if test="${salesType == 'C'}">
									<logic:notPresent name="quotModule" scope="request">										
										<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.Station" /></td>
										<td class="lable1"><html:select
												property="to.customer.salesOffice.cityId"
												styleClass="selectBox width130" styleId="Station"
												onchange="getAlloffices();">
												<option value="">----Select----</option>
												<c:forEach var="cityTOs" items="${cityTOs}" varStatus="loop">

													<option value="${cityTOs.cityId}">
														<c:out value="${cityTOs.cityName}" />
													</option>

												</c:forEach>
											</html:select></td>

										<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesOffice" /></td>
										<td class="lable1"><html:select
												property="to.customer.salesOffice.officeId"
												styleClass="selectBox width170" styleId="SalesOffice"
												onchange="getAllEmployees()">
												<%--     <option value="">----Select----</option>
                           <c:forEach var="officeList" items="${officeTOs}" varStatus="loop">
              
               		<option value="${officeList.officeId}" ><c:out value="${officeList.officeName}"/></option>
             
            		</c:forEach>  --%>
											</html:select></td>

										<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesPerson" /></td>
										<td class="lable1"><html:select
												property="to.customer.salesPerson.employeeId"
												styleClass="selectBox width130" styleId="SalesPerson">

											</html:select></td>
										</logic:notPresent>
										<logic:present name="quotModule" scope="request">
										<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.Station" /></td>
										<td width="20%" class="lable1"><input name="textfield4"
											type="text" id="Station" class="txtbox width130"
											readonly="readonly" value="${cityName}" /></td>

										<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesOffice" /></td>
										<td width="19%" class="lable1"><html:text
												property="to.customer.salesOffice.officeName"
												styleId="SalesOffice" styleClass="txtbox width170"
												disabled="true" value="${office}"></html:text></td>
										<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.Rate.SalesPerson" /></td>
										<td width="16%" class="lable1"><input name="textfield4"
											type="text" id="SalesPerson" class="txtbox width130"
											readonly="readonly" disabled="disabled"
											value="${employeeName}" /></td>
										</logic:present>
									</c:if>
								</tr>
								<tr>
									<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.IndustryCategory" /></td>
									<td class="lable1"><html:select
											property="to.customer.industryCategory"
											styleClass="selectBox width130" styleId="IndustryCategory"
											onchange="changeBusinessType(this);">
											<c:forEach var="IndustryCategory"
												items="${industryCategoryToList}" varStatus="loop">

												<option value="${IndustryCategory.rateIndustryCategoryId}~${IndustryCategory.rateCustomerCategoryId}" >${IndustryCategory.rateIndustryCategoryName}</option>
											</c:forEach>
										</html:select></td>
										<c:forEach var="IndustryCategory"
												items="${industryCategoryToList}" varStatus="loop">
										<input type="hidden" id="custCat${IndustryCategory.rateIndustryCategoryId}~${IndustryCategory.rateCustomerCategoryId}" value = "${IndustryCategory.rateCustomerCategoryId }"/>
										</c:forEach>
										
									<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.BusinessType" /></td>
									<td class="lable1"><html:select
											property="to.customer.businessType"
											styleClass="selectBox width170" styleId="BusinessType" disabled="true">
											<option value="">----Select----</option>
											<c:forEach var="businessTypes"
												items="${stockStandardTypeTOList}" varStatus="loop">

												<option value="${businessTypes.stdTypeCode}">
													<c:out value="${businessTypes.description}" />
												</option>

											</c:forEach>
										</html:select></td>
									<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.IndustryType" /></td>
									<td class="lable1"><html:select
											property="to.customer.industryType"
											styleClass="selectBox width130" styleId="IndustryType">
											<option value="">----Select----</option>
											<c:forEach var="industryTypes"
												items="${rateIndustryTypeTOList}" varStatus="loop">

												<option
													value="${industryTypes.rateIndustryTypeId}~${industryTypes.rateIndustryTypeCode}">
													<c:out value="${industryTypes.rateIndustryTypeName}" />
												</option>

											</c:forEach>
										</html:select></td>
								</tr>
								<tr>
									<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.GroupKey" /></td>
									<td  class="lable1"><html:select
											property="to.customer.groupKey.customerGroupId"
											styleClass="selectBox width130" styleId="GroupKey">
											<option value="">----Select----</option>
											<c:forEach var="customerGroup" items="${customerGroupTOList}"
												varStatus="loop">

												<option value="${customerGroup.customerGroupId}">
													<c:out value="${customerGroup.customerGroupName}" />
												</option>

											</c:forEach>
										</html:select></td>
									<td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.CusotmerName" /></td>
									<td class="lable1"><html:text
											property="to.customer.businessName" styleId="CustomerName"
											styleClass="txtbox width170" maxlength="70"></html:text></td>
								</tr>
								<tr>
									<td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine1" /></td>
									<td  class="lable1"><html:text
											property="to.customer.address.address1" styleId="Address1"
											styleClass="txtbox width130" maxlength="100"></html:text></td>
									<td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine2" /></td>
									<td  class="lable1"><html:text
											property="to.customer.address.address2" styleId="Address2"
											styleClass="txtbox width170" maxlength="100"></html:text></td>
									<td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine3" /></td>
									<td  class="lable1"><html:text
											property="to.customer.address.address3" styleId="Address3"
											styleClass="txtbox width130" maxlength="100"></html:text></td>
								</tr>
								<tr>
									<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Pincode" /></td>
									<td  class="lable1"><html:text
											property="to.customer.address.pincode.pincode"
											styleId="Pincode" styleClass="txtbox width130" onkeypress="return validatePinKey(event);"
											onblur="getPincode(this);" maxlength="6"></html:text></td>
									<td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.City" /></td>
									<td  class="lable1"><html:text
											property="to.customer.address.city.cityName" styleId="City"
											styleClass="txtbox width170" readonly="true"></html:text></td>
									<td  class="lable">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4" class="lable1" align="center"></td>
								</tr>
							</table>
							<!--<p>-->


							<div class="columnuni">
								<div class="columnleft">
										<fieldset>
											<legend>
												&nbsp;
												<bean:message key="label.Rate.PRIMARYCONTACT" />
											</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.Rate.Title" /></td>
													
													<td class="lable1"><html:select
														property="to.customer.primaryContact.title"
														styleClass="selectBox width110" styleId="Title1">
														<option value="">----Select----</option>
														<c:forEach var="Titles"
															items="${RATE_QUOTATION_TITLE}" varStatus="loop">

															<option value="${Titles.stdTypeCode}">
																<c:out value="${Titles.description}" />
															</option>

														</c:forEach>
													</html:select></td>
												
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.AuthorizedPerson1" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.customer.primaryContact.name"
														styleId="AuthorizedPerson1" styleClass="txtbox width110" maxlength="50"></html:text>
												</td>
											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Designation" /></td>
												<td class="lable1"><html:text
														property="to.customer.primaryContact.designation"
														styleId="Designation1" styleClass="txtbox width110" maxlength="20"></html:text>
												</td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Department" /></td>
												<td class="lable1"><html:select
														property="to.customer.primaryContact.department"
														styleClass="selectBox width110" styleId="Department1">
														<option value="">----Select----</option>
														<c:forEach var="businessTypes"
															items="${CUSTOMER_DEPARTMENT_LIST}" varStatus="loop">

															<option value="${businessTypes.stdTypeCode}">
																<c:out value="${businessTypes.description}" />
															</option>

														</c:forEach>
													</html:select></td>


											</tr>
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Email" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.customer.primaryContact.email"
														styleId="Email1" styleClass="txtbox width110"
														onblur="validateEmailAddress(this)" maxlength="100"></html:text></td>
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Contact" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.customer.primaryContact.contactNo"
														styleId="Contact1" styleClass="txtbox width110" onkeypress="return onlyNumeric(event);" maxlength="13"></html:text>
												</td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.Extn" /></td>
												<td class="lable1"><html:text
														property="to.customer.primaryContact.extension"
														styleId="Extn1" styleClass="txtbox width110" onkeypress="return onlyNumeric(event);" maxlength="11"></html:text>
												</td>
												<td class="lable"><bean:message key="label.Rate.Mobile" /></td>
												<td class="lable1"><html:text
														property="to.customer.primaryContact.mobile"
														styleId="Mobile1" styleClass="txtbox width110"
														onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);" maxlength="10"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.FAX" /></td>
												<td class="lable1"><html:text
														property="to.customer.primaryContact.fax" styleId="FAX1"
														styleClass="txtbox width110" onkeypress="return allowOnlyNumbers(event);" maxlength="12" ></html:text></td>
												<td class="lable">&nbsp;</td>
												<td class="lable1">&nbsp;</td>
											</tr>
										</table>
									</fieldset>
									<!-- </form> -->
								</div>
								<div class="columnleft1">
									<!--  <form method="post" action="index.html"> -->
									<fieldset>
										<legend>
											<input type="checkbox" class="checkbox" name="type"
												id="secondaryCheck" onclick="enableSecondary(this);" />
											&nbsp;
											<bean:message key="label.Rate.SECONDARYCONTACT" />
											&nbsp;
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.Rate.Title" /></td>
												<td class="lable1"><html:select
														property="to.customer.secondaryContact.title"
														styleClass="selectBox width110" styleId="Title2" disabled="true">
														<option value="">----Select----</option>
														<c:forEach var="Titles"
															items="${RATE_QUOTATION_TITLE}" varStatus="loop">

															<option value="${Titles.stdTypeCode}">
																<c:out value="${Titles.description}" />
															</option>

														</c:forEach>
													</html:select></td>
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.AuthorizedPerson2" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.customer.secondaryContact.name"
														styleId="AuthorizedPerson2" styleClass="txtbox width110"
														disabled="true" maxlength="50"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Designation" /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.designation"
														styleId="Designation2" styleClass="txtbox width110"
														disabled="true" maxlength="20"></html:text></td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Department" /></td>
												<td class="lable1"><html:select
														property="to.customer.secondaryContact.department"
														styleClass="selectBox width110" styleId="Department2"
														disabled="true">
														<option value="">----Select----</option>
														<c:forEach var="businessTypes"
															items="${CUSTOMER_DEPARTMENT_LIST}" varStatus="loop">

															<option value="${businessTypes.stdTypeCode}">
																<c:out value="${businessTypes.description}" />
															</option>

														</c:forEach>
													</html:select></td>




											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Email" /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.email"
														styleId="Email2" styleClass="txtbox width110"
														disabled="true" onblur="validateEmailAddress(this)" maxlength="100"></html:text></td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Contact" /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.contactNo"
														styleId="Contact2" styleClass="txtbox width110"
														disabled="true" onkeypress="return onlyNumeric(event);"  maxlength="13"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.Extn" /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.extension"
														styleId="Extn2" styleClass="txtbox width110"
														disabled="true" onkeypress="return onlyNumeric(event);" maxlength="11"></html:text></td>
												<td class="lable"><bean:message key="label.Rate.Mobile"   /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.mobile"
														styleId="Mobile2" size="10" styleClass="txtbox width110"
														disabled="true" onblur="isValidMobile(this);"  maxlength="10" onkeypress="return onlyNumeric(event);"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.FAX" /></td>
												<td class="lable1"><html:text
														property="to.customer.secondaryContact.fax" styleId="FAX2"
														styleClass="txtbox width110" onkeypress="return allowOnlyNumbers(event);" maxlength="12" disabled="true"></html:text></td>
												<td class="lable">&nbsp;</td>
												<td class="lable1">&nbsp;</td>
											</tr>
										</table>
										</fieldset>
								</div>
								<!-- Hidden Variables Starts -->
								<input type="hidden" name="salesType" id="salesType"
									value="${salesType}" />
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${createdBy}" />
								<html:hidden property="to.customer.customerId"
									styleId="customerNewId" />
								<html:hidden property="to.customer.primaryContact.contactId"
									styleId="contactId1" />
								<html:hidden property="to.customer.secondaryContact.contactId"
									styleId="contactId2" />
								<html:hidden property="to.rateQuotationId"
									styleId="rateQuotationId" />
								<input type="hidden" name="hdnQuotationNo" id="hdnQuotationNo" />
								<html:hidden property="to.loginOfficeCode"
									styleId="loginOfficeCode" value="${loginOfficeCode}" />
								<input type="hidden" id="stateId" value="${stateId}" /> <input
									type="hidden" id="loginCityId" value="${loginCityId}" /> <input
									type="hidden" id="loginOfficeId" value="${loggedInOfficeId}" />
								<html:hidden property="to.customer.address.city.cityId"
									styleId="CityId" />
								<html:hidden property="to.customer.address.pincode.pincodeId"
									styleId="PincodeId" />
								<html:hidden property="to.customer.salesOffice.officeId"
									styleId="SalesOffice" value="${loggedInOfficeId}" />
								<html:hidden property="to.customer.salesPerson.employeeId"
									styleId="SalesPerson" value="${employeeId}" />
								<html:hidden property="to.status" styleId="quotationStatus" />
								<%-- <html:hidden property="to.rateOriginatedFrom"
									styleId="ratequotationOriginatedFrom" /> --%>
								<html:hidden property="to.rateQuotationOriginatedfromType"
									styleId="rateQuotationOriginatedfromType" />
								<html:hidden property="to.rateQuotationType"
									styleId="rateQuotationType" value="${rateQuotationType}" />
								<html:hidden property="to.quotationUsedFor"
									styleId="quotationUsedFor" />
								<input type="hidden" id="indCatGeneral" value="${indCatGeneral}" />

								<input type="hidden" id="module" value="${quotModule}" />
								<input type="hidden" id="approvalRequired" name="approvalRequired"/>
								<input type="hidden" id="approvedAt" name="approvedAt"/>
								<input type="hidden" id="approverLevel" name="approverLevel" value="${approverLevel}"/>
								<input type="hidden" id="page" name="page" value="${listViewPage}"/>
								<input type="hidden" id="empType" name="empType" value="${empType}"/>
								<html:hidden property="to.approver" styleId="approver" />
								<html:hidden property="to.contractCreated" styleId="contractCreated" />
								<html:hidden property="to.lcCode" styleId="lcCode" />
								<html:hidden property="to.empOfcType" styleId="empOfcType" />
								<html:hidden property="to.customer.legacyCustomerCode" styleId="legacyCustomerCode" />
								<html:hidden property="to.quotationCreatedFrom" styleId="quotationCreatedFrom" value="O"/>
								<!-- Hidden Variables End -->

								<!-- Button -->
								<div class="button_containerform">


									<html:button property="clearBasicInfoBtn" styleClass="btnintform"
										styleId="clearBasicInfoBtn" onclick="clearBasicInfo();">
										<bean:message key="button.clear" />
									</html:button>
									<html:button property="saveBasicInfoBtn" styleClass="btnintform"
										styleId="saveBasicInfoBtn" onclick="saveOrUpdateBasicInfo();">
										<bean:message key="button.save" />
									</html:button>
								</div>
								<!-- Button ends -->
							</div>

							<div class="clear"></div>
						</div>
						<!---tab 1 ends-->
						<div id="tabs-2" >

							<div id="tabsnested">
								<ul>
									<logic:present name="prodCatList" scope="request">
										<c:forEach var="prodCat" items="${prodCatList}"
											varStatus="loop">
											<li><a href="#tab-${prodCat.rateProductCategoryId}"
												onclick="loadAllGridValues(${prodCat.rateProductCategoryId},'${prodCat.rateProductCategoryCode}',${loop.count},${loop.count-1});"><c:out
														value='${prodCat.rateProductCategoryName}' /></a></li>
										</c:forEach>
									</logic:present>
								</ul>
								<c:forEach var="rateProdCat" items="${rateQuotNormalProdCategoryList}"
									varStatus="loop1">
									<div id="tab-${rateProdCat.value}">
										<table border="0" cellpadding="0" cellspacing="5" width="100%">
											<tr>
												<td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.rate.volumeOfBusiness" /></td>
												<td class="lable1"><html:select
														styleId="rateVob${rateProdCat.value}"
														property="proposedRatesTO.rateVob"
														styleClass="selectBox width145">
														<logic:present name="rateQuotNormalVobSlabList" scope="request">
															<c:forEach var="rateVobSlabs" items="${rateQuotNormalVobSlabList}"
																varStatus="loop2">
																<c:if
																	test="${rateVobSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
																	<html:option
																		value='${rateVobSlabs.vobSlabTO.vobSlabId}'>
																		<fmt:parseNumber integerOnly="true"
																			value="${rateVobSlabs.vobSlabTO.lowerLimit}"
																			type="number" /> - <fmt:parseNumber
																			integerOnly="true"
																			value="${rateVobSlabs.vobSlabTO.upperLimit}"
																			type="number" />
																	</html:option>
																</c:if>
															</c:forEach>
														</logic:present>
													</html:select></td>
													<c:if test="${rateProdCat.label == 'TR'}">
														<td width="18%" class="lable1" align="center">
														<input type="checkbox" name="flatRateChk" id="flatRateChk" />Flat Rate Applicable</td>
													</c:if>
														<c:if test="${rateProdCat.label != 'CO'}">
																<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
																		key="label.rate.origin" /></td>
																<td width="40%" class="lable1"><html:select
																		styleId="rateOrgSec${rateProdCat.value}"
																		property="proposedRatesTO.rateOrgSec"
																		styleClass="selectBox width145"
																		onchange="assignOrgSector();">
																	</html:select> <c:set var="origin" value="true" /></td>
																	</c:if>
																	<c:if test="${rateProdCat.label == 'CO'}">
																<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;Rate type</td>
																<td width="40%" class="lable1">
																<select id="configuredType" name="configuredType" onchange="assignConfiguredWeightSlabs();">
																<option value="B">BOTH</option>
																<option value="D">Documents</option>
																<option value="P">Parcel</option>																
																</select></td>
																	</c:if>		
														
											</tr>
										</table>


										<div style="width:98%">
										
											<c:set var="loopValue" value="5" />
											<table cellpadding="0" cellspacing="0" border="0" class="display" id="example${loop1.count}" >
												
												<thead>
												<c:if test="${rateProdCat.label != 'CO'}">
													<tr>
														<th align="center" width="24%"><bean:message
																key="label.rate.sectorWeightSlab" /></th>

														<c:forEach var="i" begin="1" end="${loopValue-1 }">
															<th align="center">
															<span id="rateStartWeightSlab${rateProdCat.value}${i}"></span> - 
															<select name="proposedRatesTO.rateQuotEndWeight" id="rateQuotEndWeight${rateProdCat.value}${i}" class="selectBox width80" onchange="assignWeightSlab('rateQuotEndWeight${rateProdCat.value}${i}','rateStartWeightSlab${rateProdCat.value}${i+1}','rateQuotEndWeight${rateProdCat.value}${i+1}','rateSpecialStartWeightSlab${rateProdCat.value}${i+1}', ${rateProdCat.value},${i})">
															<option value="">--select--</option>
															</select> <input type="hidden"
																id="rateWtSlabId${rateProdCat.value}${i}"
																name="proposedRatesTO.rateWtSlabId" size="11"
																class="txtbox width130" /> <input type="hidden"
																id="rateWtId" name="proposedRatesTO.rateWtId" size="11"
																class="txtbox width130" value="${rateProdCat.value}" />
																<input type="hidden"
																id="ratehdnWtId${rateProdCat.value}${i}" name="ratehdnWtId${rateProdCat.value}${i}" />														</th>
														</c:forEach>

														<th><select name="rateQuotAddWt"
															id="rateQuotAddWeight${rateProdCat.value}"
															class="selectBox width90" 
															onchange="assignAddWeightSlab('rateQuotAddWeight${rateProdCat.value}');">
																<option value="">--select--</option>
														</select> <input type="hidden"
															id="rateAddWtSlab${rateProdCat.value}"
															name="proposedRatesTO.rateAddWtSlab" size="11"
															class="txtbox width130" /><input type="hidden"
															id="ratehdnAddWtSlab${rateProdCat.value}"
															name="ratehdnAddWtSlab${rateProdCat.value}"/></th>

													</tr>
													</c:if>
													<c:if test="${rateProdCat.label == 'CO'}">
													<tr><th></th></tr>
													</c:if>
												</thead>
											</table>
											
												<div class="formbox">
													<h1>
														<strong><bean:message key="label.Rate.SpecialDestination" /></strong>
													</h1>
												</div>
												<div style="clear:both;line-height:3px;">&nbsp;</div>
												<div class="button_containerform">
												<html:button property="btnAdd${rateProdCat.value}"
												styleClass="btnintform"
												styleId="btnAdd${rateProdCat.value}"
												onclick="addRow(${loop1.count});" title="Add">
												<bean:message key="button.rate.add" />
												</html:button>
												</div>
												<div style="clear:both;line-height:3px;">&nbsp;</div>
												<!-- <div id="demo"> -->
												<div style="width:100%;">
													<table cellpadding="0" cellspacing="0" border="0"
														class="display" id="specialGrid${loop1.count}" width="90%">
														<thead>
															<tr>
															<th width = "5%" align="center"><bean:message key="label.Rate.SrNo" /></th>
																<th width = "18%" align="center"><bean:message key="label.rate.state" /></th>
																<th width = "18%" align="center"><bean:message key="label.rate.city" /></th>
																<c:forEach var="i" begin="1" end="4">
																	<th align="center"><span
																		id="rateSpecialStartWeightSlab${rateProdCat.value}${i}"></span> - <span
																		id="rateSpecialEndWeightSlab${rateProdCat.value}${i}"></span></th>
																</c:forEach>
																<th align="center"><span
																	id="rateSpecialAddWeightSlab${rateProdCat.value}"> - </span></th>

															</tr>
														</thead>

													</table>




												</div>
											<c:if test="${rateProdCat.label != 'CO'}">
														<div class="formTable">
															<table border="0" cellpadding="0" cellspacing="5"
																width="100%">

																<tr>
																	<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
																			key="label.rate.minChrgWt" /></td>
																	<td width="31%"><html:select
																			styleId="rateMinChargWt${rateProdCat.value}"
																			property="proposedRatesTO.rateMinChargWt"
																			styleClass="selectBox width145">
																			
																		</html:select></td>
																</tr>
															</table>
														</div>
														</c:if>
												
										</div>
										<div class="button_containerform">
											<html:button property="clearBtn${rateProdCat.value}" styleClass="btnintform"
												styleId="btnProposedRatesClear${rateProdCat.value}" onclick="clearRateFields(${loop1.count-1});">
												<bean:message key="button.clear" />
											</html:button>
											<html:button property="Save${rateProdCat.value}"
												styleClass="btnintform"
												styleId="btnProposedRatesSave${rateProdCat.value}"
												onclick="saveRateQuotation('save');" title="Save">
												<bean:message key="button.label.save" />
											</html:button>
										</div>
										<!-- </div> -->
									</div>
									<!-- tab 5 ending-->

								</c:forEach>



								<!-- tab 6 ending-->





								<!-- tab 7 ends -->
							</div>

						</div>
						<!-- tab 2nd ends-->

							
						<div id="tabs-3">
							<!-- <p>-->
							<!--<div id="demo"> -->
							<div class="formTable">
								<table border="0" cellpadding="1" cellspacing="3" width="100%">
									<tr>
										<td width="20%" class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.fuelSurchargeChk"
												styleId="FuelSurchargesChk" /> <bean:message
												key="label.Rate.FuelSurcharges" /></td>
										<td width="14%" class="lable1"><html:text
												property="rateQuotationFixedChargesTO.fuelSurcharge"
												styleId="FuelSurcharges" value="" onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','FuelSurchargesChk');"
												styleClass="txtbox width50"></html:text> <bean:message
												key="label.Rate.Percent"/></td>

										<td width="16%" class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.otherChargesChk"
												styleId="OtherChargesChk"/> <bean:message
												key="label.Rate.OtherCharges" /></td>
										<%-- <td class="lable1" width="13%"><html:text
												property="rateQuotationFixedChargesTO.otherCharges"
												styleId="OtherCharges" onkeypress="return tabKeyForOtherCharge(event,RiskSurcharges)" value="" styleClass="txtbox width110"></html:text>
											<bean:message key="label.Rate.Rupees" /></td> --%>
										<td class="lable1" width="13%"><html:text
												property="rateQuotationFixedChargesTO.otherCharges"
												styleId="OtherCharges" onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','OtherChargesChk');" 
												value="" styleClass="txtbox width50" maxlength="7"></html:text>
											<bean:message key="label.Rate.Rupees" /></td>

										<td rowspan="6" width="29%" valign="top">
                      	 					<table border="0" cellpadding="0" cellspacing="2" width="100%">                          
                          						<tr>
													<td class="lable" width="58%" align="left"><html:checkbox
															property="rateQuotationFixedChargesTO.serviceTaxChk"
														 styleId="ServiceTaxChk" />
                         								<bean:message key="label.Rate.ServiceTax"/></td>
													<td class="lable1" ><html:text
															property="rateQuotationFixedChargesTO.serviceTax"
															styleId="ServiceTax" styleClass="txtbox width50"
															size="25" value="" maxlength="7"></html:text> <bean:message
															key="label.Rate.Percent" /></td>
                          						</tr>
                             					<tr>
													<td class="lable" align="left"><html:checkbox
															property="rateQuotationFixedChargesTO.eduChargesChk"
															styleId="EducationCessChk"
															 /> <bean:message
															key="label.Rate.EducationCess" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.eduCharges"
															styleId="EducationCess" styleClass="txtbox width50"
															size="25" value="" maxlength="7"></html:text> <bean:message
															key="label.Rate.Percent" /></td>
                            </tr>
                               <tr>
													<td class="lable" align="left"><html:checkbox
															property="rateQuotationFixedChargesTO.higherEduChargesChk"
															styleId="HigherEducationCessChk"
															 /> <bean:message
															key="label.Rate.HigherEducationCess" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.higherEduCharges"
															styleId="HigherEducationCess" styleClass="txtbox width50"
															size="25" value="" maxlength="7"></html:text> <bean:message
															key="label.Rate.Percent" /></td>
                          </tr>
                          <!-- <tr>
                          
                          OR
                          </tr> -->
                       
                          
                          <tr>
													<td class="lable" align="left"><html:checkbox
															property="rateQuotationFixedChargesTO.stateTaxChk"
															styleId="StateTaxChk" />
                       
                      								    <bean:message key="label.Rate.StateTax"/></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.stateTax"
															styleId="StateTax" styleClass="txtbox width50" 
															value="" maxlength="7"></html:text> <bean:message
															key="label.Rate.Percent" /></td>
                      
                          
									</tr>
									<tr>
													<td class="lable" align="left"><html:checkbox
															property="rateQuotationFixedChargesTO.surchargeOnSTChk"
															styleId="SurchargeOnSTChk"
														 /> <bean:message
															key="label.Rate.SurchargeOnST" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.surchargeOnST"
															styleId="SurchargeOnST" styleClass="txtbox width50"
															value="" maxlength="7"></html:text> <bean:message
															key="label.Rate.Percent" /></td>
                          
                          </tr>
                          </table>
                          </td>
                             </tr>
                       

              
        
                      <tr>
										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.riskSurchrgeChk"
												styleId="RiskSurchargesChk" /> <bean:message
												key="label.Rate.RiskSurcharges" /></td>
										<td class="lable1"><html:select
												property="rateQuotationFixedChargesTO.riskSurchrge"
												styleClass="selectBox width130" value=""
												styleId="RiskSurcharges">
												<c:forEach var="insuredBy" items="${insuredByTOs}"
													varStatus="loop">

													<option
														value="${insuredBy.insuredByCode}~${insuredBy.percentile}">
														<c:out
															value="${insuredBy.insuredByDesc}-${insuredBy.percentile}" />
													</option>

												</c:forEach>
											</html:select></td>
											<c:forEach var="insuredBy" items="${insuredByTOs}"
													varStatus="loop">

													<input type="hidden" id="${insuredBy.insuredByCode}" value="${insuredBy.insuredByCode}~${insuredBy.percentile}"/>
														

												</c:forEach>
										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.octroiBourneByChk"
												styleId="OctroiBornByChk"/> <bean:message
												key="label.Rate.OctroiBornBy" /></td>
										<td><span class="lable1"> <html:select
													property="rateQuotationFixedChargesTO.octroiBourneBy"
													styleClass="selectBox width120" styleId="OctroiBornBy"
													onchange="setOctroiChargeValue();" value="">
													<c:forEach var="OctroiBourneBy"
														items="${OctroiBourneByList}" varStatus="loop">

														<option value="${OctroiBourneBy.stdTypeCode}">
															<c:out value="${OctroiBourneBy.description}" />
														</option>

													</c:forEach>

												</html:select>
										</span></td>

									</tr>
									<tr>
										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.parcelChargesChk"
												styleId="ParcelHandlingChargesChk" /> <bean:message
												key="label.Rate.ParcelHandlingCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.parcelCharges"
												styleId="ParcelHandlingCharges" styleClass="txtbox width50"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','ParcelHandlingChargesChk');" maxlength="7"></html:text> <bean:message key="label.Rate.Rupees" /></td>


										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.octroiServiceChargesChk"
												styleId="OctroiServiceChargesChk"/> <bean:message
												key="label.Rate.OctroiServiceCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.octroiServiceCharges"
												styleId="OctroiServiceCharges" styleClass="txtbox width50"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','OctroiServiceChargesChk');" maxlength="7"></html:text> <bean:message key="label.Rate.Percent" /></td>



											
									</tr>
									<tr>
										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.airportChargesChk"
												styleId="AirportHandlingChargesChk" /> <bean:message
												key="label.Rate.AirportHandlingCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.airportCharges"
												styleId="AirportHandlingCharges"
												styleClass="txtbox width50" value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','AirportHandlingChargesChk');" maxlength="7"></html:text> <bean:message
												key="label.Rate.Percent" /></td>

										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.toPayChargesChk"
												styleId="ToPayChargeChk" /> <bean:message
												key="label.Rate.ToPayCharge" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.toPayCharges"
												styleId="ToPayCharge" styleClass="txtbox width50" 
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','ToPayChargeChk');" maxlength="7"></html:text> <bean:message key="label.Rate.Rupees" /></td>

									</tr>
									<tr>

										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.documentChargesChk"
												styleId="DocumentHandlingChargesChk" /> <bean:message
												key="label.Rate.DocumentHandlingCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.documentCharges"
												styleId="DocumentHandlingCharges"
												styleClass="txtbox width50" value=""  onkeypress="return validateFCKey(event);"
												 onblur="validateFixedChargeVal(this,'','DocumentHandlingChargesChk');" maxlength="7"></html:text>
											<bean:message key="label.Rate.Rupees" /></td>

										<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.lcChargesChk"
												styleId="LCChargesChk" /> <bean:message
												key="label.Rate.LCCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.lcCharges"
												styleId="LCCharges" styleClass="txtbox width50" 
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','LCChargesChk');" maxlength="7"></html:text> <bean:message key="label.Rate.Rupees" /></td>

									</tr>
									<tr>
									<td class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.vwDenominatorChk"
												disabled="true" styleId="vwDenominatorChk"/> <bean:message
												key="label.Rate.vwDenominator" /></td>
										<td class="lable1"><html:text
												property="to.vwDenominator"
												styleId="vwDenominator" 
												styleClass="txtbox width50" disabled="true" maxlength="7"></html:text> 
												<bean:message
												key="label.Rate.cms" /></td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>		
									</tr>
									<tr>
										<td  class="lable" align="left"><html:checkbox
												property="rateQuotationFixedChargesTO.codChargesChk"
												styleId="codChargesChk" onchange="loadCodChargeBoxes();"/> <bean:message
												key="label.Rate.codCharges" /></td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
									</tr>
									</table>	
									
									<div>
										<table cellpadding="0" cellspacing="0" border="0" class="display" id="example5" width="100%">
											<thead>
												<tr>
													<th align="center"><bean:message key="label.Rate.DeclaredValue" /></th>
													<th align="center"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"><bean:message key="label.Rate.COD%" /></th>
													<th align="center"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"><bean:message key="label.Rate.FCorCODGreater" /></th>
												</tr>
											</thead>
											<tbody>
											<c:set var="codCntValue" value="1"/>
												<logic:present name="codChargeTO" scope="request">												 
													<c:forEach var="codChargeList" items="${codChargeTO}"
														varStatus="loop">
														<tr class="gradeA">
															<td align="center">
															<span id="decalredVal${loop.count}"> ${codChargeList.minimumDeclaredValLabel}
															<c:if test="${codChargeList.maximumDeclaredValLabel != null}">
																		 - ${codChargeList.maximumDeclaredValLabel}
																		 </c:if>
																		</span></td>
															<td class="center"><input type="text"
																class="txtbox width90" id="fixedCharges${loop.count}"
																name="rateQuotationFixedChargesTO.fixedChargesEco" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','codChargesChk');"/></td>
															<td class="center"><input type="text"
																class="txtbox width90" size="11"
																id="codPercent${loop.count}"
																name="rateQuotationFixedChargesTO.codPercent" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','codChargesChk');"/></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="FixedChargesRadio${loop.count}" value="f" /></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="fcOrCODRadio${loop.count}" value="c" />
																<input type="hidden" id="codChargeId${loop.count}"
																value="${codChargeList.codChargeId }"
																name="rateQuotationFixedChargesTO.codChargeId" /></td>
															 <c:set var="codCntValue" value="${loop.count}"/>
														</tr>
														
													</c:forEach>													
												</logic:present>
											</tbody>
										</table>
										<br /> <input type="hidden" id="codChargeTO"
											value="${codChargeTO}" />
											<input type="hidden" id="codRowsCnt"
																value="${codCntValue}"
																name="codRowsCnt" />
										<%--  <input type="hidden"  id="codChargeLength" value="${codChargeTO}" /> --%>
									</div>
								
								<!-- Button -->
								<div class="button_containerform">

										<html:button property="clearFiexdChrgsBtn" styleClass="btnintform"
										styleId="clearFiexdChrgsBtn" onclick="loadDefaultChargesValue();">
										<bean:message key="button.clear" />
									</html:button>
									<html:button property="saveFiexdChrgsBtn" styleClass="btnintform"
										styleId="saveFiexdChrgsBtn" onclick="saveOrUpdateFixedCharges();">
										<bean:message key="button.save" />
									</html:button>
								</div>
								<!-- Button ends -->
							</div>

							<!-- </p>-->
						</div>
						<div id="tabs-4">



							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="21%" class="lable"><html:checkbox
											property="rateQuotationRTOChargesTO.rtoChargesApplicable"
											onclick="enableFields(this);"
											styleId="RTOChargesApplicableChk" /> <bean:message
											key="label.Rate.RTOChargesApplicable" /></td>


									<td width="18%" class="lable"><html:checkbox
											property="rateQuotationRTOChargesTO.sameAsSlabRate"
											onclick="enableDiscountFields(this);"
											styleId="sameAsSlabRateChk" disabled="true" /> <bean:message
											key="label.Rate.IfYes" /></td>
									<td width="18%" class="lable1"><select name="select13"
										class="selectBox " id="sameAsSlabRate" disabled="disabled"><option value="0">Same as Tariff No.</option>
									</select></td>
									<td width="15%" class="lable"><bean:message
											key="label.Rate.Discount" /></td>
									<td width="21%" class="lable1"><html:text
											property="rateQuotationRTOChargesTO.discountOnSlab"
											styleId="Discount" value="" styleClass="txtbox width50"
											disabled="true"  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','RTOChargesApplicableChk');" maxlength="5"></html:text> <bean:message
											key="label.Rate.Percent" /></td>


								</tr>
							</table>
							<table border="0" cellpadding="1" cellspacing="4" width="100%">			
									<tr>

										<td class="lable"><bean:message
												key="label.Rate.excecutiveRemarks" /></td>
										<td class="lable1"><html:textarea property="to.excecutiveRemarks"
												styleId="excecutiveRemarks" styleClass="txtbox width170" onkeypress="return setMaxLengthForTextArea(event,this,250);"></html:textarea></td>
										<td class="lable"><bean:message
												key="label.Rate.approversRemarks" /></td>
										<td class="lable1"><html:textarea property="to.approversRemarks"
												styleId="approversRemarks" styleClass="txtbox width170" onkeypress="return setMaxLengthForTextArea(event,this,250);"></html:textarea></td>



										<!--    <td class="lable" colspan="2"></td>  -->
									</tr>
								</table>
							
							<div class="button_containerform">
							<html:button property="approveBtn" styleClass="btnintform"
									styleId="approveBtn" 
									onclick="approveQuotation('approve');">
								<bean:message key="button.approve" />
								</html:button>
								<html:button property="rejctBtn" styleClass="btnintform"
									styleId="rejectBtn" 
									onclick="approveQuotation('reject');">
								<bean:message key="button.reject" />
								</html:button>
								<html:button property="copyBtn" styleClass="btnintform"
										styleId="copyBtn" onclick="copyQuotation();">
										<bean:message key="button.copyQuotation" />
									</html:button>
									<html:button property="createBtn" styleClass="btnintform"
										styleId="createBtn" disabled="true"
										onclick="createContract();">
										 <bean:message key="button.rate.contract" />
									</html:button>
									<html:button property="submitBtn" styleClass="btnintform"
										styleId="submitBtn" onclick="submitQuotation();">
										<bean:message key="button.submit" />
									</html:button>
									<html:button property="printBtn" styleClass="btnintform"
										styleId="printBtn" onclick="printQuotation();">
										<bean:message key="button.print" />
									</html:button>
								<html:button property="clearBtn" styleClass="btnintform"
									styleId="clearRTOBtn" onclick="clearRTOChargesFields();">
									<bean:message key="button.clear" />
								</html:button>
								<html:button property="saveBtn" styleClass="btnintform"
									styleId="saveRTOBtn"
									onclick="saveOrUpdateRTOCharges();">
									<bean:message key="button.save" />
								</html:button>
								
							</div>
							<!-- Button ends -->
						</div>
					</div>
					<!-- Grid /-->
				</div>
				<!------------------------------------main ----------------body ends------------------------------->



				<html:hidden styleId="rateProdCatId"
					property="proposedRatesTO.rateProdCatId" />

				<html:hidden styleId="rateVobSlabsId"
					property="proposedRatesTO.rateVobSlabsId" />
				<html:hidden styleId="wtArrStr" property="proposedRatesTO.wtArrStr" />
				<html:hidden styleId="secArrStr"
					property="proposedRatesTO.secArrStr" />

				<html:hidden styleId="rateOriginSectorId"
					property="proposedRatesTO.rateOriginSectorId" />
				<html:hidden styleId="rateMinChargWtId"
					property="proposedRatesTO.rateMinChargWtId" value="" />
				<html:hidden styleId="rateQuotAddWeight"
					property="proposedRatesTO.rateQuotAddWeight" />
				<html:hidden styleId="rateQuotId"
					property="proposedRatesTO.rateQuotationId" value="" />
				<html:hidden styleId="rateQuotationProdCatHeaderId"
					property="proposedRatesTO.rateQuotationProdCatHeaderId" />
				<html:hidden styleId="rateProdCatCode"
					property="proposedRatesTO.rateProdCatCode" value="" />
				<html:hidden styleId="rateAddWtSlabId"
					property="proposedRatesTO.rateAddWtSlabId" />
				<html:hidden styleId="regionCode"
					property="proposedRatesTO.regionCode" />
				<html:hidden styleId="rateQuotationType"
					property="to.rateQuotationType" />
				<html:hidden styleId="proposedRatesCO"
					property="to.proposedRatesCO" />
				<html:hidden styleId="proposedRatesD"
					property="to.proposedRatesD" />
				<html:hidden styleId="proposedRatesP"
					property="to.proposedRatesP" />
				<html:hidden styleId="proposedRatesB"
					property="to.proposedRatesB" />
				<html:hidden styleId="custCatId"
					property="proposedRatesTO.custCatId" />					
				<html:hidden styleId="rowNo"
					property="proposedRatesTO.rowNo"/>
				<html:hidden styleId="errorMsg"
					property="to.errorMsg"/>						
				<c:forEach var="i" begin="1" end="4">
					<html:hidden styleId="rateQuotStartWeight${i}"
						property="proposedRatesTO.rateQuotStartWeight" value="" />
				</c:forEach>
				<c:forEach var="i" begin="1" end="7">
					<html:hidden styleId="rateQuotCOStartWeight${i}"
						property="proposedRatesTO.rateQuotCOStartWeight" value="" />
				</c:forEach>
				<c:forEach var="i" begin="1" end="2">
					<html:hidden styleId="rateCOAddWtSlabId${i}"
						property="proposedRatesTO.rateCOAddWtSlabId" value="" />
				</c:forEach>
				<input type="hidden" id="flatRate" name="proposedRatesTO.flatRate" value="P"/>
				<input type="hidden" id="rateConfiguredType" name="proposedRatesTO.rateConfiguredType"/>
				<input type="hidden" id="contractPrintUrl" name="contractPrintUrl" value="${contractPrintUrl}"/>  
			</div>
			<!-- main content ends -->
			<!-- footer -->
			<div class="button_containerform">
				<html:button property="cancelBtn" styleClass="btnintform"
					styleId="cancelBtn" onclick="cancelNormalQuotationInfo();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
			<!-- footer ends -->

		</div>
		<!--wraper ends-->





	</html:form>
</body>


