package com.hit.view;
/////////////////////////
//LoginView Class
//Handle login dialogue
/////////////////////////
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginView extends Observable implements View{

	@Override
	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				displayAndGetLogin();
			}
		});
	}
	
	public void displayAndGetLogin() {
		
		//create dialog graphics		
		JFrame frame = new JFrame("MMU Login");
		JLabel userName;
		JLabel password;
		JLabel fileName;
		JTextField userNameVal;
		JTextField passwordVal;
		JTextField fileNameVal;
		JButton loginBtn;
		JPanel loginPanel;
		
						
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
			
		frame.setSize(300,200);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create dialogue labels and text fields
		userName=new JLabel("  User Name:");
		userNameVal=new JTextField(12);
		password=new JLabel("  Password:");
		passwordVal=new JPasswordField(12);
		fileName=new JLabel("  File Name:");
		fileNameVal=new JTextField(12);
		loginBtn=new JButton("Login");
			  
		loginPanel=new JPanel(new GridLayout(4,2));
		loginPanel.add(userName);
		loginPanel.add(userNameVal);
		loginPanel.add(password);
		loginPanel.add(passwordVal);
		loginPanel.add(fileName);
		loginPanel.add(fileNameVal);
		loginPanel.add(loginBtn);
		frame.add(loginPanel);
		frame.setVisible(true);
		
		loginBtn.addActionListener(new ActionListener()
				{
					//action listener to login button
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						String userNameStr=userNameVal.getText();
						String passwordStr=passwordVal.getText();	
						String fileNameStr=fileNameVal.getText();
						String userRequestStr=userNameStr+" "+passwordStr+" "+fileNameStr;
						//observer pattern
						//send user data to MMU controller
						setChanged();
						notifyObservers(userRequestStr);
					}
			
				});
			
			
		}


}	
	
	




