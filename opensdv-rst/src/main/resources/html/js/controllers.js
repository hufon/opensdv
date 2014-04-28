var controllers = angular.module('opensdv.controllers', []);

controllers.controller('ListVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
	$scope.ventes = Vente.query();
    $scope.deleteVente = function (vente) {
        vente.$delete(function () {
            $location.path("/list");
        });
    };
}]);

controllers.controller('ListVendorController', ['$scope', 'Vendor', '$location', function ($scope, Vendor, $location) {
    $scope.vendors = Vendor.query();
}]);

controllers.controller('HeadController', ['$scope', '$location', function($scope, $location) {
	$scope.template = 'view/head.html';
	$scope.isCurrentPath = function (path) {
          return $location.path() == path;
        };
}]);

controllers.controller('AddVenteController', ['$scope', 'Vente', '$location', function ($scope, Vente, $location) {
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


controllers.controller('VendorController', ['$scope', 'Vendor','Article', '$routeParams', '$location', function ($scope, Vendor, Article, $routeParams, $location) {
      $scope.vendor = Vendor.get({id: $routeParams.id});
      $scope.articles = Article.query({vendorId : $routeParams.id});
}]);

controllers.controller('AddVendorController', ['$scope', 'Vendor', '$location', function ($scope, Vendor, $location) {
    $scope.new = true;
	$scope.vendor = new Vendor();
    $scope.saveVendor= function () {
        Vendor.save($scope.vendor, function () {
            $location.path('/vendors');
        });
    };
}]);

controllers.controller('EditVendorController', ['$scope', 'Vendor', '$routeParams', '$location', function ($scope, Vendor, $routeParams, $location) {
    $scope.vendor = Vendor.get({id: $routeParams.id});
    $scope.saveVendor = function () {
        Vendor.update($scope.vendor, function () {
            $location.path('/vendors');
        });
    };
}]);

controllers.controller('EditVenteController', ['$scope', 'Vente', '$routeParams', '$location', function ($scope, Vente, $routeParams, $location) {
    $scope.vente = Vente.get({id: $routeParams.id});
    $scope.saveVente = function () {
        Vente.update($scope.vente, function () {
            $location.path('/list');
        });
    };
}]);