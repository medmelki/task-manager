
var app = angular.module('objectsById', []);


app.filter('objectsById', function () {

    return function (objects, id) {

        var out = [];



        if (id) {
            angular.forEach(objects, function (object) {
                console.log(id);
                if (object.id == id) {
                    out.push(object);
                }

            });
        }
        else {
            return objects;
        }

        return out;
    }

});