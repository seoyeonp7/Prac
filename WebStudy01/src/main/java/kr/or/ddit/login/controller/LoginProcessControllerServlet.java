package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.MemberVO;

/**
 * 1. 검증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 2. 인증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 	- 비밀번호 오류 상태를 가정하고, 메시지 전달. -> alert 함수로 메시지 출력.
 * 	- 이전에 입력받은 아이디의 상태를 유지함.
 * 3. 인증 완료시에 웰컴 페이지로 이동함.
 */
@WebServlet("/login/loginProcess.do")
public class LoginProcessControllerServlet extends HttpServlet{
	
	private boolean authenticate(MemberVO member) {
		return member.getMemId().equals(member.getMemPass());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		HttpSession session = req.getSession();
		//검증
		//최초의 요청(form태그 안 탔으므로 세션 하이재킹에 의한 요청일 가능성 높다)
		if(session.isNew()) {
			resp.sendError(400,"로그인 폼이 없는데 어떻게 로그인을 하지???");
			return;
		}
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");
		
		MemberVO member = new MemberVO();
		member.setMemId(memId);
		member.setMemPass(memPass);
		
		boolean valid = validate(member); //검증
		String viewName = null;
		
		if(valid) {
			//2. 인증
			if(authenticate(member)) {
				//4.
				session.setAttribute("authMember", member);
				viewName = "redirect:/";
				
			} else {
				session.setAttribute("validId", memId);
				session.setAttribute("message", "비밀번호 오류");
				viewName = "redirect:/login/loginForm.jsp";
			}
		} else {
			session.setAttribute("message", "아이디나 비밀번호 누락");
			viewName = "redirect:/login/loginForm.jsp";
		}
		//5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath()+viewName);
		} else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}

	private boolean validate(MemberVO member) {
		//검증
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
