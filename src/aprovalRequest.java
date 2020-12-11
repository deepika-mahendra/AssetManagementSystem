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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

public class aprovalRequest  extends JFrame{
	JTable table ;
	JScrollPane scroll;
    Statement st;
   ResultSet rs;
   
   JLabel titleLabel;
   JLabel empLabel;
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
   JComboBox statusBox;
   
   JCheckBox active;
   String query;
 JLabel assetNameLabel;
 JLabel allocaDateLabel;
 JLabel relDateLabel;
 JLabel statusLabel;
 JTextField assetField;
 JTextField empField;
 JTextField allocaDatefield;
 JTextField relDatefield;
public aprovalRequest ( Connection con,String userName) {
	table =new JTable();
	table.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			int id =(int)(table.getModel().getValueAt(row,0));
			System.out.println("Assets Id " + id);
			assetField.setText(table.getModel().getValueAt(row, 1).toString());
			empField.setText(table.getModel().getValueAt(row, 2).toString());
			allocaDatefield.setText(table.getModel().getValueAt(row, 3).toString());
			relDatefield.setText(table.getModel().getValueAt(row, 4).toString());
			statusBox.setSelectedItem((String)table.getModel().getValueAt(row, 5));
			
		}
	});
	 query ="select a.assetallocationId,ast.AssetName,emp.EmployeeName,a.Allocation_date,a.Release_date ,a.Status from AssetAllocation a inner join asset ast on ast.AssetId=a.AssetId inner join employee emp on emp.EmployeeId = a.EmployeeId;";
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
   
   titleLabel = new JLabel("Approve Requests");
   
   backBtn=new JButton("Back");
   backBtn.addActionListener(new ActionListener() {
   	public void actionPerformed(ActionEvent e) {
   		dispose();
   		AdminDashboard admin = new AdminDashboard(con,userName);
   		admin.setVisible(true);
   	}
   });
   assetNameLabel = new JLabel("Asset: ");
   empLabel =new JLabel("Employee: ");
   allocaDateLabel =new JLabel("Allocation Date: ");
   relDateLabel= new JLabel("Released date:");
   statusLabel = new  JLabel("Status");
   
   assetField= new JTextField();
   assetField.setEnabled(false);
   empField= new JTextField();
   empField.setEnabled(false);
   
   allocaDatefield= new JTextField();
   allocaDatefield.setEnabled(false);
   relDatefield= new JTextField();
   relDatefield.setEnabled(false);
   statusBox = new JComboBox();
   statusBox.addItem("Waiting");
   statusBox.addItem("Approve");
   statusBox.addItem("Reject");
   
//   active =new JCheckBox("Is Active");
   saveBtn= new JButton("save");
   saveBtn.addActionListener(new ActionListener() {
   	private int assetid;
	private PreparedStatement ps;

	public void actionPerformed(ActionEvent e) {
   		int row = table.getSelectedRow();
		assetid =(int)(table.getModel().getValueAt(row,0));
		String query="update assetallocation set status = ? where AssetAllocationId = ?";
		try {
			ps=con.prepareStatement(query);
			ps.setString(1, statusBox.getSelectedItem().toString());
			ps.setInt(2, assetid);
			int z = ps.executeUpdate();
			if(z>0)
			{
				System.out.println("success");
				 query ="select a.assetallocationId,ast.AssetName,emp.EmployeeName,a.Allocation_date,a.Release_date ,a.Status from AssetAllocation a inner join asset ast on ast.AssetId=a.AssetId inner join employee emp on emp.EmployeeId = a.EmployeeId;";
				   try {
					   st=con.createStatement();
					   rs= st.executeQuery(query);
					 table.setModel(DbUtils.resultSetToTableModel(rs));
					   
				   }catch(SQLException ex) {
					   ex.printStackTrace();
				   }
			}
			else {
				System.out.println("update failed");
			}
		}catch(Exception e1) {
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
   leftPane.add(assetNameLabel);
   leftPane.add(assetField);
   leftPane.add(empLabel);
   leftPane.add(empField);
   leftPane.add(allocaDateLabel);
   leftPane.add(allocaDatefield);
   leftPane.add(relDateLabel);
   leftPane.add(relDatefield);
   leftPane.add(statusLabel);
   leftPane.add(statusBox);
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
