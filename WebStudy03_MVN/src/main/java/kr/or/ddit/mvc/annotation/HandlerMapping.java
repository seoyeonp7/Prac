package kr.or.ddit.mvc.annotation;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	/**
	 * 현재 요청의 조건(RequestMappingCondition)에 맞는 핸들러(핸들러 객체 + 핸들러 메소드 : RequestMappingInfo) 정보를 검색.
	 * @param request
	 * @return 
	 */
	public RequestMappingInfo findCommandHandler(HttpServletRequest request);
}
