package process;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;

public class GanttChart{
	
	private ArrayList<GanttChartElement> ganttChartElements;
	private ArrayList<ProcessControlBlock> processes;
	private JPanel panel;
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
	
	public void setPanel(JPanel panel){
		this.panel = panel;
	}
	
	/*public String printContents() {
		String output = "";
		Boolean start = true;
		int lastProcEndTime = -1;
		
		int line = 0;
		for(int index = 0; index < ganttChartElements.size(); index++) {
			GanttChartElement e = ganttChartElements.get(index);
			
			if(start || lastProcEndTime != e.getBeginTime()){
				start = false;
				JPanel timePanel = new JPanel(new GridLayout(2,1));
				JPanel blank = new JPanel();
				JPanel timeLabelPanel = new JPanel();
				JLabel timeLabel = new JLabel(e.getBeginTime() + "");
				
				timePanel.setBackground(Color.BLACK);
				blank.setBackground(Color.BLACK);
				timeLabelPanel.setBackground(Color.BLACK);
				timeLabel.setForeground(Color.WHITE);
				
				timeLabelPanel.add(timeLabel);
				timePanel.add(blank);
				timePanel.add(timeLabelPanel);
				panel.add(timePanel);
			}
			
			
			//output += "["+ e.getBeginTime()+"| P:"+e.getPID()+" |"+e.getEndTime()+"]";
			JPanel panel_proc = new JPanel();
			panel_proc.setBackground(Color.WHITE);
			JLabel label = new JLabel(e.getPID() + "");
			panel_proc.add(label);
			panel.add(panel_proc);
			
			JPanel timePanel = new JPanel(new GridLayout(2,1));
			JPanel blank = new JPanel();
			JPanel timeLabelPanel = new JPanel();
			JLabel timeLabel = new JLabel(e.getEndTime() + "");
			
			lastProcEndTime = e.getEndTime();
			
			timePanel.setBackground(Color.BLACK);
			blank.setBackground(Color.BLACK);
			timeLabelPanel.setBackground(Color.BLACK);
			timeLabel.setForeground(Color.WHITE);
			
			timeLabelPanel.add(timeLabel);
			timePanel.add(blank);
			timePanel.add(timeLabelPanel);
			panel.add(timePanel);

			if(line == 4) {
				line = 0;
			}else {
				line++;
			}
		}
		panel.revalidate();
		return output; 
	}*/
	
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
					responseTime[i] = ganttChartElements.get(j).getBeginTime() - processes.get(i).getArrivalTime();
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
