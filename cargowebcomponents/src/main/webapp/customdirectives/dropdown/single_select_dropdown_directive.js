angular.module('swacargo-coreapp')
    .directive('dropDown', [function($scope) {
      return {
        restrict: 'E',
        scope: {
          id: '=defaultid',
          values:'=data'
         },
       /* template: '<div class="ddSingle"><select>'
        +'<option ng-repeat="value in values | orderBy: \'value\'" ng-selected="value.id == id" '+
        'ng-value="value.id">{{value.name}}'+
        '</option></select> </div>'*/
        templateUrl: 'views/coredropdown.html'
      };
    }]);

