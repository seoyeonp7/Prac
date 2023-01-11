package kr.or.ddit.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.dao.SampleDAO;

@Service
public class SampleService {
	
	private SampleDAO dao;
	
	@Inject
	public SampleService(SampleDAO dao) {
		super();
		this.dao = dao;
	}
	
	public String retrieveInfo() {
		return "info " + dao.selectData();
	}
}
