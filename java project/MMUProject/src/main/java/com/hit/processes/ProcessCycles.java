package com.hit.processes;

/////////////////////////
//ProcessCycles Class
//Handles configuration json file structure
/////////////////////////
public class ProcessCycles {

	private java.util.List<ProcessCycle> processCycles;
	
	ProcessCycles(java.util.List<ProcessCycle> processCycles) 
	{
		this.processCycles=processCycles;
	}

	public java.util.List<ProcessCycle> getProcessCycles() {
		return processCycles;
	}

	public void setProcessCycles(java.util.List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}
	
	@Override
	public String toString() {
		return "ProcessCycles [processCycles=" + processCycles + "]";
	}
}
