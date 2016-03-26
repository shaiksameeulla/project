<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateCustomerInfo.js"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "300",
					"sScrollX": "100%",
					"sScrollXInner":"120%",
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
			} );
		</script>
		<!-- DataGrids /-->
		</head>
		<body>
<html:form action="/rateContract" method="post" styleId="rateContractForm">
<!--wraper-->
<div id="wraper"> 
          <!--header-->
          <!--top navigation-->
          <!--top navigation ends--> 
          <!--header ends-->
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><strong>Customer Info</strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are mandatory</div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star">*</sup><bean:message key="label.Rate.CustomerName"/></td>
                    <td width="16%" ><html:text property="to.custName" styleId="custName" styleClass="txtbox width130" onkeypress="return customerEnterKeyNav(event);"/></td>
                    <td width="44%" class="lable1"><html:button property="search" styleClass="btnintform" styleId="searchBtn" onclick="searchCustInfo()">Search</html:button></td>
                    <td width="27%">&nbsp;</td>
                  </tr>
                </table>
          </div>
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                  <thead>
            <tr>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.ListView.CreatedDate"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.Rate.ContractNo"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.Rate.RegionName"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.ListView.Station"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.Rate.CustName"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.Rate.SalesOfficeName"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.ListView.SalesPerson"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.ListView.GroupKey"/></th>
                      <th width="15%"><sup class="star">*</sup><bean:message key="label.Rate.Status"/></th>
                    </tr>
          </thead>
                  <tbody>
                  
             <c:forEach var="cust" items="${custTOs}" varStatus="loop">
                               <tr>
                               <td><html:text styleId="createdDate${loop.count}" property=""  value='${cust.createdDate}' readonly="true"/> </td>
                                <td><html:text styleId="contactNo${loop.count}" property=""  value='${cust.contractNo}' readonly="true" /></td> 
                                <td><html:text styleId="regionName${loop.count}" property=""  value='${cust.regionName}' readonly="true" /></td>
                                <td><html:text styleId="station${loop.count}" property=""  value='${cust.station}' readonly="true" /></td>
                                <td><html:text styleId="custName${loop.count}" property=""  value='${cust.customerName}' readonly="true" /></td>
                                <td><html:text styleId="salesOfficeName${loop.count}" property=""   value='${cust.salesOfficeName}' readonly="true" /></td>
                                <td><html:text styleId="salesPerson${loop.count}" property=""   value='${cust.salesPerson}' readonly="true" /></td>
                                <td><html:text styleId="groupKey${loop.count}" property=""  value='${cust.groupKey}' readonly="true" /></td>
                                <td><html:text styleId="status${loop.count}" property=""  value='${cust.status}' readonly="true" /></td>
                               </tr>
                     </c:forEach>
                    
  </tbody>
                </table>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
          
          <!-- Button -->
          <!--<div class="button_containerform">
    <input name="Edit" type="button" class="btnintform" value='Edit' title="Edit"/>
    <input name="Save" type="button" class="btnintform" value='Save' title="Save"/>
    <input name="Submit" type="button" class="btnintform" value='Submit' title="Submit"/>
  </div>-->
          <!-- Button ends --> 
          <!-- main content ends --> 
        </div>
<!-- wrapper ends -->
</html:form>
</body>
</html>
