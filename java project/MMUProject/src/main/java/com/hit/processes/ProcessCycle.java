package com.hit.processes;

/////////////////////////
//ProcessCycle Class
//Handles configuration json file structure
/////////////////////////
public class ProcessCycle {

	//sleep time in current cycle
	private int sleepMs;
	private java.util.List<Long> pages;
	private java.util.List<byte[]> data;
	

	ProcessCycle(java.util.List<java.lang.Long> pages, int sleepMs, java.util.List<byte[]> data)
	{
		this.sleepMs=sleepMs;
		this.pages=pages;
		this.data=data;
	}
	
	public int getSleepMs() {
		return sleepMs;
	}

	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}

	public java.util.List<Long> getPages() {
		return pages;
	}

	public void setPages(java.util.List<Long> pages) {
		this.pages = pages;
	}

	public java.util.List<byte[]> getData() {
		return data;
	}

	public void setData(java.util.List<byte[]> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ProcessCycle [sleepMs=" + sleepMs + ", pages=" + pages + ", data=" + data + "]";
	}
}
