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
<!-- <script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script> -->
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>

<script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript" ></script>
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="js/mec/mecCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/collection/billCollection.js"></script>

<script type="text/javascript" charset="utf-8">
var BC_MODE = '${BC_MODE}';
var COLL_AGAINST_BILL='${COLL_AGAINST_BILL}';
var STATUS_SAVED = "O";
var STATUS_SUBMITTED = "S";
var STATUS_VALIDATED = "V";

var COLL_AGAINST_ON_ACCOUNT = "O";

var custIDArr = new Array();
var custCodeArr = new Array();
var data = new Array();
var custCodeName = new Array();
function loadCustomerName(){
	  jQuery('input#custName').flushCache();
	  <c:forEach var="custTO" items="${customerList}" varStatus="rstatus"> 
	   data['${rstatus.index}'] = "${custTO.businessName}";
	   custIDArr['${rstatus.index}'] = "${custTO.customerId}";
	   custCodeArr['${rstatus.index}'] = "${custTO.customerCode}";
	   custCodeName['${rstatus.index}'] =  "${custTO.businessName}"+" "+"(${custTO.customerCode})";
	  </c:forEach> 
	jQuery("#custName").autocomplete(custCodeName);
}

function renderSearchResult(){
	alert('renderSearchResult:');
	if(jsonResult != null && jsonResult != "") {
		jQuery("#custCode").val("<c:out value='${jsonResult.custTO.customerCode}'/>");
	}
}
function getReasons() {
	var text = "<option value=''>--- SELECT ---</option>";
	<c:forEach var="reason" items="${reasonsList}" varStatus="status">  
		text = text + "<option value=${reason.reasonId}>${reason.reasonCode} - ${reason.reasonName}</option>";
	</c:forEach>
	return text;		   	
}
var billOptions="";
var reasonOptions = getReasons();
</script>
<style>
.rightAlignment {
	text-align: right !important;
}
</style>
<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects()">
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
           <html:form  action="/billCollection.do" method="post" styleId="billCollectionForm"> 
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.billcollection.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.mec.fieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
              <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.transactionNumber"/>:</td>
                    <td width="21%" ><html:text property="to.txnNo" styleId="txnNo"  styleClass="txtbox width130" maxlength="15" readonly="readonly" value="" onkeypress = "return callEnterKey(event, document.getElementById('Find'));"/>
                    <html:button property="Search" styleId="Find" styleClass="btnintgrid" onclick="searchBillCollectionDetails();"><bean:message key="button.collection.label.search"/></html:button></td>
                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.postingDate"/>:</td>
                    <td width="11%"><html:text property="to.collectionDate" styleId="collectionDate" styleClass="txtbox width130" readonly="true" value="${todaysDate}" onkeypress = "return callEnterKey(event, document.getElementById('custName'));" />&nbsp;</td>
                    <td width="11%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.collection.customer.name"/>:</td>
                    <td width="18%"> <html:text property="to.custName" styleId="custName" styleClass="txtbox width190" onblur="getCustCode();" maxlength="50" onkeypress = "return callEnterKey(event, document.getElementById('collectionModeId'));" value=""/></td>
                  </tr>
                  <tr>
                    <td class="lable"><bean:message key="label.collection.customer.code"/></td>
                    <td ><html:text property="to.custCode" styleId="custCode" styleClass="txtbox width130" readonly="true" tabindex="-1" value="" onkeypress = "return callEnterKey(event, document.getElementById('collectionModeId'));" /></td>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.collection.modeofcollection"/>:</td>
                    <td >
                    <html:select property="to.collectionModeId" styleId="collectionModeId"  styleClass="selectBox width130" onchange ="checkCollectionMode(this);" onkeypress = "return fnEnterKeyNav(event,this);" value="">
                       <html:optionsCollection property="to.collectionModeList" label="label" value="value"/>
                    </html:select></td>
                    <td  class="lable"><bean:message key="label.mec.chequeNumber"/>:</td>
                    <td ><html:text property="to.chqNo" styleId="chqNo" styleClass="txtbox width130" readonly="true" maxlength="6" onkeypress="return onlyNumberAndEnterKeyNav(event,'chqDate');" onblur="validateChqNumber(this);" value=""/></td>
                  </tr>
                  <tr>
                    <td class="lable"><bean:message key="label.mec.chequeDate"/>:</td>
                    <td ><html:text property="to.chqDate" styleId="chqDate" styleClass="txtbox width130" maxlength="10" readonly="true" onkeypress = "return callEnterKey(event, document.getElementById('bankName'));" value=""/>
                   <a href="javascript:show_calendar('chqDate', this.value)" title="Select Date" id="calendar">
                    <img id="calendarImg" src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a></td>
                    <td class="lable"><bean:message key="label.mec.bankName"/>:</td>
                    <td ><html:text property="to.bankName" styleId="bankName"  styleClass="txtbox width130" maxlength="30" readonly="true" onkeypress = "return callEnterKeyAlphaNum(event, document.getElementById('bankGL'));" value=""/></td>
                    <%-- <td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount"/>:</td>
                    <td width="12%"><html:text property="to.amount" styleId="amount"  styleClass="txtbox width130" onkeypress="return onlyNumberAndEnterKeyNav(event,'collectionAgainsts1');" value=""/></td> --%>
                    <td class="lable"><bean:message key="label.mec.bankGL"/>:</td>
                    <td>
                    	<html:select property="to.bankGL" styleId="bankGL" styleClass="selectBox width130" onkeypress = "return onlyDecimalAndEnterKeyNav(event,'collectionAgainsts1');" disabled="true">
		              		<html:option value=""><bean:message key="label.option.select" /></html:option>
		              		<logic:present name="collectionBankListLabel" scope="request">	
		              			<html:optionsCollection property="to.bankGLList" label="label" value="value" />
		              		</logic:present>
		            	</html:select>
                    </td>
                  </tr>
                  <tr>
                  	<td class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount"/>:</td>
                    <td colspan="5"><html:text property="to.amount" styleId="amount" maxlength="10" styleClass="txtbox width130" readonly="true"  onblur="validateHeaderAmt(this);" value=""/></td>
                  </tr>
                </table>
                
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.expense.dtls"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="collectionDetails" width="100%">
                  <thead>
            <tr>
                      <th width="5%" align="center" ><bean:message key="label.collection.details.srno"/></th>
                      <th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.collection.details.collection.against"/></th>
                      <th width="10%"><sup class="star">*</sup><bean:message key="label.collection.details.billNo"/></th>
                      <th width="12%"><bean:message key="label.collection.details.billamount"/></th>
                      <th width="12%"><sup class="star">*</sup><bean:message key="label.collection.details.receivedamount"/></th>
                      <th width="10%"><bean:message key="label.collection.details.tdsamount"/></th>
                      <th width="11%"><bean:message key="label.collection.details.deduction"/></th>
                      <th width="11%"><bean:message key="label.expense.cn.Total"/></th>
                      <th width="16%"><bean:message key="label.mec.reason"/></th>
                      <th width="16%"><bean:message key="label.expense.remark"/></th>
                    </tr>
          </thead>
                  
                </table>
      </div>
              <!-- Hidden Field START -->
				<html:hidden property="to.collectionID" styleId="collectionID" value=""/> 
				<html:hidden property="to.originOfficeCode" styleId="originOfficeCode" value = "${originOfficeCode}"/> 
				<html:hidden property="to.originOfficeId" styleId="originOfficeId" value = "${originOfficeId}"/> 
				<html:hidden property="to.custId" styleId="custId" value=""/> 
				<html:hidden property="to.status" styleId="trxStatus" value=""/>
              	<html:hidden property="to.createdBy" styleId="createdBy" />
				<html:hidden property="to.updatedBy" styleId="updatedBy" />
				<html:hidden property="to.isCreationScreen" styleId="isCreationScreen" value = "Y" />
              <!-- Hidden Field END -->
              <!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
         <div class="button_containerform">
            <%-- <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick = "saveOrUpdateBillCollection('save');">
				<bean:message key="button.collection.label.save"/></html:button> --%>
			<html:button property="Submit" styleClass="btnintform" styleId="submitBtn"  onclick = "saveOrUpdateBillCollection('submit');">
				<bean:message key="button.collection.label.submit"/></html:button>
			<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick = "cancleData();">
				<bean:message key="button.collection.label.cancel"/></html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>