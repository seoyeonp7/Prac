package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@link HttpServletRequest}, {@link HttpSession}, {@link Principal} 타입의 핸들러 메소드 인자 해결.
 *
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		boolean support = HttpServletRequest.class.equals(parameterType)
							||
						  HttpSession.class.equals(parameterType)
						  	||
						  Principal.class.isAssignableFrom(parameterType); //parameterType이 Principal의 하위객체인지 찾는다
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//컨트롤러가 필요한 자원은 거의 리퀘스트에 있으므로 아규먼트 거의 다 여기에서 꺼냄
		Class<?> parameterType = parameter.getType();
		Object argumentObject = null;
		if(HttpServletRequest.class.equals(parameterType)) {
			argumentObject = req;
		} else if(HttpSession.class.equals(parameterType)) {
			argumentObject = req.getSession();
		} else {
			argumentObject = req.getUserPrincipal();
		}
		return argumentObject;
	}
}
