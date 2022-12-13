<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/responseDesc.jsp</title>
</head>
<body>
<h4>response(HttpServletResponse)</h4>
<pre>
	Http의 response packaging
	1.Response Line : Status Code(응답상태코드, XXX)
		100~ : ...ING...
		200~ : OK
		300~ : 최종 처리하기 위해 클라이언트의 추가 액션이 필요함. 성공도 아니고 실패도 아니야. response body가 없음.
			304(cache data관련) : Not Modified 
			내 캐싱데이터가 최신버전인지(변경된 정보 없는지) -> 캐시 영역 뒤져서 원래꺼 쓰던가 요청해서 다시 받은 캐시 쓰던가
			301/302/307 : Moved + Location response header와 함께 사용.(redirect request) 응답 받고 상대가 무언가 해야한다.
			<% 
// 				request.getRequestDispatcher("/04/messageView.jsp").forward(request,response); //서버사이드 주소, 서버 내에서 이동
				
// 				String location =request.getContextPath() + "/04/messageView.jsp"; //클라이언트사이드 주소
// 				response.sendRedirect(location);// 클라이언트로부터 새로운 요청이 발생. 클라이언트가 명확하게 이동된 요청 알게 된다.
			%>
		400~ : client side error -> Fail
			개발자가 만들어줘야 하는 상태코드
			400 : <%=HttpServletResponse.SC_BAD_REQUEST %>, 클라이언트 전송 데이터(파라미터) 검증시 활용.
				  필수파라미터가 안 넘어왔을 때,사칙연산자의 피연산자가 알파벳인 경우,유효성 검사 맞지 않을 때 등.
			401 / 403 : 인증(Authentication,신원 확인)과 인가(Authorization,확인 거친 사람에게 어떤 자원에 대한 권한 확인) 기반의 접근 제어에 활용
				<%=HttpServletResponse.SC_UNAUTHORIZED %>, <%=HttpServletResponse.SC_FORBIDDEN %>
				401은 인증안된 유저가 보호 필요한 자원에 접근하려 할 때->브라우저가 인증시스템 동작 시킴, 403는 관리자와 일반회원의 권한 차이
			404 : <%=HttpServletResponse.SC_NOT_FOUND %> 편지의 수신자정보(URL,Key에 해당하는 메시지가 없는 경우 등)가 잘못된 경우
			405 : <%=HttpServletResponse.SC_METHOD_NOT_ALLOWED %> get,post 등의 현재 요청에 대한 콜백 메소드가 재정의 되지 않은 경우
			406 / 415 : content-type(MIME)과 관련된 상태코드
				<%=HttpServletResponse.SC_NOT_ACCEPTABLE %> Accept request 헤더에 설정된 MIME데이터를 만들어낼 수 없을 때.
				ex) accept : application/json
					content-type : application/json (XXX)
				<%=HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE %> : Content-Type request 헤더를 해석할 수 없을 때.
				ex) content-type : application/json --> unmarshalling(XXX)
				entity of the request(리퀘스트 바디) 해석할 수 없음
		500~ : server side error -> Fail, 500(Internal Server Error) 
			서버 정보는 오류가 났더라도 정확한 정보 안준다. 좋은 클라이언트인지 모르기 때문에, 500으로 퉁쳐
		
	2.Response Header : meta data
		Content(Body)에 대한 부가정보 설정 : Content-*, Content-Type(MIME), Content-Length(size),
									Content-Disposition(content name, 첨부여부)
		Auto Request : 주기적으로 갱신되는 자원에 대한 자동 요청	
		<%
			response.setHeader("Content-Disposition", "inline[attatchement];filename=\"파일명\"");
			
		%>
		Cache control : 자원에 대한 캐싱 설정 
		Location 기반의 이동 구조.(Redirectioin).
	
	3.Response Body(Content body, message body) : 
<%-- 		response.getWriter(), response.getOutputStream(),<%= %> ,out --%>
		
</pre>
</body>
</html>