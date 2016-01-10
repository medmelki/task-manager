'use strict';

app.controller('UserController', ['$rootScope', '$scope', 'UserService', function ($rootScope, $scope, UserService) {
    var self = this;
    self.user = {username: "", password: '', address: '', email: ''};
    self.users = [];
    self.roles = [{"name": "ROLE_USER", "description": null}, {"name": "ROLE_ADMIN", "description": null},
        {"name": "ROLE_SUPERADMIN", "description": null}];
    $rootScope.updateMode = 0;

    self.findAllUsers = function () {
        UserService.findAllUsers()
            .then(
                function (d) {
                    self.users = d;
                    $scope.setActiveRole(0);
                },
                function (errResponse) {
                    console.error('Error while fetching Users');
                }
            );
    };

    self.createUser = function (user) {
        UserService.createUser(user)
            .then(
                self.findAllUsers,
                function (errResponse) {
                    console.error('Error while creating User.');
                }
            );
    };

    self.updateUser = function (user) {
        UserService.updateUser(user)
            .then(
                self.findAllUsers,
                function (errResponse) {
                    console.error('Error while updating User.');
                }
            );
    };

    self.deleteUser = function (username) {
        UserService.deleteUser(username)
            .then(
                self.findAllUsers,
                function (errResponse) {
                    console.error('Error while deleting User.');
                }
            );
    };

    self.findAllUsers();

    self.submit = function () {
        if ($rootScope.updateMode === 0) {
            console.log('Saving New User', self.user);
            self.createUser(self.user);
        } else {
            self.updateUser(self.user);
            console.log('User updated with username ', self.user.username);
        }
    };

    self.edit = function (username) {
        console.log('username to be edited', username);
        for (var i = 0; i < self.users.length; i++) {
            if (self.users[i].username === username) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }
    };

    self.remove = function (username) {
        console.log('username to be deleted', username);
        if (self.user.username === username) {//clean form if the user to be deleted is shown there.
            self.reset();
        }
        self.deleteUser(username);
    };


    self.reset = function () {
        self.user = {username: null, password: '', address: '', email: ''};
        //$scope.addForm.$setPristine(); //reset Form
    };

    self.populateModal = function (user) {
        $rootScope.updateMode = 1;
        self.reset();
        self.user.username = user.username;
        self.user.password = user.password;
        self.user.companyId = user.companyId;
        self.user.phone = user.phone;
        self.user.address = user.address;
        self.user.email = user.email;
        self.user.roles = user.roles;
    };

    self.setAddMode = function () {
        $rootScope.updateMode = 0;
    };

    $scope.setActiveRole = function (x) {
        $scope.activeRole = x;
    };


}]);