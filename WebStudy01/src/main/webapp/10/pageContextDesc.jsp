<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public String sample = "SAMPLE";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/pageContextDesc.jsple here</title>
</head>
<body>
<h4>pageContext(PageContext)</h4>
<pre>
	: 하나의 JSP 페이지와 관련된 모든 정보(기본 객체)를 가진 객체
	1. EL에서 주로 기본 객체를 확보할 때 사용.
		ex) <%=request.getContextPath() %>,
			<%=((HttpServletRequest)pageContext.getRequest()).getContextPath()%>
			${pageContext.request.contextPath}
	2. 에러 데이터 확보
	3. 흐름 제어(요청 분기) : forward/include
	4. 영역 제어(*****)
		
	
	
CAC(Context Aware Computing) 상황인식컴퓨터이론
기본객체 9가지 중 본인 제외한 8개가 다 들어있다.
1.제일 먼저 생성되는 기본객체
2.나머지 기본객체를 레퍼런스로 소유한다.

</pre>
<h4>Scope</h4>
<pre>
	Servlet[JSP] Container : 서블릿 객체나 JSP 객체의 모든 관리 권한을 가진 주체.(IoC-Inversion of Control).
							(IoC : 제어의 역전구조. 제어권이 개발자에게 있는 것이 아니고 컨테이너의 서블릿에 넘어간 구조)
	Scope : 웹 어플리케이션에서 데이터를 공유하기 위해 사용되는 저장 공간.(Map&lt;String,Object&gt;)
	Attribute :  scope를 통해 공유되는 데이터.(String name/Object value).
	
	: Scope라는 저장 공간을 소유한 기본 객체의 생명주기와 동일함.(scope = 자료구조 = 저장공간)
	page scope : pageContext의 소유 공간. 현재 페이지에서만 공유 가능 영역
	request scope : 해당 영역의 소유 요청 객체가 소멸될 때 함께 소멸됨.
	session scope : 해당 영역을 소유한 세션 객체와 생명주기 동일. (한 사람의 정보 관리)
	application scope : ServletContext와 동일한 생명주기를 가짐.
	
	setAttribute(name,value), getAttribute(name), removeAttribute(name)
	
	<%
		pageContext.setAttribute("pageAttr", "페이지 속성");
		request.setAttribute("requestAttr", "페이지 속성");
		session.setAttribute("sessionAttr", "세션 속성");
		application.setAttribute("applicationAttr", "어플리케이션 속성");
		
		pageContext.setAttribute("sample","페이지샘플");
		pageContext.setAttribute("sample","요청샘플", PageContext.REQUEST_SCOPE);
// 		1. forward
// 		2. include
		String viewName ="/09/attrView.jsp";
// 		request.getRequestDispatcher(viewName).forward(request, response);
// 		request.getRequestDispatcher(viewName).include(request, response);
// 		3.redirect
		String location = request.getContextPath() + viewName;
// 		response.sendRedirect(location);
	%>


	1. foward 요청을 가지고 분기하는 방식이므로 request데이터도 같이 넘어감
		(CLIENT -> A -> B ->CLIENT) : 클라이언트는 A가 준걸로 보임
	2. include도 page scope 빼고 다 넘어감.
	3. 
	
	세션 넣었으면 사용 후 반드시 빼주어야 한다 서버 부하 걸리기 때문에
	요즘은 세션 안쓰고 제이슨 기반의 토큰 인증구조를 사용
	
	removeAttribute 중요
	
	전역변수
	외부에서도 접근 가능, 데이터 공유할 목적
	인스턴스변수 정책변수(static?)
	지역변수에 엑세스 모디파이어를 쓸 수 없음.. 접근범위 제어할 필요 없어
	page scope,, 커스텀 태그 쓸 때 필요하다

</pre>
<h4> 공유된 속성 데이터들 </h4>
<pre>
	sample : ${requestScope.sample }
		
	page scope : <%=pageContext.getAttribute("pageAttr") %> , ${pageAttr }
	request scope : <%=request.getAttribute("requestAttr") %>, ${requestAttr }
	session scope : <%=session.getAttribute("sessionAttr") %>, ${sessionAttr }
	<%
		// flash attribute방식
		session.removeAttribute("sessionAttr");
	%>
	application scope : <%=application.getAttribute("applicationAttr") %>, ${applicationAttr }
</pre>
</body>
</html>