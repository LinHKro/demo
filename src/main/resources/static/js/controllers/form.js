'use strict';

/* Controllers */

// Form controller
app.controller('CustomerCtl', ['$scope', '$http', function ($scope, $http) {
    $scope.customer = {};

    $scope.save = function () {
        $scope.customer.gender = $scope.customer.gender.value;
        $scope.customer.flag = $scope.customer.flag.value;
        $scope.customer.photoUrl = $scope.$$childHead.myCroppedImage;
        if ($scope.customer.photoUrl === "") {

        }
        $http.post('/api/customer/save', $scope.customer)
            .then(function (response) {
                if (response.data.status != "success") {
                    alert(response.data.msg);
                } else {
                    alert(response.data.msg);
                    $scope.clearCustomer();
                }
            }, function (response) {
            });
    };

    $scope.clearCustomer = function () {
        $scope.customer = {};
        $scope.$$childHead.myCroppedImage ="";
    }

}])
;