<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>09/browsingSample.jsp</title>
<jsp:include page="/includee/preScript.jsp"/>
<script>
$.fn.generateFileList=function(){
	let target = this.data("target");
    let ulTag = this;
    $.ajax({
		url : "<%=request.getContextPath()%>/browsing/getFileList",
		data : {
			target:target
		},
		dataType : "json",
		success : function(resp) {
			let liTags = [];
			$.each(resp.files, function(index,wrapper){
				let li = $("<li>").addClass("list-group-item")
						.data("contentType",wrapper.contentType)
						.html(wrapper.name);
				liTags.push(li);
			});
			ulTag.html(liTags);
		},
		error : function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log(status);
			console.log(error);
		}
	});
    
	return this;
}

</script>
</head>
<body>
<div class="row">
	<div class="col">
	<h4>src : /resources/images</h4>
	<ul class="list-group" id="srcUL" data-target="/resources/images">
		<li data-content-type="image/jpeg" class="list-group-item active">cat1.jpg</li>
		</ul>
	</div>
	<div class="col">
		<input type="radio" name="command" value="COPY" checked /> COPY
		<input type="radio" name="command" value="MOVE" /> MOVE
		
		<button id="controlBtn" class="btn btn-primary">실행</button>
<!-- 		<button id="copyBtn" class="btn btn-primary">실행</button> -->
	</div>
	<div class="col">
		<h4>dest : /destImgs</h4>
		<ul class="list-group" id="destUL" data-target="/destImgs">
         
		</ul>
	</div>
</div>


<!-- ul 동적 태그 비동기 요청 (둘 다 같은 서블릿으로) -->
<!-- li 선택했을 때 active클래스를 토글링 할 수 있어야 한다. -->

<!-- 버튼 이벤트처리 -> 비동기 요청 -->
<!-- 소스파일과 데스티네이션 폴더 넘기기 -->
<!-- 요청 받아서 서버사이드에서 복사 -->

<!-- 사칙연산,, ENUM 활용 -->


<script>
	let srcUL = $("#srcUL").generateFileList().on("click","li",function(event){
// 		alert("1번 방식");
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
	});
	let destUL = $("#destUL").generateFileList();
	$(document).on("click","#copyBtn",function(event){
		let fileName = srcUL.find("li.active").text();
		if(!fileName) return false;

		let srcFile = srcUL.data("target") + "/"+ fileName;
		let data = {
						srcFile : srcFile
						, destFolder : destUL.data("target")
						, command : "COPY"
					};
		$.post("<%=request.getContextPath() %>/browsing/fileManager", data, function(resp){
	         if(resp.result)
	            destUL.generateFileList();
	         else {
	            alert("파일 관리 실패");
	         }
	      }, "json");

		
	});
	
	//복사
// 	$(document).on("click","#copyBtn",function(){
// 		console.log(this);
// 		$.ajax({
<%-- 			url : "<%=request.getContextPath()%>/browsing/fileManager", --%>
// 			method : "post",
// 			data : {
// 				srcFile: "/resources/images/"+$(".active").text()
// 				, destFolder:"/destImgs"
// 				, command:"COPY"
// 			},
// 			dataType : "json", //복사 될때마다 데이터 받아와 li태그 갱신
// 			success : function(resp) {

// 			},
// 			error : function(jqXHR, status, error) {
// 				console.log(jqXHR);
// 				console.log(status);
// 				console.log(error);
// 			}
// 		});
// 	});
	
</script>
<jsp:include page="/includee/postScript.jsp"/>
</body>
</html>