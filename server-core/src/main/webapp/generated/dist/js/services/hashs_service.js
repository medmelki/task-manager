'use strict';

app.factory('HashService', ['$window', '$http', '$q','$location', function ($window, $http, $q, $location) {

    var appURL = $location.absUrl() + '/';

    return {

        getHash: function () {
            return $http.get(appURL + 'hash')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching hash');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);