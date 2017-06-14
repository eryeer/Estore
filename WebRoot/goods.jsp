<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选购中心</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
function go(pageNum){
		if(typeof(pageNum) == "string"){
			pageNum = pageNum.trim();
		}
		if(pageNum == ""){
			alert("请输入页数");
			return;
		}
		if(pageNum != parseInt(pageNum)){
			alert("请输入合法数据");
			return;
		}
		if(parseInt(pageNum) <= 0){
			alert("页数不能小于1");
			return;
		}
		if(parseInt(pageNum) > parseInt("${pagination.pageCount}")){
			
			document.getElementById("input_go").value="${pagination.pageCount}";
			pageNum = "${pagination.pageCount}";
		}
		location.href="${path}/goodsServlet?method=goodsList&pageNum="+pageNum+"&pageSize=${pagination.pageSize}";
	}
	
	function changeSize(pageSize){
		location.href="${path}/goodsServlet?method=goodsList&pageNum=1&pageSize="+pageSize;
	}
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block box">
		<div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a>
			<code>&gt;</code>
			商品列表
		</div>
	</div>
	<div class="blank"></div>
	<div class="block clearfix">
		<div class="AreaR">
			<div class="box">
				<div class="box_1">
					<h3>
						<span>商品列表</span>
					</h3>
					<!-- 商品列表开始 -->
					<div class="clearfix goodsBox" style="border:none; ">
						<!-- 商品循环 -->
						<c:forEach items="${pagination.data}" var="goods">
							<div class="goodsItem" style="padding: 10px 4px 15px 1px;" onclick="location='${path}/goodsServlet?method=goodsDetail&id=${goods.id}';">
								<a href="javascript:;"> <img
									src="${path}${goods.imgurl}"
									alt="${goods.name}" class="goodsimg" /> </a><br />
								<p style=" height:20px; overflow:hidden;">
									<a href="javascript:;" title="">${goods.name}</a>
								</p>
								市场价：<font class="market">${goods.marketprice}元</font><br/> 本店价：<font class="f1">${goods.estoreprice}元
								</font>
							</div>
						</c:forEach>
					</div>
					<!-- 商品列表结束 -->
					<!-- 分页栏 -->
					<div align="center">
						<c:if test="${pagination.pageNum > 1}">
							<a href="javascript:void(0)"
								onclick="go(${pagination.firstPage})">首页</a>
							<a href="javascript:void(0)"
								onclick="go(${pagination.previousPage})">上一页</a>
						</c:if>
						<c:forEach items="${pagination.pageBar}" var="page">
							<c:if test="${page==pagination.pageNum}">
								<font color="red" size="5">${page}&nbsp;</font>
							</c:if>
							<c:if test="${page!=pagination.pageNum}">
								<a href="javascript:void(0)" onclick="go(${page})">${page}&nbsp;</a>
							</c:if>
						</c:forEach>
						<c:if test="${pagination.pageNum < pagination.pageCount}">
							<a href="javascript:void(0)" onclick="go(${pagination.nextPage})">下一页</a>
							<a href="javascript:void(0)" onclick="go(${pagination.lastPage})">尾页</a>
						</c:if>
						<input type="text" id="input_go" value="${pagination.pageNum}"
							name="input_go" size="1"> <input type="button"
							onclick="go(document.getElementById('input_go').value)"
							value="跳转" style="cursor: pointer;"> <select
							onchange="go(this.value);">
							<c:forEach var="i" begin="1" end="${pagination.pageCount}"
								step="1">
								<option value="${i}" ${pagination.pageNum==i?
									"selected='selected'":""}>${i}</option>
							</c:forEach>
						</select> &nbsp; 每页显示 <select onchange="changeSize(this.value);">
							<option value="3" ${pagination.pageSize== 3?"selected='selected'":""}>3</option>
							<option value="5" ${pagination.pageSize== 5?"selected='selected'":""}>5</option>
							<option value="10" ${pagination.pageSize==
								10?"selected='selected'":""}>10</option>
						</select>条记录
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
	<script type="text/javascript">
		window.onload = function() {
			fixpng();
		}
	</script>
</body>
</html>