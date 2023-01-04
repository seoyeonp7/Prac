package kr.or.ddit.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberDeleteController {
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateController.class);
	
	@RequestMapping("/member/memberDelete.do")
	public String memberDelete(@RequestParam("memPass") String memPass,HttpSession session) {
		//아이디 비번 인증 -> dispatch요청구조 사용하지 않는다.
		
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		String memId = authMember.getMemId();

		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		boolean valid = ValidationUtils.validate(inputData, errors, DeleteGroup.class);
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.removeMember(inputData);
			switch (result) {
				case INVALIDPASSWORD:
					session.setAttribute("message", "비번 오류");
					//인증시스템에서는 dispatch 사용하지 않는다.
					viewName = "redirect:/mypage.do";
					break;
				case FAIL:
					session.setAttribute("message", "서버 오류. 쫌따 다시 하셈.");
					viewName = "redirect:/mypage.do";
					break;
				default:
					session.invalidate();
					viewName = "redirect:/";
					break;
			}
		} else {
			session.setAttribute("message", "아이디나 비밀번호 누락");
			viewName = "redirect:/mypage.do";
			
		}
		
		return viewName;
	}
}
