package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberDelete.do")
public class MemberDeleteControllerServlet extends HttpServlet{
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateControllerServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//아이디 비번 인증 -> dispatch요청구조 사용하지 않는다.
//		1.
		req.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		String memId = authMember.getMemId();
		String memPass = req.getParameter("memPass");

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
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
		
	}
}
