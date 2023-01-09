package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.MemberVO;

/**
 * 1. 검증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 2. 인증에 통화하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 	- 비밀번호 오류 상태를 가정하고, 메시지 전달. -> alert 함수로 메시지 출력.
 * 	- 이전에 입력받은 아이디의 상태를 유지함.(클라이언트가 다시 아이디를 입력하지 않아도 되도록)
 * 3. 인증 완료시에 웰컴 페이지로 이동함.
 */
@WebServlet("/login/loginProcess.do")
public class LoginProcessControllerServlet extends HttpServlet{
	private boolean authenticate(MemberVO member) { // 인증 성공과 실패 여부 여기서 확인
		return member.getMemId().equals(member.getMemPass());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		HttpSession session = req.getSession();
		if(session.isNew()) {
			resp.sendError(400, "로그인 폼이 없는데 어떻게 로그인을 하지???");
			return;
		}
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");
		String saveId = req.getParameter("saveId"); //있거나 없거나
		MemberVO member = new MemberVO();
		member.setMemId(memId);
		member.setMemPass(memPass);
		
		boolean valid = validate(member);
		String viewName = null;
		if(valid) {
//		2.
			//검증 통과했음
			if(authenticate(member)){
				Cookie savedIdCookie = new Cookie("saveId", member.getMemId());
//				ex) www[blog].naver.com
				savedIdCookie.setDomain("localhost");
				savedIdCookie.setPath(req.getContextPath());
				int maxAge = 0;
				if(StringUtils.isNotBlank(saveId)){
					maxAge = 60*60*24*5; //5일동안 살아아요~
				}
				savedIdCookie.setMaxAge(maxAge);
				resp.addCookie(savedIdCookie);
				//redirect를 사용하겠다는 약속~!
				session.setAttribute("authMember", member);
				viewName = "redirect:/";
			}else {
				//인증실패~ 비밀번호 오류 났으니 메시지를 전달하고 로그인 폼으로 보내자
				session.setAttribute("validId", memId); //아이디는 맞으니까 초기화 하지 말자~
				session.setAttribute("message", "비밀번호 오류~!");
				viewName = "redirect:/login/loginForm.jsp";
			}
		}else {
			//검증 통과하지 못함! 메시지를 전달하고 로그인 폼으로 보내자
			session.setAttribute("message", "아이디나 비밀번호 누락~!");
			viewName = "redirect:/login/loginForm.jsp";
		}
//		5. 컨트롤러에서 뷰로 이동할때 이 코드 이용하자(약속~!)
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			// 디스패치로 포워딩
			req.getRequestDispatcher(viewName).forward(req, resp);
			
		}
	}

	private boolean validate(MemberVO member) {
		boolean valid = true;
		
		if(StringUtils.isBlank(member.getMemId())) {
			valid = false;
		}
		if(StringUtils.isBlank(member.getMemPass())) {
			valid = false;
		}
		
		return valid;
	}
}
