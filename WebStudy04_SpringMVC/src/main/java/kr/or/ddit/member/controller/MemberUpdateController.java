package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;
import lombok.RequiredArgsConstructor;

@RequestMapping("/member/memberUpdate.do")
@RequiredArgsConstructor
@Controller
public class MemberUpdateController {
	
	private final MemberService service;
	
	@GetMapping
	public String memberUpdate(
//			MemberVOWrapper principal
			@SessionAttribute("authMember") MemberVO authMember //넘어오는 파라미터 있어도 넣어주지 않음
			, Model model
	) {
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		model.addAttribute("member", member);
		
		return "member/memberForm";
	}
	
	@PostMapping
	public String updateProcess(
		@Validated(UpdateGroup.class) @ModelAttribute("member") MemberVO member //해당 이름 속성이 있으면 가져오고 vo 처음부터 만들어서 넣어주는 것 까지
		, BindingResult errors //반드시 검증의 대상이 되는 파라미터 뒤에 넣어야 한다.
		, Model model
		, HttpSession session //꺼낼 떄는 필요x 넣어줄 땐 있어야 한다.
	) throws IOException{
		
		String viewName = null;
		
		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
				case INVALIDPASSWORD:
					model.addAttribute("message", "비밀번호 오류");
					viewName = "member/memberForm";
					break;
				case FAIL:
					model.addAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
					viewName = "member/memberForm";
					break;
		
				default:
					MemberVO modifiedMember = service.retrieveMember(member.getMemId());
					session.setAttribute("authMember", modifiedMember);
					
					viewName = "redirect:/mypage.do";
					break;
			}
			
		} else {
			viewName = "member/memberForm";
		}
		
		return viewName;
	}
}
