package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 요청 파라미터(request parameter) 중 특정 파라미터(value) 하나의 값을 획득하기 위한 설정.
 *  ex) @RequestParam("who") : request.getParameter("who"), req.getParameterValues("who")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) //메소드의 파라미터에만 사용 가능한 어노테이션
public @interface RequestParam {
	String value();
	boolean required() default true;
	String defaultValue() default "";
}
