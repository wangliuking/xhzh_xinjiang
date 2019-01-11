var outputPath = 'tiles/';    //地图瓦片所在的文件夹
var fromat = ".jpg";    //格式
var arr;
var map;
var initStatus = true;
var markers = [];
var count = 0;

if (!("xh" in window)) {
    window.xh = {};
};

var frist = 0;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
    var app = angular.module("app", []);
    app.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }]);
    app.controller("xhcontroller", function($scope,$http,$location) {

        //判断是否登录start
        /*$.ajax({
            type: 'GET',
            url: "../../connect/ensure",
            async: false,
            dataType: 'json',
            success: function(response){

            } ,
            error: function () {
                alert("登录已失效，请重新登录！");
                window.location.href = "../login.html";
                window.parent.location.href = "../login.html";
            }
        });*/
        //判断是否登录end

        $scope.industryData = ["医疗","气象","新能源","轨道交通","石油化工","国防军工","电力","通讯"];

        $scope.searchShow = function(){
            if($(".navform").css("display") == "none"){
                $(".navform").css({"display":"block"});
            }else{
                $(".navform").css({"display":"none"});
            }
        };

        $scope.showMenu = function(){
            if($(".info_right").css("display") == "none"){
                $(".info_right").css({"display":"block"});
                $(".info_right_temp").css({"display":"none"});
            }else{
                $(".info_right").css({"display":"none"});
                $(".info_right_temp").css({"display":"block"});
            }
        };

        initMap();
        /*setInterval(function () {
            initMap();
            count++;
        }, 60000);*/

        /* 显示添加框 */
        $scope.showMod = function() {
            /*$('#add input').val('');*/
            $('#add').modal('show');
        };

        /* 刷新数据 */
        $scope.refresh = function() {
            $scope.search(1);
        };

        //分页点击
        $scope.pageClick = function(page, totals, totalPages) {
            var site_name = $("#testSiteName").val();
            if(site_name == "全部名称"){
                site_name = null;
            }
            var site_industry = $("#testIndustrys").val();
            if(site_industry == "全部行业"){
                site_industry = null;
            }
            var site_province = $("#testProvince").val();
            if(site_province == "请选择省份"){
                site_province = null;
            }
            var site_city = $("#testCity").val();
            if(site_city == "请选择城市"){
                site_city = null;
            }
            var site_county = $("#testArea").val();
            if(site_county == "请选择区县" || site_county == "市辖区"){
                site_county = null;
            }
            var status = $("#testSiteStatus").val();
            if(status == "全部状态"){
                status = null;
            }else if(status == "正常"){
                status = 0;
            }else if(status == "异常"){
                status = 1;
            }else if(status == "断开"){
                status = 2;
            }

            var pageSize = $("#page-limit").val();
            var start = 1, limit = pageSize;
            page = parseInt(page);
            if (page <= 1) {
                start = 0;
            } else {
                start = (page - 1) * pageSize;
            }

            $http.get("../../connect/selectAllSite?start="+start+"&limit=" + limit+"&site_name="+site_name+"&site_industry="+site_industry+"&site_province="+site_province+"&site_city="+site_city+"&site_county="+site_county+"&status="+status).
            success(function(response){
                xh.maskHide();
                $scope.start = (page - 1) * pageSize + 1;
                $scope.lastIndex = page * pageSize;
                if (page == totalPages) {
                    if (totals > 0) {
                        $scope.lastIndex = totals;
                    } else {
                        $scope.start = 0;
                        $scope.lastIndex = 0;
                    }
                }
                $scope.data = response.items;
                $scope.totals = response.totals;
            });
        };
    });
};
//区域选择函数
function chooseArea(checkbox) {

    console.log(checkbox);
    if (checkbox.checked == true) {
        console.log("选中了！！！"+checkbox.value)
    } else {
        console.log("取消了！！！"+checkbox.value)
    }

}

// 刷新数据
xh.refresh = function() {
    var $scope = angular.element(appElement).scope();
    // 调用$scope中的方法
    $scope.refresh();
};

/* 数据分页 */
xh.pagging = function(currentPage, totals, $scope) {
    var pageSize = $("#page-limit").val();
    var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
        .ceil(parseInt(totals, 10) / pageSize);
    var start = (currentPage - 1) * pageSize + 1;
    var end = currentPage * pageSize;
    if (currentPage == totalPages) {
        if (totals > 0) {
            end = totals;
        } else {
            start = 0;
            end = 0;
        }
    }
    $scope.start = start;
    $scope.lastIndex = end;
    $scope.totals = totals;
    if (totals > 0) {
        $(".page-paging").html('<ul class="pagination"></ul>');
        $('.pagination').twbsPagination({
            totalPages : totalPages,
            visiblePages : 10,
            version : '1.1',
            startPage : currentPage,
            onPageClick : function(event, page) {
                if (frist == 1) {
                    $scope.pageClick(page, totals, totalPages);
                }
                frist = 1;

            }
        });
    }
};

function initMap() {
    var url = "../../connect/selectAllSite";
    $.ajax({
        type: 'GET',
        url: url,
        async: false,
        success: function (data) {
            console.log(data.items);
            arr = data.items;
            if (initStatus) {
                start();
                initStatus = false;
            }
            /*$.ajax({
                type: 'GET',
                url: "../../total/selectSiteAllInfo",
                async: false,
                dataType: 'json',
                success: function (data) {

                }
            });*/

        },
        dataType: 'json'
    });
}

function start() {
    map = new BMap.Map("container");
    var point = new BMap.Point(104.075493, 30.660545);  // 创建点坐标
    map.centerAndZoom(point, 6);                 // 初始化地图，设置中心点坐标和地图级别
    //添加地图类型控件
    map.addControl(new BMap.MapTypeControl({
        mapTypes: [
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
        ]
    }));
    map.addControl(new BMap.NavigationControl());//添加默认缩放平移控件
    map.addControl(new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT })); //向地图中添加比例尺控件
    //map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    //添加轮廓线start
    /*var areaUrl = "area.json";
    $.getJSON(areaUrl, function (data){
        var rs = data["博乐市"];
        map.clearOverlays();        //清除地图覆盖物
        var count = rs.length; //行政区域的点有多少个
        console.log(count);
        for(var i = 0; i < count; i++){
            var ply = new BMap.Polygon(rs[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
            map.addOverlay(ply);  //添加覆盖物
            map.setViewport(ply.getPath());    //调整视野
        }
    });*/
    //添加轮廓线end

    addMarker();


    //监听地图层级
    map.addEventListener("zoomend", function (e) {
        var ZoomNum = map.getZoom();
        console.log("当前地图层级：" + ZoomNum);
        /*var allOverlay = map.getOverlays();
        for(var i = 0;i<allOverlay.length;i++) {
            //删除指定经度的点
            map.removeOverlay(allOverlay[i]);
        }
        addMarker();*/
    });
}

function addMarker() {  // 创建图标对象
    for (var i = 0; i < arr.length; i++) {
        $.ajax({
            type: 'GET',
            url: "../../total/selectSiteAllStatus?site_id=" + arr[i].site_id,
            async: false,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                var industry = arr[i].site_industry;
                var rtuWarningNum = data.rtuWarningNum;
                var rtuOffNum = data.rtuOffNum;
                var rtuNum = data.rtuNum;
                var iconImg;
                if ("医疗" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/6-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/6-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/6-2.png";
                    } else {
                        iconImg = "../iconfont/6-3.png";
                    }
                } else if ("气象" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/7-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/7-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/7-2.png";
                    } else {
                        iconImg = "../iconfont/7-3.png";
                    }
                } else if ("新能源" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/3-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/3-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/3-2.png";
                    } else {
                        iconImg = "../iconfont/3-3.png";
                    }
                } else if ("轨道交通" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/4-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/4-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/4-2.png";
                    } else {
                        iconImg = "../iconfont/4-3.png";
                    }
                } else if ("石油化工" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/5-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/5-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/5-2.png";
                    } else {
                        iconImg = "../iconfont/5-3.png";
                    }
                } else if ("国防军工" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/8-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/8-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/8-2.png";
                    } else {
                        iconImg = "../iconfont/8-3.png";
                    }
                } else if ("电力" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/1-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/1-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/1-2.png";
                    } else {
                        iconImg = "../iconfont/1-3.png";
                    }
                } else if ("通讯" == industry) {
                    if (rtuNum == 0) {
                        iconImg = "../iconfont/2-1.png";
                    } else if (rtuOffNum > 0) {
                        iconImg = "../iconfont/2-1.png";
                    } else if (rtuWarningNum > 0) {
                        iconImg = "../iconfont/2-2.png";
                    } else {
                        iconImg = "../iconfont/2-3.png";
                    }
                }
                var zoom = map.getZoom();
                var x;
                var y;
                if (zoom >= 5 && zoom <= 7) {
                    x = 32;
                    y = 40;
                } else if (zoom >= 8 && zoom <= 9) {
                    x = 48;
                    y = 56;
                }
                var myIcon = new BMap.Icon(iconImg, new BMap.Size(x, y), {
                    // 指定定位位置。
                    // 当标注显示在地图上时，其所指向的地理位置距离图标左上
                    // 角各偏移10像素和25像素。您可以看到在本例中该位置即是
                    // 图标中央下端的尖角位置。
                    //anchor: new BMap.Size(10, 25)
                    // 设置图片偏移。
                    // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您
                    // 需要指定大图的偏移位置，此做法与css sprites技术类似。
                    //imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移
                    imageSize: new BMap.Size(x, y)
                });
                // 创建标注对象并添加到地图
                var point = new BMap.Point(arr[i].site_lng, arr[i].site_lat);
                var marker = new BMap.Marker(point, {icon: myIcon});
                var label = new BMap.Label(arr[i].site_name, {
                    offset: new BMap.Size(15, -25)
                });
                var backgroundColor;
                if (rtuNum == 0) {
                    backgroundColor = "#bfbfbf";
                } else if (rtuOffNum > 0) {
                    backgroundColor = "#bfbfbf";
                } else if (rtuWarningNum > 0) {
                    backgroundColor = "#EEAD0E";
                } else {
                    backgroundColor = "green";
                }
                label.setStyle({
                    width: "auto",
                    color: '#fff',
                    background: backgroundColor,
                    border: '1px solid "#00CD66"',
                    borderRadius: "5px",
                    textAlign: "center",
                    height: "24px",
                    lineHeight: "24px",
                    fontSize: "15px",
                    fontWeight: "bold"
                });
                marker.setLabel(label); //为标注添加一个标签
                createInfoWindow(marker, i);
                map.addOverlay(marker);
            }
        });
    }

}

function createInfoWindow(marker, i) {
    var $scope = angular.element(appElement).scope();
    marker.addEventListener("click", function () {
        console.log(arr[i].site_name);
        $("#myModal").modal('show');
        $(".modal-title").text(arr[i].site_name);
    });
}

//时间戳格式化
function getLocalTime(nS) {
    var now = new Date(nS);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

//当前时间
function getNowTime() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}