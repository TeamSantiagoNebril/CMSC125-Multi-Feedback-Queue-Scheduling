package mlfq;

import java.util.ArrayList;

import classicalSchedulingAlgorithm.FCFS;
import classicalSchedulingAlgorithm.NP_Prio;
import classicalSchedulingAlgorithm.Prio;
import classicalSchedulingAlgorithm.RR;
import classicalSchedulingAlgorithm.SJF;
import classicalSchedulingAlgorithm.SRTF;
import classicalSchedulingAlgorithm.SchedulingAlgorithm;
import gui.MLFQSimulatorGUI;
import process.GanttChart;
import process.ProcessControlBlock;

public class MLFQ extends Thread{

	private SchedulingAlgorithm[] mlfQueues;
	private int prioPolicy;
	private ArrayList<ProcessControlBlock> processes;
	private int entryQueue;
	private int schedAlgo[];
	
	private GanttChart performanceChart;
	
	public MLFQ(int numQueues, int[] schedAlgo, int prioPolicy, int[] timeSlices, int entry) {
		this.schedAlgo = schedAlgo;
		mlfQueues = new SchedulingAlgorithm[numQueues];
		processes = new ArrayList<ProcessControlBlock>();
		this.prioPolicy = prioPolicy;
		int curTimeSlice = 0;
		entryQueue = entry;
		for(int i = 0; i < mlfQueues.length; i++) {
			switch(schedAlgo[i]) {
				case 1:
					mlfQueues[i] = new FCFS();
					break;
				case 2:
					mlfQueues[i] = new SJF();
					break;
				case 3:
					mlfQueues[i] = new SRTF();
					break;
				case 4:
					mlfQueues[i] = new NP_Prio();
					break;
				case 5:
					mlfQueues[i] = new Prio();
					break;
				case 6:
					mlfQueues[i] = new RR(timeSlices[curTimeSlice++]);
					break;
			}
		}
	}
	
	public void initProcesses(int[] PIDs, int[] arrivalTime, int[] burstTime, int[] priority) {
		for(int i = 0; i < PIDs.length; i++) {
			processes.add(new ProcessControlBlock(PIDs[i], arrivalTime[i], burstTime[i], priority[i]));
			processes.sort((o1, o2) -> new Integer(o1.getArrivalTime()).compareTo(o2.getArrivalTime())); //sort asc based on arrival
		}
	}
	
	
	public void run() {
		performanceChart = new GanttChart(processes);
		int time = 0;
		int previousRunQueue = -1;
		boolean preempted = false;
		int a = 0;
		if(prioPolicy == 0) { //Higher Before Lower
			
			while(true) {
				while(processes.size() > 0 && time == processes.get(0).getArrivalTime()) {
					mlfQueues[entryQueue-1].addProcess(processes.get(0));
					processes.remove(0);
				}
				
				a++;

				MLFQSimulatorGUI.addLabel(time);
				
				ProcessControlBlock output = null;
				for(int i = 0; i < mlfQueues.length; i++) {
					
					if(!mlfQueues[i].isEmptyQueue()) {
						output = mlfQueues[i].executeScheduling();
						
						if(previousRunQueue > i && preempted){
							mlfQueues[previousRunQueue - 1].addProcess(mlfQueues[previousRunQueue].removeFirstProcess());
						}
						
						if(previousRunQueue != -1){
							if(mlfQueues[previousRunQueue].isReplaced() && previousRunQueue != 0){
								preempted = false;
							}else{
								preempted = true;
							}
						}
						
						previousRunQueue = i;
						
						performanceChart.addGTElement(output.getPID(), time);
						
						if(mlfQueues.length > 1 && schedAlgo[i] == 6 && !mlfQueues[i].isEmptyQueue() && mlfQueues[i].getppIndex() >= 0) {
							if(mlfQueues[i].getCurrentTimeSlice() == 0) { //demotion
								if((i + 1) < mlfQueues.length) {
									ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
									mlfQueues[i+1].addProcess(temp);
								}
							}
						}
						break;
					}
				}
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				time++;
				if(checkifShouldEND()) {
					MLFQSimulatorGUI.addLabel(time);
					break;
				}
			}
		} else {
			//Fixed Time Slot
			int[] queueTimeSlot = new int[mlfQueues.length]; //this block assigns timeslices on queues
			int currentTimeSlice = 0;
			for(int i = 0; i < queueTimeSlot.length; i++) {
				queueTimeSlot[i] = (mlfQueues.length*2) - i;
			}
			int i = 0;
			
			while(true){
				ProcessControlBlock output = null;
				while(processes.size() > 0 && time == processes.get(0).getArrivalTime()) { //add process
					mlfQueues[entryQueue-1].addProcess(processes.get(0));
					processes.remove(0);
				}
				
				if(currentTimeSlice <= queueTimeSlot[i]) {
					MLFQSimulatorGUI.addLabel(time);
					if(!mlfQueues[i].isEmptyQueue()){
						output = mlfQueues[i].executeScheduling();
						performanceChart.addGTElement(output.getPID(), time);
						if((schedAlgo[i] == 3 || schedAlgo[i] == 5 || schedAlgo[i] == 6) && mlfQueues[i].isReplaced() && mlfQueues[i].getppIndex() > -1) {
							if((i - 1) >= 0) {
								ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
								mlfQueues[i-1].addProcess(temp);
								mlfQueues[i].resetPPIndex();
							}
						}

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						currentTimeSlice++;
						time++;
					}else {
						if(checkifShouldEND()){
							MLFQSimulatorGUI.addLabel(time);
							break;
						}
						currentTimeSlice = 0;
						i++; 
						if(i == mlfQueues.length) {
							i = 0;
						}
						
						int ctr = 0;
						for(int j = 0; j < mlfQueues.length; j++) {
							if(mlfQueues[j].isEmptyQueue()) {
								ctr++;
							}
						}
						
						if(ctr != 0) {

							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							currentTimeSlice++;
							time++;
						}
						
					}
					
				}else {
					if(!mlfQueues[i].isEmptyQueue() && !mlfQueues[i].isReplaced()) { //demotion
						if((i + 1) < mlfQueues.length) {
							ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(0);
							mlfQueues[i+1].addProcess(temp);
						}
					}
					currentTimeSlice = 0;
					i++;
					if(i == mlfQueues.length) {
						i = 0;
					}
				}
				if(checkifShouldEND()){
					MLFQSimulatorGUI.addLabel(time);
					break;
				}
			}
		}
		performanceChart.closeCurrentGantt(); 
		performanceChart.processPerformance();
		
		MLFQSimulatorGUI.setPerformance(performanceChart.getAveResponseTime(), performanceChart.getAveWaitTime(), performanceChart.getAveTurnAroundTime());
	}
	
	public boolean checkifShouldEND() { // check if no process to be executed for Fixed Time Slot
		boolean phase1 = false; //if process > 0
		boolean phase2 = true; // if all queues is empty
		if(processes.size() > 0) {
			phase1 = true;
		}
		for(int i = 0; i < mlfQueues.length; i++) {
			if(mlfQueues[i].isEmptyQueue() == false) {
				phase2 =  false;
			}
		}
		
		if(!phase1 && phase2) {
			return true;
		}else {
			return false;
		}
	}
	
	public GanttChart getPerformance() {
		return performanceChart;
	}
	
}
