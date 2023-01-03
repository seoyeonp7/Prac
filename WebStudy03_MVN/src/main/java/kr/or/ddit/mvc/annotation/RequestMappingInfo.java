package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.ToString;

@Getter //수정할 수 없음 immutable객체
@ToString
public class RequestMappingInfo {
	private RequestMappingCondition mappingCondition;
	private Object commandHandler;
	private Method handlerMethod;
	
	//기본생성자 사라짐
	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handlerMethod = handlerMethod;
	}
	
}
