<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<h4><%=request.getHeader("User-Agent") %>${header['user-agent'] }</h4>
<h4>jQuery와 BootStrap 사용 가능한 페이지</h4>
<jsp:include page="${contentPage }" />
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>