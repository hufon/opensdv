var app = angular.module('opensdv.app', ['ngRoute','ngResource']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
});

app.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);

app.controller('ListVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.ventes = Vente.query();
}]);