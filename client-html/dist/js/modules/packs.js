'use strict';

app.controller('PackController', ['$rootScope', '$scope', 'Upload', 'PackService', 'CommonService',
    function ($rootScope, $scope, Upload, PackService, CommonService) {

        var self = this;
        self.pack = {};
        self.packs = [];
        $scope.updateMode = 0;
        self.pack1 = null;

        self.appURL = CommonService.appURL + '/';

        self.findAllPacks = function () {
            PackService.findAllPacks()
                .then(
                    function (d) {
                        self.packs = d;
                        $scope.setActiveRole(0);
                    },
                    function (errResponse) {
                        console.error('Error while fetching Packs');
                    }
                );
        };

        self.findPackById = function (id) {
            PackService.findPackByPackname(id)
                .then(
                    function (d) {
                        self.packs = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching pack');
                    }
                );
        };

        self.createPack = function (pack) {
            PackService.createPack(pack)
                .then(
                    self.findAllPacks,
                    function (errResponse) {
                        console.error('Error while creating Pack.');
                    }
                );
        };

        self.updatePack = function (pack) {
            PackService.updatePack(pack)
                .then(
                    self.findAllPacks,
                    function (errResponse) {
                        console.error('Error while updating Pack.');
                    }
                );
        };

        self.deletePack = function (id) {
            PackService.deletePack(id)
                .then(
                    self.findAllPacks,
                    function (errResponse) {
                        console.error('Error while deleting Pack.');
                    }
                );
        };

        self.findAllPacks();

        self.submit = function (pack, isUpdateMode) {
            $scope.updateMode = isUpdateMode;
            console.log($scope.updateMode);
            if ($scope.updateMode === 0) {
                console.log('Saving New Pack', pack);
                self.createPack(pack);
            } else {
                self.updatePack(pack);
                console.log('Pack updated with id ', pack.id);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.packs.length; i++) {
                if (self.packs[i].id === id) {
                    self.pack = angular.copy(self.packs[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.pack.id === id) {//clean form if the pack to be deleted is shown there.
                self.reset();
            }
            self.deletePack(id);
        };

        self.reset = function () {
            self.pack = {};
        };

        self.populateModal = function (pack) {
            $scope.updateMode = 1;
            self.reset();
            self.pack = pack;
        };

        self.setAddMode = function () {
            $scope.updateMode = 0;
        };
        
    }])
;