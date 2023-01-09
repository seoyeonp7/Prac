package kr.or.ddit.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberViewController {
	MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberView.do")
	public String memberView(
		@RequestParam(value="who",required=true) String memId
		, HttpServletRequest req
	) {
//		1.
//		String memId = req.getParameter("who");
//		if(StringUtils.isBlank(memId)) {
//			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return null;
//		}
//		2.
		MemberVO member = service.retrieveMember(memId);
		System.out.println(member);
//		3.
		req.setAttribute("member", member);
//		4.
		String viewName = "member/memberView";
//		5.
		return viewName;
	}
}
