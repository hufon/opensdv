var app = angular.module('opensdv.app', ['ngRoute','ngResource','ui.bootstrap.datetimepicker','opensdv.services','opensdv.controllers']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.when('/addvente',      {templateUrl: 'view/addvente.html',     controller: 'AddVenteController'});
    $routeProvider.when('/vendors',      {templateUrl: 'view/listvendor.html',     controller: 'ListVendorController'});
    $routeProvider.when('/vendor/:id/edit',      {templateUrl: 'view/addvendor.html',     controller: 'EditVendorController'});
    $routeProvider.when('/vente/:id/edit', {templateUrl: 'view/addvente.html', controller: 'EditVenteController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
    $locationProvider.hashPrefix('!'); // Enable ajax crawling
});







