<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jstlDesc.jsp</title>
</head>
<body>
<h4>JSTL(Jsp Standard Tag Library)</h4>
<pre>
	: 커스텀 태그들 중에서 많이 활용될 수 있는 것들을 모아놓은 라이브러리.
	&lt;prefix:tagname attr_name="attr_value"&gt;
	1. 커스텀 태그 로딩 : taglib directive
	2. prefix 를 통한 태그 접근
	
	** core 태그 종류
	1. EL 변수(속성)와 관련된 태그 : set, remove
		<c:set var="sample" value="샘플값" scope="session"/>
		${sample }, ${sessionScope.sample }
		<c:remove var="sample" scope="session"/> <!-- 싹 다 지우므로 scope 명시해주는 것이 좋음 -->
		--> ${sample }
	2. 조건문 : if(조건식){블럭문}else{}, switch~case(조건값)...default
		단일조건문 : if (else X)
		<c:if test="${empty param.name1 }">
			파라미터 없음.
		</c:if>
		<c:if test="${not empty param.name1 }">
			파라미터 있음.
		</c:if>
		다중조건문 : choose ~ when ~ otherwise, 더 직관적이고 안전
		<c:choose>
			<c:when test="${empty param.name1 }">
				파라미터 없음
			</c:when>
			<c:otherwise>
				파라미터 있음.
			</c:otherwise>
		</c:choose>
		
	3. 반복문 : foreach, forTokens , for(선언절, 조건절, 증강절) for( 임시 블럭 변수 : 반복대상 집합객체 )
		<c:set var="array" value='<%=new String[]{"value1","value2"} %>'/>
		<c:forEach var="i" begin="0" end="${fn:length(array)-1 }" step="1" varStatus="vs">
			${array[i] } --> 현재 반복의 상태(LoopTagStatus) : ${vs.index }, ${vs.count },${vs.first },${vs.last }
		</c:forEach>
		<c:forEach items="${array }" var="element">
			${element }
		</c:forEach>
		
		<c:forTokens items="아버지 가방에 들어가신다" delims=" " var="token">
			${token }
		</c:forTokens>
		<c:forTokens items="1,2,3,4" delims="," var="token">
			${token * 10}
		</c:forTokens>
		
	4. 기타
		url 재작성 : url(client side path, session parameter), redirect
			<c:url value="/06/memoView.jsp" />
			<a href='<c:url value='/10/jstlDesc.jsp'/>'>세션 유지</a>
<%-- 			<c:redirect url="/06/memoView.jsp"/> --%><!-- 서버사이드 코드 -->
			<%--
				String location = request.getContextPath()+"/06/memoView.jsp";
				response.sendRedirect(location);
			--%>
			
			url : 출력만 하는 용도 아님, 클라이언트 사이드의 절대경로 완성시켜줌, 
				(트래킹모드 없앴을 때) 쿠키 없어도 세션 유지될 수 있도록 세션 파라미터 만들어줌
				가독성굿
				
		표현구조 : out
<%-- 			<jsp:include page=""></jsp:include> 동적 내포에 사용, a에 b 끌고 오기, 같은 어플리케이션 안, 동일한 컨텍스트 안에 있을 경우만 --%>
<%-- 			<c:import url="https://www.naver.com" />동적 내포에 사용, 컨텍스트에 제한이 없다. --%>
			<c:out value="<h4>출력값</h4>" escapeXml="false"></c:out> <!-- 기본값 true -->
			자동으로 태깅 기호를 escape 시켜줌
		
</pre>
<c:import url="https://www.naver.com" var="naver"/>
<c:out value="${naver }" escapeXml="true"></c:out>
</body>
</html>