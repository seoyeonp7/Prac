package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 * Backend controller(command handler) --> POJO(Plain Old Java Object) : 제약x
 */
public class MemberInsertController implements AbstractController{
	MemberService service = new MemberServiceImpl();
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		RequestMethod requestMethod = RequestMethod.valueOf(method.toUpperCase());
		String viewName = null;
		if(requestMethod==requestMethod.GET) {
			viewName = memberForm(req, resp);
		}else if(requestMethod==requestMethod.POST){
			viewName = insert(req, resp);
		}else {
			resp.sendError(405,method +"는 지원하지 않음");
		}
		return viewName;
	}
	
	
	public String memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/member/memberForm";
		
	}
	
	public String insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 검증 포함
		req.setCharacterEncoding("UTF-8");
		
		//command object - 검증 대상
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);

		Map<String, String[]> parameterMap = req.getParameterMap();
		
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		//MemberVO가 넘어온 모든 파라미터 갖게 됨
		
		//command object : 검증대상**
//		MemberVO target = new MemberVO();
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors",errors);
		
		String viewName = null;
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
				case PKDUPLICATED:
					req.setAttribute("message", "아이디 중복");
					viewName = "member/memberForm";
					break;
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
