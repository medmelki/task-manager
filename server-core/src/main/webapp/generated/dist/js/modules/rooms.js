
(function () {

    var app = angular.module('rooms', []);

    app.controller('roomController', function ($scope, $http) {

        var iobeyaserverURL = "http://localhost:8080/iobeya";
        var jsonURL = iobeyaserverURL + "/s/j";

        var rooms = this;

        $scope.$on('login', function (event, data) {

            if (data === "Failure") {
                console.log("\nfailed to get Rooms");
            }

            if (data === "Success") {
                getRoomsList();
            }
        });

        function getRoomsList() {
            $http({
                method: 'GET',
                url: jsonURL + "/rooms",
                dataType: 'json',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                },
                withCredentials: 'true'
            }).success(function (response) {
                rooms.list = response;
                //console.log("GET ROOMS call result : " + rooms.list);
            }).error(function (data, status, headers, config) {
                //console.log("Data : " + data + "status : " + status + "headers : " + headers + "config : " + config);
            });
        }

        $scope.$on('activeDomain', function (event, data) {

            rooms.activeDomain = data;
        });

        $scope.setActiveRoom = function (roomId) {

            if (null != roomId) {
                $scope.$broadcast('activeRoom', roomId);
            }
        }
    });
})();

