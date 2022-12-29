package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 상품 하나의 정보(분류명, 거래처 정보 포함)를 담기 위한 객체
 * PROD(1) : BUYER(1) -> has a  
 */
@Data
@EqualsAndHashCode(of="prodId")
@ToString(exclude="prodDetail") //부하가 걸릴만한 케이스 제외
public class ProdVO implements Serializable{
	private String prodId;
	private String prodName;
	
	private String prodLgu;
	private String lprodNm;
	
	private String prodBuyer;
	private BuyerVO buyer; // has a
	
	private Integer prodCost;
	private Integer prodPrice;
	private Integer prodSale;
	private String prodOutline;
	private String prodDetail;
	private String prodImg;
	private Integer prodTotalstock;
	private String prodInsdate;
	private Integer prodProperstock;
	private String prodSize;
	private String prodColor;
	private String prodDelivery;
	private String prodUnit;
	private Integer prodQtyin;
	private Integer prodQtysale;
	private Integer prodMileage;
	
	private Set<MemberVO> memberSet; // has many, 중복불가set
}
