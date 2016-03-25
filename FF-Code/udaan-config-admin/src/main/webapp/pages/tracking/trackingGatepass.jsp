<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>  

<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquerydropmenu.js"/>
        <!-- DataGrids -->
        <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/common.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/tracking/gatePassTracking.js"></script>
        <script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "95",
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
					"iRightWidth": 10
				} );
			} );
		</script>
        <!-- DataGrids /-->

        <script>  
		$(function() {    $( "#tabs" ).tabs();
						  showDropDownBySelected("typename","GP");
						  $("#number").focus();
		});
        </script>
        <!-- Tabs ends /-->
        </head>
        <body>
<!--wraper-->
<div id="wraper"> 
   <html:form  action="/gatePassTracking.do" method="post" styleId="gatepassTrackingForm">
         
          <div id="main-header-int">
   
        </div>
          
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.tracking.title.gatepass"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      </div>
              <div class="formTable">
     	   <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td class="lable"><sup class="star"><bean:message key="symbol.common.star"/></sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
                    <td >
                    	<html:select property="to.type"  styleId="typename"  styleClass="selectBox width140" onkeypress="return callEnterKey(event,document.getElementById('number'));">
                          <%-- <html:option value="" >--Select--</html:option> --%>
                   		<c:forEach var="type" items="${typeNameTo}"  varStatus="loop">
		                  <html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>
		                </c:forEach>
                	</html:select>
                </td>
                   		 <td class="lable"><sup class="star"><bean:message key="symbol.common.star"/></sup>&nbsp;<bean:message key="label.tracking.Number"/></td>
                   	 		<td><html:text property="to.number" styleId="number" styleClass="txtbox width145" maxlength="12" onkeypress="return callEnterKey(event,document.getElementById('trackBtn'));"/>
                      	&nbsp;
                      		<html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackGatepass();">
								<bean:message key="button.label.track"/></html:button></td>
                  </tr>
                  
                  <tr>
            <td colspan="4" class="lable1" align="center"></td>
          </tr>
                </table>
</div>
              
              <div class="formbox">
        <h1><bean:message key="label.tracking.Status"/></h1>
       
      </div>
              <div id="tabs">
        <ul>
                  <li><a href="#tabs-1"><bean:message key="label.tracking.tab.trackingInfo"/></a></li>                 
                </ul>
        <div id="tabs-1">
                  <div class="formTable">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                <td width="19%" class="lable"><bean:message key="label.ConsignmentTracking.originOffice"/></td>
                <td width="20%" ><input type="text" name="originoffice" id="originoffice" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="34%" class="lable"><bean:message key="label.ConsignmentTracking.destinationOffice"/></td>
                <td width="27%"><input type="text" name="destination" id="destination" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
                      <tr>
                        <td class="lable"><bean:message key="label.Tracking.bagDispatched"/></td>
                        <td ><input type="text" name="bagDispatched" id="bagDispatched" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                        <td class="lable"><bean:message key="label.Tracking.bagReceived"/></td>
                        <td><input type="text" name="bagReceived" id="bagReceived" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                      </tr>
                      <tr>
                <td width="19%" class="lable"><bean:message key="label.Tracking.dispatchedwt"/></td>
                <td width="20%" ><input type="text" name="dispatchedwt" id="dispatchedwt" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="34%" class="lable"><bean:message key="label.Tracking.receivedwt"/></td>
                <td><input type="text" name="receivedwt" id="receivedwt" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td width="19%" class="lable"><bean:message key="label.Tracking.mode"/></td>
                <td width="20%" ><input type="text" name="mode" id="mode" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="34%" class="lable"><bean:message key="label.Tracking.ftvNo"/></td>
                <td><input type="text" name="ftvNo" id="ftvNo" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td width="19%" class="lable"><bean:message key="label.Tracking.dispatchedDate"/></td>
                <td width="20%" ><input type="text" name="dispatchedDate" id="dispatchedDate" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="34%" class="lable"><bean:message key="label.Tracking.receiveDate"/></td>
                <td><input type="text" name="receiveDate" id="receiveDate" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
                      <tr>
                <td colspan="4" class="lable1" align="center"></td>
              </tr>
                    </table>
          </div>
                  <div>
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="90%">
                      <thead>
                <tr>
                          <th align="center"><bean:message key="label.Tracking.bplMbplNo"/></th>
                          <th align="center"><bean:message key="label.Tracking.actualWeight"/></th>
                          <th align="center"><bean:message key="label.Tracking.status"/></th>
                          <th align="center"><bean:message key="label.Tracking.rcvDate"/> &amp; <bean:message key="label.Tracking.time"/></th>
                          <th align="center"><bean:message key="label.Tracking.remarks"/></th>
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
          <!-- footer ends --> 
         
           </html:form>
        </div>
<!--wraper ends-->
</body>
</html>