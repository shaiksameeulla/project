<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.capgemini.lbs.framework.utils.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/global.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

function submitForm(url, action){
	var submit=false;
	var formObj=document.getElementById("cnPrintingForm");
	
	if(action=='print'){
		submit=true;
	}
	if(submit){
		formObj.action=url;
		formObj.submit();
	}
}
//This Method will generate unique Requisition Number 
function checkNull(obj) {
	if (obj!="" && obj!= null && obj != "null"){
		return false;
	}else{
		return true;
	}
}

function printCNSeries() 
{	
	var slNumber = document.getElementById("to.startSlNo").value;
	var qnty = document.getElementById("to.quantity").value;
	
	var lengthSlNo=slNumber.length;
	
	if(checkNull(slNumber)){
		alert('Please enter the Start Serial Number');
		return false;
    }

	if(lengthSlNo!=12){
		alert("The length should be 9 alphanumeric series - The First letter input should be character\n The length should be 8 or 12 Numeric series - Input should be only Numeric Value");
		return false;
	}
    
	if(checkNull(qnty)){
		alert('Please enter the Quantity');
		return false;
    }
	

	var url="./cnPrinting.do?submitName=printCnSeries&slNo="+slNumber+"&quantity="+qnty;
	var answer = confirm ("Do you want to print the CN Series?");
	
	if(answer){
		var w =window.open(url,'','height=450,width=600,left=60,top=120,resizable=yes,scrollbars=auto');
		setTimeout(function() 
			{
               if (w.closed) {
            	   if(confirm("Do you want to clear?")){
            		   var url="./cnPrinting.do?submitName=viewCNPrintingPage"; 
            		   window.location =url; 
            	   }
               }
               else
                 setTimeout(arguments.callee, 100);
             }, 100);
		}
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)){
	   //alert("Please give number format value");
      return false;     
	}
   return true;
}
</script>

</head>
<body>
<html:form  action="/cnPrinting" styleId="cnPrintingForm">
     <!--- CONTENT SECTION -->
    <div id="contentwrapper">        	
        	<div class="contentContainer" id="contentContainer">
                <table border="0" cellpadding="0" cellspacing="0" class="formTable" width="80%"> 
		       		<tr>
		            <td align="center" align="center">
		            	<div class="paginationContainer">CN Printing</div>
		            </td>
		            </tr>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" class="formTable" width="80%">
                	<tr>
                        <td>Start Serial Number</td>                        
                        <td align="left">
                            <html:text styleId="to.startSlNo" property="to.startSlNo" styleClass="inputField" size="14" maxlength="14"/>           
                        </td>
                        <td class="lable">&nbsp; </td>
                        <td class="lable">&nbsp; </td>
                    </tr>
                    <tr>
                        <td>Quantity</td>                        
                        <td>
                            <html:text styleId="to.quantity" property="to.quantity" styleClass="inputField" size="4" maxlength="4" onkeypress="return isNumberKey(event)"/>           
                        </td>
                        <td class="lable" width="80%">&nbsp; </td>
                        <td class="lable">&nbsp; </td>
                    </tr>
                    
                    <tr>
                        <td></td>
                        <td></td>                        
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                    	<td class="lable">&nbsp; </td>
			          	<td >
	                	 	 <html:button styleClass="button" property="print" onclick="printCNSeries()">Print</html:button>
	                	</td>
	                	
			            <td class="lable">&nbsp; </td>
			            <td class="lable">&nbsp; </td>
               	 </tr>
                 </table>
                </div>
             	
            </div>
</html:form>
</body>
</html>
