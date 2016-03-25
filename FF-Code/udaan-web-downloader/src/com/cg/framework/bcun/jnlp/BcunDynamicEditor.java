package com.cg.framework.bcun.jnlp;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BcunDynamicEditor {
	 JTextField userText=null;
	 JLabel messageLabel=null;
	 JFrame frame =null;
	 
	 BcunDynamicEditor(){
		  frame = new JFrame("Branch Code Verifier");
		 frame.setSize(300, 150);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		 JPanel panel = new JPanel();
		 frame.add(panel);
		 placeComponents(panel);

		 frame.setVisible(true);
	 }
	

	void placeComponents(JPanel panel) {

		//panel.setLayout(null);

		JLabel userLabel = new JLabel("Branch Code");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);
		messageLabel = new JLabel("");
		messageLabel.setBounds(0, 40, 80, 50);
		panel.add(messageLabel);
		

		JButton loginButton = new JButton("Update");
		loginButton.setBounds(10, 80, 220, 30);
		panel.add(loginButton);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public  void actionPerformed(ActionEvent e) {
				  System.out.println("Action Performed");
				if(userText.getText()!=null){
					messageLabel.setForeground(Color.blue);
					messageLabel.setBackground(Color.lightGray);
					userText.setText(userText.getText().toUpperCase());
					String userName = System.getProperty("user.name");
					if(userName!=null && userName.toUpperCase().contains(userText.getText())){
						messageLabel.setText("Congratulation !, it's a valid Branch Code");
						
						
					}else{
						messageLabel.setText("Invalid Branch code");
						
						userText.setText("");
						return;
					}
				}
				
				OutputStreamWriter writer=null;
				try {
					
					final File propsFileReader = new File(FileLocator.getWarFileLocation()+"jdbcTemplate.properties");
					final File propsFileWriter = new File(FileLocator.getWarFileLocation()+"jdbc.properties");
					
					 writer=new OutputStreamWriter(
						    new FileOutputStream(propsFileWriter), "8859_1");
					Properties props = new Properties();
					props.load(new FileInputStream(propsFileReader));
					props.setProperty("branch.office.code", userText.getText());
					System.out.println(props.getProperty("udaan.jdbc.url"));
					String dburl=props.getProperty("udaan.jdbc.url");
					
					props.setProperty("udaan.jdbc.url", dburl);
					props.store(writer, 
						    "UDAAN-WEB jdbc Properties");
					System.out.println("Proerties file closing");
					//writer.close();
					System.out.println("Proerties file modified");

				}
				 catch (Exception ioException) {
					 System.out.println("########ERROR #######Please contact IT department ##############");
					ioException.printStackTrace();
				}finally{
					if(writer!=null){
						try {
							writer.close();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				frame.setVisible(false);
                frame.dispose();
                System.out.println("Frame Closed");
                System.out.println("Initiating  Jar update");
                ProgressBar.showProgress();
				JarUpdate jarUpdate= new JarUpdate();
				  System.out.println("Jar Update done");
				  try {
					  Thread deploy= new Thread(new UdaanDeployUtil());
					  deploy.start();
				  } catch (Exception e1) {
					  System.out.println("########ERROR(in deploy) #######Please contact IT department ##############");
					  e1.printStackTrace();
				  }
			}
		});

}
}