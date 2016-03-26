<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/manifestCommon.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/bplBranchOutManifest.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
		<script type="text/javascript" charset="utf-8">
		var OGM_SERIES=null;
		var BPL_SERIES=null;
		var MBPL_SERIES=null;
		var BAGLOCK_SERIES=null;
		
		$(document).ready( function () {
				BPL_SERIES='${SERIES_TYPE_BPL_NO}';
				OGM_SERIES='${SERIES_TYPE_OGM_STICKERS}';
				
				BAGLOCK_SERIES='${SERIES_TYPE_BAG_LOCK_NO}';
				
				
				var wmActualWeight=0.000;
				var wmCapturedWeight = 0.000;
				var newWMWt=0.000;
				var bookingDetail=null;
				
				var oTable = $('#outManifestTable').dataTable( {
					"sScrollY": "255",
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
				bplBranchOutManifestStartup();
				//getWeightFromWeighingMachine();
			} );
		</script>
		<!-- DataGrids /-->
		</head>
		<body onload="addRows();">
<!--wraper-->
<div id="wraper"> 
     <html:form action="/bplBranchOutManifest.do" method="post" styleId="bplBranchOutManifestForm">
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1>BPL for Branch Manifest</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
                <tr>
                  <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.dateTime"/></td>
                  <td width="14%"><html:text property="to.manifestDate" styleClass="txtbox width130"  readonly="true" styleId="dateTime" ></html:text></td>
                   <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.bplNo"/></td>
                  <td width="25%"><html:text property="to.manifestNo" styleClass="txtbox width130" maxlength="10"  styleId="bplNo" size="11" onblur="convertDOMObjValueToUpperCase (this);isValidManifestNoForBout(this,'H');"  onkeypress = "return callEnterKey(event, document.getElementById('destOffice'));"></html:text>
                   <html:button property="Search"  styleClass="btnintgrid" onclick="searchManifest()"><bean:message key="button.label.search"/></html:button></td>
                   <!-- &nbsp;<img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch" onclick="searchManifest();"/></td> -->
                  <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationHub"/></td>
                  <td width="16%"><html:text property="to.loginOfficeName" styleClass="txtbox width130"  styleId="originOffice" size="11" readonly="true"  ></html:text></td>
                  </tr>
                <tr>
                  <td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationOffice"/></td>
                  <td width="14%"><html:select property="to.destinationOfficeId" styleClass="selectBox width130" styleId="destOffice"  onkeypress = "return callEnterKey(event, document.getElementById('loadNo'));" onchange="checkGridEmpty(this,'manifestNo');" >                 
            	   <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
            	    <c:forEach var="officeTO" items="${officeTOs}" varStatus="loop">
              		<option value="${officeTO.officeId}" ><c:out value="${officeTO.officeName}"/></option>
            		</c:forEach> 
                  </html:select></td>
                  <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.loadNo"/></td>
                  <td>
                  <html:select styleId="loadNo" property="to.loadNo" styleClass="selectBox width35"  onkeypress = "return callEnterKey(event, document.getElementById('bagLockNo'));" > 
						<%-- <html:option value=""><bean:message key="label.common.select" /></html:option> --%>
						<html:optionsCollection  property="to.loadList" label="label" value="value" />
				</html:select>
					</td>
                  <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.bagLockNo"/></td>
                  <td width="16%"><html:text property="to.bagLockNo" maxlength="8" styleClass="txtbox width130" styleId="bagLockNo" onblur="convertDOMObjValueToUpperCase (this);validateBagLockNo(this,'H')" 
                  onkeypress = "return callEnterKey(event, document.getElementById('rfidNo'));"></html:text> </td>
                 </tr>
               <tr>
                    <td width="15%" class="lable"><bean:message key="label.manifest.rfid"/></td>
                    <td width="14%">
                    	<html:text property="to.rfidNo" styleClass="txtbox width130" styleId="rfidNo" maxlength="4" onblur="getRfIdByRfNo(this);" onkeypress = "return callFocusEnterKey(event);" />
                    	<html:hidden property="to.bagRFID" styleId="bagRFID" />
                    </td>
                    
                    <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.weight"/></td>
                    <td width="25%" colspan="4">
                     <html:text property="" styleClass="txtbox width30" readonly="true" size="11" styleId="finalKgs" value=""  /><span class="lable">.</span>
           			 <html:text property="" styleClass="txtbox width30"  readonly="true" size="11" styleId="finalGms" value="" maxlength="3"/><span class="lable">kgs</span>   
                     <html:hidden property="to.finalWeight" styleId="finalWeight" />
                    </td>       
            		<!--  <td width="15%" class="lable">&nbsp;</td>
            	 		<td width="15%">&nbsp;</td> -->
                  </tr>
              </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="outManifestTable" width="100%">
                  <thead>
            <tr>
                      <th width="5%" align="center" ><input type="checkbox"  class="checkbox" name="type" id="checkAll" onclick = "selectAllCheckBox(maxRowAllowed)";/></th>
                      <th width="7%" align="center" >Sr. No.</th>
                     
                      <th width="30%" align="center"><sup class="star">*</sup>&nbsp;Branch Manifest No.</th>
                      <th width="31%">Weight (kgs)</th>
                      <th width="27%"><sup class="star">*</sup>&nbsp;No. of Consignments</th>
                    </tr>
          </thead>
                  
                </table>
      </div>
              <!-- Grid /--> 
              <!-- Hidden Fields Start -->
              
		
	 <html:hidden property="to.manifestIdListAtGrid"  styleId="manifestIdListAtGrid"/>
		<%-- <html:hidden property="to.consgIdListAtGrid"  styleId="consgIdListAtGrid"/> --%>
		<html:hidden property="to.manifestStatus" styleId="manifestStatus"/>
           <html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />      
                <html:hidden property="to.destinationOfficeId" />
                 <html:hidden styleId="manifestId" property="to.manifestId"/>
                 <html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed" />
				<html:hidden property="to.maxWeightAllowed" styleId="maxWeightAllowed" />
				<html:hidden property="to.maxTolerenceAllowed" styleId="maxTolerenceAllowed" />
                  <html:hidden property="to.manifestDirection" styleId="manifestDirection"/>
               <html:hidden property="to.processCode" value="${processCode}" styleId="processCode"/>
               <html:hidden property="to.loginCityCode" styleId="loginCityCode"/>
                <html:hidden property="to.seriesType" value="${seriesType}" styleId="seriesType"/>
                 <html:hidden property="to.officeCode" styleId="officeCode"/>
                 <html:hidden property="to.regionId" styleId="regionId"/>
                  <html:hidden property="to.regionCode" styleId="regionCode"/>
                 <html:hidden property="to.action" styleId="action" />
                  <html:hidden  property="to.destinationCityId" styleId="destCity"/>
               <html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId"/>
                <!-- Hidden Fields Stop -->
              
              
            </div>
    <!-- Button --> 
    <!--<div class="button_containergrid">
  <input name="Generate" type="button" value="Generate" class="btnintgrid"  title="Generate"/>  
  </div>--><!-- Button ends --> 
  </div>
          <!-- Button -->
          <div class="button_containerform">
     <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveOrCloseBplBranchOutManifest('save');"><bean:message key="button.label.save"/></html:button>
    <html:button property="Delete" styleClass="btnintform" styleId="deleteBtn" onclick="deleteManifstDtlsClientSide();"><bean:message key="button.label.delete"/></html:button>
    <html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printDtls();"><bean:message key="button.label.Print"/></html:button>
    <html:button property="Close" styleClass="btnintform" styleId="closeBtn" onclick="saveOrCloseBplBranchOutManifest('close');"><bean:message key="button.label.close"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" ><bean:message key="button.label.cancel" /></html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
     </html:form>
        </div>
<!--wraper ends-->
<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000" > </iframe> -->
</body>
</html>
