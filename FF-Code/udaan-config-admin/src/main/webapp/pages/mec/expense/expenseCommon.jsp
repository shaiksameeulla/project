<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!-- Hidden Fields Start -->

<html:hidden property="to.expenseId" styleId="expenseId" />
<html:hidden property="to.expenseStatus" styleId="expenseStatus" />
<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
<html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode" />
<html:hidden property="to.regionId" styleId="regionId" />
<html:hidden property="to.createdBy" styleId="createdBy" />
<html:hidden property="to.updatedBy" styleId="updatedBy" />
<html:hidden property="to.isValidateScreen" styleId="isValidateScreen" />
<!-- To update old expense status -->
<html:hidden property="to.oldExpId" styleId="oldExpId" />
<html:hidden property="to.expenseOfficeRho" styleId="expenseOfficeRho" />
<html:hidden property="to.glPaymentType" styleId="glPaymentType" />
<%-- <html:hidden property="to.isNegativeGLNature" styleId="isNegativeGLNature" /> --%>
<html:hidden property="to.isEmpGL" styleId="isEmpGL" />
<html:hidden property="to.isOctroiGL" styleId="isOctroiGL" />
<!-- Hidden Fields End -->