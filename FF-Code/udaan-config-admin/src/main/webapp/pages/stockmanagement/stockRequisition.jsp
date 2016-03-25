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
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script> --><!-- DataGrids -->
<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript"  src="js/jquery.dataTables.js"></script>
<script type="text/javascript"  src="js/FixedColumns.js"></script> 
<script type="text/javascript"  src="js/common.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockRequisition.js"></script>
<script type="text/javascript"  src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript">

$(document).ready( function () {
	var oTable = $('#reqGrid').dataTable( {
		"sScrollY": "220",
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
	
	requisitinStartup();
	var url="${url}";
	if(!isNull(url)){
		globalFormSubmit(url,'createRequisitionForm');
	}
} );

</script>
<!-- DataGrids /-->
</head>
<body>
<html:form  method="post" styleId="createRequisitionForm">
<!--wraper-->
<div id="wraper"> 
	<!-- main content -->
	<div class="clear"></div>
    <div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
        		<h1><bean:message key="stock.lable.req.streqbranch"/></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="stock.label.issue.mandatory"/></div>
      		</div>
            <div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="5" width="100%">
                	<tr>
            			<td width="4%" class="lable"><bean:message key="stock.label.issue.date"/> <strong>/</strong> <bean:message key="stock.label.issue.time"/>:</td>
            			<td width="10%"><html:text styleId="reqCreatedDateStr" property="to.reqCreatedDateStr" readonly="true" tabindex="-1" styleClass="txtbox width145" />
            			<%-- <html:text styleId="reqCreatedTimeStr" property="to.reqCreatedTimeStr" readonly="true" tabindex="-1"/> --%></td>
            			<td width="4%" class="lable"><bean:message key="stock.label.issue.requisitionNum"/>:</td>
            			<td width="10%">
            				<html:text property="to.requisitionNumber" styleId="requisitionNumber" styleClass="txtbox width145" maxlength="12" size="14" onkeydown="return findOnEnterKey('create',event.keyCode);"/>
            				<!-- <a href="#" id="Find" onclick="find('create')"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
            				<%-- <html:button styleClass="searchButton" styleId="Find" property="FIND" alt="Search" onclick="find('create')"/> --%>
            				<html:button styleClass="btnintgrid" styleId="Find" property="FIND" alt="Search" onclick="find('create')">
            					<bean:message key="button.search" />
            				</html:button>
            				
            			</td>
            		</tr>
           			<tr>
            			<td width="4%" class="lable"><bean:message key="stock.label.reqoffice"/>:</td>
			           	<td width="10%">
            				<html:text styleId="loggedInOfficeName" property="to.loggedInOfficeName" styleClass="txtbox width145" readonly="true" tabindex="-1"/>&nbsp;
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
        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="reqGrid" width="100%">
                	<thead>
            			<tr>
            				<!-- <th width="4%" align="center">
                        		<input type="checkbox" name="chkAll" id="chkAll" onclick="return checkAllBoxes('to.checkbox',this.checked);" tabindex="-1"/>
            				</th> -->
                      		<th width="3%" align="center" ><bean:message key="stock.label.issue.SrNo"/></th>
                      		<th width="20%" align="center" ><bean:message key="stock.label.issue.materialType"/></th>
                      		<th width="18%" align="center" ><sup class="star">*</sup><bean:message key="stock.label.issue.materialCode"/></th>
                      		<th width="16%" align="center" ><bean:message key="stock.label.issue.description"/></th>
                      		<th width="11%"><bean:message key="stock.label.issue.uom"/></th>
                      		<th width="7%"><sup class="star">*</sup><bean:message key="stock.label.issue.requestedQty"/></th>
                      		
                      		
                      		<c:if test="${createRequisitionForm.to.isApproved eq createRequisitionForm.to.approveFlagYes}">
									<th width="8%" scope="col"><bean:message key="stock.label.issue.approvedQty"/>
                                    </th>
                                    </c:if>
                      		<th width="17%"><bean:message key="stock.label.issue.remarks"/></th>
                    	</tr>
          			</thead>
                	<tbody>
						<c:forEach var="itemDtls" items="${createRequisitionForm.to.reqItemDetls}" varStatus="stat">
                        <tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}">
                        	<%-- <td><html:checkbox property="to.checkbox" value='${stat.count-1}' styleId="checkbox${stat.count}"/></td> --%>
                            <td><label><c:out value="${itemDtls.rowNumber}"/> </label></td>
                            <td>
                            	<html:select property="to.rowItemTypeId" styleId="rowItemTypeId${stat.count}" 
                            		styleClass="selectBox width170" onchange="getItemList(this)" disabled="true"> <!-- style="width: 100px" -->
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
                              	<html:select property="to.rowItemId" styleId="rowItemId${stat.count}" 
                              		styleClass="selectBox width170" onchange="isDuplicateRowExist(this)" disabled="true"><!-- style="width: 100px" -->
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
                            <td><html:text styleId="rowDescription${stat.count}" property="to.rowDescription"  styleClass="txtbox width145" value='${itemDtls.description}' readonly="true" tabindex="-1"/></td> 
                            <td><html:text styleId="rowUom${stat.count}" property="to.rowUom" styleClass="txtbox width100" value='${itemDtls.uom}' readonly="true" tabindex="-1" /></td>
                            <td><html:text styleId="rowRequestedQuantity${stat.count}" property="to.rowRequestedQuantity" styleClass="txtbox width40"  value='${itemDtls.requestedQuantity}' maxlength="6" onkeypress="return onlyNumeric(event);" onkeydown = "return enterKeyNav('rowRemarks${stat.count}' ,event.keyCode);"/></td>
                             <c:if test="${createRequisitionForm.to.isApproved eq createRequisitionForm.to.approveFlagYes}">
                             <td><html:text styleId="rowApprovedQuantity${loop.count}" property="to.rowApprovedQuantity" styleClass="txtbox width40" value='${itemDtls.approvedQuantity}' maxlength="6" onkeypress="return onlyNumeric(event)" readonly="true"/></td>
                            </c:if>
                            <td><html:text styleId="rowRemarks${stat.count}" property="to.rowRemarks" styleClass="txtbox width145" value='${itemDtls.remarks}' maxlength="50" onkeydown="focusOnNextRowOnEnter(event.keyCode,this,'reqGrid','rowRemarks','rowRequestedQuantity')"/>
                            	<html:hidden styleId="rowStockReqItemDtlsId${stat.count}" property="to.rowStockReqItemDtlsId"   value='${itemDtls.stockRequisitionItemDtlsId}'  />
                                <html:hidden property="to.rowNumber" styleId="rowNumber${stat.count}" value='${itemDtls.rowNumber}'  />
                               	<html:hidden property="to.rowTransCreateDate" styleId="transCreateDate${stat.count}" value='${itemDtls.transactionCreateDateStr}'  />
                         	</td>
                     	</tr>
                        </c:forEach>
                	</tbody>
				</table>
			</div>
            <!-- Grid /--> 
		</div>
  	</div>
    <!-- Button  starts-->
	<div class="button_containerform">
		<c:choose>
			<c:when test="${not empty createRequisitionForm.to.stockRequisitionId && createRequisitionForm.to.stockRequisitionId>0}">
			<html:button property="Update" styleId="Update" styleClass="btnintform"
				title="Save" onclick="save('Update')"><bean:message key="button.update" /></html:button>
			</c:when>
			<c:otherwise>
				<html:button property="Save" styleId="Save" styleClass="btnintform"	
					title="Save" onclick="save('Save')"><bean:message key="button.save" /></html:button>
			</c:otherwise>
		</c:choose>
		<html:button property="Print" styleId="Print" styleClass="btnintform" title="Print">
			<bean:message key="button.print" /></html:button>
		<html:button property="Cancel" styleId="Cancel"	styleClass="btnintform" 
			title="Cancel" onclick="clearScreen('create')"><bean:message key="button.cancel" /></html:button>
		<%-- <html:button property="Add" styleId="Add" styleClass="btnintform"
			title="Add" onclick="isValidForAddNewRow();"><bean:message key="button.add" /></html:button> --%>
			
	</div>
	<!-- Button ends --> 
    <!-- main content ends --> 
	
	<!--hidden fields start from here --> 
		
	<html:hidden property="to.stockRequisitionId" styleId="stockRequisitionId"/>
	<html:hidden property="to.loggedInUserId"/>
	<html:hidden property="to.createdByUserId"/>
	<html:hidden property="to.updatedByUserId"/>
	<html:hidden property="to.requisitionOfficeId"/>
	<html:hidden property="to.loggedInOfficeCode"/>
	<html:hidden property="to.loggedInOfficeId"/>
	<html:hidden property="to.canUpdate" styleId="canUpdate"/>
	<html:hidden property="to.loggedInRho"/>
    <html:hidden property="to.approveFlagYes" styleId="approveFlagYes"/>
    
	<!--hidden fields ENDs  here --> 
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
