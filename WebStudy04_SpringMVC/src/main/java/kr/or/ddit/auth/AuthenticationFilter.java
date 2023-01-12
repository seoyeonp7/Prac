package kr.or.ddit.auth;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 보호자원에 대한 요청인 경우, 신원 확인(session authmember 속성)을 한 사용자인지 판단 
 *
 */
@Slf4j
public class AuthenticationFilter implements Filter{
	
	private Map<String, String[]> securedResources;
	public static final String SECUREDNAME ="securedResources";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		securedResources = new LinkedHashMap<String, String[]>();
		filterConfig.getServletContext().setAttribute(SECUREDNAME, securedResources);
		String filterPath = filterConfig.getInitParameter("filePath"); //web.xml
		try(
			InputStream is = this.getClass().getResourceAsStream(filterPath);
		){
			Properties props = new Properties();
			props.load(is);
			props.keySet().stream()
					.map(Object::toString)
//					.collect(Collectors.toList())
					.forEach(key->{
						String value = props.getProperty(key);
						securedResources.put(key, value.split(","));
						log.info("보호자원 [{} : {}]",key,securedResources.get(key));
					});
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String uri = req.getServletPath();
		
		boolean pass = true;
		if(securedResources.containsKey(uri)) {
			Principal principal = req.getUserPrincipal();
			if(principal==null) {
				pass = false;
			}
		}
		
		if(pass) {
			//보호자원 아니거나 보호자원+신원확인 된 케이스
			chain.doFilter( request, response );
		} else {
			//보호자원이면서 신원확인 안 된 케이스 -> loginForm으로 이동
			//인증시스템에서는 무조건 redirect시킨다
			String viewName =req.getContextPath()+"/login/loginForm.jsp";
			resp.sendRedirect(viewName);
		}
	}

	@Override
	public void destroy() {

	}
	
}
