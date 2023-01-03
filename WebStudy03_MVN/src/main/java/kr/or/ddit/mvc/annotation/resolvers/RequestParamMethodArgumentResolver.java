package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * @RequestParam 을 가지고 있으며, 기본형 타입인 인자를 해결.
 *
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		boolean support = requestParam!=null
					&&
				(
					parameterType.isPrimitive()
					||
					String.class.equals(parameterType)
					||
					(
						parameterType.isArray() 
						&& 
						(
							parameterType.getComponentType().isPrimitive() 
							//int나 boolean배열
							|| 
							parameterType.getComponentType().equals(String.class)
						)
					)
				);
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		
		String requestParameterName = requestParam.value();
		boolean required = requestParam.required();
		String defaultValue = requestParam.defaultValue();
		
		String[] requestParameterValues = req.getParameterValues(requestParameterName);
		if(required && (requestParameterValues == null 
				|| requestParameterValues.length==0
				|| StringUtils.isBlank(requestParameterValues[0])
				)) { //필수 파라미터가 안 들어옴 -> 400에러
			throw new BadRequestException(requestParameterName + " 이름의 필수 파라미터 누락"); //unchecked
		}
		if(requestParameterValues==null||requestParameterValues.length==0 ||StringUtils.isBlank(requestParameterValues[0])) {
			requestParameterValues = new String[] {defaultValue};
		}
		
		Object argumentObject = null;
		
		if(parameterType.isArray()) {
			Object[] argumentObjects = new Object[requestParameterValues.length];
			for(int i=0;i<argumentObjects.length;i++) {
				argumentObjects[i] = 
						singleValueGenerate(parameterType.getComponentType(), requestParameterValues[i]);
			}
			argumentObject = argumentObjects;
		}else {
			argumentObject = singleValueGenerate(parameterType, requestParameterValues[0]);
		}
		return argumentObject;
	}
	
	private Object singleValueGenerate(Class<?> singleValueType, String requestParameter) {
		Object singleValue = null;
		if(int.class.equals(singleValueType)) {
			singleValue = Integer.parseInt(requestParameter);
		} else if(boolean.class.equals(singleValueType)) {
			singleValue = Boolean.parseBoolean(requestParameter);
		} else { //String
			singleValue = requestParameter;
		}
		return singleValue;
	}
	
	public static class BadRequestException extends RuntimeException{
		public BadRequestException(String message) {
			super(message);
		}
	}
}
