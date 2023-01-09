<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h4>Cache 제어</h4>
<pre>
	cache 란? 시스템 내부에서 발생하는 속도 저하를 커버하기 위한 임시 저장 데이터.
	- cache를 제어하기 위한 헤더들 -
	Pragma(1.0), Cache-Control(1.1) : 캐싱 정책 설정용
		no-cache(O) : 일단 저장하고, 캐시 데이터 사용 전 확인 절차를 거치도록 함.
		no-store(X) 
		must-revalidate : 시한이 만료된 캐시 데이터 사용 전 확인 절차를 거치도록 함.
		public : (공용으로 캐싱해라)클라이언트, 프록시 서버에도 캐싱
		private : 클라이언트만 캐싱
		ex) public;maxages=milliseconds
	Expires : 캐싱 데이터 만료 시한 설정용 (구체적인 날짜)
	<%
		//브라우저는 어떤 경우에도 현재 페이지를 캐싱하지 않는다는 뜻
		response.setHeader("pragma", "no-store"); //1.0
		response.setHeader("Cache-Control", "no-store"); //1.1
		response.addHeader("pragma", "no-cache"); //1.0
		response.addHeader("Cache-Control", "no-cache"); //1.1
		response.setDateHeader("Expires", 0); //70-1-1, 0:0:0
	%>
</pre>
</body>
</html>