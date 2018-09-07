// Form Controller: ShipTo
//===============================================
app.controller('ShipToController', ['$scope', 'ShipData',
	function ($scope, ShipData) {
		
		console.log("ShipToController");
		
		$scope.ShipTo=ShipData.ShipTo;
		
		// changing the data model - not scope
		$scope.OnSameCity = function() {
			++ShipData.ShipTo.focus_city;
			ShipData.ShipFrom.city=ShipData.ShipTo.city;
		}
		
		$scope.OnNext = function() {
			++ShipData.ShipTo.focus_state;;
		}
		
		
								  
}]);
