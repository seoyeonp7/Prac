package kr.or.ddit.servlet01;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;

public class ImageStreamingServlet extends HttpServlet{
	private String imageFolder;
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
//		imageFolder = config.getInitParameter("imageFolder");
		application = getServletContext();
		imageFolder = application.getInitParameter("imageFolder");
		System.out.printf("받은 파라미터 : %s\n", imageFolder);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException
	{
		ServletContext application = getServletContext();
		// 응답데이터에 한글 컨텐츠를 포함시키기 위해
		String imageName = req.getParameter("image");
		if(imageName == null || imageName.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String mimeType = application.getMimeType(imageName);
		resp.setContentType(mimeType);
		
		
		File imageFile = new File(imageFolder, imageName);
		if(!imageFile.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 쿠키 response하기~
		Cookie imageCookie = new Cookie("imageCookie", imageName);
		imageCookie.setPath(req.getContextPath());
		imageCookie.setMaxAge(60*60*24*3); //3일 살려두기!
		resp.addCookie(imageCookie);
		
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(imageFile);		
			os = resp.getOutputStream();
			int tmp = -1;
			while((tmp = fis.read())!=-1){
				os.write(tmp);
		}
		}finally {
			if(fis!=null)
				fis.close();
			if(os!=null)
			os.close();
		}
	}
}