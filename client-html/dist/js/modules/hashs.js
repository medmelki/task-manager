'use strict';

app.controller('HashController', ['$rootScope', '$scope', 'Upload', 'HashService', 'CommonService',
    function ($rootScope, $scope, Upload, HashService, CommonService) {

        var self = this;
        var appURL = CommonService.appURL + '/generated/';
        self.hash = '';

        self.getHash = function () {
            HashService.getHash()
                .then(
                    function (d) {
                        self.hash = d;
                        self.hash.hach = appURL + self.hash.hach;
                    },
                    function (errResponse) {
                        console.error('Error while fetching hash');
                    }
                );
        };

    }])
;