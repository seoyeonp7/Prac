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
			<th>일련번호</th>
			<th>상품분류</th>
			<th>상품명</th>
			<th>거래처명</th>
			<th>구매가</th>
			<th>판매가</th>
			<th>상품구매자수</th>
		</tr>
	</thead>
	<tbody id="listBody">
<%-- 		<c:set var="prodList" value="${pagingVO.dataList}"></c:set> --%>
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${not empty prodList}"> --%>
<%-- 				<c:forEach items="${prodList }" var="prod"> --%>
<!-- 						<tr> -->
<%-- 							<td>${prod.rnum }</td> --%>
<%-- 							<td>${prod.lprodNm}</td> --%>
<%-- 							<td>${prod.prodName}</td> --%>
<%-- 							<td>${prod.buyer.buyerName}</td> --%>
<%-- 							<td>${prod.prodCost}</td> --%>
<%-- 							<td>${prod.prodPrice}</td> --%>
<%-- 							<td>${prod.memCount}</td> --%>
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<%-- 				</c:when> --%>
<%-- 			<c:otherwise> --%>
<!-- 				<tr> -->
<!-- 					<td colspan="7">조건에 맞는 회원이 없음.</td> -->
<!-- 				</tr> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				<div class="pagingArea">
<%-- 					${pagingVO.pagingHTML } --%>
				</div>
				<div id="searchUI">
					<select name="prodLgu">
						<option value>분류</option>
						<c:forEach items="${lprodList}" var="lprod">
							<option value="${lprod.lprodGu}">${lprod.lprodNm}</option>
						</c:forEach>
					</select>
					<select name="prodBuyer">
						<option value>거래처</option>
						<c:forEach items="${buyerList }" var="buyer">
							<option value="${buyer.buyerId }" class="${buyer.buyerLgu }">
								${buyer.buyerName }
							</option>
						</c:forEach>
					</select>
					<input type="text" name="prodName" placeholder="상품명">
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
	<input type="text" name="prodLgu" placeholder="분류코드">
	<input type="text" name="prodBuyer" placeholder="거래처코드">
	<input type="text" name="prodName" placeholder="상품명">
</form>
<script>

	$("[name=prodLgu]").on("change",function(){
		let prodLgu = $(this).val();		//select태그는 this말고 $(this)로
		prodBuyerTag.find("option:gt(0)").hide();
		prodBuyerTag.find("option."+prodLgu).show();
	}).val("${detailCondition.prodLgu}");
	
	let prodBuyerTag = $("[name=prodBuyer]").val("${detailCondition.prodBuyer}");
	let listBody=$("#listBody");
	
	//a태그 비동기로 넘어오므로 존재하지 않는 엘리먼트에 바인딩 한 것
	//동적 엘리먼트에 바인딩 하지 마라
	//부모에 바인딩 할 것
	let pagingArea =$(".pagingArea").on("click","a.paging",function(event){
	   event.preventDefault();
	   let page = $(this).data("page");
	   if(!page) return false;
	   searchForm.find("[name=page]").val(page);
	   searchForm.submit();
	   return false;
	});
	
	let makeTrTag = function(prod){
		let aTag=$("<a>")
				.attr("href","${pageContext.request.contextPath}/prod/prodView.do?what="+prod.prodId)
				.html(prod.prodName);
		return $("<tr>").append(
					$("<td>").html(prod.rnum)		
					, $("<td>").html(aTag)		
					, $("<td>").html(prod.lprodNm)		
					, $("<td>").html(prod.buyer.buyerName)		
					, $("<td>").html(prod.prodCost)		
					, $("<td>").html(prod.prodPrice)
					, $("<td>").html(prod.memCount)		
		);
	}
	
	//비동기 요청
	let searchForm = $("#searchForm").on("submit",function(event){
		event.preventDefault();
		
		let url = this.action;
		let method = this.method;
		let queryString = $(this).serialize();
		$.ajax({
			url : url,
			method : method,
			contentType:"application/json;charset=UTF-8", //request content-type
			data : queryString,
			dataType : "json", //답장의 타입, request Accept, response content-type
			success : function(resp) {
				//페이지 일부 UI가 재활되므로 비워주는 작업 필요
				listBody.empty();
				pagingArea.empty();
				searchForm[0].page.value="";
				
				let pagingVO = resp.pagingVO;
				
				let dataList = pagingVO.dataList;
				let trTags = [];
				if(dataList){
					$.each(dataList, function(index,prod){
						trTags.push(makeTrTag(prod));
					})
				}else{
					let tr = $("<tr>").html(
						$("<td>").attr("colspan","7")	
								.html("조건에 맞는 상품 없음.")
					);
					trTags.push(tr);
				}
				listBody.html(trTags);
				pagingArea.html(pagingVO.pagingHTML);
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		})
		return false;
	}).submit(); //페이지 로딩되면 무조건 한번 submit 하겠다.
	
	let searchUI = $("#searchUI").on("click", "#searchBtn", function(){ 
		/* this:searchBtn */
	   let inputs = searchUI.find(":input[name]"); 
		/* 콜론: '모든' 입력태그, name: 파라미터 받는 애들은 name 속성 가지므로 */
	   $.each(inputs, function(index, input){
	      let name = this.name;
	      let value = $(this).val(); /* select태그에서는 value못꺼냄, this X */
// 	      searchForm.find("[name="+name+"]").val(value);
// 	      searchForm.get(0)['searchType']
	      searchForm[0][name].value = value;
	   });
	   searchForm.submit();
	});
	
	
	

</script>
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>