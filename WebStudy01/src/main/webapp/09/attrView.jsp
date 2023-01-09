<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h4> 공유된 속성 데이터들 </h4>
<pre>
	page scope : <%=pageContext.getAttribute("pageAttr") %>
	request scope : <%=request.getAttribute("requestAttr") %>
	session scope : <%=session.getAttribute("sessionAttr") %>
	<%
		// flash attribute
		session.removeAttribute("sessionAttr");
	%>
	application scope : <%=application.getAttribute("applicationAttr") %>, ${applicationAttr }
</pre>
</body>
</html>