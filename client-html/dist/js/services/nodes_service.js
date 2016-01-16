'use strict';

app.factory('NodeService', ['$http', '$q', function ($http, $q) {

    var appURL = "http://localhost:8080/";

    return {

        findAllNodes: function () {
            return $http.get(appURL + 'node/', {withCredentials: true})
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
            return $http.get(appURL + 'node/' + id, {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching node');
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
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);