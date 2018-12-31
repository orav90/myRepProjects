package com.hit.businesslogic;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.hit.service.AuthenticationManager;
/////////////////////////
//MMUConfigFileService Class
//Checks if the user data exists in the file
//Return the user requested file, if exists
/////////////////////////
public class MMUConfigFileService implements Runnable {

	private MMUConfigFileBrowsing configFileBrowsing=new MMUConfigFileBrowsing(); 
	private AuthenticationManager userManager=new AuthenticationManager();
	private Socket socket;
	
	public MMUConfigFileService(Socket socket) 
	{
		this.socket=socket;
	}
	
	@Override
	public void run() {
		ObjectOutputStream output;
		ObjectInputStream input;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input=new ObjectInputStream(socket.getInputStream());
			//get user data
			String userData=(String)input.readObject();
			String[] userDataArray = userData.split(" ");
			
			//if the user exists in the user file
			if(AuthenticationManager.authenticate(userDataArray[0], userDataArray[1]))
			{
				output.writeObject(true);
				output.flush();
				
				//get the requested user file path
				String extendedFileName=configFileBrowsing.getFile(userDataArray[2]);
				
				File file = null;
				//if the file exists
				if(extendedFileName!=null)
				{
					file=new File(extendedFileName);
				}	
					output.writeObject(file);
					output.flush();
				
			}
			else
			{
				output.writeObject(false); //Error, username/password incompatible
				output.flush();
			}
			
			output.close();
			input.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Server error");
		}
		
	}

}
