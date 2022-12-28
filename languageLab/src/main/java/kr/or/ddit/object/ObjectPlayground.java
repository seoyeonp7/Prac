package kr.or.ddit.object;

import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

public class ObjectPlayground {
	public static void main(String[] args) throws Exception {
		String qualifiedName = "kr.or.ddit.object.Parent";
		Class<?> type1 = Parent.class;  //Class타입의 인스턴스에 접근
		Class<?> type2 = Class.forName(qualifiedName);
		//클래스는 힙메모리 아니라 상수메모리에 적재된다. 힙메모리가 아니라는 것은 한번만들면 사라지지 않는다는 것
		//상수메모리-> 같은 클래스에 대해 두번 이상 저장되지 않는다.
		VirtualMachine vm = VM.current();
		System.out.printf("type1 address : %d \n",vm.addressOf(type1));
		System.out.printf("type2 address : %d \n",vm.addressOf(type2));
		
		Parent parent1 = new Parent();
		Object parent2 = type1.newInstance();
		System.out.printf("parent1 address : %d \n",vm.addressOf(parent1));
		System.out.printf("parent2 address : %d \n",vm.addressOf(parent2));
		
		//클래스와 인스턴스의 차이 - 주소값
		
		System.out.printf("parent1 == parent2 : %b \n",parent1 == parent2);
		System.out.printf("parent1.equals(parent2) : %b \n",parent1.equals(parent2));
		
		//equal 연산자와 equals 메소드의 차이점
		
		int number1 = 20;
		int number2 = 20;
		System.out.printf("number1 address : %d \n",vm.addressOf(number1));
		System.out.printf("number2 address : %d \n",vm.addressOf(number2));
		//같은 주소값. 기본형 데이터가 적재되는 공간 상수공간, 변수 값이 같으면 같은 주소값이다.
		
	
		
		StringBuffer sb1 = new StringBuffer("ORIGINAL");
		StringBuffer sb2 = new StringBuffer("ORIGINAL");
		System.out.printf("sb1 address : %d \n",vm.addressOf(sb1));
		System.out.printf("sb2 address : %d \n",vm.addressOf(sb2));
		
		number1 = sample(number1,sb1);
		System.out.printf("number1 : %d \n", number1); //call by value -> 20 (값을 복사해서 넣어줌)
		System.out.printf("sb1 : %s \n", sb1); //call by reference -> original append (값이 아니라 레퍼런스를 갖고 있는 구조, 그 객체의 주소값을 넘겨줌)
		//append 앞에 . 레퍼런스 참조. 
		//call by reference 메소드에서 만들어진 다양한 구조를 다시 반환시킬 수 있음
		
		Child child = new Child();
		child.template(); //child method execute
		
	}
	
	
	private static int sample(int number, StringBuffer sb) {
		number = number + 1;
		sb.append(" APPEND "); //call by reference 쓰면 반환값 필요 없다.
		return number;
	}
}

