var app = angular.module('opensdv.app', ['ngRoute','ngResource','ui.bootstrap.datetimepicker']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.when('/addvente',      {templateUrl: 'view/addvente.html',     controller: 'AddController'});
    $routeProvider.when('/:id/edit', {templateUrl: 'view/addvente.html', controller: 'EditController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
    $locationProvider.hashPrefix('!'); // Enable ajax crawling
});

app.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);

app.controller('ListVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.ventes = Vente.query();
    $scope.deleteVente = function (vente) {
        vente.$delete(function () {
            $location.path("/list");
        });
    };
}]);

app.controller('AddController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.vente = new Vente();
	$scope.vente.name = "VAE_";
	$scope.vente.date = new Date();
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
	$scope.format = $scope.formats[0];
	  $scope.opencalendar = function($event) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope.opened = true;
		  };
	
    $scope.saveVente= function () {
        Vente.save($scope.vente, function () {
            $location.path('/listvente');
        });
    };
}]);

app.controller('EditController', ['$scope', 'Vente', '$routeParams', '$location', function ($scope, Vente, $routeParams, $location) {
    $scope.vente = Vente.get({id: $routeParams.id});
    $scope.saveVente = function () {
        Vente.update($scope.vente, function () {
            $location.path('/list');
        });
    };
}]);