package classicalSchedulingAlgorithms;
import processInformation.ProcessControlBlock;
import java.util.ArrayList;

public class FCFS extends Thread{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	public void run(){
		int time;
		while(running){
			if(processes.size() != 0){
				System.out.println("Process: " + processes.get(0).getPID());
				while(processes.get(0).getBurstTime() != 0){
					time = processes.get(0).getBurstTime()-1;
					processes.get(0).setBurstTime(time);
					totalBurstTime++;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				processes.remove(0);
			}else if(endSignal){
				break;
			}
		}
		System.out.println("Total: " + totalBurstTime);
		
	}
	public void arriveProcess(ProcessControlBlock process){
		processes.add(process);
	}
	
	public void signalToEnd(){
		endSignal = true;
	}
	
	public void stop_thread(){
		running = false;
	}
}
