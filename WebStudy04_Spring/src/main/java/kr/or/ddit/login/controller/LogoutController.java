package kr.or.ddit.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
