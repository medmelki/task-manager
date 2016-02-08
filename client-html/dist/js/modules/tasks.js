'use strict';

app.controller('TaskController', ['$rootScope', '$scope', 'Upload', 'TaskService', 'CommonService',
    function ($rootScope, $scope, Upload, TaskService, CommonService) {

        var self = this;
        self.task = {};
        self.tasks = [];
        $scope.updateMode = 0;

        self.appURL = CommonService.appURL + '/';

        self.findAllTasks = function () {
            TaskService.findAllTasks()
                .then(
                    function (d) {
                        self.tasks = d;
                        $scope.setActiveUser(0);
                        $scope.setActivePackage(0);
                        $scope.setActiveNode(0);
                    },
                    function (errResponse) {
                        console.error('Error while fetching Tasks');
                    }
                );
        };

        self.findTaskById = function (id) {
            TaskService.findTaskByTaskname(id)
                .then(
                    function (d) {
                        self.tasks = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching task');
                    }
                );
        };

        self.createTask = function (task) {
            TaskService.createTask(task)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while creating Task.');
                    }
                );
        };

        self.updateTask = function (task) {
            TaskService.updateTask(task)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while updating Task.');
                    }
                );
        };

        self.deleteTask = function (id) {
            TaskService.deleteTask(id)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while deleting Task.');
                    }
                );
        };

        self.findAllTasks();

        self.submit = function (task, isUpdateMode) {
            $scope.updateMode = isUpdateMode;
            console.log($scope.updateMode);
            var new_users = [];
            for (var i = 0; i < task.users.length; i++) {
                var temp = task.users[i];
                task.users[i] = {};
                task.users[i].username = temp.username;
                task.users[i].password = temp.password;
                task.users[i].email = temp.email;
                task.users[i].companyId = temp.companyId;
                task.users[i].address = temp.address;
                task.users[i].phone = temp.phone;
                task.users[i].pictures = temp.pictures;
                task.users[i].documents = temp.documents;
                task.users[i].tasks = temp.tasks;
                task.users[i].gps = temp.gps;
                task.users[i].roles = temp.roles;
                new_users.push(task.users[i]);
            }
            task.users = new_users;
            if ($scope.updateMode === 0) {
                console.log('Saving New Task', task);
                self.createTask(task);
            } else {
                self.updateTask(task);
                console.log('Task updated with id ', task.id);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.tasks.length; i++) {
                if (self.tasks[i].id === id) {
                    self.task = angular.copy(self.tasks[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.task.id === id) {//clean form if the task to be deleted is shown there.
                self.reset();
            }
            self.deleteTask(id);
        };

        self.reset = function () {
            self.task = {};
        };

        self.populateModal = function (task) {
            $scope.updateMode = 1;
            self.reset();
            self.task = task;
        };

        self.setAddMode = function () {
            $scope.updateMode = 0;
        };

        $scope.setActiveUser = function (x) {
            $scope.activeUser = x;
        };

        $scope.setActivePackage = function (x) {
            $scope.activePackage = x;
        };

        $scope.setActiveNode = function (x) {
            $scope.activeNode = x;
        };

    }])
;