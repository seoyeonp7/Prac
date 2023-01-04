<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.do</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<h4>회원목록 조회</h4>
<table>
	<thead>
		<tr>
			<th>일련번호</th>
			<th>회원아이디</th>
			<th>회원명</th>
			<th>이메일</th>
			<th>휴대폰</th>
			<th>거주지역</th>
			<th>마일리지</th>
			<th>구매건수</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="memberList" value="${pagingVO.dataList}"></c:set>
		<c:choose>
			<c:when test="${not empty memberList}">
			<c:forEach items="${memberList }" var="member">
					<tr>
						<td>${member.rnum }</td>
						<td>${member.memId}</td>
						<td>
							<c:url value="/member/memberView.do" var="viewURL">
								<c:param name="who" value="${member.memId }" />
							</c:url>
							<a href="${viewURL}">${member.memName}</a>
						</td>
						<td>${member.memMail}</td>
						<td>${member.memHp}</td>
						<td>${member.memAdd1}</td>
						<td>${member.memMileage}</td>
						<td>${member.cartCount}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6">조건에 맞는 회원이 없음.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				${pagingVO.pagingHTML }
				<div id="searchUI">
					<select name="searchType">
						<option value>전체</option>
						<option value="name">이름</option>
						<option value="address">주소1</option>
					</select>
					<input type="text" name="searchWord" />
					<input type="button" id="searchBtn" value="검색" />
				</div>
			</td>
		</tr>
	</tfoot>
</table>
<!-- 데이터를 입력받기 위한 ui와 전송하기 위한 ui 분리 -->
<h4>Hidden Form</h4>
<form id="searchForm">
	<input type="text" name="page" />
	<input type="text" name="searchType" />
	<input type="text" name="searchWord" />
</form>
<script>
	$("[name=searchType]").val("${searchVO.searchType}");
	$("[name=searchWord]").val("${searchVO.searchWord}");
	
	let searchForm = $("#searchForm");
	let searchUI = $("#searchUI").on("click", "#searchBtn", function(){
	   let inputs = searchUI.find(":input[name]");
	   $.each(inputs, function(index, input){
	      let name = this.name;
	      let value = $(this).val();
	      searchForm.find("[name="+name+"]").val(value);
	   });
	   searchForm.submit();
	});
	
	$("a.paging").on("click", function(event){
	   event.preventDefault();
	   let page = $(this).data("page");
	   if(!page) return false;
	   searchForm.find("[name=page]").val(page);
	   searchForm.submit();
	   return false;
	});

</script>
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>