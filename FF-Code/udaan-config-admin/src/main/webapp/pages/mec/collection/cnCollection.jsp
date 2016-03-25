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
<!-- <script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script> -->
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/mecCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/collection/cnCollection.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8">
	var CHQ_MODE = '${CHQ_MODE}';
	var CASH_MODE = '${CASH_MODE}';
	var COLLECTED = '${COLLECTED}';
	var PARTY_LETTER = '${PARTY_LETTER}';
	var COLL_FOR_FFCL = "F";
	var COLL_FOR_CUST = "C";
	
	var STATUS_OPENED = '${STATUS_OPENED}';
	var STATUS_SUBMITTED = '${STATUS_SUBMITTED}';
	var STATUS_VALIDATED = '${STATUS_VALIDATED}';
	
	var CN_FOR_LC = "${CN_FOR_LC}";
	var CN_FOR_COD = "${CN_FOR_COD}";
	
	function getReasons() {
		var text = "<option value=''>--- SELECT ---</option>";
		<c:forEach var="reason" items="${reasonsList}" varStatus="status">  
			text = text + "<option value=${reason.reasonId}>${reason.reasonCode} - ${reason.reasonName}</option>";
		</c:forEach>
		return text;		   	
	}
	function getModes() {
		var text = "";
		<c:forEach var="mode" items="${modesList}" varStatus="status">  
			text = text + "<option value=${mode.paymentId}>${mode.paymentType}</option>";
		</c:forEach>
		return text;		   	
	}
	function getCnFor() {
		var text = "";
		<c:forEach var="cnFor" items="${cnForList}" varStatus="status">  
			text = text + "<option value=${cnFor.stdTypeCode}>${cnFor.description}</option>";
		</c:forEach>
		return text;		   	
	}
	function getBankGLs() {
		var text = "<option value=''>---SELECT---</option>";
		<c:forEach var="bankGL" items="${collectionBankList}" varStatus="status">  
			text = text + "<option value=${bankGL.glId}>${bankGL.glDesc}</option>";
		</c:forEach>
		return text;		   	
	}
	var reasonOptions = getReasons();
	var modeOptions = getModes(); 
	var cnForOptions = getCnFor();
	var bankGLOptions = getBankGLs();
</script>
</head>
<body onload="loadDefaultObjects();">
<!--wraper-->
<div id="wraper"> 
<html:form method="post" styleId="cnCollectionForm"> 
	<html:hidden property="to.originOfficeId" styleId="originOfficeId" value = "${originOfficeId}"/> 
    <html:hidden property="to.originOfficeCode" styleId="originOfficeCode" value = "${originOfficeCode}"/>
    <!-- main content -->
    	<div id="maincontent">
    		<div class="mainbody">
      			<div class="formbox">
        			<h1><bean:message key="label.cncollection.header"/></h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.mec.fieldsAreMandatory"/></div>
      			</div>
        		<div class="formTable">
              		<table border="0" cellpadding="0" cellspacing="5" width="100%">
                		<tr>
                    		<td width="11%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.collection.dateTime"/></td>
                    		<td width="89%" >  <html:text property="to.cnCollectionDate" styleId="collectionDate" styleClass="txtbox width130"  readonly="true" value="${todaysDate}"/>&nbsp;</td>
                  		</tr>
                	</table>
                
				</div>
              	<div id="demo">
        		<div class="title">
                	<div class="title2"><bean:message key="label.expense.dtls"/></div>
                </div>
        		<table cellpadding="3" cellspacing="0" border="0" class="display" id="cnCollectionDetails" width="100%">
                	<thead>
            			<tr>
            				<th width="2%" align="center"><input type="checkbox" name="checkAll" id="checkAll" tabindex="-1" onclick="selectAllCheckBox(this);" /></th>
            				<th width="2%" align="center"><bean:message key="label.expense.srNo" /></th>
                      		<th width="7%" align="center"><sup class="star">*</sup><bean:message key="label.cncollection.transactionno"/></th>
                      		<th width="5%" align="center"><sup class="star">*</sup><bean:message key="label.cncollection.receiptno"/></th>
                      		<th width="8%"><sup class="star">*</sup><bean:message key="label.cncollection.cnno"/></th>
                      		<th width="6%"><sup class="star">*</sup><bean:message key="label.cncollection.collection.type"/></th>
                      		<th width="5%"><sup class="star">*</sup><bean:message key="label.mec.amount"/></th>
                      		<th width="6%"><sup class="star">*</sup><bean:message key="label.collection.details.receivedamount"/></th>
                      		<th width="5%"><bean:message key="label.collection.details.tdsamount"/></th>
                      		<th width="5%"><bean:message key="label.expense.cn.Total"/></th>
                      		<th width="9%"><sup class="star">*</sup><bean:message key="label.cncollection.mode"/></th>
                      		<th width="9%"><bean:message key="label.cncollection.for"/></th>
                      		<th width="5%"><bean:message key="label.cncollection.chequeno"/></th>
                      		<th width="6%"><bean:message key="label.mec.chequeDate"/></th>
                      		<th width="5%"><bean:message key="label.mec.bankName"/></th>
                      		<th width="8%"><bean:message key="label.mec.bankGL"/></th>
                      		<th width="8%"><bean:message key="label.mec.reason"/></th>
                    	</tr>
          			</thead>
				</table>
      		</div>
      		
      		<!-- Hidden Field START -->
      		<html:hidden property="to.status" styleId="status" />
      		<html:hidden property="to.createdBy" styleId="createdBy" />
      		<html:hidden property="to.updatedBy" styleId="updatedBy" />
      		<html:hidden property="to.isCreationScreen" styleId="isCreationScreen" value = "Y" />
      		<!-- Hidden Field END -->
      		
			<!-- Grid /--> 
		</div>
	</div>
    <!-- Button -->
    	<div class="button_containerform">
  			<%-- <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick = "saveOrUpdateCNCollection('save');">
				<bean:message key="button.collection.label.save"/></html:button> --%>
			<html:button property="Submit" styleClass="btnintform" styleId="submitBtn"  onclick = "saveOrUpdateCNCollection('submit');">
				<bean:message key="button.collection.label.submit"/></html:button>
			<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick = "cancleCnData();">
				<bean:message key="button.collection.label.cancel"/></html:button>
  		</div>
        <!-- Button ends --> 
</html:form>
<!-- main content ends --> 
</div>
<!--wraper ends-->
</body>
</html>