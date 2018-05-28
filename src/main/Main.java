package main;

import java.util.ArrayList;

import classicalSchedulingAlgorithm.ClassicAlgorithm;
import classicalSchedulingAlgorithm.FCFS;
import gui.SimulationGUI;
import mlfq.MLFQ;
import process.ProcessControlBlock;

public class Main {

	public static void main(String[] args) {
		
		//SimulationGUI s = new SimulationGUI();
		
		int[] PIDs = {1,2,3};
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
		z.execute();
		
	}

}
