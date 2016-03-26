<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/manifestCommon.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestParcel.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/weightReader.js "></script>
			
		
		<script type="text/javascript" charset="utf-8">
			var ROW_COUNT="";
			var MAX_CNS_ALLOWED='${outManifestParcelForm.to.maxCNsAllowed}';
			var MANIFEST_STATUS_OPEN='${MANIFEST_STATUS_OPEN}';
			var MANIFEST_STATUS_CLOSE='${MANIFEST_STATUS_CLOSE}';
			var OFF_TYPE_REGION_HEAD_OFFICE='${OFF_TYPE_REGION_HEAD_OFFICE}';
			var OFF_TYPE_CODE_HUB ='${OFF_TYPE_CODE_HUB}';
			var OFF_TYPE_CODE_BRANCH ='${OFF_TYPE_CODE_BRANCH}';
			var MANIFEST_TYPE_PURE='${PURE}';
			var MANIFEST_TYPE_TRANSHIPMENT='${TRANS}';
			var OGM_SERIES=null;
			var BPL_SERIES='${SERIES_TYPE_BPL_NO}';
			var MBPL_SERIES=null;
			var BAGLOCK_SERIES= '${SERIES_TYPE_BAG_LOCK_NO}';
			var wmActualWeight=0.000;
			var wmCapturedWeight = 0.000;
			var newWMWt=0.000;
			var bookingDetail=null;
			var bookingManifestDetail=null;
			var CONSG_TYPE_PPX = "${CONSG_TYPE_PPX}";
			var CONSIGNOR = "${CONSIGNOR}";
			var INSURED_BY_FFCL = "${INSURED_BY_FFCL}";
			var INSURED_BY_CONSIGNOR = "${INSURED_BY_CONSIGNOR}";
		</script>
		<!-- DataGrids /-->
		</head>
<body onload="closedManifestActions();setDataTableDefaultWidth();">
<div id="wraper"> <!--wraper-->
<html:form action="/outManifestParcel.do" method="post" styleId="outManifestParcelForm" >
    <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
     <div class="formbox">
        <h1><bean:message key="label.bplForParcel"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.FieldsAreMandatory"/></div>
      </div>
      <div class="formTable">
      <table border="0" cellpadding="0" cellspacing="5" width="100%">
                <tr>
                  <td width="12%" class="lable"><sup class="star">*</sup>&nbsp; <bean:message key="label.manifest.dateTime"/></td>
                <td width="19%"><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width130" readonly="true" /></td>
                  <td width="16%" class="lable"><sup class="star">*</sup>&nbsp; <bean:message key="label.manifest.bplNo"/></td>
                  <td width="25%"><html:text property="to.manifestNo" styleId="manifestNo" styleClass="txtbox width130" size="11" maxlength="10" value="" onkeypress = "return callEnterKey(event, document.getElementById('manifestType'));"  onblur="isValidManifestNo(this,'H')"/>
                  &nbsp;<html:button property="Search"  styleClass="btnintgrid" onclick="searchManifest()"><bean:message key="button.label.search"/></html:button></td>
                  <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOffice"/>:</td>
                  <td width="15%"><html:text property="to.loginOfficeName" styleId="originOffice" styleClass="txtbox width130" readonly="true" />
                  </td>
                </tr>  
                <tr>
                  <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.manifestType"/></td>
                  <td width="19%"><html:select property="to.bplManifestType" styleId="manifestType" styleClass="selectBox width130" onkeypress = "return callEnterKeyForManifestType(event);" onchange="fnHeaderDataChanges('MT');" >
                   	<option value="">----Select----</option>
                    <c:forEach var="manifestTypes" items="${manifestTypeTOList}" varStatus="loop">              
               		<option value="${manifestTypes.stdTypeCode}" ><c:out value="${manifestTypes.description}"/></option>             
            		</c:forEach></html:select></td>  
            		<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationRegion"/></td>
                  <td><html:select property="to.destinationRegionId" styleId="destRegionId" styleClass="selectBox width130"  onchange="fnHeaderDataChanges('R');" onkeypress = "return callEnterKey(event, document.getElementById('destCity'));">
                  	 <option value="">----Select----</option>
              		 <c:forEach var="region" items="${regionTOs}" varStatus="loop">              
               			<option value="${region.regionId}" ><c:out value="${region.regionName}"/></option>             
           			 </c:forEach> 
              		</html:select></td>
              		<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationCity"/></td>
                  <td width="15%"><html:select property="to.destinationCityId" styleId="destCity" styleClass="selectBox width130" onchange="fnHeaderDataChanges('C');" onkeypress = "return callEnterManifest(event);">
              		<option value="">----Select----</option>
             		 </html:select> </td>
                  </tr>
                <tr>
                  <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.officeType"/></td>
                  <td width="19%"><html:select property="to.destOfficeType" styleId="destOfficeType" styleClass="selectBox width130" onchange="fnHeaderDataChanges('OT');"  onkeypress = "return callEnterKey(event, document.getElementById('destOffice'));">
              		<option value="">----Select----</option>
                    <c:forEach var="officeTypes" items="${officeTypeList}" varStatus="loop">              
               		<option value="${officeTypes.value}" ><c:out value="${officeTypes.label}"/></option>             
            		</c:forEach>              		
              		</html:select></td>
                  <td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationOffice"/></td>
                  <td><html:select property="to.destinationOfficeId" styleId="destOffice" styleClass="selectBox width130" onchange="getAllOfficeIds(this); fnHeaderDataChanges('DO');" 
                  onblur="getReportingRHOByoffice(this.value);" onkeypress = "return callEnterKey(event, document.getElementById('bagLockNo'));" >
                  <option value="">----Select----</option>
             		 </html:select></td>
                  <td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.bagLockNo"/></td>
                  <td width="15%"><html:text property="to.bagLockNo" styleId="bagLockNo" styleClass="txtbox width130" maxlength="8" onblur="validateBagLockNo(this,'H');convertDOMObjValueToUpperCase(this);" onkeypress = "return callFocusEnterKey(event);"/></td>
                </tr>
                 <tr>
                    <td width="11%" class="lable"><bean:message key="label.manifest.rfid"/></td>
                    <td width="18%"><html:text property="to.rfidNo" styleId="rfidNo" styleClass="txtbox width130" onblur="getRfIdByRfNo(this);" onkeypress = "return callFocusEnterKey(event);"/> </td>
                    <td width="20%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.weight"/></td>
                    <td width="21%"><html:text property="" styleClass="txtbox width30" readonly="true" styleId="finalKgs" maxlength="3" value="" />.
            		<html:text property="" styleClass="txtbox width30"  readonly="true" styleId="finalGms" maxlength="3" value=""/><span class="lable">kgs</span>
            		<html:hidden property="to.finalWeight" styleId="finalWeight" value=""/></td>  
                    <td width="18%" class="lable">&nbsp;</td>
                  	<td width="23%">&nbsp;</td>
                  </tr>
              
     </table><!--Table ends-->
      
</div><!--formTable ends-->
 <div id="demo">
        <div class="title">
            <div class="title2"><bean:message key="label.manifest.dtls"/></div>
        </div>  
       <table id="outManifstTable" class="display" width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-left: 0px; width: 140%;">
 					<thead>
           			 <tr>
                      <th width="2%" align="center" ><input type="checkbox" class="checkbox" id = "checkAll" name="type" onclick = "selectAllCheckBox (${outManifestParcelForm.to.maxCNsAllowed});" /></th>
                      <th width="3%" align="center" ><bean:message key="label.common.serialNo"/></th>                     
                      <th width="7%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.consgNo"/></th>
                      <th width="6%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.noOfPieces"/></th>
                      <th width="6%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.weight"/></th>
                      <th width="6%"><bean:message key="label.manifestGrid.volWtkgs"/></th>
                      <th width="8%"><bean:message key="label.manifestGrid.chargeableWtkgs"/></th>
                      <th width="4%"><sup class="star">*</sup>&nbsp;<bean:message key="label.pickup.pincode"/></th>
                      <th width="4%"><bean:message key="label.pickup.mobilenumber"/></th>
                      <th width="13%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.contentDesc"/></th>
                      <th width="5%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.decValue"/></th>
                      <th width="9%"><bean:message key="label.manifestGrid.paperWork"/></th>
                      <th width="4%"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.insuredBy"/></th>
                      <th width="5%"><bean:message key="label.manifestGrid.policyNo"/></th>
                      <th width="5%"><bean:message key="label.manifestGrid.toPayAmt"/></th>
                      <th width="4%"><bean:message key="label.creditCustBookingDox.codOrLcAmt"/></th>
                      <th width="4%"><bean:message key="label.creditCustBookingDox.lcBankName"/></th>
                      <th width="13%"><bean:message key="label.manifestGrid.customerRefNo"/></th>                      
                    </tr>
         			 </thead>
         			 <tbody>
         			 <c:forEach var="i" begin="1" end="${outManifestParcelForm.to.maxCNsAllowed}">
         			 <tr>
         			 <td><input type="checkbox" class="checkbox" name="type" id="chk${i}" onclick="getConsignmntIdOnCheck(this)"/></td>
         			 <td>${i}</td>
         			 <td><html:text property="to.consgNos" styleId="consigntNo${i}" styleClass="txtbox width130" maxlength="12" value="" onfocus="validateCnMandatoryFields();"
         			 	onblur="validateConsignment(this);"
         			 	onkeypress = "fnEnterKeyCallOnCnNo(event,${i});"/>
         			 <!-- Added temporarily by Ami  -->
         		 	 <html:hidden property="to.status" styleId="status${i}" value="" />
 	    			 <html:hidden property="to.consgIds" styleId="consgId${i}" value=""/>
         			 <html:hidden property="to.isCNs" styleId="isCN${i}" value="Y"/>
         			 <html:hidden property="to.consgManifestedIds" styleId="consgManifestedId${i}" value=""/>
         			 <html:hidden property="to.bookingTypes" styleId="bookingType${i}" value=""/>
         			 <html:hidden styleId="isCNProcessedFromPickup${i}" property="to.isCNProcessedFromPickup" value="N"/>
					 <html:hidden styleId="bookingId${i}" property="to.bookingIds" value=""/>
					 <html:hidden styleId="bookingTypeId${i}" property="to.bookingTypeIds" value=""/>
					 <html:hidden styleId="customerId${i}" property="to.customerIds" value=""/>
					 <html:hidden styleId="runsheetNo${i}" property="to.runsheetNos" value=""/>
					 <html:hidden styleId="consignorId${i}" property="to.consignorIds" value=""/>
					 <html:hidden styleId="isDataMismatch${i}" property="to.isDataMismatched" value="N"/>
					 <html:hidden styleId="bookingWeight${i}" property="to.bookingWeights" value=""/>
					 <html:hidden styleId="oldWeights${i}" property="to.oldWeights" value=""/>
					 </td>
         			 <td><html:text property="to.noOfPcs" styleId="noOfPcs${i}" styleClass="txtbox width30" value=""  onblur="redirectToChildCNPage(${i});" 
         			 onkeypress="return validateNumberOfPcs(event, this,'actWeightKg${i}');" maxlength="3" onchange="isDataMismatched(this.value,${i});"/>
         			 	<html:hidden property="to.isCnUpdated" styleId="isCnUpdated${i}" value="N"/> 
         			 	<html:hidden property="to.childCns" styleId="childCns${i}" value=""/> 
         			 </td>
         			 <td><html:text property="actWeightKg" styleId="actWeightKg${i}" styleClass="txtbox width30" value="" maxlength="4" onblur="setTotalFinalWeight(${i});updateChildCNNumber(childCns${i});" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'actWeightGm${i}');"/>.
         			 <html:text property="actWeightGm" styleId="actWeightGm${i}" styleClass="txtbox width30" value="" maxlength="3" onblur="setTotalFinalWeight(${i});updateChildCNNumber(childCns${i});compairChildCNWeightAndCNWeight(${i});" onkeypress="return validateWeight(event, this,'pincode${i}');"/>
         			 <html:hidden property="to.actWeights" styleId="actWeight${i}" value=""/> 
         			 </td>
         			 <td><html:text property="volWeight" styleId="weightVW${i}" styleClass="txtbox width30" value="" maxlength="2" readonly="true" tabindex="-1" />.
         			 <html:text property="volWeightGm" styleId="weightGmVW${i}" styleClass="txtbox width30" value="" maxlength="3" readonly="true" tabindex="-1" />
         			 <html:hidden property="to.volWeight" styleId="volWeight${i}" value=""/>
         			 <html:hidden property="to.lengths" styleId="length${i}" value=""/>
         			 <html:hidden property="to.breadths" styleId="breath${i}" value=""/>
         			 <html:hidden property="to.heights" styleId="height${i}" value=""/>
         			 <img src="images/calculator.png" alt="calculate volume"  onclick="redirectToVolumetricWeight(${i});"/>
         			 </td>
         			 <td><html:text property="weightKg" styleId="weightKg${i}" styleClass="txtbox width30" value="" maxlength="2" readonly="true" tabindex="-1"/>.
         			 <html:text property="weightGm" styleId="weightGm${i}" styleClass="txtbox width30" value="" maxlength="3" readonly="true" tabindex="-1"/>
         			 <html:hidden property="to.weights" styleId="weight${i}" value=""/> 
         			 </td>
         			 <td><html:text property="to.pincodes" styleId="pincode${i}" styleClass="txtbox width80" maxlength="6" value="" onchange="validatePincode(this);isDataMismatched(this.value,${i});" 
         			 onkeypress="return validateManditoryPincode(event, this,'contentCode${i}');"/> <!-- onfocus="setTotalFinalWeight(${i});" -->
         			 <html:hidden property="to.pincodeIds" styleId="pincodeId${i}" value=""/>
         			 </td>         			 
         			 <td><html:text property="to.mobileNos" styleId="mobile${i}" styleClass="txtbox width80" maxlength="10" value="" onchange="isDataMismatched(this.value,${i});"
         			 onkeypress="return onlyNumberAndEnterKeyNav(event, this,'contentName${i}');"/>
         			 <html:hidden property="to.consigneeIds" styleId="consigneeId${i}" value=""/>
         			 </td>         			 
         			 <td>
         			 <html:text styleClass="txtbox width50" size="40" styleId="contentCode${i}" property="to.cnContentCodes" value="" onchange="setContentValues(this);"  onkeypress="return validateContents(event, this, 'C','declaredValue${i}', ${i});" />&nbsp;         			 
         			 <html:select property="to.cnContentNames" styleClass="selectBox width90" styleId="contentName${i}" onchange="setContentDtls(this);isDataMismatched(this.value,${i});" onkeypress="return validateContents(event, this, 'N','declaredValue${i}',${i});">
         			 <option value="">--Select--</option>
         			 <c:forEach var="content" items="${contentVal}" varStatus="status">  
						<option value='${content.cnContentId}#${content.cnContentCode}'>${content.cnContentName}</option>
					 </c:forEach></html:select>&nbsp;					 
					 <html:text styleClass="txtbox width50"  styleId="cnContentOther${i}" property="to.otherCNContents" readonly="true" value="" onchange="isDataMismatched(this.value,${i});" onkeypress="return validateContents(event, this,'O','declaredValue${i}',${i});"/>
					 <html:hidden styleId="contentIds${i}" property="to.cnContentIds" value=""/>
					<%--  <html:hidden styleId="contentNames${i}" property="to.cnContentNames" value=""/>  --%>
         			 </td>
         			 <td><html:text property="to.declaredValues" styleId="declaredValue${i}" styleClass="txtbox width100" size="11" value="" 
         			 onkeypress="return validateDeclareValue(event,this,'consigntNo${i+1}');"  onchange="getPaperWorks(${i});getInsuarnceConfigDtls(this);isDataMismatched(this.value,${i});" onblur="isValidDecValue(this);setDeclaredFractions(this,2);calculateCnRate(${i});" /></td>
         			 <td><html:select property="to.cnPaperWorkNames" onchange="setPaperWorkDetails(this);isDataMismatched(this.value,${i});" styleClass="selectBox width90" 
         			 styleId="cnPaperWorkselect${i}" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'insuredBy${i}');">
         			 <option value="">--Select--</option></html:select>&nbsp;
         			 <html:text styleClass="txtbox width50"  styleId="cnPaperWorks${i}" property="to.paperRefNums"   value=""/>
         			 <html:hidden styleId="paperWorkId${i}" property="to.paperWorkIds" value=""/> 
         			 <%-- <html:hidden styleId="paperWorkNames${i}" property="to.cnPaperWorkNames" value=""/> --%>
         			 </td>
         			 <td><html:select property="to.insuredByIds" styleId="insuredBy${i}" styleClass="txtbox width80" onchange="isDataMismatched(this.value,${i});"
         			 onkeypress="return validateInsuredBy(event, this,'policyNo${i}');">
         			 <option value="">--Select--</option>
         			  <c:forEach var="insuranceTO" items="${insurance}" varStatus="status">  
						<option value='${insuranceTO.insuredById}'>${insuranceTO.insuredByDesc}</option>
					 </c:forEach>
         			 
         			 </html:select></td>
         			 <td><html:text property="to.policyNos" styleId="policyNo${i}" styleClass="txtbox width80" size="11" value="" onchange="isDataMismatched(this.value,${i});"
         			 onkeypress="return validatePolicyNo(event, this, document.getElementById('toPayAmt${i}'));"/></td>
         			 <td><html:text property="to.toPayAmts" styleId="toPayAmt${i}" styleClass="txtbox width80" size="6" maxlength="12" value="" onchange="isDataMismatched(this.value,${i});" 
         			 onblur='setFractions(this,2)'/></td> <%--  onkeypress="return validateToPayAmount(event, this);" --%>
         			 <td><html:text property="to.codAmts" styleId="codAmt${i}" styleClass="txtbox width80" size="6" maxlength="6" value="" onchange="isDataMismatched(this.value,${i});" 
         			 onblur='setFractions(this,2)' onkeypress="return validateCODAmount(event, this,'custRefNo${i}');"/></td>
         			 <td><html:text property="to.lcBankName" styleId="lcBankName${i}" styleClass="txtbox width80" size="10" maxlength="20" value="" 
         			 onkeypress="return validatelcBankName(event, this,'custRefNo${i}');"/></td>
         			 <td><html:text property="to.custRefNos" styleId="custRefNo${i}" styleClass="txtbox width100" value="" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'consigntNo${i+1}');" onblur="calculateCnRate(${i});" />
         			  	<html:hidden property="to.position" styleId="position${i}" value="${i}" />
         			  	
         			 </td>
         			  </tr>
         			 </c:forEach>         			 
         			 </tbody>
	   </table>				
	   </div>			
      </div>
      </div>
 <!--      </div> -->
 <!-- Hidden Fields Start -->
       <html:hidden property="to.manifestId" styleId="manifestId"/>
       <html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId"/>
       <html:hidden property="to.loginOfficeId" styleId="loginOfficeId" /> 
       <html:hidden property="to.isMulDestination" styleId="isMulDest"/>
	   <html:hidden property="to.multiDestinations" styleId="multiDest"/>
	   <html:hidden property="to.bagRFID" styleId="bagRFID" value=""/>
	   <html:hidden property="to.seriesType" styleId="seriesType"/>
	   <html:hidden property="to.manifestStatus" styleId="manifestStatus"/>
	   <html:hidden property="to.regionId" styleId="regionId"/>
	   <html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed"/>
	   <html:hidden property="to.manifestDirection" styleId="manifestDirection"/>
	   <html:hidden property="to.loginCityCode" styleId="loginCityCode"/>
	   <html:hidden property="to.loginCityId" styleId="loginCityId"/>
       <html:hidden property="to.consgIdListAtGrid" value="" styleId="consgIdListAtGrid"/>	
       <html:hidden property="to.processCode" styleId="processCode"/>
       <html:hidden property="to.maxWeightAllowed" styleId="maxWeightAllowed" />
	   <html:hidden property="to.maxTolerenceAllowed" styleId="maxTolerenceAllowed" />	
	   <html:hidden property="to.officeCode" styleId="officeCode" />
	   <html:hidden property="to.regionCode" styleId="regionCode"/>
	   <html:hidden property="to.repHubOfficeId" styleId="repHubOfficeId"/>
	   <html:hidden property="to.bookingType" styleId="bookingType" value=""/>
	   <html:hidden property="to.allowedConsgManifestedType" styleId="allowedConsgManifestedType" />
	   <html:hidden property="to.outMnfstDestIds" styleId="outMnfstDestIds" />
	   <html:hidden property="to.loginOfficeType" styleId="loginOfficeType"/>
	   <html:hidden property="to.loginRepHub" styleId="loginRepHub"/>
	   <html:hidden property="to.operatingLevel" styleId="operatingLevel"/>
	   <%-- <html:hidden property="to.consignmentTypeTO.consignmentId" styleId="consignmentTypeId" /> --%>
	   <html:hidden property="to.processId" styleId="processId" />
	   <html:hidden property="to.isWMConnected" styleId="isWMConnected"/>
    <!-- Hidden Fields End -->
    
	<div class="button_containerform">
    <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveOrCloseOutManifestParcel('save');"><bean:message key="button.label.save"/></html:button>
    <html:button property="Delete" styleClass="btnintform" styleId="deleteBtn" onclick="deleteConsgDtlsClientSide();" ><bean:message key="button.label.delete" /></html:button>
    <html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printManifest();"><bean:message key="button.label.Print"/></html:button>
    <html:button property="Close" styleClass="btnintform" styleId="closeBtn" onclick="saveOrCloseOutManifestParcel('close');"><bean:message key="button.label.close"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" ><bean:message key="button.label.cancel" /></html:button>
  	</div>
  </html:form>
</div><!--wraper ends-->
<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000" > </iframe> -->
</body>
</html>