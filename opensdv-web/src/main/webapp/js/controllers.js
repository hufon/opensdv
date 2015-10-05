var controllers = angular.module('opensdv.controllers', ['mgcrea.ngStrap.alert']);

controllers.controller('ListVenteController', ['$scope', 'Vente', '$location','$alert', function ($scope, Vente, $location,$alert) {
	$scope.ventes = Vente.query();
    $scope.deleteVente = function (vente) {
        vente.$delete(function () {
            var myAlert = $alert({title: 'Holy guacamole!', content: 'Best check yo self, you\'re not looking too good.', placement: 'top', type: 'info', show: true});
            $location.path("/list");
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

controllers.controller('VenteController', ['$scope', 'Vente','Article','Client','$routeParams', '$location','$log', function ($scope, Vente, Article, Client,$routeParams, $location,$log) {
	$scope.vente = Vente.get({id : $routeParams.id});
	$scope.articles = Article.query({venteId : $routeParams.id});
    if ($routeParams.tabid)
        $scope.currentTab = $routeParams.tabid;
        else
        $scope.currentTab = "objects";
    $scope.currentTabTpl="view/vente-"+$scope.currentTab+".html";
   	$scope.saveVenteOrder=function (aArticle) {
	    Article.update(aArticle, function()
	    {
	        $log.info(aArticle.id+" updated...")
	    });
	}


}]);

controllers.controller('ListClientController', ['$scope', 'Vente','Article','Client','$routeParams', '$location','$log', function ($scope, Vente, Article, Client,$routeParams, $location,$log) {

    $scope.clients = Client.query({venteId : $routeParams.id});
    $scope.deleteClient = function (client) {
                client.$delete(function () {
                    $scope.clients = Client.query({venteId : $routeParams.id});
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
        if (newValue != "") {
          $scope.vendors = Vendor.query({search : newValue});
        }
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
	$scope.vente = Vente.get({id : 'new'})
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
	$scope.format = $scope.formats[0];
	
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
    $scope.vendor = Vendor.get({id : 'new'})
    $scope.saveVendor= function () {
        Vendor.save($scope.vendor, function () {
            $location.path('/vendors');
        });
    };
}]);

controllers.controller('AddClientController', ['$scope', 'Client','Vente', '$location', '$routeParams', function ($scope, Client, Vente, $location, $routeParams) {
	$scope.client = Client.get({id : 'new',venteId : $routeParams.venteid})
    $scope.vente = Vente.get({id : $routeParams.venteid})
    $scope.saveClient= function () {
        $scope.client.vente = $scope.vente;
        Client.save($scope.client, function () {
            $location.path('/vente/'+$scope.vente.id);
        });
    };
}]);

controllers.controller('EditClientController', ['$scope', 'Client','Vente', '$location', '$routeParams', function ($scope, Client, Vente, $location, $routeParams) {
	$scope.client = Client.get({id : $routeParams.id})
    $scope.vente = Vente.get({id : $routeParams.venteid})
    $scope.saveClient= function () {
        Client.update($scope.client, function () {
            $location.path('/vente/'+$scope.vente.id+'/clients');
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




controllers.controller('AddArticleController', ['$scope', 'Article','Vendor','Vente','$routeParams', '$location', function ($scope, Article, Vendor, Vente,$routeParams, $location) {
    $scope.new = true;
	$scope.article = new Article();
	$scope.article.vendor = Vendor.get({id: $routeParams.vendorId});
	$scope.ventes = Vente.query();
    $scope.saveArticle = function () {
        //$scope.article.vente = $scope.selectedvente;
        Article.save($scope.article, function () {
            $location.path('/vendor/'+$routeParams.vendorId);
        });
    };

    $scope.returnTo = function() {
            var returnPath;
            if ($routeParams.venteid) {
                returnPath =  '/vente/'+$routeParams.venteid;
            } else
            {
                returnPath = '/vendor/'+$scope.article.vendor.id
            }
            $location.path(returnPath);
        }
}]);

controllers.controller('AddArticleVenteController', ['$scope', 'Article','ArticleVente','Vente','$routeParams', '$location', function ($scope, Article, ArticleVente, Vente, $routeParams, $location) {
    $scope.new = true;
	$scope.articlevente = new ArticleVente();
	$scope.articlevente.article = Article.get({id: $routeParams.articleId});
	$scope.ventes = Vente.query();
    $scope.saveArticleVente = function () {
        $scope.articlevente.vente = $scope.selectedvente;
        ArticleVente.save($scope.articlevente, function () {
            $location.path('/article/'+$routeParams.articleId);
        });
    };

    $scope.returnTo = function() {
            var returnPath;
            returnPath =  '/article/'+$routeParams.articleId;
            $location.path(returnPath);
        }
}]);

controllers.controller('EditArticleVenteController', ['$scope', 'Article','Vendor','ArticleVente','$routeParams', '$location', function ($scope, Article, Vendor, ArticleVente,$routeParams, $location) {
    $scope.new = false;

    $scope.articlevente = ArticleVente.get({id: $routeParams.id});
	/*$scope.ventes = Vente.query(function() {
	    $scope.article = Article.get({id: $routeParams.id}, function(a) {
           for (var i=0;i<$scope.ventes.length;i++)
           {
                if ($scope.ventes[i].id == a.vente.id)
                 {
                    $scope.selectedvente=$scope.ventes[i];
                 }
           }
	    });
	});*/

    $scope.returnTo = function() {
        var returnPath;
        returnPath =  '/article/'+$scope.articlevente.article.id;
        $location.path(returnPath);
    }

    $scope.saveArticleVente = function () {
        ArticleVente.update($scope.articlesventes, function () {
           $scope.returnTo();
        });
    };
}]);

controllers.controller('EditArticleController', ['$scope', 'Article','Vendor','ArticleVente','$routeParams', '$location', function ($scope, Article, Vendor, ArticleVente,$routeParams, $location) {
    $scope.new = false;

    $scope.article = Article.get({id: $routeParams.id});
    $scope.articlesventes = ArticleVente.query({articleId : $routeParams.id})


    $scope.returnTo = function() {
        var returnPath;
        if ($routeParams.venteid) {
            returnPath =  '/vente/'+$routeParams.venteid;
        } else
        {
            returnPath = '/vendor/'+$scope.article.vendor.id;
        }
        $location.path(returnPath);
    }

        $scope.saveArticle = function () {
            $scope.article.vente = $scope.selectedvente;
            Article.update($scope.article, function () {
               $scope.returnTo();
            });
        };


}]);