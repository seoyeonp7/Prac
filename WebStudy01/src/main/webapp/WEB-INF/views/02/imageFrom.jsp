<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	File[] imageFiles = (File[]) request.getAttribute("imageFiles");
%>
   <form action="<%=request.getContextPath()%>/imageStreaming.do">
      <select name="imgChoice">
<%for(File tmp : imageFiles){ %>
         <option><%=tmp.getName()%></option>
<%}%>
      </select>
      <input type="submit" value="전송" />
   </form>
</body>
</html>