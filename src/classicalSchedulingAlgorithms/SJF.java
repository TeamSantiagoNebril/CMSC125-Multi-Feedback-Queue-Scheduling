package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class SJF{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	private boolean executingProcess;
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public void execute(){
		int time;
		if(arrivedProcesses.size() != 0){
			executingProcess = true;
			System.out.println("Process: " + arrivedProcesses.get(0).getPID());
			time = arrivedProcesses.get(0).getBurstTime()-1;
			arrivedProcesses.get(0).setBurstTime(time);
			totalBurstTime++;
			
			if(arrivedProcesses.get(0).getBurstTime() == 0){
				executingProcess = false;
				arrivedProcesses.remove(0);
			}
			
		}else{
			totalBurstTime++;
		}
	}
	
	public void addProcess(ProcessControlBlock process){
		
		arrivedProcesses.add(process);
		int a;
		if(executingProcess){
			a = 1;
		}else{
			a = 0;
		}
		
		for(; a < arrivedProcesses.size(); a++){
			if((process.getBurstTime() < arrivedProcesses.get(a).getBurstTime()) ){
				for(int b = arrivedProcesses.size() - 1; b >= a + 1 ; b--){
					arrivedProcesses.set(b, arrivedProcesses.get(b-1));
				}
				arrivedProcesses.set(a, process);
				break;
			}
		}
	}
	
	public ProcessControlBlock removeProcess(int a){
		ProcessControlBlock proc = arrivedProcesses.get(a);
		arrivedProcesses.remove(a);
		return proc;
	}
	
	public boolean isProcessing(){
		if(arrivedProcesses.size() != 0){
			return true;
		}
		
		return false;
	}
	
	public void signalToEnd(){
		endSignal = true;
	}
	
	public void stop_thread(){
		running = false;
	}
	
	public static void executeAlgorithm(PriorityQueue<ProcessControlBlock> queue, ArrayList<GanttChartElement> ganttChart) {
		
	}
}
