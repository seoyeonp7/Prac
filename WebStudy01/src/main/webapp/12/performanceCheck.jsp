<%@page import="java.sql.SQLException"%>
<%@page import="kr.or.ddit.vo.MemoVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>12/performanceCheck.jsp</title>
</head>
<body>
<h4>성능 고려사항</h4>
<pre>
	응답 소요 시간 : latency time(커넥션 수립 시간 - 99.999%) + processing time(쿼리 실행 시간)
	case 1 - 13ms
	case 2 - 841ms --> 커넥션을 생성하는 작업이 가장 오래 걸림! 미리 생성해놓는 것이 좋아~ , DBCP case 4 - 24ms -> connectionpoling이 필수임
	case 3 - 23ms
	
	<%
		long startTime = System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  code, writer, content, \"DATE\"  ");
		sql.append(" FROM   tbl_memo                        ");
		// 커넥션은 1번 수립하고 쿼리 실행할때 100번 실행한다는 뜻
		for(int i=1; i<=100; i++){
			try (
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
		
				ResultSet rs = pstmt.executeQuery();
				List<MemoVO> memoList = new ArrayList<>();
				while (rs.next()) {
					MemoVO memo = new MemoVO();
					memoList.add(memo);
					memo.setCode(rs.getInt("CODE"));
					memo.setWriter(rs.getString("WRITER"));
					memo.setContent(rs.getString("CONTENT"));
					memo.setDate(rs.getString("DATE"));
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		long endTime = System.currentTimeMillis();
	%>
	소요시간 : <%=endTime-startTime %>ms
</pre>
</body>
</html>