package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.memo.controller.MemoController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;
import oracle.jdbc.proxy.annotation.Methods;

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
				case INVALIDPASSWORD:
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
