'use strict';

app.factory('NodeService', ['$window', '$http', '$q', '$location', function ($window, $http, $q, $location) {

    var appURL = $location.absUrl() + '/';

    return {

        findAllNodes: function () {
            return $http.get(appURL + 'node/')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching nodes');
                        return $q.reject(errResponse);
                    }
                );
        },

        findNodeById: function (id) {
            return $http.get(appURL + 'node/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching node');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        createNode: function (node) {
            return $http.post(appURL + 'node/', node, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating node');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        updateNode: function (node) {
            return $http.put(appURL + 'node/', node, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating node');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteNode: function (id) {
            return $http.delete(appURL + 'node/' + id, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting node');
                        if (errResponse.status == 403) {
                            $window.location = "login.html";
                        }
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);