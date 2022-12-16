<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="1kb" autoFlush="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/bufferDesc.jsp</title>
</head>
<body>
<h4>out : JspWriter</h4>
<pre>
	출력 버퍼 관리자.
	현재 버퍼의 크기 : <%=out.getBufferSize() %>
	잔여 버퍼의 크기 : <%=out.getRemaining() %>
	auto flush 지원 여부 : <%=out.isAutoFlush() %>
	<%
		for(int i=1; i<=100;i++){
			out.println(i+"번째 반복");
			if(out.getRemaining()<50)
				out.flush();
			if(i==100)
// 				throw new RuntimeException("강제 발생 예외");
// 			request.getRequestDispatcher("/02/standard.jsp").forward(request, response);
			//a단계에서 버퍼 방출되어서 foward가 동작하지 못함. foward는 b에서 나가야 함.
			//java.lang.IllegalStateException: 응답이 이미 커밋된 후에는 forward할 수 없습니다.
// 				request.getRequestDispatcher("/02/standard.jsp").include(request, response);
				//include 동작 된다. 문제 아닌것처럼 보이지만 문제되는 상황 생김.
				pageContext.include("/02/standard.jsp",false); //예상할 수 있는 위치
		}
	%>
</pre>
</body>
</html>