<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
	

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%-- <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to Opsman</title>
<%-- <link href="${csspath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${csspath}/css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="${csspath}/css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="${csspath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${csspath}/css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" /> --%>
	<link href="/opsman-report-old/static/css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="/opsman-report-old/static/css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="/opsman-report-old/static/css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="/opsman-report-old/static/css/global.css" rel="stylesheet" type="text/css" />
		<link href="/opsman-report-old/static/css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/report/commonReport.js"></script>
</head>



<!-- 
<script type="text/javascript">
var xmlHttp;
function createXMLHttpRequest() {
if (window.ActiveXObject) {
xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
}
else if (window.XMLHttpRequest) {
xmlHttp = new XMLHttpRequest();
}
}
 
function doRequestUsingGETCountry(url) {
 
createXMLHttpRequest();
queryString =   url ;
queryString = queryString + "&selcountry="+document.forms[0].country.options[document.forms[0].country.selectedIndex].value;
xmlHttp.onreadystatechange = handleStateChange;
xmlHttp.open("GET", queryString, true);
xmlHttp.send(null);
}
 
function handleStateChange() {
if(xmlHttp.readyState == 4) {
if(xmlHttp.status == 200) {
parseResults();
}
}
}
function parseResults() {
var responseText = document.createTextNode(xmlHttp.responseText);
var returnElements=xmlHttp.responseText.split("||");
//Process each of the elements   
for ( var i=0; i<returnElements.length; i++ ){
 
if(returnElements[i]!="")
{
valueLabelPair = returnElements[i].split(";")
document.getElementById('state').options.length= returnElements.length;    
document.getElementById('state').options[i] = new Option(valueLabelPair[0], valueLabelPair[1]);
}
}
}
function inc(filename)
{
var body = document.getElementsByTagName('body').item(0);
script = document.createElement('script');
script.src = filename;
script.type = 'text/javascript';
body.appendChild(script)
}


</script>


 -->

        <script>
         /*  function getCitiesByRegCode(regCode,index){
			
			$.getJSON("BookingCityIdAction.do", {
				regCode : regCode,
				
			}, function(data) {
				var options = '';
				var len = data.length;
				 for ( var i = 0; i < len; i++) {
					options += '<option value="' + data[i].cityCode + '">'
							+ data[i].cityName + '</option>';
				}
				options += '<option value="0" selected="selected">Select</option>';
				$("#city" + index).html(options); 
			});

			
		} */
         /*  function validate {
              var region = document.getElementById('CityNames').value;
              alert('RegionCode is :'+region);
              
          } */
          
          function validate(){
        	  var selectBox =document.getElementById("cityes");
        	  var selectedValue = selectBox.options[selectBox.selectedIndex].value;
              //alert('vale of city: '+selectedValue);
               
        	  var cityName1 = document.getElementById('cityes').value;
      		
      		  if(cityName1=="0" || cityName1==" "|| cityName1 == null){
      			//alert('Please select originRegion');
      			var res = showVariable(cityName1);
      		    alert(res);
      			return false;
      		  }
        	}
          
          function submit(){
             var city=document.getElementById("origenId");
        	var url = './execute.do?submitName=getStations&region='
        		+ city;
        	alert('vale of city: '+cityes);
        	//return false;
        	
          }
			
        
             </script>

<body>
  <div id="wraper"> 
 
     <%--   <%@include file="/WEB-INF/jsp/includes/menubar.jsp"%> --%>

    <div class="clear"></div>
   <!-- main content -->
    <div id="maincontent">
      <div class="mainbody">
         <div class="formbox">
           <h1>Booking Details</h1>
           <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
        </div>
        
        <%-- <html:form method="post" action="BookingCityCode.do">
        
        
        </html:form> --%>
        
     <html:form method="post" action="/BookingCityIdAction" >
     
    
      <div class="formTable">
         <table border="0" cellpadding="0" cellspacing="5" width="100%">
         
         <tbody>
           <tr>
              <td width="24%" ><span class="mandatoryf">*</span> Select Origin Region </td>
                 
                 <td>
                 <html:select property="origenId" onchange="submit();">
                  <!-- <select  id="origenId" property="origenId" onchange="validate();"> -->
                     <option value="0">Select</option> 
                       <option value="1">Andhra Pradesh</option>
                       <option value="3">Chennai</option>
                       <option value="4">Delhi</option>
                       <option value="7">Gujarat</option>
                       <option value="11">Haryana</option>
                       <option value="10">Himachal Pradesh</option>
                       <option value="19">Jamshedpur / Jharkhand / Orissa / Bihar</option>
                       <option value="6">Karnataka</option>
                       <option value="8">Kerala</option>
                       <option value="15">Madhya Pradesh / Chattisgarh</option>
                       <option value="13">Maharashtra</option>
                       <option value="2">Mumbai</option>
                       <option value="14">North East</option>
                       <option value="9">Panjab and J&amp;K</option>
                       <option value="16">Rajasthan</option>
                       <option value="17">Tamil Nadu North</option>
                       <option value="18">Tamil Nadu South</option>
                       <option value="12">Uttar Pradesh (East)</option>
                       <option value="20">Uttar Pradesh (West)</option>
                       <option value="5">West Bangal</option>
                       </td>
                 <!-- </select>
           -->
              </html:select> 
          <%-- <td width="24%" class="lable"><span class="mandatoryf">*</span> Select Origin City </td>
          <logic:iterate id="origenId" name="cityNames">
          <option value="0" selected="selected">Select</option>
            
          </logic:iterate> --%>
          
    <tr>  
         
		
           </tr> 
           <%-- <tr> <td><html:submit value="submit"></html:submit>    </td></tr> --%>
           
          </tbody>    
         </table>
      </div>
      
       <%-- <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
          <tr>
          
            <td width="24%" class="lable" ><span class="mandatoryf">*</span> Select Origin Region </td>
            <td width="24%"><input type="text"/> <form:select path="region"  
				onchange="getCitiesByRegCodes(this.value,1)">
				<option value="0" selected="selected" >Select</option>
				<form:options items="${regionList}" 
				itemValue="regionCode"
				itemLabel="regionName" />
				
				</form:select>
			</td>
            <td width="24%" class="lable"><span class="mandatoryf">*</span> Select Origin City </td>
            <td width="24%"><form:select path="cityId"  
				 id="city1">
				 
				<option value="0" selected="selected">Select</option>
				</form:select>
				</td>
		  </tr>	
		 <tr>
			 <td width="24%" class="lable"> <span class="mandatoryf">*</span> Select Destination Region</td>	
            <td width="24%"> <form:select path="destRegion"  
				onchange="getCitiesByRegCode(this.value,2)" >
				
				<option value="0" selected="selected">Select</option>
				<form:options items="${regionList}" 
				itemValue="regionCode"
				itemLabel="regionName" />
				
				
				</form:select>
				</td>
          
            <td width="24%" class="lable"> <span class="mandatoryf">*</span> Select Destination City</td>
            <td width="24%"><form:select path="destCity"  
				 id="city2">
				
				<option value="0" selected="selected">Select</option>
				</form:select>
				
				</td>
          </tr>  
          <tr>
           <td width="11%" class="lable"><span class="mandatoryf">*</span> Start Date : </td>
           <td width="24%"><input name="startDate" type="text" class="txtbox width75" size="11" id="datepicker"/>
         
           <td width="11%" class="lable"><span class="mandatoryf">*</span> End Date : </td>
           <td width="24%"><input name="endDate" type="text" class="txtbox width75" size="11" id="datepicker2"/>
          
          </tr>
          <!-- Button -->
      <tr>
			   <td> <div class="button_containerform">
			    <input name="Submit" type="submit" class="btnintform" value='Submit' title="Submit" onclick="return validate();"/>
			    
				<c:choose> 
					<c:when test="${!empty dbfFileName}">
						<a href="<c:url value="downloads.do">
						    <c:param name="fileName" value="${dbfFileName}" /></c:url>">
						    <input name="Download" type="button" class="btnintform" value='Download Zip' title="Download"/>
						</a>
					</c:when>
				  </c:choose>
				    
	    </div> </td>
			    <!-- Button ends --> 
	  </tr>                
         </table>
      </div> <!-- formTable End --> --%>
      </html:form>
      

	<%-- <form method="post" id="consignmentReportForm"
		action="/opsman-report-old/BookingDetailsReport.do?submitName=getBookingDetailsReport">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<div class="mandatoryMsgf">
							<span class="mandatoryf">* Fields are Mandatory</span>
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
					 <tr>
	<td class="lable"><sup class="star">* region</sup></td>
	
	<td><c:choose>
			<c:when test="${regionTO.size() == 1}">

				<select name="to.regionTo" id="regionList"
					class="selectBox width130" disabled="disabled">
					<option
						value="${regionTO.get(0).getRegionId() }"
						selected="selected">${
						regionTO.get(0).getRegionName()}</option>
				</select>
			</c:when>
			<c:when test="${regionTO().size() == 0}">
				<select name="to.regionTo" id="regionList"
					class="selectBox width130" >
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.regionTo" id="regionList"
					class="selectBox width130"
					onchange="getStationsListForStatus('regionList');">
					<option value="Select">Select</option>
					<c:forEach var="regions" items="${regionTO)}">
						<option value="${regions.regionId }">${regions.regionName}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose></td>
		

	<td class="lable"><sup class="star">* station</sup></td>
    
    <td><c:choose>
			<c:when test="${cityTO.size() == 1}">
				<select name="to.station" id="stationList"
					class="selectBox width130" disabled="disabled">
					<option value="${cityTO.get(0).getCityId() }"
						selected="selected">
						${cityTO.get(0).getCityName()}</option>
				</select>
			</c:when>
			<c:when test="${cityTO.size() == 0}">
				<select name="to.station" id="stationList"
					class="selectBox width130" onchange="getBranchList('stationList')">
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="to.station" id="stationList"
					class="selectBox width130" onchange="getBranchList('stationList')">
					<option value="Select">Select</option>
					<c:forEach var="city" items="${cityTO}">
						<option value="${city.cityId }" >
							${city.cityName}</option>
					</c:forEach>

				</select>
			</c:otherwise>

		</c:choose></td>

	<td><c:choose>
			<c:when test="${CityDO}">
				<select name="do.station" id="originStation"
					class="selectBox width130" disabled="disabled" multiple>
					<option value="${CityDO.cityid }"
						selected="selected">
						${CityDO.CityName}</option>
				</select>
			</c:when>
			<c:when test="${CityDO}">
				<select name="do.station" id="originStation"
					class="selectBox width130" onchange="getOrgBranchList('originStation',this)" multiple>
					<option value="Select">Select</option>
				</select>

			</c:when>
			<c:otherwise>
				<select name="do.station" id="originStation"
					class="selectBox width130" onchange="getOrgBranchList('originStation',this)" multiple>
					<option value="Select">Select</option>
					<c:forEach var="city" items="${CityDO}">
						<option value="${city.cityId }" >
							${city.cityName}</option>
					</c:forEach>

				</select>
			</c:otherwise>

		</c:choose>
		
		</td>
		
</tr><tr>
								<td class="lable"><sup class="star">* fromDate</sup> </td>
								<td><input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
								<td class="lable"><sup class="star">* toDate</sup></td>
								<td><input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
								<td colspan="2"><br /> <br /></td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					onclick="showBASalesReport();" styleId="Submit">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="window.location.reload();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		</div>
	</form> --%>
	</div></div>
	</div>
</body>
<!-- </head> -->
</html>