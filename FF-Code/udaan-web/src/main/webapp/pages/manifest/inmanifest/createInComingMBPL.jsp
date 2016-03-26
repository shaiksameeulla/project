<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<html xmlns="http://www.w3.org/1999/xhtml">
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
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/bplOutManifestDox.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/inmanifest/inMasterBagManifest.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/inmanifest/inManifest.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script language="JavaScript" src="js/weightReader.js" type="text/javascript" ></script>
		
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		
		<script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#inManifestTable').dataTable( {
					"sScrollY": "250",
					"sScrollX": "100%",
					"sScrollXInner":"110%",
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
				getConsignmentTypeList();
			} );
			
			var wmWeight = 0.00;
			var wmWeightActual = 0.00;
			var weightInkgs=0;
			var weightInGms=0;
			var isWeighingMachineConnected = false;
			
		</script>
		<!-- DataGrids /-->
		<!-- Start param to get Remarks -->
		<jsp:include page="inManifest.jsp"/>	           
		<!-- End param to get Remarks   -->
		</head>
		<body onload="loadDefaultObjects()">
<!--wraper-->
<div id="wraper"> 

         	<html:form action="/inMasterBagManifest" method="post"	styleId="inMasterBagManifestForm">
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>In MBPL</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are mandatory</div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
           <tr>
           	<!-- Hidden Param Start -->
			<html:hidden property="to.manifestWeight" styleId="manifestWeight"/>
			<html:hidden property="" styleId="processCode" value="${processCode}"/>
			<html:hidden property="" styleId="updatedProcessCode" value="${updatedProcessCode}"/>
			<html:hidden property="" styleId="processCodeBPL" value="${processCodeBPL}"/>
			<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId" value="${LoginOfficeId}"/>
			<html:hidden property="to.loginOffName" styleId="loinOffName" value="${destinationOffice}" />
			<html:hidden property="to.manifestId" styleId="manifestId" value=""/>
			<html:hidden property="to.destinationOfficeTO.officeId" styleId="destOffcId" value=""/>
			<html:hidden property="to.destCityId" styleId="destCityId" value="${destCityId}" />
			<html:hidden property="" styleId="updatedProcessCodeBPL" value="${updatedProcessCodeBPL}" />
			<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId"/>
			<html:hidden property="to.manifestReceivedStatus" styleId="manifestReceivedStatus"/>
           	<!-- Hidden Param ENd -->
           	
            <td width="14%" class="lable">&nbsp;<bean:message key="label.manifest.dateTime"/></td>
            <td width="12%" ><html:text property="to.manifestDate" styleClass="txtbox width130" styleId="dateTime" readonly="true" value="${todaysDate}"/></td>
            
            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.mbplNo"/></td>
            <td width="22%"><html:text property="to.manifestNumber" styleClass="txtbox width130" styleId="mbplNo"  value="" onchange="searchManifestDetails();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'btnSearch','MBPL Number.');" maxlength="10"/> <!--  onkeypress="enterKeyNavigationFocus(event,'region');" -->
             &nbsp;<!-- <a href="#" title="Search"><img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a> -->
               <html:button styleClass="btnintgrid" styleId="btnSearch" property="" alt="Search" onclick="searchManifestDetails();" title="Search">
				<bean:message key="button.label.search"/>
				</html:button>
            </td>
            
            <td width="15%" class="lable"><bean:message key="label.inmanifest.bagLockNo"/></td>
            <td width="10%"><html:text property="to.lockNum" styleClass="txtbox width130" styleId="lockNum" onblur="isValidLockNo(this)" value=""/></td>
            
          </tr>
                  <tr>
            <td width="14%" class="lable">&nbsp;<bean:message key="label.inmanifest.destinationOffice"/></td>
            <td width="12%"><html:text property="to.destinationOfficeTO.officeName" styleClass="txtbox width130" styleId="officeName" readonly="true" value="${destinationOffice}" tabindex="-1"/></td>
            
            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originRHO"/></td>
            
               <td width="22%"><html:select property="to.originRegionTO.regionId" onchange="getCitiesByRegion();" styleId="region"  onkeypress="enterKeyNavigationFocus(event,'destCity');" styleClass="selectBox width130">
               <html:option value="" >--Select--</html:option>
             <c:forEach var="region" items="${regionTOs}"  varStatus="loop">
		                  <html:option value="${region.regionId}" ><c:out value="${region.regionName}"/></html:option>
		     </c:forEach>
            </html:select></td>
            
            
            <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originCity"/></td>
            <td width="10%"><html:select property="to.originCityTO.cityId" onkeypress="enterKeyNavigationFocus(event,'destOfficeType');" onchange="getAllOfficesByCity();" styleId="destCity" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0">---Select---</option>
                    	 </html:select></td>
          </tr>
                  <tr>
            <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOfficeType"/></td>
            <td width="12%"><html:select property="to.officeType" onkeypress="enterKeyNavigationFocus(event,'office');" onchange="getAllOfficesByCityAndOfficeType();" styleId="destOfficeType" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0">---Select---</option>
                         <c:forEach var="officeTypes" items="${officeTypeList}"  varStatus="loop">
		                <option value="${officeTypes.value}" ><c:out value="${officeTypes.label}"/></option>
		                 </c:forEach>
                    	 </html:select></td>
                    	 
            <td width="12%"class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOffice"/></td>
            <td width="22%"><html:select property="to.originOfficeTO.officeId" styleId="office" onchange="validateDestOriginOffice();" onkeypress="enterKeyNavigationFocus(event,'bplNo1');" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0" styleClass="txtbox width130" >---Select---</option>
                    	 </html:select></td>
            <td width="15%" class="lable">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
                </table>
</div>
      <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
       	 		<table cellpadding="0" cellspacing="0" border="0" class="display" id="inManifestTable" width="100%"> 
       	 		    <thead>
           			 <tr>
                     	<th width="3%" align="center" ><input type="checkbox"  class="checkbox" id="chk0" name="type" onchange="checkUncheckAllRows('inManifestTable','chk')"/></th>
                     	<th width="5%"><bean:message key="label.manifestGrid.serialNo"/></th>
                      	<th width="15%" align="center" ><sup class="star">*</sup><bean:message key="label.manifestGrid.bplNo"/></th>
                      	<th width="15%" align="center" ><bean:message key="label.manifestGrid.bagLockNo"/></th>
                      	<th width="15%"><sup class="star">*</sup><bean:message key="label.manifestGrid.weight"/></th>
                      	<th width="15%"><bean:message key="label.manifestGrid.destination"/></th> 
                      	<th width="11%"><bean:message key="label.manifestGrid.type"/></th> 
                      	<th width="15%"><bean:message key="label.remarks"/>
                    </tr>
         		 </thead>
                   <tbody>
                   </tbody>
            </table>
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
  <html:button property="Save"  styleClass="btnintform" styleId="save" onclick="saveOrUpdateInManifestMBPL()" >
    <bean:message key="button.label.Submit" locale="display" />
    </html:button>
     <html:button property="clear"  styleClass="btnintform" styleId="clear" onclick="clearDetails()">
    <bean:message key="button.label.clear" locale="display" />
    </html:button>
    <html:button property="Print"   styleClass="btnintform" styleId="print" onclick="printMbpl();">
    <bean:message key="button.label.Print" locale="display"/>
    </html:button>
    <html:button property="delete" styleId="delete" styleClass="btnintform"  onclick="deleteTableRow('inManifestTable')">
			<bean:message key="button.label.delete" locale="display" />
	</html:button>
   <!--  <input name="Close" type="button" class="btnintform" value="Close" title="Close"/> -->
  </div>
  </html:form>
          <!-- Button ends --> 
          <!-- main content ends --> 
          </div>
<!--wraper ends-->
<!-- <iframe name="iFrame" id="iFrame" width="0.5" height="0.5" > </iframe> -->
</body>


</html>
