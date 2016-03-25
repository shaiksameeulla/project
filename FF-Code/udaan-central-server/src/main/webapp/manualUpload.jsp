<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="./pages/resources/html/calendar.html"%>
<jsp:directive.page import="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Udaan-Manual Upload Process</title>
<link href="pages/resources/css/global.css" rel="stylesheet"
	type="text/css" />
<link href="pages/resources/css/content.css" rel="stylesheet"
	type="text/css" />
<link href="pages/resources/css/calendar.css" rel="stylesheet"
	type="text/css" />



	<script language="JavaScript"
	src="pages/resources/js/jquery/jquery-1.4.4.js"
	type="text/javascript"></script>
<script language="JavaScript"
	src="pages/resources/js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script language="JavaScript" type="text/javascript"
	src="pages/resources/js/common.js"></script>
	<script language="JavaScript" type="text/javascript"
	src="pages/resources/js/manualDownload.js"></script>
<script language="JavaScript" src="pages/resources/js/ts_picker.js"
	type="text/javascript"></script>
<script language="JavaScript" src="pages/resources/js/calendar.js"
	type="text/javascript"></script>

<script>
	jQuery.noConflict();
	function isNull(value){
		var flag=true;
		if (value !=undefined && value!= null && value!=""  && value != "null" && value!=" " && value !="0"  ){
			flag = false;
		}
		return  flag;
	}
	function validate() {
		var branch=document.getElementById("brCode");
		var fileDom=document.getElementById("file");
		var uploadedFileName=fileDom.value;
		
		if(isNull(branch.value)){
			alert("Please enter Branch-Code");
			branch.focus();
			return false;
		} if(isNull(fileDom.value)){
			alert("Please Upload zip file");
			fileDom.focus();
			return false;
		}
		if(!endsWith(uploadedFileName,".zip")){
			alert("Please upload only zip file");
			fileDom.focus();
			fileDom.value="";
			return false;
		}
		var formName=document.getElementsByName("processFrom");
		/*  formName=document.getElementById("processFromId");
		
		var myBody=document.getElementsByTagName("body") 
		formName.setAttribute("action","bcunServlet.ff");
		formName.setAttribute("method","post");
		myBody.appendChild(formName); */

		//Here, you can set parameters with adding button element
		document.processFrom.setAttribute("action","bcunUploadServlet.ff");
		document.processFrom.setAttribute("method","post");;
		document.processFrom.submit();
	}
	function endsWith(str, suffix) {
	    return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}
	window.onbeforeunload = confirmExit;
	window.onload = myOnload;

	function confirmExit() {
		jQuery.blockUI({
					message : '<h3><img src="pages/resources/images/loading_animation.gif"/></h3>'
				});
	}
	function myOnload() {
		//alert("${UDAAN}");
	jQuery.unblockUI();
}
</script>
</head>
<body>


	<form name="processFrom" method="post" action="bcunUploadServlet.ff" id="processFromId" enctype="multipart/form-data">
		<h2>
			<span class="mandatoryMsg">Fields marked with <span
				class="mandatory">*</span> are mandatory
			</span>
		</h2>
		<div
			style="border: 1px #cccccc solid; width: 600px; margin: 0px auto; width: 600px">
			<table>
			
			<tr align="center">
					<td colspan="9" align="center"><img
						src="pages/resources/images/ff_logo.jpg" width="280" height="64"
						alt="DTDC" /><br /></td>
				</tr>
				<tr>
					<td><br /> <br />
					<center> 
<b>Current  Date 
and time is:&nbsp; <font color="#f3f3f3">

<jsp:scriptlet>out.print(""+new Date()); </jsp:scriptlet>
</font>
</b>
</center></td>
				</tr>
				
				<tr>
					<td align="center"><br /> <br />
						<P>
							<strong>Manual Data Upload process</strong>
						</P></td>
				</tr>
				<tr>
					<td><br /> <br /> <br /></td>
				</tr>
				<tr>
					<td>
						<table align="center">
							<tr style="border-bottom: none;">
								<th class="leftLogo"></th>
								<th class="rightLogo"></th>
							</tr>
							<tr>
								<td class="lable" align="center">BranchCode : <span
									class="mandatory">*</span></td>
								<td class="tdTwo"><input type="text" id="brCode"
									name="BRANCH_CODE" size="10" maxlength="4" style="width: 150px" /></td>
							</tr>
							<tr>
								<td class="lable" align="center">Upload Bcun Inbound Zip file : <span
									class="mandatory">*</span></td>
								<td class="tdTwo"><input type="file" name="file" id="file" /></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><input type="button" style=""
									value="Upload" onclick="validate()" /></td>
							</tr>
						</table>
					</td>
					<td></td>
				</tr>
				<tr></tr>
			</table>
		</div>
	</form>

</body>
</html>