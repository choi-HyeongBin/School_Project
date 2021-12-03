import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB ���� ���� �� ����
public class DBConnManager {
	static String dbServerAddr = "jdbc:mysql://220.67.115.32:3306/";
	static String dbName = "stdt055"; // ������ DB �̸����� ����
	static String user = "stdt055"; // ������ ���� �̸����� ����
	static String pswd = "anstn606!"; // ������ ��й�ȣ�� ����
	
	// DB ���� ����
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	public static Connection getConnection(String dbName) throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	// DB ���� ����
	public static void closeConnection(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}
}
