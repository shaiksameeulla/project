
// Start of Changes by <31913> on 01/04/2013
//variables
/** refer drsCommon.js */
formId='listDrsForm';
tableId="listGrid";
/**
 * Allows you to tag each parameter supported by a function.
 */
$(document).ready( function () {
				var oTable = $('#'+tableId).dataTable( {
					"sScrollY": "170",
					"sScrollX": "104%",
					"sScrollXInner":"102%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
				load();
			} );


function load(){
	var empDom= getDomElementById("drsPartyId");
	setFocusOnDom(empDom);
}
/**
 * search
 * @returns {Boolean}
 */
function search(){
	var empDom= getDomElementById("drsPartyId");
	if(isNull(empDom.value)){
		alert("Please select Field-Staff");
		setFocusOnDom(empDom);
		return false;
	}
	var url="./listDrs.do?submitName=getAllDrsByOfficeAndEmployee";
	globalFormSubmit(url,formId);
}
