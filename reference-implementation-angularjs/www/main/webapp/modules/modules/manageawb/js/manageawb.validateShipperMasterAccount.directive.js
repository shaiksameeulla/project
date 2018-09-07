angular.module("manageawb.directives")
.directive('swaValidateShipperMasterAccount', function ($timeout, $q) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function postLink(scope, element, attrs, ctrl) {

			// console.log("swaValidateShipperMasterAccount linked", scope, element, attrs, ctrl);

			// MOCK SERVICE
			var ValidationService = {};
			ValidationService.validateShipperId = function(value, validity){
				var p = $q(function (resolve, reject) {
					$timeout(function(){ 
						if (validity) resolve(true); 
						else reject(false);
					}, 200);
				});

				// console.log("Promise? ", p);
				return p;
			}
			// MOCK SERVICE

			// validate the shipper id
			ctrl.$asyncValidators.shippedIdExists = function(modelValue, viewValue) {
				var value = modelValue || viewValue;

				// Lookup user by username
				// return $http.get('/api/users/' + value)
				return ValidationService.validateShipperId(value, true)
				.then(function resolved() {
				   //username exists, this means validation fails
				   return true;
				}, function rejected() {
				   //username does not exist, therefore this validation passes
				   return $q.reject('Shipper ID Does Not Exist');
				});
			}

			// console.log("Validators: ", ctrl.$asyncValidators);
				
		}
	
	}
});