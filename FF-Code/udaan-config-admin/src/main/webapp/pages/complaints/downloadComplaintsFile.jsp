<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><bean:message key="label.complaints.comaplaintsFile.title" /></title>

</head>
<body>
<div class="formTable">
	<div id="demo">
       	<div class="title">
           	<div class="title2"><bean:message key="label.complaints.comaplaintsFile.title" /></div>
        </div>
        <br/><br/>
		<table cellpadding="1" cellspacing="1" border="0" class="display" id="complaintsFileTable" width="100%">
			<thead>
           		<tr>
           		  	<th align="center"><bean:message key="common.label.serialNo"/></th>
                   	<th align="center"><bean:message key="label.complaints.fileName"/></th>
                   	<th align="center"><bean:message key="label.complaints.fileDesc"/></th>
                   	<th align="center"><bean:message key="label.complaints.uploadedBy"/></th>
                   	<th align="center"><bean:message key="label.complaints.uploadedDate"/></th>
               	</tr>
    	   	</thead>
       		<tbody>
       			<c:forEach var="complaintsFileDtls" items="${complaintsFileList}" varStatus="loop">
		        	<tr class="gradeA">
			        	<td class="center"><c:out value="${loop.count}" /></td>
	                    <td class="center"><a href="./paperwork.do?submitName=downloadFile&paperWorkId=${complaintsFileDtls.paperWorkId}" title="Click Here To Download"><c:out value="${complaintsFileDtls.fileName}" /></a></td>
	                    <td class="center"><c:out value="${complaintsFileDtls.fileDescrition}" /></td>
	                    <td class="center"><c:out value="${complaintsFileDtls.uploadedBy}" /></td>
	                    <td class="center"><c:out value="${complaintsFileDtls.uploadedDate}" /></td>
					</tr>
				</c:forEach>
       		</tbody>
       	</table>
       	    <!-- Button -->
            <div class="button_containerform">   
			<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>       
            </div><!-- Button ends --> 
	</div>
</div>
</body>