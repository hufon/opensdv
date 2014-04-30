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
    $scope.vendorfilter="";
    $scope.deleteVendor = function (vendor) {
            vendor.$delete(function () {
                $location.path("/vendors");
                $scope.vendors = Vendor.query();
            });
        };
    $scope.$watch('vendorfilter', function(newValue, oldValue)
    {
          $scope.vendors = Vendor.query({search : newValue});
    });

    $scope.search = function()
    {
            $scope.vendors = Vendor.query({search : $scope.vendorfilter});
    }
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


controllers.controller('AddArticleController', ['$scope', 'Article','Vendor','Vente','$routeParams', '$location', function ($scope, Article, Vendor, Vente,$routeParams, $location) {
    $scope.new = true;
	$scope.article = new Article();
	$scope.article.vendor = Vendor.get({id: $routeParams.vendorId});
	$scope.ventes = Vente.query();
    $scope.saveArticle = function () {
        $scope.article.vente = $scope.selectedvente;
        Article.save($scope.article, function () {
            $location.path('/vendor/'+$routeParams.vendorId);
        });
    };
}]);

controllers.controller('EditArticleController', ['$scope', 'Article','Vendor','Vente','$routeParams', '$location', function ($scope, Article, Vendor, Vente,$routeParams, $location) {
    $scope.new = false;

	$scope.ventes = Vente.query(function() {
	    $scope.article = Article.get({id: $routeParams.id}, function(a) {
           for (var i=0;i<$scope.ventes.length;i++)
           {
                if ($scope.ventes[i].id == a.vente.id)
                 {
                    $scope.selectedvente=$scope.ventes[i];
                 }
           }
	    });
	});
    $scope.saveArticle = function () {
        $scope.article.vente = $scope.selectedvente;
        Article.update($scope.article, function () {
            $location.path('/vendor/'+$scope.article.vendor.id);
        });
    };
}]);