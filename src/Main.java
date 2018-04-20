import processInformation.ProcessControlBlock;
import simulation.SimulationGUI;

import java.util.Random;

public class Main extends Thread{
	public Main(){
		int pID[]= {1,2,3,4,5,6,7,8,9,10};
		int arrivalTime[] = {50,34,10,49,29,5,7,48,0,24};
		int burstTime[] = {31,38,7,46,22,36,17,18,27,35};
		int priority[] = {15,20,9,5,5,5,5,5,5,5};
		
		MLFQ schedAlgo = new MLFQ();	
		
	//	for(int a = 0; a < pID.length; a++){							//for randomizing pa ini, in the mean time, let's settle for 7 laanay para masayon pagcheck
	//		ProcessControlBlock process = new ProcessControlBlock(pID[a], arrivalTime[a], burstTime[a], priority[a]);	
	//		schedAlgo.addProcess(process);
	//	}
		schedAlgo.execute();
		schedAlgo.getGanttChart().printContents();
		
	}
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
