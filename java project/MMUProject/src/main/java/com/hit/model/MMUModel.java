package com.hit.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.MRUAlgoCacheImpl;
import com.hit.algorithm.Random;
import com.hit.exception.HardDiskException;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.LogFileLoader;
import com.hit.util.MMULogger;

/////////////////////////
//MMUModel Class
//The model component of MVC pattern
/////////////////////////
public class MMUModel extends Observable implements Model {

	int numProcesses; 
	int ramCapacity;
	private MemoryManagementUnit mmu;
	private RunConfiguration runConfig;
	public static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration2.json";
	
	public MMUModel()
	{
		//read configuration file
		runConfig = readConfigurationFile();
	}
	
	//set configuration according to controller
	public void setConfiguration(List<String> configuration) 
	{
		IAlgoCache<Long, Long> algo = null;
		//set ram capacity
		ramCapacity=Integer.parseInt(configuration.get(1));
		//set algorithm
		algo=getAlgo(configuration.get(0));
		try {
			//create mmu
			mmu = new MemoryManagementUnit(ramCapacity, algo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//select mmu algorithm
	public IAlgoCache<Long, Long> getAlgo(String algoName)
	{
		IAlgoCache<Long, Long> algo=null;
		switch(algoName)
		{
			case "LRU":
			{
				algo=new LRUAlgoCacheImpl<Long,Long>(ramCapacity);
				break;
			}
			case "MRU":
			{
				algo=new MRUAlgoCacheImpl<Long,Long>(ramCapacity);
				break;
			}
			case "RANDOM":
			{
				algo=new Random<Long,Long>(ramCapacity);
				break;
			}
		}
		return algo;
	}
	
	
	@Override
	public void start() {
		List<ProcessCycles> processCycles = runConfig.getProcessesCycles();
		List<Process> processes = createProcesses(processCycles, mmu);
		MMULogger.getInstance().write("RC:" + ramCapacity, Level.INFO);
		MMULogger.getInstance().write("PN:" + processes.size(), Level.INFO);
		runProcesses(processes);
		
		try {
			//shuts down mmu after processes finished
			mmu.shutDown();
			MMULogger.getInstance().closeLogger();
			LogFileLoader logLoader = new LogFileLoader(MMULogger.getInstance().getLogFileName());
			List<String> logList = logLoader.load();

			//observer pattern
			// Notify observers
			setChanged();
			//send logList to view component of MVC
			notifyObservers(logList);
		} catch (HardDiskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	//run processes
	public static void runProcesses(List<Process> processes) {
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<Boolean>> list = new ArrayList<Future<Boolean>>(processes.size());
		for(int i=0;i<processes.size();i++)
		{
			Future<Boolean> future = executor.submit(processes.get(i));
			list.add(future);
		}
		
		for(Future<Boolean> fut : list)
		{
			try {
				fut.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		
	}
	
	//create processes
	public static List<Process> createProcesses(List<ProcessCycles> processCycles, MemoryManagementUnit mmu) {
		List<Process> processArray = new ArrayList<>();
		
		for(int i = 0 ; i < processCycles.size(); i++) {
			Process p=new Process(i, mmu, processCycles.get(i));
			processArray.add(p);
		}
		
		return processArray;
	}
	
	
	//read json file
	public static RunConfiguration readConfigurationFile(){
		Gson gson = new Gson();
		RunConfiguration runConfig = null;
		try {
			runConfig = gson.fromJson(new FileReader(CONFIG_FILE), RunConfiguration.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return runConfig;
	}


	public void setConfigFile(RunConfiguration runConfig ) {
		this.runConfig=runConfig;
		
	}


}
