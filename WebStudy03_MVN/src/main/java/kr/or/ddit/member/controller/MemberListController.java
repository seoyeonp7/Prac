package kr.or.ddit.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberListController { //완전한 POJO
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);

	@RequestMapping("/member/memberList.do")
	public String memberList(
		@RequestParam(value="page",required=false, defaultValue="1") int currentPage
		, @ModelAttribute SearchVO simpleCondition //COC -> 이름 안 주면 searchVO로 
		, HttpServletRequest req
	) {
		
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		req.setAttribute("pagingVO", pagingVO);
		
		String viewName = "member/memberList";
		return viewName;
				
	}
}
