<%@page import="java.nio.file.StandardCopyOption"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="kr.or.ddit.servlet01.DescriptionServlet"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/resourceIdentify.jsp</title>
</head>
<body>
<h4>자원의 종류와 식별방법</h4>
<pre>
	: 자원의 위치와 경로 표기 방법에 따라 분류
	
	1. File system resource : d:/contents/images/cat1.jpg
	<%
		String realPath = "d:/contents/images/cat1.jpg";
		File fileSystemResource = new File(realPath);
	%>
	파일의 크기 : <%=fileSystemResource.length() %>
	
	2. Class path resource : /kr/or/ddit/images/cat2.png
	<%
		String qualifiedName = "../images/cat2.png";
		realPath = DescriptionServlet.class.getResource(qualifiedName).getFile();
		DescriptionServlet.class.getClassLoader().getResource("kr/or/ddit/images/cat2.png").getFile();
		File classPathResource = new File(realPath);
	%>
	실제 경로 : <%=realPath %>
	파일의 크기 : <%=classPathResource.length()%>
	
	3. Web resource : https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif
	/WebStudy01/src/main/webapp/resources/js/jquery-3.6.1.min.js
	<%
// 		String resourceURL = "https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif";
		String resourceURL = "http://localhost/WebStudy01/resources/js/jquery-3.6.1.min.js";
		URL url = new URL(resourceURL);
		URLConnection conn = url.openConnection();
		String resourcePath = url.getPath();
		int lastIdx = resourcePath.lastIndexOf('/');
		String fileName = resourcePath.substring(lastIdx+1);
		String folderPath = "d:/contents/images";
		File downloadFile = new File(folderPath,fileName);
		InputStream is = conn.getInputStream();
		Files.copy(is, Paths.get(downloadFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
	%>
	resourcePath : <%=resourcePath %>
	
</pre>
</body>
</html>