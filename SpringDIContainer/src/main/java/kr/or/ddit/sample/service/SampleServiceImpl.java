package kr.or.ddit.sample.service;

import java.util.Calendar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import kr.or.ddit.sample.dao.SampleDAO;
import kr.or.ddit.sample.dao.SampleDAOFactory;
import kr.or.ddit.sample.dao.SampleDAOImpl_Oracle;
import kr.or.ddit.sample.dao.SampleDAOImpl_Postgre;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SampleServiceImpl implements SampleService, ApplicationContextAware {
	private ConfigurableApplicationContext context;
	
	private Resource log4j2xml;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = (ConfigurableApplicationContext) arg0;
		log.info("ApplicationContextAware 구조로 컨테이너 주입됨.");
	}

	public void init1() {
		log.info("{} 객체 초기화",getClass().getSimpleName());
		log4j2xml = context.getResource("classpath:log4j2.xml");
		log.info("{} 리소스 로딩 완료", log4j2xml);
	}
	
	public void destroy2() {
		log.info("{} 객체 소멸",getClass().getSimpleName());
	}
	
	
	// case1 : 의존객체를 new 키워드로 직접 생성.(결합력 최상)
//	private SampleDAO dao = new SampleDAOImpl_Oracle();
//	private SampleDAO dao = new SampleDAOImpl_Postgre();
//	case2 : Factory Object[Method] Pattern (factory객체가 모든 결합력 떠안는다)
//	private SampleDAO dao = SampleDAOFactory.getSampleDAO();
//	case3 : Strategy Pattern, 전략 주입자가 필요함. (전략 주입자가 결합력 떠안는다 -> 외부 컨테이너로 사용하여 결합력 이동)
	@javax.annotation.Resource(name="daoOracle")
	private SampleDAO dao;
	
//	@Autowired
	public SampleServiceImpl(SampleDAO dao) {
		super();
		this.dao = dao;
		log.info("{} 객체 생성 및 전략 객체({}) 주입", getClass().getSimpleName(), dao.getClass().getSimpleName());
	}
//	case4 : DI Container : 객체 형성 의존코드가 xml로 빠져나감. 기존 어플리케이션 건드릴 필요 없어진다. 수정하더라도 java코드 안건드림.
	
//	생성자 주입
	public SampleServiceImpl() {
		super();
		log.info("기본 생성자로 {} 객체 생성", getClass().getSimpleName());
	}
	
	public void setDao(SampleDAO dao) {
		this.dao = dao;
		log.info("{} 객체 생성 후 setter를 통해 {} 주입받음"
				, getClass().getSimpleName()
				, dao.getClass().getSimpleName());
	}
	
	@Override
	public StringBuffer retrieveInformation(String pk) {
		String data = dao.seletRawData(pk);
		StringBuffer information = new StringBuffer();
		information.append(
			String.format("%tc에 조회된 데이터를 가공함 - %s", Calendar.getInstance() , data)
		);
		return information;
	}

}
