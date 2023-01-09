package kr.or.ddit.servlet01;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿
 * 		: 웹상에서 발생하는 요청을 처리하고, 그에 따른 동적 응답을 생성하기 위한 객체의 필요요건(명세, specification)
 *   개발 단계
 *   1. HttpServlet 상속
 *   2. callback 메소드 재정의
 *	 3. 컴파일 : /WEB-INF/classes (context의 최우선 classpath) 에 배포, 
 *	 4. WAS(Servlet Container) 에 등록, 
 *		 : web.xml -> servlet -> servlet-name, servlet-class (2.X)
 *		 : @WebServlet (3.X)
 *   5. 서블릿 매핑 설정
 *   	 : web.xml -> servlet-mapping -> servlet-name, url-pattern (2.X)
 *   	 : @WebServlet(속성들) (3.X)
 *   6. 컨테이너 재구동
 *   
 *   ** servlet container?
 *   	 : 컨테이너에 의해 관리되는 서블릿 객체의 생명 주기 제어.
 *   서블릿 객체의 생성 : 구체적인 설정(loadOnStartup)이 없는 한, 매핑된 조건의 요청이 발생했을 때 생성.
 *   
 *   ** 서블릿에서 재정의할 수 있는 콜백 메소드 종류.
 *   생명주기 이벤트
 *   	생성 : init
 *   	소멸 : destroy
 *   요청 관련 이벤트 : service, doXXX
 *   callback : 관련 이벤트가 발생했을 때 시스템 내부적으로 자동호출 되는 코드의 형태.(ex.jquery)
 *   $("button").on("click", function(){
 *   	// 실행코드
 *   })
 */
@WebServlet(value="/desc.do", loadOnStartup=1)
public class DescriptionServlet extends HttpServlet {
	public DescriptionServlet() {
		super();
		System.out.printf("%s 생성\n", this.getClass().getName());
	}
	
	@Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			System.out.printf("%s 서블릿 인스턴스 초기화\n", this.getClass().getSimpleName());
		}
		
	@Override
		public void destroy() {
			System.out.printf("%s 서블릿 인스턴스 소멸\n", this.getClass().getSimpleName());
		}
	
	@Override // service는 do계열의 메서드를 가져오는 것
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			System.out.println("==========service 시작==========");
			super.service(req, resp); // doGet실행
			System.out.println("==========service 종료==========");
		}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println(String.format("discription-%s", Thread.currentThread().getName()));
		System.out.println("doGet 실행");
	}
}
