var app = angular.module('app', []);

app.controller('MainController', ['$scope', '$rootScope', '$location', '$window', 'ShipData',
	function ($scope, $rootScope, $location, $window, ShipData) {
								  
	$window.scope=$scope;
		
	$scope.ShipData=ShipData;
	ShipData.ShipTo.visible=0;
		
	//===============================================
	// Router
	$rootScope.$on('$locationChangeSuccess',
		function(event) {
			var url = $location.url();
			$scope.Page = url.substring(1);
			if ($scope.Page=="")
				$scope.Page="Ship";

			$window.scrollTo(0, 0);		// hide mobile url bar
		}
	);

	//===============================================
	$scope.PageChange=function(name) {
		$location.url(name);
	}
		
	//===============================================
	$scope.HrefChange=function(href) { // external page
		$window.location.href=href;
	}
		
	//===============================================

}]);

