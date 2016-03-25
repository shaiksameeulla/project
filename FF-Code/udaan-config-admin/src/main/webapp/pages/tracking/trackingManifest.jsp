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
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/common.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/tracking/manifestTracking.js"></script>
        <script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
        <script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				manifestInfo('example');
			} );			
		</script>
        <script type="text/javascript" charset="utf-8">
			 $(document).ready( function () {
				 manifestInfo('example2');
			} );
		</script>
		<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script>  
		$(function() {    $( "#tabs" ).tabs();  
						  $("#typeName").focus();	
		});  
        </script>
        <!-- Tabs ends /-->
        </head>
        <body>
<!--wraper-->
<div id="wraper"> 
         <html:form  action="/manifestTrackingHeader.do" method="post" styleId="manifestTrackingForm">
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><strong>Manifest Tracking </strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      </div>
        <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
          <tr>
            <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.tracking.ManifestType"/></td>
            <td width="26%" >
               <html:select property="to.type"  styleId="typeName"  styleClass="selectBox width140" onkeypress="return callEnterKey(event,document.getElementById('manifestNumber'));">
               <html:option value="" >--Select--</html:option>
               		<c:forEach var="type" items="${typeNameTo}"  varStatus="loop">
		            	<html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>
		             </c:forEach>
                </html:select>
            </td>
            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.tracking.ManifestNumber"/></td>
            <td width="18%"><html:text property="to.manifestNumber" styleId="manifestNumber" styleClass="txtbox width145" maxlength="12" onkeypress="return callEnterKey(event,document.getElementById('trackBtn'));"/>&nbsp;
                <html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackmanifest();"><bean:message key="button.label.track"/></html:button>
          </tr>
          <tr>
            <td colspan="4" class="lable1" align="center">Manifest Tracking Information for <strong>Manifest No. <label id="maniNum" ></label></strong></td>
          </tr>
          <tr>
            <td colspan="4" class="lable1" align="center"></td>
          </tr>
        </table>
        </div>
      <div id="tabs">
        <ul>
            <li onclick="manifestInfo('example');"><a href="#tabs-1">First Out Manifest Info</a></li>
            <li onclick="manifestInfo('example1');"><a href="#tabs-2">Latest In Manifest Info</a></li>
            <li onclick="manifestInfo('example2');"><a href="#tabs-3">Detailed Tracking</a></li>
        </ul>
        <div id="tabs-1">
        <div class="formTable">
           <table border="0" cellpadding="0" cellspacing="5" width="100%">
             <tr>
                <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.originOffice"/></td>
                <td width="16%" ><input type="text" name="originoffice" id="originoffice" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.destinationOffice"/></td>
                <td width="16%"><input type="text" name="destination" id="destination" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
                <td width="18%"><input type="text" name="type" id="type" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.actualWeight"/></td>
                <td width="16%" ><input type="text" name="actualwt" id="actualwt" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.Tracking.manifestdate"/></td>
                <td width="16%"><input type="text" name="manifestdate" id="manifestdate" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td colspan="6" class="lable1" align="center"></td>
              </tr>
            </table>
          </div>
          <div>
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
              <thead>
                <tr>
                    <th align="center">Consignments/Packets/Bags No.</th>
                    <th align="center">Manifest Direction</th>
                    <th align="center">Actual Weight</th>
                    <th align="center">Destination</th>
               </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
     </div>
     <div id="tabs-2">
         <div class="formTable">
           <table border="0" cellpadding="0" cellspacing="5" width="100%">
              <tr>
                <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.originOffice"/></td>
                <td width="16%" ><input type="text" id="inOriginoffice" name="originoffice" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.destinationOffice"/></td>
                <td width="16%"><input type="text" id="inDestination" name="destination" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.tracking.operatingOffice"/></td>
                <td width="18%"><input type="text" id="OperatingOff" name="OperatingOff" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ConsignmentTracking.actualWeight"/></td>
                <td width="16%" ><input type="text" id="inActualwt" name="actualwt" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.Tracking.manifestdate"/></td>
                <td width="16%"><input type="text" id="inManifestdate" name="manifestdate" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
                <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.tracking.receiveStatus"/></td>
                <td width="16%"><input type="text" id="receiveStatus" name="receiveStatus" class="txtbox width110" tabindex="-1" size="11" readonly="true"/></td>
              </tr>
              <tr>
                <td colspan="6" class="lable1" align="center"></td>
              </tr>
           </table>
          </div>
          <div>
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example1" width="100%">
               <thead>
                <tr>
                    <th align="center">Consignments/Packets/Bags No.</th>
                    <th align="center">Manifest Direction</th>
                    <th align="center">Actual Weight</th>
                    <th align="center">Destination</th>
                </tr>
               </thead>
               <tbody>
               </tbody>
             </table>
          </div>
        </div>
        <div id="tabs-3">
          <div class="formTable">
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example2" width="100%">
              <thead>                
                     <tr>
                         <th width="2%" align="center">Sr.No.</th>
                         <th width="9%" align="center">Manifest Type</th>
                         <th width="9%" align="center">Date &amp; Time</th>
                         <th width="48%" align="center">Manifest Path</th>
                     </tr>                
              </thead>
              <tbody></tbody>
             </table>
          </div>
        </div>
      </div>
              <!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
  
           
            <div class="button_containerform">
			<html:button property="Clear" styleClass="btnintform" styleId="clearBtn" onclick="clearScreen('clear');">
				<bean:message key="button.clear"/></html:button>
		 </div>
         
         
          
          <!-- <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div> -->
          <!-- footer ends --> 
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>
