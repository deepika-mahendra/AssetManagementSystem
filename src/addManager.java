import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import net.proteanit.sql.DbUtils;

public class addManager extends JFrame{
	JButton backButton;
	JButton saveButton;
	JLabel title;
	JPanel topPane;
	UtilDateModel aModel;

	JTextField nameField;
	JLabel nameLabel;
	JLabel passwordLabel;
	JTextField passwordField;
	JComboBox UserType;
	JLabel usertypeLable;
	JTable table;
	UtilDateModel rModel;
    JPanel centerPane;
    JScrollPane scroll;
    Statement st;
    ResultSet rs;
    JButton saveBtn;
    JButton backBtn;
    JLabel titleLabel;
    JPanel mainPanel;

    JPanel tablePane;
    JPanel leftPane;
    JPanel mainPane;

    String query;
	

	public addManager(Connection con) {
		table =new JTable();
		table.addMouseListener(new MouseAdapter() {
			
//			public void mouseClicked(MouseEvent e) {
//				int row = table.getSelectedRow();
//				int id =(int)(table.getModel().getValueAt(row,0));
//				System.out.println("Assets Id " + id);
//				nameField.setText(table.getModel().getValueAt(row, 1).toString());
//				passwordField.setText(table.getModel().getValueAt(row, 2).toString());
//				UserType.setText(table.getModel().getValueAt(row, 3).toString());
//				
//			}
		});
		String query ="select * from user where UserType='Manager'";
	   try {
		   st=con.createStatement();
		   rs= st.executeQuery(query);
		 table.setModel(DbUtils.resultSetToTableModel(rs));
		   
	   }catch(SQLException ex) {
		   ex.printStackTrace();
	   }
	   scroll = new JScrollPane(table);
	   table.setPreferredScrollableViewportSize(new Dimension(420,250));
	   table.setFillsViewportHeight(true);
	   
	   titleLabel = new JLabel("Asset");
	   backBtn=new JButton("Back");
	   nameLabel =new JLabel("User Name");
	   passwordLabel =new JLabel("User Password");
	   usertypeLable= new JLabel("User Type");
	   nameField= new JTextField(10);
	   passwordField= new JTextField(10);

	   UserType = new JComboBox();
	   UserType.addItem("Manager");
	   saveBtn= new JButton("save");
	   saveBtn.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
	   		
	   String query ="insert into user(UserName, UserPassword,UserType) value(?,?,?)";
	   			try {
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, nameField.getText());
					ps.setString(2, passwordField.getText());
					ps.setString(3, "Manager");
					
					int status = ps.executeUpdate();
					 
					if(status>0) {
						System.out.println("success");
						 query ="select * from user where UserType='Manager'";
						 st=con.createStatement();
						   rs= st.executeQuery(query);
						  table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					else {
						System.out.println("fail");
					}
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
	   		}
	   		
	   	
	   });
	   
	   topPane = new JPanel();
	   topPane.setLayout(new BorderLayout());
	   topPane.add(titleLabel,BorderLayout.WEST);
	   topPane.add(backBtn,BorderLayout.EAST);
	   
	   tablePane = new JPanel();
	   tablePane.add(scroll);
	   
	   leftPane = new JPanel();
	   leftPane.setLayout(new GridLayout(6,1,10,10));
	   leftPane.add(nameLabel);
	   leftPane.add(nameField);
	   leftPane.add(passwordLabel);
	   leftPane.add(passwordField);
	   leftPane.add(usertypeLable);
	   leftPane.add(UserType);
	
	   leftPane.add(saveBtn);
	   
	   mainPanel = new JPanel();
	   mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
	   
	   getContentPane().add(topPane,BorderLayout.NORTH);
	   getContentPane().add(tablePane,BorderLayout.EAST);
	   getContentPane().add(leftPane,BorderLayout.WEST);
	   pack();
	   setVisible(true);
	}

}
