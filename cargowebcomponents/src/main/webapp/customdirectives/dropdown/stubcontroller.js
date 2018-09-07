angular.module('swacargo-coreapp',[]);

 angular.module('swacargo-coreapp')
    .controller('stubController', function($scope){
      $scope.locVal = 'MX';
      $scope.countries = [{
            id: "IN",
            name: "India"
          }, {
            id: "US",
            name: "UnitedStates"
          }, {
            id: "AU",
            name: "Australia"
          },{
            id: "MX",
            name: "Mexico"
          }];
    });


