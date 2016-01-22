'use strict';

app.controller('NodeController', ['$rootScope', '$scope', 'Upload', 'NodeService','$location',
    function ($rootScope, $scope, Upload, NodeService, $location) {

        var self = this;
        self.node = {};
        self.nodes = [];
        $scope.updateMode = 0;

        self.appURL = $location.absUrl() + '/';

        self.findAllNodes = function () {
            NodeService.findAllNodes()
                .then(
                    function (d) {
                        self.nodes = d;
                        $scope.setActiveRole(0);
                    },
                    function (errResponse) {
                        console.error('Error while fetching Nodes');
                    }
                );
        };

        self.findNodeById = function (id) {
            NodeService.findNodeByNodename(id)
                .then(
                    function (d) {
                        self.nodes = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching node');
                    }
                );
        };

        self.createNode = function (node) {
            NodeService.createNode(node)
                .then(
                    self.findAllNodes,
                    function (errResponse) {
                        console.error('Error while creating Node.');
                    }
                );
        };

        self.updateNode = function (node) {
            NodeService.updateNode(node)
                .then(
                    self.findAllNodes,
                    function (errResponse) {
                        console.error('Error while updating Node.');
                    }
                );
        };

        self.deleteNode = function (id) {
            NodeService.deleteNode(id)
                .then(
                    self.findAllNodes,
                    function (errResponse) {
                        console.error('Error while deleting Node.');
                    }
                );
        };

        self.findAllNodes();

        self.submit = function (node, isUpdateMode) {
            node.time = new Date(node.time).getTime();
            $scope.updateMode = isUpdateMode;
            console.log($scope.updateMode);
            if ($scope.updateMode === 0) {
                console.log('Saving New Node', node);
                self.createNode(node);
            } else {
                self.updateNode(node);
                console.log('Node updated with id ', node.id);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.nodes.length; i++) {
                if (self.nodes[i].id === id) {
                    self.node = angular.copy(self.nodes[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.node.id === id) {//clean form if the node to be deleted is shown there.
                self.reset();
            }
            self.deleteNode(id);
        };

        self.reset = function () {
            self.node = {};
        };

        self.populateModal = function (node) {
            $scope.updateMode = 1;
            self.reset();
            self.node = node;
        };

        self.setAddMode = function () {
            $scope.updateMode = 0;
        };
        
    }])
;