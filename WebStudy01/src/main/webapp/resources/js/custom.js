/**
 * 
 */
//폼태그의 모든 입력 데이터의 이름과 값을 콘솔에 로그로 출력할 수 있는 함수.
//ex) $("form").log().serializeObject();
//체인구조를 사용할 수 있어야 함.
$.fn.serializeObject = function(){
	if(this.prop('tagName')!='FORM')
		throw Error("form 태그 외에는 사용 불가.");
	let fd = new FormData(this[0]);
	let nameSet = new Set();
	for(let key of fd.keys()){
		nameSet.add(key);
	}
	let data = {}
	for(let name of nameSet){
		let values = fd.getAll(name) //get과의 차이 : getAll은 반환타입이 배열
		if(values.length>1){
			data[name] = values;
		} else {
			data[name] = values[0];
		}
	}
	return data;
}

$.fn.log = function(){
	let data = this.serializeObject();
	for(let prop in data){
		console.log(prop+" : "+data[prop]); //prop 는 input tag의 네임
	}
	return this;
}