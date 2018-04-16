import java.util.ArrayList;

import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
import classicalSchedulingAlgorithms.Priority;
import classicalSchedulingAlgorithms.RoundRobinScheduling;
import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;
import classicalSchedulingAlgorithms.SJF;
import processInformation.ProcessControlBlock;

public class MLFQ {
	private ArrayList<Integer>entryQueue = new ArrayList<Integer>();
	private int[] entryQueueElements= {2, 1, 0, 0, 2, 2, 1};
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	private int time;
	public MLFQ(){
		for(int a = 0; a < 7; a++){
			entryQueue.add(entryQueueElements[a]);
		}
	}
	
	public void execute(){
		SJF q[] = new SJF[3];
		q[0] = new SJF();
		q[1] = new SJF();
		q[2] = new SJF();
		while(true){
			if((processes.size() != 0) && (processes.get(0).getArrivalTime() <= time)){
				q[entryQueue.get(0)].addProcess(processes.get(0));
				entryQueue.remove(0);
				processes.remove(0);
			}
			
			if(q[0].isProcessing()){
				q[0].execute();
			}else if(q[1].isProcessing()){
				q[1].execute();
			}else if(q[2].isProcessing()){
				q[2].execute();
			}else if(processes.size() == 0){
				break;
			}
			time++;
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
	
	
}
