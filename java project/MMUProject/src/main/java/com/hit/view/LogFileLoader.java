package com.hit.view;
/////////////////////////
//LogFileLoader Class
//Reads the log file
/////////////////////////
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LogFileLoader {

	private String logFileName;
	
	public LogFileLoader(String logFileName) {
		this.logFileName=logFileName;
		
	};

	
	public List<String> load() {
		List<String> loadFileRecords = new ArrayList<String>();
		
		try {
			Scanner s = new Scanner(new BufferedReader(new FileReader(logFileName)));
			
			while (s.hasNext())
				loadFileRecords.add(s.nextLine());
			
			s.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Error, log file was not found");
			return null;
		}
		
		return loadFileRecords;
	}
}
