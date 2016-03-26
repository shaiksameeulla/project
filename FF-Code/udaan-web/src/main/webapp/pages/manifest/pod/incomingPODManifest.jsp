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
		<script language="JavaScript"  type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/pod/podManfstCommon.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/manifest/pod/incomingPODManifest.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<!-- DataGrids /-->
		</head>
		<body onload="loadDefaultObjects()">
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
      <html:form  action="/incomingPODManifest.do" method="post" styleId="incomingPODManifestForm"> 
          <!-- main content -->
          <div id="maincontent">
           <html:hidden property="to.dispachOfficeTO.officeId" styleId="orginOfficeId" value="${originOfficeId}"/> 
           <html:hidden property="to.dispachOfficeTO.officeName" styleId="officeName" value="${officeName}"/> 
           <html:hidden property="to.manifestType" styleId="manifestType" value="I"/> 
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.pod.incoming.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.pod.datetime"/></td>
                    <td width="10%" ><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width130"  readonly="true" value="${todaysDate}"/></td>
                    <td class="lable" width="18%"><sup class="star">*</sup>&nbsp;<bean:message key="label.pod.receivingofficecode"/></td>
                    <td><html:text property="to.dispatchOfficeTO.officeCode" styleId="dispatchOffice" styleClass="txtbox width130"  readonly="true" value="${originOfficeCodeName}"/>&nbsp;</td>
                    <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.pod.manifestno"/></td>
                    <td ><html:text property="to.manifestNo" styleId="manifestNo" styleClass="txtbox width130" onblur="isValidManifestNo(this); outPODManifestDestDtls();" onkeypress="enterKeyNavigationFocus(event,'destRegionId');" maxlength ="12"/>
                     <html:button property="Search" styleId="Find" styleClass="btnintgrid" onclick="searchPODManifestDtls()"><bean:message key="button.label.search"/></html:button></td>
                  </tr>
                  <tr>
                    <td class="lable"><sup class="star">*</sup><bean:message key="label.pod.origincode"/><bean:message key="label.pod.region"/></td>
                    <td>
                    <html:select property="to.regionId" styleId="destRegionId" styleClass="selectBox width130" onchange="getAllCities();" onkeypress="enterKeyNavigationFocus(event,'destCity');">
		          	<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		           	<c:forEach var="regionTO" items="${regionTOs}" varStatus="loop">
		              <option value="${regionTO.regionId}" ><c:out value="${regionTO.regionName}"/></option>
		            </c:forEach>  
		           	</html:select></td>
                    <td class="lable"><sup class="star">*</sup><bean:message key="label.pod.origincode"/><bean:message key="label.pod.station"/></td>
                    <td>
                     <html:select property="to.destCityId" styleClass="selectBox width140"  styleId="destCity" onchange="getAllOffices();"  onkeypress="enterKeyNavigationFocus(event,'destOffice');">
              			 <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
           			 </html:select>
                      &nbsp;</td>
                    <td width="10%" class="lable"><sup class="star">*</sup><bean:message key="label.pod.origincode"/><bean:message key="label.pod.office"/></td>
                    <td>
                     <html:select property="to.destOffId" styleClass="selectBox width140" styleId="destOffice"  onkeypress="enterKeyNavigationFocus(event,'cnNumber1');">
           				 <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
            		</html:select>
                      &nbsp;</td>
                    
                  </tr>
                </table>
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.pod.details"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="podConsignmentDetails" width="100%">
                  <thead>
                     <tr>
                      <th width="2%" align="center" ><input type="checkbox" id="checkAll" onclick="selectAllCheckBox();" class="checkbox" name="type"/></th>
                      <th width="4%" align="center" ><bean:message key="label.pod.serialNo"/></th>
                      <th width="13%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.pod.consignmentNo"/></th>
                      <th width="13%"><bean:message key="label.pod.receiveddate"/></th>
                      <th width="18%"><bean:message key="label.pod.deliverydatetime"/></th>
                      <th width="17%"><bean:message key="label.pod.receivername"/></th>
                   </tr>
          </thead>
          
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
  			<html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick = "saveOrUpdatePODManifest();">
				<bean:message key="button.label.save"/></html:button>
			<html:button property="Delete" styleClass="btnintform" styleId="deleteBtn"  onclick = "deleteConsgDtlsClientSide();">
				<bean:message key="button.label.delete"/></html:button>
			<html:button property="Print" styleClass="btnintformbigdis" styleId="printBtn">
				<bean:message key="button.label.Print"/></html:button>
			<html:button property="Clear" styleClass="btnintform" styleId="clearBtn" onclick = "clearForm();">
				<bean:message key="button.label.clear"/></html:button>
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
 </html:form> 
        </div>
<!--wraper ends-->
</body>
</html>
