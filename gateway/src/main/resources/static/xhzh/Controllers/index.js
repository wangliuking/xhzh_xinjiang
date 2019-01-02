
if (!("xh" in window)) {
	window.xh = {};
};
var appElement = document.querySelector('[ng-controller=index]');
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("index", function($scope, $http) {
		$scope.totals=0;
		$scope.mshow=0;
		$scope.voiceTag=0;
		if(xh.getcookie("skin")!=null){
			$('body').attr('id', xh.getcookie("skin"));
		}else{
			$('body').attr('id', "skin-blur-ocean");
		}

		/*$http.get("web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});*/
	});
};
/* 获取cookie */
xh.getcookie = function(name) {
	var strcookie = document.cookie;
	var arrcookie = strcookie.split(";");
	for (var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0].match(name) == name)
			return arr[1];
	}
	return "";
};

