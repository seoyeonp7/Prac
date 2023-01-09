package kr.or.ddit.servlet01;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/01/imageForm.do")
//, initParams= {@WebInitParam(name="imageFolder", value = "D:\\contents\\images")})
public class ImageStreamingFormServlet01 extends HttpServlet {

	private ServletContext application;
	private String imageFolder;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = getServletContext();
		imageFolder = application.getInitParameter("imageFolder");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");  // 출력스트림 개방하기 전에 설정해야함
		
		File folder = new File(imageFolder);
		File[] imageFiles = folder.listFiles((dir, name)->{
			String mime = application.getMimeType(name);
			return mime != null && mime.startsWith("image/");
		});
		StringBuffer content = new StringBuffer();
		content.append("<html>\n");
		content.append("	<body>       \n");
		content.append(String.format("<form action = '%s/imageStreaming.do'>\n", req.getContextPath()));
		content.append("		<select name = 'image'> \n");
		
		String pattern = "<option>%s</option>\n";
		for(File tmp : imageFiles) {
			content.append(String.format(pattern, tmp.getName()));
		}
		content.append("		</select>\n");
		content.append("<input type='submit' value='전송' />");
		content.append("</form>\n");
		content.append("	</body>      \n");
		content.append("</html>          \n");
		
		// try with resource : 알아서 close가 되기 때문에 직접close할 필요가 없음
		try(
		PrintWriter out =  resp.getWriter();
		){
			out.println(content);
		}
	}
}
