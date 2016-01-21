'use strict';

app.controller('HashController', ['$rootScope', '$scope', 'Upload', 'HashService', '$timeout', '$sce',
    function ($rootScope, $scope, Upload, HashService, $timeout, $sce) {

        var self = this;
        var appURL = "http://localhost:8080/generated/";
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