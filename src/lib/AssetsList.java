package lib;


import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.*;

import net.proteanit.sql.DbUtils;



public class AssetsList extends JFrame {
	JTable table ;
	JScrollPane scroll;
    Statement st;
   ResultSet rs;
	public AssetsList( Connection con) {
//		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setSize(300, 300);
		setLocationRelativeTo(null);
		table =new JTable();
		String query ="select * from asset where IsActive =true";
	   try {
		   st=con.createStatement();
		   rs= st.executeQuery(query);
		table.setModel(DbUtils.resultSetToTableModel(rs));
		   
	   }catch(SQLException ex) {
		   ex.printStackTrace();
	   }
	   scroll = new JScrollPane(table);
	   table.setFillsViewportHeight(true);
	   getContentPane().add(scroll);
	
	}

}
