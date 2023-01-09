<%@ page language="java" contentType="text/html; charset=UTF-8" buffer="7kb"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h4>response(HttpServletResponse)</h4>
<pre>
	Http의 response packaging
	1. Response Line : Status Code(응답상태코드, XXX)
		100~ : ...ING...
		200~ : OK
		300~ : 최종 처리하기 위해 클라이언트의 추가 액션이 필요함.(response body가 없음.) (300번대는 개발자가 설정하는 게 아니라 서버 등에서 자동으로 설정됨)
			304(cache data관련) : Not Modyfied (내가 캐싱해놓은 데이터를 사용함.)(응답데이터를 받고, 브라우저가 동작을 한 번 더 해야함.(캐싱데이터를 뒤지거나, 요청해서 다시 데이터를 받거나))Response Header와 연관되어있음
			301/302/307 : Moved + Location response header와 함께 사용.(상태코드와 세트로 location이라는 header가 함께 있음(새로운 위치를 알려줌(redirect request)))
			<%--
// 				request.getRequestDispatcher("/04/messageView.jsp").forward(request, response); // 서버 내에서 이동
				String location = request.getContextPath() + "/04/messageView.jsp";
				response.sendRedirect(location); // 클라이언트로부터 새로운 요청이 발생.
			--%>
		400~ : client side error -> Fail (클라이언트쪽에서 생기는 문제는 조목조목 알려줘야 클라이언트가 수정해서 다시 보낼 수 있음, 개발자가 만들어줘야 하는 상태코드)
			400 : <%=HttpServletResponse.SC_BAD_REQUEST %> (클라이언트가 전송한 데이터(파라미터) 검증시 활용)
			401 / 403 : 인증(Authentication)(신원을 확인하는 과정)과 인가(Authoization)(이미 신원이 확인된 자에게 어떤 권한이 있는지 확인하는 과정) 기반의 접근 제어에 활용
						401-인증이 안된 유저가 보호가 필요한 자원에 접근했을때, 403-관리자와 일반회원의 권한 차이
				<%=HttpServletResponse.SC_UNAUTHORIZED %>, <%=HttpServletResponse.SC_FORBIDDEN %>
			404 : <%=HttpServletResponse.SC_NOT_FOUND %>
			405 : <%=HttpServletResponse.SC_METHOD_NOT_ALLOWED %>(클라이언트의 현재 요청 메소드에 대한 콜백 메소드가 재정의되지 않았을 때 발생)
			406 / 415 : content-type(MIME)과 관련된 상태코드
				<%=HttpServletResponse.SC_NOT_ACCEPTABLE %>(서버가 클라이언트가 요청한 언어로 만들 수 없을 때 발생(response body를 못만들어)) : Accept request 헤더에 설정된 MIME 데이터를 만들어낼 수 없을 때.
					ex)accept:application/json
					   content-type : application/json(XXX)
				<%=HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE %>(클라이언트가 요청한 언어를 해석할 수 없을 때 발생(request body를 해석할 수 없어)) : Contetn-Type request 헤더를 해석할 수 없을 때.
					ex)content-type : application/json --> unmarshalling(XXX)
		500~ : server side error -> Fail, 500(Internal Server Error)
		
	2. Response Header : meta data
		Content에 대한 부가정보 설정 : Content-*, Content-Type(MIME), Content-Length(size)
						Content-Disposition(content name, 첨부여부)
			<%--
				response.setHeader("Content-Disposition", "inline[attatchement];filename=\"파일명\"");
			--%>
		Cache control : 자원에 대한 캐싱 설정
		Auto Request : 주기적으로 갱신되는 자원에 대한 자동 요청
		Location 기반의 이동구조(Redirection).
	3. Response Body(Content Body, message body) :
<%-- 		response.getWriter(), response.getOutputStream(),<%= %>, out --%>
</pre>
</body>
</html>