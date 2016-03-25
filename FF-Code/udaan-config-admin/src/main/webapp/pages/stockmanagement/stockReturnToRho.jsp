<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script> DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/stockmanagement/stockReturnToRho.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8">
	
</script>

</head>

<body>
<html:form method="post" styleId="stockReturnForm">
<div id="wraper"> 
	<div class="clear">
	</div>
	<div id="maincontent">
		<div class="mainbody">
  
		  <div class="formbox">
		       <h1>Stock Return</h1>
		       <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="stock.label.issue.mandatory"/></div>
		  </div>
  
		  <div class="formTable">
		  		<table border="0" cellpadding="0" cellspacing="7" width="100%">
		            <tr>
		            	<td width="20%" class="lable"><bean:message key="stock.label.return.returnDate"/><strong>/</strong> <bean:message key="stock.label.issue.time"/>:</td>
		            	<td width="16%"><html:text property="to.returnDateStr" styleId="returnDateStr" styleClass="txtbox width175" size="18" readonly="true" tabindex="-1"/></td>
		            	
			            <td width="18%" class="lable"><bean:message key="stock.label.return.stockReturnNo"/>:</td>
			            <td width="20%"><html:text property="to.stockReturnNumber" styleId="stockReturnNumber"  styleClass="txtbox width145" maxlength="12" size="14" onkeydown="return findOnEnterKey('stockReturn',event.keyCode);" />
            				
            				<html:button styleClass="btnintgrid" property="FIND" styleId="returnSearch" alt="Search" onclick="find('stockReturn');">
            					<bean:message key="button.search" />
			            	</html:button>
            			</td>
			            
		            </tr>
		            
		            <tr>
			            <td width="20%" class="lable"><bean:message key="stock.label.ack.stkackNum"/>:</td>
            			<td width="16%" colspan="3"><html:text property="to.acknowledgementNumber" styleId="acknowledgementNumber"  styleClass="txtbox width145" maxlength="12" size="14" onkeydown="return findOnEnterKey('stockReceipt',event.keyCode);"/>
            				
            				<html:button styleClass="btnintgrid" property="FIND" styleId="" alt="Search" onclick="find('stockReceipt');">
            					<bean:message key="button.search" />
			            	</html:button>
            			</td>
		            </tr>
		        </table>
		        
		   </div>
              
		   <div id="demo">
		   
		   		<div class="title">
		           <div class="title2"><bean:message key="stock.label.issue.stockDetails" /></div>
		        </div>
		        
		        <table cellpadding="0" cellspacing="0" border="0" class="display" id="returnGrid" width="100%">
		            <thead>
		            <tr>
		       		  	 <th width="1%" align="center" ><input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('to.checkbox',this.checked);" tabindex="-1"/></th>	
		                 <th width="1%" align="center" ><bean:message key="stock.label.issue.SrNo"/></th>
		                 <th width="5%" align="center" ><bean:message key="stock.label.issue.materialType"/></th>
		                 <th width="3%" align="center" ><bean:message key="stock.label.issue.materialCode"/></th>
		                 <th width="5%" align="center" ><bean:message key="stock.label.issue.description"/></th>
		                 <th width="2%"><bean:message key="stock.label.issue.uom"/></th>
		                 <th width="3%"><bean:message key="stock.label.issue.approvedQty"/></th>
		                 <th width="3%"><bean:message key="stock.label.issue.issuedQty"/></th>
		                 <th width="3%"><bean:message key="stock.label.return.receivingQty"/></th>
		                 <th width="3%"><sup class="star">*</sup><bean:message key="stock.label.return.returningQty"/></th>
		                 <th width="5%"><sup class="star">*</sup><bean:message key="stock.label.return.startSrNum"/></th>
		                 <th width="5%"><bean:message key="stock.label.return.endSrNum"/></th>
		                 <th width="5%"><bean:message key="stock.label.issue.remarks"/></th>
		             </tr>
		             </thead>
		          
		           <tbody>
		           	<c:forEach var="itemDtls" items="${stockReturnForm.to.returnItemDetls}" varStatus="loop">
		           		<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}"> 
		            		  <td><html:checkbox property="to.checkbox" value="${loop.count-1}" styleId="checkbox${loop.count}" tabindex="-1" /></td>
		                	  <td><label><c:out value="${loop.count}"/></label></td>
		                 <td class="center">
                      		<html:select property="to.rowItemTypeId" styleId="rowItemTypeId${loop.count}" 
                      			styleClass="selectBox width170" disabled="true" tabindex="-1"><!-- style="width: 100px"-->
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
                      		styleClass="selectBox width170" disabled="true" tabindex="-1"><!-- style="width: 100px" -->
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
		                      
			                      <td><html:text styleId="rowDescription${loop.count}" property="to.rowDescription" value="${itemDtls.description}" styleClass="txtbox width145" size="11" readonly="true" tabindex="-1"/></td>
			                      <td><html:text styleId="rowUom${loop.count}" property="to.rowUom" value="${itemDtls.uom}" styleClass="txtbox width100" size="11" readonly="true" tabindex="-1"/></td>
			                      <td><html:text styleId="rowApprovedQuantity${loop.count}" property="to.rowApprovedQuantity" value="${itemDtls.approvedQuantity}" styleClass="txtbox width45" size="11"  maxlength="6" readonly="true" tabindex="-1"/></td> 
			                      <td><html:text styleId="rowIssuedQuantity${loop.count}" property="to.rowIssuedQuantity" value="${itemDtls.issuedQuantity}" styleClass="txtbox width45" size="11" maxlength="6" readonly="true" tabindex="-1"/></td>
			                     
			                      <td><html:text styleId="rowReceivingQuantity${loop.count}" property="to.rowReceivingQuantity" value="${itemDtls.receivedQuantity}" styleClass="txtbox width45" size="11" maxlength="6" readonly="true" tabindex="-1" /></td>
			                      <td>
			                      <html:text styleId="rowReturningQuantity${loop.count}" property="to.rowReturningQuantity" value="${itemDtls.returningQuantity}" styleClass="txtbox width45" size="11"  maxlength="6" onkeypress="return onlyNumeric(event)" onchange="checkQuantity('${loop.count}');seriesValidationWrapper('${loop.count}')" onkeydown = "return enterKeyforStockReturnQnty(event.keyCode,'${loop.count}');"/>
			                      </td>
			                      <td><html:text styleId="rowStartSerialNumber${loop.count}" property="to.rowStartSerialNumber" value="${itemDtls.startSerialNumber}" styleClass="txtbox width105" onchange="validateSeries(this)"  maxlength="12" size="14" onkeydown = "return enterKeyNav('rowRemarks${loop.count}' ,event.keyCode);"/></td>
			                      <td><html:text styleId="rowEndSerialNumber${loop.count}" property="to.rowEndSerialNumber" value="${itemDtls.endSerialNumber}" styleClass="txtbox width105"  readonly="true" maxlength="12" tabindex="-1" size="14"/></td>
			                      <td><html:text styleId="rowRemarks${loop.count}" property="to.rowRemarks" value="${itemDtls.remarks}" styleClass="txtbox width145"  maxlength="20" onkeydown="return enterKeyforStockReturn(event.keyCode,'${loop.count}','rowRemarks')"/></td>
			                     
			                      <html:hidden styleId="rowStockReturnItemDtlsId${loop.count}" property="to.rowStockReturnItemDtlsId" value="${itemDtls.stockReturnItemDtlsId}"  />
			                    
			                    <html:hidden property="to.rowSeries" styleId="rowSeries${loop.count}" value='${itemDtls.series}'  />
                   			    <html:hidden property="to.rowSeriesLength" styleId="rowSeriesLength${loop.count}" value='${itemDtls.seriesLength}'  />
                     		  <html:hidden property="to.rowIsItemHasSeries" styleId="rowIsItemHasSeries${loop.count}" value='${itemDtls.isItemHasSeries}'  />
                       
                      			 <html:hidden property="to.rowStockItemDtlsId" styleId="rowStockItemDtlsId${loop.count}" value='${itemDtls.stockItemDtlsId}'  />
		                        <html:hidden property="to.rowCurrentStockQuantity" styleId="rowCurrentStockQuantity${loop.count}" value='${itemDtls.currentStockQuantity}'  />
		                         <html:hidden property="to.rowBalanceReturnQuantity" styleId="rowBalanceReturnQuantity${loop.count}" value='${itemDtls.balanceQuantity}'  />
		                        
		                        
		                        <html:hidden property="to.rowSeriesType" styleId="rowSeriesType${loop.count}" value='${itemDtls.seriesType}'  />
		                        
		                       <html:hidden property="to.rowOfficeProduct" styleId="rowOfficeProduct${loop.count}" value='${itemDtls.officeProductCodeInSeries}'  />
                     			 <html:hidden property="to.rowStartLeaf" styleId="rowStartLeaf${loop.count}" value='${itemDtls.startLeaf}' />
                      			<html:hidden property="to.rowEndLeaf" styleId="rowEndLeaf${loop.count}" value='${itemDtls.endLeaf}' />
				                      
		                     
		                	</tr>
		           		</c:forEach>
		          </tbody>  
		        </table>
		    </div>
              
     <!-- Grid /--> 
</div>
</div>

		<html:hidden property="to.stockReturnId" styleId="stockReturnId"/>
		<html:hidden property="to.createdByUserId" styleId="createdByUserId"/>
		<html:hidden property="to.updatedByUserId" styleId="updatedByUserId"/>
		
        <html:hidden property="to.returningOfficeId" styleId="returningOfficeId"/>
        <html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
		<html:hidden property="to.loggedInUserId" styleId="loggedInUserId"/>
		<html:hidden property = "to.transactionFromType" styleId="transactionFromType"/>
			<html:hidden property = "to.issuedOfficeId" styleId="issuedOfficeId"/>
		
			
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
				
				<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode"/>
				<html:hidden property="to.canUpdate" styleId="canUpdate"/>
				<html:hidden property="to.issuedDate" styleId="issuedDate"/>
				<html:hidden property="to.officeType" styleId="officeType"/>
				<html:hidden property="to.officeRhoType" styleId="officeRhoType"/>
				<html:hidden property="to.officeTypeCo" styleId="officeTypeCo"/>
				
				
				
				
				
		

<!-- Button -->
          
   <div class="button_containerform">
   
   
   <c:choose>
			<c:when test="${not empty stockReturnForm.to.stockReturnId && stockReturnForm.to.stockReturnId>0}">
			<%-- <html:button property="Modify" styleClass="btnintform" onclick="save('Update');">
   			<bean:message key="button.modify" /></html:button> --%>
			</c:when>
			<c:otherwise>
			<html:button property="Save" styleClass="btnintform" onclick="save('Save');" styleId="Save">
   			<bean:message key="button.save" /></html:button>
   			
			</c:otherwise>
			</c:choose>
   		
   		<html:button property="Print" styleClass="btnintform" styleId="Print">
   			<bean:message key="button.print" /></html:button>
   		<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen('stockReturn')" styleId="Cancel">
   			<bean:message key="button.cancel" /></html:button>
   			
  	</div>
  	
<!-- Button ends --> 
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
