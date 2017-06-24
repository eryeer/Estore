<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="inc/common_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="inc/header.jsp"%>
<form action="${path}/adminServlet?method=addGoods" method="post" enctype="multipart/form-data">
<table align="center">
	<tr><th colspan="2">添加商品</th></tr>
	<tr>
		<td>商品名:</td>
		<td><input type="text" name="name"></td>
	</tr>
	<tr>
		<td>市场价:</td>
		<td><input type="text" name="marketprice">元</td>
	</tr>
	<tr>
		<td>商城价:</td>
		<td><input type="text" name="estoreprice">元</td>
	</tr>
	<tr>
		<td>分类:</td>
		<td><select name="category">
				<option value="图书、电子书刊、音像">图书、电子书刊、音像</option>
				<option value="服装服饰">服装服饰</option>
				<option value="家用电器">家用电器</option>
			</select></td>
	</tr>
	<tr>
		<td>库存量:</td>
		<td><input type="text" name="num" value="输入整数"></td>
	</tr>
	<tr>
		<td>上传图片:</td>
		<td><input type="file" name="upload"></td>
	</tr>
	<tr>
		<td>商品描述:</td>
		<td><textarea name="description"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button>提交</button>
		</td>
	</tr>
</table>
</form>
<%@include file="inc/footer.jsp"%>
</body>
</html>