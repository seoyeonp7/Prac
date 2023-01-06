package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiecImpl implements ProdService {
	private ProdDAO prodDAO = new ProdDAOImpl();
	
	@Override
	public void retrieveProdList(PagingVO<ProdVO> pagingVO) {
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> dataList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(dataList);
	}
	
	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)
			throw new RuntimeException(String.format("%s 는 없는 상품",prodId));
		return prod;
	}

	@Override
	public ServiceResult createProd(ProdVO prodVO) {
		ServiceResult result = null;
		
		int rowcnt = prodDAO.insertProd(prodVO);
		return result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		//존재하지 않는 경우, RuntimeException 발생. OK, FAIL
		retrieveProd(prod.getProdId());
		
		//update
		int rowcnt = prodDAO.updateProd(prod);
		return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
}
