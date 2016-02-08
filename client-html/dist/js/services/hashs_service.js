'use strict';

app.factory('HashService', ['$window', '$http', '$q', function ($window, $http, $q) {

    var appURL = "http://52.33.209.86:8080/task-manager-server-1.0/";

    return {

        getHash: function () {
            return $http.get(appURL + 'hash', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching hash');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);