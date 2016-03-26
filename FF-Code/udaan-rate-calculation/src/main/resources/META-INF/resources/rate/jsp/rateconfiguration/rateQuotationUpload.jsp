<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateQuotationUpload.js"></script>
</head>
<body>        
           
<html:form action="/rateQuotation.do?submitName=viewRateQuotationUpload"
		styleId="rateQuotationForm" method="post" enctype="multipart/form-data">
	<div class="formTable">
    	
	<div class="columnuni">
		<div class="columnleftcaller">
       
            	
            	<table border="0" cellpadding="0" cellspacing="2" width="100%">
            		<tr>
                    	<td width="15%" class="lable"><sup class="mandatoryf">*</sup>Quotation upload file:</td>
                      	<td width="37%"><html:file property="to.quotationUploadFile" styleId="quotationUploadFile" styleClass="txtbox width160"/>
                      	<input name="Upload" type="button" value="Upload" class="btnintform" title="Save" onclick="uploadFile();"/></td>
                 	</tr>
            		
          		</table>
      
        	</div> 
        
          
  		</div>
  			
  		 
  		</div>
</html:form>
</body>
</html>