/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : 'lib/echarts'
	}
});
var alarmbs=true;
var alarmji=true;
var appElement = document.querySelector('[ng-controller=screen]');
xh.load = function() {
	var app = angular.module("app", []);

	app.controller("screen", function($scope, $http) {

        $("#siteNum").html(xh.formatNum(10));
        $("#rtuNum").html(xh.formatNum(10));
        $("#deviceNum").html(xh.formatNum(10));
        $("#rtuOffNum").html(xh.formatNum(10));

        xh.map();
        xh.groupTop5();
        xh.userTop5();
        xh.callInfo();
        xh.getOneDay();
	});
};

xh.map=function(){
	// 设置容器宽高
	var height=document.documentElement.clientHeight;
	var width=document.documentElement.clientWidth;
	var resizeBarContainer = function() {
		$("#map").width((width/12)*5);
		$("#map").height(height-115);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	require([ 'echarts', 'echarts/chart/map' ], function(ec) {
		chart = ec.init(document.getElementById('map'));
		require('echarts/util/mapData/params').params.CD = {
		    getGeoJson: function (callback) {
		        $.getJSON('lib/echarts/util/mapData/params/510100.json',callback);
		    }
		}
		var option = {
			    backgroundColor: '',
			    color: ['gold','aqua','lime'],
			  
			 
			    textStyle:{
                	color:'#fff'
                },
			    tooltip : {
			        trigger: 'item',
			        formatter: '{b}'
			    },
			    series : [
			        {
			            name: '全国',
			            type: 'map',
			            roam: true,
			            hoverable: false,
			            mapType: 'CD',
			            itemStyle:{
			                normal:{
			                	label:{
			                		show:true,
			                		textStyle:{
			    			            color:'#fff',
			    			            fontSize:11
			    			        }
			                		} ,
			                    borderColor:'green',
			                    borderWidth:1,
			                    areaStyle:{
			                        /*color: '#1b1b1b',*/
			                        color:'',
			                        visibility: 'off'
			                    },
			                    
			                    emphasis:{label:{show:true}} 
			                }
			            },
			            geoCoord: {
			                
			            },
			            data:[],
			            markPoint:{
			            	symbol:'emptyCircle',
			               /* symbolSize : function (v){
			                    return 10 + v/10
			                },*/
			                effect : {
			                    show: true,
			                    shadowBlur : 20,
			                    
			                    color:'red'
			                },
			                itemStyle:{
			                    normal:{
			                        label:{show:true}
			                    },
			                    emphasis: {
			                        label:{position:'bottom'}
			                    }
			                },
			                data : name
			            }
			            
			        }
			    ]
			};
		 /*for (var i in data) {
             option.series[0].geoCoord[data[i].bsId+"-"+data[i].name] = [parseFloat(data[i].lng), parseFloat(data[i].lat)];
         }*/
		chart.setOption(option);
	});
	
}
xh.groupTop5=function(){
	// 设置容器宽高
	var height=document.documentElement.clientHeight;
	var width=document.documentElement.clientWidth;
	var resizeBarContainer = function() {
		$("#group-top5").width((width/12)*3);
		$("#group-top5").height((height-200)/3);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/funnel' ], function(ec) {
		chart = ec.init(document.getElementById('group-top5'));
		var leglend=[]
		/*for(var i=0;i<data.length;i++){
			leglend.push(data[i].name+"-"+data[i].value);
			data[i].name=data[i].name+"    ["+data[i].value+"]"
		}*/
		
		var option = {
			    
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}"
			    },
			    calculable : false,
			    series : [
			              {
					            name:'基站注册组',
					            type:'funnel',
					            width: 60,
					            height:'80%',
					            maxSize: '30%',
					            sort: 'descending', //数据排序，如果是：ascending，则是金字塔状 
					            gap: 1, //数据图像间距 
					            itemStyle: {//图像样式 
					                normal: { 
					                    borderColor: '#fff', //描边颜色 
					                    borderWidth: 1  //描边宽度 
					                    
					                } 
					            },
					            x:'10%',
					            y:10,
					            data:[{name:"站点1",value:"10"},{name:"站点2",value:"20"},{name:"站点3",value:"30"},{name:"站点4",value:"40"},{name:"站点5",value:"50"}]
					        }
			    ]
			};
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.userTop5=function(){
	// 设置容器宽高
	var height=document.documentElement.clientHeight;
	var width=document.documentElement.clientWidth;
	var resizeBarContainer = function() {
		$("#user-top5").width((width/12)*3);
		$("#user-top5").height((height-200)/3);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/funnel' ], function(ec) {
		chart = ec.init(document.getElementById('user-top5'));
		var leglend=[]
		/*for(var i=0;i<data.length;i++){
			leglend.push(data[i].name);
			data[i].name=data[i].name+"    ["+data[i].value+"]"
		}*/
		var option = {
			    
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}"
			    },
			   
			   /* legend: {
			    	 orient : 'vertical',
				        x : 'left',
				        textStyle:{
				        	color:'#fff',
				        },
			        data :leglend
			    },*/
			    calculable : false,
			    series : [
			        {
			            name:'基站注册终端',
			            type:'funnel',
			            width: 60,
			            height:'80%',
			            maxSize: '30%',
			            sort: 'descending', //数据排序，如果是：ascending，则是金字塔状 
			            gap: 1, //数据图像间距 
			            itemStyle: {//图像样式 
			                normal: { 
			                    borderColor: '#fff', //描边颜色 
			                    borderWidth: 1  //描边宽度 
			                } 
			            },
			  
			            
			            x:'10%',
			            y:10,
			            data:[]
			        }
			    ]
			};
		chart.setOption(option);
		
		

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.callInfo=function(){
	// 设置容器宽高
	var height=document.documentElement.clientHeight;
	var width=document.documentElement.clientWidth;
	var resizeBarContainer = function() {
		$("#call-bar").width((width/12)*4-40);
		$("#call-bar").height(height-450);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/bar','echarts/chart/line' ], function(ec) {
		chart = ec.init(document.getElementById('call-bar'));
		
		var option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	data:['呼叫时长','呼叫次数'],
			    	textStyle:{
			    		color:'#fff'
			    	}
			    },
			    
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        splitLine:{show: false},//去除网格线
                        splitArea : {show : false},//去除网格区域
			            data : ["00","01","02","03","04","05",
			                    "06","07","08","09","10","11",
			                    "12","13","14","15","16","17",
			                    "18","19","20","21","22","23"]
			        }
			    ],
			    yAxis : [ {
                    type: 'value',
                    name: '呼叫时长',
                    min: 0,
                    
                    position: 'left',
                    axisLabel: {
                        formatter: '{value} （分钟）',
                        textStyle:{
                        	color:'#fff'
                        }
                    }
                    },{
	                    type: 'value',
	                    name: '呼叫次数',
	                    min: 0,
	                  
	                    position: 'right',
	                    axisLabel: {
	                        formatter: '{value} （次）',
	                        textStyle:{
	                        	color:'#fff'
	                        }
	                    }
	                }],
			    series : [{
		            name:'次数',
		            type:'bar',
		            data:[],
		            itemStyle:{normal:{color:'#FF00FF'}}
		        }]
			};
		
		/*$.ajax({
			url : 'call/chart_call_hour_now',
			type : 'POST',
			dataType : "json",
			async : false,
			timeout:2000,
			data:{
				bsId:0,
				time:xh.getOneDay(),
				type:'hour'
			},
			success : function(response) {
				var data = response.time;
				var num = response.num;
				var xData=[],yData=[],yData2=[];
				
				for(var i=0;i<data.length;i++){
					xData.push(data[i].label);
					yData.push(data[i].time);
					yData2.push(num[i].num);
				}
				option.series[0].data = yData;
				option.series[1].data = yData2;
				option.xAxis[0].data = xData;
				chart.setOption(option);
				xh.maskHide();

			},
			failure : function(response) {
				xh.maskHide();
			}
		});*/
		
		

	});
		
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.waterstatus=function(id,totals){
	var vaterColor="blue";
	if(id==1){
		vaterColor="#808000";
	}else if(id==2){
		vaterColor="#00FF00";
	}else if(id==3){
		vaterColor="red";
	}
	$('#access'+id).waterbubble({
		radius : 40,
		lineWidth : 2,
		data : 0.7,
		waterColor : vaterColor,
		textColor : '#fff',
		txt : totals.toString(),
		font : 'bold 20px "Microsoft YaHei"',
		wave : true,
		animation : true
	});
}
xh.getOneDay=function() {
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay;   
    return  strYesterday;
}

xh.formatNum=function(text){
    var str="";
    var a=text.toString();
    for(var i=0;i<a.length;i++){
        str+="<span>"+a.charAt(i)+"</span>";
    }
    return str;

}