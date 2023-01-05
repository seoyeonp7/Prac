package kr.or.ddit.prod.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품 상세 조회시, 해당 거래처의 모든 정보 함께 조회함.
 * 상품 상세 조회시, 구매자 리스트(회원아이디, 회원명, 휴대폰, 이메일, 마일리지) 함께 조회.
 * 분류명도 함께 조회함.
 */
@Controller
public class ProdViewController {
	ProdService service = new ProdServiecImpl();
	
	@RequestMapping("/prod/prodView.do")
	public String prodView(@RequestParam("what") String prodId, HttpServletRequest req) throws IOException, ServletException {

		ProdVO prod = service.retrieveProd(prodId);
		
		String prodImg = prod.getProdImg();
		HttpSession session = req.getSession();
		String saveFolerURL = "/resources/prodImages";
		ServletContext application = req.getServletContext();
		String saveFolerPath = application.getRealPath(saveFolerURL);
		String saveFileURL = saveFolerURL + "/" + prodImg;
					
				
		session.setAttribute("fileMetadata", saveFileURL);
		
		req.setAttribute("prod", prod);

		return "prod/prodView"; // logical view name
	}
}
