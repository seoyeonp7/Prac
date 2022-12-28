package kr.or.ddit.object;

public class Child extends Parent {
	@Override
	public void method() {
		System.out.println("child method execute");
	}
	
	@Override
	public void template() {
		method(); // = this.method(); -> super.method() 쓰면 Parent 메서드 실행된다.
	}

}
