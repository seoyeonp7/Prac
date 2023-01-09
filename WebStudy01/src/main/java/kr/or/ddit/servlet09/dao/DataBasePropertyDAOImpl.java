package kr.or.ddit.servlet09.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAOImpl implements DataBasePropertyDAO {

   @Override
   public List<DataBasePropertyVO> selectPropertyList(String propertyName) {
      // driver class loading : oracle.jdbc.driver.OracleDriver
         StringBuffer sql = new StringBuffer();
         sql.append(" SELECT PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION ");
         sql.append(" FROM DATABASE_PROPERTIES ");
         if(StringUtils.isNotBlank(propertyName)) {
             sql.append(" WHERE PROPERTY_NAME = ? "); //?자체로 값이라서 그 안에 연산자를 사용할 수 없다!
          }
     
      try(  
          Connection conn = ConnectionFactory.getConnection();//db계정 정보가 바뀌더라도 얘는 바꿀 필요 없음!
    	  PreparedStatement pstmt = conn.prepareStatement(sql.toString());
//         Statement stmt = conn.createStatement(); // 결정적인 쿼리는 여기서 결정됨~!
      ){
//         ResultSet rs = stmt.executeQuery(sql.toString());
    	  if(StringUtils.isNotBlank(propertyName)) {
//    		  pstmt.setInt(1, 34); 숫자만 가능
	    	  pstmt.setString(1, propertyName); //문자도 가능
    	  }
    	  ResultSet rs = pstmt.executeQuery();
          List<DataBasePropertyVO> list = new ArrayList<>();
          while(rs.next()){
             DataBasePropertyVO vo = new DataBasePropertyVO();
             list.add(vo);
             vo.setPropertyName(rs.getString("PROPERTY_NAME"));
             vo.setPropertyValue(rs.getString("PROPERTY_VALUE"));
             vo.setDescription(rs.getString("DESCRIPTION"));
          }
         return list;
      }catch(SQLException e){
         throw new RuntimeException(e);
      }
   }

}