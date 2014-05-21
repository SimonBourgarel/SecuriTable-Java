/**
 *    Copyright 2014 Simon Bourgarel

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   *
   **/

package com.simon_bourgarel.securitable_java.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.simon_bourgarel.securitable_java.controller.Manager;
import com.simon_bourgarel.securitable_java.model.Password;

// Unlock window.
public class LockWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	File file;
	Manager manager;
	private int[][] tab = new int[9][9]; // Creation of the table
	Password password;
	String passwordEntered;
	String passwordGenerated;
	
	JButton jButton_OK;
	JButton jButtonHelp;
	
	MyTableModel myTableModel;
	JTable jTable;
	JTextField jTextFieldPassword;
	JCheckBox jCheckBox = new JCheckBox("New Coordinates");
	
	public LockWindow(){
		manager = new Manager();
		tab = manager.tableGeneration();
		password = manager.recover();
		
		this.setTitle("SecuriTable");
		this.setSize(250, 365);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		passwordGenerated = "";
		
		myTableModel = new MyTableModel(tab);
		passwordGenerated = manager.generate(tab, password); // Generation of the password,
		//depending on the table and the coordinates (stored in the class "Password").
		jTable = new JTable(myTableModel);
		jTable.setDefaultRenderer(Object.class, new MyRenderer());
		jTable.setEnabled(false);
		
		JLabel label = new JLabel("Password :");
		
		jTextFieldPassword = new JTextField(10);// Field where the user enters by the user.
		
		jButton_OK = new JButton("OK");
		jButton_OK.addActionListener(new ButtonOKListener());

		jButtonHelp = new JButton("Help");
		jButtonHelp.addActionListener(new ButtonHelpListener());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		JPanel jPanelPassword = new JPanel();
		jPanelPassword.setLayout(new GridLayout(1,2));
		jPanelPassword.add(label);
		jPanelPassword.add(jTextFieldPassword);
		

		JPanel panelNew_Help = new JPanel();
		panelNew_Help.setLayout(new GridLayout(1,2));
		panelNew_Help.add(jCheckBox);
		panelNew_Help.add(jButtonHelp);
		
		
		JPanel lastPanel = new JPanel();
		lastPanel.setLayout(new GridLayout(3,1));
		lastPanel.add(jPanelPassword);
		lastPanel.add(jButton_OK);
		lastPanel.add(panelNew_Help);

		
		panel.add(new JScrollPane(jTable));
		panel.add(lastPanel);
		
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
	class ButtonOKListener implements ActionListener{
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent event){
			
			// Comparison between the generated password and the entered password.
			if(jTextFieldPassword.getText().equals(passwordGenerated)){
				if(jCheckBox.isSelected()){
					setVisible(false);
					SChoiceWindow sChoiceWindow = new SChoiceWindow();
				}
				else{
					System.exit(0);
				}
			}
		}
	}
	
	class ButtonHelpListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "The password is " + passwordGenerated);
		}
	}
}
