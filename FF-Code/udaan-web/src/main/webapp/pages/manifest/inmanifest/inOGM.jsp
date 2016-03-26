<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script type="text/javascript" charset="utf-8" src="js/manifest/inmanifest/inMasterBagManifest.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/inmanifest/inOGMDox.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/inmanifest/inManifest.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/weightReader.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready( function () {
				var oTable = $('#inOGMDoxTable').dataTable( {
					"sScrollY": "220",
					"sScrollX": "100%",
					"sScrollXInner":"150%",
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
		<!-- Start param to get Remarks -->
	           	<jsp:include page="inManifest.jsp"/>	           
	    <!-- End param to get Remarks   -->
		<!-- DataGrids /-->
		</head>
		<body onload="loadDefaultObjects()">
<!--wraper-->
<div id="wraper"> 
          <!--header-->
     	<html:form action="/inOGMDoxManifest" method="post"	styleId="inOGMDoxManifestForm">
         
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>In OGM</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
			<tr>
	            <html:hidden property="" styleId="processCode" value="${processCode}"/>
	            <html:hidden property="" styleId="updatedProcessCode" value="${updatedProcessCode}"/>
	            <html:hidden property="" styleId="processCodeBPL" value="${processCodeBPL}"/>
	            <html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId" value="${LoginOfficeId}"/>
	            <html:hidden property="to.manifestId" styleId="manifestId" value=""/>
	            <html:hidden property="to.destinationOfficeTO.officeId" styleId="destOffcId" value=""/>
	            <html:hidden property="to.deletedIds" styleId="deletedIds"/>
	            <html:hidden property="to.headerRemarks" styleId="headerRemarks" />
	            <html:hidden property="to.manifestEmbeddedIn" styleId="manifestEmbeddedIn"/>
	            <html:hidden property="to.loginRegionOffice" styleId="loginRegionOffice" value="${destinationOffice}"/>
	            <html:hidden property="to.destCityId" styleId="destCityId" value="${destCityId}" />
				<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId"/>
            	<html:hidden property="to.consignmentTypeId" styleId="consignmentTypeId" value="${consignmentTypeId}"/>
				<html:hidden property="to.manifestReceivedStatus" styleId="manifestReceivedStatus"/>
              	<%-- <html:hidden  property="to.isOnlyComail" styleId="isOnlyComail" value=""/> --%>
                  	  
             	<td width="14%" class="lable">&nbsp;<bean:message key="label.manifest.dateTime"/></td>
            	<td width="15%" ><html:text property="to.manifestDate" styleClass="txtbox width130" styleId="dateTime" readonly="true" value="${todaysDate}"/></td>
           
           		<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.ogmNo"/></td>
            	<td width="25%" colspan="3"><html:text property="to.manifestNumber" styleClass="txtbox width130" styleId="ogmNo"  value="" maxlength="10" onchange="isValidManifest();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'btnSearch','OGM/Open Manifest No.');"/> <!-- onkeypress="enterKeyNavigation(event,'btnSearch');"  -->
	             	<html:button styleClass="btnintgrid" styleId="btnSearch" property="" alt="Search" onclick="isValidManifest();" title="Search" >
						<bean:message key="button.label.search"/>
					</html:button>
				</td>
            <!--<td width="15%" class="lable">Destination Region:</td>
            <td width="17%"><input name="textfield7" type="text" class="txtbox width130" value="based logged in user"/></td>-->
            
          </tr>
                  <tr>
           <td class="lable">&nbsp;<bean:message key="label.inmanifest.destinationOffice"/></td>
            <td width="19%"><html:text property="to.destinationOfficeTO.officeName" styleClass="txtbox width130" styleId="officeName" readonly="true" value="${destinationOffice}" tabindex="-1"/></td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originRHO"/></td>
            <td width="15%"> 
               <html:select property="to.originRegionTO.regionId" onkeypress="enterKeyNavigationFocus(event,'destCity');" onchange="getCitiesByRegion();" styleId="region"  styleClass="selectBox width130">
               <html:option value="" >--Select--</html:option>
             <c:forEach var="region" items="${regionTOs}"  varStatus="loop">
		                  <html:option value="${region.regionId}" ><c:out value="${region.regionName}"/></html:option>
		     </c:forEach>
            </html:select></td>
             <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originCity"/></td>
            <td width="17%"><html:select property="to.originCityTO.cityId" onkeypress="enterKeyNavigationFocus(event,'destOfficeType');" onchange="getAllOfficesByCity();" styleId="destCity" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0">---Select---</option>
                    	 </html:select></td>
          </tr>
                  <tr>
            
               <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOfficeType"/></td>
            <td width="19%"><html:select property="to.officeType" onkeypress="enterKeyNavigationFocus(event,'office');" onchange="getAllOfficesByCityAndOfficeType();" styleId="destOfficeType" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0">---Select---</option>
                         <c:forEach var="officeTypes" items="${officeTypeList}"  varStatus="loop">
		                <option value="${officeTypes.value}" ><c:out value="${officeTypes.label}"/></option>
		                 </c:forEach>
                    	 </html:select></td>
                    	 
             <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOffice"/></td>
            <td width="15%"><html:select property="to.originOfficeTO.officeId" styleId="office" onchange="validateDestOriginOffice();" onkeypress="enterKeyNavigationFocus(event,'consgNo1');" value="" styleClass="txtbox width130" >
                        <option selected="selected" value="0">---Select---</option>
                    	 </html:select></td>
                    	 
            <td class="lable" width="12%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.finalWeight"/></td>
            <td width="20%" class="lable1"> <html:hidden  property="to.totalManifestWeight" styleId="totalWeight"  value=""/>
	            <input type="text" class="txtbox width60" id="wtKg" disabled="disabled"  tabindex="-1" maxlength="3" size="2"/>.
	            <input type="text" class="txtbox width60" id="wtGm" disabled="disabled" tabindex="-1"  maxlength="3" size="2" />Kgs
            </td>
          </tr>
          <tr>
             <td class="lable"><sup class="star">*</sup>&nbsp;Co Mail:</td>
         <td width="19%"><input type="checkbox" name="to.isCoMail" id="coMailOnly" class="checkbox"  onclick="fnCoMailOnly(this);"/></td>
            <td class="lable">&nbsp;</td>
            <td width="19%">&nbsp;</td>
            <td class="lable" width="12%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
          </tr>
                </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="inOGMDoxTable" width="100%">
                  <thead>
                    <tr>
                      <th width="2%" align="center" ><input type="checkbox" id="chk0" class="checkbox" name="type" onchange="checkUncheckAllRows('inOGMDoxTable','chk')"/></th>
                      <th width="3%" align="center" ><bean:message key="label.manifestGrid.serialNo"/></th>
                      <th width="11%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.consignmentNo"/></th>
                      <th width="8%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.pincode"/></th>
                      <th width="8%"><bean:message key="label.manifestGrid.destination"/></th>
                      <th width="7%"><sup class="star">*</sup><bean:message key="label.manifestGrid.weight"/></th>
		               <th width="6%"><bean:message key="label.manifestGrid.toPayAmt"/></th>
		               <th width="6%"><bean:message key="label.manifestGrid.codLcAmt"/></th>
               			<th width="5%"><bean:message key="label.inManifestGrid.baAmt"/></th>
		               <th width="6%"><bean:message key="label.manifestGrid.lcBankName"/></th>        
                      <th width="10%"><bean:message key="label.manifestGrid.mobilenumber"/></th>
                      <th width="12%"><bean:message key="label.remarks"/></th>
                      
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
   <html:button property="Save"  styleClass="btnintform" styleId="save" onclick="saveOrUpdateInOGMDox()" >
    <bean:message key="button.label.Submit" locale="display" />
    </html:button>
     <html:button property="clear"  styleClass="btnintform" styleId="clear" onclick="clearDetails()">
    <bean:message key="button.label.clear" locale="display" />
    </html:button>
    <html:button property="Print"   styleClass="btnintform" styleId="print" onclick="printOgm();">
    <bean:message key="button.label.Print" locale="display"/>
    </html:button>
    <html:button property="delete" styleId="delete" styleClass="btnintform"  onclick="deleteTableRow('inOGMDoxTable')">
			<bean:message key="button.label.delete" locale="display" />
	</html:button>
	 <html:button property="edit" styleId="edit" styleClass="btnintform"  onclick="enableScreen()">
			<bean:message key="button.label.edit" locale="display" />
	</html:button>
	<html:button property="cancel" styleId="cancel" styleClass="btnintform"  onclick="cancelScreen()">
			<bean:message key="button.label.cancel" locale="display" />
	</html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <div id="main-footer">
    
  </div>
   </html:form>
          <!-- footer ends --> 
        </div>
       
<!--wraper ends-->
<!-- <iframe name="iFrame" id="iFrame" width="0.5" height="0.5" > </iframe> -->
<input id="maxAllowedRows" name="maxAllowedRows"  type="hidden"  value="${maxAllowedRows}"/>
</body>
</html>
