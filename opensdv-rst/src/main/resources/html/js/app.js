var app = angular.module('opensdv.app', ['ngRoute','ngResource','mgcrea.ngStrap', 'mgcrea.ngStrap.timepicker','opensdv.services','opensdv.controllers','opensdv.directives']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/listvente', {templateUrl: 'view/listvente.html', controller: 'ListVenteController'});
    $routeProvider.when('/addvente',      {templateUrl: 'view/addvente.html',     controller: 'AddVenteController'});
    $routeProvider.when('/vendors',      {templateUrl: 'view/listvendor.html',     controller: 'ListVendorController'});
    $routeProvider.when('/vendor/add',      {templateUrl: 'view/addvendor.html',     controller: 'AddVendorController'});
    $routeProvider.when('/vente/:venteid/client/add',      {templateUrl: 'view/addclient.html',     controller: 'AddClientController'});
    $routeProvider.when('/vente/:venteid/client/:id/edit',      {templateUrl: 'view/addclient.html',     controller: 'EditClientController'});
    $routeProvider.when('/vendor/:id',      {templateUrl: 'view/vendor.html',     controller: 'VendorController'});
    $routeProvider.when('/vendor/:id/edit',      {templateUrl: 'view/addvendor.html',     controller: 'EditVendorController'});
    $routeProvider.when('/vente/:id/edit', {templateUrl: 'view/addvente.html', controller: 'EditVenteController'});
    $routeProvider.when('/vente/:id', {templateUrl: 'view/vente.html', controller: 'VenteController'});
    $routeProvider.when('/vente/:id/:tabid', {templateUrl: 'view/vente.html', controller: 'VenteController'});
    $routeProvider.when('/article/add/forvendor/:vendorId', {templateUrl: 'view/addarticle.html', controller: 'AddArticleController'});
    $routeProvider.when('/article/:id/edit', {templateUrl: 'view/addarticle.html', controller: 'EditArticleController'});
    $routeProvider.when('/vente/:venteid/article/:id/edit', {templateUrl: 'view/addarticle.html', controller: 'EditArticleController'});
    $routeProvider.otherwise({redirectTo: '/listvente'});
    $locationProvider.hashPrefix('!'); // Enable ajax crawling
});







