package classicalSchedulingAlgorithms;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class FCFS{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	public void execute(){
		int time;
		while(running){
			if(processes.size() != 0){
				if(totalBurstTime >= processes.get(0).getArrivalTime()){
					System.out.println("Process: " + processes.get(0).getPID());
					while(processes.get(0).getBurstTime() != 0){
						time = processes.get(0).getBurstTime()-1;
						processes.get(0).setBurstTime(time);
						totalBurstTime++;
						
					}
					processes.remove(0);
				}else{
					totalBurstTime++;
				}
			}else{
				break;
			}
		}
		System.out.println("Total: " + totalBurstTime);	
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
		
		for(int a = 0; a < processes.size(); a++){
			if((process.getArrivalTime() < processes.get(a).getArrivalTime()) && processes.size() > 1){
				for(int b = processes.size() - 1; b >= a + 1 ; b--){
					processes.set(b, processes.get(b-1));
				}
				processes.set(a, process);
				break;
			}
		}
	}
	
	public void signalToEnd(){
		endSignal = true;
	}
	
	public void stop_thread(){
		running = false;
	}
	
	public static void executeAlgorithm(PriorityQueue<ProcessControlBlock> queue, ArrayList<GanttChartElement> ganttChart) {
		
	}
}
