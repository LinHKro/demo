'use strict';

/* Controllers */

// Form controller
app.controller('signHistoryCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.page = {
        size: 5,
        index: 1
    };

    $scope.customerId = '';
    $scope.signDate = '';

    $scope.findHistoryById = function (customerId) {
        $http.post('/api/sign/historyListById', customerId).then(function (response) {
            $scope.signList = response.data;
            if ($scope.signList.length === 0) {
                alert("没有签到记录！");
            }
        }, function (response) {
        });
    };

    $scope.findHistoryByDate = function (signDate) {
        signDate = formatDate(signDate, 'yyyy-MM-dd');
        $http.post('/api/sign/historyListByDate', signDate).then(function (response) {
            $scope.signList = response.data;
            if ($scope.signList.length === 0) {
                alert("没有签到记录！");
            }
        }, function (response) {
        });
    };
    $scope.today = function () {
        $scope.dt = formatDate(new Date(), 'yyyy-MM-dd');
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function () {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1,
        class: 'datepicker'
    };
    //格式化日期,
    function formatDate(date, format) {
        var paddNum = function (num) {
            num += "";
            return num.replace(/^(\d)$/, "0$1");
        };
        //指定格式字符
        var cfg = {
            yyyy: date.getFullYear() //年 : 4位
            , yy: date.getFullYear().toString().substring(2)//年 : 2位
            , M: date.getMonth() + 1  //月 : 如果1位的时候不补0
            , MM: paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
            , d: date.getDate()   //日 : 如果1位的时候不补0
            , dd: paddNum(date.getDate())//日 : 如果1位的时候补0
            , HH: paddNum(date.getHours())//时
            , mm: paddNum(date.getMinutes()) //分
            , ss: paddNum(date.getSeconds()) //秒
        };
        format || (format = "yyyy-MM-dd hh:mm:ss");
        return format.replace(/([a-z])(\1)*/ig, function (m) {
            return cfg[m];
        });
    }

    $scope.signCardId = {};
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

    $scope.format = 'yyyy-MM-dd';

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