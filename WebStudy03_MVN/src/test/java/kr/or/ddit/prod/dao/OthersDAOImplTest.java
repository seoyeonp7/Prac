package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import kr.or.ddit.vo.BuyerVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OthersDAOImplTest {
	OthersDAO dao = new OthersDAOImpl();

	
	@Test
	public void testSelectLprodList() {
		List<Map<String, Object>> list = dao.selectLprodList();
		list.stream().forEach(System.out::println);
	}
	
	@Test
	public void testSelectBuyerList() {
		//분류값에 해당하는 BuyerVO 리스트
		List<BuyerVO> list = dao.selectBuyerList(null);
		list.stream().forEach(System.out::println);
		list = dao.selectBuyerList("P101");
		list.stream().forEach(System.out::println);
	}

}
