<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!-- Button Container  START HERE-->
<div class="button_containerform">
          <html:button property="Save" styleClass="btnintform" styleId="Save" title="Save">
   			<bean:message key="button.label.save" /></html:button>
   			<html:button property="Print" styleClass="btnintform" styleId="Print" title="Print">
   			<bean:message key="button.label.Print" /></html:button>
   			
   			<html:button property="Delete" styleClass="btnintform" styleId="Delete" title="Delete">
   			<bean:message key="button.label.delete" /></html:button>
   			
   			<html:button property="Modify" styleClass="btnintform" styleId="Modify" title="Modify DRS">
   			<bean:message key="button.label.modify" /></html:button>
   			
   			<html:button property="Discard" styleClass="btnintform" styleId="Discard" title="Discard DRS">
   			<bean:message key="button.label.discard" /></html:button>
   			<html:button property="Cancel" styleClass="btnintform" styleId="Cancel" title="Clear Screen">
   			<bean:message key="button.label.Cancel" /></html:button>
   			
  </div>
  <!-- Button Container  ENDS HERE-->