package kr.or.ddit.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *	Factory Object[Method] Pattern
 *	 :필요 객체의 생성을 전담하는 객체를 별도 운영하는 구조.
 */
public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	
	private static DataSource ds;
	
	//클래스가 메모리에 로딩될 때 실행되는 코드블럭 
	//(클래스는 한번만 로딩됨, 따라서 어플리케이션 전체 통틀어서 한번 실행됨)
	static {
		String path = "/kr/or/ddit/db/dbInfo.properties"; //클래스패스 리소스(물리경로를 통해)
		try(
			InputStream is = ConnectionFactory.class.getResourceAsStream(path); //클래스 로더
		) {
			Properties dbInfo = new Properties();
			dbInfo.load(is);
			
			url = dbInfo.getProperty("url");
			user = dbInfo.getProperty("user");
			password = dbInfo.getProperty("password");
			
//			Class.forName(dbInfo.getProperty("driverClassName"));
			
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(dbInfo.getProperty("driverClassName"));
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(password);
			
			bds.setInitialSize(Integer.parseInt(dbInfo.getProperty("initialSize")));
			bds.setMaxIdle(Integer.parseInt(dbInfo.getProperty("maxIdle"))); 
			//다 놀고 있을 때 5개 빼고 다 죽여야 한다.
			//InitialSize와 MaxIdledms 값이 같아야 함
			
			bds.setMaxTotal(Integer.parseInt(dbInfo.getProperty("maxTotal"))); 
			//반납 없을 때 여유분 5개 더 만들 수 있다.
			//풀링 : 미리 만들어 놓는다 + 객체의 재활용
			bds.setMaxWaitMillis(Integer.parseInt(dbInfo.getProperty("maxWait"))); //max 찼을 때 2초까지만 기다리고 SQLException 발생
			
			ds = bds;
			
		} catch (Exception e1) {
			throw new RuntimeException(e1); //checked를 unchecked exception으로
		}
	}
	
	public static Connection getConnection() throws SQLException {
//		Connection conn = DriverManager.getConnection(url, user, password);
		return ds.getConnection();
	}
}
