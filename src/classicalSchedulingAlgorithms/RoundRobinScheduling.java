package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processInformation.GanttChart;
import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class RoundRobinScheduling extends SchedulingAlgorithm{
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public ArrayList<GanttChartElement> ganttChart = new ArrayList<GanttChartElement>();
	private int timeQuantum;
	private int currentTimeQuantum;
	
	public RoundRobinScheduling(int quantumTime) {
		this.timeQuantum = quantumTime;
		currentTimeQuantum = 0;
	}
	
	
	public void execute(GanttChart ganttChart){
		if(arrivedProcesses.size() != 0)
		{
			//System.out.println("Process: " + arrivedProcesses.get(0).getPID());
			if(currentTimeQuantum == timeQuantum) {
				arrivedProcesses.get(0).incCPUPreempCounter();
				ProcessControlBlock temp = arrivedProcesses.get(0);
				arrivedProcesses.remove(0);
				arrivedProcesses.add(temp);
				currentTimeQuantum = 0;
			}
			
			int burst = arrivedProcesses.get(0).getBurstTime();
			arrivedProcesses.get(0).setBurstTime(--burst);
			
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
			
			
			currentTimeQuantum++;
			
			
			if(arrivedProcesses.get(0).getBurstTime() == 0) {
				if(ganttChart.currentIsClosed() == false) {
					ganttChart.closeCurrentGantt();
					ganttChart.setEndTime();
				}
				arrivedProcesses.remove(0);
			}
			
		}
		
		
	/*	System.out.println("totalBurstTime: "+totalBurstTime);
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
			
			
			if(arrivedProcesses.get(0).getBurstTime() == 0) {
				arrivedProcesses.remove(0);
			} else if(arrivedProcesses.size() != 1){
				arrivedProcesses.get(0).incCPUPreempCounter();
				temp = arrivedProcesses.get(0);
				arrivedProcesses.remove(0);
				arrivedProcesses.add(temp);
			}
			
			if((ganttChart.size() == 0) || ganttChart.get(ganttChart.size()-1).getPID() != arrivedProcesses.get(0).getPID()) {
				GanttChartElement element = new GanttChartElement(arrivedProcesses.get(0).getPID(),
						counter, counter);
				ganttChart.add(element);
			}

			for(int counter2 = 0; counter2 < timeQuantum; counter2++)
			{
				if(arrivedProcesses.get(0).getBurstTime() > 0) {
					int burst = arrivedProcesses.get(0).getBurstTime();
					arrivedProcesses.get(0).setBurstTime(--burst);
					ganttChart.get(ganttChart.size()-1).incEndTime();
					counter++;
				} else {
					break;
				}
			}
		}
	*/
	}
	
	public boolean isBurstTimeEmpty() {
		if(arrivedProcesses.get(0).getBurstTime() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void preEmpProcess() {
		
	}
	
	public boolean isProcessing(){
		if(arrivedProcesses.size() != 0){
			return true;
		}
		
		return false;
	}
	
	public ProcessControlBlock getProcess(){
		if(arrivedProcesses.size() == 0){
			return null;
		}
		return arrivedProcesses.get(0);
	}
	
	public void addProcess(ProcessControlBlock process){
		arrivedProcesses.add(process);
	}
}
