package test;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import java.util.*;

@WebServlet("/WatchServlet.do")
public class WatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws IOException, ServletException {
		
		StringBuffer content = new StringBuffer();
		content.append("<html>");
		content.append("<body>");
		content.append(String.format("<h4>current context name : %s</h4>",req.getContextPath()));
		content.append(String.format("<h4>last received server time : %s</h4>",new Date()));
		content.append(String.format("<h4>current client time : <span id ='timeArea'></span>"));
		content.append("<script>");
		content.append("setInterval(function(){");
		content.append("let now = new Date();");
		content.append("let timeArea = document.getElementById('timeArea');");
		content.append("timeArea.innerHTML = now;");
		content.append("},1000);");
		content.append("</script>");
		content.append("</body>");
		content.append("</html>");
		PrintWriter out = resp.getWriter();
		out.println(content);
		out.close();
	}
}
