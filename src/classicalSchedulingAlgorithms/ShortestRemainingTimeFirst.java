package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processInformation.GanttChart;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class ShortestRemainingTimeFirst extends SchedulingAlgorithm{
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	private ArrayList<ProcessControlBlock> finalProcessState = new ArrayList<ProcessControlBlock>();
	private ProcessControlBlock currentProcess;
	
	
	public void execute(GanttChart ganttChart){
		ProcessControlBlock temp;
		
		if(arrivedProcesses.size()  != 0) {
			int tempIndex = 0;
			temp = arrivedProcesses.get(tempIndex);
			for(int counter2 = 1; counter2 < arrivedProcesses.size(); counter2++)//selects from the arrivedlist with smallest burstime
			{
				if(arrivedProcesses.get(counter2).getBurstTime() < temp.getBurstTime()) {
					tempIndex = counter2;
				}else if(arrivedProcesses.get(counter2).getBurstTime() == temp.getBurstTime()) { //uses fifo if both burstime is equal
					if(arrivedProcesses.get(counter2).getArrivalTime() < temp.getArrivalTime()) {
						tempIndex = counter2;
					}
				}
			}
			
			//System.out.println("Process: " + arrivedProcesses.get(tempIndex).getPID());
			
			int burst = arrivedProcesses.get(tempIndex).getBurstTime();
			arrivedProcesses.get(tempIndex).setBurstTime(--burst);
			
			if(ganttChart.isEmpty()) {
				int PID = arrivedProcesses.get(tempIndex).getPID();
				ganttChart.addGTElement(PID);
			} else if (ganttChart.getLastElement().getPID() != arrivedProcesses.get(tempIndex).getPID()) {
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				int PID = arrivedProcesses.get(tempIndex).getPID();
				ganttChart.addGTElement(PID);
			}
			
			
			if(currentProcess == null) { //For Incrementing Execution History Information
				currentProcess = arrivedProcesses.get(tempIndex);
			} else if(currentProcess.getPID() != arrivedProcesses.get(tempIndex).getPID()) {
				currentProcess.incCPUPreempCounter();
				currentProcess = arrivedProcesses.get(tempIndex);

			}
			
			//for removing processes with no more burst time
			if(arrivedProcesses.get(tempIndex).getBurstTime() == 0)
			{
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				finalProcessState.add(arrivedProcesses.get(tempIndex));
				arrivedProcesses.remove(tempIndex);
			}
		}
		
		
		
/*		System.out.println("totalBurstTime: "+totalBurstTime);
		int lastIndex = 0;
		for(int counter = 0; counter < totalBurstTime; counter++)
		{
			ProcessControlBlock temp;
			for(int counter2 = 0; counter2 < processes.size(); counter2++){ //pushes arrived processes to the arrivedlist
				if(processes.get(counter2).getArrivalTime() <= counter) {
					temp = processes.get(counter2);
					arrivedProcesses.add(temp);
					processes.remove(counter2);
				}else {
					break;
				}
			}
			
			if(arrivedProcesses.size()  > 0) {
				int tempIndex = 0;
				temp = arrivedProcesses.get(tempIndex);
				for(int counter2 = 1; counter2 < arrivedProcesses.size(); counter2++)//selects from the arrivedlist with smallest burstime
				{
					if(arrivedProcesses.get(counter2).getBurstTime() < temp.getBurstTime()) {
						tempIndex = counter2;
					}else if(arrivedProcesses.get(counter2).getBurstTime() == temp.getBurstTime()) { //uses fifo if both burstime is equal
						if(arrivedProcesses.get(counter2).getArrivalTime() < temp.getArrivalTime()) {
							tempIndex = counter2;
						}
					}
				}
				
				int burst = arrivedProcesses.get(tempIndex).getBurstTime();
				arrivedProcesses.get(tempIndex).setBurstTime(--burst);
				//For GanntChart Creation/Update
				if(ganttChart.size() == 0 || ganttChart.get(ganttChart.size()-1).getPID() != arrivedProcesses.get(tempIndex).getPID())
				{
					if(ganttChart.size() != 0)
					{
						arrivedProcesses.get(tempIndex).incCPUPreempCounter();
					}
					GanttChartElement element = new GanttChartElement(arrivedProcesses.get(tempIndex).getPID(),
																counter, counter + 1);
					ganttChart.add(element);
					//ganttChart.get(ganttChart.size()-1).incEndTime();
					
				}
				else if(ganttChart.get(ganttChart.size()-1).getPID() == arrivedProcesses.get(tempIndex).getPID()){
					ganttChart.get(ganttChart.size()-1).incEndTime();
				}
				
				//for removing processes with no more burst time
				if(arrivedProcesses.get(tempIndex).getBurstTime() == 0)
				{
					finalProcessState.add(arrivedProcesses.get(tempIndex));
					arrivedProcesses.remove(tempIndex);
				}
			}
			
		}*/
	}
	
	public boolean isProcessing(){
		if(arrivedProcesses.size() != 0){
			return true;
		}
		
		return false;
	}
	
	public void addProcess(ProcessControlBlock process){
		arrivedProcesses.add(process);
	}
	
	public ProcessControlBlock getProcess(){
		if(arrivedProcesses.size() == 0){
			return null;
		}
		return arrivedProcesses.get(0);
	}
	
}
