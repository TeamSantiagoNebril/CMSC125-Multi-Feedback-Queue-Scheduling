package process;

import java.util.ArrayList;

public class GanttChart{
	
	private ArrayList<GanttChartElement> ganttChartElements;
	private ArrayList<ProcessControlBlock> processes;

	private float aveWaitTime;
	private float aveTurnAroundTime;
	private float aveResponseTime;
	String ganttChartResult;
	
	public GanttChart(ArrayList<ProcessControlBlock> processes) {
		this.processes = new ArrayList<ProcessControlBlock>();
		for(int i = 0; i < processes.size(); i++) {
			int pid = processes.get(i).getPID();
			int at = processes.get(i).getArrivalTime();
			int bt = processes.get(i).getBurstTime();
			int pt = processes.get(i).getPriority();
			this.processes.add(new ProcessControlBlock(pid, at, bt, pt));
		}
		ganttChartElements = new ArrayList<GanttChartElement>();
	}
	
	public void addGTElement(int PID, int time) {
		if(ganttChartElements.size() > 0 && getLastElement().getPID() == PID) {
			getLastElement().incEndTime();
		} else {
			if(ganttChartElements.size() > 0) {
				getLastElement().incEndTime();
			}
			ganttChartElements.add(new GanttChartElement(PID, time));
		}
	}
	
	public GanttChartElement getLastElement() {
		return ganttChartElements.get(ganttChartElements.size()-1);
	}
	
	
	public boolean isEmpty() {
		if(ganttChartElements.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String printContents() {
		String output = "";

		int line = 0;
		for(int index = 0; index < ganttChartElements.size(); index++) {
			GanttChartElement e = ganttChartElements.get(index);
			if(index == 0 || index != ganttChartElements.size()-1) {
				output += "["+ e.getBeginTime()+"| P:"+e.getPID()+" |"+e.getEndTime()+"]";
			}
			else {
				output += "[E:"+ e.getBeginTime()+" P:"+e.getPID()+" D:"+e.getEndTime()+"]";
			}

			if(line == 4) {
				line = 0;
			}else {
				line++;
			}
		}
		return output;
	}
	
	public void closeCurrentGantt() {
		getLastElement().close();
	}
	
	public boolean currentIsClosed() {
		if(getLastElement().isClose()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void processPerformance() {
		processes.sort((o1, o2) -> new Integer(o1.getPID()).compareTo(o2.getPID()));
		calculateTurnAroundTime();
		calculateWaitingTime();
		calculateResponseTime();
		ganttChartResult = printContents();
		System.out.println("average Turn Around Time: "+aveTurnAroundTime);
		System.out.println("average Wait Time: "+aveWaitTime);
		System.out.println("average Response Time: "+aveResponseTime);
	}
	
	float[] completionTime;
	float[] trnArndTime;
	float[] waitingTime;
	float[] responseTime;
	public void calculateTurnAroundTime() {
		completionTime = new float[processes.size()];
		trnArndTime = new float[processes.size()];
		waitingTime = new float[processes.size()];
		responseTime = new float[processes.size()];
		for(int i = 0; i < processes.size(); i++) {
			for(int j = 0; j < ganttChartElements.size(); j++) {
				if(processes.get(i).getPID() == ganttChartElements.get(j).getPID()) {
					completionTime[i] = ganttChartElements.get(j).getEndTime();
				}
			}
		}
		float sumOfTotaltrnarndTime = 0;
		for(int i = 0; i < completionTime.length; i++) {
			trnArndTime[i] = (completionTime[i] - processes.get(i).getArrivalTime()); 
			sumOfTotaltrnarndTime += trnArndTime[i];
		}
		
		aveTurnAroundTime = sumOfTotaltrnarndTime / (float)completionTime.length;
	}
	
	public void calculateWaitingTime() {
		float sumOfWaitingTime = 0;
		for(int i = 0; i < completionTime.length; i++) {
			//System.out.println("ct: "+ completionTime[i] +" at" + processes.get(i).getArrivalTime() + "tt: " + trnArndTime[i] + " bt: " + processes.get(i).getBurstTime());
			waitingTime[i] = (trnArndTime[i] - processes.get(i).getBurstTime());
			sumOfWaitingTime += waitingTime[i];
		}

		aveWaitTime = sumOfWaitingTime / completionTime.length;
	}

	public void calculateResponseTime() {
		float sumOfResponseTime = 0;
		for(int i = 0; i < processes.size(); i++) {
			for(int j = 0; j < ganttChartElements.size(); j++) {
				if(processes.get(i).getPID() == ganttChartElements.get(j).getPID()) {
					responseTime[i] = ganttChartElements.get(j).getBeginTime();
					break;
				}
			}
			sumOfResponseTime += responseTime[i];
		}
	
		aveResponseTime = sumOfResponseTime / completionTime.length;
	}
	
	public float getAveWaitTime() {
		return aveWaitTime;
	}

	public float getAveTurnAroundTime() {
		return aveTurnAroundTime;
	}

	public float getAveResponseTime() {
		return aveResponseTime;
	}
	
	public String getOutput() {
		return ganttChartResult;
	}

}