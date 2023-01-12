package kr.or.ddit.prod.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;

/**
 * 상품 상세 조회시, 해당 거래처의 모든 정보 함께 조회함.
 * 상품 상세 조회시, 구매자 리스트(회원아이디, 회원명, 휴대폰, 이메일, 마일리지) 함께 조회.
 * 분류명도 함께 조회함.
 */
@RequiredArgsConstructor
@Controller
public class ProdViewController {
	
	private final ProdService service;
	
	//경로 변수로 대체(path변수 식별자 노출 안되므로 같게할 수 있고 따라서 생략도 가능)
	@RequestMapping("/prod/{prodId}")
	public String prodView(
		@PathVariable String prodId
		, Model model
	) throws IOException, ServletException {

		ProdVO prod = service.retrieveProd(prodId);
		
		model.addAttribute("prod", prod);

		return "prod/prodView"; // logical view name
	}
}
