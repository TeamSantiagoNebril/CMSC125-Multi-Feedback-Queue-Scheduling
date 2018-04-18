import java.util.ArrayList;

import classicalSchedulingAlgorithms.ClassicalSchedulingMethods;
import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
import classicalSchedulingAlgorithms.Priority;
import classicalSchedulingAlgorithms.RoundRobinScheduling;
import classicalSchedulingAlgorithms.SRTF;
import classicalSchedulingAlgorithms.SJF;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class MLFQ {
	
	private int priority;
	private static final int HIGH_BEFORE_LOW = 31;
	private static final int FIXED_TIME_SLOT = 32;
	
	private int firstEntry = 0;
	private static final int TOP_QUEUE = 41;
	private static final int BOTTOM_QUEUE = 41;
	
	
	
//	private Boolean running = true;
//	private int totalBurstTime;
	//private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	private ArrayList<ProcessControlBlock> processes;

	private ArrayList<ProcessControlBlock>[] processQueues;
	private ArrayList<ClassicalSchedulingMethods> schedulingAlgorithms;
	private ArrayList<GanttChartElement> ganttChart;
	
	private int[] schedulingAlgorithm4EachQueue;
	private int numQueues;
	private int time;
	private int timeQuantum = 10;
	
	
	public MLFQ(int numQueues, int[] schedulingAlgos) {
		priority = HIGH_BEFORE_LOW;
		
		
		
		
		
		
		
		
		this.numQueues = numQueues;
		this.schedulingAlgorithm4EachQueue = schedulingAlgos;
		processQueues = new ArrayList[numQueues];
		for(int index = 0; index < numQueues; index++) {
			processQueues[index] = new ArrayList<ProcessControlBlock>();
		}
		schedulingAlgorithms = new ArrayList<ClassicalSchedulingMethods>();
		processes = new ArrayList<ProcessControlBlock>();
		ganttChart = new ArrayList<GanttChartElement>();
		initConfigs();
	}
	
	public void execute() {
		while(true)
		{
			if((processes.size() != 0) && (processes.get(0).getArrivalTime() <= time)){ //Insert processes here
				processQueues[firstEntry].add(processes.get(0));
				processes.remove(0);
			}
			boolean allEmpty = true;
			
			switch(priority) {
			case HIGH_BEFORE_LOW:
				for(int index = 0; index < numQueues; index++) {
					if(processQueues[index].size() != 0) {
						System.out.println(index);
						executeSpecifiedAlgo(index, processQueues[index]);
						allEmpty = false;
						break;
					}
				}
				break;
			case FIXED_TIME_SLOT:
				
				
			}
			
			if(allEmpty && processes.size() == 0 ) { 
				break;
			}
			time++;
		}
		
	}
		//executeQueue();
/*		SJF q[] = new SJF[3];
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
		}*/
	
	public void executeSpecifiedAlgo(int index, ArrayList<ProcessControlBlock> queue) {
		switch(schedulingAlgorithm4EachQueue[index]) { //Setting the correct algorithm for each queues
			case 1://FCFS
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
			case 2://SJF
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
			case 3://SRTF
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
			case 4://NPPRIO
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
			case 5://PRIO
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
			case 6://RR
				schedulingAlgorithms.get(index).execute(queue, ganttChart, time);
				break;
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
	
	public void initConfigs() {
		for(int index = 0; index < numQueues; index++) { //initializing scheduling algorithms
			switch(schedulingAlgorithm4EachQueue[index]) {
			case 1:
				schedulingAlgorithms.add(new FCFS());
				break;
			case 2:
				schedulingAlgorithms.add(new SJF());
				break;
			case 3:
				schedulingAlgorithms.add(new SRTF());
				break;
			case 4:
				schedulingAlgorithms.add(new NPPriority());
				break;
			case 5:
				schedulingAlgorithms.add(new Priority());
				break;
			case 6:
				schedulingAlgorithms.add(new RoundRobinScheduling(timeQuantum));
				break;
			}
		}
	}
	
	public void printGanttChart() {
		System.out.println("Goes here");
		for(GanttChartElement e: ganttChart) {
			System.out.println("PID: "+e.getPID() +"||bTime: "+e.getBeginTime()+"||eTime: "+e.getEndTime());
		}
	}
	
	
}
