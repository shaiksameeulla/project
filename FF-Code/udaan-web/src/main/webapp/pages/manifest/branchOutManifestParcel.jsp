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
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/manifest/branchOutManifestParcel.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" charset="utf-8">
	
	var MANIFEST_STATUS_OPEN='${MANIFEST_STATUS_OPEN}';
	var MANIFEST_STATUS_CLOSE='${MANIFEST_STATUS_CLOSE}';
	
	var OGM_SERIES=null;
	var MBPL_SERIES=null;
	var BPL_SERIES='${SERIES_TYPE_BPL_NO}';
	var BAGLOCK_SERIES='${SERIES_TYPE_BAG_LOCK_NO}';
	
	/* weighing machine integration */
	var wmActualWeight=0.000;
	var wmCapturedWeight = 0.000;
	var newWMWt=0.000;
	var bookingDetail=null;
		
	$(document).ready( function () {
		var oTable = $('#branchOutManifestParcelTable').dataTable( {
			"sScrollY": "250",
			"sScrollX": "100%",
			"sScrollXInner":"180%",
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
		branchOutManifestParcelStartup();
		//getWeightFromWeighingMachine();
	} );
	
</script>
<!-- DataGrids /-->
</head>
<body onload="setDataTableDefaultWidth();">
<!--wraper-->
<div id="wraper"> 
	<html:form styleId="branchOutManifestParcelForm"> 
    <!-- main content -->
    	<div id="maincontent">
    		<div class="mainbody">
            	<div class="formbox">
        			<h1><bean:message key="label.manifest.branchParcel"/></h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.manifest.FieldsareMandatory"/></div>
      			</div>
          		<div class="formTable">
        			<table border="0" cellpadding="0" cellspacing="4" width="100%">
                		<tr>
							<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.dateTime"/></td>
							<td width="14%"><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width155" readonly="true" tabindex="-1"/></td>
							<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.branchOutManifest.manifestNo"/></td>
							<td width="26%">
								<html:text property="to.manifestNo" styleId="manifestNo" styleClass="txtbox width155" maxlength="10"  onkeypress = "return callEnterKey(event, document.getElementById('destinationOfficeId'));" onblur="convertDOMObjValueToUpperCase (this);isValidManifestNo(this,'H')" />
								<html:button property="Find" styleClass="btnintgrid" styleId="Find" alt="Search" onclick="searchDtls();">
									<bean:message key="button.label.search"/></html:button>
							</td>
							<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationHub"/></td>
							<td width="16%"><html:text property="to.loginOfficeName" styleId="loginOfficeName" styleClass="txtbox width155" readonly="true" tabindex="-1"  /></td>
						</tr>
						<tr>
							<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationOffice"/></td>
							<td width="14%">
								<html:select property="to.destinationOfficeId" styleId="destinationOfficeId" styleClass="selectBox width155"  onkeypress = "return callEnterKey(event, document.getElementById('loadNoId'));" onchange="checkGridEmpty(this,'consgNo');">
									<html:option value=""><bean:message key="label.option.select" locale="display"/></html:option>
           							<logic:present scope="request" name="officeTOs">
           								<c:forEach var="officeTO" items="${officeTOs}" varStatus="loop">
	              							<option value="${officeTO.officeId}">${officeTO.officeName}</option>
            							</c:forEach>
            						</logic:present>
								</html:select>
							</td>
							<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.loadNo"/></td>
							<td width="26%">
								<html:select property="to.loadNoId" styleId="loadNoId" styleClass="selectBox width35" onkeypress="return callEnterKey(event, document.getElementById('bagLockNo'));">
									<%-- <html:option value=""><bean:message key="label.option.select" locale="display"/></html:option> --%>
									<logic:present scope="request" name="loadLotList">
	                    				<c:forEach var="loadLotTO" items="${loadLotList}">
	              							<option value="${loadLotTO.loadLotId}">${loadLotTO.loadNo}</option>
	            						</c:forEach>
            						</logic:present>
								</html:select>
							</td>
							<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.bagLockNo"/></td>
							<td width="16%"><html:text property="to.bagLockNo" maxlength="8" styleId="bagLockNo" styleClass="txtbox width155" onblur="convertDOMObjValueToUpperCase (this);validateBagLockNo(this,'H')" onkeypress="return callFocusEnterKey(event);" /></td>
						</tr>
						<tr>
							<td width="15%" class="lable"><bean:message key="label.manifest.rfid"/></td>
							<td width="14%"><html:text property="to.rfidNo" styleId="rfidNo" styleClass="txtbox width155" onblur="getRfId(this);" maxlength="4" onkeypress="return callFocusEnterKey(event);" /></td>
							<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.weight"/></td>
							<td width="22%" colspan="3">
								<input type="text" name="finalKgs" id="finalKgs" class="txtbox width30" readonly="readonly" size="11" tabindex="-1"  /><span class="lable">.</span>
								<input type="text" name="finalGms" id="finalGms" class="txtbox width30" readonly="readonly" size="11" tabindex="-1"/><span class="lable"><bean:message key="label.kgs"/></span>
							</td>          
						</tr>
					</table>
				</div>
              	<div id="demo">
        			<div class="title">
                  		<div class="title2"><bean:message key="label.manifest.dtls"/></div>
                	</div>
        			<table cellpadding="0" cellspacing="0" border="0" class="display" id="branchOutManifestParcelTable" width="100%">
	                  	<thead>
	            			<tr>
								<th width="2%" align="center" ><input type="checkbox" id="checkAll" name="checkAll" class="checkbox" tabindex="-1" onclick="selectAllCheckBox(maxCNsAllowed.value);"/></th>
								<th width="3%" align="center" ><bean:message key="label.manifestGrid.serialNo"/></th>
								<th width="11%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifestGrid.consgNo"/></th>
								<th width="5%"><bean:message key="label.manifestGrid.noOfPieces"/></th>
								<th width="9%"><bean:message key="label.manifestGrid.weight"/></th>
								<th width="9%"><bean:message key="label.manifestGrid.contentDesc"/></th>
								<th width="7%"><bean:message key="label.manifestGrid.decValue"/></th>
								<th width="7%"><bean:message key="label.manifestGrid.paperWork"/></th>
								<th width="7%"><bean:message key="label.manifestGrid.toPayAmt"/></th>
								<th width="7%"><bean:message key="label.manifestGrid.codAmt"/></th>
								<th width="7%"><bean:message key="label.manifestGrid.customduty"/></th>
								<th width="8%"><bean:message key="label.manifestGrid.serviceCharge"/></th>
								<th width="8%"><bean:message key="label.manifestGrid.stateTax"/></th>
							</tr>
	          			</thead>
	          			
					</table>
      			</div>
              	<!-- Grid /--> 
               	<!-- Hidden Fields Start -->
	               	<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
					<html:hidden property="to.manifestStatus" styleId="manifestStatus" />
					<html:hidden property="to.bagRFID" styleId="bagRFID" />
					<html:hidden property="to.finalWeight" styleId="finalWeight" />
					<html:hidden property="to.consgIdListAtGrid" styleId="consgIdListAtGrid" />
					<html:hidden property="to.manifestId" styleId="manifestId" />
					<html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed" />
					<html:hidden property="to.maxWeightAllowed" styleId="maxWeightAllowed" />
					<html:hidden property="to.maxTolerenceAllowed" styleId="maxTolerenceAllowed" />
					<html:hidden property="to.loginCityCode" styleId="loginCityCode"/>
					<html:hidden property="to.loginCityId" styleId="loginCityId"/>
					<html:hidden property="to.processCode" styleId="processCode" value="${processCode}"/>
					<html:hidden property="to.regionCode" styleId="regionCode" />
					<html:hidden property="to.regionId" styleId="regionId" />
					<html:hidden property="to.manifestDirection" styleId="manifestDirection" />
					<html:hidden property="to.seriesType" styleId="seriesType" />
					<html:hidden property="to.manifestType" styleId="manifestType" />
					<html:hidden property="to.officeCode" styleId="officeCode"/>
					<html:hidden property="to.bookingType" styleId="bookingType" value=""/>
					<html:hidden property="to.allowedConsgManifestedType" styleId="allowedConsgManifestedType" />
					<html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId"/>
				<!-- Hidden Fields Stop -->
            </div>
  		</div>
		<!-- Button -->
		<div class="button_containerform">
			<html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveOrCloseBranchOutManifestParcel('save');">
				<bean:message key="button.label.save"/></html:button>
			<html:button property="Delete" styleClass="btnintform" styleId="deleteBtn" onclick="deleteConsgDtlsClientSide();">
				<bean:message key="button.label.delete"/></html:button><%-- deleteTableRow(); --%>
			<html:button property="Print" styleClass="btnintform" styleId="printBtn"  onclick="printBranchOutManifest();">
				<bean:message key="button.label.Print"/></html:button>
			<html:button property="Close" styleClass="btnintform" styleId="closeBtn" onclick="saveOrCloseBranchOutManifestParcel('close');">
				<bean:message key="button.label.close"/></html:button>
			<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" >
				<bean:message key="button.label.cancel" /></html:button>	
		</div>
		<!-- Button ends --> 
	<!-- main content ends --> 
	</html:form> 
</div>
<!--wraper ends-->
<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000" > </iframe> -->
</body>
</html>
