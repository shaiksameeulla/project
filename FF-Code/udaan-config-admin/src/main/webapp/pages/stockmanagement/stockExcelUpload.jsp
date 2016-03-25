<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> -->
<!-- <script type="text/javascript" src="js/jquerydropmenu.js"></script> --><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockExcelUpload.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var url="${url}";
	
	if(!isNull(url)){
		var receiptNUmbers="${stockExcelUploadForm.to.acknowledgementNumber}";
		if(!isNull(receiptNUmbers)){
			alert("Stock Receipt number(s) :"+receiptNUmbers+" have been generated");
		}
		globalFormSubmit(url,'stockExcelUploadForm');
	}
	
} );

</script>

<!-- DataGrids /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/stockExcelUpload.do" method="post"
			styleId="stockExcelUploadForm"
			enctype="multipart/form-data">
			
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							Bulk Stock Upload
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">

							<tr>
								<td width="18%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;Excel Upload</td>
								<td width="35%" colspan="5"><html:file
										property="to.stockExcelFile" styleId="fileUpload"
										styleClass="txtbox"
										onkeypress="return callEnterKey(event, document.getElementById('Save'));" /></td>

							</tr>
						</table>
					</div>
					<!-- Grid /-->
				</div>


			</div>

			<!-- Button -->
			<div class="button_containerform">

				<html:button styleId="Save" styleClass="btnintform"
					onclick="upload()" property="Save">
					save
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="clearScreen();" styleId="cancel">
					clear
				</html:button>


				<%-- 				<html:button property="Print" styleClass="btnintform" onclick="" --%>
				<%-- 					styleId="print"> --%>
				<%-- 					<bean:message key="label.bulkBooking.Print" locale="display" /> --%>
				<%-- 				</html:button> --%>
			</div>
			<br />
			<br />
			<!-- Button ends -->
			 <html:hidden property="to.loggedInUserId"/>
			 <html:hidden property="to.canUpdate" styleId="canUpdate"/>
			 <html:hidden property="to.acknowledgementNumber" styleId="acknowledgementNumber"/>
			<!-- main content ends -->
		</html:form>
	</div>

	<!--wraper ends-->
</body>
</html>