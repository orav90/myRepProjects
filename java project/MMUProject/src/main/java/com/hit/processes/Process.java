package com.hit.processes;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import com.hit.exception.InconsistencePagesAndData;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

/////////////////////////
//Process Class
//Handles configuration json file structure
/////////////////////////
public class Process implements Callable<Boolean> {
	
	private int id;
	private MemoryManagementUnit mmu;
	private ProcessCycles processCycles;

	public Process(int id,MemoryManagementUnit mmu,ProcessCycles processCycles)
	{
		this.id=id;
		this.mmu=mmu;
		this.processCycles=processCycles;
		
	}
	@Override
	public Boolean call() throws Exception {
		
		
		for(ProcessCycle processCycleIter:processCycles.getProcessCycles())
		{
			int ms;
			synchronized(mmu)
			{
				Long[] pagesIds=new Long[processCycleIter.getPages().size()];
				int i=0;
			
				for(Long pageIdIter:processCycleIter.getPages())
				{
					pagesIds[i++]=pageIdIter;
				}
		
				//get the process data from json
				java.util.List<byte[]> newData=processCycleIter.getData();
				//get the pages before update from mmu
				Page<byte[]>[] pagesBeforeUpdate=mmu.getPages(pagesIds);
					
				if(newData.size()!=pagesBeforeUpdate.length)
				{
					MMULogger.getInstance().write("In process id"+id+"number of updated pages is inconsistent with data",Level.SEVERE);
					throw new InconsistencePagesAndData();
				}
					
				for(int p=0;p<processCycleIter.getPages().size();++p)
				{
					//if newData is not null
					if(newData.get(p)!=null)
					{
						//update the pages requested from mmu with new data
						pagesBeforeUpdate[p].setContent(newData.get(p));
						MMULogger.getInstance().write("GP:P" + id + " " + pagesBeforeUpdate[p].toString(), Level.INFO);
					}
					else //new Data is empty
					{
						MMULogger.getInstance().write("GP:P" + id + " " +pagesBeforeUpdate[p].getPageId()+"[]", Level.INFO);
					}
				}
			}//end synchronized
			
			//get sleep time for process
			ms=processCycleIter.getSleepMs();
			Thread.sleep(ms);
		}
		return true;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
