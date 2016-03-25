<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/expense/expenseEntry.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/expense/validateExpense.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/mecCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8">

var selectOption = "<bean:message key='label.option.select' />";
var officeId ="${officeId}";

$(document).ready( function () {
	var oTable = $('#validateExpense').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	});
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});
	validateExpStartup();
});
</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<div id="wraper"> 
	<html:form styleId="validateExpenseForm"> 
	<!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
	        	<h1><bean:message key="label.expense.validate.title" /></h1>
	        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.mec.fieldsAreMandatory" /></div>
      		</div>
	        <div class="formTable">
	        	<table border="0" cellpadding="0" cellspacing="5" width="100%">
	            	<tr>
	                	<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.fromDt" />:</td>
	                    <td width="16%" >
	                    	<html:text property="to.fromDate" styleId="fromDate" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" />
	                    	<%-- <c:if test="${expenseEntryForm.to.expenseStatus==STATUS_OPENED}"> --%>
							<a href="#" onclick="javascript:show_calendar('fromDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
							<%-- </c:if> --%>
                    	</td>
	                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.toDt" />:</td>
	                    <td width="15%">
	                    	<html:text property="to.toDate" styleId="toDate" styleClass="txtbox width100" maxlength="10" readonly="true" tabindex="-1" />
	                    	<%-- <c:if test="${expenseEntryForm.to.expenseStatus==STATUS_OPENED}"> --%>
							<a href="#" onclick="javascript:show_calendar('toDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
							<%-- </c:if> --%>
                    	</td>
	                    <td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.station" />:</td>
	                    <td width="23%">
	                    	<html:select property="to.stationId" styleId="stationId" styleClass="selectBox width130" onchange="getAllOffices(this);" onkeypress="return callEnterKey(event,document.getElementById('officeId'));">
		                    	<html:option value=""><bean:message key="label.option.select" /></html:option>
		              			<logic:present name="stationList" scope="request">
		              				<html:optionsCollection property="to.stationList" label="label" value="value" />
		              			</logic:present>
	                    	</html:select>
                    	</td>
					</tr>
	                <tr>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.office" />:</td>
	                    <td width="16%">
	                    	<html:select property="to.officeId" styleId="officeId" styleClass="selectBox width130" onkeypress="return callEnterKey(event,document.getElementById('status'));">
		                      	<html:option value=""><bean:message key="label.option.select" /></html:option>
		              			<logic:present name="officeList" scope="request">
		              				<html:optionsCollection property="to.officeList" label="label" value="value" />
		              			</logic:present>
	                    	</html:select>
                    	</td>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.status" />:</td>
	                    <td width="15%">
	                    	<html:select property="to.status" styleId="status" styleClass="selectBox width130" onkeypress="return callEnterKey(event,document.getElementById('txNumber'));">
		                    	<logic:present name="statusList" scope="request">
		              				<html:optionsCollection property="to.statusList" label="label" value="value" />
		              			</logic:present>
	                    	</html:select>
                    	</td>
	                    <td width="18%" class="lable"><!-- <sup class="star">*</sup> --><bean:message key="label.mec.transactionNumber" />:</td>
	                    <td width="23%">
	                    	<html:text property="to.txNumber" styleId="txNumber" styleClass="txtbox width130" maxlength="16" onblur="validateTxNoForValidate(this);"/>
	                    	<html:button property="Search" styleId="Search" styleClass="button" onclick="searchValidateExpDtls();">
								<bean:message key="button.search" /></html:button>
	                    </td>
					</tr>
				</table>
			</div>
	        <div id="demo">
	        	<div class="title">
	           		<div class="title2"><bean:message key="label.expense.dtls" /></div>
	            </div>
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="validateExpense" width="100%">
	            	<thead>
	            		<tr>
		                    <th width="10%" align="center" ><sup class="star">*</sup><bean:message key="label.mec.date" /></th>
		                    <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.mec.transactionNumber" /></th>
		                    <th width="11%"><sup class="star">*</sup><bean:message key="label.expense.expenseFor" /></th>
		                    <th width="14%"><sup class="star">*</sup><bean:message key="label.expense.typeOfExpense" /></th>
		                    <th width="14%"><sup class="star">*</sup><bean:message key="label.mec.amount" /></th>
		                    <th width="14%"><sup class="star">*</sup><bean:message key="label.mec.validate.status" /></th>
						</tr>
	          		</thead>
					<tbody>
						<c:forEach var="validateExpDtls" items="${validateExpenseForm.to.validateExpDtlsTOs}" varStatus="loop">
		            		<tr class="gradeA">
			                    <td class="center"><c:out value="${validateExpDtls.txDate}" />
			                    	<html:hidden property="to.rowTxDate" styleId="rowTxDate${loop.count}" value="${validateExpDtls.txDate}" />
			                    </td>
			                    <td class="center">
			                    	<c:choose>
			                    		<c:when test="${validateExpDtls.expenseStatus eq STATUS_VALIDATED}">
			                    			<c:out value="${validateExpDtls.txNumber}" />
			                    		</c:when>
			                    		<c:otherwise>
			                    			<a href="#" onclick="validatePopup('${loop.count}');"><c:out value="${validateExpDtls.txNumber}" /></a>
			                    		</c:otherwise>
			                    	</c:choose>
			                    	<html:hidden property="to.rowTxNumber" styleId="rowTxNumber${loop.count}" value="${validateExpDtls.txNumber}"/>
			                    </td>
			                    <td class="center"><c:out value="${validateExpDtls.expenseForDesc}" />
			                    	<html:hidden property="to.rowExpenseForDesc" styleId="rowExpenseForDesc${loop.count}" value="${validateExpDtls.expenseForDesc}" />
			                    	<html:hidden property="to.rowExpenseFor" styleId="rowExpenseFor${loop.count}" value="${validateExpDtls.expenseFor}" />
			                    </td>
			                    <td>
			                    	<html:text property="to.rowExpenseType" styleId="rowExpenseType${loop.count}" styleClass="txtbox width130" readonly="true" value="${validateExpDtls.expenseType}" />
			                    	<html:hidden property="to.rowExpenseTypeId" styleId="rowExpenseTypeId${loop.count}" value="${validateExpDtls.expenseTypeId}" />
			                    </td>
			                    <td class="center">
			                    	<html:text property="to.rowAmount" styleId="rowAmount${loop.count}" styleClass="txtbox width100" readonly="true" value="${validateExpDtls.amount}" />
			                    </td>
			                    <td class="center"><c:out value="${validateExpDtls.status}" />
			                    	<html:hidden property="to.rowStatus" styleId="rowStatus${loop.count}" value="${validateExpDtls.status}" />
			                    	<html:hidden property="to.rowExpenseStatus" styleId="rowExpenseStatus${loop.count}" value="${validateExpDtls.expenseStatus}" />
			                    	<html:hidden property="to.rowExpenseId" styleId="rowExpenseId${loop.count}" value="${validateExpDtls.expenseId}" />
			                    	<html:hidden property="to.rowExpenseOfficeId" styleId="rowExpenseOfficeId${loop.count}" value="${validateExpDtls.expenseOfficeId}" />
			                    </td>
							</tr>
						</c:forEach>
	          		</tbody>
				</table>
	     	</div>
	        <!-- Hidden Fields Starts -->
			<html:hidden property="to.expenseId" styleId="expenseId" />
			<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
			<html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode" />
			<html:hidden property="to.regionId" styleId="regionId" />
			<html:hidden property="to.createdBy" styleId="createdBy" />
			<html:hidden property="to.updatedBy" styleId="updatedBy" />
			<!-- Hidden Fields Ends -->
		</div>
	</div>
	<!-- main content ends --> 
	</html:form>
</div>
<!--wraper ends-->
</body>
</html>
