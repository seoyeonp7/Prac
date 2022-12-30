package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctc.wstx.util.StringUtil;

import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/member/memberList.do")
public class MemberListControllerServlet extends HttpServlet {
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String pageParam = req.getParameter("page");
		
		//검증 대상 아님, 마이바티스까지 전달
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		
		SearchVO simpleCondition = new SearchVO(searchType,searchWord);
		
		int currentPage = 1;
		if(StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
		
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		req.setAttribute("pagingVO", pagingVO);
		
		log.info("paging data : {}",pagingVO);
		String viewName = "member/memberList";
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
	}
}
