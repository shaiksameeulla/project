<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/manifest/codDetails.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>

<script type="text/javascript">
	var rowCount = '<%=request.getParameter("rowCount")%>';
	if (rowCount == "") {
		rowCount = 0;
	}
	rowCount = parseInt(rowCount);
</script>
</head>
<body>
<body onload="setDefaultValues('<%=request.getParameter("rowCount")%>')" onkeypress="ESCclose(event);">
<!--wraper-->
<div id="wraper">
	<!-- header -->
	<div id="popupheader">
		<div class="ltxt">COD Details</div>
		<div class="rtxt"><a title="Close Window" href="javascript:close();"><img src="images/close_bt.png" alt="Close Window" /></a></div>
	</div>
	<!-- header ends-->
	<div id="popupheader_orange"></div>
	<!-- maincontent -->
	<div id="popupmain">
		<table border="0" cellpadding="7" cellspacing="5" width="100%">
			<tr>
				<td width="12%" class="lable"><sup class="mandatoryf" style="color: white;">*</sup>COD Amount:</td>
				<td width="18%"><input name="textfield" type="text"
					class="txtbox width130" id="lcAmount" size="12" value=""
					onkeypress = "return onlyNumberAndEnterKeyNav(event, this, 'saveBtn');"
					onblur="setToFixed(this)" /></td>
			</tr>
		</table>
	</div>
	<!-- maincontent ends-->
	<!-- footer -->
	<div id="popupfooter">
		<input name="Save" type="button" class="btnintform" id="saveBtn"
			value='Save' title="Save" onclick="submitValues();" /> 
		<input name="Cancel" type="button" class="btnintform" id="cancelBtn"
			value='Cancel' title="Cancel" onclick="cancelVal();" />
	</div>
	<!-- footer ends-->
</div>
<!-- wraper ends -->
</body>
</html>