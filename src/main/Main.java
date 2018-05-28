package main;

import java.util.ArrayList;

import classicalSchedulingAlgorithm.ClassicAlgorithm;
import classicalSchedulingAlgorithm.FCFS;
import gui.SimulationGUI;
import mlfq.MLFQ;
import process.ProcessControlBlock;

public class Main {

	public static void main(String[] args) {
		
/*		ArrayList<ProcessControlBlock> d = new ArrayList<ProcessControlBlock>();
		d.add(new ProcessControlBlock(1, 0, 34, 2));
		d.add(new ProcessControlBlock(2, 3, 30, 2));
		d.add(new ProcessControlBlock(3, 2, 45, 2));
		d.add(new ProcessControlBlock(4, 5, 23, 2));
		d.sort((o1, o2) -> new Integer(o1.getBurstTime()).compareTo(o2.getBurstTime()));
		for(int i =0; i<d.size();i++) {
			System.out.println(d.get(i).getArrivalTime());
		}
		
		
		
		ClassicAlgorithm s = new FCFS();*/
		
		SimulationGUI s = new SimulationGUI();
		
/*		int[] PIDs = {1,2,3};
		int[] AT = {0,1,2};
		int[] BT = {24,3,3};
		int[] PT = {3,1,4};
		
		int numQ = 1;
		int[] shedAlgo = {1};
		int prioPolicy = 1;
		int[] timeSlices = {3};
		int entry = 1;
		MLFQ z = new MLFQ(numQ, shedAlgo, prioPolicy, timeSlices, entry);
		z.initProcesses(PIDs, AT, BT, PT);
		z.execute();*/
		
	}

}
