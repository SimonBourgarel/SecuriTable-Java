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

package com.simon_bourgarel.securitable_java.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.simon_bourgarel.securitable_java.model.Password;

public class Manager {

	/// Method that generates the password from the coordinates and the table.
	public String generate(int[][] tab, Password password){
		String string = new String();

		int abs1 = password.get_abscissa1();
		int ord1 = password.get_ordinate1();
		int abs2 = password.get_abscissa2();
		int ord2 = password.get_ordinate2();
		int abs3 = password.get_abscissa3();
		int ord3 = password.get_ordinate3();
		int abs4 = password.get_abscissa4();
		int ord4 = password.get_ordinate4();

		if(password.get_type() == 0){	// Linear

			/// If the password is vertical:
			if(abs1 == abs2){
				string = "";
				/// Up -> Down
				if(ord1 < ord2){
					for(int i = ord1 ; i < ord2 + 1 ; i++){
						string += tab[i][abs1];
					}
				}
				/// Down -> Up
				else{
					for(int i = ord1 ; i > ord2 - 1; i--){
						string += tab[i][abs1];
					}
				}
			}
			/// If the password is horizontal:
			else if(ord1 == ord2){
				string = "";
				/// Left -> Right
				if(abs1 < abs2){
					for(int i = abs1 ; i < abs2 + 1 ; i++){
						string += tab[ord1][i];
					}
				}
				/// Right -> Left
				else{
					for(int i = abs1 ; i > abs2 - 1; i--){
						string += tab[ord1][i];
					}
				}
			}
			/// If the password is in diagonal NW-SE or SE-NW
			else if((abs1 - abs2) == (ord1 - ord2)){
				string = "";
				if ((abs1 - abs2) < 0){   /// NW -> SE
					int i = abs1 - 1;
					int j = ord1 - 1;
					while((i != abs2) && (j != ord2)){
						i++;
						j++;
						string += tab[j][i];
					}
				}
				else{   /// SE -> NW
					int i = abs1 + 1;
					int j = ord1 + 1;
					while((i != abs2) && (j != ord2)){
						i--;
						j--;
						string += tab[j][i];
					}
				}
			}
			/// Else, the password is in diagonal NE-SW or SW-NE
			else{
				string = "";
				if ((abs1 - abs2) < 0){   /// SW -> NE
					int i = abs1 - 1;
					int j = ord1 + 1;
					while((i != abs2) && (j != ord2)){
						i++;
						j--;
						string += tab[j][i];
					}
				}
				else{   /// NE -> SW
					int i = abs1 + 1;
					int j = ord1 - 1;
					while((i != abs2) && (j != ord2)){
						i--;
						j++;
						string += tab[j][i];
					}
				}
			}
		}
		else if(password.get_type() == 1){	// NCC3
			string = "";
			string += tab[ord1][abs1];
			string += tab[ord2][abs2];
			string += tab[ord3][abs3];
		}
		else{	// NCC4
			string = "";
			string += tab[ord1][abs1];
			string += tab[ord2][abs2];
			string += tab[ord3][abs3];
			string += tab[ord4][abs4];
		}
		return string;
	}

	// Method that recovers the coordinates of the password from the file
	public Password recover(){
		Password password = new Password();
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader("SecuriTable_Password.txt"));
			ObjectInputStream ojectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("SecuriTable_Password.txt")));

			password = (Password) ojectInputStream.readObject();
			bufferedReader.close();
			ojectInputStream.close();
			return password;
		}
		catch (Exception e) {
			System.out.println("Fail while trying to open SecuriTable_Password.txt");
		}
		return null;
	}

	// Method that saves the data into a specific file, SecuriTable_Password.txt
	public boolean save(Password password){
		try{
			File f = new File("SecuriTable_Password.txt");
			if(f.exists()){
				f.delete();
			}
			FileWriter fileWriter = new FileWriter("SecuriTable_Password.txt", true);
			PrintWriter output = new PrintWriter(fileWriter);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("SecuriTable_Password.txt")));
			password.save(objectOutputStream);

			objectOutputStream.close();
			output.close();
			fileWriter.close();

		} catch (IOException e) {
			return false;
		}
		return true;
	}


	// Method that generates the table with random values.
	public int[][] tableGeneration(){

		int[][] tab = new int[9][9];
		
		boolean ok = false;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				do {
					tab[i][j] = (int) Math.round(Math.random() * 8 + 1);
					// Algorithm that forbids having two consecutive numbers AND ALSO forbids having generation like 101 or 636
					if (((i - 1 >= 0) && (tab[i - 1][j] == tab[i][j]))
							|| ((j - 1 >= 0) && (tab[i][j - 1] == tab[i][j]))/** Comment this lines to allow generation like 101 but still deny generation like 11 or 00. **/
							|| ((i - 2 >= 0) && (tab[i - 2][j] == tab[i][j]))
							|| ((j - 2 >= 0) && (tab[i][j - 2] == tab[i][j]))/** End comment **/
							) {
						ok = false;
					} else {
						ok = true;
					}
				} while (!ok);
				ok = false;
			}
		}
		return tab;
	}
}
