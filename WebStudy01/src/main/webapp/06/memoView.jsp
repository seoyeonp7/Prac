<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
</head>
<body>
<h4>Restful 기반의 메모 관리</h4>
<form id="resultArea" action="${pageContext.request.contextPath}/memo" method="post" accept-charset="utf-8">
	<input type="text" name="writer" placeholder="작성자" />
	<input type="date" name="date" placeholder="작성일" />
	<textarea name="content"></textarea>
<!-- 	<input type="submit" value="등록" /> -->
	<input type="button" value="등록" onclick="func(); return false;" />
</form>
<table class="table-bordered">
	<thead>
		<tr>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	</thead>
	<tbody id="listBody">
	
	</tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
// 	EDD(Event Driven Development)방법론, TDD(Test Driven Development)
	$("#exampleModal").on("show.bs.modal",function(event){
		//this==event.target : this는 모달 그 자체
		//event.relatedTarget : moodal을 오픈할 때 사용한 클릭 대상, 여기서는 tr태그
		let memo = $(event.relatedTarget).data("memo");
		$(this).find(".modal-body").html(memo.content); //전체 모달이 아닌 떠있는 모달의 바디
	}).on("hidden.bs.modal",function(){
		$(event.target).find(".modal-body").empty();
	});
	
	let listBody = $('#listBody');
	let makeListBody = function(memoList){
		listBody.empty();
		let trTags=[];
		if(makeListBody.length>0){
// 			data-bs-toggle="modal" data-bs-target="#exampleModal"
			$.each(memoList,function(index,memo){
				let tr = $("<tr>").append(
					$("<td>").html(memo.writer)	
					,$("<td>").html(this.date)
				).attr({
					"data-bs-toggle":"modal"
					,"data-bs-target":"#exampleModal"
				}).data("memo",memo);
				trTags.push(tr);
			});
		}else{
			let tr = $("<tr>").html(
				$("<td>")
				.attr("colspan","2")
				.html("작성된 메모 없음.")
			);
			trTags.push(tr);
		}
		listBody.append(trTags);
	}
	$.ajax({
		url : "${pageContext.request.contextPath}/memo",
		method : "get",
		dataType : "json",
		success : function(resp) {
			makeListBody(resp.memoList);
			console.log(resp.memoList);
		},
		error : function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log(status);
			console.log(error);
		}
	});
	
	$(function(){
	      let resultArea = $("#resultArea");
	      $("form").on("submit", function(event){
	         event.preventDefault();
	         console.log($(this));
	         console.log(this);
	         console.log($(this)[0]);
	         console.log($(this).get(0));
	         let url = this.action;
	         let method = this.method;
	         let data = $(this).serialize();
	         console.log(data);
	         $.ajax({
	            url : url,
	            method : method,
	            data : data,
	            dataType : "html",
	            success : function(resp) {
	               resultArea.html(resp);
	            },
	            error : function(jqXHR, status, error) {
	               console.log(jqXHR);
	               console.log(status);
	               console.log(error);
	            }
	         });
	         return false;
	      });
	   });
	
// 	function func(){
		
// 	  const data = $("#myForm").serialize();

// 	  const ajax = $.ajax({
// 		cache : false,
// 		url : '${pageContext.request.contextPath}/memo',
// 	    type : 'POST',
// 	    dataType : 'json',
// 	    data : data
// 	  });
// 	  //성공
// 	  ajax.done(function(res){
// 	    console.log(res);
// 	  });
// 	  //실패
// 	  ajax.fail(function(res){
// 	    alert(res.responseJSON.error[0].msg);
// 	  });
// 	}
	
	
// 	console.log("data : ",listBody.find("tr:first").data("memo"));
</script>
<jsp:include page="/includee/postScript.jsp"/>
</body>
</html>