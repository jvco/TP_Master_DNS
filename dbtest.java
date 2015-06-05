import java.sql.*;

public class dbtest
{
	public void main(String[] args) throws ClassNotFoundException, SQLException
	{
			String strClassName = "org.postgresql.Driver"; 
			String strUrl = "jdbc:postgresql://194.57.216.22:5432/coursSN?user=etd-sn&password=3tud14nt"; 
			String strQuery = "SELECT * FROM numero;"; 
			Class.forName(strClassName); 
			Connection conn = null;
			Statement stLogin = null;
			
			try {
				conn = DriverManager.getConnection(strUrl);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			try {
				stLogin = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			try {
				ResultSet rsLogin = stLogin.executeQuery(strQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			conn.close();
	}
}
	