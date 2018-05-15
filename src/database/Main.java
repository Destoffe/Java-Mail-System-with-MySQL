package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class Main extends Application{

	public static SQLConnection sql;
	public static Connection con;
	
	public static void main(String[] args)  throws Exception{
	launch(args);

	}
	
	public static ArrayList<String> get() throws Exception{
		
		try {
			Connection con = null;
			PreparedStatement statement = con.prepareStatement("SELECT first,last FROM tablename");
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				System.out.print(result.getString("first"));
				System.out.print(" ");
				System.out.println(result.getString("last"));
				
				array.add(result.getString("last"));
				
			}
			
			System.out.println("All data have been selected!");
			return array;
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}	
	
	
	
    public void start(Stage primaryStage) {
    	
    	sql = new SQLConnection();
        con = sql.getConnection();
    	
    	sql.createUserTable(con);
    	sql.createUser(con, "USER", "Pelle", "password123");
    	
    	primaryStage.setTitle("Mail");
    	primaryStage.show();
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25,25,25,25));
    	
    	Scene scene = new Scene(grid,300,275);
    	primaryStage.setScene(scene);
    	
    	Text scenetitle = new Text("Welcome to Mail");
    	scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
    	grid.add(scenetitle, 0, 0,2,1);
    	
    	Label userName = new Label("User Name:");
    	grid.add(userName, 0, 1);
    	
    	TextField userTextField = new TextField();
    	grid.add(userTextField, 1, 1);
    	
    	Label password = new Label("Password:");
    	grid.add(password, 0, 2);
    	
    	PasswordField pwBox = new PasswordField();
    	grid.add(pwBox, 1, 2);
    	
    	Button signInBtn = new Button("Sign in");
    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(signInBtn);
    	
    	
    	Button registerBtn = new Button("Register");
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(registerBtn);
    	grid.add(hbBtn, 1, 4);
    	
    	final Text actiontarget = new Text();
    	grid.add(actiontarget, 1, 6);
    	
    	signInBtn.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent e) {
    	       
    	        
    	        if(sql.checkUser(con,userTextField.getText(), pwBox.getText())) {
    	        	 actiontarget.setText("Login Success");
    	        	 actiontarget.setFill(Color.GREEN);
    	        }else {
    	        	 actiontarget.setText("Login Failed");
    	        	 actiontarget.setFill(Color.FIREBRICK);
    	        }	        
    	    }
    	});
    	
    	registerBtn.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent e) {
    	       
    	      if(sql.createUser(con,"USER",userTextField.getText(), pwBox.getText())) {
    	    	  actiontarget.setText("Register Complete");
    	    	  actiontarget.setFill(Color.GREEN);
    	      }else {
    	    	  actiontarget.setText("Username already exists");
    	    	  actiontarget.setFill(Color.FIREBRICK);
    	      }
    	    }
    	}); 
    	
    }
}


