package com.hit.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/////////////////////////
//MMULogger Class
//Creates log file
//Publish log records in file
/////////////////////////
public class MMULogger {
	
	public final static String DEFAULT_FILE_NAME="logs/log.txt"; //log file name
	private FileHandler handler;
	private File file=new File(DEFAULT_FILE_NAME);
	private static MMULogger instance=null;
	
	private MMULogger(){
		try {
			//create new file
	        handler = new FileHandler(DEFAULT_FILE_NAME);
		} 
		catch (IOException e) {
			System.out.println("Cannot open log file");
			handler = null;
		}
		
		if(handler!=null)
			handler.setFormatter(new OnlyMessageFormatter());
		
	}
	
	//singelton pattern, creates only one instance
	public synchronized static MMULogger getInstance()
	{
		if(instance==null)
		{
			instance=new MMULogger();
		}
		return instance;
	}
	
	//write message to log file
	public synchronized void write(String command,Level level)
	{
		if (handler != null) {
			//publish info message if system data is updated
			if (level == Level.INFO)
				handler.publish(new LogRecord(level,command));
			//publish severe error message if system error occurred
			else if (level == Level.SEVERE)
				handler.publish(new LogRecord(level,"ERROR: " + command));
		}
		else
			System.out.println("Log file was not found");
	}
	
	public String getLogFileName() {
		return DEFAULT_FILE_NAME;
	}
	
	public void closeLogger()
	{
		handler.close();
	}
	
	public class OnlyMessageFormatter extends Formatter{

		@Override
		public String format(final LogRecord record)
		{
			return record.getMessage() + "\r\n";
		}
	}


}
