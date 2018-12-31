package com.hit.view;
/////////////////////////
//MainFrame Class
//Manages all the view components
/////////////////////////
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//the MainFrame implements dispatcher which is an interface for the all the GUI components
//which communicate with each other through the dispatcher
public class MainFrame extends JFrame implements Dispatcher {

	private static final long serialVersionUID = 1L;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private TablePanel pagesTablePanel;
	private SelectProcessesPannel selectProcessesPannel;
	private DrawingPanel drawingPanel;
	private int ramCapacity;
	private int processesNum;
	private RamHandler ramHandler;
	private int pFNum=0;
	private int pRNum=0;
	private final int RAM_PAGE_SIZE=5;
	
	public MainFrame(List<String> logList)//goes to start function
	{
		//create the ram handler, which handles ram display
		ramHandler=new RamHandler(this);
		//create view panels
		pagesTablePanel=new TablePanel();
		formPanel=new FormPanel();
		toolbar=new Toolbar(this);
		selectProcessesPannel = new SelectProcessesPannel(this);
		drawingPanel = new DrawingPanel();
		
		// Set the layout of the MainFrame to BoxLayout
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		//panels container
		JPanel ramPanel = new JPanel ();
		ramPanel.setLayout(new BoxLayout(ramPanel, BoxLayout.X_AXIS));
	
		//right size of the screen display
		JPanel pagesInfoPanel = new JPanel ();
		pagesInfoPanel.setLayout(new BoxLayout(pagesInfoPanel, BoxLayout.Y_AXIS));
		//form panel includes page counters
		pagesInfoPanel.add(formPanel);
		//selectProcessesPannel includes processes check boxes
		pagesInfoPanel.add(selectProcessesPannel);
		
		//left size of the screen display
		JPanel simulationPanel=new JPanel();
		simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
		
		//table of pages
		simulationPanel.add(pagesTablePanel);
		//hard disk and ram simulation
		simulationPanel.add(drawingPanel);
		ramPanel.setLayout(new BoxLayout(ramPanel, BoxLayout.X_AXIS));
		ramPanel.add(simulationPanel);
		ramPanel.add(pagesInfoPanel);
		
		//toolbar holds play,playAll and reset buttons
		add (toolbar);
		add (ramPanel);
				
		ramHandler.setLogList(logList);
		pagesTablePanel.setRowNum (RAM_PAGE_SIZE);
		ramCapacity=ramHandler.getRamCapacity();
		processesNum=ramHandler.getProcessesNum();
		selectProcessesPannel.setNumOfProcesses(processesNum);
		
		//set play and playAll buttons to false until at least one process is selected
		toolbar.setPlayButtonEnable(false);
		toolbar.setPlayAllButtonEnable(false);
		
		//get list of pages for hard disk and ram graphic simulation
		ArrayList<Integer> pagesList= new ArrayList<Integer>();
		pagesList=ramHandler.getListOfPages();
		drawingPanel.setDiskPages(pagesList);
		
		setSize(780,700);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);					
	}
	//handle reset button,reset the entire display to default
	@Override
	public void resetRam() {
		ramHandler.resetRam();
		pagesTablePanel.clear();
		formPanel.clear();
		//reset the counters
		pFNum=0;
		pRNum=0;
		toolbar.setPlayButtonEnable(true);
		selectProcessesPannel.setEnableProcesses(true);
		drawingPanel.resetRam();
		
	}
	//handle play button
	@Override
	public void play() {
		//if play pressed you cannot change the selected processes
		selectProcessesPannel.setEnableProcesses(false);
		ramHandler.play();
	}
	//handle playAll button
	@Override
	public void playAll() {
		//if play pressed you cannot change the selected processes
		selectProcessesPannel.setEnableProcesses(false);
		ramHandler.playAll();		
		//after playAll done, enable processes check box 
		selectProcessesPannel.setEnableProcesses(true);

	}
	
	//handle page fault
	@Override
	public void handlePageFault(int pageNum) {
		pagesTablePanel.addPage(pageNum);
		formPanel.setPageFault(++pFNum);
		drawingPanel.handlePageFault(pageNum);
	}
	//handle page replacement
	@Override
	public void handlePageReplacement(int pageToHD, int pageToRam) {
		pagesTablePanel.replacePages(pageToHD, pageToRam);
		formPanel.setPageReplacement(++pRNum);
		drawingPanel.handlePageReplacement(pageToHD,pageToRam);
	}
	
	@Override
	public void handleGetPage(int processNum, int pageNum, int[] intDataValues) {
		pagesTablePanel.setPageData(pageNum,intDataValues);
	}
	
	//end of log file processing
	@Override
	public void logFileEnded() {
		toolbar.setPlayButtonEnable(false);
		selectProcessesPannel.setEnableProcesses(true);
	}
	
	//handle the list of selected processes
	public void setSelectedProcesses(Boolean[] processesSelection)
	{
		boolean processSelected=false;
		//set the selected processes and the ram handler
		ramHandler.setProcessesFilter(processesSelection);
		for(int i=0;i<processesSelection.length;i++)
		{
			//at least one process must be selected in order to enable the play and playAll buttons 
			if(processesSelection[i]==true)
			{
				toolbar.setPlayButtonEnable(true);
				toolbar.setPlayAllButtonEnable(true);
				processSelected=true;
				break;
			}
		}
		//if no process was selected disable the play and playAll buttons
		if(!processSelected)
		{
			toolbar.setPlayButtonEnable(false);
			toolbar.setPlayAllButtonEnable(false);
		}
	}
}
