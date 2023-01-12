package kr.or.ddit.member.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.AbstractTestCase;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

//Mock request

@Slf4j
public class MemberServiceTest extends AbstractTestCase {
	@Inject
	private MemberService service;
	
	@Test
	public void testInit() {
		
	}
	
	@Test
	public void testCreateMember() {
	}
	@Test
	public void testRetrieveMemberList() {
	}
	@Test
	public void testRetrieveMember() {
	}
	
	
	
	
	
	
	
	
	MemberVO member;
	@Before
	public void setUp() throws Exception {
		member = new MemberVO();
		member.setMemId("a002");
		member.setMemPass("java");
		member.setMemName("신규");
		member.setMemBir("2000-01-01");
		member.setMemZip("00000");
		member.setMemAdd1("주소1");
		member.setMemAdd2("주소2");
		
	}
	
	
	@Test
	public void retrieveMember(String memId) {
		MemberVO mv = service.retrieveMember("c001");
		log.info("c001 : {}", mv);
	}
	
	@Test
	public void testInsertMember() {
		service.createMember(member);
	}
	
	@Test
	public void modifyMember() {
		member.setMemName("수정이름");
		service.modifyMember(member);
		log.info("수정멤버이름 : {}", member.getMemName());
	}
	
	@Test
	public void removeMember() {
		service.removeMember(member);
	}

}
