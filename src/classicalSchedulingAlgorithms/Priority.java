package classicalSchedulingAlgorithms;

import java.util.ArrayList;

import processInformation.GanttChart;
import processInformation.ProcessControlBlock;

public class Priority extends SchedulingAlgorithm{
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	private ProcessControlBlock currentProcess;
	
	public void execute(GanttChart ganttChart){
		int time;
		
		if(arrivedProcesses.size() != 0){
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
			
			if(currentProcess == null) { //For Incrementing Execution History Information
				currentProcess = arrivedProcesses.get(0);
			} else if(currentProcess.getPID() != arrivedProcesses.get(0).getPID()) {
				currentProcess.incCPUPreempCounter();
				currentProcess = arrivedProcesses.get(0);
			}
			
			if(arrivedProcesses.get(0).getBurstTime() == 0){
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				arrivedProcesses.remove(0);
			}
		}
	}
	
	public void addProcess(ProcessControlBlock process){
		
		arrivedProcesses.add(process);
		
		for(int a = 0; a < arrivedProcesses.size(); a++){
			if((process.getPriority() < arrivedProcesses.get(a).getPriority()) ){
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
