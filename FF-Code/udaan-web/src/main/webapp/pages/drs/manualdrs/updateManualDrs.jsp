<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/drs/drsCommon.js"></script>
<script type="text/javascript" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" src="js/drs/manualdrs/updateManualDrs.js"></script>
</head>

<body>
<html:form method="post" styleId="manualDrsForm">
<div id="wraper"> 
         
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
             <div class="formbox">
						<h1>
							<bean:message key="label.drs.manual.update" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.cashbooking.mandatoryField" />
						</div>
					</div>
					<!--  Header START-->
					<jsp:include page="/pages/drs/manualdrs/manualDrsHeader.jsp" />

					<!--  Header END-->
      <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.drs.prep.npdox.details" /></div>                 
                </div>
                
                <table cellpadding="0" cellspacing="0" border="0" class="display"
							id="updateManualDrs" width="100%">
							<thead>
								<tr>
								<th align="center" ></th>
											<th align="center"><bean:message
											key="label.common.serialNo" /></th>
											
											<th align="center"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.consgNo" /></th>
											
											<th><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.update.delivery.type" /></th>
											
											<th><sup class="star">*</sup>&nbsp;<bean:message
											key="label.drs.update.delivery.time" /></th>
											
											<th><bean:message
											key="label.drs.update.receiver.name" /></th>
											
											<th><bean:message
											key="label.drs.update.contact.number" /></th>
											
											<th><bean:message
											key="label.drs.update.sealsign" /></th>
											
											<th><bean:message
											key="label.drs.prep.codLc.codAmount" /></th>
											
											<th><bean:message
											key="label.drs.prep.codLc.LCAmount" /></th>
											
											<th><bean:message
											key="label.drs.prep.codLc.toPayAmount" /></th>
											
											<th><bean:message
											key="label.drs.update.additionalCharges" /></th>
											<th><bean:message key="label.drs.prep.codLc.ba.amount"/></th>
											<th><sup class="star">*</sup><bean:message
											key="label.drs.update.modeOfPayment" /></th>
											
											<th><bean:message
											key="label.drs.update.chequeNo" /></th>
											
											<th><bean:message
											key="label.drs.update.chequeDate" /></th>
											
											<th><bean:message
											key="label.drs.update.bankName" /></th>
											<th><bean:message
											key="label.drs.update.relationship" /></th>
										<th><bean:message
											key="label.drs.update.idProofType" /></th>
										<th><bean:message
											key="label.drs.update.idNo"/></th>
											
											<th><bean:message
											key="label.drs.update.reason" /></th>  
											<th><bean:message
											key="label.drs.update.missedcard" /></th>
											<th><bean:message            
											key="label.drs.update.remarks" /></th>
								</tr>
							</thead>
							 <tbody>
							  <c:forEach var="drsDtls" items="${manualDrsForm.to.codLcDrsDetailsToList}"
									varStatus="loop">
									<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
									<td align="center">&nbsp;</td>
										<td><label><c:out value="${drsDtls.rowNumber}" />
										</label></td>
										
										<td><html:text
												styleId="rowConsignmentNumber${loop.count}"
												property="to.rowConsignmentNumber"
												styleClass="txtbox width145"
												value='${drsDtls.consignmentNumber}' readonly="true"
												tabindex="-1" /></td>
												
												
										<td><html:select property="to.rowDeliveryType"
												styleId="rowDeliveryType${loop.count}"
												styleClass="selectBox width110"
												value='${drsDtls.deliveryType}' onchange="enableForDlvType('${loop.count}')" onkeydown="return enterKeyNav('rowDeliveryTimeInHH${loop.count}',event.keyCode);">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="dlvTypeMap" scope="request">
													<html:optionsCollection name="dlvTypeMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
											
										<td><html:text styleId="rowDeliveryTimeInHH${loop.count}"
												property="to.rowDeliveryTimeInHH" styleClass="txtbox width30"
												value='${drsDtls.deliveryTimeInHHStr}' maxlength="5" onchange="clearDeliveryTimeMinutes('${loop.count}')" onkeydown="return enterKeyNav('rowDeliveryTimeInMM${loop.count}',event.keyCode);"  />:<html:text styleId="rowDeliveryTimeInMM${loop.count}"
												property="to.rowDeliveryTimeInMM" styleClass="txtbox width30"
												value='${drsDtls.deliveryTimeInMMStr}' maxlength="5" onchange="validateDeliveryTime('${loop.count}')" onkeydown="return enterKeynavForDlvTime('${loop.count}',event.keyCode)"  />
												</td>
												
										<td><html:text styleId="rowReceiverName${loop.count}"
												property="to.rowReceiverName" styleClass="txtbox width100"
												value='${drsDtls.receiverName}' maxlength="20" readonly="true" onkeydown="return enterKeyNav('rowContactNumber${loop.count}',event.keyCode);" /></td>
												
										<td><html:text styleId="rowContactNumber${loop.count}"
												property="to.rowContactNumber" styleClass="txtbox width100"
												value='${drsDtls.contactNumber}' maxlength="11" readonly="true" onchange="validateContactNumber(this)" onkeydown="return enterKeyNav('rowCompanySealSign${loop.count}',event.keyCode);" onkeypress="return onlyNumeric(event)"/></td>
												
										<td><html:select property="to.rowCompanySealSign"
												styleId="rowCompanySealSign${loop.count}"
												styleClass="selectBox width110"
												value='${drsDtls.companySealSign}' disabled="true" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,'${loop.count}',rowCompanySealSign);" >
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="drsSealMap" scope="request">
													<html:optionsCollection name="drsSealMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
											
											<td><html:text styleId="rowCodAmount${loop.count}"
												property="to.rowCodAmount"
												styleClass="txtbox width70"
												value='${drsDtls.codAmount}' readonly="true"
												tabindex="-1" maxlength="15" /></td>
												
												
											<td><html:text styleId="rowLCAmount${loop.count}"
												property="to.rowLCAmount"
												styleClass="txtbox width70"
												value='${drsDtls.lcAmount}' readonly="true"
												tabindex="-1" maxlength="15" /></td>
											
											<td><html:text styleId="rowToPayAmount${loop.count}"
												property="to.rowToPayAmount"
												styleClass="txtbox width70"
												value='${drsDtls.toPayAmount}' readonly="true"
												tabindex="-1" maxlength="8" /></td>
												
											<td><html:text styleId="rowOtherCharges${loop.count}"
												property="to.rowOtherCharges"
												styleClass="txtbox width70"
												value='${drsDtls.otherCharges}' readonly="true"
												tabindex="-1" maxlength="8" /></td>
													<td align="center" ><html:text styleId="rowBaAmount${loop.count}" property="to.rowBaAmount" value="${drsDtls.baAmount}" styleClass="txtbox width70" readonly="true" /></td>
												
											<td><html:select property="to.rowModeOfPayment"
												styleId="rowModeOfPayment${loop.count}"
												styleClass="selectBox width110"
												value='${drsDtls.modeOfPayment}' onchange="enableForModeOfPaymntChequeType('${loop.count}')" onkeydown="return enterKeyNavForModeofPaymnt('${loop.count}',event.keyCode)">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="modeOfPaymentMap" scope="request">
													<html:optionsCollection name="modeOfPaymentMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
											
											<td><html:text styleId="rowChequeNo${loop.count}"
												property="to.rowChequeNo"
												styleClass="txtbox width100"
												value='${drsDtls.chequeNo}' readonly="true" 
												tabindex="-1" maxlength="6" onkeydown="return enterKeyNav('calImg${loop.count}',event.keyCode);"  onchange="validateChequDDNumber(this)" onkeypress="return onlyNumeric(event)"/></td>
											
											<td><html:text styleId="rowChequeDate${loop.count}"
												property="to.rowChequeDate"
												styleClass="txtbox width100"
												value='${drsDtls.chequeDate}' readonly="true" 
												tabindex="-1" maxlength="12" onkeydown="return enterKeyNav('rowBankNameAndBranch${loop.count}',event.keyCode);"/>
												
												
											<a href='javascript:show_calendar("rowChequeDate${loop.count}", this.value)' id="calImg${loop.count}" style="display: none;"> 
         				 		<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0"  /></a> 
												</td>
											
											<td><html:text styleId="rowBankNameAndBranch${loop.count}"
												property="to.rowBankNameAndBranch"
												styleClass="txtbox width100"
												value='${drsDtls.bankNameAndBranch}' readonly="true" 
												tabindex="-1" maxlength="15" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,'${loop.count}',rowBankNameAndBranch);"/></td>
											<td><html:select property="to.rowRelationId"
												styleId="rowRelationId${loop.count}"
												styleClass="selectBox width110"
												value='${drsDtls.relationId}' disabled="true" tabindex="-1" >
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="relationMap" scope="request">
													<html:optionsCollection name="relationMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
											
											<td><html:select property="to.rowIdentityProofId"
												styleId="rowIdentityProofId${loop.count}"
												styleClass="selectBox width110"
												value='${drsDtls.identityProofTypeId}' disabled="true" tabindex="-1" onkeydown="return enterKeyNav('rowIdNumber${loop.count}',event.keyCode);">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
												<logic:present name="idproofMap" scope="request">
													<html:optionsCollection name="idproofMap" label="value"
														value="key" />
												</logic:present>
											</html:select></td>
											
											<td><html:text styleId="rowIdNumber${loop.count}"
												property="to.rowIdNumber"
												styleClass="txtbox width100"
												value='${drsDtls.idNumber}' 
												tabindex="-1" maxlength="15" readonly="true" onkeydown="return enterKeyNavforDrsUpdateContactNum(event.keyCode,'${loop.count}',rowIdNumber);"/></td>
											
										<td><html:select property="to.rowPendingReasonId"
												styleId="rowPendingReasonId${loop.count}"
												styleClass="selectBox width110" disabled="true" tabindex="-1">
												<option value="">SELECT</option>
												<logic:present name="drsDtls" property="nonDlvReason">
													<logic:iterate id="reason" name="drsDtls"
														property="nonDlvReason">
														<c:choose>
															<c:when test="${reason.key==drsDtls.reasonId}">
																<option value="${reason.key}" selected="selected">
																	<c:out value='${reason.value}' />
																</option>
															</c:when>
															<c:otherwise>
																<option value="${reason.key}">
																	<c:out value='${reason.value}' />
																</option>
															</c:otherwise>
														</c:choose>
													</logic:iterate>
												</logic:present>
											</html:select></td>
												<td><html:text styleId="rowMissedCardNumber${loop.count}"
												property="to.rowMissedCardNumber"
												styleClass="txtbox width100"
												value='${drsDtls.missedCardNumber}' readonly="true"
												tabindex="-1" maxlength="25" />
												
												</td>
												<td><html:text styleId="rowRemarks${loop.count}"
												property="to.rowRemarks" styleClass="txtbox width100"
												value='${drsDtls.remarks}' readonly="true" tabindex="-1"
												maxlength="30" /> <html:hidden
												styleId="rowDeliveryDetailId${loop.count}"
												property="to.rowDeliveryDetailId"
												value='${drsDtls.deliveryDetailId}'/>
												<html:hidden
													styleId="rowConsignmentId${loop.count}"
													property="to.rowConsignmentId"
													value="${drsDtls.consgnmentId}"	/>
												<html:hidden
													styleId="rowOriginCityId${loop.count}"
													property="to.rowOriginCityId"
													value="${drsDtls.originCityId}" /> <html:hidden
													styleId="rowOriginCityCode${loop.count}"
													property="to.rowOriginCityCode"
													value="${drsDtls.originCityCode}" />
													
													<html:hidden
													styleId="rowParentChildCnType${loop.count}"
													property="to.rowParentChildCnType" value="${drsDtls.parentChildCnType}" />
													<html:hidden
													styleId="rowAttemptNumber${loop.count}"
													property="to.rowAttemptNumber" value="${drsDtls.attemptNumber}" />
													<html:hidden styleId="rowIsPaymentAlreadyCaptured${loop.count}"
													property="to.rowIsPaymentAlreadyCaptured"/>
												
												</td>
												
												
												
									</tr>
								</c:forEach>
							 </tbody>
							       </table>
					
      </div>
              
              <!-- Grid /--> 
            </div>
     <!--hidden fields start from here --> 
           <jsp:include page="/pages/drs/drsCommon.jsp" />
           <html:hidden property="to.modeOfPaymentCheque" styleId="modeOfPaymentCheque" />
           <html:hidden property="to.modeOfPaymentCash" styleId="modeOfPaymentCash" />
           
            <!--hidden fields ENDs  here --> 
  </div>
         <!-- Button -->
			<jsp:include page="/pages/drs/update/drsUpdateButtonContainer.jsp" />
			<!-- Button ends -->
			<!-- main content ends -->
			</div>
          
       </html:form>
<!--wraper ends-->
</body>
</html>
							 