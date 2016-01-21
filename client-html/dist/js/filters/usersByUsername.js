
var app = angular.module('usersByUsername', []);


app.filter('usersByUsername', function () {

    return function (users, username) {

        var out = [];



        if (null != username) {
            angular.forEach(users, function (user) {

                if (user.username.indexOf(username) > -1) {
                    out.push(user);
                }

            });
        }
        else {
            return users;
        }

        return out;
    }

});