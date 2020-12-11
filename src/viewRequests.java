import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;


import javax.swing.*;

import com.mysql.cj.xdevapi.Result;

import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class viewRequests extends JFrame{
 JButton backButton;
 JLabel title;
 JTable table;
 JPanel topPane;
 JPanel centrePane;
 Statement st;
 ResultSet rs;
 JScrollPane scroll;
 JPanel centerPane;
 

public viewRequests(Connection con) {
	 setSize(440,350);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setLocationRelativeTo(null);
	   backButton = new JButton("Back");
	  
	   title = new JLabel("View Requests");
	   table =new JTable();
	   String query="select a.assetallocationId,ast.AssetName,emp.EmployeeName,a.Allocation_date,a.Release_date ,a.Status from AssetAllocation a inner join asset ast on ast.AssetId=a.AssetId inner join employee emp on emp.EmployeeId = a.EmployeeId;";
	   try {
		   st = con.createStatement();
		   rs = st.executeQuery(query);
		   table.setModel(DbUtils.resultSetToTableModel(rs));
		   
	   }catch (Exception e){
		   e.printStackTrace();
	   }
	   scroll=new JScrollPane(table);
	   table.setPreferredScrollableViewportSize(new Dimension(420,500));
	   table.setFillsViewportHeight(true);
	   
	   topPane = new JPanel();
	   topPane.setLayout(new BorderLayout());
	   
	   topPane.add(title,BorderLayout.WEST);
	   topPane.add(backButton,BorderLayout.EAST);
	   
	   centerPane = new JPanel();
	   centerPane.add(scroll);
	   
	   getContentPane().setLayout(new BorderLayout(0,0));
	   getContentPane().add(topPane,BorderLayout.NORTH);
	   getContentPane().add(centerPane,BorderLayout.CENTER);
	   setVisible(true);   
	   setBackground(Color.WHITE);
	   setSize(440,350);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setLocationRelativeTo(null);
	   
	   
 }
}
