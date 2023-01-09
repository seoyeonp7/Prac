package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 * Backend controller(command handler) --> POJO(Plain Old Java Object) : 제약x
 */
@Controller
public class MemberInsertController{
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	
	@RequestMapping("/member/memberInsert.do")
	public String memberForm() {
		return "/member/memberForm";
	}
	
	@RequestMapping(value="/member/memberInsert.do",method=RequestMethod.POST)
	public String memberInsert(
		HttpServletRequest req
		, @ModelAttribute("member") MemberVO member
		, @RequestPart(value="memImage",required=false) MultipartFile memImage
	) throws ServletException, IOException {
			member.setMemImage(memImage);
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		String viewName = null;
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
				case PKDUPLICATED:
					req.setAttribute("message", "아이디 중복");
					viewName = "member/memberForm";
					break;
				case FAIL:
					req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
					viewName = "member/memberForm";
					break;
		
				default:
					viewName = "redirect:/";
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
