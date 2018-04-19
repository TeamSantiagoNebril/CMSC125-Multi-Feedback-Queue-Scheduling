package classicalSchedulingAlgorithms;
import processInformation.ProcessControlBlock;
import java.util.ArrayList;

public class FCFS extends SchedulingAlgorithm{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private boolean executingProcess;
	public void execute(){
		int time;
		if(processes.size() != 0){
			System.out.println("Process: " + processes.get(0).getPID());
			executingProcess = true;
			time = processes.get(0).getBurstTime()-1;
			processes.get(0).setBurstTime(time);
			if(processes.get(0).getBurstTime() == 0){
				executingProcess = false;
				processes.remove(0);
			}
		}
			
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
		int a;
		if(executingProcess){
			a = 1;
		}else{
			a = 1;
		}
		
		for(; a < processes.size(); a++){
			if((process.getArrivalTime() < processes.get(a).getArrivalTime()) && processes.size() > 1){
				for(int b = processes.size() - 1; b >= a + 1 ; b--){
					processes.set(b, processes.get(b-1));
				}
				processes.set(a, process);
				break;
			}
		}
	}
	
	public ProcessControlBlock getProcess(){
		if(processes.size() == 0){
			return null;
		}
		return processes.get(0);
	}
	
	public ProcessControlBlock removeProcess(){
		ProcessControlBlock proc = processes.get(0);
		processes.remove(0);
		return proc;
	}
	
	public boolean isProcessing(){
		if(processes.size() != 0){
			return true;
		}
		return false;
	}
}
