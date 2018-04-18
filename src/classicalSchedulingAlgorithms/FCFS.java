package classicalSchedulingAlgorithms;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;
import java.util.ArrayList;
import java.util.Comparator;

public class FCFS implements ClassicalSchedulingMethods{
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
	
	public void execute(ArrayList<ProcessControlBlock> e, ArrayList<GanttChartElement> f, int time) {
		e.sort(new Comparator<ProcessControlBlock>() {
			 public int compare(ProcessControlBlock one, ProcessControlBlock other) {
                 return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
             }
		});
		if((f.size() != 0 && f.get(f.size()-1).getPID() != e.get(0).getPID()) || f.size() == 0) {
			f.add(new GanttChartElement(e.get(0).getPID(), time, 0));
		}
		
		e.get(0).decBurstTime();
		if(e.get(0).isEmptyBurstTime())
		{
			f.get(f.size()-1).setEndTime(time+1);
			e.remove(0);
		}
	}
}
