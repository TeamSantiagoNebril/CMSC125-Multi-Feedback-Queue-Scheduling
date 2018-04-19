package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class ShortestRemainingTimeFirst extends SchedulingAlgorithm{
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private ArrayList<ProcessControlBlock> finalProcessState = new ArrayList<ProcessControlBlock>();
//	private Boolean running = true;
//	private boolean endSignal;
//	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public ArrayList<GanttChartElement> ganttChart = new ArrayList<GanttChartElement>();
//	private int totalBurstTime = 0;
	
	public void execute(){
		ProcessControlBlock temp;
		
		if(processes.size()  != 0) {
			int tempIndex = 0;
			temp = processes.get(tempIndex);
			for(int counter2 = 1; counter2 < processes.size(); counter2++)//selects from the arrivedlist with smallest burstime
			{
				if(processes.get(counter2).getBurstTime() < temp.getBurstTime()) {
					tempIndex = counter2;
				}else if(processes.get(counter2).getBurstTime() == temp.getBurstTime()) { //uses fifo if both burstime is equal
					if(processes.get(counter2).getArrivalTime() < temp.getArrivalTime()) {
						tempIndex = counter2;
					}
				}
			}
			System.out.println("Process: " + processes.get(tempIndex).getPID());
			
			int burst = processes.get(tempIndex).getBurstTime();
			processes.get(tempIndex).setBurstTime(--burst);
			
			//for removing processes with no more burst time
			if(processes.get(tempIndex).getBurstTime() == 0)
			{
				finalProcessState.add(processes.get(tempIndex));
				processes.remove(tempIndex);
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
		if(processes.size() != 0){
			return true;
		}
		
		return false;
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
/*		totalBurstTime += process.getBurstTime();
		Collections.sort(processes, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
		    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
		        return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
		    }
		});*/
	}
	
}
