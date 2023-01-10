package kr.or.ddit.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

public class ResourceLoaderDesc {
	public static void main(String[] args) {
		//container를 ResourceLoader로 사용
		ApplicationContext context = new GenericXmlApplicationContext("classpath:kr/or/ddit/di/conf/DIContainer-Context.xml");
		Resource resource1 = context.getResource("file:D:/contents/images/cat1.jpg");
		Resource resource2 = context.getResource("classpath:log4j2.xml");
		Resource resource3 = context.getResource("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		
		System.out.printf("file system resoure : %s\n",resource1.getClass().getSimpleName());
		System.out.printf("class path resoure : %s\n",resource2.getClass().getSimpleName());
		System.out.printf("web resoure : %s\n",resource3.getClass().getSimpleName());
		
	}
}
