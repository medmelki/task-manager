'use strict';

app.factory('HashService', ['$window', '$http', '$q', 'CommonService', function ($window, $http, $q, CommonService) {

    var appURL = CommonService.appURL + '/';

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