package com.hit.driver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import com.hit.util.MMULogger;

/////////////////////////
//CLI Class
//command language interpreter to handle user requests
/////////////////////////
public class CLI extends Observable implements Runnable {

	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String LRU = "LRU";
	public static final String MRU = "MRU";
	public static final String RANDOM = "RANDOM";
	public static final String LOCAL = "LOCAL";
	public static final String REMOTE = "REMOTE";
	private InputStream in;
	private OutputStream out;
	
	public CLI(java.io.InputStream in,java.io.OutputStream out)
	{
		this.in=in;
		this.out=out;
	}
	
	@Override
	public void run() {
		
		Scanner s = new Scanner(new BufferedInputStream(in));	
		String command;
		String algorithm;
		String mode;
		ArrayList<String> commandAlgoCapacity=new ArrayList<String>();
		Integer capacity;

		boolean done=false;
		while(!done)
		{
			System.out.println("Please enter start or stop");
			command=s.nextLine();
			
			if((!command.equals(START)) && (!command.equals(STOP)))
			{
				System.out.println("Not a valid command");
			}
			else if(command.equals(STOP))
			{
				MMULogger.getInstance().closeLogger();

				System.out.println("Thank you");
				done=true;
			}
			else
			{
				System.out.println("Please enter required algorithm(LRU, MRU or RANDOM) and RAM capacity and LOCAL or REMOTE");
				algorithm=s.next();
				capacity=s.nextInt();
				mode=s.next();
				command=s.nextLine();
				
				if((!algorithm.equals(LRU) && !algorithm.equals(MRU) && !algorithm.equals(RANDOM)) || (!mode.equals(LOCAL) && !mode.equals(REMOTE)))
				{
					System.out.println("Not a valid command");
				}
				else
				{
					try {
						commandAlgoCapacity.add(algorithm);
						commandAlgoCapacity.add(Integer.toString(capacity));
						commandAlgoCapacity.add(mode);
						
						//observer pattern
						//transfer request to controller 
						setChanged();
						notifyObservers(commandAlgoCapacity);

					}
					catch(Exception e)
					{
						System.out.println("Capacity must be a number");
					}
				}
			
			}
		}
		s.close();
		
	}
	
	public void write(java.lang.String string)
	{
		PrintWriter writer = new PrintWriter(new BufferedOutputStream(out));
		writer.write(string);
	}

}
