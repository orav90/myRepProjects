package com.hit.driver;
/////////////////////////
//MMUDriver Class
//Creates the main components
//Registers components as observers to the MMU controller
/////////////////////////
import com.hit.controller.MMUController;
import com.hit.model.MMUModel;
import com.hit.view.LoginView;
import com.hit.view.MMUView;
import com.hit.view.View;

public class MMUDriver {

	@SuppressWarnings("deprecation")
	public static void main(String[] args){
				CLI cli = new CLI(System.in, System.out);
				MMUModel model = new MMUModel();
				MMUView view = new MMUView();
				LoginView loginView = new LoginView();
				View[] views = { view , loginView };
				MMUController controller = new MMUController(model, views);
				model.addObserver(controller);
				cli.addObserver(controller);
				view.addObserver(controller);
				loginView.addObserver(controller);
				new Thread(cli).start();
			}
}
