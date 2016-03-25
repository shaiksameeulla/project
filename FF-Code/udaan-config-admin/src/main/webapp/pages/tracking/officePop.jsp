<%@ page isELIgnored="false"%>
        <%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
        <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
        <%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
        <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<c:if test="${not empty consgTO}">
Address:
<c:out value="${consgTO.officeTO.address1}" />
<c:out value="${consgTO.officeTO.address2}" />
<c:out value="${consgTO.officeTO.address3}" />
<br/>
Phone:
<c:out value="${consgTO.officeTO.phone}" />
<br/>
Email id:
<c:out value="${consgTO.officeTO.email}" />
</c:if>