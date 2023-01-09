package kr.or.ddit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //기본생성자
public class SearchVO {
	private String searchType;
	private String searchWord;
	
}
