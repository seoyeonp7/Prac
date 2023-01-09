package kr.or.ddit.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Factory Object[Method] Pattern
 * 	: 필요 객체의 생성을 전담하는 객체를 별도 운영하는 구조. 
 *
 */
public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	
	private static DataSource ds;
	//클레스 메모리에 로딩될때 실행됨! 어플리케이션 전체 통틀어서 딱 한 번 실행된다.
	static {
		String path ="/kr/or/ddit/db/dbInfo.properties";
		try(
			InputStream is = ConnectionFactory.class.getResourceAsStream(path);
		) {
			Properties dbInfo = new Properties();
			dbInfo.load(is);
			
			url = dbInfo.getProperty("url");
			user = dbInfo.getProperty("user");
			password = dbInfo.getProperty("password");
			
//	        Class.forName(dbInfo.getProperty("driverClassName")); //db가 바껴도 팩토리 건들 필요 없어짐
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(dbInfo.getProperty("driverClassName"));
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(password);
			
			bds.setInitialSize(Integer.parseInt(dbInfo.getProperty("initialSize")));
			bds.setMaxIdle(Integer.parseInt(dbInfo.getProperty("maxIdle"))); // 10개가 한꺼번에 반납되면 최대 5개까지 놀릴 수 있고, 5개는 죽인다. setInitialSize랑 같은거임
			
			bds.setMaxTotal(Integer.parseInt(dbInfo.getProperty("maxTotal"))); // 최대 10개 
			bds.setMaxWaitMillis(Integer.parseInt(dbInfo.getProperty("maxWait"))); //2초를 기다렸는데도 반납되는 게 없으면 sql예외 발생시킴
			
			ds = bds;
			
		} catch (Exception e1) {
	         throw new RuntimeException(e1);
	      }
	}
	
	public static Connection getConnection() throws SQLException {
//	      Connection conn = DriverManager.getConnection(url, user, password);
	      return ds.getConnection();
	}
}
