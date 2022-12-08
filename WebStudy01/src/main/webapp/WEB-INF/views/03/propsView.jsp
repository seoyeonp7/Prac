<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.6.1.min.js"></script>
</head>
<body>
<h4>properties 파일 뷰어</h4>

<table>
<thead>
	<tr>
		<th>key</th>
		<th>value</th>
	</tr>
</thead>
<tbody>
	<tr>
		<th></th>
		<th></th>
	</tr>
</tbody>

</table>
<script>
	$.ajax({
		dataType : "json",
		success : function(resp) {
			resp['prop1']
			
			let options = [];
            $.each(resp, function (index, file) {
               let option = $("<option>")
                        .addClass(file.mime)   
                        .html(file.name);
               options.push(option);
            });
            SELECTTAG.append(options);
            
		},
		error : function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log(status);
			console.log(error);
		}
	});
</script>
<!-- <img src="../../resources/images/cat1.jpg"> -->
<%-- <img src="<%=request.getContextPath() %>/resources/images/cat1.jpg"> --%>
</body>
</html>