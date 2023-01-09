package kr.or.ddit.servlet05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/05/getServerTime")
public class GetServerTimeControllerServlet extends HttpServlet {
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청의 조건: 헤더(Accept)와 파라미터(locale) / 요청분석(line, header, body)
	  String localeParam = req.getParameter("locale");
	  String accept = req.getHeader("Accept");
//	    파라미터 locale에 따라, 로케일 객체 변경.
	  Locale clientLocale = req.getLocale();
	  if(localeParam!=null && !localeParam.isEmpty()) {
		  clientLocale = Locale.forLanguageTag(localeParam);
	  }
//		2. 모델 확보
      Date now = new Date();
      String nowStr = String.format(clientLocale, "now : %tc",now);
//		3. 모델 공유(setAttribute)
      req.setAttribute("now", nowStr);
      req.setAttribute("message", nowStr);
      resp.setHeader("Refresh", "1");
//		4. 뷰 선택
//	    헤더 accept에 따라, viewName 변경.
      String viewName = null;
      if(accept.contains("json")) {
    	  viewName = "/jsonView.do";
      }else if(accept.contains("plain")) {
    	  viewName = "/WEB-INF/views/04/plainView.jsp";
      }else {
    	  resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, accept + " mime 은 생성할 수 없음.");
      }
      if(viewName==null && !resp.isCommitted()) {
    	  resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "view네임은 null일 수 없음.");
      }else {
    	  
//		5. 뷰로 이동
    	  req.getRequestDispatcher(viewName).forward(req, resp);
    	  
      }
   }
}