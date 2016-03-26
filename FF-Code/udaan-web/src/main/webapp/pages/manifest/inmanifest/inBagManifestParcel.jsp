<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>In BPL - Parcel</title>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" charset="utf-8" src="js/consignmentValidator.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inBagManifestParcel.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inBagManifest.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inManifest.js"></script>
<!-- Start param to get Remarks -->
<jsp:include page="inManifest.jsp"/>	           
<!-- End param to get Remarks   -->
</head>

<body>
<html:form action="/inBagManifestParcel.do?submitName=viewInBagManifestParcel" styleId="inBagManifestParcelForm">  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.manifest.header.inBplParcel"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.manifest.FieldsareMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="15%" class="lable"><bean:message key="label.manifest.dateTime"/></td>
            <td width="17%" >
            	<html:text styleId="manifestDateTime" property="to.manifestDateTime" styleClass="txtbox width130" disabled="true"/>	
            </td>
            <td width="12%" class="lable"><sup class="star">*</sup><bean:message key="label.manifest.bplNumber"/></td>
            <td width="25%">
            	<html:text styleId="manifestNumber" property="to.manifestNumber" styleClass="txtbox width130" onchange="findBplNumberParcel();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'btnSearch','BPL Number.');" maxlength="10"/><!-- onchange="findBplNumberParcel();" -->
				<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="findBplNumberParcel();" onfocus="bplNumberFocus();" title="Search">
			        <bean:message key="button.label.search"/>
			    </html:button>
            </td>            
            <td width="15%" class="lable"><bean:message key="label.manifest.bagLockNo"/></td>
            <td width="17%">
            	<html:text styleId="lockNum" property="to.lockNum" styleClass="txtbox width130" onchange="validateBagLockNo();"/>
            	<%-- <html:text styleId="destinationRegion" property="to.destinationRegion" styleClass="txtbox width130" readonly="true"/> --%>
            	<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId"/>
            	<html:hidden property="to.manifestId" styleId="manifestId"/>
            	<html:hidden property="to.manifestWeight" styleId="manifestWeight"/>
            	<html:hidden property="to.manifestType" styleId="manifestType"/>
            	<html:hidden property="to.destCityId" styleId="destCityId"/>
            	<html:hidden property="to.consignmentTypeId" styleId="consignmentTypeId"/>
            	<html:hidden property="to.processCode" styleId="processCode"/>
            	<html:hidden property="to.updateProcessCode" styleId="updateProcessCode"/>
            	<html:hidden property="to.updateProcessId" styleId="updateProcessId"/>
            	<html:hidden property="to.gridProcessCode" styleId="gridProcessCode"/>
            	<html:hidden property="to.gridProcessId" styleId="gridProcessId"/>
            	<html:hidden property="to.gridOgmProcessCode" styleId="gridOgmProcessCode"/>
                <html:hidden property="to.headerRemarks" styleId="headerRemarks"/>
                <html:hidden property="to.manifestEmbeddedIn" styleId="manifestEmbeddedIn"/>
				<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
				<html:hidden property="to.position" styleId="position"/>
				<html:hidden property="to.manifestReceivedStatus" styleId="manifestReceivedStatus"/>
            </td>
          </tr>
          
          <tr>
            <td class="lable"><bean:message key="label.inmanifest.destinationOffice"/></td>
            <td><html:text styleId="destinationOffice" property="to.destinationOffice" styleClass="txtbox width130" disabled="true"/> </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originRegion"/></td>
            <td>
            	<html:select styleId="originRegion" property="to.originRegion" styleClass="selectBox width130" onchange="getOriginCitiesByRegion();checkGridValueWhenHeaderModify();" onkeypress="enterKeyNavigationFocus(event,'originCity');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.originRegionTOs" label="regionDisplayName" value="regionId"/>
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originCity"/></td>
            <td>
            	<html:select styleId="originCity" property="to.originCity" styleClass="selectBox width130" onkeypress="enterKeyNavigationFocus(event,'originOfficeType');" onchange="clearOriginOffice();checkGridValueWhenHeaderModify();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>            
          </tr>
                    
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOfficeType" /></td>
            <td>
            	<html:select styleId="originOfficeType" property="to.officeType" styleClass="selectBox width130" onchange="getOriginOfficesByCityAndOfficeType();checkGridValueWhenHeaderModify();" onkeypress="enterKeyNavigationFocus(event,'originOffice');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.originOfficeTypeList" label="label" value="value"/>
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOffice" /></td>
            <td>
	     		<html:select styleId="originOffice" property="to.originOffice" onchange="validateDestOriginOffice();checkGridValueWhenHeaderModify();" styleClass="selectBox width130" onkeypress="enterKeyNavigationFocus(event,'consgNumbers1');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>
            <td class="lable"><bean:message key="label.inmanifest.totalWeight"/></td>
            <td>
            	<input id="totalWeightKg" type="text" maxlength="3" size="2" class="txtbox" readonly="readonly" value="0" style="text-align: right"/>
            	<span class="lable"><bean:message key="label.point"/></span>
            	<input id="totalWeightGm" type="text" maxlength="3" size="2" class="txtbox" readonly="readonly" value="000"/>
            	<span class="lable"><bean:message key="label.kgs"/></span>
            </td>
          </tr>
          
        </table>
      </div>
      
     <!-- Grid -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.load.details"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="bplParcelGrid" width="100%">
           <thead>
            <tr>
               <th width="1%" align="center"><input type="checkbox" class="checkbox" id="chk0" name="chk0" onchange="checkUncheckAllRows('bplParcelGrid','chk')"/></th>
               <th width="2%" align="center" ><bean:message key="label.manifestGrid.serialNo"/></th>                     
               <th width="5%" align="center"><sup class="star">*</sup><bean:message key="label.manifestGrid.consgNo"/></th>
               <th width="5%"><sup class="star">*</sup><bean:message key="label.pickup.pincode"/></th>
               <th width="5%"><bean:message key="label.manifestGrid.destination"/></th>
               <th width="4%"><sup class="star">*</sup><bean:message key="label.manifestGrid.noOfPieces"/></th>
               <th width="4%"><sup class="star">*</sup><bean:message key="label.manifestGrid.weight"/></th>
               <th width="5%"><bean:message key="label.manifestGrid.volWtkgs"/></th>
               <th width="5%"><bean:message key="label.manifestGrid.chargeableWtkgs"/></th>
               <th width="5%"><bean:message key="label.pickup.mobilenumber"/></th>
               <th width="7%"><sup class="star">*</sup><bean:message key="label.manifestGrid.contentDesc"/></th>
               <th width="5%"><sup class="star">*</sup><bean:message key="label.manifestGrid.decValue"/></th>
               <th width="7%"><bean:message key="label.manifestGrid.paperWork"/></th>
               <th width="5%"><sup class="star">*</sup><bean:message key="label.manifestGrid.insuredBy"/></th>
               <th width="3%"><bean:message key="label.manifestGrid.policyNo"/></th>
               <th width="3%"><bean:message key="label.manifestGrid.toPayAmt"/></th>
               <th width="3%"><bean:message key="label.manifestGrid.codLcAmt"/></th>
               <th width="3%"><bean:message key="label.inManifestGrid.baAmt"/></th>
               <th width="3%"><bean:message key="label.manifestGrid.lcBankName"/></th>
               <th width="5%"><bean:message key="label.manifestGrid.customerRefNo"/></th>
               <th width="6%"><bean:message key="label.manifestGrid.remarks"/></th>
            </tr>
          </thead>
          <tbody>

          </tbody>
        </table>
      </div>
     <!-- Grid /-->
    </div>    
</div>

   <!-- Button -->
   <div class="button_containerform">    
	<html:button property="Save" styleClass="btnintform" styleId="btnSave" onclick="saveOrUpdateInBagManifestParcel();" title="Submit">
		<bean:message key="button.label.save"/>
	</html:button>
	<html:button property="Delete" styleClass="btnintform" styleId="btnDelete" onclick="deleteInBplParcelRow('bplParcelGrid');" title="Delete">
		<bean:message key="button.label.delete"/>
	</html:button>
	<html:button property="Print" styleClass="btnintform" styleId="btnPrint" title="PrintInBplparcel" onclick="PrintInBplparcel();" >
		<bean:message key="button.label.Print"/>
	</html:button>	
	<html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelInBplParcel();" title="Clear">
		<bean:message key="button.label.clear"/>
	</html:button>
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->   
  <div style="height: 15px;"></div>          
</div>        
</html:form>
<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000" > </iframe> -->
</body>
</html>