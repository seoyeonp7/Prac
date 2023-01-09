<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>10/elCollection.jsp</title>
</head>
<body>
<h4>EL 에서 집합 객체 접근 방법</h4>
<%
	String[] array = new String[]{"value1", "value2"};
	List<String> list = Arrays.asList(array);
	Set<String> set = list.stream().collect(Collectors.toSet());
	
	Map<String,Object> map = new HashMap<>();
	map.put("key-1", "value1");
	map.put("key 2", "value2");
	
	pageContext.setAttribute("array", array);
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("set", set);
	pageContext.setAttribute("map", map);
	
	pageContext.setAttribute("memo", null);
%>
<pre>
	array : <%--=array[3] --%>, ${array[3] } el은 근본적으로 데이터 출력이 목적! 예외가 발생하더라도 그 예외를 처리하는 게 목적이 아님! 어지간한 에러는 다 화이트스페이스 처리함!
	list : <%--=list.get(3) --%>, ${list[3] },  \${list.get(3) }  el은 근본적으로 메소드를 호출할 수 없음(사실은 틀린말~ 지금 버전은 호출 가능 but 1번이 안전하니까 el에서는 메소드를 직접 호출하지 말라고함)! 배열과 list 값을 가져오는 게 똑같음! 
	ex) memo's writer : ${memo.writer }, ${memo.getWriter() }, \${memo['writer'] }
	set : <%=set %>${set }
	map : <%=map.get("key-1") %>, ${map.get("key-1") }, ${map.key-1 } -> 얘만 -1 출력, ${map['key-1'] }-> 가장 안전
	<%=map.get("key 2") %>, ${map.get("key 2") }, \${map.key 2 } -> 500에러 뜸, ${map['key 2'] }-> 가장 안전
	ex) int i 2;
	
	결론 : 객체나 집합구조에 접근할때 연산배열구조가 가장 안전하다~
</pre>
</body>
</html>