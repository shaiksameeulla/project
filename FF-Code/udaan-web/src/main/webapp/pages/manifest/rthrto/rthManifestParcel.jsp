<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
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
		<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rthRtoManifest.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rthRtoManifestParcel.js"></script>
		<script type="text/javascript" charset="utf-8">
		var MANIFEST_TYPE_RTH='${MANIFEST_TYPE_RTH}'; //MANIFEST_TYPE_RTH = 'T'
		var MANIFEST_TYPE_RTO='${MANIFEST_TYPE_RTO}'; //MANIFEST_TYPE_RTO = 'R'
		var OGM_SERIES='${SERIES_TYPE_OGM_NO}';
		var BPL_SERIES='${SERIES_TYPE_BPL_NO}';
		var BAGLOCK_SERIES='${SERIES_TYPE_BAG_LOCK_NO}';
		var CN_TYPE_PPX='${CONSIGNMENT_TYPE_PARCEL}';
		var OFF_TYPE_HUB_OFFICE='${OFF_TYPE_HUB_OFFICE}';
		var LOGIN_OFF_TYPE='${loginOfficeType}';
		function getReasons() {
			var text = "";
			<c:forEach var="reason" items="${reasonsList}" varStatus="status">  
				text = text + "<option value=${reason.reasonId}>${reason.reasonCode} - ${reason.reasonName}</option>";
			</c:forEach>
			return text;		   	
		}
		</script>
		<!-- DataGrids /-->
		</head>
		<body>
<!--wraper-->
<div id="wraper"> 
   <html:form action="/rthRtoManifestParcel.do" method="post" styleId="rthRtoManifestForm" >
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.rthManifestingScreenPpx"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="3" width="100%">
                   <tr>
           <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.common.date"/><strong>/</strong><bean:message key="label.common.time"/>:</td>
            <td width="15%"><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width130" size="11" readonly="true" tabindex="-1"/></td>
              <td width="12%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.manifestNumber"/></td>
            <td width="21%"><html:text property="to.manifestNumber" styleId="manifestNo" styleClass="txtbox width130" maxlength="10" size="11" value=""
            onkeypress = "return callEnterKey4HubLoginRTH(event, '${loginOfficeType}');" onblur="validateManifestNo(this);"/>&nbsp;
            <html:button property="Search" styleId="searchBtn" styleClass="btnintgrid" onclick="searchManifest()"><bean:message key="button.label.search"/></html:button></td>
            <td width="15%" class="lable"><bean:message key="label.rto.rth.dispatchingOfficeCode"/></td>
            <td width="13%">                       
            <html:hidden property="to.originOfficeTO.officeTypeTO.offcTypeCode" styleId="originOffTypeCode"/>
            <html:hidden property="to.originOfficeTO.cityId" styleId="origincityId"/>
             <html:hidden property="to.originCityCode" styleId="originCityCode"/>
            <html:hidden property="to.originOfficeTO.officeId" styleId="originOfficeId"/>
            <html:text property="to.originOfficeTO.officeCode" styleClass="txtbox width130" styleId="originOfficeCode" size="11" readonly="true" tabindex="-1"/></td>
            
          </tr>
           <tr>
             <c:if test="${loginOfficeType eq OFF_TYPE_HUB_OFFICE}">
             <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.destinationOfficeCode"/></td>
             <td width="11%" class="lable" colspan="3">
                <table cellpadding="0" cellspacing="0" border="0" width="85%">
                    <tr>
                        <td align="left" class="lable1" width="13%"> <bean:message key="label.rto.rth.station"/><br />
                            <html:select property="to.destCityTO.cityId" styleId="destCity" onchange="getHubOfficesByCity();" styleClass="selectBox width130"
                                onkeypress = "return callEnterKey(event, document.getElementById('destOffice'));">
                             <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
                                <c:forEach var="cityTO" items="${cityList}" varStatus="loop">
                                    <option value="${cityTO.cityId}" ><c:out value="${cityTO.cityCode} - ${cityTO.cityName}"/></option>
                                </c:forEach> 
                            </html:select>&nbsp;</td>
                        <td align="left" class="lable1" width="13%"><bean:message key="label.rto.rth.office"/><br />
                            <html:select property="to.destOfficeTO.officeId" styleId="destOffice" styleClass="selectBox width130" 
                             onkeypress = "return callEnterKey(event, document.getElementById('bagLockNo'));">
                            <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option> 
                            </html:select>&nbsp;</td>
                    </tr>
                </table>
                </td>           
             </c:if>
             <c:if test="${loginOfficeType eq 'BO'}">
             <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.destinationOfficeCode"/></td>
            <td width="1%"><html:select property="to.destOfficeTO.officeId" styleId="destOffice" styleClass="selectBox width140" onkeypress = "return callEnterKey(event, document.getElementById('bagLockNo'));">
                          <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
                          <c:forEach var="officeTO" items="${officeList}" varStatus="loop">
              				<option value="${officeTO.officeId}" ><c:out value="${officeTO.officeCode} - ${officeTO.officeName}"/></option>
            			  </c:forEach> 
                        </html:select></td>
	        </c:if>                
                        
            <td width="12%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.manifest.bagLockNo"/></td>
            <td width="21%">
            <html:text property="to.bagLockNo" styleId="bagLockNo" styleClass="txtbox width130" 
            maxlength="8" onkeypress = "return callEnterKey(event, document.getElementById('consgNumber1'));" onblur="validateBagLockNo(this,'H');" />
            </td>
            
            <td width="15%" class="lable"><bean:message key="label.manifest.ConsignmentType"/></td>
            <td width="15%"><html:hidden property="to.consignmentTypeTO.consignmentId" styleId="consignmentTypeId"/>
            <html:hidden property="to.consignmentTypeTO.consignmentCode" styleId="consignmentTypeCode"/>
            <html:text property="to.consignmentTypeTO.consignmentName" styleId="consignmentTypeName" styleClass="txtbox width130"  
            size="11" readonly="true" tabindex="-1" />
            </td>
          </tr>
                
                </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.rto.rth.validationDetails"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="rthRtoManifestTable" width="100%">
                  <thead>
            <tr>
                      <th width="1%"><input id="checkAll" type="checkbox" name="type" onclick="checkAllBoxes(this.checked);"/></th>
                       <th width="7%"><bean:message key="label.common.serialNo"/></th>
                       <th width="7%"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.consignmentNumber"/></th>
                       <th width="7%"><bean:message key="label.manifestGrid.noOfPieces"/></th>
                       <th width="7%"><bean:message key="label.manifestGrid.pincode"/></th>
                       <th width="7%"><bean:message key="label.rto.rth.actualWeightkgs"/></th>
                        <th width="7%"><bean:message key="label.rto.rth.content"/></th>
                        <th width="7%"><bean:message key="label.rto.rth.paperwork"/></th>
                      <th width="7%"><bean:message key="label.rto.rth.receivedDate"/></th>
                      <th width="9%"><bean:message key="label.rto.rth.codAmount"/></th>
                      <th width="9%"><bean:message key="label.rto.rth.toPayAmount"/></th>
                      <th width="5%"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.pendingReason"/></th>
                      <th width="8%"><bean:message key="label.remarks"/></th>
                    </tr>
          </thead>                  
                </table>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
  
   <!-- Hidden Fields Start -->
       <html:hidden property="to.manifestType" styleId="manifestType" />
       <html:hidden property="to.manifestId" styleId="manifestId" />
       <html:hidden property="to.manifestProcessId" styleId="manifestProcessId" />
       <html:hidden property="to.seriesType" styleId="seriesType" value=""/>
       
       <html:hidden property="to.regionCode" styleId="regionCode" />
       <html:hidden property="to.regionId" styleId="regionId" />
       <html:hidden property="to.manifestDirection" styleId="manifestDirection" />
       <html:hidden property="to.processCode" styleId="processCode" />
       
   <!-- Hidden Fields End -->
          
          <!-- Button -->
 <div class="button_containerform">
   	<html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveOrUpdateRtohManifest();"><bean:message key="button.label.save"/></html:button>
    <html:button property="Delete" styleClass="btnintform" styleId="deleteBtn" onclick="deleteRows();" ><bean:message key="button.label.delete" /></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();"><bean:message key="button.label.Cancel"/></html:button>
    <html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printManifest();"><bean:message key="button.label.Print"/></html:button>    
 </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          </html:form>
        </div>
<!-- wrapper ends -->
<!-- <iframe name="iFrame" id="iFrame" width="0.5" height="0.5" > </iframe> -->
</body>
</html>
