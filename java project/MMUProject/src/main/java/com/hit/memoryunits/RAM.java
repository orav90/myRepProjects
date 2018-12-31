package com.hit.memoryunits;

import java.util.HashMap;
import java.util.Map;

/////////////////////////
//RAM Class
//Handles ram
/////////////////////////
public class RAM {
	
	//ram size
	private int initialCapacity;
	//ram container
	private Map<java.lang.Long,Page<byte[]>> pagesMap;
	
	
	RAM(int initialCapacity){
		this.initialCapacity=initialCapacity;
		pagesMap=new HashMap<java.lang.Long,Page<byte[]>>(initialCapacity);
	}

	public java.util.Map<java.lang.Long,Page<byte[]>> getPages()
	{
		return pagesMap;
	}
	
	public void setPages(java.util.Map<java.lang.Long , Page<byte[]>> pages)
	{
		this.pagesMap=pages;
	}
	
	public Page<byte[]> getPage(java.lang.Long pageId)
	{
		return pagesMap.get(pageId);
	}
	
	public void addPage(Page<byte[]> addPage)
	{
		//if ram does not contain the page, add it on ram
		if(!pagesMap.containsKey(addPage.getPageId()))
		{
			pagesMap.put(addPage.getPageId(),addPage);
		}
	}
	
	public void removePage(Page<byte[]> removePage) 
	{
		//if ram contains the page, remove it from ram
		if(pagesMap.containsKey(removePage.getPageId()))
		{
			pagesMap.remove(removePage.getPageId());
		}
		
	}
	
	//get pages contents according to pageIds
	public Page<byte[]>[] getPages(java.lang.Long[] pageIds)
	{
		@SuppressWarnings("unchecked")
		Page<byte[]>[] pagesToGet= new Page[pageIds.length];
		
		for(int i=0;i<pageIds.length;++i) 
		{
			pagesToGet[i]=getPage(pageIds[i]);
		}
		return pagesToGet;
	}
	
	//add pages to pagesMap(ram container) if the pages does not exist
	public void addPages(Page<byte[]>[] addPages)
	{
		for(Page<byte[]> i:addPages)
		{
			if(!pagesMap.containsKey(i.getPageId()))
			{
				pagesMap.put(i.getPageId(), i);
			}
		}
	}
	
	//remove pages from ram
	@SuppressWarnings("unlikely-arg-type")
	public void removePages(Page<byte[]>[] removePages)
	{
		for(Page<byte[]> page:removePages)
		{
			if(pagesMap.containsKey(page.getPageId()))
				pagesMap.remove(page);
		}
	}
	
	public int getInitialCapacity()
	{
		return initialCapacity;
	}
	
	public void setInitialCapacity(int initialCapacity)
	{
		this.initialCapacity=initialCapacity;
	}
	
	
	
}
