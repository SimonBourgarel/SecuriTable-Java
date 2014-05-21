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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.simon_bourgarel.securitable_java.controller.Manager;
import com.simon_bourgarel.securitable_java.model.Password;


//Activity for configuring a password with linear cells.
@SuppressWarnings("rawtypes")
public class SLinearWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	int[][] tab;
	JPanel mainPanel;
	JTable jTable;
	Manager manager;
	
	JButton jButtonSubmit;
	JButton jButtonExit;
	JButton jButtonRules;
	
	JComboBox jcomboBoxFA;	// First cell's abscissa
	JComboBox jcomboBoxFO;	// First cell's ordinate
	JComboBox jcomboBoxLA;	// Last cell's abscissa 
	JComboBox jcomboBoxLO;	// Last cell's ordinate
	
	
	@SuppressWarnings("unchecked")
	public SLinearWindow(){
		
		this.setTitle("SecuriTable");
		this.setSize(new Dimension(200,400));
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		Manager manager = new Manager();
		tab = manager.tableGeneration();
		MyTableModel tableModel = new MyTableModel(tab);
		
		jcomboBoxFA = new JComboBox();
		jcomboBoxFO = new JComboBox();
		jcomboBoxLA = new JComboBox();
		jcomboBoxLO = new JComboBox();

		for(int i = 0 ; i < 9 ; i++){
			// First cell of the password.
			jcomboBoxFO.addItem(i + 1);
			jcomboBoxFA.addItem(String.valueOf(Character.toChars(i + 65)));
			
			// Last cell of the password.
			jcomboBoxLO.addItem(i + 1);
			jcomboBoxLA.addItem(String.valueOf(Character.toChars(i + 65)));
		}

		jcomboBoxFA.addActionListener(new BoxListener());
		jcomboBoxFO.addActionListener(new BoxListener());
		jcomboBoxLA.addActionListener(new BoxListener());		
		jcomboBoxLO.addActionListener(new BoxListener());
		
		jTable = new JTable(tableModel);
		jTable.setDefaultRenderer(Object.class, new MyRenderer());
		jTable.setEnabled(false);
		
		jButtonRules = new JButton("Rules");
		jButtonRules.addActionListener(new ButtonRulesListener());
		
		jButtonSubmit = new JButton("Submit");
		jButtonSubmit.addActionListener(new ButtonSubmitListener());
		jButtonSubmit.setEnabled(false);
		
		jButtonExit = new JButton("Exit");
		jButtonExit.addActionListener(new ButtonExitListener());
		jButtonExit.setEnabled(false);
		
		GridLayout mainLayout = new GridLayout(2,1);
		JPanel firstpanel = new JPanel();
		JPanel lastPanel = new JPanel();

		firstpanel.add(jcomboBoxFA);
		firstpanel.add(jcomboBoxFO);

		lastPanel.add(jcomboBoxLA);
		lastPanel.add(jcomboBoxLO);
		
		GridLayout gridLayout = new GridLayout(6, 1);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(1,2));
		panelButtons.add(jButtonSubmit);
		panelButtons.add(jButtonExit);
		
		JPanel panel = new JPanel();
		panel.setLayout(gridLayout);
		panel.add(jButtonRules);
		panel.add(new JLabel("Set the first cell of the code :"));
		panel.add(firstpanel);
		panel.add(new JLabel("Set the last cell of the code :"));
		panel.add(lastPanel);
		panel.add(panelButtons);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(mainLayout);
		mainPanel.add(new JScrollPane(jTable));
		mainPanel.add(panel);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
	
	boolean correct(){
		int abs1 = jcomboBoxFA.getSelectedIndex();
		int ord1 = jcomboBoxFO.getSelectedIndex();
		int abs2 = jcomboBoxLA.getSelectedIndex();
		int ord2 = jcomboBoxLO.getSelectedIndex();
		
		if(((abs1 == abs2) && ((ord1 == (ord2 - 3))))
				|| ((abs1 == abs2) && ((ord1 == (ord2 + 3))))
				|| ((abs1 == abs2) && ((ord1 == (ord2 - 2))))
				|| ((abs1 == abs2) && ((ord1 == (ord2 + 2))))
				|| ((abs1 == (abs2 - 3)) && (ord1 == ord2))
				|| ((abs1 == (abs2 + 3)) && (ord1 == ord2))
				|| ((abs1 == (abs2 - 2)) && (ord1 == ord2))
				|| ((abs1 == (abs2 + 2)) && (ord1 == ord2))
				|| (((abs1 - abs2) == (ord1 - ord2)) && (((abs1 - abs2) == 2)
				|| ((abs1 - abs2) == 3) || ((abs1 - abs2) == -2) || ((abs1 - abs2) == -3)))
				|| (((abs1 - abs2) == -(ord1 - ord2)) && (((abs1 - abs2) == 2) 
				|| ((abs1 - abs2) == 3) || ((abs1 - abs2) == -2) || ((abs1 - abs2) == -3)))){
			return true;
		}
		return false;
	}
	
	class BoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(correct())jButtonSubmit.setEnabled(true);
			else jButtonSubmit.setEnabled(false);
		}
	}
	
	class ButtonSubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){

			Password password = new Password();
			password.set_type(0);
			password.set_abscissa1(jcomboBoxFA.getSelectedIndex());
			password.set_ordinate1(jcomboBoxFO.getSelectedIndex());
			password.set_abscissa2(jcomboBoxLA.getSelectedIndex());
			password.set_ordinate2(jcomboBoxLO.getSelectedIndex());
			
			Manager manager = new Manager();
			manager.save(password);
			jButtonExit.setEnabled(true);
		}
	}
	
	class ButtonExitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	
	class ButtonRulesListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "The password will be the content of the cells from the first one to the last one choosen.\nFor example if you choose as first cell A1 and as last cell A4, the password will be (with this randomly generated table): " + tab[0][0] + tab[1][0] + tab[2][0] + tab[3][0] + ".\nNote: the password's length should be of 3 or 4 cells.");
		}
	}
}
