<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h4> 예외 처리 테스트 </h4>
<%
	if(1==1)
		throw new IOException("강제발생예외");
%>
</body>
</html>