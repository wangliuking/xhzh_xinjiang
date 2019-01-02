
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

        $http.get("../../spd/selectAllSPDHistory?start=0&limit="+pageSize).
        success(function(response){
            $scope.changeDeviceTypeShow = 1;
            $scope.data = response.items;
            $scope.totals = response.totals;
            console.log($scope.data);
            xh.pagging(1, parseInt($scope.totals), $scope);
        });
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};

		/* 删除 */
		$scope.delBs = function(id) {
			swal({
				title : "提示",
				text : "确定要删除该站点吗？这同时会删除RTU和相关设备",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
                        url : '../../connect/deleteSite?id='+id+"&access_token="+localStorage.getItem("access_token"),
                        type : 'get',
                        dataType : "json",
                        async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();
							} else {
								toastr.error(data.message, '提示');
							}
						},
						error : function() {
							$scope.refresh();
						}
					});
				}
			});
		};
		/* 查询数据 */
		$scope.search = function(page) {
		    var deviceName = $("#deviceName").val();
            $scope.changeDeviceTypeShow = deviceName;

            var site_id = $("#siteName").val();
            var rtu_id = $("#rtuName").val();
            var location = $("#location").val();
            var deviceId = $("#deviceId").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();

            console.log(deviceName+" "+site_id+" "+rtu_id+" "+location+" "+deviceId);

			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}

			if(deviceName == 1){
                $http.get("../../spd/selectAllSPDHistory?start=0&limit="+limit+"&site_id="+site_id+"&rtu_id="+rtu_id+"&spd_number="+deviceId+"&location="+location+"&startTime="+startTime+"&endTime="+endTime).
                success(function(response){
                    console.log(response);
                    $scope.data = response.items;
                    $scope.totals = response.totals;
                    xh.pagging(page, parseInt($scope.totals), $scope);
                });
            }else if(deviceName == 2){
                $http.get("../../etcr/selectAllETCRHistory?start=0&limit="+limit+"&site_id="+site_id+"&rtu_id="+rtu_id+"&rst_id="+deviceId+"&location="+location+"&startTime="+startTime+"&endTime="+endTime).
                success(function(response){
                    console.log(response);
                    $scope.data = response.items;
                    $scope.totals = response.totals;
                    xh.pagging(page, parseInt($scope.totals), $scope);
                });
            }
			

		};

		//分页点击
		$scope.pageClick = function(page, totals, totalPages) {
            var deviceName = $("#deviceName").val();

            var site_id = $("#siteName").val();
            var rtu_id = $("#rtuName").val();
            var location = $("#location").val();
            var deviceId = $("#deviceId").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();

			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}

            if(deviceName == 1){
                $http.get("../../spd/selectAllSPDHistory?start="+start+"&limit=" + limit+"&site_id="+site_id+"&rtu_id="+rtu_id+"&spd_number="+deviceId+"&location="+location+"&startTime="+startTime+"&endTime="+endTime).
                success(function(response){
                    //xh.maskHide();
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
            }else if(deviceName == 2){
                $http.get("../../etcr/selectAllETCRHistory?start="+start+"&limit=" + limit+"&site_id="+site_id+"&rtu_id="+rtu_id+"&rst_id="+deviceId+"&location="+location+"&startTime="+startTime+"&endTime="+endTime).
                success(function(response){
                    //xh.maskHide();
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
            }

		};
	});
};


xh.add = function() {
    var fields = $("#addForm").serializeArray();
    var f = {};//声明一个对象
    $.each(fields,function(index,field){
        f[field.name] = field.value;//通过变量，将属性值，属性一起放到对象中
    });
    var str = JSON.stringify(f);
	$.ajax({
		url : '../../connect/insertSite?access_token='+localStorage.getItem("access_token"),
		contentType : "application/json;charset=utf-8",
		type : 'POST',
		dataType : "json",
		async : true,
		data : str,
		success : function(data) {
			$("#add_btn").button('reset');
            if (data.success) {
                $('#add').modal('hide');
                toastr.success(data.message, '提示');
                xh.refresh();
            } else {
                toastr.error(data.message, '提示');
                xh.refresh();
            }
		},
		error : function() {
			$("#add_btn").button('reset');
		}
	});
};

xh.edit = function() {

    var fields = $("#editForm").serializeArray();
    var f = {};//声明一个对象
    $.each(fields,function(index,field){
        f[field.name] = field.value;//通过变量，将属性值，属性一起放到对象中
    });
    console.log(f);
    var str = JSON.stringify(f);
    $.ajax({
        url : '../../connect/updateSite?&access_token='+localStorage.getItem("access_token"),
        contentType : "application/json;charset=utf-8",
        type : 'POST',
        dataType : "json",
        async : true,
        data : str,
        success : function(data) {
            $("#edit_btn").button('reset');
            if (data.success) {
                $('#edit').modal('hide');
                toastr.success(data.message, '提示');
                xh.refresh();
            } else {
                toastr.error(data.message, '提示');
                xh.refresh();
            }
        },
        error : function() {
            $("#edit_btn").button('reset');
        }
    });
};

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