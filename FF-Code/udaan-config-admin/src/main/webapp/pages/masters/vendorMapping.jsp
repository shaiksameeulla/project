<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
		 <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		
		<script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript" ></script>
		<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" charset="utf-8" src="js/masters/vendorMapping.js"></script>
		<script type="text/javascript" charset="utf-8">
             $(document).ready( function () {
	           vendorMappingStartUp();
            });

            var data = new Array();
             
            function loadVendorList(){
              showProcessing();
           	  jQuery('input#vendorName').flushCache();
           	  
           	  <c:forEach var="vendorTO" items="${vendorList}" varStatus="rstatus"> 
           	   data['${rstatus.index}'] =  "${vendorTO}";
           	  </c:forEach> 
           	 	
             jQuery("#vendorName").autocomplete(data);
             jQuery.unblockUI();
             document.getElementById("vendorName").focus();
           } 
       </script>
		<!-- DataGrids /-->
		</head>
<body onload="loadVendorList();" >
<!--wraper-->
<div id="wraper"> 
   <html:form method="post" styleId="vendorMappingForm">
          <!--header-->
          <!--top navigation-->
          <!--top navigation ends--> 
          <!--header ends-->
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="lavel.master.vendorMappingHeader" /></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.common.FieldsareMandatory" /></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.vendorName"  /></td>
                    <td width="23%" ><html:text property="to.vendorName" styleId="vendorName" styleClass="txtbox width145"/></td>
                    <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.vendorCode" /></td>
                    <td width="23%" ><html:text property="to.vendorCode" styleId="vendorCode" styleClass="txtbox width145" onfocus="getVendorMappingDetails();"  readonly="true"/>
                      &nbsp;</td>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.address" /></td>
                    <td ><textarea name="to.address" id="address" class="width145"  readonly="readonly">
                      
                    </textarea></td>
                  </tr>
                  <tr>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.region" /></td>
                    <td ><html:select property="to.regionTo" onfocus="getVendorMappingDetails();"
										styleId="regionList" styleClass="selectBox width140" onchange="enabledFields();clearFields();getStationsList();" >
										
									</html:select></td>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.station" /></td>
                    <td><html:select property="to.stationTo"
										styleId="stationList" styleClass="selectBox width140" onchange="getBranchesList();clearFields();">
										<html:option value=""><bean:message key="label.common.select"/></html:option>
										<%-- <c:forEach var="type" items="${productTo}" varStatus="loop">
											<html:option value="${type.productId},${type.consgSeries}">
												<c:out value="${type.productName}" />
											</html:option>
										</c:forEach> --%>
									</html:select></td>
                    <!-- <td class="lable">&nbsp;</td>
                    <td >&nbsp;</td> -->
                     <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="lavel.master.vendorMapping.service" /></td>
                     <td width="23%" ><html:text property="to.vendorName" styleId="service" styleClass="txtbox width145"    readonly="true"/></td>
                  </tr>
                  <tr>
                    <td colspan="6" >
                    	<table border="0" cellpadding="0" cellspacing="0" width="50%">
                          <tr>
                          <td width="5%" class="lable1">&nbsp;</td>
                          <td width="21%" style="background-color:#ebf1fe"><bean:message key="lavel.master.vendorMapping.office" /> <br />
                              	<select multiple="multiple" name="officeList" id="officeList"
																class="selectBox width200">
                              <!-- <option value="1">Office 1</option>
                              <option value="2">Office 2</option>
                              <option value="3">Office 3</option>
                              <option value="4">Office 4</option>
                              <option value="5">Office 5</option>
                              <option value="6">Office 6</option> -->
                            </select></td>
                          <td width="5%" class="lable1"><input name="Add" type="button" value="" class="btnintmultiselectr"  title="Add" onclick="addOffices();"/>
                              <br />
                              <input name="Add" type="button" value="" class="btnintmultiselectl"  title="remove" onclick="removeOffices();"/></td>
                          <td width="21%" style="background-color:#ebf1fe"><bean:message key="lavel.master.vendorMapping.officeSelected" />
                         <select multiple="multiple" name="officeListSelect" id="officeListSelect"
																class="selectBox width200">
                              <!-- <option value="1">Selected Office 1</option>
                              <option value="2">Selected Office 2</option>
                              <option value="3">Selected Office 3</option>
                              <option value="4">Selected Office 5</option>
                              <option value="5">Selected Office 6</option>
                              <option value="6">Selected Office 7</option> -->
                            </select></td>
                        </tr>
                        </table>
                    </td>
                  </tr>
                </table>
                <input type="hidden" name="vendorId" value="${vendorDetail.vendorId }"/>  
</div>
              
              
              <!-- Grid /--> 
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
          <div class="button_containerform">
          <html:button property="Edit" styleClass="btnintform"
					styleId="editBtn" onclick="editVendorMapping();">
					<bean:message key="button.edit" />
				</html:button>
				
				 <html:button property="Save" styleClass="btnintform"
					styleId="saveBtn" onclick="saveOrUpdateVendorMapping();">
					<bean:message key="button.label.save" />
				</html:button>
				
				 <html:button property="Clear" styleClass="btnintform"
					styleId="clearBtn" onclick="clearVendorDetails();">
					<bean:message key="button.label.Clear" />
				</html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div>
          <!-- footer ends --> 
          
          </html:form>
        </div>
<!--wraper ends-->
</body>
</html>
