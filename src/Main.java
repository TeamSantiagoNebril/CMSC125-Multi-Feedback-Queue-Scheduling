import processInformation.ProcessControlBlock;
import java.util.Random;

public class Main extends Thread{
	public Main(){
		int pID[]= {1, 2, 3, 4, 5, 6, 7};
		int arrivalTime[] = {33, 25, 41, 26, 36, 0, 7};
		int burstTime[] = {6, 39, 14, 10, 5, 39, 48};
		int priority[] = {15, 20, 9, 7, 8, 8, 17};
		
		MLFQ schedAlgo = new MLFQ();
		
		
		for(int a = 0; a < 7; a++){							//for randomizing pa ini, in the mean time, let's settle for 7 laanay para masayon pagcheck
			ProcessControlBlock process = new ProcessControlBlock(pID[a], arrivalTime[a], burstTime[a], priority[a]);	
			schedAlgo.addProcess(process);
		}
		schedAlgo.execute();
		
	}
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
