package kr.or.ddit.servlet06;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/bts",loadOnStartup=1)
public class BTSServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = config.getServletContext(); //톰캣 정보 가져올수있다.
		application.setAttribute("btsMembers", getBtsMemberList());
	}
	
	public Map<String, String[]> getBtsMemberList() {
		Map<String, String[]> members = new LinkedHashMap<>();
		int idx =1;
		members.put("B00"+idx++,new String[] {"RM","/WEB-INF/views/bts/rm.jsp"});
		members.put("B00"+idx++,new String[] {"뷔","/WEB-INF/views/bts/bui.jsp"});
		members.put("B00"+idx++,new String[] {"제이홉","/WEB-INF/views/bts/jhop.jsp"});
		members.put("B00"+idx++,new String[] {"진","/WEB-INF/views/bts/jin.jsp"});
		members.put("B00"+idx++,new String[] {"슈가","/WEB-INF/views/bts/suga.jsp"});
		members.put("B00"+idx++,new String[] {"정국","/WEB-INF/views/bts/jungkuk.jsp"});
		members.put("B00"+idx++,new String[] {"지민","/WEB-INF/views/bts/jimin.jsp"});
		return members;
	}
	
	public String[] getMemberContent(String code) {
		String[] content = getBtsMemberList().get(code);
		return content;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//2.모델 확보
		Map<String, String[]> bts = getBtsMemberList();
		//3.모델 공유
		req.setAttribute("bts", bts);
		//4.뷰 선택
		String viewName = "/jsonView.do";
		//5.뷰로 이동
		req.getRequestDispatcher(viewName).forward(req, resp);
	}
}
