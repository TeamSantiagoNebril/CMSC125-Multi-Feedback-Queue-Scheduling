package mlfq;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

//import classicalSchedulingAlgorithms.FCFS;
//import classicalSchedulingAlgorithms.NPPriority;
//import classicalSchedulingAlgorithms.Priority;
//import classicalSchedulingAlgorithms.RoundRobinScheduling;
//import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;
//import classicalSchedulingAlgorithms.SJF;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class MLFQ {
	
	private int promotionMode;
	private int demotionMode;
	private int retentionMode;
	//Migration Policy Constants
	public static final int DEFAULT_PROMOTION = 1;
	public static final int DEFAULT_DEMOTION = 2;
	public static final int DEFAULT_RETENTION = 3;
	
	private int entry;
	//Entry Policy Constants
	//public static final int DEFAULT_ENTRY = 11;
	public static final int FIRST_ENTRY = 12;
	public static final int LAST_ENTRY = 13;
		
	private int priorityMode;
	//Priority Policy Constants
	public static final int HIGH_BEFORE_LOW = 21;
	public static final int FIXED_TIME = 22;
		
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private int time;
	
	private int numQueues;
	private int[] schedulingAlgorithms;
	private int[] forPosRR;
	private Queue<ProcessControlBlock>[] queues;
	private ArrayList<GanttChartElement> ganttChart;
	

	public MLFQ(int numQueues, int[] schedulingAlgorithms){
		
		ganttChart = new ArrayList<GanttChartElement>();
		//Initialize Default Mode
		promotionMode = DEFAULT_PROMOTION;
		demotionMode = DEFAULT_DEMOTION;
		retentionMode = DEFAULT_RETENTION;
		entry = 0;
		priorityMode = HIGH_BEFORE_LOW;
		forPosRR = new int[numQueues];
		if(numQueues < 1) {
			System.err.println("Number of Queues should be greater than 1");
			System.exit(-1);
		}
		
		if(schedulingAlgorithms.length != numQueues) {
			System.err.println("Number of Scheduling Algorithms given does not match number of Queues");
			System.exit(-1);
		}
		
		this.numQueues = numQueues;
		this.schedulingAlgorithms = schedulingAlgorithms;
		queues = new Queue[numQueues];
		
		for(int counter = 0; counter < this.numQueues; counter++) { // Para sa Queue
			switch(this.schedulingAlgorithms[counter]) {
				case 1: //FCFS
					queues[counter] = new PriorityQueue<ProcessControlBlock>(20, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
					    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
					        return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
					    }
					});
					break;
				case 2: //SJF
					queues[counter] = new PriorityQueue<ProcessControlBlock>(20, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
					    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
					        if(one.getHasBegun() == true) {
					        	return -1;
					        } else if(other.getHasBegun() == true) {
					        	return 1;
					        } else {
					        	if(one.getBurstTime() == other.getBurstTime()) {
					        		return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
					        	}else {
					        		return one.getBurstTimeInInteger().compareTo(other.getBurstTime());
					        	}
					        }
					    }
					});
					break;
				case 3: //SRTF
					queues[counter] = new PriorityQueue<ProcessControlBlock>(20, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
					    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
					    	if(one.getBurstTime() == other.getBurstTime()) {
				        		return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
				        	}else {
				        		return one.getBurstTimeInInteger().compareTo(other.getBurstTime());
				        	}
					    }
					});
					break;
				case 4: //NP-Prio
					queues[counter] = new PriorityQueue<ProcessControlBlock>(20, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
					    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
					        if(one.getHasBegun() == true) {
					        	return -1;
					        } else if(other.getHasBegun() == true) {
					        	return 1;
					        } else /*if(one.getHasBegun() == false && other.getHasBegun() == false)*/ {
					        	if(one.getPriority() == other.getPriority()) {
					        		return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
					        	}else {
					        		return one.getPriorityInInteger().compareTo(other.getPriority());
					        	}
					        }
					    }
					});
					break;
				case 5: //Preemptive Prio
					queues[counter] = new PriorityQueue<ProcessControlBlock>(20, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
					    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
					    	if(one.getPriority() == other.getPriority()) {
				        		return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
				        	}else {
				        		return one.getPriorityInInteger().compareTo(other.getPriority());
				        	}
					    }
					});
					break;
				case 6: //RoundRobin
					queues[counter] = new LinkedList<ProcessControlBlock>();
					break;
			}
		}
		

	}
	

	public void execute(){
		time = 0;
		while(true) {

			if((processes.size() != 0) && (processes.get(0).getArrivalTime() <= time)){

				queues[entry].add(processes.get(0));
				processes.remove(0);
			} else if(processes.size() == 0) {
				boolean testIfEmptyAll = false;
				for(int i = 0; i < numQueues; i++) {
					if(queues[i].peek() != null) {
						//System.out.println(i);
						testIfEmptyAll = true;
					}
				}
				if(testIfEmptyAll == false) {
					break;
				}
			}
			
			performExecution();
			//checkMigration();
			time++;
		}
	}
	
	public void performExecution() {
		switch(priorityMode) {
			case HIGH_BEFORE_LOW:
				for(int counter = 0; counter < queues.length; counter++) {
					if(queues[counter].peek() == null) {
						continue;
					} else {
						System.out.println("Queue number: " + counter);
						if(queues[counter].peek().getHasBegun() == false) {
							queues[counter].peek().setHasBegun();
						}
						
						
/***************************GanttChart Purpose************************************/						
						if(ganttChart.size() == 0 || ganttChart.get(ganttChart.size()-1).getPID() != queues[counter].peek().getPID()) {
							if(ganttChart.size() != 0) {
								int endTime = time;
								ganttChart.get(ganttChart.size()-1).setEndTime(endTime);
							}
							int PID = queues[counter].peek().getPID();
							int beginTime = time;
							ganttChart.add(new GanttChartElement(PID, beginTime, 0));
						}
/***************************GanttChart Purpose************************************/
						
						queues[counter].peek().decBurstTime();
						ProcessControlBlock temp;
						if(schedulingAlgorithms[counter] != 6)
						{
							temp = queues[counter].remove();
							queues[counter].add(temp);
							if(temp.getPID() != queues[counter].peek().getPID()) { //if this is true then na pre empt yung process
								if(counter > 0) {
									System.out.println("Hala naaaa");
									queues[counter].remove(temp);
									queues[counter-1].add(temp);
								}
							}
						}
						
						if(schedulingAlgorithms[counter] == 6) {
							forPosRR[counter]++;
							if(forPosRR[counter] == 5) {
								temp = queues[counter].remove();
								if(temp.getBurstTime() != 0) {
									System.out.println("Hindi siya pumupunta dito");
									temp.incCPUPreempCounter();
									checkMigration(counter, temp);
									
								}
								forPosRR[counter] = 0;
							}
						}

						if(queues[counter].peek().getBurstTime() == 0) {
							
							
/***************************GanttChart Purpose************************************/		
							if(ganttChart.get(ganttChart.size()-1).getPID() == queues[counter].peek().getPID()) {
								int endTime = time + 1;
								ganttChart.get(ganttChart.size()-1).setEndTime(endTime);
							}
/***************************GanttChart Purpose************************************/	
							
							
							queues[counter].remove();
						}
					}
					break;
				}
				break;
			case FIXED_TIME:
				
				break;
		}
	}
	
	
	public void checkMigration(int counter, ProcessControlBlock temp) {
		switch(promotionMode) {
		
		}
		
		switch(demotionMode) {
			case DEFAULT_DEMOTION:
					queues[counter].add(temp);
				break;
		}
		
		switch(retentionMode) {
		
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
	
	
	public void initParameters(int migrationPromotion, int migrationDemotion, int migrationRetention, int entryPolicy, int priorityPolicy) {
		
		promotionMode = migrationPromotion;
		demotionMode = migrationDemotion;
		retentionMode = migrationRetention;
		
		//entryMode = entryPolicy;
		switch(entryPolicy) {
			case FIRST_ENTRY:
				entry = 0;
				break;
			case LAST_ENTRY:
				entry = queues.length-1;
				break;
		}
		priorityMode = priorityPolicy;
	}
	
	public void printGanttChart() {
		for(GanttChartElement e: ganttChart) {
			System.out.println("Process ID: "+e.getPID());
			System.out.println("begin: "+e.getBeginTime());
			System.out.println("end: "+e.getEndTime());
		}
	}
}
