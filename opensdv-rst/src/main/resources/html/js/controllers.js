var controllers = angular.module('opensdv.controllers', []);

controllers.controller('ListVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.ventes = Vente.query();
    $scope.deleteVente = function (vente) {
        vente.$delete(function () {
            $location.path("/list");
        });
    };
}]);

controllers.controller('HeadController', ['$scope', function($scope) {
	$scope.template = 'view/head.html';
}]);

controllers.controller('AddController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
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

controllers.controller('EditController', ['$scope', 'Vente', '$routeParams', '$location', function ($scope, Vente, $routeParams, $location) {
    $scope.vente = Vente.get({id: $routeParams.id});
    $scope.saveVente = function () {
        Vente.update($scope.vente, function () {
            $location.path('/list');
        });
    };
}]);