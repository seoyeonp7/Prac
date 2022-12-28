package kr.or.ddit.object;

public class StringPlayground {
	public static void main(String[] args) {
		String str1 = "SAMPLE"; // 상수공간, 같은데이터 2번 이상 올리지 않는다.
		str1 = str1 + "append"; // 메모리 공간 3개 소요, SAMPLE, APPEND, SAMPLEAPPEND -> 권장x
		String str2 = "SAMPLE";
		String str3 = new String("SAMPLE");
		String str4 = new String(str1);
		
		StringBuffer original = new StringBuffer("SAMPLE"); //heap메모리 공간
		original.append("append");
		
		System.out.printf("str1 == str2 : %b \n",str1 == str2); //true
		System.out.printf("str2 == str3 : %b \n",str2 == str3); //false
		System.out.printf("str3 == str4 : %b \n",str3 == str4); //false
		System.out.printf("str1 == str4 : %b \n",str1 == str4); //false
		System.out.printf("str1 == str4 : %b \n",str1 == str4.intern()); //true
		System.out.printf("str1 == str4 : %b \n",str4 == str2.intern()); //true
		System.out.printf("str1 == str4 : %b \n",str4 == str3.intern()); //true
		
		//vm으로 주소값 비교하기 미션
		
	}
}
