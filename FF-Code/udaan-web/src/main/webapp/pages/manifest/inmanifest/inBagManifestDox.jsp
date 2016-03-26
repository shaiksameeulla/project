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
<title>In BPL - Dox</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inBagManifestDox.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inBagManifest.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/inmanifest/inManifest.js"></script>
<!-- Start param to get Remarks -->
<jsp:include page="inManifest.jsp"/>	           
<!-- End param to get Remarks   -->
</head>

<body>
<html:form action="/inBagManifest.do?submitName=viewInBagManifestDox" styleId="inBagManifestForm">  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.manifest.header.inBplDox"/></h1>
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
            	<html:text styleId="manifestNumber" property="to.manifestNumber" styleClass="txtbox width130" onchange="findBplNumberDox();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'btnSearch','BPL Number.');" maxlength="10"/>
				<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="findBplNumberDox();" onfocus="bplNumberFocus();" title="Search">
			        <bean:message key="button.label.search"/>
			    </html:button>
            </td>
            <td width="15%" class="lable"><bean:message key="label.inmanifest.destinationRegion"/></td>
            <td width="17%">
            	<html:text styleId="destinationRegion" property="to.destinationRegion" styleClass="txtbox width130" readonly="true"/>
            	<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId"/>
            	<html:hidden property="to.manifestId" styleId="manifestId"/>
            	<html:hidden property="to.manifestWeight" styleId="manifestWeight"/>
            	<html:hidden property="to.manifestType" styleId="manifestType"/>
            	<html:hidden property="to.lockNum" styleId="lockNum"/>
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
            	<!-- <input type="hidden" id="deletedIds"/> -->
            	<%-- <html:hidden property="to.outManifestId" styleId="outManifestId"/> --%>
            </td>
          </tr>
          
          <tr>
            <td class="lable"><bean:message key="label.inmanifest.destinationOffice"/></td>
            <td><html:text styleId="destinationOffice" property="to.destinationOffice" styleClass="txtbox width130" disabled="true"/> </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originRegion"/></td>
            <td>
            	<html:select styleId="originRegion" property="to.originRegion" styleClass="selectBox width130" onchange="getOriginCitiesByRegion();"
            	 onkeypress="enterKeyNavigationFocus(event,'originCity');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.originRegionTOs" label="regionDisplayName" value="regionId"/>
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originCity"/></td>
            <td>
            	<html:select styleId="originCity" property="to.originCity" styleClass="selectBox width130" onchange="clearOriginOffice();"
            		onkeypress="enterKeyNavigationFocus(event,'originOfficeType');" >
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>            
          </tr>
                    
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOfficeType" /></td>
            <td>
            	<html:select styleId="originOfficeType" property="to.officeType" styleClass="selectBox width130"
            	 onchange="getOriginOfficesByCityAndOfficeType();" onkeypress="enterKeyNavigationFocus(event,'originOffice');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.originOfficeTypeList" label="label" value="value"/>
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.manifest.originOffice" /></td>
            <td>
	     		<html:select styleId="originOffice" property="to.originOffice" onchange="validateDestOriginOffice();" styleClass="selectBox width130"
	     		 onkeypress="enterKeyNavigationFocus(event,'manifestNumbers1');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>
          </tr>
          
        </table>
      </div>
      
     <!-- Grid -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.load.details"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="bplDoxGrid" width="100%">
           <thead>
            <tr>
               <th width="3%" align="center"><input type="checkbox" class="checkbox" id="chk0" name="chk0" onchange="checkUncheckAllRows('bplDoxGrid','chk')"/></th>
               <th width="5%" align="center"><bean:message key="label.manifestGrid.serialNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.manifestGrid.ogmOpenManifestNumber"/></th>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.manifestGrid.weight"/></th>
               <th width="12%"><bean:message key="label.manifestGrid.remarks"/></th>
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
	<html:button property="Save" styleClass="btnintform" styleId="btnSave" onclick="saveOrUpdateInBagManifetDox();" title="Submit">
		<bean:message key="button.label.save"/>
	</html:button>
	<html:button property="Delete" styleClass="btnintform" styleId="btnDelete" onclick="deleteInBplRow('bplDoxGrid');" title="Delete">
		<bean:message key="button.label.delete"/>
	</html:button>
	<html:button property="Print" styleClass="btnintform" styleId="btnPrint" title="Print" onclick="printInBplDox();">
		<bean:message key="button.label.Print"/>
	</html:button>	
	<html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelInBplDox();" title="Clear">
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