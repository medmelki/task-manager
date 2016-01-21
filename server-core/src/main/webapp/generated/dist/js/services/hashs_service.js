'use strict';

app.factory('HashService', ['$window', '$http', '$q', function ($window, $http, $q) {

    var appURL = "http://localhost:8080/";

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