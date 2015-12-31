
var app = angular.module('myApp', []);

app.controller('productsCtrl', function ($scope, $http) {
    $http.get("http://localhost:8099/product")
        .then(function (response) {
            $scope.products = response.data;
        });
});
