package kr.or.ddit.prod.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
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
		, @RequestPart(value="prodImage",required=false) MultipartFile prodImage
	) throws IOException, ServletException {
		addAttribute(req);
		
		prod.setProdImage(prodImage);
		
		String saveFolerURL = "/resources/prodImages"; //????????? ??????
		ServletContext application = req.getServletContext();
		String saveFolerPath = application.getRealPath(saveFolerURL);
		File saveFolder = new File(saveFolerPath);
		if(!saveFolder.exists())
			saveFolder.mkdirs();
		
		prod.saveTo(saveFolder);

		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		String viewName = null;
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK==result) {
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
			} else {
				req.setAttribute("message", "????????? ?????? ??????. ?????? ?????? ??????.");
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
