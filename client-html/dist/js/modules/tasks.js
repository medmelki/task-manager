'use strict';

app.controller('TaskController', ['$rootScope', '$scope', 'Upload', 'TaskService', 'CommonService',
    function ($rootScope, $scope, Upload, TaskService, CommonService) {

        var self = this;
        self.task = {};
        self.node = {};
        self.tasks = [];
        self.task.nodes = new Array();
        self.status = ["Not assigned", "Assigned", "In Progress", "Completed", "Cancelled"];
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
                        console.error('Error while fetching Orders');
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
                        console.error('Error while fetching order');
                    }
                );
        };

        self.createTask = function (task) {
            TaskService.createTask(task)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while creating Order.');
                    }
                );
        };

        self.updateTask = function (task) {
            TaskService.updateTask(task)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while updating Order.');
                    }
                );
        };

        self.deleteTask = function (id) {
            TaskService.deleteTask(id)
                .then(
                    self.findAllTasks,
                    function (errResponse) {
                        console.error('Error while deleting Order.');
                    }
                );
        };

        self.findAllTasks();

        self.submit = function (task, isUpdateMode) {

            task.date = new Date(task.date).getTime();
            $scope.updateMode = isUpdateMode;
            var temp = task.user;
            task.user = {};
            task.user.username = temp.username;
            task.user.password = temp.password;
            task.user.email = temp.email;
            task.user.companyId = temp.companyId;
            task.user.address = temp.address;
            task.user.phone = temp.phone;
            task.user.pictures = temp.pictures;
            task.user.documents = temp.documents;
            task.user.tasks = temp.tasks;
            task.user.gps = temp.gps;
            task.user.roles = temp.roles;
            if ($scope.updateMode === 0) {
                if (self.node) {
                    self.addNode();
                }
                console.log('Saving New Order', task);
                self.createTask(task);
            } else {
                if (self.node) {
                    self.updateNode();
                    task.nodes = self.task.nodes;
                }
                self.updateTask(task);
                console.log('Order updated with id ', task.id);
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
            self.node = task.nodes[0];
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

        self.addNode = function () {

            // add current node to tasks' node
            if (!self.task.nodes) {
                self.task.nodes = new Array();
            }
            if (self.node.time) {
                self.node.time = new Date(self.node.time).getTime();
            }
            self.task.nodes.push(self.node);
            // reset current node
            self.node = {};
        };

        self.updateNode = function () {

            // add current node to tasks' node
            if (!self.task.nodes) {
                self.task.nodes = new Array();
            }
            if (self.node.time) {
                self.node.time = new Date(self.node.time).getTime();
            }
            self.task.nodes[0] = self.node;
        };

    }])
;