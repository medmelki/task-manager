

(function () {

    var app = angular.module('users', []);

    app.controller('userController', function ($scope, $http) {

        var serverURL = "http://localhost:8080";

        var users = this;

        /*$scope.$on('login', function (event, data) {

            if (data === "Failure") {
                console.log("\nfailed to get Users");
            }

            if (data === "Success") {
                getUsersList();
            }
        });*/

        getUsersList();

        function getUsersList() {
            $http({
                method: 'GET',
                url: serverURL + "/users",
                dataType: 'json',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                },
                withCredentials: 'true'
            }).success(function (response) {
                users.list = response;
                console.log("GET USERS call result : " + users.list);
            }).error(function (data, status, headers, config) {
                console.log("Data : " + data + "status : " + status + "headers : " + headers + "config : " + config);
            });
        }


    });
})();

