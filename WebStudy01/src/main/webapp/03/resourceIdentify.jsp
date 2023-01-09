<%@page import="java.nio.file.StandardCopyOption"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="kr.or.ddit.servlet01.DescriptionServlet"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
		realPath = DescriptionServlet.class.getClassLoader().getResource("kr/or/ddit/images/cat2.png").getFile();
		File classPathResource = new File(realPath);
	%>
	실제 경로 : <%=realPath %>
	파일의 크기 : <%=classPathResource.length() %>
	3. Web resource : https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif
	http://localhost/WebStudy01/src/main/webapp/resources/js/jquery-3.6.1.min.js
	<%
// 		String resourceURL = "https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif";
		String resourceURL = "http://localhost/WebStudy01/src/main/webapp/resources/js/jquery-3.6.1.min.js";
		URL url = new URL("https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif");
		URLConnection conn = url.openConnection();
		String resourcePath = url.getPath();
		int lastIdx = resourcePath.lastIndexOf('/');
		String fileName = resourcePath.substring(lastIdx+1);
		String folderPath = "d:/contents/images";
		File downloadFile = new File(folderPath, fileName);
		InputStream is = conn.getInputStream();
		Files.copy(is, Paths.get(downloadFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
	%>
	resourcePath : <%=resourcePath %>
	
	*** 웹자원에 대한 식별성 : URI
	URI(Uniform Resource Identifier : 범용 자원의 식별자)
	
	URL(Uniform Resource Locator : 범용 자원의 위치자)
	URN(Uniform Resource Name : 범용 자원의 명령성) 단점 : 같은 이름이 두개면 문제, 이름을 알기위한 출석부가 우선으로 필요(배보다 배꼽이 더 크다~) 
	URC(Uniform Resource Content : 범용 자원의 조건)
	
	URL 구조
	protocol(scheme): //(루트를 표현하기 위한 구분자)IP(DN):port/context/depth1...depthN/resourceName
	
	DomainName
	3 level www.naver.com  가장 마지막에 있는 게(com) 가장 최상위 레벨 (GlobalTopLevelDomain) : GTLD
	4 level	www.naver.co.kr (kr)NationalTopLevelDomain : NTLD
	
	URL 표기 방식
	절대경로(**) : 최상위 루트부터 전체 경로 표현 - 생략 가능한 요소가 존재.
		client side : /WebStudy01/resources/images/cat1.jpg
					: context path 부터 시작됨.
		server side : /resources/images/cat1.jpg
					: context path 이후의 경로 표기.
	상대경로 : 기준점(브라우저의 현재 주소)을 중심으로한 경로 표현
</pre>
<%
// 	InputStream is2 = application.getResourceAsStream(request.getContextPath() + "/resources/images/cat1.jpg");
	String realPath1 = application.getRealPath("/resources/images/cat1.jpg");
	String realPath2 = application.getRealPath(request.getContextPath() + "/resources/images/cat1.jpg");
	
	request.getRequestDispatcher("/WEB-INF/view/depth1/test.jsp").forward(request, response);
	response.sendRedirect(request.getContextPath() + "/member/memberForm.do");
%>
<img src="<%=request.getContextPath() %>/resources/images/cat1.jpg"/>
<img src="../resources/images/cat1.jpg"/>
<img src="cat1.jpg"/>
<%-- 서버사이드 방식으로 접근한 파일의 크기 : <%=is2.available() %> --%>
realPath1 : <%=realPath1 %> <br/>
realPath2 : <%=realPath2 %>
</body>
</html>