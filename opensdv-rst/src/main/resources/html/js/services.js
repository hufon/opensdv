var services = angular.module('opensdv.services', ['ngResource']);

services.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);


services.factory('Vendor', ['$resource', function ($resource) {
    return $resource(
        '../rest/vendors/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);