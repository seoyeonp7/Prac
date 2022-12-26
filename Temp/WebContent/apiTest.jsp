<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head> 
    <meta charset="utf-8">
    <title>1좌표1</title>
<style>
.map_wrap {position:relative;width:100%;height:350px;}
.title {font-weight:bold;display:block;}
.hAddr {position:absolute;left:10px;top:10px;border-radius: 2px;background:#fff;background:rgba(255,255,255,0.8);z-index:1;padding:5px;}
#centerAddr {display:block;margin-top:2px;font-weight: normal;}
.bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
</style>
</head> 
<body>

<div class="map_wrap">
    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
    <div class="hAddr">
        <span class="title">지도중심기준 행정동 주소정보</span>
        <span id="centerAddr"></span>
    </div>
</div>
<p id="data1"></p>
<p id="data2"></p>
<div>
	기존버전 위경도 + 버튼
</div>
<form action="/ApiTest/updateRegion.do" method="get">
<!-- 	<input type="submit"  value="전송"> -->
	<button id="modify" type="submit"> 
		동네등록!
	</button>
</form>
<p>p1입니다</p>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e719101244762760ce768ead3d05c1c3&libraries=services"></script>
<script>


navigator.geolocation.getCurrentPosition(function(pos){
	
    console.log(pos);
    
    var myX = pos.coords.longitude; //경도
    var myY = pos.coords.latitude; //위도
    
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    	mapOption = {
        center: new kakao.maps.LatLng(myY, myX), // 지도의 중심좌표
	        level: 1 // 지도의 확대 레벨
	    };  
	
	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
    
    console.log('경도(X좌표): ',myX);
    console.log('위도(Y좌표): ',myY);

	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 마커가 표시될 위치입니다 
	var markerPosition  = new kakao.maps.LatLng(myY, myX); 
	
	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	}),
	infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다;
	
// 	var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
// 	    infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다
	
	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
	// marker.setMap(null);    
	
	
	// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
// 	searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	
	// 현재 센터 값이 아닌 서버 위치 들어온 값으로 주소 검색해서 좌측 상단 표시
	searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	
	// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
// 	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
// 	    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
// 	        if (status === kakao.maps.services.Status.OK) {
// 	            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
// 	            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
	            
// 	            var content = '<div class="bAddr">' +
// 	                            '<span class="title">법정동 주소정보</span>' + 
// 	                            detailAddr + 
// 	                        '</div>';
	
// 	            // 마커를 클릭한 위치에 표시합니다 
// 	            marker.setPosition(mouseEvent.latLng);
// 	            marker.setMap(map);
	
// 	            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
// 	            infowindow.setContent(content);
// 	            infowindow.open(map, marker);
// 	        }   
// 	    });
// 	});
	
	// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
// 	kakao.maps.event.addListener(map, 'idle', function() {
// 	    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
// 	});
	
	function searchAddrFromCoords(coords, callback) {
	    // 좌표로 행정동 주소 정보를 요청합니다
	    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
	}
	
// 	function searchDetailAddrFromCoords(coords, callback) {
// 	    // 좌표로 법정동 상세 주소 정보를 요청합니다
// 	    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
// 	}
	
	// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
	function displayCenterInfo(result, status) {
	    if (status === kakao.maps.services.Status.OK) {
	        var infoDiv = document.getElementById('centerAddr');
	
	        for(var i = 0; i < result.length; i++) {
	            // 행정동의 region_type 값은 'H' 이므로
	            if (result[i].region_type === 'H') {
	                infoDiv.innerHTML = result[i].address_name;
	                break;
	            }
	        }
	    }
	}
	
	$.ajax({
	    type : "get", //디폴트값 get이므로 안써도 됨
	    url : "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x="+myX+"&y="+myY,
	    dataType : "json",
	    headers : {'Authorization': 'KakaoAK 056de8a0406ff7a44e6ff2173687b332'},
	    success : function(data){
	    	
// 	        console.log(data);
	        console.log(data.documents); //Array
	        console.log(data.documents[0].address_name);
	        $('#data1').text('JSON_법정동_address_name값 : ' + data.documents[0].address_name); //보낼 값1
	        $('#data2').text('JSON_행정동_address_name값 : ' + data.documents[1].address_name); //보낼 값2
	        
	        //B타입(법정동) 쓸지 H타입(행정동) 쓸지 정하기
	        
	        var sendData = data.documents[0].address_name;
	        
	        $('#modify').click(function(){ //submit 버튼 클릭 하였을 떄
	        	console.log(sendData);
        		$.ajax({
		            url: '/ApiTest/ajaxTest.do',
// 		            data: {"address":sendData},
// 		            dataType: 'json',
		            error: function() {
		                console.log('통신실패!!');
		            },
		            success: function(data) {
		            	var result = document.createElement("p");
		            	result.setAttribute("adress2",sendData);
		                console.log("통신데이터 값 : " + data);
		            }
       		 	});
   			 });
	    },
	    error : function(){
	        console.log('실패');
	    }
	});
	
	
});

</script>
</body>
</html>