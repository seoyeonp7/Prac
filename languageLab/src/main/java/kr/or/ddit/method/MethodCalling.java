package kr.or.ddit.method;

public class MethodCalling {
	public static void main(String[] args) {
		int number = 10;
		test1(number);
		System.out.println(number);
		//callByValue
		
		StringBuffer sb = new StringBuffer("original");
		test2(sb);
		sb.append("dkdk");
		System.out.println(sb);
		//callByReference
	}
	
	private static void test1(int value) {
		value = value+1;
	}
	private static void test2(StringBuffer sb) {
		sb.append("append");
	}

}
