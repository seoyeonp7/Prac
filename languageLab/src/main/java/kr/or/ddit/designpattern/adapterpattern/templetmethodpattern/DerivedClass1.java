package kr.or.ddit.designpattern.adapterpattern.templetmethodpattern;

public class DerivedClass1 extends TemplateClass {
	@Override
	protected void stepTwo() {
		System.out.println("A 방식으로 구현된 2단계");
		
	}
}
