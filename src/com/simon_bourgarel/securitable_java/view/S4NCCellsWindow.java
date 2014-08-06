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
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.simon_bourgarel.securitable_java.controller.Manager;
import com.simon_bourgarel.securitable_java.model.Password;

//Class for configuring a password with 4 non consecutive cells.
@SuppressWarnings("rawtypes")
public class S4NCCellsWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	int[][] tab;
	JPanel mainPanel;

	JButton jButtonSubmit;
	JButton jButtonExit;
	JButton jButtonRules;
	
	JComboBox jcomboBoxFA;	// First cell
	JComboBox jcomboBoxFO;
	JComboBox jcomboBoxSA;	// Second cell
	JComboBox jcomboBoxSO;
	JComboBox jcomboBoxTA;	// Third cell
	JComboBox jcomboBoxTO;
	JComboBox jcomboBoxLA;	// Last cell ( = Fourth)
	JComboBox jcomboBoxLO;
	
	ArrayList<String> _cells;
	
	@SuppressWarnings("unchecked")
	public S4NCCellsWindow(){
		
		this.setTitle("SecuriTable");
		this.setSize(250, 540);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		Manager manager = new Manager();
		tab = manager.tableGeneration();
		
		jcomboBoxFA = new JComboBox();
		jcomboBoxFO = new JComboBox();
		jcomboBoxSA = new JComboBox();
		jcomboBoxSO = new JComboBox();
		jcomboBoxTA = new JComboBox();
		jcomboBoxTO = new JComboBox();
		jcomboBoxLA = new JComboBox();
		jcomboBoxLO = new JComboBox();
		

		for(int i = 0 ; i < 9 ; i++){
			// First cell of the password.
			jcomboBoxFO.addItem(i + 1);
			jcomboBoxFA.addItem(String.valueOf(Character.toChars(i + 65)));
			
			// Second cell of the password.
			jcomboBoxSO.addItem(i + 1);
			jcomboBoxSA.addItem(String.valueOf(Character.toChars(i + 65)));
			
			// Third cell of the password.
			jcomboBoxTO.addItem(i + 1);
			jcomboBoxTA.addItem(String.valueOf(Character.toChars(i + 65)));

			// Last cell of the password.
			jcomboBoxLO.addItem(i + 1);
			jcomboBoxLA.addItem(String.valueOf(Character.toChars(i + 65)));
		}
		jButtonRules = new JButton("Rules");
		jButtonRules.addActionListener(new ButtonRulesListener());
		
		jButtonSubmit = new JButton("Submit");
		jButtonSubmit.addActionListener(new ButtonSubmitListener());
		jButtonSubmit.setEnabled(false);
		
		jButtonExit = new JButton("Exit");
		jButtonExit.addActionListener(new ButtonExitListener());
		jButtonExit.setEnabled(false);
		

		jcomboBoxFA.addActionListener(new BoxListener());
		jcomboBoxFO.addActionListener(new BoxListener());
		jcomboBoxSA.addActionListener(new BoxListener());		
		jcomboBoxSO.addActionListener(new BoxListener());
		jcomboBoxTA.addActionListener(new BoxListener());		
		jcomboBoxTO.addActionListener(new BoxListener());
		jcomboBoxLA.addActionListener(new BoxListener());		
		jcomboBoxLO.addActionListener(new BoxListener());
		
		GridLayout mainLayout = new GridLayout(2,1);
		
		JPanel firstCellPanel = new JPanel();
		GridLayout firstCellPanelLayout = new GridLayout(1,3);
		firstCellPanel.setLayout(firstCellPanelLayout);
		firstCellPanel.add(new JLabel("First cell:"));
		firstCellPanel.add(jcomboBoxFA);
		firstCellPanel.add(jcomboBoxFO);
		JPanel secondCellPanel = new JPanel();
		GridLayout secondCellPanelLayout = new GridLayout(1,3);
		secondCellPanel.setLayout(secondCellPanelLayout);
		secondCellPanel.add(new JLabel("Second cell:"));
		secondCellPanel.add(jcomboBoxSA);
		secondCellPanel.add(jcomboBoxSO);
		JPanel thirdCellPanel = new JPanel();
		GridLayout thirdCellPanelLayout = new GridLayout(1,3);
		thirdCellPanel.setLayout(thirdCellPanelLayout);
		thirdCellPanel.add(new JLabel("Third cell:"));
		thirdCellPanel.add(jcomboBoxTA);
		thirdCellPanel.add(jcomboBoxTO);
		JPanel lastCellPanel = new JPanel();
		GridLayout lastCellPanelLayout = new GridLayout(1,3);
		lastCellPanel.setLayout(lastCellPanelLayout);
		lastCellPanel.add(new JLabel("Fourth cell:"));
		lastCellPanel.add(jcomboBoxLA);
		lastCellPanel.add(jcomboBoxLO);
		
		JPanel panel = new JPanel();
		GridLayout gridLayout = new GridLayout(6, 1);

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new GridLayout(1,2));
		panelButtons.add(jButtonSubmit);
		panelButtons.add(jButtonExit);
		
		panel.setLayout(gridLayout);
		panel.add(jButtonRules);
		panel.add(firstCellPanel);
		panel.add(secondCellPanel);
		panel.add(thirdCellPanel);
		panel.add(lastCellPanel);
		panel.add(panelButtons);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(mainLayout);
		
		// Draw the table (an image) into the screen
		try {
			mainPanel.add(new JLabel(new ImageIcon(manager.createImage(tab))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mainPanel.add(panel);
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}

	// Method that check if the cells selected are all different from each other.
	boolean correct(){		
		if(((jcomboBoxFA.getSelectedIndex() == jcomboBoxSA.getSelectedIndex()) && (jcomboBoxFO.getSelectedIndex() == jcomboBoxSO.getSelectedIndex())) ||
				((jcomboBoxFA.getSelectedIndex() == jcomboBoxTA.getSelectedIndex()) && (jcomboBoxFO.getSelectedIndex() == jcomboBoxTO.getSelectedIndex())) ||
					((jcomboBoxFA.getSelectedIndex() == jcomboBoxLA.getSelectedIndex()) && (jcomboBoxFO.getSelectedIndex() == jcomboBoxLO.getSelectedIndex())) ||
						((jcomboBoxSA.getSelectedIndex() == jcomboBoxTA.getSelectedIndex()) && (jcomboBoxSO.getSelectedIndex() == jcomboBoxTO.getSelectedIndex())) ||
							((jcomboBoxSA.getSelectedIndex() == jcomboBoxLA.getSelectedIndex()) && (jcomboBoxSO.getSelectedIndex() == jcomboBoxLO.getSelectedIndex())) ||
								((jcomboBoxTA.getSelectedIndex() == jcomboBoxLA.getSelectedIndex()) && (jcomboBoxTO.getSelectedIndex() == jcomboBoxLO.getSelectedIndex()))){
			return false;
		}
		return true;
	}
	
	class BoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(correct())jButtonSubmit.setEnabled(true);
			else jButtonSubmit.setEnabled(false);
		}
	}

	// Method that saves the coordinates.
	class ButtonSubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){

			Password password = new Password();
			password.set_type(2);
			password.set_abscissa1(jcomboBoxFA.getSelectedIndex());
			password.set_ordinate1(jcomboBoxFO.getSelectedIndex());
			password.set_abscissa2(jcomboBoxSA.getSelectedIndex());
			password.set_ordinate2(jcomboBoxSO.getSelectedIndex());
			password.set_abscissa3(jcomboBoxTA.getSelectedIndex());
			password.set_ordinate3(jcomboBoxTO.getSelectedIndex());
			password.set_abscissa4(jcomboBoxLA.getSelectedIndex());
			password.set_ordinate4(jcomboBoxLO.getSelectedIndex());
			
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
