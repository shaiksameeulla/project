<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/locationsearch/locationSearch.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>

<link href="css/jquery.autocomplete.css" rel="stylesheet"
	type="text/css" />
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Location Search</title>

<script type="text/javascript" charset="utf-8">
	function loadLocationList() {
		showProcessing();
		var data = new Array();
		jQuery('input#enteredLocation').flushCache();

		<c:forEach var="locationTO" items="${locationList}" varStatus="rstatus">
		data['${rstatus.index}'] = "${locationTO}";
		</c:forEach>
		jQuery("#enteredLocation").autocomplete(data);
		jQuery.unblockUI();
		document.getElementById("enteredLocation").focus();
	}
</script>
</head>
<body onload="loadLocationList();">
<body>
	<html:form action="/locationSearch.do" method="post"
		styleId="locationSearchForm">

		<div id="wraper">
			<div class="clear">
				<div id="maincontent">
					<div class="mainbody">
						<div class="formbox">
							<h1>Location Search</h1>
							<div class="mandatoryMsgf">
								<span class="mandatoryf">*</span>
								<bean:message key="label.mandatory" />
							</div>
						</div>

						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="35%" class="title2"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Search_lel" /></td>
									<td width="30%"><html:text property="to.enteredLocation"
											styleClass="txtbox width330" styleId="enteredLocation"
											onkeypress="return callEnterKey(event, document.getElementById('searchBtn'));" /></td>
									<td width="35%" class="lable1"><html:button
											property="Search" styleClass="btnintgrid" styleId="searchBtn"
											onclick="getLocationMappingDetails()">
											<bean:message key="button.search" />
										</html:button></td>

									<!-- <td width="10%">&nbsp;</td> -->
								</tr>
							</table>
						</div>
						<div class="demo">
							<div class="title">
								<div class="title2">
									<bean:message key="label.locationDetails" />
								</div>
							</div>

							<table cellpadding="0" cellspacing="0" border="0" class="display"
								width="100%" id="addressTable"
								style="overflow: visible; margin-left: 0px; width: 993px">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>