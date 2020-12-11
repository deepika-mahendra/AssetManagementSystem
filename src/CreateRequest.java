import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.cj.xdevapi.Result;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CreateRequest extends JFrame{
	JButton backButton;
	JButton saveButton;
	JLabel title;
	JPanel topPane;
	UtilDateModel aModel;
	JDatePickerImpl aDatePicker;
	JDatePickerImpl rDatePicker;
	
	JLabel assetLabel;
	JLabel empLabel;
	JComboBox assetField;
	JComboBox empField;
	JLabel aDateLabel;
	JLabel rDateLabel;
	UtilDateModel rModel;
    JPanel centerPane;
	HashMap<String,Integer> assetMap;
	HashMap<String,Integer> empMap;
	
	
	

	public CreateRequest(Connection con) {
		
		 backButton = new JButton("Back");
		   saveButton = new JButton("Save");
		   saveButton.addActionListener(new ActionListener() {
		   	
			   public void actionPerformed(ActionEvent e) {
				   
				   String aId= (String)assetField.getSelectedItem();
				   String eId= (String)empField.getSelectedItem();
				   Date aDate = (Date)aDatePicker.getModel().getValue();
				   Date rDate = (Date)aDatePicker.getModel().getValue();
				   int assetId =assetMap.get(aId);
				   int empId =assetMap.get(aId);
				   System.out.println(assetId+ " " + empId + aDate +" "+ rDate);
				   String query ="insert into assetallocation(AssetId,EmployeeId,Allocation_date,Release_date,status) "+ "value(?,?,?,?,?)";
				   try {
					PreparedStatement ps=con.prepareStatement(query);
					ps.setInt(1, assetId);
					ps.setInt(2, empId);
					ps.setDate(3, new java.sql.Date(aDate.getTime()));
					ps.setDate(4, new java.sql.Date(rDate.getTime()));
					ps.setString(5, "Waiting");
					int z =ps.executeUpdate();
					if(z>0) {
						System.out.println("success");
					}
					else {
						System.out.println("fail");
					}
				} catch (SQLException e1) {
		
					e1.printStackTrace();
				}
		   	}
		   });
		   title = new JLabel("Create Requests");
		   
		   topPane = new JPanel();
		   topPane.setLayout(new BorderLayout());
		   
		   topPane.add(title,BorderLayout.WEST);
		   topPane.add(backButton,BorderLayout.EAST);
		   
		   aModel = new UtilDateModel();
		   Properties p = new Properties();
		   p.put("text.today","Today");
		   p.put("text.month","Month");
		   p.put("text.year","Year");
		   JDatePanelImpl datePanel = new JDatePanelImpl(aModel,p);
		   aDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		   
		   rModel = new UtilDateModel();
		   JDatePanelImpl reldatePanel = new JDatePanelImpl(rModel,p);
		   rDatePicker = new JDatePickerImpl(reldatePanel, new DateLabelFormatter());
		   
		   assetLabel = new JLabel("Asset : ");
		   empLabel = new JLabel("Exmployee : ");
		   aDateLabel =  new JLabel("Allocation Date : "); 
		   rDateLabel =  new JLabel("Release Date : "); 
		   
		   assetField = new JComboBox();
		   
	   empField = new JComboBox();
		   
	   assetMap = GetAssetCombo(con);
	   for(String s: assetMap.keySet()) {
		   assetField.addItem(s);
	   }
	   
	   empMap = GetempCombo(con);
	   for(String s: empMap.keySet()) {
		   empField.addItem(s);
	   }
	   
	   
		   centerPane = new JPanel();
		   centerPane.setBackground(Color.WHITE);
		   centerPane.setLayout(new GridLayout(6,1,20,20));
		   centerPane.add(assetLabel);
		   centerPane.add(assetField);
		   centerPane.add(empLabel);
		   centerPane.add(empField);
		   centerPane.add(aDateLabel);
		   centerPane.add(aDatePicker);
		   centerPane.add(rDateLabel);
		   centerPane.add(rDatePicker);
		   centerPane.add(saveButton);
		   getContentPane().setLayout(new BorderLayout(0,0));
		   getContentPane().add(topPane,BorderLayout.NORTH);
		   getContentPane().add(centerPane,BorderLayout.CENTER);
		   setVisible(true);   
		   setBackground(Color.WHITE);
		   setSize(440,350);
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   setLocationRelativeTo(null);

		
		
		
	}
	public HashMap<String,Integer> GetAssetCombo(Connection con) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Statement st;
		ResultSet rs;
		try {
			String query = "select AssetId,AssetName from asset where IsActive = true";
			st=con.createStatement();
			rs = st.executeQuery(query);
			Item obj;
			while(rs.next()) {
				obj = new Item(rs.getInt(1),rs.getString(2));
				map.put( obj.getName(),obj.getId());
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return map;
	}

	public HashMap<String,Integer> GetempCombo(Connection con) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Statement st;
		ResultSet rs;
		try {
			String query = "select EmployeeId,EmployeeName from employee where IsActive = true";
			st=con.createStatement();
			rs = st.executeQuery(query);
			Item obj;
			while(rs.next()) {
				obj = new Item(rs.getInt(1),rs.getString(2));
				map.put( obj.getName(),obj.getId());
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return map;
	}
	
}
