<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
function change_buynum(current_buynum,gid,marketprice,estoreprice){
	var difference = parseFloat(marketprice)-parseFloat(estoreprice);
	var formal_buynum = window["formalnumber"+gid];
	var increase_buynum = current_buynum - formal_buynum;
	ajax({
 		url:"${path}/cartServlet?method=changeCartAJAX&gid="+gid+"&buynum="+increase_buynum,
 		method:"post",
 		callback:function(data){
 			if (data == "error") {
				location="${path}/500.jsp";
			}else if(data=="unlogin"){
				location="${path}/login.jsp";
			}else{
				var count = document.getElementById("count"+gid);
				count.innerHTML = (current_buynum * parseFloat(estoreprice)).toFixed(2) + "元";
				var totalprice = document.getElementById("totalprice");
				totalprice.innerHTML = (parseFloat(totalprice.innerHTML.replace(',','')) + increase_buynum * parseFloat(estoreprice)).toFixed(2);
				var totaldiscount = document.getElementById("totaldiscount");
				totaldiscount.innerHTML = (parseFloat(totaldiscount.innerHTML.replace(',','')) + increase_buynum * difference).toFixed(2);
				// 把当前的购买数量，作为下一次修改的之前的数量
				window["formalnumber"+gid] = current_buynum;
			}
 		}
	});
}
function _delete(buynum, gid, marketprice, estoreprice){
	var difference = parseFloat(marketprice)-parseFloat(estoreprice);
	ajax({
		url:"${path}/cartServlet?method=deleteCartAJAX&gid="+gid,
 		method:"post",
 		callback:function(data){
 			//alert(data);
 			if (data == "error") {
				location="${path}/500.jsp";
			}else if(data=="unlogin"){
				location="${path}/login.jsp";
			}else{
				var tr = document.getElementById("tr"+gid);
				tr.parentElement.removeChild(tr);
				var totalprice = document.getElementById("totalprice");
				totalprice.innerHTML = (parseFloat(totalprice.innerHTML.replace(',','')) - buynum * parseFloat(estoreprice)).toFixed(2);
				var totaldiscount = document.getElementById("totaldiscount");
				totaldiscount.innerHTML = (parseFloat(totaldiscount.innerHTML.replace(',','')) - buynum * difference).toFixed(2);
				window["formalnumber"+gid]=0;
				check_cart();
			}
 		}
	
	});
}
function check_cart(){
	var tbody_cart = document.getElementById("tb_cart");
	var childrens = tbody_cart.children;
	//只有标题行和结算行时运行链接
	if (tbody_cart.children.length <= 2) {
		var table_parent = tbody_cart.parentElement.parentElement;
		table_parent.removeChild(tbody_cart.parentElement);
		var pg = document.createElement("p");
		pg.innerHTML="<br/><a href='${path}/goodsServlet?method=goodsList'>购物车空空如也,去逛逛..</a>";
		table_parent.appendChild(pg);
	} 
}
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a>
					<code>&gt;</code>
					我的购物车
				</div>
			</div>
			<div class="blank"></div>
			<div class="box">
				<div class="box_1">
					<div class="userCenterBox boxCenterList clearfix"
						style="_height:1%;">
						<h5>
							<span>我的购物车</span>
						</h5>
						<c:if test="${empty list}">
							<p></br><a href="${path}/goodsServlet?method=goodsList">购物车空空如也,去逛逛..</a></p>
						</c:if>
						<c:if test="${not empty list}">
							<table  width="100%" align="center" border="0" cellpadding="5"
								cellspacing="1" bgcolor="#dddddd">
								<tbody id="tb_cart">
								<tr>
									<th bgcolor="#ffffff">商品名称</th>
									<th bgcolor="#ffffff">市场价</th>
									<th bgcolor="#ffffff">本店价</th>
									<th bgcolor="#ffffff">购买数量</th>
									<th bgcolor="#ffffff">小计</th>
									<th bgcolor="#ffffff" width="160px">操作</th>
								</tr>
								
								<c:set var="totalprice" value="0" scope="page"></c:set>
								<c:set var="totaldiscount" value="0" scope="page"></c:set>
								<c:forEach items="${list}" var="cart">
									<c:set var="totalprice"
										value="${totalprice + cart.goods.estoreprice * cart.buynum}"
										scope="page"></c:set>
									<c:set var="totaldiscount"
										value="${totaldiscount + (cart.goods.marketprice-cart.goods.estoreprice) * cart.buynum}"
										scope="page"></c:set>
									<tr id="tr${cart.gid}" >
										<td bgcolor="#ffffff" align="center" style="width:300px;">
											<!-- 商品图片 --> <a href="javascript:;" target="_blank"> <img
												style="width:80px; height:80px;"
												src="${path}${cart.goods.imgurl}" border="0"
												title="${cart.goods.name}" /> </a><br /> <!-- 商品名称 --> <a
											href="javascript:;" target="_blank" class="f6">${cart.goods.name}</a>
										</td>
										<td align="center" bgcolor="#ffffff">${cart.goods.marketprice}元</td>
										<td align="center" bgcolor="#ffffff">${cart.goods.estoreprice}元</td>
										<script type="text/javascript">
											var formalnumber${cart.gid} = ${cart.buynum};
										</script>
										<td align="center" bgcolor="#ffffff"><input
											id="buynum${cart.gid}" value="${cart.buynum}"
											onchange="change_buynum(this.value,'${cart.gid}','${cart.goods.marketprice}','${cart.goods.estoreprice}')"
											size="4" class="inputBg" style="text-align:center;" /></td>
										<td align="center" bgcolor="#ffffff" id="count${cart.gid}">${cart.goods.estoreprice
											* cart.buynum}元</td>
										<td align="center" bgcolor="#ffffff"><a
											href="javascript:;"
											onclick="_delete(buynum${cart.gid}.value,'${cart.gid}','${cart.goods.marketprice}','${cart.goods.estoreprice}');"
											class="f6">删除</a></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="6"
										style="text-align:right;padding-right:10px;font-size:25px;">
										购物金额小计&nbsp;<font color="red"><span id="totalprice"><fmt:formatNumber
													value="${totalprice}" pattern="#,###.00"></fmt:formatNumber>
										</span>
									</font>元， 共为您节省了&nbsp;<font color="red"><span id="totaldiscount"><fmt:formatNumber
													value="${totaldiscount}" pattern="#,###.00"></fmt:formatNumber>
										</span>
									</font>元 <a href="javascript:;"  onclick="location='${path}/cartServlet?method=cartList&source=cartJsp';"><input value="去结算"
											type="button" class="btn"/>
									</a></td>
								</tr>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>
