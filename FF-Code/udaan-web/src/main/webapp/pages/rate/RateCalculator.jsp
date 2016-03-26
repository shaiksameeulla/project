<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en" xmlns:mso="urn:schemas-microsoft-com:office:office" xmlns:msdt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">
<head>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="pages/resources/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js" type="text/javascript" ></script>
<script language="javascript">
function submitCalculateRateRequest() {
	/* var url="calculateRate.do?submitName=calculateRate";
	var formId="rateCalculatorForm";
	var ajaxResponse="calculateRateResponse";
	jQuery.ajax({
		url: url,
		data:jQuery("#"+formId).serialize(),
		context: document.body,
		success: function (data){
			ajaxResponse(data);
		},
		error: function( xhr, ajaxOptions, thrownError ) {
			alert('Server Un-available(network error)');
			alert( "xhr :"+xhr+"\tajaxOptions:"+ajaxOptions+"\tthrownError:"+thrownError);
		}
	}); */
	jQuery("#rateCalculatorForm").submit();
}

function calculateRateResponse(data) {
	alert("response: " + data);
}
</script>
</head>
<body>
<html:form action="calculateRate.do?submitName=calculateRate" method="post" styleId="rateCalculatorForm">

	</br>
	<table width = "70%" border="0">
		<tr> 		
			<td width="20%" class="lable">Product Code :</td>
			<td><html:select property="to.productCode" styleClass="selectBox width130" styleId="productType">
			<option value="" selected="selected">---Select ----</option>
			<option value="PC000001">PC000001-Emotional Bond</option>
			<option value="PC000002">PC000002-Cash COD</option>
			<option value="PC000003">PC000003-Topay</option>
			<option value="PC000004">PC000004-BA</option>
			<option value="PC000005">PC000005-Priority</option>
			<option value="PC000006">PC000006-Air Cargo</option>
			<option value="PC000007">PC000007-Credit Card</option>
			<option value="PC000008">PC000008-Train/Surface/Rail</option>
			<option value="PC000009">PC000009-LC</option>
			<option value="PC000010">PC000010-Securitized</option>
			<option value="PC000011">PC000011-Cash</option>
			<option value="PC000012">PC000012-Quality</option>
			<option value="PC000013">PC000013-Normal</option>
			<option value="PC000016">PC000016-Z Series</option>
			<option value="PC000015">PC000015-Within State</option>
			
			</html:select>
			</td>
			<td width="25%" class="lable">Consignment Type :</td>
			<td><html:select property="to.consignmentType" styleClass="selectBox width130" styleId="consignmentType">
			<option value="" selected="selected">---Select ----</option>
			<option value="DOX">DOX (Document)</option>
			<option value="PPX">PPX (Parcel)</option>
			</html:select>
			</td>
		</tr>
		<tr> 
			<td   class="lable">Origin City Code : </td>
			<td><html:text property="to.originCityCode" styleClass="txtbox width130" styleId="originCityCode" value = "" /></td>
			<td   class="lable">Destination Pincode : </td>
			<td><html:text property="to.destinationPincode" styleClass="txtbox width130" styleId="destinationPincode" value = ""/></td>
		</tr>
		<tr>
			<td   class="lable">Weight : </td>
			<td><html:text property="to.weight" styleClass="txtbox width130" styleId="weight" value = "" /></td>
			<td   class="lable">Rate Type : </td>
			<td><html:select property="to.rateType" styleClass="selectBox width130" styleId="rateType">
			<option value="" selected="selected">---Select ----</option>
			<option value="CC">Credit</option>
			<option value="CH">CASH</option>
			<option value="BA">BA</option>
			<option value="FR">Franchisee</option>
			<%-- <option value="ACC">Authorized Collection Center</option> --%>
			</html:select>
			</td>
		</tr>
		<tr>
			<td   class="lable">Customer Code : </td>
			<td><html:text property="to.customerCode" styleClass="txtbox width130" styleId="weight" value ="" /></td>
			<td   class="lable">Declared Value : </td>
			<td><html:text property="to.declaredValue" styleClass="txtbox width130" styleId="rateType"  value =""  /></td>
		</tr>
		<tr>
			<td   class="lable">Discount : </td>
			<td><html:text property="to.discount" styleClass="txtbox width130" styleId="weight" value ="" /></td>
			<td   class="lable">Special Charges : </td>
			<td><html:text property="to.otherCharges" styleClass="txtbox width130" styleId="rateType"  value ="" /></td>
		</tr>
		<tr>
			<td   class="lable">Insured By :: </td>
			<td>
			<html:select property="to.insuredBy" styleClass="selectBox width130" styleId="insuredBy">
			<option value="" selected="selected">---Select ----</option>
			<option value="FF">First FLight</option>
			<option value="C">Customer</option>
			</html:select>
			</td>
			<td   class="lable">is RTO : </td>
			<td>
				<html:select property="to.isRTO" styleClass="selectBox width130" styleId="insuredBy">
				<option value="" selected="selected">---Select ----</option>
				<option value="Y" >Yes</option>
				<option value="N">NO</option>
				</html:select>
			</td>
		</tr>
		<tr> 
			<td   class="lable">Risk Surcharge: </td>
			<td><html:text property="to.riskSurcharge" styleClass="txtbox width130" styleId="riskSurcharge"/></td>
			<td   class="lable">Preference</td>
			<td>
			<html:select property="to.ebPreference" styleId="preference"  styleClass="selectBox width130">
			    <option value="" selected="selected">---Select ----</option>
			    <option value="pref1">pref1</option>
			    <option value="pref2">pref2</option>
			    <option value="pref3">pref3</option>
			    <option value="pref4">pref4</option>
			    <option value="pref5">pref5</option>
			    <option value="pref6">pref6</option>
			    <option value="pref7">pref7</option>
			    <option value="pref8">pref8</option>
			    <option value="pref9">pref9</option>
			    <option value="pref10">pref10</option>
			    <option value="pref11">pref11</option>
			    <option value="EBP0016">EBP0016</option>
			</html:select>
			</td>
		</tr>
		<tr> 
			<td   class="lable">LC Amount: </td>
			<td><html:text property="to.lcAmount" styleClass="txtbox width130" styleId="lcAmount"/></td>
			<td   class="lable">Service On :</td>
			<td>	
			<html:select property="to.serviceOn" styleId="serviceOn"  styleClass="selectBox width130">
			    <option value="" selected="selected">---Select ----</option>
			    <option value="B">BF-14</option>
			    <option value="A">AF-14</option>
			    <option value="S">Sunday</option>
			</html:select>
			</td>
		</tr>
		<tr> 
			<td   class="lable">COD Amount: </td>
			<td><html:text property="to.codAmount" styleClass="txtbox width130" styleId="codAmount" value = ""/>
			</td>
			<td   class="lable">Rate Calculation Request Date:</td>
			<td><html:text property="to.calculationRequestDate" styleClass="txtbox width130" styleId="calculationRequestDate" value = "${todaysDate}"/></td>
		</tr>
		<tr> 
			<td   class="lable">TO Pay Charge: </td>
			<td><html:text property="to.toPayCharge" styleClass="txtbox width130" styleId="codAmount" value = ""/>
			</td>
			<td   class="lable"></td>
			<td></td>
		</tr>
		<tr> 
		
		</tr>
		<tr>
		<td colspan="4" align = "center"><input type="button" class = "btnintform" name="Calculate" value="Calculate" onclick="submitCalculateRateRequest()"/>
	    <input type="button" class = "btnintform" name="Clear" value="Clear"/></td>
		</tr>
	</table>
	
</html:form>
</body>
</html>