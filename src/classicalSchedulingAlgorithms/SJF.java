package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Comparator;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class SJF  implements ClassicalSchedulingMethods{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private Boolean running = true;
	private int totalBurstTime;
	private boolean endSignal;
	private boolean executingProcess;
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public void execute(ArrayList<GanttChartElement> gc, int time){
		
		int btime;
		if(arrivedProcesses.size() != 0){
			executingProcess = true;
			//System.out.println("Process: " + arrivedProcesses.get(0).getPID());
			if(gc.size() == 0 ||(gc.get(gc.size()-1).getPID() != arrivedProcesses.get(0).getPID())) {
				gc.add(new GanttChartElement(arrivedProcesses.get(0).getPID(), time, 0));
			}
			btime = arrivedProcesses.get(0).getBurstTime()-1;
			arrivedProcesses.get(0).setBurstTime(btime);
			totalBurstTime++;

			if(arrivedProcesses.get(0).getBurstTime() == 0){
				gc.get(gc.size()-1).setEndTime(time);
				executingProcess = false;
				arrivedProcesses.remove(0);
			}
		}else{
			totalBurstTime++;
		}
	}
	
	public void addProcess(ProcessControlBlock process){
		
		arrivedProcesses.add(process);
		int a;
		if(executingProcess){
			a = 1;
		}else{
			a = 0;
		}
		
		for(; a < arrivedProcesses.size(); a++){
			if((process.getBurstTime() < arrivedProcesses.get(a).getBurstTime()) ){
				for(int b = arrivedProcesses.size() - 1; b >= a + 1 ; b--){
					arrivedProcesses.set(b, arrivedProcesses.get(b-1));
				}
				arrivedProcesses.set(a, process);
				break;
			}
		}
	}
	
	public ProcessControlBlock removeProcess(int a){
		ProcessControlBlock proc = arrivedProcesses.get(a);
		arrivedProcesses.remove(a);
		return proc;
	}
	
	public boolean isProcessing(){
		if(arrivedProcesses.size() != 0){
			return true;
		}
		
		return false;
	}
	
	public void signalToEnd(){
		endSignal = true;
	}
	
	public void stop_thread(){
		running = false;
	}
	
	public void execute(ArrayList<ProcessControlBlock> e, ArrayList<GanttChartElement> f, int time) {
		
	}
	
	public int getProcessList(){
		return processes.size();
	}

}
