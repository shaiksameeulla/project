<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<script type="text/javascript" charset="utf-8" src="js/complaints/serviceRequestFollowup.js"></script>
</head>
<body>        
           
<html:form action="/paperwork.do?submitName=preparePaperwork" styleId="paperworkForm" method="post" enctype="multipart/form-data">
	<div class="formTable">
    	<table border="0" cellpadding="0" cellspacing="2" width="100%">
        	<tr>
            	<td width="16%" class="lable">&nbsp;Date/Time</td>
				<td width="19%" class="lable1"><html:text property="to.serviceRequestPaperworkDateStr" styleId="serviceRequestPaperworkDateStr" readonly="true" tabindex="-1" styleClass="txtbox width160"></html:text></td>
              	<td width="31%" class="lable">Customer Feedback:</td>
              	<td width="19%" class="lable1"><html:text property="to.feedback" styleId="feedback" styleClass="txtbox width160"></html:text></td>
           	</tr>
      	</table>
	<div class="columnuni">
		<div class="columnleftcaller">
        	<fieldset>
            	<legend>&nbsp;PAPER WORK ATTACHEMENT</legend>
            	<table border="0" cellpadding="0" cellspacing="2" width="100%">
            		<tr>
                    	<td width="15%" class="lable">Complaint Letter:</td>
                      	<td width="37%"><html:file property="to.complaintFile" styleId="complaintFile" styleClass="txtbox width150"/></td>
                      	<td width="15%" class="lable">Consignor Copy:</td>
                      	<td width="37%"><html:file property="to.consignorCopyFile" styleId="consignorCopyFile" styleClass="txtbox width150"/></td>
                 	</tr>
            		<tr>
                      	<td width="15%" class="lable">Mail Copy:</td>
                      	<td><html:file property="to.mailCopyFile" styleId="mailCopyFile" styleClass="txtbox width150"/></td>
                      	<td width="15%" class="lable">Undertaking letter & Bill:</td>
                      	<td><html:file property="to.undertakingFile" styleId="undertakingFile" styleClass="txtbox width150"/>&nbsp;</td>
                	</tr>
            		<tr>
              			<td class="lable">Dec/Invoice:</td>
              			<td><html:file property="to.invoiceFile" styleId="invoiceFile" styleClass="txtbox width150" /></td>
              			<td class="lable">Remarks:</td>
              			<td><html:text maxlength="500" property="to.clientMeet" styleId="clientMeet" styleClass="txtbox width160" /></td>
              			<!-- <td class="lable">&nbsp;</td>
              			<td>&nbsp;</td> -->
            		</tr>	
            		<%-- <tr>
              			<td class="lable">Transfer to ICC:</td>
              			<td>
              				<select id="transferIcc" name="to.transferIcc" class="selectBox width155">
              					<option value="0"><bean:message key="label.common.select" /></option>
              						<logic:present name="transferIccList" scope="request"> 
                						<c:forEach var="transferto"	items="${transferIccList}" varStatus="loop">
											<option value="${transferto.serviceRequestTransfertoId}" >${transferto.transfertoDesc}</option>
										</c:forEach>
               						</logic:present>
              				</select>
              			</td>
              			<td class="lable">Remarks:</td>
              			<td><html:text property="to.clientMeet" styleId="clientMeet" styleClass="txtbox width160" /></td>
              		</tr> --%>
          		</table>
                </fieldset>
       		<br />
        	</div>    
  		</div>
  			<html:hidden styleId="paperWorkServiceRequestId" property="to.serviceRequestId" value = "${complaintId}"/>
  		   	
  		   	<html:hidden styleId="complaintFileId" property="to.complaintFileId"/>
  		   	<html:hidden styleId="consignorCopyFileId" property="to.consignorCopyFileId"/>
  		   	<html:hidden styleId="mailCopyFileId" property="to.mailCopyFileId"/>
  		   	<html:hidden styleId="undertakingFileId" property="to.undertakingFileId"/>
  		   	<html:hidden styleId="invoiceFileId" property="to.invoiceFileId"/>

  		   	<html:hidden styleId="complaintNumber" property="to.complaintNumber" value="${complaintNumber}"/>
  		   	<html:hidden styleId="consignmentNumber" property="to.consignmentNumber" value="${consignmentNumber}"/>
  		   	<html:hidden styleId="createdBy" property="to.createdBy"/>
  		   	<html:hidden styleId="updateBy" property="to.updateBy"/>
  		   	<!-- Button -->
            <div class="button_containerform">                      
              <input name="Save" type="button" value="Save" class="btnintform" title="Save" onclick="uploadFile();"/>
              <html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>
            </div><!-- Button ends --> <br />
  		</div>
</html:form>
</body>
</html>