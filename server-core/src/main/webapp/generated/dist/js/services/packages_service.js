'use strict';

app.factory('PackService', ['$window', '$http', '$q', '$location', function ($window, $http, $q, $location) {

    var appURL = $location.absUrl() + '/';

    return {

        findAllPacks: function () {
            return $http.get(appURL + 'pack/')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching packs');
                        return $q.reject(errResponse);
                    }
                );
        },

        findPackById: function (id) {
            return $http.get(appURL + 'pack/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching pack');
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
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);