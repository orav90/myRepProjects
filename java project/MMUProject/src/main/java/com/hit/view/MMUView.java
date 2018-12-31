package com.hit.view;

/////////////////////////
//MMUModel Class
//The view component of MVC pattern
/////////////////////////
import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

//Observable pattern
public class MMUView extends Observable implements View {

		private List<String> logList=null;
		public void initialize(List<String> logList)
		{
			this.logList=logList;
		}
	
		public void start() {
			
		
			if(logList!=null)
				
			{
				SwingUtilities.invokeLater(new Runnable() {
			
				@Override
				public void run() {
					new MainFrame(logList);
			
				}
						
			});
		}
			
	}
		
}
