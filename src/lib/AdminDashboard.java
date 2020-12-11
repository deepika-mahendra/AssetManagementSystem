package lib;

import javax.swing.JFrame;

import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame{
	public AdminDashboard(Connection con,String userName) {
		getContentPane().setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		lblWelcome.setBounds(137, 23, 79, 26);
		getContentPane().add(lblWelcome);
		
		
		JLabel user = new JLabel("");
		user.setFont(new Font("Yu Gothic Medium", Font.BOLD, 16));
		user.setBounds(208, 23, 97, 26);
		getContentPane().add(user);
		user.setText(userName);
		
		
		JButton btnAssets = new JButton("Assets");
		btnAssets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AssetsList asset =new AssetsList(con);
				asset.setVisible(true);
			}
		});
		btnAssets.setBackground(Color.RED);
		btnAssets.setBounds(76, 72, 85, 21);
		getContentPane().add(btnAssets);
		
		JButton btnRequest = new JButton("Request");
		btnRequest.setBackground(Color.RED);
		btnRequest.setBounds(228, 72, 85, 21);
		getContentPane().add(btnRequest);
		
		JButton btnAssets_1 = new JButton("Manager");
		btnAssets_1.setBackground(Color.RED);
		btnAssets_1.setBounds(76, 146, 85, 21);
		getContentPane().add(btnAssets_1);
		
		JButton btnAssets_2 = new JButton("Employee");
		btnAssets_2.setBackground(Color.RED);
		btnAssets_2.setBounds(228, 146, 85, 21);
		getContentPane().add(btnAssets_2);
		
		JButton btnAssets_1_1 = new JButton("Logout");
		btnAssets_1_1.setBackground(Color.RED);
		btnAssets_1_1.setBounds(155, 193, 85, 21);
		getContentPane().add(btnAssets_1_1);
		
		setSize(400,300);
		
	}
}
