package kr.or.ddit.prod.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdInsertConstroller{
	ProdService service = new ProdServiecImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodInsert.do")
	public String prodForm(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodInsert.do",method=RequestMethod.POST)
	public String insertProcess(
		HttpServletRequest req
		, @ModelAttribute("prod") ProdVO prod //command object
	) {
		addAttribute(req);
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		String viewName = null;
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK==result) {
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
			} else {
				req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
				viewName = "prod/prodForm";
			}
		} else {
			viewName = "prod/prodForm";
			errors.forEach((k,v)->{
				log.error("{} : {}", k, v);				
			});
		}
		return viewName;
	}
}
