package kr.or.ddit.prod.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProdListController{ //POJO
	private ProdService service = new ProdServiecImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	
	private String listUI(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodList";
	}
	
	private String listData(
		int currentPage
		, HttpServletRequest req
	) throws ServletException {
//		String pageParam = req.getParameter("page");
		ProdVO detailCondition = new ProdVO();
		req.setAttribute("detailCondition", detailCondition);
		
		try {
			BeanUtils.populate(detailCondition, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
//		int currentPage = 1;
//		if(StringUtils.isNumeric(pageParam)) {
//			currentPage = Integer.parseInt(pageParam);
//		}
		
		PagingVO<ProdVO> pagingVO = new PagingVO<>(5,5);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setDetailCondition(detailCondition);
		
		service.retrieveProdList(pagingVO);
		req.setAttribute("pagingVO", pagingVO);
		return "forward:/jsonView.do";
	}
	
	@RequestMapping("/prod/prodList.do")
	public String prodList(
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage
		, HttpServletRequest req
	) throws ServletException {
		
		String accept = req.getHeader("Accept");
		String viewName = null;
		if(accept.contains("json")) {
			viewName = listData(currentPage,req);
		}else {
			viewName = listUI(req);
		}
		return viewName;
	}
}