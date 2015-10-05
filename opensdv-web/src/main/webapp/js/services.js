var services = angular.module('opensdv.services', ['ngResource']);

services.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);


services.factory('Vendor', ['$resource', function ($resource) {
    return $resource(
        '../rest/vendors/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);

services.factory('Article', ['$resource', function ($resource) {
    return $resource(
        '../rest/articles/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);

services.factory('ArticleVente', ['$resource', function ($resource) {
    return $resource(
        '../rest/articlesventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);


services.factory('Client', ['$resource', function ($resource) {
    return $resource(
        '../rest/clients/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);