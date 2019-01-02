
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
        var pageSize = "";
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

            var deviceNames = [{"id":"1","name":"SPD在线检测仪"},{"id":"2","name":"地阻在线检测管理模块"}]
            $scope.deviceNames = deviceNames;
        });

		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};


	});

    siteForBar();
    rtuForBar();
    deviceForBar();
    deviceForWeek();
    deviceForType();
    deviceForNum();
    deviceForMonth()
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

//站点统计图
function siteForBar() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('siteForBar'),'macarons');
    myChart.showLoading();

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '站点统计情况',
            //subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['站点正常','站点异常','站点离线']
        },
        series : [
            {
                name: '统计数量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'站点正常'},
                    {value:310, name:'站点异常'},
                    {value:234, name:'站点离线'}
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

//rtu统计图
function rtuForBar() {
    // 基于准备好的dom，初始化echarts实例
    $("#rtuForBar").width($("#siteStatus").width());
    var myChart = echarts.init(document.getElementById('rtuForBar'));

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: 'RTU统计情况',
            //subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['RTU正常','RTU异常','RTU离线']
        },
        series : [
            {
                name: '统计数量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:390, name:'RTU正常'},
                    {value:288, name:'RTU异常'},
                    {value:330, name:'RTU离线'}
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
}

//设备统计图
function deviceForBar() {
    // 基于准备好的dom，初始化echarts实例
    $("#deviceForBar").width($("#siteStatus").width());
    var myChart = echarts.init(document.getElementById('deviceForBar'),'macarons');

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '设备统计情况',
            //subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['设备正常','设备异常','设备离线']
        },
        series : [
            {
                name: '统计数量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:209, name:'设备正常'},
                    {value:220, name:'设备异常'},
                    {value:234, name:'设备离线'}
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
}

//设备最近一周每日统计
function deviceForWeek() {
    // 基于准备好的dom，初始化echarts实例
    console.log($("#card1").height());
    $("#deviceForWeek").height($("#card1").height()-48);
    var myChart = echarts.init(document.getElementById('deviceForWeek'),'macarons');
    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '设备最近一周每日统计',
            //subtext: '纯属虚构',
            x:'left'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['设备总数','设备离线数','设备异常数']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'设备总数',
                type:'bar',
                data:[320, 332, 301, 334, 390, 330, 320]
            },
            {
                name:'设备离线数',
                type:'bar',
                data:[120, 132, 101, 134, 90, 230, 210]
            },
            {
                name:'设备异常数',
                type:'bar',
                data:[220, 182, 191, 234, 290, 330, 310]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//设备分类统计图
function deviceForType() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('deviceForType'),'macarons');
    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '设备分类统计图',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:['SPD','雷电流','地阻','温湿度','静电','杂散电流','阴极保护','倾斜度']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'统计数量',
                type:'pie',
                radius : [30, 110],
                center : ['50%', '50%'],
                roseType : 'area',
                data:[
                    {value:10, name:'SPD'},
                    {value:5, name:'雷电流'},
                    {value:15, name:'地阻'},
                    {value:25, name:'温湿度'},
                    {value:20, name:'静电'},
                    {value:35, name:'杂散电流'},
                    {value:30, name:'阴极保护'},
                    {value:40, name:'倾斜度'}
                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//设备分类调用
var yearCount = 8;
var categoryCount = 34;

var xAxisData = ["北京", "广东", "上海", "天津", "重庆", "辽宁", "江苏", "湖北", "四川", "陕西", "河北", "山西", "河南", "吉林", "黑龙江", "内蒙古", "山东", "安徽", "浙江", "福建", "湖南", "广西", "江西", "贵州", "云南", "西藏", "海南", "甘肃", "宁夏", "青海", "新疆", "香港", "澳门", "台湾"];
var customData = [];
var legendData = ["","SPD","雷电流","地阻","温湿度","静电","杂散电流","阴极保护","倾斜度"];
var dataList = [];

//legendData.push('trend');
var encodeY = [];
for (var i = 0; i < yearCount; i++) {
    //legendData.push((2010 + i) + '');
    dataList.push([]);
    encodeY.push(1 + i);
}

for (var i = 0; i < categoryCount; i++) {
    var val = Math.random() * 1000;
    //xAxisData.push('category' + i);
    var customVal = [];
    customData.push(customVal);

    //var data = dataList[0];
    for (var j = 0; j < dataList.length; j++) {
        var value = j === 0
            ? echarts.number.round(val, 2)
            : echarts.number.round(Math.max(0, dataList[j - 1][i] + (Math.random() - 0.5) * 200), 2);
        dataList[j].push(value);
        customVal.push(value);
    }
}
function renderItem(params, api) {
    var xValue = api.value(0);
    var currentSeriesIndices = api.currentSeriesIndices();
    var barLayout = api.barLayout({
        barGap: '30%', barCategoryGap: '20%', count: currentSeriesIndices.length - 1
    });

    var points = [];
    for (var i = 0; i < currentSeriesIndices.length; i++) {
        var seriesIndex = currentSeriesIndices[i];
        if (seriesIndex !== params.seriesIndex) {
            var point = api.coord([xValue, api.value(seriesIndex)]);
            point[0] += barLayout[i - 1].offsetCenter;
            point[1] -= 20;
            points.push(point);
        }
    }
    var style = api.style({
        stroke: api.visual('color'),
        fill: null
    });

    return {
        type: 'polyline',
        shape: {
            points: points
        },
        style: style
    };
}
//设备数量统计图(根据省份)
function deviceForNum() {
    console.log(customData);
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('deviceForNum'),'macarons');
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: legendData
        },
        dataZoom: [{
            type: 'slider',
            start: 50,
            end: 70
        }, {
            type: 'inside',
            start: 50,
            end: 70
        }],
        xAxis: {
            data: xAxisData
        },
        yAxis: {},
        series: [{
            type: 'custom',
            name: 'SPD',
            /*renderItem: renderItem,
            itemStyle: {
                normal: {
                    borderWidth: 2
                }
            },*/
            encode: {
                x: 0,
                y: encodeY
            },
            data: customData,
            z: 100
        }].concat(echarts.util.map(dataList, function (data, index) {
            return {
                type: 'bar',
                animation: false,
                name: legendData[index + 1],
                itemStyle: {
                    normal: {
                        opacity: 0.5
                    }
                },
                data: data
            };
        }))
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//设备一月状态统计
function deviceForMonth() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('deviceForMonth'),'macarons');
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '设备最近一月状态变化统计'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['正常','异常','离线']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一','周二','周三','周四','周五','周六','周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:'正常',
                type:'line',
                stack: '总量',
                data:[120, 132, 101, 134, 90, 230, 210]
            },
            {
                name:'异常',
                type:'line',
                stack: '总量',
                data:[220, 182, 191, 234, 290, 330, 310]
            },
            {
                name:'离线',
                type:'line',
                stack: '总量',
                data:[150, 232, 201, 154, 190, 330, 410]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}