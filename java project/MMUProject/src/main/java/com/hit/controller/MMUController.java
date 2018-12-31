package com.hit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.hit.driver.CLI;
import com.hit.model.MMUClient;
import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.LoginView;
import com.hit.view.MMUView;
import com.hit.view.View;

/////////////////////////
//MMUModel Class
//The controller component of MVC pattern
/////////////////////////
public class MMUController implements Controller{

	private Model model;
	private View view;
	private LoginView loginView;
	private MMUClient client;

	
	public MMUController(Model model,View[] views)
	{
		this.model=model;
		this.view=views[0];
		this.loginView=(LoginView) views[1];
		client=new MMUClient(model);
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CLI) {
			//handle requested from cli 
			((MMUModel)model).setConfiguration((ArrayList<String>)arg);
			//if local, start model
			if(((ArrayList<String>) arg).get(2).equals("LOCAL") )
			{
				((MMUModel)model).start();
			}
			//if remote start login process
			else 
			{
				loginView.start();
			}
				
			//handle model request and transfer to view component
		}else if(o instanceof Model){
			((MMUView)view).initialize((List<String>)arg);
			((MMUView)view).start();
			//handle login view 
		}else if (o instanceof LoginView )	
		{
			//transfer user name and password to client 
			client.setUserData((String)arg);
			//handle request from client by server
			client.requestFromServer();
		}


		}
	
	
}
