<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>05/flowControl.jsp</title>
<%-- <jsp:include page="/includee/preScript.jsp" /> --%>
<%@ include file="/includee/preScript.jsp"%>
<%=varOnPre %>
</head>
<body>
<h4>흐름 제어 방법</h4>
<pre>
	Http : Connectless, Stateless
	A -> B 이동 방식
	
	1. 요청 분기(request dispatch) : A를 대상으로 한 최초의 요청이 계속 유지됨.
		1) forward(Model2) : A(request 처리, controller)-> B(response 생성, view)에서 이동 후의 최종 응답은 B에서 전송.
		2) include(페이지 모듈화) : A -> B -> A 최종 응답에 A+B의 모든 데이터가 포함됨.
			내포되는 시점과 내포되는 대상에 따라 분류됨.
			- 정적 내포 : 컴파일 전에 소스가 파싱되는 단계에서 소스파일이 내포됨.
			- 동적 내포 : 실행시에 실행의 결과 데이터가 내포됨.
		<%
			request.setAttribute("attr1", new Date());
			String path = "/02/standard.jsp";
// 			request.getRequestDispatcher(path).forward(request, response);
//  		request.getRequestDispatcher(path).include(request, response);
			pageContext.include(path);
		%>
	2. Redirect : (도착지 정보만 넣는다는 차이점이 있음) (클라이언트는 b의 존재를 알아, 1번의 요청의 정보는 다 사라지고 없다는 특징이 있음)
		A -> response body가 없고, Line(302) + Header(Location)로만 구성된 응답이 전송
		  -> Location 방향으로 새로운 요청을 전송함
		  -> B에서 Body를 가진 최종 응답이 전송됨.
		<%--
			session.setAttribute("attr2", "세션 속성");
			String location = request.getContextPath() + path;
			response.sendRedirect(location);
		--%>
</pre>
</body>
</html>