package processInformation;

import java.util.ArrayList;

public class GanttChart{
	
	private ArrayList<GanttChartElement> ganttChart;
	int time;
	
	
	public GanttChart() {
		ganttChart = new ArrayList<GanttChartElement>();
		time = 0;
	}
	
	public void addGTElement(int PID) {
		ganttChart.add(new GanttChartElement(PID, time, 0));
	}
	
	public GanttChartElement getLastElement() {
		return ganttChart.get(ganttChart.size()-1);
	}
	
	public void setEndTime() {
		getLastElement().setEndTime(time);
	}
	
	public void incTime(){
		time++;
	}
	
	public boolean isEmpty() {
		if(ganttChart.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void printContents() {
		for(GanttChartElement e: ganttChart) {
			System.out.println("ProcessID: "+e.getPID()+"||Entry: "+e.getBeginTime()+"||End: "+e.getEndTime());
		}
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
}
