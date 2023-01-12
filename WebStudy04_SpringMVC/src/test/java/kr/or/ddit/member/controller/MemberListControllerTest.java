package kr.or.ddit.member.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextHierarchy({
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*-context.xml")
	, @ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
})
@WebAppConfiguration
public class MemberListControllerTest {
	
	@Inject
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testMemberList() throws Exception {
		mockMvc.perform(get("/member/memberList.do").param("page", "2"))
				.andExpect(status().isOk()) //andExpect 컨트롤러의 실행결과 매칭 , status 상태코드 매칭
				.andExpect(model().attributeExists("pagingVO")) //model 모델 만들어졌는지 확인
				.andExpect(view().name("member/memberList")) // view 뷰 테스트, name 로지컬뷰네임 확인
				.andDo(log()); //보고서 형태로
		//컨트롤러 예외 적극적으로 처리하지 않는다.=>던짐
	}
}
