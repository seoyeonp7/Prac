package kr.or.ddit.memo.controller;

import java.util.List;

import kr.or.ddit.memo.dao.FileSystemMemoDAOimpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.vo.MemoVO;

public class MemoService{ //controller가 아닌 service라 가정하자
	
	private MemoDAO dao = FileSystemMemoDAOimpl.getInstance();
	
	
	public List<MemoVO> retrieveMemoList(){
		return dao.selectMemoList();
		
	}
}
