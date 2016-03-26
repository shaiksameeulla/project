<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 



<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/common.js" type="text/javascript"></script>
<script language="JavaScript" src="js/pickupmanagement/ConfirmPickupOrder.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">


$(document).ready(function() {
	var oTable = $('#confirm').dataTable({
		"sScrollY" : "200",
		"sScrollX" : "100%",
		"sScrollXInner" : "210%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
	setDataTableDefaultWidth();
});


</script>

<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<div id="wraper"><!--header-->	
<!-- main content -->
	

   
<html:form action="/confirmPickupOrder" method="post" styleId="confirmPickupOrderForm">
<div id="maincontent">      
      <div class="mainbody">		
        <div id="demo">
        <div class="title">
          <div class="title2"><bean:message key="label.confirm.pickup.title" locale="display"  /></div>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="confirm" width="100%" >
                <thead>
                  <tr>
                          <th style="border-bottom:0;"><input type='checkbox' value='selectall' id='selectall'/></th>
                          <th style="border-bottom:0;"><bean:message key="label.confirm.pickup.date" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.ordernumber" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.consignerName" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.address" locale="display"  /></th>
                           <th><bean:message key="label.confirm.pickup.city" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.pincode" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.productType" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.materialDesc" locale="display"  /></th>
                          <th><bean:message key="label.confirm.pickup.region" locale="display"  /></th>
                           <th><bean:message key="label.confirm.pickup.hub" locale="display"  /></th>
                           <th><bean:message key="label.confirm.pickup.branch" locale="display"  /></th>
                        </tr>
                </thead>
                <tbody>
                <!-- populate the grid with pending request details -->
					  <c:forEach var="pDetails" items="${confirmPickupOrderForm.to.detailsTO}" varStatus="loop">
                               <tr>
                               <td><input type="checkbox" name="to.checkbox" id="checkbox${loop.count}" value='${loop.count-1}'  /></td>
                                                          
                               <td><html:text styleId="requestDate${loop.count}" property="to.reqDate"  value='${pDetails.requestDate}' readonly="true"/> </td>
                               <td> <a id="orderNumber${loop.count}" href="#" onclick="createPickupOrder('${loop.count}','${pDetails.orderNumber}');">${pDetails.orderNumber}</a> </td>
                                <td><html:text styleId="consignnorName${loop.count}" property="to.consignnorName"  value='${pDetails.consignnorName}' readonly="true" /></td> 
                                <td><html:text styleId="address${loop.count}" property="to.address"  value='${pDetails.address}' readonly="true" /></td>
                                <td><html:text styleId="city${loop.count}" property="to.city"  value='${pDetails.cityName}' readonly="true" /></td>
                                <td><html:text styleId="pincode${loop.count}" property="to.pincode"  value='${pDetails.pincodeName}' readonly="true" /></td>
                                <td><html:text styleId="consignmentType${loop.count}" property="to.consignmentType"   value='${pDetails.consignmentName}' readonly="true" /></td>
                                <td><html:text styleId="materialDesc${loop.count}" property="to.materialDesc"   value='${pDetails.materialDesc}' readonly="true" /></td>
                                <td><html:text styleId="originatingRegionName${loop.count}" property="to.regionName"  value='${pDetails.originatingRegionName}' readonly="true" /></td>
                                <td><html:text styleId="originatingHubName${loop.count}" property="to.hubName"  value='${pDetails.originatingHubName}' readonly="true" /></td>
                                <td><html:text styleId="branchName${loop.count}" property="to.branchName"   value='${pDetails.originatingBranchName}' readonly="true" />
                                    <html:hidden property="to.orderBranchId"     value='${pDetails.revOrderBranchId}' ></html:hidden>
                                    <html:hidden property="to.assignedOfficeId"  value='${pDetails.assignedOfficeId}' ></html:hidden>
                                
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
    </div><!-- main content ends -->
<!-- Button -->
      <div class="button_containerform"> 
       
  <html:button styleClass="btnintform"  property="accept" onclick="acceptDetails()" > <bean:message key="label.confirm.pickup.accept" locale="display"  /></html:button>
  <!--  artf2965698 : Decline button is not required -->
  <%-- <html:button styleClass="btnintform"  property="decline" onclick="declineDetails()" > <bean:message key="label.confirm.pickup.decline" locale="display"  /></html:button> --%>
 </div>
 </html:form>
  </div><!-- Button ends -->


</body>

