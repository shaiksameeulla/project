<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	
	<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script> 
	<script language="JavaScript" src="js/common.js" type="text/javascript" ></script>
	 <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script> 
	 <script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript" ></script>
	 <script language="JavaScript" src="login/js/createUser.js"	type="text/javascript"></script>
	 <link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	

<script language="JavaScript" type="text/javascript">

$(document).ready( function () {
	var oTable = $('#loginIdTable').dataTable( {
		"sScrollY": "55",
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
} );



function enterKeyNavigation(e) {
	
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("search").click();
}

function enterKeyNavigationForRegion(e) {
	
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("cityId").click();
}

function enterKeyNavigationForCity(e){
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("empName").click();
}

var empIDArr = new Array();
function loadEmployeeName(){
	
	var message="<c:out value='${message}'/>";
		if(message !=""){
			alert(message);
		}
	hideBtn('add');
	/* jQuery('input#empName').flushCache();
	  var data = new Array();
	
	 <c:forEach var="empTO" items="${employeesList}" varStatus="rstatus"> 
	   data['${rstatus.index}'] = "${empTO.firstName}-${empTO.lastName}~${empTO.empCode}";
	   empIDArr['${rstatus.index}'] = "${empTO.employeeId}";
	  </c:forEach>
	  jQuery("#empName").autocomplete(data); */
	//alert('empIDArr is='+ empIDArr);
}


function renderSearchResult(){
	//var jsonResult="<c:out value='${jsonResult}'/>";
	alert('renderSearchResult:');
	if(jsonResult != null && jsonResult != "") {
		//searchEmployeeResponse(jsonResult);
		jQuery("#empCode").val("<c:out value='${jsonResult.empTO.empCode}'/>");
	}
		
}




$(document).ready( function () {
	var oTable = $('#example').dataTable( {
		"sScrollY": "30",
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
} );

</script>
	<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		fnClickAddRow();
		//renderSearchResult();
	});
	
</script>
</head>
<body onload="loadEmployeeName();">
	<!--wraper-->
	<div id="wraper">
   		
   		<html:form action="/createEmployeeLogin" method="post"	styleId="createEmployeeLoginForm" >
   			<div class="clear"></div>
   			<!-- main content -->
   			<div id="maincontent">
    			<div class="mainbody">
            		<div class="formbox">
		       			<h1>
		       				<bean:message key="label.umc.createUser" bundle="loginResource" />
		       				<br><div class="mandatoryMsgf"><span class="mandatoryf">*&nbsp</span><bean:message 
key="label.login.FieldsAreMandatory" bundle="loginResource" /></div>
		       			</h1>
		    		</div>
		    		<%-- <html:hidden property="" /> --%>
        			<div class="formContainer">
        				<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationRegion" /></td>
									<td><html:select property="to.empTO.regionId"
											styleClass="selectBox  width130"
											styleId="regionId"
											
											onchange="getAllCitiesByRegion(this);">
											<option value="">----Select----</option>
											<c:forEach var="region" items="${regionTOs}"
												varStatus="loop">

												<option value="${region.regionId}">
													<c:out value="${region.regionName}" />
												</option>

											</c:forEach>
										</html:select></td>
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.manifest.destinationCity" /></td>
									<td width="15%"><html:select
											property="to.empTO.cityId"
											styleClass="selectBox width130" styleId="cityId" onchange="getEmpByCity(this);">
											<option value="">----Select----</option>
										
										
										
										</html:select></td>

									<td width="58%" class="lable1" colspan="5"><sup class="star">*</sup><bean:message key="label.umc.empName" bundle="loginResource" />
				  					<html:text property="to.empTO.firstName" maxlength="15"	styleClass="txtbox width145" styleId="empName" onkeypress="enterKeyNavigation(event)" /></td> 
								</tr>
							</table>
						</div>
        				<!-- Button -->
        				<div class="button_containerform">
               				<input name="Search" type="button" value="Search" class="btnintgrid"  title="Search" id="search" onclick="searchEmployee();" />
               			</div>
        				<!-- Button ends --> 
      				</div>
              		<div id="demo">
        				<div class="title">
                			 <div class="title2">
                				<bean:message key="label.umc.empDetails" bundle="loginResource" />
                			</div> 
                		</div>
       					<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
							<thead>
								<tr>
									<th width="5%"><bean:message key="label.umc.empCode"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.fname"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.lname"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.mobile"
											bundle="loginResource" /></th>
									<th width="12%"><sup class="star">*</sup><bean:message key="label.umc.email"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.designation"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.officeName"
											bundle="loginResource" /></th>
								</tr>
							</thead>
							<tbody>
								<html:hidden property="to.empTO.employeeId" styleId="employeeId"/>
								  <tr class="gradeA">
									<td><html:text	property="to.empTO.empCode" styleId="empCode" maxlength="25" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
								 	<td><html:text	property="to.empTO.firstName" styleId="firstName" maxlength="50" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
									<td><html:text	property="to.empTO.lastName" styleId="lastName" maxlength="50" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
									<td><html:text	property="to.empTO.empPhone" styleId="mobile"  maxlength="15" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
									<td><html:text	property="to.empTO.emailId" styleId="email"  maxlength="50" styleClass="txtbox width130" readonly="true" tabindex="-1" onchange="handleEmailChange();"/></td>
									<td><html:text	property="to.empTO.designation" styleId="designation" maxlength="15" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
									<td><html:text	property="to.empTO.officeName" styleId="officeID" maxlength="15" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
								</tr> 
							</tbody>
						</table>
      				</div>
        			<br />
        			<div class="formTable">
        				<table border="0" cellpadding="0" cellspacing="3" width="100%" id="loginIdTable" class="display" >        				
        				<thead>
								<tr>
									<th width="1%" ></th>
									<th width="1%" >Sr.No</th>
									<th width="3%" styleClass="txtbox width80"><bean:message key="label.umc.loginId" bundle="loginResource" /></th>
									<th width="3%"><bean:message key="label.umc.status" bundle="loginResource" /></th>
								</tr>
							</thead>
							<tbody></tbody>
        				</table>
      				</div>
              		<!-- Grid /-->
              		
            	</div>
  			</div>
          	<!-- main content ends --> 
          	<!-- Button -->
          	
    		<div class="button_containerform">
    		<input name="Add" type="button" value="Add" class="btnintform"  title="Add" onclick="fnClickAddRow();"  id="add" />
    			<input name="Save" type="button" value="<bean:message key="button.label.Save" bundle="loginResource" />" class="btnintform"  title="Save" id="Save"  onclick="saveOrUpdateEmployeeLogin();"  />
    			<input name="Modify" type="button" value="<bean:message key="button.label.Modify" bundle="loginResource" />" class="btnintform" title="Modify"   onclick="modifyEmployeeLogin();" />
    			<input name="Activate" type="button" value="<bean:message key="button.label.Activate" bundle="loginResource" />" class="btnintform"  title="Activate" id="active"   onclick="activateDeactivateEmployeeLogin('active');" />
    			<input name="Deactivate" type="button" value="<bean:message key="button.label.Deactivate" bundle="loginResource" />" class="btnintformbig1"  title="Deactivate"  id="de-active"  onclick="activateDeactivateEmployeeLogin('de-active');"/>
    			<input name="Reset Password" type="button" value="Reset Password" class="btnintformbig1"  title="Reset Password" id="resetPawsd"  onclick="resetEmpPassword('reset');" />
  			
  			</div>
          	<!-- Button ends -->
		</html:form>
	</div>
</body>
</html>
