<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:if test="${not empty message}">
	<script>
		alert("${message}");
	</script>
	<c:remove var="message" scope="session"/>
</c:if>
</head>
<body>
	<form method="post" action="<c:url value='/login/loginProcess.do'/>">
 		<ul>
			<li>
				<input type="text" name="memId" placeholder="아이디" value="${validId }"/>
				<input type="checkbox" name="saveId" />아이디 기억하기
				<c:remove var="validId" scope="session"/>
			</li>
			<li>
				<input type="password" name="memPass" placeholder="비밀번호" value="${param.memPass }"/>
				<input type="submit" value="로그인" />
			</li>
		</ul>
	</form>
</body>
<script>
</script>
</html>