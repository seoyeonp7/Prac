package kr.or.ddit.prod.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdServiecImpl implements ProdService {
	private final ProdDAO prodDAO;
	
	@Inject
	private WebApplicationContext context;
	private File saveFolder;
	
	@PostConstruct
	public void Init() throws IOException {
		String saveFolerURL = "/resources/prodImages"; //논리적 경로
//		ServletContext application = req.getServletContext();
//		String saveFolerPath = application.getRealPath(saveFolerURL);
		Resource saveFolderRes = context.getResource(saveFolerURL);
		saveFolder = saveFolderRes.getFile();
		if(!saveFolder.exists())
			saveFolder.mkdirs();
	}
	
	@Override
	public void retrieveProdList(PagingVO<ProdVO> pagingVO) {
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> dataList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(dataList);
	}
	
	private void processProdImage(ProdVO prod) {
		try {
			if(1==1) throw new RuntimeException("트랜잭션 관리 여부 확인을 위한 강제 발생 예외");
			prod.saveTo(saveFolder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)
			throw new RuntimeException(String.format("%s 는 없는 상품",prodId));
		return prod;
	}
	
	@Inject
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public ServiceResult createProd(ProdVO prod) {
//		session open
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); 
			//트랜잭션 시작, 닫혔을 때 commit되지 않은 모든 데이터는 롤백된다.
		){
			//롤백 가능
			int rowcnt = prodDAO.insertProd(prod,sqlSession);
			//이진데이터 저장하다 실패해도 수동으로 지워야 하므로 나중에 실행
			processProdImage(prod);
			sqlSession.commit();
			return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		//존재하지 않는 경우, RuntimeException 발생. OK, FAIL
		retrieveProd(prod.getProdId());
		
		//update
		int rowcnt = prodDAO.updateProd(prod);
		processProdImage(prod);
		
		return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
}
