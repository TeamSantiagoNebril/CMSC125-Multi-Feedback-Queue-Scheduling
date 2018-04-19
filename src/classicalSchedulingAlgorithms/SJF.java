package classicalSchedulingAlgorithms;
import java.util.ArrayList;

import processInformation.GanttChart;
import processInformation.ProcessControlBlock;

public class SJF extends SchedulingAlgorithm{
	private boolean executingProcess;
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public void execute(GanttChart ganttChart){
		int time;
		if(arrivedProcesses.size() != 0){
			executingProcess = true;
			//System.out.println("Process: " + arrivedProcesses.get(0).getPID());
			time = arrivedProcesses.get(0).getBurstTime()-1;
			arrivedProcesses.get(0).setBurstTime(time);
			
			if(ganttChart.isEmpty()) {
				int PID = arrivedProcesses.get(0).getPID();
				ganttChart.addGTElement(PID);
			} else if (ganttChart.getLastElement().getPID() != arrivedProcesses.get(0).getPID()) {
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				int PID = arrivedProcesses.get(0).getPID();
				ganttChart.addGTElement(PID);
			}
			
			if(arrivedProcesses.get(0).getBurstTime() == 0){
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				executingProcess = false;
				arrivedProcesses.remove(0);
			}
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
	
	public ProcessControlBlock getProcess(){
		if(arrivedProcesses.size() == 0){
			return null;
		}
		return arrivedProcesses.get(0);
	}
	
	public ProcessControlBlock removeProcess(){
		ProcessControlBlock proc = arrivedProcesses.get(0);
		arrivedProcesses.remove(0);
		return proc;
	}
	
	public boolean isProcessing(){
		if(arrivedProcesses.size() != 0){
			return true;
		}
		return false;
	}
}
