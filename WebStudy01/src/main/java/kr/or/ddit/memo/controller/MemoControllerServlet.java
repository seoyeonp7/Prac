package kr.or.ddit.memo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.memo.dao.DataBaseMemoDAOImpl;
import kr.or.ddit.memo.dao.FileSystemMemoDAOimpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.vo.MemoVO;

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet{
	
//	private MemoDAO dao = FileSystemMemoDAOimpl.getInstance();
	private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청분석
		String accept = req.getHeader("Accept");
		if(accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		// 모델확보
		List<MemoVO> memoList = dao.selectMemoList();
		// 모델공유
		req.setAttribute("memoList", memoList);
		// 뷰 선택
		 String viewName = "/jsonView.do";
		// 뷰로 이동
		 req.getRequestDispatcher(viewName).forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
//		Post-Redirect-Get : PRG pattern 
		MemoVO memo = getMemoFromRequest(req);
		dao.insertMemo(memo);
		resp.sendRedirect(req.getContextPath() + "/memo");
	}
	
	private MemoVO getMemoFromRequest(HttpServletRequest req) throws IOException{
		String contentType = req.getContentType();
		MemoVO memo = null;
		if(contentType.contains("json")) {
			try(
				BufferedReader br = req.getReader(); // body content read용 입력 스트림 (역직렬화)
			){
				memo = new ObjectMapper().readValue(br, MemoVO.class);
			}
		}else if(contentType.contains("xml")){
			try(
				BufferedReader br = req.getReader(); // body content read용 입력 스트림 (역직렬화)
			){
				memo = new ObjectMapper().readValue(br, MemoVO.class);
			}
		}else {
//	     	 확보한 모델을 담을 VO객체 생성
			 memo = new MemoVO();
//		 	 객체에 모델 담기
		     memo.setWriter(req.getParameter("writer"));
		     memo.setDate(req.getParameter("date"));
		     memo.setContent(req.getParameter("content"));
		}
		return memo;
	}
		
		
//      	try(
//      			BufferedReader br = req.getReader();
//      	){
//      		String tmp = null;
//      		StringBuffer strJson = new StringBuffer();
//      		while((tmp=br.readLine())!=null) {
//      			strJson.append(tmp);
//      		}
//      		ObjectMapper mapper = new ObjectMapper();
//      		memo = mapper.readValue(strJson.toString(), MemoVO.class);
//      		
//      	}catch(IOException e) {
//      		
//      	}
//      	return memo;

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
