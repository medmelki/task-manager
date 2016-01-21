'use strict';

app.factory('PackService', ['$window', '$http', '$q', function ($window, $http, $q) {

    var appURL = "http://localhost:8080/";

    return {

        findAllPacks: function () {
            return $http.get(appURL + 'pack/', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching packs');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        findPackById: function (id) {
            return $http.get(appURL + 'pack/' + id, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching pack');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        createPack: function (pack) {
            return $http.post(appURL + 'pack/', pack, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating pack');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        updatePack: function (pack) {
            return $http.put(appURL + 'pack/', pack, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating pack');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        deletePack: function (id) {
            return $http.delete(appURL + 'pack/' + id, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting pack');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);