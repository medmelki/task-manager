'use strict';

app.controller('UserController', ['$rootScope', '$scope', 'Upload', 'UserService', '$timeout', '$location',
    function ($rootScope, $scope, Upload, UserService, $timeout, $location) {

        var self = this;
        self.user = {username: '', password: '', address: '', email: ''};
        self.currentUser = {username: '', password: '', address: '', email: ''};
        self.users = [];
        self.roles = [{"name": "ROLE_USER", "description": null}, {"name": "ROLE_ADMIN", "description": null},
            {"name": "ROLE_SUPERADMIN", "description": null}];
        $rootScope.updateMode = 0;

        self.isSuperAdmin = false;


        self.appURL = $location.absUrl() + '/';

        self.getPicture = function (username) {
            UserService.getPicture(username)
                .then(
                    function (d) {
                        self.cstantPicture = URL.createObjectURL(d);
                    },
                    function (errResponse) {
                        console.error('Error while getting Picture');
                        self.cstantPicture = 'dist/img/avatar5.png';
                    }
                );
        };

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

        self.findCurrentUser = function () {
            UserService.getCurrentUser()
                .then(
                    function (d) {
                        self.currentUser = d;
                        self.getPicture(self.currentUser.username);
                        self.isItSuperAdmin(self.currentUser.roles);
                    },
                    function (errResponse) {
                        console.error('Error while fetching current user');
                    }
                );
        };

        self.findCurrentUser();

        self.findUserByUsername = function (username) {
            UserService.findUserByUsername(username)
                .then(
                    function (d) {
                        self.users = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching user');
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
                    self.isItSuperAdmin(self.currentUser.roles),
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

        self.submit = function (user, isUpdateMode) {
            console.log(isUpdateMode);
            $rootScope.updateMode = isUpdateMode;
            var temp = user;
            user = {};
            user.username = temp.username;
            user.password = temp.password;
            user.email = temp.email;
            user.companyId = temp.companyId;
            user.address = temp.address;
            user.phone = temp.phone;
            user.pictures = temp.pictures;
            user.documents = temp.documents;
            user.tasks = temp.tasks;
            user.gps = temp.gps;
            user.roles = temp.roles;
            if ($rootScope.updateMode === 0) {
                console.log('Saving New User', user);
                self.createUser(user);
            } else {
                self.updateUser(user);
                console.log('User updated with username ', user.username);
            }
            if ($scope.file != null) {
                $scope.uploadProfilePicture($scope.file);
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

        self.deletePicture = function (username, pictureId) {
            UserService.deletePicture(username, pictureId)
                .then(
                    self.findCurrentUser,
                    function (errResponse) {
                        console.error('Error while deleting Picure.');
                    }
                );
        };

        self.reset = function () {
            self.user = {username: null, password: '', address: '', email: ''};
            $scope.documents = [];
            $scope.pictures = [];
        };

        self.populateModal = function (user) {
            $rootScope.updateMode = 1;
            self.reset();
            self.user = user;
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
            $scope.uploadPictures($scope.pictures);
        });
        $scope.$watch('picture', function () {
            if ($scope.picture != null) {
                $scope.pictures = [$scope.picture];
            }
        });

        $scope.uploadPictures = function (pictures) {
            if (pictures && pictures.length) {
                for (var i = 0; i < pictures.length; i++) {
                    var picture = pictures[i];
                    if (!picture.$error) {
                        Upload.upload({
                            url: self.appURL + 'user/pictures/upload/',
                            data: {
                                username: self.user.username ? self.user.username : self.currentUser.username,
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
                                self.findCurrentUser();
                            });
                        });
                    }
                }
            }
        };

        $scope.uploadProfilePicture = function (picture) {
            if (picture) {
                Upload.upload({
                    url: self.appURL + 'user/pic/upload/',
                    data: {
                        username: self.user.username ? self.user.username : self.currentUser.username,
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
                        self.findCurrentUser();
                    });
                });
            }
        };

        self.isItSuperAdmin = function (roles) {
            for (var i = 0; i < roles.length; i++) {
                if (roles[i].name === "ROLE_SUPERADMIN") {
                    self.isSuperAdmin = true;
                    break;
                }
            }
        };

    }])
;