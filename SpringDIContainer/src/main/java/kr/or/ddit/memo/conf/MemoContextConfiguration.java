package kr.or.ddit.memo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import kr.or.ddit.memo.MemoTestView;
import kr.or.ddit.memo.dao.FileSystemMemoDAOimpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.service.MemoService;

@ComponentScan("kr.or.ddit.memo")
@Lazy
public class MemoContextConfiguration {
//	@Bean
//	@Scope("prototype")
//	public MemoDAO memoDAO() {
//		return new FileSystemMemoDAOimpl();
//	}
//	
//	@Bean
//	public MemoService generateService(MemoDAO dao) {
//		return new MemoService(dao);
//	}
//	
//	@Bean("testView")
//	public MemoTestView testView(MemoService service) {
//		MemoTestView view = new MemoTestView();
//		view.setService(service);
//		return view;
//	}
}
