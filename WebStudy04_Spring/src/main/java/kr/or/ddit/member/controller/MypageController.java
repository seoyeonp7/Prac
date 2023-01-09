package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

@Controller
public class MypageController {
	
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/mypage.do")
	public String myPage(
		HttpServletRequest req
		, MemberVOWrapper principal
	) throws ServletException, IOException {
		MemberVO authMember = principal.getRealMember();
		
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		
		return "member/memberView"; //logical view name
	}
}
