package mlfq;

import java.util.ArrayList;

import classicalSchedulingAlgorithm.FCFS;
import classicalSchedulingAlgorithm.NP_Prio;
import classicalSchedulingAlgorithm.Prio;
import classicalSchedulingAlgorithm.RR;
import classicalSchedulingAlgorithm.SJF;
import classicalSchedulingAlgorithm.SRTF;
import classicalSchedulingAlgorithm.SchedulingAlgorithm;
import process.GanttChart;
import process.ProcessControlBlock;

public class MLFQ {

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
		//performanceChart initialization;
	}
	
	
	public void execute() {
		performanceChart = new GanttChart(processes);
		int time = 0;
		if(prioPolicy == 1) { //Higher Before Lower
			while(true) {	
				if(processes.size() > 0 && time == processes.get(0).getArrivalTime()) {
					mlfQueues[entryQueue-1].addProcess(processes.get(0));
					processes.remove(0);
				}
				ProcessControlBlock output = null;
				for(int i = 0; i < mlfQueues.length; i++) {
					if(!mlfQueues[i].isEmptyQueue()) {
						output = mlfQueues[i].executeScheduling();
						performanceChart.addGTElement(output.getPID(), time);
						
						if(mlfQueues.length > 1 && schedAlgo[i] == 6 && !mlfQueues[i].isEmptyQueue() && mlfQueues[i].getppIndex() >= 0) {
							if(mlfQueues[i].getCurrentTimeSlice() == 0) { //demotion
								if((i + 1) < mlfQueues.length) {
									ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
									mlfQueues[i+1].addProcess(temp);
								}
							}else if(mlfQueues[i].getCurrentTimeSlice() > 0) { //promotion
									if((i - 1) >= 0) {
										ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
										mlfQueues[i-1].addProcess(temp);
									}

							}
						}
						break;
					}
				}
				
				time++;
				if(checkifShouldEND()) {
					break;
				}
			}
		} else { //Fixed Time Slot
			int[] queueTimeSlot = new int[mlfQueues.length]; //this block assigns timeslices on queues
			int currentTimeSlice = 0;
			for(int i = 0; i < queueTimeSlot.length; i++) {
				queueTimeSlot[i] = mlfQueues.length - i;
			}
			int i = 0;
			
			while(true){
				if(processes.size() > 0 && time == processes.get(0).getArrivalTime()) { //add process
					mlfQueues[entryQueue-1].addProcess(processes.get(0));
					processes.remove(0);
				}
				if(currentTimeSlice <= queueTimeSlot[i]) {
					if(!mlfQueues[i].isEmptyQueue()){
						System.out.println("i: "+ i + "::" + mlfQueues[i].executeScheduling().getPID());
						if(mlfQueues.length > 1 && schedAlgo[i] == 6 && !mlfQueues[i].isEmptyQueue() && mlfQueues[i].getppIndex() >= 0) {
							if(mlfQueues[i].getCurrentTimeSlice() == 0) { //demotion
								if((i + 1) < mlfQueues.length) {
									System.out.println("Demoted");
									ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
									mlfQueues[i+1].addProcess(temp);
									mlfQueues[i].resetPPIndex();
								}
							}else if(mlfQueues[i].getCurrentTimeSlice() > 0) { //promotion
									if((i - 1) >= 0) {
										System.out.println("Promoted");
										ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
										mlfQueues[i-1].addProcess(temp);
										mlfQueues[i].resetPPIndex();
									}
							}
						}
					}else {
						if(checkifShouldEND()){
							break;
						}
						currentTimeSlice = 0;
						i++;
						if(i == mlfQueues.length) {
							i = 0;
						}
					}
					currentTimeSlice++;
				}else {
					if(mlfQueues.length > 1 && schedAlgo[i] == 6 && !mlfQueues[i].isEmptyQueue() && mlfQueues[i].getppIndex() >= 0) {
						if(mlfQueues[i].getCurrentTimeSlice() == 0) { //demotion
							if((i + 1) < mlfQueues.length) {
								System.out.println("Demoted2");
								ProcessControlBlock temp = mlfQueues[i].getsQueue().remove(mlfQueues[i].getppIndex());
								mlfQueues[i+1].addProcess(temp);
								mlfQueues[i].resetPPIndex(); 
							}
						}
					}
					currentTimeSlice = 0;
					i++;
					if(i == mlfQueues.length) {
						i = 0;
					}
				}
				
				time++;
				if(checkifShouldEND()){
					break;
				}
				
			}
		}
		//System.out.println("time"+time);
		performanceChart.closeCurrentGantt(); //first before anything else for prope
		performanceChart.processPerformance();
		//performanceChart.printContents();
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
