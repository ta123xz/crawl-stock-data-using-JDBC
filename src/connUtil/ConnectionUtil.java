package connUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {  
	private static Connection conn = null;// 單例方法=使用時僅會有一個實例
	String url = "jdbc:sqlserver://localhost:1433;databasename=demo;encrypt=false";
	String userName = "sa";
	String pwd = "ta123xz";

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(url, userName, pwd);
		}
		if (conn.isClosed()) {
			conn = DriverManager.getConnection(url, userName, pwd);
		}
		return conn;
	}
}
