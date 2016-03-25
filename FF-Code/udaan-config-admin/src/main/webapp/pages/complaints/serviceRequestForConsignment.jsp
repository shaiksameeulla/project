<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script type="text/javascript" language="JavaScript" src="js/complaints/serviceRequestForConsignment.js"></script>
		 <script type="text/javascript" src="js/jquerydropmenu.js"></script>
		 <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
	<!-- 	 <script language="javascript" type="text/javascript">
		function openPopup(){
				
				window.open('popup_ChildCN.html','_blank','width=200,height=100,directories=0,titlebar=0,toolbar=0,location=0,status=0, menubar=0,scrollbars=no,resizable=no,width=400,height=350')
		}
		</script> -->
		
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Welcome to UDAAN</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />

        tab css--><!--<link rel="stylesheet" href="jquery-tab-ui.css" />--><!--tab css ends

        <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        DataGrids
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>popup begins/
        <script language="javascript" type="text/javascript"> -->
		  
		<!--  /* function popitup(url) {
			  newwindow=window.open(url,'name','height=200,width=150');
			  if (window.focus) {newwindow.focus()}
			  return false;
		  }	  */
		    function openPopup(){
				
				window.open('popup_ChildCN.html','_blank','width=200,height=100,directories=0,titlebar=0,toolbar=0,location=0,status=0, menubar=0,scrollbars=no,resizable=no,width=400,height=350')
}
		  </script> -->
         

         <!-- popup script ends/-->

        <!-- Tabs-->
        <!--<script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
        <!--<link rel="stylesheet" href="/resources/demos/style.css" />  -->
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script>  
		$(function() {    $( "#tabs" ).tabs();  });  
        </script>
        <script>  
		$(function() {    $( "#tabsnested" ).tabs();  });  
        </script>
        <!-- Tabs ends /-->

        </head>
        <body>
<div id="wraper">
<html:form action="/serviceRequestForConsignment.do" method="post"  styleId="serviceRequestForConsignmentForm" >
<div class="clear"></div>
<!-- main content -->
<div id="maincontent">
<div class="mainbody">
<div class="formbox">
          <%-- <h1><bean:message key="label.cashbooking.cashBooking"/></h1>
          <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.cashbooking.cashBooking"/></div>
        </div> --%>
<div class="formTable">
          <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
              <!-- <td class="lable"><sup class="star">*</sup>&nbsp;Search By:</td>
              <td ><input name="textfield2" type="text" class="txtbox width130" value=""/></td>
              <td class="lable"><sup class="star">*</sup>Source:</td>
              <td><select name="select6" class="selectBox width130">
                <option value="0">Phone</option>
                <option value="1">Email</option>
                <option value="2">Letter</option>
                <option value="3">Phone no.</option>
              </select></td> -->
              <td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.common.search"/> </td>
              <td width="17%" >
				<html:select styleId="searchType" property="to.searchType" styleClass="selectBox width145" value="CNO">
						<%-- <html:option value=""><bean:message key="label.common.select" /></html:option> --%>
						<c:forEach var="searchCategory" items="${searchCategoryList}" varStatus="status">  
							<option value='${searchCategory.stdTypeCode}'>${searchCategory.description}</option>
							
						<%-- <html:optionsCollection property="${searchCategory}" label="description" value="stdTypeCode"/> --%>
						</c:forEach>
				</html:select>
              <td class="lable"><sup class="star">*</sup><bean:message key="label.complaints.consig.cosnsigNo"/>  </td>
             <!--  <td><input name="textfield2" type="text" class="txtbox width130" value=""/></td> -->
             <td><html:text property="to.cosnsigNo" styleClass="txtbox width130" styleId="cosnsigNo"  value=""/></td>
              
            </tr>
            
            
<!--     <tr>
              <td width="11%" class="lable"><sup class="star">*</sup>&nbsp;Query Type:</td>
              <td width="14%" ><select name="select2" class="selectBox">
                <option value="0">Service Request</option>
                <option value="1">CN</option>
                <option value="2">Booking Reference</option>
                <option value="3">Phone no.</option>
              </select></td>
              <td width="14%" class="lable">&nbsp;</td>
              <td width="22%">&nbsp;</td>
              <td width="14%" class="lable">&nbsp;</td>
              <td width="25%">&nbsp;</td>
            </tr> -->
  </table>
        </div>
<div id="tabs">
<ul>
          <li><a href="#tabs-1">Basic Details</a></li>
          <li><a href="#" onclick="trackingPopUp();">CN Detail</a></li>
        </ul>
<div id="tabs-1">
          <div class="clear"></div>
          <div class="formTable">
           <table border="0" cellpadding="0" cellspacing="2" width="100%">
                      <tr>
                <td width="13%" class="lable">Reference Number:</td>
                <!-- <td width="28%" class="lable1"><input name="textfield5" type="text" class="txtbox width130" value=""/></td> -->
                 <td><html:text property="to.referenceNo" styleClass="txtbox width130" styleId="referenceNo"  readonly="true"/></td>
                <td width="13%" class="lable1">&nbsp;</td>
                <td width="20%" class="lable1">&nbsp;</td>
                <td width="13%" class="lable1">&nbsp;</td>
                <td width="13%" class="lable1">&nbsp;</td>
              </tr>
                    </table>
                    
                    
                <div class="columnuni">
    <div class="columnleftcaller">
              <form method="post" action="index.html">
        <fieldset>
            <legend>&nbsp;CALLERS DETAILS
                  </legend>
                  <table border="0" cellpadding="0" cellspacing="2" width="100%">
            <tr>
                      <!-- <td width="15%" class="lable"><sup class="mandatoryf">*</sup>Name:</td>
                      <td width="14%"><input name="mobile" type="text" class="txtbox width130" id="mobile" value=""/></td>
                      <td width="19%" class="lable"><sup class="mandatoryf">*</sup>Phone:</td>
                      <td width="16%"><input name="mobile8" type="text" class="txtbox width130" id="mobile8" value=""/></td>
                      <td width="14%" class="lable"><sup class="mandatoryf">*</sup>Email:</td>
                      <td width="22%"><input name="mobile10" type="text" class="txtbox width130" id="mobile7" value=""/></td> -->
                      <td width="12%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.name"/> </td>
                       <td><html:text property="to.callerName" styleClass="txtbox width130" styleId="callerName" onkeypress="return onlyAlphabet(event);" maxlength="50" value=""/></td> 
                      <td width="18%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.phone"/> </td>
                       <td><html:text property="to.callerPhone" styleClass="txtbox width130" styleId="callerPhone" onkeypress="return onlyNumeric(event);" onchange="validateMobile(this);" maxlength="10" value=""/></td> 
                      <td class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.email"/> </td>
                       <td><html:text property="to.callerEmail" styleClass="txtbox width130" styleId="callerEmail" maxlength="50" onchange="validateEmail(this.value());" value=""/></td> 
                    </tr>
            <tr>
                      <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.backlineExecutive"/></td>
                   <!--    <td width="14%"><select name="select8" class="selectBox width130">
                        <option value="0">Region1</option>
                        <option value="1">Region2</option>
                </select></td> -->
                 <td width="14%"> <html:select styleId="reasonName" property="to.complaintStatus" styleClass="selectBox width145" value="">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<c:forEach var="empUser" items="${empList}" varStatus="status">  
							<option value='${empUser.empTO.employeeId}~${empUser.empTO.empPhone}~${empUser.empTO.emailId}'>${empUser.empTO.firstName}~${empUser.empTO.lastName}</option>
						</c:forEach>
					</html:select> 
					</td>
					
                      <td width="19%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.complaints.service.employeeName"/></td>
                      <td width="16%"><input name="mobile3" type="text" class="txtbox width130" id="mobile4" value=""/></td>
                      <!-- <td class="lable">Status:</td>
                      <td><select name="select" class="selectBox width130">
                        <option value="0"></option>
                        <option value="1"></option>
                  </select></td> -->
                  <td width="12%" class="lable"> <bean:message key="label.complaints.service.status"/></td>
                      <td width="14%">
	                 <html:select styleId="reasonName" property="to.complaintStatus" styleClass="selectBox width145" value="">
						<html:option value=""><bean:message key="label.common.select" /></html:option>
						<c:forEach var="complaintsStatus" items="${complaintsStatusList}" varStatus="status">  
							<option value='${complaintsStatus.stdTypeCode}'>${complaintsStatus.description}</option>
						</c:forEach>
					</html:select>
                    </tr>
            <tr>
              <!-- <td class="lable"><sup class="mandatoryf">*</sup>Remarks:</td>
              <td><textarea cols="20" rows="3" name="myname">Text here</textarea></td> -->
              <td class="lable"> <bean:message key="label.complaints.service.remark"/></td>
                      <td>
                      <html:text property="to.remark" styleClass="txtbox width130" styleId="remark" value=""/>
                      </td>
              <td class="lable" valign="top"><bean:message key="label.complaints.consigcomm"/></td>
             <%--  <td><input type="radio" class="checkbox" name="type"/><bean:message key="label.complaints.consignorSMS"/><br /> --%>
              <td><html:radio  property="to.consignor" value="consignor" styleId="consignor" /><bean:message key="label.complaints.consignorSMS"/><br />
              <%-- <input type="radio" class="checkbox" name="type"/><bean:message key="label.complaints.consigneeSMS"/> <br /> --%>
              <html:radio  property="to.consignor" value="consignee" styleId="consignee" /><bean:message key="label.complaints.consigneeSMS"/> <br />
             <!--  <input type="radio" class="checkbox" name="type"/> -->
             <html:radio  property="to.consignor" value="caller" styleId="caller" />
              <bean:message key="label.complaints.emailToCaller"/></td>
              <td class="lable">&nbsp;</td>
              <td>&nbsp;</td>
              </tr>
          </table>
                </fieldset>
      </form>
            </div>
    
   
  </div>
            <!-- <div class="button_containerform">
                      
                      <input name="Save" type="button" value="Save" class="btnintform" title="Save"/>
                      <input name="Cancel" type="button" value="Cancel" class="btnintform"  title="Cancel"/>
            </div> -->
            <!-- Button -->
            <div class="button_containerform">
                       <%--  <html:button property="Edit" styleClass="btnintform" styleId="Edit" onclick="editServiceRequest();" > 
					    <bean:message key="button.edit"/>
					    </html:button> --%>
					    
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
       <div id="tabs-2">
       		<div>&nbsp;</div>
  		</div>
       </div>     
        
            </div>
   
  </div>
  </html:form>      
        </div>
</body>
</html>
