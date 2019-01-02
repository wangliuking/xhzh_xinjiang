
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
	
	var pageSize = $("#page-limit").val();
    app.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }]);
	app.controller("xhcontroller", function($scope,$http,$location) {
        pageSize = "";
		$scope.count = "15";//每页数据显示默认值
		$scope.businessMenu=true; //菜单变色

        $http.get("../../connect/selectAllSite").
        success(function(response){
            var data = response.items;
            var siteNames = [];
            for(var i=0;i<data.length;i++){
                siteNames.push({"site_id":data[i].site_id,"site_name":data[i].site_name});
            }
            $scope.siteNames = siteNames;

            //添加页面的site-id
            $scope.siteNamesAdd = siteNames;
            //修改页面的site-id
            $scope.siteNamesEdit = siteNames;
        });

        $http.get("../../connect/selectAllRTU").
        success(function(response){
            var data = response.items;
            var rtuNames = [];
            for(var i=0;i<data.length;i++){
                rtuNames.push({"rtu_id":data[i].rtu_id,"rtu_name":data[i].rtu_id});
            }
            $scope.rtuNames = rtuNames;

            //添加页面的rtu-id
            $scope.rtuNamesAdd = rtuNames;
            //修改页面的rtu-id
            $scope.rtuNamesEdit = rtuNames;

            var description = [{"id":"1","name":"设备异常"},{"id":"2","name":"设备离线"},{"id":"3","name":"RTU离线"}]
            $scope.description = description;

            var allStatus = [{"id":"1","name":"处理状态"},{"id":"2","name":"已处理"},{"id":"3","name":"未处理"}]
            $scope.allStatus = allStatus;
        });

		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};


	});

    alarmForMonth();
    alarmForWeek();
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

//最近一月告警统计
function alarmForMonth() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('alarmForMonth'),'macarons');
    myChart.showLoading();

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '最近一月情况统计'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'right',
            data:['异常次数','异常设备数','离线次数','离线设备数','异常RTU数','异常站点数','离线RTU数','离线站点数']
        },
        series: [
            {
                name:'统计结果',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '26',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:335, name:'异常次数'},
                    {value:310, name:'异常设备数'},
                    {value:234, name:'离线次数'},
                    {value:335, name:'离线设备数'},
                    {value:310, name:'异常RTU数'},
                    {value:234, name:'异常站点数'},
                    {value:135, name:'离线RTU数'},
                    {value:1548, name:'离线站点数'}
                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    myChart.hideLoading();
}

//最近一周告警统计所需数据
var xAxisData = [];
var data1 = [];
var data2 = [];
var data3 = [];
var d = new Date();
for (var i = 0; i < 7; i++) {
    xAxisData.push(d.getMonth()+1+"-"+(d.getDate()-i));
    data1.push((i+5) * 15);
    data2.push((i+5) * 21);
    data3.push((i+5) * 27);
}

//最近一周告警统计
function alarmForWeek() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('alarmForWeek'),'macarons');
    myChart.showLoading();

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '近一周报警统计'
        },
        legend: {
            data: ['设备异常', '设备离线', 'RTU离线'],
            align: 'left'
        },
        toolbox: {
            // y: 'bottom',
            feature: {
                magicType: {
                    type: ['stack', 'tiled']
                },
                dataView: {},
                saveAsImage: {
                    pixelRatio: 2
                }
            }
        },
        tooltip: {},
        xAxis: {
            data: xAxisData,
            silent: false,
            splitLine: {
                show: false
            }
        },
        yAxis: {
        },
        series: [{
            name: '设备异常',
            type: 'bar',
            data: data1,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: '设备离线',
            type: 'bar',
            data: data2,
            animationDelay: function (idx) {
                return idx * 10 + 100;
            }
        }, {
            name: 'RTU离线',
            type: 'bar',
            data: data3,
            animationDelay: function (idx) {
                return idx * 10 + 100;
            }
        }],
        animationEasing: 'elasticOut',
        animationDelayUpdate: function (idx) {
            return idx * 5;
        }
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    myChart.hideLoading();
}