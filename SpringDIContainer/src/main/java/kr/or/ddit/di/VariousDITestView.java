package kr.or.ddit.di;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VariousDITestView {
//		컨테이너 객체 생성
//		필요없으면 자동 소멸
//		등록된 모든 빈들은 라이프사이클 콜백 갖고 있어야 한다. 
//		초기화로그, 소멸로그 확인할 수 있어야 함.
//		등록된 빈을 메인 메소드 안에서 주입받음
//		빈의 프로퍼티 상태 확인할 수 있도록 로그 출력
	
	public static void main(String[] args) {
		//컨테이너객체
		ConfigurableApplicationContext context = 
				new ClassPathXmlApplicationContext("kr/or/ddit/di/conf/VariousDI-Context.xml");
		context.registerShutdownHook();
		VariousDIVO vo1 = context.getBean("vo1",VariousDIVO.class);
		VariousDIVO vo2 = context.getBean("vo2",VariousDIVO.class);
		log.info("주입된 객체 : {}",vo1);
		log.info("주입된 객체 : {}",vo2);
	}
}
