package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/prod/prodUpdate.do")
public class ProdUpdateController {
	private final ProdService service;
	private final OthersDAO othersDAO;
	
	
	
	@ModelAttribute("lprodList")
	public List<Map<String, Object>> lprodList() {
		return othersDAO.selectLprodList();
	}
	
	@ModelAttribute("buyerList")
	public List<BuyerVO> buyerList() {
		return othersDAO.selectBuyerList(null);
	}
	
	@GetMapping
	public String updateForm(
		Model model
		, @RequestParam("what") String prodId
	) {
		ProdVO prod = service.retrieveProd(prodId);
		model.addAttribute("prod", prod);
		return "prod/prodForm";
	}
	
	@PostMapping
	public String updateProd(
		Model model
		, @Validated(UpdateGroup.class) @ModelAttribute("prod") ProdVO prod
		, BindingResult errors
	) throws IOException {
		String viewName = null;

		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyProd(prod);
			switch (result) {
				case FAIL:
					model.addAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
					viewName = "prod/prodForm";
					break;
				default:
					viewName = "redirect:/prod/"+prod.getProdId();
					break;
			}
		} else {
			viewName = "prod/prodForm";
		}
		return viewName;
	}
}
