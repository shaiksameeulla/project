<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="./pages/resources/html/calendar.html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Udaan-Manual Download Process</title>
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
var processStatus=false;
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
		var stDate=document.getElementById("startDate");
		var endDate=document.getElementById("endDate");
		var recordTypeDom=document.getElementById("recordType");
		var statusDom=document.getElementById("status");
		
		if(isNull(branch.value)){
			alert("Please enter Branch-Code");
			branch.focus();
			return false;
		}
		
		if(isNull(recordTypeDom.value)){
			alert("Please select Record Type to download");
			recordTypeDom.focus();
			return false;
		}
		if(isNull(statusDom.value)){
			alert("Please select Record Status to download");
			statusDom.focus();
			return false;
		}
		if(isNull(stDate.value)){
			alert("Please enter Start Date");
			stDate.focus();
			return false;
		}if(isNull(endDate.value)){
			alert("Please enter End Date");
			endDate.focus();
			return false;
		}
		
		var noOfDays=calculateDateDiff(stDate.value,endDate.value);
		try{
		noOfDays=parseInt(noOfDays,10);
		}catch(e){
			alert(e);
			noOfDays=0;
		}
	//	alert("noOfDays:"+noOfDays);
		if(noOfDays !=null && noOfDays >3 ){
			alert("Data download is allowed only for 3 days at a time  ");
			stDate.value="";
			//endDate.value=""
			stDate.focus();
			return false;
		}
		
		
		var formName=document.getElementsByName("processFrom");
		/*  formName=document.getElementById("processFromId");
		
		var myBody=document.getElementsByTagName("body") 
		formName.setAttribute("action","bcunServlet.ff");
		formName.setAttribute("method","post");
		myBody.appendChild(formName); */

		if(processStatus){
			alert("Request already submitted, please wait while we process your request.");
			return false;
		}
		processStatus=true;
		//Here, you can set parameters with adding button element
		document.processFrom.setAttribute("action","bcunServlet.ff");
		document.processFrom.setAttribute("method","post");;
		document.processFrom.submit();
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


	<form name="processFrom" method="post" action="bcunServlet.ff" id="processFromId">
		<h2>
			<span class="mandatoryMsg">Fields marked with <span
				class="mandatory">*</span> are mandatory
			</span>
		</h2>
		<div
			style="border: 1px #cccccc solid; width: 600px; margin: 0px auto; width: 600px">
			<table>
				<tr>
					<td><br /> <br /></td>
				</tr>
				<tr align="center">
					<td colspan="9" align="center"><img
						src="pages/resources/images/ff_logo.jpg" width="280" height="64"
						alt="DTDC" /><br /></td>
				</tr>
				<tr>
					<td align="center"><br /> <br />
						<P>
							<strong>Manual Central Data Extraction process</strong>
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
									name="BRANCH_CODE" size="15" maxlength="8" style="width: 150px" /></td>
							</tr>
							<tr>
								<td class="lable" align="center">Max Records To be Fetch: <span
									class="mandatory">*</span></td>
								<td class="tdTwo"><input type="text" id="maxRecords"
									name="MAX_RECORD" size="15" maxlength="8" style="width: 150px" /></td>
							</tr>
							<tr>
								<td class="lable" align="center">Record Type: <span
									class="mandatory">*</span></td>
								<td class="lable" align="center"><SELECT name="RECORD_TYPE"
									id="recordType" style="width: 150px">
										<OPTION value="A" selected>ALL</OPTION>
										<OPTION value="M">MASTERS</OPTION>
										<OPTION value="S">STOCK</OPTION>
										<OPTION value="U">USER</OPTION>
										<OPTION value="F">MANIFEST</OPTION>
								</SELECT></td>
							</tr>
							<tr>
								<td class="lable" align="center">Record Status: <span
									class="mandatory">*</span></td>
								<td class="lable" align="center"><SELECT name=STATUS
									id="status" style="width: 150px">
										<OPTION value="N" selected>UNREAD</OPTION>
										<OPTION value="M">MANUAL</OPTION>
										<OPTION value="T">READ</OPTION>
										 <OPTION value="P">ALL</OPTION>
										<OPTION value="I">IN-Progress</OPTION>
								</SELECT></td>
							</tr>
							<tr>
								<td class="lable" align="center">Start Date:<span
									class="mandatory">*</span>
								</td>
								<td class="data"><input type="text" id="startDate"
									name="START_DATE" size="15" maxlength="20" readonly="readonly"/> <span
									class="data"> <img
										src="pages/resources/images/icon_calendar.gif" width="14"
										height="17" alt="Select Date" border="0" longdesc="#"
										onclick="setYears(1980, 2030);showCalender(this, 'startDate');" />
								</span></td>
								<td class="lable" align="center">End Date:<span
									class="mandatory">*</span>
								</td>
								<td class="data"><input type="text" id="endDate"
									name="END_DATE" size="15" maxlength="20" readonly="readonly"/> <span class="data">
										<img src="pages/resources/images/icon_calendar.gif" width="14"
										height="17" alt="Select Date" border="0" longdesc="#"
										onclick="setYears(1980, 2030);showCalender(this, 'endDate');" />
								</span></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><input type="button" style=""
									value="PROCESS" onclick="validate()" /></td>
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