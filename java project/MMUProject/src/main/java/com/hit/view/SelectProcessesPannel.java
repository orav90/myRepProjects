package com.hit.view;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
/////////////////////////
//SelectProcessesPannel Class
//Handles processes check boxes
/////////////////////////
public class SelectProcessesPannel extends JPanel implements ItemListener{
	
	private static final long serialVersionUID = 1L;
	private Dispatcher dispatcher;
	int processesNum = 0;
	JCheckBox[] processes;
	Boolean[] processesSelection;
	
	//initialize panel graphics 
	public SelectProcessesPannel (Dispatcher dispatcher) {
		this.dispatcher=dispatcher;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		setPreferredSize(new Dimension(250,300));
		//set border
		Border innerBorder=BorderFactory.createTitledBorder("Select ");
		Border outerBorder=BorderFactory.createEmptyBorder(10,10, 10, 10);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

	}
	
	//create check jcheckbox for each process 
	public void setNumOfProcesses(int processesNum) {
		this.processesNum = processesNum;
		processes = new JCheckBox[processesNum];   
		processesSelection = new Boolean[processesNum];
		Font processFont = new Font("SansSerif", Font.BOLD, 15);
		java.awt.Color fg = new java.awt.Color (150,0,255);
		for (int i = 0; i < processesNum; i++)
		{	
			processes[i] = new JCheckBox("Process " + Integer.toString((i+1)));
			processes[i].setForeground(fg);
			processes[i].setFont(processFont);
			processes[i].addItemListener(this);	
			add(processes[i]);
			processesSelection[i]=false;
		}
	}

	//notify the dispatcher which process check box was selected when check box was checked/unchecked
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		for(int i=0; i<processesNum; i++)
		{	
			//check which process
	        if(arg0.getSource()==processes[i])
	        {
	        	if (arg0.getStateChange()  == ItemEvent.SELECTED)
	        		processesSelection[i]=true;
	        	else 
	        		processesSelection[i]=false;
	        }
		}
		dispatcher.setSelectedProcesses(processesSelection);
	}
 
	//enable/disable the check boxes according to state
	public void setEnableProcesses(boolean enable)
	{
		for(int i=0;i<processesNum;i++)
		{
			processes[i].setEnabled(enable);
		}
	}
}
