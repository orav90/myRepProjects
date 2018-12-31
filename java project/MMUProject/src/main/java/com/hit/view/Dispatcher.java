package com.hit.view;
/////////////////////////
//Dispatcher Class 
//Interface for graphic functions
/////////////////////////
public interface Dispatcher {

	public void resetRam(); 
	public void play();
	public void playAll();
	public void handlePageFault(int pRNum);
	public void handlePageReplacement(int pageToHD, int pageToRam);
	public void handleGetPage(int processNum,int pageNum,int[] intDataValues);
	public void logFileEnded();
	public void setSelectedProcesses(Boolean[] processesSelection);
}
