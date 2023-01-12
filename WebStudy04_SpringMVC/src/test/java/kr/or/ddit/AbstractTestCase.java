package kr.or.ddit;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // 1. Junit Context 구성 (메타어노테이션으로 쓸 수 없다.)
@RootContextConfiguration 
public abstract class AbstractTestCase {

}
