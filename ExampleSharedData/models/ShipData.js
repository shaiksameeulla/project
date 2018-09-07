// Model: ShipData - shared by ShipFrom and ShipTo

app.factory('ShipData', function()
{
	var ShipData =
	{
		ShipFrom: {
			// fields
			city: "",
			state: "",
			zipcode: "",
			
			// other
			"____________": "",
			visible: "",
			focus_city: "",
			focus_state: "",
			focus_zip: "",
		},
		ShipTo: {
			// fields
			city: "",
			state: "",
			zipcode: "",
			
			// other
			"____________": "",
			visible: "",
			focus_city: "",
			focus_state: "",
			focus_zip: "",
		},
		
	};
		
	return(ShipData);
});