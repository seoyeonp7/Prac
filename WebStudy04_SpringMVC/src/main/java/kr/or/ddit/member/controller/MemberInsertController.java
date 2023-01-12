package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Backend controller(command handler) --> POJO(Plain Old Java Object) : 제약x
 */
@Slf4j
@RequestMapping("/member/memberInsert.do")
@Controller
public class MemberInsertController{
	@Inject
	private MemberService service;
	
	//핸들러메소드 호출 전에 먼저 호출됨, 객체는 재활용된다.
	@ModelAttribute("member")
	public MemberVO member() {
		log.info("@ModelAttribute 메소드 실행 -> member 속성 생성");
		return new MemberVO();
	}
	
	@GetMapping
	public String memberForm() {
		return "/member/memberForm";
	}
	
	@PostMapping
	public String memberInsert(
		HttpServletRequest req
		, @Validated(InsertGroup.class) @ModelAttribute("member") MemberVO member
		//새로 만들지 않고 요청파라미터만 바인딩 해서 넘겨줌. 위에서 만들어진 MemberVO가 재활용 된다.
		, Errors errors
	) throws ServletException, IOException {
//		
		boolean valid = !errors.hasErrors();
		//MemberVO, Errors : req안에 들어있는 상태(memberVO,eoors로)
		
		String viewName = null;
		
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
		}
		
		return viewName;
	}
}
