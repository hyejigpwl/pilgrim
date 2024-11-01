package com.lec.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/* 
	JNDI(Java Naming and Directory Interface)? 
	
	디렉터리서비스에서 제공하는 데이터 및 객체를 발견하고 참고(lookup)하기 위한 자바 API다.
	즉, 쉽게이야기 하면 외부에 있는 객체를 가져오기 위한 기술이다. tomcat서버와 같은 WAS를
	보면 특정폴더에 필요한 데이터소스(라이브러리)등을 사용하기 위해 JNDI기술을 이용해서
	가져오는 기법을 이야기 한다.
*/
public class JDBCUtility {

	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/pilgrim");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			// dummy
		}
	}
	
	public static void commit(Connection conn) {
		
		try {
			if(conn != null) {
				conn.commit();			
			}
		} catch (Exception e) {
			// dummy
		}		
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null) conn.rollback();
		} catch (Exception e) {
			// dummy
		}		
	}
}














