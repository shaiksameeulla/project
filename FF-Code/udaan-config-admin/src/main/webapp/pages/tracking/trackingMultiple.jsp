<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ page import="org.apache.struts.action.ActionMessages"%>

<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/tracking/multipleTracking.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
        <script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
        <script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "125",
					"sScrollX": "100%",
					"sScrollXInner":"350%",
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
					"iRightWidth": 10
				} );
				$("#consgRefNo").focus();
			} );
		</script>
        
       <!-- DataGrids /-->

        <!-- Tabs-->
        
        <script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
        <!-- Tabs ends /-->
        </head>
        <body>
<!--wraper-->
<div id="wraper"> 
          <!--header-->
          <html:form action="/multipleTracking.do" method="post" styleId="multipleTrackingForm">
          
          <!--header ends-->
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.tracking.multipleTracking.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf"><bean:message key="symbol.common.star"/></span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star"><bean:message key="symbol.common.star"/></sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
                    <td width="25%" ><html:select property="to.type"  styleId="typeName"  styleClass="selectBox width140" onkeypress="return callEnterKey(event,document.getElementById('consgRefNo'));">
                       <c:forEach var="type" items="${typeNameTo}"  varStatus="loop">
		                  <html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>                  
		                </c:forEach>
                </html:select></td>
                    <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.consignmentorrefno"/></td>
                    <td width="21%" valign="top">
                    
                   <textarea id="consgRefNo" rows="3" cols="50" autofocus="autofocus" wrap="OFF" style="text-transform: uppercase;" ></textarea>
                   <html:hidden property="to.consgNumber" styleId="trackingText"/>
            </td>
                    <td width="21%">
                    &nbsp;<html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackmultiple();">
								<bean:message key="button.label.track"/></html:button></td>
                  </tr>
                  <tr>
            <td colspan="5" align="center" class="lable1"><bean:message key="label.tracking.results"/></td>
          </tr>
                  <tr>
            <td colspan="4" class="lable1" align="center"></td>
          </tr>
                </table>
</div>
        <div id="tabs">
        <ul>
                  <li><a href="#tabs-1"><bean:message key="label.tracking.tab.trackingInfo"/></a></li>
                </ul>
        
        <div id="tabs-1">
                  <div>
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="90%">
                      <thead>
                        <tr>
                          <th align="center"><bean:message key="label.tracking.consignmentNo"/></th>
                          <th align="center"><bean:message key="label.tracking.referenceNo"/></th>
                          <th align="center"><bean:message key="label.tracking.bookingDate"/></th>
                          <th align="center"><bean:message key="label.tracking.Origin"/></th>
                          <th align="center"><bean:message key="label.tracking.Destination"/></th>
                          <%-- <th align="center"><bean:message key="label.tracking.Status"/></th> --%>                          
                          <th align="center"><bean:message key="label.tracking.Weight"/></th>
                          <th align="center"><bean:message key="label.tracking.Status"/></th>
                          
                          <th align="center">DRS NO</th>
                          <th align="center"><bean:message key="label.tracking.DeliveryDate"/></th>                          
                          <th align="center">Delivery Office</th>
                          <th align="center"><bean:message key="label.tracking.ReceiverName"/></th>
                          <th align="center">Third Party Name</th>                          
                          <th align="center"><bean:message key="label.tracking.PendingReason"/></th>
                          <%-- <th align="center"><bean:message key="label.tracking.DeliveryDate"/></th> --%>
                          <%-- <th align="center"><bean:message key="label.tracking.ReceiverName"/></th> --%>
                          <%-- <th align="center"><bean:message key="label.tracking.Weight"/></th> --%>
                          
                          <th align="center"><bean:message key="label.tracking.ogmNo"/></th>
                          <th align="center"><bean:message key="label.tracking.ogmDate"/></th>
                          <th align="center"><bean:message key="label.tracking.bplNo"/></th>
                          <th align="center"><bean:message key="label.tracking.bplDate"/></th>
                          
                          <th align="center"><bean:message key="label.tracking.cdNo"/></th>
                          <th align="center"><bean:message key="label.tracking.cdDate"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportNo"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportDep"/></th>
                          <th align="center"><bean:message key="label.tracking.TransportArr"/></th>
                          <th align="center"><bean:message key="label.tracking.ReceiveDate"/></th>
                          <th align="center"><bean:message key="label.tracking.InManifestDate"/></th>
                        </tr>
              </thead>
                    </table>
</div>
                </div>
      </div>
              <!-- Grid /--> 
            </div>
 
  </div>

   	  <div class="button_containerform">
			<html:button property="Clear" styleClass="btnintform" styleId="clearBtn" onclick="clearScreen('clear');">
				<bean:message key="button.clear"/></html:button>
		
		</div>
		
          <!-- Button ends --> 
          <!-- main content ends --> 
          
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>
