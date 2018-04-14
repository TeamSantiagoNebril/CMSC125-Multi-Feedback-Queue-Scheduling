import processInformation.ProcessControlBlock;
import classicalSchedulingAlgorithms.FCFS;
import java.util.Random;
import java.util.ArrayList;

public class Main extends Thread{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private int time = 0;
	public Main(){
		int pID[]= {1, 2, 3, 4, 5, 6, 7};
		int arrivalTime[] = {33, 25, 41, 26, 36, 0, 7};
		int burstTime[] = {6, 39, 14, 10, 42, 39, 48};
		int priority[] = {15, 20, 9, 7, 8, 8, 17};
		
		for(int a = 0; a < 7; a++){							//for randomizing pa ini, in the mean time, let's settle for 7 laanay para masayon pagcheck
			ProcessControlBlock process = new ProcessControlBlock(pID[a], arrivalTime[a], burstTime[a], priority[a]);	
			processes.add(process);
		}
		
		ProcessControlBlock temp;
		for(int a = 0; a < 6; a++){
			for(int b = a + 1; b < 7; b++){
				if(processes.get(a).getArrivalTime() > processes.get(b).getArrivalTime()){
					temp = processes.get(a);
					processes.set(a, processes.get(b));
					processes.set(b, temp);
				}
			}
		}
		this.start();
	}
	
	public void run(){
		FCFS fcfs = new FCFS();
		fcfs.start();
		while(processes.size() != 0){
			time = processes.get(0).getArrivalTime() - time;
			try {
				Thread.sleep(time*100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fcfs.arriveProcess(processes.get(0));
			processes.remove(0);
		}
		
		fcfs.signalToEnd();
	}
	
	public static void main(String args[]){
		Main main = new Main();
	}
}
