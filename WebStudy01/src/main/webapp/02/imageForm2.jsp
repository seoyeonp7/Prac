<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

//    public ServletContext application;
//    public String imageFolder;
   ServletContext application2 = getServletContext();
   String imageFolder = application2.getInitParameter("imageFolder");
    
    File folder = new File(imageFolder);
    File[] imageFiles = folder.listFiles();
    
    String name = "";
    String mime = "";                  
    String path = "";
    
    for(File f : imageFiles){
       name = f.getName();
       mime = application2.getMimeType(name);
       path = f.getPath();
       
       //out.print("name : " + name + ", mime : " + mime + ", path : " + path);
    }
    
//     File[] imageFiles = folder.listFiles((dir,name)->{
//        String mime = application2.getMimeType(name);
//        return mime != null && mime.startsWith("image/");
//     });
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

   <form action="<%=request.getContextPath()%>/imageStreaming.do">
      <select name="image">
<%for(File tmp : imageFiles){ %>
         <option><%=tmp.getName()%></option>
<%
}
%> 
      </select>
      <input type="submit" value="전송" />
   </form>
</body>
</html>