package test;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ImageStreamingServlet.do")
public class ImageStreamingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws IOException, ServletException {
		//응답데이터에 한글 컨텐츠를 포함시키기 위해
		resp.setContentType("image/png;charset=UTF-8");
		String imageFolder = "d:/contents/images";
		String imageName ="cat3.png";
		
		File imageFile = new File(imageFolder, imageName);
		
		FileInputStream fis = new FileInputStream(imageFile);
		OutputStream os = resp.getOutputStream();
		int tmp = -1;
		while((tmp=fis.read())!=-1){
			os.write(tmp);
		}
		fis.close();
		os.close();
	}
}
