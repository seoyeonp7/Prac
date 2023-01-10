package kr.or.ddit.memo.dao;

import java.util.List;

import kr.or.ddit.vo.MemoVO;

public interface MemoDAO {
	public List<MemoVO> selectMemoList();
	public int insertMemo(MemoVO memo); // 신규로 작성, 코드값 필요 x
	public int updateMemo(MemoVO memo); // 프라이머리키 역할하는 코드값 필요 o
	public int deleteMemo(int code);
}
