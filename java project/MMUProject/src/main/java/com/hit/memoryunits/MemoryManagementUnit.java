package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import com.hit.algorithm.IAlgoCache;
import com.hit.exception.AlgoRamUnsync;
import com.hit.exception.HardDiskException;
import com.hit.util.MMULogger;


/////////////////////////
//MemoryManagementUnit Class
//Handles memory management units: ram,hard disk, algo cache
/////////////////////////
public class MemoryManagementUnit {

	private RAM ram;
	private IAlgoCache<java.lang.Long,java.lang.Long> algo;
	private HardDisk hardDisk;
	
	//C'tor, save ram capacity and algorithm to use
	public MemoryManagementUnit(int ramCapacity, com.hit.algorithm.IAlgoCache<java.lang.Long,java.lang.Long> algo) throws IOException 
	{
		this.ram=new RAM(ramCapacity);
		this.algo=algo;
	}
	
	//get pages
	public Page<byte[]>[] getPages(java.lang.Long[] pageIds)throws java.io.IOException, HardDiskException,AlgoRamUnsync, ClassNotFoundException{
		
		@SuppressWarnings("unchecked")
		//create page contents array in the size of pageIds - return value
		Page<byte[]>[] pageContents=new Page[pageIds.length];
		Page<byte[]> page;
		hardDisk = HardDisk.getInstance();
		for(int i=0;i<pageIds.length;++i)
		{
			//if cache doesn't contain this pageId
			if(algo.getElement(pageIds[i]) == null)
			{
				//if Ram is not full
				if(ram.getPages().size()<ram.getInitialCapacity())
				{
					page=hardDisk.pageFault(pageIds[i]);//get page from hard disk
					ram.addPage(page);//add page to ram
					algo.putElement(pageIds[i], pageIds[i]);//add page to cache
					MMULogger.getInstance().write("PF:"+pageIds[i],Level.INFO);
				}
				else//if Ram is full
				{	
					//get page to replace from the algorithm
					Long pageIdToReplace=algo.putElement(pageIds[i], pageIds[i]);
					//get the updated page to replace from the ram
					Page<byte[]> pageToReplace=ram.getPage(pageIdToReplace);
					if(pageToReplace==null)
					{
						MMULogger.getInstance().write("Inconsistenty of ram and algorithm cache",Level.SEVERE);
						throw new AlgoRamUnsync();
					}
					//remove the page to replace from ram
					ram.removePage(pageToReplace);
					//save the updated page to replace on the hard disk
					page=hardDisk.pageReplacement(pageToReplace,pageIds[i]);
					//add the requested page from hard disk
					ram.addPage(page);
					MMULogger.getInstance().write("PR:MTH "+pageIdToReplace+" MTR "+pageIds[i],Level.INFO);

				}
				//update requested pages array
				pageContents[i]=page;
			}
			//page exists on cache
			else
			{
				//update requested pages array
				pageContents[i]=ram.getPage(pageIds[i]);
			}

		}
		return pageContents;
	}
	
	//handle shut down
	@SuppressWarnings("unlikely-arg-type")
	public void shutDown() throws HardDiskException, FileNotFoundException, IOException
	{
		for(Map.Entry<Long,Page<byte[]>> pageIter : ram.getPages().entrySet())
		{
			Long pageId=pageIter.getKey();
			//compare the page on the ram with the page on the hard disk
			//if the page on ram is different than the copy of that page of hard disk,
			//then update the page on hard disk
			if (!ram.getPage(pageId).equals(hardDisk.pageFault(pageId).getContent())) {
				hardDisk.pageReplacement(pageIter.getValue(),pageId);
			}
		}
		hardDisk.writeHardDiskToFile();
	
	}
	
	

}
