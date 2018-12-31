package com.hit.processes;

/////////////////////////
//RunConfiguration Class
//Handles configuration json file structure
/////////////////////////
public class RunConfiguration {

	private java.util.List<ProcessCycles> processesCycles;

	RunConfiguration(java.util.List<ProcessCycles> processesCycles) 
	{
		this.processesCycles=processesCycles;
	}
	
	public java.util.List<ProcessCycles> getProcessesCycles() {
		return processesCycles;
	}

	public void setProcessesCycles(java.util.List<ProcessCycles> processesCycles) {
		this.processesCycles = processesCycles;
	}
	
	@Override
	public String toString() {
		return "RunConfiguration [processesCycles=" + processesCycles + "]";
	}
}
