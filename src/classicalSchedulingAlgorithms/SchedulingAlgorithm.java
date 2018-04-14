package classicalSchedulingAlgorithms;

import java.util.ArrayList;

import processInformation.ProcessControlBlock;

public class SchedulingAlgorithm {
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	public void arriveProcess(ProcessControlBlock process){
		processes.add(process);
	}
	
	public void signalToEnd(){
		endSignal = true;
	}
	
	public void stop_thread(){
		running = false;
	}
}
