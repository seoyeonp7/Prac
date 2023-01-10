package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoVO implements Serializable {
	private Integer code;
	private String writer;
	private String content;
	private String date;
	
}
