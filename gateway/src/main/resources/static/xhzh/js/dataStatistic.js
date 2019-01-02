
if (!("xh" in window)) {
	window.xh = {};
};

toastr.options = {
    "debug" : false,
    "newestOnTop" : false,
    "positionClass" : "toast-top-center",
    "closeButton" : true,
    /* 动态效果 */
    "toastClass" : "animated fadeInRight",
    "showDuration" : "300",
    "hideDuration" : "1000",
    /* 消失时间 */
    "timeOut" : "1000",
    "extendedTimeOut" : "1000",
    "showMethod" : "fadeIn",
    "hideMethod" : "fadeOut",
    "progressBar" : true,
};

var frist = 0;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
	var app = angular.module("app", []);

    app.filter('upp', function() { //可以注入依赖
        return function(text) {
            if(text=="" || text==null)
                return "";
            else
                return parseFloat(text);
        };
    });

    app.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }]);
	app.controller("xhcontroller", function($scope,$http,$location) {
		$scope.businessMenu=true; //菜单变色

        $http.get("../../total/selectDeviceNum?site_id="+$location.search().id).
        success(function(response){
            $scope.siteInfo = response.siteInfo;
            $scope.deviceOffCount = response.deviceOffCount;
            $scope.deviceWarningCount = response.deviceWarningCount;
            $scope.rtuNum = response.rtuNum;
            $scope.spdNum = response.spdNum;
            $scope.etcrNum = response.etcrNum;
            $scope.lightningNum = response.lightningNum;
            $scope.staticNum = response.staticNum;
            $scope.rswsNum = response.rswsNum;
            $scope.svtNum = response.svtNum;
            $scope.hcNum = response.hcNum;
            $scope.strayNum = response.strayNum;
            $scope.catNum = response.catNum;
            statusForDevice();
        });

		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};


	});

    statusForWeek();

};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

//设备状态图
function statusForDevice() {
    var $scope = angular.element(appElement).scope();

    // 基于准备好的dom，初始化echarts实例 macarons
    var myChart = echarts.init(document.getElementById('statusForDevice'));
    myChart.showLoading();

    var deviceNum = $scope.spdNum+$scope.etcrNum+$scope.lightningNum+$scope.staticNum+$scope.rswsNum+$scope.svtNum+$scope.hcNum+$scope.strayNum+$scope.catNum;
    // 指定图表的配置项和数据
    var option = {
        color:['#00CD66', '#FFD700','#DCDCDC'],
        title: {
            text: '设备统计数：'+deviceNum,
            left: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['正常','异常','离线']
        },
        series : [
            {
                type: 'pie',
                radius : '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:[
                    {value:deviceNum-$scope.deviceWarningCount-$scope.deviceOffCount, name: '正常'},
                    {value:$scope.deviceWarningCount, name: '异常'},
                    {value:$scope.deviceOffCount, name: '离线'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    myChart.hideLoading();
}

//一周内设备状态变化
function statusForWeek() {
    // 基于准备好的dom，初始化echarts实例 macarons
    var myChart = echarts.init(document.getElementById('statusForWeek'));
    myChart.showLoading();

    var d = new Date();
    var xAxisData = [];
    for (var i = 0; i < 7; i++) {
        xAxisData.push(d.getMonth()+1+"-"+(d.getDate()-i));
    }

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '近期设备状态变化'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['正常','异常','离线']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: xAxisData
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} °C'
            }
        },
        series: [
            {
                name:'正常',
                type:'line',
                data:[11, 11, 15, 13, 12, 13, 10],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'异常',
                type:'line',
                data:[6, 3, 7, 10, 8, 7, 5],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }
            },
            {
                name:'离线',
                type:'line',
                data:[1, -2, 2, 5, 3, 2, 0],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    myChart.hideLoading();
}