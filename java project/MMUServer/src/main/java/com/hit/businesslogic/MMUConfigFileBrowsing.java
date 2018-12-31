package com.hit.businesslogic;

/////////////////////////
//MMUConfigFileBrowsing Class
//Searches file that user requests in the folder
/////////////////////////
import java.io.File;

public class MMUConfigFileBrowsing {
	
	private File folder;
	private File[] filesArray;
	
	public MMUConfigFileBrowsing() {
		//folder that holds all the files
		folder=new File("src/main/resources/com/hit/config");
		//list of files on the folder
		filesArray=folder.listFiles();
	}
	
	//search the file in the folder
	public String getFile(String fileName)
	{
		String path=folder+"\\"+fileName;
		for(int i=0;i<filesArray.length;i++)
		{
			//if the file is found, return the file's path
			if(path.equals(filesArray[i].toString()))
			{
				return path;
			}
		}
		//if the file was not found
		return null;
	}
}
