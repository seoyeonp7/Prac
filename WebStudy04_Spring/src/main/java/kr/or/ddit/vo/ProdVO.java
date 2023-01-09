package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.validate.UpdateGroup;
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
	private int rnum;
	@NotBlank(groups=UpdateGroup.class)
	private String prodId;
	@NotBlank
	private String prodName;
	@NotBlank
	private String prodLgu;
	private String lprodNm;
	@NotBlank
	private String prodBuyer;
	private BuyerVO buyer; // has a
	@NotNull
	@Min(0)
	private Integer prodCost;
	@NotNull
	@Min(0)
	private Integer prodPrice;
	@NotNull
	@Min(0)
	private Integer prodSale;
	@NotBlank
	private String prodOutline;
	private String prodDetail;
	@NotBlank
	private String prodImg; // PROD 테이블 조회용 프로퍼티
	
	private MultipartFile prodImage;
	
	@NotNull
	@Min(0)
	private Integer prodTotalstock;
	
	private String prodInsdate;
	@NotNull
	@Min(0)
	private Integer prodProperstock;
	private String prodSize;
	private String prodColor;
	private String prodDelivery;
	private String prodUnit;
	private Integer prodQtyin;
	private Integer prodQtysale;
	private Integer prodMileage;
//	private Integer cnt;
	
	private Set<MemberVO> memberSet; // has many, 중복불가set
	
	private int memCount;
	
}
