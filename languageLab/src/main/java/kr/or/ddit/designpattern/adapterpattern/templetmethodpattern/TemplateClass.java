package kr.or.ddit.designpattern.adapterpattern.templetmethodpattern;

public abstract class TemplateClass {
	//순서가 정해진 메소드 : template method
	public final void template() { 
		stepOne();
		stepTwo();
		stepThree();
	}
	//hook method
	private void stepOne() {
		System.out.println("1단계");
	}
	
	protected abstract void stepTwo();
	
	private void stepThree() {
		System.out.println("3단계");
	}
}
