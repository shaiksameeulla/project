<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/complaints/serviceRequestForService.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/tracking/consignmentTracking.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/complaints/serviceRequestCommon.js"></script>

        <script language="javascript" type="text/javascript">
        function isValidToReload(){
        	var url="${reloadurl}";
        	//alert(url);
        	if(!isNull(url)){
        		globalFormSubmit(url,'serviceRequestForServiceForm');
        	}
        }
		    function openPopup(){
				
				window.open('popup_ChildCN.html','_blank','width=200,height=100,directories=0,titlebar=0,toolbar=0,location=0,status=0, menubar=0,scrollbars=no,resizable=no,width=400,height=350');
			}
		</script>

        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
        <script>  
		$(function() {    $( "#tabsnested" ).tabs();  });  
        </script>
        <!-- Tabs ends /-->

        </head>
        <body>
<!--wraper-->
<div id="wraper">
<html:form action="/serviceRequestForService.do" styleId="serviceRequestForServiceForm" >
<div class="clear"></div>
<!-- main content -->
<div id="maincontent">
<div class="mainbody">
<div class="formbox">
          <h1><bean:message key="label.complaints.service.header"/></h1>
          <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.common.FieldsareMandatory"/></div>
        </div>



           <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
              <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;Service Type :</td>
              <td width="17%" >
				<html:select styleId="serviceType" property="to.serviceType" styleClass="selectBox width130"  onchange="populateEmployeesByServiceType(this);applicabilityByServiceType(this);dynamicMandatorySymbolForServiceType(this);getServiceRealtedQueryTypeByServiceType(this)" onkeydown="return enterKeyNav('number' ,event.keyCode)">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="serviceType" scope="request">
                          	<html:optionsCollection name="serviceType" label="value" value="key"/>
                          	</logic:present>
				</html:select>
      		
      		</td>
              <td width="17%" class="lable"><sup id="mandatoryNumber" class="star" style="display:none">*</sup><bean:message key="label.complaints.service.number"/> </td>
             <td><html:text property="to.bookingNo" styleClass="txtbox width130" styleId="number"    maxlength="12" onkeypress="return OnlyAlphabetsAndNos(event);" onkeydown="return enterKeyNav('callerName' ,event.keyCode)" />
             <input id="trackBtn" name="trackBtn" type="button" value="Track" class="button"  title="Track a CN/B.Ref number"  onclick="consignmentTrackingForComplaint()"/></td>
            </tr>
                      <tr>
                <td width="12%" class="lable">Reference Number:</td>
                <td><html:text property="to.serviceRequestNo" styleClass="txtbox width130" styleId="serviceRequestNo"  readonly="true" tabindex="-1"/></td>
                <td width="13%" class="lable"><sup id="mandatorySrvRefNumber" class="star" style="display:none">*</sup>Linked with :<html:checkbox  property="to.isLinkedWith" value="Y" styleId="isLinkedWith" onclick="enableLinkedReferenceNo(this)" tabindex="-1"/> </td>
               <td> <html:text property="to.linkedServiceReqNo" styleClass="txtbox width130" styleId="linkedServiceReqNo"  readonly="true" maxlength="15" tabindex="-1" onkeydown="return enterKeyNav('callerName' ,event.keyCode)"/>
               
               </td>
              </tr>
             <tr>
								<td colspan="4" class="lable1" align="center" id="consignment">Consignment
									tracking information for <strong>CN No. <label id="cnNum" ></label></strong>
								</td>
							</tr>
							<tr >
								<td colspan="4" class="lable1" align="center" id="consignmentStatus">Status : <strong><label id="cnStatus" ></label></strong></td>
							</tr>
							<tr >
								<td colspan="4" class="lable1" align="center" id="errorMsg" style="color:red; margin-top:5px;" ></td>
							</tr>
							
                    </table>
                    <fieldset>
            <legend>&nbsp;CALLERS DETAILS
            </legend>
        <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
                      <td width="12%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.name"/> </td>
                       <td><html:text property="to.callerName" styleClass="txtbox width130" styleId="callerName" onkeypress="return onlyAlphabetForComplaints(event);" maxlength="30"  onkeydown="return enterKeyNav('callerPhone' ,event.keyCode)" onchange="validateNameValidation(this)"/></td> 
                      <td width="16%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.phone"/> </td>
                       <td><html:text property="to.callerPhone" styleClass="txtbox width130"  size="13" styleId="callerPhone" onkeypress="return onlyNumeric(event);" onchange="validateSrvReqPhone(this);" maxlength="11" onkeydown="return enterKeyNav('callerEmail' ,event.keyCode)"/></td> 
                      <td class="lable"><sup id="mandatoryCallerEmail" class="mandatoryf" style="display:none">*</sup><bean:message key="label.complaints.service.email"/> </td>
                       <td><html:text property="to.callerEmail" styleClass="txtbox width130" styleId="callerEmail" maxlength="50" onchange="validateSrvReqEmail(this);" onkeydown="return enterKeyNav('serviceRelated' ,event.keyCode)" /></td> 
                    </tr>
                    </table>
       </fieldset>
                    <div id="tabs">
<ul>
 <li><a href="#tabs-1" >Detailed Tracking</a></li>
 		<li><a href="#tabs-2">Booking Information</a></li>
		<li><a href="#tabs-3">Child CN</a></li>
          <li><a href="#tabs-4">Service Query</a></li>
    </ul>
       
       <div id="tabs-1">
       
       <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="detailsTable">
						    	<thead>									
									<tr>
										<th width="2%" align="center">Sr.No.</th>
										<th width="9%"  align="center">Manifest Type</th>
										<th width="12%" align="center">Date &amp; Time</th>
										<th width="45%" align="center">Consignment Path</th>
									</tr>									
								</thead>
							</table>
        
        </div>
        <div id="tabs-2">
        <div class="columnuni">
								<div class="columnleft">
									<fieldset>
										<legend class="lable1">
											&nbsp;
											<bean:message key="label.ConsignmentTracking.ConsignorDetail" />
											&nbsp;
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="19%" class="lable">
												<bean:message key="label.ConsignmentTracking.Name" /></td>
												<td width="26%"><input type="text" name="firstName"
													id="firstName" class="txtbox width110" tabindex="-1"
													 readonly="readonly" /></td>
												<td width="28%" class="lable"><bean:message
														key="label.ConsignmentTracking.City" /></td>
												<td width="27%"><input type="text" name="cityName"
													id="cityName" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.Address" /></td>
												<td width="26%"><input type="text" name="address"
													id="address" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="28%" class="lable"><bean:message
														key="label.ConsignmentTracking.State" /></td>
												<td width="27%"><input type="text" name="state"
													id="state" class="txtbox width110" tabindex="-1" 
													readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.Pincode" /></td>
												<td width="26%"><input type="text" name="pincode"
													id="pincodeTr" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="28%" class="lable"><bean:message
														key="label.ConsignmentTracking.PhoneNo" /></td>
												<td width="27%"><input type="text" name="phone"
													id="phone" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
													</tr>
													<tr>
													<td width="28%" class="lable"><bean:message
														key="label.ConsignmentTracking.mobileNo" /></td>
												<td width="27%" colspan="2"><input type="text" name="mobile"
													id="mobile" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
													</tr>
										</table>
									</fieldset>
								</div>
								<div class="columnleft1">
									<fieldset>
										<legend>
											&nbsp;
											<bean:message key="label.ConsignmentTracking.ConsigneeDetail" />
											&nbsp;
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="20%" class="lable">
												<bean:message key="label.ConsignmentTracking.Name" /></td>
												<td width="30%"><input type="text" name="firstname"
													id="firstname" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="21%" class="lable"><bean:message
														key="label.ConsignmentTracking.City" /></td>
												<td width="29%"><input type="text" name="city"
													id="city" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="20%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.Address" /></td>
												<td width="30%"><input type="text" name="adress"
													id="adress" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
												<td width="21%" class="lable"><bean:message
														key="label.ConsignmentTracking.State" /></td>
												<td width="29%"><input type="text" name="State"
													id="State" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="20%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.Pincode" /></td>
												<td width="30%"><input type="text" name="pincodes"
													id="pincodes" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="21%" class="lable"><bean:message
														key="label.ConsignmentTracking.PhoneNo" /></td>
												<td width="29%"><input type="text" name="phones"
													id="phones" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
											</tr>
											<tr>
											<td width="28%" class="lable"><bean:message
														key="label.ConsignmentTracking.mobileNo" /></td>
												<td width="27%"><input type="text" name="mobiles"
													id="mobiles" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
											</tr>
										</table>
									</fieldset>
								</div>
							</div>
							<div class="columnuni">
								<div class="columnleft">
									<fieldset>
										<legend>
											&nbsp;
											<bean:message key="label.ConsignmentTracking.BookingInfo" />
											&nbsp;
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="21%" class="lable">
												<bean:message key="label.ConsignmentTracking.originOffice" /></td>
												<td width="25%"><input type="text" name="bookingoffice"
													id="bookingoffice" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="30%" class="lable"><bean:message
														key="label.ConsignmentTracking.destinationOffice" /></td>
												<td width="26%"><input type="text" name="destoffice"
													id="destoffice" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.bookedBy" /></td>
												<td width="25%"><input type="text" name="bookname"
													id="bookname" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="30%" class="lable"><bean:message
														key="label.ConsignmentTracking.consignmentType" /></td>
												<td width="26%"><input type="text" name="consgtype"
													id="consgtype" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.customerCode" /></td>
												<td width="25%"><input type="text" name="custname"
													id="custname" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="30%" class="lable"><bean:message
														key="label.ConsignmentTracking.bookedDate" /></td>
												<td width="26%"><input type="text" name="bookdate"
													id="bookdate" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.Address" /></td>
												<td width="25%"><input type="text" name="addr"
													id="addr" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
												<td width="30%" class="lable"><bean:message
														key="label.ConsignmentTracking.finalWeight" /></td>
												<td width="26%"><input type="text" name="finalwt"
													id="finalwt" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="19%" class="lable">&nbsp;<bean:message
														key="label.ConsignmentTracking.mobileNo" /></td>
												<td width="25%"><input type="text" name="mobile"
													id="bkgInfoMobile" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
												<td width="30%" class="lable"><bean:message
														key="label.ConsignmentTracking.pickupDate" /></td>
												<td width="26%"><input type="text" name="pickdate"
													id="pickdate" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
										</table>
									</fieldset>
								</div>
								<div class="columnleft1">
									<fieldset>
										<legend>
											&nbsp;
											<bean:message key="label.ConsignmentTracking.Paperwork" />
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="25%" class="lable"><bean:message
														key="label.ConsignmentTracking.paperworkNo" /></td>
												<td width="24%"><input type="text" name="paperworkno"
													id="paperworkno" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="27%" class="lable"><bean:message
														key="label.ConsignmentTracking.height" /></td>
												<td width="24%"><input type="text" name="height"
													id="height" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="25%" class="lable"><bean:message
														key="label.ConsignmentTracking.paperworkType" /></td>
												<td width="24%"><input type="text" name="paperworktype"
													id="paperworktype" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="27%" class="lable"><bean:message
														key="label.ConsignmentTracking.breadth" /></td>
												<td width="24%"><input type="text" name="breadth"
													id="breadth" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="25%" class="lable"><bean:message
														key="label.ConsignmentTracking.actualWeight" /></td>
												<td width="24%"><input type="text" name="actualwt"
													id="actualwt" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="27%" class="lable"><bean:message
														key="label.ConsignmentTracking.insured" /></td>
												<td width="24%"><input type="text" name="insured"
													id="insured" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="25%" class="lable"><bean:message
														key="label.ConsignmentTracking.volWeight" /></td>
												<td width="24%"><input type="text" name="volweight"
													id="volweight" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
												<td width="27%" class="lable"><bean:message
														key="label.ConsignmentTracking.declareValue" /></td>
												<td width="24%"><input type="text" name="declaredValue"
													id="declareValue" class="txtbox width110" tabindex="-1"
													size="11" readonly="readonly" /></td>
											</tr>
											<tr>
												<td width="25%" class="lable"><bean:message
														key="label.ConsignmentTracking.length" /></td>
												<td width="24%"><input type="text" name="length"
													id="length" class="txtbox width110" tabindex="-1" size="11"
													readonly="readonly" /></td>
												<td width="27%" class="lable">&nbsp;</td>
												<td width="24%">&nbsp;</td>
											</tr>
											<tr><td colspan="4"><br></td></tr>
										</table>
									</fieldset>
								</div>
							</div>
							<div class="clear"></div> 	
        </div>
        <div id="tabs-3">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">							
								<tr>
									<td class="lable1" align="center"><bean:message key="label.ConsignmentTracking.pieces" /><strong><label id="nopeices" ></label></strong></td>
								</tr>
								<tr>
									<td>									
									 <table cellpadding="0" cellspacing="0" border="0" class="display" id="childCN" width="90%">									
											<thead>
												<tr>
													<th>Child Consignments</th>
												</tr>
											</thead>
										</table>
									</td>
								</tr>
							</table>
        
        </div>
       
     
      
<div id="tabs-4">
                   
                <div class="columnuni">
<div class="columnleftcaller">
 
        
       <fieldset>
            <legend>&nbsp;Create Service Request
            </legend>
            <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
                     
                     
                     <td class="lable" width="18%"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.complaints.service.serviceRelated"/> </td>
                      <td width="18%">
                      <html:select styleId="serviceRelated" property="to.serviceRelated" styleClass="selectBox width130"  onchange="validateServiceQuery(this);dynamicMandatorySymbolForServiceRelated(this);populateAllBranchesOrCities(this)" onkeydown="return enterKeyForServiceQuery(this ,event.keyCode)">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="serviceQueryTypeMap" scope="request">
                          	<html:optionsCollection name="serviceQueryTypeMap" label="value" value="key"/>
                          	</logic:present>
						
					</html:select>
                      </td>
                    
                      <td width="18%" class="lable"><sup id="mandatoryCompCtgry" class="mandatoryf" style="display:none">*</sup>&nbsp;Complaint Category :</td>
                      <td width="20%">
                      <html:select styleId="complaintCategory" property="to.complaintCategory" styleClass="selectBox width130">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
					<logic:present name="complaintTypeMap" scope="request">
                          	<html:optionsCollection name="complaintTypeMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>
                      </td>
                       <td width="18%" class="lable"><sup id="mandatoryCustomerType" class="mandatoryf" style="display:none">*</sup><bean:message key="label.complaints.service.customerType"/></td>
                      <td width="14%">
                      <html:select styleId="custType" property="to.customerType" styleClass="selectBox width130">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						
						<logic:present name="complaintCustomerTypeMap" scope="request">
                          	<html:optionsCollection name="complaintCustomerTypeMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>
                      </td>
                      
                    </tr>
            <tr>
              <td class="lable"><sup id="mandatoryOriginCity" class="mandatoryf" style="display:none">*</sup><label id="originName"><bean:message key="label.complaints.service.origin"/></label></td>
              <td>
              <html:select styleId="originCity" property="to.originCityId" styleClass="selectBox width130" onchange="populatePincodesByCity(this)">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="cityDtlsList" scope="request">
                          	<html:optionsCollection name="cityDtlsList" label="value" value="key"/>
                          	</logic:present>
					</html:select>
              
              
              </td> 
              <td class="lable"><sup id="mandatoryProduct" class="mandatoryf" style="display:none">*</sup>&nbsp;<bean:message key="label.complaints.service.product"/></td>
                      <td>
                      <html:select styleId="product" property="to.productId" styleClass="selectBox width130">
						
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="productMap" scope="request">
                          	<html:optionsCollection name="productMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>
                      </td>
              <td class="lable"><sup id="mandatoryPincode" class="star" style="display:none">*</sup>&nbsp;<bean:message key="label.complaints.service.pincode"/> </td>
              <td> <html:select styleId="pincode" property="to.pincodeId" styleClass="selectBox width130">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="pincodeMap" scope="request">
                          	<html:optionsCollection name="pincodeMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>
              
              </td> 
              </tr>
            <tr>
                      <td class="lable"><sup id="mandatoryWeight" class="mandatoryf" style="display:none">*</sup>&nbsp;<bean:message key="label.complaints.service.weight"/> </td>
                      <td ><html:text property="to.weightKgs" styleClass="txtbox width70" styleId="weightKgs"  maxlength="4" onkeypress="return onlyNumeric(event);" readonly="true"/> 
                      
                      .
                      <html:text property="to.weightGrm" styleClass="txtbox width30" styleId="weightGrm"  maxlength="3" readonly="true" onkeypress="return onlyNumeric(event);"/>
                      </td>
                      <td  class="lable"><sup id="mandatoryConsgType" class="mandatoryf" style="display:none">*</sup>&nbsp;<bean:message key="label.complaints.service.type"/></td>
                      <td>
                      <html:select styleId="consgTypes" property="to.consignmentType" styleClass="selectBox width130">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="consgTypesMap" scope="request">
                          	<html:optionsCollection name="consgTypesMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>
                      </td>
                      <td class="lable"><sup id="mandatoryIndustryType" class="mandatoryf" style="display:none">*</sup><bean:message key="label.complaints.industrytype"/></td>
                      <td >
                        <html:select styleId="industryType" property="to.industryType" styleClass="selectBox width130">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="industryCategory" scope="request">
                          	<html:optionsCollection name="industryCategory" label="value" value="key"/>
                          	</logic:present>
					</html:select>
                       </td>
					
              </tr>
           
           
            <tr>
            
            <td  class="lable"><sup id="mandatoryEmployee" class="mandatoryf" style="display:none">*</sup>&nbsp;<bean:message key="label.complaints.service.employeeName"/></td>
            <td >
			
			 <html:select styleId="employeeId" property="to.employeeId" styleClass="selectBox width130"  onchange="populateEmpEmailIdAndMobile(this);" onkeydown="return enterKeyNav('status' ,event.keyCode)">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
							<logic:present name="employeeMap" scope="request">
                          	<html:optionsCollection name="employeeMap" label="value" value="key"/>
                          	</logic:present>
			 </html:select>
					
              <td class="lable"><sup id="mandatoryEmpMail" class="mandatoryf" style="display:none">*</sup>&nbsp; Employee <bean:message key="label.complaints.service.email"/></td>
              <td>   <html:text property="to.empEmailId" styleClass="txtbox width130" styleId="empEmailId" readonly="true" tabindex="-1"/></td> 
              <td class="lable"><sup id="mandatoryEmpPhone" class="mandatoryf" style="display:none">*</sup>&nbsp;Employee <bean:message key="label.complaints.service.mobileNo"/></td>
             	<td>   <html:text property="to.empPhone" styleClass="txtbox width130" styleId="empPhone" readonly="true" tabindex="-1"/></td> 
             
            </tr>
            <tr>
                      <td class="lable">&nbsp;</td>
                      <td>
                       <%-- <html:button property="enquiryBtn" styleClass="btnintformbigdis" styleId="enquiryBtn" onclick="serviceEnquiry()" disabled="true" tabindex="-1"> 
					   Enquiry
					    </html:button> --%>
                      </td>
                      <td  class="lable"><bean:message key="label.complaints.service.result"/> :</td>
                      <td><html:textarea  property="to.serviceResult" cols="20" rows="3" styleId="result"  readonly="true" tabindex="-1" style="width: 151px; height: 76px; resize:none">Text here</html:textarea></td>
                      <td class="lable"><sup class="mandatoryf">*</sup>&nbsp;Source Of Query :</td>
                      <td><html:select styleId="sourceOfQuery" property="to.sourceOfQuery" styleClass="selectBox width130">
						<logic:present name="sourceOfQuery" scope="request">
                          	<html:optionsCollection name="sourceOfQuery" label="value" value="key"/>
                          	</logic:present>
					</html:select></td>
                    </tr>
            <tr>
                      <td  class="lable"> <span class="mandatoryf">*</span>&nbsp;<bean:message key="label.complaints.service.status"/></td>
                      <td >
	                 <html:select styleId="status" property="to.status" styleClass="selectBox width130" onchange="dynamicMandatorySymbolForStatus(this)" onkeydown="return enterKeyNav('remark' ,event.keyCode)">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<logic:present name="complaintsStatusMap" scope="request">
                          	<html:optionsCollection name="complaintsStatusMap" label="value" value="key"/>
                          	</logic:present>
					</html:select>

					
                    </td>
                      <td class="lable"><span class="mandatoryf">*</span> &nbsp;<bean:message key="label.complaints.service.remark"/></td>
                      <td>
                      <html:textarea  property="to.remark" cols="20" rows="3" styleId="remark" style="width: 151px; height: 76px; resize:none" onkeydown="return enterKeyNav('save' ,event.keyCode)">Text here</html:textarea>
                      </td>
                       <td class="lable" valign="top"><bean:message key="label.complaints.consigcomm"/></td>
           
              <td class="lable1">
              <html:checkbox  property="to.smsToConsignor" value="Y" styleId="smsToConsignor" /><bean:message key="label.complaints.consignorSMS"/><br />
              <html:checkbox   property="to.smsToConsignee" value="Y" styleId="smsToConsignee" /><bean:message key="label.complaints.consigneeSMS"/> <br />
             <html:checkbox   property="to.emailToCaller" value="Y" styleId="emailToCaller" onclick="enableMandatoryMark(this)"/><bean:message key="label.complaints.emailToCaller"/>   <br/>
             </td>
                   
             </tr>
          </table>
                </fieldset>
       <html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode"/>
	   <html:hidden property="to.loginOfficeId" styleId="loginOfficeId"/>
	   <html:hidden property="to.logginUserId" styleId="logginUserId"/>
	   <html:hidden property="to.serviceRequestId" styleId="serviceRequestId"/>
	   <html:hidden property="to.consignmentTypeDox" styleId="consignmentTypeDox"/>
	   
	   
       
       <html:hidden property="to.complaintStatusResolved" styleId="complaintStatusResolved"/>
       <html:hidden property="to.complaintStatusBackline" styleId="complaintStatusBackline"/>
       <html:hidden property="to.complaintStatusFollowup" styleId="complaintStatusFollowup"/>
       
       <html:hidden property="to.serviceRequestConsgQueryTypeComplaint" styleId="queryTypeComplaint"/>
        <html:hidden property="to.serviceRequestConsgQueryTypePodStatus" styleId="queryTypePodStatus"/>
       <%-- <html:hidden property="to.serviceRequestConsgQueryTypeCriticalComplaint" styleId="queryTypeCriticalComplaint"/>
       <html:hidden property="to.serviceRequestConsgQueryTypeEscalationComplaint" styleId="queryTypeEscalationComplaint"/>
       <html:hidden property="to.serviceRequestConsgQueryTypeFinancialComplaint" styleId="queryTypeFinancialComplaint"/> --%>
       
       <html:hidden property="to.serviceRequestServiceQueryTypeTariffEnquiry" styleId="queryTypeTariffEnquiry"/>
       <html:hidden property="to.serviceRequestServiceQueryTypeServiceCheck" styleId="queryTypeServiceCheck"/>
        <html:hidden property="to.serviceRequestServiceQueryTypeGeneralInfo" styleId="queryTypeGeneralInfo"/>
         <html:hidden property="to.serviceRequestServiceQueryTypeLeadCall" styleId="queryTypeLeadCall"/>
       <html:hidden property="to.serviceRequestServiceQueryTypePickupCall" styleId="queryTypePickupCall"/>
        <html:hidden property="to.serviceRequestServiceQueryTypePaperwork" styleId="queryTypePaperwork"/>
         <html:hidden property="to.serviceRequestServiceQueryTypeEmotionalBond" styleId="queryTypeEmotionalBond"/>
        
       
          <html:hidden property="to.backlineExecutiveRole" styleId="backlineExecutiveRole" />
           <html:hidden property="to.salesCoordinatorRole" styleId="salesCoordinatorRole" />
            <html:hidden property="to.serviceRequestTypeForService" styleId="serviceRequestTypeForService" />
             <html:hidden property="to.serviceRequestTypeForConsg" styleId="serviceRequestTypeForConsg" />
             <html:hidden property="to.serviceRequestTypeForBref" styleId="serviceRequestTypeForBref" />
             
             <html:hidden property="to.complaintSourceOfQueryPhone" styleId="sourceOfQueryPhone" />
           
           
         
   </div>
    
  <!-- </div> -->
  </div>
            <!-- Button -->
            <div class="button_containerform">
             <html:button property="enquiryBtn" styleClass="btnintformbigdis" styleId="enquiryBtn" onclick="serviceEnquiry()" disabled="true" tabindex="-1"> 
					   Enquiry
					    </html:button>
            <html:button property="Save" styleClass="btnintform" styleId="save" onclick="saveOrCloseServiceRequest();" > 
					    <bean:message key="button.save"/>
					    </html:button>
                        <html:button property="Edit" styleClass="btnintform" styleId="Edit" onclick="enditForServiceRequest();" > 
					    <bean:message key="button.update"/>
					    </html:button>
					      <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" >
						<bean:message key="button.cancel" />
						</html:button>
						<c:if test="${not empty complaintTransfer}">
						 <html:button property="transfer" styleClass="btnintform" styleId="cancelBtn" onclick="openComplaintTransferScreen('${serviceRequestForServiceForm.to.serviceRequestNo}');" >
						<bean:message key="button.compaint.transfer" /> 
						</html:button>
                     </c:if>
                      
            </div>
            <!-- Button ends --> 
<!---tab 1 ends-->
        	
     </div>
        
            </div>
   
 		 </div> 
 		 </div>
  </html:form>
        </div>
</body>
</html>
