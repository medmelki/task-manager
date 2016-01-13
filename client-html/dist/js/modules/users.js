'use strict';

app.controller('UserController', ['$rootScope', '$scope', 'Upload', 'UserService', '$timeout', '$sce',
    function ($rootScope, $scope, Upload, UserService, $timeout, $sce) {

        var self = this;
        self.user = {username: '', password: '', address: '', email: ''};
        self.users = [];
        self.roles = [{"name": "ROLE_USER", "description": null}, {"name": "ROLE_ADMIN", "description": null},
            {"name": "ROLE_SUPERADMIN", "description": null}];
        $rootScope.updateMode = 0;

        var appURL = "http://localhost:8080/";

        $scope.$on('login', function (event, data) {

            self.user.username = data;

            /*console.log("test");
             self.getPicture(data);*/

        });

        self.getPicture = function (username) {
            UserService.getPicture(username)
                .then(
                    function (d) {
                        self.cstantPicture = URL.createObjectURL(d);
                    },
                    function (errResponse) {
                        console.error('Error while getting Picture');
                    }
                );
        };

        self.getPicture(self.user.username);


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

        self.findUserByUsername = function () {
            UserService.findUserByUsername()
                .then(
                    function (d) {
                        self.users = d;
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
            self.reset();
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
            $scope.documents = [];
            $scope.pictures = [];
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

        $scope.$watch('documents', function () {
            $scope.uploadDocument($scope.documents);
        });
        $scope.$watch('document', function () {
            if ($scope.document != null) {
                $scope.documents = [$scope.document];
            }
        });
        $scope.log = '';

        $scope.uploadDocument = function (documents) {
            if (documents && documents.length) {
                for (var i = 0; i < documents.length; i++) {
                    var document = documents[i];
                    if (!document.$error) {
                        Upload.upload({
                            url: appURL + 'user/documents/upload/',
                            data: {
                                username: self.user.username,
                                document: document
                            },
                            withCredentials: true
                        }).progress(function (evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            $scope.log = 'progress: ' + progressPercentage + '% ' +
                                evt.config.data.document.name + '\n' + $scope.log;
                        }).success(function (data, status, headers, config) {
                            $timeout(function () {
                                $scope.log = 'document: ' + config.data.document.name + ', Response: ' + JSON.stringify(data) + '\n' + $scope.log;
                            });
                        });
                    }
                }
            }
        };

        $scope.$watch('pictures', function () {
            $scope.uploadPicture($scope.pictures);
        });
        $scope.$watch('picture', function () {
            if ($scope.picture != null) {
                $scope.pictures = [$scope.picture];
            }
        });

        $scope.uploadPicture = function (pictures) {
            if (pictures && pictures.length) {
                for (var i = 0; i < pictures.length; i++) {
                    var picture = pictures[i];
                    if (!picture.$error) {
                        Upload.upload({
                            url: appURL + 'user/pictures/upload/',
                            data: {
                                username: self.user.username,
                                picture: picture
                            },
                            withCredentials: true
                        }).progress(function (evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            $scope.log = 'progress: ' + progressPercentage + '% ' +
                                evt.config.data.picture.name + '\n' + $scope.log;
                        }).success(function (data, status, headers, config) {
                            $timeout(function () {
                                $scope.log = 'picture: ' + config.data.picture.name + ', Response: ' + JSON.stringify(data) + '\n' + $scope.log;
                            });
                        });
                    }
                }
            }
        };

    }])
;