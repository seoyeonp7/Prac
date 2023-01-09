package kr.or.ddit.member.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.CaseUtils;

import kr.or.ddit.db.ConnectionFactory;

public abstract class AbstractJDBCDAO {
	
	public <T> T selectOne(String sql, Class<T> resultType, Object...params) {
		try(
				Connection conn = makeConnection();
				PreparedStatement pstmt = makePreparedStatement(conn, sql);
			){
				queryParameterSetting(pstmt, params);
				ResultSet rs = exeuteQuery(pstmt);
				T resultObject = null;
				if(rs.next()) {
					resultObject = resultBindingForOneRecord(rs, resultType);
				}
				return resultObject;
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public <T> List<T> selectList(String sql, Class<T> resultType, Object...params) {
		try(
			Connection conn = makeConnection();
			PreparedStatement pstmt = makePreparedStatement(conn, sql);
		){
			queryParameterSetting(pstmt, params);
			ResultSet rs = exeuteQuery(pstmt);
			List<T> list = new ArrayList<>();
			while(rs.next()) {
				T recordObject = resultBindingForOneRecord(rs, resultType);
				list.add(recordObject);
			}
			return list;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
		


	// 커넥션 만들기
	private Connection makeConnection() throws SQLException {
		return ConnectionFactory.getConnection();
	}
//	쿼리 객체 생성하기
	private PreparedStatement makePreparedStatement(Connection conn, String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
//	쿼리 실행하기
	private ResultSet exeuteQuery(PreparedStatement pstmt) throws SQLException {
		return pstmt.executeQuery();
	}
	
//	쿼리 파라미터 세팅
	public void queryParameterSetting(PreparedStatement pstmt, Object...params) throws SQLException {
		try {
			if(params.length>0) {
				for(int idx=0; idx<params.length; idx++) {
					Object param = params[idx];
					if(param.getClass().equals(int.class)) {
						pstmt.setInt(idx+1,(Integer) param);
					}else {
						pstmt.setString(idx+1, param.toString());
					}
				}
			}
		}catch(Exception e) {
			throw new SQLException(e);
		}
	}

	public <T> T resultBindingForOneRecord(ResultSet rs, Class<T> resultType) throws SQLException {
		try {
			T resultObject = resultType.newInstance();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int idx=1; idx<=count; idx++) {
			//			MEM_ID -> memId -> setMemId(rs.getString("MEM_ID"))
			//			MEM_MILEAGE -> memMileage -> setMemMileage(rs.getInt("MEM_MILEAGE"))
				String coulumName = rsmd.getColumnName(idx);
				String propertyName = CaseUtils.toCamelCase(coulumName, false, '_');
				PropertyDescriptor pd = new PropertyDescriptor(propertyName, resultType);
				Method setter = pd.getWriteMethod();
				Class<?> propertyType = pd.getPropertyType();
				if(propertyType.equals(Integer.class)) {
					// Integer타입일때
					setter.invoke(resultObject, rs.getInt(coulumName));
				}else {
					// String타입일때
					setter.invoke(resultObject, rs.getString(coulumName)); // 직접적인 setter를 쓰지 않고도 값을 호출 가능
				}
			}
			return resultObject;
		}catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
