<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CD/AWB Modification</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/coloading/cdAwbModification.js"></script>

</head>

<body>
<html:form action="/cdAwbModification.do?submitName=viewCdAwbModification" styleId="cdAwbModificationForm">  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.cdAwb.cdAwbScreen"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.common.FieldsareMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.cdAwb.fromDate" /></td>
            <td width="25%">
				<html:text styleId="fromDate" property="to.fromDate" styleClass="txtbox width145" onkeypress="enterKeyNavigationFocus(event,'fromDateCal');" maxlength="10"/>
				<a href='javascript:show_calendar("fromDate", this.value)' id="fromDateCal">
                  <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" id="fromDateCal" />
                 </a>
            	<html:hidden property="to.regionTO.regionId" styleId="regionId"/>
            	<html:hidden property="to.transportModeRoadCode" styleId="transportModeRoadCode"/>
            </td>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.cdAwb.toDate" /></td>
            <td width="30%">
            	<html:text styleId="toDate" property="to.toDate" styleClass="txtbox width145" onkeypress="enterKeyNavigationFocus(event,'toDateCal');" maxlength="10"/>
            	<a href='javascript:show_calendar("toDate", this.value)' id="toDateCal" >
                  <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" id="toDateCal" />
                 </a>
            </td>
          </tr>
          
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.cdAwb.regionName" /></td>
            <td>
            	<html:text styleId="regionName" property="to.regionTO.regionName" styleClass="txtbox width145" disabled="true"/>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.cdAwb.status"/></td>
            <td>
            	<html:select styleId="status" property="to.status" styleClass="selectBox width130" onchange="findCdAwbDetails();" onkeypress="enterKeyNavigationFocus(event,'btnSave');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.standardTypeTOs4Status" label="description" value="stdTypeCode"/>
				</html:select>
            	<%-- <html:text styleId="consignmentName" property="to.consignmentTypeTO.consignmentName" styleClass="txtbox width145" readonly="true"/> --%>
            </td>
          </tr>
        </table>
      </div>
      
     <!-- Grid -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="common.label.details"/></div></div>
        <br></br>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="cdAwbGrid" width="100%">
           <thead>
            <tr>
               <th width="5%" align="center"><bean:message key="label.serialNo"/></th>
			   <!-- <th width="5%" align="center"><input type="checkbox" class="checkbox" name="chk0" disabled="disabled"/></th> -->
               <th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.cdAwb"/></th>
               <th width="14%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.dispatchedUsing"/></th>
               <th width="21%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.dispatchedType"/></th>
               <th width="11%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.dispatchDate"/></th>
               <th width="11%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.gatepassNo"/></th>
               <th width="11%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.vendor"/></th>
               <th width="20%" align="center"><sup class="star">*</sup><bean:message key="label.cdAwb.destination"/></th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
     <!-- Grid /-->
    </div>    
</div>

   <!-- Button -->
   <div class="button_containerform">
	<html:button property="Save" styleClass="btnintform" styleId="btnSave" onclick="updateCdAwbDetails();" title="Submit">
		<bean:message key="button.save"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelCdAwbDetails();" title="Cancel">
		<bean:message key="button.cancel"/>
	</html:button>
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->   
  <div style="height: 15px;"></div>
</div>        
</html:form>
</body>
</html>