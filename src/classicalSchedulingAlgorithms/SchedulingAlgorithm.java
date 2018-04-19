package classicalSchedulingAlgorithms;

import java.util.ArrayList;

import processInformation.ProcessControlBlock;

public class SchedulingAlgorithm {
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	public void addProcess(ProcessControlBlock process){
	}
	
	public void execute(){
		
	}
	
	public boolean isProcessing(){
		
		return false;
	}
	
	public ProcessControlBlock getProcess(){
		return processes.get(0);
	}
	
	public ProcessControlBlock removeProcess(){
		return processes.get(0);
	}
}
