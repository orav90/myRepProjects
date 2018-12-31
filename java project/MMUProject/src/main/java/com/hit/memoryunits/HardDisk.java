package com.hit.memoryunits;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.hit.exception.HardDiskException;
import com.hit.util.MMULogger;


/////////////////////////
//	HardDisk Class
//	Simulates hard disk
//	Supports page fault,page replacement and shut down
/////////////////////////
public class HardDisk {

	//default size, represents number of pages on hard disk
	private final int _SIZE=1000; 
	//default file for saving hard disk 
	private final String DEFAULT_FILE_NAME="src/main/resources/hardDisk/HDPages.txt";
	private static HardDisk instance=null;
	//container for hard disk pages
	private Map<java.lang.Long,Page<byte[]>> pagesHardDisk;	
	
	@SuppressWarnings("unchecked")
	private HardDisk() throws IOException, HardDiskException, ClassNotFoundException 
	{
		pagesHardDisk=new HashMap<Long, Page<byte[]>>(_SIZE); 
		ObjectInputStream in = null;
		
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(DEFAULT_FILE_NAME)));
		
			//read pages from file(hard disk) and save them on the container
			pagesHardDisk = (HashMap<Long, Page<byte[]>>) in.readObject(); 
			
		} catch (FileNotFoundException e) {
			MMULogger.getInstance().write("Hard disk was not found",Level.SEVERE);
			throw new HardDiskException();
		} catch (IOException e) {
			MMULogger.getInstance().write("Hard disk IO is not working",Level.SEVERE);
			throw new HardDiskException();
		} catch (ClassNotFoundException e) {
			MMULogger.getInstance().write("Cannot read page from hard disk",Level.SEVERE);
			throw e;
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				MMULogger.getInstance().write("Fail to disconnect from hard disk",Level.SEVERE);
				throw new HardDiskException();
			}
		}

	}
	
	//singelton pattern, create only one instance of hard disk
	public synchronized static HardDisk getInstance() throws IOException, ClassNotFoundException, HardDiskException
	{
		if(instance==null)
		{
			instance=new HardDisk();
		}
		return instance;
	}
	
	//handle page fault
	//if the requested page does not exist on hard disk then throw exception
	//otherwise return page
	public Page<byte[]> pageFault(java.lang.Long pageId)throws HardDiskException, IOException
	{
		//if the requested page does not exist on the hard disk
		if (!pagesHardDisk.containsKey(pageId))
		{
			MMULogger.getInstance().write("Page id "+pageId+" does not exist on hard disk",Level.SEVERE);
			throw new HardDiskException();
		}
		return pagesHardDisk.get(pageId);
	}
	
	//handle page replacement
	//if the requested page does not exist on the hard disk throw exception
	//otherwise replace the old version of the page on the hard disk with the updated version from the ram
	//and return the requested page
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage,java.lang.Long moveToRamId)throws HardDiskException, IOException
	{
		
		if (!pagesHardDisk.containsKey(moveToHdPage.getPageId()))
		{
			MMULogger.getInstance().write("Page id "+moveToHdPage.getPageId()+" does not exist on hard disk",Level.SEVERE);
			throw new HardDiskException();
		}

		Page<byte[]> oldPageOnHD = pagesHardDisk.get(moveToHdPage.getPageId());
		
		//if the page on the ram was updated, save the updates on the hard disk
		if (!oldPageOnHD.getContent().equals(moveToHdPage.getContent())) {
			pagesHardDisk.remove(moveToHdPage.getPageId());
			pagesHardDisk.put(moveToHdPage.getPageId(), moveToHdPage);
		}
		
		//return from hard disk to ram
		return pageFault(moveToRamId);
	}
	
	//handle shut down
	//write hard disk to file
	public void writeHardDiskToFile() throws HardDiskException, IOException  {
		ObjectOutputStream out = null;
					
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(DEFAULT_FILE_NAME)));
			out.writeObject(pagesHardDisk);
		} catch (FileNotFoundException e) {
			MMULogger.getInstance().write("Hard disk was not found",Level.SEVERE);
			throw new HardDiskException();
		} catch (IOException e) {
			MMULogger.getInstance().write("Hard disk IO is not working",Level.SEVERE);
			throw new HardDiskException();
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				MMULogger.getInstance().write("Fail to disconnect from hard disk",Level.SEVERE);
				e.printStackTrace();
			}
		}
			
			
		
		

	}
	
}

