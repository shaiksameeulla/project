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
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/expense/expenseEntry.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/expense/expenseConsignment.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/mecCommon.js"></script>
<script type="text/javascript" charset="utf-8">

var STATUS_OPENED = '${STATUS_OPENED}';
var STATUS_SUBMITTED = '${STATUS_SUBMITTED}';
var STATUS_VALIDATED = '${STATUS_VALIDATED}';

var EX_FOR_OFFICE = '${EX_FOR_OFFICE}';
var EX_FOR_EMP = '${EX_FOR_EMP}';
var EX_FOR_CN = '${EX_FOR_CN}';

var TX_CODE_EX = '${TX_CODE_EX}';//EX
var EX_MODE_CASH = '${EX_MODE_CASH}';
var EX_MODE_CHQ = '${EX_MODE_CHQ}';

/* No. of rows during search */
var NO_OF_ROWS = '${NO_OF_ROWS}';

/* Consignment details for search */
var cnDtls = '${cnDtls}';

var IS_VALIDATE_YES = '${IS_VALIDATE_YES}';
var CR_NT_YES = '${CR_NT_YES}';
var IS_CR_NT = '${expenseEntryForm.to.isCrNote}';
var YES = '${YES}';

var TODAY_DATE = '${expenseEntryForm.to.documentDate}';

$(document).ready( function () {
	if(IS_CR_NT!=CR_NT_YES){
		var oTable = $('#expenseEntryTable').dataTable({
			"sScrollY": "180",
			"sScrollX": "100%",
			"sScrollXInner":"140%",
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
	}
	var URL = '${expenseUrl}';
	if(!isNull(URL)){
		submitWithoutPrompt(URL);
	}
	expenseCNStartup();
});
</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->                      
<div id="wraper"> 
<html:form styleId="expenseEntryForm">
	<!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
        		<h1><bean:message key="label.expense.cn.title"/></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mec.fieldsAreMandatory"/></div>
      		</div>
            <div class="formTable">
				<table border="0" cellpadding="0" cellspacing="5" width="100%">
                	<tr>
	                    <td width="18%" class="lable"><sup class="star">*</sup>&nbsp; <bean:message key="label.mec.transactionNumber"/>:</td>
	                    <td width="24%" >
	                    	<html:text property="to.txNumber" styleId="txNumber" styleClass="txtbox width130" maxlength="15"  onkeypress="return callEnterKey(event,document.getElementById('expenseFor'));"/>
	                    	<c:if test="${expenseEntryForm.to.expenseStatus!=STATUS_SUBMITTED}">
	                    		<html:button property="Search" styleClass="btnintgrid" styleId="Search" alt="Search" onclick="searchExpenseDtls();">
									<bean:message key="button.search"/></html:button>
							</c:if>
	                    </td>
	                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.postingDate"/>:</td>
	                    <td width="15%"><html:text property="to.postingDate" styleId="postingDate" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
	                    <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.expenseFor"/>:</td>
	                    <td width="17%">
	                    	<html:select property="to.expenseFor" styleId="expenseFor" styleClass="selectBox width130" onchange="getExpenseScreen(this);" onkeypress="return callEnterKey(event,document.getElementById('documentDate'));">
	                      		<logic:present name="expenseForList" scope="request">					
									<html:optionsCollection property="to.expenseForList" label="label" value="value" />
								</logic:present>
	                    	</html:select>
	                    </td>
                  	</tr>
                  	<tr>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.documentDate" />:</td>
	                    <td width="20%">
	                    	<html:text property="to.documentDate" styleId="documentDate" styleClass="txtbox width100" maxlength="10" readonly="true" onfocus="validateDocDate(this);" onkeypress="return callEnterKey(event,document.getElementById('expenseType'));"/>
							<c:if test="${expenseEntryForm.to.expenseStatus==STATUS_OPENED || (expenseEntryForm.to.isValidateScreen==IS_VALIDATE_YES && (expenseEntryForm.to.expenseMode==EX_MODE_CHQ || expenseEntryForm.to.isCrNote==CR_NT_YES))}">
								<a href="#" onclick="setDocumentDate(this);" title="Select Date" id="docDt" ><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
							</c:if>
	                    </td>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.typeOfExpense"/>:</td>
	                    <td width="15%">
	                    	<html:select property="to.expenseType" styleId="expenseType" styleClass="selectBox width130" onchange="checkGridEmpty(this,expenseFor.value);" onkeypress="return callEnterKey(event,document.getElementById('finalAmount'));">
	                      		<html:option value=""><bean:message key="label.option.select" /></html:option>
		              			<logic:present name="expenseTypeList" scope="request">
		              				<html:optionsCollection property="to.expenseTypeList" label="label" value="value" />
		              			</logic:present>
	                    	</html:select>
	                    </td>
	                    <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.modeOfExpense"/>:</td>
	                    <td width="17%">
	                    	<html:select property="to.expenseMode" styleId="expenseMode" styleClass="selectBox width130" onchange="validateForExpMode(this.value);">
	                      		<logic:present name="expenseModeList" scope="request">	
					  				<html:optionsCollection property="to.expenseModeList" label="label" value="value"/>
					  			</logic:present>
	                    	</html:select>
	                    </td>
                  	</tr>
                  	<tr>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.chequeNumber"/>:</td>
	                    <td width="15%"><html:text property="to.chequeNumber" styleId="chequeNumber" styleClass="txtbox width130" disabled="true" onkeypress="return onlyNumberAndEnterKeyNav(event,this,'chequeDate');" maxlength="6" onblur="validateChqNumber(this);"/></td>
	                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.chequeDate"/>:</td>
	                    <td width="17%">
	                    	<html:text property="to.chequeDate" styleId="chequeDate" styleClass="txtbox width130" disabled="true" readonly="true" onkeypress="return callEnterKey(event,document.getElementById('bank'));"/>
	                    	<c:if test="${expenseEntryForm.to.expenseStatus==STATUS_OPENED || expenseEntryForm.to.isValidateScreen==IS_VALIDATE_YES}">
	            				<a href="#" onclick="javascript:show_calendar('chequeDate', this.value)" title="Select Date" id="chequeDt"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
	            			</c:if>
	                    </td>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.bankName"/>:</td>
		            	<td width="20%">
		            		<html:select property="to.bank" styleId="bank" styleClass="selectBox width130" disabled="true" onkeypress="return callEnterKey(event,document.getElementById('bankBranchName'));">
	                      		<html:option value=""><bean:message key="label.option.select" /></html:option>
		              			<logic:present name="bankList" scope="request">	
		              				<html:optionsCollection property="to.bankList" label="label" value="value" />
		              			</logic:present>
	                    	</html:select>
		            	</td>
		            </tr>
		            <tr>
	                    <td class="lable"><sup class="star">*</sup><bean:message key="label.mec.bankBranchName" />:</td>
                    	<td width="15%"><html:text property="to.bankBranchName" styleId="bankBranchName" styleClass="txtbox width130" maxlength="50" disabled="true" onkeypress="return callEnterKeyAlphaNum(event,document.getElementById('finalAmount'));"/></td>
                    	<td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount" />:</td>
                    	<td width="17%" colspan="3"><html:text property="to.finalAmount" styleId="finalAmount" styleClass="txtbox width130" onkeypress="return onlyNumberAndEnterKeyNav(event,this,'consgNos1');" onblur="setCNAmtFormatZero(this);" maxlength="10" /></td>
                  	</tr>
               	</table>
      		</div>
      		<c:if test="${expenseEntryForm.to.isCrNote!=CR_NT_YES}">
            <div id="demo">
        		<div class="title">
                	<div class="title2"><bean:message key="label.expense.dtls"/></div>
				</div>
        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="expenseEntryTable" width="100%">
                	<thead>
            			<tr>
							<th width="4%" align="center" ><bean:message key="label.expense.srNo" /></th>
							<th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.expense.cn" /></th>
							<th width="7%"><sup class="star">*</sup><bean:message key="label.mec.amount" /></th>
							<th width="7%"><bean:message key="label.expense.cn.serviceCharge" /></th>
							<th width="7%"><bean:message key="label.expense.cn.serviceTax" /></th>
							<th width="9%"><bean:message key="label.expense.cn.eduCess" /></th>
							<th width="12%"><bean:message key="label.expense.cn.higherEduCess" /></th>
							<th width="7%"><bean:message key="label.expense.cn.otherCharge" /></th>
							<th width="7%"><sup class="star">*</sup><bean:message key="label.expense.cn.Total" /></th>
							<th width="11%"><sup class="star">*</sup><bean:message key="label.expense.remark" /></th>
                    	</tr>
          			</thead>
					<tbody>
     				</tbody>
				</table>
   			</div>
   			</c:if>
			<!-- Hidden Fields Starts -->
			<jsp:include page="expenseCommon.jsp" />
			<!-- Hidden Fields Ends -->
		</div>
	</div>
    <!-- Button -->
	<div class="button_containerform">
		<c:choose>
			<c:when test="${expenseEntryForm.to.isValidateScreen==IS_VALIDATE_YES}">
				<c:if test="${expenseEntryForm.to.expenseMode!=EX_MODE_CHQ && expenseEntryForm.to.isCrNote!=CR_NT_YES}">
					<html:button property="CreditNote" styleId="CreditNote" style="width: 125px;" styleClass="btnintform" onclick="validateExpense('creditNote');">
						<bean:message key="button.creditNote" /></html:button>
				</c:if>
				<html:button property="Validate" styleId="Validate" styleClass="btnintform" onclick="validateExpense('validate');">
					<bean:message key="button.validate" /></html:button>
			</c:when>
			<c:otherwise>
			   <%-- <html:button property="Save" styleId="Save" styleClass="btnintform" onclick="saveOrUpdateExpenseDtls('save');">
					<bean:message key="button.save" /></html:button> --%>
				<html:button property="Submit" styleId="Submit" styleClass="btnintform" onclick="saveOrUpdateExpenseDtls('submit');">
					<bean:message key="button.submit" /></html:button>
				<html:button property="Cancel" styleId="Cancel" styleClass="btnintform" onclick="clearScreen('clear');">
					<bean:message key="button.cancel" /></html:button>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
	</html:form>
</div>
<!--wraper ends-->
</body>
</html>
