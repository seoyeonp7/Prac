package kr.or.ddit.memo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.memo.dao.FileSystemMemoDAOImpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.vo.MemoVO;

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet{
	private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet");
		String accept = req.getHeader("Accept");
		if(accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		
		//2.모델확보
		List<MemoVO> memoList = dao.selectMemoList();
		//3.모델공유
		req.setAttribute("memoList", memoList);
		String viewName = "/jsonView.do";
		req.getRequestDispatcher(viewName).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Post-Redirect-Get : PRG pattern
		req.setCharacterEncoding("UTF-8");
		System.out.println("doPost");
		MemoVO memo = getMemoFromRequest(req);
		System.out.println("writer:"+memo.getWriter());
		System.out.println("date:"+memo.getDate());
		
		dao.insertMemo(memo);
		System.out.println("메모 인서트됨");
		resp.sendRedirect(req.getContextPath()+"/memo"); 
	}
	
	private MemoVO getMemoFromRequest(HttpServletRequest req) {
		String writer = req.getParameter("writer");
		String date = req.getParameter("date");
		String content = req.getParameter("content");
		
		MemoVO memo = new MemoVO();
		memo.setWriter(writer);
		memo.setDate(date);
		memo.setContent(content);
		return memo;
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
