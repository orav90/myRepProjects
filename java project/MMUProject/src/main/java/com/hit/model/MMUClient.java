package com.hit.model;

import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;
import com.hit.processes.RunConfiguration;
/////////////////////////
//MMUClient Class
//Sends user data to server
//Gets the configuration file back
//Sets configuration and starts model
/////////////////////////
public class MMUClient {

	String userDataRequest;
	File configurationFile;
	MMUModel model;
	
	public MMUClient(Model model)
	{
		this.model=(MMUModel) model;
	}
	
	public void setUserData(String userData) {
		
		this.userDataRequest=userData;
	}

	public void requestFromServer() {
		Socket myServer;
		try{
			 //open communicate with the server
			 myServer=new Socket("localhost",12345);
			 ObjectOutputStream output=new ObjectOutputStream(myServer.getOutputStream());
			 ObjectInputStream input=new ObjectInputStream(myServer.getInputStream());
			 
			 //send user data to server
			 output.writeObject(userDataRequest);
			 output.flush();
			 Boolean messageFromServer=(Boolean)input.readObject();
			 
			 //if server confirmed user data
			 if(messageFromServer)
			 {
				 	//get file path
				 	configurationFile =(File)input.readObject();
					if(configurationFile!=null)
					{
						//read json configuration file
						Gson gson = new Gson();
						RunConfiguration runConfig = null;
						runConfig = gson.fromJson(new FileReader(configurationFile), RunConfiguration.class);
						//set configuration
						model.setConfigFile(runConfig);
						//run model, runs same as local mode
						model.start();
					}
					else
					{
						System.out.println("File does not exist in the folder. Please try again");
					}					
			 }
			 else
			 {
				 System.out.println("Error, username or password inccorect!");
			 }
			 

			 output.close();
			 input.close();
			 myServer.close();

			}catch (Exception e) {}

	}
	
}
