import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;
import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
<<<<<<< HEAD
import classicalSchedulingAlgorithms.Priority;
=======
import classicalSchedulingAlgorithms.RoundRobinScheduling;
>>>>>>> b74f7caea5ea373429c1b9c725b7b90151c5e49f

import java.util.Random;
import java.util.ArrayList;
import classicalSchedulingAlgorithms.SJF;
import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;

public class Main extends Thread{
	private int time = 0;
	public Main(){
<<<<<<< HEAD
		int pID[]= {1, 2, 3, 4, 5, 6, 7};
		int arrivalTime[] = {33, 25, 41, 26, 36, 0, 7};
		int burstTime[] = {6, 39, 14, 10, 42, 39, 48};
		int priority[] = {15, 20, 9, 7, 8, 25, 17};
		
		Priority schedAlgo = new Priority();
=======
		int pID[]= {1, 2, 3};
		int arrivalTime[] = {0,1,2};
		int burstTime[] = {10,5,2};
		int priority[] = {15, 20, 9, 7, 8, 8, 17};
		int timeQuantum = 5;
		
		RoundRobinScheduling schedAlgo = new RoundRobinScheduling(timeQuantum);
>>>>>>> b74f7caea5ea373429c1b9c725b7b90151c5e49f
		
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
