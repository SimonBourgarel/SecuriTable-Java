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

import javax.swing.table.AbstractTableModel;

// TableModel for printing the table with random values.
public class MyTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;

	String _password;
	int[][] tab;
	
	private final String [] entetes;
	
	public MyTableModel(int[][] tab){	// Fill the cells of the TableModel with the values.
		this.tab = new int[9][10];
		for(int i = 0 ; i < 9 ; i++){
			for(int j = 0 ; j < 9 ; j++){
				this.tab[i][j+1] = tab[i][j];
			}
		}
		entetes = new String [10];
		
		entetes[0] = "";
		for (int i = 0 ; i < 9 ; i++){
			entetes [i+1] = String.valueOf((char) ('A' + i));
		}
	}
	
	@Override
	public int getColumnCount() {
		return 10;
	}

	@Override
	public int getRowCount() {
		return 9;
	}

	public String getColumnName(int columnIndex){
		return entetes[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0){
			return String.valueOf(rowIndex + 1);
		}
		else{
			return String.valueOf(tab[rowIndex][columnIndex]);
		}
	}

	public String get_password(){
		return _password;
	}
}
