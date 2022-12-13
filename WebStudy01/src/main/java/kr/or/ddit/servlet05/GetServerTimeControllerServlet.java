package kr.or.ddit.servlet05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

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
		//1.요청 분석 => 요청의 조건 : 헤더(Accept)와 파라미터(locale)
		String localeParam = req.getParameter("locale");
		String accept = req.getHeader("Accept");
		
		//파라미터 locale에 따라, locale 객체 변경.
		Locale clientLocale= req.getLocale();
		if(localeParam!=null && !localeParam.isEmpty()) {
			clientLocale = Locale.forLanguageTag(localeParam);
		}
	    //2.
		Date date = new Date();
	    String nowStr = String.format(clientLocale,"date : %tc",date);
	    
	    //3.
		req.setAttribute("date", nowStr);
		req.setAttribute("message", nowStr);
		resp.setHeader("Refresh", "1");
		
		//4.
		//header의 accept에 따라, viewName 변경.
		String viewName = null;
		int statusCode = HttpServletResponse.SC_OK;
		if(accept.contains("json")) {
			viewName = "/jsonView.do";
		} else if(accept.contains("plain")) {
			viewName = "/WEB-INF/views/04/plainView.jsp";
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,accept+" mime은 생성할 수 없음.");
		}
	    
		if(viewName==null && !resp.isCommitted()) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"뷰네임은 널일 수 없음.");
		}else {
			
		}
	    //5.  
		req.getRequestDispatcher(viewName).forward(req, resp);
	}
}
