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
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockAcknowledgementAtRho.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var oTable = $('#receiptGrid').dataTable( {
		"sScrollY": "220",
		"sScrollX": "100%",
		"sScrollXInner":"140%",
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
	receiptStartUp();
	var url="${url}";
	if(!isNull(url)){
	globalFormSubmit(url,'stockReceiptForm');
	}
} );
</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<html:form method="post" styleId="stockReceiptForm">
<div id="wraper"> 
	<div class="clear"></div>
   	<!-- main content -->
    <div id="maincontent">
    		<div class="mainbody">
            	<div class="formbox">
	        		<h1><bean:message key="stock.label.receiptrho.title" /></h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory" /></div>
      			</div>
            	<div class="formTable">
              		<table border="0" cellpadding="0" cellspacing="7" width="100%">
	                  	<tr>
	            			<td width="7%" class="lable"><bean:message key="stock.label.ack.receiveddate" /> <strong>/</strong> <bean:message key="stock.label.issue.time" />:</td>
				            <td width="7%"><html:text property="to.receiptDateStr" styleId="receiptDateStr" styleClass="txtbox width145" size="18" tabindex="-1" readonly="true" />&nbsp;<%-- <html:text property="to.receiptTimeStr" styleId="receiptTimeStr" styleClass="txtbox width65" size="11" readonly="true" /> --%></td>
	            			<td width="6%" class="lable"><bean:message key="stock.label.ack.stkackNum" />:</td>
				            <td width="8%"><html:text property="to.acknowledgementNumber" styleId="acknowledgementNumber" styleClass="txtbox width145" size="11" onkeydown="return findOnEnterKey('ackNumber',event.keyCode);" />
				            	<html:button styleClass="btnintgrid" property="FIND" alt="Search" styleId="acknowledgeSearch" onclick="find('ackNumber');" >
				            		<bean:message key="button.search"/>
				            	</html:button>
				            </td>
				    	</tr>
				        <tr>
				            <td width="7%" class="lable"><bean:message key="stock.label.ack.stkReqNum" />:</td>
				            <td width="7%"><html:text property="to.requisitionNumber" styleId="requisitionNumber" styleClass="txtbox width145" size="11" onkeydown="return findOnEnterKey('reqNumber',event.keyCode);" />
				            	<%-- <html:button styleClass="searchButton" property="FIND" alt="Search" styleId="requisitionSearch" onclick="find('req');" /> --%>
				            	<html:button styleClass="btnintgrid" property="FIND" alt="Search" styleId="requisitionSearch" onclick="find('reqNumber');" >
				            		<bean:message key="button.search"/>
				            	</html:button>
				            </td>
				            
				        </tr>
           			</table>
				</div>
				<div id="demo">
        			<div class="title">
                		<div class="title2"><bean:message key="stock.label.issue.stockDetails" /></div>
                	</div>
	        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="receiptGrid" width="100%">
	                  	<thead>
	           				<tr>
		           				<th width="4%" align="center" ><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('to.checkbox',this.checked);" tabindex="-1"/></th>
	                     		<th width="4%" align="center" ><bean:message key="stock.label.issue.SrNo" /></th>
			                    <th width="12%" align="center" ><bean:message key="stock.label.issue.materialType" /></th>
			                    <th width="12%" align="center" ><bean:message key="stock.label.issue.materialCode" /></th>
			                    <th width="20%" align="center" ><bean:message key="stock.label.issue.description" /></th>
			                    <th width="11%"><bean:message key="stock.label.issue.uom" /></th>
			                    <th width="11%"><bean:message key="stock.label.receipt.for.branch" /></th>
			                    <th width="6%"><bean:message key="stock.label.issue.approvedQty" /></th>
			                    <th width="6%"><sup class="star">*</sup><bean:message key="stock.label.ack.receivingQty" /></th>
			                    <%-- <th width="6%"><bean:message key="stock.label.ack.returningQty" /></th> --%>
			                    <th width="7%"><sup class="star">*</sup><bean:message key="stock.label.issue.startNum" /></th>
			                    <th width="7%"><bean:message key="stock.label.issue.endNum" /></th>
			                    <th width="21%"><bean:message key="stock.label.issue.remarks" /></th>
	                    	</tr>
	          			</thead>
	                  	<tbody>
	                  		<c:forEach var="itemDtls" items="${stockReceiptForm.to.receiptItemDtls}" varStatus="loop">
	           				<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}"><!-- class="gradeA" -->
	           					<td><html:checkbox property="to.checkbox" value="${loop.count-1}" styleId="checkbox${loop.count}" tabindex="-1"/></td>
		                      	<td><label><c:out value="${loop.count}"/></label></td>
	                     		<td class="center">
	                     			<html:select property="to.rowItemTypeId" styleId="rowItemTypeId${loop.count}"
	                     				styleClass="selectBox width170" onchange="getItemList(this)" disabled="true" tabindex="-1"><!-- style="width: 160px" -->
                      					<html:option value=""><bean:message key="stock.label.select" /></html:option>
                          	        	<logic:present name="itemDtls" property="itemType">
											<logic:iterate id="itype"  name="itemDtls" property="itemType" >
												<c:choose>
													<c:when	test="${itype.key==itemDtls.itemTypeId}">
														<option value="${itype.key}" selected="selected">
															<c:out value='${itype.value}' />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${itype.key}">
															<c:out value='${itype.value}' />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
                        			</html:select>
	                       		</td>
	                     		<td class="center">
									<html:select property="to.rowItemId" styleId="rowItemId${loop.count}"
                      					styleClass="selectBox width170" disabled="true" tabindex="-1"><!-- style="width: 160px" -->
										<html:option value=""><bean:message key="stock.label.select" /></html:option>
										<logic:present name="itemDtls" property="item">
											<logic:iterate id="item1"  name="itemDtls" property="item" >
												<c:choose>
													<c:when	test="${item1.key==itemDtls.itemId}">
														<option value="${item1.key}" selected="selected">
															<c:out value='${item1.value}' />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${item1.key}">
															<c:out value='${item1.value}' />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select>
	                       		</td>
	                   			<td><html:text property="to.rowDescription" styleId="rowDescription${loop.count}" value="${itemDtls.description}" styleClass="txtbox width145" tabindex="-1" size="11" readonly="true"/></td>
	                   			<td><html:text property="to.rowUom" styleId="rowUom${loop.count}" value="${itemDtls.uom}" styleClass="txtbox width100" tabindex="-1" size="11" readonly="true"/></td>
	                   			<td><html:text property="to.rowStockForBranchName" styleId="rowStockForBranchName${loop.count}" value="${itemDtls.stockForBranchName}" styleClass="txtbox width100" tabindex="-1" size="11" readonly="true"/></td>
	                   			<td><html:text property="to.rowApprovedQuantity" styleId="rowApprovedQuantity${loop.count}" value="${itemDtls.approvedQuantity}" styleClass="txtbox width55" tabindex="-1" size="11" readonly="true" maxlength="6" onkeypress="return onlyNumeric(event)" /></td>
	                   			<td><html:text property="to.rowReceivingQuantity" styleId="rowReceivingQuantity${loop.count}" value="${itemDtls.receivedQuantity}" styleClass="txtbox width55" size="11" maxlength="6" onkeypress="return onlyNumeric(event)" onchange="checkBalanceQnty('${loop.count}');seriesValidationWrapperAtRho('${loop.count}')" onkeydown = "return enterKeyNav('rowStartSerialNumber${loop.count}' ,event.keyCode);"/></td>
	                   			<td><html:text property="to.rowStartSerialNumber" styleId="rowStartSerialNumber${loop.count}" value="${itemDtls.startSerialNumber}" styleClass="txtbox width110" size="15" maxlength="12" onchange="validateSeriesAtRHO(this)" onkeydown = "return enterKeyNav('rowRemarks${loop.count}' ,event.keyCode);"/></td>
	                   			<td><html:text property="to.rowEndSerialNumber" styleId="rowEndSerialNumber${loop.count}" value="${itemDtls.endSerialNumber}" styleClass="txtbox width110" tabindex="-1" size="15" maxlength="12" readonly="true"/></td>
	                   			<td><html:text property="to.rowRemarks" styleId="rowRemarks${loop.count}" value="${itemDtls.remarks}" styleClass="txtbox width145" maxlength="20" onkeydown="return enterKeyforStockReceipt(event.keyCode,'${loop.count}','rowRemarks')"/>
									
									<html:hidden property="to.rowRequestedQuantity" styleId="rowRequestedQuantity${loop.count}" value="${itemDtls.requestedQuantity}" />
                       				<html:hidden property="to.rowNumber" styleId="rowNumber${stat.count}" value='${itemDtls.rowNumber}' />
                       				
                       				<html:hidden property="to.rowSeries" styleId="rowSeries${loop.count}" value='${itemDtls.series}' />
			                       	<html:hidden property="to.rowSeriesLength" styleId="rowSeriesLength${loop.count}" value='${itemDtls.seriesLength}' />
			                       	<html:hidden property="to.rowIsItemHasSeries" styleId="rowIsItemHasSeries${loop.count}" value='${itemDtls.isItemHasSeries}' />
			                       	
			                       	<html:hidden property="to.rowBalanceQuantity" styleId="rowBalanceQuantity${loop.count}" value='${itemDtls.balanceQuantity}' />
			                       	<html:hidden property="to.rowStockItemDtlsId" styleId="rowStockItemDtlsId${loop.count}" value='${itemDtls.stockItemDtlsId}' />
	                   			   
	                   			    <html:hidden property="to.rowSeriesType" styleId="rowSeriesType${loop.count}" value='${itemDtls.seriesType}'  />
	                   			 
	                   			 <html:hidden property="to.rowOfficeProduct" styleId="rowOfficeProduct${loop.count}" value='${itemDtls.officeProductCodeInSeries}'  />
                     			 <html:hidden property="to.rowStartLeaf" styleId="rowStartLeaf${loop.count}" value='${itemDtls.startLeaf}' />
                      			<html:hidden property="to.rowEndLeaf" styleId="rowEndLeaf${loop.count}" value='${itemDtls.endLeaf}' />
	                   			<html:hidden property="to.rowStockReceiptItemDtlsId" styleId="rowStockReceiptItemDtlsId${loop.count}" value='${itemDtls.stockReceiptItemDtlsId}'  />
	                   			<html:hidden property="to.rowStockForBranchId" styleId="rowStockForBranchId${loop.count}" value='${itemDtls.requisitionCreatedOfficeId}'  />
	                   			<input type="hidden" name="seriesStartsWith" id="seriesStartsWith${loop.count}" value='${itemDtls.seriesStartsWith}' />
	                   			</td>
	                   		</tr>
	                   		</c:forEach>
	                   		
						</tbody>
	               	</table>
      			</div>
        	<!-- Grid /-->
			<!-- Hidden fields START from here -->
			<html:hidden property="to.stockReceiptId" styleId="stockReceiptId" />
			<html:hidden property="to.issuedDate" styleId="issuedDate" />
			
			<html:hidden property="to.issueOfficeId" styleId="issueOfficeId"/>
			<html:hidden property="to.receiptOfficeId" styleId="receiptOfficeId"/>
			<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
			<html:hidden property="to.loggedInUserId" styleId="loggedInUserId"/>
			<html:hidden property="to.createdByUserId" styleId="createdByUserId"/>
			<html:hidden property="to.updatedByUserId" styleId="updatedByUserId"/>
			<html:hidden property="to.transactionFromType" styleId="transactionFromType"/>
			
			<html:hidden property="to.transactionIssueType" styleId="transactionIssueType" />
				<html:hidden property="to.transactionPRType" styleId="transactionPRType" />

				<html:hidden property="to.loggedInOfficeCode" styleId="loggedInOfficeCode"/>			
				<html:hidden property="to.noSeries" styleId="noSeries"/>
			
				<html:hidden property="to.issuedToBranch" styleId="issuedToBranch" />
				<html:hidden property="to.issuedToBa" styleId="issuedToBa" />
				<html:hidden property="to.issuedToFr" styleId="issuedToFr" />
				<html:hidden property="to.baAllowedSeries" styleId="baAllowedSeries" />
				<html:hidden property="to.regionCode" styleId="regionCode" />
				<html:hidden property="to.consignmentType" styleId="consignmentType" />
				<html:hidden property="to.ogmStickers" styleId="ogmStickers" />
				<html:hidden property="to.bplStickers" styleId="bplStickers" />
				<html:hidden property="to.comailNumber" styleId="comailNumber" />
				<html:hidden property="to.bagLocNumber" styleId="bagLocNumber" />
				<html:hidden property="to.mbplStickers" styleId="mbplStickers"/>
				<html:hidden property="to.cityCode" styleId="cityCode"/>
				
				<html:hidden property="to.canUpdate" styleId="canUpdate"/>
				<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode"/>
				<html:hidden property="to.screenType" styleId="rhoScreen" value="rhoScreen"/><!-- Identifier to Identify Receipt at RHO -->
				<html:hidden property="to.officeType" styleId="officeType"/>
				<html:hidden property="to.officeRhoType" styleId="officeRhoType"/>
				
				
			<!-- Hidden fields END here -->
	    </div>
	</div>
	<!-- Button -->
	<div class="button_containerform">
		<c:choose>
			<c:when test="${not empty stockReceiptForm.to.stockReceiptId && stockReceiptForm.to.stockReceiptId>0}">
			</c:when>
			<c:otherwise>
		    	<html:button property="Save" styleClass="btnintform" onclick="save('Save');" styleId="Save"><bean:message key="button.save" /></html:button>
			</c:otherwise>
		</c:choose>
			
  		<%-- <html:button property="Print" styleClass="btnintform"><bean:message key="button.print" /></html:button> --%>
   		<html:button property="Cancel" styleId="Cancel" styleClass="btnintform" onclick="clearScreen();"><bean:message key="button.cancel" /></html:button>    
	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
</div>
</html:form>
<!--wraper ends-->
</body>
</html>
