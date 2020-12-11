import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

public class AssetsList extends JFrame{
	JTable table ;
	JScrollPane scroll;
    Statement st;
   ResultSet rs;
   
   JLabel titleLabel;
   JLabel nameLabel;
   JLabel descLabel;
   JLabel qtyLabel;
   JLabel activeLabel;
   
   JButton backBtn;
   JButton saveBtn;
   
   JTextField nameField;
   JTextField descField;
   JTextField qtyField;
   
   JPanel mainPanel;
   JPanel topPane;
   JPanel tablePane;
   JPanel leftPane;
   JPanel mainPane;
   
   JCheckBox active;
   String query;
   
  
	public AssetsList( Connection con) {
		table =new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int id =(int)(table.getModel().getValueAt(row,0));
				System.out.println("Assets Id " + id);
				nameField.setText(table.getModel().getValueAt(row, 1).toString());
				descField.setText(table.getModel().getValueAt(row, 2).toString());
				qtyField.setText(table.getModel().getValueAt(row, 3).toString());
				active.setSelected((boolean)table.getModel().getValueAt(row, 4));
				
			}
		});
		 query ="select * from asset where IsActive =true";
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
	   nameLabel =new JLabel("Asset Name");
	   descLabel =new JLabel("Asset Description");
	   qtyLabel= new JLabel("Quantity");
	   nameField= new JTextField(10);
	   descField= new JTextField(10);
	   qtyField= new JTextField(10);
	   active =new JCheckBox("Is Active");
	   saveBtn= new JButton("save");
	   saveBtn.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
	   		int row = table.getSelectedRow();
	   		System.out.println(row);
	   		if(row>0) {//update call
	   			
	   		}
	   		else {
	   			query ="insert into asset(AssetName, AssetDescription,Quantity,IsActive) value(?,?,?,?)";
	   			try {
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, nameField.getText());
					ps.setString(2, descField.getText());
					ps.setString(3, qtyField.getText());
					ps.setBoolean(4, active.isSelected());
					int status = ps.executeUpdate();
					if(status>0) {
						System.out.println("success");
						 query ="select * from asset where IsActive =true";
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
	   leftPane.add(descLabel);
	   leftPane.add(descField);
	   leftPane.add(qtyLabel);
	   leftPane.add(qtyField);
	   leftPane.add(active);
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
