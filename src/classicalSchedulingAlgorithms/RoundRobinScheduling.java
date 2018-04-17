package classicalSchedulingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public class RoundRobinScheduling {
	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private ArrayList<ProcessControlBlock> arrivedProcesses = new ArrayList<ProcessControlBlock>();
	public ArrayList<GanttChartElement> ganttChart = new ArrayList<GanttChartElement>();
	private int totalBurstTime = 0;
	private int timeQuantum;
	
	public RoundRobinScheduling(int quantumTime) {
		this.timeQuantum = quantumTime;
	}
	
	
	public void execute(){
		System.out.println("totalBurstTime: "+totalBurstTime);
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
		
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
		totalBurstTime += process.getBurstTime();
		/*Collections.sort(processes, new Comparator<ProcessControlBlock>() { //Sort according to arrivalTime
		    public int compare(ProcessControlBlock one, ProcessControlBlock other) {
		        return one.getArrivalTimeInInteger().compareTo(other.getArrivalTime());
		    }
		});*/
	}
	
	public static void executeAlgorithm(PriorityQueue<ProcessControlBlock> queue, ArrayList<GanttChartElement> ganttChart) {
		
	}
}
