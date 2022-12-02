package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		/* ArrayList */
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));

        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()){
            int num = iter.next();
            System.out.println("iter값 : " + num);
        }

        Stream<Integer> stream = list.stream();
        stream.forEach(num-> System.out.println("stream값 : " + num));

        /* String 배열 */
        String[] strArray = { "홍길동", "이순신", "임꺽정"};
        Stream<String> strStream = Arrays.stream(strArray);
        strStream.forEach(a-> System.out.println(a));

        /* int 배열 */
        int[] intArray = {1,2,3,4,5};
        IntStream intStream = Arrays.stream(intArray);
        intStream.forEach(a-> System.out.println(a));
	}
}
