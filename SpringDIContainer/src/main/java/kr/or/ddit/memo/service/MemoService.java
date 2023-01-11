package kr.or.ddit.memo.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.memo.dao.FileSystemMemoDAOimpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.vo.MemoVO;

@Service
public class MemoService{ //controller가 아닌 service라 가정하자
	
	private MemoDAO dao;
	
	@Inject
	public MemoService(MemoDAO dao) {
		super();
		this.dao = dao;
	}

	public List<MemoVO> retrieveMemoList(){
		return dao.selectMemoList();
		
	}
}
