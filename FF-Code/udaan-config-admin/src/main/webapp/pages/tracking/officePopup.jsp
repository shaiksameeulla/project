<%@ page isELIgnored="false"%>
        <%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
        <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
        <%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
        <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<c:if test="${not empty manifestTO}">
Address:
<c:out value="${manifestTO.officeTO.address1}," /><br/>
<c:out value="${manifestTO.officeTO.address2}," /><br/>
<c:out value="${manifestTO.officeTO.address3}." />
<br/>
Phone:
<c:out value="${manifestTO.officeTO.phone}" />
<br/>
Email id:
<c:out value="${manifestTO.officeTO.email}" />
</c:if>