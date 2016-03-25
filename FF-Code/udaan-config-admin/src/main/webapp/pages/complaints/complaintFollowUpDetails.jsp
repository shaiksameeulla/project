<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


       <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Paper Work</title>
        <link href="css/demo_table.css" rel="stylesheet" type="text/css" />
        <link href="css/top-menu.css" rel="stylesheet" type="text/css" />
        <link href="css/global.css" rel="stylesheet" type="text/css" />
        <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
        <link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
         <!-- tab css--><!--<link rel="stylesheet" href="jquery-tab-ui.css" />--><!--tab css ends-->
        <script type="text/javascript" src="js/jquerydropmenu.js"></script>
        <!-- DataGrids -->
        <script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script><!-- popup begins/-->
        <script language="JavaScript" src="js/jquery/jquery.blockUI.js"	type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
        <script type="text/javascript" charset="utf-8" src="js/complaints/serviceRequestFollowup.js"></script>
        <script type="text/javascript">
        $('.dataTables_scroll').width("100%");
		</script>
 </head>
        <body>

 <div class="formTable">
          <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="followupDetails" width="100%">
                  <thead>
            <tr>
            		  <th width="2%" align="center"><bean:message key="common.label.serialNo"/></th>
                      <th width="3%" align="center"><bean:message key="label.followup.followup.details"/></th>
                      <th width="2%" align="center"><bean:message key="label.followup.cn.number"/></th>
                      <th width="3%"><bean:message key="label.followup.followup.date"/></th>
                      <th width="3%"><bean:message key="label.followup.followup.time"/></th>
                      <th width="4%"><bean:message key="label.followup.mode.of.interaction"/></th>
                      <th width="3%"><bean:message key="label.followup.interaction.with"/></th>
                      <th width="3%"><bean:message key="label.followup.name"/></th>
                      <th width="3%"><bean:message key="label.followup.details.email"/></th>
                      <th width="4%"><bean:message key="label.followup.followup.Note"/></th>
                      <th width="4%"><bean:message key="label.followup.employeee.details"/></th>
                    </tr>
            
          </thead>
          <tbody>
          </tbody>
             
                </table>
            <!-- Button -->
            <div class="button_containerform">   
			<html:button property="Back" styleClass="btnintform" styleId="backBtn" onclick = "closePopup();"><bean:message key="button.back"/></html:button>       
            </div><!-- Button ends --> 
      </div>
  		</div>
 </body>