<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/tracking/consignmentTracking.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript" charset="utf-8">
var screen = '<%= request.getParameter("screen") %>';
var userType = '${userType}';

$(function() {$( "#tabs" ).tabs();});
function fnTableStruct(tableId){
	var oTable2 = $('#'+tableId).dataTable({
		"sScrollY" : "300",
		"sScrollX" : "80%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"bDestroy" : true,
		"bRetrieve" : true,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable2, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
}
$(document).ready(function() {
	detailTracking();
	if (userType != 'C') {
		fnTableStruct('childCN');
	}
});

function detailTracking() {
	var tableId = null;
	if (userType == 'C') {
		//Customer Tracking
		tableId = 'detailsTable4Customer';
		document.getElementById("detailsTable4Customer").style.display = "table";
		document.getElementById("detailsTable").style.display = "none";
	} else {
		tableId = 'detailsTable';
	}		
	fnTableStruct(tableId);	
}
</script>
<!-- Tabs ends /-->
</head>

<%-- <%@include file="trackingHeader.jsp"%> --%>

<body onload="crmTracking('<%= request.getParameter("consgNumber") %>', 'CN'); setDefaultValues('<%= request.getParameter("consignmentNo") %>', '<%= request.getParameter("type") %>')" onkeypress="ESCclose(event);">

	<!--wraper-->
	<div id="wraper">
		<html:form action="/consignmentTrackingHeader.do" method="post" styleId="consignmentTrackingForm">
			<input type="hidden" id="childRows" />
			<input type="hidden" id="detailRows" />

			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
			
			<div class="formbox">
        	<h1><bean:message key="label.tracking.consignmenttracking.header"/></h1>
        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      	  	</div>
			<div class="mainbody">
				<!-- top header form begins -->
				<div class="formTable" id="trackingHead">
					<table border="0" cellpadding="0" cellspacing="5" width="100%">
						<tr>
				            <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
				            <td width="26%" >
								<html:select property="to.type"  styleId="typeName"  styleClass="selectBox width180" onkeypress="return callEnterKey(event,document.getElementById('consgNumber'));">
				                  	<c:forEach var="type" items="${typeNameTo}"  varStatus="loop">
					                  <html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>
					                </c:forEach>
				               </html:select>
				            </td>
				            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.consignmentorrefno"/></td>
				            <td width="18%"><html:text property="to.consgNumber" styleId="consgNumber" styleClass="txtbox width145" maxlength="15" 
				             onkeypress="return callEnterKey(event,document.getElementById('trackBtn'));" onblur="isValidConsgNum(this);" />
				            	&nbsp;<html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackconsignment();">
									<bean:message key="button.label.track"/></html:button>
							</td>
		         		</tr>                           
						<tr>
							<td colspan="4" class="lable1" align="center">Consignment tracking information for <strong>CN No. <label id="cnNum" ></label></strong></td>
						</tr>
						<tr style="height: 22px;">
							<td colspan="4" id="status" class="lable1" align="center" style="font-style: italic;color:#fff;"><strong><label id="cnStatus"></label></strong></td>
						</tr>
						<tr>
							<td colspan="4" class="lable1" align="center" id="errorMsg" style="color:red; margin-top:5px;" ></td>
						</tr>
					</table>
				</div><!-- top header form ends -->
				<!-- tab begins -->
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Booking Information</a></li>
						<c:if test="${userType !='C'}">
							<li onclick="fnTableStruct('childCN');"><a href="#tabs-2">Child CN</a></li>
						</c:if>							
						<li onclick="detailTracking();"><a href="#tabs-3" >Detailed Tracking</a></li>
					</ul>
					<div id="tabs-1">
						<div class="columnuni">
							<div class="columnleft">
								<fieldset>
									<legend class="lable1">&nbsp;<bean:message key="label.ConsignmentTracking.ConsignorDetail" />&nbsp;</legend>
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
												id="pincode" class="txtbox width110" tabindex="-1"
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
									<legend>&nbsp;<bean:message key="label.ConsignmentTracking.ConsigneeDetail" />&nbsp;</legend>
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
									<legend>&nbsp;<bean:message key="label.ConsignmentTracking.BookingInfo" />&nbsp;</legend>
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
									<legend>&nbsp;<bean:message key="label.ConsignmentTracking.Paperwork" /></legend>
									<table border="0" cellpadding="0" cellspacing="2" width="100%">
										<tr>
											<td width="25%" class="lable"><bean:message
													key="label.ConsignmentTracking.paperworkNo" /></td>
											<td width="24%"><input type="text" name="paperworkno"
												id="paperworkno" class="txtbox width110" tabindex="-1"
												size="11" readonly="readonly"/></td>
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
											<td width="27%" class="lable"><bean:message
													key="label.ConsignmentTracking.content" /></td>
											<td width="24%"><textarea id="cnContent" rows="1" cols="10" wrap="ON" readonly="readonly" tabindex="-1"></textarea></td>
										</tr>
										<tr><td colspan="4"><br></td></tr>
									</table>
								</fieldset>
							</div>
						</div>
						<div class="clear"></div> 	
					</div>
					<c:if test="${userType !='C'}">
					<div id="tabs-2">						
						 <table cellpadding="0" cellspacing="0" border="0" class="display" id="childCN" width="90%">
							<tr>
								<td class="lable1" align="center"><bean:message key="label.ConsignmentTracking.pieces" /><strong><label id="nopeices" ></label></strong></td>
							</tr>									
							<thead><tr><th>Child Consignments</th></tr></thead>
						</table>
					</div>
					</c:if>	
					<div id="tabs-3">
						<table width="90%" cellpadding="0" cellspacing="0" border="0" class="display" id="detailsTable"  >
					    	<thead>									
								<tr>
									<th width="2%">Sr.No.</th>
									<th width="9%">Manifest Type</th>
									<th width="12%">Date &amp; Time</th>
									<th width="45%">Consignment Path</th>
								</tr>									
							</thead>
						</table>
						<table width="90%" cellpadding="0" cellspacing="0" border="0" class="display" id="detailsTable4Customer"  style="display:none">
                               <thead>                                                              
                                  <tr>                                          
                                     <th>Process</th>
                                     <th>Office</th>
                                     <th>Date &amp; Time</th>
                                  </tr>	                                                         
                               </thead>
                           </table>							
					</div>			
				</div>
			</div>
			</div>

         <div class="button_containerform">
            <input type="text" name="mailId" id="mailId" class="txtbox width120"/>
            <html:button property="to.sendMail" styleClass="btnintgrid" styleId="sendMail" onclick="fnSendMailOrSMS('MAIL');">Send Mail</html:button>     
            <input type="text" name="mobileNo" id="mobileNo" class="txtbox width100"/>
            <html:button property="to.sendSMS" styleClass="btnintgrid" styleId="sendSMS" onclick="fnSendMailOrSMS('SMS');">Send SMS</html:button> 
			<html:button property="Clear" styleClass="btnintform" styleId="clearBtn" onclick="clearScreen('clear');">
				<bean:message key="button.clear"/></html:button>
				<c:if test="${cmp=='Y'}">
				<html:button property="Close" styleClass="btnintform" styleId="Close" onclick="closeWindow()">
				<bean:message key="button.close"/></html:button>
				</c:if>
		</div>
		</html:form>
	</div>
	<!--wraper ends-->

</body>
</html>