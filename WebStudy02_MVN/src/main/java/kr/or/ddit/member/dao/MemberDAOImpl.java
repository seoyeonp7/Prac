package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	

	@Override
	public int insertMember(MemberVO member) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); //트랜잭션(ACID) 시작
		){
			//네임 오타, 시그니쳐 제약 생겨서 실수 방지
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapperProxy.insertMember(member);
//				int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo",memo);
			sqlSession.commit(); //트랜잭션 종료
			return rowcnt;
		}
	}

	@Override
	public List<MemberVO> selectMemberList() {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			return mapperProxy.selectMemberList();
		}
	}

   @Override
   public MemberVO selectMember(String memId) {
	   try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			return mapperProxy.selectMember(memId);
//				return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList");
		}
   }

   @Override
   public int updateMember(MemberVO member) {
	   try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); 
		){
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapperProxy.updateMember(member);
			sqlSession.commit(); //트랜잭션 종료
			return rowcnt;
		}
   }

   @Override
   public int deleteMember(String memId) {
	   try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapperProxy.deleteMember(memId);
			sqlSession.commit(); //트랜잭션 종료
			return rowcnt;
		}
   }

}