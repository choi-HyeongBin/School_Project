import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB 연결 설정 및 해제
public class DBConnManager {
	static String dbServerAddr = "jdbc:mysql://220.67.115.32:3306/";
	static String dbName = "stdt055"; // 여러분 DB 이름으로 수정
	static String user = "stdt055"; // 여러분 계정 이름으로 수정
	static String pswd = "anstn606!"; // 여러분 비밀번호로 수정
	
	// DB 연결 설정
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	public static Connection getConnection(String dbName) throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	// DB 연결 해제
	public static void closeConnection(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}
}
