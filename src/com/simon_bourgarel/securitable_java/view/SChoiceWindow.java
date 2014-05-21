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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Class that allows the user to determine the type of password he wants : consecutive cells (linear), or non consecutive cells
//(NC, 3 or 4).
public class SChoiceWindow extends JFrame{
	private static final long serialVersionUID = 1L;

	JButton jButtonRules;
	JButton jButtonlinearCode;
	JButton jButtonNCC3Cells;
	JButton jButtonNCC4Cells;
	JButton jButtonCancel;
	
	public SChoiceWindow(){
		
		this.setTitle("SecuriTable");
		this.setSize(new Dimension(200,150));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		jButtonRules = new JButton("Rules");
		jButtonRules.addActionListener(new JButtonListener(4));
		
		jButtonlinearCode = new JButton("Linear cases");
		jButtonlinearCode.addActionListener(new JButtonListener(0));
		
		jButtonNCC3Cells = new JButton("3 non consecutives cases");
		jButtonNCC3Cells.addActionListener(new JButtonListener(1));
		
		jButtonNCC4Cells = new JButton("4 non consecutives cases");
		jButtonNCC4Cells.addActionListener(new JButtonListener(2));
		
		jButtonCancel = new JButton("Cancel");
		jButtonCancel.addActionListener(new JButtonListener(3));
		
		JPanel mainPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(5,1);
		mainPanel.setLayout(gridLayout);
		mainPanel.add(jButtonRules);
		mainPanel.add(jButtonlinearCode);
		mainPanel.add(jButtonNCC3Cells);
		mainPanel.add(jButtonNCC4Cells);
		mainPanel.add(jButtonCancel);
		
		setContentPane(mainPanel);
		setVisible(true);
	}
	
	class JButtonListener implements ActionListener{
		int type;
		JButtonListener(int type){
			this.type = type;
		}
		
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e){
			if(type == 0){	// Linear password
				setVisible(false);
				SLinearWindow wSChooseLinear = new SLinearWindow();
			}
			else if(type == 1){	// Password with 3 non consecutive cells
				setVisible(false);
				S3NCCellsWindow s3NCCellsWindow = new S3NCCellsWindow();
			}
			else if(type == 2){	// Password with 4 non consecutive cells
				setVisible(false);
				S4NCCellsWindow s4NCCellsWindow = new S4NCCellsWindow();
			}
			else if(type == 3){
				setVisible(false);
				System.exit(0);
			}
			else{
				JOptionPane.showMessageDialog(null, "Here you can choose the type of password you want: \na password composed of 3 or 4 linear cells, \na password composed of 3 non consecutives cells,\n a password composed of 4 non consecutives cells.");
			}
		}
	}
}
