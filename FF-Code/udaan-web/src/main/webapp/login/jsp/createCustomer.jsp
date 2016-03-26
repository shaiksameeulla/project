<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<script language="JavaScript" src="login/js/createUser.js"	type="text/javascript"></script>
	<!-- <script language="JavaScript" src="js/jquery/jquery/jquery.blockUI.js" type="text/javascript" ></script>   -->
	<!-- <script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script> -->  
	<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script> 
	<script language="JavaScript" src="js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
	<!-- <script language="JavaScript" src="js/common.js" type="text/javascript" ></script> -->
	<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
	<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
	<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript">
function enterKeyNavigation(e) {
	
	if (window.event)
        e = window.event;
    if (e.keyCode == 13) 
		document.getElementById("search").click();
}


function loadCustomerName(){
	var message="<c:out value='${message}'/>";
	if(message !=""){
		alert(message);
		window.location="./createCustomerLogin.do?submitName=customerLogin";
	}
	/* jQuery('input#businessName').flushCache();
	 var data = new Array();
	 <c:forEach var="custTo" items="${customersList}" varStatus="rstatus"> 
	   data['${rstatus.index}'] = "${custTo.businessName}~${custTo.customerCode}";
	  </c:forEach>
	  jQuery("#businessName").autocomplete(data); */
	
}

</script>
</head>
<body onload="loadCustomerName();">
	<!--wraper-->
	<div id="wraper">
	
		<html:form action="/createCustomerLogin" method="post"	styleId="createCustomerLoginForm">
			<div class="clear"></div>
			<div id="errorDiv" style="color:red; margin-top:5px;" >
	        <html:messages name="error" id="message" bundle="loginError">
	         <LI>
	           <bean:write name="message"/>
	         </LI>
	        </html:messages>
	    </div>
	    <div id="warningDiv" style="color:yellow; margin-top:10px;" >
	        <html:messages name="warning" id="message" bundle="loginError">
	         <LI>
	           <bean:write name="message" />
	         </LI>
	        </html:messages>
	    </div>
	    <div id="infoDiv" style="color:white; margin-top:10px;" >
	        <html:messages name="info" id="message" bundle="loginError">
	         <LI>
	           <bean:write name="message" />
	         </LI>
	        </html:messages>
	    </div> 
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.umc.createUser" bundle="loginResource" />
							<br><div class="mandatoryMsgf"><span class="mandatoryf">*&nbsp</span><bean:message key="label.login.FieldsAreMandatory" bundle="loginResource" /></div>
						</h1>						
					</div>					
					<div class="formContainer">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
								
								<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationRegion" /></td>
									<td><html:select property="to.custTO.regionId"
											styleClass="selectBox  width130"
											styleId="regionId"
											
											onchange="getAllCitiesByRegionForCust(this);">
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
											property="to.custTO.cityId"
											styleClass="selectBox width130" styleId="cityId" onchange="getCustByCity(this);">
											<option value="">----Select----</option>
										</html:select></td>
								
									<td width="58%" class="lable1" colspan="5"><sup class="star">*</sup><bean:message	key="label.umc.custName" bundle="loginResource" /> 
									<html:text	property="to.custTO.businessName" maxlength="15"  styleClass="txtbox width145" styleId="businessName" onkeypress="enterKeyNavigation(event)" />
								</tr>
							</table>
						</div>
						<!-- Button -->
						<div class="button_containerform">
						
							<input name="Search" type="button" value="Search"	class="btnintgrid" title="Search" id="search" onclick="searchCustomer();"/>
						</div>
						<!-- Button ends -->
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2" >
								<bean:message key="label.umc.custDetails" bundle="loginResource" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="example" width="100%">
							<thead>
								<tr>
									<th width="12%"><bean:message key="label.umc.custCode"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.custName"
											bundle="loginResource" /></th>
									<th width="12%"><span class="mandatoryf">*&nbsp</span><bean:message key="label.umc.email"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.mobile"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.city"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.custType"
											bundle="loginResource" /></th>
									<th width="12%"><bean:message key="label.umc.status"
											bundle="loginResource" /></th>
								</tr>
							</thead>
							<tbody>
							<html:hidden property="to.custTO.customerId" styleId="customerId"/>
								<tr class="gradeA">
									<td><html:text	property="to.custTO.customerCode" styleId="customerCode"  styleClass="txtbox width140" readonly="true" /></td>
									<td><html:text	property="to.custTO.businessName" styleId="custName"  styleClass="txtbox width140" readonly="true"/></td>
									<td><html:text	property="to.custTO.email" styleId="email"  styleClass="txtbox width240" readonly="true" /></td>
									<td><html:text	property="to.custTO.mobile" styleId="mobile"  styleClass="txtbox width100" readonly="true" /></td>
									<td><html:text	property="to.custTO.city" styleId="city"  styleClass="txtbox width100" readonly="true"/></td>
									<td><html:text	property="to.custTO.customerTypeTO.customerTypeDesc" styleId="customerType" styleClass="txtbox width100" readonly="true"/></td>
									<td><html:text	property="to.userTO.active" styleId="active"  styleClass="txtbox width80" readonly="true" /></td>
								</tr>
							</tbody>
						</table>
					</div>
					<br />
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="25%" class="lable1"><bean:message key="label.umc.loginId" bundle="loginResource" /> 
								<html:text	property="to.userTO.userName" styleClass="txtbox width145" styleId="userName" readonly="true"/>
								</td>
								<td width="75%">&nbsp;</td>
							</tr>
						</table>
					</div>
					<!-- Grid /-->

				</div>
			</div>
			<!-- main content ends -->
			<!-- Button -->

			<div class="button_containerform">

				<input name="Save" type="button" value="Save" class="btnintform"	title="Save" id="Save"  onclick="saveOrUpdateCustomerLogin();" /> 
				<input	name="Activate" type="button" value="Activate" class="btnintform"	title="Activate" id="activate"   onclick="activateDeactivateCustomerLogin('active');" />
				<input name="Deactivate" type="button" value="Deactivate"class="btnintform"  title="Deactivate" id="deactivate"  onclick="activateDeactivateCustomerLogin('de-active');" /> 
				<input	name="Reset Password" type="button" value="Reset Password"	class="btnintformbig1" id="resetBtn" title="Reset Password" onclick="resetPassword('reset');" />
			</div>
			<!-- Button ends -->
			
	</div>
	</html:form>
</body>
</html>
