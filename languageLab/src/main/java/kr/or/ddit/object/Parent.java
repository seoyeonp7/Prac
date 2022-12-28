package kr.or.ddit.object;

public class Parent {
	
	private String code="defualt"; 
	
	public void method() {
		System.out.println("parent method execute");
	}
	
	public void template() {
		method(); //this.method(); 생략됨. this는 생성된 인스턴스 자체를 의미
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parent other = (Parent) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	//주소로 비교 하는 것 아니고 객체가 가진 상수값(code)로 비교 하겠다는 것.
	
}
