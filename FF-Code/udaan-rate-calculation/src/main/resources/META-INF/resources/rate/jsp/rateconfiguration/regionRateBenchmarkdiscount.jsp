<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Region Rate BenchMark Discount</title>

<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script> 

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script language="JavaScript" src="js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="rate/js/rateconfiguration/regionRateBenchmarkDiscount.js"></script>

<script type="text/javascript" charset="utf-8">


$(document).ready( function () {
	var oTable = $('#discountGrid').dataTable( {
		"sScrollY": "300",
		"sScrollX": "100%",
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
	
	loadDefaultValues();
} );




</script>
</head>

<body>
 <html:form action="/rateBenchMarkDiscount.do?submitName=viewBenchMarkDiscount" styleId="regionRateBenchMarkDiscountForm" > 

 <div id="wraper">  
<div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><strong><bean:message key="label.Rate.rateDiscount"/></strong></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mandatory"/></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                    <td width="13%" class="lable"><sup class="star">*</sup><bean:message key="label.Rate.IndustryCategory"/></td>
                  <td width="40%"> <html:select styleId="rateIndustryCategoryId" property="to.industryCategory" styleClass="selectBox width145" onchange="getDiscountDetails();">
                   <logic:present name="rateIndustryCategoryId" scope="request">

							<html:optionsCollection property="to.rateIndCatList" label="label" value="value" />

</logic:present>

</html:select></td>
	<html:hidden property="to.regionTO" styleId="regionId"  value="${regionTOs}" />
		  <html:hidden  styleId="discountApproved"  property="to.discountApproved" />

                    <td width="20%" class="lable">&nbsp;</td>
                    <td width="27%">&nbsp;</td>
                  </tr>
                  
                </table>
          </div>
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.Rate.details"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="discountGrid" width="100%">
                  <thead>
            <tr>
                      <th width="17%"><sup class="star">*</sup><bean:message key="label.Rate.RHOName"/></th>
                      <th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.Discount%"/></th>
                      <th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.ApproverCode"/></th>
                      <th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.ApproverName"/></th>
                    </tr>
          </thead>
                  <tbody>
                  
               	<logic:present name="regionTOs" scope="request"> 
                    <c:forEach var="regionList" items="${regionTOs}" varStatus="loop">
                  		  <tr class="gradeA" align="left">
		             
		              <td class="center"><span id="regionName${loop.count}"><c:out value="${regionList.regionName}" ></c:out></span></td>
                      <td class="center"><input type="text"  class="txtbox width75" id="discount${loop.count}" name="to.discountPercentage" onblur="validateDiscount(this);"  onkeypress="return onlyDecimal(event);" size="11"/></td>
                     <td class="center"> <input type="text"   class="txtbox width75" size="11" id="empCode${loop.count}" onblur="getEmployeeDetails(this,${loop.count});"/></td>
                    <td class="left"><span class="center"> <input type="text"   class="txtbox width130" size="25" id="empName${loop.count}" readonly="readonly" tabindex="-1"/></span></td>
  					  <input type="hidden"  id="empId${loop.count}"  name="to.employeeId" />
  						<input type="hidden" id="regionId${loop.count}"  name="to.regionId" value="${regionList.regionId}" />
  			      	  <input type="hidden"  id="regionRateBenchMarkDiscount${loop.count}"  name="to.regionRateBenchMarkDiscountArr" />
  			      	  
                    </tr>
		                 
		     </c:forEach>
		    </logic:present>
         
                   
                    
                  
  </tbody>
                </table>
      </div>
              <!-- Grid /--> 
            </div>
  </div>
          
          <!-- Button -->
          <div class="button_containerform">
            
    <html:button property="editBtn" styleClass="btnintform" styleId="editBtn"  onclick="editDiscountDetails();"><bean:message key="button.edit"/></html:button>
   <html:button property="saveBtn" styleClass="btnintform" styleId="saveBtn" onclick="saveAndSubmitDiscount('N')"><bean:message key="button.save"/></html:button>
    <html:button property="submitBtn" styleClass="btnintform" styleId="submitBtn"  onclick="saveAndSubmitDiscount('Y');"><bean:message key="button.submit"/></html:button>
   
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
       </div>
<!-- wrapper ends -->

</html:form>
</body>

</html>
