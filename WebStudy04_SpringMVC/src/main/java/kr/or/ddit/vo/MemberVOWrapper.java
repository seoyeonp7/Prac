package kr.or.ddit.vo;

import java.security.Principal;

public class MemberVOWrapper implements Principal{ //adapter
	private MemberVO realMember; //adaptee

	public MemberVOWrapper(MemberVO realMember) {
		super();
		this.realMember = realMember;
	}
	
	public MemberVO getRealMember() {
		return realMember;
	}

	@Override
	public String getName() { //name : 한 사람의 개인이나 한 기관을 대표할 수 있는 식별자, 지금은 memId
		return realMember.getMemId();
	}
}
