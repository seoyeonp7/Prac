package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	private MemberDAO memberDAO = new MemberDAOImpl();
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Override 
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = memberDAO.selectMember(member.getMemId());
		if(savedMember==null || savedMember.isMemDelete())
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass(); 
		
		ServiceResult result = null;
		if(encoder.matches(inputPass, savedPass)) {
			//콜바이레퍼런스
//			member.setMemName(savedMember.getMemName());
			
			try {
				BeanUtils.copyProperties(member, savedMember);
				result = ServiceResult.OK;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			} 
			
		} else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}

}
