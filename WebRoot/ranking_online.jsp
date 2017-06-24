<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="inc/common_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ranking Online</title>
<script src="${path }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#container')
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : '商品销售榜单'
							},
							xAxis : {
								categories : [ 
								'Apples', 'Oranges', 'Pears',
										'Grapes', 'Bananas' ]
							},
							yAxis : {
								min : 0,
								title : {
									text : '销售数量'
								},
								stackLabels : {
									enabled : true,
									style : {
										fontWeight : 'bold',
										color : (Highcharts.theme && Highcharts.theme.textColor)
												|| 'gray'
									}
								}
							},
							legend : {
								align : 'right',
								x : -70,
								verticalAlign : 'top',
								y : 20,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
										|| 'white',
								borderColor : '#CCC',
								borderWidth : 1,
								shadow : false
							},
							tooltip : {
								formatter : function() {
									return '<b>' + this.x + '</b><br/>'
											+ this.series.name + ': ' + this.y
											+ '<br/>' + 'Total: '
											+ this.point.stackTotal;
								}
							},
							plotOptions : {
								column : {
									stacking : 'normal',
									dataLabels : {
										enabled : true,
										color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
												|| 'white',
										style : {
											textShadow : '0 0 3px black, 0 0 3px black'
										}
									}
								}
							},
							series : [ {
								name : 'John',
								data : [ 5, 3, 4, 7, 2 ]
							}, {
								name : 'Jane',
								data : [ 2, 2, 3, 2, 1 ]
							}, {
								name : 'Joe',
								data : [ 3, 4, 4, 2, 5 ]
							} ]
						});
	});
</script>
</head>
<body>
	<script src="${path }/js/highcharts.js"></script>
	<script src="${path }/js/modules/exporting.js"></script>

	<div id="container"
		style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>
