package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MemberVO;

/**
 * 회원관리(CRUD)를 위한 Persistence Layer
 *
 */
public interface MemberDAO {
	/**
	 * 회원 신규 등록
	 * @param member
	 * @return 등록된 레코드 수 (rowcnt) > 0: 성공, <= 0 : 실패
	 */
	public int insertMember(MemberVO member);
	/**
	 * 회원 목록 조회
	 * @return size == 0인 경우, 조건에 맞는 레코드 없음.
	 */
	public List<MemberVO> selectMemberList();
	/**
	 * 회원 상세 조회
	 * @param memId
	 * @return 조건에 맞는 레코드 없는 경우, null 반환
	 */
	public MemberVO selectMember(@Param("memId") String memId);
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return 수정된 레코드 수(rowcnt) > 0 : 성공, <= 0 : 실패
	 */
	public int updateMember(MemberVO member);
	/**
	 * 회원 정보 삭제(?)
	 * @param memId
	 * @return 삭제된 레코드 수 (rowcnt) > 0 : 성공, <= 0 실패
	 */
	public int deleteMember(@Param("memId") String memId);
}
