
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

//设备名称和类型id映射
var deviceTypeForName = {"接地电阻":1,"雷电流":3,"静电":4,"温湿度":5,"倾斜度":6,"电气安全":7,"杂散电流":8,"阴极保护":9};

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
        $scope.rtuId = 0;

        //判断是否登录start
        $.ajax({
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
        });
        //判断是否登录end
        $http.get("../../connect/selectAllRTU").
        success(function(response){
            var data = response.items;
            var rtuNames = [];
            for(var i=0;i<data.length;i++){
                rtuNames.push({"rtu_id":data[i].rtu_id,"rtu_name":data[i].rtu_id});
            }
            $scope.rtuNames = rtuNames;
            $scope.industrys = ["医疗","气象","新能源","轨道交通","石油化工","国防军工","电力","通讯"]
        });

        //多级联动start
        $scope.changeIndustry = function(x){
            console.log(x);
            $http.get("../../connect/selectStructureList?industry="+x).
            success(function(response){
                var data = response;
                var companys = [];
                for(var i=0;i<data.length;i++){
                    companys.push(data[i].name);
                }
                $scope.companys = companys;
            });
        }

        $scope.changeCompany = function(x){
            console.log(x);
            $http.get("../../connect/selectAllSite?site_company="+x).
            success(function(response){
                var data = response.items;
                var siteNames = [];
                for(var i=0;i<data.length;i++){
                    siteNames.push({"id":data[i].site_id,"name":data[i].site_name});
                }
                $scope.siteNames = siteNames;
            });
        }

        $scope.changeSite = function(x){
            console.log(x);
            $http.get("../../connect/selectAllRTU?site_id="+x).
            success(function(response){
                var data = response.items;
                var rtuNames = [];
                for(var i=0;i<data.length;i++){
                    rtuNames.push({"rtu_id":data[i].rtu_id,"rtu_name":data[i].rtu_id});
                }
                $scope.rtuNames = rtuNames;
            });
        }
        //多级联动end

        $scope.searchDevicesByRTU = function(){
            var rtu_id = $("#testRTU").val();
            //console.log("===");
            //console.log(rtu_id);
            if(rtu_id == null || rtu_id == ""){
                alert("请选择RTU！！！");
                return false;
            }
            $scope.rtuId = rtu_id;
            $http.get("../../connect/selectRTUById?id="+rtu_id).
            success(function(response){
                $scope.rtuData = response;
            });

            $http.get("../../total/selectDeviceNum?rtu_id="+rtu_id).
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

                $http.get("../../total/selectRTUPort?rtu_id="+rtu_id).
                success(function(response){
                    $scope.spdPort = response.spdPort;
                    $scope.spdPort1 = [1,2,3,4,5,6,7,8];
                    $scope.testPort1 = [1,2,3,4,5,6,7,8];
                    $scope.testRS1 = [1,2,3,4,5,6,7,8,9,10];
                    $scope.testList = response.testList;
                    $scope.rs485List = response.rs485List;
                    console.log($scope.rs485List);
                    console.log($scope.testList);
                });

            });
        }

        $scope.showRSDevice = function(x){
            var type = $scope.judgeDeviceRSType(x);
            $scope.setTimeType = 1;
            $scope.showDevice(type,1,x);
        }

        $scope.showTestDevice = function(x){
            var type = $scope.judgeDeviceTestType(x);
            $scope.setTimeType = 0;
            console.log(type);
            $scope.showDevice(type,0,x);
        }

        $scope.showDevice = function(type,test485,channel){
            //test485=0 模拟量 test485=1 485
            //console.log(type);
            var rtu_id = $scope.rtuId;
            $scope.deviceTypeChoosed = type;
            if(type == 1){
                //接触式地阻
                $http.get("../../etcr/selectETCROneType?rtu_id="+rtu_id).
                success(function(response){
                    var finalData1 = $scope.filterRSData(response,"rst_id",channel,test485);
                    $scope.ETCROneCount = finalData1.length;
                    $scope.ETCROneData = finalData1;
                    $http.get("../../etcr/selectETCRTwoType?rtu_id="+rtu_id).
                    success(function(response){
                        var finalData2 = $scope.filterRSData(response,"rst_id",channel,test485);
                        $scope.ETCRTwoCount = finalData2.length;
                        $scope.ETCRTwoData = finalData2;
                        $scope.ETCRAddCount = $scope.ETCROneCount+$scope.ETCRTwoCount;
                        console.log($scope.ETCRAddCount);
                        //console.log($scope.ETCROneData);
                        //console.log($scope.ETCRTwoData);
                    });
                });

                //非接触式地阻
                $http.get("../../etcr/selectETCRThreeType?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"rst_id",channel,test485);
                    //console.log(response);
                    $scope.ETCRThreeData = finalData;
                    $scope.ETCRThreeCount = finalData.length;
                });
            }else if(type == 3){
                //雷电流
                $http.get("../../lightning/selectLightningByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"ltn_id",channel,test485);
                    //console.log(response);
                    $scope.LightningData = finalData;
                });
            }else if(type == 4){
                //静电
                $http.get("../../static/selectStaticByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"staet_id",channel,test485);
                    //console.log(response);
                    $scope.StaticData = finalData;
                });
            }else if(type == 5){
                //温湿度
                $http.get("../../rsws/selectRswsByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"hmt_id",channel,test485);
                    //console.log(response);
                    $scope.RswsData = finalData;
                });
            }else if(type == 6){
                //倾斜度
                $http.get("../../svt/selectSvtByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"tilt_id",channel,test485);
                    //console.log(response);
                    $scope.SvtData = finalData;
                });
            }else if(type == 7){
                //电器安全
                $http.get("../../hc/selectHcByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"es_id",channel,test485);
                    //console.log(response);
                    $scope.HcData = finalData;
                });
            }else if(type == 8){
                //杂散电流
                $http.get("../../stray/selectStrayByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"stret_id",channel,test485);
                    console.log(response);
                    $scope.StrayData = finalData;
                });
            }else if(type == 9){
                //阴极保护
                $http.get("../../cat/selectCatByRTU?rtu_id="+rtu_id).
                success(function(response){
                    var finalData = $scope.filterRSData(response,"cathode_id",channel,test485);
                    //console.log(response);
                    $scope.CatData = finalData;
                });
            }
            $("#show").modal('show');
        }

        $scope.filterRSData = function(data,deviceId,channel,test485){
            console.log("test485:"+test485);
            if(test485 == 1){
                var finalData = [];
                var ids = [];
                var rs485List = $scope.rs485List;
                for(var i=0;i<rs485List.length;i++){
                    var port = rs485List[i]["port"];
                    if(port == channel){
                        ids.push(rs485List[i]["deviceId"]);
                    }
                }
                for(var i=0;i<data.length;i++){
                    var devId = data[i][deviceId];
                    for(var j=0;j<ids.length;j++){
                        if(devId == ids[j]){
                            finalData.push(data[i]);
                        }
                    }
                }
                return finalData;
            }else if(test485 == 0){
                var finalData = [];
                var tempKey;
                var testList = $scope.testList;
                for(var i=0;i<testList.length;i++){
                    for(var key in testList[i]){
                        if(key > 0 && key == channel){
                            tempKey = key;
                        }
                    }
                }
                for(var i=0;i<data.length;i++){
                    var devId = data[i][deviceId];
                    var ch = data[i]["rtu_channel"];
                    if(ch == tempKey && devId == 0){
                        finalData.push(data[i]);
                    }
                }
                return finalData;
            }

        }

        $scope.judgeDeviceRSType = function(x){
            var rs485List = $scope.rs485List;
            for(var i=0;i<rs485List.length;i++){
                var temp = rs485List[i];
                var port = temp["port"];
                if(x == port){
                    var type = temp["type"];
                    //console.log(type);
                    return deviceTypeForName[type];
                }
            }
            return 0;
        }

        $scope.judgeDeviceTestType = function(x){
            var testList = $scope.testList;
            for(var i=0;i<testList.length;i++){
                var temp = testList[i];
                if(temp[x] != null && temp[x] != ""){
                    return deviceTypeForName[temp[x]];
                }
            }
            return 0;
        }

        $scope.compareRS = function (x) {
            var rs485List = $scope.rs485List;
            var count = 0;
            var type;
            for(var i=0;i<rs485List.length;i++){
                var temp = rs485List[i];
                var port = temp["port"];
                if(x == port){
                    count++;
                    type = temp["type"];
                }
            }
            if(count>0){
                return type+"("+count+")";
            }else{
                return x;
            }
        };

        $scope.compareRSStyle = function (x) {
            var rs485List = $scope.rs485List;
            var count = 0;
            var status = 0;
            var alarm = 0;
            for(var i=0;i<rs485List.length;i++){
                var temp = rs485List[i];
                var port = temp["port"];
                if(x == port){
                    count++;
                    if(temp["status"] != null && temp["status"] != ""){
                        status = status + parseInt(temp["status"]);
                    }
                    if(temp["alarm"] != null && temp["alarm"] != ""){
                        alarm = alarm + parseInt(temp["alarm"]);
                    }
                }
            }
            if(count>0){
                if(status > 0 && alarm == 0){
                    return {"background-color" : "red"};
                }else if(status > 0 && alarm > 0){
                    return {"background" : "-webkit-linear-gradient(left, red , #EEAD0E)","background":"-o-linear-gradient(right, red, #EEAD0E)","background":"-moz-linear-gradient(right, red, #EEAD0E)","background":"linear-gradient(to right, red , #EEAD0E)"};
                }else if(status == 0 && alarm > 0){
                    return {"background-color" : "#EEAD0E"};
                }
            }else{
                return {"background-color" : "grey"};
            }
        };

        $scope.compareTest = function (x) {
            var testList = $scope.testList;
            for(var i=0;i<testList.length;i++){
                var temp = testList[i];

                for (var p in temp) {
                    if (temp.hasOwnProperty(p)){
                        //console.log("p : "+p);
                        //console.log("values : "+temp[p]);
                        if(p == x){
                            return temp[p];
                        }
                    }
                }

            }
            return x;
        };

        $scope.compareTestStyle = function (x) {
            var testList = $scope.testList;
            for(var i=0;i<testList.length;i++){
                var temp = testList[i];

                for (var p in temp) {
                    if (temp.hasOwnProperty(p)){
                        //console.log("p : "+p);
                        //console.log("values : "+temp[p]);
                        if(p == x){
                            var state = temp["0"];
                            var alarm = temp["-1"];
                            if(state == 0 && alarm == 0){
                                return {"background-color" : "green"};
                            }else if(state > 0 && alarm == 0){
                                return {"background-color" : "red"};
                            }else if(state > 0 && alarm > 0){
                                return {"background" : "-webkit-linear-gradient(left, red , #EEAD0E)","background":"-o-linear-gradient(right, red, #EEAD0E)","background":"-moz-linear-gradient(right, red, #EEAD0E)","background":"linear-gradient(to right, red , #EEAD0E)"};
                            }else if(state == 0 && alarm > 0){
                                return {"background-color" : "#EEAD0E"};
                            }
                        }
                    }
                }

            }
            return {"background-color" : "grey"};
        };

        $scope.compareSpd = function (x) {
            var spdPorts = $scope.spdPort;
            for(var i=0;i<spdPorts.length;i++){
                var temp = spdPorts[i].spd_number;
                if(temp == x){
                    var state = spdPorts[i].spd_state;
                    if(state == 0){
                        return {"background-color" : "green"};
                    }else{
                        return {"background-color" : "red"};
                    }
                }
            }
            return {"background-color" : "grey"};
        };

		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};

	});

};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

var countdown = 60;
var result = "no";

function settime(obj) {
    var $scope = angular.element(appElement).scope();
    console.log("=====");
    console.log($scope.setTimeType);
    //console.log(obj);
    if(countdown == 60){
        var rtu_id = $("#testRTU").val();
        var channo = $(obj).siblings("input").eq(0).val();
        if($scope.setTimeType == 0){
            channo = parseInt(channo)+20;
        }
        var deviceId = $(obj).siblings("input").eq(1).val();
        var deviceType = $(obj).val();
        var name = $(obj).attr("name");
        //console.log($(obj).siblings("input"));
        console.log(rtu_id+"=="+channo+"=="+deviceId+"=="+deviceType+"=="+name);
        $.ajax({
            url : '../../mq/getDeviceInfo?rtu_id='+rtu_id+'&channo='+channo+'&deviceId='+deviceId+'&deviceType='+deviceType+"&portId="+name,
            contentType : "application/json;charset=utf-8",
            type : 'GET',
            dataType : "json",
            async : true,
            success : function(data) {
                result = "yes";
                console.log(data);
                if(deviceType == 3){//温湿度
                    var i = parseFloat(data.resultValue[0]);
                    var j = parseFloat(data.resultValue[1]);
                    $(obj).siblings("input").eq(2).val(j);
                    $(obj).siblings("input").eq(3).val(i);
                }else if(deviceType == 5){//倾斜度
                    var x = parseFloat(data.resultValue[0]);
                    var y = parseFloat(data.resultValue[1]);
                    $(obj).siblings("input").eq(2).val(x);
                    $(obj).siblings("input").eq(3).val(y);
                    var tanX = Math.tan(x/180*(Math.PI));
                    var tanY = Math.tan(y/180*(Math.PI));
                    var Z = Math.sqrt((tanX*tanX)+(tanY*tanY));
                    $(obj).siblings("input").eq(4).val(tanX);
                    $(obj).siblings("input").eq(5).val(tanY);
                    $(obj).siblings("input").eq(6).val(Z);
                }else if(deviceType == 6){//电气安全
                    var a = parseFloat(data.resultValue[0]);
                    var b = parseFloat(data.resultValue[1]);
                    var c = parseFloat(data.resultValue[2]);
                    var d = parseFloat(data.resultValue[3]);
                    $(obj).siblings("input").eq(2).val(a);
                    $(obj).siblings("input").eq(3).val(b);
                    $(obj).siblings("input").eq(4).val(c);
                    $(obj).siblings("input").eq(5).val(d);
                }else if(deviceType == 7){//杂散电流
                    var a = parseFloat(data.resultValue[0]);
                    var b = parseFloat(data.resultValue[1]);
                    var c = parseFloat(data.resultValue[2]);
                    var d = parseFloat(data.resultValue[3]);
                    $(obj).siblings("input").eq(2).val(a);
                    $(obj).siblings("input").eq(3).val(b);
                    $(obj).siblings("input").eq(4).val(c);
                    $(obj).siblings("input").eq(5).val(d);
                }else{
                    var i = parseFloat(data.resultValue);
                    var a = Math.round(i*100);
                    var res = a/100;
                    $(obj).siblings("input").eq(2).val(res);
                }

            },
            error : function() {
            }
        });
    }

    if (countdown == 0) {
        $("button").attr('disabled',false);
        //obj.removeAttribute("disabled");
        obj.innerHTML = "";
        countdown = 60;
        //$scope.testETCR1 = Math.floor(Math.random()*10)+1.1;
        return;
    } else {
        $("button").attr('disabled',true);
        //obj.setAttribute("disabled", true);
        obj.innerHTML = countdown;
        countdown--;
    }
    if(result == "no"){
        setTimeout(function () {
            settime(obj)
        }, 1000)
    }else{
        $("button").attr('disabled',false);
        //obj.removeAttribute("disabled");
        obj.innerHTML = "";
        countdown = 60;
        //$scope.testETCR1 = Math.floor(Math.random()*10)+1.1;
        result = "no";
        return;
    }

}