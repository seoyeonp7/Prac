<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%!
	public String Sample = "SAMPLE";
%>
<!DOCTYPE html>
<html>
<head>
<title>10/pageContextDesc.jsp</title>
</head>
<body>
<h4>pageContext(PageContext)</h4>
<pre>
<!-- CAC(Context Aware Computing) -->
	: 하나의 JSP 페이지와 관련된 모든 정보(기본 객체)를 가진 객체
	
	1. EL에서 주로 기본 객체를 확보할때 사용.
	   ex) <%=request.getContextPath() %>, 
	       <%=((HttpServletRequest)pageContext.getRequest()).getContextPath() %>
	       ${pageContext.request.contextPath }
	2. 에러 데이터 확보
	3. 흐름 제어(요청 분기) : forward/include
	4. 영역 제어(*****)

</pre>
<h4>Scope</h4>
<pre>
	Servlet[JSP] Container : 서블릿 객체나 JSP 객체의 모든 관리 권한을 가진 주체(IoC-Inversion Of Control). (IOC=모든 프로그램의 가장 밑바닥에 깔려있는 개념)
	
	Scope : 웹 어플리케이션에서 데이터를 공유하기 위해 사용되는 저장 공간(Map&lt;String,Object&gt;-하나가 아니고 네개가 존재).
	Attribute : scope 를 통해 공유되는 데이터(String name/Object value).
	
	: Scope라는 저장 곤간을 소유한 기본 객체의 생명주기와 동일함.
	page scope : pageContext의 소유 공간, 현재 페이지에서만 공유 가능 영역.
	request scope : 해당 영역의 소유 요청 객체가 소멸될때 함께 소멸됨.
	session scope : 해당 영역을 소유한 세션 객체와 생명주기 동일.
	application scope : ServletContext 와 동일한 생명주기를 가짐.
	
	setAttrebute(name, value), getAttribute(name), removeAttribute(name) json의 토큰구조를 사용해서 세션이라는 구조를 사용 못하게함 - 서버가 무거워지기 때문에~  removeAttribute(name)가 가장 중요하다고 볼 수 있음
	
 <%
      pageContext.setAttribute("pageAttr", "페이지 속성");
      request.setAttribute("requestAttr", "요청 속성");
      session.setAttribute("sessionAttr", "세션 속성");
      application.setAttribute("applicationAttr", "어플리케이션 속성");
      
      pageContext.setAttribute("sample", "페이지샘플");
      pageContext.setAttribute("sample", "요청샘플", PageContext.REQUEST_SCOPE);
      
//       1. forward -> page scope는 나오지 않음
//       2. include -> page scope는 나오지 않음
      String viewName = "/09/attrView.jsp";
//       request.getRequestDispatcher(viewName).include(request, response);
//       3. redirect -> page scope와 request scope는 나오지 않음
      String location = request.getContextPath() + viewName; // body가 없어도 어차피 응답이 나간 것이니 새로운 request가 생겨서 두 개밖에 안 나옴
//       response.sendRedirect(location);
         
   %>

</pre>
<h4> 공유된 속성 데이터들 </h4>
<pre>
	sample : ${requestScope.sample }
	 
	page scope : <%=pageContext.getAttribute("pageAttr") %>
	request scope : <%=request.getAttribute("requestAttr") %>
	session scope : <%=session.getAttribute("sessionAttr") %>
	<%
		// flash attribute
		session.removeAttribute("sessionAttr");
	%> 
	application scope : <%=application.getAttribute("applicationAttr") %>, ${applicationAttr } (EL은 범위가 좁은 녀석들부터 뒤진다.)
</pre>
</body>
</html>