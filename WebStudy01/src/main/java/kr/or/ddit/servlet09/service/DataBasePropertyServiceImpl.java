package kr.or.ddit.servlet09.service;

import java.util.List;

import kr.or.ddit.servlet09.dao.DataBasePropertyDAO;
import kr.or.ddit.servlet09.dao.DataBasePropertyDAOImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyServiceImpl implements DataBasePropertyService {
	private DataBasePropertyDAO dao = new DataBasePropertyDAOImpl();
	
	@Override
	public List<DataBasePropertyVO> retrievePropertyList(String propertyName) {
		List<DataBasePropertyVO> list = dao.selectPropertyList(propertyName);
		list.stream()
			.forEach(System.out::println);//메소드 레퍼런스 구조 함수형 구조로 어떤 메소드를 쓸 것인지 구현한 것!
		
		return list;
	}
}
