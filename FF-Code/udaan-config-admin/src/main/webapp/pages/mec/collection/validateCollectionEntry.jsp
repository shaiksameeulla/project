<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>

<script language="JavaScript" src="js/jquery/jquery-ui.min.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/ui/jquery.ui.core.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/ui/jquery.ui.widget.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.dimensions.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.bgiframe-2.1.2.js" type="text/javascript" ></script>

<script language="JavaScript" type="text/javascript" src="js/jquery/jquery.idletimeout.js"></script>
<script language="JavaScript" type="text/javascript" src="js/jquery/jquery.idletimer.js"></script>
<!-- <script language="JavaScript" type="text/javascript" src="js/firstLevelNav.js"></script> -->	
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script>DataGrids 
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script> -->

<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/collection/billCollection.js"></script>

<script type="text/javascript" charset="utf-8">
var BILL_TYPE = '${BILL_TYPE}';
var BC_MODE = '${BC_MODE}';
var collectionType = '${billCollectionForm.to.collectionType}';
var collStatus = '${billCollectionForm.to.status}';
var VALIDATE_STATUS = '${VALIDATE_STATUS}';
var screenName = '${screenName}';
/* alert("CT : " + collectionType + "  BT : " + BILL_TYPE); */
$(document).ready( function () {
	var oTable = $('#billCollectionValidate').dataTable( {
		"sScrollY": "120",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
	validateHeaderAmount();
} );

function validateHeaderAmount() {
	var headerAmt = '${billCollectionForm.to.amount}';
	headerAmt.value = parseFloat(headerAmt.value).toFixed(2);
}
</script>
<!-- DataGrids /-->
</head>
<body onload="loadBillValidateDefaultObjects()">
<!--wraper-->
<div id="wraper"> 
	<div class="clear"></div>
    <html:form  action="/billCollection.do" method="post" styleId="billCollectionForm"> 
	    <!-- main content -->
	    <%-- <html:hidden property="to.collectionID" styleId="collectionID"/> --%> 
		<html:hidden property="to.isCorrection" styleId="isCorrectionParam"/>
		<html:hidden property="to.collectionID" styleId="collectionID"/> 
		<html:hidden property="to.originOfficeId" styleId="originOfficeId"/> <!--  value = "${originOfficeId}" -->
		<html:hidden property="to.originOfficeCode" styleId="collectionCode" value = "${originOfficeCode}"/> 
		<html:hidden property="to.collectionType" styleId="collectionType" value = "${billCollectionForm.to.collectionType}"/>
		<%-- <html:hidden property="to.isCorrection" styleId="isCorrection" /> --%>
		<html:hidden property="to.custId" styleId="custId"/> 
		<html:hidden property="to.status" styleId="status" />
		<html:hidden property="to.isCreationScreen" styleId="isCreationScreen" value = "N" />
	    <!-- main content -->
	    <div id="maincontent">
	    	<div class="mainbody">
            	<div class="formbox">
        			<h1>
        				<c:choose>
	        				<c:when test="${billCollectionForm.to.collectionType==BILL_TYPE}">
		        				Verify Bill Collection</c:when>
		        			<c:otherwise>Verify CN Collection</c:otherwise>
	        			</c:choose>
        			</h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are mandatory</div>
      			</div>
              	<div class="formTable">
              	<table border="0" cellpadding="0" cellspacing="5" width="100%">
                	<tr>
	                	<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.transactionNumber"/>:</td>
	                    <td width="21%" ><html:text property="to.txnNo" styleId="txnNo"  styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
	                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.expense.postingDate"/>:</td>
	                    <td width="15%"><html:text property="to.collectionDate" styleId="collectionDate" styleClass="txtbox width110" readonly="true" tabindex="-1" />&nbsp;</td>
	                    <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.collection.customer.name"/></td>
	                    <td width="12%"> <html:text property="to.custName" styleId="custName" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
                  </tr>
                  <tr>
	                    <td class="lable"><bean:message key="label.collection.customer.code"/></td>
	                    <td width="21%"><html:text property="to.custCode" styleId="custCode" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
	                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.collection.modeofcollection"/></td>
	                    <td width="15%">
	                    <html:select property="to.collectionModeId" styleId="collectionModeId"  styleClass="selectBox width130">
	                       <html:optionsCollection property="to.collectionModeList" label="label" value="value"/>
	                    </html:select></td>
	                    <td width="15%" class="lable"><bean:message key="label.mec.chequeNumber"/>:</td>
	                    <td width="12%"><html:text property="to.chqNo" styleId="chqNo" styleClass="txtbox width130" onkeypress="return onlyNumeric(event);" onblur="validateChqNumber(this);" /></td>
                  </tr>
                  <tr>
	                    <td class="lable"><bean:message key="label.mec.chequeDate"/>:</td>
	                    <td width="21%"><html:text property="to.chqDate" styleId="chqDate"  styleClass="txtbox width110"  readonly="readonly" />
	                    &nbsp;<a href="javascript:show_calendar('chqDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a></td>
	                    <td class="lable"><bean:message key="label.mec.bankName"/>:</td>
	                    <td width="15%"><html:text property="to.bankName" styleId="bankName"  styleClass="txtbox width110"  readonly="true" /></td>
	                    <%-- <td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount"/>:</td>
	                    <td width="12%"><html:text property="to.amount" styleId="amount"  styleClass="txtbox width130" onkeypress="return onlyDecimal(event);" readonly="readonly"/></td> --%>
	                    <td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.bankGL"/>:</td>
	                    <td width="12%">
		                    <html:select styleId="bankGL" property="to.bankGL" styleClass="selectBox width145" value="${billCollectionForm.to.bankGL}">
		                    	<html:option value="">--- SELECT ---</html:option>
		                    	<logic:present name="collectionBankList" scope="request">
			                    	<c:forEach var="collBankGL" items="${collectionBankList}" >  
										<html:option value="${collBankGL.glId}">${collBankGL.glDesc}</html:option>
									</c:forEach>
								</logic:present>
							</html:select>
	                    </td>
                  </tr>
	                  <tr>
                    	<c:choose>
	                   		<c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
		                    	<td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount"/>:</td>
		                    	<td width="12%"><html:text property="to.corrAmount" styleId="amount"  styleClass="txtbox width130" onkeypress="return onlyDecimal(event);" readonly="true"  value="${billCollectionForm.to.amount}"/></td>
		                    	<%-- <td  width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.collection.correctedRevdAmount"/>:</td>
		                    	<td  width="17%"><html:text property="to.amount" styleId="corrAmount"  styleClass="txtbox width130" onkeypress="return onlyDecimal(event);" value="" readonly="readonly"/></td> --%>
		                    	<td class="lable" colspan="5">&nbsp;
		                    		<html:hidden property="to.amount" styleId="corrAmount" value="" />
		                    	</td>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<td width="15%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount"/>:</td>
	                    		<td width="12%"><html:text property="to.amount" styleId="amount"  styleClass="txtbox width130" onkeypress="return onlyDecimal(event);" readonly="true"/></td>
	                    		<td class="lable" colspan="4">&nbsp;</td>
	                    	</c:otherwise>
	                    </c:choose>
	                  </tr>
                </table>
                
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="billCollectionValidate" width="100%">
                  <thead>
           			 <tr>
           			 
           			  <th width="5%" align="center" ><bean:message key="label.collection.details.srno"/></th>
           			  
           			  <c:choose>
           			  	<c:when test="${billCollectionForm.to.collectionType==BILL_TYPE}">
                      		<th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.collection.details.collection.against"/></th>
                      		<th width="10%"><sup class="star">*</sup><bean:message key="label.collection.details.billNo"/></th>
                      		<th width="12%"><bean:message key="label.collection.details.billamount"/></th>
                      	</c:when>
                      	<c:otherwise>
                      		<th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.cncollection.receiptno"/></th>
                      		<th width="10%"><sup class="star">*</sup><bean:message key="label.cncollection.cnno"/></th>
                      		<th width="12%"><bean:message key="label.cncollection.collection.type"/></th>
                      	</c:otherwise>
                      </c:choose>
	                      
	                  <c:choose> 
	                      <c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
	                      	<th width="12%"><sup class="star">*</sup><bean:message key="label.collection.details.receivedamount"/></th>
	                      	<th width="10%"><bean:message key="label.collection.correctedRevdAmount"/></th>
	                      </c:when>
	                      <c:otherwise>
	        				<th width="12%"><sup class="star">*</sup><bean:message key="label.collection.details.receivedamount"/></th>              
	                      </c:otherwise>
                      </c:choose>
                      
                      <c:choose>
	                      <c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
	                      	<th width="10%"><bean:message key="label.collection.details.tdsamount"/></th>
	                      	<th width="10%"><bean:message key="label.collection.corrected.TDSAmount"/></th>
	                      </c:when>
	                      <c:otherwise>
	  						<th width="10%"><bean:message key="label.collection.details.tdsamount"/></th>                    
	                      </c:otherwise>
                      </c:choose>
                    
					<c:if test="${billCollectionForm.to.collectionType==BILL_TYPE}">
						<c:choose>
							<c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
								<th width="10%"><bean:message key="label.collection.details.deduction" /></th>
		                      	<th width="10%"><bean:message key="label.collection.corrected.deduction" /></th>
							</c:when>
							<c:otherwise>
								<th width="10%"><bean:message key="label.collection.details.deduction" /></th>  	
							</c:otherwise>
						</c:choose>
                    </c:if>
                      
                      <th width="11%"><bean:message key="label.expense.cn.Total"/></th>
                      <%-- <c:if test="${billCollectionForm.to.collectionType!=BILL_TYPE}"> --%>
 						<th width="14%"><bean:message key="label.mec.reason"/></th>
					  <%-- </c:if> --%> 
                      <th width="16%"><bean:message key="label.expense.remark"/></th>
           	      </tr>
          </thead>
       			<tbody>
	               <c:forEach var="collnDtls" items="${billCollectionForm.to.billCollectionDetailTO}" varStatus="loop">
	                 	<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}"><!-- class="gradeA" -->
	                   	    <td><label><c:out value="${loop.count}"/> </label><html:hidden property="to.collectionEntryIds" styleId="collectionEntryIds${loop.count}" value = "${collnDtls.collectionEntryId}"/><html:hidden property="to.srNos" styleId="srNos${loop.count}" value = "${collnDtls.srNo}"/></td>
	                   	     <c:choose>
           			  			<c:when test="${billCollectionForm.to.collectionType==BILL_TYPE}">
			                   	    <td  id = "billcollTD1${loop.count}">
			                   	    <html:select property="to.collectionAgainsts" styleId="collectionAgainsts${loop.count}" 
		                            		styleClass="selectBox" onfocus="getItemList(this);" disabled="true" tabindex="-1"> <!-- style="width: 100px" -->
									  		<html:option value=""><bean:message key="label.option.select" /></html:option>
				          				 	<c:forEach var="collectionAgainstDtls" items="${collectionAgainst}" varStatus="loop1">
		          				 				<c:choose>
		          				 				<c:when test="${collnDtls.collectionAgainst==collectionAgainstDtls.stdTypeCode}">
		          				 					  <option selected="selected" value="${collectionAgainstDtls.stdTypeCode}" ><c:out value="${collectionAgainstDtls.description}"/></option>
												</c:when>
												<c:otherwise>
													 <option value="${collectionAgainstDtls.stdTypeCode}" ><c:out value="${collectionAgainstDtls.description}"/></option>
												</c:otherwise>
												</c:choose>
				          					</c:forEach> 
									</html:select>
									</td>	
		 							<td id = "billcollTD2${loop.count}"><html:text styleId="billNos${loop.count}" property="to.billNos" readonly="true" tabindex="-1" styleClass="txtbox width70" value='${collnDtls.billNo}'/></td> 
		 							<fmt:formatNumber var="fmtBillAmt" type="number" pattern="0.00" value="${collnDtls.billAmount}" />
		 							<td id = "billcollTD3${loop.count}"><html:text styleId="billAmounts${loop.count}" property="to.billAmounts" readonly="true" tabindex="-1" styleClass="txtbox width70" value='${fmtBillAmt}' /></td>
 								</c:when>
                      			<c:otherwise>
		 							<td width="13%" id = "CNcollTD1${loop.count}"><html:text styleId="receiptNo${loop.count}" property="to.receiptNo" readonly="true" tabindex="-1" styleClass="txtbox width100" value='${collnDtls.receiptNo}'/></td> 
		 							<td width="10%" id = "CNcollTD2${loop.count}"><html:text styleId="cnNo${loop.count}" property="to.cnNo" readonly="true" tabindex="-1" styleClass="txtbox width120" value='${collnDtls.cnNo}' /></td> 
		 							<td width="12%" id = "CNcollTD3${loop.count}"><html:text styleId="collectionType${loop.count}" property="to.collectionTypes" tabindex="-1" readonly="true" styleClass="txtbox width100" value='${collnDtls.collectionType}'/>
		 																		<html:hidden styleId="billAmounts${loop.count}" property="to.billAmounts" value='${collnDtls.billAmount}' />
		 																		<html:hidden styleId="consgId${loop.count}" property="to.consgIds" value='${collnDtls.consgId}' />
		 																		<html:hidden styleId="cnfor${loop.count}" property="to.cnfor" value='${collnDtls.cnfor}' />
		 																		<html:hidden styleId="cnDeliveryDt${loop.count}" property="to.cnDeliveryDt" value='${collnDtls.cnDeliveryDt}' />
		 							</td>
 								</c:otherwise>
                     		</c:choose>
                     		
                     		<c:choose>
	 							<c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
	 								<fmt:formatNumber var="fmtReceiveAmt" type="number" pattern="0.00" value="${collnDtls.recvdAmt}" />
	 								<td><html:text styleId="receivedAmounts${loop.count}" property="to.correctedRecvAmount"  styleClass="txtbox width70" value='${fmtReceiveAmt}' readonly="true" tabindex="-1"/></td>
	 								<td><html:text styleId="correctedRecvAmount${loop.count}" property="to.receivedAmounts" onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,'R');" styleClass="txtbox width70" value='${collnDtls.correctedAmount}'/></td>
	 							</c:when>
	 							<c:otherwise>
	 								<td><fmt:formatNumber var="fmtReceiveAmt" type="number"  pattern="0.00" value="${collnDtls.recvdAmt}" /><html:text styleId="receivedAmounts${loop.count}" property="to.receivedAmounts"  styleClass="txtbox width70" value='${fmtReceiveAmt}' onkeypress="return onlyDecimalAndEnterKeyNav(event,'tdsAmounts${loop.count}');"  onblur = "calculateTotalAmountForCorrection(this,'R'); validateRcvdAmtForCorrection(this); calculateHeaderAmtForCorection(this);setFractions(this,2);"/></td>
	 							</c:otherwise> 
 							</c:choose>
 							
 							<c:choose>
	 							<c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
	 								<fmt:formatNumber type="number"  var = "fmtTDSAmt" pattern="0.00" value="${collnDtls.tdsAmt}" />
	 								<td><html:text styleId="tdsAmounts${loop.count}" property="to.correctedTDS"  styleClass="txtbox width70" value='${fmtTDSAmt}' readonly="true" tabindex="-1"/></td>
	 								<td><html:text styleId="correctedTDS${loop.count}" property="to.tdsAmounts" onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,'T');" styleClass="txtbox width70" value='${collnDtls.correctedTDS}'/></td>
	 							</c:when>
	 							<c:otherwise>
	 							    <fmt:formatNumber type="number"  var = "fmtTDSAmt" pattern="0.00" value="${collnDtls.tdsAmt}" />
	 								<td><html:text styleId="tdsAmounts${loop.count}" property="to.tdsAmounts"  styleClass="txtbox width70" value='${fmtTDSAmt}' onkeypress="return onlyDecimalAndEnterKeyNav(event,'deduction${loop.count}');" onchange = "setFractions(this,2);" onblur = "calculateTotalAmountForCorrection(this,'T'); validateTDSAmtForCorrection(this);" /></td>	
	 							</c:otherwise>
 							</c:choose> 
 							
 							<c:if test="${billCollectionForm.to.collectionType==BILL_TYPE}">
	 							<c:choose>
		 							<c:when test="${billCollectionForm.to.isCorrection==IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">
		 								<fmt:formatNumber var="fmtDeduction" type='number'  pattern='0.00' value='${collnDtls.deduction}' />
		 								<td><html:text styleId="correctedDeduction${loop.count}" property="to.correctedDeduction"  styleClass="txtbox width70" value='${fmtDeduction}' readonly="true" tabindex="-1"/></td>
		 								<td><html:text styleId="deduction${loop.count}" property="to.deductions" onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,'D');" styleClass="txtbox width70" value="" /></td>
		 							</c:when>
		 							<c:otherwise>
		 								<fmt:formatNumber var="fmtDeduction" type='number'  pattern='0.00' value='${collnDtls.deduction}' />
		 								<td><html:text styleId="deduction${loop.count}" property="to.deductions"  styleClass="txtbox width70" value="${fmtDeduction}"  onkeypress="return onlyUnsignedDecimalAndEnterKeyNav(event,'totals${loop.count}', this);" onblur = "calculateTotalAmountForCorrection(this,'D');validateDeductionAmtForCorrection(this,${loop.count}); setFractions(this,2);" /></td>	
		 							</c:otherwise>
	 							</c:choose>
 							</c:if>
 							<fmt:formatNumber type="number" var="fmtTotals" pattern="0.00" value="${collnDtls.total}" />
 							<td><html:text styleId="totals${loop.count}" property="to.totals"  styleClass="txtbox width70" value='${fmtTotals}' readonly="true" tabindex="-1" /></td>
 							<%-- <c:if test="${billCollectionForm.to.collectionType!=BILL_TYPE}"> --%>
 								<td>
 									<html:select styleId="reason${loop.count}" property="to.reasonIds" styleClass="selectBox width145" value='${collnDtls.reasonId}'>
										<html:option value="">--- SELECT ---</html:option>
 										<logic:present name="reasonList" scope="request">					
											<html:optionsCollection property="to.reasonList" label="label" value="value" />
										</logic:present>
 									</html:select>
 								</td>
 							<%-- </c:if>  --%>
 							<td>
 								<html:text styleId="remarks${loop.count}" property="to.remarks" styleClass="txtbox width130" value='${collnDtls.remarks}'/>
 								<!-- Common Hidden Field START -->
 								<html:hidden styleId="createdBy${loop.count}" property="to.createdBys" value='${collnDtls.createdBy}' />
 								<!-- Common Hidden Field END -->
 							</td> 
	               		</tr>
		          </c:forEach>
               </tbody>
                </table>
      </div>
              
              <!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
         <div class="button_containerform">
          <c:if test = "${billCollectionForm.to.status!=VALIDATE_STATUS}">
	            <html:button property="Validate" styleClass="btnintform" styleId="validateBtn" onclick = "ValidateCollection();">
					<bean:message key="button.validate"/></html:button>
				<c:if test="${billCollectionForm.to.isCorrection!=IS_CORRECTION_YES && billCollectionForm.to.collectionModeId!=BC_MODE}">	
	   				<html:button property="Correction" styleClass="btnintform" styleId="correctionBtn" onclick = "correction();">
						<bean:message key="button.correction"/></html:button>
				</c:if>
		 </c:if>
		 <c:if test = "${billCollectionForm.to.status==VALIDATE_STATUS}"> 
		 <html:button property="close" styleClass="btnintform" styleId="closeBtn" onclick = "closePage();">
					<bean:message key="button.close"/></html:button>
		</c:if>
  		 </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>
