<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提交订单</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
	function load(value,target){
		if(value === ""){
			target.length=1;
			district.length=1;
			return;
		}
		ajax({
 			url:"${path}/orderServlet?method=findCitiesAJAX&pid="+value,
 			method:"get",
 			callback:function(data){
 				if (data == "error") {
					location="${path}/500.jsp";
				}else if(data=="unlogin"){
					location="${path}/login.jsp";
				}else{
 					var list = JSON.parse(data);
 					for(var i = 0; i < list.length; i++){
 						var op = new Option(list[i].name,list[i].id);
 						target.appendChild(op);
 					}
 					if (value != 0) {
						
					}
 				}
 			}
		});
	}
	
	if (window.addEventListener) {
			window.addEventListener("load",function(){
				load(0, province);
				cityEvent();
			});
		}else {
			window.attachEvent("onload",function(){
				load(0, province);
				cityEvent();
			});
	}
	function cityEvent(){
		province.onchange=function(){
			
			province_name=this.options[this.selectedIndex].innerHTML;
			load(this.value,city);
		}
		
		city.onchange=function(){
			
			city_name=this.options[this.selectedIndex].innerHTML;
			load(this.value,district);
		}
						
		district.onchange=function(){
			district_name=this.options[this.selectedIndex].innerHTML;
		}
	}
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form action="${path}/orderServlet?method=orderSubmit" method="post">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
					<!-- 省 -->
					<select id="province">
						<option value="">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 市 -->
					<select id="city">
						<option value="">-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 县(区) -->
					<select id="district">
						<option value="">-- 请选择县(区) --</option>
					</select>
				</td>
			</tr>
			
				<input type="hidden" id="province_name" name="province_name">
				<input type="hidden" id="city_name" name="city_name">
				<input type="hidden" id="district_name" name="district_name">
			
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" id="detail_address" name="detail_address"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input id="postcode" name="postcode"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input id="name" name="name"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input id="tel" name="tel"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#dddddd">
			<tr>
				<th width="30%" align="center">商品名称</th>
				<th width="22%" align="center">市场价格</th>
				<th width="22%" align="center">商品价格</th>
				<th width="15%" align="center">购买数量</th>
				<th align="center">小计</th>
			</tr>
			<c:set var="totalprice" value="0" scope="page"></c:set>
			<c:forEach items="${list}" var="cart">
			<c:set var="totalprice" value="${totalprice + cart.goods.estoreprice * cart.buynum}" scope="page"></c:set>
				<tr>
					<td>
						<a href="javascript:;" class="f6">${cart.goods.name }</a>
					</td>
					<td>${cart.goods.marketprice}元</td>
					<td>${cart.goods.estoreprice}元</td>
					<td align="center">${cart.buynum}</td>
					<td>${cart.goods.estoreprice * cart.buynum}元</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" style="text-align:right;padding-right:10px;font-size:25px;">
					商品总价&nbsp;<font color="red">&yen;${totalprice}</font>元
					<input type="submit" value="提交订单" class="btn" />
				</td>
			</tr>
		</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>