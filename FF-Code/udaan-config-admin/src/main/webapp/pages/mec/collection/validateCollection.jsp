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
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js" ></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/mec/collection/validateCollection.js"></script>

<script type="text/javascript" charset="utf-8">
var STATUS_VALIDATED = '${STATUS_VALIDATED}';
var YES = "Y";
var NO = "N";
</script>
<!-- DataGrids /-->
</head>
<body onload="setDefaultValue();">
<!--wraper-->
<html:form  action="/validateCollection.do" method="post" styleId="validateCollectionForm"> 
<div id="wraper"> 
	<div class="clear"></div>
	<html:hidden property="to.originOfficeId" styleId="originOfficeId" value = "${originOfficeId}"/> 
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
      		<div class="formbox">
        		<h1> <bean:message key="label.validate.collection.header"/></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.mec.fieldsAreMandatory"/></div>
      		</div>
        	<div class="formTable">
	       		<table border="0" cellpadding="0" cellspacing="5" width="100%">
	        		<tr>
	          			<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.fromDt"/>:</td>
	          			<td width="20%" >
	          				<html:text property="to.frmDate" styleId="frmDate" styleClass="txtbox width100" readonly="true" onkeypress = "return callEnterKey(event, document.getElementById('toDate'));"/>
	            			&nbsp;<a href="javascript:show_calendar('frmDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
	            		</td>
	          			<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.toDt"/>:</td>
	          			<td width="15%">
	          				<html:text property="to.toDate" styleId="toDate" styleClass="txtbox width100" readonly="true" onkeypress = "return callEnterKey(event, document.getElementById('stationId'));"/>
	            			&nbsp;<a href="javascript:show_calendar('toDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
	            		</td>
	          			<td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.station"/>:</td>
	          			<td width="17%">
	          				<html:select property="to.stationId" styleId="stationId"  styleClass="selectBox width130" onchange="getAllOffices();" onkeypress = "return callEnterKey(event, document.getElementById('officeId'));">
                 				<html:option value=""><bean:message key="label.option.select" locale="display"/></html:option>
	           					<c:forEach var="cityTO" items="${cityTOs}" varStatus="loop">
	           			 			<c:choose>
       				 		 			<c:when test="${validateCollectionForm.to.stationId==cityTO.cityId}">
									 		<option value="${cityTO.cityId}" selected="selected" ><c:out value="${cityTO.cityName}"/></option>
										</c:when>
										<c:otherwise>
											<option value="${cityTO.cityId}" ><c:out value="${cityTO.cityName}"/></option>
										</c:otherwise>
									</c:choose>
	            				</c:forEach> 
              				</html:select>
              			</td>
	        		</tr>
	        		<tr>
						<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.office"/>:</td>
						<td width="20%">
							<html:hidden property="officeId" styleId="hiddenofficeId" value = "${validateCollectionForm.to.officeId}"/>
							<html:select property="to.officeId" styleId="officeId" styleClass="selectBox width130" onkeypress = "return callEnterKey(event, document.getElementById('headerStatus'));">
						    	<html:option value=""><bean:message key="label.option.select" locale="display"/></html:option>
							</html:select>
						</td>
						<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.validate.status"/>:</td>
						<td width="15%">
	           				<html:select property="to.headerStatus" styleId="headerStatus"  styleClass="selectBox width130" onkeypress = "return callEnterKey(event, document.getElementById('headerTransNo'));">tranStatus
                 				<c:forEach var="status" items="${tranStatus}" varStatus="loop">
                 	        		<c:choose>
       				 		 			<c:when test="${validateCollectionForm.to.headerStatus==status.value}">
											<option value="${status.value}" selected="selected" ><c:out value="${status.label}"/></option>
										</c:when>
										<c:otherwise>
											<option value="${status.value}"><c:out value="${status.label}"/></option>
										</c:otherwise>
									</c:choose>
	           					</c:forEach> 
              				</html:select>
              			</td>
						<td width="20%" class="lable"><bean:message key="label.mec.txnno"/>:</td>
						<td width="17%"><html:text property="to.headerTransNo" styleId="headerTransNo" styleClass="txtbox width100" onkeypress = "return callEnterKey(event, document.getElementById('searchBtn'));"/></td>
	        		</tr>
	        	<tr>
	        		<td align="right" valign="top" colspan="6"><input id="searchBtn" name="Search" type="button" value="Search" class="button" onclick ="searchCollectionDetlsForValidation();"  title="Search"/></td>
	        	</tr>
	      	</table>
		</div>
		<div id="demo">
        	<div class="title">
            	<div class="title2"><bean:message key="label.expense.dtls"/></div>
          	</div>
          	<table class="display" id= "collectionDetails" cellpadding="0" cellspacing="0" border="0" width="100%">
   				<thead>
                	<tr>
                    	<th align="center"><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAllCheckBox(this);" tabindex="-1" /></th>
                        <th align="center"><bean:message key="label.mec.date"/></th>
                        <th align="center"><bean:message key="label.cncollection.transactionno"/></th>
                        <th align="center"><bean:message key="label.collection.customer.name"/></th>
                        <th align="center"><bean:message key="label.collection.details.collection.against"/></th>
                        <th align="center"><bean:message key="label.collection.mode.payment"/></th>
                        <th align="center"><bean:message key="label.mec.amount"/></th>
                        <th align="center"><bean:message key="label.mec.validate.status"/></th>
					</tr>
				</thead>
               	<tbody>
               		<c:forEach var="collnDtls" items="${validateCollectionEntry}" varStatus="loop">
                 		<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}"><!-- class="gradeA" -->
                 			<td class="row1" align="center"><input type="checkbox" name="chk${loop.count}" id="chk${loop.count}" onclick="setIsCheckedValue(this);" tabindex="-1" /></td>
	               			<td class="row1" align="center"><label><c:out value="${collnDtls.transactionDate}" /></label></td>
	               			<td class="row1" align="center"><a href="JavaScript:collectionDtls('./billCollection.do?submitName=viewCollectionEntryDtls&txnNo=' + '${collnDtls.transactionNo}' + '&collectionType=' + '${collnDtls.collectionType}' + '&isCorrectionParam=N' + '&officeId=' + '${collnDtls.collectionOfficeId}');" ><c:out value='${collnDtls.transactionNo}'/></a></td>
	               			<td class="row1" align="center">
	               				<c:choose>
		               				<c:when test="${not empty collnDtls.custName}">
		               					<label><c:out value="${collnDtls.custName}"/></label>
		               				</c:when>
		               				<c:otherwise>
		               					<label>-</label>
		               				</c:otherwise>
	               				</c:choose>
	               			</td>
	               			<td class="row1" align="center"><label><c:out value='${collnDtls.collectionAgainst}'/></label></td>
	               			<td class="row1" align="center"><label><c:out value='${collnDtls.paymentMode}'/> </label></td>
	               			<td class="row1" align="center"><label><c:out value='${collnDtls.amount}'/> </label></td>
	               			<td class="row1" align="center"><label><c:out value='${collnDtls.status}' /></label>
	               				<html:hidden property="to.isChecked" styleId="isChecked${loop.count}" />
	               				<html:hidden property="to.txns" styleId="txns${loop.count}" value="${collnDtls.transactionNo}" />
	               			</td>
	               		</tr>
	          		</c:forEach>
				</tbody>
			</table>
		</div>
        
        <!-- Grid /--> 
        </div>
	</div>
	<!-- Button --> 
	<div class="button_containerform">
		<c:if test="${validateCollectionForm.to.headerStatus!=STATUS_VALIDATED}">
			<html:button property="Validate" styleId="Validate" styleClass="btnintform" onclick="validateTxns();">
				<bean:message key="button.validate" /></html:button>
		</c:if>
	</div>
	<!-- Button ends -->
	<!-- main content ends -->
</div>
</html:form>
<!--wraper ends-->
</body>
</html>
