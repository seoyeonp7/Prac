package kr.or.ddit.prod.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.controller.MemberUpdateController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdUpdateController {
	ProdService service = new ProdServiecImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateController.class);
	
	@RequestMapping("/prod/prodUpdate.do")
	public String prodForm() {
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodUpdate.do",method=RequestMethod.POST)
	public String updateProcess(
		HttpServletRequest req
		, @ModelAttribute("prod") ProdVO prod
		, @RequestParam("prodId") String prodId
	) {
		String viewName = null;
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		if(valid) {
			prod.setProdId(prodId);
			ServiceResult result = service.modifyProd(prod);
			switch (result) {
				case FAIL:
					req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
					viewName = "member/memberForm";
					break;

				default:
					viewName = "redirect:/";
					break;
			}
		} else {
			viewName = "member/memberForm";
			errors.forEach((k,v)->{
				log.error("{} : {}", k, v);				
			});
		}
		return viewName;
	}
}
