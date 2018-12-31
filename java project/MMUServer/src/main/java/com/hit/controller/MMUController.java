package com.hit.controller;
/////////////////////////
//MMUController Class
//Opens server socket 
/////////////////////////
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.hit.businesslogic.MMUConfigFileService;

public class MMUController {

	private static boolean serverUp = true;
	
	public void start()
	{
		try {
			//open a server socket 
			ServerSocket server=new ServerSocket(12345);
			while(serverUp){
				//listen to client connection
				Socket someClient = server.accept();
				//run limited size of processes  
				Executor executor = Executors.newCachedThreadPool();
				executor.execute(new MMUConfigFileService(someClient));
				((ExecutorService)executor).shutdown();
			}
			
			server.close();
		} catch (Exception e) {
			System.out.println("Server error");
		}
	}

}
