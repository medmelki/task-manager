'use strict';

app.factory('UserService', ['$http', '$q', function ($http, $q) {

    var appURL = "http://localhost:8080/";

    return {

        findAllUsers: function () {
            return $http.get(appURL + 'user/', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching users');
                        return $q.reject(errResponse);
                    }
                );
        },

        findUserByUsername: function () {
            return $http.get(appURL + 'user/{username}', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching users');
                        return $q.reject(errResponse);
                    }
                );
        },

        findAllRoles: function () {
            return $http.get(appURL + 'role/', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching roles');
                        return $q.reject(errResponse);
                    }
                );
        },

        createUser: function (user) {
            return $http.post(appURL + 'user/', user, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating user');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateUser: function (user) {
            return $http.put(appURL + 'user/', user, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating user');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteUser: function (username) {
            return $http.delete(appURL + 'user/' + username, {
                    withCredentials: true,
                    headers: {'Content-type': 'application/json', 'X-Requested-With': 'XMLHttpRequest'}
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting user');
                        return $q.reject(errResponse);
                    }
                );
        },

        getPicture: function (username) {
            return $http.get(appURL + 'user/picture/' + username, {
                    withCredentials: true,
                    responseType : 'blob'
                })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while retrieving picture');
                        return $q.reject(errResponse);
                    }
                );
        },

        getCurrentUser: function () {
            return $http.get(appURL + 'user/current', {withCredentials: true})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching current user');
                        return $q.reject(errResponse);
                    }
                );
        },

    };

}]);