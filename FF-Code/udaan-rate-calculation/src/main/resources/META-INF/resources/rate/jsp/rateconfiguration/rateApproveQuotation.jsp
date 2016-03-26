<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>Approve Domestic Quotation</title>
            <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
            <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
            <link href="css/global.css" rel="stylesheet" type="text/css" />
            <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
      		<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
     
    		<script type="text/javascript" src="js/jquerydropmenu.js"></script>
     		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
            <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
            <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
            <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
            <script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
            <script type="text/javascript" charset="utf-8" src="js/common.js"></script>
	 		<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
			<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
	 		<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateApproveQuotation.js"></script>
	 		<script type="text/javascript" charset="utf-8">
			
	 		var FROM_DATE = '${fromDate}'; <!-- This variable has been added to explicitly set the from date as the current date -->
	 		
	 		 	$(document).ready( function () {
					var oTable = $('#example').dataTable( {
					"sScrollY": "115",
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
			} );
	 		 	
	 		 	function showAlert(){
	 		 		var message="<c:out value='${message}'/>";
	 				if(message !=""){
	 					alert(message);
	 					//$("#btnSearch").removeClass('button');
	 					$("#btnSearch").addClass('button_disable');
	 					jQuery(":button").attr("disabled", true);
	 					}
	 				else{
	 					enableOrDisablebuttons(document.getElementById("quotatnStatus"));
	 					getQuotations();
	 				}
	 		 	}
				 </script>
	 	</head>
<body onload="showAlert()">
<html:form action="/rateQuotation?submitName=viewApproveDomesticQuotation" styleId="rateQuotationForm">
<div id="wraper">
<div class="clear"></div>
<!-- main content -->
<div id="maincontent">
<div class="mainbody">
		<div class="formbox">
          <h1><strong><bean:message key="label.Rate.ApproveDomesticQuotation" /></strong></h1>
          <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message
											key="label.Rate.fieldMandatory" /></div>
        </div>
        
         <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                    <td width="13%" class="lable"><bean:message
											key="label.Rate.fromDt" /></td>
                    <td width="15%" >
                    <html:text styleId="fromDate" property="to.fromDate" styleClass="txtbox width100" readonly="true" tabindex="-1" onfocus="validateFromDate(this);"/>
                      &nbsp;<a href="#" onclick="javascript:show_calendar('fromDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch" /></a></td>
                    <td width="20%" class="lable"><bean:message
											key="label.Rate.toDt" /></td>
                    <td width="15%">
                     <html:text styleId="toDate" property="to.toDate" styleClass="txtbox width100" readonly="true" tabindex="-1" onfocus="validateToDate(this);"/>
                      &nbsp;<a href="#" onclick="javascript:show_calendar('toDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a></td>
                    <td width="20%" class="lable"><bean:message
											key="label.Rate.paramStatus" /></td>
                    <td width="17%">
                    <html:select property="to.status" styleId="quotatnStatus" styleClass="selectBox width130" onchange="enableOrDisablebuttons(this);">
                            <c:forEach var="stockStandardTypeTO" items="${quotationStatus}" varStatus="loop">
		              		<html:option value="${stockStandardTypeTO.stdTypeCode}" >${stockStandardTypeTO.description}</html:option>
		            		</c:forEach>
                    </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td class="lable"><bean:message
											key="label.Rate.quotation" /></td>
                    <td width="15%">
                    <html:text property="to.rateQuotationNo" styleId="quotationNo" styleClass="txtbox width130"  maxlength="12"/>
                    </td>
                    <td width="15%">&nbsp;</td>
                    <td width="20%" class="lable">&nbsp;</td>
                    <td width="17%">&nbsp;</td>
                     <td class="lable1">
                    <html:button property="Search" styleClass="button" styleId="search" onclick = "getQuotations();">
 					<bean:message key="label.rate.search"/></html:button>
 					</td>
                  </tr>
                  <tr>
                    <td align="right" valign="top" colspan="6">&nbsp;</td>
                  </tr>
                </table>
			</div>


  		<div id="demo">
        <div class="title">
              <div class="title2"><bean:message
											key="label.Rate.details" /></div>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
             <thead>
          		  	<tr>
                      <th width="7%"><input type="checkbox" class="checkbox" name="chk0" id="chk0" onClick = "gridCheckAll();"/></th>
                      <!-- <th width="7%">Sr. No</th> -->
                      <th width="15%"><sup class="star">*</sup><bean:message
											key="label.ListView.CreatedDate" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.ListView.QuotationNo" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.Rate.RegionName" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.ListView.Station" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.Rate.CustName" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.Rate.SalesOfficeName" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.ListView.SalesPerson" /></th>
                      <th width="17%"><sup class="star">*</sup><bean:message
											key="label.Rate.Status" /></th>
                   </tr>
        	 </thead>
         
          </table>
  </div>
 <!-- Grid /--> 
 </div>
 </div>
  
  
 <!-- Button -->
 <div class="button_containerform">
 <html:button property="Approve" styleClass="btnintform" styleId="approveBtn" onclick = "approveQuotation('approve');">
 <bean:message key="label.rate.approve"/></html:button>
 <html:button property="Reject" styleClass="btnintform" styleId="rejectBtn" onclick = "approveQuotation('reject');">
 <bean:message key="label.rate.reject"/></html:button>
</div>
 <!-- Button ends -->
 <html:hidden property="to.userId" styleId="empId"/>
  <input type="hidden" name="type" id="type" value="${approver}"/>
  <input type="hidden" name="isEQApprover" id="isEQApprover" value="${isEQApprover}"/>   
 </div><!-- main content ends --> 



</html:form>
</body>
