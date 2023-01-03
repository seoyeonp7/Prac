package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.commons.IndexController;
import kr.or.ddit.login.controller.LoginProcessController;
import kr.or.ddit.login.controller.LogoutController;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.member.controller.MemberListController;
import kr.or.ddit.member.controller.MemberViewController;
import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerAdapter;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.mvc.view.ViewResolver;
import kr.or.ddit.prod.controller.ProdInsertConstroller;
import kr.or.ddit.prod.controller.ProdListController;

public class DispatcherServlet extends HttpServlet{
	private ViewResolver viewResolver;
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		viewResolver = new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
		handlerMapping = new RequestMappingHandlerMapping("kr.or.ddit");
		handlerAdapter = new RequestMappingHandlerAdapter();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.service(req, resp); 
//		이 코드 지우면 현재 서블릿에서는 do계열 메소드를 사용하지 않겠다는 뜻(실제 데이터 처리할 것 아니므로)
		
		req.setCharacterEncoding("UTF-8");
		
//		String requestURI = req.getRequestURI(); //contextPath포함
//		requestURI = requestURI.substring(req.getContextPath().length());
		String requestURI = req.getServletPath();
		
		RequestMappingInfo mappingInfo = handlerMapping.findCommandHandler(req);
		
//		AbstractController controller = null;
		
		if(mappingInfo==null) {
			resp.sendError(404,requestURI+" 는 처리할 수 없는 자원임(Not found).");
			return;
		}
		
		
//		String viewName = controller.process(req, resp);
		String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp);
		
		if(viewName==null) {
			if(!resp.isCommitted())
				resp.sendError(500,"논리적인 뷰 네임은 null일 수 없음.");
		}else {
			viewResolver.resolveView(viewName, req, resp);
		}
	}
}
