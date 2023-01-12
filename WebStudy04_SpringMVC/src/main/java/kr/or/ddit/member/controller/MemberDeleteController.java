package kr.or.ddit.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberDeleteController {
	@Inject
	private MemberService service;
	
	@RequestMapping("/member/memberDelete.do")
	public String memberDelete(
		@RequestParam(value="memPass", required=true) String memPass //없으면 400에러
		, @SessionAttribute(value="authMember",required=true) MemberVO authMember
		, HttpSession session
		, RedirectAttributes redirectAttributes
	) {
		//두 가지 검증 handler adaper가 수행
		//아이디 비번 인증 -> dispatch요청구조 사용하지 않는다.
		String memId = authMember.getMemId();

		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		String viewName = null;
		
		ServiceResult result = service.removeMember(inputData);
		switch (result) {
			case INVALIDPASSWORD:
				redirectAttributes.addFlashAttribute("message", "비번 오류");
				//인증시스템에서는 dispatch 사용하지 않는다.
				viewName = "redirect:/mypage.do";
				break;
			case FAIL:
				redirectAttributes.addFlashAttribute("message", "서버 오류. 쫌따 다시 하셈.");
				viewName = "redirect:/mypage.do";
				break;
			default:
				session.invalidate();
				viewName = "redirect:/";
				break;
		
		}
		return viewName;
	}
}
