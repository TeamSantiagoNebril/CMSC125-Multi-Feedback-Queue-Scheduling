import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;
import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
import classicalSchedulingAlgorithms.RoundRobinScheduling;

import java.util.Random;
import java.util.ArrayList;
import classicalSchedulingAlgorithms.SJF;
import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;

public class Main extends Thread{
	private int time = 0;
	public Main(){
		int pID[]= {1, 2, 3};
		int arrivalTime[] = {0,1,2};
		int burstTime[] = {10,5,2};
		int priority[] = {15, 20, 9, 7, 8, 8, 17};
		int timeQuantum = 5;
		
		RoundRobinScheduling schedAlgo = new RoundRobinScheduling(timeQuantum);
		
		for(int a = 0; a < 3; a++){							//for randomizing pa ini, in the mean time, let's settle for 7 laanay para masayon pagcheck
			ProcessControlBlock process = new ProcessControlBlock(pID[a], arrivalTime[a], burstTime[a], priority[a]);	
			schedAlgo.addProcess(process);
		}
		schedAlgo.execute();
		for(GanttChartElement e: schedAlgo.ganttChart)
		{
			System.out.println("ProcessID:"+ e.getPID() +
								"\nbeginTime: "+ e.getBeginTime() +
									"\n endTime: "+ e.getEndTime()+"\n");
		}
	}
	
	
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
