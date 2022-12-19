package kr.or.ddit.servlet06;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/bts/*",loadOnStartup=2)
public class BTSMemberServlet extends HttpServlet {
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = config.getServletContext();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		String code = Optional.of(requestURI)
								.map(uri->uri.substring(req.getContextPath().length()))
								.map(uri->uri.substring("/bts/".length()))
								.get();
		Map<String,String[]> members = (Map) application.getAttribute("btsMembers");
		//조건1.bts서블릿이 먼저 객체 생성해야 한다. multi value annotation 으로 loadOnStartup 설정
		//2.싱글톤. ServletContext는 어플리케이션 전체 통틀어서 하나
		String[] contents = members.get(code);
		
		//요청에 대한 검증 코드
		if(contents==null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String contentPage = contents[1];
		req.setAttribute("contentPage", contentPage);
		req.getRequestDispatcher("/WEB-INF/views/bts/btsLayout.jsp").forward(req,resp);
	}
}
