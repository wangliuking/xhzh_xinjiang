
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
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

//节点上的数据遵循如下的格式：
var tree = [{
    text:"Node 1",
    tags:["test"],
    nodes: [{
        text: "Child 1",
        nodes: [{
            text: "Grandchild 1",
            nodes: [{text: "child1333"},{text: "child2333"}]
        }, {
            text: "Grandchild 2"
        }]
    }, {
        text: "Child 2"
    }]
}, {
    text: "Parent 2",
    nodes: [{
        text: "People 1",
        nodes: [{
            text: "Grandchild 3"
        }, {
            text: "Grandchild 4"
        }]
    }, {
        text: "People 2"
    }]
}, {
    text: "Parent 3"
}, {
    text: "Parent 4"
}, {
    text: "Parent 5"
}];

var initSearchableTree = function() {
    return $('#treeview-searchable').treeview({
        size: 18,
        color: "#428bca",
        //backColor: "black",背景色
        //borderColor:'green',
        collapseIcon: "glyphicon glyphicon-minus",//可收缩的节点图标
        nodeIcon: "glyphicon glyphicon-user",
        //emptyIcon: "glyphicon glyphicon-ban-circle",//设置列表树中没有子节点的节点的图标
        expandIcon: "glyphicon glyphicon-plus",  //设置列表上中有子节点的图标
        highlightSearchResults:true,//是否高亮搜索结果 默认true
        highlightSelected:true,     //是否选中高亮显示
        onhoverColor: "#f5f5f5",    //鼠标滑过的颜色
        levels: 0 ,                 //设置初始化展开几级菜单 默认为2
        selectedIcon: 'glyphicon glyphicon-user',
        // selectedBackColor: 'black',  //设置被选中的节点背景颜色
        //selectedColor : 'red',      //设置被选择节点的字体、图标颜色
        showBorder:true,                //是否显示边框
        showCheckbox:false,              //是否显示多选框
        //uncheckedIcon:'',             //设置未选择节点的图标
        data:tree,
        showTags:true//显示徽章
    });
};
var $searchableTree = initSearchableTree();
$('#treeview-searchable').treeview('collapseAll', {silent : true});

//节点选择事件
$('#treeview-searchable').on('nodeSelected',function(event, data) {
    console.log(data);
    $('#treeview-searchable').treeview('expandNode', [ data.nodeId, { levels: 1, silent: true } ]);
});
//节点取消选择事件
/*$('#treeview-searchable').on('nodeUnselected',function(event, data) {
    $('#treeview-searchable').treeview('collapseNode', [ data.nodeId, { silent: true, ignoreChildren: true } ]);
});*/

var findSearchableNodes = function() {
    return $searchableTree.treeview('search', [ $.trim($('#input-search').val()), { ignoreCase: false, exactMatch: false } ]);
};
var searchableNodes = findSearchableNodes();
// Select/unselect/toggle nodes
$('#input-search').on('keyup', function (e) {
    var str = $('#input-search').val();
    if($.trim(str).length>0){
        searchableNodes = findSearchableNodes();
    } else {
        $('#treeview-searchable').treeview('collapseAll', {
            silent : false //设置初始化节点关闭
        });
    }
    //$('.select-node').prop('disabled', !(searchableNodes.length >= 1));
});
var search = function(e) {
    var pattern = $.trim($('#input-search').val());
    var options = {
        ignoreCase: $('#chk-ignore-case').is(':checked'),
        exactMatch: $('#chk-exact-match').is(':checked'),
        revealResults: $('#chk-reveal-results').is(':checked')
    };
    var results = $searchableTree.treeview('search', [ pattern, options ]);
    var output = '<p>' + results.length + ' 匹配的搜索结果</p>';
    $.each(results, function (index, result) {
        output += '<p>- <span style="color:red;">' + result.text + '</span></p>';
    });
    $('#search-output').html(output);
}
$('#btn-search').on('click', search);
$('#input-search').on('keyup', search);
$('#btn-clear-search').on('click', function (e) {
    $searchableTree.treeview('clearSearch');
    $('#input-search').val('');
    $('#search-output').html('');
    $('#treeview-searchable').treeview('collapseAll', {
        silent : true//设置初始化节点关闭
    });
});


