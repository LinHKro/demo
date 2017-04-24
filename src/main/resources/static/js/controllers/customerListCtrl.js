'use strict';

/* Controllers */

// Form controller
app.controller('CustomerListCtrl', ['$scope', '$http','$modal', function ($scope, $http,$modal) {
    $scope.page = {
        size: 5,
        index: 1
    };


    $http.get('/api/customer/listAll').then(function (response) {
        $scope.customerList = response.data;
        angular.forEach($scope.customerList, function (data) {
            if (data.signRecord == null) {
                data.signRecord = {
                    status: '未签到'
                };
            } else {
                if (data.signRecord.status == 0) {
                    data.signRecord.status = '未签到';
                }
                if (data.signRecord.status == 1) {
                    data.signRecord.status = '已签到';
                }
            }
        })
    }, function (response) {
    });

    $scope.customerDetail = function (id) {
        var modalInstance = $modal.open({
            url: '/modal',
            templateUrl: 'tpl/customer_modal.html',
            size:100/*,*/
            /*resolve: {
                deps: ['uiLoad',
                    function( uiLoad ){
                        return uiLoad.load( ['js/app/contact/contact.js'] );
                    }]
            }*/
        });
        $http.post('/api/customer/getById', id).then(function (response) {
            $scope.customerInfo = response.data;
        }, function (response) {
        });
    };

    $scope.signCardId = {};

    $scope.customerSign = function (id, customer) {
        if (id == '') {
            alert('请输入会员番号！');
            return;
        }
        if (undefined == customer || undefined == customer.signRecord) {
            angular.forEach($scope.customerList, function (data) {
                if (data.cardId == id) {
                    customer = data;
                }
            })
        }
        if (customer.signRecord.status == '已签到') {
            alert('请不要重复签到！');
        } else {
            $http.post('/api/sign/sign', id).then(function (response) {
                if (response.data.status != "success") {
                    alert(response.data.msg);
                } else {
                    alert(response.data.msg);
                }
            }, function (response) {
            });
            angular.forEach($scope.customerList, function (data) {
                if (data.cardId == id) {
                    data.signRecord.status = '已签到';
                    return;
                }
            });
        }
        $scope.signCardId = {};
    };

    $scope.customerDel = function (id) {
        if (confirm('确认删除该会员吗？')) {
            $http.post('/api/customer/delete', id).then(function (response) {
                if (response.data.status != "success") {
                    alert(response.data.msg);
                } else {
                    alert(response.data.msg);
                }
                angular.forEach($scope.customerList, function (data, index, array) {
                    if (data.id == id) {
                        array.splice(index, 1);
                        return;
                    }
                })
            }, function (response) {
            });
        }
    };

    $scope.searchStr = '';
    $scope.orderByProperty = 'id';
    $scope.order = '-';
    $scope.sta = true;

    $scope.changeStatus = function (property) {
        $scope.orderByProperty = property;
        $scope.sta = !$scope.sta;
        if ($scope.order == '-') {
            $scope.order = '';
        } else {
            $scope.order = '-';
        }
    }
}])
;

app.filter('size', function () {
    return function (items) {
        if (!items)
            return 0;

        return items.length || 0
    }
});
app.filter('paging', function () {
    return function (items, index, pageSize) {
        if (!items)
            return [];

        var offset = (index - 1) * pageSize;
        return items.slice(offset, offset + pageSize);
    }
});