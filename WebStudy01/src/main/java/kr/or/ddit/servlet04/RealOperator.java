package kr.or.ddit.servlet04;

@FunctionalInterface // 람다식을 쓸 수 있게되는 구조(생략가능)
public interface RealOperator {
	public int operate(int leftOp, int rightOp);
}
