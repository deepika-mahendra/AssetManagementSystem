package lib;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;


public class Login  extends JFrame{
 String query;
	public Login(Connection con ) {
       setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
	    setVisible(true);
	    
	    
	    JLabel lblFirstName = new JLabel("user name:");
	    lblFirstName.setFont(new Font("Stencil", Font.PLAIN, 14));
	    lblFirstName.setBounds(23, 62, 90, 22);
	    getContentPane().add(lblFirstName);
	    
	    JTextField textField = new JTextField();
	    textField.setBounds(131, 61, 145, 25);
	    getContentPane().add(textField);
	    textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("password : ");
		lblPassword.setFont(new Font("Stencil", Font.PLAIN, 13));
		lblPassword.setBounds(23, 107, 81, 50);
	    getContentPane().add(lblPassword);
	    
	    JPasswordField passwordField = new JPasswordField();
	    setBounds(194, 101, -108, 19);
	    getContentPane().add(passwordField);

	    JPasswordField passwordField_1 = new JPasswordField();
	    passwordField_1.setBounds(131, 117, 145, 29);
	    getContentPane().add(passwordField_1);
	    

	    JCheckBox chckbxNewCheckBox = new JCheckBox("I Accept tearms and conditons");
	    chckbxNewCheckBox.setBounds(32, 180, 169, 21);
	    getContentPane().add(chckbxNewCheckBox);
	    
	    JButton btnLogin = new JButton("Login");
	    btnLogin.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
    			String uname=textField.getText();
    			String pas= passwordField_1.getText();
    			System.out.println(uname + " "+ pas);
    			 query = "select * from user where UserName = ? and UserPassword = ?";
	    		try 
	    		{
	    			
	    			PreparedStatement ps = con.prepareStatement(query);
	    			ps.setString(1, uname);
	    			ps.setString(2, pas);
	    			
	    			ResultSet rs= ps.executeQuery();
	    			if(rs.next()) {
	    			System.out.println("user logged in successfully");
	    			if(rs.getString("UserType").equalsIgnoreCase("Admin")) {
	    				System.out.println("Admin Login");
	    				setVisible(false);
	    				AdminDashboard admin=new AdminDashboard(con,rs.getString("UserName"));
	    				admin.setVisible(true);
	    			}
	    			else if(rs.getString("UserType").equalsIgnoreCase("Manager")) {
	    				System.out.println("Manager Login");
	    			}
	    			}
	    			else {
	    				System.out.println("failed to login");
	    			}
	    	} catch (SQLException ex) {
	    	
	    		ex.printStackTrace();
	    	}	
	    		
	    	}
	    });
	    btnLogin.setBounds(159, 228, 100, 25);
	   getContentPane().add(btnLogin);
	    JLabel lblNewLabel = new JLabel("");
	    lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ASUS\\Documents\\GitHub\\focus\\images\\fevicon\\android-icon-36x36.png"));
	    lblNewLabel.setBounds(25, 0, 66, 50);
	    getContentPane().add(lblNewLabel);
	    JLabel lblLoginForm = new JLabel("Login Form");
	    lblLoginForm.setFont(new Font("Times New Roman", Font.BOLD, 16));
	    lblLoginForm.setBounds(101, 10, 100, 30);
	    getContentPane().add(lblLoginForm);
	    JButton btnSignUp = new JButton("Sign Up");
	    btnSignUp.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    	}
	    });
	    btnSignUp.setBounds(33, 230, 85, 21);
	    getContentPane().add(btnSignUp);
	    JLabel lblNewLabel_1 = new JLabel("");
	    lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ASUS\\Documents\\GitHub\\focus\\images\\brown-bg.jpg"));
	    lblNewLabel_1.setBounds(0, 0, 323, 309);
	    lblNewLabel_1.setSize(300,350);
	    setSize(300,350);
	    getContentPane().add(lblNewLabel_1);
	    setVisible(true);
	}
	public static void main(String[] args) {
	 DBConnection connection = new DBConnection();
	 Connection con = connection.GetConnection();
	 Login login = new Login(con);
	 

	}

}
