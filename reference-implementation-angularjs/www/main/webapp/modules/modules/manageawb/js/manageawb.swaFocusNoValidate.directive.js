/***
 * swaFocusNoValidate -  directive Hide validation errors on focus in the form fields
 * This custom directive will be called in many places of the application.
 **/
(function(app){

	app.directive('swaFocusNoValidate', swaFocusNoValidate);

	swaFocusNoValidate.$inject = ['$timeout'];

	function swaFocusNoValidate ($timeout) {
		return {
			restrict : 'A',
			require : 'ngModel',
			link : function postLink(scope, element, attrs, ctrl) {

				var WAIT_TILL_UPDATE = 10000;
				var DEBUG = true;

				if (DEBUG) console.log("swaFocusNoValidate linked");

				ctrl.$focused = false;
				ctrl.$blured = true;

				element.bind('focus', function (evt) {

					scope.$evalAsync(function () {
						ctrl.$focused = true;
						ctrl.$blured = false;
					});

				}).bind('blur', function (evt) {

					$timeout(function () {

						scope.$evalAsync(function () {
							ctrl.$focused = false;
							ctrl.$blured = true;
						}, WAIT_TILL_UPDATE);//@CodeReviewFix:03-2016  Hard coded value has been moved to constant.

					});

				});
			}
		}
	}

})(angular.module("manageawb.directives"));