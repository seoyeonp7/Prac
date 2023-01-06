package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.controller.MemberUpdateController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiecImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdUpdateController {
	ProdService service = new ProdServiecImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateController.class);
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodUpdate.do")
	public String updateForm(
		HttpServletRequest req
		, @RequestParam("what") String prodId
	) {
		addAttribute(req);
		ProdVO prod = service.retrieveProd(prodId);
		req.setAttribute("prod", prod);
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodUpdate.do",method=RequestMethod.POST)
	public String updateProd(
		HttpServletRequest req
		, @ModelAttribute("prod") ProdVO prod
		, @RequestPart(value="prodImage",required=false) MultipartFile prodImage
	) throws IOException {
		addAttribute(req);
		
		prod.setProdImage(prodImage);
		
		String saveFolerURL = "/resources/prodImages"; //논리적 경로
		ServletContext application = req.getServletContext();
		String saveFolerPath = application.getRealPath(saveFolerURL);
		File saveFolder = new File(saveFolerPath);
		if(!saveFolder.exists())
			saveFolder.mkdirs();
		
		prod.saveTo(saveFolder);
		
		String viewName = null;
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);
		if(valid) {
			ServiceResult result = service.modifyProd(prod);
			switch (result) {
				case FAIL:
					req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
					viewName = "prod/prodForm";
					break;

				default:
					viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
					break;
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
