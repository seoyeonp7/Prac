<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<table>
	<thead>
		<tr>
			<td>일련번호</td>
			<td>상품분류</td>
			<td>상품명</td>
			<td>거래처명</td>
			<td>구매가</td>
			<td>판매가</td>
			<td>상품구매자수</td>
		</tr>
	</thead>
	<tbody>
		<c:set var="prodList" value="${pagingVO.dataList}"></c:set>
		<c:choose>
			<c:when test="${not empty prodList}">
			<c:forEach items="${prodList }" var="prod">
					<tr>
						<td>${prod.rnum }</td>
						<td>${prod.lprodNm}</td>
						<td>${prod.prodName}</td>
						<td>${prod.buyer.buyerName}</td>
						<td>${prod.prodCost}</td>
						<td>${prod.prodPrice}</td>
						<td>${prod.cnt}</td>
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
					<select id="searchType">
						<option value>전체</option>
						<option value="lprodNm">분류명</option>
						<option value="buyerName">거래처명</option>
						<option value="prodName">상품명</option>
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
	$("[name=searchType]").val("${pagingVO.simpleCondition.searchType}");
	$("[name=searchWord]").val("${pagingVO.simpleCondition.searchWord}");
	
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