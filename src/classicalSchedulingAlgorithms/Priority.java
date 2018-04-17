package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class Priority {
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public void execute(){
		int time;
		while(running){
			if(arrivedProcesses.size() != 0 || processes.size() != 0){
				while(true){
					if(processes.size() != 0 && processes.get(0).getArrivalTime() <= totalBurstTime ){
						arrangeArrivedProcesses(processes.get(0));
						processes.remove(0);
					}else{
						break;
					}
				}
				if(arrivedProcesses.size() != 0){
					System.out.println("Process: " + arrivedProcesses.get(0).getPID());
					time = arrivedProcesses.get(0).getBurstTime()-1;
					arrivedProcesses.get(0).setBurstTime(time);
					totalBurstTime++;
					if(arrivedProcesses.get(0).getBurstTime() == 0){
						arrivedProcesses.remove(0);
					}
				}else{
					totalBurstTime++;
				}
			}else{
				break;
			}
		}
		System.out.println("Total: " + totalBurstTime);	
	}
	
	public void arrangeArrivedProcesses(ProcessControlBlock process){
		
		arrivedProcesses.add(process);
		
		for(int a = 0; a < arrivedProcesses.size(); a++){
			if((process.getPriority() < arrivedProcesses.get(a).getPriority()) ){
				for(int b = arrivedProcesses.size() - 1; b >= a + 1 ; b--){
					arrivedProcesses.set(b, arrivedProcesses.get(b-1));
				}
				arrivedProcesses.set(a, process);
				break;
			}
		}
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
		
		for(int a = 0; a < processes.size(); a++){
			if((process.getArrivalTime() < processes.get(a).getArrivalTime()) && processes.size() > 1){
				for(int b = processes.size() - 1; b >= a + 1 ; b--){
					processes.set(b, processes.get(b-1));
				}
				processes.set(a, process);
				break;
			}
		}
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
