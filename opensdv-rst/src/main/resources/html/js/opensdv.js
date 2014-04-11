var app = angular.module('opensdv.app', ['ngRoute','ngResource']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.when('/addvente',      {templateUrl: 'view/addvente.html',     controller: 'AddController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
    $locationProvider.hashPrefix('!'); // Enable ajax crawling
});

app.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);

app.controller('ListVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.ventes = Vente.query();
}]);

app.controller('AddController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.vente = new Vente();
    $scope.saveVente= function () {
        Vente.save($scope.vente, function () {
            $location.path('/listvente');
        });
    };
}]);