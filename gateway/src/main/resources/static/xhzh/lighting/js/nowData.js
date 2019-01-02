
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
	
	//var pageSize = $("#page-limit").val();
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

        $http.get("../../connect/selectAllSite?start=0&limit=" + pageSize+"&access_token="+localStorage.getItem("access_token")).
        success(function(response){
        	var data = response.items;
        	var siteNames = [];
            siteNames.push("全部名称");
        	for(var i=0;i<data.length;i++){
                siteNames.push(data[i].site_name);
			}
        	$scope.siteNames = siteNames;
        	$scope.industrys = ["全部行业","医疗","气象","新能源","轨道交通","石油化工","国防军工","电力","通讯"]
			$scope.siteStatus = ["全部状态","正常","异常","断开"]
            $scope.connectType = [{"id":2,"name":"全部模式"},{"id":1,"name":"TCP"},{"id":0,"name":"UDP"}];
            $scope.data = data;
            $scope.totals = response.totals;
            xh.pagging(1, parseInt($scope.totals), $scope);
        });

        $scope.showETCR = function(){
            $http.get("../../etcr/selectAllRelayno").
            success(function(response){
                $scope.etcrData = response.items;
            });
        }

        $scope.showETCRConf = function(id){
            /*console.log(id);
            console.log($scope.etcrData);*/
            $scope.oneConfData = $scope.etcrData[id];
        }


        $scope.searchNowData = function(data){
            console.log(data);
            $.ajax({
                url : '../../etcr/selectNowData?rtu_id='+data.rtu_id+"&rst_id="+data.rst_id+"&relayno="+data.relayno,
                type : 'get',
                dataType : "json",
                async : false,
                success : function(data) {
                    console.log(data);
                    $scope.wlk = data.items;
                },
                error : function() {
                    $scope.refresh();
                }
            });
        }

        /* 显示添加框 */
        $scope.showMod = function() {
            /*$('#add input').val('');*/
            $('#add').modal('show');
        };

        /* 显示修改框 */
        $scope.showEdit = function(id) {
            $scope.editData = $scope.data[id];
            var site_province = $scope.editData.site_province;
            var site_city = $scope.editData.site_city;
            var site_county = $scope.editData.site_county;
            $("#editProvince").val(site_province);
            $("#editProvince").change();
            $("#editCity").val(site_city);
            $("#editCity").change();
            $("#editArea").val(site_county);
            $("#editArea").change();
            $('#edit').modal('show');
        };
		
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
            //console.log(site_name+"=="+site_industry+"=="+site_province+"=="+site_city+"=="+site_county+"=="+status)

			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			
			$http.get("../../connect/selectAllSite?start=0&limit=" + limit+"&site_name="+site_name+"&site_industry="+site_industry+"&site_province="+site_province+"&site_city="+site_city+"&site_county="+site_county+"&status="+status+"&access_token="+localStorage.getItem("access_token")).
			success(function(response){
				console.log(response);
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
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
			
			$http.get("../../connect/selectAllSite?start="+start+"&limit=" + limit+"&site_name="+site_name+"&site_industry="+site_industry+"&site_province="+site_province+"&site_city="+site_city+"&site_county="+site_county+"&status="+status+"&access_token="+localStorage.getItem("access_token")).
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