'use strict';

app.factory('TaskService', ['$window', '$http', '$q', '$location',  function ($window, $http, $q, $location) {

    var appURL = $location.absUrl() + '/';

    return {

        findAllTasks: function () {
            return $http.get(appURL + 'task/')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching tasks');
                        return $q.reject(errResponse);
                    }
                );
        },

        findTaskById: function (id) {
            return $http.get(appURL + 'task/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching task');
                        return $q.reject(errResponse);
                    }
                );
        },

        createTask: function (task) {
            return $http.post(appURL + 'task/', task, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating task');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateTask: function (task) {
            return $http.put(appURL + 'task/', task, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating task');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteTask: function (id) {
            return $http.delete(appURL + 'task/' + id, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting task');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);