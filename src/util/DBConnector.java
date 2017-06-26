package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	public static Connection conn;
	
	public static void connectToDB() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mythesis?user=root&password=thesis");
			Util.print("Connected to Database");
		}
	}
	
}
