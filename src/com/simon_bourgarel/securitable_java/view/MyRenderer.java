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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// JTable renderer
public class MyRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		((JLabel) component).setHorizontalAlignment(JLabel.CENTER);
		
		// Fill the cells with the different colors.
		if(((row % 2 == 0) && ((column + 1) % 2 == 0)) || ((row % 2 == 1) && ((column + 1) % 2 == 1))){
			component.setBackground(Color.lightGray);
		}
		else{
			component.setBackground(Color.cyan);
		}
		
		if(column == 0)component.setBackground(Color.LIGHT_GRAY);
		
		component.setForeground(Color.black);
		
		return this;
	}
}