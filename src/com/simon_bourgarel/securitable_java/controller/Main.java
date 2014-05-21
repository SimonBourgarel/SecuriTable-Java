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

import java.io.File;

import com.simon_bourgarel.securitable_java.view.LockWindow;
import com.simon_bourgarel.securitable_java.view.SChoiceWindow;

@SuppressWarnings("unused")
public class Main {
	public static void main(String arg []){
		
		File f = new File("SecuriTable_Password.txt");
		
		if (f.exists()){ /// If the file exists, unlock process.
			LockWindow lockWindow = new LockWindow(); 
		} else{ /// If the file does not exist, launching of settings.
			SChoiceWindow sChoiceWindow = new SChoiceWindow();
		}
	}
}
