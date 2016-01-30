(function () {

    var app = angular.module('login', []);

    app.controller('loginController', function ($rootScope, $scope, $http, $q, $window, $location) {

        var serverURL = "http://52.33.209.86:8080/task-manager-server-1.0";

        $scope.login = function () {

            var deferred = $q.defer();

            var username = $scope.username;
            var password = $scope.password;
            var rememberMe = $scope.rememberMe;

            if (null != username && null != password) {

                var loginURL = serverURL + '/j_spring_security_check?XMLHttpRequest=true&j_username='
                    + username + '&j_password=' + password + '&_spring_security_remember_me=' + rememberMe;

                $http({
                    method: 'POST',
                    url: loginURL,
                    dataType: 'jsonp',
                    headers: {
                        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                    },
                    withCredentials: 'true'
                }).success(function (response) {
                    $scope.result = JSON.stringify(response);
                    $scope.loginStatus = 1;
                    deferred.resolve('request successful');
                }).error(function () {
                    deferred.reject('ERROR');
                    console.log("error");
                });
            }

            deferred.promise.then(
                function () {
                    $rootScope.$broadcast('login', username);
                    $window.location.href = 'tasks.html';
                },

                function () {
                    $scope.$broadcast('login', 'Failure');
                }
            );
        };

    });
})();
