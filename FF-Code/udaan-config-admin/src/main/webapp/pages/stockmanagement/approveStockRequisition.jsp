<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<!-- 	<script type="text/javascript" src="js/jquerydropmenu.js"></script>DataGrids -->
<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript"  src="js/jquery.dataTables.js"></script>
<script type="text/javascript"  src="js/FixedColumns.js"></script> 
<script type="text/javascript"  src="js/common.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockRequisition.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		var oTable = $('#approveTable').dataTable( {
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
		var isCorpOffice="${corpType}";
		if(!isNull(isCorpOffice)){
			setForCoOffice(true);
		}
		disableForApprove();
		var url="${url}";
		
		if(!isNull(url)){
			globalFormSubmit(url,'createRequisitionForm');
		}
	});
</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<div id="wraper"> 
    <!-- main content -->
    <html:form  method="post" styleId="createRequisitionForm">
    <div id="maincontent">
    	<div class="mainbody">
    		
        	<div class="formbox">
        		<h1><bean:message key="stock.lable.req.streqrho"/></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory"/></div>
      		</div>
            <div class="formTable">
               	<table border="0" cellpadding="0" cellspacing="5" width="100%">
                 	<tr>
            			<td width="4%" class="lable"><bean:message key="stock.label.issue.date"/> <strong>/</strong> <bean:message key="stock.label.issue.time"/>:</td>
            			<td width="10%"><html:text styleId="reqCreatedDateStr" property="to.reqCreatedDateStr" readonly="true" tabindex="-1" styleClass="inputFieldGrey"/>
            			<%-- <html:text styleId="reqCreatedTimeStr" property="to.reqCreatedTimeStr"  readonly="true" tabindex="-1"/> --%></td>
            			<td width="4%" class="lable"><bean:message key="stock.label.issue.requisitionNum"/>:</td>
            			<td width="10%"><html:text property="to.requisitionNumber" styleId="requisitionNumber"  styleClass="txtbox width145" maxlength="12" size="14" onkeydown="return findOnEnterKey('approve',event.keyCode);"/>
            				<!-- <a href="#" id="Find" onclick="find('approve')"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
            				<%-- <html:button styleClass="searchButton" property="FIND"  styleId="Find" alt="Search" onclick="find('approve')" /> --%>
            				<html:button styleClass="btnintgrid" property="FIND"  styleId="Find" alt="Search" onclick="find('approve')" >
            					<bean:message key="button.search" />
			            	</html:button>
            			</td>
            		</tr>
           			<tr>
            			<td width="4%" class="lable"><bean:message key="stock.label.reqoffice"/>:</td>
			            <td width="10%">
            				<%-- <html:text styleId="loggedInOfficeName" property="to.loggedInOfficeName" styleClass="inputFieldGrey" readonly="true" tabindex="-1"/> --%>
                        	<html:text styleId="requisitionOfficeName" property="to.requisitionOfficeName" styleClass="inputFieldGrey" readonly="true" tabindex="-1"/>
                        	&nbsp;
                        </td>
			            <td width="4%">&nbsp;</td>
			            <td width="10%">&nbsp;</td>
            		</tr>
                </table>
       		</div>
            <div id="demo">
        		<div class="title">
                	<div class="title2"><bean:message key="stock.label.issue.stockDetails"/></div>
                </div>
				<table cellpadding="0" cellspacing="0" border="0" class="display" id="approveTable" width="100%">
                <thead>
            		<tr>
            		 <th width="4%" align="center">
                        		<input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllForApprove('to.checkbox',this.checked);" tabindex="-1"/>
            				</th> 
                      <th width="2%" align="center" ><bean:message key="stock.label.issue.SrNo"/></th>
                      <th width="12%" align="center" ><bean:message key="stock.label.issue.materialType"/></th>
                      <th width="10%" align="center" ><bean:message key="stock.label.issue.materialCode"/></th>
                      <th width="18%" align="center" ><bean:message key="stock.label.issue.description"/></th>
                      <th width="7%"><bean:message key="stock.label.issue.uom"/></th>
                      <th width="7%"><bean:message key="stock.label.issue.requestedQty"/></th>
                      <th width="7%"><sup class="star">*</sup><bean:message key="stock.label.issue.approvedQty"/></th>
                       <th width="4%"><sup class="star">*</sup><bean:message key="stock.label.requisition.procrmnt"/></th>
                      <th width="6%"><bean:message key="stock.label.issue.remarks"/></th>
                    </tr>
                    </thead><tbody>
                     <c:forEach var="itemDtls" items="${createRequisitionForm.to.reqItemDetls}" varStatus="loop">
                           <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
                               <td><input type="checkbox" name="to.checkbox" value='${loop.count-1}' id="checkbox${loop.count}" onclick="isValidCheckBox(this)" tabindex="-1"/></td>
                               <td><label><c:out value="${itemDtls.rowNumber}"/> </label></td>
                               <td>
                               
                               	<html:select property="to.rowItemTypeId" styleId="rowItemTypeId${loop.count}"
									 disabled="true" tabindex="-1" styleClass="selectBox width170"><!-- style="width: 160px" -->
									<option value="">SELECT</option>
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
                               <td>
                               	<html:select property="to.rowItemId" styleId="rowItemId${loop.count}" 
                               		styleClass="selectBox width170" disabled="true" tabindex="-1"><!-- style="width: 160px" -->
									<option value="">SELECT</option>
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
                               <td><html:text styleId="rowDescription${loop.count}" property="to.rowDescription" styleClass="txtbox width145" value='${itemDtls.description}' readonly="true" tabindex="-1" /><!-- styleClass="inputFieldGrey" --></td> 
                                <td><html:text styleId="rowUom${loop.count}" property="to.rowUom" styleClass="txtbox width100" value='${itemDtls.uom}' readonly="true" tabindex="-1" /><!-- styleClass="inputFieldGrey" --></td>
                                <td><html:text styleId="rowRequestedQuantity${loop.count}" property="to.rowRequestedQuantity" styleClass="txtbox width60" value='${itemDtls.requestedQuantity}' readonly="true" tabindex="-1" size="8"/><!-- styleClass="inputFieldGrey" --></td>
                                <td><html:text styleId="rowApprovedQuantity${loop.count}" property="to.rowApprovedQuantity" styleClass="txtbox width60" value='${itemDtls.approvedQuantity}' maxlength="6" onkeypress="return onlyNumeric(event)" onkeydown = "return enterKeyNav('rowStockProcurementType${loop.count}' ,event.keyCode);" onchange="checkMandatoryForAdd('${loop.count}')" size="8"/></td>
                                 <td>
                                 <html:select property="to.rowStockProcurementType" styleId="rowStockProcurementType${loop.count}" 
                               		styleClass="selectBox width110" value='${itemDtls.procurementType}' onkeydown = "return enterKeyNav('rowRemarks${loop.count}' ,event.keyCode);"><!-- style="width: 160px" -->
									<logic:present name="procureType" scope="request">
                          			
                          			<c:forEach var="procuremt" items="${procureType}">

											<c:choose>
												<c:when	test="${procuremt.key==itemDtls.procurementType}">
													<option value="${procuremt.key}" selected="selected">
														<c:out value='${procuremt.value}' />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${procuremt.key}">
														<c:out value='${procuremt.value}' />
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
                          			
                          			</logic:present>
								</html:select>
                                 
                                 </td>
                                <td><html:text styleId="rowRemarks${loop.count}" property="to.rowApproveRemarks" styleClass="txtbox width145" value='${itemDtls.approveRemarks}' maxlength="50" onkeydown=" return enterKeyforStockApprove(event.keyCode,'${loop.count}','rowRemarks')"/>
                                <html:hidden property="to.rowStockReqItemDtlsId" styleId="rowStockReqItemDtlsId${loop.count}" value='${itemDtls.stockRequisitionItemDtlsId}'  />
                                <html:hidden property="to.rowNumber" styleId="rowNumber${loop.count}" value='${itemDtls.rowNumber}'  />
                               <input type="hidden" id="isApproved${loop.count}" name="isApproved" value="${itemDtls.isApproved}"/>
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
    			<html:button property="Approve" styleId="Approve" styleClass="btnintform"
					title="Approve" onclick="approve()">
					<bean:message key="button.approve" />
				</html:button>
				<!-- <input name="Edit" type="button" value="Edit" class="btnintform"  title="Edit"/> -->
				<html:button property="Print" styleId="Print"
					styleClass="btnintform" title="Print">
					<bean:message key="button.print" />
				</html:button>
				<html:button property="Cancel" styleId="Cancel"
					styleClass="btnintform" title="Cancel" onclick="clearScreen('approve')">
					<bean:message key="button.cancel" />
				</html:button>

				
  			</div>
		<!-- Button ends --> 
	<!-- main content ends --> 
     <!--hidden fields start from here --> 
           <html:hidden property="to.stockRequisitionId" styleId="stockRequisitionId"/>
           <html:hidden property="to.loggedInUserId"/>
           <html:hidden property="to.createdByUserId"/>
            <html:hidden property="to.updatedByUserId"/>
            <html:hidden property="to.requisitionOfficeId"/>
             <html:hidden property="to.loggedInOfficeId"/>
             <html:hidden property="to.loggedInOfficeCode"/>
             <html:hidden property="to.status" styleId="status"/>
             <html:hidden property="to.approveFlagYes" styleId="approveFlagYes"/>
             
             
             
            <!--hidden fields ENDs  here --> 
            </html:form> 
</div>
<!--wraper ends-->

</body>
</html>
