package kr.or.ddit.servlet04;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.servlet01.DescriptionServlet;
import kr.or.ddit.servlet04.serviece.PropertiesServiceImpl;
import kr.or.ddit.servlet04.serviece.PropertiesServiece;

@WebServlet("/03/props/propsView.do")
public class PropertiesControllerServlet extends HttpServlet{
	private PropertiesServiece service = new PropertiesServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accept = req.getHeader("Accept"); //1.요청 분석
		
		Object target = service.retrieveData(); //2.모델 확보
		req.setAttribute("target", target); //3.모델 공유
		
		String path = null;
		
		//4.뷰를 선택
		if(accept.startsWith("*/*") || accept.toLowerCase().contains("html")) {
			path = "/WEB-INF/views/03/propsView.jsp";
		} else if(accept.toLowerCase().contains("json")) {
			path = "/jsonView.do";
		} else if(accept.toLowerCase().contains("xml")) {
			path = "/xmlView.do";
		} 
		req.getRequestDispatcher(path).forward(req, resp); //5.뷰로 이동
	}
}
