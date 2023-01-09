package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberUpdateController {
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateController.class);
	
	@RequestMapping("/member/memberUpdate.do")
	public String memberUpdate(
			HttpSession session
//			, @SessionAttribute("authMember") MemberVO authMember
			,HttpServletRequest req
	) {
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		
		return "member/memberForm";
	}
	
	@RequestMapping(value="/member/memberUpdate.do", method=RequestMethod.POST)
	public String updateProcess(
		@ModelAttribute("member") MemberVO member
		, HttpServletRequest req
		, @RequestPart(value="memImage",required=false) MultipartFile memImage
		, HttpSession session
	) throws IOException{
		
		String viewName = null;
		
		member.setMemImage(memImage);

		//검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, UpdateGroup.class);
		
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
				case PKDUPLICATED:
					req.setAttribute("message", "비밀번호 오류");
					viewName = "member/memberForm";
					break;
				case FAIL:
					req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
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
			errors.forEach((k,v)->{
				log.error("{} : {}", k, v);				
			});
		}
		
		return viewName;
	}
}
