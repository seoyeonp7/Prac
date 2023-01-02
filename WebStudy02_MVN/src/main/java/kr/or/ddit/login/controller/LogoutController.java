package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.AbstractController;

public class LogoutController implements AbstractController {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		session.removeAttribute("authMemeber");
		//현재 세션 모든 정보 지워주고 + 더이상 사용할 수 없게 만료시킴
		session.invalidate();
		
		return "redirect:/";
	}
}
