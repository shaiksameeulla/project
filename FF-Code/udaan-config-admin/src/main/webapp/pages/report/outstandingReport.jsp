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
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/billing/reBilling.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" charset="utf-8" src="login/js/jquery.autocomplete.js"></script>
<script type="text/javascript" charset="utf-8">
		</script>
		<script type="text/javascript" charset="utf-8" src="js/report/outstandingReport.js"></script>
		<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />		
<!-- DataGrids /-->

<style type="text/css">

p
{
color:red;
text-align:center;
font-size: 12px;
border-style: solid;
border-width: 1px;

}

</style>

<script type="text/javascript" charset="utf-8">

function isDataSaved(){
	
	var saved = new Boolean();
	 saved = <%= request.getAttribute("isSaved") %>;
	if(saved){
		alert('Data saved successfully');
	}
}

function loadCustomerName(){
	  jQuery('input#customerName').flushCache();
	  <c:forEach var="custTO" items="${customerList}" varStatus="rstatus"> 
	   data['${rstatus.index}'] = "${custTO.businessName}";
	   custCodeArr['${rstatus.index}'] = "${custTO.customerCode}";
	  </c:forEach> 
	
	   
	jQuery("#customerName").autocomplete(data);
}

</script>

<!-- styleId="outstandingReportForm"  -->

</head>
<body onLoad="body_onload();">
	<form name="outstandingReportForm1"  id="outstandingReportForm"
		method="post"
		action="/udaan-config-admin/outstandingReport.do?submitName=saveData">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Outstanding Report</h1>

						<div class="mandatoryMsgf">
							<span class="mandatoryf">* </span> Fields are Mandatory
							<!--Fields are mandatory -->
						</div>
						<div id="errorMessageBlock" align="center" style="display: none;"> <p id="errorMessage" align="left"></p></div>
						
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="3" width="70%"
								align="center">
								<tr><td class="lable"> <sup class="star">*</sup>&nbsp;Report For</td>
								<td colspan="2"> <select name="to.reportFor" style="height: 20px; width: 200px;" id="reportForID" class="selectBox width130">
										<option value=""> Please Select</option>
										<c:forEach var="item" items="${reportList}">
										<option value="${item.reportName}"> ${item.reportDescription}</option>
										</c:forEach>
								</select>  </td>
								
								</tr>
								
								
								<tr >
									<td class="lable"><sup class="star">*</sup>&nbsp;Bill Upto</td>
									<td><input type="text" name="to.billUpto" style="height: 20px" value="" 
										id="billUptoID" class="txtbox width130" readonly="readonly"> <a href="#"
											onclick="javascript:show_calendar('billUptoID', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a></td>
									<td class="lable"><sup class="star">*</sup>&nbsp;Payment Upto</td>
									<td><input type="text" name="to.paymentUpto" style="height: 20px" value=""
										id="paymentUptoID" class="txtbox width100" readonly="readonly"> <a
											href="#"
											onclick="javascript:show_calendar('paymentUptoID', this.value)"
											title="Select Date"><img src="images/icon_calendar.gif"
												alt="Search" width="16" height="16" border="0"
												class="imgsearch" /></a></td>
								</tr>

								<tr>
									<td class="lable"><sup class="star">*</sup>&nbsp;Outstanding For</td>
									<td><select name="to.outStandingFor" style="height: 20px"
										onchange="checkForDisableField();" id="outstandingFor"
										class="selectBox width130"><option value="">Please
												Select</option>
											<option value="P">Profit Center</option>
											<option value="C">Customer</option></select></td>
									<td colspan="2"></td>

								</tr>

								<tr>
									<td class="lable"> Customer Name</td>
									<td><input type="text" name="to.customerName" style="height: 20px; width: 130px" value=""
										onblur="setCustomerCode();" id="customerName"
										class="txtbox width100"></td>
									<td class="lable">Customer Code</td>

									<td><input type="text" name="to.customerCode" style="height: 20px" value=""
										id="customerCode" class="txtbox width100"></td>
								</tr>
								<tr>
									<td class="lable">CC Email</td>
									<td><input type="text" name="to.ccemail" style="height: 20px; width: 200px" value="" id=""
										class="txtbox width100"></td>
									<td colspan="2"></td>
								</tr>

								<tr>
									<td colspan="4" align="right">
										<div class="button_containerform" align="right">

											<input type="submit" value="Save" style="height: 20px;"
												onclick="return validate();" class="btnintform"/> <input type="button"
												name="Submit" value="Cancel" onclick="resetField();"
												id="btnSave" style="height: 20px;" class="btnintform"/>
										</div>
									</td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>