package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlindFilter implements Filter{

	private Map<String, String> blindMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("{} 초기화",this.getClass().getName());
		blindMap = new LinkedHashMap<>();
		blindMap.put("127.0.0.1","나니까 블라인드");
		blindMap.put("0:0:0:0:0:0:0:1","나니까 블라인드");
		blindMap.put("192.168.35.47","나니까 블라인드");
		blindMap.put("192.168.35.29","하예 블라인드");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("blind filter 동작시작");

		//1.클라이언트의 ip adr
		String ipAddress=request.getRemoteAddr();
		System.out.println("접속IP : "+ipAddress); //**0:0:0:0:0:0:0:1
		
		//2.ip adr로 블라인드 대상자인지 판단
		//3.블라인드 대상자 아니면 정상적 서비스 이용하게
		if(blindMap.containsKey(ipAddress)) {
			String reason = blindMap.get(ipAddress);
			String message = String.format("당신은 %s 사유로 블라인드 처리 됐씀다.", reason);
			request.setAttribute("message", message);
			
			String viewName ="/WEB-INF/views/commons/messageView.jsp";
			request.getRequestDispatcher(viewName).forward(request, response);
		} else {
			System.out.println("인증성공");
			chain.doFilter( request, response );
		}
		//4.맞으면 통과 시키지 말고 블라인드 사유 알려주는 메시지
		
		log.info("blind filter 동작종료");
	}

	@Override
	public void destroy() {
		log.info("{} 소멸",this.getClass().getName());
	}
}
