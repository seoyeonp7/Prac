package kr.or.ddit.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class LogoutController {
	@RequestMapping(value="/login/logout.do",method=RequestMethod.POST)
	public String logout(HttpSession session) {
		
//		session.removeAttribute("authMemeber");
		//현재 세션 모든 정보 지워주고 + 더이상 사용할 수 없게 만료시킴
		session.invalidate();
		
		return "redirect:/";
	}
}
