package com.hit.view;
import java.util.ArrayList;
import java.util.List;
/////////////////////////
//RamHandler Class
/////////////////////////

public class RamHandler {
	
	//holds the log list data
	private List<String> logList;
	private int currInd=0;
	private int ramCapacity=0;
	private int processesNum=0;
	Boolean[] processesSelection;
	private Dispatcher dispatcher;

	//set the dispatcher to communicate between the different GUI components
	public RamHandler (Dispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}
	
	//set the log list current counter to 0
	public void resetRam() {
		currInd=0;
	}

	//handle playAll
	public void playAll() {
		
		//check if we reached the end of log list
		checkResetRam ();
		
		//do next step until end of log list
		while (currInd < logList.size())
		{
			nextStep();
		}
		
	}
	

	public void play()
	{
		//check if we reached the end of log list
		checkResetRam();
		nextStep ();
	}

	//handle next step, handle next line in the log file
	private void nextStep() {
		
		//if it is a page fault,notify the dispatcher to handle page fault
		if(logList.get(currInd).startsWith("PF:"))
		{
			dispatcher.handlePageFault(getNumFromLine("PF:",logList.get(currInd)));
		}
		//if it is a page replacement,notify the dispatcher to handle page replacement
		else if(logList.get(currInd).startsWith("PR:"))
		{
			String[] pRSpaceString = logList.get(currInd).split(" ");
			int pageToHD = Integer.parseInt(pRSpaceString[1]);
			int pageToRam = Integer.parseInt(pRSpaceString[3]);
			dispatcher.handlePageReplacement(pageToHD, pageToRam);
		}
		//if it is a get page command,notify the dispatcher to handle get page command
		else //GP
		{
			String[] gpSpaceString = logList.get(currInd).split(" ");
			int processNum=getNumFromLine("GP:P",gpSpaceString[0]);
			//ignore the process that is not selected 
			//send the page data to the dispatcher for selected processes 
			if(processesSelection[processNum])
			{
				int pageNum=Integer.parseInt(gpSpaceString[1]);
				int indexOpenBracket=logList.get(currInd).indexOf('[');
				int indexCloseBracker=logList.get(currInd).length()-1;
				String dataString=logList.get(currInd).substring(indexOpenBracket+1,indexCloseBracker);
				String[] dataValues=dataString.split(",");
				int[] intDataValues=new int[dataValues.length];
				for(int i=0;i<dataValues.length;i++)
				{
					intDataValues[i]=Integer.parseInt(dataValues[i].replaceAll("\\s",""));
				}
				
				//send get page data to dispatcher
				dispatcher.handleGetPage(processNum,pageNum,intDataValues);
			}
		}

		//proceed to next line
		currInd++;
		//check if it is the end of the log file
		if (currInd == logList.size())
		{
			dispatcher.logFileEnded();
		}
					
	}

	//if current index points to the end of log list then reset the ram
	private void checkResetRam() {
		if(currInd==logList.size())
		{
			dispatcher.resetRam();
			currInd=0;
		}		
	}

	//sets the log list and remove the first two lines needed only for init
	public void setLogList(List<String> logList) {
		this.logList=logList;
		ramCapacity=getNumFromLine("RC:", logList.get(0));
		logList.remove(0);
		processesNum=getNumFromLine("PN:",logList.get(0));
		logList.remove(0);
		processesSelection=new Boolean[processesNum];
	}
	
	//general function to get the number from the log file line
	public int getNumFromLine(String strToCompare, String line) {
		
		int number=0; 
			
		if(line.startsWith(strToCompare))
		{
			//check if it is a number
			  try  
			  {  
				  number=Integer.parseInt(line.substring(strToCompare.length()));
			  }  
			  catch(NumberFormatException e)  
			  {  
			      return 0;
			  }  
		}
		return number;
		
	}

 
	public int getRamCapacity ()
	{
		return ramCapacity;
	}

	public int getProcessesNum ()
	{
		return processesNum;
	}

	public void setProcessesFilter(Boolean[] processesSelection) {
		this.processesSelection=processesSelection;
	}
	
	//the function needed for hard disk and ram graphic simulation
	
	public ArrayList<Integer> getListOfPages() {
		
		
		ArrayList<Integer> arrayOfPages = new ArrayList<Integer>();
		//get the list of all the pages in the log list
		for(int i=0;i<logList.size();i++)
		{
			//if page fault
			if(logList.get(i).startsWith("PF:"))
			{
				int pageNum=getNumFromLine("PF:",logList.get(i));
				//add to arrayOfPages only if it is not there already
				if(!arrayOfPages.contains(pageNum))
					arrayOfPages.add(pageNum);
			}
			//if page replacement
			else if(logList.get(i).startsWith("PR:"))
			{
				String[] pRSpaceString = logList.get(i).split(" ");
				int pageToRam = Integer.parseInt(pRSpaceString[3]);
				//add to arrayOfPages only if it is not there already
				if(!arrayOfPages.contains(pageToRam))
					arrayOfPages.add(pageToRam);
			}
		}
		
		return arrayOfPages;
	}

}
