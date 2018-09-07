// Form Controller: ShipFrom
//===============================================
app.controller('ShipFromController', ['$scope', 'ShipData',
	function ($scope, ShipData) {
		
		console.log("ShipFromController");
		
		$scope.ShipFrom=ShipData.ShipFrom;
		
		// changing the data model - not scope
		// ShipTo and it's scope may not exist yet
		$scope.OnSameState = function() {
			ShipData.ShipTo.state=ShipData.ShipFrom.state;
			++ShipData.ShipTo.focus_state;

		}
								  
		$scope.OnToggle = function() {
			++ShipData.ShipTo.focus_city;
			ShipData.ShipTo.focus_state=0;
			ShipData.ShipTo.focus_zip=0;

			ShipData.ShipTo.visible^=1;
		}
								  
		$scope.OnNext = function() {
			++ShipData.ShipTo.focus_city;
			ShipData.ShipTo.focus_state=0;
			ShipData.ShipTo.focus_zip=0;
			
			ShipData.ShipTo.visible=1;
		}
								  
}]);
