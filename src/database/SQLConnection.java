package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQLConnection {
	
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/Mail";
	private final String username = "XXX";
	private final String password = "XXX";
	
	//s
	
	public Connection getConnection() {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Conneted to database!");
			
			return con;
		} catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public void createUserTable(Connection connection) {
		try {
			Connection con = connection;
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS user(id int NOT NULL AUTO_INCREMENT, "
					+ " employee_id int DEFAULT NULL,"
					+ " user_type varchar(50) DEFAULT NULL,"
					+ "username varchar(100) NOT NULL UNIQUE,"
					+ "password varchar(100) NOT NULL,PRIMARY KEY (id))");
			create.executeUpdate();
			
			
		}catch(Exception e ) {
			System.out.println(e);
			System.out.println("Table already exists");
		}
		finally {
			System.out.println("Created Table");
		}
	}
	
	public boolean createUser(Connection connection, String user_type, String username, String password) {
		try {
			Connection con = connection;
			PreparedStatement posted = con.prepareStatement("INSERT INTO user (user_type, username, password) VALUES"
					+ " ('"+user_type+"', '"+username+"', '"+password+"')");
			posted.executeUpdate();
			System.out.println("Created User");
			return true;
			}catch(Exception e) {
				//System.out.println(e);
				System.out.println("Error, perhaps that username is taken, try a new one");
			}
		return false;
		}
	
	public boolean checkUser(Connection connection,String username,String password) {
		try {
			Connection con = connection;
			PreparedStatement statement = con.prepareStatement("SELECT username,password FROM user");
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				System.out.println("SQL Username : "+ result.getString("username")  + " Input username : " + username);
				System.out.println("SQL Password : "+ result.getString("password")  + " Input password : " + password);
				
				if(username.equals(result.getString("username")) && password.equals(result.getString("password"))) {
					System.out.println("Found user");
					return true;					
				}
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		System.out.println("User not found");
		return false;
	}
}
