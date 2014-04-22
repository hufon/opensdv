angular.module('opensdv.services', ['ngResource'])
	.factory('Vente', ['$resource', function ($resource) {
    return $resource(
        '../rest/ventes/:id', { 'id': '@id'}, {'update': {method: 'PUT'} });
}]);