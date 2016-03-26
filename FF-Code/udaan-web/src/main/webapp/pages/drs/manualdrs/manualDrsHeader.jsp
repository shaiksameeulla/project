<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Header START FROM HERE -->
<div class="formTable">
        <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
            <td width="5%" class="lable"><sup class="star">*</sup> <bean:message key="label.drs.drs" /> <bean:message key="label.common.date" /> <strong><bean:message key="label.common.slash" /></strong> <bean:message key="label.common.time" /><bean:message key="label.common.colon" /></td>
            
            <td width="5%" >
            <html:text styleId="drsDateTimeStr" property="to.drsDateTimeStr" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
            <td width="3%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.manual.drstype" /><bean:message key="label.common.colon" /></td>
            <td width="5%">
              
              <html:select property="to.manifestDrsType" styleId="manifestDrsType" styleClass="selectBox width140" onchange="clearGridForHeaderChange();clearDrsNumber();clearHeaderForManualDrs(this)" onkeydown="return enterKeyNav('drsNumber',event.keyCode);">
                			<html:option value=""><bean:message key="label.common.select" /></html:option>
                          	<logic:present name="manualDrsTypeMap" scope="request">
                          	<html:optionsCollection name="manualDrsTypeMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select>
              </td>
            <td width="5%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.drs" /> <bean:message key="label.common.no" /><bean:message key="label.common.colon" /></td>
            
            <td width="10%">
            <html:text styleId="drsNumber" property="to.drsNumber" styleClass="txtbox width130" size="16" maxlength="14" onkeydown="return findOnEnterKey(event.keyCode);"/>
            <html:button styleClass="btnintgrid" styleId="Find" property="FIND" alt="DRS number Search" onclick="find()">
            					<bean:message key="button.label.search" />
            				</html:button>
            </td>
           
          </tr>
           <tr>
          		 <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.common.office" /> <bean:message key="label.common.code" /><bean:message key="label.common.colon" /></td>
            <td>
            <html:text styleId="drsOfficeName" property="to.drsOfficeName" styleClass="txtbox width130" readonly="true" tabindex="-1"/>
            </td>
          		 <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.drs" /> <bean:message key="label.drs.for" /><bean:message key="label.common.colon" /></td>
            <td>
              
              <html:select property="to.drsFor" styleId="drsFor" styleClass="selectBox width140" onchange="getDRSForPartyDetails(this)" onkeydown="return enterKeyNav('drsPartyId',event.keyCode);">
                			<html:option value=""><bean:message key="label.common.select" /></html:option>
                          	<logic:present name="drsForMap" scope="request">
                          	<html:optionsCollection name="drsForMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select>
              </td>
          		<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.generate.runsheet.employee" /> <bean:message key="label.common.slash" /> <bean:message key="label.drs.da.code" /><bean:message key="label.common.colon" /></td>
            <td>
              <html:select property="to.drsPartyId" styleId="drsPartyId" styleClass="selectBox width140" onkeydown="return enterKeyNav('consignmentType',event.keyCode);">
                			<html:option value=""><bean:message key="label.common.select" /></html:option>
              			<logic:present name="partyTypeMap" scope="request">
                          	<html:optionsCollection name="partyTypeMap" label="value" value="key"/>
                          	</logic:present>
              			
              			</html:select>
              </td>
          </tr>
                  <tr>
          <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.bulkBooking.consigType" /></td>
            <td>
              <html:select property="to.consignmentType" styleId="consignmentType" styleClass="selectBox width130" onkeydown="return enterKeyNav('fsOutTimeImg',event.keyCode);" onchange="clearGridForHeaderChange()">
                			<html:option value=""><bean:message key="label.common.select" /></html:option>
              			<logic:present name="consTypeMap" scope="request">
                          	<html:optionsCollection name="consTypeMap" label="value" value="key"/>
                          	</logic:present>
              			
              			</html:select>
            </td>
           <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.drs.fs.out" />&nbsp;<bean:message key="label.drs.fs.date" />/<bean:message key="label.common.time" /></td>
            <td>
            <html:text styleId="fsOutTimeDateStr" property="to.fsOutTimeDateStr" styleClass="txtbox width80" maxlength="12" readonly="true" tabindex="-1" onkeydown="return enterKeyNav('fsOutTimeHHStr',event.keyCode);" onblur="isFutureDateForDrs(this,'fsOutTimeMinStr')"/><a href='javascript:show_calendar("fsOutTimeDateStr", this.value)' id="fsOutTimeImg" > 
         				 		<img src="images/calender.gif" alt="Select Date" width="15" height="15" border="0"  /></a> 
            <html:text styleId="fsOutTimeHHStr" property="to.fsOutTimeHHStr" styleClass="txtbox width30"  tabindex="-1" maxlength="2" onkeydown="return enterKeyNav('fsOutTimeMMStr',event.keyCode);" onchange="clearFsOutTimMM()"/>:<html:text styleId="fsOutTimeMMStr" property="to.fsOutTimeMMStr" styleClass="txtbox width30"  tabindex="-1" maxlength="2" onkeydown="return enterKeyNav('fsInTimeImg',event.keyCode);" onchange="validateFsOutMinutes()"/> </td>
			
					<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message
							key="label.drs.time.indate" />/<bean:message
							key="label.common.time" /></td>
					<td><html:text styleId="fsInTimeDateStr"
							property="to.fsInTimeDateStr" styleClass="txtbox width80" onchange="validateFSInTime('date')" onkeydown="return enterKeyNav('fsInTimeHHStr',event.keyCode);" readonly="true"  /> <a href='javascript:show_calendar("fsInTimeDateStr", this.value)' id="fsInTimeImg" > 
         				 		<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0"  /></a> 
							<html:text styleId="fsInTimeHHStr"
							property="to.fsInTimeHHStr" styleClass="txtbox width30" onchange="clearFsInTimMM()"  maxlength="2"	 onkeydown="return enterKeyNav('fsInTimeMMStr',event.keyCode);" />:<html:text styleId="fsInTimeMMStr"
							property="to.fsInTimeMMStr" styleClass="txtbox width30"  onblur="validateFSInTime('time')" onkeydown="return enterKeyNav('loadNumber',event.keyCode);"  maxlength="2"/></td>

          </tr>
                  <tr>
           
           <td  class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.loadNo" /></td>
            <td colspan="5">
               <html:select property="to.loadNumber" styleId="loadNumber" styleClass="selectBox width60" onkeydown="return focusOnFirstRowConsignment(event.keyCode)" >
                          	<logic:present name="loadNumberMap" scope="request">
                          	<html:optionsCollection name="loadNumberMap" label="value" value="key"/>
                          	</logic:present>
              			</html:select>
              
              
              </td>
             
          </tr>
          
        
                </table>
      </div>
       <!-- Header ENDS  HERE -->      