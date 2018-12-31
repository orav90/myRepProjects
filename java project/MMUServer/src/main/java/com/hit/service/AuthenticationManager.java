package com.hit.service;
/////////////////////////
//AuthenticationManager Class
//Authenticates if user exists in the users list
/////////////////////////
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.hit.dm.User;

public class AuthenticationManager {

	static User[] userList;
	public static final String USERS_FILE = "src/main/resources/com/hit/login/Users.json";

	
	public AuthenticationManager()
	{
		//read json file
		Gson gson = new Gson();
		try {
			userList = gson.fromJson(new FileReader(USERS_FILE), User[].class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//check if user exists in the users list
	public static boolean authenticate(String user, String password){
	
		for(int i=0;i<userList.length;i++)
		{
			if(userList[i].getUserName().equals(user) &&
					userList[i].getUserPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}
	
	
}
