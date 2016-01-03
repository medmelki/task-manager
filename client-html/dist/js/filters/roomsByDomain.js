
var app = angular.module('roomsByDomain', []);


app.filter('roomsByDomain', function () {

    return function (rooms, domain) {

        var out = [];

        if (null != domain) {
            angular.forEach(rooms, function (room) {

                if (room.domainName == domain) {
                    out.push(room);
                }

            });
        }

        return out;
    }

});