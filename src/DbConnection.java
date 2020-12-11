import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	 public Connection GetConnection() {
		   Connection con=null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assetmanagementsystem","root","root");
		 System.out.println("connection successful");
			} catch (Exception e) {
			
				e.printStackTrace();
			}

	   return con;
	   }
}
