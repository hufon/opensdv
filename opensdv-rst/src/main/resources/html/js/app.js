var app = angular.module('opensdv.app', ['ngRoute','ngResource','ui.bootstrap.datetimepicker','opensdv.services','opensdv.controllers']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.when('/addvente',      {templateUrl: 'view/addvente.html',     controller: 'AddController'});
    $routeProvider.when('/:id/edit', {templateUrl: 'view/addvente.html', controller: 'EditController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
    $locationProvider.hashPrefix('!'); // Enable ajax crawling
});







