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
<a href="${path }/admin_add.jsp">添加商品</a>
<a href="${path }/adminServlet?method=rankingOnline">在线查看销售榜单</a>
<a href="${path }/adminServlet?method=ranking">下载销售榜单</a>
<%@include file="inc/footer.jsp"%>
</body>
</html>