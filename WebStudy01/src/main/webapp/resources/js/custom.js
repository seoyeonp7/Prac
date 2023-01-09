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

$.fn.sessionTimer = function(timeout, msgObj){
   if(!timeout)
      throw Error("세션 타임아웃 값이 없음.");
      
   const SPEED = 100;
   const TIMEOUT = timeout;
   const timerArea = this;
//    event propagation : bubbling 방식
   let msgArea = null;
   if(msgObj) {
      msgArea = $(msgObj.msgAreaSelector).on("click", msgObj.btnSelector , function(event){
   //       console.log(this.id +", "+ $(this).prop("id")); //this:이벤트 대상(.controlBtn)
         if(this.id=="YES"){
            jobClear();
            timerInit();
            $.ajax({
               method : "head"
            });
         }
         msgArea.hide();
      }).hide();
   }
   
   let jobClear = function(){
      let timerJob = timerArea.data("timerJob");
      if(timerJob)
         clearInterval(timerJob);
      let msgJob = msgArea.data("msgJob");
      if(msgJob)
         clearTimeout(msgJob);
   }
   
   let timerInit = function(){
      if(msgObj){
         let msgJob = setTimeout(() => {
            msgArea.show();
         }, (TIMEOUT-60) * SPEED);
         msgArea.data("msgJob",msgJob);
      }
      
      let timer = TIMEOUT;
      let timerJob = setInterval(() => {
         if(timer==1){
            clearInterval(timerJob);
            location.reload();
         }else 
            timerArea.html(timeFormat(--timer));
      }, SPEED);
      timerArea.data("timerJob",timerJob);
   }
   
   timerInit();

   let timeFormat = function(time){
      let min = Math.trunc( time / 60 );
      let sec = time % 60;
      return min + ":" + sec;
   }
   
   return this;
}