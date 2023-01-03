<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
</head>
<body>
<h4>Restful 기반의 메모 관리</h4>
<form name="memoForm" action="${pageContext.request.contextPath}/memo" method="post" id = "memoform">
   <input type="text" name="writer" placeholder="작성자" />
   <input type="date" name="date" placeholder="작성일" />
   <textarea name="content"></textarea>
   <input type="submit" value="등록" />
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
	// <비동기로 메모가 등록되도록 해야함>
   // 1. 등록버튼을 누른다 (client의 행위 click(button이 타겟)이벤트의 발생 submit이벤트(form이 타겟) 까지 발생 이벤트 2번 발생 )
   // 2. submit 이벤트에 의해서 발생하는 동기요청을 중단시키고, 비동기 요청으로 변경해야함
// 	$('[name="memoForm"]')
	let memoForm = $(document.memoForm).on('submit',function(){ //memoForm:제이쿼리객체
		event.preventDefault();
// 3. URL, method, data 모든 것들이 동기 요청이 가지고있었던 모든 것들이 form으로 부터 꺼내야한다     
// 		this
// 		event.target 
// 		$(this)
		let url = this.action;
		let method = this.method;
// 		let data = $(this).serialize(); // writer=작성자&date=작성일&content=내용(QueryString)
		let data = $(this).serializeObject(); //QueryString 없앰
// 		let memoForm = this; //memoForm:일반객체
		$.ajax({
			url : url,
			method : method,
			contentType:"application/json;charset=UTF-8", //request content-type
			data : JSON.stringify(data),
			dataType : "json", //답장의 타입, request Accept, response content-type
			success : function(resp) {
				makeListBody(resp.memoList);
				memoForm[0].reset(); //제이쿼리객체 : 배열처럼 사용된다.
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
		return false;
	});
// 4. 비동기(Post) 요청에 의해 처리후에 리다이렉션된 요청(GET) 그 요청에 대한 처리 결과 (memolist - json)로 tbody 갱신
//  console.log("data : ",listBody.find("tr:first").data("memo"));
//    EDD(Event Driven Development)방법론, TDD(Test Driven Development)
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
//          data-bs-toggle="modal" data-bs-target="#exampleModal"
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
      },
      error : function(jqXHR, status, error) {
         console.log(jqXHR);
         console.log(status);
         console.log(error);
      }
   });
   
</script>
<jsp:include page="/includee/postScript.jsp"/>
</body>
</html>