<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="../js/FixedColumns.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="js/tracking/consignmentTracking.js"></script> -->
<script type="text/javascript" language="JavaScript" src="js/complaints/serviceRequestForConsignment.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
<script type="text/javascript" src="/js/common.js"></script>


		<script type="text/javascript" charset="utf-8">
			  $(document).ready( function () {
				var oTable = $('#detailsTable').dataTable( {
					"sScrollY": "220",
					"sScrollX": "80%",
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
			   function detailTracking() { 
				 
				 var oTable2 = $('#detailsTable').dataTable( {
						"sScrollY": "220",
						"sScrollX": "80%",
						"sScrollXInner":"100%",
						"bScrollCollapse": false,
						"bSort": false,
						"bInfo": false,
						"bPaginate": false,
						 "bDestroy":true,
						 "bRetrieve":true,
						"sPaginationType": "full_numbers"
					} );
				 new FixedColumns( oTable2, {
						"sLeftWidth": 'relative',
						"iLeftColumns": 0,
						"iRightColumns": 0,
						"iLeftWidth": 0,
						"iRightWidth": 0
					} );
			 } 
			 
		</script>
        <!-- DataGrids /-->

<!-- Tabs-->

<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script>  
        $(function() {    $( "#tabs" ).tabs();  });  
		
		
		
        </script>
<!-- Tabs ends /-->
</head>

<body>

	<!--wraper-->
	<div id="wraper">
		<html:form action="/serviceRequestForConsignment.do" method="post"
			styleId="serviceRequestForConsignmentForm">
			<input type="hidden" id="childRows" />
			<input type="hidden" id="detailRows" />

			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
			
			<div class="formbox">
        	<h1><bean:message key="label.complaints.service.consign.header"/></h1>
        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.heldup.FieldsareMandatory"/></div>
      	  </div>
				<div class="mainbody">
					<!-- top header form begins -->
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
                            
                            <tr>
		            <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.type"/></td>
		            <td width="26%" >
		            	
					
					<html:select property="to.searchType"  styleId="typeName"  styleClass="selectBox width140">
                   		<c:forEach var="type" items="${searchCategoryList}"  varStatus="loop">
		                  <html:option value="${type.stdTypeCode}" ><c:out value="${type.description}"/></html:option>
		                </c:forEach>
                </html:select>
		            </td>
		            <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.consignmenttracking.consignmentorrefno"/></td>
		            <td width="18%"><html:text property="to.number" styleId="consgNumber" styleClass="txtbox width145" onblur="isValidConsgNum(this);" />
		            	&nbsp;<html:button property="track" styleClass="btnintgrid" styleId="trackBtn" onclick="trackconsignment();">
								<bean:message key="button.search"/></html:button>
					</td>
          		</tr>
                            
							<tr>
								<td colspan="4" class="lable1" align="center">Consignment
									tracking information for <strong>CN No. <label id="cnNum" ></label></strong>
								</td>
							</tr>
							<tr>
								<td colspan="4" class="lable1" align="center"></td>
							</tr>
						</table>
					</div><!-- top header form ends -->
					<!-- tab begins -->
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">Booking Information</a></li>
							<!-- <li onclick="childCN();"><a href="#tabs-2">Child CN</a></li> -->
							<li onclick="detailTracking();"><a href="#tabs-3" >CN Detail</a></li>
						</ul>
						<div id="tabs-1">
          <div class="clear"></div>
          <div class="formTable">
           <table border="0" cellpadding="0" cellspacing="2" width="100%">
                      <tr>
                <td width="13%" class="lable">Reference Number:</td>
                <td><html:text property="to.referenceNo" styleClass="txtbox width130" styleId="referenceNo"  readonly="true" /></td>
                <td width="13%" class="lable1">&nbsp;</td>
                <td width="20%" class="lable1">&nbsp;</td>
                <td width="13%" class="lable1">&nbsp;</td>
                <td width="13%" class="lable1">&nbsp;</td>
              </tr>
                    </table>
                    
                    
                <div class="columnuni">
    <div class="columnleftcaller">
        <fieldset>
            <legend>&nbsp;CALLERS DETAILS
                  </legend>
                  <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
                      
                      <td width="12%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.name"/> </td>
                       <td><html:text property="to.callerName" styleClass="txtbox width130" styleId="callerName" onkeypress="return onlyAlphabet(event);" maxlength="50" value=""/></td> 
                      <td width="18%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.phone"/> </td>
                       <td><html:text property="to.callerPhone" styleClass="txtbox width130" styleId="callerPhone" onkeypress="return onlyNumeric(event);" onchange="validateMobile(this);" maxlength="10" value=""/></td> 
                      <td class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.email"/> </td>
                       <td><html:text property="to.callerEmail" styleClass="txtbox width130" styleId="callerEmail" maxlength="50" onchange="validateEmail(this.value());" value=""/></td> 
                    </tr>
            <tr>
                      <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.backlineExecutive"/></td>
                                   <td width="14%"> <html:select styleId="employeeDtls" property="to.employeeDtls" styleClass="selectBox width145" value="">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<c:forEach var="empUser" items="${empList}" varStatus="status">  
							<option value='${empUser.empTO.employeeId}~${empUser.empTO.empPhone}~${empUser.empTO.emailId}'>${empUser.empTO.firstName}~${empUser.empTO.lastName}</option>
						</c:forEach>
					</html:select> 
					</td>
					
                      <%-- <td width="19%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.employeeName"/></td>
                      <!-- <td width="16%"><input name="mobile3" type="text" class="txtbox width130" id="mobile4" value=""/></td> -->
                       <td><html:text property="to.callerPhone" styleClass="txtbox width130" styleId="empName"  maxlength="10" value=""/></td>
                      --%>
                  <td width="12%" class="lable"> <bean:message key="label.complaints.service.status"/></td>
                      <td width="14%">
	                 <html:select styleId="status" property="to.status" styleClass="selectBox width145" value="">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<c:forEach var="statusCode" items="${complaintsStatusList}" varStatus="status">  
							<option value='${statusCode.stdTypeCode}'>${statusCode.description}</option>
						</c:forEach>
					</html:select>
                    </tr>
            <tr>
              
              <td class="lable"> <bean:message key="label.complaints.service.remark"/></td>
                      <td>
                      <html:textarea  property="to.remark" cols="20" rows="3" styleId="remark" value="" name="myname2">Text here</html:textarea>
              <%-- <html:text property="to.remark" styleClass="txtbox width130" styleId="remark" value=""/> --%>
                      </td>
                 
                     
                      
              <td class="lable" valign="top"><bean:message key="label.complaints.consigcomm"/></td>
              <td><html:radio  property="to.consignor" value="consignor" styleId="consignor" /><bean:message key="label.complaints.consignorSMS"/><br />
              <html:radio  property="to.consignor" value="consignee" styleId="consignee" /><bean:message key="label.complaints.consigneeSMS"/> <br />
             <html:radio  property="to.consignor" value="caller" styleId="caller" />
              <bean:message key="label.complaints.emailToCaller"/></td>
              <td class="lable">&nbsp;</td>
              <td>&nbsp;</td>
              </tr>
          </table>
                </fieldset>
            </div>
    
   
  </div>
           
            <!-- Button -->
            <div class="button_containerform">
                      					    
                        <html:button property="Save" styleClass="btnintform" styleId="save" onclick="saveOrCloseServiceConsg();" > 
					    <bean:message key="button.save"/>
					    </html:button>
					    
                       <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" >
						<bean:message key="button.cancel" />
						</html:button>
                      
                      
            </div>
            <!-- Button ends --> 
  </div>
</div>

						<div id="tabs-3">
							<table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display" id="detailsTable"  >
						    	<thead>									
									<tr>
										<th width="2%" align="center">Sr.No.</th>
										<th width="9%"  align="center">Manifest Type</th>
										<th width="9%" align="center">Date &amp; Time</th>
										<th width="48%" align="center">Consignment Path</th>
									</tr>									
								</thead>
								<tbody>									
								</tbody>
							</table>
						</div>			
					</div>
				</div>
			</div>

        <%--  <div class="button_containerform">
			<html:button property="Clear" styleClass="btnintform" styleId="clearBtn" onclick="clearScreen('clear');">
				<bean:message key="button.clear"/></html:button>
		
		</div> --%>


		</html:form>
	</div>
	<!--wraper ends-->

</body>
</html>