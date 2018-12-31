package com.hit.memoryunits;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.exception.AlgoRamUnsync;
import com.hit.exception.HardDiskException;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class MemoryManagementUnitTest {
	private MemoryManagementUnit mmu;
	private int ramSize=10;

	@Test
	void MMUTester() throws FileNotFoundException, IOException, ClassNotFoundException, HardDiskException, AlgoRamUnsync
	{
		//create mmu
		mmu=new MemoryManagementUnit(ramSize,new LRUAlgoCacheImpl<java.lang.Long,java.lang.Long>(ramSize));
		
		//create pages array
		Long[] pageIdsToGet=new Long[2*ramSize];
		for(int i=0;i<pageIdsToGet.length;i++)
		{
			pageIdsToGet[i]=(long) i;
		}
		//print requested pages ids
		System.out.println(Arrays.toString(pageIdsToGet));

		//2 rounds with the same pages to get, we accept page faults 
		for(int numOfRounds=0;numOfRounds<2;numOfRounds++)
		{
			Page<byte[]>[] pagesFromMMU = mmu.getPages(pageIdsToGet);
			//check that the size of the accepted pages equals to the size of the requested pages
			assertEquals("true",pageIdsToGet.length,pagesFromMMU.length);
		
			
			//check that all the requested pages where accepted from mmu and print the accepted pages ids
			for(int i=0;i<pageIdsToGet.length;i++)
			{
				assertEquals("true",pagesFromMMU[i].getPageId(),pageIdsToGet[i]);
				System.out.print(pagesFromMMU[i].getPageId()+" ");
			}
		}
	
		
		
	}
	
	


}
