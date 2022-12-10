package test;

import java.util.*;

public class DivisorTest {
	public static void main(String[] args) {
        int answer = 0;
        int n = 0;
        int sqrt = (int)Math.sqrt(n);
        List<Integer> list = new ArrayList();

        for(int i=1;i<=sqrt;i++){
            if(n%i==0){
                list.add(i);
                if(n/i!=i){
                    list.add(n/i);
                }
            }
        }
//        Collections.sort(list);
//        Iterator<Integer> iter = list.iterator();
//        while(iter.hasNext()){
//            int a = iter.next();
//            answer +=a;
//        }
        answer = list.stream().mapToInt(i->i).sum();

    }
}
