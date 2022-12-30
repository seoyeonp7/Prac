package kr.or.ddit.member.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class memberServiceTest {
	private MemberService service = new MemberServiceImpl();
	private MemberVO member;

	@Before
	public void setUp() throws Exception {
		member = new MemberVO();
//		member.setMemId("a001");
	}

	@Test
	public void testCreateMember() { 
		ServiceResult result = service.createMember(member);
//		result == ServiceResult.OK
//		result == ServiceResult.FAIL
//		result == ServiceResult.PKDUPLICATED
	}

	@Test
	public void testRetrieveMemberList(PagingVO<MemberVO> pagingVO) {
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		assertNotEquals(0, memberList.size());
	}

	@Test
	public void testRetrieveMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveMember() {
		fail("Not yet implemented");
	}

}
