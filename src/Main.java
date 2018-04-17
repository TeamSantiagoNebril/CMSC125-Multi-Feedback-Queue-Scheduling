import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;
import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
import classicalSchedulingAlgorithms.Priority;
import classicalSchedulingAlgorithms.RoundRobinScheduling;

import java.util.Random;
import java.util.ArrayList;
import classicalSchedulingAlgorithms.SJF;
import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;
import mlfq.MLFQ;

public class Main extends Thread{
	private int time = 0;
	public Main(){
	//Process Control Block Information
		int pID[]= {1, 2, 3, 4}; //Process IDs
		int arrivalTime[] = {0, 2, 4, 5}; //Arrival Time of Process IDs
		int burstTime[] = {7, 4, 1, 4}; //Burst Time of Process IDs
		int priority[] = {7, 4, 1, 4}; //Priority of Process IDs
		
		int timeQuantum = 5; //Time Quantum (For Round Robin)
		
		/*Scheduling Algorithms
		 * 	1 - FIFO
		 *  2 - SJF
		 *  3 - SRTF
		 *  4 - NPCPU (Non - Preemptive Prio)
		 *  5 - Preemptive Prio
		 *  6 - Round Robin
		 * */
		int numQueues = 1;
		int[] schedAlgorithms = {6};
		MLFQ schedAlgo = new MLFQ(numQueues, schedAlgorithms);
		//schedAlgo.initParameters(0,0,0,MLFQ.FIRST_ENTRY,0);
		for(int a = 0; a < 4; a++){							//for randomizing pa ini, in the mean time, let's settle for 7 laanay para masayon pagcheck
			ProcessControlBlock process = new ProcessControlBlock(pID[a], arrivalTime[a], burstTime[a], priority[a]);	
			schedAlgo.addProcess(process);
		}
		schedAlgo.execute();
		schedAlgo.printGanttChart();
		
	}
	
	
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
