<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

       <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Follow Up</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
        <!-- tab css--><!--<link rel="stylesheet" href="jquery-tab-ui.css" />--><!--tab css ends-->
        <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script><!-- popup begins/-->
        <script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/complaints/paperwork.js"></script>
		<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet" />
		<script language="JavaScript" src="js/jquery/jquery-ui.min.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/jquery/ui/jquery.ui.core.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/jquery/ui/jquery.ui.widget.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/jquery/jquery.dimensions.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/jquery/jquery.bgiframe-2.1.2.js" type="text/javascript" ></script>
	<!-- 	<script language="JavaScript" type="text/javascript" src="js/firstLevelNav.js"></script> -->	
		<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/complaints/legalComplaints.js"></script>
         <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
         <script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/complaints/complaintDetails.js"></script>
		<script type="text/javascript">
		var complaintNumber="${complaintNumber}";
		var complaintId="${complaintId}";
		var consignmentNumber="${consignmentNumber}";
		var complaintStatus="${complaintStatus}";
		var callerEmail="";
		
			function disableAllInputElements() {
				if (complaintStatus == "Resolved") {
					$(":button").addClass('button_disable');
					$(":button").attr('disabled', 'disabled');
					$('#tabs').find('input, textarea, button, select').attr(
							'disabled', 'disabled');
				}
			}
			
			function enableAllInputElements() {
				if (complaintStatus == "Resolved") {
					$('#tabs').find('input, textarea, button, select').attr(
							'disabled', false);
				}
			}


			$(document).ready(function() {
				var oTable = $('#followupDetails').dataTable({
					"sScrollY" : "250",
					"sScrollX" : "100%",
					"sScrollXInner" : "120%",
					"bScrollCollapse" : false,
					"bSort" : false,
					"bInfo" : false,
					"bPaginate" : false,
					"sPaginationType" : "full_numbers"
				});
				new FixedColumns(oTable, {
					"sLeftWidth" : 'relative',
					"iLeftColumns" : 0,
					"iRightColumns" : 0,
					"iLeftWidth" : 0,
					"iRightWidth" : 0
				});
				// To call service request details function
				searchServiceReqDtls(complaintNumber);
				
			});

			function showAlert() {
				var tabName="${tabName}";
				//alert("tabName"+tabName);
				var msg = "${successMsg}";
				var failureMsg = "${failureMsg}";
				var index = "${index}";
				//alert("index"+index);
				if (!isNull(index)) {
					$("#tabs").tabs("option", "active", index);
					if (!isNull(msg)) {
						alert(msg);
					} else if (!isNull(failureMsg)) {
						alert(failureMsg);
					}
					if (index == "7") {
						searchPaperwork();
					}
					if (index == "5") {
						viewLegalComplaint(complaintId,complaintNumber,consignmentNumber,complaintStatus);
					}
					if(index == "3"){
						viewCriticalComplaint(complaintNumber);
					}
				} else {
					prepareComplaintFollowup();
				}
				if(!isNull(tabName)){
					var index1 = $('#tabs a[href="#tabs-2"]').parent().index();
					//alert("index1"+index1);
					$("#tabs").tabs("option", "active", index1);
					viewCriticalComplaint(complaintNumber);
				
				}
			}
		</script>
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
        <script>  
		$(function() {    $( "#tabsnested" ).tabs();  });  
        </script>
        <!-- Tabs ends /-->
		
        </head>
 <body onload = "showAlert();disableAllInputElements();">
<!--wraper-->
<div id="wraper">
<div id="popupheader">
	<div class="ltxt" align="center"><h3><bean:message key="label.followUp.title" /></h3></div>
	<div class="rtxt"><a title="Close Window" href="javascript:close();"><img src="images/close_bt.png" alt="Close Window" /></a></div>
</div>
<!-- header ends-->
<div id="popupheader_orange"></div>
<div class="clear"></div>
<!-- main content -->
<div id="maincontent">
<div class="mainbody">
<div class="formbox">
	<%-- <h1><bean:message key="label.followUp.title" /></h1> --%>
	<h1>&nbsp;<span class="lable" style="font-weight: bold;"><bean:message key="label.complaints.header.serviceRequestNo" /> </span> <span class="lable"> ${complaintNumber} </span>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="lable" style="font-weight: bold;"><bean:message key="label.complaints.header.cnno"/> </span><span class="lable">  ${consignmentNumber}</span></h1>
	<div class="mandatoryMsgf">
	<span class="mandatoryf"><bean:message key="symbol.common.star" /></span>
	&nbsp;<bean:message key="label.common.FieldsareMandatory" />
</div>

</div>


<div id="tabs">
		 <ul>
		 	<li><a href="#tabs-1" onclick="searchServiceReqDtls(complaintNumber);"><bean:message key="label.complaintDtls.title" /></a></li>
			<li><a href="#tabs-3" onclick="prepareComplaintFollowup();"><bean:message key="label.complaintFollowUp.title" /></a></li>
			<li><a href="#tabs-8" onclick="getComplaintFollowupDetails();"><bean:message key="label.followUpDtls.title" /></a></li>
			<li><a href="#tabs-4" onclick="solveComplaints();"><bean:message key="label.solveComplaint.title" /></a></li>
			<li><a href="#tabs-2" onclick="viewCriticalComplaint(complaintNumber);"><bean:message key="label.criticalComplaint.title" /></a></li>
			<li><a href="#tabs-6" onclick="getCriticalClaimComplaintDtls(complaintNumber);"><bean:message key="label.claimComplaint.title" /></a></li>
			<li><a href="#tabs-7" onclick="viewLegalComplaint(complaintId,complaintNumber,consignmentNumber,complaintStatus);"><bean:message key="label.legalComplaint.title" /></a></li>
			<li><a href="#tabs-9" onclick="searchPaperwork();"><bean:message key="label.paperwork.title" /></a></li>
			<li><a href="#tabs-10" onclick="preparePaperwork();"><bean:message key="label.complaintsFile.downlaod" /></a></li>
		</ul>
		<!---tab 1 begins-->
		<div id="tabs-1">
			<%-- <%@ include file="complaintDetails.jsp" %> --%>
			<jsp:include page="complaintDetails.jsp" />
        </div><!---tab 1 ends-->
	    <!---tab 3 begins-->
		<div id="tabs-3">
         <%@ include file="complaintFollowUp.jsp" %>
        </div><!---tab 3 ends-->
        <div id="tabs-8">
			<jsp:include page="complaintFollowUpDetails.jsp" />
		</div><!---tab 8 ends-->
	    <!---tab 4 begins-->
		<div id="tabs-4">    
			<jsp:include page="solveComplaint.jsp" />      
         </div> <!---tab 4 ends-->
         <!-- Tab 2 START -->
		<div id="tabs-2">
			<jsp:include page="criticalComplaint.jsp" />
		</div><!-- Tab 2 END -->
	    <!---tab 6 begins-->
		<div id="tabs-6">
	         <jsp:include page="criticalClaimComplaint.jsp" /> 
		</div><!---tab 6 ends-->
    	<!---tab 7 begins-->
		<div id="tabs-7">
        	 <jsp:include page="legalComplaint.jsp" /> 
        </div> <!---tab 7 ends-->
	    <!---tab 8 begins-->
     	<!---tab 9 begins-->
		<div id="tabs-9">
			<jsp:include page="paperwork.jsp" />
		</div><!---tab 9 ends-->
		<!-- Tab 10 START -->
		<div id="tabs-10">
			<jsp:include page="downloadComplaintsFile.jsp" />
		</div><!-- Tab 10 END -->
      </div>   
        	  <!-- Grid /--> 
            </div><!------------------------------------main ----------------body ends------------------------------->
   
  </div> <!-- main content ends --> 
          <!-- footer -->
        </div>
<!--wraper ends-->
</body>
